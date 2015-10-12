/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.map.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.TDoubleCollection;
import gnu.trove.function.TDoubleFunction;
import gnu.trove.map.TCharDoubleMap;
import gnu.trove.map.hash.TCharDoubleHashMap;
import gnu.trove.procedure.TCharDoubleProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.set.TCharSet;
import java.io.BufferedOutputStream;
import java.io.IOException;
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

public class TCharDoubleHashMapEvoSuite_Branch {

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
   * 9 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.removeAt(I)V: root-Branch
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.<init>(IF)V: root-Branch
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.rehash(I)V: I46 Branch 37 IFLE L185 - true
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.rehash(I)V: I46 Branch 37 IFLE L185 - false
   * 5 gnu.trove.map.hash.TCharDoubleHashMap.rehash(I)V: I53 Branch 38 IF_ICMPNE L186 - true
   * 6 gnu.trove.map.hash.TCharDoubleHashMap.remove(C)D: I14 Branch 46 IFLT L276 - false
   * 7 gnu.trove.map.hash.TCharDoubleHashMap.adjustOrPutValue(CDD)D: I9 Branch 72 IFGE L491 - true
   * 8 gnu.trove.map.hash.TCharDoubleHashMap.adjustOrPutValue(CDD)D: I58 Branch 73 IFEQ L502 - false
   * 9 gnu.trove.map.hash.TCharDoubleHashMap.setUp(I)I: root-Branch
   */

  @Test
  public void test0()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(Integer.MAX_VALUE, Integer.MAX_VALUE);
      tCharDoubleHashMap0.adjustOrPutValue('I', (double) Integer.MAX_VALUE, 1361.4265582604842);
      assertEquals("{I=1361.4265582604842}", tCharDoubleHashMap0.toString());
      
      double double0 = tCharDoubleHashMap0.remove('I');
      assertEquals(1361.4265582604842, double0, 0.01D);
  }

  //Test case number: 1
  /*
   * 7 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.iterator()Lgnu/trove/iterator/TCharDoubleIterator;: root-Branch
   * 2 gnu.trove.map.hash.TCharDoubleHashMap$TCharDoubleHashIterator.<init>(Lgnu/trove/map/hash/TCharDoubleHashMap;Lgnu/trove/map/hash/TCharDoubleHashMap;)V: root-Branch
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.<init>(Lgnu/trove/map/TCharDoubleMap;)V: I10 Branch 34 IFEQ L133 - false
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.<init>(Lgnu/trove/map/TCharDoubleMap;)V: I38 Branch 35 IFEQ L139 - false
   * 5 gnu.trove.map.hash.TCharDoubleHashMap.<init>(Lgnu/trove/map/TCharDoubleMap;)V: I52 Branch 36 IFEQ L143 - false
   * 6 gnu.trove.map.hash.TCharDoubleHashMap.putAll(Lgnu/trove/map/TCharDoubleMap;)V: I15 Branch 43 IFEQ L243 - true
   * 7 gnu.trove.map.hash.TCharDoubleHashMap.<init>(IFCD)V: root-Branch
   */

  @Test
  public void test1()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap((-2), (float) (-2), 'F', (double) (-2));
      TCharDoubleHashMap tCharDoubleHashMap1 = new TCharDoubleHashMap((TCharDoubleMap) tCharDoubleHashMap0);
      assertEquals('F', tCharDoubleHashMap1.getNoEntryKey());
      assertEquals(3, tCharDoubleHashMap1.capacity());
      assertEquals((-2.0), tCharDoubleHashMap1.getNoEntryValue(), 0.01D);
  }

  //Test case number: 2
  /*
   * 2 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.clear()V: root-Branch
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.<init>()V: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap();
      tCharDoubleHashMap0.clear();
      assertEquals(23, tCharDoubleHashMap0.capacity());
      assertEquals("{}", tCharDoubleHashMap0.toString());
  }

  //Test case number: 3
  /*
   * 2 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.increment(C)Z: root-Branch
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.adjustValue(CD)Z: I9 Branch 71 IFGE L477 - false
   */

  @Test
  public void test3()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap((-690), (-690));
      boolean boolean0 = tCharDoubleHashMap0.increment('O');
      assertEquals(false, boolean0);
  }

  //Test case number: 4
  /*
   * 3 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.keySet()Lgnu/trove/set/TCharSet;: root-Branch
   * 2 gnu.trove.map.hash.TCharDoubleHashMap$TKeyView.<init>(Lgnu/trove/map/hash/TCharDoubleHashMap;)V: root-Branch
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.<init>(I)V: root-Branch
   */

  @Test
  public void test4()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap((-251));
      TCharSet tCharSet0 = tCharDoubleHashMap0.keySet();
      assertEquals('\u0000', tCharSet0.getNoEntryValue());
  }

  //Test case number: 5
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.containsKey(C)Z: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap();
      tCharDoubleHashMap0.containsKey('L');
      assertEquals(23, tCharDoubleHashMap0.capacity());
  }

  //Test case number: 6
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.forEachKey(Lgnu/trove/procedure/TCharProcedure;)Z: root-Branch
   */

  @Test
  public void test6()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap((-9));
      boolean boolean0 = tCharDoubleHashMap0.forEachKey((TCharProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 7
  /*
   * 5 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.put(CD)D: root-Branch
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.doPut(CDI)D: I12 Branch 40 IFGE L214 - true
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.doPut(CDI)D: I41 Branch 41 IFEQ L221 - false
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.putAll(Ljava/util/Map;)V: I15 Branch 42 IFEQ L233 - true
   * 5 gnu.trove.map.hash.TCharDoubleHashMap.putAll(Ljava/util/Map;)V: I15 Branch 42 IFEQ L233 - false
   */

  @Test
  public void test7()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap();
      HashMap<Character, Double> hashMap0 = new HashMap<Character, Double>();
      Double double0 = new Double((double) 0.5F);
      hashMap0.put((Character) '-', double0);
      tCharDoubleHashMap0.putAll((Map<? extends Character, ? extends Double>) hashMap0);
      assertEquals(false, tCharDoubleHashMap0.isEmpty());
      assertEquals("{-=0.5}", tCharDoubleHashMap0.toString());
  }

  //Test case number: 8
  /*
   * 2 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.valueCollection()Lgnu/trove/TDoubleCollection;: root-Branch
   * 2 gnu.trove.map.hash.TCharDoubleHashMap$TValueView.<init>(Lgnu/trove/map/hash/TCharDoubleHashMap;)V: root-Branch
   */

  @Test
  public void test8()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap((-1979), (-1979));
      TDoubleCollection tDoubleCollection0 = tCharDoubleHashMap0.valueCollection();
      assertEquals(0, tDoubleCollection0.size());
  }

  //Test case number: 9
  /*
   * 3 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.<init>(Lgnu/trove/map/TCharDoubleMap;)V: I38 Branch 35 IFEQ L139 - true
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.<init>(Lgnu/trove/map/TCharDoubleMap;)V: I52 Branch 36 IFEQ L143 - true
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.<init>(Lgnu/trove/map/TCharDoubleMap;)V: I10 Branch 34 IFEQ L133 - false
   */

  @Test
  public void test9()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap();
      TCharDoubleHashMap tCharDoubleHashMap1 = new TCharDoubleHashMap((TCharDoubleMap) tCharDoubleHashMap0);
      assertEquals(0.0, tCharDoubleHashMap1.getNoEntryValue(), 0.01D);
      assertEquals(23, tCharDoubleHashMap1.capacity());
  }

  //Test case number: 10
  /*
   * 3 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.putIfAbsent(CD)D: I9 Branch 39 IFGE L205 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.rehash(I)V: I53 Branch 38 IF_ICMPNE L186 - false
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.putIfAbsent(CD)D: I9 Branch 39 IFGE L205 - true
   */

  @Test
  public void test10()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap((-1979), (-1979));
      tCharDoubleHashMap0.putIfAbsent('B', (double) (-1979));
      double double0 = tCharDoubleHashMap0.putIfAbsent('B', 'B');
      assertEquals(false, tCharDoubleHashMap0.isEmpty());
      assertEquals((-1979.0), double0, 0.01D);
  }

  //Test case number: 11
  /*
   * 10 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.get(C)D: I9 Branch 44 IFGE L253 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I4 Branch 74 IFNE L1184 - true
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I20 Branch 75 IF_ICMPEQ L1188 - true
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I53 Branch 76 IFLE L1195 - true
   * 5 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I53 Branch 76 IFLE L1195 - false
   * 6 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I60 Branch 77 IF_ICMPNE L1196 - true
   * 7 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I60 Branch 77 IF_ICMPNE L1196 - false
   * 8 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I85 Branch 78 IFEQ L1200 - false
   * 9 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I89 Branch 79 IFEQ L1200 - false
   * 10 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I93 Branch 80 IFEQ L1200 - true
   */

  @Test
  public void test11()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(0, (float) 0, '8', (double) '8');
      TCharDoubleHashMap tCharDoubleHashMap1 = new TCharDoubleHashMap((int) '8', (float) '8');
      tCharDoubleHashMap1.put('8', (-97.67506829647759));
      tCharDoubleHashMap0.put('Q', 'Q');
      boolean boolean0 = tCharDoubleHashMap0.equals((Object) tCharDoubleHashMap1);
      assertEquals("{8=-97.67506829647759}", tCharDoubleHashMap1.toString());
      assertEquals(true, boolean0);
  }

  //Test case number: 12
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.isEmpty()Z: I5 Branch 45 IF_ICMPNE L268 - true
   */

  @Test
  public void test12()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(Integer.MAX_VALUE, Integer.MAX_VALUE);
      tCharDoubleHashMap0.adjustOrPutValue('I', (double) Integer.MAX_VALUE, 1361.4265582604842);
      boolean boolean0 = tCharDoubleHashMap0.isEmpty();
      assertEquals(1, tCharDoubleHashMap0.size());
      assertEquals(false, boolean0);
  }

  //Test case number: 13
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.isEmpty()Z: I5 Branch 45 IF_ICMPNE L268 - false
   */

  @Test
  public void test13()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap((-690), (-690));
      boolean boolean0 = tCharDoubleHashMap0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 14
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.remove(C)D: I14 Branch 46 IFLT L276 - true
   */

  @Test
  public void test14()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(0, (float) 0, '8', (double) '8');
      double double0 = tCharDoubleHashMap0.remove('G');
      assertEquals(56.0, double0, 0.01D);
  }

  //Test case number: 15
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.keys()[C: I34 Branch 48 IF_ICMPNE L304 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.keys()[C: I27 Branch 47 IFLE L303 - true
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.keys()[C: I27 Branch 47 IFLE L303 - false
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.keys()[C: I34 Branch 48 IF_ICMPNE L304 - true
   */

  @Test
  public void test15()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(Integer.MAX_VALUE, Integer.MAX_VALUE);
      tCharDoubleHashMap0.adjustOrPutValue('I', (double) Integer.MAX_VALUE, 1361.4265582604842);
      tCharDoubleHashMap0.keys();
      assertEquals("{I=1361.4265582604842}", tCharDoubleHashMap0.toString());
      assertEquals(1, tCharDoubleHashMap0.size());
  }

  //Test case number: 16
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.keys([C)[C: I10 Branch 49 IF_ICMPGE L315 - true
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.keys([C)[C: I37 Branch 50 IFLE L322 - true
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.keys([C)[C: I37 Branch 50 IFLE L322 - false
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.keys([C)[C: I44 Branch 51 IF_ICMPNE L323 - true
   */

  @Test
  public void test16()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(0, (float) 0, '8', (double) '8');
      char[] charArray0 = tCharDoubleHashMap0.keys(tCharDoubleHashMap0._set);
      assertNotNull(charArray0);
  }

  //Test case number: 17
  /*
   * 14 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.keys([C)[C: I10 Branch 49 IF_ICMPGE L315 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.keys([C)[C: I44 Branch 51 IF_ICMPNE L323 - false
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.iterator()Lgnu/trove/iterator/TCharDoubleIterator;: root-Branch
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.putAll(Lgnu/trove/map/TCharDoubleMap;)V: I15 Branch 43 IFEQ L243 - true
   * 5 gnu.trove.map.hash.TCharDoubleHashMap.putAll(Lgnu/trove/map/TCharDoubleMap;)V: I15 Branch 43 IFEQ L243 - false
   * 6 gnu.trove.map.hash.TCharDoubleHashMap.keys([C)[C: I37 Branch 50 IFLE L322 - true
   * 7 gnu.trove.map.hash.TCharDoubleHashMap.keys([C)[C: I37 Branch 50 IFLE L322 - false
   * 8 gnu.trove.map.hash.TCharDoubleHashMap.keys([C)[C: I44 Branch 51 IF_ICMPNE L323 - true
   * 9 gnu.trove.map.hash.TCharDoubleHashMap$TCharDoubleHashIterator.<init>(Lgnu/trove/map/hash/TCharDoubleHashMap;Lgnu/trove/map/hash/TCharDoubleHashMap;)V: root-Branch
   * 10 gnu.trove.map.hash.TCharDoubleHashMap$TCharDoubleHashIterator.value()D: root-Branch
   * 11 gnu.trove.map.hash.TCharDoubleHashMap$TCharDoubleHashIterator.advance()V: root-Branch
   * 12 gnu.trove.map.hash.TCharDoubleHashMap$TCharDoubleHashIterator.key()C: root-Branch
   * 13 gnu.trove.map.hash.TCharDoubleHashMap.<init>([C[D)V: I24 Branch 33 IF_ICMPGE L119 - true
   * 14 gnu.trove.map.hash.TCharDoubleHashMap.<init>([C[D)V: I24 Branch 33 IF_ICMPGE L119 - false
   */

  @Test
  public void test17()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(522);
      char[] charArray0 = new char[1];
      double[] doubleArray0 = new double[4];
      tCharDoubleHashMap0.put('6', (-10.420498717768304));
      TCharDoubleHashMap tCharDoubleHashMap1 = new TCharDoubleHashMap(charArray0, doubleArray0);
      tCharDoubleHashMap0.putAll((TCharDoubleMap) tCharDoubleHashMap1);
      tCharDoubleHashMap0.keys(charArray0);
      assertEquals("{6=-10.420498717768304, \u0000=0.0}", tCharDoubleHashMap0.toString());
      assertFalse(tCharDoubleHashMap0.equals(tCharDoubleHashMap1));
  }

  //Test case number: 18
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.values()[D: I34 Branch 53 IF_ICMPNE L344 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.values()[D: I27 Branch 52 IFLE L343 - true
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.values()[D: I27 Branch 52 IFLE L343 - false
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.values()[D: I34 Branch 53 IF_ICMPNE L344 - true
   */

  @Test
  public void test18()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap();
      tCharDoubleHashMap0.adjustOrPutValue('Y', 'Y', 'Y');
      tCharDoubleHashMap0.values();
      assertEquals(1, tCharDoubleHashMap0.size());
      assertEquals("{Y=89.0}", tCharDoubleHashMap0.toString());
  }

  //Test case number: 19
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.values([D)[D: I10 Branch 54 IF_ICMPGE L355 - true
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.values([D)[D: I37 Branch 55 IFLE L362 - true
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.values([D)[D: I37 Branch 55 IFLE L362 - false
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.values([D)[D: I44 Branch 56 IF_ICMPNE L363 - true
   */

  @Test
  public void test19()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(522);
      double[] doubleArray0 = new double[4];
      double[] doubleArray1 = tCharDoubleHashMap0.values(doubleArray0);
      assertNotNull(doubleArray1);
      assertSame(doubleArray1, doubleArray0);
      assertEquals(1117, tCharDoubleHashMap0.capacity());
  }

  //Test case number: 20
  /*
   * 8 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.values([D)[D: I10 Branch 54 IF_ICMPGE L355 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.values([D)[D: I44 Branch 56 IF_ICMPNE L363 - false
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.values([D)[D: I37 Branch 55 IFLE L362 - true
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.values([D)[D: I37 Branch 55 IFLE L362 - false
   * 5 gnu.trove.map.hash.TCharDoubleHashMap.values([D)[D: I44 Branch 56 IF_ICMPNE L363 - true
   * 6 gnu.trove.map.hash.TCharDoubleHashMap.values()[D: I27 Branch 52 IFLE L343 - true
   * 7 gnu.trove.map.hash.TCharDoubleHashMap.values()[D: I27 Branch 52 IFLE L343 - false
   * 8 gnu.trove.map.hash.TCharDoubleHashMap.values()[D: I34 Branch 53 IF_ICMPNE L344 - true
   */

  @Test
  public void test20()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(Integer.MAX_VALUE, Integer.MAX_VALUE);
      double[] doubleArray0 = tCharDoubleHashMap0.values();
      tCharDoubleHashMap0.adjustOrPutValue('I', (double) Integer.MAX_VALUE, 1361.4265582604842);
      tCharDoubleHashMap0.values(doubleArray0);
      assertEquals(false, tCharDoubleHashMap0.isEmpty());
      assertEquals("{I=1361.4265582604842}", tCharDoubleHashMap0.toString());
  }

  //Test case number: 21
  /*
   * 5 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.containsValue(D)Z: I25 Branch 58 IF_ICMPNE L377 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.containsValue(D)Z: I31 Branch 59 IFNE L377 - true
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.containsValue(D)Z: I18 Branch 57 IFLE L376 - true
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.containsValue(D)Z: I18 Branch 57 IFLE L376 - false
   * 5 gnu.trove.map.hash.TCharDoubleHashMap.containsValue(D)Z: I25 Branch 58 IF_ICMPNE L377 - true
   */

  @Test
  public void test21()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(0, (float) 0, '8', (double) '8');
      tCharDoubleHashMap0.put('Q', 'Q');
      boolean boolean0 = tCharDoubleHashMap0.containsValue(0.0);
      assertEquals(false, tCharDoubleHashMap0.isEmpty());
      assertEquals(false, boolean0);
  }

  //Test case number: 22
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.containsValue(D)Z: I31 Branch 59 IFNE L377 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.containsValue(D)Z: I18 Branch 57 IFLE L376 - false
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.containsValue(D)Z: I25 Branch 58 IF_ICMPNE L377 - true
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.containsValue(D)Z: I25 Branch 58 IF_ICMPNE L377 - false
   */

  @Test
  public void test22()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(0, (float) 0, '8', (double) '8');
      tCharDoubleHashMap0.putIfAbsent('8', (double) 0);
      boolean boolean0 = tCharDoubleHashMap0.containsValue(0.0);
      assertEquals(1, tCharDoubleHashMap0.size());
      assertEquals(true, boolean0);
  }

  //Test case number: 23
  /*
   * 3 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.forEachValue(Lgnu/trove/procedure/TDoubleProcedure;)Z: I18 Branch 60 IFLE L407 - true
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.forEachValue(Lgnu/trove/procedure/TDoubleProcedure;)Z: I18 Branch 60 IFLE L407 - false
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.forEachValue(Lgnu/trove/procedure/TDoubleProcedure;)Z: I25 Branch 61 IF_ICMPNE L408 - true
   */

  @Test
  public void test23()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap((int) '\u001D');
      boolean boolean0 = tCharDoubleHashMap0.forEachValue((TDoubleProcedure) null);
      assertEquals(67, tCharDoubleHashMap0.capacity());
      assertEquals(true, boolean0);
  }

  //Test case number: 24
  /*
   * 3 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.forEachValue(Lgnu/trove/procedure/TDoubleProcedure;)Z: I25 Branch 61 IF_ICMPNE L408 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.forEachValue(Lgnu/trove/procedure/TDoubleProcedure;)Z: I18 Branch 60 IFLE L407 - false
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.forEachValue(Lgnu/trove/procedure/TDoubleProcedure;)Z: I25 Branch 61 IF_ICMPNE L408 - true
   */

  @Test
  public void test24()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(9);
      tCharDoubleHashMap0.putIfAbsent('c', 'c');
      // Undeclared exception!
      try {
        tCharDoubleHashMap0.forEachValue((TDoubleProcedure) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 25
  /*
   * 3 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.transformValues(Lgnu/trove/function/TDoubleFunction;)V: I18 Branch 66 IFLE L434 - true
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.transformValues(Lgnu/trove/function/TDoubleFunction;)V: I18 Branch 66 IFLE L434 - false
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.transformValues(Lgnu/trove/function/TDoubleFunction;)V: I25 Branch 67 IF_ICMPNE L435 - true
   */

//   @Test
//   public void test25()  throws Throwable  {
//       TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap((-9));
//       tCharDoubleHashMap0.transformValues((TDoubleFunction) null);
//       assertEquals(0.5F, tCharDoubleHashMap0.getAutoCompactionFactor(), 0.01F);
//   }

  //Test case number: 26
  /*
   * 3 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.transformValues(Lgnu/trove/function/TDoubleFunction;)V: I25 Branch 67 IF_ICMPNE L435 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.transformValues(Lgnu/trove/function/TDoubleFunction;)V: I18 Branch 66 IFLE L434 - false
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.transformValues(Lgnu/trove/function/TDoubleFunction;)V: I25 Branch 67 IF_ICMPNE L435 - true
   */

  @Test
  public void test26()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap();
      tCharDoubleHashMap0.adjustOrPutValue('9', (double) '9', (double) 855);
      // Undeclared exception!
      try {
        tCharDoubleHashMap0.transformValues((TDoubleFunction) null);
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
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.retainEntries(Lgnu/trove/procedure/TCharDoubleProcedure;)Z: I31 Branch 68 IFLE L453 - true
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.retainEntries(Lgnu/trove/procedure/TCharDoubleProcedure;)Z: I31 Branch 68 IFLE L453 - false
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.retainEntries(Lgnu/trove/procedure/TCharDoubleProcedure;)Z: I38 Branch 69 IF_ICMPNE L454 - true
   */

  @Test
  public void test27()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap((int) '\u001D');
      boolean boolean0 = tCharDoubleHashMap0.retainEntries((TCharDoubleProcedure) null);
      assertEquals(67, tCharDoubleHashMap0.capacity());
      assertEquals(false, boolean0);
  }

  //Test case number: 28
  /*
   * 5 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.retainEntries(Lgnu/trove/procedure/TCharDoubleProcedure;)Z: I38 Branch 69 IF_ICMPNE L454 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.retainEntries(Lgnu/trove/procedure/TCharDoubleProcedure;)Z: I31 Branch 68 IFLE L453 - false
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.retainEntries(Lgnu/trove/procedure/TCharDoubleProcedure;)Z: I38 Branch 69 IF_ICMPNE L454 - true
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.doPut(CDI)D: I12 Branch 40 IFGE L214 - false
   * 5 gnu.trove.map.hash.TCharDoubleHashMap.doPut(CDI)D: I41 Branch 41 IFEQ L221 - true
   */

  @Test
  public void test28()  throws Throwable  {
      char[] charArray0 = new char[19];
      double[] doubleArray0 = new double[15];
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(charArray0, doubleArray0);
      // Undeclared exception!
      try {
        tCharDoubleHashMap0.retainEntries((TCharDoubleProcedure) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 29
  /*
   * 3 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.adjustValue(CD)Z: I9 Branch 71 IFGE L477 - true
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.doPut(CDI)D: I12 Branch 40 IFGE L214 - false
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.doPut(CDI)D: I41 Branch 41 IFEQ L221 - true
   */

  @Test
  public void test29()  throws Throwable  {
      char[] charArray0 = new char[4];
      double[] doubleArray0 = new double[8];
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(charArray0, doubleArray0);
      boolean boolean0 = tCharDoubleHashMap0.adjustValue('\u0000', 0.0);
      assertEquals(17, tCharDoubleHashMap0.capacity());
      assertEquals("{\u0000=0.0}", tCharDoubleHashMap0.toString());
      assertEquals(true, boolean0);
      assertEquals(1, tCharDoubleHashMap0.size());
  }

  //Test case number: 30
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.adjustOrPutValue(CDD)D: I9 Branch 72 IFGE L491 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.adjustOrPutValue(CDD)D: I58 Branch 73 IFEQ L502 - true
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.<init>([C[D)V: I24 Branch 33 IF_ICMPGE L119 - true
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.<init>([C[D)V: I24 Branch 33 IF_ICMPGE L119 - false
   */

  @Test
  public void test30()  throws Throwable  {
      char[] charArray0 = new char[10];
      charArray0[0] = 'N';
      double[] doubleArray0 = new double[2];
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(charArray0, doubleArray0);
      double double0 = tCharDoubleHashMap0.adjustOrPutValue('N', 'N', 'N');
      assertEquals("{N=78.0, \u0000=0.0}", tCharDoubleHashMap0.toString());
      assertEquals(78.0, double0, 0.01D);
  }

  //Test case number: 31
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I4 Branch 74 IFNE L1184 - false
   */

  @Test
  public void test31()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap((int) '\u001D');
      boolean boolean0 = tCharDoubleHashMap0.equals((Object) "/mnt/fastdata/ac1gf/Experiments_Major/apps/trove-3.0.2/=F]UaFvUnUf/=F]UaFvUnUf");
      assertEquals(67, tCharDoubleHashMap0.capacity());
      assertEquals(false, boolean0);
  }

  //Test case number: 32
  /*
   * 1 covered goal:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I20 Branch 75 IF_ICMPEQ L1188 - false
   */

  @Test
  public void test32()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(0, (float) 0, '8', (double) '8');
      TCharDoubleHashMap tCharDoubleHashMap1 = new TCharDoubleHashMap((int) '8', (float) '8');
      tCharDoubleHashMap0.put('Q', 'Q');
      boolean boolean0 = tCharDoubleHashMap1.equals((Object) tCharDoubleHashMap0);
      assertEquals(1, tCharDoubleHashMap0.size());
      assertEquals(false, boolean0);
  }

  //Test case number: 33
  /*
   * 12 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I89 Branch 79 IFEQ L1200 - true
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.<init>(IF)V: root-Branch
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I4 Branch 74 IFNE L1184 - true
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I20 Branch 75 IF_ICMPEQ L1188 - true
   * 5 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I53 Branch 76 IFLE L1195 - true
   * 6 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I53 Branch 76 IFLE L1195 - false
   * 7 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I60 Branch 77 IF_ICMPNE L1196 - true
   * 8 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I60 Branch 77 IF_ICMPNE L1196 - false
   * 9 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I85 Branch 78 IFEQ L1200 - true
   * 10 gnu.trove.map.hash.TCharDoubleHashMap.equals(Ljava/lang/Object;)Z: I85 Branch 78 IFEQ L1200 - false
   * 11 gnu.trove.map.hash.TCharDoubleHashMap.get(C)D: I9 Branch 44 IFGE L253 - true
   * 12 gnu.trove.map.hash.TCharDoubleHashMap.get(C)D: I9 Branch 44 IFGE L253 - false
   */

  @Test
  public void test33()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(0, (float) 0, '8', (double) '8');
      TCharDoubleHashMap tCharDoubleHashMap1 = new TCharDoubleHashMap((int) '8', (float) '8');
      tCharDoubleHashMap1.put('8', (double) 0.0F);
      tCharDoubleHashMap0.put('Q', 'Q');
      tCharDoubleHashMap0.putIfAbsent('8', (double) 0);
      tCharDoubleHashMap1.putIfAbsent('&', '\u0000');
      boolean boolean0 = tCharDoubleHashMap1.equals((Object) tCharDoubleHashMap0);
      assertEquals(false, tCharDoubleHashMap0.isEmpty());
      assertEquals(true, boolean0);
  }

  //Test case number: 34
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.hashCode()I: I18 Branch 81 IFLE L1216 - true
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.hashCode()I: I18 Branch 81 IFLE L1216 - false
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.hashCode()I: I25 Branch 82 IF_ICMPNE L1217 - true
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.<init>(I)V: root-Branch
   */

  @Test
  public void test34()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap((-251));
      int int0 = tCharDoubleHashMap0.hashCode();
      assertEquals(0, int0);
  }

  //Test case number: 35
  /*
   * 4 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.hashCode()I: I25 Branch 82 IF_ICMPNE L1217 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.hashCode()I: I18 Branch 81 IFLE L1216 - true
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.hashCode()I: I18 Branch 81 IFLE L1216 - false
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.hashCode()I: I25 Branch 82 IF_ICMPNE L1217 - true
   */

  @Test
  public void test35()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap();
      tCharDoubleHashMap0.adjustOrPutValue('Y', 'Y', 'Y');
      int int0 = tCharDoubleHashMap0.hashCode();
      assertEquals("{Y=89.0}", tCharDoubleHashMap0.toString());
      assertEquals(1079394393, int0);
  }

  //Test case number: 36
  /*
   * 18 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap$1.execute(CD)Z: I4 Branch 115 IFEQ L1233 - true
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.toString()Ljava/lang/String;: root-Branch
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.<init>(IFCD)V: root-Branch
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.put(CD)D: root-Branch
   * 5 gnu.trove.map.hash.TCharDoubleHashMap.rehash(I)V: I46 Branch 37 IFLE L185 - true
   * 6 gnu.trove.map.hash.TCharDoubleHashMap.rehash(I)V: I46 Branch 37 IFLE L185 - false
   * 7 gnu.trove.map.hash.TCharDoubleHashMap.rehash(I)V: I53 Branch 38 IF_ICMPNE L186 - true
   * 8 gnu.trove.map.hash.TCharDoubleHashMap.rehash(I)V: I53 Branch 38 IF_ICMPNE L186 - false
   * 9 gnu.trove.map.hash.TCharDoubleHashMap.putIfAbsent(CD)D: I9 Branch 39 IFGE L205 - true
   * 10 gnu.trove.map.hash.TCharDoubleHashMap.forEachEntry(Lgnu/trove/procedure/TCharDoubleProcedure;)Z: I23 Branch 63 IFLE L421 - true
   * 11 gnu.trove.map.hash.TCharDoubleHashMap.forEachEntry(Lgnu/trove/procedure/TCharDoubleProcedure;)Z: I23 Branch 63 IFLE L421 - false
   * 12 gnu.trove.map.hash.TCharDoubleHashMap.forEachEntry(Lgnu/trove/procedure/TCharDoubleProcedure;)Z: I30 Branch 64 IF_ICMPNE L422 - true
   * 13 gnu.trove.map.hash.TCharDoubleHashMap.forEachEntry(Lgnu/trove/procedure/TCharDoubleProcedure;)Z: I30 Branch 64 IF_ICMPNE L422 - false
   * 14 gnu.trove.map.hash.TCharDoubleHashMap.forEachEntry(Lgnu/trove/procedure/TCharDoubleProcedure;)Z: I39 Branch 65 IFNE L422 - true
   * 15 gnu.trove.map.hash.TCharDoubleHashMap.doPut(CDI)D: I12 Branch 40 IFGE L214 - true
   * 16 gnu.trove.map.hash.TCharDoubleHashMap.doPut(CDI)D: I41 Branch 41 IFEQ L221 - false
   * 17 gnu.trove.map.hash.TCharDoubleHashMap$1.<init>(Lgnu/trove/map/hash/TCharDoubleHashMap;Ljava/lang/StringBuilder;)V: root-Branch
   * 18 gnu.trove.map.hash.TCharDoubleHashMap$1.execute(CD)Z: I4 Branch 115 IFEQ L1233 - false
   */

  @Test
  public void test36()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap(0, (float) 0, '8', (double) '8');
      tCharDoubleHashMap0.put('8', (double) 0.0F);
      tCharDoubleHashMap0.putIfAbsent('&', '\u0000');
      String string0 = tCharDoubleHashMap0.toString();
      assertEquals(false, tCharDoubleHashMap0.isEmpty());
      assertEquals("{8=0.0, &=0.0}", string0);
  }

  //Test case number: 37
  /*
   * 8 covered goals:
   * 1 gnu.trove.map.hash.TCharDoubleHashMap.writeExternal(Ljava/io/ObjectOutput;)V: I33 Branch 84 IF_ICMPNE L1260 - false
   * 2 gnu.trove.map.hash.TCharDoubleHashMap.<init>()V: root-Branch
   * 3 gnu.trove.map.hash.TCharDoubleHashMap.setUp(I)I: root-Branch
   * 4 gnu.trove.map.hash.TCharDoubleHashMap.writeExternal(Ljava/io/ObjectOutput;)V: I25 Branch 83 IFLE L1259 - true
   * 5 gnu.trove.map.hash.TCharDoubleHashMap.writeExternal(Ljava/io/ObjectOutput;)V: I25 Branch 83 IFLE L1259 - false
   * 6 gnu.trove.map.hash.TCharDoubleHashMap.writeExternal(Ljava/io/ObjectOutput;)V: I33 Branch 84 IF_ICMPNE L1260 - true
   * 7 gnu.trove.map.hash.TCharDoubleHashMap.adjustOrPutValue(CDD)D: I9 Branch 72 IFGE L491 - true
   * 8 gnu.trove.map.hash.TCharDoubleHashMap.adjustOrPutValue(CDD)D: I58 Branch 73 IFEQ L502 - false
   */

  @Test
  public void test37()  throws Throwable  {
      TCharDoubleHashMap tCharDoubleHashMap0 = new TCharDoubleHashMap();
      tCharDoubleHashMap0.adjustOrPutValue('&', '\u0000', '\u0000');
      PipedInputStream pipedInputStream0 = new PipedInputStream((int) '\\');
      PipedOutputStream pipedOutputStream0 = new PipedOutputStream(pipedInputStream0);
      BufferedOutputStream bufferedOutputStream0 = new BufferedOutputStream((OutputStream) pipedOutputStream0);
      ObjectOutputStream objectOutputStream0 = new ObjectOutputStream((OutputStream) bufferedOutputStream0);
      tCharDoubleHashMap0.writeExternal((ObjectOutput) objectOutputStream0);
  }
}
