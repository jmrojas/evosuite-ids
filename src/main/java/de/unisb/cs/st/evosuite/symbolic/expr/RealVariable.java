package de.unisb.cs.st.evosuite.symbolic.expr;

public class RealVariable extends RealExpression implements Variable<Double>{
	
	protected String name;
	protected double minValue;
	protected double maxValue;
	
	public RealVariable(String name, double minValue, double maxValue) {
		super();
		this.name = name;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public Double getConcreteValue() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Double getMinValue() {
		return minValue;
	}

	@Override
	public Double getMaxValue() {
		return maxValue;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RealVariable)
		{
			Variable v=(Variable) obj;
			return this.getName().equals(v.getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if(hash==0)
		{
			hash=this.name.hashCode();
		}
		return hash;
	}

	@Override
	public int getSize() {
		return 1;
	}
	
	
	
}
