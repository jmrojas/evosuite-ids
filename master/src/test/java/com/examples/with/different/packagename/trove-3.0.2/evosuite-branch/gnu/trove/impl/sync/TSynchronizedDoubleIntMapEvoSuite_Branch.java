/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.sync;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TIntFunction;
import gnu.trove.impl.sync.TSynchronizedDoubleIntMap;
import gnu.trove.impl.sync.TSynchronizedDoubleSet;
import gnu.trove.impl.sync.TSynchronizedIntCollection;
import gnu.trove.iterator.TDoubleIntIterator;
import gnu.trove.map.TDoubleIntMap;
import gnu.trove.map.hash.TDoubleIntHashMap;
import gnu.trove.procedure.TDoubleIntProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TIntProcedure;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TSynchronizedDoubleIntMapEvoSuite_Branch {

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
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.values()[I: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.<init>(Lgnu/trove/map/TDoubleIntMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test0()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(1433);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0, (Object) "\u4E2D\u6587");
      int[] intArray0 = tSynchronizedDoubleIntMap0.values();
      assertNotNull(intArray0);
  }

  //Test case number: 1
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.containsValue(I)Z: root-Branch
   */

  @Test
  public void test1()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap((-1868));
      Integer integer0 = new Integer((-1868));
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0, (Object) integer0);
      boolean boolean0 = tSynchronizedDoubleIntMap0.containsValue((-1868));
      assertEquals(false, boolean0);
  }

  //Test case number: 2
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.adjustOrPutValue(DII)I: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap((-1868));
      Integer integer0 = new Integer((-1868));
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0, (Object) integer0);
      int int0 = tSynchronizedDoubleIntMap0.adjustOrPutValue((double) (-1868), (-1868), (-1868));
      assertEquals(false, tDoubleIntHashMap0.isEmpty());
      assertEquals((-1868), int0);
  }

  //Test case number: 3
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.keys([D)[D: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      double[] doubleArray0 = new double[4];
      int[] intArray0 = new int[9];
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(doubleArray0, intArray0);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0, (Object) 0);
      double[] doubleArray1 = tSynchronizedDoubleIntMap0.keys(tDoubleIntHashMap0._set);
      assertNotSame(doubleArray1, doubleArray0);
  }

  //Test case number: 4
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.equals(Ljava/lang/Object;)Z: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.<init>(Lgnu/trove/map/TDoubleIntMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test4()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap((-1737));
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      boolean boolean0 = tSynchronizedDoubleIntMap0.equals((Object) tDoubleIntHashMap0);
      assertEquals(true, boolean0);
  }

  //Test case number: 5
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.remove(D)I: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap((-1868));
      Integer integer0 = new Integer((-1868));
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0, (Object) integer0);
      int int0 = tSynchronizedDoubleIntMap0.remove((double) (-1868));
      assertNotSame(integer0, int0);
  }

  //Test case number: 6
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.values([I)[I: root-Branch
   */

  @Test
  public void test6()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap();
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      int[] intArray0 = new int[1];
      int[] intArray1 = tSynchronizedDoubleIntMap0.values(intArray0);
      assertSame(intArray1, intArray0);
  }

  //Test case number: 7
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.containsKey(D)Z: root-Branch
   */

  @Test
  public void test7()  throws Throwable  {
      double[] doubleArray0 = new double[4];
      int[] intArray0 = new int[9];
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(doubleArray0, intArray0);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0, (Object) 0);
      boolean boolean0 = tSynchronizedDoubleIntMap0.containsKey((double) 0);
      assertEquals(true, boolean0);
  }

  //Test case number: 8
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.isEmpty()Z: root-Branch
   */

  @Test
  public void test8()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(1433);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0, (Object) "\u4E2D\u6587");
      boolean boolean0 = tSynchronizedDoubleIntMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 9
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.getNoEntryKey()D: root-Branch
   */

  @Test
  public void test9()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap();
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      double double0 = tSynchronizedDoubleIntMap0.getNoEntryKey();
      assertEquals(0.0, double0, 0.01D);
  }

  //Test case number: 10
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.putAll(Ljava/util/Map;)V: root-Branch
   */

  @Test
  public void test10()  throws Throwable  {
      double[] doubleArray0 = new double[3];
      int[] intArray0 = new int[8];
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(doubleArray0, intArray0);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      HashMap<Double, Integer> hashMap0 = new HashMap<Double, Integer>();
      tSynchronizedDoubleIntMap0.putAll((Map<? extends Double, ? extends Integer>) hashMap0);
      assertEquals(0, hashMap0.size());
  }

  //Test case number: 11
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.get(D)I: root-Branch
   */

  @Test
  public void test11()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(1811107, 1811107);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      int int0 = tSynchronizedDoubleIntMap0.get((double) 1811107);
      assertEquals(0, int0);
  }

  //Test case number: 12
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.transformValues(Lgnu/trove/function/TIntFunction;)V: root-Branch
   */

  @Test
  public void test12()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap((-1737));
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      tSynchronizedDoubleIntMap0.transformValues((TIntFunction) null);
      assertEquals(0, tSynchronizedDoubleIntMap0.size());
  }

  //Test case number: 13
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.keys()[D: root-Branch
   */

  @Test
  public void test13()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap((-1737));
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      double[] doubleArray0 = tSynchronizedDoubleIntMap0.keys();
      assertNotNull(doubleArray0);
  }

  //Test case number: 14
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.iterator()Lgnu/trove/iterator/TDoubleIntIterator;: root-Branch
   */

  @Test
  public void test14()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(1811107, 1811107);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      TDoubleIntIterator tDoubleIntIterator0 = tSynchronizedDoubleIntMap0.iterator();
      assertEquals(false, tDoubleIntIterator0.hasNext());
  }

  //Test case number: 15
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.put(DI)I: root-Branch
   */

  @Test
  public void test15()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap((-1868));
      Integer integer0 = new Integer((-1868));
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0, (Object) integer0);
      tSynchronizedDoubleIntMap0.put((double) integer0, (int) integer0);
      assertEquals(1, tDoubleIntHashMap0.size());
      assertEquals(false, tSynchronizedDoubleIntMap0.isEmpty());
  }

  //Test case number: 16
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.clear()V: root-Branch
   */

  @Test
  public void test16()  throws Throwable  {
      double[] doubleArray0 = new double[4];
      int[] intArray0 = new int[9];
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(doubleArray0, intArray0);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0, (Object) 0);
      tSynchronizedDoubleIntMap0.clear();
      assertEquals(0, tDoubleIntHashMap0.size());
      assertEquals(true, tDoubleIntHashMap0.isEmpty());
  }

  //Test case number: 17
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.toString()Ljava/lang/String;: root-Branch
   */

  @Test
  public void test17()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap();
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      String string0 = tSynchronizedDoubleIntMap0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 18
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.size()I: root-Branch
   */

  @Test
  public void test18()  throws Throwable  {
      double[] doubleArray0 = new double[3];
      int[] intArray0 = new int[8];
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(doubleArray0, intArray0);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      int int0 = tSynchronizedDoubleIntMap0.size();
      assertEquals(1, int0);
  }

  //Test case number: 19
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.adjustValue(DI)Z: root-Branch
   */

  @Test
  public void test19()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap((-1737));
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      boolean boolean0 = tSynchronizedDoubleIntMap0.adjustValue((double) (-1737), (-1737));
      assertEquals(false, boolean0);
  }

  //Test case number: 20
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z: root-Branch
   */

  @Test
  public void test20()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap();
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      boolean boolean0 = tSynchronizedDoubleIntMap0.forEachValue((TIntProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 21
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.hashCode()I: root-Branch
   */

  @Test
  public void test21()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap();
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      int int0 = tSynchronizedDoubleIntMap0.hashCode();
      assertEquals(0, int0);
  }

  //Test case number: 22
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.forEachKey(Lgnu/trove/procedure/TDoubleProcedure;)Z: root-Branch
   */

  @Test
  public void test22()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap((-1737));
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      boolean boolean0 = tSynchronizedDoubleIntMap0.forEachKey((TDoubleProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 23
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.forEachEntry(Lgnu/trove/procedure/TDoubleIntProcedure;)Z: root-Branch
   */

  @Test
  public void test23()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(1811107, 1811107);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      boolean boolean0 = tSynchronizedDoubleIntMap0.forEachEntry((TDoubleIntProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 24
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.increment(D)Z: root-Branch
   */

  @Test
  public void test24()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(1433);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0, (Object) "\u4E2D\u6587");
      boolean boolean0 = tSynchronizedDoubleIntMap0.increment((double) 1433);
      assertEquals(false, boolean0);
  }

  //Test case number: 25
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.putIfAbsent(DI)I: root-Branch
   */

  @Test
  public void test25()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap((-1868));
      Integer integer0 = new Integer((-1868));
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0, (Object) integer0);
      tSynchronizedDoubleIntMap0.putIfAbsent((double) (-1868), 0);
      assertEquals(1, tDoubleIntHashMap0.size());
      assertEquals("{-1868.0=0}", tDoubleIntHashMap0.toString());
  }

  //Test case number: 26
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.putAll(Lgnu/trove/map/TDoubleIntMap;)V: root-Branch
   */

  @Test
  public void test26()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap((-1737));
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      tSynchronizedDoubleIntMap0.putAll((TDoubleIntMap) tDoubleIntHashMap0);
      assertEquals("{}", tDoubleIntHashMap0.toString());
  }

  //Test case number: 27
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.getNoEntryValue()I: root-Branch
   */

  @Test
  public void test27()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(1811107, 1811107);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      int int0 = tSynchronizedDoubleIntMap0.getNoEntryValue();
      assertEquals(0, int0);
  }

  //Test case number: 28
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.retainEntries(Lgnu/trove/procedure/TDoubleIntProcedure;)Z: root-Branch
   */

  @Test
  public void test28()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(1811107, 1811107);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      boolean boolean0 = tSynchronizedDoubleIntMap0.retainEntries((TDoubleIntProcedure) null);
      assertEquals(false, boolean0);
  }

  //Test case number: 29
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.<init>(Lgnu/trove/map/TDoubleIntMap;)V: I17 Branch 1 IFNONNULL L59 - false
   */

  @Test
  public void test29()  throws Throwable  {
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = null;
      try {
        tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) null);
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
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.keySet()Lgnu/trove/set/TDoubleSet;: I11 Branch 2 IFNONNULL L107 - true
   * 2 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.keySet()Lgnu/trove/set/TDoubleSet;: I11 Branch 2 IFNONNULL L107 - false
   * 3 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.<init>(Lgnu/trove/map/TDoubleIntMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test30()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap();
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0);
      TSynchronizedDoubleSet tSynchronizedDoubleSet0 = (TSynchronizedDoubleSet)tSynchronizedDoubleIntMap0.keySet();
      assertNotNull(tSynchronizedDoubleSet0);
      
      TSynchronizedDoubleSet tSynchronizedDoubleSet1 = (TSynchronizedDoubleSet)tSynchronizedDoubleIntMap0.keySet();
      assertSame(tSynchronizedDoubleSet1, tSynchronizedDoubleSet0);
  }

  //Test case number: 31
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.valueCollection()Lgnu/trove/TIntCollection;: I11 Branch 3 IFNONNULL L121 - true
   * 2 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.valueCollection()Lgnu/trove/TIntCollection;: I11 Branch 3 IFNONNULL L121 - false
   * 3 gnu.trove.impl.sync.TSynchronizedDoubleIntMap.<init>(Lgnu/trove/map/TDoubleIntMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test31()  throws Throwable  {
      TDoubleIntHashMap tDoubleIntHashMap0 = new TDoubleIntHashMap(1433);
      TSynchronizedDoubleIntMap tSynchronizedDoubleIntMap0 = new TSynchronizedDoubleIntMap((TDoubleIntMap) tDoubleIntHashMap0, (Object) "\u4E2D\u6587");
      TSynchronizedIntCollection tSynchronizedIntCollection0 = (TSynchronizedIntCollection)tSynchronizedDoubleIntMap0.valueCollection();
      assertNotNull(tSynchronizedIntCollection0);
      
      TSynchronizedIntCollection tSynchronizedIntCollection1 = (TSynchronizedIntCollection)tSynchronizedDoubleIntMap0.valueCollection();
      assertSame(tSynchronizedIntCollection1, tSynchronizedIntCollection0);
  }
}
