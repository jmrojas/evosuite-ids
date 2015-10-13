/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.map.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TIntFunction;
import gnu.trove.map.TFloatIntMap;
import gnu.trove.map.hash.TFloatIntHashMap;
import gnu.trove.procedure.TFloatIntProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TIntProcedure;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TFloatIntHashMapEvoSuite_Branch {

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
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap$TKeyView.<init>(Lgnu/trove/map/hash/TFloatIntHashMap;)V: root-Branch
   * 2 gnu.trove.map.hash.TFloatIntHashMap.keySet()Lgnu/trove/set/TFloatSet;: root-Branch
   * 3 gnu.trove.map.hash.TFloatIntHashMap.<init>()V: root-Branch
   * 4 gnu.trove.map.hash.TFloatIntHashMap.setUp(I)I: root-Branch
   */

  @Test
  public void test0()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      tFloatIntHashMap0.keySet();
      assertEquals(23, tFloatIntHashMap0.capacity());
  }

  //Test case number: 1
  /*
   * 9 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.removeAt(I)V: root-Branch
   * 2 gnu.trove.map.hash.TFloatIntHashMap.<init>([F[I)V: I24 Branch 33 IF_ICMPGE L119 - true
   * 3 gnu.trove.map.hash.TFloatIntHashMap.<init>([F[I)V: I24 Branch 33 IF_ICMPGE L119 - false
   * 4 gnu.trove.map.hash.TFloatIntHashMap.doPut(FII)I: I12 Branch 40 IFGE L214 - false
   * 5 gnu.trove.map.hash.TFloatIntHashMap.doPut(FII)I: I41 Branch 41 IFEQ L221 - true
   * 6 gnu.trove.map.hash.TFloatIntHashMap.remove(F)I: I14 Branch 46 IFLT L276 - false
   * 7 gnu.trove.map.hash.TFloatIntHashMap.put(FI)I: root-Branch
   * 8 gnu.trove.map.hash.TFloatIntHashMap.doPut(FII)I: I12 Branch 40 IFGE L214 - true
   * 9 gnu.trove.map.hash.TFloatIntHashMap.doPut(FII)I: I41 Branch 41 IFEQ L221 - false
   */

  @Test
  public void test1()  throws Throwable  {
      float[] floatArray0 = new float[9];
      int[] intArray0 = new int[3];
      floatArray0[0] = (-1.0F);
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap(floatArray0, intArray0);
      assertEquals("{-1.0=0, 0.0=0}", tFloatIntHashMap0.toString());
      
      int int0 = tFloatIntHashMap0.remove((-1.0F));
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals(0, int0);
  }

  //Test case number: 2
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.forEachKey(Lgnu/trove/procedure/TFloatProcedure;)Z: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      boolean boolean0 = tFloatIntHashMap0.forEachKey((TFloatProcedure) null);
      assertEquals(23, tFloatIntHashMap0.capacity());
      assertEquals(true, boolean0);
  }

  //Test case number: 3
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.containsKey(F)Z: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      tFloatIntHashMap0.containsKey((float) (-913));
      assertEquals(23, tFloatIntHashMap0.capacity());
  }

  //Test case number: 4
  /*
   * 2 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.increment(F)Z: root-Branch
   * 2 gnu.trove.map.hash.TFloatIntHashMap.adjustValue(FI)Z: I9 Branch 71 IFGE L477 - false
   */

  @Test
  public void test4()  throws Throwable  {
      float[] floatArray0 = new float[10];
      int[] intArray0 = new int[7];
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap(floatArray0, intArray0);
      boolean boolean0 = tFloatIntHashMap0.increment(643.1363F);
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals(false, boolean0);
      assertEquals(23, tFloatIntHashMap0.capacity());
      assertEquals("{0.0=0}", tFloatIntHashMap0.toString());
  }

  //Test case number: 5
  /*
   * 2 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.valueCollection()Lgnu/trove/TIntCollection;: root-Branch
   * 2 gnu.trove.map.hash.TFloatIntHashMap$TValueView.<init>(Lgnu/trove/map/hash/TFloatIntHashMap;)V: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      tFloatIntHashMap0.valueCollection();
      assertEquals(23, tFloatIntHashMap0.capacity());
  }

  //Test case number: 6
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.clear()V: root-Branch
   */

  @Test
  public void test6()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      tFloatIntHashMap0.clear();
      assertEquals(23, tFloatIntHashMap0.capacity());
      assertEquals("{}", tFloatIntHashMap0.toString());
  }

  //Test case number: 7
  /*
   * 10 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.<init>(Lgnu/trove/map/TFloatIntMap;)V: I40 Branch 35 IFEQ L139 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.<init>(Lgnu/trove/map/TFloatIntMap;)V: I52 Branch 36 IFEQ L143 - false
   * 3 gnu.trove.map.hash.TFloatIntHashMap.rehash(I)V: I46 Branch 37 IFLE L185 - true
   * 4 gnu.trove.map.hash.TFloatIntHashMap.rehash(I)V: I46 Branch 37 IFLE L185 - false
   * 5 gnu.trove.map.hash.TFloatIntHashMap.rehash(I)V: I53 Branch 38 IF_ICMPNE L186 - true
   * 6 gnu.trove.map.hash.TFloatIntHashMap$TFloatIntHashIterator.<init>(Lgnu/trove/map/hash/TFloatIntHashMap;Lgnu/trove/map/hash/TFloatIntHashMap;)V: root-Branch
   * 7 gnu.trove.map.hash.TFloatIntHashMap.iterator()Lgnu/trove/iterator/TFloatIntIterator;: root-Branch
   * 8 gnu.trove.map.hash.TFloatIntHashMap.<init>(IFFI)V: root-Branch
   * 9 gnu.trove.map.hash.TFloatIntHashMap.putAll(Lgnu/trove/map/TFloatIntMap;)V: I15 Branch 43 IFEQ L243 - true
   * 10 gnu.trove.map.hash.TFloatIntHashMap.<init>(Lgnu/trove/map/TFloatIntMap;)V: I10 Branch 34 IFEQ L133 - false
   */

//   @Test
//   public void test7()  throws Throwable  {
//       TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap((-1), (-1), (-1), (-1));
//       TFloatIntHashMap tFloatIntHashMap1 = new TFloatIntHashMap((TFloatIntMap) tFloatIntHashMap0);
//       assertEquals(3, tFloatIntHashMap1.capacity());
//       assertEquals(-1, tFloatIntHashMap1.getNoEntryValue());
//       assertEquals((-1.0F), tFloatIntHashMap1.getNoEntryKey(), 0.01F);
//   }

  //Test case number: 8
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.rehash(I)V: I53 Branch 38 IF_ICMPNE L186 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.<init>(I)V: root-Branch
   * 3 gnu.trove.map.hash.TFloatIntHashMap.adjustOrPutValue(FII)I: I9 Branch 72 IFGE L491 - true
   * 4 gnu.trove.map.hash.TFloatIntHashMap.adjustOrPutValue(FII)I: I58 Branch 73 IFEQ L502 - false
   */

  @Test
  public void test8()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap((-554));
      tFloatIntHashMap0.adjustOrPutValue((-1400.1345F), 1241, (-917));
      int int0 = tFloatIntHashMap0.put((float) (-917), (-554));
      assertEquals(2, tFloatIntHashMap0.size());
      assertEquals(0, int0);
  }

  //Test case number: 9
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.putIfAbsent(FI)I: I9 Branch 39 IFGE L205 - false
   */

  @Test
  public void test9()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      tFloatIntHashMap0.adjustOrPutValue(0.0F, 1161, 0);
      int int0 = tFloatIntHashMap0.putIfAbsent(0.0F, (-913));
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals(0, int0);
  }

  //Test case number: 10
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.putAll(Ljava/util/Map;)V: I15 Branch 42 IFEQ L233 - true
   * 2 gnu.trove.map.hash.TFloatIntHashMap.rehash(I)V: I46 Branch 37 IFLE L185 - true
   * 3 gnu.trove.map.hash.TFloatIntHashMap.rehash(I)V: I46 Branch 37 IFLE L185 - false
   * 4 gnu.trove.map.hash.TFloatIntHashMap.rehash(I)V: I53 Branch 38 IF_ICMPNE L186 - true
   */

  @Test
  public void test10()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap((-1545), (-1545), (-1545), (-1256));
      HashMap<Float, Integer> hashMap0 = new HashMap<Float, Integer>();
      tFloatIntHashMap0.putAll((Map<? extends Float, ? extends Integer>) hashMap0);
      assertEquals("{}", tFloatIntHashMap0.toString());
      assertEquals(3, tFloatIntHashMap0.capacity());
  }

  //Test case number: 11
  /*
   * 8 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.get(F)I: I9 Branch 44 IFGE L253 - true
   * 2 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I4 Branch 74 IFNE L1184 - true
   * 3 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I20 Branch 75 IF_ICMPEQ L1188 - true
   * 4 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I53 Branch 76 IFLE L1195 - true
   * 5 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I53 Branch 76 IFLE L1195 - false
   * 6 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I60 Branch 77 IF_ICMPNE L1196 - true
   * 7 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I60 Branch 77 IF_ICMPNE L1196 - false
   * 8 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I84 Branch 78 IF_ICMPEQ L1200 - true
   */

  @Test
  public void test11()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      tFloatIntHashMap0.adjustOrPutValue(0.0F, 166, 83);
      boolean boolean0 = tFloatIntHashMap0.equals((Object) tFloatIntHashMap0);
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals(true, boolean0);
  }

  //Test case number: 12
  /*
   * 5 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.get(F)I: I9 Branch 44 IFGE L253 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I84 Branch 78 IF_ICMPEQ L1200 - false
   * 3 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I87 Branch 79 IF_ICMPEQ L1200 - false
   * 4 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I90 Branch 80 IF_ICMPEQ L1200 - true
   * 5 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I53 Branch 76 IFLE L1195 - true
   */

  @Test
  public void test12()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      tFloatIntHashMap0.adjustOrPutValue(528.22266F, 166, 83);
      int[] intArray0 = new int[10];
      TFloatIntHashMap tFloatIntHashMap1 = new TFloatIntHashMap(tFloatIntHashMap0._set, intArray0);
      boolean boolean0 = tFloatIntHashMap0.equals((Object) tFloatIntHashMap1);
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals(true, boolean0);
  }

  //Test case number: 13
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.isEmpty()Z: I5 Branch 45 IF_ICMPNE L268 - true
   */

  @Test
  public void test13()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap((-554));
      tFloatIntHashMap0.adjustOrPutValue((-1400.1345F), 1241, (-917));
      boolean boolean0 = tFloatIntHashMap0.isEmpty();
      assertEquals("{-1400.1345=-917}", tFloatIntHashMap0.toString());
      assertEquals(false, boolean0);
  }

  //Test case number: 14
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.isEmpty()Z: I5 Branch 45 IF_ICMPNE L268 - false
   */

  @Test
  public void test14()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap((-1));
      boolean boolean0 = tFloatIntHashMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 15
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.remove(F)I: I14 Branch 46 IFLT L276 - true
   */

  @Test
  public void test15()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      int int0 = tFloatIntHashMap0.remove((float) 0);
      assertEquals(0, int0);
      assertEquals(true, tFloatIntHashMap0.isEmpty());
      assertEquals(23, tFloatIntHashMap0.capacity());
  }

  //Test case number: 16
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.keys()[F: I34 Branch 48 IF_ICMPNE L304 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.keys()[F: I27 Branch 47 IFLE L303 - true
   * 3 gnu.trove.map.hash.TFloatIntHashMap.keys()[F: I27 Branch 47 IFLE L303 - false
   * 4 gnu.trove.map.hash.TFloatIntHashMap.keys()[F: I34 Branch 48 IF_ICMPNE L304 - true
   */

  @Test
  public void test16()  throws Throwable  {
      float[] floatArray0 = new float[4];
      int[] intArray0 = new int[10];
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap(floatArray0, intArray0);
      float[] floatArray1 = tFloatIntHashMap0.keys();
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals(23, tFloatIntHashMap0.capacity());
      assertNotNull(floatArray1);
      assertEquals("{0.0=0}", tFloatIntHashMap0.toString());
  }

  //Test case number: 17
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.keys([F)[F: I10 Branch 49 IF_ICMPGE L315 - true
   * 2 gnu.trove.map.hash.TFloatIntHashMap.keys([F)[F: I37 Branch 50 IFLE L322 - true
   * 3 gnu.trove.map.hash.TFloatIntHashMap.keys([F)[F: I37 Branch 50 IFLE L322 - false
   * 4 gnu.trove.map.hash.TFloatIntHashMap.keys([F)[F: I44 Branch 51 IF_ICMPNE L323 - true
   */

  @Test
  public void test17()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      float[] floatArray0 = tFloatIntHashMap0.keys(tFloatIntHashMap0._set);
      assertEquals(23, tFloatIntHashMap0.capacity());
      assertNotNull(floatArray0);
  }

  //Test case number: 18
  /*
   * 8 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.keys([F)[F: I10 Branch 49 IF_ICMPGE L315 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.keys([F)[F: I44 Branch 51 IF_ICMPNE L323 - false
   * 3 gnu.trove.map.hash.TFloatIntHashMap.keys()[F: I27 Branch 47 IFLE L303 - true
   * 4 gnu.trove.map.hash.TFloatIntHashMap.keys()[F: I27 Branch 47 IFLE L303 - false
   * 5 gnu.trove.map.hash.TFloatIntHashMap.keys()[F: I34 Branch 48 IF_ICMPNE L304 - true
   * 6 gnu.trove.map.hash.TFloatIntHashMap.keys([F)[F: I37 Branch 50 IFLE L322 - true
   * 7 gnu.trove.map.hash.TFloatIntHashMap.keys([F)[F: I37 Branch 50 IFLE L322 - false
   * 8 gnu.trove.map.hash.TFloatIntHashMap.keys([F)[F: I44 Branch 51 IF_ICMPNE L323 - true
   */

  @Test
  public void test18()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap((-554));
      float[] floatArray0 = tFloatIntHashMap0.keys();
      tFloatIntHashMap0.adjustOrPutValue((-1400.1345F), 1241, (-917));
      tFloatIntHashMap0.keys(floatArray0);
      assertEquals("{-1400.1345=-917}", tFloatIntHashMap0.toString());
      assertEquals(1, tFloatIntHashMap0.size());
  }

  //Test case number: 19
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.values()[I: I34 Branch 53 IF_ICMPNE L344 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.values()[I: I27 Branch 52 IFLE L343 - true
   * 3 gnu.trove.map.hash.TFloatIntHashMap.values()[I: I27 Branch 52 IFLE L343 - false
   * 4 gnu.trove.map.hash.TFloatIntHashMap.values()[I: I34 Branch 53 IF_ICMPNE L344 - true
   */

  @Test
  public void test19()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      tFloatIntHashMap0.adjustOrPutValue(0.0F, 166, 83);
      tFloatIntHashMap0.values();
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals("{0.0=83}", tFloatIntHashMap0.toString());
  }

  //Test case number: 20
  /*
   * 5 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.values([I)[I: I10 Branch 54 IF_ICMPGE L355 - true
   * 2 gnu.trove.map.hash.TFloatIntHashMap.values([I)[I: I37 Branch 55 IFLE L362 - true
   * 3 gnu.trove.map.hash.TFloatIntHashMap.values([I)[I: I37 Branch 55 IFLE L362 - false
   * 4 gnu.trove.map.hash.TFloatIntHashMap.values([I)[I: I44 Branch 56 IF_ICMPNE L363 - true
   * 5 gnu.trove.map.hash.TFloatIntHashMap.values([I)[I: I44 Branch 56 IF_ICMPNE L363 - false
   */

  @Test
  public void test20()  throws Throwable  {
      float[] floatArray0 = new float[8];
      int[] intArray0 = new int[7];
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap(floatArray0, intArray0);
      int[] intArray1 = tFloatIntHashMap0.values(intArray0);
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals(17, tFloatIntHashMap0.capacity());
      assertSame(intArray1, intArray0);
      assertNotNull(intArray1);
      assertEquals("{0.0=0}", tFloatIntHashMap0.toString());
  }

  //Test case number: 21
  /*
   * 5 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.containsValue(I)Z: I18 Branch 57 IFLE L376 - true
   * 2 gnu.trove.map.hash.TFloatIntHashMap.containsValue(I)Z: I18 Branch 57 IFLE L376 - false
   * 3 gnu.trove.map.hash.TFloatIntHashMap.containsValue(I)Z: I25 Branch 58 IF_ICMPNE L377 - true
   * 4 gnu.trove.map.hash.TFloatIntHashMap.containsValue(I)Z: I25 Branch 58 IF_ICMPNE L377 - false
   * 5 gnu.trove.map.hash.TFloatIntHashMap.containsValue(I)Z: I30 Branch 59 IF_ICMPNE L377 - true
   */

  @Test
  public void test21()  throws Throwable  {
      float[] floatArray0 = new float[4];
      int[] intArray0 = new int[10];
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap(floatArray0, intArray0);
      boolean boolean0 = tFloatIntHashMap0.containsValue((-363));
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals(false, boolean0);
      assertEquals("{0.0=0}", tFloatIntHashMap0.toString());
      assertEquals(23, tFloatIntHashMap0.capacity());
  }

  //Test case number: 22
  /*
   * 5 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.containsValue(I)Z: I30 Branch 59 IF_ICMPNE L377 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.putIfAbsent(FI)I: I9 Branch 39 IFGE L205 - true
   * 3 gnu.trove.map.hash.TFloatIntHashMap.containsValue(I)Z: I18 Branch 57 IFLE L376 - false
   * 4 gnu.trove.map.hash.TFloatIntHashMap.containsValue(I)Z: I25 Branch 58 IF_ICMPNE L377 - true
   * 5 gnu.trove.map.hash.TFloatIntHashMap.containsValue(I)Z: I25 Branch 58 IF_ICMPNE L377 - false
   */

  @Test
  public void test22()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      tFloatIntHashMap0.putIfAbsent((float) 1, 1);
      boolean boolean0 = tFloatIntHashMap0.containsValue(1);
      assertEquals(false, tFloatIntHashMap0.isEmpty());
      assertEquals(true, boolean0);
  }

  //Test case number: 23
  /*
   * 3 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z: I18 Branch 60 IFLE L407 - true
   * 2 gnu.trove.map.hash.TFloatIntHashMap.forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z: I18 Branch 60 IFLE L407 - false
   * 3 gnu.trove.map.hash.TFloatIntHashMap.forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z: I25 Branch 61 IF_ICMPNE L408 - true
   */

  @Test
  public void test23()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      boolean boolean0 = tFloatIntHashMap0.forEachValue((TIntProcedure) null);
      assertEquals(true, boolean0);
      assertEquals(23, tFloatIntHashMap0.capacity());
  }

  //Test case number: 24
  /*
   * 3 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z: I25 Branch 61 IF_ICMPNE L408 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z: I18 Branch 60 IFLE L407 - false
   * 3 gnu.trove.map.hash.TFloatIntHashMap.forEachValue(Lgnu/trove/procedure/TIntProcedure;)Z: I25 Branch 61 IF_ICMPNE L408 - true
   */

  @Test
  public void test24()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      tFloatIntHashMap0.adjustOrPutValue(0.0F, (-1), 1506);
      // Undeclared exception!
      try {
        tFloatIntHashMap0.forEachValue((TIntProcedure) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 25
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.transformValues(Lgnu/trove/function/TIntFunction;)V: I18 Branch 66 IFLE L434 - true
   * 2 gnu.trove.map.hash.TFloatIntHashMap.transformValues(Lgnu/trove/function/TIntFunction;)V: I18 Branch 66 IFLE L434 - false
   * 3 gnu.trove.map.hash.TFloatIntHashMap.transformValues(Lgnu/trove/function/TIntFunction;)V: I25 Branch 67 IF_ICMPNE L435 - true
   * 4 gnu.trove.map.hash.TFloatIntHashMap.<init>(I)V: root-Branch
   */

  @Test
  public void test25()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap((-322));
      tFloatIntHashMap0.transformValues((TIntFunction) null);
      assertEquals("{}", tFloatIntHashMap0.toString());
  }

  //Test case number: 26
  /*
   * 3 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.transformValues(Lgnu/trove/function/TIntFunction;)V: I25 Branch 67 IF_ICMPNE L435 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.transformValues(Lgnu/trove/function/TIntFunction;)V: I18 Branch 66 IFLE L434 - false
   * 3 gnu.trove.map.hash.TFloatIntHashMap.transformValues(Lgnu/trove/function/TIntFunction;)V: I25 Branch 67 IF_ICMPNE L435 - true
   */

  @Test
  public void test26()  throws Throwable  {
      float[] floatArray0 = new float[9];
      int[] intArray0 = new int[3];
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap(floatArray0, intArray0);
      // Undeclared exception!
      try {
        tFloatIntHashMap0.transformValues((TIntFunction) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 27
  /*
   * 3 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.retainEntries(Lgnu/trove/procedure/TFloatIntProcedure;)Z: I31 Branch 68 IFLE L453 - true
   * 2 gnu.trove.map.hash.TFloatIntHashMap.retainEntries(Lgnu/trove/procedure/TFloatIntProcedure;)Z: I31 Branch 68 IFLE L453 - false
   * 3 gnu.trove.map.hash.TFloatIntHashMap.retainEntries(Lgnu/trove/procedure/TFloatIntProcedure;)Z: I38 Branch 69 IF_ICMPNE L454 - true
   */

  @Test
  public void test27()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      boolean boolean0 = tFloatIntHashMap0.retainEntries((TFloatIntProcedure) null);
      assertEquals(false, boolean0);
      assertEquals(23, tFloatIntHashMap0.capacity());
  }

  //Test case number: 28
  /*
   * 2 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.adjustOrPutValue(FII)I: I9 Branch 72 IFGE L491 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.adjustOrPutValue(FII)I: I58 Branch 73 IFEQ L502 - true
   */

  @Test
  public void test28()  throws Throwable  {
      float[] floatArray0 = new float[10];
      floatArray0[1] = 1.0F;
      int[] intArray0 = new int[7];
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap(floatArray0, intArray0);
      int int0 = tFloatIntHashMap0.adjustOrPutValue((float) 1, 0, 0);
      assertEquals(23, tFloatIntHashMap0.capacity());
      assertEquals(0, int0);
      assertEquals(2, tFloatIntHashMap0.size());
  }

  //Test case number: 29
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I4 Branch 74 IFNE L1184 - false
   */

  @Test
  public void test29()  throws Throwable  {
      float[] floatArray0 = new float[10];
      int[] intArray0 = new int[7];
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap(floatArray0, intArray0);
      boolean boolean0 = tFloatIntHashMap0.equals((Object) "");
      assertEquals(23, tFloatIntHashMap0.capacity());
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals(false, boolean0);
      assertEquals("{0.0=0}", tFloatIntHashMap0.toString());
  }

  //Test case number: 30
  /*
   * 2 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I20 Branch 75 IF_ICMPEQ L1188 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.<init>(IFFI)V: root-Branch
   */

  @Test
  public void test30()  throws Throwable  {
      float[] floatArray0 = new float[4];
      int[] intArray0 = new int[10];
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap(floatArray0, intArray0);
      TFloatIntHashMap tFloatIntHashMap1 = new TFloatIntHashMap(40, 0.0F, 0.0F, 0);
      boolean boolean0 = tFloatIntHashMap0.equals((Object) tFloatIntHashMap1);
      assertEquals(23, tFloatIntHashMap0.capacity());
      assertFalse(tFloatIntHashMap1.equals(tFloatIntHashMap0));
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals("{0.0=0}", tFloatIntHashMap0.toString());
      assertEquals(false, boolean0);
  }

  //Test case number: 31
  /*
   * 20 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I90 Branch 80 IF_ICMPEQ L1200 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap$TFloatIntHashIterator.value()I: root-Branch
   * 3 gnu.trove.map.hash.TFloatIntHashMap$TFloatIntHashIterator.key()F: root-Branch
   * 4 gnu.trove.map.hash.TFloatIntHashMap$TFloatIntHashIterator.<init>(Lgnu/trove/map/hash/TFloatIntHashMap;Lgnu/trove/map/hash/TFloatIntHashMap;)V: root-Branch
   * 5 gnu.trove.map.hash.TFloatIntHashMap$TFloatIntHashIterator.advance()V: root-Branch
   * 6 gnu.trove.map.hash.TFloatIntHashMap.iterator()Lgnu/trove/iterator/TFloatIntIterator;: root-Branch
   * 7 gnu.trove.map.hash.TFloatIntHashMap.get(F)I: I9 Branch 44 IFGE L253 - true
   * 8 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I4 Branch 74 IFNE L1184 - true
   * 9 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I20 Branch 75 IF_ICMPEQ L1188 - true
   * 10 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I53 Branch 76 IFLE L1195 - false
   * 11 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I60 Branch 77 IF_ICMPNE L1196 - true
   * 12 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I60 Branch 77 IF_ICMPNE L1196 - false
   * 13 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I84 Branch 78 IF_ICMPEQ L1200 - false
   * 14 gnu.trove.map.hash.TFloatIntHashMap.equals(Ljava/lang/Object;)Z: I87 Branch 79 IF_ICMPEQ L1200 - false
   * 15 gnu.trove.map.hash.TFloatIntHashMap.putAll(Lgnu/trove/map/TFloatIntMap;)V: I15 Branch 43 IFEQ L243 - true
   * 16 gnu.trove.map.hash.TFloatIntHashMap.putAll(Lgnu/trove/map/TFloatIntMap;)V: I15 Branch 43 IFEQ L243 - false
   * 17 gnu.trove.map.hash.TFloatIntHashMap.<init>(Lgnu/trove/map/TFloatIntMap;)V: I10 Branch 34 IFEQ L133 - false
   * 18 gnu.trove.map.hash.TFloatIntHashMap.<init>(Lgnu/trove/map/TFloatIntMap;)V: I40 Branch 35 IFEQ L139 - true
   * 19 gnu.trove.map.hash.TFloatIntHashMap.<init>(Lgnu/trove/map/TFloatIntMap;)V: I52 Branch 36 IFEQ L143 - true
   * 20 gnu.trove.map.hash.TFloatIntHashMap.adjustValue(FI)Z: I9 Branch 71 IFGE L477 - true
   */

  @Test
  public void test31()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      tFloatIntHashMap0.adjustOrPutValue(0.0F, 166, 83);
      TFloatIntHashMap tFloatIntHashMap1 = new TFloatIntHashMap((TFloatIntMap) tFloatIntHashMap0);
      tFloatIntHashMap0.adjustValue(0.0F, 240);
      boolean boolean0 = tFloatIntHashMap0.equals((Object) tFloatIntHashMap1);
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals(false, boolean0);
  }

  //Test case number: 32
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.hashCode()I: I18 Branch 81 IFLE L1216 - true
   * 2 gnu.trove.map.hash.TFloatIntHashMap.hashCode()I: I18 Branch 81 IFLE L1216 - false
   * 3 gnu.trove.map.hash.TFloatIntHashMap.hashCode()I: I25 Branch 82 IF_ICMPNE L1217 - true
   * 4 gnu.trove.map.hash.TFloatIntHashMap.<init>()V: root-Branch
   */

  @Test
  public void test32()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap();
      int int0 = tFloatIntHashMap0.hashCode();
      assertEquals(0, int0);
      assertEquals(23, tFloatIntHashMap0.capacity());
  }

  //Test case number: 33
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.hashCode()I: I25 Branch 82 IF_ICMPNE L1217 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.hashCode()I: I18 Branch 81 IFLE L1216 - true
   * 3 gnu.trove.map.hash.TFloatIntHashMap.hashCode()I: I18 Branch 81 IFLE L1216 - false
   * 4 gnu.trove.map.hash.TFloatIntHashMap.hashCode()I: I25 Branch 82 IF_ICMPNE L1217 - true
   */

  @Test
  public void test33()  throws Throwable  {
      float[] floatArray0 = new float[4];
      int[] intArray0 = new int[10];
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap(floatArray0, intArray0);
      int int0 = tFloatIntHashMap0.hashCode();
      assertEquals(23, tFloatIntHashMap0.capacity());
      assertEquals(1, tFloatIntHashMap0.size());
      assertEquals(0, int0);
  }

  //Test case number: 34
  /*
   * 13 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap$1.execute(FI)Z: I4 Branch 115 IFEQ L1233 - true
   * 2 gnu.trove.map.hash.TFloatIntHashMap.toString()Ljava/lang/String;: root-Branch
   * 3 gnu.trove.map.hash.TFloatIntHashMap.<init>([F[I)V: I24 Branch 33 IF_ICMPGE L119 - true
   * 4 gnu.trove.map.hash.TFloatIntHashMap.<init>([F[I)V: I24 Branch 33 IF_ICMPGE L119 - false
   * 5 gnu.trove.map.hash.TFloatIntHashMap.doPut(FII)I: I12 Branch 40 IFGE L214 - false
   * 6 gnu.trove.map.hash.TFloatIntHashMap.doPut(FII)I: I41 Branch 41 IFEQ L221 - true
   * 7 gnu.trove.map.hash.TFloatIntHashMap.forEachEntry(Lgnu/trove/procedure/TFloatIntProcedure;)Z: I23 Branch 63 IFLE L421 - true
   * 8 gnu.trove.map.hash.TFloatIntHashMap.forEachEntry(Lgnu/trove/procedure/TFloatIntProcedure;)Z: I23 Branch 63 IFLE L421 - false
   * 9 gnu.trove.map.hash.TFloatIntHashMap.forEachEntry(Lgnu/trove/procedure/TFloatIntProcedure;)Z: I30 Branch 64 IF_ICMPNE L422 - true
   * 10 gnu.trove.map.hash.TFloatIntHashMap.forEachEntry(Lgnu/trove/procedure/TFloatIntProcedure;)Z: I30 Branch 64 IF_ICMPNE L422 - false
   * 11 gnu.trove.map.hash.TFloatIntHashMap.forEachEntry(Lgnu/trove/procedure/TFloatIntProcedure;)Z: I39 Branch 65 IFNE L422 - true
   * 12 gnu.trove.map.hash.TFloatIntHashMap$1.<init>(Lgnu/trove/map/hash/TFloatIntHashMap;Ljava/lang/StringBuilder;)V: root-Branch
   * 13 gnu.trove.map.hash.TFloatIntHashMap$1.execute(FI)Z: I4 Branch 115 IFEQ L1233 - false
   */

  @Test
  public void test34()  throws Throwable  {
      float[] floatArray0 = new float[8];
      floatArray0[0] = 1702.9539F;
      int[] intArray0 = new int[7];
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap(floatArray0, intArray0);
      String string0 = tFloatIntHashMap0.toString();
      assertEquals(17, tFloatIntHashMap0.capacity());
      assertNotNull(string0);
      assertEquals(2, tFloatIntHashMap0.size());
      assertEquals("{1702.9539=0, 0.0=0}", string0);
  }

  //Test case number: 35
  /*
   * 13 covered goals:
   * 1 gnu.trove.map.hash.TFloatIntHashMap.readExternal(Ljava/io/ObjectInput;)V: I25 Branch 85 IFLE L1281 - false
   * 2 gnu.trove.map.hash.TFloatIntHashMap.put(FI)I: root-Branch
   * 3 gnu.trove.map.hash.TFloatIntHashMap.<init>(IF)V: root-Branch
   * 4 gnu.trove.map.hash.TFloatIntHashMap.setUp(I)I: root-Branch
   * 5 gnu.trove.map.hash.TFloatIntHashMap.writeExternal(Ljava/io/ObjectOutput;)V: I25 Branch 83 IFLE L1259 - true
   * 6 gnu.trove.map.hash.TFloatIntHashMap.writeExternal(Ljava/io/ObjectOutput;)V: I25 Branch 83 IFLE L1259 - false
   * 7 gnu.trove.map.hash.TFloatIntHashMap.writeExternal(Ljava/io/ObjectOutput;)V: I33 Branch 84 IF_ICMPNE L1260 - true
   * 8 gnu.trove.map.hash.TFloatIntHashMap.writeExternal(Ljava/io/ObjectOutput;)V: I33 Branch 84 IF_ICMPNE L1260 - false
   * 9 gnu.trove.map.hash.TFloatIntHashMap.readExternal(Ljava/io/ObjectInput;)V: I25 Branch 85 IFLE L1281 - true
   * 10 gnu.trove.map.hash.TFloatIntHashMap.doPut(FII)I: I12 Branch 40 IFGE L214 - true
   * 11 gnu.trove.map.hash.TFloatIntHashMap.doPut(FII)I: I41 Branch 41 IFEQ L221 - false
   * 12 gnu.trove.map.hash.TFloatIntHashMap.adjustOrPutValue(FII)I: I9 Branch 72 IFGE L491 - true
   * 13 gnu.trove.map.hash.TFloatIntHashMap.adjustOrPutValue(FII)I: I58 Branch 73 IFEQ L502 - false
   */

  @Test
  public void test35()  throws Throwable  {
      TFloatIntHashMap tFloatIntHashMap0 = new TFloatIntHashMap(15661423, 15661423);
      PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
      PipedInputStream pipedInputStream0 = new PipedInputStream(pipedOutputStream0, 144);
      ObjectOutputStream objectOutputStream0 = new ObjectOutputStream((OutputStream) pipedOutputStream0);
      tFloatIntHashMap0.adjustOrPutValue((-130.7241F), (-458), 366);
      tFloatIntHashMap0.writeExternal((ObjectOutput) objectOutputStream0);
      objectOutputStream0.reset();
      ObjectInputStream objectInputStream0 = new ObjectInputStream((InputStream) pipedInputStream0);
      tFloatIntHashMap0.readExternal((ObjectInput) objectInputStream0);
  }
}
