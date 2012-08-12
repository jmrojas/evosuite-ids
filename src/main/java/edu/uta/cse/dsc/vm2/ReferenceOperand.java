package edu.uta.cse.dsc.vm2;

public class ReferenceOperand implements SingleWordOperand {

	private final Reference ref;

	public ReferenceOperand(Reference o) {
		this.ref = o;
	}

	public Reference getReference() {
		return this.ref;
	}

}