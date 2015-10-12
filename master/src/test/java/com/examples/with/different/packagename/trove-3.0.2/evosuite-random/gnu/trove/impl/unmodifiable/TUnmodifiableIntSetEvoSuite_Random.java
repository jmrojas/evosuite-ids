/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.unmodifiable;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.TIntCollection;
import gnu.trove.impl.sync.TSynchronizedIntSet;
import gnu.trove.impl.unmodifiable.TUnmodifiableIntSet;
import gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessIntList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.linked.TIntLinkedList;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TUnmodifiableIntSetEvoSuite_Random {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.utils.LoggingUtils.setLoggingForJUnit(); 
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


  @Test
  public void test0()  throws Throwable  {
      int[] intArray0 = new int[7];
      int int0 = 2;
      intArray0[0] = int0;
      int int1 = 0;
      intArray0[1] = int1;
      int int2 = (-1580);
      intArray0[2] = int2;
      int int3 = 0;
      intArray0[3] = int3;
      int int4 = 31;
      intArray0[4] = int4;
      int int5 = 1416;
      intArray0[5] = int5;
      int int6 = 201;
      intArray0[6] = int6;
      TIntHashSet tIntHashSet0 = new TIntHashSet(intArray0);
      PipedInputStream pipedInputStream0 = new PipedInputStream();
      DataInputStream dataInputStream0 = new DataInputStream((InputStream) pipedInputStream0);
      try {
        dataInputStream0.readUTF();
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Pipe not connected
         //
      }
  }

  @Test
  public void test1()  throws Throwable  {
      TIntSet tIntSet0 = null;
      int[] intArray0 = new int[10];
      int int0 = 359339171;
      intArray0[0] = int0;
      int int1 = 1617;
      intArray0[1] = int1;
      int int2 = 1;
      intArray0[2] = int2;
      int int3 = 111;
      intArray0[3] = int3;
      int int4 = (-1433);
      intArray0[4] = int4;
      int int5 = 1;
      intArray0[5] = int5;
      int int6 = (-1);
      intArray0[6] = int6;
      int int7 = 711;
      intArray0[7] = int7;
      int int8 = 1214;
      intArray0[8] = int8;
      int int9 = 0;
      intArray0[9] = int9;
      int int10 = (-1724);
      TIntArrayList tIntArrayList0 = TIntArrayList.wrap(intArray0, int10);
      TUnmodifiableRandomAccessIntList tUnmodifiableRandomAccessIntList0 = new TUnmodifiableRandomAccessIntList((TIntList) tIntArrayList0);
      String string0 = tUnmodifiableRandomAccessIntList0.toString();
      TSynchronizedIntSet tSynchronizedIntSet0 = new TSynchronizedIntSet(tIntSet0, (Object) string0);
      TSynchronizedIntSet tSynchronizedIntSet1 = new TSynchronizedIntSet((TIntSet) tSynchronizedIntSet0);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tSynchronizedIntSet1);
      // Undeclared exception!
      try {
        tUnmodifiableIntSet0.remove(int1);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test2()  throws Throwable  {
      TIntHashSet tIntHashSet0 = new TIntHashSet();
      String string0 = "";
      URI uRI0 = URI.create(string0);
      URI uRI1 = uRI0.resolve(string0);
      String string1 = uRI1.getSchemeSpecificPart();
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      tUnmodifiableIntSet0.isEmpty();
      TSynchronizedIntSet tSynchronizedIntSet0 = new TSynchronizedIntSet((TIntSet) tIntHashSet0, (Object) string1);
      TUnmodifiableIntSet tUnmodifiableIntSet1 = new TUnmodifiableIntSet((TIntSet) tSynchronizedIntSet0);
      int int0 = 0;
      TIntHashSet tIntHashSet1 = new TIntHashSet(int0, int0);
      LinkedList<Object> linkedList0 = new LinkedList<Object>();
      tIntHashSet1.removeAll((Collection<?>) linkedList0);
      linkedList0.clone();
      TUnmodifiableIntSet tUnmodifiableIntSet2 = new TUnmodifiableIntSet((TIntSet) tIntHashSet1);
      // Undeclared exception!
      try {
        tUnmodifiableIntSet2.removeAll((TIntCollection) tIntHashSet1);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test3()  throws Throwable  {
      int int0 = 1126;
      int int1 = 0;
      int int2 = 57955739;
      TIntHashSet tIntHashSet0 = new TIntHashSet(int1);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      tUnmodifiableIntSet0.size();
      TIntHashSet tIntHashSet1 = new TIntHashSet();
      TUnmodifiableIntSet tUnmodifiableIntSet1 = new TUnmodifiableIntSet((TIntSet) tIntHashSet1);
      // Undeclared exception!
      try {
        tUnmodifiableIntSet1.addAll((TIntCollection) tIntHashSet1);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test4()  throws Throwable  {
      int int0 = (-485);
      TIntHashSet tIntHashSet0 = new TIntHashSet(int0, int0, int0);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      // Undeclared exception!
      try {
        tUnmodifiableIntSet0.clear();
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test5()  throws Throwable  {
      int int0 = 0;
      int int1 = 0;
      float float0 = (-459.80637F);
      TIntHashSet tIntHashSet0 = new TIntHashSet(int1, float0, int0);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      // Undeclared exception!
      try {
        tUnmodifiableIntSet0.retainAll(tIntHashSet0._set);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test6()  throws Throwable  {
      int[] intArray0 = new int[8];
      int int0 = (-1607);
      intArray0[0] = int0;
      int int1 = 17135863;
      intArray0[1] = int1;
      int int2 = 75;
      intArray0[2] = int2;
      int int3 = 0;
      intArray0[3] = int3;
      int int4 = 701819;
      intArray0[4] = int4;
      int int5 = 0;
      intArray0[5] = int5;
      int int6 = 168;
      intArray0[6] = int6;
      int int7 = 0;
      intArray0[7] = int7;
      TIntHashSet tIntHashSet0 = new TIntHashSet(intArray0);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      int int8 = 0;
      boolean boolean0 = tUnmodifiableIntSet0.contains(int8);
      assertEquals(true, boolean0);
  }

  @Test
  public void test7()  throws Throwable  {
      int[] intArray0 = new int[1];
      int int0 = 1006;
      intArray0[0] = int0;
      TIntHashSet tIntHashSet0 = new TIntHashSet(intArray0);
      TIntHashSet tIntHashSet1 = new TIntHashSet((TIntCollection) tIntHashSet0);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet1);
      TUnmodifiableIntSet tUnmodifiableIntSet1 = new TUnmodifiableIntSet((TIntSet) tUnmodifiableIntSet0);
      LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
      tUnmodifiableIntSet1.containsAll((Collection<?>) linkedList0);
      TIntHashSet tIntHashSet2 = new TIntHashSet(intArray0);
      TUnmodifiableIntSet tUnmodifiableIntSet2 = new TUnmodifiableIntSet((TIntSet) tIntHashSet2);
      TIntHashSet tIntHashSet3 = (TIntHashSet)tUnmodifiableIntSet2.c;
      tIntHashSet2.retainAll((TIntCollection) tIntHashSet3);
      TUnmodifiableIntSet tUnmodifiableIntSet3 = new TUnmodifiableIntSet((TIntSet) tIntHashSet2);
      OutputStream outputStream0 = null;
      ObjectOutputStream objectOutputStream0 = null;
      try {
        objectOutputStream0 = new ObjectOutputStream(outputStream0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test8()  throws Throwable  {
      int int0 = 223;
      TIntHashSet tIntHashSet0 = new TIntHashSet(int0, int0);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      // Undeclared exception!
      try {
        tUnmodifiableIntSet0.retainAll(tIntHashSet0._set);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test9()  throws Throwable  {
      TIntSet tIntSet0 = null;
      TUnmodifiableIntSet tUnmodifiableIntSet0 = null;
      try {
        tUnmodifiableIntSet0 = new TUnmodifiableIntSet(tIntSet0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test10()  throws Throwable  {
      int[] intArray0 = new int[1];
      int int0 = 0;
      TIntHashSet tIntHashSet0 = new TIntHashSet(int0);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      // Undeclared exception!
      try {
        tUnmodifiableIntSet0.clear();
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test11()  throws Throwable  {
      LinkedList<String> linkedList0 = new LinkedList<String>();
      TIntHashSet tIntHashSet0 = new TIntHashSet();
      TSynchronizedIntSet tSynchronizedIntSet0 = new TSynchronizedIntSet((TIntSet) tIntHashSet0);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tSynchronizedIntSet0);
      int[] intArray0 = new int[9];
      int int0 = 0;
      intArray0[0] = int0;
      int int1 = 776;
      intArray0[1] = int1;
      int int2 = 1;
      intArray0[2] = int2;
      int int3 = 1403641;
      intArray0[3] = int3;
      int int4 = 0;
      intArray0[4] = int4;
      int int5 = 1315;
      intArray0[5] = int5;
      int int6 = 0;
      intArray0[6] = int6;
      intArray0[0] = int1;
      intArray0[1] = int4;
      intArray0[2] = int1;
      intArray0[3] = int2;
      int int7 = 221;
      intArray0[4] = int7;
      intArray0[5] = int1;
      intArray0[6] = int5;
      intArray0[7] = int6;
      intArray0[8] = int2;
      int int8 = 1;
      intArray0[7] = int8;
      int int9 = 1;
      intArray0[8] = int9;
      tUnmodifiableIntSet0.containsAll(intArray0);
      int int10 = 1039;
      // Undeclared exception!
      try {
        linkedList0.get(int10);
        fail("Expecting exception: IndexOutOfBoundsException");
      
      } catch(IndexOutOfBoundsException e) {
         //
         // Index: 1039, Size: 0
         //
      }
  }

  @Test
  public void test12()  throws Throwable  {
      int int0 = 0;
      float float0 = 0.0F;
      TIntHashSet tIntHashSet0 = new TIntHashSet(int0, float0, int0);
      String string0 = "|Fi#GLAQb";
      URI uRI0 = null;
      try {
        uRI0 = new URI(string0, string0, string0, string0, string0);
        fail("Expecting exception: URISyntaxException");
      
      } catch(URISyntaxException e) {
         //
         // Relative path in absolute URI: |Fi#GLAQb://%7CFi%23GLAQb%7CFi%23GLAQb?%7CFi%23GLAQb#%7CFi%23GLAQb
         //
      }
  }

  @Test
  public void test13()  throws Throwable  {
      int int0 = (-132);
      TIntList tIntList0 = null;
      TIntLinkedList tIntLinkedList0 = null;
      try {
        tIntLinkedList0 = new TIntLinkedList(tIntList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test14()  throws Throwable  {
      TIntList tIntList0 = null;
      TUnmodifiableRandomAccessIntList tUnmodifiableRandomAccessIntList0 = null;
      try {
        tUnmodifiableRandomAccessIntList0 = new TUnmodifiableRandomAccessIntList(tIntList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test15()  throws Throwable  {
      int int0 = 0;
      float float0 = 1354.0394F;
      TIntHashSet tIntHashSet0 = new TIntHashSet(int0, float0, int0);
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      // Undeclared exception!
      try {
        tUnmodifiableIntSet0.removeAll(tIntHashSet0._set);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test16()  throws Throwable  {
      TIntHashSet tIntHashSet0 = new TIntHashSet();
      TUnmodifiableIntSet tUnmodifiableIntSet0 = new TUnmodifiableIntSet((TIntSet) tIntHashSet0);
      TUnmodifiableIntSet tUnmodifiableIntSet1 = new TUnmodifiableIntSet((TIntSet) tUnmodifiableIntSet0);
      LinkedList<Object> linkedList0 = new LinkedList<Object>();
      // Undeclared exception!
      try {
        tUnmodifiableIntSet1.retainAll((Collection<?>) linkedList0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }
}
