/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.decorator;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.decorator.TByteDoubleMapDecorator;
import gnu.trove.map.TByteDoubleMap;
import gnu.trove.map.hash.TByteDoubleHashMap;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TByteDoubleMapDecoratorEvoSuite_Branch {

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
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.writeExternal(Ljava/io/ObjectOutput;)V: root-Branch
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.<init>(Lgnu/trove/map/TByteDoubleMap;)V: root-Branch
   */

  @Test
  public void test0()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((-1831), (-1831));
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      // Undeclared exception!
      try {
        tByteDoubleMapDecorator0.writeExternal((ObjectOutput) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 1
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.readExternal(Ljava/io/ObjectInput;)V: root-Branch
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.<init>()V: root-Branch
   */

  @Test
  public void test1()  throws Throwable  {
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator();
      // Undeclared exception!
      try {
        tByteDoubleMapDecorator0.readExternal((ObjectInput) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 2
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.getMap()Lgnu/trove/map/TByteDoubleMap;: root-Branch
   */

//   @Test
//   public void test2()  throws Throwable  {
//       TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
//       TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
//       TByteDoubleHashMap tByteDoubleHashMap1 = (TByteDoubleHashMap)tByteDoubleMapDecorator0.getMap();
//       assertEquals(0, tByteDoubleHashMap1.getNoEntryKey());
//   }

  //Test case number: 3
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.clear()V: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      tByteDoubleMapDecorator0.clear();
      assertEquals(0, tByteDoubleMapDecorator0.size());
  }

  //Test case number: 4
  /*
   * 3 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.wrapValue(D)Ljava/lang/Double;: root-Branch
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Double;: I3 Branch 11 IFNULL L160 - true
   * 3 gnu.trove.decorator.TByteDoubleMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Double;: I41 Branch 13 IFNE L173 - true
   */

  @Test
  public void test4()  throws Throwable  {
      byte[] byteArray0 = new byte[5];
      double[] doubleArray0 = new double[6];
      doubleArray0[4] = (double) (byte)23;
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(byteArray0, doubleArray0);
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      Double double0 = tByteDoubleMapDecorator0.remove((Object) null);
      assertEquals(0, tByteDoubleHashMap0.size());
      assertEquals(23.0, (double)double0, 0.01D);
  }

  //Test case number: 5
  /*
   * 23 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Double;)Ljava/lang/Double;: I51 Branch 7 IFNE L108 - true
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator$1$1.next()Ljava/util/Map$Entry;: I45 Branch 22 IFNE L216 - true
   * 3 gnu.trove.decorator.TByteDoubleMapDecorator$1$1.<init>(Lgnu/trove/decorator/TByteDoubleMapDecorator$1;)V: root-Branch
   * 4 gnu.trove.decorator.TByteDoubleMapDecorator$1$1.next()Ljava/util/Map$Entry;: I19 Branch 21 IF_ICMPNE L214 - true
   * 5 gnu.trove.decorator.TByteDoubleMapDecorator$1$1.next()Ljava/util/Map$Entry;: I19 Branch 21 IF_ICMPNE L214 - false
   * 6 gnu.trove.decorator.TByteDoubleMapDecorator$1$1.next()Ljava/util/Map$Entry;: I45 Branch 22 IFNE L216 - false
   * 7 gnu.trove.decorator.TByteDoubleMapDecorator.entrySet()Ljava/util/Set;: root-Branch
   * 8 gnu.trove.decorator.TByteDoubleMapDecorator.wrapKey(B)Ljava/lang/Byte;: root-Branch
   * 9 gnu.trove.decorator.TByteDoubleMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Double;)Ljava/lang/Double;: I3 Branch 5 IFNONNULL L97 - false
   * 10 gnu.trove.decorator.TByteDoubleMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Double;)Ljava/lang/Double;: I21 Branch 6 IFNONNULL L102 - false
   * 11 gnu.trove.decorator.TByteDoubleMapDecorator.putAll(Ljava/util/Map;)V: I14 Branch 20 IFLE L334 - true
   * 12 gnu.trove.decorator.TByteDoubleMapDecorator.putAll(Ljava/util/Map;)V: I14 Branch 20 IFLE L334 - false
   * 13 gnu.trove.decorator.TByteDoubleMapDecorator$1$1$1.getKey()Ljava/lang/Byte;: root-Branch
   * 14 gnu.trove.decorator.TByteDoubleMapDecorator$1$1$1.getValue()Ljava/lang/Double;: root-Branch
   * 15 gnu.trove.decorator.TByteDoubleMapDecorator$1$1$1.<init>(Lgnu/trove/decorator/TByteDoubleMapDecorator$1$1;Ljava/lang/Double;Ljava/lang/Byte;)V: root-Branch
   * 16 gnu.trove.decorator.TByteDoubleMapDecorator$1.iterator()Ljava/util/Iterator;: root-Branch
   * 17 gnu.trove.decorator.TByteDoubleMapDecorator$1.<init>(Lgnu/trove/decorator/TByteDoubleMapDecorator;)V: root-Branch
   * 18 gnu.trove.decorator.TByteDoubleMapDecorator.unwrapValue(Ljava/lang/Object;)D: root-Branch
   * 19 gnu.trove.decorator.TByteDoubleMapDecorator.size()I: root-Branch
   * 20 gnu.trove.decorator.TByteDoubleMapDecorator.unwrapKey(Ljava/lang/Object;)B: root-Branch
   * 21 gnu.trove.decorator.TByteDoubleMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Double;)Ljava/lang/Double;: I3 Branch 5 IFNONNULL L97 - true
   * 22 gnu.trove.decorator.TByteDoubleMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Double;)Ljava/lang/Double;: I21 Branch 6 IFNONNULL L102 - true
   * 23 gnu.trove.decorator.TByteDoubleMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Double;)Ljava/lang/Double;: I51 Branch 7 IFNE L108 - false
   */

  @Test
  public void test5()  throws Throwable  {
      byte[] byteArray0 = new byte[17];
      byteArray0[0] = (byte) (-66);
      double[] doubleArray0 = new double[12];
      doubleArray0[0] = (double) (byte) (-66);
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(byteArray0, doubleArray0);
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      tByteDoubleMapDecorator0.putAll((Map<? extends Byte, ? extends Double>) tByteDoubleMapDecorator0);
      assertEquals(false, tByteDoubleMapDecorator0.isEmpty());
      assertEquals("{-66=-66.0, null=null}", tByteDoubleMapDecorator0.toString());
      assertEquals("{-66=-66.0, 0=0.0}", tByteDoubleHashMap0.toString());
  }

  //Test case number: 6
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Double;: I3 Branch 8 IFNULL L123 - true
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Double;: I41 Branch 10 IFNE L136 - false
   */

  @Test
  public void test6()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((int) (byte) (-4));
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      tByteDoubleMapDecorator0.get((Object) null);
  }

  //Test case number: 7
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Double;: I3 Branch 8 IFNULL L123 - false
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Double;: I8 Branch 9 IFEQ L124 - true
   */

  @Test
  public void test7()  throws Throwable  {
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator();
      tByteDoubleMapDecorator0.get((Object) "{23=23.0, 0=23.0}");
  }

  //Test case number: 8
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Double;: I8 Branch 9 IFEQ L124 - false
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Double;: I41 Branch 10 IFNE L136 - false
   */

  @Test
  public void test8()  throws Throwable  {
      byte[] byteArray0 = new byte[9];
      double[] doubleArray0 = new double[8];
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(byteArray0, doubleArray0);
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      tByteDoubleMapDecorator0.get((Object) (byte) (-4));
  }

  //Test case number: 9
  /*
   * 4 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Double;: I41 Branch 10 IFNE L136 - true
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.wrapValue(D)Ljava/lang/Double;: root-Branch
   * 3 gnu.trove.decorator.TByteDoubleMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Double;: I3 Branch 8 IFNULL L123 - false
   * 4 gnu.trove.decorator.TByteDoubleMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Double;: I8 Branch 9 IFEQ L124 - false
   */

  @Test
  public void test9()  throws Throwable  {
      byte[] byteArray0 = new byte[9];
      byteArray0[7] = (byte) (-4);
      double[] doubleArray0 = new double[8];
      doubleArray0[7] = (double) (byte) (-4);
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(byteArray0, doubleArray0);
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      Double double0 = tByteDoubleMapDecorator0.get((Object) (byte) (-4));
      assertEquals((-4.0), (double)double0, 0.01D);
      assertEquals("{-4=-4.0, null=null}", tByteDoubleMapDecorator0.toString());
  }

  //Test case number: 10
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Double;: I3 Branch 11 IFNULL L160 - false
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Double;: I8 Branch 12 IFEQ L161 - true
   */

  @Test
  public void test10()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      tByteDoubleMapDecorator0.remove((Object) "zh_TW");
  }

  //Test case number: 11
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Double;: I8 Branch 12 IFEQ L161 - false
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Double;: I3 Branch 11 IFNULL L160 - false
   */

  @Test
  public void test11()  throws Throwable  {
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator();
      Byte byte0 = new Byte((byte)83);
      // Undeclared exception!
      try {
        tByteDoubleMapDecorator0.remove((Object) byte0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 12
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Double;: I41 Branch 13 IFNE L173 - false
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Double;: I3 Branch 11 IFNULL L160 - true
   */

  @Test
  public void test12()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      tByteDoubleMapDecorator0.remove((Object) null);
  }

  //Test case number: 13
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.containsValue(Ljava/lang/Object;)Z: I4 Branch 14 IFEQ L288 - true
   */

  @Test
  public void test13()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((-1831), (-1831));
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tByteDoubleMapDecorator0.containsValue((Object) tByteDoubleHashMap0);
      assertEquals(false, boolean0);
  }

  //Test case number: 14
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.containsValue(Ljava/lang/Object;)Z: I4 Branch 14 IFEQ L288 - false
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.containsValue(Ljava/lang/Object;)Z: I11 Branch 15 IFEQ L288 - true
   */

  @Test
  public void test14()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((-1831), (-1831));
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      Double double0 = new Double((double) (-1831));
      boolean boolean0 = tByteDoubleMapDecorator0.containsValue((Object) double0);
      assertEquals(false, boolean0);
  }

  //Test case number: 15
  /*
   * 6 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.containsValue(Ljava/lang/Object;)Z: I11 Branch 15 IFEQ L288 - false
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.unwrapValue(Ljava/lang/Object;)D: root-Branch
   * 3 gnu.trove.decorator.TByteDoubleMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Double;)Ljava/lang/Double;: I3 Branch 5 IFNONNULL L97 - true
   * 4 gnu.trove.decorator.TByteDoubleMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Double;)Ljava/lang/Double;: I21 Branch 6 IFNONNULL L102 - true
   * 5 gnu.trove.decorator.TByteDoubleMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Double;)Ljava/lang/Double;: I51 Branch 7 IFNE L108 - false
   * 6 gnu.trove.decorator.TByteDoubleMapDecorator.containsValue(Ljava/lang/Object;)Z: I4 Branch 14 IFEQ L288 - false
   */

  @Test
  public void test15()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap((-1831), (-1831));
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      Double double0 = new Double((double) (-1831));
      Byte byte0 = new Byte((byte)46);
      tByteDoubleMapDecorator0.put(byte0, double0);
      boolean boolean0 = tByteDoubleMapDecorator0.containsValue((Object) double0);
      assertEquals(false, tByteDoubleMapDecorator0.isEmpty());
      assertEquals(true, boolean0);
  }

  //Test case number: 16
  /*
   * 3 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.containsKey(Ljava/lang/Object;)Z: I3 Branch 16 IFNONNULL L299 - true
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.containsKey(Ljava/lang/Object;)Z: I15 Branch 17 IFEQ L300 - false
   * 3 gnu.trove.decorator.TByteDoubleMapDecorator.containsKey(Ljava/lang/Object;)Z: I22 Branch 18 IFEQ L300 - true
   */

  @Test
  public void test16()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tByteDoubleMapDecorator0.containsKey((Object) (byte)11);
      assertEquals(false, boolean0);
  }

  //Test case number: 17
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.containsKey(Ljava/lang/Object;)Z: I3 Branch 16 IFNONNULL L299 - false
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.<init>()V: root-Branch
   */

  @Test
  public void test17()  throws Throwable  {
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator();
      // Undeclared exception!
      try {
        tByteDoubleMapDecorator0.containsKey((Object) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 18
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.containsKey(Ljava/lang/Object;)Z: I15 Branch 17 IFEQ L300 - true
   */

  @Test
  public void test18()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tByteDoubleMapDecorator0.containsKey((Object) tByteDoubleHashMap0);
      assertEquals(false, boolean0);
  }

  //Test case number: 19
  /*
   * 4 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.containsKey(Ljava/lang/Object;)Z: I22 Branch 18 IFEQ L300 - false
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.unwrapKey(Ljava/lang/Object;)B: root-Branch
   * 3 gnu.trove.decorator.TByteDoubleMapDecorator.containsKey(Ljava/lang/Object;)Z: I3 Branch 16 IFNONNULL L299 - true
   * 4 gnu.trove.decorator.TByteDoubleMapDecorator.containsKey(Ljava/lang/Object;)Z: I15 Branch 17 IFEQ L300 - false
   */

  @Test
  public void test19()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      tByteDoubleHashMap0.putIfAbsent((byte)11, (byte)11);
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tByteDoubleMapDecorator0.containsKey((Object) (byte)11);
      assertEquals(true, boolean0);
  }

  //Test case number: 20
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.isEmpty()Z: I4 Branch 19 IFNE L320 - true
   */

  @Test
  public void test20()  throws Throwable  {
      byte[] byteArray0 = new byte[9];
      double[] doubleArray0 = new double[3];
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap(byteArray0, doubleArray0);
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tByteDoubleMapDecorator0.isEmpty();
      assertEquals(false, boolean0);
  }

  //Test case number: 21
  /*
   * 3 covered goals:
   * 1 gnu.trove.decorator.TByteDoubleMapDecorator.isEmpty()Z: I4 Branch 19 IFNE L320 - false
   * 2 gnu.trove.decorator.TByteDoubleMapDecorator.<init>(Lgnu/trove/map/TByteDoubleMap;)V: root-Branch
   * 3 gnu.trove.decorator.TByteDoubleMapDecorator.size()I: root-Branch
   */

  @Test
  public void test21()  throws Throwable  {
      TByteDoubleHashMap tByteDoubleHashMap0 = new TByteDoubleHashMap();
      TByteDoubleMapDecorator tByteDoubleMapDecorator0 = new TByteDoubleMapDecorator((TByteDoubleMap) tByteDoubleHashMap0);
      boolean boolean0 = tByteDoubleMapDecorator0.isEmpty();
      assertEquals(true, boolean0);
  }
}
