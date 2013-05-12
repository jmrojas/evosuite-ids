package org.evosuite.continuous.job;

public class JobDefinition {

	public final int seconds;
	public final String cut;
	public final int configurationId;
	
	//FIXME we do need memory as well
	
	public JobDefinition(int seconds, String cut, int configurationId) {
		super();
		this.seconds = seconds;
		this.cut = cut;
		this.configurationId = configurationId;
	}
}
