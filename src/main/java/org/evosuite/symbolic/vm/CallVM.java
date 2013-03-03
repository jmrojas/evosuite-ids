package org.evosuite.symbolic.vm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.evosuite.symbolic.expr.bv.IntegerConstant;
import org.evosuite.symbolic.expr.fp.RealConstant;
import org.objectweb.asm.Type;

import edu.uta.cse.dsc.AbstractVM;
import edu.uta.cse.dsc.instrument.DscInstrumentingClassLoader;
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

	private final SymbolicEnvironment env;

	/**
	 * Constructor
	 */
	public CallVM(SymbolicEnvironment env,
			DscInstrumentingClassLoader classLoader) {
		this.env = env;
		this.classLoader = classLoader;
	}

	/**
	 * Start executing a static (class) initializer -- <clinit>()
	 */
	private void CLINIT_BEGIN(String className) {
		/*
		 * <clinit>() method can read textually earlier fields
		 */
		env.ensurePrepared(className);
		Frame frame = new StaticInitializerFrame(className);
		env.pushFrame(frame); // <clinit>() has no parameters
	}

	/**
	 * @param function
	 *            the method we are looking for in the frame stack
	 * @return constructor matches with the current frame, after discarding some
	 *         frames when necessary to match
	 */
	private boolean discardFrames(String className, String methName,
			Member function) {
		if (function == null)
			throw new IllegalArgumentException("function should be non null");

		if (env.topFrame() instanceof FakeBottomFrame)
			return false;

		Frame topFrame = env.topFrame();
		if (topFrame instanceof StaticInitializerFrame) {
			StaticInitializerFrame clinitFrame = (StaticInitializerFrame) topFrame;
			if (methName.equals(conf.INIT)
					&& clinitFrame.getClassName().equals(className)) {
				return true;
			}
		}

		if (function != null && function.equals(topFrame.getMember()))
			return true;

		env.popFrame();
		return discardFrames(className, methName, function);
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

		if (conf.CLINIT.equals(methName)) {

			discardFramesClassInitializer(className, methName);

		} else {

			// the method or constructor containing this handler
			Member function = null;
			if (conf.INIT.equals(methName))
				function = resolveConstructorOverloading(className, methDesc);
			else
				function = resolveMethodOverloading(className, methName,
						methDesc);

			/**
			 * function could be equal to null if handler is in class
			 * initializer
			 */
			discardFrames(className, methName, function);
		}

		env.topFrame().operandStack.clearOperands();
		/**
		 * This exception is added to the HANDLER_BEGIN because no other
		 * instruction adds the corresponding exception. The handler will store
		 * the exception to the locals table
		 */
		env.topFrame().operandStack.pushRef(ExceptionReference.getInstance());
	}

	private boolean discardFramesClassInitializer(String className,
			String methName) {
		if (!conf.CLINIT.equals(methName))
			throw new IllegalArgumentException("methName should be <clinit>");

		if (env.topFrame() instanceof FakeBottomFrame)
			return false;

		Frame topFrame = env.topFrame();
		if (topFrame instanceof StaticInitializerFrame) {
			StaticInitializerFrame clinitFrame = (StaticInitializerFrame) topFrame;
			if (methName.equals(conf.CLINIT)
					&& clinitFrame.getClassName().equals(className)) {
				return true;
			}
		}

		env.popFrame();
		return discardFramesClassInitializer(className, methName);
	}

	private final THashMap<Member, MemberInfo> memberInfos = new THashMap<Member, MemberInfo>();
	private final DscInstrumentingClassLoader classLoader;

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
		/* TODO: Use access param to determine needsThis */

		if (conf.CLINIT.equals(methName)) {
			CLINIT_BEGIN(className);
			return;
		}

		prepareStackIfNeeded(className, methName, methDesc);

		/* Begin of a method or constructor */
		Frame frame;
		boolean calleeNeedsThis = false;
		if (conf.INIT.equals(methName)) {
			Constructor<?> constructor = resolveConstructorOverloading(
					className, methDesc);
			int maxLocals = conf.MAX_LOCALS_DEFAULT;
			MemberInfo memberInfo = memberInfos.get(constructor);
			if (memberInfo != null)
				maxLocals = memberInfo.maxLocals;
			frame = new ConstructorFrame(constructor, maxLocals);
			calleeNeedsThis = true;
		} else {
			Method method = resolveMethodOverloading(className, methName,
					methDesc);
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
		if (env.topFrame().weInvokedInstrumentedCode() == false) { // guy who
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
		Iterator<Operand> it = env.topFrame().operandStack.iterator();
		for (int i = paramTypes.length - 1; i >= 0; i--) {
			// read parameters from caller operand srack
			Operand param = it.next();
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
			Operand param = it.next();
			ReferenceOperand refOperand = (ReferenceOperand) param;
			frame.localsTable.setRefLocal(0, refOperand.getReference());
		}

		env.pushFrame(frame);
	}

	private void prepareStackIfNeeded(String className, String methName,
			String methDesc) {

		Method method = null;
		if (env.isEmpty()) {
			Class<?> claz = classLoader.getClassForName(className);

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

		if (env.isEmpty()) {
			throw new IllegalStateException();
		}

	}

	@Override
	public void METHOD_BEGIN_RECEIVER(Object value) {
		if (!env.callerFrame().weInvokedInstrumentedCode()) {
			Reference ref = env.heap.getReference(value);
			env.topFrame().localsTable.setRefLocal(0, ref);
		}
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
	public void METHOD_BEGIN_PARAM(int nr, int index, Object conc_ref) {
		if (!env.callerFrame().weInvokedInstrumentedCode()) {
			Reference symb_ref = env.heap.getReference(conc_ref);
			env.topFrame().localsTable.setRefLocal(index, symb_ref);
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
			classes[i] = classLoader.getClassForType(asmTypes[i]);

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
	private boolean isIgnored(Method method) {
		if (Modifier.isNative(method.getModifiers()))
			return false;

		/* virtual method */

		if (method.getDeclaringClass().isAnonymousClass()) {
			// anonymous class
			String name = method.getDeclaringClass().getName();
			int indexOf = name.indexOf("$");
			String fullyQualifiedTopLevelClassName = name.substring(0, indexOf);
			return !conf.isIgnored(fullyQualifiedTopLevelClassName);
		} else {
			String declClass = method.getDeclaringClass().getCanonicalName();
			return !conf.isIgnored(declClass);
		}
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
		boolean instrumented = isIgnored(method);
		env.topFrame().invokeInstrumentedCode(instrumented);
		return method;
	}

	/**
	 * http://java.sun.com/docs/books/jvms/second_edition/html/Instructions2.
	 * doc6.html#invokestatic
	 */
	@Override
	public void INVOKESTATIC(String className, String methName, String methDesc) {
		stackParamCount = 0;
		env.topFrame().invokeNeedsThis = false;
		methodCall(className, methName, methDesc);
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
		stackParamCount = 0;
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
	 * The current instrumentation system only calls this version of
	 * INVOKEVIRTUAL for methods that take two or fewer parameters.
	 * 
	 * http://java.sun.com/docs/books/jvms/second_edition/html/Instructions2.
	 * doc6.html#invokevirtual
	 */
	@Override
	public void INVOKEVIRTUAL(Object conc_receiver, String className,
			String methName, String methDesc) {
		stackParamCount = 0;

		env.topFrame().invokeNeedsThis = true;

		Iterator<Operand> it = env.topFrame().operandStack.iterator();
		Type[] argTypes = Type.getArgumentTypes(methDesc);
		for (int i = 0; i < argTypes.length; i++) {
			it.next();
		}
		ReferenceOperand ref_operand = (ReferenceOperand) it.next();
		Reference symb_receiver = ref_operand.getReference();
		env.heap.initializeReference(conc_receiver, symb_receiver);

		if (nullReferenceViolation(conc_receiver, symb_receiver))
			return;

		Method staticMethod = methodCall(className, methName, methDesc);
		chooseReceiverType(className, conc_receiver, methDesc, staticMethod);
	}

	private boolean nullReferenceViolation(Object conc_receiver,
			Reference symb_receiver) {
		return conc_receiver == null;
	}

	/**
	 * We get this callback right before the user code makes the corresponding
	 * call to interface method className.methName(methDesc). See:
	 * {@link DscMethodAdapter#visitMethodInsn}
	 * 
	 * <p>
	 * http://java.sun.com/docs/books/jvms/second_edition/html/Instructions2.
	 * doc6.html#invokeinterface
	 */
	@Override
	public void INVOKEINTERFACE(Object conc_receiver, String className,
			String methName, String methDesc) {
		stackParamCount = 0;
		env.topFrame().invokeNeedsThis = true;
		if (nullReferenceViolation(conc_receiver, null))
			return;

		String concreteClassName = conc_receiver.getClass().getName();
		Method staticMethod = methodCall(concreteClassName, methName, methDesc);
		chooseReceiverType(className, conc_receiver, methDesc, staticMethod);

	}

	/**
	 * Add dynamic type of receiver to path condition.
	 */
	private void chooseReceiverType(String className, Object receiver,
			String methDesc, Method staticMethod) {

		if (nullReferenceViolation(receiver, null)) {
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

		if (!env.isEmpty() && env.topFrame().weInvokedInstrumentedCode())
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

		if (env.topFrame().weInvokedInstrumentedCode())
			// RETURN already did it
			return;
		else {
			/**
			 * Since control flow is returning from un-instrumented code, we
			 * must get rid of the method arguments since the callee did not
			 * consume the method arguments.
			 */
			env.topFrame().disposeMethInvokeArgs(desc);
		}
	}

	/**
	 * Our chance to capture the value returned by a native or un-instrumented
	 * method.
	 */
	@Override
	public void CALL_RESULT(boolean res, String owner, String name, String desc) {
		CALL_RESULT(owner, name, desc);

		if (env.topFrame().weInvokedInstrumentedCode()) { // RETURN already did
															// it
			return;
		} else {
			/**
			 * We are returning from uninstrumented code. This is the only way
			 * of storing the uninstrumented return value to the symbolic state.
			 */
			int i = res ? 1 : 0;
			IntegerConstant value = ExpressionFactory
					.buildNewIntegerConstant(i);
			env.topFrame().operandStack.pushBv32(value);
		}
	}

	/**
	 * int, short, byte all map to a BitVec32
	 * 
	 * TODO: Will this work for char?
	 */
	@Override
	public void CALL_RESULT(int res, String owner, String name, String desc) {
		CALL_RESULT(owner, name, desc);

		if (env.topFrame().weInvokedInstrumentedCode()) {// RETURN already did
															// it
			return;
		} else {
			/**
			 * We are returning from uninstrumented code. This is the only way
			 * of storing the uninstrumented return value to the symbolic state.
			 */
			IntegerConstant value = ExpressionFactory
					.buildNewIntegerConstant(res);
			env.topFrame().operandStack.pushBv32(value);
		}
	}

	@Override
	public void CALL_RESULT(Object res, String owner, String name, String desc) {
		CALL_RESULT(owner, name, desc);

		if (env.topFrame().weInvokedInstrumentedCode())
			// RETURN already did it
			return;
		else {
			/**
			 * We are returning from uninstrumented code. This is the only way
			 * of storing the method return value to the symbolic state.
			 */
			Reference symb_ref = env.heap.getReference(res);
			env.topFrame().operandStack.pushRef(symb_ref);
		}
	}

	@Override
	public void CALL_RESULT(long res, String owner, String name, String desc) {
		CALL_RESULT(owner, name, desc);

		if (env.topFrame().weInvokedInstrumentedCode()) {
			// RETURN already did it
			return;
		} else {
			/**
			 * We are returning from uninstrumented code. This is the only way
			 * of storing the uninstrumented return value to the symbolic state.
			 */
			IntegerConstant value = ExpressionFactory
					.buildNewIntegerConstant(res);
			env.topFrame().operandStack.pushBv64(value);
		}
	}

	@Override
	public void CALL_RESULT(double res, String owner, String name, String desc) {
		CALL_RESULT(owner, name, desc);

		if (env.topFrame().weInvokedInstrumentedCode()) {
			// RETURN already did it
			return;
		} else {
			/**
			 * We are returning from uninstrumented code. This is the only way
			 * of storing the uninstrumented return value to the symbolic state.
			 */
			RealConstant value = ExpressionFactory.buildNewRealConstant(res);
			env.topFrame().operandStack.pushFp64(value);
		}
	}

	@Override
	public void CALL_RESULT(float res, String owner, String name, String desc) {
		CALL_RESULT(owner, name, desc);

		if (env.topFrame().weInvokedInstrumentedCode()) {// RETURN already did
															// it
			return;
		} else {
			/**
			 * We are returning from uninstrumented code. This is the only way
			 * of storing the uninstrumented return value to the symbolic state.
			 */
			RealConstant value = ExpressionFactory.buildNewRealConstant(res);
			env.topFrame().operandStack.pushFp32(value);
		}

	}

	/**
	 * Nested class: Container for maximum size of operand stack and maximum
	 * number of local variables.
	 */
	private final static class MemberInfo {
		@SuppressWarnings("unused")
		final int maxStack, maxLocals;

		MemberInfo(int maxStack, int maxLocals) {
			this.maxStack = maxStack;
			this.maxLocals = maxLocals;
		}
	}

	int stackParamCount = 0;

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, int value) {
		stackParamCount++;
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, boolean value) {
		stackParamCount++;
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, short value) {
		stackParamCount++;
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, byte value) {
		stackParamCount++;
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, char value) {
		stackParamCount++;
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, long value) {
		stackParamCount++;
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, float value) {
		stackParamCount++;
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex, double value) {
		stackParamCount++;
	}

	@Override
	public void CALLER_STACK_PARAM(int nr, int calleeLocalsIndex,
			Object conc_ref) {
		stackParamCount++;

		int operand_index = stackParamCount - 1;
		Operand op = getOperand(operand_index);
		ReferenceOperand ref_op = (ReferenceOperand) op;
		Reference symb_ref = ref_op.getReference();

		env.heap.initializeReference(conc_ref, symb_ref);
	}

	private Operand getOperand(int index) {
		Operand op;
		Iterator<Operand> it = env.topFrame().operandStack.iterator();
		for (int i = 0; i < index + 1; i++) {
			op = it.next();
			if (i == index) {
				return op;
			}
		}
		return null;
	}

}
