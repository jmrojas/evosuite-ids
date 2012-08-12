package edu.uta.cse.dsc.vm2.string;

import java.util.HashMap;

import edu.uta.cse.dsc.AbstractVM;
import edu.uta.cse.dsc.vm2.SymbolicEnvironment;
import edu.uta.cse.dsc.vm2.string.builder.SB_Append;
import edu.uta.cse.dsc.vm2.string.builder.SB_Init;
import edu.uta.cse.dsc.vm2.string.builder.SB_ToString;

/**
 * This listeners deals with trapping function calls to symbolic functions from
 * java.lang.String
 * 
 * This listener is expected to be executed after the CallVM listener.
 * 
 * @author galeotti
 * 
 */
public final class StringFunctionCallVM extends AbstractVM {

	private static class StringFunctionKey {
		public StringFunctionKey(String owner, String name, String desc) {
			super();
			this.owner = owner;
			this.name = name;
			this.desc = desc;
		}

		public String owner;
		public String name;
		public String desc;

		@Override
		public int hashCode() {
			return owner.hashCode() + name.hashCode() + desc.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (o == null || !o.getClass().equals(StringFunctionKey.class)) {
				return false;
			} else {
				StringFunctionKey that = (StringFunctionKey) o;
				return this.owner.equals(that.owner)
						&& this.name.equals(that.name)
						&& this.desc.equals(that.desc);
			}
		}
	}

	public static final String JAVA_LANG_STRING = String.class.getName()
			.replace(".", "/");

	public static final String JAVA_LANG_STRING_BUILDER = StringBuilder.class
			.getName().replace(".", "/");

	private HashMap<StringFunctionKey, VirtualFunction> invokeVirtual = new HashMap<StringFunctionKey, VirtualFunction>();
	private HashMap<StringFunctionKey, StaticFunction> invokeStatic = new HashMap<StringFunctionKey, StaticFunction>();
	private HashMap<StringFunctionKey, SpecialFunction> invokeSpecial = new HashMap<StringFunctionKey, SpecialFunction>();

	private final SymbolicEnvironment env;

	public StringFunctionCallVM(SymbolicEnvironment env) {
		this.env = env;
		fillFunctionTables();
	}

	private void fillFunctionTables() {
		invokeVirtual.clear();
		invokeStatic.clear();
		invokeSpecial.clear();

		// java.lang.String
		addNewVirtualFunction(new CharAt(env));
		addNewVirtualFunction(new CompareTo(env));
		addNewVirtualFunction(new CompareToIgnoreCase(env));
		addNewVirtualFunction(new Concat(env));
		addNewVirtualFunction(new Contains(env));
		addNewVirtualFunction(new EndsWith(env));
		addNewVirtualFunction(new Equals(env));
		addNewVirtualFunction(new EqualsIgnoreCase(env));
		addNewVirtualFunction(new IndexOf.IndexOf_C(env));
		addNewVirtualFunction(new IndexOf.IndexOf_S(env));
		addNewVirtualFunction(new IndexOf.IndexOf_CI(env));
		addNewVirtualFunction(new IndexOf.IndexOf_SI(env));
		addNewVirtualFunction(new LastIndexOf.LastIndexOf_C(env));
		addNewVirtualFunction(new LastIndexOf.LastIndexOf_S(env));
		addNewVirtualFunction(new LastIndexOf.LastIndexOf_CI(env));
		addNewVirtualFunction(new LastIndexOf.LastIndexOf_SI(env));
		addNewVirtualFunction(new Length(env));
		addNewVirtualFunction(new RegionMatches(env));
		addNewVirtualFunction(new Replace.Replace_C(env));
		addNewVirtualFunction(new Replace.Replace_CS(env));
		addNewVirtualFunction(new ReplaceAll(env));
		addNewVirtualFunction(new ReplaceFirst(env));
		addNewVirtualFunction(new StartsWith(env));
		addNewVirtualFunction(new Substring(env));
		addNewVirtualFunction(new ToLowerCase(env));
		addNewVirtualFunction(new ToString(env));
		addNewVirtualFunction(new ToUpperCase(env));
		addNewVirtualFunction(new Trim(env));
		addNewStaticFunction(new ValueOf.ValueOf_O(env));

		// java.lang.StringBuilder
		addNewSpecialFunction(new SB_Init.StringBuilderInit_S(env));
		addNewVirtualFunction(new SB_Append.Append_C(env));
		addNewVirtualFunction(new SB_Append.Append_S(env));
		addNewVirtualFunction(new SB_ToString(env));
		
	}

	private void addNewVirtualFunction(VirtualFunction f) {
		StringFunctionKey k = new StringFunctionKey(f.getOwner(), f.getName(),
				f.getDesc());
		StringFunction prev = this.invokeVirtual.put(k, f);
		if (prev != null) {
			throw new IllegalArgumentException(
					"Adding two functions with the same key!");
		}
	}

	private void addNewSpecialFunction(SpecialFunction f) {
		StringFunctionKey k = new StringFunctionKey(f.getOwner(), f.getName(),
				f.getDesc());
		StringFunction prev = this.invokeSpecial.put(k, f);
		if (prev != null) {
			throw new IllegalArgumentException(
					"Adding two functions with the same key!");
		}
	}

	private void addNewStaticFunction(StaticFunction f) {
		StringFunctionKey k = new StringFunctionKey(f.getOwner(), f.getName(),
				f.getDesc());
		StringFunction prev = this.invokeStatic.put(k, f);
		if (prev != null) {
			throw new IllegalArgumentException(
					"Adding two functions with the same key!");
		}
	}

	@Override
	public void CALL_RESULT(int res, String owner, String name, String desc) {
		StringFunction f = getStringFunction(owner, name, desc);
		if (f == null) {
			return; // do nothing
		}
		f.CALL_RESULT(res);
	}

	@Override
	public void INVOKEVIRTUAL(Object receiver, String owner, String name,
			String desc) {
		if (receiver == null) {
			// CallVM takes care of all NullPointerException details
			return; // do nothing;
		}

		VirtualFunction f = (VirtualFunction) this
				.getStringFunction(owner, name, desc);
		if (f == null) {
			// Unsupported string function
			return; // do nothing
		}
		f.INVOKEVIRTUAL(receiver);
	}

	@Override
	public void INVOKEVIRTUAL(String owner, String name, String desc) {
		VirtualFunction f = (VirtualFunction) this
				.getStringFunction(owner, name, desc);
		if (f == null) {
			// Unsupported string function
			return; // do nothing
		}
		f.INVOKEVIRTUAL();
	}

	@Override
	public void CALL_RESULT(Object res, String owner, String name, String desc) {
		StringFunction f = getStringFunction(owner, name, desc);
		if (f == null) {
			return; // do nothing
		}
		f.CALL_RESULT(res);
	}

	private StringFunction getStringFunction(String owner, String name,
			String desc) {
		StringFunction f;
		StringFunctionKey k = new StringFunctionKey(owner, name, desc);
		f = invokeVirtual.get(k);
		if (f == null) {
			f = invokeStatic.get(k);
		}
		if (f == null) {
			f = invokeSpecial.get(k);
		}
		return f;
	}

	@Override
	public void CALL_RESULT(boolean res, String owner, String name, String desc) {
		StringFunction f = getStringFunction(owner, name, desc);
		if (f == null) {
			return; // do nothing
		}
		f.CALL_RESULT(res);
	}

	@Override
	public void CALL_RESULT(String owner, String name, String desc) {
		StringFunction f = getStringFunction(owner, name, desc);
		if (f == null) {
			return; // do nothing
		}
		f.CALL_RESULT();
	}

	@Override
	public void INVOKESTATIC(String owner, String name, String desc) {
		StaticFunction f = (StaticFunction) this.getStringFunction(
				owner, name, desc);
		if (f == null) {
			// Unsupported string function
			return; // do nothing
		}
		f.INVOKESTATIC();
	}

	@Override
	public void INVOKESPECIAL(String owner, String name, String desc) {
		SpecialFunction f = (SpecialFunction) this
				.getStringFunction(owner, name, desc);
		if (f == null) {
			// Unsupported string function
			return; // do nothing
		}
		f.INVOKESPECIAL();
	}
}
