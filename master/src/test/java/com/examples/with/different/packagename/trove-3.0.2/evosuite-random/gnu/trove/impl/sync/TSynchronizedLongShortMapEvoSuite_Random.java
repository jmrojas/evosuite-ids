/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.sync;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.impl.sync.TSynchronizedLongSet;
import gnu.trove.impl.sync.TSynchronizedLongShortMap;
import gnu.trove.map.TLongShortMap;
import gnu.trove.map.hash.TLongShortHashMap;
import gnu.trove.procedure.TShortProcedure;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TSynchronizedLongShortMapEvoSuite_Random {

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
      int int0 = (-1511);
      long[] longArray0 = new long[10];
      long long0 = (-1041L);
      longArray0[0] = long0;
      longArray0[1] = (long) int0;
      longArray0[2] = (long) int0;
      longArray0[3] = (long) int0;
      longArray0[4] = (long) int0;
      longArray0[5] = (long) int0;
      longArray0[6] = (long) int0;
      long long1 = 0L;
      longArray0[7] = long1;
      long long2 = 2023L;
      longArray0[8] = long2;
      longArray0[9] = (long) int0;
      short[] shortArray0 = new short[8];
      short short0 = (short)0;
      shortArray0[0] = short0;
      short short1 = (short)812;
      shortArray0[1] = short1;
      short short2 = (short)616;
      shortArray0[2] = short2;
      short short3 = (short) (-969);
      shortArray0[3] = short3;
      short short4 = (short)1028;
      shortArray0[4] = short4;
      short short5 = (short) (-1);
      shortArray0[5] = short5;
      short short6 = (short)1474;
      shortArray0[6] = short6;
      short short7 = (short)32767;
      shortArray0[7] = short7;
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap(longArray0, shortArray0);
      Object object0 = null;
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0, object0);
      short short8 = (short)32767;
      short short9 = (short) (-1464);
      // Undeclared exception!
      try {
        tSynchronizedLongShortMap0.adjustOrPutValue(longArray0[4], short8, short9);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test1()  throws Throwable  {
      long[] longArray0 = new long[5];
      long long0 = 0L;
      longArray0[0] = long0;
      long long1 = 0L;
      longArray0[1] = long1;
      long long2 = 0L;
      longArray0[2] = long2;
      long long3 = 0L;
      longArray0[3] = long3;
      long long4 = 0L;
      longArray0[4] = long4;
      short[] shortArray0 = new short[6];
      short short0 = (short)0;
      shortArray0[0] = short0;
      short short1 = (short) (-249);
      shortArray0[1] = short1;
      short short2 = (short)1618;
      shortArray0[2] = short2;
      short short3 = (short)0;
      shortArray0[3] = short3;
      short short4 = (short) (-466);
      shortArray0[4] = short4;
      short short5 = (short)0;
      shortArray0[5] = short5;
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap(longArray0, shortArray0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0);
      TSynchronizedLongSet tSynchronizedLongSet0 = (TSynchronizedLongSet)tSynchronizedLongShortMap0.keySet();
      assertEquals("{0}", tSynchronizedLongSet0.toString());
  }

  @Test
  public void test2()  throws Throwable  {
      int int0 = (-500);
      long long0 = (-9223372036854775808L);
      short short0 = (short) (-1693);
      long[] longArray0 = new long[7];
      longArray0[0] = long0;
      longArray0[1] = long0;
      longArray0[2] = (long) int0;
      longArray0[3] = (long) short0;
      longArray0[4] = (long) short0;
      longArray0[5] = long0;
      longArray0[6] = (long) short0;
      short[] shortArray0 = new short[2];
      shortArray0[0] = short0;
      shortArray0[1] = short0;
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap(longArray0, shortArray0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0);
      TLongShortHashMap tLongShortHashMap1 = new TLongShortHashMap(int0, (float) int0, long0, short0);
      Object object0 = null;
      TSynchronizedLongShortMap tSynchronizedLongShortMap1 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap1, object0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap2 = new TSynchronizedLongShortMap((TLongShortMap) tSynchronizedLongShortMap1);
      TLongShortHashMap tLongShortHashMap2 = null;
      try {
        tLongShortHashMap2 = new TLongShortHashMap((TLongShortMap) tSynchronizedLongShortMap2);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test3()  throws Throwable  {
      int int0 = 175;
      short short0 = (short) (-1926);
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap(int0, (float) int0, (long) int0, short0);
      TLongShortHashMap tLongShortHashMap1 = new TLongShortHashMap((TLongShortMap) tLongShortHashMap0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap1);
      tSynchronizedLongShortMap0.getNoEntryValue();
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
  public void test4()  throws Throwable  {
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap();
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0);
      String string0 = "Factor must be >= 0: ";
      File file0 = new File(string0, string0);
      String string1 = file0.getName();
      TSynchronizedLongShortMap tSynchronizedLongShortMap1 = new TSynchronizedLongShortMap((TLongShortMap) tSynchronizedLongShortMap0, (Object) string1);
      int int0 = (-1443);
      ByteArrayOutputStream byteArrayOutputStream0 = null;
      try {
        byteArrayOutputStream0 = new ByteArrayOutputStream(int0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Negative initial size: -1443
         //
      }
  }

  @Test
  public void test5()  throws Throwable  {
      long[] longArray0 = new long[7];
      long long0 = 424L;
      longArray0[0] = long0;
      long long1 = 0L;
      int int0 = 0;
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap(int0);
      short short0 = (short)0;
      String string0 = Short.toString(short0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0, (Object) string0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap1 = new TSynchronizedLongShortMap((TLongShortMap) tSynchronizedLongShortMap0);
      longArray0[1] = long1;
      long long2 = (-6L);
      longArray0[2] = long2;
      long long3 = 9223372036854775807L;
      longArray0[3] = long3;
      long long4 = 431L;
      longArray0[4] = long4;
      int int1 = 37;
      TLongShortHashMap tLongShortHashMap1 = new TLongShortHashMap(int1);
      String string1 = File.separator;
      TSynchronizedLongShortMap tSynchronizedLongShortMap2 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap1, (Object) string1);
      long long5 = 181L;
      longArray0[5] = long5;
      long long6 = (-2017L);
      longArray0[6] = long6;
      short[] shortArray0 = new short[1];
      short short1 = (short) (-588);
      shortArray0[0] = short1;
      TLongShortHashMap tLongShortHashMap2 = new TLongShortHashMap(longArray0, shortArray0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap3 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap2);
      short[] shortArray1 = tSynchronizedLongShortMap3.values(shortArray0);
      assertSame(shortArray1, shortArray0);
  }

  @Test
  public void test6()  throws Throwable  {
      long[] longArray0 = new long[10];
      long long0 = (-1195L);
      longArray0[0] = long0;
      long long1 = 0L;
      longArray0[1] = long1;
      long long2 = 0L;
      longArray0[2] = long2;
      long long3 = (-1644L);
      longArray0[3] = long3;
      long long4 = (-566L);
      longArray0[4] = long4;
      long long5 = (-1798L);
      longArray0[5] = long5;
      long long6 = (-1918L);
      longArray0[6] = long6;
      long long7 = (-25L);
      longArray0[7] = long7;
      long long8 = 1788L;
      longArray0[8] = long8;
      long long9 = (-9223372036854775808L);
      longArray0[9] = long9;
      short[] shortArray0 = new short[4];
      short short0 = (short)32647;
      shortArray0[0] = short0;
      short short1 = (short)1;
      shortArray0[1] = short1;
      short short2 = (short)0;
      shortArray0[2] = short2;
      short short3 = (short)849;
      shortArray0[3] = short3;
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap(longArray0, shortArray0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0);
      boolean boolean0 = tSynchronizedLongShortMap0.containsValue(short1);
      assertEquals(false, boolean0);
  }

  @Test
  public void test7()  throws Throwable  {
      int int0 = (-797);
      long long0 = 1949L;
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap(int0, int0);
      TLongShortHashMap tLongShortHashMap1 = new TLongShortHashMap((TLongShortMap) tLongShortHashMap0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap1);
      TSynchronizedLongShortMap tSynchronizedLongShortMap1 = new TSynchronizedLongShortMap((TLongShortMap) tSynchronizedLongShortMap0);
      short short0 = (short) (-858);
      TLongShortHashMap tLongShortHashMap2 = new TLongShortHashMap(int0, (float) int0, long0, short0);
      String string0 = tLongShortHashMap2.toString();
      TSynchronizedLongShortMap tSynchronizedLongShortMap2 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap2, (Object) string0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap3 = new TSynchronizedLongShortMap((TLongShortMap) tSynchronizedLongShortMap2);
      long[] longArray0 = tSynchronizedLongShortMap3.keys(tLongShortHashMap2._set);
      assertNotNull(longArray0);
      
      boolean boolean0 = tSynchronizedLongShortMap3.isEmpty();
      assertEquals(true, boolean0);
  }

  @Test
  public void test8()  throws Throwable  {
      TLongShortMap tLongShortMap0 = null;
      String string0 = "}";
      // Undeclared exception!
      try {
        URI.create(string0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal character in path at index 0: }
         //
      }
  }

  @Test
  public void test9()  throws Throwable  {
      long[] longArray0 = new long[10];
      long long0 = 0L;
      longArray0[0] = long0;
      long long1 = 445L;
      longArray0[1] = long1;
      long long2 = 0L;
      longArray0[2] = long2;
      long long3 = 181L;
      longArray0[3] = long3;
      long long4 = (-1859L);
      longArray0[4] = long4;
      long long5 = (-1849L);
      longArray0[5] = long5;
      long long6 = (-1L);
      longArray0[6] = long6;
      long long7 = 796L;
      longArray0[7] = long7;
      long long8 = 119L;
      longArray0[8] = long8;
      long long9 = 458L;
      longArray0[9] = long9;
      short[] shortArray0 = new short[5];
      short short0 = (short)0;
      shortArray0[0] = short0;
      short short1 = (short) (-1873);
      shortArray0[1] = short1;
      short short2 = (short)557;
      shortArray0[2] = short2;
      short short3 = (short)811;
      shortArray0[3] = short3;
      short short4 = (short) (-830);
      shortArray0[4] = short4;
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap(longArray0, shortArray0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0);
      short short5 = tSynchronizedLongShortMap0.get((long) shortArray0[3]);
      assertFalse(short5 == short4);
  }

  @Test
  public void test10()  throws Throwable  {
      long[] longArray0 = new long[10];
      long long0 = (-1255L);
      longArray0[0] = long0;
      long long1 = 0L;
      longArray0[1] = long1;
      long long2 = 1631L;
      longArray0[2] = long2;
      long long3 = 0L;
      longArray0[3] = long3;
      long long4 = 0L;
      longArray0[4] = long4;
      long long5 = 82L;
      longArray0[5] = long5;
      long long6 = 0L;
      longArray0[6] = long6;
      long long7 = 1992L;
      longArray0[7] = long7;
      long long8 = 763L;
      longArray0[8] = long8;
      long long9 = 9223372036854775807L;
      longArray0[9] = long9;
      short[] shortArray0 = new short[6];
      short short0 = (short)0;
      shortArray0[0] = short0;
      short short1 = (short)0;
      shortArray0[1] = short1;
      short short2 = (short) (-1476);
      shortArray0[2] = short2;
      short short3 = (short) (-1);
      shortArray0[3] = short3;
      short short4 = (short) (-530);
      shortArray0[4] = short4;
      short short5 = (short) (-4219);
      shortArray0[5] = short5;
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap(longArray0, shortArray0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0);
      boolean boolean0 = tSynchronizedLongShortMap0.containsValue(shortArray0[3]);
      assertEquals(false, boolean0);
  }

  @Test
  public void test11()  throws Throwable  {
      int int0 = (-1615);
      float float0 = 0.11376512F;
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap(int0);
      long long0 = 0L;
      short short0 = (short)1;
      tLongShortHashMap0.putIfAbsent(long0, short0);
      Object object0 = null;
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0, object0);
      TShortProcedure tShortProcedure0 = null;
      // Undeclared exception!
      try {
        tSynchronizedLongShortMap0.forEachValue(tShortProcedure0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test12()  throws Throwable  {
      long[] longArray0 = new long[9];
      long long0 = 0L;
      longArray0[0] = long0;
      long long1 = (-115L);
      longArray0[1] = long1;
      long long2 = 1704L;
      longArray0[2] = long2;
      long long3 = 9223372036854775807L;
      longArray0[3] = long3;
      TLongShortMap tLongShortMap0 = null;
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = null;
      try {
        tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap(tLongShortMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test13()  throws Throwable  {
      TLongShortMap tLongShortMap0 = null;
      Locale locale0 = Locale.KOREA;
      long long0 = 335L;
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = null;
      try {
        tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap(tLongShortMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test14()  throws Throwable  {
      int int0 = 494;
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap(int0, int0);
      ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream(int0);
      String string0 = byteArrayOutputStream0.toString();
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0, (Object) string0);
      short[] shortArray0 = tSynchronizedLongShortMap0.values();
      assertNotNull(shortArray0);
  }

  @Test
  public void test15()  throws Throwable  {
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap();
      Locale locale0 = Locale.KOREAN;
      String string0 = locale0.getDisplayLanguage();
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0, (Object) string0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap1 = new TSynchronizedLongShortMap((TLongShortMap) tSynchronizedLongShortMap0);
      TSynchronizedLongSet tSynchronizedLongSet0 = (TSynchronizedLongSet)tSynchronizedLongShortMap1.keySet();
      assertNotNull(tSynchronizedLongSet0);
      
      int int0 = 1873;
      TLongShortHashMap tLongShortHashMap1 = new TLongShortHashMap(int0, int0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap2 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap1);
      float float0 = 0.0F;
      tLongShortHashMap1.getNoEntryValue();
      TLongShortHashMap tLongShortHashMap2 = new TLongShortHashMap(int0, float0);
      tSynchronizedLongShortMap2.isEmpty();
      short short0 = (short) (-172);
      tSynchronizedLongShortMap2.putIfAbsent((long) int0, short0);
      tSynchronizedLongShortMap2.getNoEntryKey();
      long long0 = (-97L);
      tLongShortHashMap1.increment(long0);
      TLongShortHashMap tLongShortHashMap3 = null;
      try {
        tLongShortHashMap3 = new TLongShortHashMap((TLongShortMap) tLongShortHashMap2);
        fail("Expecting exception: OutOfMemoryError");
      
      } catch(OutOfMemoryError e) {
         //
         // Java heap space
         //
      }
  }

  @Test
  public void test16()  throws Throwable  {
      long[] longArray0 = new long[6];
      long long0 = 0L;
      longArray0[0] = long0;
      long long1 = (-1L);
      longArray0[1] = long1;
      long long2 = (-1264L);
      longArray0[2] = long2;
      long long3 = 9223372036854775807L;
      longArray0[3] = long3;
      long long4 = 9223372036854775807L;
      longArray0[4] = long4;
      long long5 = 0L;
      longArray0[5] = long5;
      short[] shortArray0 = new short[8];
      short short0 = (short)0;
      shortArray0[0] = short0;
      short short1 = (short)921;
      shortArray0[1] = short1;
      short short2 = (short)69;
      shortArray0[2] = short2;
      short short3 = (short)1382;
      shortArray0[3] = short3;
      short short4 = (short)1483;
      shortArray0[4] = short4;
      short short5 = (short)0;
      shortArray0[5] = short5;
      short short6 = (short)1;
      shortArray0[6] = short6;
      short short7 = (short)0;
      shortArray0[7] = short7;
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap(longArray0, shortArray0);
      Locale locale0 = Locale.TRADITIONAL_CHINESE;
      Locale locale1 = Locale.JAPAN;
      String string0 = locale0.getDisplayName(locale1);
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0, (Object) string0);
      short[] shortArray1 = tSynchronizedLongShortMap0.values();
      assertFalse(shortArray0.equals(shortArray1));
  }

  @Test
  public void test17()  throws Throwable  {
      TLongShortHashMap tLongShortHashMap0 = new TLongShortHashMap();
      long long0 = 1L;
      String string0 = Long.toString(long0);
      TSynchronizedLongShortMap tSynchronizedLongShortMap0 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0, (Object) string0);
      long long1 = 0L;
      short short0 = tSynchronizedLongShortMap0.get(long1);
      long long2 = 0L;
      short short1 = (short)0;
      tSynchronizedLongShortMap0.adjustOrPutValue(long2, short1, short0);
      Object object0 = null;
      tSynchronizedLongShortMap0.equals(object0);
      HashMap<Object, Object> hashMap0 = new HashMap<Object, Object>();
      String string1 = hashMap0.toString();
      TSynchronizedLongShortMap tSynchronizedLongShortMap1 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0, (Object) string1);
      long long3 = 1013L;
      short short2 = (short) (-1474);
      tSynchronizedLongShortMap1.adjustValue(long3, short2);
      TSynchronizedLongShortMap tSynchronizedLongShortMap2 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0, (Object) string0);
      tSynchronizedLongShortMap2.putAll((TLongShortMap) tLongShortHashMap0);
      tLongShortHashMap0.size();
      Object object1 = null;
      TSynchronizedLongShortMap tSynchronizedLongShortMap3 = new TSynchronizedLongShortMap((TLongShortMap) tLongShortHashMap0, object1);
      long long4 = (-804L);
      short short3 = (short)1755;
      // Undeclared exception!
      try {
        tSynchronizedLongShortMap3.putIfAbsent(long4, short3);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }
}
