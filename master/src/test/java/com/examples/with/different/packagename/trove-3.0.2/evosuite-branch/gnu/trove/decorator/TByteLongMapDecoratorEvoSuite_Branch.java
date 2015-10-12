/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.decorator;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.decorator.TByteLongMapDecorator;
import gnu.trove.map.TByteLongMap;
import gnu.trove.map.hash.TByteLongHashMap;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TByteLongMapDecoratorEvoSuite_Branch {

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
   * 1 gnu.trove.decorator.TByteLongMapDecorator.writeExternal(Ljava/io/ObjectOutput;)V: root-Branch
   * 2 gnu.trove.decorator.TByteLongMapDecorator.<init>(Lgnu/trove/map/TByteLongMap;)V: root-Branch
   */

  @Test
  public void test0()  throws Throwable  {
      byte[] byteArray0 = new byte[3];
      long[] longArray0 = new long[8];
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(byteArray0, longArray0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      // Undeclared exception!
      try {
        tByteLongMapDecorator0.writeExternal((ObjectOutput) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 1
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.getMap()Lgnu/trove/map/TByteLongMap;: root-Branch
   */

  @Test
  public void test1()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((int) (byte)0, (float) (byte)0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      TByteLongHashMap tByteLongHashMap1 = (TByteLongHashMap)tByteLongMapDecorator0.getMap();
      assertEquals(0L, tByteLongHashMap1.getNoEntryValue());
  }

  //Test case number: 2
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.size()I: root-Branch
   * 2 gnu.trove.decorator.TByteLongMapDecorator.isEmpty()Z: I4 Branch 19 IFNE L320 - false
   */

  @Test
  public void test2()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      boolean boolean0 = tByteLongMapDecorator0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 3
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.readExternal(Ljava/io/ObjectInput;)V: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap();
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      // Undeclared exception!
      try {
        tByteLongMapDecorator0.readExternal((ObjectInput) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 4
  /*
   * 3 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.unwrapValue(Ljava/lang/Object;)J: root-Branch
   * 2 gnu.trove.decorator.TByteLongMapDecorator.containsValue(Ljava/lang/Object;)Z: I4 Branch 14 IFEQ L288 - false
   * 3 gnu.trove.decorator.TByteLongMapDecorator.containsValue(Ljava/lang/Object;)Z: I11 Branch 15 IFEQ L288 - true
   */

  @Test
  public void test4()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-1), (float) (-1), (byte)0, (long) (byte)0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      Long long0 = new Long((long) (-1));
      boolean boolean0 = tByteLongMapDecorator0.containsValue((Object) long0);
      assertEquals(false, boolean0);
  }

  //Test case number: 5
  /*
   * 14 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.wrapValue(J)Ljava/lang/Long;: root-Branch
   * 2 gnu.trove.decorator.TByteLongMapDecorator.unwrapKey(Ljava/lang/Object;)B: root-Branch
   * 3 gnu.trove.decorator.TByteLongMapDecorator$1$1$1.<init>(Lgnu/trove/decorator/TByteLongMapDecorator$1$1;Ljava/lang/Long;Ljava/lang/Byte;)V: root-Branch
   * 4 gnu.trove.decorator.TByteLongMapDecorator$1$1$1.hashCode()I: root-Branch
   * 5 gnu.trove.decorator.TByteLongMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Long;)Ljava/lang/Long;: I3 Branch 5 IFNONNULL L97 - true
   * 6 gnu.trove.decorator.TByteLongMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Long;)Ljava/lang/Long;: I21 Branch 6 IFNONNULL L102 - true
   * 7 gnu.trove.decorator.TByteLongMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Long;)Ljava/lang/Long;: I51 Branch 7 IFNE L108 - false
   * 8 gnu.trove.decorator.TByteLongMapDecorator$1$1.next()Ljava/util/Map$Entry;: I19 Branch 21 IF_ICMPNE L214 - false
   * 9 gnu.trove.decorator.TByteLongMapDecorator$1$1.next()Ljava/util/Map$Entry;: I45 Branch 22 IFNE L216 - true
   * 10 gnu.trove.decorator.TByteLongMapDecorator$1$1.hasNext()Z: root-Branch
   * 11 gnu.trove.decorator.TByteLongMapDecorator$1.iterator()Ljava/util/Iterator;: root-Branch
   * 12 gnu.trove.decorator.TByteLongMapDecorator$1.<init>(Lgnu/trove/decorator/TByteLongMapDecorator;)V: root-Branch
   * 13 gnu.trove.decorator.TByteLongMapDecorator$1$1.<init>(Lgnu/trove/decorator/TByteLongMapDecorator$1;)V: root-Branch
   * 14 gnu.trove.decorator.TByteLongMapDecorator.entrySet()Ljava/util/Set;: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-1), (float) (-1), (byte)0, (long) (byte)0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      Long long0 = new Long((long) (-1));
      tByteLongMapDecorator0.put((Byte) (byte)0, long0);
      // Undeclared exception!
      try {
        tByteLongMapDecorator0.hashCode();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 6
  /*
   * 13 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.wrapKey(B)Ljava/lang/Byte;: root-Branch
   * 2 gnu.trove.decorator.TByteLongMapDecorator$1$1$1.getValue()Ljava/lang/Long;: root-Branch
   * 3 gnu.trove.decorator.TByteLongMapDecorator$1$1$1.getKey()Ljava/lang/Byte;: root-Branch
   * 4 gnu.trove.decorator.TByteLongMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Long;)Ljava/lang/Long;: I21 Branch 6 IFNONNULL L102 - false
   * 5 gnu.trove.decorator.TByteLongMapDecorator$1$1.next()Ljava/util/Map$Entry;: I19 Branch 21 IF_ICMPNE L214 - true
   * 6 gnu.trove.decorator.TByteLongMapDecorator$1$1.next()Ljava/util/Map$Entry;: I45 Branch 22 IFNE L216 - false
   * 7 gnu.trove.decorator.TByteLongMapDecorator.putAll(Ljava/util/Map;)V: I14 Branch 20 IFLE L334 - true
   * 8 gnu.trove.decorator.TByteLongMapDecorator.putAll(Ljava/util/Map;)V: I14 Branch 20 IFLE L334 - false
   * 9 gnu.trove.decorator.TByteLongMapDecorator$1.iterator()Ljava/util/Iterator;: root-Branch
   * 10 gnu.trove.decorator.TByteLongMapDecorator$1.<init>(Lgnu/trove/decorator/TByteLongMapDecorator;)V: root-Branch
   * 11 gnu.trove.decorator.TByteLongMapDecorator$1$1.<init>(Lgnu/trove/decorator/TByteLongMapDecorator$1;)V: root-Branch
   * 12 gnu.trove.decorator.TByteLongMapDecorator.entrySet()Ljava/util/Set;: root-Branch
   * 13 gnu.trove.decorator.TByteLongMapDecorator$1$1$1.<init>(Lgnu/trove/decorator/TByteLongMapDecorator$1$1;Ljava/lang/Long;Ljava/lang/Byte;)V: root-Branch
   */

  @Test
  public void test6()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      Byte byte0 = new Byte((byte) (-17));
      Long long0 = new Long((long) 0);
      tByteLongMapDecorator0.put(byte0, long0);
      tByteLongMapDecorator0.putAll((Map<? extends Byte, ? extends Long>) tByteLongMapDecorator0);
      assertEquals(false, tByteLongHashMap0.isEmpty());
      assertEquals("{-17=null}", tByteLongMapDecorator0.toString());
  }

  //Test case number: 7
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.clear()V: root-Branch
   */

  @Test
  public void test7()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      tByteLongMapDecorator0.clear();
      assertEquals("{}", tByteLongMapDecorator0.toString());
  }

  //Test case number: 8
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Long;)Ljava/lang/Long;: I51 Branch 7 IFNE L108 - true
   * 2 gnu.trove.decorator.TByteLongMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Long;)Ljava/lang/Long;: I3 Branch 5 IFNONNULL L97 - false
   */

  @Test
  public void test8()  throws Throwable  {
      byte[] byteArray0 = new byte[3];
      byteArray0[2] = (byte) (-109);
      long[] longArray0 = new long[5];
      longArray0[1] = (long) (byte) (-109);
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(byteArray0, longArray0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      Long long0 = tByteLongMapDecorator0.put((Byte) null, (Long) 0L);
      assertEquals("{-109=0, 0=0}", tByteLongHashMap0.toString());
      assertEquals((-109L), (long)long0);
  }

  //Test case number: 9
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Long;: I3 Branch 8 IFNULL L123 - true
   * 2 gnu.trove.decorator.TByteLongMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Long;: I41 Branch 10 IFNE L136 - false
   */

  @Test
  public void test9()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((int) (byte)0, (float) (byte)0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      tByteLongMapDecorator0.get((Object) null);
  }

  //Test case number: 10
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Long;: I3 Branch 8 IFNULL L123 - false
   * 2 gnu.trove.decorator.TByteLongMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Long;: I8 Branch 9 IFEQ L124 - true
   */

  @Test
  public void test10()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-1), (float) (-1), (byte)0, (long) (byte)0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      tByteLongMapDecorator0.get((Object) tByteLongHashMap0);
  }

  //Test case number: 11
  /*
   * 3 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Long;: I8 Branch 9 IFEQ L124 - false
   * 2 gnu.trove.decorator.TByteLongMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Long;: I3 Branch 8 IFNULL L123 - false
   * 3 gnu.trove.decorator.TByteLongMapDecorator.<init>()V: root-Branch
   */

  @Test
  public void test11()  throws Throwable  {
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator();
      Byte byte0 = Byte.decode("5");
      // Undeclared exception!
      try {
        tByteLongMapDecorator0.get((Object) byte0);
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
   * 1 gnu.trove.decorator.TByteLongMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Long;: I41 Branch 10 IFNE L136 - true
   * 2 gnu.trove.decorator.TByteLongMapDecorator.get(Ljava/lang/Object;)Ljava/lang/Long;: I3 Branch 8 IFNULL L123 - true
   */

  @Test
  public void test12()  throws Throwable  {
      byte[] byteArray0 = new byte[7];
      long[] longArray0 = new long[7];
      longArray0[6] = (long) (byte) (-11);
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(byteArray0, longArray0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      Long long0 = tByteLongMapDecorator0.get((Object) null);
      assertEquals("{null=-11}", tByteLongMapDecorator0.toString());
      assertEquals((-11L), (long)long0);
  }

  //Test case number: 13
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Long;: I3 Branch 11 IFNULL L160 - false
   * 2 gnu.trove.decorator.TByteLongMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Long;: I8 Branch 12 IFEQ L161 - true
   */

  @Test
  public void test13()  throws Throwable  {
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator();
      Long long0 = new Long(0L);
      tByteLongMapDecorator0.remove((Object) long0);
  }

  //Test case number: 14
  /*
   * 3 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Long;: I8 Branch 12 IFEQ L161 - false
   * 2 gnu.trove.decorator.TByteLongMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Long;: I3 Branch 11 IFNULL L160 - false
   * 3 gnu.trove.decorator.TByteLongMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Long;: I41 Branch 13 IFNE L173 - false
   */

  @Test
  public void test14()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((int) (byte)0, (float) (byte)0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      tByteLongMapDecorator0.remove((Object) (byte)0);
  }

  //Test case number: 15
  /*
   * 3 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Long;: I41 Branch 13 IFNE L173 - true
   * 2 gnu.trove.decorator.TByteLongMapDecorator.wrapValue(J)Ljava/lang/Long;: root-Branch
   * 3 gnu.trove.decorator.TByteLongMapDecorator.remove(Ljava/lang/Object;)Ljava/lang/Long;: I3 Branch 11 IFNULL L160 - true
   */

  @Test
  public void test15()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(0, (float) 0, (byte)0, (long) (byte)0);
      tByteLongHashMap0.put((byte)0, (-5L));
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      Long long0 = tByteLongMapDecorator0.remove((Object) null);
      assertEquals(true, tByteLongHashMap0.isEmpty());
      assertEquals((-5L), (long)long0);
  }

  //Test case number: 16
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.containsValue(Ljava/lang/Object;)Z: I4 Branch 14 IFEQ L288 - true
   * 2 gnu.trove.decorator.TByteLongMapDecorator.<init>()V: root-Branch
   */

  @Test
  public void test16()  throws Throwable  {
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator();
      boolean boolean0 = tByteLongMapDecorator0.containsValue((Object) null);
      assertEquals(false, boolean0);
  }

  //Test case number: 17
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.containsValue(Ljava/lang/Object;)Z: I11 Branch 15 IFEQ L288 - false
   * 2 gnu.trove.decorator.TByteLongMapDecorator.containsValue(Ljava/lang/Object;)Z: I4 Branch 14 IFEQ L288 - false
   */

  @Test
  public void test17()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-1), (float) (-1), (byte)0, (long) (byte)0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      Long long0 = new Long((long) (-1));
      tByteLongMapDecorator0.put((Byte) (byte)0, long0);
      boolean boolean0 = tByteLongMapDecorator0.containsValue((Object) long0);
      assertEquals(1, tByteLongHashMap0.size());
      assertEquals(true, boolean0);
  }

  //Test case number: 18
  /*
   * 3 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.containsKey(Ljava/lang/Object;)Z: I3 Branch 16 IFNONNULL L299 - true
   * 2 gnu.trove.decorator.TByteLongMapDecorator.containsKey(Ljava/lang/Object;)Z: I15 Branch 17 IFEQ L300 - false
   * 3 gnu.trove.decorator.TByteLongMapDecorator.containsKey(Ljava/lang/Object;)Z: I22 Branch 18 IFEQ L300 - true
   */

  @Test
  public void test18()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-1), (float) (-1), (byte)0, (long) (byte)0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      boolean boolean0 = tByteLongMapDecorator0.containsKey((Object) (byte)0);
      assertEquals(false, boolean0);
  }

  //Test case number: 19
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.containsKey(Ljava/lang/Object;)Z: I3 Branch 16 IFNONNULL L299 - false
   */

  @Test
  public void test19()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-571), (float) (-571), (byte)15, (long) (byte)15);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      boolean boolean0 = tByteLongMapDecorator0.containsKey((Object) null);
      assertEquals(false, boolean0);
  }

  //Test case number: 20
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.containsKey(Ljava/lang/Object;)Z: I15 Branch 17 IFEQ L300 - true
   */

  @Test
  public void test20()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((int) (byte)0, (float) (byte)0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      boolean boolean0 = tByteLongMapDecorator0.containsKey((Object) "");
      assertEquals(false, boolean0);
  }

  //Test case number: 21
  /*
   * 3 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.containsKey(Ljava/lang/Object;)Z: I22 Branch 18 IFEQ L300 - false
   * 2 gnu.trove.decorator.TByteLongMapDecorator.containsKey(Ljava/lang/Object;)Z: I3 Branch 16 IFNONNULL L299 - true
   * 3 gnu.trove.decorator.TByteLongMapDecorator.containsKey(Ljava/lang/Object;)Z: I15 Branch 17 IFEQ L300 - false
   */

  @Test
  public void test21()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap((-1), (float) (-1), (byte)0, (long) (byte)0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      Long long0 = new Long((long) (-1));
      tByteLongMapDecorator0.put((Byte) (byte)0, long0);
      boolean boolean0 = tByteLongMapDecorator0.containsKey((Object) (byte)0);
      assertEquals(1, tByteLongHashMap0.size());
      assertEquals(true, boolean0);
  }

  //Test case number: 22
  /*
   * 8 covered goals:
   * 1 gnu.trove.decorator.TByteLongMapDecorator.isEmpty()Z: I4 Branch 19 IFNE L320 - true
   * 2 gnu.trove.decorator.TByteLongMapDecorator.size()I: root-Branch
   * 3 gnu.trove.decorator.TByteLongMapDecorator.unwrapValue(Ljava/lang/Object;)J: root-Branch
   * 4 gnu.trove.decorator.TByteLongMapDecorator.<init>(Lgnu/trove/map/TByteLongMap;)V: root-Branch
   * 5 gnu.trove.decorator.TByteLongMapDecorator.unwrapKey(Ljava/lang/Object;)B: root-Branch
   * 6 gnu.trove.decorator.TByteLongMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Long;)Ljava/lang/Long;: I3 Branch 5 IFNONNULL L97 - true
   * 7 gnu.trove.decorator.TByteLongMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Long;)Ljava/lang/Long;: I21 Branch 6 IFNONNULL L102 - true
   * 8 gnu.trove.decorator.TByteLongMapDecorator.put(Ljava/lang/Byte;Ljava/lang/Long;)Ljava/lang/Long;: I51 Branch 7 IFNE L108 - false
   */

  @Test
  public void test22()  throws Throwable  {
      TByteLongHashMap tByteLongHashMap0 = new TByteLongHashMap(0);
      TByteLongMapDecorator tByteLongMapDecorator0 = new TByteLongMapDecorator((TByteLongMap) tByteLongHashMap0);
      Byte byte0 = new Byte((byte) (-17));
      Long long0 = new Long((long) 0);
      tByteLongMapDecorator0.put(byte0, long0);
      boolean boolean0 = tByteLongMapDecorator0.isEmpty();
      assertEquals(false, tByteLongHashMap0.isEmpty());
      assertEquals(false, boolean0);
  }
}
