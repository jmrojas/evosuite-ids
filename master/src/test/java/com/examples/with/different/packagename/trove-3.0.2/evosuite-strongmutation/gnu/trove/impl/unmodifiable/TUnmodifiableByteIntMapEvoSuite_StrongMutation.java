/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.unmodifiable;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap;
import gnu.trove.impl.unmodifiable.TUnmodifiableByteSet;
import gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection;
import gnu.trove.map.TByteIntMap;
import gnu.trove.map.hash.TByteIntHashMap;
import gnu.trove.procedure.TByteIntProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TIntProcedure;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TUnmodifiableByteIntMapEvoSuite_StrongMutation {

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
   * 6 covered goals:
   * 1 Strong Mutation 11: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.isEmpty()Z:64 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.isEmpty()Z: root-Branch
   * 3 Strong Mutation 8: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.<init>(Lgnu/trove/map/TByteIntMap;)V:58 - ReplaceComparisonOperator != null -> = null
   * 4 Strong Mutation 11: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.isEmpty()Z:64 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 5 Strong Mutation 12: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.isEmpty()Z:64 - DeleteStatement: isEmpty()Z
   * 6 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.<init>(Lgnu/trove/map/TByteIntMap;)V: I17 Branch 1 IFNONNULL L58 - true
   */

  @Test
  public void test0()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap();
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      int[] intArray0 = new int[2];
      intArray0[1] = (-286);
      tUnmodifiableByteIntMap0.isEmpty();
      intArray0[1] = 0;
  }

  //Test case number: 1
  /*
   * 1 covered goal:
   * 1 Strong Mutation 12: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.isEmpty()Z:64 - DeleteStatement: isEmpty()Z
   */

  @Test
  public void test1()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap();
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      boolean boolean0 = tUnmodifiableByteIntMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 2
  /*
   * 3 covered goals:
   * 1 Strong Mutation 13: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsKey(B)Z:65 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsKey(B)Z: root-Branch
   * 3 Strong Mutation 13: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsKey(B)Z:65 - DeleteField: mLgnu/trove/map/TByteIntMap;
   */

  @Test
  public void test2()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-142), 161.64511F);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      int[] intArray0 = new int[8];
      intArray0[5] = (-142);
      tUnmodifiableByteIntMap0.containsKey((byte)0);
      intArray0[5] = (int) (byte)1;
  }

  //Test case number: 3
  /*
   * 8 covered goals:
   * 1 Strong Mutation 16: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsKey(B)Z:65 - InsertUnaryOp IINC -1 key
   * 2 Strong Mutation 14: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsKey(B)Z:65 - InsertUnaryOp Negation of key
   * 3 Strong Mutation 15: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsKey(B)Z:65 - InsertUnaryOp IINC 1 key
   * 4 Strong Mutation 17: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsKey(B)Z:65 - DeleteStatement: containsKey(B)Z
   * 5 Strong Mutation 14: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsKey(B)Z:65 - InsertUnaryOp Negation of key
   * 6 Strong Mutation 15: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsKey(B)Z:65 - InsertUnaryOp IINC 1 key
   * 7 Strong Mutation 17: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsKey(B)Z:65 - DeleteStatement: containsKey(B)Z
   * 8 Strong Mutation 16: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsKey(B)Z:65 - InsertUnaryOp IINC -1 key
   */

  @Test
  public void test3()  throws Throwable  {
      byte[] byteArray0 = new byte[19];
      byteArray0[1] = (byte)64;
      int[] intArray0 = new int[7];
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap(byteArray0, intArray0);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      boolean boolean0 = tUnmodifiableByteIntMap0.containsKey((byte)64);
      assertEquals(true, boolean0);
  }

  //Test case number: 4
  /*
   * 6 covered goals:
   * 1 Strong Mutation 19: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsValue(I)Z:66 - InsertUnaryOp Negation of val
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsValue(I)Z: root-Branch
   * 3 Strong Mutation 19: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsValue(I)Z:66 - InsertUnaryOp Negation of val
   * 4 Strong Mutation 21: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsValue(I)Z:66 - InsertUnaryOp IINC -1 val
   * 5 Strong Mutation 20: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsValue(I)Z:66 - InsertUnaryOp IINC 1 val
   * 6 Strong Mutation 22: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsValue(I)Z:66 - DeleteStatement: containsValue(I)Z
   */

  @Test
  public void test4()  throws Throwable  {
      byte[] byteArray0 = new byte[6];
      int[] intArray0 = new int[2];
      intArray0[1] = (int) (byte)76;
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap(byteArray0, intArray0);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      boolean boolean0 = tUnmodifiableByteIntMap0.containsValue((int) (byte)76);
      assertEquals(true, boolean0);
  }

  //Test case number: 5
  /*
   * 2 covered goals:
   * 1 Strong Mutation 18: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsValue(I)Z:66 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 2 Strong Mutation 18: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsValue(I)Z:66 - DeleteField: mLgnu/trove/map/TByteIntMap;
   */

  @Test
  public void test5()  throws Throwable  {
      byte[] byteArray0 = new byte[4];
      int[] intArray0 = new int[6];
      intArray0[3] = (int) (byte)0;
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap(byteArray0, intArray0);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      tUnmodifiableByteIntMap0.containsValue((-795));
      intArray0[3] = (int) (byte) (-51);
  }

  //Test case number: 6
  /*
   * 3 covered goals:
   * 1 Strong Mutation 22: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsValue(I)Z:66 - DeleteStatement: containsValue(I)Z
   * 2 Strong Mutation 21: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsValue(I)Z:66 - InsertUnaryOp IINC -1 val
   * 3 Strong Mutation 20: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.containsValue(I)Z:66 - InsertUnaryOp IINC 1 val
   */

  @Test
  public void test6()  throws Throwable  {
      int[] intArray0 = new int[4];
      byte[] byteArray0 = new byte[10];
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap(byteArray0, intArray0);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      boolean boolean0 = tUnmodifiableByteIntMap0.containsValue(0);
      assertEquals(true, boolean0);
  }

  //Test case number: 7
  /*
   * 3 covered goals:
   * 1 Strong Mutation 23: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.get(B)I:67 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.get(B)I: root-Branch
   * 3 Strong Mutation 23: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.get(B)I:67 - DeleteField: mLgnu/trove/map/TByteIntMap;
   */

  @Test
  public void test7()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-142), 161.64511F);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      int[] intArray0 = new int[8];
      intArray0[5] = (-142);
      tUnmodifiableByteIntMap0.get((byte)1);
      intArray0[5] = (int) (byte)1;
  }

  //Test case number: 8
  /*
   * 8 covered goals:
   * 1 Strong Mutation 26: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.get(B)I:67 - InsertUnaryOp IINC -1 key
   * 2 Strong Mutation 25: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.get(B)I:67 - InsertUnaryOp IINC 1 key
   * 3 Strong Mutation 24: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.get(B)I:67 - InsertUnaryOp Negation of key
   * 4 Strong Mutation 27: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.get(B)I:67 - DeleteStatement: get(B)I
   * 5 Strong Mutation 25: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.get(B)I:67 - InsertUnaryOp IINC 1 key
   * 6 Strong Mutation 24: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.get(B)I:67 - InsertUnaryOp Negation of key
   * 7 Strong Mutation 27: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.get(B)I:67 - DeleteStatement: get(B)I
   * 8 Strong Mutation 26: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.get(B)I:67 - InsertUnaryOp IINC -1 key
   */

  @Test
  public void test8()  throws Throwable  {
      byte[] byteArray0 = new byte[10];
      byteArray0[1] = (byte) (-82);
      int[] intArray0 = new int[3];
      intArray0[1] = (int) (byte)10;
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap(byteArray0, intArray0);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      int int0 = tUnmodifiableByteIntMap0.get((byte) (-82));
      assertEquals(10, int0);
  }

  //Test case number: 9
  /*
   * 5 covered goals:
   * 1 Strong Mutation 35: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keys()[B:83 - DeleteStatement: keys()[B
   * 2 Strong Mutation 34: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keys()[B:83 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 3 Strong Mutation 34: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keys()[B:83 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 4 Strong Mutation 35: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keys()[B:83 - DeleteStatement: keys()[B
   * 5 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keys()[B: root-Branch
   */

  @Test
  public void test9()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-142), 161.64511F);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      byte[] byteArray0 = tUnmodifiableByteIntMap0.keys();
      assertNotNull(byteArray0);
  }

  //Test case number: 10
  /*
   * 5 covered goals:
   * 1 Strong Mutation 37: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keys([B)[B:84 - DeleteStatement: keys([B)[B
   * 2 Strong Mutation 36: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keys([B)[B:84 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 3 Strong Mutation 36: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keys([B)[B:84 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 4 Strong Mutation 37: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keys([B)[B:84 - DeleteStatement: keys([B)[B
   * 5 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keys([B)[B: root-Branch
   */

  @Test
  public void test10()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-1267), (float) (-1267), (byte)1, (int) (byte)1);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      byte[] byteArray0 = tUnmodifiableByteIntMap0.keys(tByteIntHashMap0._states);
      assertNotNull(byteArray0);
  }

  //Test case number: 11
  /*
   * 5 covered goals:
   * 1 Strong Mutation 45: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.values()[I:91 - DeleteStatement: values()[I
   * 2 Strong Mutation 44: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.values()[I:91 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 3 Strong Mutation 44: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.values()[I:91 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 4 Strong Mutation 45: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.values()[I:91 - DeleteStatement: values()[I
   * 5 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.values()[I: root-Branch
   */

  @Test
  public void test11()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-1267), (float) (-1267), (byte)1, (int) (byte)1);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      int[] intArray0 = tUnmodifiableByteIntMap0.values();
      assertNotNull(intArray0);
  }

  //Test case number: 12
  /*
   * 5 covered goals:
   * 1 Strong Mutation 47: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.values([I)[I:92 - DeleteStatement: values([I)[I
   * 2 Strong Mutation 46: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.values([I)[I:92 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 3 Strong Mutation 46: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.values([I)[I:92 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 4 Strong Mutation 47: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.values([I)[I:92 - DeleteStatement: values([I)[I
   * 5 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.values([I)[I: root-Branch
   */

  @Test
  public void test12()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-1), (float) (-1), (byte)1, (-756));
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      int[] intArray0 = new int[9];
      int[] intArray1 = tUnmodifiableByteIntMap0.values(intArray0);
      assertSame(intArray1, intArray0);
  }

  //Test case number: 13
  /*
   * 7 covered goals:
   * 1 Strong Mutation 50: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.equals(Ljava/lang/Object;)Z:94 - DeleteStatement: equals(Ljava/lang/Object;)Z
   * 2 Strong Mutation 51: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.equals(Ljava/lang/Object;)Z:94 - ReplaceComparisonOperator == -> !=
   * 3 Strong Mutation 50: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.equals(Ljava/lang/Object;)Z:94 - DeleteStatement: equals(Ljava/lang/Object;)Z
   * 4 Strong Mutation 48: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.equals(Ljava/lang/Object;)Z:94 - ReplaceComparisonOperator == -> !=
   * 5 Strong Mutation 53: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.equals(Ljava/lang/Object;)Z:94 - ReplaceConstant - 0 -> 1
   * 6 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.equals(Ljava/lang/Object;)Z: I4 Branch 4 IF_ACMPEQ L94 - false
   * 7 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.equals(Ljava/lang/Object;)Z: I9 Branch 5 IFEQ L94 - true
   */

  @Test
  public void test13()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-1267), (float) (-1267), (byte)1, (int) (byte)1);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap1 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      boolean boolean0 = tUnmodifiableByteIntMap0.equals((Object) "valuesof nan are not suported. (VALUESOF NAN ARE NOT SUPORTED.)");
      assertTrue(tUnmodifiableByteIntMap0.equals(tUnmodifiableByteIntMap1));
      assertEquals(false, boolean0);
  }

  //Test case number: 14
  /*
   * 2 covered goals:
   * 1 Strong Mutation 49: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.equals(Ljava/lang/Object;)Z:94 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 2 Strong Mutation 49: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.equals(Ljava/lang/Object;)Z:94 - DeleteField: mLgnu/trove/map/TByteIntMap;
   */

  @Test
  public void test14()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap();
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      int[] intArray0 = new int[2];
      intArray0[1] = (-286);
      tUnmodifiableByteIntMap0.equals((Object) "fr_FR");
      intArray0[1] = 0;
  }

  //Test case number: 15
  /*
   * 3 covered goals:
   * 1 Strong Mutation 53: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.equals(Ljava/lang/Object;)Z:94 - ReplaceConstant - 0 -> 1
   * 2 Strong Mutation 51: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.equals(Ljava/lang/Object;)Z:94 - ReplaceComparisonOperator == -> !=
   * 3 Strong Mutation 48: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.equals(Ljava/lang/Object;)Z:94 - ReplaceComparisonOperator == -> !=
   */

  @Test
  public void test15()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-1267), (float) (-1267), (byte)1, (int) (byte)1);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      boolean boolean0 = tUnmodifiableByteIntMap0.equals((Object) "valuesof nan are not suported. (VALUESOF NAN ARE NOT SUPORTED.)");
      assertEquals(false, boolean0);
  }

  //Test case number: 16
  /*
   * 3 covered goals:
   * 1 Strong Mutation 55: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.hashCode()I:95 - DeleteStatement: hashCode()I
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.hashCode()I: root-Branch
   * 3 Strong Mutation 55: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.hashCode()I:95 - DeleteStatement: hashCode()I
   */

  @Test
  public void test16()  throws Throwable  {
      byte[] byteArray0 = new byte[6];
      byteArray0[2] = (byte) (-128);
      int[] intArray0 = new int[7];
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap(byteArray0, intArray0);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      int int0 = tUnmodifiableByteIntMap0.hashCode();
      assertEquals((-128), int0);
  }

  //Test case number: 17
  /*
   * 2 covered goals:
   * 1 Strong Mutation 54: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.hashCode()I:95 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 2 Strong Mutation 54: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.hashCode()I:95 - DeleteField: mLgnu/trove/map/TByteIntMap;
   */

  @Test
  public void test17()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap();
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      int[] intArray0 = new int[2];
      intArray0[1] = (-286);
      tUnmodifiableByteIntMap0.hashCode();
      intArray0[1] = 0;
  }

  //Test case number: 18
  /*
   * 5 covered goals:
   * 1 Strong Mutation 56: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.toString()Ljava/lang/String;:96 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 2 Strong Mutation 57: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.toString()Ljava/lang/String;:96 - DeleteStatement: toString()Ljava/lang/String;
   * 3 Strong Mutation 57: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.toString()Ljava/lang/String;:96 - DeleteStatement: toString()Ljava/lang/String;
   * 4 Strong Mutation 56: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.toString()Ljava/lang/String;:96 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 5 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.toString()Ljava/lang/String;: root-Branch
   */

  @Test
  public void test18()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap();
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      String string0 = tUnmodifiableByteIntMap0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 19
  /*
   * 3 covered goals:
   * 1 Strong Mutation 59: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.getNoEntryKey()B:97 - DeleteStatement: getNoEntryKey()B
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.getNoEntryKey()B: root-Branch
   * 3 Strong Mutation 59: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.getNoEntryKey()B:97 - DeleteStatement: getNoEntryKey()B
   */

  @Test
  public void test19()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-1267), (float) (-1267), (byte)1, (int) (byte)1);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      byte byte0 = tUnmodifiableByteIntMap0.getNoEntryKey();
      assertEquals((byte)1, byte0);
  }

  //Test case number: 20
  /*
   * 3 covered goals:
   * 1 Strong Mutation 61: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.getNoEntryValue()I:98 - DeleteStatement: getNoEntryValue()I
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.getNoEntryValue()I: root-Branch
   * 3 Strong Mutation 61: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.getNoEntryValue()I:98 - DeleteStatement: getNoEntryValue()I
   */

  @Test
  public void test20()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-493), 3.6693473F, (byte)0, (-493));
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      int int0 = tUnmodifiableByteIntMap0.getNoEntryValue();
      assertEquals((-493), int0);
  }

  //Test case number: 21
  /*
   * 2 covered goals:
   * 1 Strong Mutation 60: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.getNoEntryValue()I:98 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 2 Strong Mutation 60: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.getNoEntryValue()I:98 - DeleteField: mLgnu/trove/map/TByteIntMap;
   */

  @Test
  public void test21()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-142), 161.64511F);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      int[] intArray0 = new int[8];
      intArray0[0] = (-142);
      tUnmodifiableByteIntMap0.getNoEntryValue();
      intArray0[0] = (int) (byte)67;
  }

  //Test case number: 22
  /*
   * 3 covered goals:
   * 1 Strong Mutation 63: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z:101 - DeleteStatement: forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z: root-Branch
   * 3 Strong Mutation 63: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z:101 - DeleteStatement: forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z
   */

  @Test
  public void test22()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-1267), (float) (-1267), (byte)1, (int) (byte)1);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      boolean boolean0 = tUnmodifiableByteIntMap0.forEachKey((TByteProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 23
  /*
   * 4 covered goals:
   * 1 Strong Mutation 64: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z:104 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z: root-Branch
   * 3 Strong Mutation 64: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z:104 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 4 Strong Mutation 65: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z:104 - DeleteStatement: forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z
   */

  @Test
  public void test23()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap();
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      byte[] byteArray0 = new byte[8];
      byteArray0[2] = (byte)0;
      tUnmodifiableByteIntMap0.forEachValue((TIntProcedure) null);
      byteArray0[2] = (byte)97;
  }

  //Test case number: 24
  /*
   * 1 covered goal:
   * 1 Strong Mutation 65: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z:104 - DeleteStatement: forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z
   */

  @Test
  public void test24()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap();
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      boolean boolean0 = tUnmodifiableByteIntMap0.forEachValue((TIntProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 25
  /*
   * 3 covered goals:
   * 1 Strong Mutation 67: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.forEachEntry(Lgnu/trove/procedure/TByteIntProcedure;)Z:107 - DeleteStatement: forEachEntry(Lgnu/trove/procedure/TByteIntProcedure;)Z
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.forEachEntry(Lgnu/trove/procedure/TByteIntProcedure;)Z: root-Branch
   * 3 Strong Mutation 67: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.forEachEntry(Lgnu/trove/procedure/TByteIntProcedure;)Z:107 - DeleteStatement: forEachEntry(Lgnu/trove/procedure/TByteIntProcedure;)Z
   */

  @Test
  public void test25()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap((-1267), (float) (-1267), (byte)1, (int) (byte)1);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      boolean boolean0 = tUnmodifiableByteIntMap0.forEachEntry((TByteIntProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 26
  /*
   * 17 covered goals:
   * 1 Strong Mutation 1: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.key()B:114 - DeleteStatement: key()B
   * 2 Strong Mutation 0: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.key()B:114 - DeleteField: iterLgnu/trove/iterator/TByteIntIterator;
   * 3 Strong Mutation 1: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.key()B:114 - DeleteStatement: key()B
   * 4 Strong Mutation 2: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.value()I:115 - DeleteField: iterLgnu/trove/iterator/TByteIntIterator;
   * 5 Strong Mutation 4: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.advance()V:116 - DeleteField: iterLgnu/trove/iterator/TByteIntIterator;
   * 6 Strong Mutation 5: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.advance()V:116 - DeleteStatement: advance()V
   * 7 Strong Mutation 6: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.hasNext()Z:117 - DeleteField: iterLgnu/trove/iterator/TByteIntIterator;
   * 8 Strong Mutation 7: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.hasNext()Z:117 - DeleteStatement: hasNext()Z
   * 9 Strong Mutation 9: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.size()I:63 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 10 Strong Mutation 10: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.size()I:63 - DeleteStatement: size()I
   * 11 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.value()I: root-Branch
   * 12 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.<init>(Lgnu/trove/impl/unmodifiable/TUnmodifiableByteIntMap;)V: root-Branch
   * 13 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.advance()V: root-Branch
   * 14 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.hasNext()Z: root-Branch
   * 15 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.key()B: root-Branch
   * 16 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.size()I: root-Branch
   * 17 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.iterator()Lgnu/trove/iterator/TByteIntIterator;: root-Branch
   */

  @Test
  public void test26()  throws Throwable  {
      byte[] byteArray0 = new byte[3];
      byteArray0[0] = (byte) (-51);
      int[] intArray0 = new int[6];
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap(byteArray0, intArray0);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      TByteIntHashMap tByteIntHashMap1 = new TByteIntHashMap((TByteIntMap) tUnmodifiableByteIntMap0);
      assertEquals("{-51=0, 0=0}", tByteIntHashMap1.toString());
  }

  //Test case number: 27
  /*
   * 2 covered goals:
   * 1 Strong Mutation 3: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.value()I:115 - DeleteStatement: value()I
   * 2 Strong Mutation 3: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.value()I:115 - DeleteStatement: value()I
   */

  @Test
  public void test27()  throws Throwable  {
      byte[] byteArray0 = new byte[3];
      int[] intArray0 = new int[6];
      intArray0[2] = (-1057);
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap(byteArray0, intArray0);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      TByteIntHashMap tByteIntHashMap1 = new TByteIntHashMap((TByteIntMap) tUnmodifiableByteIntMap0);
      assertEquals("{0=-1057}", tByteIntHashMap1.toString());
      assertEquals(1, tUnmodifiableByteIntMap0.size());
  }

  //Test case number: 28
  /*
   * 8 covered goals:
   * 1 Strong Mutation 7: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.hasNext()Z:117 - DeleteStatement: hasNext()Z
   * 2 Strong Mutation 0: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.key()B:114 - DeleteField: iterLgnu/trove/iterator/TByteIntIterator;
   * 3 Strong Mutation 2: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.value()I:115 - DeleteField: iterLgnu/trove/iterator/TByteIntIterator;
   * 4 Strong Mutation 4: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.advance()V:116 - DeleteField: iterLgnu/trove/iterator/TByteIntIterator;
   * 5 Strong Mutation 5: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.advance()V:116 - DeleteStatement: advance()V
   * 6 Strong Mutation 6: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap$1.hasNext()Z:117 - DeleteField: iterLgnu/trove/iterator/TByteIntIterator;
   * 7 Strong Mutation 9: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.size()I:63 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 8 Strong Mutation 10: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.size()I:63 - DeleteStatement: size()I
   */

  @Test
  public void test28()  throws Throwable  {
      byte[] byteArray0 = new byte[3];
      int[] intArray0 = new int[6];
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap(byteArray0, intArray0);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      TByteIntHashMap tByteIntHashMap1 = new TByteIntHashMap((TByteIntMap) tUnmodifiableByteIntMap0);
      assertEquals(1, tUnmodifiableByteIntMap0.size());
      assertEquals(false, tByteIntHashMap1.isEmpty());
  }

  //Test case number: 29
  /*
   * 12 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keySet()Lgnu/trove/set/TByteSet;: I4 Branch 2 IFNONNULL L79 - true
   * 2 Strong Mutation 29: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keySet()Lgnu/trove/set/TByteSet;:79 - ReplaceComparisonOperator != null -> = null
   * 3 Strong Mutation 31: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keySet()Lgnu/trove/set/TByteSet;:80 - DeleteStatement: keySet()Lgnu/trove/set/TByteSet;
   * 4 Strong Mutation 30: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keySet()Lgnu/trove/set/TByteSet;:80 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 5 Strong Mutation 32: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keySet()Lgnu/trove/set/TByteSet;:80 - DeleteStatement: unmodifiableSet(Lgnu/trove/set/TByteSet;)Lgnu/trove/set/TByteSet;
   * 6 Strong Mutation 33: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keySet()Lgnu/trove/set/TByteSet;:81 - DeleteField: keySetLgnu/trove/set/TByteSet;
   * 7 Strong Mutation 29: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keySet()Lgnu/trove/set/TByteSet;:79 - ReplaceComparisonOperator != null -> = null
   * 8 Strong Mutation 31: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keySet()Lgnu/trove/set/TByteSet;:80 - DeleteStatement: keySet()Lgnu/trove/set/TByteSet;
   * 9 Strong Mutation 30: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keySet()Lgnu/trove/set/TByteSet;:80 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 10 Strong Mutation 32: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keySet()Lgnu/trove/set/TByteSet;:80 - DeleteStatement: unmodifiableSet(Lgnu/trove/set/TByteSet;)Lgnu/trove/set/TByteSet;
   * 11 Strong Mutation 33: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keySet()Lgnu/trove/set/TByteSet;:81 - DeleteField: keySetLgnu/trove/set/TByteSet;
   * 12 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.keySet()Lgnu/trove/set/TByteSet;: I4 Branch 2 IFNONNULL L79 - false
   */

  @Test
  public void test29()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap(164, 1805.024F);
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      TUnmodifiableByteSet tUnmodifiableByteSet0 = (TUnmodifiableByteSet)tUnmodifiableByteIntMap0.keySet();
      assertNotNull(tUnmodifiableByteSet0);
      
      TUnmodifiableByteSet tUnmodifiableByteSet1 = (TUnmodifiableByteSet)tUnmodifiableByteIntMap0.keySet();
      assertSame(tUnmodifiableByteSet1, tUnmodifiableByteSet0);
  }

  //Test case number: 30
  /*
   * 13 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.valueCollection()Lgnu/trove/TIntCollection;: I4 Branch 3 IFNONNULL L87 - true
   * 2 Strong Mutation 8: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.<init>(Lgnu/trove/map/TByteIntMap;)V:58 - ReplaceComparisonOperator != null -> = null
   * 3 Strong Mutation 39: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.valueCollection()Lgnu/trove/TIntCollection;:87 - ReplaceComparisonOperator != null -> = null
   * 4 Strong Mutation 42: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.valueCollection()Lgnu/trove/TIntCollection;:88 - DeleteStatement: unmodifiableCollection(Lgnu/trove/TIntCollection;)Lgnu/trove/TIntCollection;
   * 5 Strong Mutation 43: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.valueCollection()Lgnu/trove/TIntCollection;:89 - DeleteField: valuesLgnu/trove/TIntCollection;
   * 6 Strong Mutation 40: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.valueCollection()Lgnu/trove/TIntCollection;:88 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 7 Strong Mutation 41: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.valueCollection()Lgnu/trove/TIntCollection;:88 - DeleteStatement: valueCollection()Lgnu/trove/TIntCollection;
   * 8 Strong Mutation 39: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.valueCollection()Lgnu/trove/TIntCollection;:87 - ReplaceComparisonOperator != null -> = null
   * 9 Strong Mutation 42: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.valueCollection()Lgnu/trove/TIntCollection;:88 - DeleteStatement: unmodifiableCollection(Lgnu/trove/TIntCollection;)Lgnu/trove/TIntCollection;
   * 10 Strong Mutation 43: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.valueCollection()Lgnu/trove/TIntCollection;:89 - DeleteField: valuesLgnu/trove/TIntCollection;
   * 11 Strong Mutation 40: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.valueCollection()Lgnu/trove/TIntCollection;:88 - DeleteField: mLgnu/trove/map/TByteIntMap;
   * 12 Strong Mutation 41: gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.valueCollection()Lgnu/trove/TIntCollection;:88 - DeleteStatement: valueCollection()Lgnu/trove/TIntCollection;
   * 13 gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap.valueCollection()Lgnu/trove/TIntCollection;: I4 Branch 3 IFNONNULL L87 - false
   */

  @Test
  public void test30()  throws Throwable  {
      TByteIntHashMap tByteIntHashMap0 = new TByteIntHashMap();
      TUnmodifiableByteIntMap tUnmodifiableByteIntMap0 = new TUnmodifiableByteIntMap((TByteIntMap) tByteIntHashMap0);
      TUnmodifiableIntCollection tUnmodifiableIntCollection0 = (TUnmodifiableIntCollection)tUnmodifiableByteIntMap0.valueCollection();
      assertNotNull(tUnmodifiableIntCollection0);
      
      TUnmodifiableIntCollection tUnmodifiableIntCollection1 = (TUnmodifiableIntCollection)tUnmodifiableByteIntMap0.valueCollection();
      assertSame(tUnmodifiableIntCollection1, tUnmodifiableIntCollection0);
  }
}
