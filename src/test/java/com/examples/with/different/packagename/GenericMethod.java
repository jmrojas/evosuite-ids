package com.examples.with.different.packagename;

public class GenericMethod {

	
	public <T> boolean coverMe(T parameter) {
		if(parameter.equals("test")) {
			return true;
		} else {
			return false;
		}
	}
}
