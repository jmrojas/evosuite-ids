/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.map.custom_hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.map.custom_hash.TObjectShortCustomHashMap;
import gnu.trove.map.hash.TObjectShortHashMap;
import gnu.trove.strategy.HashingStrategy;
import gnu.trove.strategy.IdentityHashingStrategy;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TObjectShortCustomHashMapEvoSuite_Random {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.utils.LoggingUtils.setLoggingForJUnit(); 
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


//   @Test
//   public void test0()  throws Throwable  {
//       IdentityHashingStrategy<TObjectShortCustomHashMap<Short>> identityHashingStrategy0 = new IdentityHashingStrategy<TObjectShortCustomHashMap<Short>>();
//       TObjectShortCustomHashMap<TObjectShortCustomHashMap<Short>> tObjectShortCustomHashMap0 = new TObjectShortCustomHashMap<TObjectShortCustomHashMap<Short>>((HashingStrategy<? super TObjectShortCustomHashMap<Short>>) identityHashingStrategy0);
//       IdentityHashingStrategy<TObjectShortCustomHashMap<String>> identityHashingStrategy1 = new IdentityHashingStrategy<TObjectShortCustomHashMap<String>>();
//       Integer integer0 = null;
//       int int0 = 501165979;
//       short short0 = (short)1176;
//       TObjectShortHashMap<Short> tObjectShortHashMap0 = null;
//       try {
//         tObjectShortHashMap0 = new TObjectShortHashMap<Short>(int0, (float) integer0, short0);
//         fail("Expecting exception: IllegalArgumentException");
//       
//       } catch(IllegalArgumentException e) {
//          //
//          // no message in exception (getMessage() returned null)
//          //
//       }
//   }

  @Test
  public void test1()  throws Throwable  {
      TObjectShortCustomHashMap<Short> tObjectShortCustomHashMap0 = new TObjectShortCustomHashMap<Short>();
      assertEquals(23, tObjectShortCustomHashMap0.capacity());
  }
}
