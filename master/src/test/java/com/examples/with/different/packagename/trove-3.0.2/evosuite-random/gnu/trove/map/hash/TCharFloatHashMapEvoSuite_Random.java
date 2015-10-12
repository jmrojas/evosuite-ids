/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.map.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.map.TCharFloatMap;
import gnu.trove.map.hash.TCharFloatHashMap;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.evosuite.Properties.SandboxMode;
import org.evosuite.sandbox.Sandbox;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class TCharFloatHashMapEvoSuite_Random {

  private static ExecutorService executor; 

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.utils.LoggingUtils.setLoggingForJUnit(); 
    org.evosuite.Properties.REPLACE_CALLS = true; 
    org.evosuite.agent.InstrumentingAgent.initialize(); 
    org.evosuite.Properties.SANDBOX_MODE = SandboxMode.RECOMMENDED; 
    Sandbox.initializeSecurityManagerForSUT(); 
    executor = Executors.newCachedThreadPool(); 
  } 

  @AfterClass 
  public static void clearEvoSuiteFramework(){ 
    executor.shutdownNow(); 
    Sandbox.resetDefaultSecurityManager(); 
  } 

  @Before 
  public void initTestCase(){ 
    Sandbox.goingToExecuteSUTCode(); 
    org.evosuite.agent.InstrumentingAgent.activate(); 
  } 

  @After 
  public void doneWithTestCase(){ 
    Sandbox.doneWithExecutingSUTCode(); 
    org.evosuite.agent.InstrumentingAgent.deactivate(); 
  } 


  @Test
  public void test0()  throws Throwable  {
      char[] charArray0 = new char[5];
      char char0 = 's';
      charArray0[0] = char0;
      char char1 = '\\';
      charArray0[1] = char1;
      char char2 = '~';
      charArray0[2] = char2;
      char char3 = 'A';
      charArray0[3] = char3;
      char char4 = 'a';
      charArray0[4] = char4;
      float[] floatArray0 = new float[10];
      floatArray0[0] = (float) charArray0[0];
      floatArray0[1] = (float) charArray0[3];
      floatArray0[2] = (float) char0;
      floatArray0[3] = (float) char4;
      floatArray0[4] = (float) charArray0[1];
      floatArray0[5] = (float) char4;
      floatArray0[6] = (float) charArray0[4];
      floatArray0[7] = (float) charArray0[0];
      floatArray0[8] = (float) char1;
      floatArray0[9] = (float) char3;
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(charArray0, floatArray0);
      TCharFloatHashMap tCharFloatHashMap1 = new TCharFloatHashMap((TCharFloatMap) tCharFloatHashMap0);
      TCharFloatHashMap tCharFloatHashMap2 = new TCharFloatHashMap((TCharFloatMap) tCharFloatHashMap1);
      char[] charArray1 = tCharFloatHashMap2.keys();
      assertNotNull(charArray1);
      assertEquals("{A=97.0, \\=65.0, ~=115.0, a=92.0, s=115.0}", tCharFloatHashMap2.toString());
      assertEquals("{A=97.0, s=115.0, ~=115.0, a=92.0, \\=65.0}", tCharFloatHashMap1.toString());
      assertEquals(23, tCharFloatHashMap1.capacity());
  }

  @Test
  public void test1()  throws Throwable  {
      char[] charArray0 = new char[3];
      char char0 = 'o';
      charArray0[0] = char0;
      char char1 = 'L';
      int int0 = 2;
      float float0 = 0.0F;
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(int0, float0);
      TCharFloatHashMap tCharFloatHashMap1 = null;
      try {
        tCharFloatHashMap1 = new TCharFloatHashMap((TCharFloatMap) tCharFloatHashMap0);
        fail("Expecting exception: OutOfMemoryError");
      
      } catch(OutOfMemoryError e) {
         //
         // Java heap space
         //
      }
  }

  @Test
  public void test2()  throws Throwable  {
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap();
      String string0 = "ArlcJI.C\"=aG*e)g7";
      AbstractMap.SimpleEntry<String, TCharFloatHashMap> abstractMap_SimpleEntry0 = new AbstractMap.SimpleEntry<String, TCharFloatHashMap>(string0, tCharFloatHashMap0);
      abstractMap_SimpleEntry0.getValue();
      int int0 = tCharFloatHashMap0.capacity();
      assertEquals(23, int0);
      
      TCharFloatHashMap tCharFloatHashMap1 = new TCharFloatHashMap();
      boolean boolean0 = false;
      tCharFloatHashMap1.reenableAutoCompaction(boolean0);
      assertEquals(23, tCharFloatHashMap1.capacity());
  }

  @Test
  public void test3()  throws Throwable  {
      char[] charArray0 = new char[6];
      char char0 = ']';
      charArray0[0] = char0;
      char char1 = '-';
      charArray0[1] = char1;
      char char2 = 'b';
      charArray0[2] = char2;
      char char3 = 'l';
      charArray0[3] = char3;
      char char4 = '#';
      charArray0[4] = char4;
      char char5 = 'w';
      charArray0[5] = char5;
      float[] floatArray0 = new float[4];
      floatArray0[0] = (float) charArray0[0];
      floatArray0[1] = (float) charArray0[3];
      floatArray0[2] = (float) char5;
      floatArray0[3] = (float) charArray0[0];
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(charArray0, floatArray0);
      int int0 = (-1440);
      tCharFloatHashMap0.ensureCapacity(int0);
      assertEquals(false, tCharFloatHashMap0.isEmpty());
      assertEquals("{b=119.0, -=108.0, ]=93.0, l=93.0}", tCharFloatHashMap0.toString());
      assertEquals(4, tCharFloatHashMap0.size());
  }

  @Test
  public void test4()  throws Throwable  {
      int int0 = (-1);
      int int1 = 79;
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(int1);
      char char0 = '|';
      Float float0 = new Float((double) int1);
      String string0 = "h3V!?_82QcPm3gN97]";
      AbstractMap.SimpleImmutableEntry<Float, String> abstractMap_SimpleImmutableEntry0 = new AbstractMap.SimpleImmutableEntry<Float, String>(float0, string0);
      AbstractMap.SimpleImmutableEntry<AbstractMap.SimpleImmutableEntry<Float, String>, TCharFloatHashMap> abstractMap_SimpleImmutableEntry1 = new AbstractMap.SimpleImmutableEntry<AbstractMap.SimpleImmutableEntry<Float, String>, TCharFloatHashMap>(abstractMap_SimpleImmutableEntry0, tCharFloatHashMap0);
      AbstractMap.SimpleImmutableEntry<Object, Object> abstractMap_SimpleImmutableEntry2 = new AbstractMap.SimpleImmutableEntry<Object, Object>((Map.Entry<?, ?>) abstractMap_SimpleImmutableEntry1);
      abstractMap_SimpleImmutableEntry2.hashCode();
      tCharFloatHashMap0.remove(char0);
      char[] charArray0 = tCharFloatHashMap0.keys();
      assertNotNull(charArray0);
      
      TCharFloatHashMap tCharFloatHashMap1 = new TCharFloatHashMap(int0, int0);
      tCharFloatHashMap1.putIfAbsent(char0, (float) int0);
      assertEquals(false, tCharFloatHashMap1.isEmpty());
      
      TCharFloatHashMap tCharFloatHashMap2 = new TCharFloatHashMap();
      assertFalse(tCharFloatHashMap2.equals(tCharFloatHashMap1));
  }

  @Test
  public void test5()  throws Throwable  {
      int int0 = 137;
      char[] charArray0 = new char[9];
      char char0 = 'V';
      charArray0[0] = char0;
      char char1 = 'S';
      charArray0[1] = char1;
      char char2 = ' ';
      charArray0[2] = char2;
      char char3 = '1';
      charArray0[3] = char3;
      char char4 = '\'';
      charArray0[4] = char4;
      char char5 = '0';
      charArray0[5] = char5;
      char char6 = 'y';
      charArray0[6] = char6;
      char char7 = '[';
      charArray0[7] = char7;
      char char8 = '_';
      charArray0[8] = char8;
      float[] floatArray0 = new float[5];
      floatArray0[0] = (float) int0;
      floatArray0[1] = (float) char8;
      floatArray0[2] = (float) charArray0[8];
      floatArray0[3] = (float) char7;
      floatArray0[4] = (float) char5;
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(charArray0, floatArray0);
      char char9 = '&';
      boolean boolean0 = tCharFloatHashMap0.increment(char9);
      assertEquals("{V=137.0, '=48.0, S=95.0,  =95.0, 1=91.0}", tCharFloatHashMap0.toString());
      assertEquals(false, boolean0);
      
      TCharFloatHashMap tCharFloatHashMap1 = new TCharFloatHashMap(int0);
      char[] charArray1 = tCharFloatHashMap1._set;
      TCharFloatHashMap tCharFloatHashMap2 = new TCharFloatHashMap();
      int int1 = tCharFloatHashMap2.capacity();
      assertFalse(tCharFloatHashMap2.equals(tCharFloatHashMap0));
      assertEquals(23, int1);
  }

//   @Test
//   public void test6()  throws Throwable  {
//       int int0 = 350899;
//       float float0 = Float.POSITIVE_INFINITY;
//       TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(int0, float0);
//       TCharFloatHashMap tCharFloatHashMap1 = new TCharFloatHashMap((TCharFloatMap) tCharFloatHashMap0);
//       tCharFloatHashMap1._set = tCharFloatHashMap0._set;
//       assertEquals(0.0F, tCharFloatHashMap1.getNoEntryValue(), 0.01F);
//       assertEquals(3, tCharFloatHashMap1.capacity());
//       
//       int int1 = 1717;
//       int int2 = (-545);
//       float float1 = 0.0F;
//       char char0 = '0';
//       TCharFloatHashMap tCharFloatHashMap2 = new TCharFloatHashMap(int2, float1, char0, (float) int1);
//       tCharFloatHashMap2.valueCollection();
//       TCharFloatHashMap tCharFloatHashMap3 = new TCharFloatHashMap(int1);
//       tCharFloatHashMap3.valueCollection();
//       tCharFloatHashMap3.getAutoCompactionFactor();
//       TCharFloatHashMap tCharFloatHashMap4 = new TCharFloatHashMap();
//       char[] charArray0 = tCharFloatHashMap4.keys();
//       assertNotNull(charArray0);
//       
//       char char1 = '_';
//       float[] floatArray0 = tCharFloatHashMap3.values();
//       assertNotNull(floatArray0);
//       
//       float float2 = tCharFloatHashMap3.remove(char1);
//       assertEquals(0.0F, float2, 0.01F);
//       
//       tCharFloatHashMap4.putAll((TCharFloatMap) tCharFloatHashMap3);
//       tCharFloatHashMap4.iterator();
//       assertEquals(23, tCharFloatHashMap4.capacity());
//       assertTrue(tCharFloatHashMap4.equals(tCharFloatHashMap3));
//   }

  @Test
  public void test7()  throws Throwable  {
      int int0 = (-373);
      char char0 = 'n';
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(int0, (float) int0, char0, (float) char0);
      float[] floatArray0 = new float[4];
      float float0 = 0.0F;
      floatArray0[0] = float0;
      floatArray0[1] = (float) int0;
      floatArray0[2] = (float) int0;
      floatArray0[3] = (float) char0;
      float[] floatArray1 = tCharFloatHashMap0.values(floatArray0);
      assertSame(floatArray0, floatArray1);
      assertNotNull(floatArray1);
  }

  @Test
  public void test8()  throws Throwable  {
      int int0 = 0;
      float float0 = (-2026.7455F);
      int int1 = 34;
      float float1 = 1.0F;
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(int1, float1);
      char char0 = 'O';
      float float2 = 10.0F;
      float float3 = 1.0F;
      float float4 = 1.0F;
      TCharFloatHashMap tCharFloatHashMap1 = new TCharFloatHashMap(int0, float0);
      tCharFloatHashMap1.get(char0);
      TCharFloatHashMap tCharFloatHashMap2 = new TCharFloatHashMap();
      float float5 = 1.0F;
      tCharFloatHashMap2.put(char0, float5);
      TCharFloatHashMap tCharFloatHashMap3 = new TCharFloatHashMap(int0, float3, char0, float4);
      TCharFloatHashMap tCharFloatHashMap4 = new TCharFloatHashMap(int0, float0, char0, float2);
      tCharFloatHashMap2.hashCode();
      assertEquals("{O=1.0}", tCharFloatHashMap2.toString());
      
      boolean boolean0 = tCharFloatHashMap4.containsValue((float) int0);
      assertEquals(false, boolean0);
  }

  @Test
  public void test9()  throws Throwable  {
      char[] charArray0 = new char[8];
      char char0 = '(';
      charArray0[0] = char0;
      char char1 = 'Q';
      charArray0[1] = char1;
      char char2 = 'D';
      charArray0[2] = char2;
      char char3 = 'D';
      charArray0[3] = char3;
      char char4 = '7';
      charArray0[4] = char4;
      char char5 = 'm';
      charArray0[5] = char5;
      char char6 = '9';
      charArray0[6] = char6;
      char char7 = '$';
      charArray0[7] = char7;
      float[] floatArray0 = new float[6];
      float float0 = 821.9461F;
      floatArray0[0] = float0;
      floatArray0[1] = (float) charArray0[3];
      floatArray0[2] = (float) charArray0[3];
      floatArray0[3] = (float) charArray0[0];
      floatArray0[4] = (float) charArray0[7];
      floatArray0[5] = (float) char0;
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(charArray0, floatArray0);
      TCharFloatHashMap tCharFloatHashMap1 = new TCharFloatHashMap((TCharFloatMap) tCharFloatHashMap0);
      assertEquals(23, tCharFloatHashMap1.capacity());
      
      tCharFloatHashMap1._states = tCharFloatHashMap0._states;
      assertEquals(false, tCharFloatHashMap0.isEmpty());
  }

  @Test
  public void test10()  throws Throwable  {
      int int0 = 0;
      char[] charArray0 = new char[3];
      char char0 = 'P';
      charArray0[0] = char0;
      char char1 = 'P';
      charArray0[1] = char1;
      char char2 = '?';
      charArray0[2] = char2;
      float[] floatArray0 = new float[9];
      floatArray0[0] = (float) char2;
      floatArray0[1] = (float) int0;
      floatArray0[2] = (float) char2;
      floatArray0[3] = (float) char2;
      floatArray0[4] = (float) charArray0[1];
      floatArray0[5] = (float) char0;
      floatArray0[6] = (float) charArray0[0];
      floatArray0[7] = (float) charArray0[0];
      floatArray0[8] = (float) charArray0[0];
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(charArray0, floatArray0);
      tCharFloatHashMap0.size();
      TCharFloatHashMap tCharFloatHashMap1 = new TCharFloatHashMap(int0, int0);
      TCharFloatHashMap tCharFloatHashMap2 = new TCharFloatHashMap();
      char char3 = ' ';
      tCharFloatHashMap1.ensureCapacity(int0);
      float float0 = 0.0F;
      tCharFloatHashMap2.adjustOrPutValue(char3, float0, float0);
      TCharFloatHashMap tCharFloatHashMap3 = new TCharFloatHashMap((TCharFloatMap) tCharFloatHashMap2);
      assertEquals(1, tCharFloatHashMap2.size());
      assertEquals("{ =0.0}", tCharFloatHashMap3.toString());
  }

  @Test
  public void test11()  throws Throwable  {
      int int0 = 0;
      char char0 = '^';
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap(int0, (float) int0, char0, (float) int0);
      tCharFloatHashMap0.getAutoCompactionFactor();
      char[] charArray0 = new char[5];
      charArray0[0] = char0;
      charArray0[1] = char0;
      boolean boolean0 = tCharFloatHashMap0.increment(charArray0[0]);
      assertEquals(false, boolean0);
      
      tCharFloatHashMap0.compact();
      tCharFloatHashMap0.clear();
      charArray0[2] = char0;
      charArray0[3] = char0;
      charArray0[4] = char0;
      char[] charArray1 = tCharFloatHashMap0.keys(charArray0);
      assertSame(charArray1, charArray0);
      assertNotNull(charArray1);
      
      int int1 = (-929);
      tCharFloatHashMap0.getNoEntryValue();
      assertEquals("{}", tCharFloatHashMap0.toString());
      assertEquals(3, tCharFloatHashMap0.capacity());
      
      char char1 = '1';
      TCharFloatHashMap tCharFloatHashMap1 = new TCharFloatHashMap();
      // Undeclared exception!
      try {
        tCharFloatHashMap1.setAutoCompactionFactor((float) int1);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Factor must be >= 0: -929.0
         //
      }
  }

  @Test
  public void test12()  throws Throwable  {
    Future<?> future = executor.submit(new Runnable(){ 
            public void run() { 
        try {
          TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap();
          char[] charArray0 = new char[8];
          char char0 = 'p';
          charArray0[0] = char0;
          char char1 = 'A';
          charArray0[1] = char1;
          char char2 = '*';
          String string0 = "k7E<!.[[~";
          // Undeclared exception!
          try {
            File.createTempFile(string0, string0);
            fail("Expecting exception: SecurityException");
          
          } catch(SecurityException e) {
             //
             // Unable to create temporary file
             //
          }
        } catch(Throwable t) {
            // Need to catch declared exceptions
        }
      } 
    }); 
    future.get(6000, TimeUnit.MILLISECONDS); 
  }

  @Test
  public void test13()  throws Throwable  {
      char[] charArray0 = new char[10];
      char char0 = '=';
      charArray0[0] = char0;
      charArray0[1] = char0;
      charArray0[2] = char0;
      charArray0[3] = char0;
      charArray0[4] = char0;
      charArray0[5] = char0;
      charArray0[6] = char0;
      charArray0[7] = char0;
      char char1 = 'K';
      charArray0[8] = char1;
      charArray0[9] = char0;
      charArray0[0] = char0;
      char char2 = 'k';
      TCharFloatHashMap tCharFloatHashMap0 = new TCharFloatHashMap();
      charArray0[1] = char2;
      char char3 = 'b';
      charArray0[2] = char3;
      char char4 = '$';
      charArray0[3] = char4;
      char char5 = '9';
      charArray0[4] = char5;
      char char6 = 'O';
      charArray0[5] = char6;
      char char7 = ' ';
      charArray0[6] = char7;
      char char8 = '(';
      charArray0[7] = char8;
      char char9 = 'P';
      charArray0[8] = char9;
      char char10 = '~';
      charArray0[9] = char10;
      float[] floatArray0 = new float[3];
      floatArray0[0] = (float) charArray0[7];
      floatArray0[1] = (float) charArray0[6];
      floatArray0[2] = (float) charArray0[1];
      TCharFloatHashMap tCharFloatHashMap1 = new TCharFloatHashMap(charArray0, floatArray0);
      char[] charArray1 = tCharFloatHashMap1.keys();
      assertNotNull(charArray1);
      assertEquals(23, tCharFloatHashMap1.capacity());
      assertEquals("{==40.0, k=32.0, b=107.0}", tCharFloatHashMap1.toString());
  }
}
