/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.unmodifiable;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.impl.unmodifiable.TUnmodifiableLongSet;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TUnmodifiableLongSetEvoSuite_Branch {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.utils.LoggingUtils.loadLogbackForEvoSuite(); 
    org.evosuite.Properties.REPLACE_CALLS = true; 
    org.evosuite.agent.InstrumentingAgent.initialize(); 
  } 

  @Before 
  public void initTestCase(){ 
    org.evosuite.agent.InstrumentingAgent.activate(); 
  } 

  @After 
  public void doneWithTestCase(){ 
    org.evosuite.agent.InstrumentingAgent.deactivate(); 
  } 


  //Test case number: 0
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableLongSet.hashCode()I: root-Branch
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableLongSet.<init>(Lgnu/trove/set/TLongSet;)V: root-Branch
   */

  @Test
  public void test0()  throws Throwable  {
      TLongHashSet tLongHashSet0 = new TLongHashSet(0, (float) 0, 0L);
      TUnmodifiableLongSet tUnmodifiableLongSet0 = new TUnmodifiableLongSet((TLongSet) tLongHashSet0);
      int int0 = tUnmodifiableLongSet0.hashCode();
      assertEquals(0, int0);
  }

  //Test case number: 1
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableLongSet.equals(Ljava/lang/Object;)Z: I4 Branch 1 IF_ACMPEQ L58 - true
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableLongSet.equals(Ljava/lang/Object;)Z: I9 Branch 2 IFEQ L58 - false
   */

  @Test
  public void test1()  throws Throwable  {
      TLongHashSet tLongHashSet0 = new TLongHashSet();
      TUnmodifiableLongSet tUnmodifiableLongSet0 = new TUnmodifiableLongSet((TLongSet) tLongHashSet0);
      boolean boolean0 = tUnmodifiableLongSet0.equals((Object) tLongHashSet0);
      assertEquals(true, boolean0);
  }

  //Test case number: 2
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableLongSet.equals(Ljava/lang/Object;)Z: I4 Branch 1 IF_ACMPEQ L58 - false
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableLongSet.equals(Ljava/lang/Object;)Z: I9 Branch 2 IFEQ L58 - true
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableLongSet.<init>(Lgnu/trove/set/TLongSet;)V: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      TLongHashSet tLongHashSet0 = new TLongHashSet();
      TUnmodifiableLongSet tUnmodifiableLongSet0 = new TUnmodifiableLongSet((TLongSet) tLongHashSet0);
      boolean boolean0 = tUnmodifiableLongSet0.equals((Object) "{}");
      assertEquals(false, boolean0);
  }
}
