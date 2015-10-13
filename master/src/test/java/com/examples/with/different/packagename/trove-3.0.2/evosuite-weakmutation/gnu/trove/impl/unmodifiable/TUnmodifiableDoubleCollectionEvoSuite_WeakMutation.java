/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.unmodifiable;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.TDoubleCollection;
import gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection;
import gnu.trove.impl.unmodifiable.TUnmodifiableDoubleList;
import gnu.trove.impl.unmodifiable.TUnmodifiableDoubleSet;
import gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessDoubleList;
import gnu.trove.list.TDoubleList;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.linked.TDoubleLinkedList;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.set.TDoubleSet;
import gnu.trove.set.hash.TDoubleHashSet;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TUnmodifiableDoubleCollectionEvoSuite_WeakMutation {

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
   * 1 Weak Mutation 7: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.size()I:63 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 2 Weak Mutation 8: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.size()I:63 - DeleteStatement: size()I
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.size()I: root-Branch
   * 4 Weak Mutation 4: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.<init>(Lgnu/trove/TDoubleCollection;)V:58 - ReplaceVariable c -> c
   * 5 Weak Mutation 5: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.<init>(Lgnu/trove/TDoubleCollection;)V:58 - ReplaceComparisonOperator != null -> = null
   * 6 Weak Mutation 6: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.<init>(Lgnu/trove/TDoubleCollection;)V:60 - ReplaceVariable c -> c
   * 7 Weak Mutation 7: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.size()I:63 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 8 Weak Mutation 8: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.size()I:63 - DeleteStatement: size()I
   * 9 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.<init>(Lgnu/trove/TDoubleCollection;)V: I7 Branch 1 IFNONNULL L58 - true
   */

  @Test
  public void test0()  throws Throwable  {
      LinkedList<Double> linkedList0 = new LinkedList<Double>();
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet((Collection<? extends Double>) linkedList0);
      TUnmodifiableDoubleSet tUnmodifiableDoubleSet0 = new TUnmodifiableDoubleSet((TDoubleSet) tDoubleHashSet0);
      int int0 = tUnmodifiableDoubleSet0.size();
      assertEquals(0, int0);
  }

  //Test case number: 1
  /*
   * 5 covered goals:
   * 1 Weak Mutation 9: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.isEmpty()Z:64 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 2 Weak Mutation 10: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.isEmpty()Z:64 - DeleteStatement: isEmpty()Z
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.isEmpty()Z: root-Branch
   * 4 Weak Mutation 9: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.isEmpty()Z:64 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 5 Weak Mutation 10: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.isEmpty()Z:64 - DeleteStatement: isEmpty()Z
   */

  @Test
  public void test1()  throws Throwable  {
      LinkedList<Double> linkedList0 = new LinkedList<Double>();
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet((Collection<? extends Double>) linkedList0);
      TUnmodifiableDoubleSet tUnmodifiableDoubleSet0 = new TUnmodifiableDoubleSet((TDoubleSet) tDoubleHashSet0);
      boolean boolean0 = tUnmodifiableDoubleSet0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 2
  /*
   * 7 covered goals:
   * 1 Weak Mutation 11: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.contains(D)Z:65 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 2 Weak Mutation 12: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.contains(D)Z:65 - InsertUnaryOp Negation of o
   * 3 Weak Mutation 13: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.contains(D)Z:65 - DeleteStatement: contains(D)Z
   * 4 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.contains(D)Z: root-Branch
   * 5 Weak Mutation 11: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.contains(D)Z:65 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 6 Weak Mutation 12: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.contains(D)Z:65 - InsertUnaryOp Negation of o
   * 7 Weak Mutation 13: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.contains(D)Z:65 - DeleteStatement: contains(D)Z
   */

  @Test
  public void test2()  throws Throwable  {
      TDoubleLinkedList tDoubleLinkedList0 = new TDoubleLinkedList((-120.80255579631731));
      TUnmodifiableRandomAccessDoubleList tUnmodifiableRandomAccessDoubleList0 = new TUnmodifiableRandomAccessDoubleList((TDoubleList) tDoubleLinkedList0);
      boolean boolean0 = tUnmodifiableRandomAccessDoubleList0.contains((-120.80255579631731));
      assertEquals(false, boolean0);
  }

  //Test case number: 3
  /*
   * 5 covered goals:
   * 1 Weak Mutation 14: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toArray()[D:66 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 2 Weak Mutation 15: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toArray()[D:66 - DeleteStatement: toArray()[D
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toArray()[D: root-Branch
   * 4 Weak Mutation 14: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toArray()[D:66 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 5 Weak Mutation 15: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toArray()[D:66 - DeleteStatement: toArray()[D
   */

  @Test
  public void test3()  throws Throwable  {
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet();
      TUnmodifiableDoubleSet tUnmodifiableDoubleSet0 = new TUnmodifiableDoubleSet((TDoubleSet) tDoubleHashSet0);
      double[] doubleArray0 = tUnmodifiableDoubleSet0.toArray();
      assertNotNull(doubleArray0);
  }

  //Test case number: 4
  /*
   * 5 covered goals:
   * 1 Weak Mutation 17: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toArray([D)[D:67 - DeleteStatement: toArray([D)[D
   * 2 Weak Mutation 16: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toArray([D)[D:67 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toArray([D)[D: root-Branch
   * 4 Weak Mutation 17: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toArray([D)[D:67 - DeleteStatement: toArray([D)[D
   * 5 Weak Mutation 16: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toArray([D)[D:67 - DeleteField: cLgnu/trove/TDoubleCollection;
   */

  @Test
  public void test4()  throws Throwable  {
      TDoubleLinkedList tDoubleLinkedList0 = new TDoubleLinkedList();
      TUnmodifiableDoubleList tUnmodifiableDoubleList0 = new TUnmodifiableDoubleList((TDoubleList) tDoubleLinkedList0);
      double[] doubleArray0 = new double[1];
      double[] doubleArray1 = tUnmodifiableDoubleList0.toArray(doubleArray0);
      assertSame(doubleArray0, doubleArray1);
  }

  //Test case number: 5
  /*
   * 5 covered goals:
   * 1 Weak Mutation 19: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toString()Ljava/lang/String;:68 - DeleteStatement: toString()Ljava/lang/String;
   * 2 Weak Mutation 18: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toString()Ljava/lang/String;:68 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toString()Ljava/lang/String;: root-Branch
   * 4 Weak Mutation 19: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toString()Ljava/lang/String;:68 - DeleteStatement: toString()Ljava/lang/String;
   * 5 Weak Mutation 18: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.toString()Ljava/lang/String;:68 - DeleteField: cLgnu/trove/TDoubleCollection;
   */

  @Test
  public void test5()  throws Throwable  {
      TDoubleLinkedList tDoubleLinkedList0 = new TDoubleLinkedList(0.0);
      TUnmodifiableRandomAccessDoubleList tUnmodifiableRandomAccessDoubleList0 = new TUnmodifiableRandomAccessDoubleList((TDoubleList) tDoubleLinkedList0);
      String string0 = tUnmodifiableRandomAccessDoubleList0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 6
  /*
   * 5 covered goals:
   * 1 Weak Mutation 21: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.getNoEntryValue()D:69 - DeleteStatement: getNoEntryValue()D
   * 2 Weak Mutation 20: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.getNoEntryValue()D:69 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.getNoEntryValue()D: root-Branch
   * 4 Weak Mutation 21: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.getNoEntryValue()D:69 - DeleteStatement: getNoEntryValue()D
   * 5 Weak Mutation 20: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.getNoEntryValue()D:69 - DeleteField: cLgnu/trove/TDoubleCollection;
   */

  @Test
  public void test6()  throws Throwable  {
      TDoubleLinkedList tDoubleLinkedList0 = new TDoubleLinkedList(6.332164720999337);
      TUnmodifiableDoubleList tUnmodifiableDoubleList0 = new TUnmodifiableDoubleList((TDoubleList) tDoubleLinkedList0);
      double double0 = tUnmodifiableDoubleList0.getNoEntryValue();
      assertEquals(6.332164720999337, double0, 0.01D);
  }

  //Test case number: 7
  /*
   * 5 covered goals:
   * 1 Weak Mutation 23: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z:70 - DeleteStatement: forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z
   * 2 Weak Mutation 22: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z:70 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z: root-Branch
   * 4 Weak Mutation 23: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z:70 - DeleteStatement: forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z
   * 5 Weak Mutation 22: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.forEach(Lgnu/trove/procedure/TDoubleProcedure;)Z:70 - DeleteField: cLgnu/trove/TDoubleCollection;
   */

  @Test
  public void test7()  throws Throwable  {
      TDoubleLinkedList tDoubleLinkedList0 = new TDoubleLinkedList((-120.80255579631731));
      TUnmodifiableRandomAccessDoubleList tUnmodifiableRandomAccessDoubleList0 = new TUnmodifiableRandomAccessDoubleList((TDoubleList) tDoubleLinkedList0);
      boolean boolean0 = tUnmodifiableRandomAccessDoubleList0.forEach((TDoubleProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 8
  /*
   * 12 covered goals:
   * 1 Weak Mutation 2: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection$1.next()D:77 - DeleteField: iLgnu/trove/iterator/TDoubleIterator;
   * 2 Weak Mutation 3: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection$1.next()D:77 - DeleteStatement: next()D
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection$1.next()D: root-Branch
   * 4 Weak Mutation 0: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection$1.hasNext()Z:76 - DeleteField: iLgnu/trove/iterator/TDoubleIterator;
   * 5 Weak Mutation 1: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection$1.hasNext()Z:76 - DeleteStatement: hasNext()Z
   * 6 Weak Mutation 0: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection$1.hasNext()Z:76 - DeleteField: iLgnu/trove/iterator/TDoubleIterator;
   * 7 Weak Mutation 1: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection$1.hasNext()Z:76 - DeleteStatement: hasNext()Z
   * 8 Weak Mutation 2: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection$1.next()D:77 - DeleteField: iLgnu/trove/iterator/TDoubleIterator;
   * 9 Weak Mutation 3: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection$1.next()D:77 - DeleteStatement: next()D
   * 10 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection$1.hasNext()Z: root-Branch
   * 11 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection$1.<init>(Lgnu/trove/impl/unmodifiable/TUnmodifiableDoubleCollection;)V: root-Branch
   * 12 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.iterator()Lgnu/trove/iterator/TDoubleIterator;: root-Branch
   */

  @Test
  public void test8()  throws Throwable  {
      TDoubleArrayList tDoubleArrayList0 = new TDoubleArrayList(1, 1);
      TUnmodifiableRandomAccessDoubleList tUnmodifiableRandomAccessDoubleList0 = new TUnmodifiableRandomAccessDoubleList((TDoubleList) tDoubleArrayList0);
      double[] doubleArray0 = new double[4];
      tDoubleArrayList0.add(doubleArray0);
      boolean boolean0 = tDoubleArrayList0.removeAll((TDoubleCollection) tUnmodifiableRandomAccessDoubleList0);
      assertEquals(false, tDoubleArrayList0.isEmpty());
      assertEquals(true, boolean0);
  }

  //Test case number: 9
  /*
   * 5 covered goals:
   * 1 Weak Mutation 25: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll(Ljava/util/Collection;)Z:85 - DeleteStatement: containsAll(Ljava/util/Collection;)Z
   * 2 Weak Mutation 24: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll(Ljava/util/Collection;)Z:85 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll(Ljava/util/Collection;)Z: root-Branch
   * 4 Weak Mutation 25: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll(Ljava/util/Collection;)Z:85 - DeleteStatement: containsAll(Ljava/util/Collection;)Z
   * 5 Weak Mutation 24: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll(Ljava/util/Collection;)Z:85 - DeleteField: cLgnu/trove/TDoubleCollection;
   */

  @Test
  public void test9()  throws Throwable  {
      TDoubleLinkedList tDoubleLinkedList0 = new TDoubleLinkedList(0.0);
      TUnmodifiableRandomAccessDoubleList tUnmodifiableRandomAccessDoubleList0 = new TUnmodifiableRandomAccessDoubleList((TDoubleList) tDoubleLinkedList0);
      LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
      boolean boolean0 = tUnmodifiableRandomAccessDoubleList0.containsAll((Collection<?>) linkedList0);
      assertEquals(false, boolean0);
  }

  //Test case number: 10
  /*
   * 7 covered goals:
   * 1 Weak Mutation 27: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll(Lgnu/trove/TDoubleCollection;)Z:86 - ReplaceVariable coll -> c
   * 2 Weak Mutation 26: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll(Lgnu/trove/TDoubleCollection;)Z:86 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 3 Weak Mutation 28: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll(Lgnu/trove/TDoubleCollection;)Z:86 - DeleteStatement: containsAll(Lgnu/trove/TDoubleCollection;)Z
   * 4 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll(Lgnu/trove/TDoubleCollection;)Z: root-Branch
   * 5 Weak Mutation 27: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll(Lgnu/trove/TDoubleCollection;)Z:86 - ReplaceVariable coll -> c
   * 6 Weak Mutation 26: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll(Lgnu/trove/TDoubleCollection;)Z:86 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 7 Weak Mutation 28: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll(Lgnu/trove/TDoubleCollection;)Z:86 - DeleteStatement: containsAll(Lgnu/trove/TDoubleCollection;)Z
   */

  @Test
  public void test10()  throws Throwable  {
      double[] doubleArray0 = new double[17];
      TDoubleLinkedList tDoubleLinkedList0 = new TDoubleLinkedList(0.0);
      TUnmodifiableRandomAccessDoubleList tUnmodifiableRandomAccessDoubleList0 = new TUnmodifiableRandomAccessDoubleList((TDoubleList) tDoubleLinkedList0);
      TDoubleArrayList tDoubleArrayList0 = TDoubleArrayList.wrap(doubleArray0);
      boolean boolean0 = tUnmodifiableRandomAccessDoubleList0.containsAll((TDoubleCollection) tDoubleArrayList0);
      assertEquals(false, boolean0);
  }

  //Test case number: 11
  /*
   * 5 covered goals:
   * 1 Weak Mutation 29: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll([D)Z:87 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 2 Weak Mutation 30: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll([D)Z:87 - DeleteStatement: containsAll([D)Z
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll([D)Z: root-Branch
   * 4 Weak Mutation 29: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll([D)Z:87 - DeleteField: cLgnu/trove/TDoubleCollection;
   * 5 Weak Mutation 30: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.containsAll([D)Z:87 - DeleteStatement: containsAll([D)Z
   */

  @Test
  public void test11()  throws Throwable  {
      LinkedList<Double> linkedList0 = new LinkedList<Double>();
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet((Collection<? extends Double>) linkedList0);
      TUnmodifiableDoubleSet tUnmodifiableDoubleSet0 = new TUnmodifiableDoubleSet((TDoubleSet) tDoubleHashSet0);
      boolean boolean0 = tUnmodifiableDoubleSet0.containsAll(tDoubleHashSet0._set);
      assertEquals(false, boolean0);
  }

  //Test case number: 12
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.removeAll(Ljava/util/Collection;)Z: root-Branch
   * 2 Weak Mutation 4: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.<init>(Lgnu/trove/TDoubleCollection;)V:58 - ReplaceVariable c -> c
   * 3 Weak Mutation 6: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.<init>(Lgnu/trove/TDoubleCollection;)V:60 - ReplaceVariable c -> c
   */

  @Test
  public void test12()  throws Throwable  {
      LinkedList<Object> linkedList0 = new LinkedList<Object>();
      double[] doubleArray0 = new double[15];
      TDoubleArrayList tDoubleArrayList0 = TDoubleArrayList.wrap(doubleArray0, 0.0);
      TUnmodifiableRandomAccessDoubleList tUnmodifiableRandomAccessDoubleList0 = new TUnmodifiableRandomAccessDoubleList((TDoubleList) tDoubleArrayList0);
      // Undeclared exception!
      try {
        tUnmodifiableRandomAccessDoubleList0.removeAll((Collection<?>) linkedList0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 13
  /*
   * 2 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.<init>(Lgnu/trove/TDoubleCollection;)V: I7 Branch 1 IFNONNULL L58 - false
   * 2 Weak Mutation 5: gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection.<init>(Lgnu/trove/TDoubleCollection;)V:58 - ReplaceComparisonOperator != null -> = null
   */

  @Test
  public void test13()  throws Throwable  {
      TUnmodifiableDoubleCollection tUnmodifiableDoubleCollection0 = null;
      try {
        tUnmodifiableDoubleCollection0 = new TUnmodifiableDoubleCollection((TDoubleCollection) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }
}
