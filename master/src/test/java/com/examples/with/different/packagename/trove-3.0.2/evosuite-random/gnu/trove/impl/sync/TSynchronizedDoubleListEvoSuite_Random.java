/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.sync;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.TDoubleCollection;
import gnu.trove.impl.sync.TSynchronizedDoubleList;
import gnu.trove.impl.sync.TSynchronizedRandomAccessDoubleList;
import gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection;
import gnu.trove.impl.unmodifiable.TUnmodifiableDoubleList;
import gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessDoubleList;
import gnu.trove.list.TDoubleList;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.linked.TDoubleLinkedList;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.set.hash.TDoubleHashSet;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TSynchronizedDoubleListEvoSuite_Random {

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
      TDoubleList tDoubleList0 = null;
      TSynchronizedDoubleList tSynchronizedDoubleList0 = null;
      try {
        tSynchronizedDoubleList0 = new TSynchronizedDoubleList(tDoubleList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test1()  throws Throwable  {
      TDoubleLinkedList tDoubleLinkedList0 = new TDoubleLinkedList();
      TUnmodifiableRandomAccessDoubleList tUnmodifiableRandomAccessDoubleList0 = new TUnmodifiableRandomAccessDoubleList((TDoubleList) tDoubleLinkedList0);
      double double0 = 1503.4595417383189;
      // Undeclared exception!
      try {
        tUnmodifiableRandomAccessDoubleList0.indexOf(double0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test2()  throws Throwable  {
      double[] doubleArray0 = new double[2];
      double double0 = Double.POSITIVE_INFINITY;
      doubleArray0[0] = double0;
      double double1 = 0.0;
      doubleArray0[1] = double1;
      TDoubleArrayList.wrap(doubleArray0);
      byte[] byteArray0 = new byte[10];
      byte byte0 = (byte)1;
      byteArray0[0] = byte0;
      byte byte1 = (byte) (-41);
      byteArray0[1] = byte1;
      byte byte2 = (byte) (-102);
      byteArray0[2] = byte2;
      byte byte3 = (byte) (-122);
      byteArray0[3] = byte3;
      byte byte4 = (byte)2;
      byteArray0[4] = byte4;
      byte byte5 = (byte) (-34);
      byteArray0[5] = byte5;
      byte byte6 = (byte) (-1);
      byteArray0[6] = byte6;
      byte byte7 = (byte)0;
      byteArray0[7] = byte7;
      byte byte8 = (byte) (-52);
      byteArray0[8] = byte8;
      byte byte9 = (byte) (-26);
      byteArray0[9] = byte9;
      ByteArrayInputStream byteArrayInputStream0 = new ByteArrayInputStream(byteArray0);
      DataInputStream dataInputStream0 = new DataInputStream((InputStream) byteArrayInputStream0);
      try {
        dataInputStream0.readUTF();
        fail("Expecting exception: EOFException");
      
      } catch(EOFException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test3()  throws Throwable  {
      int int0 = (-559);
      TDoubleArrayList tDoubleArrayList0 = null;
      try {
        tDoubleArrayList0 = new TDoubleArrayList(int0);
        fail("Expecting exception: NegativeArraySizeException");
      
      } catch(NegativeArraySizeException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test4()  throws Throwable  {
      double[] doubleArray0 = new double[4];
      double double0 = 0.0;
      doubleArray0[0] = double0;
      double double1 = 1.0;
      doubleArray0[1] = double1;
      double double2 = 0.0;
      doubleArray0[2] = double2;
      double double3 = Double.POSITIVE_INFINITY;
      doubleArray0[3] = double3;
      TDoubleArrayList tDoubleArrayList0 = TDoubleArrayList.wrap(doubleArray0);
      TSynchronizedDoubleList tSynchronizedDoubleList0 = new TSynchronizedDoubleList((TDoubleList) tDoubleArrayList0);
      PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
      boolean boolean0 = true;
      PrintStream printStream0 = new PrintStream((OutputStream) pipedOutputStream0, boolean0);
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      String string0 = tDoubleHashSet0.toString();
      PrintStream printStream1 = printStream0.append((CharSequence) string0);
      ObjectOutputStream objectOutputStream0 = new ObjectOutputStream((OutputStream) printStream1);
      tDoubleArrayList0.writeExternal((ObjectOutput) objectOutputStream0);
      int int0 = tSynchronizedDoubleList0.hashCode();
      assertEquals((-1075838976), int0);
  }

  @Test
  public void test5()  throws Throwable  {
      double[] doubleArray0 = new double[6];
      double double0 = 0.0;
      doubleArray0[0] = double0;
      double double1 = 258.93149276137876;
      doubleArray0[1] = double1;
      double double2 = 1.0;
      doubleArray0[2] = double2;
      double double3 = 4.9E-324;
      doubleArray0[3] = double3;
      double double4 = 0.0;
      doubleArray0[4] = double4;
      double double5 = (-1185.2105066633924);
      doubleArray0[5] = double5;
      TDoubleArrayList tDoubleArrayList0 = new TDoubleArrayList(doubleArray0);
      int int0 = 1691;
      // Undeclared exception!
      try {
        tDoubleArrayList0.subList(int0, int0);
        fail("Expecting exception: IndexOutOfBoundsException");
      
      } catch(IndexOutOfBoundsException e) {
         //
         // end index < 6
         //
      }
  }

  @Test
  public void test6()  throws Throwable  {
      double[] doubleArray0 = new double[4];
      double double0 = (-1523.741268719634);
      doubleArray0[0] = double0;
      double double1 = 1105.5167019339085;
      TDoubleArrayList tDoubleArrayList0 = TDoubleArrayList.wrap(doubleArray0);
      TSynchronizedRandomAccessDoubleList tSynchronizedRandomAccessDoubleList0 = new TSynchronizedRandomAccessDoubleList((TDoubleList) tDoubleArrayList0);
      int int0 = 0;
      tSynchronizedRandomAccessDoubleList0.removeAt(int0);
      doubleArray0[1] = double1;
      double double2 = 4.9E-324;
      doubleArray0[2] = double2;
      double double3 = 0.0;
      doubleArray0[3] = double3;
      double double4 = Double.NEGATIVE_INFINITY;
      TDoubleArrayList.wrap(doubleArray0, double4);
      String string0 = "xT6UDCZ0,Zrij-zKa";
      String string1 = "X@(&R6<";
      URI uRI0 = null;
      try {
        uRI0 = new URI(string0, string0, string1);
        fail("Expecting exception: URISyntaxException");
      
      } catch(URISyntaxException e) {
         //
         // Illegal character in scheme name at index 8: xT6UDCZ0,Zrij-zKa:xT6UDCZ0,Zrij-zKa#X@(&R6%3C
         //
      }
  }

  @Test
  public void test7()  throws Throwable  {
      TDoubleList tDoubleList0 = null;
      TDoubleLinkedList tDoubleLinkedList0 = null;
      try {
        tDoubleLinkedList0 = new TDoubleLinkedList(tDoubleList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test8()  throws Throwable  {
      double[] doubleArray0 = new double[8];
      double double0 = (-1015.3394483627795);
      doubleArray0[0] = double0;
      double double1 = 1.0;
      doubleArray0[1] = double1;
      double double2 = (-1661.8008274095296);
      doubleArray0[2] = double2;
      double double3 = 656.464109994952;
      doubleArray0[3] = double3;
      double double4 = 1.0;
      doubleArray0[4] = double4;
      double double5 = (-1512.5046497329195);
      doubleArray0[5] = double5;
      double double6 = 0.0;
      doubleArray0[6] = double6;
      double double7 = (-1.0);
      doubleArray0[7] = double7;
      TDoubleArrayList tDoubleArrayList0 = new TDoubleArrayList(doubleArray0);
      TSynchronizedDoubleList tSynchronizedDoubleList0 = new TSynchronizedDoubleList((TDoubleList) tDoubleArrayList0);
      int int0 = 0;
      int int1 = 83;
      tSynchronizedDoubleList0.fill(int0, int1, int0);
      assertEquals(83, tSynchronizedDoubleList0.size());
      assertEquals(0.0, tDoubleArrayList0.min(), 0.01D);
  }

  @Test
  public void test9()  throws Throwable  {
      double[] doubleArray0 = new double[6];
      double double0 = 1386.9030812394276;
      doubleArray0[0] = double0;
      double double1 = (-1800.4337636980533);
      doubleArray0[1] = double1;
      double double2 = (-1501.057362840807);
      doubleArray0[2] = double2;
      double double3 = 0.0;
      doubleArray0[3] = double3;
      double double4 = 0.0;
      doubleArray0[4] = double4;
      double double5 = (-1.0);
      doubleArray0[5] = double5;
      double double6 = 1.0;
      TDoubleArrayList tDoubleArrayList0 = TDoubleArrayList.wrap(doubleArray0, double6);
      TSynchronizedRandomAccessDoubleList tSynchronizedRandomAccessDoubleList0 = new TSynchronizedRandomAccessDoubleList((TDoubleList) tDoubleArrayList0);
      int int0 = 0;
      TSynchronizedRandomAccessDoubleList tSynchronizedRandomAccessDoubleList1 = (TSynchronizedRandomAccessDoubleList)tSynchronizedRandomAccessDoubleList0.subList(int0, int0);
      TSynchronizedDoubleList tSynchronizedDoubleList0 = new TSynchronizedDoubleList((TDoubleList) tSynchronizedRandomAccessDoubleList1);
      int int1 = 1;
      // Undeclared exception!
      try {
        tSynchronizedDoubleList0.set(int0, doubleArray0, int0, int1);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // Array index out of range: 0
         //
      }
  }

  @Test
  public void test10()  throws Throwable  {
      TDoubleLinkedList tDoubleLinkedList0 = new TDoubleLinkedList();
      Object object0 = null;
      TSynchronizedRandomAccessDoubleList tSynchronizedRandomAccessDoubleList0 = new TSynchronizedRandomAccessDoubleList((TDoubleList) tDoubleLinkedList0, object0);
      int int0 = 584;
      // Undeclared exception!
      try {
        tSynchronizedRandomAccessDoubleList0.lastIndexOf(int0, int0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test11()  throws Throwable  {
      TDoubleArrayList tDoubleArrayList0 = new TDoubleArrayList();
      String string0 = "";
      File file0 = new File(string0, string0);
      URI uRI0 = file0.toURI();
      uRI0.getPath();
      TUnmodifiableDoubleCollection tUnmodifiableDoubleCollection0 = new TUnmodifiableDoubleCollection((TDoubleCollection) tDoubleArrayList0);
      TDoubleList tDoubleList0 = null;
      TUnmodifiableDoubleList tUnmodifiableDoubleList0 = null;
      try {
        tUnmodifiableDoubleList0 = new TUnmodifiableDoubleList(tDoubleList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test12()  throws Throwable  {
      TDoubleList tDoubleList0 = null;
      String string0 = File.pathSeparator;
      TSynchronizedRandomAccessDoubleList tSynchronizedRandomAccessDoubleList0 = null;
      try {
        tSynchronizedRandomAccessDoubleList0 = new TSynchronizedRandomAccessDoubleList(tDoubleList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test13()  throws Throwable  {
      TDoubleLinkedList tDoubleLinkedList0 = new TDoubleLinkedList();
      double[] doubleArray0 = new double[2];
      double double0 = Double.POSITIVE_INFINITY;
      double double1 = 344.2627795411045;
      tDoubleLinkedList0.add(double1);
      doubleArray0[0] = double0;
      TSynchronizedDoubleList tSynchronizedDoubleList0 = new TSynchronizedDoubleList((TDoubleList) tDoubleLinkedList0);
      tSynchronizedDoubleList0.iterator();
      double double2 = 0.0;
      Locale locale0 = Locale.GERMANY;
      int int0 = (-72);
      int int1 = (-1176);
      // Undeclared exception!
      try {
        tSynchronizedDoubleList0.toArray(int0, int1);
        fail("Expecting exception: NegativeArraySizeException");
      
      } catch(NegativeArraySizeException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test14()  throws Throwable  {
      double[] doubleArray0 = new double[8];
      double double0 = 1554.501469850196;
      doubleArray0[0] = double0;
      double double1 = (-1925.4817063272474);
      doubleArray0[1] = double1;
      double double2 = 1.0;
      doubleArray0[2] = double2;
      double double3 = 1.7976931348623157E308;
      doubleArray0[3] = double3;
      double double4 = (-617.641545166494);
      doubleArray0[4] = double4;
      double double5 = 682.8797528511328;
      doubleArray0[5] = double5;
      double double6 = 65.93141327252594;
      doubleArray0[6] = double6;
      double double7 = 853.8099800049783;
      doubleArray0[7] = double7;
      TDoubleArrayList tDoubleArrayList0 = TDoubleArrayList.wrap(doubleArray0, doubleArray0[0]);
      TSynchronizedDoubleList tSynchronizedDoubleList0 = new TSynchronizedDoubleList((TDoubleList) tDoubleArrayList0);
      int int0 = 224;
      // Undeclared exception!
      try {
        tSynchronizedDoubleList0.sort(int0, int0);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // Array index out of range: 224
         //
      }
  }

  @Test
  public void test15()  throws Throwable  {
      TDoubleList tDoubleList0 = null;
      TDoubleLinkedList tDoubleLinkedList0 = null;
      try {
        tDoubleLinkedList0 = new TDoubleLinkedList(tDoubleList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test16()  throws Throwable  {
      TDoubleArrayList tDoubleArrayList0 = new TDoubleArrayList();
      TSynchronizedDoubleList tSynchronizedDoubleList0 = new TSynchronizedDoubleList((TDoubleList) tDoubleArrayList0);
      tSynchronizedDoubleList0.toArray();
      int int0 = (-622);
      String string0 = "";
      tSynchronizedDoubleList0.hashCode();
      URI uRI0 = null;
      try {
        uRI0 = new URI(string0, string0, string0, string0);
        fail("Expecting exception: URISyntaxException");
      
      } catch(URISyntaxException e) {
         //
         // Expected scheme name at index 0: ://#
         //
      }
  }

  @Test
  public void test17()  throws Throwable  {
      TDoubleArrayList tDoubleArrayList0 = new TDoubleArrayList();
      TDoubleProcedure tDoubleProcedure0 = null;
      TDoubleArrayList tDoubleArrayList1 = (TDoubleArrayList)tDoubleArrayList0.grep(tDoubleProcedure0);
      TDoubleLinkedList tDoubleLinkedList0 = new TDoubleLinkedList((TDoubleList) tDoubleArrayList1);
      String string0 = "";
      File file0 = new File(string0, string0);
      String string1 = file0.getPath();
      TSynchronizedRandomAccessDoubleList tSynchronizedRandomAccessDoubleList0 = new TSynchronizedRandomAccessDoubleList((TDoubleList) tDoubleLinkedList0, (Object) string1);
      TDoubleLinkedList tDoubleLinkedList1 = (TDoubleLinkedList)tSynchronizedRandomAccessDoubleList0.grep(tDoubleProcedure0);
      assertNotNull(tDoubleLinkedList1);
      
      TSynchronizedDoubleList tSynchronizedDoubleList0 = new TSynchronizedDoubleList((TDoubleList) tDoubleLinkedList1);
      double double0 = 0.0;
      int int0 = tSynchronizedDoubleList0.lastIndexOf(double0);
      assertEquals((-1), int0);
  }

  @Test
  public void test18()  throws Throwable  {
      TDoubleLinkedList tDoubleLinkedList0 = new TDoubleLinkedList();
      int int0 = 273;
      // Undeclared exception!
      try {
        tDoubleLinkedList0.subList(int0, int0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // begin index 273 greater than last index 0
         //
      }
  }
}
