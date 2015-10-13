/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.unmodifiable;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.TIntCollection;
import gnu.trove.impl.sync.TSynchronizedIntList;
import gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection;
import gnu.trove.impl.unmodifiable.TUnmodifiableIntList;
import gnu.trove.impl.unmodifiable.TUnmodifiableIntSet;
import gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessIntList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.linked.TIntLinkedList;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TUnmodifiableIntCollectionEvoSuite_DefUse {

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
   * 1 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 7 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in size()I.3 root-Branch Line 63
   * 2 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 37 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in size()I.4 root-Branch Line 63
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.size()I: root-Branch
   * 4 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method <init>(Lgnu/trove/TIntCollection;)V
	Use 6 for Parameter-Variable "<init>(Lgnu/trove/TIntCollection;)V_LV_1" in <init>(Lgnu/trove/TIntCollection;)V.17 Branch 1t Line 60
   * 5 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method <init>(Lgnu/trove/TIntCollection;)V
	Use 5 for Parameter-Variable "<init>(Lgnu/trove/TIntCollection;)V_LV_1" in <init>(Lgnu/trove/TIntCollection;)V.6 root-Branch Line 58
   * 6 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.<init>(Lgnu/trove/TIntCollection;)V: I7 Branch 1 IFNONNULL L58 - true
   */

  @Test
  public void test0()  throws Throwable  {
      TIntHashSet tIntHashSet0 = new TIntHashSet(0, 0);
      TUnmodifiableIntCollection tUnmodifiableIntCollection0 = new TUnmodifiableIntCollection((TIntCollection) tIntHashSet0);
      int int0 = tUnmodifiableIntCollection0.size();
      assertEquals(0, int0);
  }

  //Test case number: 1
  /*
   * 3 covered goals:
   * 1 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 34 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in isEmpty()Z.4 root-Branch Line 64
   * 2 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 8 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in isEmpty()Z.3 root-Branch Line 64
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.isEmpty()Z: root-Branch
   */

  @Test
  public void test1()  throws Throwable  {
      TIntArrayList tIntArrayList0 = new TIntArrayList(1);
      TUnmodifiableRandomAccessIntList tUnmodifiableRandomAccessIntList0 = new TUnmodifiableRandomAccessIntList((TIntList) tIntArrayList0);
      boolean boolean0 = tUnmodifiableRandomAccessIntList0.isEmpty();
      assertEquals(true, boolean0);
  }

  //Test case number: 2
  /*
   * 4 covered goals:
   * 1 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 9 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in contains(I)Z.3 root-Branch Line 65
   * 2 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 28 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in contains(I)Z.5 root-Branch Line 65
   * 3 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method contains(I)Z
	Use 10 for Parameter-Variable "contains(I)Z_LV_1" in contains(I)Z.4 root-Branch Line 65
   * 4 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.contains(I)Z: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      TIntHashSet tIntHashSet0 = new TIntHashSet(0, 0);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      boolean boolean0 = tUnmodifiableIntSet0.contains((-1084));
      assertEquals(false, boolean0);
  }

  //Test case number: 3
  /*
   * 3 covered goals:
   * 1 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 31 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in toArray()[I.4 root-Branch Line 66
   * 2 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 11 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in toArray()[I.3 root-Branch Line 66
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.toArray()[I: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      TIntLinkedList tIntLinkedList0 = new TIntLinkedList();
      TUnmodifiableRandomAccessIntList tUnmodifiableRandomAccessIntList0 = new TUnmodifiableRandomAccessIntList((TIntList) tIntLinkedList0);
      int[] intArray0 = tUnmodifiableRandomAccessIntList0.toArray();
      assertNotNull(intArray0);
  }

  //Test case number: 4
  /*
   * 4 covered goals:
   * 1 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 12 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in toArray([I)[I.3 root-Branch Line 67
   * 2 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 30 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in toArray([I)[I.5 root-Branch Line 67
   * 3 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method toArray([I)[I
	Use 13 for Parameter-Variable "toArray([I)[I_LV_1" in toArray([I)[I.4 root-Branch Line 67
   * 4 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.toArray([I)[I: root-Branch
   */

  @Test
  public void test4()  throws Throwable  {
      int[] intArray0 = new int[3];
      TIntHashSet tIntHashSet0 = new TIntHashSet(0, 0.0F, 0);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      int[] intArray1 = tUnmodifiableIntSet0.toArray(intArray0);
      assertSame(intArray1, intArray0);
  }

  //Test case number: 5
  /*
   * 3 covered goals:
   * 1 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 14 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in toString()Ljava/lang/String;.3 root-Branch Line 68
   * 2 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 32 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in toString()Ljava/lang/String;.4 root-Branch Line 68
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.toString()Ljava/lang/String;: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      TIntHashSet tIntHashSet0 = new TIntHashSet((-767), 1447.4852F);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      String string0 = tUnmodifiableIntSet0.toString();
      assertEquals("{}", string0);
  }

  //Test case number: 6
  /*
   * 3 covered goals:
   * 1 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 15 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in getNoEntryValue()I.3 root-Branch Line 69
   * 2 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 27 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in getNoEntryValue()I.4 root-Branch Line 69
   * 3 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.getNoEntryValue()I: root-Branch
   */

  @Test
  public void test6()  throws Throwable  {
      TIntArrayList tIntArrayList0 = new TIntArrayList(1);
      TUnmodifiableRandomAccessIntList tUnmodifiableRandomAccessIntList0 = new TUnmodifiableRandomAccessIntList((TIntList) tIntArrayList0);
      int int0 = tUnmodifiableRandomAccessIntList0.getNoEntryValue();
      assertEquals(0, int0);
  }

  //Test case number: 7
  /*
   * 4 covered goals:
   * 1 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 25 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in forEach(Lgnu/trove/procedure/TIntProcedure;)Z.5 root-Branch Line 70
   * 2 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 16 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in forEach(Lgnu/trove/procedure/TIntProcedure;)Z.3 root-Branch Line 70
   * 3 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method forEach(Lgnu/trove/procedure/TIntProcedure;)Z
	Use 17 for Parameter-Variable "forEach(Lgnu/trove/procedure/TIntProcedure;)Z_LV_1" in forEach(Lgnu/trove/procedure/TIntProcedure;)Z.4 root-Branch Line 70
   * 4 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.forEach(Lgnu/trove/procedure/TIntProcedure;)Z: root-Branch
   */

  @Test
  public void test7()  throws Throwable  {
      TIntLinkedList tIntLinkedList0 = new TIntLinkedList();
      TUnmodifiableRandomAccessIntList tUnmodifiableRandomAccessIntList0 = new TUnmodifiableRandomAccessIntList((TIntList) tIntLinkedList0);
      boolean boolean0 = tUnmodifiableRandomAccessIntList0.forEach((TIntProcedure) null);
      assertEquals(true, boolean0);
  }

  //Test case number: 8
  /*
   * 4 covered goals:
   * 1 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 18 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in containsAll(Ljava/util/Collection;)Z.3 root-Branch Line 85
   * 2 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 33 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in containsAll(Ljava/util/Collection;)Z.5 root-Branch Line 85
   * 3 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method containsAll(Ljava/util/Collection;)Z
	Use 19 for Parameter-Variable "containsAll(Ljava/util/Collection;)Z_LV_1" in containsAll(Ljava/util/Collection;)Z.4 root-Branch Line 85
   * 4 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.containsAll(Ljava/util/Collection;)Z: root-Branch
   */

  @Test
  public void test8()  throws Throwable  {
      TIntHashSet tIntHashSet0 = new TIntHashSet((-767), 1447.4852F);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
      boolean boolean0 = tUnmodifiableIntSet0.containsAll((Collection<?>) linkedList0);
      assertEquals(true, boolean0);
  }

  //Test case number: 9
  /*
   * 4 covered goals:
   * 1 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 20 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in containsAll(Lgnu/trove/TIntCollection;)Z.3 root-Branch Line 86
   * 2 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 29 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in containsAll(Lgnu/trove/TIntCollection;)Z.5 root-Branch Line 86
   * 3 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method containsAll(Lgnu/trove/TIntCollection;)Z
	Use 21 for Parameter-Variable "containsAll(Lgnu/trove/TIntCollection;)Z_LV_1" in containsAll(Lgnu/trove/TIntCollection;)Z.4 root-Branch Line 86
   * 4 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.containsAll(Lgnu/trove/TIntCollection;)Z: root-Branch
   */

  @Test
  public void test9()  throws Throwable  {
      TIntArrayList tIntArrayList0 = new TIntArrayList(0, 1570);
      TSynchronizedIntList tSynchronizedIntList0 = new TSynchronizedIntList((TIntList) tIntArrayList0, (Object) null);
      TUnmodifiableIntList tUnmodifiableIntList0 = new TUnmodifiableIntList((TIntList) tSynchronizedIntList0);
      // Undeclared exception!
      try {
        tUnmodifiableIntList0.containsAll((TIntCollection) tIntArrayList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 10
  /*
   * 4 covered goals:
   * 1 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 24 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in containsAll([I)Z.5 root-Branch Line 87
   * 2 INTRA_CLASS-Definition-Use-Pair
	Definition 2 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in <init>(Lgnu/trove/TIntCollection;)V.18 Branch 1t Line 60
	Use 22 for Field-Variable "gnu/trove/impl/unmodifiable/TUnmodifiableIntCollection.c" in containsAll([I)Z.3 root-Branch Line 87
   * 3 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method containsAll([I)Z
	Use 23 for Parameter-Variable "containsAll([I)Z_LV_1" in containsAll([I)Z.4 root-Branch Line 87
   * 4 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.containsAll([I)Z: root-Branch
   */

  @Test
  public void test10()  throws Throwable  {
      TIntLinkedList tIntLinkedList0 = new TIntLinkedList(10);
      TUnmodifiableIntCollection tUnmodifiableIntCollection0 = new TUnmodifiableIntCollection((TIntCollection) tIntLinkedList0);
      int[] intArray0 = new int[3];
      boolean boolean0 = tUnmodifiableIntCollection0.containsAll(intArray0);
      assertEquals(false, boolean0);
  }

  //Test case number: 11
  /*
   * 3 covered goals:
   * 1 gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection.retainAll(Ljava/util/Collection;)Z: root-Branch
   * 2 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method <init>(Lgnu/trove/TIntCollection;)V
	Use 6 for Parameter-Variable "<init>(Lgnu/trove/TIntCollection;)V_LV_1" in <init>(Lgnu/trove/TIntCollection;)V.17 Branch 1t Line 60
   * 3 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method <init>(Lgnu/trove/TIntCollection;)V
	Use 5 for Parameter-Variable "<init>(Lgnu/trove/TIntCollection;)V_LV_1" in <init>(Lgnu/trove/TIntCollection;)V.6 root-Branch Line 58
   */

  @Test
  public void test11()  throws Throwable  {
      TIntHashSet tIntHashSet0 = new TIntHashSet();
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      LinkedList<Object> linkedList0 = new LinkedList<Object>();
      // Undeclared exception!
      try {
        tUnmodifiableIntSet0.retainAll((Collection<?>) linkedList0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }
}
