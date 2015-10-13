/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.unmodifiable;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.impl.unmodifiable.TUnmodifiableDoubleByteMap;
import gnu.trove.map.TDoubleByteMap;
import gnu.trove.map.hash.TDoubleByteHashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TUnmodifiableDoubleByteMapEvoSuite_Random {

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
      TDoubleByteHashMap tDoubleByteHashMap0 = new TDoubleByteHashMap();
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap0);
      double double0 = (-1966.838666453728);
      tUnmodifiableDoubleByteMap0.get(double0);
      TDoubleByteMap tDoubleByteMap0 = null;
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap1 = null;
      try {
        tUnmodifiableDoubleByteMap1 = new TUnmodifiableDoubleByteMap(tDoubleByteMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test1()  throws Throwable  {
      int int0 = 0;
      double double0 = 0.0;
      byte byte0 = (byte)0;
      TDoubleByteHashMap tDoubleByteHashMap0 = new TDoubleByteHashMap(int0, (float) int0, double0, byte0);
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap0);
      byte byte1 = (byte) (-53);
      // Undeclared exception!
      try {
        tUnmodifiableDoubleByteMap0.putIfAbsent((double) byte0, byte1);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test2()  throws Throwable  {
      int int0 = 868;
      TDoubleByteHashMap tDoubleByteHashMap0 = new TDoubleByteHashMap(int0);
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap0);
      // Undeclared exception!
      try {
        tUnmodifiableDoubleByteMap0.increment((double) int0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test3()  throws Throwable  {
      TDoubleByteMap tDoubleByteMap0 = null;
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = null;
      try {
        tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap(tDoubleByteMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test4()  throws Throwable  {
      int int0 = (-1443);
      float float0 = (-1946.4072F);
      TDoubleByteHashMap tDoubleByteHashMap0 = new TDoubleByteHashMap(int0, float0);
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap0);
      tUnmodifiableDoubleByteMap0.isEmpty();
      TDoubleByteHashMap tDoubleByteHashMap1 = new TDoubleByteHashMap();
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap1 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap1);
      byte[] byteArray0 = tUnmodifiableDoubleByteMap1.values();
      assertNotNull(byteArray0);
      
      TDoubleByteHashMap tDoubleByteHashMap2 = new TDoubleByteHashMap();
      tDoubleByteHashMap2.valueCollection();
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap2 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap2);
      double double0 = 979.5884274121381;
      byte byte0 = (byte)0;
      boolean boolean0 = tUnmodifiableDoubleByteMap2.isEmpty();
      assertEquals(true, boolean0);
      
      // Undeclared exception!
      try {
        tUnmodifiableDoubleByteMap2.adjustValue(double0, byte0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test5()  throws Throwable  {
      TDoubleByteHashMap tDoubleByteHashMap0 = new TDoubleByteHashMap();
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap0);
      tUnmodifiableDoubleByteMap0.hashCode();
      byte[] byteArray0 = tUnmodifiableDoubleByteMap0.values();
      assertNotNull(byteArray0);
  }

  @Test
  public void test6()  throws Throwable  {
      int int0 = 1;
      byte byte0 = (byte)0;
      TDoubleByteHashMap tDoubleByteHashMap0 = new TDoubleByteHashMap(int0, (float) int0, (double) int0, byte0);
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap0);
      tUnmodifiableDoubleByteMap0.getNoEntryValue();
      double[] doubleArray0 = new double[8];
      doubleArray0[0] = (double) byte0;
      double double0 = 122.72813816922498;
      doubleArray0[0] = double0;
      double double1 = 241.58044744776396;
      doubleArray0[1] = double1;
      doubleArray0[2] = double1;
      doubleArray0[3] = (double) byte0;
      doubleArray0[4] = (double) byte0;
      doubleArray0[5] = double0;
      doubleArray0[6] = double1;
      doubleArray0[7] = (double) int0;
      doubleArray0[1] = (double) int0;
      doubleArray0[2] = (double) byte0;
      doubleArray0[3] = (double) byte0;
      doubleArray0[4] = (double) byte0;
      // Undeclared exception!
      try {
        tUnmodifiableDoubleByteMap0.putAll((TDoubleByteMap) tDoubleByteHashMap0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test7()  throws Throwable  {
      int int0 = 0;
      TDoubleByteHashMap tDoubleByteHashMap0 = new TDoubleByteHashMap(int0, int0);
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap0);
      tUnmodifiableDoubleByteMap0.toString();
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap1 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap0);
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap2 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tUnmodifiableDoubleByteMap1);
      double double0 = 0.0;
      // Undeclared exception!
      try {
        tUnmodifiableDoubleByteMap2.remove(double0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test8()  throws Throwable  {
      int int0 = 745;
      TDoubleByteHashMap tDoubleByteHashMap0 = new TDoubleByteHashMap(int0);
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap0);
      tUnmodifiableDoubleByteMap0.values();
      TDoubleByteMap tDoubleByteMap0 = null;
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap1 = null;
      try {
        tUnmodifiableDoubleByteMap1 = new TUnmodifiableDoubleByteMap(tDoubleByteMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test9()  throws Throwable  {
      TDoubleByteMap tDoubleByteMap0 = null;
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = null;
      try {
        tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap(tDoubleByteMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test10()  throws Throwable  {
      int int0 = 1009;
      TDoubleByteHashMap tDoubleByteHashMap0 = new TDoubleByteHashMap(int0);
      TDoubleByteHashMap tDoubleByteHashMap1 = new TDoubleByteHashMap((TDoubleByteMap) tDoubleByteHashMap0);
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap1);
      // Undeclared exception!
      try {
        tUnmodifiableDoubleByteMap0.clear();
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test11()  throws Throwable  {
      TDoubleByteMap tDoubleByteMap0 = null;
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = null;
      try {
        tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap(tDoubleByteMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test12()  throws Throwable  {
      double[] doubleArray0 = new double[6];
      double double0 = 0.0;
      doubleArray0[0] = double0;
      double double1 = 1895.912892726763;
      doubleArray0[1] = double1;
      double double2 = Double.NEGATIVE_INFINITY;
      doubleArray0[2] = double2;
      double double3 = 1576.4621154969213;
      doubleArray0[3] = double3;
      double double4 = 1.0;
      doubleArray0[4] = double4;
      double double5 = 1.0;
      doubleArray0[5] = double5;
      byte[] byteArray0 = new byte[6];
      byte byte0 = (byte) (-12);
      byteArray0[0] = byte0;
      byte byte1 = (byte) (-1);
      byteArray0[1] = byte1;
      byte byte2 = (byte)97;
      byteArray0[2] = byte2;
      byte byte3 = (byte) (-44);
      byteArray0[3] = byte3;
      byte byte4 = (byte) (-1);
      byteArray0[4] = byte4;
      byte byte5 = (byte)3;
      byteArray0[5] = byte5;
      TDoubleByteHashMap tDoubleByteHashMap0 = new TDoubleByteHashMap(doubleArray0, byteArray0);
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap0);
      byte byte6 = (byte)19;
      tUnmodifiableDoubleByteMap0.containsValue(byte6);
      TDoubleByteHashMap tDoubleByteHashMap1 = new TDoubleByteHashMap();
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap1 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap1);
      // Undeclared exception!
      try {
        tUnmodifiableDoubleByteMap1.clear();
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test13()  throws Throwable  {
      TDoubleByteHashMap tDoubleByteHashMap0 = new TDoubleByteHashMap();
      TUnmodifiableDoubleByteMap tUnmodifiableDoubleByteMap0 = new TUnmodifiableDoubleByteMap((TDoubleByteMap) tDoubleByteHashMap0);
      double double0 = (-876.8140622135718);
      byte byte0 = (byte)0;
      // Undeclared exception!
      try {
        tUnmodifiableDoubleByteMap0.adjustOrPutValue(double0, byte0, byte0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }
}
