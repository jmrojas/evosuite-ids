/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.map.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TLongFunction;
import gnu.trove.map.TShortLongMap;
import gnu.trove.map.hash.TShortLongHashMap;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TShortProcedure;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TShortLongHashMapEvoSuite_Random {

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
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap();
      short short0 = (short)0;
      tShortLongHashMap0.getNoEntryKey();
      int int0 = 0;
      float float0 = 251.76845F;
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(int0, float0);
      short short1 = (short) (-1);
      long long0 = 0L;
      tShortLongHashMap0.adjustOrPutValue(short1, long0, (long) short1);
      tShortLongHashMap0.containsKey(short0);
      long long1 = 0L;
      tShortLongHashMap0.keySet();
      short short2 = (short)0;
      tShortLongHashMap0.get(short2);
      tShortLongHashMap0.put(short0, long1);
      assertEquals("{-1=-1, 0=0}", tShortLongHashMap0.toString());
      
      TShortLongHashMap tShortLongHashMap2 = new TShortLongHashMap();
      TShortLongHashMap tShortLongHashMap3 = new TShortLongHashMap((int) short0, (float) long1, short0, (long) short0);
      TLongProcedure tLongProcedure0 = null;
      tShortLongHashMap2.forEachValue(tLongProcedure0);
      tShortLongHashMap2.size();
      tShortLongHashMap2.keys();
      tShortLongHashMap2.trimToSize();
      assertEquals(3, tShortLongHashMap2.capacity());
  }

  @Test
  public void test1()  throws Throwable  {
      int int0 = 1200;
      float float0 = (-344.9199F);
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0, float0);
      short short0 = (short)798;
      boolean boolean0 = tShortLongHashMap0.containsKey(short0);
      assertEquals(false, boolean0);
  }

  @Test
  public void test2()  throws Throwable  {
      short[] shortArray0 = new short[5];
      short short0 = (short) (-1593);
      shortArray0[0] = short0;
      shortArray0[1] = short0;
      shortArray0[2] = short0;
      short short1 = (short) (-1);
      long[] longArray0 = new long[6];
      longArray0[0] = (long) shortArray0[2];
      longArray0[1] = (long) shortArray0[1];
      longArray0[2] = (long) shortArray0[0];
      longArray0[3] = (long) shortArray0[2];
      longArray0[4] = (long) shortArray0[2];
      longArray0[5] = (long) shortArray0[2];
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(shortArray0, longArray0);
      shortArray0[3] = short1;
      short short2 = (short)0;
      shortArray0[4] = short2;
      short short3 = (short) (-1697);
      shortArray0[0] = short3;
      short short4 = (short)5119;
      long long0 = 0L;
      tShortLongHashMap0.adjustOrPutValue(short1, (long) short1, long0);
      shortArray0[1] = short4;
      short short5 = (short)24123;
      shortArray0[2] = short5;
      short short6 = (short)6;
      shortArray0[3] = short6;
      short short7 = (short)0;
      shortArray0[4] = short7;
      long[] longArray1 = new long[4];
      longArray1[0] = (long) shortArray0[2];
      longArray1[1] = (long) short5;
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap();
      TShortLongHashMap tShortLongHashMap2 = new TShortLongHashMap((TShortLongMap) tShortLongHashMap1);
      tShortLongHashMap0.putAll((TShortLongMap) tShortLongHashMap2);
      long long1 = 1053L;
      tShortLongHashMap0.size();
      assertEquals(3, tShortLongHashMap0.size());
      
      longArray1[2] = long1;
      longArray1[3] = (long) shortArray0[4];
      TShortLongHashMap tShortLongHashMap3 = new TShortLongHashMap(shortArray0, longArray1);
      assertEquals("{-1697=24123, 6=0, 5119=24123, 24123=1053}", tShortLongHashMap3.toString());
  }

//   @Test
//   public void test3()  throws Throwable  {
//       int int0 = (-1542);
//       short short0 = (short)0;
//       TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0, (float) int0, short0, (long) short0);
//       ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
//       PrintStream printStream0 = new PrintStream((OutputStream) byteArrayOutputStream0);
//       ObjectOutputStream objectOutputStream0 = new ObjectOutputStream((OutputStream) printStream0);
//       tShortLongHashMap0.writeExternal((ObjectOutput) objectOutputStream0);
//       assertEquals(0, tShortLongHashMap0.getNoEntryKey());
//   }

  @Test
  public void test4()  throws Throwable  {
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap();
      String string0 = ", ";
      RandomAccessFile randomAccessFile0 = null;
      try {
        randomAccessFile0 = new RandomAccessFile(string0, string0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal mode \", \" must be one of \"r\", \"rw\", \"rws\", or \"rwd\"
         //
      }
  }

  @Test
  public void test5()  throws Throwable  {
      TShortLongMap tShortLongMap0 = null;
      int int0 = 1310;
      float float0 = 756.0782F;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0, float0);
      short short0 = (short)1436;
      tShortLongHashMap0.get(short0);
      int int1 = 0;
      tShortLongHashMap0.ensureCapacity(int1);
      TShortLongHashMap tShortLongHashMap1 = null;
      try {
        tShortLongHashMap1 = new TShortLongHashMap(tShortLongMap0);
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
      float float0 = 1.0F;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0, float0);
      tShortLongHashMap0.capacity();
      int int1 = 0;
      int int2 = 0;
      short short0 = (short) (-1184);
      long long0 = 9223372036854775807L;
      float float1 = 1890.4783F;
      long long1 = (-116L);
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(int1, float1, short0, long1);
      tShortLongHashMap1.capacity();
      int int3 = (-1990);
      TShortLongHashMap tShortLongHashMap2 = new TShortLongHashMap(int3, int1);
      int int4 = tShortLongHashMap2.hashCode();
      assertEquals(0, int4);
      
      TShortLongHashMap tShortLongHashMap3 = new TShortLongHashMap(int2, (float) int2, short0, long0);
      short short1 = (short)1173;
      tShortLongHashMap3.getNoEntryKey();
      long[] longArray0 = new long[2];
      longArray0[0] = (long) short1;
      longArray0[1] = (long) int3;
      long[] longArray1 = tShortLongHashMap1.values(longArray0);
      assertNotNull(longArray1);
      assertSame(longArray1, longArray0);
      
      long long2 = 1926L;
      TShortLongHashMap tShortLongHashMap4 = new TShortLongHashMap(int1, (float) int1, short1, long2);
      byte[] byteArray0 = tShortLongHashMap4._states;
      TShortLongHashMap tShortLongHashMap5 = new TShortLongHashMap();
      TShortProcedure tShortProcedure0 = null;
      boolean boolean0 = tShortLongHashMap5.forEachKey(tShortProcedure0);
      assertEquals(true, boolean0);
      assertEquals(23, tShortLongHashMap5.capacity());
  }

  @Test
  public void test7()  throws Throwable  {
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap();
      tShortLongHashMap0.valueCollection();
      int int0 = (-1);
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap();
      tShortLongHashMap1.capacity();
      tShortLongHashMap1.keys();
      String string0 = "begin index can not be < 0";
      try {
        Long.decode(string0);
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // For input string: \"begin index can not be < 0\"
         //
      }
  }

  @Test
  public void test8()  throws Throwable  {
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap();
      tShortLongHashMap0.valueCollection();
      assertEquals(23, tShortLongHashMap0.capacity());
  }

  @Test
  public void test9()  throws Throwable  {
      int int0 = (-1826);
      short short0 = (short)207;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0, (float) int0, short0, (long) int0);
      tShortLongHashMap0.iterator();
      int int1 = 1;
      int int2 = (-866);
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(int2);
      short[] shortArray0 = new short[2];
      short short1 = (short)21;
      shortArray0[0] = short1;
      int int3 = tShortLongHashMap1.hashCode();
      assertEquals(0, int3);
      
      short short2 = (short)397;
      short[] shortArray1 = tShortLongHashMap0.keys();
      assertNotNull(shortArray1);
      
      shortArray0[1] = short2;
      short[] shortArray2 = tShortLongHashMap1.keys(shortArray0);
      assertNotNull(shortArray2);
      assertSame(shortArray0, shortArray2);
      
      float float0 = 1240.8561F;
      TShortLongHashMap tShortLongHashMap2 = new TShortLongHashMap(int1, float0);
      TShortLongHashMap tShortLongHashMap3 = new TShortLongHashMap((TShortLongMap) tShortLongHashMap2);
      boolean boolean0 = true;
      tShortLongHashMap3.reenableAutoCompaction(boolean0);
      TShortLongHashMap tShortLongHashMap4 = new TShortLongHashMap((TShortLongMap) tShortLongHashMap3);
      long[] longArray0 = new long[2];
      long long0 = (-301L);
      longArray0[0] = long0;
      longArray0[1] = (long) int1;
      long[] longArray1 = tShortLongHashMap4.values(longArray0);
      assertEquals(3, tShortLongHashMap4.capacity());
      assertNotNull(longArray1);
      assertEquals(3, tShortLongHashMap3.capacity());
  }

  @Test
  public void test10()  throws Throwable  {
      int int0 = 0;
      float float0 = 0.0F;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0, float0);
      long[] longArray0 = new long[9];
      longArray0[0] = (long) int0;
      longArray0[1] = (long) int0;
      longArray0[2] = (long) int0;
      longArray0[3] = (long) int0;
      longArray0[4] = (long) int0;
      longArray0[5] = (long) int0;
      longArray0[6] = (long) int0;
      longArray0[7] = (long) int0;
      longArray0[8] = (long) int0;
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(tShortLongHashMap0._set, longArray0);
      tShortLongHashMap0.putAll((TShortLongMap) tShortLongHashMap1);
      assertEquals(7, tShortLongHashMap0.capacity());
      assertEquals("{0=0}", tShortLongHashMap0.toString());
  }

  @Test
  public void test11()  throws Throwable  {
      int int0 = 0;
      short short0 = (short)1148;
      long long0 = (-1L);
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0, (float) int0, short0, long0);
      long[] longArray0 = tShortLongHashMap0.values();
      assertNotNull(longArray0);
  }

  @Test
  public void test12()  throws Throwable  {
      int int0 = 0;
      int int1 = 1417;
      int int2 = (-971);
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int2, int2);
      short[] shortArray0 = new short[6];
      short short0 = (short)0;
      shortArray0[0] = short0;
      short short1 = (short)44;
      shortArray0[1] = short1;
      short short2 = (short)1070;
      shortArray0[2] = short2;
      short short3 = (short)0;
      shortArray0[3] = short3;
      short short4 = (short)0;
      shortArray0[4] = short4;
      short short5 = (short)12853;
      shortArray0[5] = short5;
      tShortLongHashMap0._set = shortArray0;
      float float0 = 0.0F;
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(int1, float0);
      long[] longArray0 = new long[10];
      tShortLongHashMap1.getAutoCompactionFactor();
      longArray0[0] = (long) int1;
      longArray0[1] = (long) int1;
      longArray0[2] = (long) int1;
      longArray0[3] = (long) int0;
      tShortLongHashMap1.hashCode();
      longArray0[4] = (long) int1;
      longArray0[5] = (long) int0;
      longArray0[6] = (long) int0;
      longArray0[7] = (long) int1;
      longArray0[8] = (long) int0;
      TShortLongHashMap tShortLongHashMap2 = new TShortLongHashMap();
      longArray0[9] = (long) int1;
      tShortLongHashMap2.isEmpty();
      int int3 = tShortLongHashMap2.hashCode();
      assertEquals(0, int3);
      assertEquals(23, tShortLongHashMap2.capacity());
      
      long[] longArray1 = tShortLongHashMap1.values(longArray0);
      assertNotNull(longArray1);
      assertSame(longArray1, longArray0);
      
      TShortLongHashMap tShortLongHashMap3 = new TShortLongHashMap(int0);
      tShortLongHashMap3.getAutoCompactionFactor();
      boolean boolean0 = tShortLongHashMap3.isEmpty();
      assertEquals(true, boolean0);
  }

  @Test
  public void test13()  throws Throwable  {
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap();
      tShortLongHashMap0.isEmpty();
      int int0 = 0;
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(int0, int0);
      short short0 = (short)0;
      long long0 = 0L;
      String string0 = "";
      File file0 = new File(string0);
      // Undeclared exception!
      try {
        File.createTempFile(string0, string0, file0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Prefix string too short
         //
      }
  }

  @Test
  public void test14()  throws Throwable  {
      short[] shortArray0 = new short[1];
      short short0 = (short) (-996);
      shortArray0[0] = short0;
      long[] longArray0 = new long[10];
      longArray0[0] = (long) short0;
      longArray0[1] = (long) short0;
      longArray0[2] = (long) short0;
      longArray0[3] = (long) shortArray0[0];
      longArray0[4] = (long) shortArray0[0];
      longArray0[5] = (long) short0;
      longArray0[6] = (long) short0;
      longArray0[7] = (long) short0;
      longArray0[8] = (long) shortArray0[0];
      longArray0[9] = (long) short0;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(shortArray0, longArray0);
      long long0 = tShortLongHashMap0.remove(shortArray0[0]);
      assertEquals((-996L), long0);
      assertEquals(0, tShortLongHashMap0.size());
      
      short[] shortArray1 = new short[1];
      short short1 = (short) (-188);
      shortArray1[0] = short1;
      long[] longArray1 = new long[3];
      longArray1[0] = (long) short1;
      longArray1[1] = (long) shortArray1[0];
      longArray1[2] = (long) short1;
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(shortArray1, longArray1);
      short short2 = (short) (-1718);
      TShortLongHashMap tShortLongHashMap2 = new TShortLongHashMap();
      boolean boolean0 = tShortLongHashMap1.increment(short2);
      assertEquals(false, boolean0);
  }

  @Test
  public void test15()  throws Throwable  {
      short[] shortArray0 = new short[1];
      short short0 = (short)1;
      float float0 = 6.6360896E8F;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap((int) short0, float0);
      tShortLongHashMap0.ensureCapacity((int) short0);
      shortArray0[0] = short0;
      long[] longArray0 = new long[9];
      longArray0[0] = (long) shortArray0[0];
      longArray0[1] = (long) short0;
      longArray0[2] = (long) short0;
      float float1 = (-47.580383F);
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap((int) short0, float1);
      byte[] byteArray0 = tShortLongHashMap1._states;
      longArray0[3] = (long) short0;
      longArray0[4] = (long) shortArray0[0];
      longArray0[5] = (long) short0;
      tShortLongHashMap1.values();
      InputStream inputStream0 = null;
      longArray0[0] = (long) shortArray0[0];
      long long0 = 0L;
      longArray0[1] = long0;
      longArray0[2] = long0;
      longArray0[3] = (long) short0;
      longArray0[4] = long0;
      longArray0[5] = (long) short0;
      longArray0[6] = (long) shortArray0[0];
      long long1 = (-1593L);
      longArray0[7] = long1;
      longArray0[8] = long1;
      longArray0[0] = (long) shortArray0[0];
      longArray0[1] = (long) shortArray0[0];
      longArray0[2] = (long) short0;
      longArray0[3] = (long) shortArray0[0];
      longArray0[4] = (long) short0;
      longArray0[5] = (long) short0;
      longArray0[6] = (long) short0;
      longArray0[7] = (long) short0;
      longArray0[8] = (long) short0;
      ObjectInputStream objectInputStream0 = null;
      try {
        objectInputStream0 = new ObjectInputStream(inputStream0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test16()  throws Throwable  {
      int int0 = (-934);
      float float0 = 10.0F;
      short short0 = (short)0;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0, float0, short0, (long) int0);
      int int1 = tShortLongHashMap0.hashCode();
      assertEquals(0, int1);
      
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap();
      short short1 = (short) (-904);
      long[] longArray0 = new long[2];
      longArray0[0] = (long) short1;
      longArray0[1] = (long) short1;
      long[] longArray1 = tShortLongHashMap0.values(longArray0);
      assertNotNull(longArray1);
      assertSame(longArray1, longArray0);
      
      tShortLongHashMap1.containsKey(short1);
      assertEquals(23, tShortLongHashMap1.capacity());
  }

  @Test
  public void test17()  throws Throwable  {
      int int0 = 141;
      float float0 = (-1.0F);
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0, float0);
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap((TShortLongMap) tShortLongHashMap0);
      TShortLongHashMap tShortLongHashMap2 = new TShortLongHashMap();
      float float1 = 0.0F;
      tShortLongHashMap0.tempDisableAutoCompaction();
      tShortLongHashMap2.valueCollection();
      tShortLongHashMap2.setAutoCompactionFactor(float1);
      assertEquals(23, tShortLongHashMap2.capacity());
      
      byte[] byteArray0 = new byte[1];
      byte byte0 = (byte)0;
      byteArray0[0] = byte0;
      tShortLongHashMap2._states = byteArray0;
      int int1 = (-908);
      TShortLongHashMap tShortLongHashMap3 = new TShortLongHashMap(int1, float1);
      int int2 = (-758);
      tShortLongHashMap3.ensureCapacity(int2);
      tShortLongHashMap3.isEmpty();
      tShortLongHashMap3.iterator();
      TShortLongHashMap tShortLongHashMap4 = new TShortLongHashMap();
      tShortLongHashMap4.keySet();
      assertEquals(true, tShortLongHashMap4.isEmpty());
  }

  @Test
  public void test18()  throws Throwable  {
      int int0 = 169;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0);
      TLongFunction tLongFunction0 = null;
      tShortLongHashMap0.transformValues(tLongFunction0);
      byte[] byteArray0 = tShortLongHashMap0._states;
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap();
      int int1 = tShortLongHashMap0.hashCode();
      assertEquals(0, int1);
      assertEquals(359, tShortLongHashMap0.capacity());
  }
}
