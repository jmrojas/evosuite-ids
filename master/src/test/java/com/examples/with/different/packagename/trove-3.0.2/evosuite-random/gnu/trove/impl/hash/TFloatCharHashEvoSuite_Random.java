/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TCharFunction;
import gnu.trove.map.TFloatCharMap;
import gnu.trove.map.hash.TFloatCharHashMap;
import gnu.trove.procedure.TFloatProcedure;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.evosuite.testcase.TestCaseExecutor;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TFloatCharHashEvoSuite_Random {

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


//   @Test
//   public void test0()  throws Throwable  {
//       int int0 = 0;
//       int int1 = 0;
//       float float0 = Float.POSITIVE_INFINITY;
//       int int2 = (-1);
//       int int3 = (-21);
//       float float1 = 1437.3727F;
//       char char0 = 'O';
//       float float2 = (-1585.1082F);
//       TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(int2, float2, float1, char0);
//       tFloatCharHashMap0.valueCollection();
//       TFloatCharHashMap tFloatCharHashMap1 = new TFloatCharHashMap(int3, float1, (float) int0, char0);
//       tFloatCharHashMap1.clear();
//       TFloatCharHashMap tFloatCharHashMap2 = new TFloatCharHashMap(int2);
//       char char1 = '';
//       tFloatCharHashMap0.containsValue(char1);
//       TFloatCharHashMap tFloatCharHashMap3 = new TFloatCharHashMap(int1, (float) int1, float0, char1);
//       boolean boolean0 = true;
//       tFloatCharHashMap3.reenableAutoCompaction(boolean0);
//       assertEquals('', tFloatCharHashMap3.getNoEntryValue());
//       
//       TFloatCharHashMap tFloatCharHashMap4 = new TFloatCharHashMap(int0);
//       float float3 = 0.0F;
//       float float4 = Float.NEGATIVE_INFINITY;
//       char char2 = tFloatCharHashMap4.remove(float4);
//       TFloatCharHashMap tFloatCharHashMap5 = new TFloatCharHashMap();
//       boolean boolean1 = tFloatCharHashMap4.increment(float3);
//       assertEquals(false, boolean1);
//       
//       TFloatCharHashMap tFloatCharHashMap6 = new TFloatCharHashMap();
//       char char3 = tFloatCharHashMap6.getNoEntryValue();
//       int int4 = 82;
//       float float5 = 715.6289F;
//       TFloatCharHashMap tFloatCharHashMap7 = new TFloatCharHashMap(int4, (float) int4, float5, char3);
//       float float6 = tFloatCharHashMap7.getNoEntryKey();
//       assertEquals(715.6289F, float6, 0.01F);
//       
//       float float7 = (-1544.4614F);
//       int int5 = 0;
//       TFloatCharHashMap tFloatCharHashMap8 = new TFloatCharHashMap(int5);
//       assertEquals(0.0F, tFloatCharHashMap8.getNoEntryKey(), 0.01F);
//       
//       boolean boolean2 = tFloatCharHashMap6.containsKey(float7);
//       assertEquals(false, boolean2);
//       
//       tFloatCharHashMap7.capacity();
//       tFloatCharHashMap7.ensureCapacity((int) char3);
//       assertTrue(char3 == char2);
//       assertEquals(0.0F, tFloatCharHashMap6.getNoEntryKey(), 0.01F);
//       assertEquals(23, tFloatCharHashMap6.capacity());
//       assertTrue(tFloatCharHashMap7.equals(tFloatCharHashMap4));
//   }

//   @Test
//   public void test1()  throws Throwable  {
//       int int0 = 1143;
//       TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(int0, int0);
//       tFloatCharHashMap0.getAutoCompactionFactor();
//       int int1 = 1;
//       TFloatCharHashMap tFloatCharHashMap1 = new TFloatCharHashMap(int1, int1);
//       tFloatCharHashMap1.trimToSize();
//       int int2 = 0;
//       ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream(int2);
//       tFloatCharHashMap1.getAutoCompactionFactor();
//       ObjectOutputStream objectOutputStream0 = new ObjectOutputStream((OutputStream) byteArrayOutputStream0);
//       float float0 = tFloatCharHashMap1.getNoEntryKey();
//       assertEquals(0.0F, float0, 0.01F);
//       
//       boolean boolean0 = tFloatCharHashMap1.containsKey((float) int1);
//       assertEquals(false, boolean0);
//       
//       tFloatCharHashMap1.clear();
//       tFloatCharHashMap1.writeExternal((ObjectOutput) objectOutputStream0);
//       float float1 = (-1955.5967F);
//       char char0 = tFloatCharHashMap0.remove(float1);
//       assertEquals('\u0000', char0);
//       
//       objectOutputStream0.writeBoolean(boolean0);
//       tFloatCharHashMap1.getAutoCompactionFactor();
//       ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
//       boolean boolean1 = false;
//       byteArrayOutputStream1.size();
//       String string0 = "no elemenet at ";
//       PrintStream printStream0 = null;
//       try {
//         printStream0 = new PrintStream((OutputStream) byteArrayOutputStream1, boolean1, string0);
//         fail("Expecting exception: UnsupportedEncodingException");
//       
//       } catch(UnsupportedEncodingException e) {
//          //
//          // no elemenet at 
//          //
//       }
//   }

//   @Test
//   public void test2()  throws Throwable  {
//       TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap();
//       int int0 = 1627;
//       PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
//       PipedInputStream pipedInputStream0 = new PipedInputStream(pipedOutputStream0, int0);
//       ObjectInputStream objectInputStream0 = null;
//       try {
//         objectInputStream0 = new ObjectInputStream((InputStream) pipedInputStream0);
//         fail("Expecting exception: InterruptedIOException");
//       
//       } catch(InterruptedIOException e) {
//          //
//          // no message in exception (getMessage() returned null)
//          //
//       }
//   }

  @Test
  public void test3()  throws Throwable  {
      int int0 = 1016;
      float float0 = 0.0F;
      TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(int0, float0);
      TFloatCharHashMap tFloatCharHashMap1 = null;
      try {
        tFloatCharHashMap1 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap0);
        fail("Expecting exception: OutOfMemoryError");
      
      } catch(OutOfMemoryError e) {
         //
         // Java heap space
         //
      }
  }

  @Test
  public void test4()  throws Throwable  {
      float[] floatArray0 = new float[8];
      float float0 = (-1682.7189F);
      floatArray0[0] = float0;
      float float1 = (-1.0F);
      floatArray0[1] = float1;
      float float2 = (-758.45776F);
      floatArray0[2] = float2;
      float float3 = (-1.0F);
      floatArray0[3] = float3;
      float float4 = 0.0F;
      floatArray0[4] = float4;
      float float5 = 0.0F;
      floatArray0[5] = float5;
      float float6 = 0.0F;
      floatArray0[6] = float6;
      float float7 = (-1.0F);
      floatArray0[7] = float7;
      char[] charArray0 = new char[2];
      char char0 = '>';
      charArray0[0] = char0;
      char char1 = 'w';
      charArray0[1] = char1;
      TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(floatArray0, charArray0);
      tFloatCharHashMap0.trimToSize();
      assertEquals(5, tFloatCharHashMap0.capacity());
      assertEquals("{-1682.7189=>, -1.0=w}", tFloatCharHashMap0.toString());
  }

  @Test
  public void test5()  throws Throwable  {
      int int0 = 1335;
      TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap();
      char char0 = 'b';
      tFloatCharHashMap0.getNoEntryValue();
      tFloatCharHashMap0.put((float) int0, char0);
      tFloatCharHashMap0.tempDisableAutoCompaction();
      TFloatCharMap tFloatCharMap0 = null;
      TFloatCharHashMap tFloatCharHashMap1 = null;
      try {
        tFloatCharHashMap1 = new TFloatCharHashMap(tFloatCharMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

//   @Test
//   public void test6()  throws Throwable  {
//       int int0 = 2081;
//       float float0 = 1469.6937F;
//       float float1 = 257.9701F;
//       TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap();
//       float float2 = 0.0F;
//       tFloatCharHashMap0.setAutoCompactionFactor(float2);
//       tFloatCharHashMap0.getAutoCompactionFactor();
//       TFloatCharHashMap tFloatCharHashMap1 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap0);
//       TFloatCharHashMap tFloatCharHashMap2 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap1);
//       boolean boolean0 = tFloatCharHashMap2.contains(float0);
//       assertEquals('\u0000', tFloatCharHashMap1.getNoEntryValue());
//       assertEquals(0.0F, tFloatCharHashMap1.getNoEntryKey(), 0.01F);
//       assertEquals(false, boolean0);
//       
//       TFloatCharHashMap tFloatCharHashMap3 = new TFloatCharHashMap(int0, float1);
//       TFloatCharHashMap tFloatCharHashMap4 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap3);
//       int int1 = tFloatCharHashMap3.capacity();
//       assertEquals(11, int1);
//       
//       tFloatCharHashMap4.increment(float0);
//       TFloatCharHashMap tFloatCharHashMap5 = new TFloatCharHashMap(int0, float0);
//       TFloatCharHashMap tFloatCharHashMap6 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap5);
//       TFloatCharHashMap tFloatCharHashMap7 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap6);
//       float float3 = 0.0F;
//       char char0 = 'D';
//       TFloatCharHashMap tFloatCharHashMap8 = new TFloatCharHashMap(int0, (float) int0, float3, char0);
//       assertEquals(0.0F, tFloatCharHashMap8.getNoEntryKey(), 0.01F);
//       assertEquals('D', tFloatCharHashMap8.getNoEntryValue());
//       
//       HashMap<String, Object> hashMap0 = new HashMap<String, Object>();
//       int int2 = 0;
//       ByteArrayInputStream byteArrayInputStream0 = new ByteArrayInputStream(tFloatCharHashMap3._states, int1, int2);
//       SequenceInputStream sequenceInputStream0 = new SequenceInputStream((InputStream) byteArrayInputStream0, (InputStream) byteArrayInputStream0);
//       ObjectInputStream objectInputStream0 = null;
//       try {
//         objectInputStream0 = new ObjectInputStream((InputStream) sequenceInputStream0);
//         fail("Expecting exception: EOFException");
//       
//       } catch(EOFException e) {
//          //
//          // no message in exception (getMessage() returned null)
//          //
//       }
//   }

//   @Test
//   public void test7()  throws Throwable  {
//       float[] floatArray0 = new float[9];
//       float float0 = (-1713.8416F);
//       floatArray0[0] = float0;
//       float float1 = (-1771.3013F);
//       floatArray0[1] = float1;
//       float float2 = 6.6360896E8F;
//       floatArray0[2] = float2;
//       float float3 = (-1070.4229F);
//       floatArray0[3] = float3;
//       float float4 = 907.5745F;
//       floatArray0[4] = float4;
//       float float5 = 1425.6854F;
//       floatArray0[5] = float5;
//       float float6 = 1698.3059F;
//       floatArray0[6] = float6;
//       float float7 = (-889.68134F);
//       floatArray0[7] = float7;
//       float float8 = 0.0F;
//       floatArray0[8] = float8;
//       char[] charArray0 = new char[10];
//       char char0 = '6';
//       charArray0[0] = char0;
//       char char1 = 'q';
//       charArray0[1] = char1;
//       char char2 = 'f';
//       charArray0[2] = char2;
//       char char3 = '<';
//       charArray0[3] = char3;
//       char char4 = '|';
//       charArray0[4] = char4;
//       char char5 = ']';
//       charArray0[5] = char5;
//       char char6 = '=';
//       charArray0[6] = char6;
//       char char7 = '$';
//       charArray0[7] = char7;
//       char char8 = '\\';
//       charArray0[8] = char8;
//       char char9 = 'G';
//       charArray0[9] = char9;
//       TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(floatArray0, charArray0);
//       assertEquals("{6.6360896E8=f, 907.5745=|, -1771.3013=q, -1070.4229=<, 1698.3059==, 1425.6854=], -1713.8416=6, -889.68134=$, 0.0=\\}", tFloatCharHashMap0.toString());
//       
//       byte[] byteArray0 = new byte[10];
//       byte byte0 = (byte)65;
//       byteArray0[0] = byte0;
//       byte byte1 = (byte)0;
//       byteArray0[1] = byte1;
//       byte byte2 = (byte) (-114);
//       byteArray0[2] = byte2;
//       byte byte3 = (byte)22;
//       byteArray0[3] = byte3;
//       byte byte4 = (byte)0;
//       byteArray0[4] = byte4;
//       byte byte5 = (byte) (-13);
//       byteArray0[5] = byte5;
//       byte byte6 = (byte) (-35);
//       byteArray0[6] = byte6;
//       byte byte7 = (byte)77;
//       byteArray0[7] = byte7;
//       byte byte8 = (byte)0;
//       byteArray0[8] = byte8;
//       byte byte9 = (byte)99;
//       byteArray0[9] = byte9;
//       tFloatCharHashMap0._states = byteArray0;
//       assertEquals(0.0F, tFloatCharHashMap0.getNoEntryKey(), 0.01F);
//       assertEquals('\u0000', tFloatCharHashMap0.getNoEntryValue());
//   }

//   @Test
//   public void test8()  throws Throwable  {
//       float[] floatArray0 = new float[6];
//       char[] charArray0 = new char[4];
//       char char0 = 'j';
//       charArray0[0] = char0;
//       char char1 = 'u';
//       charArray0[1] = char1;
//       char char2 = '1';
//       charArray0[2] = char2;
//       char char3 = '\\';
//       charArray0[3] = char3;
//       TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(floatArray0, charArray0);
//       TFloatCharHashMap tFloatCharHashMap1 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap0);
//       assertEquals(23, tFloatCharHashMap1.capacity());
//       
//       tFloatCharHashMap1.ensureCapacity((int) charArray0[1]);
//       float float0 = 1241.6434F;
//       floatArray0[0] = float0;
//       float float1 = (-1216.8724F);
//       floatArray0[1] = float1;
//       float float2 = (-615.7488F);
//       floatArray0[2] = float2;
//       float float3 = Float.NEGATIVE_INFINITY;
//       floatArray0[3] = float3;
//       float float4 = 134.92207F;
//       floatArray0[4] = float4;
//       float float5 = Float.NEGATIVE_INFINITY;
//       floatArray0[5] = float5;
//       char[] charArray1 = new char[2];
//       char char4 = 'Z';
//       charArray1[0] = char4;
//       char char5 = 'f';
//       charArray1[1] = char5;
//       TFloatCharHashMap tFloatCharHashMap2 = new TFloatCharHashMap(floatArray0, charArray1);
//       TFloatCharHashMap tFloatCharHashMap3 = new TFloatCharHashMap();
//       tFloatCharHashMap3.iterator();
//       assertEquals(0.0F, tFloatCharHashMap3.getNoEntryKey(), 0.01F);
//   }

  @Test
  public void test9()  throws Throwable  {
      int int0 = 0;
      TFloatCharMap tFloatCharMap0 = null;
      TFloatCharHashMap tFloatCharHashMap0 = null;
      try {
        tFloatCharHashMap0 = new TFloatCharHashMap(tFloatCharMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

//   @Test
//   public void test10()  throws Throwable  {
//       float[] floatArray0 = new float[8];
//       float float0 = 31.728622F;
//       floatArray0[0] = float0;
//       float float1 = 1131.198F;
//       floatArray0[1] = float1;
//       float float2 = 0.0F;
//       floatArray0[2] = float2;
//       float float3 = 1.0F;
//       int int0 = 0;
//       float float4 = 1483.0428F;
//       char char0 = 'p';
//       TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(int0, float0, float4, char0);
//       tFloatCharHashMap0.isEmpty();
//       assertEquals('p', tFloatCharHashMap0.getNoEntryValue());
//       assertEquals(1483.0428F, tFloatCharHashMap0.getNoEntryKey(), 0.01F);
//       
//       floatArray0[3] = float3;
//       float float5 = (-415.26706F);
//       floatArray0[4] = float5;
//       float float6 = 0.0F;
//       int int1 = 1665;
//       TFloatCharHashMap tFloatCharHashMap1 = new TFloatCharHashMap(int1, float5);
//       assertEquals('\u0000', tFloatCharHashMap1.getNoEntryValue());
//       assertEquals(0.0F, tFloatCharHashMap1.getNoEntryKey(), 0.01F);
//       
//       floatArray0[5] = float6;
//       float float7 = Float.NEGATIVE_INFINITY;
//       floatArray0[6] = float7;
//       float float8 = 0.0F;
//       floatArray0[7] = float8;
//       char[] charArray0 = new char[1];
//       char char1 = 'L';
//       charArray0[0] = char1;
//       charArray0[0] = char1;
//       TFloatCharHashMap tFloatCharHashMap2 = new TFloatCharHashMap(floatArray0, charArray0);
//       tFloatCharHashMap2.tempDisableAutoCompaction();
//       assertEquals("{31.728622=L}", tFloatCharHashMap2.toString());
//       assertEquals(0.0F, tFloatCharHashMap2.getNoEntryKey(), 0.01F);
//       assertEquals('\u0000', tFloatCharHashMap2.getNoEntryValue());
//       assertEquals(17, tFloatCharHashMap2.capacity());
//   }

//   @Test
//   public void test11()  throws Throwable  {
//       float[] floatArray0 = new float[6];
//       float float0 = 0.5F;
//       floatArray0[0] = float0;
//       float float1 = 0.0F;
//       floatArray0[1] = float1;
//       float float2 = (-1017.726F);
//       floatArray0[2] = float2;
//       float float3 = 0.0F;
//       floatArray0[3] = float3;
//       float float4 = (-1271.3489F);
//       floatArray0[4] = float4;
//       float float5 = 1342.6807F;
//       floatArray0[5] = float5;
//       char[] charArray0 = new char[6];
//       char char0 = 'l';
//       charArray0[0] = char0;
//       char char1 = 'u';
//       charArray0[1] = char1;
//       char char2 = 'D';
//       charArray0[2] = char2;
//       char char3 = '=';
//       charArray0[3] = char3;
//       char char4 = '6';
//       charArray0[4] = char4;
//       char char5 = 'k';
//       charArray0[5] = char5;
//       TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(floatArray0, charArray0);
//       TFloatCharHashMap tFloatCharHashMap1 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap0);
//       TFloatCharHashMap tFloatCharHashMap2 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap1);
//       assertEquals(0.0F, tFloatCharHashMap1.getNoEntryKey(), 0.01F);
//       assertEquals("{0.5=l, 1342.6807=k, -1017.726=D, -1271.3489=6, 0.0==}", tFloatCharHashMap1.toString());
//       assertEquals(17, tFloatCharHashMap0.capacity());
//       assertTrue(tFloatCharHashMap1.equals(tFloatCharHashMap0));
//       assertEquals("{0.5=l, -1017.726=D, 1342.6807=k, -1271.3489=6, 0.0==}", tFloatCharHashMap2.toString());
//       
//       int int0 = (-1);
//       int int1 = 0;
//       float float6 = (-666.6228F);
//       TFloatCharHashMap tFloatCharHashMap3 = new TFloatCharHashMap(int1, float6);
//       TCharFunction tCharFunction0 = null;
//       tFloatCharHashMap3.transformValues(tCharFunction0);
//       TFloatCharHashMap tFloatCharHashMap4 = new TFloatCharHashMap(int0, int0);
//       byte[] byteArray0 = tFloatCharHashMap4._states;
//       char char6 = 'R';
//       tFloatCharHashMap4.containsValue(char6);
//       assertEquals(0.0F, tFloatCharHashMap4.getNoEntryKey(), 0.01F);
//       assertEquals('\u0000', tFloatCharHashMap4.getNoEntryValue());
//       
//       TFloatCharHashMap tFloatCharHashMap5 = new TFloatCharHashMap();
//       tFloatCharHashMap5.getAutoCompactionFactor();
//       tFloatCharHashMap5.hashCode();
//       tFloatCharHashMap5.values();
//       assertEquals('\u0000', tFloatCharHashMap5.getNoEntryValue());
//       assertEquals(23, tFloatCharHashMap5.capacity());
//   }

//   @Test
//   public void test12()  throws Throwable  {
//       float[] floatArray0 = new float[9];
//       float float0 = (-1871.5144F);
//       floatArray0[0] = float0;
//       float float1 = 0.0F;
//       floatArray0[1] = float1;
//       float float2 = (-235.96806F);
//       floatArray0[2] = float2;
//       float float3 = (-259.08118F);
//       floatArray0[3] = float3;
//       float float4 = Float.POSITIVE_INFINITY;
//       floatArray0[4] = float4;
//       float float5 = (-1099.6097F);
//       floatArray0[5] = float5;
//       float float6 = 1.0F;
//       floatArray0[6] = float6;
//       float float7 = 0.0F;
//       floatArray0[7] = float7;
//       float float8 = 0.0F;
//       floatArray0[8] = float8;
//       char[] charArray0 = new char[2];
//       char char0 = 'i';
//       charArray0[0] = char0;
//       char char1 = '';
//       charArray0[1] = char1;
//       TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(floatArray0, charArray0);
//       TFloatCharHashMap tFloatCharHashMap1 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap0);
//       assertEquals('\u0000', tFloatCharHashMap0.getNoEntryValue());
//       assertEquals(0.0F, tFloatCharHashMap0.getNoEntryKey(), 0.01F);
//       assertEquals(23, tFloatCharHashMap0.capacity());
//       assertEquals("{-1871.5144=i, 0.0=}", tFloatCharHashMap0.toString());
//       assertTrue(tFloatCharHashMap1.equals(tFloatCharHashMap0));
//   }

  @Test
  public void test13()  throws Throwable  {
      float[] floatArray0 = new float[4];
      float float0 = 0.0F;
      floatArray0[0] = float0;
      float float1 = 0.0F;
      floatArray0[1] = float1;
      float float2 = 0.0F;
      floatArray0[2] = float2;
      float float3 = 0.0F;
      floatArray0[3] = float3;
      char[] charArray0 = new char[2];
      char char0 = '!';
      charArray0[0] = char0;
      charArray0[1] = char0;
      char char1 = 'm';
      charArray0[0] = char1;
      char char2 = '\"';
      charArray0[1] = char2;
      TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap((int) char2, floatArray0[0]);
      TFloatCharHashMap tFloatCharHashMap1 = null;
      try {
        tFloatCharHashMap1 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap0);
        fail("Expecting exception: OutOfMemoryError");
      
      } catch(OutOfMemoryError e) {
         //
         // Java heap space
         //
      }
  }

  @Test
  public void test14()  throws Throwable  {
      float[] floatArray0 = new float[6];
      float float0 = (-1.0F);
      floatArray0[0] = float0;
      floatArray0[1] = float0;
      float float1 = (-1.0F);
      floatArray0[2] = float1;
      floatArray0[3] = float0;
      floatArray0[4] = float0;
      floatArray0[5] = float1;
      floatArray0[0] = float0;
      float float2 = 0.0F;
      floatArray0[1] = float2;
      float float3 = 860.15674F;
      floatArray0[2] = float3;
      float float4 = 1.0F;
      floatArray0[3] = float4;
      float float5 = 1509.3439F;
      floatArray0[4] = float5;
      float float6 = 0.0F;
      floatArray0[5] = float6;
      char[] charArray0 = new char[5];
      char char0 = '`';
      charArray0[0] = char0;
      char char1 = 'm';
      charArray0[1] = char1;
      char char2 = '#';
      charArray0[2] = char2;
      char char3 = 'c';
      charArray0[3] = char3;
      char char4 = 'i';
      charArray0[4] = char4;
      TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(floatArray0, charArray0);
      tFloatCharHashMap0.tempDisableAutoCompaction();
      char char5 = tFloatCharHashMap0.getNoEntryValue();
      assertEquals("{-1.0=`, 1.0=c, 860.15674=#, 1509.3439=i, 0.0=m}", tFloatCharHashMap0.toString());
      assertEquals('\u0000', char5);
  }

//   @Test
//   public void test15()  throws Throwable  {
//       int int0 = (-1272);
//       char char0 = 'Q';
//       TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(int0, (float) int0, (float) int0, char0);
//       char char1 = tFloatCharHashMap0.getNoEntryValue();
//       assertEquals('Q', char1);
//       
//       float[] floatArray0 = new float[1];
//       float float0 = 0.0F;
//       floatArray0[0] = float0;
//       char[] charArray0 = new char[5];
//       int int1 = 9677;
//       float float1 = (-1625.861F);
//       char char2 = 'p';
//       TFloatCharHashMap tFloatCharHashMap1 = new TFloatCharHashMap(int1, (float) int1, float1, char2);
//       assertEquals((-1625.861F), tFloatCharHashMap1.getNoEntryKey(), 0.01F);
//       
//       char char3 = ')';
//       charArray0[0] = char3;
//       char char4 = 'R';
//       charArray0[1] = char4;
//       char char5 = 'l';
//       charArray0[2] = char5;
//       TFloatCharHashMap tFloatCharHashMap2 = new TFloatCharHashMap();
//       char char6 = tFloatCharHashMap2.getNoEntryValue();
//       assertEquals('\u0000', char6);
//       assertEquals(0.0F, tFloatCharHashMap2.getNoEntryKey(), 0.01F);
//       
//       char char7 = 'L';
//       charArray0[3] = char7;
//       char char8 = '/';
//       charArray0[4] = char8;
//       TFloatCharHashMap tFloatCharHashMap3 = new TFloatCharHashMap(floatArray0, charArray0);
//       assertEquals('\u0000', tFloatCharHashMap3.getNoEntryValue());
//       assertEquals(1, tFloatCharHashMap3.size());
//       assertEquals("{0.0=)}", tFloatCharHashMap3.toString());
//       assertEquals(11, tFloatCharHashMap3.capacity());
//       assertEquals(0.0F, tFloatCharHashMap3.getNoEntryKey(), 0.01F);
//   }

  @Test
  public void test16()  throws Throwable  {
      int int0 = 0;
      TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(int0);
      TFloatCharHashMap tFloatCharHashMap1 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap0);
      float[] floatArray0 = tFloatCharHashMap1.keys();
      assertNotNull(floatArray0);
      
      tFloatCharHashMap1.size();
      int int1 = (-299);
      TFloatCharHashMap tFloatCharHashMap2 = new TFloatCharHashMap(int1);
      TFloatCharHashMap tFloatCharHashMap3 = new TFloatCharHashMap(int1);
      tFloatCharHashMap2.hashCode();
      TFloatCharHashMap tFloatCharHashMap4 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap3);
      // Undeclared exception!
      try {
        tFloatCharHashMap4.setAutoCompactionFactor((float) int1);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Factor must be >= 0: -299.0
         //
      }
  }

//   @Test
//   public void test17()  throws Throwable  {
//       int int0 = (-1);
//       float float0 = 371.63663F;
//       int int1 = 0;
//       TFloatCharHashMap tFloatCharHashMap0 = new TFloatCharHashMap(int1);
//       TFloatProcedure tFloatProcedure0 = null;
//       boolean boolean0 = tFloatCharHashMap0.forEach(tFloatProcedure0);
//       assertEquals(true, boolean0);
//       
//       TFloatCharHashMap tFloatCharHashMap1 = new TFloatCharHashMap(int0, float0);
//       tFloatCharHashMap1.capacity();
//       assertEquals(0.0F, tFloatCharHashMap1.getNoEntryKey(), 0.01F);
//       assertEquals('\u0000', tFloatCharHashMap1.getNoEntryValue());
//       
//       int int2 = Integer.MAX_VALUE;
//       float float1 = 0.0F;
//       TFloatCharHashMap tFloatCharHashMap2 = new TFloatCharHashMap(int2);
//       tFloatCharHashMap2.getAutoCompactionFactor();
//       tFloatCharHashMap2.isEmpty();
//       char char0 = tFloatCharHashMap2.getNoEntryValue();
//       TFloatCharHashMap tFloatCharHashMap3 = new TFloatCharHashMap((TFloatCharMap) tFloatCharHashMap2);
//       tFloatCharHashMap3.keySet();
//       char[] charArray0 = new char[5];
//       char char1 = 'H';
//       charArray0[0] = char1;
//       charArray0[1] = char0;
//       char char2 = 'W';
//       charArray0[2] = char2;
//       charArray0[3] = char0;
//       charArray0[4] = char0;
//       char[] charArray1 = tFloatCharHashMap3.values(charArray0);
//       assertNotNull(charArray1);
//       assertEquals(23, tFloatCharHashMap3.capacity());
//       
//       char char3 = '#';
//       tFloatCharHashMap2.capacity();
//       boolean boolean1 = true;
//       tFloatCharHashMap2.reenableAutoCompaction(boolean1);
//       boolean boolean2 = false;
//       tFloatCharHashMap2.reenableAutoCompaction(boolean2);
//       TFloatCharHashMap tFloatCharHashMap4 = new TFloatCharHashMap(int2, (float) int2, float1, char3);
//       byte[] byteArray0 = tFloatCharHashMap4._states;
//       assertEquals('#', tFloatCharHashMap4.getNoEntryValue());
//       assertEquals(0.0F, tFloatCharHashMap4.getNoEntryKey(), 0.01F);
//       
//       char char4 = tFloatCharHashMap2.getNoEntryValue();
//       assertEquals('\u0000', char4);
//       assertEquals(0.0F, tFloatCharHashMap2.getNoEntryKey(), 0.01F);
//   }
}
