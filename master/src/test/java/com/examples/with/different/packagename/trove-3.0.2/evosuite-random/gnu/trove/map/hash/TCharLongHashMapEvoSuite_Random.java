/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.map.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TLongFunction;
import gnu.trove.map.TCharLongMap;
import gnu.trove.map.hash.TCharLongHashMap;
import gnu.trove.procedure.TCharProcedure;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.AbstractMap;
import java.util.Locale;
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

public class TCharLongHashMapEvoSuite_Random {

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
      int int0 = 492;
      int int1 = 1142;
      char[] charArray0 = new char[10];
      char char0 = '_';
      charArray0[0] = char0;
      char char1 = 'e';
      charArray0[1] = char1;
      char char2 = '3';
      charArray0[2] = char2;
      char char3 = ':';
      charArray0[3] = char3;
      char char4 = ']';
      charArray0[4] = char4;
      char char5 = '!';
      charArray0[5] = char5;
      char char6 = 'e';
      charArray0[6] = char6;
      char char7 = '1';
      charArray0[7] = char7;
      char char8 = '4';
      charArray0[8] = char8;
      char char9 = ']';
      charArray0[9] = char9;
      long[] longArray0 = new long[4];
      longArray0[0] = (long) charArray0[8];
      longArray0[1] = (long) charArray0[1];
      longArray0[2] = (long) char7;
      longArray0[3] = (long) charArray0[4];
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(charArray0, longArray0);
      long long0 = 0L;
      tCharLongHashMap0.put(charArray0[8], long0);
      assertEquals(5, tCharLongHashMap0.size());
      
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap(int1, int1);
      float float0 = (-137.4663F);
      TCharLongHashMap tCharLongHashMap2 = new TCharLongHashMap(int0, float0);
      char char10 = 'P';
      long long1 = 0L;
      long long2 = tCharLongHashMap2.putIfAbsent(char10, long1);
      assertEquals(0L, long2);
  }

  @Test
  public void test1()  throws Throwable  {
      char[] charArray0 = new char[3];
      char char0 = 't';
      charArray0[0] = char0;
      char char1 = '$';
      charArray0[1] = char1;
      char char2 = 'e';
      charArray0[2] = char2;
      long[] longArray0 = new long[5];
      longArray0[0] = (long) charArray0[0];
      longArray0[1] = (long) charArray0[0];
      longArray0[2] = (long) char1;
      longArray0[3] = (long) char2;
      longArray0[4] = (long) char2;
      int int0 = 0;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int0);
      tCharLongHashMap0.containsValue(longArray0[0]);
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap(charArray0, longArray0);
      TCharLongHashMap tCharLongHashMap2 = new TCharLongHashMap((TCharLongMap) tCharLongHashMap1);
      tCharLongHashMap2.isEmpty();
      int int1 = 86;
      TCharLongHashMap tCharLongHashMap3 = new TCharLongHashMap(int1);
      long[] longArray1 = tCharLongHashMap3.values();
      assertNotNull(longArray1);
      
      TCharProcedure tCharProcedure0 = null;
      tCharLongHashMap3.forEachKey(tCharProcedure0);
      tCharLongHashMap3.forEach(tCharProcedure0);
      TCharLongHashMap tCharLongHashMap4 = new TCharLongHashMap();
      tCharLongHashMap4.keySet();
      char char3 = '}';
      tCharLongHashMap4.putIfAbsent(char3, char3);
      TCharLongHashMap tCharLongHashMap5 = new TCharLongHashMap((int) char3);
      tCharLongHashMap4.keySet();
      char char4 = '\"';
      long long0 = tCharLongHashMap4.get(char4);
      assertEquals(1, tCharLongHashMap4.size());
      assertEquals(0L, long0);
  }

  @Test
  public void test2()  throws Throwable  {
      int int0 = (-61);
      char[] charArray0 = new char[8];
      char char0 = 'L';
      charArray0[0] = char0;
      char char1 = 'b';
      charArray0[1] = char1;
      char char2 = '>';
      charArray0[2] = char2;
      char char3 = 'i';
      charArray0[3] = char3;
      char char4 = ']';
      charArray0[4] = char4;
      char char5 = 'U';
      charArray0[5] = char5;
      char char6 = '4';
      charArray0[6] = char6;
      char char7 = 'W';
      charArray0[7] = char7;
      long[] longArray0 = new long[2];
      longArray0[0] = (long) charArray0[1];
      longArray0[1] = (long) int0;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(charArray0, longArray0);
      boolean boolean0 = true;
      tCharLongHashMap0.reenableAutoCompaction(boolean0);
      assertEquals("{b=-61, L=98}", tCharLongHashMap0.toString());
      
      int int1 = 1478;
      int int2 = 0;
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap(int2);
      tCharLongHashMap1.clear();
      TCharLongHashMap tCharLongHashMap2 = new TCharLongHashMap(int1, int1);
      float float0 = 378.111F;
      TCharLongHashMap tCharLongHashMap3 = new TCharLongHashMap(int0, float0);
      char[] charArray1 = new char[4];
      char char8 = '@';
      charArray1[0] = char8;
      charArray1[0] = char8;
      charArray1[1] = char8;
      tCharLongHashMap2.contains(charArray1[0]);
      charArray1[2] = char8;
      tCharLongHashMap2._states = tCharLongHashMap3._states;
      charArray1[3] = char8;
      char char9 = 'n';
      charArray1[1] = char9;
      byte[] byteArray0 = tCharLongHashMap3._states;
      tCharLongHashMap3.clear();
      char char10 = 'W';
      charArray1[2] = char10;
      char char11 = 'i';
      charArray1[3] = char11;
      char[] charArray2 = tCharLongHashMap3.keys(charArray1);
      assertNotNull(charArray2);
      
      long long0 = tCharLongHashMap3.remove(charArray1[2]);
      assertSame(charArray1, charArray2);
      assertEquals(0, tCharLongHashMap3.size());
      assertEquals(0L, long0);
      assertFalse(tCharLongHashMap3.equals(tCharLongHashMap0));
  }

  @Test
  public void test3()  throws Throwable  {
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap();
      char char0 = '<';
      long long0 = tCharLongHashMap0.put(char0, char0);
      assertEquals("{<=60}", tCharLongHashMap0.toString());
      assertEquals(0L, long0);
  }

  @Test
  public void test4()  throws Throwable  {
      int int0 = 0;
      float float0 = 1.0F;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int0, float0);
      long[] longArray0 = new long[8];
      longArray0[0] = (long) int0;
      longArray0[1] = (long) int0;
      longArray0[2] = (long) int0;
      longArray0[3] = (long) int0;
      longArray0[4] = (long) int0;
      longArray0[5] = (long) int0;
      longArray0[6] = (long) int0;
      longArray0[7] = (long) int0;
      long[] longArray1 = tCharLongHashMap0.values(longArray0);
      assertSame(longArray0, longArray1);
      assertNotNull(longArray1);
  }

  @Test
  public void test5()  throws Throwable  {
      int int0 = (-1);
      float float0 = (-1412.366F);
      char[] charArray0 = new char[9];
      char char0 = '/';
      charArray0[0] = char0;
      char char1 = 'N';
      charArray0[1] = char1;
      char char2 = 'b';
      charArray0[2] = char2;
      char char3 = '2';
      charArray0[3] = char3;
      char char4 = '(';
      charArray0[4] = char4;
      char char5 = 'Z';
      charArray0[5] = char5;
      char char6 = 'G';
      charArray0[6] = char6;
      char char7 = '/';
      charArray0[7] = char7;
      char char8 = '-';
      charArray0[8] = char8;
      long[] longArray0 = new long[8];
      longArray0[0] = (long) charArray0[8];
      longArray0[1] = (long) charArray0[3];
      longArray0[2] = (long) charArray0[6];
      longArray0[3] = (long) charArray0[4];
      longArray0[4] = (long) char8;
      longArray0[5] = (long) charArray0[1];
      longArray0[6] = (long) charArray0[0];
      long long0 = (-1L);
      longArray0[7] = long0;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(charArray0, longArray0);
      tCharLongHashMap0.isEmpty();
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap(int0, float0);
      char char9 = 'N';
      tCharLongHashMap1.adjustOrPutValue(char9, (long) int0, (long) char9);
      assertEquals("{N=78}", tCharLongHashMap1.toString());
      
      int int1 = (-1903);
      TCharLongHashMap tCharLongHashMap2 = new TCharLongHashMap(int1);
      tCharLongHashMap2.keySet();
      TCharLongHashMap tCharLongHashMap3 = new TCharLongHashMap();
      tCharLongHashMap3.getNoEntryValue();
      char char10 = 'Y';
      tCharLongHashMap3.contains(char10);
      TLongFunction tLongFunction0 = null;
      tCharLongHashMap3.getNoEntryValue();
      tCharLongHashMap3.transformValues(tLongFunction0);
      assertFalse(tCharLongHashMap3.equals(tCharLongHashMap1));
  }

  @Test
  public void test6()  throws Throwable  {
      char[] charArray0 = new char[6];
      char char0 = 'A';
      char char1 = '0';
      charArray0[0] = char1;
      charArray0[1] = char0;
      charArray0[2] = char0;
      charArray0[3] = char1;
      charArray0[4] = char1;
      charArray0[5] = char0;
      charArray0[0] = char0;
      char char2 = '1';
      charArray0[1] = char2;
      char char3 = '6';
      charArray0[2] = char3;
      char char4 = 'W';
      charArray0[3] = char4;
      char char5 = 't';
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap();
      long long0 = 1020L;
      boolean boolean0 = tCharLongHashMap0.adjustValue(char3, long0);
      assertEquals(false, boolean0);
      
      String string0 = tCharLongHashMap0.toString();
      assertNotNull(string0);
      
      charArray0[4] = char5;
      char char6 = '}';
      charArray0[5] = char6;
      long[] longArray0 = new long[6];
      tCharLongHashMap0.getAutoCompactionFactor();
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap();
      assertEquals(23, tCharLongHashMap1.capacity());
      
      long long1 = 0L;
      longArray0[0] = long1;
      longArray0[1] = (long) charArray0[4];
      longArray0[2] = (long) charArray0[2];
      longArray0[3] = (long) charArray0[3];
      longArray0[4] = (long) charArray0[1];
      longArray0[5] = (long) charArray0[4];
      TCharLongHashMap tCharLongHashMap2 = new TCharLongHashMap(charArray0, longArray0);
      tCharLongHashMap2.getNoEntryValue();
      assertEquals(false, tCharLongHashMap2.isEmpty());
      assertEquals("{1=116, A=0, t=49, }=116, 6=54, W=87}", tCharLongHashMap2.toString());
      assertFalse(tCharLongHashMap2.equals(tCharLongHashMap1));
  }

  @Test
  public void test7()  throws Throwable  {
      int int0 = (-1);
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap();
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap((TCharLongMap) tCharLongHashMap0);
      tCharLongHashMap1.trimToSize();
      char char0 = '0';
      long long0 = (-1L);
      TCharLongHashMap tCharLongHashMap2 = new TCharLongHashMap(int0, (float) int0, char0, long0);
      assertNotSame(tCharLongHashMap2, tCharLongHashMap0);
  }

  @Test
  public void test8()  throws Throwable  {
      int int0 = 816;
      float float0 = 704.40814F;
      int int1 = (-700);
      char char0 = 'E';
      long long0 = (-1756L);
      float float1 = 45.573822F;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int1, float1, char0, (long) int0);
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap((TCharLongMap) tCharLongHashMap0);
      tCharLongHashMap1.values();
      TCharLongHashMap tCharLongHashMap2 = new TCharLongHashMap(int1, (float) int0, char0, long0);
      tCharLongHashMap2.size();
      char char1 = '5';
      long long1 = 1L;
      TCharLongHashMap tCharLongHashMap3 = new TCharLongHashMap(int0, float0, char1, long1);
      String string0 = "";
      AbstractMap.SimpleImmutableEntry<Integer, String> abstractMap_SimpleImmutableEntry0 = new AbstractMap.SimpleImmutableEntry<Integer, String>((Integer) int1, string0);
      AbstractMap.SimpleImmutableEntry<Object, Object> abstractMap_SimpleImmutableEntry1 = new AbstractMap.SimpleImmutableEntry<Object, Object>((Map.Entry<?, ?>) abstractMap_SimpleImmutableEntry0);
      abstractMap_SimpleImmutableEntry1.hashCode();
      String string1 = "x3{t]!33";
      URI uRI0 = null;
      try {
        uRI0 = new URI(string1, string1, string1, string1);
        fail("Expecting exception: URISyntaxException");
      
      } catch(URISyntaxException e) {
         //
         // Relative path in absolute URI: x3{t]!33://x3{t]!33x3%7Bt%5D!33#x3%7Bt]!33
         //
      }
  }

  @Test
  public void test9()  throws Throwable  {
    Future<?> future = executor.submit(new Runnable(){ 
            public void run() { 
        try {
          int int0 = (-748);
          float float0 = (-1348.6876F);
          char char0 = 'P';
          TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int0, float0, char0, (long) char0);
          long[] longArray0 = new long[6];
          TCharProcedure tCharProcedure0 = null;
          tCharLongHashMap0.forEach(tCharProcedure0);
          longArray0[0] = (long) int0;
          longArray0[1] = (long) int0;
          longArray0[2] = (long) int0;
          longArray0[3] = (long) char0;
          longArray0[4] = (long) int0;
          longArray0[5] = (long) int0;
          tCharLongHashMap0.values(longArray0);
          String string0 = "iyaE}<Q\"MzEP_4*k";
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
  public void test10()  throws Throwable  {
      TCharLongMap tCharLongMap0 = null;
      TCharLongHashMap tCharLongHashMap0 = null;
      try {
        tCharLongHashMap0 = new TCharLongHashMap(tCharLongMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test11()  throws Throwable  {
      int int0 = 0;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int0);
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap((TCharLongMap) tCharLongHashMap0);
      char char0 = 's';
      long long0 = (-1480L);
      long long1 = tCharLongHashMap0.putIfAbsent(char0, long0);
      long[] longArray0 = new long[7];
      longArray0[0] = (long) int0;
      longArray0[1] = (long) int0;
      longArray0[2] = long0;
      longArray0[3] = (long) char0;
      longArray0[4] = long1;
      longArray0[5] = (long) char0;
      longArray0[6] = long0;
      TCharLongHashMap tCharLongHashMap2 = new TCharLongHashMap(tCharLongHashMap1._set, longArray0);
      tCharLongHashMap1.size();
      assertEquals(false, tCharLongHashMap0.isEmpty());
      assertEquals(23, tCharLongHashMap1.capacity());
  }

  @Test
  public void test12()  throws Throwable  {
      int int0 = 0;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int0);
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap((TCharLongMap) tCharLongHashMap0);
      Locale locale0 = Locale.TAIWAN;
      String string0 = locale0.getLanguage();
      tCharLongHashMap1.equals((Object) string0);
      tCharLongHashMap1.iterator();
      char char0 = 'Z';
      long long0 = 1L;
      long long1 = 1007L;
      tCharLongHashMap1.adjustOrPutValue(char0, long0, long1);
      int int1 = 0;
      TCharLongHashMap tCharLongHashMap2 = new TCharLongHashMap((TCharLongMap) tCharLongHashMap1);
      TCharLongHashMap tCharLongHashMap3 = new TCharLongHashMap(int1);
      String string1 = "BnlFet&pB{9ke";
      tCharLongHashMap3.get(char0);
      int int2 = 180;
      try {
        Long.valueOf(string1, int2);
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // radix 180 greater than Character.MAX_RADIX
         //
      }
  }

  @Test
  public void test13()  throws Throwable  {
      char[] charArray0 = new char[7];
      char char0 = '!';
      charArray0[0] = char0;
      char char1 = '/';
      charArray0[1] = char1;
      char char2 = ')';
      charArray0[2] = char2;
      char char3 = '*';
      charArray0[3] = char3;
      char char4 = 'O';
      charArray0[4] = char4;
      char char5 = 'U';
      charArray0[5] = char5;
      char char6 = 't';
      charArray0[6] = char6;
      long[] longArray0 = new long[3];
      longArray0[0] = (long) char0;
      longArray0[1] = (long) char1;
      longArray0[2] = (long) char0;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(charArray0, longArray0);
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap((TCharLongMap) tCharLongHashMap0);
      TCharLongHashMap tCharLongHashMap2 = new TCharLongHashMap((TCharLongMap) tCharLongHashMap1);
      char char7 = 'R';
      String string0 = Character.toString(char7);
      boolean boolean0 = tCharLongHashMap2.equals((Object) string0);
      assertEquals(23, tCharLongHashMap1.capacity());
      assertEquals(3, tCharLongHashMap1.size());
      assertTrue(tCharLongHashMap1.equals(tCharLongHashMap2));
      assertEquals("{)=33, !=33, /=47}", tCharLongHashMap2.toString());
      assertEquals(0L, tCharLongHashMap1.getNoEntryValue());
      
      int int0 = 89834777;
      float float0 = 0.0F;
      char char8 = '-';
      TCharLongHashMap tCharLongHashMap3 = new TCharLongHashMap();
      int int1 = tCharLongHashMap3.hashCode();
      assertEquals(0, int1);
      
      boolean boolean1 = tCharLongHashMap3.adjustValue(char8, char8);
      assertTrue(boolean1 == boolean0);
      
      TCharLongHashMap tCharLongHashMap4 = new TCharLongHashMap(int0, float0, char8, (long) int0);
      tCharLongHashMap4.size();
      long long0 = tCharLongHashMap4.get(char8);
      assertEquals(89834777L, long0);
  }

  @Test
  public void test14()  throws Throwable  {
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap();
      char char0 = 'M';
      tCharLongHashMap0.put(char0, char0);
      assertEquals(false, tCharLongHashMap0.isEmpty());
      
      tCharLongHashMap0.clear();
      tCharLongHashMap0.valueCollection();
      tCharLongHashMap0.getAutoCompactionFactor();
      int int0 = (-1254);
      float float0 = 805.6017F;
      char char1 = '?';
      long long0 = (-1871L);
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap(int0, float0, char1, long0);
      byte[] byteArray0 = tCharLongHashMap1._states;
      tCharLongHashMap0.compact();
      int int1 = 229;
      TCharLongHashMap tCharLongHashMap2 = new TCharLongHashMap(int1);
      assertEquals(557, tCharLongHashMap2.capacity());
  }
}
