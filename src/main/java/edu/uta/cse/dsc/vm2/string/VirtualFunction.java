package edu.uta.cse.dsc.vm2.string;

import edu.uta.cse.dsc.vm2.SymbolicEnvironment;

public abstract class VirtualFunction extends StringFunction  {

	public VirtualFunction(SymbolicEnvironment env, String owner, String name,
			String desc) {
		super(env, owner, name, desc);
	}

	public abstract void INVOKEVIRTUAL(Object receiver);

	public void INVOKEVIRTUAL() {
		/* STUB */
	}

}
