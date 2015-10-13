/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.TDoubleCollection;
import gnu.trove.map.hash.TDoubleObjectHashMap;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.set.hash.TDoubleHashSet;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TDoubleHashEvoSuite_Branch {

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
   * 1 gnu.trove.impl.hash.TDoubleHash.getNoEntryValue()D: root-Branch
   * 2 gnu.trove.impl.hash.TDoubleHash.setUp(I)I: root-Branch
   * 3 gnu.trove.impl.hash.TDoubleHash.<init>(IF)V: I17 Branch 3 IFEQ L103 - true
   */

  @Test
  public void test0()  throws Throwable  {
      TDoubleObjectHashMap<Object> tDoubleObjectHashMap0 = new TDoubleObjectHashMap<Object>((-16), (-16));
      double double0 = tDoubleObjectHashMap0.getNoEntryValue();
      assertEquals(0.0, double0, 0.01D);
  }

  //Test case number: 1
  /*
   * 4 covered goals:
   * 1 gnu.trove.impl.hash.TDoubleHash.forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z: I18 Branch 6 IFLE L177 - true
   * 2 gnu.trove.impl.hash.TDoubleHash.forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z: I18 Branch 6 IFLE L177 - false
   * 3 gnu.trove.impl.hash.TDoubleHash.forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z: I25 Branch 7 IF_ICMPNE L178 - true
   * 4 gnu.trove.impl.hash.TDoubleHash.<init>(IFD)V: I16 Branch 4 IFEQ L122 - true
   */

  @Test
  public void test1()  throws Throwable  {
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(0, 0, 0);
      boolean boolean0 = tDoubleHashSet0.forEach((TDoubleProcedure) null);
      assertEquals(0.0, tDoubleHashSet0.getNoEntryValue(), 0.01D);
      assertEquals(true, boolean0);
  }

  //Test case number: 2
  /*
   * 9 covered goals:
   * 1 gnu.trove.impl.hash.TDoubleHash.forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z: I25 Branch 7 IF_ICMPNE L178 - false
   * 2 gnu.trove.impl.hash.TDoubleHash.forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z: I18 Branch 6 IFLE L177 - false
   * 3 gnu.trove.impl.hash.TDoubleHash.forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z: I25 Branch 7 IF_ICMPNE L178 - true
   * 4 gnu.trove.impl.hash.TDoubleHash.insertKeyAt(ID)V: root-Branch
   * 5 gnu.trove.impl.hash.TDoubleHash.<init>(I)V: I16 Branch 2 IFEQ L85 - true
   * 6 gnu.trove.impl.hash.TDoubleHash.insertKey(D)I: I30 Branch 17 IFNE L263 - true
   * 7 gnu.trove.impl.hash.TDoubleHash.insertKey(D)I: I30 Branch 17 IFNE L263 - false
   * 8 gnu.trove.impl.hash.TDoubleHash.insertKey(D)I: I50 Branch 18 IF_ICMPNE L270 - false
   * 9 gnu.trove.impl.hash.TDoubleHash.insertKey(D)I: I57 Branch 19 IFNE L270 - false
   */

  @Test
  public void test2()  throws Throwable  {
      double[] doubleArray0 = new double[2];
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      // Undeclared exception!
      try {
        tDoubleHashSet0.forEach((TDoubleProcedure) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 3
  /*
   * 15 covered goals:
   * 1 gnu.trove.impl.hash.TDoubleHash.index(D)I: I52 Branch 11 IFNE L216 - true
   * 2 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I46 Branch 13 IFNE L235 - true
   * 3 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I59 Branch 14 IFNE L239 - false
   * 4 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I62 Branch 15 IF_ICMPEQ L239 - false
   * 5 gnu.trove.impl.hash.TDoubleHash.insertKey(D)I: I57 Branch 19 IFNE L270 - true
   * 6 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I28 Branch 20 IF_ICMPNE L290 - true
   * 7 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I45 Branch 22 IFGE L294 - false
   * 8 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I62 Branch 23 IFNE L300 - false
   * 9 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I67 Branch 24 IF_ICMPEQ L301 - true
   * 10 gnu.trove.impl.hash.TDoubleHash.contains(D)Z: I5 Branch 5 IFLT L163 - false
   * 11 gnu.trove.impl.hash.TDoubleHash.index(D)I: I46 Branch 10 IF_ICMPNE L216 - false
   * 12 gnu.trove.impl.hash.TDoubleHash.index(D)I: I52 Branch 11 IFNE L216 - false
   * 13 gnu.trove.impl.hash.TDoubleHash.index(D)I: I37 Branch 9 IFNE L213 - true
   * 14 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I29 Branch 12 IFGE L230 - false
   * 15 gnu.trove.impl.hash.TDoubleHash.<init>(IFD)V: I16 Branch 4 IFEQ L122 - false
   */

  @Test
  public void test3()  throws Throwable  {
      double[] doubleArray0 = new double[10];
      doubleArray0[1] = (double) 1464;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      TDoubleHashSet tDoubleHashSet1 = new TDoubleHashSet(1464, (float) 1464, 699.1874782791302);
      tDoubleHashSet1.addAll(doubleArray0);
      boolean boolean0 = tDoubleHashSet0.retainAll((TDoubleCollection) tDoubleHashSet1);
      assertEquals("{1464.0,0.0}", tDoubleHashSet1.toString());
      assertEquals(false, boolean0);
  }

  //Test case number: 4
  /*
   * 9 covered goals:
   * 1 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I62 Branch 15 IF_ICMPEQ L239 - true
   * 2 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I46 Branch 13 IFNE L235 - false
   * 3 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I59 Branch 14 IFNE L239 - false
   * 4 gnu.trove.impl.hash.TDoubleHash.removeAt(I)V: root-Branch
   * 5 gnu.trove.impl.hash.TDoubleHash.contains(D)Z: I5 Branch 5 IFLT L163 - true
   * 6 gnu.trove.impl.hash.TDoubleHash.index(D)I: I46 Branch 10 IF_ICMPNE L216 - true
   * 7 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I29 Branch 12 IFGE L230 - true
   * 8 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I59 Branch 14 IFNE L239 - true
   * 9 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I71 Branch 16 IF_ICMPNE L241 - true
   */

  @Test
  public void test4()  throws Throwable  {
      double[] doubleArray0 = new double[9];
      doubleArray0[3] = (double) 1464;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      TDoubleHashSet tDoubleHashSet1 = new TDoubleHashSet(1464, 1347.2144F, (double) 1464);
      tDoubleHashSet1.addAll(tDoubleHashSet0._set);
      LinkedList<Object> linkedList0 = new LinkedList<Object>();
      tDoubleHashSet1.retainAll((Collection<?>) linkedList0);
      boolean boolean0 = tDoubleHashSet0.retainAll((TDoubleCollection) tDoubleHashSet1);
      assertEquals(true, tDoubleHashSet1.isEmpty());
      assertEquals(true, boolean0);
  }

  //Test case number: 5
  /*
   * 8 covered goals:
   * 1 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I71 Branch 16 IF_ICMPNE L241 - false
   * 2 gnu.trove.impl.hash.TDoubleHash.index(D)I: I37 Branch 9 IFNE L213 - true
   * 3 gnu.trove.impl.hash.TDoubleHash.index(D)I: I46 Branch 10 IF_ICMPNE L216 - true
   * 4 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I29 Branch 12 IFGE L230 - true
   * 5 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I29 Branch 12 IFGE L230 - false
   * 6 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I46 Branch 13 IFNE L235 - true
   * 7 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I59 Branch 14 IFNE L239 - true
   * 8 gnu.trove.impl.hash.TDoubleHash.indexRehashed(DIIB)I: I71 Branch 16 IF_ICMPNE L241 - true
   */

  @Test
  public void test5()  throws Throwable  {
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(0, 0, 0);
      byte[] byteArray0 = new byte[4];
      byteArray0[0] = (byte) (-99);
      byteArray0[1] = (byte) (-99);
      byteArray0[2] = (byte) (-99);
      tDoubleHashSet0._states = byteArray0;
      boolean boolean0 = tDoubleHashSet0.contains((double) (byte) (-99));
      assertEquals(0.0, tDoubleHashSet0.getNoEntryValue(), 0.01D);
      assertEquals(false, boolean0);
  }

  //Test case number: 6
  /*
   * 7 covered goals:
   * 1 gnu.trove.impl.hash.TDoubleHash.insertKey(D)I: I50 Branch 18 IF_ICMPNE L270 - true
   * 2 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I28 Branch 20 IF_ICMPNE L290 - false
   * 3 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I31 Branch 21 IF_ICMPNE L290 - false
   * 4 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I67 Branch 24 IF_ICMPEQ L301 - false
   * 5 gnu.trove.impl.hash.TDoubleHash.<init>()V: I15 Branch 1 IFEQ L68 - true
   * 6 gnu.trove.impl.hash.TDoubleHash.contains(D)Z: I5 Branch 5 IFLT L163 - true
   * 7 gnu.trove.impl.hash.TDoubleHash.index(D)I: I37 Branch 9 IFNE L213 - false
   */

  @Test
  public void test6()  throws Throwable  {
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet();
      TDoubleHashSet tDoubleHashSet1 = new TDoubleHashSet(tDoubleHashSet0._set);
      tDoubleHashSet1.retainAll((TDoubleCollection) tDoubleHashSet0);
      assertEquals(true, tDoubleHashSet1.isEmpty());
      
      boolean boolean0 = tDoubleHashSet1.addAll(tDoubleHashSet1._set);
      assertEquals(true, boolean0);
  }

  //Test case number: 7
  /*
   * 10 covered goals:
   * 1 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I31 Branch 21 IF_ICMPNE L290 - true
   * 2 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I45 Branch 22 IFGE L294 - true
   * 3 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I62 Branch 23 IFNE L300 - true
   * 4 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I97 Branch 25 IF_ICMPNE L311 - true
   * 5 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I116 Branch 27 IF_ICMPNE L316 - true
   * 6 gnu.trove.impl.hash.TDoubleHash.removeAt(I)V: root-Branch
   * 7 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I28 Branch 20 IF_ICMPNE L290 - false
   * 8 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I31 Branch 21 IF_ICMPNE L290 - false
   * 9 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I67 Branch 24 IF_ICMPEQ L301 - false
   * 10 gnu.trove.impl.hash.TDoubleHash.<init>(IFD)V: I16 Branch 4 IFEQ L122 - false
   */

  @Test
  public void test7()  throws Throwable  {
      double[] doubleArray0 = new double[10];
      doubleArray0[1] = (double) 1464;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      TDoubleHashSet tDoubleHashSet1 = new TDoubleHashSet(1464, (float) 1464, 699.1874782791302);
      tDoubleHashSet1.addAll(tDoubleHashSet0._set);
      LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
      tDoubleHashSet0.retainAll((Collection<?>) linkedList0);
      boolean boolean0 = tDoubleHashSet0.addAll(tDoubleHashSet1._set);
      assertEquals(2, tDoubleHashSet1.size());
      assertEquals(true, boolean0);
  }

  //Test case number: 8
  /*
   * 11 covered goals:
   * 1 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I104 Branch 26 IFNE L311 - false
   * 2 gnu.trove.impl.hash.TDoubleHash.insertKeyAt(ID)V: root-Branch
   * 3 gnu.trove.impl.hash.TDoubleHash.<init>(I)V: I16 Branch 2 IFEQ L85 - true
   * 4 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I62 Branch 23 IFNE L300 - false
   * 5 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I67 Branch 24 IF_ICMPEQ L301 - true
   * 6 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I97 Branch 25 IF_ICMPNE L311 - false
   * 7 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I104 Branch 26 IFNE L311 - true
   * 8 gnu.trove.impl.hash.TDoubleHash.insertKey(D)I: I30 Branch 17 IFNE L263 - false
   * 9 gnu.trove.impl.hash.TDoubleHash.insertKey(D)I: I50 Branch 18 IF_ICMPNE L270 - false
   * 10 gnu.trove.impl.hash.TDoubleHash.insertKey(D)I: I57 Branch 19 IFNE L270 - true
   * 11 gnu.trove.impl.hash.TDoubleHash.insertKey(D)I: I57 Branch 19 IFNE L270 - false
   */

  @Test
  public void test8()  throws Throwable  {
      double[] doubleArray0 = new double[9];
      doubleArray0[1] = (double) 1347.2144F;
      doubleArray0[3] = (double) 1464;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      tDoubleHashSet0.trimToSize();
      boolean boolean0 = tDoubleHashSet0.addAll(tDoubleHashSet0._set);
      assertEquals(7, tDoubleHashSet0.capacity());
      assertEquals(false, boolean0);
  }

  //Test case number: 9
  /*
   * 12 covered goals:
   * 1 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I116 Branch 27 IF_ICMPNE L316 - false
   * 2 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I121 Branch 28 IF_ICMPEQ L320 - true
   * 3 gnu.trove.impl.hash.TDoubleHash.setUp(I)I: root-Branch
   * 4 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I28 Branch 20 IF_ICMPNE L290 - true
   * 5 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I45 Branch 22 IFGE L294 - true
   * 6 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I45 Branch 22 IFGE L294 - false
   * 7 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I62 Branch 23 IFNE L300 - true
   * 8 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I97 Branch 25 IF_ICMPNE L311 - true
   * 9 gnu.trove.impl.hash.TDoubleHash.insertKeyRehash(DIIB)I: I116 Branch 27 IF_ICMPNE L316 - true
   * 10 gnu.trove.impl.hash.TDoubleHash.insertKey(D)I: I30 Branch 17 IFNE L263 - true
   * 11 gnu.trove.impl.hash.TDoubleHash.insertKey(D)I: I50 Branch 18 IF_ICMPNE L270 - true
   * 12 gnu.trove.impl.hash.TDoubleHash.<init>(IFD)V: I16 Branch 4 IFEQ L122 - true
   */

  @Test
  public void test9()  throws Throwable  {
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(0, 0, 0);
      byte[] byteArray0 = new byte[4];
      byteArray0[0] = (byte) (-99);
      byteArray0[1] = (byte) (-99);
      byteArray0[2] = (byte) (-99);
      tDoubleHashSet0._states = byteArray0;
      // Undeclared exception!
      try {
        tDoubleHashSet0.addAll(tDoubleHashSet0._set);
        fail("Expecting exception: IllegalStateException");
      
      } catch(IllegalStateException e) {
         //
         // No free or removed slots available. Key set full?!!
         //
      }
  }
}
