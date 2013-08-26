package org.evosuite.continuous.job.schedule;

import java.util.LinkedList;
import java.util.List;

import org.evosuite.continuous.job.JobDefinition;
import org.evosuite.continuous.job.JobScheduler;
import org.evosuite.continuous.project.ProjectStaticData;
import org.evosuite.continuous.project.ProjectStaticData.ClassInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Root class for the different kinds of schedule.
 * 
 * <p>
 * TODO: memory based on number of branches, as we can/should use larger population size, longer
 * test cases, larger test suites, etc. These latter are an initial proof-of-concept of a more 
 * general optimized tuning.
 * 
 * <p>
 * TODO: handle schedule that is specific for the SVN/Git changes (ie only recently modified classes used for search).
 * Would be interesting to analyze how often there are commits (how many classes are modified) in open source / industrial projects. 
 * If in last commit there were added/modified a set X of classes, not only we might 
 * want to focus on those, but also on the others that take them as input.
 * Furthermore, we can look at the history: if CTG has been run for weeks/months,
 * and then new classes are added, those should have much higher priority than old,
 * heavily tested classes 
 * 
 * <p>
 * TODO: we should also consider what version of EvoSuite has been used.
 * If a new version of EvoSuite is introduced during CTG, it would make sense
 * to re-generate test cases for all CUTs, as new version might achieve higher
 * coverage
 * 
 * @author arcuri
 *
 */
public abstract class ScheduleType {

	private static Logger logger = LoggerFactory.getLogger(ScheduleType.class);

	protected final JobScheduler scheduler;

	/**
	 * The minimum amount of seconds a search/job should run.
	 * Less than that, and there would be no point to even run
	 * the search.
	 */
	public static final int MINIMUM_SECONDS = 60;

	/**
	 * To run a job, you need a minimum of RAM.
	 * If not enough RAM, then no point in even trying to start
	 * a search.
	 * Note: this include the memory of both the master and
	 * clients together 
	 */
	protected final int MINIMUM_MEMORY_PER_JOB_MB = 500;

	protected ScheduleType(JobScheduler scheduler){
		this.scheduler = scheduler;
	}


	/**
	 * We cannot use cores if we do not have enough memory,
	 * as each process has some minimum requirements
	 * 
	 * @return
	 */
	public int getNumberOfUsableCores() {
		if(scheduler.getNumberOfCores() * MINIMUM_MEMORY_PER_JOB_MB <=  scheduler.getTotalMemoryInMB()) {
			return scheduler.getNumberOfCores();
		} else {
			return scheduler.getTotalMemoryInMB() / MINIMUM_MEMORY_PER_JOB_MB;
		}
	}

	protected int getConstantMemoryPerJob(){
		if(scheduler.getNumberOfCores() * MINIMUM_MEMORY_PER_JOB_MB <= scheduler.getTotalMemoryInMB()) {
			return  scheduler.getTotalMemoryInMB() / scheduler.getNumberOfCores() ;
		} else {
			return scheduler.getTotalMemoryInMB() / MINIMUM_MEMORY_PER_JOB_MB;
		}
	}

	protected boolean enoughBudgetForAll(){
		int totalBudget = 60 * scheduler.getTotalBudgetInMinutes() * getNumberOfUsableCores();
		int maximumNumberOfJobs = totalBudget / MINIMUM_SECONDS;
		return maximumNumberOfJobs >= scheduler.getProjectData().getTotalNumberOfTestableCUTs();
	}

	/**
	 * Create a new partial/complete schedule if there is still search budget left
	 * 
	 * @return
	 * @throws IllegalStateException
	 */
	public abstract List<JobDefinition> createNewSchedule() throws IllegalStateException;

	/**
	 * <p>
	 * When we get a schedule, the scheduler might decide to do not use the entire
	 * budget. Reason? It might decide to generate some test cases first, and then 
	 * use those as seeding for a new round of execution.
	 * </p>
	 * 
	 * <p>
	 * Once the budget is finished, this schedule cannot be reused. A new 
	 * instance needs to be created.
	 * </p>
	 * 
	 * @return
	 */
	public abstract boolean canExecuteMore();

	/**
	 * if there is not enough search budget, then try
	 * to target as many CUTs as possible
	 * @return
	 */
	protected List<JobDefinition> createScheduleForWhenNotEnoughBudget(){
		
		ProjectStaticData data = scheduler.getProjectData();
		int totalBudget = 60 * scheduler.getTotalBudgetInMinutes() * getNumberOfUsableCores(); 
		
		List<JobDefinition> jobs = new LinkedList<JobDefinition>();

		/*
		 * TODO: when starting schedule, check how many classes we do already have test cases from previous runs.
		 * If from previous run we have some classes, then prioritize the others
		 */
		
		//not enough budget
		for(ClassInfo info : data.getClassInfos()){
			if(!info.isTestable()){
				continue;
			}
			JobDefinition job = new JobDefinition(
					MINIMUM_SECONDS, getConstantMemoryPerJob(), info.getClassName(), 0, null, null);
			jobs.add(job);
			
			totalBudget -= MINIMUM_SECONDS;
			
			if(totalBudget <= 0){
				break;
			}
		}
		return jobs;
	}
}
