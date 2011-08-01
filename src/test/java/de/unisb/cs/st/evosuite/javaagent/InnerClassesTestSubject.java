package de.unisb.cs.st.evosuite.javaagent;

public class InnerClassesTestSubject 
{
	@Override
	public String toString()
	{
		System.out.println("Starting doSomethingNow()");
		
		Object obj = new Object(){
			@Override 
			public String toString()
			{
				return "a";
			}
						
		};
		
		String a = obj.toString();
		
		Foo0 foo0 = new Foo0();
		String b = foo0.toString();

		Foo1 foo1 = new Foo1();
		String c = foo1.toString();
		
		return a+b+c;
	}
	
	private class Foo0
	{
		public String toString()
		{
			System.out.println("Printing in private class");
			return "b";
		}
	}
	
	private static class Foo1
	{
		public String toString()
		{
			System.out.println("Printing in private static class");
			return "c";
		}		
	}
}
