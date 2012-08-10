package edu.uta.cse.dsc.vm2;

import java.util.ArrayList;
import java.util.List;

import org.evosuite.symbolic.expr.IntegerExpression;
import org.evosuite.symbolic.expr.RealExpression;

public final class LocalsTable {

	/**
	 * List of local variables
	 */
	private final List<Operand> locals = new ArrayList<Operand>();

	public LocalsTable(int maxLocals) {
		for (int i = 0; i < maxLocals; i++)
			locals.add(null);
	}

	public Object getRefLocal(int i) {
		Operand x = locals.get(i);
		ReferenceOperand refOp = (ReferenceOperand) x;
		return refOp.getReference();
	}

	public Operand getOperand(int i) {
		Operand x = locals.get(i);
		return x;
	}
	
	public void setRefLocal(int i, Object o) {
		locals.set(i, new ReferenceOperand(o));
	}

	public IntegerExpression getBv64Local(int i) {
		Operand x = locals.get(i);
		Bv64Operand bv64 = (Bv64Operand) x;
		return bv64.getIntegerExpression();
	}

	public IntegerExpression getBv32Local(int i) {
		Operand x = locals.get(i);
		Bv32Operand bv32 = (Bv32Operand) x;
		return bv32.getIntegerExpression();
	}

	public void setBv32Local(int i, IntegerExpression e) {
		locals.set(i, new Bv32Operand(e));
	}

	public void setBv64Local(int i, IntegerExpression e) {
		locals.set(i, new Bv64Operand(e));
	}

	public RealExpression getFp32Local(int i) {
		Operand x = locals.get(i);
		Fp32Operand fp32 = (Fp32Operand) x;
		return fp32.getRealExpression();
	}

	public RealExpression getFp64Local(int i) {
		Operand x = locals.get(i);
		Fp64Operand fp64 = (Fp64Operand) x;
		return fp64.getRealExpression();
	}

	public void setFp64Local(int i, RealExpression r) {
		locals.set(i, new Fp64Operand(r));
	}

	public void setFp32Local(int i, RealExpression r) {
		locals.set(i, new Fp32Operand(r));
	}

	public void setOperand(int i, Operand operand) {
		locals.set(i, operand);
	}
}
