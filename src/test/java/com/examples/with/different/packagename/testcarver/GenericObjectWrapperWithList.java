/**
 * 
 */
package com.examples.with.different.packagename.testcarver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon Fraser
 * 
 */
public class GenericObjectWrapperWithList<T> {

	private final List<T> list = new ArrayList<T>();

	public void add(T obj) {
		list.add(obj);
	}

	public List<T> getList() {
		return list;
	}
}
