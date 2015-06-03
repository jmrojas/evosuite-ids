package com.examples.with.different.packagename;

public class ClassWithPrivateNonStaticInnerClass {

	public void foo(int x, int y) {
		if(x == 100) {
			AnInnerClass z = new AnInnerClass();
			z.foo(y);
		}
	}
	
	private class AnInnerClass {
		public boolean foo(int x) {
			if(x == 42)
				return true;
			else
				return false;
		}
	};
	
}
