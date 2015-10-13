/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.list.array;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.TByteCollection;
import gnu.trove.impl.sync.TSynchronizedByteCollection;
import gnu.trove.impl.unmodifiable.TUnmodifiableByteSet;
import gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessByteList;
import gnu.trove.list.TByteList;
import gnu.trove.list.array.TByteArrayList;
import gnu.trove.set.TByteSet;
import gnu.trove.set.hash.TByteHashSet;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URI;
import java.util.Collection;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TByteArrayListEvoSuite_Random {

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
      byte[] byteArray0 = new byte[9];
      byte byte0 = (byte)0;
      byteArray0[0] = byte0;
      byte byte1 = (byte) (-49);
      byteArray0[1] = byte1;
      byte byte2 = (byte) (-69);
      byteArray0[2] = byte2;
      byte byte3 = (byte)0;
      byteArray0[3] = byte3;
      byte byte4 = (byte)2;
      byteArray0[4] = byte4;
      byte byte5 = (byte)116;
      byteArray0[5] = byte5;
      byte byte6 = (byte)65;
      byteArray0[6] = byte6;
      byte byte7 = (byte)127;
      byteArray0[7] = byte7;
      byte byte8 = (byte) (-1);
      byteArray0[8] = byte8;
      TByteArrayList tByteArrayList0 = TByteArrayList.wrap(byteArray0);
      assertNotNull(tByteArrayList0);
      
      String string0 = "YJzum";
      File file0 = new File(string0);
      URI uRI0 = file0.toURI();
      String string1 = uRI0.getUserInfo();
      TSynchronizedByteCollection tSynchronizedByteCollection0 = new TSynchronizedByteCollection((TByteCollection) tByteArrayList0, (Object) string1);
      TByteArrayList tByteArrayList1 = null;
      try {
        tByteArrayList1 = new TByteArrayList((TByteCollection) tSynchronizedByteCollection0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

//   @Test
//   public void test1()  throws Throwable  {
//       int int0 = 873;
//       byte[] byteArray0 = new byte[8];
//       byte byte0 = (byte)0;
//       byteArray0[0] = byte0;
//       byte byte1 = (byte)89;
//       byteArray0[1] = byte1;
//       byte byte2 = (byte)113;
//       byteArray0[2] = byte2;
//       byte byte3 = (byte) (-110);
//       byteArray0[3] = byte3;
//       byte byte4 = (byte)10;
//       byteArray0[4] = byte4;
//       byte byte5 = (byte)0;
//       byteArray0[5] = byte5;
//       byte byte6 = (byte) (-45);
//       byteArray0[6] = byte6;
//       byte byte7 = (byte) (-88);
//       byteArray0[7] = byte7;
//       TByteArrayList tByteArrayList0 = TByteArrayList.wrap(byteArray0, byteArray0[2]);
//       assertEquals(113, tByteArrayList0.getNoEntryValue());
//       assertNotNull(tByteArrayList0);
//       
//       byte byte8 = (byte)13;
//       TByteArrayList tByteArrayList1 = new TByteArrayList(int0, byte8);
//       byte byte9 = (byte) (-93);
//       tByteArrayList1.fill(byte9);
//       // Undeclared exception!
//       try {
//         tByteArrayList1.replace(int0, byte9);
//         fail("Expecting exception: ArrayIndexOutOfBoundsException");
//       
//       } catch(ArrayIndexOutOfBoundsException e) {
//          //
//          // Array index out of range: 873
//          //
//       }
//   }

  @Test
  public void test2()  throws Throwable  {
      int int0 = (-584);
      byte byte0 = (byte) (-1);
      TByteArrayList tByteArrayList0 = null;
      try {
        tByteArrayList0 = new TByteArrayList(int0, byte0);
        fail("Expecting exception: NegativeArraySizeException");
      
      } catch(NegativeArraySizeException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test3()  throws Throwable  {
      TByteArrayList tByteArrayList0 = new TByteArrayList();
      byte byte0 = (byte)0;
      tByteArrayList0.trimToSize();
      tByteArrayList0.fill(byte0);
      assertEquals(0, tByteArrayList0.size());
      
      TByteArrayList tByteArrayList1 = new TByteArrayList();
      int int0 = (-1627);
      int int1 = 0;
      // Undeclared exception!
      try {
        tByteArrayList1.sort(int0, int1);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // Array index out of range: -1627
         //
      }
  }

  @Test
  public void test4()  throws Throwable  {
      TByteArrayList tByteArrayList0 = new TByteArrayList();
      int int0 = 10;
      TByteCollection tByteCollection0 = null;
      boolean boolean0 = tByteArrayList0.retainAll(tByteCollection0);
      assertEquals(false, boolean0);
      
      // Undeclared exception!
      try {
        tByteArrayList0.toArray(int0, int0);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // Array index out of range: 10
         //
      }
  }

  @Test
  public void test5()  throws Throwable  {
      byte[] byteArray0 = new byte[6];
      byte byte0 = (byte)99;
      byteArray0[0] = byte0;
      byte byte1 = (byte)127;
      byteArray0[1] = byte1;
      byte byte2 = (byte)0;
      byteArray0[2] = byte2;
      byte byte3 = (byte)7;
      byteArray0[3] = byte3;
      byte byte4 = (byte) (-76);
      byteArray0[4] = byte4;
      byte byte5 = (byte) (-1);
      byteArray0[5] = byte5;
      TByteArrayList tByteArrayList0 = TByteArrayList.wrap(byteArray0, byte1);
      assertNotNull(tByteArrayList0);
      
      byte[] byteArray1 = new byte[6];
      byteArray1[0] = byte4;
      byteArray1[1] = byte4;
      byteArray1[2] = byte5;
      byteArray1[3] = byte4;
      byteArray1[4] = byte0;
      byteArray1[5] = byte1;
      int int0 = 127;
      // Undeclared exception!
      try {
        tByteArrayList0.insert((int) byte2, byteArray1, int0, (int) byteArray1[3]);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test6()  throws Throwable  {
      byte[] byteArray0 = new byte[10];
      byte byte0 = (byte)0;
      byteArray0[0] = byte0;
      byte byte1 = (byte) (-1);
      byteArray0[1] = byte1;
      byte byte2 = (byte)76;
      byteArray0[2] = byte2;
      byte byte3 = (byte)115;
      byteArray0[3] = byte3;
      byte byte4 = (byte)111;
      byteArray0[4] = byte4;
      byte byte5 = (byte)86;
      byteArray0[5] = byte5;
      byte byte6 = (byte)115;
      byteArray0[6] = byte6;
      byte byte7 = (byte)127;
      byteArray0[7] = byte7;
      byte byte8 = (byte)10;
      byteArray0[8] = byte8;
      byte byte9 = (byte)1;
      byteArray0[9] = byte9;
      TByteArrayList tByteArrayList0 = new TByteArrayList(byteArray0);
      tByteArrayList0.add(byteArray0);
      int int0 = (-669);
      byte byte10 = (byte)127;
      TByteArrayList tByteArrayList1 = null;
      try {
        tByteArrayList1 = new TByteArrayList(int0, byte10);
        fail("Expecting exception: NegativeArraySizeException");
      
      } catch(NegativeArraySizeException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

//   @Test
//   public void test7()  throws Throwable  {
//       byte[] byteArray0 = new byte[7];
//       byte byte0 = (byte) (-128);
//       byteArray0[0] = byte0;
//       byte byte1 = (byte)0;
//       byteArray0[1] = byte1;
//       byte byte2 = (byte)92;
//       byteArray0[2] = byte2;
//       byte byte3 = (byte)0;
//       byteArray0[3] = byte3;
//       byte byte4 = (byte)0;
//       byteArray0[4] = byte4;
//       byte byte5 = (byte) (-75);
//       byteArray0[5] = byte5;
//       byte byte6 = (byte)72;
//       byteArray0[6] = byte6;
//       TByteArrayList tByteArrayList0 = TByteArrayList.wrap(byteArray0);
//       assertNotNull(tByteArrayList0);
//       
//       byte byte7 = tByteArrayList0.max();
//       assertEquals((byte)92, byte7);
//       assertEquals(0, tByteArrayList0.getNoEntryValue());
//       assertEquals(7, tByteArrayList0.size());
//   }

  @Test
  public void test8()  throws Throwable  {
      int int0 = 0;
      byte byte0 = (byte)0;
      TByteArrayList tByteArrayList0 = new TByteArrayList(int0, byte0);
      byte[] byteArray0 = new byte[4];
      byte byte1 = (byte) (-12);
      tByteArrayList0.insert((int) byte0, byte1);
      byte byte2 = (byte)1;
      tByteArrayList0.insert(int0, byteArray0);
      byteArray0[0] = byte2;
      byte byte3 = (byte)0;
      byteArray0[1] = byte3;
      byte byte4 = (byte)0;
      tByteArrayList0.add(byte2);
      byte byte5 = (byte) (-108);
      tByteArrayList0.add(byte5);
      Collection<TByteArrayList> collection0 = null;
      // Undeclared exception!
      try {
        tByteArrayList0.containsAll(collection0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test9()  throws Throwable  {
      TByteArrayList tByteArrayList0 = new TByteArrayList();
      InputStream inputStream0 = null;
      int int0 = 2036;
      BufferedInputStream bufferedInputStream0 = new BufferedInputStream(inputStream0, int0);
      ObjectInputStream objectInputStream0 = null;
      try {
        objectInputStream0 = new ObjectInputStream((InputStream) bufferedInputStream0);
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Stream closed
         //
      }
  }

  @Test
  public void test10()  throws Throwable  {
      TByteArrayList tByteArrayList0 = new TByteArrayList();
      tByteArrayList0.hashCode();
      int int0 = (-87);
      TByteArrayList tByteArrayList1 = null;
      try {
        tByteArrayList1 = new TByteArrayList(int0);
        fail("Expecting exception: NegativeArraySizeException");
      
      } catch(NegativeArraySizeException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test11()  throws Throwable  {
      byte[] byteArray0 = new byte[10];
      byte byte0 = (byte) (-95);
      byteArray0[0] = byte0;
      TByteArrayList tByteArrayList0 = null;
      try {
        tByteArrayList0 = new TByteArrayList((int) byte0);
        fail("Expecting exception: NegativeArraySizeException");
      
      } catch(NegativeArraySizeException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

//   @Test
//   public void test12()  throws Throwable  {
//       byte[] byteArray0 = new byte[9];
//       byte byte0 = (byte)43;
//       byteArray0[0] = byte0;
//       byte byte1 = (byte) (-75);
//       byteArray0[1] = byte1;
//       byte byte2 = (byte)1;
//       byteArray0[2] = byte2;
//       byte byte3 = (byte) (-107);
//       byteArray0[3] = byte3;
//       byte byte4 = (byte) (-100);
//       byteArray0[4] = byte4;
//       byte byte5 = (byte) (-52);
//       byteArray0[5] = byte5;
//       byte byte6 = (byte) (-128);
//       byteArray0[6] = byte6;
//       byte byte7 = (byte) (-1);
//       byteArray0[7] = byte7;
//       byte byte8 = (byte) (-11);
//       byteArray0[8] = byte8;
//       TByteArrayList tByteArrayList0 = TByteArrayList.wrap(byteArray0);
//       assertNotNull(tByteArrayList0);
//       
//       byte[] byteArray1 = new byte[3];
//       byte byte9 = (byte) (-115);
//       byteArray1[0] = byte9;
//       byteArray1[1] = byte8;
//       byteArray1[2] = byte2;
//       byte[] byteArray2 = tByteArrayList0.toArray(byteArray1);
//       assertEquals(0, tByteArrayList0.getNoEntryValue());
//       assertNotNull(byteArray2);
//   }

//   @Test
//   public void test13()  throws Throwable  {
//       int int0 = 1522;
//       TByteArrayList tByteArrayList0 = new TByteArrayList(int0);
//       byte[] byteArray0 = new byte[10];
//       byte byte0 = (byte)111;
//       byteArray0[0] = byte0;
//       byte byte1 = (byte)107;
//       byteArray0[1] = byte1;
//       byte byte2 = (byte) (-53);
//       byteArray0[2] = byte2;
//       byte byte3 = (byte) (-78);
//       byteArray0[3] = byte3;
//       byte byte4 = (byte) (-128);
//       byteArray0[4] = byte4;
//       byte byte5 = (byte)0;
//       byteArray0[5] = byte5;
//       byte byte6 = (byte)0;
//       byteArray0[6] = byte6;
//       byte byte7 = (byte) (-16);
//       byteArray0[7] = byte7;
//       byte byte8 = (byte)0;
//       byteArray0[8] = byte8;
//       byte byte9 = (byte)1;
//       byteArray0[9] = byte9;
//       boolean boolean0 = tByteArrayList0.retainAll(byteArray0);
//       byte byte10 = (byte) (-122);
//       TByteArrayList tByteArrayList1 = new TByteArrayList(int0, byte10);
//       boolean boolean1 = tByteArrayList1.remove(byte10);
//       assertEquals(-122, tByteArrayList1.getNoEntryValue());
//       assertEquals("{}", tByteArrayList1.toString());
//       assertTrue(boolean1 == boolean0);
//   }

  @Test
  public void test14()  throws Throwable  {
      byte[] byteArray0 = new byte[3];
      byte byte0 = (byte)10;
      byteArray0[0] = byte0;
      byteArray0[1] = byte0;
      byteArray0[2] = byte0;
      byte byte1 = (byte)30;
      float float0 = (-1341.9785F);
      int int0 = 985;
      TByteArrayList tByteArrayList0 = new TByteArrayList(int0);
      byte byte2 = (byte)38;
      TByteHashSet tByteHashSet0 = new TByteHashSet((int) byteArray0[1], float0, byte2);
      TUnmodifiableByteSet tUnmodifiableByteSet0 = new TUnmodifiableByteSet((TByteSet) tByteHashSet0);
      int int1 = (-2009);
      int int2 = 1017;
      // Undeclared exception!
      try {
        tByteArrayList0.set(int1, tByteHashSet0._states, (int) byte2, int2);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // Array index out of range: -2009
         //
      }
  }

  @Test
  public void test15()  throws Throwable  {
      byte[] byteArray0 = new byte[9];
      byte byte0 = (byte)0;
      byteArray0[0] = byte0;
      byte byte1 = (byte) (-7);
      byteArray0[1] = byte1;
      byte byte2 = (byte)22;
      byteArray0[2] = byte2;
      byte byte3 = (byte) (-68);
      byteArray0[3] = byte3;
      byte byte4 = (byte)127;
      int int0 = 0;
      TByteArrayList tByteArrayList0 = new TByteArrayList(int0);
      TUnmodifiableRandomAccessByteList tUnmodifiableRandomAccessByteList0 = new TUnmodifiableRandomAccessByteList((TByteList) tByteArrayList0);
      int int1 = 310081;
      // Undeclared exception!
      try {
        tUnmodifiableRandomAccessByteList0.subList((int) byte3, int1);
        fail("Expecting exception: IndexOutOfBoundsException");
      
      } catch(IndexOutOfBoundsException e) {
         //
         // begin index can not be < 0
         //
      }
  }

//   @Test
//   public void test16()  throws Throwable  {
//       byte[] byteArray0 = new byte[4];
//       byte byte0 = (byte) (-106);
//       byteArray0[0] = byte0;
//       byte byte1 = (byte) (-27);
//       byteArray0[1] = byte1;
//       byte byte2 = (byte)76;
//       byteArray0[2] = byte2;
//       byte byte3 = (byte)122;
//       byteArray0[3] = byte3;
//       TByteArrayList tByteArrayList0 = TByteArrayList.wrap(byteArray0, byteArray0[0]);
//       assertNotNull(tByteArrayList0);
//       
//       long long0 = 0L;
//       Random random0 = new Random(long0);
//       tByteArrayList0.shuffle(random0);
//       assertEquals("{-27, 76, 122, -106}", tByteArrayList0.toString());
//       
//       TByteArrayList tByteArrayList1 = new TByteArrayList();
//       tByteArrayList1.toArray();
//       assertEquals(0, tByteArrayList1.getNoEntryValue());
//   }

  @Test
  public void test17()  throws Throwable  {
      byte[] byteArray0 = new byte[1];
      byte byte0 = (byte)0;
      byteArray0[0] = byte0;
      byteArray0[0] = byte0;
      byte byte1 = (byte)0;
      int int0 = 127;
      byte byte2 = (byte)116;
      TByteArrayList tByteArrayList0 = new TByteArrayList(int0, byte2);
      int int1 = 127;
      // Undeclared exception!
      try {
        tByteArrayList0.replace(int1, byteArray0[0]);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // Array index out of range: 127
         //
      }
  }

  @Test
  public void test18()  throws Throwable  {
      byte[] byteArray0 = new byte[5];
      int int0 = 1620;
      TByteArrayList tByteArrayList0 = new TByteArrayList(int0);
      byte[] byteArray1 = new byte[3];
      byte byte0 = (byte) (-63);
      byteArray1[0] = byte0;
      byte byte1 = (byte)107;
      byteArray1[1] = byte1;
      byte byte2 = (byte) (-71);
      byteArray1[2] = byte2;
      // Undeclared exception!
      try {
        tByteArrayList0.retainAll(byteArray1);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // Array index out of range: 1619
         //
      }
  }
}
