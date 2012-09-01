/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 * 
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.coverage.dataflow;

import org.evosuite.graphs.cfg.BytecodeInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract superclass for all Definitions and Uses
 * 
 * @author Andre Mis
 */
public class DefUse extends BytecodeInstruction {

	private static final long serialVersionUID = -2643584238269671760L;

	private static Logger logger = LoggerFactory.getLogger(DefUse.class);

	int defuseId;
	int defId;
	int useId;
	boolean isParameterUse;
	boolean isFieldMethodCall;
	boolean isFieldMethodCallDefinition;
	boolean isFieldMethodCallUse;

	String varName;

	/**
	 * <p>
	 * Constructor for DefUse.
	 * </p>
	 * 
	 * @param wrap
	 *            a {@link org.evosuite.graphs.cfg.BytecodeInstruction} object.
	 * @param defuseId
	 *            a int.
	 * @param defId
	 *            a int.
	 * @param useId
	 *            a int.
	 * @param isParameterUse
	 *            a boolean.
	 */
	protected DefUse(BytecodeInstruction wrap) {
		super(wrap);
		if (!DefUsePool.isKnown(wrap))
			throw new IllegalArgumentException(
			        "only instructions known by the DefUsePool are accepted");
		if (defuseId < 0)
			throw new IllegalArgumentException("expect defUseId to be positive");
		if (defId < 0 && useId < 0)
			throw new IllegalArgumentException("expect either defId or useId to be set");

		this.defuseId = DefUsePool.getRegisteredDefUseId(wrap);
		this.defId = DefUsePool.getRegisteredDefId(wrap);
		this.useId = DefUsePool.getRegisteredUseId(wrap);
		this.isParameterUse = DefUsePool.isKnownAsParameterUse(wrap);
		this.isFieldMethodCall = DefUsePool.isKnownAsFieldMethodCall(wrap);
		if (this.isFieldMethodCall) {
			if (DefUsePool.isKnownAsDefinition(wrap))
				isFieldMethodCallDefinition = true;
			if (DefUsePool.isKnownAsUse(wrap))
				isFieldMethodCallUse = true;
			if (!(isFieldMethodCallDefinition || isFieldMethodCallUse))
				throw new IllegalStateException(
				        "field method calls only accepted once they got categorized");
		}

		this.varName = super.getVariableName();
		if (this.varName == null)
			throw new IllegalStateException(
			        "expect defUses to have non-null varaible names");
	}

	@Override
	public boolean isFieldMethodCallDefinition() {
		return isFieldMethodCallDefinition;
	}

	@Override
	public boolean isFieldMethodCallUse() {
		return isFieldMethodCallUse;
	}

	/**
	 * Determines whether the given BytecodeInstruction constitutes a Definition
	 * that can potentially become an active Definition for this DefUse
	 * 
	 * in the sense that if control flow passes through the instruction of the
	 * given Definition that Definition becomes active for this DefUse's
	 * variable
	 * 
	 * This is the case if the given Definition defines the same variable as
	 * this DefUse So a Definition canBecomeActive for itself
	 * 
	 * @param instruction
	 *            a {@link org.evosuite.graphs.cfg.BytecodeInstruction} object.
	 * @return a boolean.
	 */
	public boolean canBecomeActiveDefinition(BytecodeInstruction instruction) {
		if (!instruction.isDefinition())
			return false;

		// Definition otherDef = DefUseFactory.makeDefinition(instruction);
		return sharesVariableWith(instruction);
	}

	/**
	 * Determines whether the given DefUse reads or writes the same variable as
	 * this DefUse
	 * 
	 * @param du
	 *            a {@link org.evosuite.coverage.dataflow.DefUse} object.
	 * @return a boolean.
	 */
	public boolean sharesVariableWith(DefUse du) {
		return varName.equals(du.varName);
	}

	/**
	 * <p>
	 * sharesVariableWith
	 * </p>
	 * 
	 * @param instruction
	 *            a {@link org.evosuite.graphs.cfg.BytecodeInstruction} object.
	 * @return a boolean.
	 */
	public boolean sharesVariableWith(BytecodeInstruction instruction) {
		if (!instruction.isDefUse())
			return false;

		return varName.equals(instruction.getVariableName());
	}

	/**
	 * <p>
	 * getDUVariableType
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getDUVariableType() {
		// TODO remember that in a flag
		if (isMethodCallOfField())
			return "FieldMethodCall";
		if (isFieldDU())
			return "Field";
		if (isParameterUse())
			return "Parameter";
		if (isLocalDU())
			return "Local";

		logger.warn("unexpected state");
		return "UNKNOWN";
	}

	/** {@inheritDoc} */
	@Override
	public String getVariableName() {
		return varName;
	}

	// getter

	/**
	 * <p>
	 * getDefUseId
	 * </p>
	 * 
	 * @return a int.
	 */
	public int getDefUseId() {
		return defuseId;
	}

	/**
	 * <p>
	 * Getter for the field <code>useId</code>.
	 * </p>
	 * 
	 * @return a int.
	 */
	public int getUseId() {
		return useId;
	}

	/**
	 * <p>
	 * Getter for the field <code>defId</code>.
	 * </p>
	 * 
	 * @return a int.
	 */
	public int getDefId() {
		return defId;
	}

	/**
	 * <p>
	 * isParameterUse
	 * </p>
	 * 
	 * @return a boolean.
	 */
	public boolean isParameterUse() {
		return isParameterUse;
	}

	// inherited from Object

	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		if (isDefinition())
			r.append("Definition " + getDefId());
		if (isUse())
			r.append("Use " + getUseId());
		r.append(" for ");
		if (isStaticDefUse())
			r.append("static ");
		r.append(getDUVariableType());
		r.append("-Variable \"" + getVariableName() + "\"");
		r.append(" in " + getMethodName() + "." + getInstructionId());
		if (isRootBranchDependent())
			r.append(" root-Branch");
		else
			r.append(" Branch " + getControlDependentBranchId()
			        + (getControlDependentBranchExpressionValue() ? "t" : "f"));
		r.append(" Line " + getLineNumber());
		return r.toString();
	}
}
