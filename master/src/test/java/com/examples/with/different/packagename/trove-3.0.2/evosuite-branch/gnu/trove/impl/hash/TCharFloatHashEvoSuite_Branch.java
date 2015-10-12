/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.map.TCharFloatMap;
import gnu.trove.map.hash.TCharFloatHashMap;
import gnu.trove.procedure.TCharProcedure;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TCharFloatHashEvoSuite_Branch {

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
   * 1 gnu.trove.impl.hash.TCharFloatHash.writeExternal(Ljava/io/ObjectOutput;)V: root-Branch
   * 2 gnu.trove.impl.hash.TCharFloatHash.<init>(IFCF)V: root-Branch
   * 3 gnu.trove.impl.hash.TCharFloatHash.setUp(I)I: root-Branch
   */

//   @Test
//   public void test0()  throws Throwable  {
//       TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(71, (float) 71, '_', (float) '_');
//       ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
//       PrintStream printStream0 = new PrintStream((OutputStream) byteArrayOutputStream0);
//       ObjectOutputStream objectOutputStream0 = new ObjectOutputStream((OutputStream) printStream0);
//       tCharFloatHashMap0.writeExternal((ObjectOutput) objectOutputStream0);
//       assertEquals('_', tCharFloatHashMap0.getNoEntryKey());
//       assertEquals(95.0F, tCharFloatHashMap0.getNoEntryValue(), 0.01F);
//   }

  //Test case number: 1
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.hash.TCharFloatHash.getNoEntryKey()C: root-Branch
   * 2 gnu.trove.impl.hash.TCharFloatHash.<init>()V: root-Branch
   */

//   @Test
//   public void test1()  throws Throwable  {
//       TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap();
//       char char0 = tCharFloatHashMap0.getNoEntryKey();
//       assertEquals(23, tCharFloatHashMap0.capacity());
//       assertEquals('\u0000', char0);
//       assertEquals(0.0F, tCharFloatHashMap0.getNoEntryValue(), 0.01F);
//   }

  //Test case number: 2
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.hash.TCharFloatHash.<init>(IF)V: root-Branch
   */

//   @Test
//   public void test2()  throws Throwable  {
//       TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap((int) '*', (float) '*');
//       assertEquals('\u0000', tCharFloatHashMap0.getNoEntryKey());
//       assertEquals(0.0F, tCharFloatHashMap0.getNoEntryValue(), 0.01F);
//   }

  //Test case number: 3
  /*
   * 7 covered goals:
   * 1 gnu.trove.impl.hash.TCharFloatHash.removeAt(I)V: root-Branch
   * 2 gnu.trove.impl.hash.TCharFloatHash.index(C)I: I37 Branch 5 IFNE L228 - true
   * 3 gnu.trove.impl.hash.TCharFloatHash.index(C)I: I46 Branch 6 IF_ICMPNE L231 - false
   * 4 gnu.trove.impl.hash.TCharFloatHash.index(C)I: I51 Branch 7 IF_ICMPNE L231 - false
   * 5 gnu.trove.impl.hash.TCharFloatHash.insertKeyAt(IC)V: root-Branch
   * 6 gnu.trove.impl.hash.TCharFloatHash.<init>(I)V: root-Branch
   * 7 gnu.trove.impl.hash.TCharFloatHash.insertKey(C)I: I30 Branch 13 IFNE L279 - false
   */

  @Test
  public void test3()  throws Throwable  {
      char[] charArray0 = new char[2];
      float[] floatArray0 = new float[7];
      charArray0[0] = '*';
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(charArray0, floatArray0);
      tCharFloatHashMap0.remove('*');
      assertEquals(1, tCharFloatHashMap0.size());
      assertEquals("{\u0000=0.0}", tCharFloatHashMap0.toString());
  }

  //Test case number: 4
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.hash.TCharFloatHash.getNoEntryValue()F: root-Branch
   */

//   @Test
//   public void test4()  throws Throwable  {
//       TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(71, (float) 71, '_', (float) '_');
//       float float0 = tCharFloatHashMap0.getNoEntryValue();
//       assertEquals('_', tCharFloatHashMap0.getNoEntryKey());
//       assertEquals(95.0F, float0, 0.01F);
//   }

  //Test case number: 5
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.hash.TCharFloatHash.contains(C)Z: I5 Branch 1 IFLT L178 - true
   * 2 gnu.trove.impl.hash.TCharFloatHash.index(C)I: I37 Branch 5 IFNE L228 - false
   */

//   @Test
//   public void test5()  throws Throwable  {
//       TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap();
//       boolean boolean0 = tCharFloatHashMap0.containsKey('|');
//       assertEquals(false, boolean0);
//       assertEquals(23, tCharFloatHashMap0.capacity());
//       assertEquals('\u0000', tCharFloatHashMap0.getNoEntryKey());
//       assertEquals(0.0F, tCharFloatHashMap0.getNoEntryValue(), 0.01F);
//   }

  //Test case number: 6
  /*
   * 5 covered goals:
   * 1 gnu.trove.impl.hash.TCharFloatHash.contains(C)Z: I5 Branch 1 IFLT L178 - false
   * 2 gnu.trove.impl.hash.TCharFloatHash.index(C)I: I51 Branch 7 IF_ICMPNE L231 - false
   * 3 gnu.trove.impl.hash.TCharFloatHash.insertKey(C)I: I30 Branch 13 IFNE L279 - true
   * 4 gnu.trove.impl.hash.TCharFloatHash.insertKey(C)I: I50 Branch 14 IF_ICMPNE L286 - false
   * 5 gnu.trove.impl.hash.TCharFloatHash.insertKey(C)I: I56 Branch 15 IF_ICMPNE L286 - false
   */

  @Test
  public void test6()  throws Throwable  {
      char[] charArray0 = new char[10];
      charArray0[0] = 'a';
      float[] floatArray0 = new float[3];
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(charArray0, floatArray0);
      boolean boolean0 = tCharFloatHashMap0.contains('a');
      assertEquals('\u0000', tCharFloatHashMap0.getNoEntryKey());
      assertEquals(2, tCharFloatHashMap0.size());
      assertEquals(true, boolean0);
  }

  //Test case number: 7
  /*
   * 4 covered goals:
   * 1 gnu.trove.impl.hash.TCharFloatHash.forEach(Lgnu/trove/procedure/TCharProcedure;)Z: I18 Branch 2 IFLE L192 - true
   * 2 gnu.trove.impl.hash.TCharFloatHash.forEach(Lgnu/trove/procedure/TCharProcedure;)Z: I18 Branch 2 IFLE L192 - false
   * 3 gnu.trove.impl.hash.TCharFloatHash.forEach(Lgnu/trove/procedure/TCharProcedure;)Z: I25 Branch 3 IF_ICMPNE L193 - true
   * 4 gnu.trove.impl.hash.TCharFloatHash.<init>(IFCF)V: root-Branch
   */

//   @Test
//   public void test7()  throws Throwable  {
//       TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(71, (float) 71, '_', (float) '_');
//       boolean boolean0 = tCharFloatHashMap0.forEachKey((TCharProcedure) null);
//       assertEquals(true, boolean0);
//       assertEquals('_', tCharFloatHashMap0.getNoEntryKey());
//       assertEquals(95.0F, tCharFloatHashMap0.getNoEntryValue(), 0.01F);
//   }

  //Test case number: 8
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.hash.TCharFloatHash.forEach(Lgnu/trove/procedure/TCharProcedure;)Z: I25 Branch 3 IF_ICMPNE L193 - false
   * 2 gnu.trove.impl.hash.TCharFloatHash.forEach(Lgnu/trove/procedure/TCharProcedure;)Z: I18 Branch 2 IFLE L192 - false
   * 3 gnu.trove.impl.hash.TCharFloatHash.forEach(Lgnu/trove/procedure/TCharProcedure;)Z: I25 Branch 3 IF_ICMPNE L193 - true
   */

  @Test
  public void test8()  throws Throwable  {
      char[] charArray0 = new char[1];
      float[] floatArray0 = new float[1];
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(charArray0, floatArray0);
      // Undeclared exception!
      try {
        tCharFloatHashMap0.forEach((TCharProcedure) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 9
  /*
   * 7 covered goals:
   * 1 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I46 Branch 9 IFNE L250 - true
   * 2 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I58 Branch 10 IF_ICMPNE L254 - true
   * 3 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I70 Branch 12 IF_ICMPNE L256 - true
   * 4 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I46 Branch 9 IFNE L250 - false
   * 5 gnu.trove.impl.hash.TCharFloatHash.index(C)I: I46 Branch 6 IF_ICMPNE L231 - true
   * 6 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I29 Branch 8 IFGE L245 - true
   * 7 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I29 Branch 8 IFGE L245 - false
   */

//   @Test
//   public void test9()  throws Throwable  {
//       TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap();
//       byte[] byteArray0 = new byte[3];
//       byteArray0[0] = (byte) (-82);
//       byteArray0[1] = (byte) (-82);
//       tCharFloatHashMap0.trimToSize();
//       tCharFloatHashMap0._states = byteArray0;
//       boolean boolean0 = tCharFloatHashMap0.containsKey('|');
//       assertEquals(false, boolean0);
//       assertEquals(0.0F, tCharFloatHashMap0.getNoEntryValue(), 0.01F);
//   }

  //Test case number: 10
  /*
   * 10 covered goals:
   * 1 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I58 Branch 10 IF_ICMPNE L254 - false
   * 2 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I61 Branch 11 IF_ICMPEQ L254 - false
   * 3 gnu.trove.impl.hash.TCharFloatHash.insertKey(C)I: I56 Branch 15 IF_ICMPNE L286 - true
   * 4 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I28 Branch 16 IF_ICMPNE L306 - true
   * 5 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I45 Branch 18 IFGE L310 - false
   * 6 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I62 Branch 19 IFNE L316 - false
   * 7 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I67 Branch 20 IF_ICMPEQ L317 - true
   * 8 gnu.trove.impl.hash.TCharFloatHash.contains(C)Z: I5 Branch 1 IFLT L178 - false
   * 9 gnu.trove.impl.hash.TCharFloatHash.index(C)I: I46 Branch 6 IF_ICMPNE L231 - false
   * 10 gnu.trove.impl.hash.TCharFloatHash.index(C)I: I51 Branch 7 IF_ICMPNE L231 - true
   */

  @Test
  public void test10()  throws Throwable  {
      char[] charArray0 = new char[10];
      charArray0[0] = 'a';
      charArray0[1] = 'x';
      float[] floatArray0 = new float[3];
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(charArray0, floatArray0);
      TCharFloatHashMap tCharFloatHashMap1 = new TCharFloatHashMap((TCharFloatMap) tCharFloatHashMap0);
      boolean boolean0 = tCharFloatHashMap1.contains('a');
      assertEquals("{x=0.0, a=0.0, \u0000=0.0}", tCharFloatHashMap0.toString());
      assertEquals(true, boolean0);
  }

  //Test case number: 11
  /*
   * 9 covered goals:
   * 1 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I70 Branch 12 IF_ICMPNE L256 - false
   * 2 gnu.trove.impl.hash.TCharFloatHash.contains(C)Z: I5 Branch 1 IFLT L178 - true
   * 3 gnu.trove.impl.hash.TCharFloatHash.index(C)I: I37 Branch 5 IFNE L228 - true
   * 4 gnu.trove.impl.hash.TCharFloatHash.index(C)I: I46 Branch 6 IF_ICMPNE L231 - true
   * 5 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I29 Branch 8 IFGE L245 - true
   * 6 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I29 Branch 8 IFGE L245 - false
   * 7 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I46 Branch 9 IFNE L250 - true
   * 8 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I58 Branch 10 IF_ICMPNE L254 - true
   * 9 gnu.trove.impl.hash.TCharFloatHash.indexRehashed(CIIB)I: I70 Branch 12 IF_ICMPNE L256 - true
   */

  @Test
  public void test11()  throws Throwable  {
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap();
      assertEquals(23, tCharFloatHashMap0.capacity());
      
      byte[] byteArray0 = new byte[3];
      byteArray0[0] = (byte) (-82);
      byteArray0[1] = (byte) (-82);
      tCharFloatHashMap0.trimToSize();
      byteArray0[2] = (byte) (-82);
      tCharFloatHashMap0._states = byteArray0;
      boolean boolean0 = tCharFloatHashMap0.containsKey('|');
      assertEquals(false, boolean0);
  }

  //Test case number: 12
  /*
   * 5 covered goals:
   * 1 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I97 Branch 21 IF_ICMPNE L327 - false
   * 2 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I103 Branch 22 IF_ICMPNE L327 - true
   * 3 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I45 Branch 18 IFGE L310 - true
   * 4 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I62 Branch 19 IFNE L316 - true
   * 5 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I115 Branch 23 IF_ICMPNE L332 - true
   */

//   @Test
//   public void test12()  throws Throwable  {
//       char[] charArray0 = new char[25];
//       charArray0[3] = '*';
//       charArray0[1] = 'n';
//       charArray0[5] = 'o';
//       float[] floatArray0 = new float[8];
//       TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(charArray0, floatArray0);
//       TCharFloatHashMap tCharFloatHashMap1 = new TCharFloatHashMap((TCharFloatMap) tCharFloatHashMap0);
//       assertEquals(0.0F, tCharFloatHashMap1.getNoEntryValue(), 0.01F);
//       assertEquals(4, tCharFloatHashMap0.size());
//       assertEquals('\u0000', tCharFloatHashMap1.getNoEntryKey());
//       assertEquals("{o=0.0, n=0.0, *=0.0, \u0000=0.0}", tCharFloatHashMap1.toString());
//   }

  //Test case number: 13
  /*
   * 10 covered goals:
   * 1 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I103 Branch 22 IF_ICMPNE L327 - false
   * 2 gnu.trove.impl.hash.TCharFloatHash.insertKeyAt(IC)V: root-Branch
   * 3 gnu.trove.impl.hash.TCharFloatHash.<init>(I)V: root-Branch
   * 4 gnu.trove.impl.hash.TCharFloatHash.insertKey(C)I: I30 Branch 13 IFNE L279 - false
   * 5 gnu.trove.impl.hash.TCharFloatHash.insertKey(C)I: I50 Branch 14 IF_ICMPNE L286 - false
   * 6 gnu.trove.impl.hash.TCharFloatHash.insertKey(C)I: I56 Branch 15 IF_ICMPNE L286 - true
   * 7 gnu.trove.impl.hash.TCharFloatHash.insertKey(C)I: I56 Branch 15 IF_ICMPNE L286 - false
   * 8 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I62 Branch 19 IFNE L316 - false
   * 9 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I67 Branch 20 IF_ICMPEQ L317 - true
   * 10 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I97 Branch 21 IF_ICMPNE L327 - false
   */

  @Test
  public void test13()  throws Throwable  {
      char[] charArray0 = new char[9];
      charArray0[0] = 'a';
      charArray0[1] = 'x';
      charArray0[5] = 'x';
      float[] floatArray0 = new float[10];
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(charArray0, floatArray0);
      assertEquals("{x=0.0, a=0.0, \u0000=0.0}", tCharFloatHashMap0.toString());
      assertEquals(3, tCharFloatHashMap0.size());
  }

  //Test case number: 14
  /*
   * 12 covered goals:
   * 1 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I115 Branch 23 IF_ICMPNE L332 - false
   * 2 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I120 Branch 24 IF_ICMPEQ L336 - true
   * 3 gnu.trove.impl.hash.TCharFloatHash.<init>()V: root-Branch
   * 4 gnu.trove.impl.hash.TCharFloatHash.setUp(I)I: root-Branch
   * 5 gnu.trove.impl.hash.TCharFloatHash.insertKey(C)I: I30 Branch 13 IFNE L279 - true
   * 6 gnu.trove.impl.hash.TCharFloatHash.insertKey(C)I: I50 Branch 14 IF_ICMPNE L286 - true
   * 7 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I28 Branch 16 IF_ICMPNE L306 - true
   * 8 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I45 Branch 18 IFGE L310 - true
   * 9 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I45 Branch 18 IFGE L310 - false
   * 10 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I62 Branch 19 IFNE L316 - true
   * 11 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I97 Branch 21 IF_ICMPNE L327 - true
   * 12 gnu.trove.impl.hash.TCharFloatHash.insertKeyRehash(CIIB)I: I115 Branch 23 IF_ICMPNE L332 - true
   */

  @Test
  public void test14()  throws Throwable  {
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap();
      assertEquals(23, tCharFloatHashMap0.capacity());
      
      byte[] byteArray0 = new byte[13];
      byteArray0[0] = (byte) (-93);
      byteArray0[1] = (byte) (-93);
      tCharFloatHashMap0.trimToSize();
      byteArray0[2] = (byte) (-93);
      tCharFloatHashMap0._states = byteArray0;
      // Undeclared exception!
      try {
        tCharFloatHashMap0.putIfAbsent('\u0000', (float) (byte) (-93));
        fail("Expecting exception: IllegalStateException");
      
      } catch(IllegalStateException e) {
         //
         // No free or removed slots available. Key set full?!!
         //
      }
  }
}
