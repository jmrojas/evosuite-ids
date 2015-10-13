/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.unmodifiable;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TCharFunction;
import gnu.trove.impl.unmodifiable.TUnmodifiableCharCollection;
import gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap;
import gnu.trove.iterator.TObjectCharIterator;
import gnu.trove.map.TObjectCharMap;
import gnu.trove.map.custom_hash.TObjectCharCustomHashMap;
import gnu.trove.map.hash.TObjectCharHashMap;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TObjectCharProcedure;
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

public class TUnmodifiableObjectCharMapEvoSuite_Branch {

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
   * 3 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap$1.<init>(Lgnu/trove/impl/unmodifiable/TUnmodifiableObjectCharMap;)V: root-Branch
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.iterator()Lgnu/trove/iterator/TObjectCharIterator;: root-Branch
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.<init>(Lgnu/trove/map/TObjectCharMap;)V: I17 Branch 1 IFNONNULL L53 - true
   */

  @Test
  public void test0()  throws Throwable  {
      IdentityHashingStrategy<Character> identityHashingStrategy0 = new IdentityHashingStrategy<Character>();
      TObjectCharCustomHashMap<Character> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Character>((HashingStrategy<? super Character>) identityHashingStrategy0, (int) 'O');
      TObjectCharHashMap<Object> tObjectCharHashMap0 = new TObjectCharHashMap<Object>((TObjectCharMap<?>) tObjectCharCustomHashMap0);
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharHashMap0);
      TObjectCharIterator<Object> tObjectCharIterator0 = tUnmodifiableObjectCharMap0.iterator();
      assertEquals(false, tObjectCharIterator0.hasNext());
  }

  //Test case number: 1
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.putIfAbsent(Ljava/lang/Object;C)C: root-Branch
   */

  @Test
  public void test1()  throws Throwable  {
      TObjectCharHashMap<String> tObjectCharHashMap0 = new TObjectCharHashMap<String>();
      TUnmodifiableObjectCharMap<String> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<String>((TObjectCharMap<String>) tObjectCharHashMap0);
      // Undeclared exception!
      try {
        tUnmodifiableObjectCharMap0.putIfAbsent("{}", '\u0000');
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 2
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.values([C)[C: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      TObjectCharHashMap<String> tObjectCharHashMap0 = new TObjectCharHashMap<String>();
      TUnmodifiableObjectCharMap<String> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<String>((TObjectCharMap<String>) tObjectCharHashMap0);
      char[] charArray0 = new char[8];
      char[] charArray1 = tUnmodifiableObjectCharMap0.values(charArray0);
      assertSame(charArray0, charArray1);
  }

  //Test case number: 3
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.containsKey(Ljava/lang/Object;)Z: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectCharCustomHashMap<Integer> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Integer>((HashingStrategy<? super Integer>) identityHashingStrategy0);
      TUnmodifiableObjectCharMap<Integer> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Integer>((TObjectCharMap<Integer>) tObjectCharCustomHashMap0);
      boolean boolean0 = tUnmodifiableObjectCharMap0.containsKey((Object) "e");
      assertEquals(false, boolean0);
  }

  //Test case number: 4
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.forEachValue(Lgnu/trove/procedure/TCharProcedure;)Z: root-Branch
   */

  @Test
  public void test4()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectCharCustomHashMap<Integer> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Integer>((HashingStrategy<? super Integer>) identityHashingStrategy0);
      TUnmodifiableObjectCharMap<Integer> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Integer>((TObjectCharMap<Integer>) tObjectCharCustomHashMap0);
      boolean boolean0 = tUnmodifiableObjectCharMap0.forEachValue((TCharProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 5
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.increment(Ljava/lang/Object;)Z: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      Character character0 = Character.valueOf('K');
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectCharCustomHashMap<Object> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Object>((HashingStrategy<? super Object>) identityHashingStrategy0, (int) character0);
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharCustomHashMap0);
      // Undeclared exception!
      try {
        tUnmodifiableObjectCharMap0.increment((Object) "{}");
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 6
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.size()I: root-Branch
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.forEachEntry(Lgnu/trove/procedure/TObjectCharProcedure;)Z: root-Branch
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.getNoEntryValue()C: root-Branch
   */

  @Test
  public void test6()  throws Throwable  {
      IdentityHashingStrategy<Character> identityHashingStrategy0 = new IdentityHashingStrategy<Character>();
      TObjectCharCustomHashMap<Character> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Character>((HashingStrategy<? super Character>) identityHashingStrategy0, (int) 'O');
      TUnmodifiableObjectCharMap<Character> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Character>((TObjectCharMap<Character>) tObjectCharCustomHashMap0);
      TObjectCharHashMap<Character> tObjectCharHashMap0 = new TObjectCharHashMap<Character>((TObjectCharMap<? extends Character>) tUnmodifiableObjectCharMap0);
      assertEquals(true, tObjectCharHashMap0.isEmpty());
  }

  //Test case number: 7
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.retainEntries(Lgnu/trove/procedure/TObjectCharProcedure;)Z: root-Branch
   */

  @Test
  public void test7()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectCharCustomHashMap<Object> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Object>((HashingStrategy<? super Object>) identityHashingStrategy0, (-256), (float) (-256), 'L');
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharCustomHashMap0);
      // Undeclared exception!
      try {
        tUnmodifiableObjectCharMap0.retainEntries((TObjectCharProcedure<? super Object>) null);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 8
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.get(Ljava/lang/Object;)C: root-Branch
   */

  @Test
  public void test8()  throws Throwable  {
      TObjectCharHashMap<String> tObjectCharHashMap0 = new TObjectCharHashMap<String>();
      TUnmodifiableObjectCharMap<String> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<String>((TObjectCharMap<String>) tObjectCharHashMap0);
      char char0 = tUnmodifiableObjectCharMap0.get((Object) '?');
      assertEquals('\u0000', char0);
  }

  //Test case number: 9
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.keys([Ljava/lang/Object;)[Ljava/lang/Object;: root-Branch
   */

  @Test
  public void test9()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectCharCustomHashMap<String> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<String>((HashingStrategy<? super String>) identityHashingStrategy0);
      TUnmodifiableObjectCharMap<String> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<String>((TObjectCharMap<String>) tObjectCharCustomHashMap0);
      String[] stringArray0 = new String[11];
      String[] stringArray1 = tUnmodifiableObjectCharMap0.keys(stringArray0);
      assertSame(stringArray0, stringArray1);
  }

  //Test case number: 10
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.adjustOrPutValue(Ljava/lang/Object;CC)C: root-Branch
   */

  @Test
  public void test10()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectCharCustomHashMap<String> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<String>((HashingStrategy<? super String>) identityHashingStrategy0);
      TUnmodifiableObjectCharMap<String> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<String>((TObjectCharMap<String>) tObjectCharCustomHashMap0);
      // Undeclared exception!
      try {
        tUnmodifiableObjectCharMap0.adjustOrPutValue("e", 'e', 'e');
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 11
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.remove(Ljava/lang/Object;)C: root-Branch
   */

  @Test
  public void test11()  throws Throwable  {
      TObjectCharHashMap<Integer> tObjectCharHashMap0 = new TObjectCharHashMap<Integer>();
      TUnmodifiableObjectCharMap<Integer> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Integer>((TObjectCharMap<Integer>) tObjectCharHashMap0);
      // Undeclared exception!
      try {
        tUnmodifiableObjectCharMap0.remove((Object) tObjectCharHashMap0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 12
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.hashCode()I: root-Branch
   */

  @Test
  public void test12()  throws Throwable  {
      TObjectCharHashMap<Object> tObjectCharHashMap0 = new TObjectCharHashMap<Object>();
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharHashMap0);
      int int0 = tUnmodifiableObjectCharMap0.hashCode();
      assertEquals(0, int0);
  }

  //Test case number: 13
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.adjustValue(Ljava/lang/Object;C)Z: root-Branch
   */

  @Test
  public void test13()  throws Throwable  {
      TObjectCharCustomHashMap<Character> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Character>();
      TUnmodifiableObjectCharMap<Character> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Character>((TObjectCharMap<Character>) tObjectCharCustomHashMap0);
      // Undeclared exception!
      try {
        tUnmodifiableObjectCharMap0.adjustValue((Character) null, 'O');
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 14
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.keys()[Ljava/lang/Object;: root-Branch
   */

  @Test
  public void test14()  throws Throwable  {
      TObjectCharCustomHashMap<Character> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Character>();
      TUnmodifiableObjectCharMap<Character> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Character>((TObjectCharMap<Character>) tObjectCharCustomHashMap0);
      Object[] objectArray0 = tUnmodifiableObjectCharMap0.keys();
      assertNotNull(objectArray0);
  }

  //Test case number: 15
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.clear()V: root-Branch
   */

  @Test
  public void test15()  throws Throwable  {
      TObjectCharHashMap<Object> tObjectCharHashMap0 = new TObjectCharHashMap<Object>();
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharHashMap0);
      // Undeclared exception!
      try {
        tUnmodifiableObjectCharMap0.clear();
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 16
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.putAll(Ljava/util/Map;)V: root-Branch
   */

  @Test
  public void test16()  throws Throwable  {
      TObjectCharHashMap<Object> tObjectCharHashMap0 = new TObjectCharHashMap<Object>(5, (float) 5, 'q');
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharHashMap0);
      HashMap<Integer, Character> hashMap0 = new HashMap<Integer, Character>();
      // Undeclared exception!
      try {
        tUnmodifiableObjectCharMap0.putAll((Map<?, ? extends Character>) hashMap0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 17
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.transformValues(Lgnu/trove/function/TCharFunction;)V: root-Branch
   */

  @Test
  public void test17()  throws Throwable  {
      IdentityHashingStrategy<Integer> identityHashingStrategy0 = new IdentityHashingStrategy<Integer>();
      TObjectCharCustomHashMap<Integer> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Integer>((HashingStrategy<? super Integer>) identityHashingStrategy0, 896740170, (float) 896740170, 'n');
      TUnmodifiableObjectCharMap<Integer> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Integer>((TObjectCharMap<Integer>) tObjectCharCustomHashMap0);
      // Undeclared exception!
      try {
        tUnmodifiableObjectCharMap0.transformValues((TCharFunction) null);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 18
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.put(Ljava/lang/Object;C)C: root-Branch
   */

  @Test
  public void test18()  throws Throwable  {
      TObjectCharHashMap<Object> tObjectCharHashMap0 = new TObjectCharHashMap<Object>((int) 'h', (float) 'h', 'h');
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharHashMap0);
      // Undeclared exception!
      try {
        tUnmodifiableObjectCharMap0.put((Object) "{}", 'h');
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 19
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.putAll(Lgnu/trove/map/TObjectCharMap;)V: root-Branch
   */

  @Test
  public void test19()  throws Throwable  {
      TObjectCharCustomHashMap<Integer> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Integer>();
      TUnmodifiableObjectCharMap<Integer> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Integer>((TObjectCharMap<Integer>) tObjectCharCustomHashMap0);
      // Undeclared exception!
      try {
        tUnmodifiableObjectCharMap0.putAll((TObjectCharMap<? extends Integer>) tObjectCharCustomHashMap0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 20
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.values()[C: root-Branch
   */

  @Test
  public void test20()  throws Throwable  {
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectCharCustomHashMap<Object> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Object>((HashingStrategy<? super Object>) identityHashingStrategy0, (-256), (float) (-256), 'L');
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharCustomHashMap0);
      char[] charArray0 = tUnmodifiableObjectCharMap0.values();
      assertNotNull(charArray0);
  }

  //Test case number: 21
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.forEachKey(Lgnu/trove/procedure/TObjectProcedure;)Z: root-Branch
   */

  @Test
  public void test21()  throws Throwable  {
      TObjectCharCustomHashMap<Character> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Character>();
      TUnmodifiableObjectCharMap<Character> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Character>((TObjectCharMap<Character>) tObjectCharCustomHashMap0);
      ToObjectArrayProceedure<Object> toObjectArrayProceedure0 = new ToObjectArrayProceedure<Object>(tObjectCharCustomHashMap0._set);
      boolean boolean0 = tUnmodifiableObjectCharMap0.forEachKey((TObjectProcedure<? super Character>) toObjectArrayProceedure0);
      assertEquals(true, boolean0);
  }

  //Test case number: 22
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.toString()Ljava/lang/String;: root-Branch
   */

  @Test
  public void test22()  throws Throwable  {
      Character character0 = Character.valueOf('K');
      IdentityHashingStrategy<Object> identityHashingStrategy0 = new IdentityHashingStrategy<Object>();
      TObjectCharCustomHashMap<Object> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Object>((HashingStrategy<? super Object>) identityHashingStrategy0, (int) character0);
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharCustomHashMap0);
      String string0 = tUnmodifiableObjectCharMap0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 23
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.isEmpty()Z: root-Branch
   */

  @Test
  public void test23()  throws Throwable  {
      TObjectCharHashMap<Object> tObjectCharHashMap0 = new TObjectCharHashMap<Object>(5, (float) 5, 'q');
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharHashMap0);
      boolean boolean0 = tUnmodifiableObjectCharMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 24
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.containsValue(C)Z: root-Branch
   */

  @Test
  public void test24()  throws Throwable  {
      TObjectCharCustomHashMap<Character> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Character>();
      TUnmodifiableObjectCharMap<Character> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Character>((TObjectCharMap<Character>) tObjectCharCustomHashMap0);
      boolean boolean0 = tUnmodifiableObjectCharMap0.containsValue('K');
      assertEquals(false, boolean0);
  }

  //Test case number: 25
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.<init>(Lgnu/trove/map/TObjectCharMap;)V: I17 Branch 1 IFNONNULL L53 - false
   */

  @Test
  public void test25()  throws Throwable  {
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = null;
      try {
        tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 26
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.keySet()Ljava/util/Set;: I4 Branch 2 IFNONNULL L74 - true
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.keySet()Ljava/util/Set;: I4 Branch 2 IFNONNULL L74 - false
   */

  @Test
  public void test26()  throws Throwable  {
      TObjectCharHashMap<Object> tObjectCharHashMap0 = new TObjectCharHashMap<Object>();
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharHashMap0);
      Set<Object> set0 = tUnmodifiableObjectCharMap0.keySet();
      assertNotNull(set0);
      
      Set<Object> set1 = tUnmodifiableObjectCharMap0.keySet();
      assertSame(set1, set0);
  }

  //Test case number: 27
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.valueCollection()Lgnu/trove/TCharCollection;: I4 Branch 3 IFNONNULL L82 - true
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.valueCollection()Lgnu/trove/TCharCollection;: I4 Branch 3 IFNONNULL L82 - false
   */

  @Test
  public void test27()  throws Throwable  {
      TObjectCharHashMap<Object> tObjectCharHashMap0 = new TObjectCharHashMap<Object>();
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharHashMap0);
      TUnmodifiableCharCollection tUnmodifiableCharCollection0 = (TUnmodifiableCharCollection)tUnmodifiableObjectCharMap0.valueCollection();
      assertNotNull(tUnmodifiableCharCollection0);
      
      TUnmodifiableCharCollection tUnmodifiableCharCollection1 = (TUnmodifiableCharCollection)tUnmodifiableObjectCharMap0.valueCollection();
      assertSame(tUnmodifiableCharCollection1, tUnmodifiableCharCollection0);
  }

  //Test case number: 28
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.equals(Ljava/lang/Object;)Z: I4 Branch 4 IF_ACMPEQ L89 - true
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.equals(Ljava/lang/Object;)Z: I9 Branch 5 IFEQ L89 - false
   */

  @Test
  public void test28()  throws Throwable  {
      TObjectCharCustomHashMap<Character> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Character>();
      TUnmodifiableObjectCharMap<Character> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Character>((TObjectCharMap<Character>) tObjectCharCustomHashMap0);
      boolean boolean0 = tUnmodifiableObjectCharMap0.equals((Object) tObjectCharCustomHashMap0);
      assertEquals(true, boolean0);
  }

  //Test case number: 29
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.equals(Ljava/lang/Object;)Z: I4 Branch 4 IF_ACMPEQ L89 - false
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.equals(Ljava/lang/Object;)Z: I9 Branch 5 IFEQ L89 - true
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap.<init>(Lgnu/trove/map/TObjectCharMap;)V: I17 Branch 1 IFNONNULL L53 - true
   */

  @Test
  public void test29()  throws Throwable  {
      IdentityHashingStrategy<Character> identityHashingStrategy0 = new IdentityHashingStrategy<Character>();
      TObjectCharCustomHashMap<Character> tObjectCharCustomHashMap0 = new TObjectCharCustomHashMap<Character>((HashingStrategy<? super Character>) identityHashingStrategy0, (int) 'O');
      TObjectCharHashMap<Object> tObjectCharHashMap0 = new TObjectCharHashMap<Object>((TObjectCharMap<?>) tObjectCharCustomHashMap0);
      TUnmodifiableObjectCharMap<Object> tUnmodifiableObjectCharMap0 = new TUnmodifiableObjectCharMap<Object>((TObjectCharMap<Object>) tObjectCharHashMap0);
      boolean boolean0 = tUnmodifiableObjectCharMap0.equals((Object) 'O');
      assertEquals(false, boolean0);
  }
}
