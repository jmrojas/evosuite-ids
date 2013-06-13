package org.evosuite.continuous.project;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>
 * Class representing the class graph.
 * For each class (CUT and non-testable), not only we want to know its hierarchy (parents, interfaces, subclasses, etc)
 * but also where in the project it is used by other classes as input.
 * </p>
 * 
 * <p>
 * For definition of CUT, see {@link ProjectStaticData}
 * </p>
 */
public class ProjectGraph {

	/*
	 * TODO: maybe best to build the graph once with all needed info, and then the public
	 * methods just querying the map data structures directly (instead of recalculating
	 * on the fly)
	 */
	
	
	/**
	 * <p>
	 * Return the full qualifying names of all classes that are CUTs and
	 * that are used as input in any of the public methods of <code>cut</code> (but 
	 * not of any of its parent hierarchy).
	 * </p>
	 *
	 * <p>
	 * If a method takes as input a reference of a non-SUT class (e.g., <code>java.lang.Object</code>),
	 * but then there is a cast to a CUT (e.g. a class X), then X will be added in the returned set.
	 * </p>
	 * 
	 * <p>
	 * If a method takes as input a reference of a SUT class X that is not a CUT (e.g., an interface with no code),
	 * then X will <b>not</b> be added in the returned set, although based on <code>includeSubclasses</code>
	 * we might add its subclasses.
	 * </p>
	 * 
	 * @param cut	the class under test (CUT) 
	 * @param includeSubclasses  If a class X is in the returned set, then normally no subclass Y of X would be added in the returned set,
	 * 							 unless Y is directly used in the CUT as input.
	 * @return	a set of full qualifying names of CUTs
	 * @throws IllegalArgumentException if the input <code>cut</code> is not a CUT
	 */
	public Set<String> getCUTsDirectlyUsedAsInput(String cut, boolean includeSubclasses) throws IllegalArgumentException{
		if(includeSubclasses){
			Set<String> directlyUsed = getCUTsDirectlyUsedAsInput(cut,false); //recursion
			Set<String> all = new LinkedHashSet<String>(directlyUsed);
			for(String name : directlyUsed){
				all.addAll(getAllCUTsSubclasses(name));
			}
			return all;
		}
		//TODO
		return null;
	}
	
	/**
	 * Calculate all the CUTs that use the given <code>cut</code>
	 * as input in any of their public methods
	 * 
	 * @param cut	the class under test (CUT) 
	 * @param includeSuperClasses  not only using as input the <code>cut</body>, but also any of its CUT ancestors/interfaces
	 * @return	a set of full qualifying names of CUTs
	 * @throws IllegalArgumentException if the input <code>cut</code> is not a CUT
	 */
	public Set<String> getCUTsThatUseThisCUTasInput(String cut, boolean includeSuperClasses) throws IllegalArgumentException{
		if(includeSuperClasses){
			Set<String> directlyUsed = getCUTsThatUseThisCUTasInput(cut,false); //recursion
			Set<String> all = new LinkedHashSet<String>(directlyUsed);
			for(String name : getAllCUTsParents(cut)){
				all.addAll(getCUTsThatUseThisCUTasInput(name,false)); //recursion
			}
			return all;
		}
		//TODO
		return null;
	}
	
	public boolean isInterface(String cut) throws IllegalArgumentException {
		return false; //TODO
	}
	
	public boolean isConcrete(String cut) throws IllegalArgumentException {
		return false; //TODO
	}
	
	public boolean isAbstract(String cut) throws IllegalArgumentException {
		return false; //TODO
	}
	
	/**
	 * Return all the child hierarchy of <code>cut</code>.
	 * Include only classes that are CUTs
	 * 
	 * @param cut the class under test (CUT) 
	 * @return a set of full qualifying names of CUTs
	 * @throws IllegalArgumentException if the input <code>cut</code> is not a CUT
	 */
	public Set<String> getAllCUTsSubclasses(String cut) throws IllegalArgumentException{
		//TODO
		return null;
	}
	
	/**
	 * Return all the CUT classes that this <code>cut</code> extends/implements (ie, parent hierarchy)
	 * 
	 * @param cut the class under test (CUT) 
	 * @return a set of full qualifying names of CUTs
	 * @throws IllegalArgumentException if the input <code>cut</code> is not a CUT
	 */
	public Set<String> getAllCUTsParents(String cut) throws IllegalArgumentException{
		//TODO
		return null;
	}
}
