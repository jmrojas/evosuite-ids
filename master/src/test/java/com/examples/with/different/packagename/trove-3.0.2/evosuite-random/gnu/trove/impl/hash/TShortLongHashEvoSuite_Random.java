/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.map.TShortLongMap;
import gnu.trove.map.hash.TShortLongHashMap;
import gnu.trove.procedure.TLongProcedure;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PipedInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TShortLongHashEvoSuite_Random {

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
//       short[] shortArray0 = new short[10];
//       short short0 = (short)1869;
//       shortArray0[0] = short0;
//       short short1 = (short)2;
//       shortArray0[1] = short1;
//       short short2 = (short)1;
//       shortArray0[2] = short2;
//       short short3 = (short)0;
//       shortArray0[3] = short3;
//       short short4 = (short)1335;
//       shortArray0[4] = short4;
//       short short5 = (short)122;
//       shortArray0[5] = short5;
//       short short6 = (short) (-150);
//       shortArray0[6] = short6;
//       short short7 = (short) (-1589);
//       shortArray0[7] = short7;
//       short short8 = (short) (-32768);
//       shortArray0[8] = short8;
//       short short9 = (short)31;
//       shortArray0[9] = short9;
//       long[] longArray0 = new long[1];
//       long long0 = 827L;
//       longArray0[0] = long0;
//       TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(shortArray0, longArray0);
//       tShortLongHashMap0.tempDisableAutoCompaction();
//       assertEquals(0L, tShortLongHashMap0.getNoEntryValue());
//       assertEquals(0, tShortLongHashMap0.getNoEntryKey());
//       assertEquals("{1869=827}", tShortLongHashMap0.toString());
//       
//       TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap();
//       tShortLongHashMap1.tempDisableAutoCompaction();
//       assertEquals(23, tShortLongHashMap1.capacity());
//       assertEquals(0, tShortLongHashMap1.getNoEntryKey());
//       assertEquals(0L, tShortLongHashMap1.getNoEntryValue());
//       assertFalse(tShortLongHashMap1.equals(tShortLongHashMap0));
//   }

//   @Test
//   public void test1()  throws Throwable  {
//       int int0 = 0;
//       short[] shortArray0 = new short[9];
//       short short0 = (short)0;
//       shortArray0[0] = short0;
//       short short1 = (short)1150;
//       shortArray0[1] = short1;
//       short short2 = (short)2411;
//       shortArray0[2] = short2;
//       short short3 = (short)465;
//       shortArray0[3] = short3;
//       short short4 = (short)758;
//       shortArray0[4] = short4;
//       short short5 = (short) (-863);
//       shortArray0[5] = short5;
//       short short6 = (short)1427;
//       shortArray0[6] = short6;
//       short short7 = (short)765;
//       shortArray0[7] = short7;
//       short short8 = (short)443;
//       shortArray0[8] = short8;
//       long[] longArray0 = new long[6];
//       longArray0[0] = (long) shortArray0[7];
//       longArray0[1] = (long) short0;
//       longArray0[2] = (long) short7;
//       longArray0[3] = (long) short1;
//       longArray0[4] = (long) short2;
//       longArray0[5] = (long) shortArray0[8];
//       TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(shortArray0, longArray0);
//       assertEquals("{758=2411, 2411=765, -863=443, 1150=0, 465=1150, 0=765}", tShortLongHashMap0.toString());
//       
//       TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(int0);
//       tShortLongHashMap1.keys();
//       tShortLongHashMap1.getAutoCompactionFactor();
//       long[] longArray1 = tShortLongHashMap1.values();
//       assertNotNull(longArray1);
//       
//       int int1 = 2045;
//       TShortLongHashMap tShortLongHashMap2 = new TShortLongHashMap();
//       tShortLongHashMap1.putAll((TShortLongMap) tShortLongHashMap2);
//       assertEquals(0L, tShortLongHashMap2.getNoEntryValue());
//       assertEquals(0, tShortLongHashMap2.getNoEntryKey());
//       
//       int int2 = 0;
//       TShortLongHashMap tShortLongHashMap3 = new TShortLongHashMap(int2);
//       TShortLongHashMap tShortLongHashMap4 = new TShortLongHashMap(int1);
//       short short9 = tShortLongHashMap1.getNoEntryKey();
//       boolean boolean0 = tShortLongHashMap4.contains(short9);
//       assertEquals(false, boolean0);
//       
//       tShortLongHashMap4.clear();
//       TShortLongHashMap tShortLongHashMap5 = new TShortLongHashMap((TShortLongMap) tShortLongHashMap1);
//       assertEquals(0L, tShortLongHashMap5.getNoEntryValue());
//   }

//   @Test
//   public void test2()  throws Throwable  {
//       int int0 = 0;
//       TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0, int0);
//       boolean boolean0 = false;
//       tShortLongHashMap0.keySet();
//       tShortLongHashMap0.reenableAutoCompaction(boolean0);
//       assertEquals(0, tShortLongHashMap0.getNoEntryKey());
//       assertEquals(0L, tShortLongHashMap0.getNoEntryValue());
//   }

  @Test
  public void test3()  throws Throwable  {
      int int0 = (-865);
      short short0 = (short) (-1);
      long long0 = 9223372036854775807L;
      int int1 = 1;
      float float0 = 6.6360896E8F;
      short short1 = (short) (-1);
      long long1 = 0L;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int1, float0, short1, long1);
      InputStream inputStream0 = null;
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
  public void test4()  throws Throwable  {
      int int0 = 1933;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0);
      tShortLongHashMap0.tempDisableAutoCompaction();
      long[] longArray0 = new long[6];
      longArray0[0] = (long) int0;
      longArray0[1] = (long) int0;
      longArray0[2] = (long) int0;
      longArray0[3] = (long) int0;
      longArray0[4] = (long) int0;
      longArray0[5] = (long) int0;
      tShortLongHashMap0.values(longArray0);
      int int1 = 1187;
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(int1);
      tShortLongHashMap0.tempDisableAutoCompaction();
      TShortLongHashMap tShortLongHashMap2 = new TShortLongHashMap((TShortLongMap) tShortLongHashMap1);
      tShortLongHashMap2.isEmpty();
      PipedInputStream pipedInputStream0 = new PipedInputStream();
      ObjectInputStream objectInputStream0 = null;
      try {
        objectInputStream0 = new ObjectInputStream((InputStream) pipedInputStream0);
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Pipe not connected
         //
      }
  }

  @Test
  public void test5()  throws Throwable  {
      int int0 = 14143;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap();
      String string0 = "";
      URI uRI0 = null;
      try {
        uRI0 = new URI(string0, string0, string0, int0, string0, string0, string0);
        fail("Expecting exception: URISyntaxException");
      
      } catch(URISyntaxException e) {
         //
         // Expected scheme name at index 0: ://@:14143?#
         //
      }
  }

//   @Test
//   public void test6()  throws Throwable  {
//       short[] shortArray0 = new short[8];
//       short short0 = (short)1851;
//       shortArray0[0] = short0;
//       short short1 = (short) (-1477);
//       shortArray0[1] = short1;
//       short short2 = (short)1051;
//       shortArray0[2] = short2;
//       short short3 = (short)456;
//       shortArray0[3] = short3;
//       short short4 = (short)55;
//       shortArray0[4] = short4;
//       short short5 = (short)12203;
//       shortArray0[5] = short5;
//       short short6 = (short)0;
//       shortArray0[6] = short6;
//       short short7 = (short)2081;
//       shortArray0[7] = short7;
//       long[] longArray0 = new long[6];
//       long long0 = (-436L);
//       longArray0[0] = long0;
//       longArray0[1] = (long) short1;
//       longArray0[2] = (long) shortArray0[5];
//       longArray0[3] = (long) short4;
//       longArray0[4] = (long) short2;
//       longArray0[5] = (long) shortArray0[5];
//       TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(shortArray0, longArray0);
//       float float0 = 0.0F;
//       tShortLongHashMap0.setAutoCompactionFactor(float0);
//       assertEquals("{1851=-436, 1051=12203, -1477=-1477, 456=55, 12203=12203, 55=1051}", tShortLongHashMap0.toString());
//       assertEquals(0, tShortLongHashMap0.getNoEntryKey());
//       assertEquals(0L, tShortLongHashMap0.getNoEntryValue());
//   }

  @Test
  public void test7()  throws Throwable  {
      short[] shortArray0 = new short[3];
      short short0 = (short)1070;
      shortArray0[0] = short0;
      short short1 = (short)1209;
      shortArray0[1] = short1;
      short short2 = (short) (-1871);
      shortArray0[2] = short2;
      long[] longArray0 = new long[9];
      longArray0[0] = (long) short0;
      longArray0[1] = (long) shortArray0[2];
      longArray0[2] = (long) shortArray0[1];
      longArray0[3] = (long) shortArray0[0];
      longArray0[4] = (long) shortArray0[0];
      longArray0[5] = (long) short2;
      longArray0[6] = (long) short0;
      longArray0[7] = (long) short0;
      longArray0[8] = (long) shortArray0[2];
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(shortArray0, longArray0);
      TLongProcedure tLongProcedure0 = null;
      // Undeclared exception!
      try {
        tShortLongHashMap0.forEachValue(tLongProcedure0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

//   @Test
//   public void test8()  throws Throwable  {
//       int int0 = 980;
//       float float0 = 1.0F;
//       short[] shortArray0 = new short[8];
//       short short0 = (short) (-1289);
//       shortArray0[0] = short0;
//       short short1 = (short)1767;
//       shortArray0[1] = short1;
//       short short2 = (short) (-1553);
//       shortArray0[2] = short2;
//       short short3 = (short)0;
//       shortArray0[3] = short3;
//       short short4 = (short)27121;
//       shortArray0[4] = short4;
//       short short5 = (short)0;
//       shortArray0[5] = short5;
//       short short6 = (short)1198;
//       shortArray0[6] = short6;
//       short short7 = (short)0;
//       shortArray0[7] = short7;
//       long[] longArray0 = new long[10];
//       longArray0[0] = (long) short5;
//       longArray0[1] = (long) int0;
//       longArray0[2] = (long) shortArray0[7];
//       longArray0[3] = (long) short0;
//       longArray0[4] = (long) shortArray0[7];
//       longArray0[5] = (long) shortArray0[3];
//       longArray0[6] = (long) shortArray0[0];
//       longArray0[7] = (long) shortArray0[3];
//       longArray0[8] = (long) short2;
//       longArray0[9] = (long) shortArray0[3];
//       TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(shortArray0, longArray0);
//       assertEquals(6, tShortLongHashMap0.size());
//       assertEquals(0, tShortLongHashMap0.getNoEntryKey());
//       assertEquals("{1767=980, -1553=0, -1289=0, 27121=0, 1198=-1289, 0=0}", tShortLongHashMap0.toString());
//       assertEquals(0L, tShortLongHashMap0.getNoEntryValue());
//       
//       short short8 = (short)0;
//       TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(int0, float0, short8, (long) int0);
//       short short9 = (short)0;
//       boolean boolean0 = tShortLongHashMap1.contains(short9);
//       assertEquals(0, tShortLongHashMap1.getNoEntryKey());
//       assertEquals(false, boolean0);
//       assertEquals(980L, tShortLongHashMap1.getNoEntryValue());
//   }

//   @Test
//   public void test9()  throws Throwable  {
//       int int0 = 1020;
//       TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0);
//       long[] longArray0 = tShortLongHashMap0.values();
//       assertNotNull(longArray0);
//       
//       TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(int0);
//       short short0 = (short)819;
//       boolean boolean0 = tShortLongHashMap1.adjustValue(short0, short0);
//       assertEquals(0, tShortLongHashMap1.getNoEntryKey());
//       assertEquals(0L, tShortLongHashMap1.getNoEntryValue());
//       assertEquals(false, boolean0);
//       
//       TShortLongHashMap tShortLongHashMap2 = new TShortLongHashMap();
//       int int1 = 0;
//       tShortLongHashMap2.keys();
//       tShortLongHashMap2.ensureCapacity(int1);
//       int int2 = (-1);
//       TShortLongHashMap tShortLongHashMap3 = new TShortLongHashMap(int2, int2);
//       tShortLongHashMap3.size();
//       assertEquals(0, tShortLongHashMap3.getNoEntryKey());
//       assertEquals(0L, tShortLongHashMap3.getNoEntryValue());
//       
//       int int3 = 0;
//       short short1 = (short)777;
//       long long0 = 1765L;
//       TShortLongHashMap tShortLongHashMap4 = new TShortLongHashMap(int3, (float) int0, short1, long0);
//       assertEquals(777, tShortLongHashMap4.getNoEntryKey());
//       assertEquals(1765L, tShortLongHashMap4.getNoEntryValue());
//       
//       float float0 = 265.61636F;
//       tShortLongHashMap2.setAutoCompactionFactor(float0);
//       assertEquals(0L, tShortLongHashMap2.getNoEntryValue());
//       assertEquals(23, tShortLongHashMap2.capacity());
//       assertEquals(0, tShortLongHashMap2.getNoEntryKey());
//   }

  @Test
  public void test10()  throws Throwable  {
      int int0 = (-271);
      short short0 = (short) (-1577);
      long long0 = 0L;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0, (float) int0, short0, long0);
      InputStream inputStream0 = null;
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

//   @Test
//   public void test11()  throws Throwable  {
//       short[] shortArray0 = new short[9];
//       short short0 = (short)407;
//       shortArray0[0] = short0;
//       short short1 = (short) (-1);
//       shortArray0[1] = short1;
//       short short2 = (short)1;
//       shortArray0[2] = short2;
//       short short3 = (short) (-485);
//       shortArray0[3] = short3;
//       short short4 = (short)245;
//       shortArray0[4] = short4;
//       short short5 = (short)1;
//       shortArray0[5] = short5;
//       short short6 = (short) (-300);
//       shortArray0[6] = short6;
//       short short7 = (short)0;
//       shortArray0[7] = short7;
//       short short8 = (short)1266;
//       shortArray0[8] = short8;
//       long[] longArray0 = new long[9];
//       longArray0[0] = (long) short4;
//       long long0 = 0L;
//       longArray0[1] = long0;
//       long long1 = 0L;
//       longArray0[2] = long1;
//       longArray0[3] = (long) short2;
//       longArray0[4] = (long) short2;
//       longArray0[5] = (long) short6;
//       longArray0[6] = (long) short5;
//       longArray0[7] = (long) short3;
//       longArray0[8] = (long) short8;
//       TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(shortArray0, longArray0);
//       TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap();
//       tShortLongHashMap0.putAll((TShortLongMap) tShortLongHashMap1);
//       assertEquals(0L, tShortLongHashMap0.getNoEntryValue());
//       assertEquals(0, tShortLongHashMap0.getNoEntryKey());
//       assertEquals("{1266=1266, 407=245, 245=1, -300=1, -1=0, -485=1, 1=-300, 0=-485}", tShortLongHashMap0.toString());
//       assertEquals(8, tShortLongHashMap0.size());
//   }

  @Test
  public void test12()  throws Throwable  {
      int int0 = (-729);
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap();
      tShortLongHashMap0.trimToSize();
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
  public void test13()  throws Throwable  {
      short[] shortArray0 = new short[1];
      short short0 = (short) (-21683);
      long[] longArray0 = new long[3];
      longArray0[0] = (long) short0;
      longArray0[1] = (long) short0;
      longArray0[2] = (long) short0;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(shortArray0, longArray0);
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap((TShortLongMap) tShortLongHashMap0);
      // Undeclared exception!
      try {
        tShortLongHashMap0.setAutoCompactionFactor((float) longArray0[1]);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Factor must be >= 0: -21683.0
         //
      }
  }

//   @Test
//   public void test14()  throws Throwable  {
//       short[] shortArray0 = new short[2];
//       short short0 = (short)1721;
//       shortArray0[0] = short0;
//       short short1 = (short) (-907);
//       shortArray0[1] = short1;
//       long[] longArray0 = new long[10];
//       longArray0[0] = (long) short0;
//       long long0 = 0L;
//       longArray0[1] = long0;
//       longArray0[2] = (long) shortArray0[0];
//       longArray0[3] = (long) shortArray0[1];
//       longArray0[4] = (long) shortArray0[1];
//       longArray0[5] = (long) short1;
//       longArray0[6] = (long) short0;
//       longArray0[7] = (long) shortArray0[0];
//       longArray0[8] = (long) short1;
//       longArray0[9] = (long) shortArray0[1];
//       TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(shortArray0, longArray0);
//       int int0 = tShortLongHashMap0.size();
//       assertEquals(0L, tShortLongHashMap0.getNoEntryValue());
//       assertEquals(0, tShortLongHashMap0.getNoEntryKey());
//       assertEquals("{-907=0, 1721=1721}", tShortLongHashMap0.toString());
//       assertEquals(2, int0);
//   }

//   @Test
//   public void test15()  throws Throwable  {
//       int int0 = 0;
//       TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap();
//       tShortLongHashMap0.iterator();
//       int int1 = (-711);
//       TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(int1);
//       byte[] byteArray0 = new byte[2];
//       byte byte0 = (byte)107;
//       byteArray0[0] = byte0;
//       byte byte1 = (byte)32;
//       byteArray0[1] = byte1;
//       tShortLongHashMap1._states = byteArray0;
//       TShortLongHashMap tShortLongHashMap2 = new TShortLongHashMap(int0);
//       short short0 = (short) (-1);
//       long long0 = 0L;
//       tShortLongHashMap2.iterator();
//       long long1 = 701L;
//       tShortLongHashMap2.adjustOrPutValue(short0, long0, long1);
//       long long2 = 0L;
//       tShortLongHashMap2.putIfAbsent(short0, long2);
//       tShortLongHashMap2.compact();
//       assertEquals(false, tShortLongHashMap2.isEmpty());
//       
//       TShortLongHashMap tShortLongHashMap3 = new TShortLongHashMap(int0);
//       TShortLongHashMap tShortLongHashMap4 = new TShortLongHashMap();
//       TShortLongHashMap tShortLongHashMap5 = new TShortLongHashMap((TShortLongMap) tShortLongHashMap3);
//       short short1 = (short) (-1255);
//       tShortLongHashMap5.contains(short1);
//       tShortLongHashMap5.trimToSize();
//       assertEquals(0, tShortLongHashMap3.getNoEntryKey());
//   }

  @Test
  public void test16()  throws Throwable  {
      int int0 = 79;
      int int1 = 464;
      short[] shortArray0 = new short[6];
      short short0 = (short)1886;
      shortArray0[0] = short0;
      short short1 = (short)17739;
      shortArray0[1] = short1;
      short short2 = (short) (-1815);
      shortArray0[2] = short2;
      short short3 = (short)0;
      shortArray0[3] = short3;
      short short4 = (short)0;
      shortArray0[4] = short4;
      short short5 = (short)22183;
      shortArray0[5] = short5;
      long[] longArray0 = new long[8];
      longArray0[0] = (long) shortArray0[4];
      longArray0[1] = (long) shortArray0[2];
      longArray0[2] = (long) shortArray0[2];
      longArray0[3] = (long) shortArray0[0];
      longArray0[4] = (long) shortArray0[4];
      longArray0[5] = (long) short3;
      longArray0[6] = (long) short3;
      longArray0[7] = (long) short3;
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(shortArray0, longArray0);
      assertEquals(5, tShortLongHashMap0.size());
      assertEquals("{1886=0, 22183=0, -1815=-1815, 17739=-1815, 0=0}", tShortLongHashMap0.toString());
      
      int int2 = 1;
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(int2);
      float float0 = (-116.144066F);
      // Undeclared exception!
      try {
        tShortLongHashMap1.setAutoCompactionFactor(float0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Factor must be >= 0: -116.144066
         //
      }
  }

  @Test
  public void test17()  throws Throwable  {
      int int0 = (-588);
      TShortLongHashMap tShortLongHashMap0 = new TShortLongHashMap(int0);
      short short0 = (short)1379;
      tShortLongHashMap0.remove(short0);
      int int1 = 1192;
      TShortLongHashMap tShortLongHashMap1 = new TShortLongHashMap(int1, int1);
      tShortLongHashMap1.iterator();
      tShortLongHashMap1.keys();
      tShortLongHashMap1.isEmpty();
      short short1 = (short)0;
      long long0 = 1L;
      long long1 = tShortLongHashMap1.putIfAbsent(short1, long0);
      assertEquals(false, tShortLongHashMap1.isEmpty());
      assertEquals(0L, long1);
  }
}
