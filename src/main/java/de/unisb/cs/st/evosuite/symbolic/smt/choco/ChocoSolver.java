/*
 * Copyright (C) 2011 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.symbolic.smt.choco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;

import choco.Choco;
import choco.cp.model.CPModel2;
import choco.cp.solver.CPSolver;
import choco.kernel.model.variables.integer.IntegerConstantVariable;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.model.variables.real.RealExpressionVariable;
import choco.kernel.model.variables.real.RealVariable;
import choco.kernel.solver.Solver;
import choco.kernel.solver.variables.integer.IntDomainVar;
import choco.kernel.solver.variables.real.RealVar;
import de.unisb.cs.st.evosuite.symbolic.expr.Comparator;
import de.unisb.cs.st.evosuite.symbolic.expr.Constraint;
import de.unisb.cs.st.evosuite.symbolic.expr.Expression;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerBinaryExpression;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerConstant;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerConstraint;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerToRealCast;
import de.unisb.cs.st.evosuite.symbolic.expr.IntegerUnaryExpression;
import de.unisb.cs.st.evosuite.symbolic.expr.RealBinaryExpression;
import de.unisb.cs.st.evosuite.symbolic.expr.RealConstant;
import de.unisb.cs.st.evosuite.symbolic.expr.RealToIntegerCast;
import de.unisb.cs.st.evosuite.symbolic.expr.RealUnaryExpression;

@SuppressWarnings({ "unchecked" })
public class ChocoSolver implements de.unisb.cs.st.evosuite.symbolic.Solver {

	private static Logger logger = Logger.getLogger(ChocoSolver.class);

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> getModel(Collection<Constraint<?>> constraints) {
		//System.out.println(constraints);
		for (Constraint c : constraints) {
			if (!c.isSolveable()) {
				logger.info("Not solvable: " + c);
				return null;
			}
		}
		logger.info("Starting thread");
		WorkingThread t = new WorkingThread(constraints);
		t.setDaemon(true);
		t.setName("ChocoWorkingThread");
		t.start();
		try {
			t.join(10000); // TODO - property
		} catch (InterruptedException e) {
		}
		if (t.isAlive()) {
			t.stop(new TimeOutError());

		}
		logger.info("Finished thread");

		return t.getSolution();

	}

	private class WorkingThread extends Thread {

		public WorkingThread(Collection<Constraint<?>> constraints) {
			super();
			this.constraints = constraints;
		}

		public Map<String, Object> getSolution() {
			return solution;
		}

		private LinkedList<IntegerVariable> vl;
		private LinkedList<RealVariable> vlr;

		private Map<String, Object> solution = null;
		private Collection<Constraint<?>> constraints;
		private final LinkedList<choco.kernel.model.constraints.Constraint> additionalConstrraints = new LinkedList<choco.kernel.model.constraints.Constraint>();
		private int castVarNumber = 0;

		@Override
		public void run() {
			try {
				vl = new LinkedList<IntegerVariable>();
				vlr = new LinkedList<RealVariable>();
				Map<String, Object> ret = new HashMap<String, Object>();

				Boolean b = null;
				CPModel2 m = null;
				Solver s = null;
				try {
					logger.info("Got model");
					m = getModel(constraints);
					logger.info("Got model");
					this.constraints = null;
					s = new CPSolver();
					s.read(m);
					//like cvs3
					s.setTimeLimit(10000 - 10);
					logger.info("Solving model: " + m);

					b = s.solve();
				} catch (Exception e) {
					logger.info("Got exception: " + e);
					return;
				}
				if (b == null || b.equals(Boolean.FALSE)) {
					return;
				}
				Iterator<IntegerVariable> it = vl.iterator();
				while (it.hasNext()) {
					IntDomainVar idv = s.getVar(it.next());
					if (idv != null && idv.isInstantiated()) {
						ret.put(idv.getName(), new Long(idv.getVal()));
					}
				}
				Iterator<RealVariable> it2 = vlr.iterator();
				while (it2.hasNext()) {
					RealVar idv = s.getVar(it2.next());
					if (idv != null && idv.isInstantiated()) {
						ret.put(idv.getName(), new Double(idv.getValue().getInf()));
					}
				}
				solution = ret;
			} catch (TimeOutError e) {
				return;
			}
		}

		private CPModel2 getModel(Collection<Constraint<?>> constraints)
		        throws MyUnsupportedException {
			CPModel2 model = new CPModel2();
			for (Constraint<?> c : constraints) {
				logger.info("Adding constraint: " + c);

				if (c instanceof IntegerConstraint) {
					choco.kernel.model.constraints.Constraint cc = getChocoConstraintInt((Constraint<Long>) c);
					if (cc != null) {
						model.addConstraint(cc);
					}
				} else {
					choco.kernel.model.constraints.Constraint cc = getChocoConstraintReal((Constraint<Double>) c);
					if (cc != null) {
						model.addConstraint(cc);
					}
				}
			}
			Iterator<choco.kernel.model.constraints.Constraint> it = additionalConstrraints.iterator();
			while (it.hasNext()) {
				model.addConstraint(it.next());
			}
			return model;
		}

		private choco.kernel.model.constraints.Constraint getChocoConstraintInt(
		        Constraint<Long> constraint) throws MyUnsupportedException {
			choco.kernel.model.constraints.Constraint ret = null;
			try {
				if (constraint.getComparator() == Comparator.EQ) {
					ret = choco.Choco.eq(getIntegerValue(constraint.getLeftOperand()),
					                     getIntegerValue(constraint.getRightOperand()));
				} else if (constraint.getComparator() == Comparator.NE) {
					ret = choco.Choco.neq(getIntegerValue(constraint.getLeftOperand()),
					                      getIntegerValue(constraint.getRightOperand()));
				} else if (constraint.getComparator() == Comparator.GT) {
					ret = choco.Choco.gt(getIntegerValue(constraint.getLeftOperand()),
					                     getIntegerValue(constraint.getRightOperand()));
				} else if (constraint.getComparator() == Comparator.LT) {
					ret = choco.Choco.lt(getIntegerValue(constraint.getLeftOperand()),
					                     getIntegerValue(constraint.getRightOperand()));
				} else if (constraint.getComparator() == Comparator.GE) {
					ret = choco.Choco.geq(getIntegerValue(constraint.getLeftOperand()),
					                      getIntegerValue(constraint.getRightOperand()));
				} else if (constraint.getComparator() == Comparator.LE) {
					ret = choco.Choco.leq(getIntegerValue(constraint.getLeftOperand()),
					                      getIntegerValue(constraint.getRightOperand()));
				}
			} catch (RuntimeException e) {

			}

			return ret;
		}

		private final Map<String, IntegerVariable> varMap = new HashMap<String, IntegerVariable>();

		private IntegerExpressionVariable getIntegerValue(Expression<Long> expression)
		        throws MyUnsupportedException {
			if (expression instanceof de.unisb.cs.st.evosuite.symbolic.expr.IntegerVariable) {
				de.unisb.cs.st.evosuite.symbolic.expr.IntegerVariable sv = (de.unisb.cs.st.evosuite.symbolic.expr.IntegerVariable) expression;
				IntegerVariable i = varMap.get(sv.getName());
				if (i == null) {
					if (sv.getMinValue() == null && sv.getMaxValue() == null) {
						i = Choco.makeIntVar(sv.getName());
					} else {
						Long min = sv.getMinValue();
						int mi;
						if (min == null || min < -21474836) {
							mi = -21474836;
						} else {
							mi = min.intValue();
						}
						Long max = sv.getMaxValue();
						int ma;
						if (max == null || max > 21474836) {
							ma = 21474836;
						} else {
							ma = max.intValue();
						}
						i = Choco.makeIntVar(sv.getName(), mi, ma);
					}
					vl.add(i);
					varMap.put(sv.getName(), i);
				}

				return i;

			} else if (expression instanceof IntegerConstant) {
				IntegerConstant ic = (IntegerConstant) expression;

				return new IntegerConstantVariable(ic.getConcreteValue().intValue());
			} else if (expression instanceof IntegerBinaryExpression) {
				IntegerBinaryExpression ae = (IntegerBinaryExpression) expression;

				switch (ae.getOperator()) {
				case MINUS:
					return Choco.minus(getIntegerValue(ae.getLeftOperand()),
					                   getIntegerValue(ae.getRightOperand()));
				case PLUS:
					return Choco.plus(getIntegerValue(ae.getLeftOperand()),
					                  getIntegerValue(ae.getRightOperand()));
				case MUL:
					return Choco.mult(getIntegerValue(ae.getLeftOperand()),
					                  getIntegerValue(ae.getRightOperand()));
				case DIV:
					return Choco.div(getIntegerValue(ae.getLeftOperand()),
					                 getIntegerValue(ae.getRightOperand()));
				case REM:
					return Choco.mod(getIntegerValue(ae.getLeftOperand()),
					                 getIntegerValue(ae.getRightOperand()));
				default:
					logger.info("Unknown operator, throwing exception");
					throw new MyUnsupportedException();
				}
			} else if (expression instanceof IntegerUnaryExpression) {
				IntegerUnaryExpression ae = (IntegerUnaryExpression) expression;

				switch (ae.getOperator()) {
				case NEG:
					return Choco.neg(getIntegerValue(ae.getOperand()));
				default:
					logger.info("Unknown operator, throwing exception");
					throw new MyUnsupportedException();
				}
			} else if (expression instanceof RealToIntegerCast) {
				RealToIntegerCast cast = (RealToIntegerCast) expression;
				RealVariable rv = choco.Choco.makeRealVar("CastVar" + castVarNumber++,
				                                          Long.MIN_VALUE, Long.MAX_VALUE);
				IntegerVariable iv = choco.Choco.makeIntVar("CastVar" + castVarNumber++);
				this.additionalConstrraints.add(choco.Choco.eq(rv, iv));
				this.additionalConstrraints.add(choco.Choco.eq(rv,
				                                               getRealValue(cast.getExpression())));
				return iv;
			} else {
				logger.info("Unknown expression, throwing exception: " + expression);

				throw new MyUnsupportedException();
			}

		}

		private choco.kernel.model.constraints.Constraint getChocoConstraintReal(
		        Constraint<Double> constraint) throws MyUnsupportedException {
			choco.kernel.model.constraints.Constraint ret = null;
			try {
				if (constraint.getComparator() == Comparator.EQ) {
					ret = choco.Choco.eq(getRealValue(constraint.getLeftOperand()),
					                     getRealValue(constraint.getRightOperand()));
				} else if (constraint.getComparator() == Comparator.NE) {
					//TODO
					ret = choco.Choco.TRUE;
					//ret=choco.Choco.not(choco.Choco.eq(getRealValue(constraint.getLeft()), getRealValue(constraint.getRight())));
				} else if (constraint.getComparator() == Comparator.GT) {
					//TODO approximation only
					ret = choco.Choco.geq(getRealValue(constraint.getLeftOperand()),
					                      getRealValue(constraint.getRightOperand()));
					//ret=choco.Choco.not(choco.Choco.leq(getRealValue(constraint.getLeft()), getRealValue(constraint.getRight())));
				} else if (constraint.getComparator() == Comparator.LT) {
					//TODO only approx
					ret = choco.Choco.leq(getRealValue(constraint.getLeftOperand()),
					                      getRealValue(constraint.getRightOperand()));
				} else if (constraint.getComparator() == Comparator.GE) {
					ret = choco.Choco.geq(getRealValue(constraint.getLeftOperand()),
					                      getRealValue(constraint.getRightOperand()));
				} else if (constraint.getComparator() == Comparator.LE) {
					ret = choco.Choco.leq(getRealValue(constraint.getLeftOperand()),
					                      getRealValue(constraint.getRightOperand()));
				}
			} catch (RuntimeException e) {

			}

			return ret;
		}

		private final Map<String, RealVariable> varMapR = new HashMap<String, RealVariable>();

		private RealExpressionVariable getRealValue(Expression<Double> expression)
		        throws MyUnsupportedException {
			if (expression instanceof de.unisb.cs.st.evosuite.symbolic.expr.RealVariable) {
				de.unisb.cs.st.evosuite.symbolic.expr.RealVariable sv = (de.unisb.cs.st.evosuite.symbolic.expr.RealVariable) expression;
				RealVariable i = varMapR.get(sv.getName());
				if (i == null) {
					if (sv.getMinValue() == null && sv.getMaxValue() == null) {
						i = Choco.makeRealVar(sv.getName(), -Double.MAX_VALUE,
						                      Double.MAX_VALUE);
					} else {
						Double min = sv.getMinValue();
						Double max = sv.getMaxValue();

						i = Choco.makeRealVar(sv.getName(), min, max);
					}
					vlr.add(i);
					varMapR.put(sv.getName(), i);
				}

				return i;

			} else if (expression instanceof RealConstant) {
				RealConstant ic = (RealConstant) expression;

				return Choco.constant(ic.getConcreteValue());
			} else if (expression instanceof RealBinaryExpression) {
				RealBinaryExpression ae = (RealBinaryExpression) expression;
				switch (ae.getOperator()) {
				case MINUS:
					return Choco.minus(getRealValue(ae.getLeftOperand()),
					                   getRealValue(ae.getRightOperand()));
				case PLUS:
					return Choco.plus(getRealValue(ae.getLeftOperand()),
					                  getRealValue(ae.getRightOperand()));
				case MUL:
					return Choco.mult(getRealValue(ae.getLeftOperand()),
					                  getRealValue(ae.getRightOperand()));
				case DIV:
					RealVariable rv = choco.Choco.makeRealVar("SupportVar"
					        + castVarNumber++, -Double.MAX_VALUE, Double.MAX_VALUE);
					RealExpressionVariable left = getRealValue(ae.getLeftOperand());
					RealExpressionVariable right = getRealValue(ae.getRightOperand());
					RealExpressionVariable mul = Choco.mult(rv, right);
					this.additionalConstrraints.add(choco.Choco.eq(mul, left));
					return rv;
				default:
					Double d = ae.getConcreteValue();
					if (d == null) {
						throw new MyUnsupportedException();
					}
					return Choco.constant(d);
				}
			} else if (expression instanceof RealUnaryExpression) {
				RealUnaryExpression ue = (RealUnaryExpression) expression;
				switch (ue.getOperator()) {
				case SIN:
					return Choco.sin(getRealValue(ue.getOperand()));
				case COS:
					return Choco.cos(getRealValue(ue.getOperand()));
				default:
					Double d = ue.getConcreteValue();
					if (d == null) {
						throw new MyUnsupportedException();
					}
					return Choco.constant(d);

				}
			} else if (expression instanceof IntegerToRealCast) {
				IntegerToRealCast cast = (IntegerToRealCast) expression;
				RealVariable rv = choco.Choco.makeRealVar("CastVar" + castVarNumber++,
				                                          Long.MIN_VALUE, Long.MAX_VALUE);
				IntegerVariable iv = choco.Choco.makeIntVar("CastVar" + castVarNumber++);
				this.additionalConstrraints.add(choco.Choco.eq(rv, iv));
				this.additionalConstrraints.add(choco.Choco.eq(iv,
				                                               getIntegerValue(cast.getExpression())));
				return rv;
			} else {
				throw new MyUnsupportedException();
			}
		}
	}

	private class MyUnsupportedException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	private class TimeOutError extends Error {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	// For running in a own process
	@SuppressWarnings("unused")
	static private class TimeOut extends Thread {
		private final int time;
		private final Process p;

		public TimeOut(int time, Process p) {
			this.time = time;
			this.p = p;
		}

		@Override
		public void run() {
			synchronized (this) {
				try {
					this.wait(time);
				} catch (InterruptedException e) {
				}
			}

			p.destroy();
		}

	}

	static class ErrReader extends Thread {
		InputStream is;

		ErrReader(InputStream is) {
			this.is = is;
			this.setDaemon(true);
		}

		@Override
		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null)
					System.err.println(line);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.unisb.cs.st.evosuite.symbolic.Solver#solve(java.util.Collection)
	 */
	@Override
	public boolean solve(Collection<Constraint<?>> constraints) {
		// TODO Auto-generated method stub
		return false;
	}
}
