/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.sync;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TDoubleFunction;
import gnu.trove.impl.sync.TSynchronizedDoubleCollection;
import gnu.trove.impl.sync.TSynchronizedObjectDoubleMap;
import gnu.trove.iterator.TObjectDoubleIterator;
import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.map.custom_hash.TObjectDoubleCustomHashMap;
import gnu.trove.map.hash.TObjectDoubleHashMap;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TObjectDoubleProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.array.ToObjectArrayProceedure;
import gnu.trove.strategy.HashingStrategy;
import gnu.trove.strategy.IdentityHashingStrategy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TSynchronizedObjectDoubleMapEvoSuite_Branch {

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
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.increment(Ljava/lang/Object;)Z: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.<init>(Lgnu/trove/map/TObjectDoubleMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test0()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectDoubleCustomHashMap<Object> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Object>((HashingStrategy<? super Object>) identityHashingStrategy0, 0);
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleCustomHashMap0);
      boolean boolean0 = tSynchronizedObjectDoubleMap0.increment((Object) "italiano");
      assertEquals(false, boolean0);
  }

  //Test case number: 1
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.toString()Ljava/lang/String;: root-Branch
   */

  @Test
  public void test1()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectDoubleCustomHashMap<Object> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Object>((HashingStrategy<? super Object>) identityHashingStrategy0, (-30), (float) (-30));
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleCustomHashMap0);
      String string0 = tSynchronizedObjectDoubleMap0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 2
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.hashCode()I: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      TObjectDoubleCustomHashMap<Object> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Object>();
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleCustomHashMap0);
      int int0 = tSynchronizedObjectDoubleMap0.hashCode();
      assertEquals(0, int0);
  }

  //Test case number: 3
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.containsKey(Ljava/lang/Object;)Z: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.<init>(Lgnu/trove/map/TObjectDoubleMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectDoubleCustomHashMap<Integer> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Integer>((HashingStrategy<? super Integer>) identityHashingStrategy0, (-808));
      TSynchronizedObjectDoubleMap<Integer> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Integer>((TObjectDoubleMap<Integer>) tObjectDoubleCustomHashMap0, (Object) "italiano");
      boolean boolean0 = tSynchronizedObjectDoubleMap0.containsKey((Object) "italiano");
      assertEquals(false, boolean0);
  }

  //Test case number: 4
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.equals(Ljava/lang/Object;)Z: root-Branch
   */

  @Test
  public void test4()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectDoubleCustomHashMap<Object> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Object>((HashingStrategy<? super Object>) identityHashingStrategy0, 0);
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleCustomHashMap0);
      boolean boolean0 = tSynchronizedObjectDoubleMap0.equals((Object) "italiano");
      assertEquals(false, boolean0);
  }

  //Test case number: 5
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.get(Ljava/lang/Object;)D: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      TObjectDoubleHashMap<Object> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Object>();
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleHashMap0, (Object) "");
      double double0 = tSynchronizedObjectDoubleMap0.get((Object) "");
      assertEquals(0.0, double0, 0.01D);
  }

  //Test case number: 6
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.isEmpty()Z: root-Branch
   */

  @Test
  public void test6()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectDoubleCustomHashMap<Integer> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Integer>((HashingStrategy<? super Integer>) identityHashingStrategy0, 930);
      Object object0 = new Object();
      TSynchronizedObjectDoubleMap<Integer> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Integer>((TObjectDoubleMap<Integer>) tObjectDoubleCustomHashMap0, object0);
      boolean boolean0 = tSynchronizedObjectDoubleMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 7
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.putIfAbsent(Ljava/lang/Object;D)D: root-Branch
   */

  @Test
  public void test7()  throws Throwable  {
      Double double0 = new Double((double) 0);
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectDoubleCustomHashMap<Integer> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Integer>((HashingStrategy<? super Integer>) identityHashingStrategy0, 0, (float) 0, (double) 0);
      TSynchronizedObjectDoubleMap<Integer> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Integer>((TObjectDoubleMap<Integer>) tObjectDoubleCustomHashMap0);
      tSynchronizedObjectDoubleMap0.putIfAbsent((Integer) 0, (double) double0);
      assertEquals("{0=0.0}", tObjectDoubleCustomHashMap0.toString());
      assertEquals(false, tObjectDoubleCustomHashMap0.isEmpty());
  }

  //Test case number: 8
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.putAll(Lgnu/trove/map/TObjectDoubleMap;)V: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.forEachEntry(Lgnu/trove/procedure/TObjectDoubleProcedure;)Z: root-Branch
   */

  @Test
  public void test8()  throws Throwable  {
      TObjectDoubleHashMap<Object> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Object>();
      IdentityHashingStrategy<Integer> identityHashingStrategy0 = new IdentityHashingStrategy<Integer>();
      TObjectDoubleCustomHashMap<Integer> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Integer>((HashingStrategy<? super Integer>) identityHashingStrategy0, (-1025), (float) (-1025));
      TSynchronizedObjectDoubleMap<Integer> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Integer>((TObjectDoubleMap<Integer>) tObjectDoubleCustomHashMap0, (Object) tObjectDoubleHashMap0);
      tSynchronizedObjectDoubleMap0.putAll((TObjectDoubleMap<? extends Integer>) tSynchronizedObjectDoubleMap0);
      assertEquals(0, tSynchronizedObjectDoubleMap0.size());
  }

  //Test case number: 9
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.put(Ljava/lang/Object;D)D: root-Branch
   */

  @Test
  public void test9()  throws Throwable  {
      TObjectDoubleHashMap<Object> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Object>();
      IdentityHashingStrategy<Integer> identityHashingStrategy0 = new IdentityHashingStrategy<Integer>();
      TObjectDoubleCustomHashMap<Integer> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Integer>((HashingStrategy<? super Integer>) identityHashingStrategy0, (-1025), (float) (-1025));
      TSynchronizedObjectDoubleMap<Integer> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Integer>((TObjectDoubleMap<Integer>) tObjectDoubleCustomHashMap0, (Object) tObjectDoubleHashMap0);
      tSynchronizedObjectDoubleMap0.put((Integer) (-1025), (double) (-1025));
      assertEquals(false, tObjectDoubleCustomHashMap0.isEmpty());
      assertEquals("{-1025=-1025.0}", tObjectDoubleCustomHashMap0.toString());
  }

  //Test case number: 10
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.forEachKey(Lgnu/trove/procedure/TObjectProcedure;)Z: root-Branch
   */

  @Test
  public void test10()  throws Throwable  {
      TObjectDoubleHashMap<Double> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Double>();
      TSynchronizedObjectDoubleMap<Double> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Double>((TObjectDoubleMap<Double>) tObjectDoubleHashMap0);
      Double[] doubleArray0 = new Double[4];
      ToObjectArrayProceedure<Double> toObjectArrayProceedure0 = new ToObjectArrayProceedure<Double>(doubleArray0);
      boolean boolean0 = tSynchronizedObjectDoubleMap0.forEachKey((TObjectProcedure<? super Double>) toObjectArrayProceedure0);
      assertEquals(true, boolean0);
  }

  //Test case number: 11
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.size()I: root-Branch
   */

  @Test
  public void test11()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectDoubleCustomHashMap<Integer> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Integer>((HashingStrategy<? super Integer>) identityHashingStrategy0, (-808));
      TSynchronizedObjectDoubleMap<Integer> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Integer>((TObjectDoubleMap<Integer>) tObjectDoubleCustomHashMap0, (Object) "italiano");
      int int0 = tSynchronizedObjectDoubleMap0.size();
      assertEquals(0, int0);
  }

  //Test case number: 12
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.clear()V: root-Branch
   */

  @Test
  public void test12()  throws Throwable  {
      TObjectDoubleHashMap<Double> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Double>();
      TSynchronizedObjectDoubleMap<Double> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Double>((TObjectDoubleMap<Double>) tObjectDoubleHashMap0);
      tSynchronizedObjectDoubleMap0.clear();
      assertEquals(0, tSynchronizedObjectDoubleMap0.size());
  }

  //Test case number: 13
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.getNoEntryValue()D: root-Branch
   */

  @Test
  public void test13()  throws Throwable  {
      TObjectDoubleHashMap<Object> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Object>();
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleHashMap0);
      double double0 = tSynchronizedObjectDoubleMap0.getNoEntryValue();
      assertEquals(0.0, double0, 0.01D);
  }

  //Test case number: 14
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.adjustValue(Ljava/lang/Object;D)Z: root-Branch
   */

  @Test
  public void test14()  throws Throwable  {
      TObjectDoubleHashMap<Double> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Double>(0);
      TSynchronizedObjectDoubleMap<Double> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Double>((TObjectDoubleMap<Double>) tObjectDoubleHashMap0);
      Double double0 = new Double((double) 0);
      boolean boolean0 = tSynchronizedObjectDoubleMap0.adjustValue(double0, (double) 0);
      assertEquals(false, boolean0);
  }

  //Test case number: 15
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.values()[D: root-Branch
   */

  @Test
  public void test15()  throws Throwable  {
      TObjectDoubleHashMap<Object> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Object>();
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleHashMap0);
      double[] doubleArray0 = tSynchronizedObjectDoubleMap0.values();
      assertNotNull(doubleArray0);
  }

  //Test case number: 16
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.containsValue(D)Z: root-Branch
   */

  @Test
  public void test16()  throws Throwable  {
      TObjectDoubleHashMap<Object> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Object>();
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleHashMap0, (Object) "");
      boolean boolean0 = tSynchronizedObjectDoubleMap0.containsValue(4.066618419615873);
      assertEquals(false, boolean0);
  }

  //Test case number: 17
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.keys()[Ljava/lang/Object;: root-Branch
   */

  @Test
  public void test17()  throws Throwable  {
      TObjectDoubleHashMap<Object> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Object>();
      IdentityHashingStrategy<Integer> identityHashingStrategy0 = new IdentityHashingStrategy<Integer>();
      TObjectDoubleCustomHashMap<Integer> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Integer>((HashingStrategy<? super Integer>) identityHashingStrategy0, (-1025), (float) (-1025));
      TSynchronizedObjectDoubleMap<Integer> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Integer>((TObjectDoubleMap<Integer>) tObjectDoubleCustomHashMap0, (Object) tObjectDoubleHashMap0);
      Object[] objectArray0 = tSynchronizedObjectDoubleMap0.keys();
      assertNotNull(objectArray0);
  }

  //Test case number: 18
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.forEachValue(Lgnu/trove/procedure/TDoubleProcedure;)Z: root-Branch
   */

  @Test
  public void test18()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectDoubleCustomHashMap<Object> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Object>((HashingStrategy<? super Object>) identityHashingStrategy0, 0);
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleCustomHashMap0);
      boolean boolean0 = tSynchronizedObjectDoubleMap0.forEachValue((TDoubleProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 19
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.retainEntries(Lgnu/trove/procedure/TObjectDoubleProcedure;)Z: root-Branch
   */

  @Test
  public void test19()  throws Throwable  {
      TObjectDoubleHashMap<Double> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Double>(0);
      TSynchronizedObjectDoubleMap<Double> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Double>((TObjectDoubleMap<Double>) tObjectDoubleHashMap0);
      boolean boolean0 = tSynchronizedObjectDoubleMap0.retainEntries((TObjectDoubleProcedure<? super Double>) null);
      assertEquals(false, boolean0);
  }

  //Test case number: 20
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.putAll(Ljava/util/Map;)V: root-Branch
   */

  @Test
  public void test20()  throws Throwable  {
      TObjectDoubleHashMap<Object> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Object>();
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleHashMap0, (Object) "");
      HashMap<String, Double> hashMap0 = new HashMap<String, Double>();
      tSynchronizedObjectDoubleMap0.putAll((Map<?, ? extends Double>) hashMap0);
      assertEquals(0, hashMap0.size());
  }

  //Test case number: 21
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.keys([Ljava/lang/Object;)[Ljava/lang/Object;: root-Branch
   */

  @Test
  public void test21()  throws Throwable  {
      TObjectDoubleCustomHashMap<Integer> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Integer>((HashingStrategy<? super Integer>) null);
      TObjectDoubleCustomHashMap<Object> tObjectDoubleCustomHashMap1 = new TObjectDoubleCustomHashMap<Object>();
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleCustomHashMap1);
      Object[] objectArray0 = tSynchronizedObjectDoubleMap0.keys(tObjectDoubleCustomHashMap0._set);
      assertNotNull(objectArray0);
  }

  //Test case number: 22
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.transformValues(Lgnu/trove/function/TDoubleFunction;)V: root-Branch
   */

  @Test
  public void test22()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectDoubleCustomHashMap<Integer> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Integer>((HashingStrategy<? super Integer>) identityHashingStrategy0, (-1914));
      TSynchronizedObjectDoubleMap<Integer> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Integer>((TObjectDoubleMap<Integer>) tObjectDoubleCustomHashMap0, (Object) (-1914));
      // Undeclared exception!
      try {
        tSynchronizedObjectDoubleMap0.transformValues((TDoubleFunction) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 23
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.values([D)[D: root-Branch
   */

  @Test
  public void test23()  throws Throwable  {
      TObjectDoubleHashMap<Double> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Double>(0);
      TSynchronizedObjectDoubleMap<Double> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Double>((TObjectDoubleMap<Double>) tObjectDoubleHashMap0);
      double[] doubleArray0 = new double[1];
      double[] doubleArray1 = tSynchronizedObjectDoubleMap0.values(doubleArray0);
      assertSame(doubleArray0, doubleArray1);
  }

  //Test case number: 24
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.iterator()Lgnu/trove/iterator/TObjectDoubleIterator;: root-Branch
   */

  @Test
  public void test24()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectDoubleCustomHashMap<Object> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Object>((HashingStrategy<? super Object>) identityHashingStrategy0, 0);
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleCustomHashMap0);
      TObjectDoubleIterator<Object> tObjectDoubleIterator0 = tSynchronizedObjectDoubleMap0.iterator();
      assertEquals(false, tObjectDoubleIterator0.hasNext());
  }

  //Test case number: 25
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.adjustOrPutValue(Ljava/lang/Object;DD)D: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.<init>(Lgnu/trove/map/TObjectDoubleMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test25()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectDoubleCustomHashMap<Object> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Object>((HashingStrategy<? super Object>) identityHashingStrategy0, (-30), (float) (-30));
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleCustomHashMap0);
      double double0 = tSynchronizedObjectDoubleMap0.adjustOrPutValue((Object) "\na.equals(b) =", (double) (-30), (double) (-30));
      assertEquals(1, tObjectDoubleCustomHashMap0.size());
      assertEquals((-30.0), double0, 0.01D);
  }

  //Test case number: 26
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.remove(Ljava/lang/Object;)D: root-Branch
   */

  @Test
  public void test26()  throws Throwable  {
      TObjectDoubleHashMap<Object> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Object>();
      TSynchronizedObjectDoubleMap<Object> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Object>((TObjectDoubleMap<Object>) tObjectDoubleHashMap0, (Object) "");
      double double0 = tSynchronizedObjectDoubleMap0.remove((Object) "");
      assertEquals(0.0, double0, 0.01D);
  }

  //Test case number: 27
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.<init>(Lgnu/trove/map/TObjectDoubleMap;)V: I17 Branch 1 IFNONNULL L59 - false
   */

  @Test
  public void test27()  throws Throwable  {
      TSynchronizedObjectDoubleMap<Double> tSynchronizedObjectDoubleMap0 = null;
      try {
        tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Double>((TObjectDoubleMap<Double>) null);
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
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.keySet()Ljava/util/Set;: I11 Branch 2 IFNONNULL L107 - true
   * 2 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.keySet()Ljava/util/Set;: I11 Branch 2 IFNONNULL L107 - false
   */

  @Test
  public void test28()  throws Throwable  {
      TObjectDoubleCustomHashMap<Integer> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Integer>((HashingStrategy<? super Integer>) null);
      Object object0 = new Object();
      TSynchronizedObjectDoubleMap<Integer> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Integer>((TObjectDoubleMap<Integer>) tObjectDoubleCustomHashMap0, object0);
      Set<Integer> set0 = tSynchronizedObjectDoubleMap0.keySet();
      assertNotNull(set0);
      
      Set<Integer> set1 = tSynchronizedObjectDoubleMap0.keySet();
      assertSame(set1, set0);
  }

  //Test case number: 29
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.valueCollection()Lgnu/trove/TDoubleCollection;: I11 Branch 3 IFNONNULL L122 - true
   * 2 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.valueCollection()Lgnu/trove/TDoubleCollection;: I11 Branch 3 IFNONNULL L122 - false
   * 3 gnu.trove.impl.sync.TSynchronizedObjectDoubleMap.<init>(Lgnu/trove/map/TObjectDoubleMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test29()  throws Throwable  {
      TObjectDoubleHashMap<Object> tObjectDoubleHashMap0 = new TObjectDoubleHashMap<Object>();
      IdentityHashingStrategy<Integer> identityHashingStrategy0 = new IdentityHashingStrategy<Integer>();
      TObjectDoubleCustomHashMap<Integer> tObjectDoubleCustomHashMap0 = new TObjectDoubleCustomHashMap<Integer>((HashingStrategy<? super Integer>) identityHashingStrategy0, (-1025), (float) (-1025));
      TSynchronizedObjectDoubleMap<Integer> tSynchronizedObjectDoubleMap0 = new TSynchronizedObjectDoubleMap<Integer>((TObjectDoubleMap<Integer>) tObjectDoubleCustomHashMap0, (Object) tObjectDoubleHashMap0);
      TSynchronizedDoubleCollection tSynchronizedDoubleCollection0 = (TSynchronizedDoubleCollection)tSynchronizedObjectDoubleMap0.valueCollection();
      assertNotNull(tSynchronizedDoubleCollection0);
      
      TSynchronizedDoubleCollection tSynchronizedDoubleCollection1 = (TSynchronizedDoubleCollection)tSynchronizedObjectDoubleMap0.valueCollection();
      assertSame(tSynchronizedDoubleCollection1, tSynchronizedDoubleCollection0);
  }
}
