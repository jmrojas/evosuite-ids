package edu.uta.cse.dsc.vm2;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Deque;
import java.util.LinkedList;

import org.evosuite.symbolic.expr.IntegerConstant;
import org.evosuite.symbolic.expr.RealConstant;
import org.evosuite.symbolic.expr.StringConstant;
import org.objectweb.asm.Type;

import edu.uta.cse.dsc.AbstractVM;
import edu.uta.cse.dsc.DscHandler;
import edu.uta.cse.dsc.instrument.DscMethodAdapter;
import gnu.trove.map.hash.THashMap;

/**
 * Explicit inter-procedural control transfer: InvokeXXX, Return, etc.
 * 
 * We ignore the CALLER_STACK_PARAM calls here, as we have maintained the
 * operand stack during the caller's execution, so we already know the operand
 * stack values and therefore the parameter values to be used for a given method
 * invocation.
 * 
 * @author csallner@uta.edu (Christoph Csallner)
 */
public final class CallVM extends AbstractVM {

	private SymbolicEnvironment env;

	/**
	 * Constructor
	 */
	public CallVM(SymbolicEnvironment env) {
		this.env = env;
	}

	/**
	 * Start executing a static (class) initializer -- <clinit>()
	 */
	private void CLINIT_BEGIN(String className) {
		/*
		 * <clinit>() method can read textually earlier fields
		 */
		env.ensurePrepared(className);
		Frame frame = new StaticInitializerFrame();
		env.pushFrame(frame); // <clinit>() has no parameters
	}

	private void checkAsmAccessFlags(int access, Member member) {
		int jvmModifiers = member.getModifiers();
		if (access != jvmModifiers)
			throw new IllegalStateException("Asm modifiers != Java modifiers: "
					+ access + " " + jvmModifiers);
	}

	/**
	 * @param function
	 *            the method we are looking for in the frame stack
	 * @return constructor matches with the current frame, after discarding some
	 *         frames when necessary to match
	 */
	private boolean discardFrames(Member function) {
		if (function == null)
			throw new IllegalArgumentException("function should be non null");

		if (env.topFrame() instanceof FakeCallerFrame)
			return false;

		Frame topFrame = env.topFrame();
		if (topFrame instanceof StaticInitializerFrame)
			throw new UnsupportedOperationException(
					"TODO: topFrame isntanceof StaticInitializerFrame");

		if (function.equals(topFrame.getMember()))
			return true;

		env.popFrame();
		return discardFrames(function);
	}

	/**
	 * Begin of a basic block that is the begin of an exception handler.
	 * 
	 * We could be in an entirely different invocation frame than the previous
	 * instruction was in.
	 * 
	 * TODO: Account for different call sites in the same method. This may lead
	 * to the need to discard frames although they are of the same function as
	 * indicated by the parameters.
	 */
	@Override
	public void HANDLER_BEGIN(int access, String className, String methName,
			String methDesc) {

		// throw exception
		final Object throwable = env.topFrame().operandStack.popRef();

		Member function = null; // the method or constructor containing this
								// handler

		if (conf.CLINIT.equals(methName))
			throw new UnsupportedOperationException(
					"DSC marked this as TODO code!"); // TODO

		if (conf.INIT.equals(methName))
			function = resolveConstructorOverloading(className, methDesc);
		else
			function = resolveMethodOverloading(className, methName, methDesc);

		checkAsmAccessFlags(access, function); // sanity check
		discardFrames(function);

		env.topFrame().operandStack.clearOperands();
		env.topFrame().operandStack.pushRef(throwable);
	}

	private final THashMap<Member, MemberInfo> memberInfos = new THashMap<Member, MemberInfo>();

	/**
	 * Cache max values for this method, except for static initializers.
	 */
	@Override
	public void METHOD_MAXS(String className, String methName, String methDesc,
			int maxStack, int maxLocals) {
		if (conf.CLINIT.equals(methName))
			return;

		Member member = null;
		if (conf.INIT.equals(methName))
			member = resolveConstructorOverloading(className, methDesc);
		else
			member = resolveMethodOverloading(className, methName, methDesc);

		if (member == null)
			return; // TODO: could not resolve method or constructor

		if (memberInfos.contains(member))
			return;

		memberInfos.put(member, new MemberInfo(maxStack, maxLocals));
	}

	/**
	 * Pop operands off caller stack
	 * 
	 * Method methName is about to start execution.
	 * 
	 * At this point we have either already seen (observed InvokeXXX) or missed
	 * this invocation of the methName method.
	 * 
	 * We miss any of the following: - invoke <clinit> (as there are no such
	 * statements) - invoke <init> (as we do not add instrumentation code for
	 * these)
	 * 
	 * User code cannot call the <clinit>() method directly. Instead, the JVM
	 * invokes a class's initializer implicitly, upon the first use of the
	 * class.
	 * 
	 * http://java.sun.com/docs/books/jvms/second_edition/html/Concepts.doc.html
	 * #32316
	 * http://java.sun.com/docs/books/jvms/second_edition/html/Concepts.doc
	 * .html#19075
	 * http://java.sun.com/docs/books/jvms/second_edition/html/Overview
	 * .doc.html#16262
	 */
	@Override
	public void METHOD_BEGIN(int access, String className, String methName,
			String methDesc) {

		prepareStackIfNeeded(className, methName, methDesc);

		/* TODO: Use access param to determine needsThis */

		if (conf.CLINIT.equals(methName)) {
			CLINIT_BEGIN(className);
			return;
		}

		/* Begin of a method or constructor */
		Frame frame;
		boolean calleeNeedsThis = false;
		if (conf.INIT.equals(methName)) {
			Constructor<?> constructor = resolveConstructorOverloading(
					className, methDesc);
			checkAsmAccessFlags(access, constructor);
			int maxLocals = conf.MAX_LOCALS_DEFAULT;
			MemberInfo memberInfo = memberInfos.get(constructor);
			if (memberInfo != null)
				maxLocals = memberInfo.maxLocals;
			frame = new ConstructorFrame(constructor, maxLocals);
			calleeNeedsThis = true;
		} else {
			Method method = resolveMethodOverloading(className, methName,
					methDesc);
			checkAsmAccessFlags(access, method);
			int maxLocals = conf.MAX_LOCALS_DEFAULT;
			MemberInfo memberInfo = memberInfos.get(method);
			if (memberInfo != null)
				maxLocals = memberInfo.maxLocals;
			frame = new MethodFrame(method, maxLocals);
			calleeNeedsThis = !Modifier.isStatic(method.getModifiers());
		}

		/*
		 * If our caller called uninstrumented code then we should not ruin his
		 * operand stack! Instead, METHOD_BEGIN_PARAM will supply the concrete
		 * parameter values and create corresponding symbolic constants.
		 */
		if (!env.topFrame().weInvokedInstrumentedCode()) { // guy who
															// (transitively)
															// called us
			env.pushFrame(frame);
			return;
		}

		/*
		 * Our caller directly called us. We should take our parameters from his
		 * stack.
		 */
		Class<?>[] paramTypes = getArgumentClasses(methDesc);
		final Deque<Operand> params = new LinkedList<Operand>();
		for (int i = paramTypes.length - 1; i >= 0; i--) { // parameter list
			Operand param = env.topFrame().operandStack.popOperand(); // From
																		// direct
																		// caller
			params.push(param);
		}

		int index = 0;
		for (Operand param : params) {
			frame.localsTable.setOperand(index + (calleeNeedsThis ? 1 : 0),
					param);
			if (param instanceof SingleWordOperand)
				index += 1;
			else if (param instanceof DoubleWordOperand)
				index += 2;
			else {
				throw new IllegalStateException("Unknown operand type "
						+ param.getClass().getName());
			}
		}

		if (calleeNeedsThis) { // "this" instance
			Object param = env.topFrame().operandStack.popRef();
			frame.localsTable.setRefLocal(0, param);
			env.topFrame().operandStack.pushRef(param); // push back to caller
		}

		for (Operand param : params)
			env.topFrame().operandStack.pushOperand(param); // push back to
															// caller

		env.pushFrame(frame);
	}

	private void prepareStackIfNeeded(String className, String methName,
			String methDesc) {

		Method method = null;
		if (env.isEmpty()) {
			Class<?> claz = DscHandler.getClassForName(className);

			Method[] declMeths = claz.getDeclaredMethods();
			for (Method declMeth : declMeths) {
				if (!Modifier.isPublic(declMeth.getModifiers()))
					continue;
				if (declMeth.getName().equals(methName))
					method = declMeth;
			}

			if (method != null) {
				env.prepareStack(method);
			}
		}

	}

	@Override
	public void METHOD_BEGIN_RECEIVER(Object value) {
		if (!env.callerFrame().weInvokedInstrumentedCode())
			env.topFrame().localsTable.setRefLocal(0, value);
	}

	@Override
	public void METHOD_BEGIN_PARAM(int nr, int index, int value) {
		if (!env.callerFrame().weInvokedInstrumentedCode()) {
			IntegerConstant literal_value = ExpressionFactory
					.buildNewIntegerConstant(value);
			env.topFrame().localsTable.setBv32Local(index, literal_value);
		}
	}

	@Override
	public void METHOD_BEGIN_PARAM(int nr, int index, boolean value) {
		if (!env.callerFrame().weInvokedInstrumentedCode()) {
			METHOD_BEGIN_PARAM(nr, index, value ? 1 : 0);
		}
	}

	@Override
	public void METHOD_BEGIN_PARAM(int nr, int index, byte value) {
		if (!env.callerFrame().weInvokedInstrumentedCode()) {
			METHOD_BEGIN_PARAM(nr, index, (int) value);
		}
	}

	@Override
	public void METHOD_BEGIN_PARAM(int nr, int index, char value) {
		if (!env.callerFrame().weInvokedInstrumentedCode()) {
			METHOD_BEGIN_PARAM(nr, index, (int) value);
		}
	}

	@Override
	public void METHOD_BEGIN_PARAM(int nr, int index, short value) {
		if (!env.callerFrame().weInvokedInstrumentedCode()) {
			METHOD_BEGIN_PARAM(nr, index, (int) value);
		}
	}

	@Override
	public void METHOD_BEGIN_PARAM(int nr, int index, long value) {
		if (!env.callerFrame().weInvokedInstrumentedCode()) {
			IntegerConstant literal_value = ExpressionFactory
					.buildNewIntegerConstant(value);
			env.topFrame().localsTable.setBv64Local(index, literal_value);
		}
	}

	@Override
	public void METHOD_BEGIN_PARAM(int nr, int index, double value) {
		if (!env.callerFrame().weInvokedInstrumentedCode()) {
			RealConstant literal_value = ExpressionFactory
					.buildNewRealConstant(value);
			env.topFrame().localsTable.setFp64Local(index, literal_value);
		}
	}

	@Override
	public void METHOD_BEGIN_PARAM(int nr, int index, float value) {
		if (!env.callerFrame().weInvokedInstrumentedCode()) {
			RealConstant literal_value = ExpressionFactory
					.buildNewRealConstant(value);
			env.topFrame().localsTable.setFp32Local(index, literal_value);
		}
	}

	@Override
	public void METHOD_BEGIN_PARAM(int nr, int index, Object value) {
		if (!env.callerFrame().weInvokedInstrumentedCode()) {
			env.topFrame().localsTable.setRefLocal(index, value);
		}
	}

	/**
	 * Asm method descriptor --> Method parameters as Java Reflection classes.
	 * 
	 * Does not include the receiver for
	 */
	private Class<?>[] getArgumentClasses(String methDesc) {
		Class<?>[] classes;

		Type[] asmTypes = Type.getArgumentTypes(methDesc);
		classes = new Class<?>[asmTypes.length];
		for (int i = 0; i < classes.length; i++)
			classes[i] = DscHandler.getClass(asmTypes[i]);

		return classes;
	}

	/**
	 * Resolves (static) method overloading.
	 * 
	 * Ensures that owner class is prepared.
	 * 
	 * FIXME: user code calling java.util.Deque.isEmpty() crashes this method
	 * 
	 * @return method named name, declared by owner or one of its super-classes,
	 *         which has the parameters encoded in methDesc.
	 */
	private Method resolveMethodOverloading(String owner, String name,
			String methDesc) {
		Method method = null;
		final Deque<Class<?>> interfaces = new LinkedList<Class<?>>();

		Class<?> claz = env.ensurePrepared(owner);

		/* Resolve method overloading -- need method parameter types */
		Class<?>[] argTypes = getArgumentClasses(methDesc);

		while ((method == null) && (claz != null)) {
			Class<?>[] ifaces = claz.getInterfaces();
			for (Class<?> iface : ifaces)
				interfaces.add(iface);

			try { // TODO: Need getDeclaredMethods here?
				method = claz.getDeclaredMethod(name, argTypes);
			} catch (NoSuchMethodException nsme) { // TODO: do not use
													// exceptions
				claz = claz.getSuperclass();
			}

			if (claz == null && !interfaces.isEmpty())
				claz = interfaces.pop();
		}

		if (method == null)
			throw new IllegalArgumentException("Failed to resolve " + owner
					+ "." + name);

		return method;
	}

	private Constructor<?> resolveConstructorOverloading(String owner,
			String desc) {
		Constructor<?> constructor = null;

		Class<?> claz = env.ensurePrepared(owner);

		/* Resolve overloading -- need parameter types */
		Class<?>[] argTypes = getArgumentClasses(desc);

		try {
			constructor = claz.getDeclaredConstructor(argTypes);
		} catch (NoSuchMethodException nsme) {
			throw new IllegalArgumentException(
					"Failed to resolve constructor of " + owner);
		}

		return constructor;
	}

	/**
	 * @return method is instrumented. It is neither native nor declared by an
	 *         ignored JDK class, etc.
	 */
	protected boolean isInstrumented(Method method) {
		if (Modifier.isNative(method.getModifiers()))
			return false;

		String declClass = method.getDeclaringClass().getCanonicalName();
		return !conf.isIgnored(declClass);
	}

	/**
	 * Method call
	 * <ul>
	 * <li>not a constructor <init></li>
	 * <li>not a class initializer <clinit></li>
	 * </ul>
	 * 
	 * @return static method descriptor
	 */
	private Method methodCall(String className, String methName, String methDesc) {
		final Method method = resolveMethodOverloading(className, methName,
				methDesc);
		/* private method may be native */
		boolean instrumented = isInstrumented(method);
		env.topFrame().invokeInstrumentedCode(instrumented);
		return method;
	}

	/**
	 * http://java.sun.com/docs/books/jvms/second_edition/html/Instructions2.
	 * doc6.html#invokestatic
	 */
	@Override
	public void INVOKESTATIC(String className, String methName, String methDesc) {
		env.topFrame().invokeNeedsThis = false;
		methodCall(className, methName, methDesc);
	}

	/**
	 * @return receiverValue null-ness
	 */
	private boolean nullViolation(String methDesc, Object receiverValue) {

		if (receiverValue == null) { // JVM will throw an exception
			env.topFrame().operandStack.clearOperands(); // clear the operand
															// stack
			// push(); // TODO: Push new NullPointerException instance
			return true;
		}

		return false;
	}

	/**
	 * Used to invoke any
	 * <ul>
	 * <li>
	 * instance initialization method <init> = (constructor + field init)</li>
	 * <li>
	 * private method</li>
	 * <li>
	 * method of a superclass of the current class</li>
	 * </ul>
	 * 
	 * No dynamic dispatch (unlike InvokeVirtual)
	 * 
	 * http://java.sun.com/docs/books/jvms/second_edition/html/Instructions2.
	 * doc6.html#invokespecial
	 * http://java.sun.com/docs/books/jvms/second_edition
	 * /html/Overview.doc.html#12174
	 * http://java.sun.com/docs/books/jvms/second_edition
	 * /html/Concepts.doc.html#33032
	 */
	@Override
	public void INVOKESPECIAL(String className, String methName, String methDesc) {
		env.topFrame().invokeNeedsThis = true;

		if (conf.INIT.equals(methName)) {
			boolean instrumented = !conf.isIgnored(className);
			env.topFrame().invokeInstrumentedCode(instrumented);
		} else
			methodCall(className, methName, methDesc);
	}

	@Override
	public void INVOKESPECIAL(Object receiver, String className,
			String methName, String methDesc) {
		INVOKESPECIAL(className, methName, methDesc);
	}

	/**
	 * We get this callback right before the user code makes the corresponding
	 * virtual call to method className.methName(methDesc). See:
	 * {@link DscMethodAdapter#visitMethodInsn}
	 * 
	 * <p>
	 * This version does not pass the receiver instance :( Currently called for
	 * methods with 2, 3, or more parameters.
	 * 
	 * <p>
	 * Delete this version.
	 * 
	 * http://java.sun.com/docs/books/jvms/second_edition/html/Instructions2.
	 * doc6.html#invokevirtual
	 */
	@Deprecated
	@Override
	public void INVOKEVIRTUAL(String className, String methName, String methDesc) {
		env.topFrame().invokeNeedsThis = true;
		methodCall(className, methName, methDesc);
	}

	/**
	 * We get this callback right before the user code makes the corresponding
	 * virtual call to method className.methName(methDesc). See:
	 * {@link DscMethodAdapter#visitMethodInsn}
	 * 
	 * <p>
	 * The current instrumentation system only calls this version of
	 * INVOKEVIRTUAL for methods that take two or fewer parameters.
	 * 
	 * http://java.sun.com/docs/books/jvms/second_edition/html/Instructions2.
	 * doc6.html#invokevirtual
	 */
	@Override
	public void INVOKEVIRTUAL(Object receiver, String className,
			String methName, String methDesc) {
		env.topFrame().invokeNeedsThis = true;
		if (nullViolation(methDesc, receiver))
			return; // TODO: ATHROW

		Method staticMethod = methodCall(className, methName, methDesc);
		chooseReceiverType(className, receiver, methDesc, staticMethod);
	}

	/**
	 * We get this callback right before the user code makes the corresponding
	 * call to interface method className.methName(methDesc). See:
	 * {@link DscMethodAdapter#visitMethodInsn}
	 * 
	 * <p>
	 * This version does not pass the receiver instance :( Currently called for
	 * methods with 2, 3, or more parameters.
	 * 
	 * TODO: Delete this version.
	 * 
	 * http://java.sun.com/docs/books/jvms/second_edition/html/Instructions2.
	 * doc6.html#invokeinterface
	 */
	@Deprecated
	@Override
	public void INVOKEINTERFACE(String className, String methName,
			String methDesc) {
		env.topFrame().invokeNeedsThis = true;
		methodCall(className, methName, methDesc);
	}

	/**
	 * We get this callback right before the user code makes the corresponding
	 * call to interface method className.methName(methDesc). See:
	 * {@link DscMethodAdapter#visitMethodInsn}
	 * 
	 * <p>
	 * The current instrumentation system only calls this version of
	 * INVOKEINTERFACE for methods that take two or fewer parameters.
	 * 
	 * http://java.sun.com/docs/books/jvms/second_edition/html/Instructions2.
	 * doc6.html#invokeinterface
	 */
	@Override
	public void INVOKEINTERFACE(Object receiver, String className,
			String methName, String methDesc) {
		env.topFrame().invokeNeedsThis = true;
		if (nullViolation(methDesc, receiver))
			return; // TODO: ATHROW

		Method staticMethod = methodCall(className, methName, methDesc);
		chooseReceiverType(className, receiver, methDesc, staticMethod);
	}

	/**
	 * Add dynamic type of receiver to path condition.
	 */
	private void chooseReceiverType(String className, Object receiver,
			String methDesc, Method staticMethod) {

		if (receiver == null) {
			throw new IllegalArgumentException("we are post null-deref check");
		}

		/*
		 * Only encode the receiver type in a constraint if dynamic dispatach
		 * can happen: not(isFinal(static receiver type))
		 */
		final Class<?> staticReceiver = env.ensurePrepared(className);
		if (Modifier.isFinal(staticReceiver.getModifiers()))
			return;

		/*
		 * Heuristic: Do not encode the receiver type if a method is
		 * "final native", e.g., Object.getClass().
		 * 
		 * not( isNative(static method descriptor) && isFinal(static method
		 * descriptor))
		 */
		final int methodModifiers = staticMethod.getModifiers();
		if (Modifier.isNative(methodModifiers)
				&& Modifier.isFinal(methodModifiers))
			return;

	}

	private Frame popFrameAndDisposeCallerParams() {
		Frame frame = env.popFrame();

		if (env.topFrame().weInvokedInstrumentedCode())
			env.topFrame().disposeMethInvokeArgs(frame);

		return frame;
	}

	/**
	 * Dispose our frame, we have no value to return.
	 */
	@Override
	public void RETURN() {
		popFrameAndDisposeCallerParams();
	}

	/**
	 * Dispose our frame and transfer the return value back.
	 */
	@Override
	public void IRETURN() {
		Frame returningFrame = popFrameAndDisposeCallerParams();

		if (env.topFrame().weInvokedInstrumentedCode()) {
			Operand ret_val = returningFrame.operandStack.popOperand();
			env.topFrame().operandStack.pushOperand(ret_val);
		}
	}

	@Override
	public void LRETURN() {
		IRETURN();
	}

	@Override
	public void FRETURN() {
		IRETURN();
	}

	@Override
	public void DRETURN() {
		IRETURN();
	}

	@Override
	public void ARETURN() {
		IRETURN();
	}

	/**
	 * No actual return value.
	 * 
	 * If we invoked uninstrumented code, then throw away the parameters passed
	 * to that uninstrumented code.
	 */
	@Override
	public void CALL_RESULT(String owner, String name, String desc) {
		if (env.topFrame().weInvokedInstrumentedCode()) // RETURN already did it
			return;

		env.topFrame().disposeMethInvokeArgs(desc);
	}

	/**
	 * Our chance to capture the value returned by a native or un-instrumented
	 * method.
	 */
	@Override
	public void CALL_RESULT(boolean res, String owner, String name, String desc) {
		CALL_RESULT(owner, name, desc);

		if (env.topFrame().weInvokedInstrumentedCode()) // RETURN already did it
			return;

		int i = res ? 1 : 0;
		IntegerConstant value = ExpressionFactory.buildNewIntegerConstant(i);
		env.topFrame().operandStack.pushBv32(value);
	}

	/**
	 * int, short, byte all map to a BitVec32
	 * 
	 * TODO: Will this work for char?
	 */
	@Override
	public void CALL_RESULT(int res, String owner, String name, String desc) {
		CALL_RESULT(owner, name, desc);

		if (env.topFrame().weInvokedInstrumentedCode()) // RETURN already did it
			return;

		IntegerConstant value = ExpressionFactory.buildNewIntegerConstant(res);
		env.topFrame().operandStack.pushBv32(value);

	}

	@Override
	public void CALL_RESULT(Object res, String owner, String name, String desc) {
		CALL_RESULT(owner, name, desc);

		if (env.topFrame().weInvokedInstrumentedCode()) // RETURN already did it
			return;

		// This is the only way we have of recovering fresh objects
		if (res instanceof String) {
			String string = (String) res;
			StringConstant strConstant = ExpressionFactory
					.buildNewStringConstant(string);
			env.topFrame().operandStack.pushStringRef(strConstant);
		} else {
			env.topFrame().operandStack.pushRef(res);
		}
	}

	@Override
	public void CALL_RESULT(long res, String owner, String name, String desc) {
		CALL_RESULT(owner, name, desc);

		if (env.topFrame().weInvokedInstrumentedCode()) // RETURN already did it
			return;

		IntegerConstant value = ExpressionFactory.buildNewIntegerConstant(res);
		env.topFrame().operandStack.pushBv64(value);
	}

	@Override
	public void CALL_RESULT(double res, String owner, String name, String desc) {
		CALL_RESULT(owner, name, desc);

		if (env.topFrame().weInvokedInstrumentedCode()) // RETURN already did it
			return;

		RealConstant value = ExpressionFactory.buildNewRealConstant(res);
		env.topFrame().operandStack.pushFp64(value);
	}

	@Override
	public void CALL_RESULT(float res, String owner, String name, String desc) {
		CALL_RESULT(owner, name, desc);

		if (env.topFrame().weInvokedInstrumentedCode()) // RETURN already did it
			return;

		RealConstant value = ExpressionFactory.buildNewRealConstant(res);
		env.topFrame().operandStack.pushFp32(value);
	}

	/**
	 * Nested class: Container for maximum size of operand stack and maximum
	 * number of local variables.
	 */
	private final static class MemberInfo {
		final int maxStack, maxLocals;

		MemberInfo(int maxStack, int maxLocals) {
			this.maxStack = maxStack;
			this.maxLocals = maxLocals;
		}
	}
}
