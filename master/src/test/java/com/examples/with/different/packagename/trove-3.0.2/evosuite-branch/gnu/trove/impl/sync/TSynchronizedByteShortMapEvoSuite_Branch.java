/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.sync;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TShortFunction;
import gnu.trove.impl.sync.TSynchronizedByteSet;
import gnu.trove.impl.sync.TSynchronizedByteShortMap;
import gnu.trove.impl.sync.TSynchronizedShortCollection;
import gnu.trove.iterator.TByteShortIterator;
import gnu.trove.map.TByteShortMap;
import gnu.trove.map.hash.TByteShortHashMap;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TByteShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TSynchronizedByteShortMapEvoSuite_Branch {

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
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.adjustValue(BS)Z: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedByteShortMap.<init>(Lgnu/trove/map/TByteShortMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test0()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(2, (float) 2, (byte)127, (short) (byte)127);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0, (Object) "bZ&NRiU");
      boolean boolean0 = tSynchronizedByteShortMap0.adjustValue((byte)127, (byte)127);
      assertEquals(false, boolean0);
  }

  //Test case number: 1
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.size()I: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedByteShortMap.<init>(Lgnu/trove/map/TByteShortMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test1()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-1844));
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      int int0 = tSynchronizedByteShortMap0.size();
      assertEquals(0, int0);
  }

  //Test case number: 2
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.iterator()Lgnu/trove/iterator/TByteShortIterator;: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(0, 0);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      TByteShortIterator tByteShortIterator0 = tSynchronizedByteShortMap0.iterator();
      assertEquals(false, tByteShortIterator0.hasNext());
  }

  //Test case number: 3
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.values([S)[S: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(0, 0);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      short[] shortArray0 = new short[2];
      short[] shortArray1 = tSynchronizedByteShortMap0.values(shortArray0);
      assertSame(shortArray1, shortArray0);
  }

  //Test case number: 4
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.transformValues(Lgnu/trove/function/TShortFunction;)V: root-Branch
   */

  @Test
  public void test4()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-1844));
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      tSynchronizedByteShortMap0.transformValues((TShortFunction) null);
      assertEquals(true, tSynchronizedByteShortMap0.isEmpty());
  }

  //Test case number: 5
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.getNoEntryKey()B: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-899), (float) (-899), (byte) (-80), (short) (byte) (-80));
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      byte byte0 = tSynchronizedByteShortMap0.getNoEntryKey();
      assertEquals((byte) (-80), byte0);
  }

  //Test case number: 6
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.putIfAbsent(BS)S: root-Branch
   */

  @Test
  public void test6()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap();
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      tSynchronizedByteShortMap0.putIfAbsent((byte)73, (byte)73);
      assertEquals(1, tByteShortHashMap0.size());
      assertEquals("{73=73}", tSynchronizedByteShortMap0.toString());
  }

  //Test case number: 7
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.forEachEntry(Lgnu/trove/procedure/TByteShortProcedure;)Z: root-Branch
   */

  @Test
  public void test7()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(0, 0);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      boolean boolean0 = tSynchronizedByteShortMap0.forEachEntry((TByteShortProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 8
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.keys([B)[B: root-Branch
   */

  @Test
  public void test8()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-1844));
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      byte[] byteArray0 = tSynchronizedByteShortMap0.keys(tByteShortHashMap0._states);
      assertNotNull(byteArray0);
  }

  //Test case number: 9
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.isEmpty()Z: root-Branch
   */

  @Test
  public void test9()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(2, (float) 2, (byte)127, (short) (byte)127);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0, (Object) "bZ&NRiU");
      boolean boolean0 = tSynchronizedByteShortMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 10
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.putAll(Lgnu/trove/map/TByteShortMap;)V: root-Branch
   */

  @Test
  public void test10()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-1589), (-1589));
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      tSynchronizedByteShortMap0.putAll((TByteShortMap) tByteShortHashMap0);
      assertEquals(0, tByteShortHashMap0.size());
  }

  //Test case number: 11
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.remove(B)S: root-Branch
   */

  @Test
  public void test11()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(2, (float) 2, (byte)127, (short) (byte)127);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0, (Object) "bZ&NRiU");
      short short0 = tSynchronizedByteShortMap0.remove((byte)127);
      assertEquals((short)127, short0);
  }

  //Test case number: 12
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.toString()Ljava/lang/String;: root-Branch
   */

  @Test
  public void test12()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(0, 0);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      String string0 = tSynchronizedByteShortMap0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 13
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.equals(Ljava/lang/Object;)Z: root-Branch
   */

  @Test
  public void test13()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(2, (float) 2, (byte)127, (short) (byte)127);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0, (Object) "bZ&NRiU");
      boolean boolean0 = tSynchronizedByteShortMap0.equals((Object) 2);
      assertEquals(false, boolean0);
  }

  //Test case number: 14
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.containsValue(S)Z: root-Branch
   */

  @Test
  public void test14()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(2, (float) 2, (byte)127, (short) (byte)127);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0, (Object) "bZ&NRiU");
      boolean boolean0 = tSynchronizedByteShortMap0.containsValue((short)127);
      assertEquals(false, boolean0);
  }

  //Test case number: 15
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.increment(B)Z: root-Branch
   */

  @Test
  public void test15()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-899), (float) (-899), (byte) (-80), (short) (byte) (-80));
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      boolean boolean0 = tSynchronizedByteShortMap0.increment((byte) (-80));
      assertEquals(false, boolean0);
  }

  //Test case number: 16
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.getNoEntryValue()S: root-Branch
   */

  @Test
  public void test16()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap();
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0, (Object) "en-CA");
      short short0 = tSynchronizedByteShortMap0.getNoEntryValue();
      assertEquals((short)0, short0);
  }

  //Test case number: 17
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z: root-Branch
   */

  @Test
  public void test17()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(22);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      boolean boolean0 = tSynchronizedByteShortMap0.forEachKey((TByteProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 18
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.clear()V: root-Branch
   */

  @Test
  public void test18()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-1589), (-1589));
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      tSynchronizedByteShortMap0.clear();
      assertEquals(true, tSynchronizedByteShortMap0.isEmpty());
  }

  //Test case number: 19
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.get(B)S: root-Branch
   */

  @Test
  public void test19()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(0, 0);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      short short0 = tSynchronizedByteShortMap0.get((byte)58);
      assertEquals((short)0, short0);
  }

  //Test case number: 20
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.putAll(Ljava/util/Map;)V: root-Branch
   */

//   @Test
//   public void test20()  throws Throwable  {
//       TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-899), (float) (-899), (byte) (-80), (short) (byte) (-80));
//       TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
//       HashMap<Byte, Short> hashMap0 = new HashMap<Byte, Short>();
//       tSynchronizedByteShortMap0.putAll((Map<? extends Byte, ? extends Short>) hashMap0);
//       assertEquals(-80, tSynchronizedByteShortMap0.getNoEntryKey());
//   }

  //Test case number: 21
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.adjustOrPutValue(BSS)S: root-Branch
   */

  @Test
  public void test21()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(0, 0);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      short short0 = tSynchronizedByteShortMap0.adjustOrPutValue((byte)58, (byte)58, (byte)58);
      assertEquals(7, tByteShortHashMap0.capacity());
      assertEquals((short)58, short0);
  }

  //Test case number: 22
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.containsKey(B)Z: root-Branch
   */

  @Test
  public void test22()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap();
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      boolean boolean0 = tSynchronizedByteShortMap0.containsKey((byte)73);
      assertEquals(false, boolean0);
  }

  //Test case number: 23
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.values()[S: root-Branch
   */

  @Test
  public void test23()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-1844));
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      short[] shortArray0 = tSynchronizedByteShortMap0.values();
      assertNotNull(shortArray0);
  }

  //Test case number: 24
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.hashCode()I: root-Branch
   */

  @Test
  public void test24()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-1844));
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      int int0 = tSynchronizedByteShortMap0.hashCode();
      assertEquals(0, int0);
  }

  //Test case number: 25
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.keys()[B: root-Branch
   */

  @Test
  public void test25()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap();
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0, (Object) "en-CA");
      byte[] byteArray0 = tSynchronizedByteShortMap0.keys();
      assertNotNull(byteArray0);
  }

  //Test case number: 26
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.put(BS)S: root-Branch
   */

  @Test
  public void test26()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-899), (float) (-899), (byte) (-80), (short) (byte) (-80));
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      short short0 = tSynchronizedByteShortMap0.put((byte) (-80), (byte) (-80));
      assertEquals(7, tByteShortHashMap0.capacity());
      assertEquals((short) (-80), short0);
  }

  //Test case number: 27
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.forEachValue(Lgnu/trove/procedure/TShortProcedure;)Z: root-Branch
   */

  @Test
  public void test27()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(2, (float) 2, (byte)127, (short) (byte)127);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0, (Object) "bZ&NRiU");
      boolean boolean0 = tSynchronizedByteShortMap0.forEachValue((TShortProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 28
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.retainEntries(Lgnu/trove/procedure/TByteShortProcedure;)Z: root-Branch
   */

  @Test
  public void test28()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap();
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      boolean boolean0 = tSynchronizedByteShortMap0.retainEntries((TByteShortProcedure) null);
      assertEquals(false, boolean0);
  }

  //Test case number: 29
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.<init>(Lgnu/trove/map/TByteShortMap;)V: I17 Branch 1 IFNONNULL L59 - false
   */

  @Test
  public void test29()  throws Throwable  {
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = null;
      try {
        tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 30
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.keySet()Lgnu/trove/set/TByteSet;: I11 Branch 2 IFNONNULL L107 - true
   * 2 gnu.trove.impl.sync.TSynchronizedByteShortMap.keySet()Lgnu/trove/set/TByteSet;: I11 Branch 2 IFNONNULL L107 - false
   * 3 gnu.trove.impl.sync.TSynchronizedByteShortMap.<init>(Lgnu/trove/map/TByteShortMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test30()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(0, 0);
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0);
      TSynchronizedByteSet tSynchronizedByteSet0 = (TSynchronizedByteSet)tSynchronizedByteShortMap0.keySet();
      assertNotNull(tSynchronizedByteSet0);
      
      TSynchronizedByteSet tSynchronizedByteSet1 = (TSynchronizedByteSet)tSynchronizedByteShortMap0.keySet();
      assertSame(tSynchronizedByteSet1, tSynchronizedByteSet0);
  }

  //Test case number: 31
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedByteShortMap.valueCollection()Lgnu/trove/TShortCollection;: I11 Branch 3 IFNONNULL L121 - true
   * 2 gnu.trove.impl.sync.TSynchronizedByteShortMap.valueCollection()Lgnu/trove/TShortCollection;: I11 Branch 3 IFNONNULL L121 - false
   * 3 gnu.trove.impl.sync.TSynchronizedByteShortMap.<init>(Lgnu/trove/map/TByteShortMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test31()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap();
      TSynchronizedByteShortMap tSynchronizedByteShortMap0 = new TSynchronizedByteShortMap((TByteShortMap) tByteShortHashMap0, (Object) "en-CA");
      TSynchronizedShortCollection tSynchronizedShortCollection0 = (TSynchronizedShortCollection)tSynchronizedByteShortMap0.valueCollection();
      assertNotNull(tSynchronizedShortCollection0);
      
      TSynchronizedShortCollection tSynchronizedShortCollection1 = (TSynchronizedShortCollection)tSynchronizedByteShortMap0.valueCollection();
      assertSame(tSynchronizedShortCollection1, tSynchronizedShortCollection0);
  }
}
