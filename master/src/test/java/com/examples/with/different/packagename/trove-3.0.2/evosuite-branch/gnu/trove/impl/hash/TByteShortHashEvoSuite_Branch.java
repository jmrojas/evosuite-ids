/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.map.hash.TByteShortHashMap;
import gnu.trove.procedure.TByteProcedure;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TByteShortHashEvoSuite_Branch {

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
   * 1 gnu.trove.impl.hash.TByteShortHash.getNoEntryKey()B: root-Branch
   * 2 gnu.trove.impl.hash.TByteShortHash.<init>(IF)V: root-Branch
   * 3 gnu.trove.impl.hash.TByteShortHash.setUp(I)I: root-Branch
   */

//   @Test
//   public void test0()  throws Throwable  {
//       TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-1284), (-1284));
//       byte byte0 = tByteShortHashMap0.getNoEntryKey();
//       assertEquals(0, tByteShortHashMap0.getNoEntryValue());
//       assertEquals((byte)0, byte0);
//   }

  //Test case number: 1
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.hash.TByteShortHash.getNoEntryValue()S: root-Branch
   */

//   @Test
//   public void test1()  throws Throwable  {
//       TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-1284), (-1284));
//       short short0 = tByteShortHashMap0.getNoEntryValue();
//       assertEquals((short)0, short0);
//       assertEquals(0, tByteShortHashMap0.getNoEntryKey());
//   }

  //Test case number: 2
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.hash.TByteShortHash.writeExternal(Ljava/io/ObjectOutput;)V: root-Branch
   * 2 gnu.trove.impl.hash.TByteShortHash.<init>(IFBS)V: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-8), (float) (-8), (byte) (-97), (short) (byte) (-97));
      PipedInputStream pipedInputStream0 = new PipedInputStream();
      PipedOutputStream pipedOutputStream0 = new PipedOutputStream(pipedInputStream0);
      ObjectOutputStream objectOutputStream0 = new ObjectOutputStream((OutputStream) pipedOutputStream0);
      tByteShortHashMap0.writeExternal((ObjectOutput) objectOutputStream0);
  }

  //Test case number: 3
  /*
   * 1 covered goal:
   * 1 gnu.trove.impl.hash.TByteShortHash.<init>()V: root-Branch
   */

//   @Test
//   public void test3()  throws Throwable  {
//       TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap();
//       assertEquals(23, tByteShortHashMap0.capacity());
//       assertEquals(0, tByteShortHashMap0.getNoEntryValue());
//       assertEquals(0, tByteShortHashMap0.getNoEntryKey());
//   }

  //Test case number: 4
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.hash.TByteShortHash.contains(B)Z: I5 Branch 1 IFLT L178 - true
   * 2 gnu.trove.impl.hash.TByteShortHash.index(B)I: I37 Branch 5 IFNE L228 - false
   */

//   @Test
//   public void test4()  throws Throwable  {
//       TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-1284), (-1284));
//       boolean boolean0 = tByteShortHashMap0.containsKey((byte)0);
//       assertEquals(0, tByteShortHashMap0.getNoEntryKey());
//       assertEquals(false, boolean0);
//       assertEquals(0, tByteShortHashMap0.getNoEntryValue());
//   }

  //Test case number: 5
  /*
   * 4 covered goals:
   * 1 gnu.trove.impl.hash.TByteShortHash.forEach(Lgnu/trove/procedure/TByteProcedure;)Z: I18 Branch 2 IFLE L192 - true
   * 2 gnu.trove.impl.hash.TByteShortHash.forEach(Lgnu/trove/procedure/TByteProcedure;)Z: I18 Branch 2 IFLE L192 - false
   * 3 gnu.trove.impl.hash.TByteShortHash.forEach(Lgnu/trove/procedure/TByteProcedure;)Z: I25 Branch 3 IF_ICMPNE L193 - true
   * 4 gnu.trove.impl.hash.TByteShortHash.<init>(IF)V: root-Branch
   */

//   @Test
//   public void test5()  throws Throwable  {
//       TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-1284), (-1284));
//       boolean boolean0 = tByteShortHashMap0.forEach((TByteProcedure) null);
//       assertEquals(0, tByteShortHashMap0.getNoEntryValue());
//       assertEquals(0, tByteShortHashMap0.getNoEntryKey());
//       assertEquals(true, boolean0);
//   }

  //Test case number: 6
  /*
   * 6 covered goals:
   * 1 gnu.trove.impl.hash.TByteShortHash.forEach(Lgnu/trove/procedure/TByteProcedure;)Z: I25 Branch 3 IF_ICMPNE L193 - false
   * 2 gnu.trove.impl.hash.TByteShortHash.forEach(Lgnu/trove/procedure/TByteProcedure;)Z: I18 Branch 2 IFLE L192 - false
   * 3 gnu.trove.impl.hash.TByteShortHash.forEach(Lgnu/trove/procedure/TByteProcedure;)Z: I25 Branch 3 IF_ICMPNE L193 - true
   * 4 gnu.trove.impl.hash.TByteShortHash.<init>(I)V: root-Branch
   * 5 gnu.trove.impl.hash.TByteShortHash.insertKeyAt(IB)V: root-Branch
   * 6 gnu.trove.impl.hash.TByteShortHash.insertKey(B)I: I30 Branch 13 IFNE L279 - false
   */

  @Test
  public void test6()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(1);
      tByteShortHashMap0.put((byte) (-1), (byte)115);
      // Undeclared exception!
      try {
        tByteShortHashMap0.forEachKey((TByteProcedure) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 7
  /*
   * 18 covered goals:
   * 1 gnu.trove.impl.hash.TByteShortHash.index(B)I: I51 Branch 7 IF_ICMPNE L231 - true
   * 2 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I29 Branch 8 IFGE L245 - false
   * 3 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I46 Branch 9 IFNE L250 - true
   * 4 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I58 Branch 10 IF_ICMPNE L254 - false
   * 5 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I61 Branch 11 IF_ICMPEQ L254 - false
   * 6 gnu.trove.impl.hash.TByteShortHash.insertKey(B)I: I56 Branch 15 IF_ICMPNE L286 - true
   * 7 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I28 Branch 16 IF_ICMPNE L306 - true
   * 8 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I45 Branch 18 IFGE L310 - false
   * 9 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I62 Branch 19 IFNE L316 - true
   * 10 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I62 Branch 19 IFNE L316 - false
   * 11 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I67 Branch 20 IF_ICMPEQ L317 - true
   * 12 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I97 Branch 21 IF_ICMPNE L327 - false
   * 13 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I103 Branch 22 IF_ICMPNE L327 - false
   * 14 gnu.trove.impl.hash.TByteShortHash.contains(B)Z: I5 Branch 1 IFLT L178 - false
   * 15 gnu.trove.impl.hash.TByteShortHash.index(B)I: I37 Branch 5 IFNE L228 - true
   * 16 gnu.trove.impl.hash.TByteShortHash.index(B)I: I46 Branch 6 IF_ICMPNE L231 - false
   * 17 gnu.trove.impl.hash.TByteShortHash.insertKey(B)I: I30 Branch 13 IFNE L279 - true
   * 18 gnu.trove.impl.hash.TByteShortHash.insertKey(B)I: I50 Branch 14 IF_ICMPNE L286 - false
   */

  @Test
  public void test7()  throws Throwable  {
      byte[] byteArray0 = new byte[4];
      byteArray0[0] = (byte) (-2);
      short[] shortArray0 = new short[4];
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(byteArray0, shortArray0);
      boolean boolean0 = tByteShortHashMap0.containsKey((byte)0);
      assertEquals(2, tByteShortHashMap0.size());
      assertEquals("{0=0, -2=0}", tByteShortHashMap0.toString());
      assertEquals(true, boolean0);
  }

  //Test case number: 8
  /*
   * 5 covered goals:
   * 1 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I58 Branch 10 IF_ICMPNE L254 - true
   * 2 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I70 Branch 12 IF_ICMPNE L256 - true
   * 3 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I46 Branch 9 IFNE L250 - false
   * 4 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I29 Branch 8 IFGE L245 - true
   * 5 gnu.trove.impl.hash.TByteShortHash.index(B)I: I46 Branch 6 IF_ICMPNE L231 - true
   */

//   @Test
//   public void test8()  throws Throwable  {
//       TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-8), (float) (-8), (byte) (-97), (short) (byte) (-97));
//       byte[] byteArray0 = new byte[6];
//       byteArray0[0] = (byte) (-97);
//       byteArray0[1] = (byte) (-97);
//       tByteShortHashMap0._states = byteArray0;
//       boolean boolean0 = tByteShortHashMap0.increment((byte) (-97));
//       assertEquals(-97, tByteShortHashMap0.getNoEntryKey());
//       assertEquals(false, boolean0);
//   }

  //Test case number: 9
  /*
   * 7 covered goals:
   * 1 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I70 Branch 12 IF_ICMPNE L256 - false
   * 2 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I29 Branch 8 IFGE L245 - true
   * 3 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I29 Branch 8 IFGE L245 - false
   * 4 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I46 Branch 9 IFNE L250 - true
   * 5 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I58 Branch 10 IF_ICMPNE L254 - true
   * 6 gnu.trove.impl.hash.TByteShortHash.indexRehashed(BIIB)I: I70 Branch 12 IF_ICMPNE L256 - true
   * 7 gnu.trove.impl.hash.TByteShortHash.index(B)I: I46 Branch 6 IF_ICMPNE L231 - true
   */

//   @Test
//   public void test9()  throws Throwable  {
//       TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-8), (float) (-8), (byte) (-97), (short) (byte) (-97));
//       byte[] byteArray0 = new byte[6];
//       byteArray0[0] = (byte) (-97);
//       byteArray0[1] = (byte) (-97);
//       byteArray0[2] = (byte) (-97);
//       tByteShortHashMap0._states = byteArray0;
//       boolean boolean0 = tByteShortHashMap0.increment((byte) (-97));
//       assertEquals(false, boolean0);
//       assertEquals(-97, tByteShortHashMap0.getNoEntryKey());
//   }

  //Test case number: 10
  /*
   * 9 covered goals:
   * 1 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I28 Branch 16 IF_ICMPNE L306 - false
   * 2 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I31 Branch 17 IF_ICMPNE L306 - false
   * 3 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I67 Branch 20 IF_ICMPEQ L317 - false
   * 4 gnu.trove.impl.hash.TByteShortHash.removeAt(I)V: root-Branch
   * 5 gnu.trove.impl.hash.TByteShortHash.index(B)I: I37 Branch 5 IFNE L228 - true
   * 6 gnu.trove.impl.hash.TByteShortHash.index(B)I: I46 Branch 6 IF_ICMPNE L231 - false
   * 7 gnu.trove.impl.hash.TByteShortHash.index(B)I: I51 Branch 7 IF_ICMPNE L231 - false
   * 8 gnu.trove.impl.hash.TByteShortHash.insertKey(B)I: I56 Branch 15 IF_ICMPNE L286 - false
   * 9 gnu.trove.impl.hash.TByteShortHash.insertKey(B)I: I50 Branch 14 IF_ICMPNE L286 - true
   */

  @Test
  public void test10()  throws Throwable  {
      byte[] byteArray0 = new byte[4];
      short[] shortArray0 = new short[8];
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(byteArray0, shortArray0);
      tByteShortHashMap0.remove((byte)0);
      assertEquals("{}", tByteShortHashMap0.toString());
      
      tByteShortHashMap0.putIfAbsent((byte)0, (byte)0);
      assertEquals(17, tByteShortHashMap0.capacity());
  }

  //Test case number: 11
  /*
   * 12 covered goals:
   * 1 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I103 Branch 22 IF_ICMPNE L327 - true
   * 2 gnu.trove.impl.hash.TByteShortHash.<init>(I)V: root-Branch
   * 3 gnu.trove.impl.hash.TByteShortHash.insertKeyAt(IB)V: root-Branch
   * 4 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I62 Branch 19 IFNE L316 - false
   * 5 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I67 Branch 20 IF_ICMPEQ L317 - true
   * 6 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I97 Branch 21 IF_ICMPNE L327 - false
   * 7 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I103 Branch 22 IF_ICMPNE L327 - false
   * 8 gnu.trove.impl.hash.TByteShortHash.insertKey(B)I: I30 Branch 13 IFNE L279 - false
   * 9 gnu.trove.impl.hash.TByteShortHash.insertKey(B)I: I50 Branch 14 IF_ICMPNE L286 - false
   * 10 gnu.trove.impl.hash.TByteShortHash.insertKey(B)I: I56 Branch 15 IF_ICMPNE L286 - true
   * 11 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I45 Branch 18 IFGE L310 - true
   * 12 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I115 Branch 23 IF_ICMPNE L332 - true
   */

//   @Test
//   public void test11()  throws Throwable  {
//       byte[] byteArray0 = new byte[4];
//       byteArray0[0] = (byte)2;
//       byteArray0[1] = (byte)77;
//       byteArray0[0] = (byte) (-14);
//       short[] shortArray0 = new short[5];
//       TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(byteArray0, shortArray0);
//       assertEquals(3, tByteShortHashMap0.size());
//       assertEquals(0, tByteShortHashMap0.getNoEntryKey());
//       assertEquals("{-14=0, 0=0, 77=0}", tByteShortHashMap0.toString());
//       assertEquals(0, tByteShortHashMap0.getNoEntryValue());
//   }

  //Test case number: 12
  /*
   * 12 covered goals:
   * 1 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I115 Branch 23 IF_ICMPNE L332 - false
   * 2 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I120 Branch 24 IF_ICMPEQ L336 - true
   * 3 gnu.trove.impl.hash.TByteShortHash.<init>(IFBS)V: root-Branch
   * 4 gnu.trove.impl.hash.TByteShortHash.setUp(I)I: root-Branch
   * 5 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I28 Branch 16 IF_ICMPNE L306 - true
   * 6 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I45 Branch 18 IFGE L310 - true
   * 7 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I45 Branch 18 IFGE L310 - false
   * 8 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I62 Branch 19 IFNE L316 - true
   * 9 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I97 Branch 21 IF_ICMPNE L327 - true
   * 10 gnu.trove.impl.hash.TByteShortHash.insertKeyRehash(BIIB)I: I115 Branch 23 IF_ICMPNE L332 - true
   * 11 gnu.trove.impl.hash.TByteShortHash.insertKey(B)I: I30 Branch 13 IFNE L279 - true
   * 12 gnu.trove.impl.hash.TByteShortHash.insertKey(B)I: I50 Branch 14 IF_ICMPNE L286 - true
   */

  @Test
  public void test12()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap((-8), (float) (-8), (byte) (-97), (short) (byte) (-97));
      byte[] byteArray0 = new byte[6];
      byteArray0[0] = (byte) (-97);
      byteArray0[1] = (byte) (-97);
      byteArray0[2] = (byte) (-97);
      tByteShortHashMap0._states = byteArray0;
      // Undeclared exception!
      try {
        tByteShortHashMap0.putIfAbsent((byte) (-97), (byte) (-97));
        fail("Expecting exception: IllegalStateException");
      
      } catch(IllegalStateException e) {
         //
         // No free or removed slots available. Key set full?!!
         //
      }
  }
}
