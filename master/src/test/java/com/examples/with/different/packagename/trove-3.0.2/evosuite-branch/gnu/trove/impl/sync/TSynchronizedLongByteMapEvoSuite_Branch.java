/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.sync;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TByteFunction;
import gnu.trove.impl.sync.TSynchronizedByteCollection;
import gnu.trove.impl.sync.TSynchronizedLongByteMap;
import gnu.trove.impl.sync.TSynchronizedLongSet;
import gnu.trove.map.TLongByteMap;
import gnu.trove.map.hash.TLongByteHashMap;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TLongByteProcedure;
import gnu.trove.procedure.TLongProcedure;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TSynchronizedLongByteMapEvoSuite_Branch {

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
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.put(JB)B: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedLongByteMap.<init>(Lgnu/trove/map/TLongByteMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test0()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap((-1879));
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      tSynchronizedLongByteMap0.put((long) (-1879), (byte)117);
      assertEquals(false, tLongByteHashMap0.isEmpty());
      assertEquals("{-1879=117}", tLongByteHashMap0.toString());
  }

  //Test case number: 1
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.toString()Ljava/lang/String;: root-Branch
   */

  @Test
  public void test1()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(0);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      String string0 = tSynchronizedLongByteMap0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 2
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.adjustValue(JB)Z: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(16);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      boolean boolean0 = tSynchronizedLongByteMap0.adjustValue((long) (byte)0, (byte)2);
      assertEquals(false, boolean0);
  }

  //Test case number: 3
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.values([B)[B: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap((-4));
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      byte[] byteArray0 = tSynchronizedLongByteMap0.values(tLongByteHashMap0._states);
      assertNotNull(byteArray0);
  }

  //Test case number: 4
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.putIfAbsent(JB)B: root-Branch
   */

  @Test
  public void test4()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap((-1535), (-1535));
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      tSynchronizedLongByteMap0.putIfAbsent((long) (-1535), (byte) (-25));
      assertEquals(false, tLongByteHashMap0.isEmpty());
      assertEquals("{-1535=-25}", tLongByteHashMap0.toString());
  }

  //Test case number: 5
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.equals(Ljava/lang/Object;)Z: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedLongByteMap.size()I: root-Branch
   * 3 gnu.trove.impl.sync.TSynchronizedLongByteMap.getNoEntryValue()B: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(16);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      boolean boolean0 = tSynchronizedLongByteMap0.equals((Object) tSynchronizedLongByteMap0);
      assertEquals(true, boolean0);
  }

  //Test case number: 6
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.getNoEntryKey()J: root-Branch
   */

  @Test
  public void test6()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap((-1535), (-1535));
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      long long0 = tSynchronizedLongByteMap0.getNoEntryKey();
      assertEquals(0L, long0);
  }

  //Test case number: 7
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.putAll(Lgnu/trove/map/TLongByteMap;)V: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedLongByteMap.iterator()Lgnu/trove/iterator/TLongByteIterator;: root-Branch
   * 3 gnu.trove.impl.sync.TSynchronizedLongByteMap.size()I: root-Branch
   */

  @Test
  public void test7()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(16);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      tSynchronizedLongByteMap0.putAll((TLongByteMap) tSynchronizedLongByteMap0);
      assertEquals("{}", tSynchronizedLongByteMap0.toString());
  }

  //Test case number: 8
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.containsKey(J)Z: root-Branch
   */

  @Test
  public void test8()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(16);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      boolean boolean0 = tSynchronizedLongByteMap0.containsKey((long) 16);
      assertEquals(false, boolean0);
  }

  //Test case number: 9
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.remove(J)B: root-Branch
   */

  @Test
  public void test9()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(16);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      byte byte0 = tSynchronizedLongByteMap0.remove((long) 16);
      assertEquals((byte)0, byte0);
  }

  //Test case number: 10
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.<init>(Lgnu/trove/map/TLongByteMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test10()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(0);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0, (Object) "{}");
      assertEquals("{}", tSynchronizedLongByteMap0.toString());
  }

  //Test case number: 11
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.values()[B: root-Branch
   */

  @Test
  public void test11()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap();
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      byte[] byteArray0 = tSynchronizedLongByteMap0.values();
      assertNotNull(byteArray0);
  }

  //Test case number: 12
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.get(J)B: root-Branch
   */

  @Test
  public void test12()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap();
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      byte byte0 = tSynchronizedLongByteMap0.get((long) (byte)1);
      assertEquals((byte)0, byte0);
  }

  //Test case number: 13
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.adjustOrPutValue(JBB)B: root-Branch
   */

  @Test
  public void test13()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap((-1535), (-1535));
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      byte byte0 = tSynchronizedLongByteMap0.adjustOrPutValue((long) (byte)0, (byte) (-25), (byte) (-25));
      assertEquals(1, tLongByteHashMap0.size());
      assertEquals((byte) (-25), byte0);
  }

  //Test case number: 14
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.forEachEntry(Lgnu/trove/procedure/TLongByteProcedure;)Z: root-Branch
   */

  @Test
  public void test14()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap((int) (byte)0, (float) 1956L, (long) (byte)0, (byte)0);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      boolean boolean0 = tSynchronizedLongByteMap0.forEachEntry((TLongByteProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 15
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.isEmpty()Z: root-Branch
   */

  @Test
  public void test15()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap((-4));
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      boolean boolean0 = tSynchronizedLongByteMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 16
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.putAll(Ljava/util/Map;)V: root-Branch
   */

  @Test
  public void test16()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(0);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      HashMap<Long, Byte> hashMap0 = new HashMap<Long, Byte>();
      tSynchronizedLongByteMap0.putAll((Map<? extends Long, ? extends Byte>) hashMap0);
      assertEquals(true, hashMap0.isEmpty());
  }

  //Test case number: 17
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.hashCode()I: root-Branch
   */

  @Test
  public void test17()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap((-1879));
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      int int0 = tSynchronizedLongByteMap0.hashCode();
      assertEquals(0, int0);
  }

  //Test case number: 18
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.containsValue(B)Z: root-Branch
   */

  @Test
  public void test18()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap();
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      boolean boolean0 = tSynchronizedLongByteMap0.containsValue((byte)0);
      assertEquals(false, boolean0);
  }

  //Test case number: 19
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.increment(J)Z: root-Branch
   */

  @Test
  public void test19()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(0);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      boolean boolean0 = tSynchronizedLongByteMap0.increment((long) 0);
      assertEquals(false, boolean0);
  }

  //Test case number: 20
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.transformValues(Lgnu/trove/function/TByteFunction;)V: root-Branch
   */

  @Test
  public void test20()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(16);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      tSynchronizedLongByteMap0.transformValues((TByteFunction) null);
      assertEquals(0L, tSynchronizedLongByteMap0.getNoEntryKey());
  }

  //Test case number: 21
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.keys([J)[J: root-Branch
   */

  @Test
  public void test21()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(0);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      long[] longArray0 = tSynchronizedLongByteMap0.keys(tLongByteHashMap0._set);
      assertNotNull(longArray0);
  }

  //Test case number: 22
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.retainEntries(Lgnu/trove/procedure/TLongByteProcedure;)Z: root-Branch
   */

  @Test
  public void test22()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(0);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      boolean boolean0 = tSynchronizedLongByteMap0.retainEntries((TLongByteProcedure) null);
      assertEquals(false, boolean0);
  }

  //Test case number: 23
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.clear()V: root-Branch
   */

  @Test
  public void test23()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap((-4));
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      tSynchronizedLongByteMap0.clear();
      assertEquals(0, tSynchronizedLongByteMap0.size());
  }

  //Test case number: 24
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.forEachValue(Lgnu/trove/procedure/TByteProcedure;)Z: root-Branch
   */

  @Test
  public void test24()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap((-4));
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      boolean boolean0 = tSynchronizedLongByteMap0.forEachValue((TByteProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 25
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.keys()[J: root-Branch
   */

  @Test
  public void test25()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(0);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      long[] longArray0 = tSynchronizedLongByteMap0.keys();
      assertNotNull(longArray0);
  }

  //Test case number: 26
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.forEachKey(Lgnu/trove/procedure/TLongProcedure;)Z: root-Branch
   */

  @Test
  public void test26()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap((int) (byte)0, (float) 1956L, (long) (byte)0, (byte)0);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      boolean boolean0 = tSynchronizedLongByteMap0.forEachKey((TLongProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 27
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.<init>(Lgnu/trove/map/TLongByteMap;)V: I17 Branch 1 IFNONNULL L59 - false
   */

  @Test
  public void test27()  throws Throwable  {
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = null;
      try {
        tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 28
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.keySet()Lgnu/trove/set/TLongSet;: I11 Branch 2 IFNONNULL L107 - true
   * 2 gnu.trove.impl.sync.TSynchronizedLongByteMap.keySet()Lgnu/trove/set/TLongSet;: I11 Branch 2 IFNONNULL L107 - false
   */

  @Test
  public void test28()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(0);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      TSynchronizedLongSet tSynchronizedLongSet0 = (TSynchronizedLongSet)tSynchronizedLongByteMap0.keySet();
      assertNotNull(tSynchronizedLongSet0);
      
      TSynchronizedLongSet tSynchronizedLongSet1 = (TSynchronizedLongSet)tSynchronizedLongByteMap0.keySet();
      assertSame(tSynchronizedLongSet1, tSynchronizedLongSet0);
  }

  //Test case number: 29
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedLongByteMap.valueCollection()Lgnu/trove/TByteCollection;: I11 Branch 3 IFNONNULL L121 - true
   * 2 gnu.trove.impl.sync.TSynchronizedLongByteMap.valueCollection()Lgnu/trove/TByteCollection;: I11 Branch 3 IFNONNULL L121 - false
   * 3 gnu.trove.impl.sync.TSynchronizedLongByteMap.<init>(Lgnu/trove/map/TLongByteMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test29()  throws Throwable  {
      TLongByteHashMap tLongByteHashMap0 = new TLongByteHashMap(16);
      TSynchronizedLongByteMap tSynchronizedLongByteMap0 = new TSynchronizedLongByteMap((TLongByteMap) tLongByteHashMap0);
      TSynchronizedByteCollection tSynchronizedByteCollection0 = (TSynchronizedByteCollection)tSynchronizedLongByteMap0.valueCollection();
      assertNotNull(tSynchronizedByteCollection0);
      
      TSynchronizedByteCollection tSynchronizedByteCollection1 = (TSynchronizedByteCollection)tSynchronizedLongByteMap0.valueCollection();
      assertSame(tSynchronizedByteCollection1, tSynchronizedByteCollection0);
  }
}
