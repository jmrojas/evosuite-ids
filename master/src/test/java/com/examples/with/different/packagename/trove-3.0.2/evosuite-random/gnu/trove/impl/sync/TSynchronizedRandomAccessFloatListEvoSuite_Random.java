/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.sync;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.TFloatCollection;
import gnu.trove.impl.sync.TSynchronizedFloatList;
import gnu.trove.impl.sync.TSynchronizedRandomAccessFloatList;
import gnu.trove.impl.unmodifiable.TUnmodifiableFloatList;
import gnu.trove.impl.unmodifiable.TUnmodifiableFloatSet;
import gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessFloatList;
import gnu.trove.list.TFloatList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.linked.TFloatLinkedList;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.set.TFloatSet;
import gnu.trove.set.hash.TFloatHashSet;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TSynchronizedRandomAccessFloatListEvoSuite_Random {

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
      TFloatArrayList tFloatArrayList0 = new TFloatArrayList();
      String string0 = "";
      URI uRI0 = URI.create(string0);
      String string1 = uRI0.getSchemeSpecificPart();
      TSynchronizedFloatList tSynchronizedFloatList0 = new TSynchronizedFloatList((TFloatList) tFloatArrayList0, (Object) string1);
      int int0 = 288;
      // Undeclared exception!
      try {
        tSynchronizedFloatList0.subList(int0, int0);
        fail("Expecting exception: IndexOutOfBoundsException");
      
      } catch(IndexOutOfBoundsException e) {
         //
         // end index < 10
         //
      }
  }

  @Test
  public void test1()  throws Throwable  {
      TFloatList tFloatList0 = null;
      Object object0 = null;
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList0 = new TSynchronizedRandomAccessFloatList(tFloatList0, object0);
      int int0 = 2;
      float float0 = Float.POSITIVE_INFINITY;
      // Undeclared exception!
      try {
        tSynchronizedRandomAccessFloatList0.replace(int0, float0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test2()  throws Throwable  {
      TFloatList tFloatList0 = null;
      Locale locale0 = Locale.ITALY;
      String string0 = locale0.getISO3Country();
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList0 = new TSynchronizedRandomAccessFloatList(tFloatList0, (Object) string0);
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList1 = null;
      try {
        tSynchronizedRandomAccessFloatList1 = new TSynchronizedRandomAccessFloatList(tFloatList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test3()  throws Throwable  {
      TFloatArrayList tFloatArrayList0 = new TFloatArrayList();
      int int0 = 0;
      tFloatArrayList0.subList(int0, int0);
      String string0 = "";
      URI uRI0 = null;
      try {
        uRI0 = new URI(string0, string0, string0);
        fail("Expecting exception: URISyntaxException");
      
      } catch(URISyntaxException e) {
         //
         // Expected scheme name at index 0: :#
         //
      }
  }

  @Test
  public void test4()  throws Throwable  {
      float float0 = 0.0F;
      TFloatLinkedList tFloatLinkedList0 = new TFloatLinkedList(float0);
      int int0 = 1991;
      float float1 = 0.0F;
      // Undeclared exception!
      try {
        tFloatLinkedList0.insert(int0, float1);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test5()  throws Throwable  {
      TFloatLinkedList tFloatLinkedList0 = new TFloatLinkedList();
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList0 = new TSynchronizedRandomAccessFloatList((TFloatList) tFloatLinkedList0);
      float[] floatArray0 = new float[4];
      float float0 = Float.POSITIVE_INFINITY;
      floatArray0[0] = float0;
      float float1 = (-51.38629F);
      LinkedList<Object> linkedList0 = new LinkedList<Object>();
      tSynchronizedRandomAccessFloatList0.containsAll((Collection<?>) linkedList0);
      floatArray0[1] = float1;
      float float2 = Float.NEGATIVE_INFINITY;
      floatArray0[2] = float2;
      linkedList0.toArray();
      // Undeclared exception!
      try {
        tSynchronizedRandomAccessFloatList0.indexOf(floatArray0[0]);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test6()  throws Throwable  {
      int int0 = 0;
      float float0 = 251.43819F;
      TFloatLinkedList tFloatLinkedList0 = new TFloatLinkedList();
      int int1 = (-1703);
      // Undeclared exception!
      try {
        tFloatLinkedList0.subList(int0, int1);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // begin index 0 greater than end index -1703
         //
      }
  }

  @Test
  public void test7()  throws Throwable  {
      float[] floatArray0 = new float[10];
      float float0 = 0.0F;
      floatArray0[0] = float0;
      float float1 = (-700.49994F);
      floatArray0[1] = float1;
      float float2 = 1846.9176F;
      floatArray0[2] = float2;
      float float3 = 0.0F;
      floatArray0[3] = float3;
      float float4 = 10.0F;
      floatArray0[4] = float4;
      float float5 = 0.0F;
      floatArray0[5] = float5;
      float float6 = (-1290.2522F);
      floatArray0[6] = float6;
      float float7 = 0.0F;
      floatArray0[7] = float7;
      float float8 = Float.POSITIVE_INFINITY;
      floatArray0[8] = float8;
      float float9 = (-1.0F);
      floatArray0[9] = float9;
      TFloatArrayList tFloatArrayList0 = new TFloatArrayList(floatArray0);
      TFloatProcedure tFloatProcedure0 = null;
      // Undeclared exception!
      try {
        tFloatArrayList0.inverseGrep(tFloatProcedure0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test8()  throws Throwable  {
      TFloatList tFloatList0 = null;
      TUnmodifiableRandomAccessFloatList tUnmodifiableRandomAccessFloatList0 = null;
      try {
        tUnmodifiableRandomAccessFloatList0 = new TUnmodifiableRandomAccessFloatList(tFloatList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test9()  throws Throwable  {
      int int0 = (-1489);
      float float0 = 429.0954F;
      TFloatArrayList tFloatArrayList0 = null;
      try {
        tFloatArrayList0 = new TFloatArrayList(int0, float0);
        fail("Expecting exception: NegativeArraySizeException");
      
      } catch(NegativeArraySizeException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test10()  throws Throwable  {
      float float0 = 1980.0479F;
      TFloatLinkedList tFloatLinkedList0 = new TFloatLinkedList(float0);
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList0 = new TSynchronizedRandomAccessFloatList((TFloatList) tFloatLinkedList0);
      long long0 = (-913L);
      Random random0 = new Random(long0);
      tSynchronizedRandomAccessFloatList0.shuffle(random0);
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList1 = new TSynchronizedRandomAccessFloatList((TFloatList) tFloatLinkedList0);
      assertEquals(0, tSynchronizedRandomAccessFloatList1.size());
  }

  @Test
  public void test11()  throws Throwable  {
      TFloatList tFloatList0 = null;
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList0 = null;
      try {
        tSynchronizedRandomAccessFloatList0 = new TSynchronizedRandomAccessFloatList(tFloatList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test12()  throws Throwable  {
      TFloatLinkedList tFloatLinkedList0 = new TFloatLinkedList();
      String string0 = "{[*";
      int int0 = 0;
      int int1 = 1;
      // Undeclared exception!
      try {
        URI.create(string0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal character in path at index 0: {[*
         //
      }
  }

  @Test
  public void test13()  throws Throwable  {
      int int0 = 1934;
      float float0 = 466.0975F;
      TFloatArrayList tFloatArrayList0 = new TFloatArrayList(int0, float0);
      String string0 = File.pathSeparator;
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList0 = new TSynchronizedRandomAccessFloatList((TFloatList) tFloatArrayList0, (Object) string0);
      tSynchronizedRandomAccessFloatList0.toArray();
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList1 = new TSynchronizedRandomAccessFloatList((TFloatList) tFloatArrayList0);
      tSynchronizedRandomAccessFloatList1.hashCode();
      int int1 = 545;
      tSynchronizedRandomAccessFloatList1.subList(int1, int1);
      tSynchronizedRandomAccessFloatList1.reverse();
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList2 = new TSynchronizedRandomAccessFloatList((TFloatList) tFloatArrayList0);
      int int2 = (-1);
      float float1 = 1182.0522F;
      TFloatProcedure tFloatProcedure0 = null;
      tSynchronizedRandomAccessFloatList2.forEachDescending(tFloatProcedure0);
      int int3 = tFloatArrayList0.lastIndexOf(int2, float1);
      float[] floatArray0 = new float[8];
      tSynchronizedRandomAccessFloatList1.containsAll((TFloatCollection) tSynchronizedRandomAccessFloatList2);
      int int4 = (-1057);
      // Undeclared exception!
      try {
        tSynchronizedRandomAccessFloatList2.remove(int4, int3);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // Array index out of range: -1057
         //
      }
  }

  @Test
  public void test14()  throws Throwable  {
      TFloatLinkedList tFloatLinkedList0 = new TFloatLinkedList();
      TUnmodifiableFloatList tUnmodifiableFloatList0 = new TUnmodifiableFloatList((TFloatList) tFloatLinkedList0);
      TSynchronizedFloatList tSynchronizedFloatList0 = new TSynchronizedFloatList((TFloatList) tUnmodifiableFloatList0);
      TUnmodifiableFloatList tUnmodifiableFloatList1 = (TUnmodifiableFloatList)tSynchronizedFloatList0.list;
      ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
      String string0 = "oau";
      try {
        byteArrayOutputStream0.toString(string0);
        fail("Expecting exception: UnsupportedEncodingException");
      
      } catch(UnsupportedEncodingException e) {
         //
         // oau
         //
      }
  }

  @Test
  public void test15()  throws Throwable  {
      float[] floatArray0 = new float[10];
      float float0 = (-1451.2152F);
      floatArray0[0] = float0;
      float float1 = 1437.1617F;
      floatArray0[1] = float1;
      float float2 = (-1932.2131F);
      floatArray0[2] = float2;
      float float3 = Float.NEGATIVE_INFINITY;
      floatArray0[3] = float3;
      float float4 = 1.0F;
      floatArray0[4] = float4;
      float float5 = 124.29077F;
      floatArray0[5] = float5;
      float float6 = (-926.3893F);
      floatArray0[6] = float6;
      float float7 = 1457.5948F;
      floatArray0[7] = float7;
      float float8 = (-161.06679F);
      floatArray0[8] = float8;
      float float9 = (-213.08345F);
      floatArray0[9] = float9;
      TFloatArrayList tFloatArrayList0 = new TFloatArrayList(floatArray0);
      TFloatHashSet tFloatHashSet0 = new TFloatHashSet(floatArray0);
      TUnmodifiableFloatSet tUnmodifiableFloatSet0 = new TUnmodifiableFloatSet((TFloatSet) tFloatHashSet0);
      String string0 = tUnmodifiableFloatSet0.toString();
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList0 = new TSynchronizedRandomAccessFloatList((TFloatList) tFloatArrayList0, (Object) string0);
      tSynchronizedRandomAccessFloatList0.isEmpty();
      int int0 = 1273;
      TFloatArrayList tFloatArrayList1 = new TFloatArrayList(int0);
      tFloatArrayList1.subList(int0, int0);
      TFloatList tFloatList0 = null;
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList1 = null;
      try {
        tSynchronizedRandomAccessFloatList1 = new TSynchronizedRandomAccessFloatList(tFloatList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test16()  throws Throwable  {
      TFloatList tFloatList0 = null;
      TUnmodifiableRandomAccessFloatList tUnmodifiableRandomAccessFloatList0 = null;
      try {
        tUnmodifiableRandomAccessFloatList0 = new TUnmodifiableRandomAccessFloatList(tFloatList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test17()  throws Throwable  {
      int int0 = (-252);
      TFloatArrayList tFloatArrayList0 = null;
      try {
        tFloatArrayList0 = new TFloatArrayList(int0);
        fail("Expecting exception: NegativeArraySizeException");
      
      } catch(NegativeArraySizeException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test18()  throws Throwable  {
      TFloatLinkedList tFloatLinkedList0 = new TFloatLinkedList();
      TFloatLinkedList tFloatLinkedList1 = new TFloatLinkedList((TFloatList) tFloatLinkedList0);
      TSynchronizedRandomAccessFloatList tSynchronizedRandomAccessFloatList0 = new TSynchronizedRandomAccessFloatList((TFloatList) tFloatLinkedList1);
      int int0 = (-1040);
      float[] floatArray0 = new float[6];
      floatArray0[0] = (float) int0;
      floatArray0[1] = (float) int0;
      floatArray0[2] = (float) int0;
      floatArray0[3] = (float) int0;
      floatArray0[4] = (float) int0;
      floatArray0[5] = (float) int0;
      // Undeclared exception!
      try {
        tSynchronizedRandomAccessFloatList0.insert(int0, floatArray0, int0, int0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }
}
