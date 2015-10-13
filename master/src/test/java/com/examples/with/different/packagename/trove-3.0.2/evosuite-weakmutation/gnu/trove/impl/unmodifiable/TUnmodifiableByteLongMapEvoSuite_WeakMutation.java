/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.unmodifiable;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap;
import gnu.trove.impl.unmodifiable.TUnmodifiableByteSet;
import gnu.trove.impl.unmodifiable.TUnmodifiableLongCollection;
import gnu.trove.map.TByteLongMap;
import gnu.trove.map.hash.TByteLongHashMap;
import gnu.trove.procedure.TByteLongProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TLongProcedure;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TUnmodifiableByteLongMapEvoSuite_WeakMutation {

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
   * 29 covered goals:
   * 1 Weak Mutation 9: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.size()I:63 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 2 Weak Mutation 10: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.size()I:63 - DeleteStatement: size()I
   * 3 Weak Mutation 0: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.key()B:114 - DeleteField: iterLgnu/trove/iterator/TByteLongIterator;
   * 4 Weak Mutation 1: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.key()B:114 - DeleteStatement: key()B
   * 5 Weak Mutation 2: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.value()J:115 - DeleteField: iterLgnu/trove/iterator/TByteLongIterator;
   * 6 Weak Mutation 3: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.value()J:115 - DeleteStatement: value()J
   * 7 Weak Mutation 4: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.advance()V:116 - DeleteField: iterLgnu/trove/iterator/TByteLongIterator;
   * 8 Weak Mutation 5: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.advance()V:116 - DeleteStatement: advance()V
   * 9 Weak Mutation 6: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.hasNext()Z:117 - DeleteField: iterLgnu/trove/iterator/TByteLongIterator;
   * 10 Weak Mutation 7: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.hasNext()Z:117 - DeleteStatement: hasNext()Z
   * 11 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.iterator()Lgnu/trove/iterator/TByteLongIterator;: root-Branch
   * 12 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.size()I: root-Branch
   * 13 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.advance()V: root-Branch
   * 14 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.<init>(Lgnu/trove/impl/unmodifiable/TUnmodifiableByteLongMap;)V: root-Branch
   * 15 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.value()J: root-Branch
   * 16 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.key()B: root-Branch
   * 17 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.hasNext()Z: root-Branch
   * 18 Weak Mutation 0: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.key()B:114 - DeleteField: iterLgnu/trove/iterator/TByteLongIterator;
   * 19 Weak Mutation 1: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.key()B:114 - DeleteStatement: key()B
   * 20 Weak Mutation 2: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.value()J:115 - DeleteField: iterLgnu/trove/iterator/TByteLongIterator;
   * 21 Weak Mutation 3: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.value()J:115 - DeleteStatement: value()J
   * 22 Weak Mutation 4: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.advance()V:116 - DeleteField: iterLgnu/trove/iterator/TByteLongIterator;
   * 23 Weak Mutation 5: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.advance()V:116 - DeleteStatement: advance()V
   * 24 Weak Mutation 6: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.hasNext()Z:117 - DeleteField: iterLgnu/trove/iterator/TByteLongIterator;
   * 25 Weak Mutation 7: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap$1.hasNext()Z:117 - DeleteStatement: hasNext()Z
   * 26 Weak Mutation 8: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.<init>(Lgnu/trove/map/TByteLongMap;)V:58 - ReplaceComparisonOperator != null -> = null
   * 27 Weak Mutation 9: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.size()I:63 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 28 Weak Mutation 10: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.size()I:63 - DeleteStatement: size()I
   * 29 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.<init>(Lgnu/trove/map/TByteLongMap;)V: I17 Branch 1 IFNONNULL L58 - true
   */

  @Test
  public void test0()  throws Throwable  {
      byte[] byteArray0 = new byte[1];
      long[] longArray0 = new long[6];
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(byteArray0, longArray0);
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      TByteLongHashMap tByteLongHashMap1 = new TByteLongHashMap((TByteLongMap) tUnmodifiableByteLongMap0);
      assertEquals(1, tUnmodifiableByteLongMap0.size());
      assertEquals(false, tByteLongHashMap1.isEmpty());
  }

  //Test case number: 1
  /*
   * 5 covered goals:
   * 1 Weak Mutation 11: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.isEmpty()Z:64 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 2 Weak Mutation 12: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.isEmpty()Z:64 - DeleteStatement: isEmpty()Z
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.isEmpty()Z: root-Branch
   * 4 Weak Mutation 11: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.isEmpty()Z:64 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 5 Weak Mutation 12: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.isEmpty()Z:64 - DeleteStatement: isEmpty()Z
   */

  @Test
  public void test1()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-8));
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      boolean boolean0 = tUnmodifiableByteLongMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 2
  /*
   * 11 covered goals:
   * 1 Weak Mutation 13: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsKey(B)Z:65 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 2 Weak Mutation 14: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsKey(B)Z:65 - InsertUnaryOp Negation of key
   * 3 Weak Mutation 15: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsKey(B)Z:65 - InsertUnaryOp IINC 1 key
   * 4 Weak Mutation 17: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsKey(B)Z:65 - DeleteStatement: containsKey(B)Z
   * 5 Weak Mutation 16: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsKey(B)Z:65 - InsertUnaryOp IINC -1 key
   * 6 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsKey(B)Z: root-Branch
   * 7 Weak Mutation 13: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsKey(B)Z:65 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 8 Weak Mutation 14: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsKey(B)Z:65 - InsertUnaryOp Negation of key
   * 9 Weak Mutation 15: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsKey(B)Z:65 - InsertUnaryOp IINC 1 key
   * 10 Weak Mutation 17: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsKey(B)Z:65 - DeleteStatement: containsKey(B)Z
   * 11 Weak Mutation 16: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsKey(B)Z:65 - InsertUnaryOp IINC -1 key
   */

  @Test
  public void test2()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap();
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      boolean boolean0 = tUnmodifiableByteLongMap0.containsKey((byte)0);
      assertEquals(false, boolean0);
  }

  //Test case number: 3
  /*
   * 7 covered goals:
   * 1 Weak Mutation 19: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsValue(J)Z:66 - InsertUnaryOp Negation of val
   * 2 Weak Mutation 18: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsValue(J)Z:66 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 3 Weak Mutation 20: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsValue(J)Z:66 - DeleteStatement: containsValue(J)Z
   * 4 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsValue(J)Z: root-Branch
   * 5 Weak Mutation 19: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsValue(J)Z:66 - InsertUnaryOp Negation of val
   * 6 Weak Mutation 18: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsValue(J)Z:66 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 7 Weak Mutation 20: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.containsValue(J)Z:66 - DeleteStatement: containsValue(J)Z
   */

  @Test
  public void test3()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-2), (-2));
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      boolean boolean0 = tUnmodifiableByteLongMap0.containsValue((long) (-2));
      assertEquals(false, boolean0);
  }

  //Test case number: 4
  /*
   * 11 covered goals:
   * 1 Weak Mutation 21: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.get(B)J:67 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 2 Weak Mutation 23: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.get(B)J:67 - InsertUnaryOp IINC 1 key
   * 3 Weak Mutation 22: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.get(B)J:67 - InsertUnaryOp Negation of key
   * 4 Weak Mutation 25: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.get(B)J:67 - DeleteStatement: get(B)J
   * 5 Weak Mutation 24: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.get(B)J:67 - InsertUnaryOp IINC -1 key
   * 6 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.get(B)J: root-Branch
   * 7 Weak Mutation 21: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.get(B)J:67 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 8 Weak Mutation 23: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.get(B)J:67 - InsertUnaryOp IINC 1 key
   * 9 Weak Mutation 22: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.get(B)J:67 - InsertUnaryOp Negation of key
   * 10 Weak Mutation 25: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.get(B)J:67 - DeleteStatement: get(B)J
   * 11 Weak Mutation 24: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.get(B)J:67 - InsertUnaryOp IINC -1 key
   */

  @Test
  public void test4()  throws Throwable  {
      byte[] byteArray0 = new byte[25];
      long[] longArray0 = new long[3];
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(byteArray0, longArray0);
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      long long0 = tUnmodifiableByteLongMap0.get((byte)0);
      assertEquals(0L, long0);
  }

  //Test case number: 5
  /*
   * 14 covered goals:
   * 1 Weak Mutation 26: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;:79 - DeleteField: keySetLgnu/trove/set/TByteSet;
   * 2 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;: I4 Branch 2 IFNONNULL L79 - true
   * 3 Weak Mutation 27: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;:79 - ReplaceComparisonOperator != null -> = null
   * 4 Weak Mutation 29: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;:80 - DeleteStatement: keySet()Lgnu/trove/set/TByteSet;
   * 5 Weak Mutation 28: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;:80 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 6 Weak Mutation 31: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;:81 - DeleteField: keySetLgnu/trove/set/TByteSet;
   * 7 Weak Mutation 30: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;:80 - DeleteStatement: unmodifiableSet(Lgnu/trove/set/TByteSet;)Lgnu/trove/set/TByteSet;
   * 8 Weak Mutation 27: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;:79 - ReplaceComparisonOperator != null -> = null
   * 9 Weak Mutation 26: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;:79 - DeleteField: keySetLgnu/trove/set/TByteSet;
   * 10 Weak Mutation 29: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;:80 - DeleteStatement: keySet()Lgnu/trove/set/TByteSet;
   * 11 Weak Mutation 28: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;:80 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 12 Weak Mutation 31: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;:81 - DeleteField: keySetLgnu/trove/set/TByteSet;
   * 13 Weak Mutation 30: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;:80 - DeleteStatement: unmodifiableSet(Lgnu/trove/set/TByteSet;)Lgnu/trove/set/TByteSet;
   * 14 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keySet()Lgnu/trove/set/TByteSet;: I4 Branch 2 IFNONNULL L79 - false
   */

  @Test
  public void test5()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(1274, 1274);
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      TUnmodifiableByteSet tUnmodifiableByteSet0 = (TUnmodifiableByteSet)tUnmodifiableByteLongMap0.keySet();
      assertNotNull(tUnmodifiableByteSet0);
      
      TUnmodifiableByteSet tUnmodifiableByteSet1 = (TUnmodifiableByteSet)tUnmodifiableByteLongMap0.keySet();
      assertSame(tUnmodifiableByteSet1, tUnmodifiableByteSet0);
  }

  //Test case number: 6
  /*
   * 5 covered goals:
   * 1 Weak Mutation 32: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keys()[B:83 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 2 Weak Mutation 33: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keys()[B:83 - DeleteStatement: keys()[B
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keys()[B: root-Branch
   * 4 Weak Mutation 32: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keys()[B:83 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 5 Weak Mutation 33: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keys()[B:83 - DeleteStatement: keys()[B
   */

  @Test
  public void test6()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(17);
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      byte[] byteArray0 = tUnmodifiableByteLongMap0.keys();
      assertNotNull(byteArray0);
  }

  //Test case number: 7
  /*
   * 5 covered goals:
   * 1 Weak Mutation 34: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keys([B)[B:84 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 2 Weak Mutation 35: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keys([B)[B:84 - DeleteStatement: keys([B)[B
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keys([B)[B: root-Branch
   * 4 Weak Mutation 34: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keys([B)[B:84 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 5 Weak Mutation 35: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.keys([B)[B:84 - DeleteStatement: keys([B)[B
   */

  @Test
  public void test7()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(17);
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      byte[] byteArray0 = tUnmodifiableByteLongMap0.keys(tByteLongHashMap0._states);
      assertNotNull(byteArray0);
  }

  //Test case number: 8
  /*
   * 14 covered goals:
   * 1 Weak Mutation 36: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;:87 - DeleteField: valuesLgnu/trove/TLongCollection;
   * 2 Weak Mutation 37: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;:87 - ReplaceComparisonOperator != null -> = null
   * 3 Weak Mutation 38: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;:88 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 4 Weak Mutation 39: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;:88 - DeleteStatement: valueCollection()Lgnu/trove/TLongCollection;
   * 5 Weak Mutation 40: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;:88 - DeleteStatement: unmodifiableCollection(Lgnu/trove/TLongCollection;)Lgnu/trove/TLongCollection;
   * 6 Weak Mutation 41: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;:89 - DeleteField: valuesLgnu/trove/TLongCollection;
   * 7 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;: I4 Branch 3 IFNONNULL L87 - true
   * 8 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;: I4 Branch 3 IFNONNULL L87 - false
   * 9 Weak Mutation 38: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;:88 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 10 Weak Mutation 39: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;:88 - DeleteStatement: valueCollection()Lgnu/trove/TLongCollection;
   * 11 Weak Mutation 36: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;:87 - DeleteField: valuesLgnu/trove/TLongCollection;
   * 12 Weak Mutation 37: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;:87 - ReplaceComparisonOperator != null -> = null
   * 13 Weak Mutation 40: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;:88 - DeleteStatement: unmodifiableCollection(Lgnu/trove/TLongCollection;)Lgnu/trove/TLongCollection;
   * 14 Weak Mutation 41: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.valueCollection()Lgnu/trove/TLongCollection;:89 - DeleteField: valuesLgnu/trove/TLongCollection;
   */

  @Test
  public void test8()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-8));
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      TUnmodifiableLongCollection tUnmodifiableLongCollection0 = (TUnmodifiableLongCollection)tUnmodifiableByteLongMap0.valueCollection();
      assertNotNull(tUnmodifiableLongCollection0);
      
      TUnmodifiableLongCollection tUnmodifiableLongCollection1 = (TUnmodifiableLongCollection)tUnmodifiableByteLongMap0.valueCollection();
      assertSame(tUnmodifiableLongCollection1, tUnmodifiableLongCollection0);
  }

  //Test case number: 9
  /*
   * 5 covered goals:
   * 1 Weak Mutation 42: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.values()[J:91 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 2 Weak Mutation 43: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.values()[J:91 - DeleteStatement: values()[J
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.values()[J: root-Branch
   * 4 Weak Mutation 42: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.values()[J:91 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 5 Weak Mutation 43: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.values()[J:91 - DeleteStatement: values()[J
   */

  @Test
  public void test9()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(1274, 1274);
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      long[] longArray0 = tUnmodifiableByteLongMap0.values();
      assertNotNull(longArray0);
  }

  //Test case number: 10
  /*
   * 5 covered goals:
   * 1 Weak Mutation 44: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.values([J)[J:92 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 2 Weak Mutation 45: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.values([J)[J:92 - DeleteStatement: values([J)[J
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.values([J)[J: root-Branch
   * 4 Weak Mutation 44: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.values([J)[J:92 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 5 Weak Mutation 45: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.values([J)[J:92 - DeleteStatement: values([J)[J
   */

  @Test
  public void test10()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(1274, 1274);
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      long[] longArray0 = new long[8];
      long[] longArray1 = tUnmodifiableByteLongMap0.values(longArray0);
      assertSame(longArray0, longArray1);
  }

  //Test case number: 11
  /*
   * 12 covered goals:
   * 1 Weak Mutation 46: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.equals(Ljava/lang/Object;)Z:94 - ReplaceComparisonOperator == -> !=
   * 2 Weak Mutation 47: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.equals(Ljava/lang/Object;)Z:94 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 3 Weak Mutation 51: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.equals(Ljava/lang/Object;)Z:94 - ReplaceConstant - 0 -> 1
   * 4 Weak Mutation 49: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.equals(Ljava/lang/Object;)Z:94 - ReplaceComparisonOperator == -> !=
   * 5 Weak Mutation 48: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.equals(Ljava/lang/Object;)Z:94 - DeleteStatement: equals(Ljava/lang/Object;)Z
   * 6 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.equals(Ljava/lang/Object;)Z: I4 Branch 4 IF_ACMPEQ L94 - false
   * 7 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.equals(Ljava/lang/Object;)Z: I9 Branch 5 IFEQ L94 - true
   * 8 Weak Mutation 46: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.equals(Ljava/lang/Object;)Z:94 - ReplaceComparisonOperator == -> !=
   * 9 Weak Mutation 47: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.equals(Ljava/lang/Object;)Z:94 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 10 Weak Mutation 51: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.equals(Ljava/lang/Object;)Z:94 - ReplaceConstant - 0 -> 1
   * 11 Weak Mutation 49: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.equals(Ljava/lang/Object;)Z:94 - ReplaceComparisonOperator == -> !=
   * 12 Weak Mutation 48: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.equals(Ljava/lang/Object;)Z:94 - DeleteStatement: equals(Ljava/lang/Object;)Z
   */

  @Test
  public void test11()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-2), (-2));
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      boolean boolean0 = tUnmodifiableByteLongMap0.equals((Object) "U8");
      assertEquals(false, boolean0);
  }

  //Test case number: 12
  /*
   * 5 covered goals:
   * 1 Weak Mutation 53: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.hashCode()I:95 - DeleteStatement: hashCode()I
   * 2 Weak Mutation 52: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.hashCode()I:95 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.hashCode()I: root-Branch
   * 4 Weak Mutation 53: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.hashCode()I:95 - DeleteStatement: hashCode()I
   * 5 Weak Mutation 52: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.hashCode()I:95 - DeleteField: mLgnu/trove/map/TByteLongMap;
   */

  @Test
  public void test12()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(17);
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      int int0 = tUnmodifiableByteLongMap0.hashCode();
      assertEquals(0, int0);
  }

  //Test case number: 13
  /*
   * 5 covered goals:
   * 1 Weak Mutation 55: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.toString()Ljava/lang/String;:96 - DeleteStatement: toString()Ljava/lang/String;
   * 2 Weak Mutation 54: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.toString()Ljava/lang/String;:96 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.toString()Ljava/lang/String;: root-Branch
   * 4 Weak Mutation 55: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.toString()Ljava/lang/String;:96 - DeleteStatement: toString()Ljava/lang/String;
   * 5 Weak Mutation 54: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.toString()Ljava/lang/String;:96 - DeleteField: mLgnu/trove/map/TByteLongMap;
   */

  @Test
  public void test13()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-8));
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      String string0 = tUnmodifiableByteLongMap0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 14
  /*
   * 5 covered goals:
   * 1 Weak Mutation 57: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.getNoEntryKey()B:97 - DeleteStatement: getNoEntryKey()B
   * 2 Weak Mutation 56: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.getNoEntryKey()B:97 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.getNoEntryKey()B: root-Branch
   * 4 Weak Mutation 57: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.getNoEntryKey()B:97 - DeleteStatement: getNoEntryKey()B
   * 5 Weak Mutation 56: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.getNoEntryKey()B:97 - DeleteField: mLgnu/trove/map/TByteLongMap;
   */

  @Test
  public void test14()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(1274, 1274);
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      byte byte0 = tUnmodifiableByteLongMap0.getNoEntryKey();
      assertEquals((byte)0, byte0);
  }

  //Test case number: 15
  /*
   * 5 covered goals:
   * 1 Weak Mutation 59: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.getNoEntryValue()J:98 - DeleteStatement: getNoEntryValue()J
   * 2 Weak Mutation 58: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.getNoEntryValue()J:98 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.getNoEntryValue()J: root-Branch
   * 4 Weak Mutation 59: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.getNoEntryValue()J:98 - DeleteStatement: getNoEntryValue()J
   * 5 Weak Mutation 58: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.getNoEntryValue()J:98 - DeleteField: mLgnu/trove/map/TByteLongMap;
   */

  @Test
  public void test15()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(17);
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      long long0 = tUnmodifiableByteLongMap0.getNoEntryValue();
      assertEquals(0L, long0);
  }

  //Test case number: 16
  /*
   * 5 covered goals:
   * 1 Weak Mutation 61: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z:101 - DeleteStatement: forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z
   * 2 Weak Mutation 60: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z:101 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z: root-Branch
   * 4 Weak Mutation 61: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z:101 - DeleteStatement: forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z
   * 5 Weak Mutation 60: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachKey(Lgnu/trove/procedure/TByteProcedure;)Z:101 - DeleteField: mLgnu/trove/map/TByteLongMap;
   */

  @Test
  public void test16()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((int) (byte)95);
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      boolean boolean0 = tUnmodifiableByteLongMap0.forEachKey((TByteProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 17
  /*
   * 5 covered goals:
   * 1 Weak Mutation 63: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachValue(Lgnu/trove/procedure/TLongProcedure;)Z:104 - DeleteStatement: forEachValue(Lgnu/trove/procedure/TLongProcedure;)Z
   * 2 Weak Mutation 62: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachValue(Lgnu/trove/procedure/TLongProcedure;)Z:104 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachValue(Lgnu/trove/procedure/TLongProcedure;)Z: root-Branch
   * 4 Weak Mutation 63: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachValue(Lgnu/trove/procedure/TLongProcedure;)Z:104 - DeleteStatement: forEachValue(Lgnu/trove/procedure/TLongProcedure;)Z
   * 5 Weak Mutation 62: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachValue(Lgnu/trove/procedure/TLongProcedure;)Z:104 - DeleteField: mLgnu/trove/map/TByteLongMap;
   */

  @Test
  public void test17()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-2), (-2));
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      boolean boolean0 = tUnmodifiableByteLongMap0.forEachValue((TLongProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 18
  /*
   * 5 covered goals:
   * 1 Weak Mutation 64: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachEntry(Lgnu/trove/procedure/TByteLongProcedure;)Z:107 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 2 Weak Mutation 65: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachEntry(Lgnu/trove/procedure/TByteLongProcedure;)Z:107 - DeleteStatement: forEachEntry(Lgnu/trove/procedure/TByteLongProcedure;)Z
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachEntry(Lgnu/trove/procedure/TByteLongProcedure;)Z: root-Branch
   * 4 Weak Mutation 64: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachEntry(Lgnu/trove/procedure/TByteLongProcedure;)Z:107 - DeleteField: mLgnu/trove/map/TByteLongMap;
   * 5 Weak Mutation 65: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.forEachEntry(Lgnu/trove/procedure/TByteLongProcedure;)Z:107 - DeleteStatement: forEachEntry(Lgnu/trove/procedure/TByteLongProcedure;)Z
   */

  @Test
  public void test18()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(1233);
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) tByteLongHashMap0);
      boolean boolean0 = tUnmodifiableByteLongMap0.forEachEntry((TByteLongProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 19
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.<init>(Lgnu/trove/map/TByteLongMap;)V: I17 Branch 1 IFNONNULL L58 - false
   * 2 Weak Mutation 8: gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap.<init>(Lgnu/trove/map/TByteLongMap;)V:58 - ReplaceComparisonOperator != null -> = null
   */

  @Test
  public void test19()  throws Throwable  {
      TUnmodifiableByteLongMap tUnmodifiableByteLongMap0 = null;
      try {
        tUnmodifiableByteLongMap0 = new TUnmodifiableByteLongMap((TByteLongMap) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }
}
