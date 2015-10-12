/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.unmodifiable;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap;
import gnu.trove.impl.unmodifiable.TUnmodifiableCharSet;
import gnu.trove.map.TCharObjectMap;
import gnu.trove.map.hash.TCharObjectHashMap;
import gnu.trove.procedure.TCharObjectProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.array.ToObjectArrayProceedure;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TUnmodifiableCharObjectMapEvoSuite_StrongMutation {

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
   * 11 covered goals:
   * 1 Strong Mutation 10: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.size()I:58 - DeleteStatement: size()I
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keySet()Lgnu/trove/set/TCharSet;: I4 Branch 2 IFNONNULL L74 - false
   * 3 Strong Mutation 8: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.<init>(Lgnu/trove/map/TCharObjectMap;)V:53 - ReplaceComparisonOperator != null -> = null
   * 4 Strong Mutation 10: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.size()I:58 - DeleteStatement: size()I
   * 5 Strong Mutation 27: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keySet()Lgnu/trove/set/TCharSet;:75 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 6 Strong Mutation 26: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keySet()Lgnu/trove/set/TCharSet;:74 - ReplaceComparisonOperator != null -> = null
   * 7 Strong Mutation 29: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keySet()Lgnu/trove/set/TCharSet;:75 - DeleteStatement: unmodifiableSet(Lgnu/trove/set/TCharSet;)Lgnu/trove/set/TCharSet;
   * 8 Strong Mutation 28: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keySet()Lgnu/trove/set/TCharSet;:75 - DeleteStatement: keySet()Lgnu/trove/set/TCharSet;
   * 9 Strong Mutation 30: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keySet()Lgnu/trove/set/TCharSet;:76 - DeleteField: keySetLgnu/trove/set/TCharSet;
   * 10 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.size()I: root-Branch
   * 11 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.<init>(Lgnu/trove/map/TCharObjectMap;)V: I17 Branch 1 IFNONNULL L53 - true
   */

  @Test
  public void test0()  throws Throwable  {
      TCharObjectHashMap<Integer> tCharObjectHashMap0 = new TCharObjectHashMap<Integer>((-72));
      TCharObjectHashMap<Object> tCharObjectHashMap1 = new TCharObjectHashMap<Object>();
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap1);
      TUnmodifiableCharObjectMap<Integer> tUnmodifiableCharObjectMap1 = new TUnmodifiableCharObjectMap<Integer>((TCharObjectMap<Integer>) tCharObjectHashMap0);
      tUnmodifiableCharObjectMap0.size();
      tCharObjectHashMap0.putIfAbsent('#', (Integer) (-72));
      tUnmodifiableCharObjectMap1.keySet();
      assertEquals(1, tUnmodifiableCharObjectMap1.size());
  }

  //Test case number: 1
  /*
   * 4 covered goals:
   * 1 Strong Mutation 11: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.isEmpty()Z:59 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.isEmpty()Z: root-Branch
   * 3 Strong Mutation 11: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.isEmpty()Z:59 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 4 Strong Mutation 12: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.isEmpty()Z:59 - DeleteStatement: isEmpty()Z
   */

  @Test
  public void test1()  throws Throwable  {
      TCharObjectHashMap<Object> tCharObjectHashMap0 = new TCharObjectHashMap<Object>();
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap0);
      Object[] objectArray0 = new Object[3];
      objectArray0[0] = (Object) (-72);
      tUnmodifiableCharObjectMap0.isEmpty();
      objectArray0[0] = (Object) true;
  }

  //Test case number: 2
  /*
   * 1 covered goal:
   * 1 Strong Mutation 12: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.isEmpty()Z:59 - DeleteStatement: isEmpty()Z
   */

  @Test
  public void test2()  throws Throwable  {
      TCharObjectHashMap<Integer> tCharObjectHashMap0 = new TCharObjectHashMap<Integer>((-802));
      TUnmodifiableCharObjectMap<Integer> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Integer>((TCharObjectMap<Integer>) tCharObjectHashMap0);
      boolean boolean0 = tUnmodifiableCharObjectMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 3
  /*
   * 3 covered goals:
   * 1 Strong Mutation 13: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsKey(C)Z:60 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsKey(C)Z: root-Branch
   * 3 Strong Mutation 13: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsKey(C)Z:60 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   */

  @Test
  public void test3()  throws Throwable  {
      TCharObjectHashMap<Integer> tCharObjectHashMap0 = new TCharObjectHashMap<Integer>((-72));
      Object[] objectArray0 = new Object[3];
      objectArray0[1] = (Object) (-72);
      TUnmodifiableCharObjectMap<Integer> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Integer>((TCharObjectMap<Integer>) tCharObjectHashMap0);
      tUnmodifiableCharObjectMap0.containsKey(';');
      objectArray0[1] = (Object) true;
  }

  //Test case number: 4
  /*
   * 8 covered goals:
   * 1 Strong Mutation 16: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsKey(C)Z:60 - InsertUnaryOp IINC -1 key
   * 2 Strong Mutation 14: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsKey(C)Z:60 - InsertUnaryOp Negation of key
   * 3 Strong Mutation 15: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsKey(C)Z:60 - InsertUnaryOp IINC 1 key
   * 4 Strong Mutation 17: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsKey(C)Z:60 - DeleteStatement: containsKey(C)Z
   * 5 Strong Mutation 14: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsKey(C)Z:60 - InsertUnaryOp Negation of key
   * 6 Strong Mutation 15: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsKey(C)Z:60 - InsertUnaryOp IINC 1 key
   * 7 Strong Mutation 17: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsKey(C)Z:60 - DeleteStatement: containsKey(C)Z
   * 8 Strong Mutation 16: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsKey(C)Z:60 - InsertUnaryOp IINC -1 key
   */

  @Test
  public void test4()  throws Throwable  {
      TCharObjectHashMap<Object> tCharObjectHashMap0 = new TCharObjectHashMap<Object>(0, 0);
      Character character0 = new Character('b');
      tCharObjectHashMap0.put('b', (Object) "b");
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap0);
      boolean boolean0 = tUnmodifiableCharObjectMap0.containsKey((char) character0);
      assertEquals(true, boolean0);
  }

  //Test case number: 5
  /*
   * 3 covered goals:
   * 1 Strong Mutation 19: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsValue(Ljava/lang/Object;)Z:61 - DeleteStatement: containsValue(Ljava/lang/Object;)Z
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsValue(Ljava/lang/Object;)Z: root-Branch
   * 3 Strong Mutation 19: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsValue(Ljava/lang/Object;)Z:61 - DeleteStatement: containsValue(Ljava/lang/Object;)Z
   */

  @Test
  public void test5()  throws Throwable  {
      TCharObjectHashMap<String> tCharObjectHashMap0 = new TCharObjectHashMap<String>(1313, 1598.2618F);
      TUnmodifiableCharObjectMap<String> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<String>((TCharObjectMap<String>) tCharObjectHashMap0);
      tCharObjectHashMap0.putIfAbsent('-', "");
      boolean boolean0 = tUnmodifiableCharObjectMap0.containsValue((Object) "");
      assertEquals(true, boolean0);
  }

  //Test case number: 6
  /*
   * 2 covered goals:
   * 1 Strong Mutation 18: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsValue(Ljava/lang/Object;)Z:61 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 2 Strong Mutation 18: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.containsValue(Ljava/lang/Object;)Z:61 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   */

  @Test
  public void test6()  throws Throwable  {
      TCharObjectHashMap<Object> tCharObjectHashMap0 = new TCharObjectHashMap<Object>();
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap0);
      Object[] objectArray0 = new Object[3];
      objectArray0[1] = (Object) (-72);
      tUnmodifiableCharObjectMap0.containsValue(objectArray0[2]);
      objectArray0[1] = (Object) true;
  }

  //Test case number: 7
  /*
   * 3 covered goals:
   * 1 Strong Mutation 20: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.get(C)Ljava/lang/Object;:62 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 2 Strong Mutation 20: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.get(C)Ljava/lang/Object;:62 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.get(C)Ljava/lang/Object;: root-Branch
   */

  @Test
  public void test7()  throws Throwable  {
      TCharObjectHashMap<Integer> tCharObjectHashMap0 = new TCharObjectHashMap<Integer>((-72));
      Object[] objectArray0 = new Object[3];
      objectArray0[0] = (Object) (-72);
      TUnmodifiableCharObjectMap<Integer> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Integer>((TCharObjectMap<Integer>) tCharObjectHashMap0);
      tUnmodifiableCharObjectMap0.get('C');
      objectArray0[0] = (Object) true;
  }

  //Test case number: 8
  /*
   * 8 covered goals:
   * 1 Strong Mutation 24: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.get(C)Ljava/lang/Object;:62 - DeleteStatement: get(C)Ljava/lang/Object;
   * 2 Strong Mutation 21: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.get(C)Ljava/lang/Object;:62 - InsertUnaryOp Negation of key
   * 3 Strong Mutation 23: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.get(C)Ljava/lang/Object;:62 - InsertUnaryOp IINC -1 key
   * 4 Strong Mutation 22: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.get(C)Ljava/lang/Object;:62 - InsertUnaryOp IINC 1 key
   * 5 Strong Mutation 21: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.get(C)Ljava/lang/Object;:62 - InsertUnaryOp Negation of key
   * 6 Strong Mutation 23: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.get(C)Ljava/lang/Object;:62 - InsertUnaryOp IINC -1 key
   * 7 Strong Mutation 22: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.get(C)Ljava/lang/Object;:62 - InsertUnaryOp IINC 1 key
   * 8 Strong Mutation 24: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.get(C)Ljava/lang/Object;:62 - DeleteStatement: get(C)Ljava/lang/Object;
   */

  @Test
  public void test8()  throws Throwable  {
      TCharObjectHashMap<Integer> tCharObjectHashMap0 = new TCharObjectHashMap<Integer>((-72));
      TUnmodifiableCharObjectMap<Integer> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Integer>((TCharObjectMap<Integer>) tCharObjectHashMap0);
      TUnmodifiableCharObjectMap<Integer> tUnmodifiableCharObjectMap1 = new TUnmodifiableCharObjectMap<Integer>((TCharObjectMap<Integer>) tCharObjectHashMap0);
      tUnmodifiableCharObjectMap0.get('C');
      tCharObjectHashMap0.putIfAbsent('#', (Integer) (-72));
      TUnmodifiableCharSet tUnmodifiableCharSet0 = (TUnmodifiableCharSet)tUnmodifiableCharObjectMap0.keySet();
      assertNotNull(tUnmodifiableCharSet0);
      assertTrue(tUnmodifiableCharObjectMap0.equals(tUnmodifiableCharObjectMap1));
  }

  //Test case number: 9
  /*
   * 5 covered goals:
   * 1 Strong Mutation 32: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keys()[C:78 - DeleteStatement: keys()[C
   * 2 Strong Mutation 31: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keys()[C:78 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 3 Strong Mutation 31: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keys()[C:78 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 4 Strong Mutation 32: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keys()[C:78 - DeleteStatement: keys()[C
   * 5 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keys()[C: root-Branch
   */

  @Test
  public void test9()  throws Throwable  {
      TCharObjectHashMap<String> tCharObjectHashMap0 = new TCharObjectHashMap<String>(867, 288.16745F);
      TUnmodifiableCharObjectMap<String> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<String>((TCharObjectMap<String>) tCharObjectHashMap0);
      char[] charArray0 = tUnmodifiableCharObjectMap0.keys();
      assertNotNull(charArray0);
  }

  //Test case number: 10
  /*
   * 5 covered goals:
   * 1 Strong Mutation 33: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keys([C)[C:79 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 2 Strong Mutation 34: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keys([C)[C:79 - DeleteStatement: keys([C)[C
   * 3 Strong Mutation 34: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keys([C)[C:79 - DeleteStatement: keys([C)[C
   * 4 Strong Mutation 33: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keys([C)[C:79 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 5 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keys([C)[C: root-Branch
   */

  @Test
  public void test10()  throws Throwable  {
      TCharObjectHashMap<Object> tCharObjectHashMap0 = new TCharObjectHashMap<Object>();
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap0);
      char[] charArray0 = tUnmodifiableCharObjectMap0.keys(tCharObjectHashMap0._set);
      assertNotNull(charArray0);
  }

  //Test case number: 11
  /*
   * 5 covered goals:
   * 1 Strong Mutation 41: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.values()[Ljava/lang/Object;:86 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 2 Strong Mutation 42: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.values()[Ljava/lang/Object;:86 - DeleteStatement: values()[Ljava/lang/Object;
   * 3 Strong Mutation 42: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.values()[Ljava/lang/Object;:86 - DeleteStatement: values()[Ljava/lang/Object;
   * 4 Strong Mutation 41: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.values()[Ljava/lang/Object;:86 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 5 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.values()[Ljava/lang/Object;: root-Branch
   */

//   @Test
//   public void test11()  throws Throwable  {
//       TCharObjectHashMap<Character> tCharObjectHashMap0 = new TCharObjectHashMap<Character>(675, 989.039F);
//       TUnmodifiableCharObjectMap<Character> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Character>((TCharObjectMap<Character>) tCharObjectHashMap0);
//       Character[] characterArray0 = tUnmodifiableCharObjectMap0.values();
//       assertNotNull(characterArray0);
//   }

  //Test case number: 12
  /*
   * 5 covered goals:
   * 1 Strong Mutation 44: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.values([Ljava/lang/Object;)[Ljava/lang/Object;:87 - DeleteStatement: values([Ljava/lang/Object;)[Ljava/lang/Object;
   * 2 Strong Mutation 43: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.values([Ljava/lang/Object;)[Ljava/lang/Object;:87 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 3 Strong Mutation 43: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.values([Ljava/lang/Object;)[Ljava/lang/Object;:87 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 4 Strong Mutation 44: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.values([Ljava/lang/Object;)[Ljava/lang/Object;:87 - DeleteStatement: values([Ljava/lang/Object;)[Ljava/lang/Object;
   * 5 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.values([Ljava/lang/Object;)[Ljava/lang/Object;: root-Branch
   */

  @Test
  public void test12()  throws Throwable  {
      TCharObjectHashMap<Integer> tCharObjectHashMap0 = new TCharObjectHashMap<Integer>((-802));
      TCharObjectHashMap<Object> tCharObjectHashMap1 = new TCharObjectHashMap<Object>((TCharObjectMap<?>) tCharObjectHashMap0);
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap1);
      Object[] objectArray0 = tCharObjectHashMap1.values();
      Object[] objectArray1 = tUnmodifiableCharObjectMap0.values(objectArray0);
      assertSame(objectArray0, objectArray1);
  }

  //Test case number: 13
  /*
   * 8 covered goals:
   * 1 Strong Mutation 48: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z:89 - ReplaceComparisonOperator == -> !=
   * 2 Strong Mutation 45: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z:89 - ReplaceComparisonOperator == -> !=
   * 3 Strong Mutation 50: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z:89 - ReplaceConstant - 0 -> 1
   * 4 Strong Mutation 45: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z:89 - ReplaceComparisonOperator == -> !=
   * 5 Strong Mutation 50: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z:89 - ReplaceConstant - 0 -> 1
   * 6 Strong Mutation 48: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z:89 - ReplaceComparisonOperator == -> !=
   * 7 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z: I4 Branch 4 IF_ACMPEQ L89 - false
   * 8 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z: I9 Branch 5 IFEQ L89 - true
   */

  @Test
  public void test13()  throws Throwable  {
      TCharObjectHashMap<Integer> tCharObjectHashMap0 = new TCharObjectHashMap<Integer>((-802));
      TCharObjectHashMap<Object> tCharObjectHashMap1 = new TCharObjectHashMap<Object>((TCharObjectMap<?>) tCharObjectHashMap0);
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap1);
      boolean boolean0 = tUnmodifiableCharObjectMap0.equals((Object) "end index > size: ");
      assertEquals(false, boolean0);
  }

  //Test case number: 14
  /*
   * 3 covered goals:
   * 1 Strong Mutation 52: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.hashCode()I:90 - DeleteStatement: hashCode()I
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.hashCode()I: root-Branch
   * 3 Strong Mutation 52: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.hashCode()I:90 - DeleteStatement: hashCode()I
   */

  @Test
  public void test14()  throws Throwable  {
      TCharObjectHashMap<Object> tCharObjectHashMap0 = new TCharObjectHashMap<Object>(0, (float) 0, 'H');
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap0);
      tCharObjectHashMap0.putIfAbsent(':', (Object) "/mnt/fastdata/ac1gf/Experiments_Major/apps/trove-3.0.2/Y9J>Su2X[\"</96Ds;1%");
      int int0 = tUnmodifiableCharObjectMap0.hashCode();
      assertEquals(1465619313, int0);
  }

  //Test case number: 15
  /*
   * 5 covered goals:
   * 1 Strong Mutation 53: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.toString()Ljava/lang/String;:91 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 2 Strong Mutation 54: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.toString()Ljava/lang/String;:91 - DeleteStatement: toString()Ljava/lang/String;
   * 3 Strong Mutation 54: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.toString()Ljava/lang/String;:91 - DeleteStatement: toString()Ljava/lang/String;
   * 4 Strong Mutation 53: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.toString()Ljava/lang/String;:91 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 5 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.toString()Ljava/lang/String;: root-Branch
   */

  @Test
  public void test15()  throws Throwable  {
      TCharObjectHashMap<Object> tCharObjectHashMap0 = new TCharObjectHashMap<Object>(0, 288.16745F, 'Z');
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap0);
      String string0 = tUnmodifiableCharObjectMap0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 16
  /*
   * 3 covered goals:
   * 1 Strong Mutation 56: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.getNoEntryKey()C:92 - DeleteStatement: getNoEntryKey()C
   * 2 Strong Mutation 56: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.getNoEntryKey()C:92 - DeleteStatement: getNoEntryKey()C
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.getNoEntryKey()C: root-Branch
   */

  @Test
  public void test16()  throws Throwable  {
      TCharObjectHashMap<Character> tCharObjectHashMap0 = new TCharObjectHashMap<Character>(0, 0.0F, 'e');
      TUnmodifiableCharObjectMap<Character> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Character>((TCharObjectMap<Character>) tCharObjectHashMap0);
      TCharObjectHashMap<Integer> tCharObjectHashMap1 = new TCharObjectHashMap<Integer>((-802));
      TCharObjectHashMap<Object> tCharObjectHashMap2 = new TCharObjectHashMap<Object>((TCharObjectMap<?>) tCharObjectHashMap1);
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap1 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap2);
      char char0 = tUnmodifiableCharObjectMap1.getNoEntryKey();
      assertEquals('\u0000', char0);
  }

  //Test case number: 17
  /*
   * 3 covered goals:
   * 1 Strong Mutation 58: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachKey(Lgnu/trove/procedure/TCharProcedure;)Z:95 - DeleteStatement: forEachKey(Lgnu/trove/procedure/TCharProcedure;)Z
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachKey(Lgnu/trove/procedure/TCharProcedure;)Z: root-Branch
   * 3 Strong Mutation 58: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachKey(Lgnu/trove/procedure/TCharProcedure;)Z:95 - DeleteStatement: forEachKey(Lgnu/trove/procedure/TCharProcedure;)Z
   */

  @Test
  public void test17()  throws Throwable  {
      TCharObjectHashMap<Character> tCharObjectHashMap0 = new TCharObjectHashMap<Character>(0, 0.0F, 'e');
      TUnmodifiableCharObjectMap<Character> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Character>((TCharObjectMap<Character>) tCharObjectHashMap0);
      boolean boolean0 = tUnmodifiableCharObjectMap0.forEachKey((TCharProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 18
  /*
   * 4 covered goals:
   * 1 Strong Mutation 59: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachValue(Lgnu/trove/procedure/TObjectProcedure;)Z:98 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachValue(Lgnu/trove/procedure/TObjectProcedure;)Z: root-Branch
   * 3 Strong Mutation 59: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachValue(Lgnu/trove/procedure/TObjectProcedure;)Z:98 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 4 Strong Mutation 60: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachValue(Lgnu/trove/procedure/TObjectProcedure;)Z:98 - DeleteStatement: forEachValue(Lgnu/trove/procedure/TObjectProcedure;)Z
   */

  @Test
  public void test18()  throws Throwable  {
      TCharObjectHashMap<Object> tCharObjectHashMap0 = new TCharObjectHashMap<Object>();
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap0);
      Object[] objectArray0 = new Object[3];
      objectArray0[1] = (Object) (-72);
      ToObjectArrayProceedure<Object> toObjectArrayProceedure0 = new ToObjectArrayProceedure<Object>(objectArray0);
      tUnmodifiableCharObjectMap0.forEachValue((TObjectProcedure<? super Object>) toObjectArrayProceedure0);
      objectArray0[1] = (Object) true;
  }

  //Test case number: 19
  /*
   * 1 covered goal:
   * 1 Strong Mutation 60: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachValue(Lgnu/trove/procedure/TObjectProcedure;)Z:98 - DeleteStatement: forEachValue(Lgnu/trove/procedure/TObjectProcedure;)Z
   */

  @Test
  public void test19()  throws Throwable  {
      TCharObjectHashMap<Object> tCharObjectHashMap0 = new TCharObjectHashMap<Object>();
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap0);
      Object[] objectArray0 = new Object[3];
      ToObjectArrayProceedure<Object> toObjectArrayProceedure0 = new ToObjectArrayProceedure<Object>(objectArray0);
      boolean boolean0 = tUnmodifiableCharObjectMap0.forEachValue((TObjectProcedure<? super Object>) toObjectArrayProceedure0);
      assertEquals(true, boolean0);
  }

  //Test case number: 20
  /*
   * 3 covered goals:
   * 1 Strong Mutation 62: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachEntry(Lgnu/trove/procedure/TCharObjectProcedure;)Z:101 - DeleteStatement: forEachEntry(Lgnu/trove/procedure/TCharObjectProcedure;)Z
   * 2 Strong Mutation 62: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachEntry(Lgnu/trove/procedure/TCharObjectProcedure;)Z:101 - DeleteStatement: forEachEntry(Lgnu/trove/procedure/TCharObjectProcedure;)Z
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachEntry(Lgnu/trove/procedure/TCharObjectProcedure;)Z: root-Branch
   */

  @Test
  public void test20()  throws Throwable  {
      TCharObjectHashMap<Character> tCharObjectHashMap0 = new TCharObjectHashMap<Character>();
      TUnmodifiableCharObjectMap<Character> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Character>((TCharObjectMap<Character>) tCharObjectHashMap0);
      boolean boolean0 = tUnmodifiableCharObjectMap0.forEachEntry((TCharObjectProcedure<? super Character>) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 21
  /*
   * 6 covered goals:
   * 1 Strong Mutation 61: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachEntry(Lgnu/trove/procedure/TCharObjectProcedure;)Z:101 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 2 Strong Mutation 9: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.size()I:58 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 3 Strong Mutation 55: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.getNoEntryKey()C:92 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 4 Strong Mutation 9: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.size()I:58 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 5 Strong Mutation 55: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.getNoEntryKey()C:92 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 6 Strong Mutation 61: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.forEachEntry(Lgnu/trove/procedure/TCharObjectProcedure;)Z:101 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   */

  @Test
  public void test21()  throws Throwable  {
      TCharObjectHashMap<Character> tCharObjectHashMap0 = new TCharObjectHashMap<Character>();
      TUnmodifiableCharObjectMap<Character> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Character>((TCharObjectMap<Character>) tCharObjectHashMap0);
      TCharObjectHashMap<Character> tCharObjectHashMap1 = new TCharObjectHashMap<Character>((TCharObjectMap<? extends Character>) tUnmodifiableCharObjectMap0);
      assertEquals('\u0000', tCharObjectHashMap1.getNoEntryKey());
  }

  //Test case number: 22
  /*
   * 6 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keySet()Lgnu/trove/set/TCharSet;: I4 Branch 2 IFNONNULL L74 - true
   * 2 Strong Mutation 27: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keySet()Lgnu/trove/set/TCharSet;:75 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 3 Strong Mutation 26: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keySet()Lgnu/trove/set/TCharSet;:74 - ReplaceComparisonOperator != null -> = null
   * 4 Strong Mutation 29: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keySet()Lgnu/trove/set/TCharSet;:75 - DeleteStatement: unmodifiableSet(Lgnu/trove/set/TCharSet;)Lgnu/trove/set/TCharSet;
   * 5 Strong Mutation 28: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keySet()Lgnu/trove/set/TCharSet;:75 - DeleteStatement: keySet()Lgnu/trove/set/TCharSet;
   * 6 Strong Mutation 30: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.keySet()Lgnu/trove/set/TCharSet;:76 - DeleteField: keySetLgnu/trove/set/TCharSet;
   */

  @Test
  public void test22()  throws Throwable  {
      TCharObjectHashMap<Integer> tCharObjectHashMap0 = new TCharObjectHashMap<Integer>((-802));
      TCharObjectHashMap<Object> tCharObjectHashMap1 = new TCharObjectHashMap<Object>((TCharObjectMap<?>) tCharObjectHashMap0);
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap1);
      TUnmodifiableCharSet tUnmodifiableCharSet0 = (TUnmodifiableCharSet)tUnmodifiableCharObjectMap0.keySet();
      assertNotNull(tUnmodifiableCharSet0);
      
      TUnmodifiableCharSet tUnmodifiableCharSet1 = (TUnmodifiableCharSet)tUnmodifiableCharObjectMap0.keySet();
      assertSame(tUnmodifiableCharSet1, tUnmodifiableCharSet0);
  }

  //Test case number: 23
  /*
   * 12 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.valueCollection()Ljava/util/Collection;: I4 Branch 3 IFNONNULL L82 - true
   * 2 Strong Mutation 38: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.valueCollection()Ljava/util/Collection;:83 - DeleteStatement: valueCollection()Ljava/util/Collection;
   * 3 Strong Mutation 39: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.valueCollection()Ljava/util/Collection;:83 - DeleteStatement: unmodifiableCollection(Ljava/util/Collection;)Ljava/util/Collection;
   * 4 Strong Mutation 36: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.valueCollection()Ljava/util/Collection;:82 - ReplaceComparisonOperator != null -> = null
   * 5 Strong Mutation 37: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.valueCollection()Ljava/util/Collection;:83 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 6 Strong Mutation 40: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.valueCollection()Ljava/util/Collection;:84 - DeleteField: valuesLjava/util/Collection;
   * 7 Strong Mutation 38: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.valueCollection()Ljava/util/Collection;:83 - DeleteStatement: valueCollection()Ljava/util/Collection;
   * 8 Strong Mutation 39: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.valueCollection()Ljava/util/Collection;:83 - DeleteStatement: unmodifiableCollection(Ljava/util/Collection;)Ljava/util/Collection;
   * 9 Strong Mutation 36: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.valueCollection()Ljava/util/Collection;:82 - ReplaceComparisonOperator != null -> = null
   * 10 Strong Mutation 37: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.valueCollection()Ljava/util/Collection;:83 - DeleteField: mLgnu/trove/map/TCharObjectMap;
   * 11 Strong Mutation 40: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.valueCollection()Ljava/util/Collection;:84 - DeleteField: valuesLjava/util/Collection;
   * 12 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.valueCollection()Ljava/util/Collection;: I4 Branch 3 IFNONNULL L82 - false
   */

  @Test
  public void test23()  throws Throwable  {
      TCharObjectHashMap<Integer> tCharObjectHashMap0 = new TCharObjectHashMap<Integer>((-802));
      TCharObjectHashMap<Object> tCharObjectHashMap1 = new TCharObjectHashMap<Object>((TCharObjectMap<?>) tCharObjectHashMap0);
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap1);
      Collection<Object> collection0 = tUnmodifiableCharObjectMap0.valueCollection();
      assertNotNull(collection0);
      
      Collection<Object> collection1 = tUnmodifiableCharObjectMap0.valueCollection();
      assertSame(collection1, collection0);
  }

  //Test case number: 24
  /*
   * 8 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z: I9 Branch 5 IFEQ L89 - false
   * 2 Strong Mutation 8: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.<init>(Lgnu/trove/map/TCharObjectMap;)V:53 - ReplaceComparisonOperator != null -> = null
   * 3 Strong Mutation 47: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z:89 - DeleteStatement: equals(Ljava/lang/Object;)Z
   * 4 Strong Mutation 49: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z:89 - ReplaceConstant - 1 -> 0
   * 5 Strong Mutation 48: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z:89 - ReplaceComparisonOperator == -> !=
   * 6 Strong Mutation 47: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z:89 - DeleteStatement: equals(Ljava/lang/Object;)Z
   * 7 Strong Mutation 49: gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z:89 - ReplaceConstant - 1 -> 0
   * 8 gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap.equals(Ljava/lang/Object;)Z: I4 Branch 4 IF_ACMPEQ L89 - true
   */

  @Test
  public void test24()  throws Throwable  {
      TCharObjectHashMap<Object> tCharObjectHashMap0 = new TCharObjectHashMap<Object>();
      TUnmodifiableCharObjectMap<Object> tUnmodifiableCharObjectMap0 = new TUnmodifiableCharObjectMap<Object>((TCharObjectMap<Object>) tCharObjectHashMap0);
      Object[] objectArray0 = new Object[3];
      objectArray0[0] = (Object) tCharObjectHashMap0;
      boolean boolean0 = tUnmodifiableCharObjectMap0.equals(objectArray0[0]);
      assertEquals(true, boolean0);
  }
}
