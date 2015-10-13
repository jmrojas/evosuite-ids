/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.sync;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.TByteCollection;
import gnu.trove.impl.sync.TSynchronizedByteList;
import gnu.trove.impl.sync.TSynchronizedRandomAccessByteList;
import gnu.trove.impl.unmodifiable.TUnmodifiableByteList;
import gnu.trove.impl.unmodifiable.TUnmodifiableByteSet;
import gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessByteList;
import gnu.trove.list.TByteList;
import gnu.trove.list.array.TByteArrayList;
import gnu.trove.list.linked.TByteLinkedList;
import gnu.trove.set.TByteSet;
import gnu.trove.set.hash.TByteHashSet;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TSynchronizedByteListEvoSuite_Random {

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
      int int0 = (-137);
      TByteArrayList tByteArrayList0 = null;
      try {
        tByteArrayList0 = new TByteArrayList(int0);
        fail("Expecting exception: NegativeArraySizeException");
      
      } catch(NegativeArraySizeException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test1()  throws Throwable  {
      byte[] byteArray0 = new byte[5];
      byte byte0 = (byte)31;
      byteArray0[0] = byte0;
      byte byte1 = (byte)0;
      byteArray0[1] = byte1;
      byte byte2 = (byte) (-58);
      byteArray0[2] = byte2;
      byte byte3 = (byte) (-2);
      byteArray0[3] = byte3;
      byte byte4 = (byte) (-7);
      byteArray0[4] = byte4;
      TByteList tByteList0 = null;
      String string0 = "3IahpCC*XZo9x,6@";
      File file0 = new File(string0, string0);
      String string1 = file0.getParent();
      TSynchronizedRandomAccessByteList tSynchronizedRandomAccessByteList0 = new TSynchronizedRandomAccessByteList(tByteList0, (Object) string1);
      int int0 = (-1);
      int int1 = 489;
      // Undeclared exception!
      try {
        tSynchronizedRandomAccessByteList0.reverse(int0, int1);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test2()  throws Throwable  {
      TByteLinkedList tByteLinkedList0 = new TByteLinkedList();
      TUnmodifiableRandomAccessByteList tUnmodifiableRandomAccessByteList0 = new TUnmodifiableRandomAccessByteList((TByteList) tByteLinkedList0);
      byte[] byteArray0 = new byte[7];
      byte byte0 = (byte) (-1);
      byteArray0[0] = byte0;
      byte byte1 = (byte)45;
      Locale locale0 = Locale.TRADITIONAL_CHINESE;
      String string0 = locale0.getISO3Language();
      TSynchronizedRandomAccessByteList tSynchronizedRandomAccessByteList0 = new TSynchronizedRandomAccessByteList((TByteList) tUnmodifiableRandomAccessByteList0, (Object) string0);
      // Undeclared exception!
      try {
        tSynchronizedRandomAccessByteList0.max();
        fail("Expecting exception: IllegalStateException");
      
      } catch(IllegalStateException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test3()  throws Throwable  {
      int int0 = 0;
      TByteArrayList tByteArrayList0 = new TByteArrayList();
      Object object0 = null;
      TSynchronizedRandomAccessByteList tSynchronizedRandomAccessByteList0 = new TSynchronizedRandomAccessByteList((TByteList) tByteArrayList0, object0);
      byte[] byteArray0 = new byte[2];
      byte byte0 = (byte) (-124);
      byteArray0[0] = byte0;
      byte byte1 = (byte) (-84);
      byteArray0[1] = byte1;
      // Undeclared exception!
      try {
        tSynchronizedRandomAccessByteList0.containsAll(byteArray0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test4()  throws Throwable  {
      TByteList tByteList0 = null;
      TSynchronizedRandomAccessByteList tSynchronizedRandomAccessByteList0 = null;
      try {
        tSynchronizedRandomAccessByteList0 = new TSynchronizedRandomAccessByteList(tByteList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test5()  throws Throwable  {
      byte[] byteArray0 = new byte[7];
      byte byte0 = (byte)124;
      byteArray0[0] = byte0;
      byte byte1 = (byte) (-128);
      byteArray0[1] = byte1;
      byte byte2 = (byte) (-67);
      byteArray0[2] = byte2;
      byte byte3 = (byte) (-1);
      byteArray0[3] = byte3;
      byte byte4 = (byte)0;
      byteArray0[4] = byte4;
      byte byte5 = (byte)1;
      byteArray0[5] = byte5;
      byte byte6 = (byte) (-1);
      byteArray0[6] = byte6;
      byte byte7 = (byte)41;
      TByteArrayList tByteArrayList0 = TByteArrayList.wrap(byteArray0, byte7);
      int int0 = 903;
      // Undeclared exception!
      try {
        tByteArrayList0.subList(int0, (int) byteArray0[6]);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // end index -1 greater than begin index 903
         //
      }
  }

  @Test
  public void test6()  throws Throwable  {
      TByteList tByteList0 = null;
      String string0 = "";
      // Undeclared exception!
      try {
        File.createTempFile(string0, string0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Prefix string too short
         //
      }
  }

  @Test
  public void test7()  throws Throwable  {
      TByteList tByteList0 = null;
      TSynchronizedByteList tSynchronizedByteList0 = null;
      try {
        tSynchronizedByteList0 = new TSynchronizedByteList(tByteList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

//   @Test
//   public void test8()  throws Throwable  {
//       TByteLinkedList tByteLinkedList0 = new TByteLinkedList();
//       String string0 = "sL8CNR";
//       Locale locale0 = new Locale(string0, string0, string0);
//       String string1 = locale0.getScript();
//       TSynchronizedRandomAccessByteList tSynchronizedRandomAccessByteList0 = new TSynchronizedRandomAccessByteList((TByteList) tByteLinkedList0, (Object) string1);
//       long long0 = (-525L);
//       Random random0 = new Random(long0);
//       tSynchronizedRandomAccessByteList0.shuffle(random0);
//       assertEquals(0, tSynchronizedRandomAccessByteList0.getNoEntryValue());
//   }

  @Test
  public void test9()  throws Throwable  {
      TByteList tByteList0 = null;
      TSynchronizedRandomAccessByteList tSynchronizedRandomAccessByteList0 = null;
      try {
        tSynchronizedRandomAccessByteList0 = new TSynchronizedRandomAccessByteList(tByteList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test10()  throws Throwable  {
      TByteList tByteList0 = null;
      InputStream inputStream0 = null;
      int int0 = (-1253);
      TByteHashSet tByteHashSet0 = new TByteHashSet(int0);
      TUnmodifiableByteSet tUnmodifiableByteSet0 = new TUnmodifiableByteSet((TByteSet) tByteHashSet0);
      TUnmodifiableByteSet tUnmodifiableByteSet1 = new TUnmodifiableByteSet((TByteSet) tUnmodifiableByteSet0);
      String string0 = tUnmodifiableByteSet1.toString();
      TSynchronizedByteList tSynchronizedByteList0 = new TSynchronizedByteList(tByteList0, (Object) string0);
      byte[] byteArray0 = new byte[8];
      byte byte0 = (byte) (-110);
      byteArray0[0] = byte0;
      byte byte1 = (byte) (-54);
      byteArray0[1] = byte1;
      byte byte2 = (byte) (-16);
      byteArray0[2] = byte2;
      byte byte3 = (byte)31;
      byteArray0[3] = byte3;
      byte byte4 = (byte) (-1);
      byteArray0[4] = byte4;
      byte byte5 = (byte)0;
      byteArray0[5] = byte5;
      byte byte6 = (byte)127;
      byteArray0[6] = byte6;
      byte byte7 = (byte)0;
      byteArray0[7] = byte7;
      tByteHashSet0.addAll(byteArray0);
      // Undeclared exception!
      try {
        tSynchronizedByteList0.set(int0, tByteHashSet0._states);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

//   @Test
//   public void test11()  throws Throwable  {
//       byte[] byteArray0 = new byte[8];
//       byte byte0 = (byte) (-74);
//       byteArray0[0] = byte0;
//       byte byte1 = (byte) (-43);
//       byteArray0[1] = byte1;
//       byte byte2 = (byte)1;
//       byteArray0[2] = byte2;
//       byte byte3 = (byte) (-69);
//       byteArray0[3] = byte3;
//       byte byte4 = (byte)0;
//       byteArray0[4] = byte4;
//       byte byte5 = (byte)4;
//       byteArray0[5] = byte5;
//       byte byte6 = (byte)124;
//       byteArray0[6] = byte6;
//       byte byte7 = (byte)9;
//       byteArray0[7] = byte7;
//       TByteArrayList tByteArrayList0 = TByteArrayList.wrap(byteArray0);
//       TSynchronizedByteList tSynchronizedByteList0 = new TSynchronizedByteList((TByteList) tByteArrayList0);
//       assertEquals(124, tSynchronizedByteList0.max());
//   }

//   @Test
//   public void test12()  throws Throwable  {
//       int int0 = 110;
//       byte byte0 = (byte)0;
//       TByteArrayList tByteArrayList0 = new TByteArrayList(int0, byte0);
//       TSynchronizedByteList tSynchronizedByteList0 = new TSynchronizedByteList((TByteList) tByteArrayList0);
//       int int1 = 0;
//       tSynchronizedByteList0.remove((int) byte0, int1);
//       assertEquals(0, tSynchronizedByteList0.sum());
//   }

  @Test
  public void test13()  throws Throwable  {
      byte byte0 = (byte)107;
      TByteLinkedList tByteLinkedList0 = new TByteLinkedList(byte0);
      tByteLinkedList0.reverse();
      // Undeclared exception!
      try {
        tByteLinkedList0.subList((int) byte0, (int) byte0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // begin index 107 greater than last index 0
         //
      }
  }

  @Test
  public void test14()  throws Throwable  {
      TByteArrayList tByteArrayList0 = new TByteArrayList();
      int int0 = 0;
      TByteArrayList tByteArrayList1 = (TByteArrayList)tByteArrayList0.subList(int0, int0);
      TSynchronizedByteList tSynchronizedByteList0 = new TSynchronizedByteList((TByteList) tByteArrayList1);
      int int1 = (-1491);
      byte byte0 = (byte)91;
      // Undeclared exception!
      try {
        tSynchronizedByteList0.replace(int0, byte0);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // Array index out of range: 0
         //
      }
  }

  @Test
  public void test15()  throws Throwable  {
      byte[] byteArray0 = new byte[6];
      byte byte0 = (byte) (-1);
      byteArray0[0] = byte0;
      byte byte1 = (byte)0;
      byteArray0[1] = byte1;
      byte byte2 = (byte) (-37);
      byteArray0[2] = byte2;
      byte byte3 = (byte)63;
      byteArray0[3] = byte3;
      byte byte4 = (byte)0;
      byteArray0[4] = byte4;
      byte byte5 = (byte)0;
      byteArray0[5] = byte5;
      byte byte6 = (byte)0;
      TByteArrayList tByteArrayList0 = TByteArrayList.wrap(byteArray0, byte6);
      String string0 = "";
      Locale locale0 = new Locale(string0, string0);
      String string1 = locale0.getDisplayVariant();
      TSynchronizedRandomAccessByteList tSynchronizedRandomAccessByteList0 = new TSynchronizedRandomAccessByteList((TByteList) tByteArrayList0, (Object) string1);
      TSynchronizedRandomAccessByteList tSynchronizedRandomAccessByteList1 = new TSynchronizedRandomAccessByteList((TByteList) tSynchronizedRandomAccessByteList0);
      TSynchronizedRandomAccessByteList tSynchronizedRandomAccessByteList2 = (TSynchronizedRandomAccessByteList)tSynchronizedRandomAccessByteList1.list;
      TSynchronizedRandomAccessByteList tSynchronizedRandomAccessByteList3 = new TSynchronizedRandomAccessByteList((TByteList) tSynchronizedRandomAccessByteList2);
      TByteArrayList tByteArrayList1 = new TByteArrayList();
      TUnmodifiableByteList tUnmodifiableByteList0 = new TUnmodifiableByteList((TByteList) tByteArrayList1);
      byte byte7 = (byte)0;
      // Undeclared exception!
      try {
        tUnmodifiableByteList0.add(byte7);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test16()  throws Throwable  {
      byte[] byteArray0 = new byte[6];
      byte byte0 = (byte)64;
      byteArray0[0] = byte0;
      byte byte1 = (byte) (-71);
      byteArray0[1] = byte1;
      byte byte2 = (byte)0;
      byteArray0[2] = byte2;
      byte byte3 = (byte) (-71);
      byteArray0[3] = byte3;
      byte byte4 = (byte) (-3);
      byteArray0[4] = byte4;
      byte byte5 = (byte)1;
      byteArray0[5] = byte5;
      TByteArrayList.wrap(byteArray0, byteArray0[2]);
      String string0 = "W";
      StringTokenizer stringTokenizer0 = new StringTokenizer(string0, string0);
      // Undeclared exception!
      try {
        stringTokenizer0.nextToken();
        fail("Expecting exception: NoSuchElementException");
      
      } catch(NoSuchElementException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test17()  throws Throwable  {
      TByteLinkedList tByteLinkedList0 = new TByteLinkedList();
      int int0 = 0;
      TSynchronizedByteList tSynchronizedByteList0 = new TSynchronizedByteList((TByteList) tByteLinkedList0);
      int int1 = 209;
      byte byte0 = (byte)18;
      TByteArrayList tByteArrayList0 = new TByteArrayList(int1, byte0);
      tSynchronizedByteList0.addAll((TByteCollection) tByteArrayList0);
      byte[] byteArray0 = tByteLinkedList0.toArray(int0, int0);
      TByteArrayList.wrap(byteArray0);
      TSynchronizedByteList tSynchronizedByteList1 = new TSynchronizedByteList((TByteList) tByteLinkedList0);
      int int2 = 0;
      tSynchronizedByteList0.set(int2, byteArray0, int0, int0);
      int int3 = tSynchronizedByteList0.size();
      assertFalse(int3 == int1);
  }
}
