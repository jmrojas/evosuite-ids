/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.sync;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TCharFunction;
import gnu.trove.impl.sync.TSynchronizedCharCharMap;
import gnu.trove.impl.sync.TSynchronizedCharCollection;
import gnu.trove.impl.sync.TSynchronizedCharSet;
import gnu.trove.iterator.TCharCharIterator;
import gnu.trove.map.TCharCharMap;
import gnu.trove.map.hash.TCharCharHashMap;
import gnu.trove.procedure.TCharCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TSynchronizedCharCharMapEvoSuite_Branch {

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
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.put(CC)C: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedCharCharMap.<init>(Lgnu/trove/map/TCharCharMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test0()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap();
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      tSynchronizedCharCharMap0.put('G', 'G');
      assertEquals(1, tCharCharHashMap0.size());
      assertEquals("{G=G}", tCharCharHashMap0.toString());
  }

  //Test case number: 1
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.values([C)[C: root-Branch
   */

  @Test
  public void test1()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap();
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      char[] charArray0 = tSynchronizedCharCharMap0.values(tCharCharHashMap0._set);
      assertNotNull(charArray0);
  }

  //Test case number: 2
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.retainEntries(Lgnu/trove/procedure/TCharCharProcedure;)Z: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap((-939));
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      boolean boolean0 = tSynchronizedCharCharMap0.retainEntries((TCharCharProcedure) null);
      assertEquals(false, boolean0);
  }

  //Test case number: 3
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.iterator()Lgnu/trove/iterator/TCharCharIterator;: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedCharCharMap.<init>(Lgnu/trove/map/TCharCharMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap();
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0, (Object) "");
      TCharCharIterator tCharCharIterator0 = tSynchronizedCharCharMap0.iterator();
      assertEquals(false, tCharCharIterator0.hasNext());
  }

  //Test case number: 4
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.clear()V: root-Branch
   */

  @Test
  public void test4()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap();
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0, (Object) "");
      tSynchronizedCharCharMap0.clear();
      assertEquals(0, tSynchronizedCharCharMap0.size());
  }

  //Test case number: 5
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.equals(Ljava/lang/Object;)Z: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(2147483639, 2147483639);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      boolean boolean0 = tSynchronizedCharCharMap0.equals((Object) tCharCharHashMap0);
      assertEquals(true, boolean0);
  }

  //Test case number: 6
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.increment(C)Z: root-Branch
   */

  @Test
  public void test6()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap();
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      boolean boolean0 = tSynchronizedCharCharMap0.increment('\u0000');
      assertEquals(false, boolean0);
  }

  //Test case number: 7
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.keys([C)[C: root-Branch
   */

  @Test
  public void test7()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(2147483639, 2147483639);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      char[] charArray0 = tSynchronizedCharCharMap0.keys(tCharCharHashMap0._set);
      assertNotNull(charArray0);
  }

  //Test case number: 8
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.putAll(Ljava/util/Map;)V: root-Branch
   */

  @Test
  public void test8()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap();
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0, (Object) "");
      HashMap<Character, Character> hashMap0 = new HashMap<Character, Character>();
      tSynchronizedCharCharMap0.putAll((Map<? extends Character, ? extends Character>) hashMap0);
      assertEquals(0, hashMap0.size());
  }

  //Test case number: 9
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.forEachValue(Lgnu/trove/procedure/TCharProcedure;)Z: root-Branch
   */

  @Test
  public void test9()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(488);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      boolean boolean0 = tSynchronizedCharCharMap0.forEachValue((TCharProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 10
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.remove(C)C: root-Branch
   */

  @Test
  public void test10()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap((-939));
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      char char0 = tSynchronizedCharCharMap0.remove(' ');
      assertEquals('\u0000', char0);
  }

  //Test case number: 11
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.getNoEntryValue()C: root-Branch
   */

  @Test
  public void test11()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(199);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      char char0 = tSynchronizedCharCharMap0.getNoEntryValue();
      assertEquals('\u0000', char0);
  }

  //Test case number: 12
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.containsValue(C)Z: root-Branch
   */

  @Test
  public void test12()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap();
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      boolean boolean0 = tSynchronizedCharCharMap0.containsValue('\u0010');
      assertEquals(false, boolean0);
  }

  //Test case number: 13
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.containsKey(C)Z: root-Branch
   */

  @Test
  public void test13()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(2147483639, 2147483639);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      boolean boolean0 = tSynchronizedCharCharMap0.containsKey('\u0000');
      assertEquals(false, boolean0);
  }

  //Test case number: 14
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.forEachEntry(Lgnu/trove/procedure/TCharCharProcedure;)Z: root-Branch
   */

  @Test
  public void test14()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap();
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0, (Object) "");
      boolean boolean0 = tSynchronizedCharCharMap0.forEachEntry((TCharCharProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 15
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.getNoEntryKey()C: root-Branch
   */

  @Test
  public void test15()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(2147483639, 2147483639);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      char char0 = tSynchronizedCharCharMap0.getNoEntryKey();
      assertEquals('\u0000', char0);
  }

  //Test case number: 16
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.adjustOrPutValue(CCC)C: root-Branch
   */

  @Test
  public void test16()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(199);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      char char0 = tSynchronizedCharCharMap0.adjustOrPutValue('/', '/', '/');
      assertEquals(1, tCharCharHashMap0.size());
      assertEquals('/', char0);
  }

  //Test case number: 17
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.isEmpty()Z: root-Branch
   */

  @Test
  public void test17()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap((-939));
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      boolean boolean0 = tSynchronizedCharCharMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 18
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.get(C)C: root-Branch
   */

  @Test
  public void test18()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap();
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      char char0 = tSynchronizedCharCharMap0.get('G');
      assertEquals('\u0000', char0);
  }

  //Test case number: 19
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.toString()Ljava/lang/String;: root-Branch
   */

  @Test
  public void test19()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(199);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      String string0 = tSynchronizedCharCharMap0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 20
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.forEachKey(Lgnu/trove/procedure/TCharProcedure;)Z: root-Branch
   */

  @Test
  public void test20()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(199);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      boolean boolean0 = tSynchronizedCharCharMap0.forEachKey((TCharProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 21
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.transformValues(Lgnu/trove/function/TCharFunction;)V: root-Branch
   */

  @Test
  public void test21()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(488);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      tSynchronizedCharCharMap0.transformValues((TCharFunction) null);
      assertEquals("{}", tSynchronizedCharCharMap0.toString());
  }

  //Test case number: 22
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.putIfAbsent(CC)C: root-Branch
   */

  @Test
  public void test22()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap();
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      tSynchronizedCharCharMap0.putIfAbsent('\u0010', '\u0010');
      assertEquals(1, tCharCharHashMap0.size());
      assertEquals("{\u0010=\u0010}", tSynchronizedCharCharMap0.toString());
  }

  //Test case number: 23
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.adjustValue(CC)Z: root-Branch
   * 2 gnu.trove.impl.sync.TSynchronizedCharCharMap.<init>(Lgnu/trove/map/TCharCharMap;Ljava/lang/Object;)V: root-Branch
   */

  @Test
  public void test23()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap();
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0, (Object) "");
      boolean boolean0 = tSynchronizedCharCharMap0.adjustValue('g', 'g');
      assertEquals(false, boolean0);
  }

  //Test case number: 24
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.values()[C: root-Branch
   */

  @Test
  public void test24()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(2147483639, 2147483639);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      char[] charArray0 = tSynchronizedCharCharMap0.values();
      assertNotNull(charArray0);
  }

  //Test case number: 25
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.keys()[C: root-Branch
   */

  @Test
  public void test25()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(199);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      char[] charArray0 = tSynchronizedCharCharMap0.keys();
      assertNotNull(charArray0);
  }

  //Test case number: 26
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.hashCode()I: root-Branch
   */

  @Test
  public void test26()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(2147483639, 2147483639);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      int int0 = tSynchronizedCharCharMap0.hashCode();
      assertEquals(0, int0);
  }

  //Test case number: 27
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.putAll(Lgnu/trove/map/TCharCharMap;)V: root-Branch
   */

  @Test
  public void test27()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(488);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      tSynchronizedCharCharMap0.putAll((TCharCharMap) tCharCharHashMap0);
      assertEquals(true, tSynchronizedCharCharMap0.isEmpty());
  }

  //Test case number: 28
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.size()I: root-Branch
   */

  @Test
  public void test28()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(488);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      int int0 = tSynchronizedCharCharMap0.size();
      assertEquals(0, int0);
  }

  //Test case number: 29
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.<init>(Lgnu/trove/map/TCharCharMap;)V: I17 Branch 1 IFNONNULL L59 - false
   */

  @Test
  public void test29()  throws Throwable  {
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = null;
      try {
        tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) null);
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
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.keySet()Lgnu/trove/set/TCharSet;: I11 Branch 2 IFNONNULL L107 - true
   * 2 gnu.trove.impl.sync.TSynchronizedCharCharMap.keySet()Lgnu/trove/set/TCharSet;: I11 Branch 2 IFNONNULL L107 - false
   */

  @Test
  public void test30()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(3);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      TSynchronizedCharSet tSynchronizedCharSet0 = (TSynchronizedCharSet)tSynchronizedCharCharMap0.keySet();
      assertNotNull(tSynchronizedCharSet0);
      
      TSynchronizedCharSet tSynchronizedCharSet1 = (TSynchronizedCharSet)tSynchronizedCharCharMap0.keySet();
      assertSame(tSynchronizedCharSet1, tSynchronizedCharSet0);
  }

  //Test case number: 31
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.sync.TSynchronizedCharCharMap.valueCollection()Lgnu/trove/TCharCollection;: I11 Branch 3 IFNONNULL L121 - true
   * 2 gnu.trove.impl.sync.TSynchronizedCharCharMap.valueCollection()Lgnu/trove/TCharCollection;: I11 Branch 3 IFNONNULL L121 - false
   * 3 gnu.trove.impl.sync.TSynchronizedCharCharMap.<init>(Lgnu/trove/map/TCharCharMap;)V: I17 Branch 1 IFNONNULL L59 - true
   */

  @Test
  public void test31()  throws Throwable  {
      TCharCharHashMap tCharCharHashMap0 = new TCharCharHashMap(2147483639, 2147483639);
      TSynchronizedCharCharMap tSynchronizedCharCharMap0 = new TSynchronizedCharCharMap((TCharCharMap) tCharCharHashMap0);
      TSynchronizedCharCollection tSynchronizedCharCollection0 = (TSynchronizedCharCollection)tSynchronizedCharCharMap0.valueCollection();
      assertNotNull(tSynchronizedCharCollection0);
      
      TSynchronizedCharCollection tSynchronizedCharCollection1 = (TSynchronizedCharCollection)tSynchronizedCharCharMap0.valueCollection();
      assertSame(tSynchronizedCharCollection1, tSynchronizedCharCollection0);
  }
}
