package de.unisb.cs.st.evosuite.junit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestExample extends ParentTestExample {
	public static class MockingBird {

		public static MockingBird create(String song) {
			return new MockingBird(song);
		}

		private final String song;

		public MockingBird(String song) {
			this.song = song;
		}

		public MockingBird doIt(String song) {
			return this;
		}

		public void executeCmd(int x) {
			if (song.equals("killSelf") && (x > 7)) {
				throw new RuntimeException("It's a sin to kill a mockingbird.");
			}
		}

		public void thisIsIt(String... args) {
			if (args.length > 0) {
				System.out.println(args);
			}
		}
	}

	protected static Integer otherValue = 10;

	static {
		value = 4;
	}

	@BeforeClass
	public static void initializeAgain() {
		value = 42;
	}

	@BeforeClass
	public static void initializeOtherValue() {
		otherValue = -5;
	}

	public TestExample() {
		otherValue = 38;
	}

	@Before
	public void goForIt() {
		needed = "convert";
	}

	@Override
	@Before
	public void setupNeeded() {
		needed = "killSelf";
	}

	@Override
	// @Ignore
	@Test
	public void test01() {
		MockingBird bird = new MockingBird(needed);
		bird.executeCmd(otherValue);
	}
	
	protected static int doCalc(int x, int y){
		//return x + 5;
		return 5;
	}
	
	protected int doOtherCalc(int x){
		//return doCalc(x, 5);
		return 6;
	}
}
