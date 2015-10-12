/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.sync;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TByteFunction;
import gnu.trove.impl.sync.TSynchronizedByteCollection;
import gnu.trove.impl.sync.TSynchronizedShortByteMap;
import gnu.trove.impl.sync.TSynchronizedShortSet;
import gnu.trove.map.TShortByteMap;
import gnu.trove.map.hash.TShortByteHashMap;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TShortByteProcedure;
import gnu.trove.procedure.TShortProcedure;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TSynchronizedShortByteMapEvoSuite_Branch {

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
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.isEmpty()Z: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedShortByteMap.<init>(Lgnu/trove/map/TShortByteMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test0()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0, (Object) "0");
      boolean boolean0 = tSynchronizedShortByteMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 1
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.equals(Ljava/lang/Object;)Z: root-Branch
   */

  @Test
  public void test1()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0, (Object) "0");
      boolean boolean0 = tSynchronizedShortByteMap0.equals((Object) "0");
      assertEquals(false, boolean0);
  }

  //Test case number: 2
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.containsValue(B)Z: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedShortByteMap.<init>(Lgnu/trove/map/TShortByteMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test2()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap((-1955), (float) (-1955), (short)0, (byte) (-33));
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      boolean boolean0 = tSynchronizedShortByteMap0.containsValue((byte) (-33));
      assertEquals(false, boolean0);
  }

  //Test case number: 3
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.clear()V: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      tSynchronizedShortByteMap0.clear();
      assertEquals(0, tSynchronizedShortByteMap0.size());
  }

  //Test case number: 4
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.getNoEntryKey()S: root-Branch
   */

  @Test
  public void test4()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0, (Object) "0");
      short short0 = tSynchronizedShortByteMap0.getNoEntryKey();
      assertEquals((short)0, short0);
  }

  //Test case number: 5
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.remove(S)B: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap((-1955), (float) (-1955), (short)0, (byte) (-33));
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      byte byte0 = tSynchronizedShortByteMap0.remove((short) (byte) (-33));
      assertEquals((byte) (-33), byte0);
  }

  //Test case number: 6
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.size()I: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedShortByteMap.iterator()Lgnu/trove/iterator/TShortByteIterator;: root-Branch
   */

  @Test
  public void test6()  throws Throwable  {
      short[] shortArray0 = new short[6];
      byte[] byteArray0 = new byte[12];
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap(shortArray0, byteArray0);
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      TShortByteHashMap tShortByteHashMap1 = new TShortByteHashMap((TShortByteMap) tSynchronizedShortByteMap0);
      assertEquals(1, tSynchronizedShortByteMap0.size());
  }

  //Test case number: 7
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.keys()[S: root-Branch
   */

  @Test
  public void test7()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      short[] shortArray0 = tSynchronizedShortByteMap0.keys();
      assertNotNull(shortArray0);
  }

  //Test case number: 8
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.values()[B: root-Branch
   */

  @Test
  public void test8()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0, (Object) "0");
      byte[] byteArray0 = tSynchronizedShortByteMap0.values();
      assertNotNull(byteArray0);
  }

  //Test case number: 9
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.forEachKey(Lgnu/trove/procedure/TShortProcedure;)Z: root-Branch
   */

  @Test
  public void test9()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0, (Object) "0");
      boolean boolean0 = tSynchronizedShortByteMap0.forEachKey((TShortProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 10
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.hashCode()I: root-Branch
   */

  @Test
  public void test10()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0, (Object) "0");
      int int0 = tSynchronizedShortByteMap0.hashCode();
      assertEquals(0, int0);
  }

  //Test case number: 11
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.putAll(Ljava/util/Map;)V: root-Branch
   */

  @Test
  public void test11()  throws Throwable  {
      short[] shortArray0 = new short[6];
      byte[] byteArray0 = new byte[12];
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap(shortArray0, byteArray0);
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      HashMap<Short, Byte> hashMap0 = new HashMap<Short, Byte>();
      tSynchronizedShortByteMap0.putAll((Map<? extends Short, ? extends Byte>) hashMap0);
      assertEquals(false, tSynchronizedShortByteMap0.isEmpty());
  }

  //Test case number: 12
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.putAll(Lgnu/trove/map/TShortByteMap;)V: root-Branch
   */

  @Test
  public void test12()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      tSynchronizedShortByteMap0.putAll((TShortByteMap) tShortByteHashMap0);
      assertEquals(23, tShortByteHashMap0.capacity());
  }

  //Test case number: 13
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.putIfAbsent(SB)B: root-Branch
   */

  @Test
  public void test13()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      tSynchronizedShortByteMap0.putIfAbsent((short)826, (byte) (-33));
      assertEquals("{826=-33}", tShortByteHashMap0.toString());
      assertEquals(false, tShortByteHashMap0.isEmpty());
  }

  //Test case number: 14
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.adjustOrPutValue(SBB)B: root-Branch
   */

  @Test
  public void test14()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      byte byte0 = tSynchronizedShortByteMap0.adjustOrPutValue((short) (byte) (-18), (byte) (-18), (byte) (-18));
      assertEquals(false, tShortByteHashMap0.isEmpty());
      assertEquals((byte) (-18), byte0);
  }

  //Test case number: 15
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.values([B)[B: root-Branch
   */

  @Test
  public void test15()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0, (Object) "0");
      byte[] byteArray0 = tSynchronizedShortByteMap0.values(tShortByteHashMap0._states);
      assertNotNull(byteArray0);
  }

  //Test case number: 16
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.forEachEntry(Lgnu/trove/procedure/TShortByteProcedure;)Z: root-Branch
   */

  @Test
  public void test16()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap(395);
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      boolean boolean0 = tSynchronizedShortByteMap0.forEachEntry((TShortByteProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 17
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.keys([S)[S: root-Branch
   */

  @Test
  public void test17()  throws Throwable  {
      short[] shortArray0 = new short[6];
      byte[] byteArray0 = new byte[12];
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap(shortArray0, byteArray0);
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      short[] shortArray1 = tSynchronizedShortByteMap0.keys(tShortByteHashMap0._set);
      assertFalse(shortArray0.equals(shortArray1));
  }

  //Test case number: 18
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.containsKey(S)Z: root-Branch
   */

  @Test
  public void test18()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap((-1955), (float) (-1955), (short)0, (byte) (-33));
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      boolean boolean0 = tSynchronizedShortByteMap0.containsKey((short)0);
      assertEquals(false, boolean0);
  }

  //Test case number: 19
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.forEachValue(Lgnu/trove/procedure/TByteProcedure;)Z: root-Branch
   */

  @Test
  public void test19()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0, (Object) "0");
      boolean boolean0 = tSynchronizedShortByteMap0.forEachValue((TByteProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 20
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.increment(S)Z: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedShortByteMap.<init>(Lgnu/trove/map/TShortByteMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test20()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      Byte byte0 = new Byte((byte)0);
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0, (Object) "0");
      boolean boolean0 = tSynchronizedShortByteMap0.increment((short) byte0);
      assertEquals(false, boolean0);
  }

  //Test case number: 21
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.transformValues(Lgnu/trove/function/TByteFunction;)V: root-Branch
   */

  @Test
  public void test21()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      tSynchronizedShortByteMap0.transformValues((TByteFunction) null);
      assertEquals(true, tSynchronizedShortByteMap0.isEmpty());
  }

  //Test case number: 22
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.retainEntries(Lgnu/trove/procedure/TShortByteProcedure;)Z: root-Branch
   */

  @Test
  public void test22()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap(395);
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      boolean boolean0 = tSynchronizedShortByteMap0.retainEntries((TShortByteProcedure) null);
      assertEquals(false, boolean0);
  }

  //Test case number: 23
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.getNoEntryValue()B: root-Branch
   */

  @Test
  public void test23()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap(395);
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      byte byte0 = tSynchronizedShortByteMap0.getNoEntryValue();
      assertEquals((byte)0, byte0);
  }

  //Test case number: 24
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.adjustValue(SB)Z: root-Branch
   */

  @Test
  public void test24()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      boolean boolean0 = tSynchronizedShortByteMap0.adjustValue((short)826, (byte)0);
      assertEquals(false, boolean0);
  }

  //Test case number: 25
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.get(S)B: root-Branch
   */

  @Test
  public void test25()  throws Throwable  {
      short[] shortArray0 = new short[6];
      byte[] byteArray0 = new byte[12];
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap(shortArray0, byteArray0);
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      byte byte0 = tSynchronizedShortByteMap0.get((short) (byte)0);
      assertEquals((byte)0, byte0);
  }

  //Test case number: 26
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.put(SB)B: root-Branch
   */

  @Test
  public void test26()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap(395);
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      tSynchronizedShortByteMap0.put((short) (byte)0, (byte)0);
      assertEquals(false, tShortByteHashMap0.isEmpty());
      assertEquals("{0=0}", tSynchronizedShortByteMap0.toString());
  }

  //Test case number: 27
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.toString()Ljava/lang/String;: root-Branch
   */

  @Test
  public void test27()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap(395);
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      String string0 = tSynchronizedShortByteMap0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 28
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.<init>(Lgnu/trove/map/TShortByteMap;)V: I17 Branch 1 IFNONNULL L59 - false
   */

  @Test
  public void test28()  throws Throwable  {
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = null;
      try {
        tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 29
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.keySet()Lgnu/trove/set/TShortSet;: I11 Branch 2 IFNONNULL L107 - true
   * 2 gnu.trove.impl.sync.TSynchronizedShortByteMap.keySet()Lgnu/trove/set/TShortSet;: I11 Branch 2 IFNONNULL L107 - false
   */

  @Test
  public void test29()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap(395);
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      TSynchronizedShortSet tSynchronizedShortSet0 = (TSynchronizedShortSet)tSynchronizedShortByteMap0.keySet();
      assertNotNull(tSynchronizedShortSet0);
      
      TSynchronizedShortSet tSynchronizedShortSet1 = (TSynchronizedShortSet)tSynchronizedShortByteMap0.keySet();
      assertSame(tSynchronizedShortSet1, tSynchronizedShortSet0);
  }

  //Test case number: 30
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedShortByteMap.valueCollection()Lgnu/trove/TByteCollection;: I11 Branch 3 IFNONNULL L121 - true
   * 2 gnu.trove.impl.sync.TSynchronizedShortByteMap.valueCollection()Lgnu/trove/TByteCollection;: I11 Branch 3 IFNONNULL L121 - false
   * 3 gnu.trove.impl.sync.TSynchronizedShortByteMap.<init>(Lgnu/trove/map/TShortByteMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test30()  throws Throwable  {
      TShortByteHashMap tShortByteHashMap0 = new TShortByteHashMap();
      TSynchronizedShortByteMap tSynchronizedShortByteMap0 = new TSynchronizedShortByteMap((TShortByteMap) tShortByteHashMap0);
      TSynchronizedByteCollection tSynchronizedByteCollection0 = (TSynchronizedByteCollection)tSynchronizedShortByteMap0.valueCollection();
      assertNotNull(tSynchronizedByteCollection0);
      
      TSynchronizedByteCollection tSynchronizedByteCollection1 = (TSynchronizedByteCollection)tSynchronizedShortByteMap0.valueCollection();
      assertSame(tSynchronizedByteCollection1, tSynchronizedByteCollection0);
  }
}
