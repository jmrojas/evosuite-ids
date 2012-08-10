package edu.uta.cse.dsc.vm2;

import edu.uta.cse.dsc.DscHandler;
import edu.uta.cse.dsc.MainConfig;
import gnu.trove.set.hash.THashSet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;

public final class SymbolicEnvironment {

	private boolean isStopped = false;

	public boolean isStopped() {
		return isStopped;
	}

	/**
	 * Stack of function/method/constructor invocation frames
	 */
	private final Deque<Frame> frames = new LinkedList<Frame>();

	/**
	 * Classes whose static fields have been set to the default zero value or a
	 * dummy value.
	 */
	private final Set<Class<?>> preparedClasses = new THashSet<Class<?>>();

	public Frame topFrame() {
		return frames.peek();
	}

	public void pushFrame(Frame frame) {
		frames.push(frame);
	}

	public Frame callerFrame() {
		Frame top = frames.pop();
		Frame res = frames.peek();
		frames.push(top);
		return res;
	}

	public Frame popFrame() {
		return frames.pop();
	}

	public Class<?> ensurePrepared(String className) {
		Class<?> claz = null;
		claz = DscHandler.getClassForName(className);
		ensurePrepared(claz);
		return claz;
	}

	public void ensurePrepared(Class<?> claz) {
		if (preparedClasses.contains(claz))
			return; // done, we have prepared this class earlier

		Class<?> superClass = claz.getSuperclass();
		if (superClass != null)
			ensurePrepared(superClass); // prepare super class first

		String className = claz.getCanonicalName();
		Field[] fields = claz.getDeclaredFields();

		final boolean isIgnored = MainConfig.get().isIgnored(className);

		for (Field field : fields) {

			final int fieldModifiers = field.getModifiers();
			if (isIgnored && Modifier.isPrivate(fieldModifiers))
				continue; // skip private field of ignored class.

		}
		preparedClasses.add(claz);

	}

	/**
	 * Prepare stack of function invocation frames.
	 * 
	 * Clear function invocation stack, push a frame that pretends to call the
	 * method under test. We push variables for our method onto the
	 * pseudo-callers stack, so our method can pop them from there.
	 */
	public void prepareStack(Method mainMethod) {
		frames.clear();
		frames.push(new FakeCallerFrame());

		final Frame frame = new MethodFrame(mainMethod,
				MainConfig.get().MAX_LOCALS_DEFAULT); // fake caller of method
														// under test

		if (mainMethod != null) {
			boolean isInstrumented = isInstrumented(mainMethod);
			frame.invokeInstrumentedCode(isInstrumented);
			String[] emptyStringArray = new String[] {};
			frame.operandStack.pushRef(emptyStringArray);
		}
		frames.push(frame);
	}

	/**
	 * @return method is instrumented. It is neither native nor declared by an
	 *         ignored JDK class, etc.
	 */
	private static boolean isInstrumented(Method method) {
		if (Modifier.isNative(method.getModifiers()))
			return false;

		String declClass = method.getDeclaringClass().getCanonicalName();
		return !MainConfig.get().isIgnored(declClass);
	}

	public boolean isEmpty() {
		return frames.isEmpty();
	}

}
