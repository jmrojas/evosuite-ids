/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.unmodifiable;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessShortList;
import gnu.trove.list.TShortList;
import gnu.trove.list.linked.TShortLinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TUnmodifiableRandomAccessShortListEvoSuite_Branch {

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
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessShortList.subList(II)Lgnu/trove/list/TShortList;: root-Branch
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessShortList.<init>(Lgnu/trove/list/TShortList;)V: root-Branch
   */

  @Test
  public void test0()  throws Throwable  {
      TShortLinkedList tShortLinkedList0 = new TShortLinkedList((short)19);
      TUnmodifiableRandomAccessShortList tUnmodifiableRandomAccessShortList0 = new TUnmodifiableRandomAccessShortList((TShortList) tShortLinkedList0);
      // Undeclared exception!
      try {
        tUnmodifiableRandomAccessShortList0.subList((int) (short)19, (int) (short)19);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // begin index 19 greater than last index 0
         //
      }
  }
}
