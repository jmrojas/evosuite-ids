/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.sync;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TDoubleFunction;
import gnu.trove.impl.sync.TSynchronizedByteDoubleMap;
import gnu.trove.impl.sync.TSynchronizedByteSet;
import gnu.trove.impl.sync.TSynchronizedDoubleCollection;
import gnu.trove.iterator.TByteDoubleIterator;
import gnu.trove.map.TByteDoubleMap;
import gnu.trove.map.hash.TByteDoubleHashMap;
import gnu.trove.procedure.TByteDoubleProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TSynchronizedByteDoubleMapEvoSuite_Branch {

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
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.equals(Ljava/lang/Object;)Z: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.<init>(Lgnu/trove/map/TByteDoubleMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test0()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(44);
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tSynchronizedByteDoubleMap0.equals((Object) 44);
      assertEquals(false, boolean0);
  }

  //Test case number: 1
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.containsValue(D)Z: root-Branch
   */

  @Test
  public void test1()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((-28), (float) (-28), (byte) (-97), (double) (byte) (-97));
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tSynchronizedByteDoubleMap0.containsValue((double) (-28));
      assertEquals(false, boolean0);
  }

  //Test case number: 2
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.values([D)[D: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((-28), (float) (-28), (byte) (-97), (double) (byte) (-97));
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      double[] doubleArray0 = new double[7];
      double[] doubleArray1 = tSynchronizedByteDoubleMap0.values(doubleArray0);
      assertSame(doubleArray1, doubleArray0);
  }

  //Test case number: 3
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.iterator()Lgnu/trove/iterator/TByteDoubleIterator;: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      TByteDoubleIterator tByteDoubleIterator0 = tSynchronizedByteDoubleMap0.iterator();
      assertEquals(false, tByteDoubleIterator0.hasNext());
  }

  //Test case number: 4
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.adjustOrPutValue(BDD)D: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.<init>(Lgnu/trove/map/TByteDoubleMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test4()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0, (Object) ":");
      double double0 = tSynchronizedByteDoubleMap0.adjustOrPutValue((byte)19, (byte)19, (byte)19);
      assertEquals(false, tByteDoubleHashMap0.isEmpty());
      assertEquals(19.0, double0, 0.01D);
  }

  //Test case number: 5
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.<init>(Lgnu/trove/map/TByteDoubleMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0, (Object) ":");
      boolean boolean0 = tSynchronizedByteDoubleMap0.forEachKey((TByteProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 6
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.increment(B)Z: root-Branch
   */

  @Test
  public void test6()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((-28), (float) (-28), (byte) (-97), (double) (byte) (-97));
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tSynchronizedByteDoubleMap0.increment((byte) (-97));
      assertEquals(false, boolean0);
  }

  //Test case number: 7
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.putIfAbsent(BD)D: root-Branch
   */

  @Test
  public void test7()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((int) (byte)30, (float) (byte)30, (byte)30, (double) (byte)30);
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      double double0 = tSynchronizedByteDoubleMap0.putIfAbsent((byte)30, (byte)30);
      assertEquals(false, tByteDoubleHashMap0.isEmpty());
      assertEquals(30.0, double0, 0.01D);
  }

  //Test case number: 8
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.remove(B)D: root-Branch
   */

  @Test
  public void test8()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      double double0 = tSynchronizedByteDoubleMap0.remove((byte) (-91));
      assertEquals(0.0, double0, 0.01D);
  }

  //Test case number: 9
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.getNoEntryKey()B: root-Branch
   */

  @Test
  public void test9()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(307);
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      byte byte0 = tSynchronizedByteDoubleMap0.getNoEntryKey();
      assertEquals((byte)0, byte0);
  }

  //Test case number: 10
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.containsKey(B)Z: root-Branch
   */

  @Test
  public void test10()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((-28), (float) (-28), (byte) (-97), (double) (byte) (-97));
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tSynchronizedByteDoubleMap0.containsKey((byte) (-97));
      assertEquals(false, boolean0);
  }

  //Test case number: 11
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.putAll(Lgnu/trove/map/TByteDoubleMap;)V: root-Branch
   */

  @Test
  public void test11()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      tSynchronizedByteDoubleMap0.putAll((TByteDoubleMap) tByteDoubleHashMap0);
      assertEquals(0.0, tSynchronizedByteDoubleMap0.getNoEntryValue(), 0.01D);
  }

  //Test case number: 12
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.clear()V: root-Branch
   */

//   @Test
//   public void test12()  throws Throwable  {
//       TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(44);
//       TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
//       tSynchronizedByteDoubleMap0.clear();
//       assertEquals(0, tSynchronizedByteDoubleMap0.getNoEntryKey());
//   }

  //Test case number: 13
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.putAll(Ljava/util/Map;)V: root-Branch
   */

  @Test
  public void test13()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(307);
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      HashMap<Byte, Double> hashMap0 = new HashMap<Byte, Double>();
      tSynchronizedByteDoubleMap0.putAll((Map<? extends Byte, ? extends Double>) hashMap0);
      assertEquals(0, tSynchronizedByteDoubleMap0.size());
  }

  //Test case number: 14
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.isEmpty()Z: root-Branch
   */

  @Test
  public void test14()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((-28), (float) (-28), (byte) (-97), (double) (byte) (-97));
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tSynchronizedByteDoubleMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 15
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.get(B)D: root-Branch
   */

  @Test
  public void test15()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(307);
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      double double0 = tSynchronizedByteDoubleMap0.get((byte)0);
      assertEquals(0.0, double0, 0.01D);
  }

  //Test case number: 16
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.keys()[B: root-Branch
   */

  @Test
  public void test16()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(307);
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      byte[] byteArray0 = tSynchronizedByteDoubleMap0.keys();
      assertNotNull(byteArray0);
  }

  //Test case number: 17
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.transformValues(Lgnu/trove/function/TDoubleFunction;)V: root-Branch
   */

//   @Test
//   public void test17()  throws Throwable  {
//       TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
//       TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
//       tSynchronizedByteDoubleMap0.transformValues((TDoubleFunction) null);
//       assertEquals(0, tSynchronizedByteDoubleMap0.getNoEntryKey());
//   }

  //Test case number: 18
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.keys([B)[B: root-Branch
   */

  @Test
  public void test18()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(307);
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      byte[] byteArray0 = tSynchronizedByteDoubleMap0.keys(tByteDoubleHashMap0._set);
      assertNotNull(byteArray0);
  }

  //Test case number: 19
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.getNoEntryValue()D: root-Branch
   */

  @Test
  public void test19()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(44);
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      double double0 = tSynchronizedByteDoubleMap0.getNoEntryValue();
      assertEquals(0.0, double0, 0.01D);
  }

  //Test case number: 20
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.forEachValue(Lgnu/trove/procedure/TDoubleProcedure;)Z: root-Branch
   */

  @Test
  public void test20()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((-28), (float) (-28), (byte) (-97), (double) (byte) (-97));
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tSynchronizedByteDoubleMap0.forEachValue((TDoubleProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 21
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.values()[D: root-Branch
   */

  @Test
  public void test21()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((-28), (float) (-28), (byte) (-97), (double) (byte) (-97));
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      double[] doubleArray0 = tSynchronizedByteDoubleMap0.values();
      assertNotNull(doubleArray0);
  }

  //Test case number: 22
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.retainEntries(Lgnu/trove/procedure/TByteDoubleProcedure;)Z: root-Branch
   */

  @Test
  public void test22()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(44);
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tSynchronizedByteDoubleMap0.retainEntries((TByteDoubleProcedure) null);
      assertEquals(false, boolean0);
  }

  //Test case number: 23
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.adjustValue(BD)Z: root-Branch
   */

  @Test
  public void test23()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tSynchronizedByteDoubleMap0.adjustValue((byte) (-1), (double) 0);
      assertEquals(false, boolean0);
  }

  //Test case number: 24
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.size()I: root-Branch
   */

  @Test
  public void test24()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      int int0 = tSynchronizedByteDoubleMap0.size();
      assertEquals(0, int0);
  }

  //Test case number: 25
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.forEachEntry(Lgnu/trove/procedure/TByteDoubleProcedure;)Z: root-Branch
   */

  @Test
  public void test25()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(44);
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tSynchronizedByteDoubleMap0.forEachEntry((TByteDoubleProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 26
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.hashCode()I: root-Branch
   */

  @Test
  public void test26()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(307);
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      int int0 = tSynchronizedByteDoubleMap0.hashCode();
      assertEquals(0, int0);
  }

  //Test case number: 27
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.toString()Ljava/lang/String;: root-Branch
   */

  @Test
  public void test27()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((-28), (float) (-28), (byte) (-97), (double) (byte) (-97));
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      String string0 = tSynchronizedByteDoubleMap0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 28
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.put(BD)D: root-Branch
   */

  @Test
  public void test28()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      tSynchronizedByteDoubleMap0.put((byte) (-1), (double) 0);
      assertEquals("{-1=0.0}", tByteDoubleHashMap0.toString());
      assertEquals("{-1=0.0}", tSynchronizedByteDoubleMap0.toString());
  }

  //Test case number: 29
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.<init>(Lgnu/trove/map/TByteDoubleMap;)V: I17 Branch 1 IFNONNULL L59 - false
   */

  @Test
  public void test29()  throws Throwable  {
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = null;
      try {
        tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 30
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.keySet()Lgnu/trove/set/TByteSet;: I11 Branch 2 IFNONNULL L107 - true
   * 2 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.keySet()Lgnu/trove/set/TByteSet;: I11 Branch 2 IFNONNULL L107 - false
   */

  @Test
  public void test30()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(44);
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      TSynchronizedByteSet tSynchronizedByteSet0 = (TSynchronizedByteSet)tSynchronizedByteDoubleMap0.keySet();
      assertNotNull(tSynchronizedByteSet0);
      
      TSynchronizedByteSet tSynchronizedByteSet1 = (TSynchronizedByteSet)tSynchronizedByteDoubleMap0.keySet();
      assertSame(tSynchronizedByteSet1, tSynchronizedByteSet0);
  }

  //Test case number: 31
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.valueCollection()Lgnu/trove/TDoubleCollection;: I11 Branch 3 IFNONNULL L121 - true
   * 2 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.valueCollection()Lgnu/trove/TDoubleCollection;: I11 Branch 3 IFNONNULL L121 - false
   * 3 gnu.trove.impl.sync.TSynchronizedByteDoubleMap.<init>(Lgnu/trove/map/TByteDoubleMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test31()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TSynchronizedByteDoubleMap tSynchronizedByteDoubleMap0 = new TSynchronizedByteDoubleMap((TByteDoubleMap) tByteDoubleHashMap0);
      TSynchronizedDoubleCollection tSynchronizedDoubleCollection0 = (TSynchronizedDoubleCollection)tSynchronizedByteDoubleMap0.valueCollection();
      assertNotNull(tSynchronizedDoubleCollection0);
      
      TSynchronizedDoubleCollection tSynchronizedDoubleCollection1 = (TSynchronizedDoubleCollection)tSynchronizedByteDoubleMap0.valueCollection();
      assertSame(tSynchronizedDoubleCollection1, tSynchronizedDoubleCollection0);
  }
}
