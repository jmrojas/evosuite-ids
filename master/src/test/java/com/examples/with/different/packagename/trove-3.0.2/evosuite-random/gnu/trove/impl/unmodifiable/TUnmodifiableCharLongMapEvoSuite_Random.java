/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.unmodifiable;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.function.TLongFunction;
import gnu.trove.impl.unmodifiable.TUnmodifiableCharLongMap;
import gnu.trove.map.TCharLongMap;
import gnu.trove.map.hash.TCharLongHashMap;
import gnu.trove.procedure.TCharLongProcedure;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TUnmodifiableCharLongMapEvoSuite_Random {

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
      int int0 = 1;
      float float0 = 792.238F;
      char char0 = '9';
      long long0 = 609L;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int0, float0, char0, long0);
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap((TCharLongMap) tCharLongHashMap0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap1);
      TCharLongProcedure tCharLongProcedure0 = null;
      tUnmodifiableCharLongMap0.forEachEntry(tCharLongProcedure0);
      TCharLongMap tCharLongMap0 = null;
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap1 = null;
      try {
        tUnmodifiableCharLongMap1 = new TUnmodifiableCharLongMap(tCharLongMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test1()  throws Throwable  {
      char[] charArray0 = new char[5];
      char char0 = 'b';
      charArray0[0] = char0;
      char char1 = '-';
      charArray0[1] = char1;
      char char2 = 'i';
      charArray0[2] = char2;
      char char3 = ']';
      charArray0[3] = char3;
      char char4 = '.';
      charArray0[4] = char4;
      long[] longArray0 = new long[4];
      longArray0[0] = (long) charArray0[0];
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap();
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      tUnmodifiableCharLongMap0.containsValue(longArray0[0]);
      tUnmodifiableCharLongMap0.hashCode();
      longArray0[1] = (long) charArray0[1];
      longArray0[2] = (long) charArray0[1];
      longArray0[3] = (long) charArray0[4];
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap(charArray0, longArray0);
      longArray0[0] = (long) char4;
      longArray0[1] = (long) charArray0[2];
      longArray0[2] = (long) char4;
      longArray0[3] = (long) charArray0[0];
      tUnmodifiableCharLongMap0.keySet();
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap1 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap1);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap2 = new TUnmodifiableCharLongMap((TCharLongMap) tUnmodifiableCharLongMap1);
      assertEquals(0L, tUnmodifiableCharLongMap2.getNoEntryValue());
  }

  @Test
  public void test2()  throws Throwable  {
      int int0 = 899;
      int int1 = 0;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int1, int1);
      TCharLongHashMap tCharLongHashMap1 = null;
      try {
        tCharLongHashMap1 = new TCharLongHashMap((TCharLongMap) tCharLongHashMap0);
        fail("Expecting exception: OutOfMemoryError");
      
      } catch(OutOfMemoryError e) {
         //
         // Java heap space
         //
      }
  }

  @Test
  public void test3()  throws Throwable  {
      TCharLongMap tCharLongMap0 = null;
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = null;
      try {
        tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap(tCharLongMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test4()  throws Throwable  {
      char[] charArray0 = new char[10];
      char char0 = '2';
      charArray0[0] = char0;
      char char1 = '|';
      charArray0[1] = char1;
      char char2 = 'i';
      charArray0[2] = char2;
      char char3 = '4';
      charArray0[3] = char3;
      char char4 = 'C';
      charArray0[4] = char4;
      char char5 = '/';
      charArray0[5] = char5;
      char char6 = '@';
      charArray0[6] = char6;
      char char7 = 'B';
      charArray0[7] = char7;
      char char8 = '';
      charArray0[8] = char8;
      char char9 = 'u';
      charArray0[9] = char9;
      long[] longArray0 = new long[9];
      longArray0[0] = (long) char4;
      longArray0[1] = (long) charArray0[2];
      longArray0[2] = (long) charArray0[8];
      long long0 = 0L;
      longArray0[3] = long0;
      longArray0[4] = (long) charArray0[4];
      longArray0[5] = (long) charArray0[5];
      longArray0[6] = (long) charArray0[9];
      longArray0[7] = (long) charArray0[4];
      longArray0[8] = (long) charArray0[8];
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(charArray0, longArray0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      char char10 = 'l';
      // Undeclared exception!
      try {
        tUnmodifiableCharLongMap0.put(char10, charArray0[1]);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test5()  throws Throwable  {
      int int0 = 0;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap1 = new TUnmodifiableCharLongMap((TCharLongMap) tUnmodifiableCharLongMap0);
      long[] longArray0 = new long[2];
      long long0 = 0L;
      longArray0[0] = long0;
      longArray0[1] = (long) int0;
      long[] longArray1 = tUnmodifiableCharLongMap1.values(longArray0);
      assertSame(longArray1, longArray0);
  }

  @Test
  public void test6()  throws Throwable  {
      char[] charArray0 = new char[6];
      char char0 = 'H';
      long[] longArray0 = new long[8];
      longArray0[0] = (long) char0;
      longArray0[1] = (long) char0;
      longArray0[2] = (long) char0;
      longArray0[3] = (long) char0;
      longArray0[4] = (long) char0;
      longArray0[5] = (long) char0;
      longArray0[6] = (long) char0;
      longArray0[7] = (long) char0;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(charArray0, longArray0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap1 = new TUnmodifiableCharLongMap((TCharLongMap) tUnmodifiableCharLongMap0);
      long long0 = 0L;
      // Undeclared exception!
      try {
        tUnmodifiableCharLongMap1.adjustOrPutValue(char0, long0, longArray0[5]);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test7()  throws Throwable  {
      int int0 = (-201);
      float float0 = 965.7699F;
      char[] charArray0 = new char[6];
      char char0 = ']';
      charArray0[0] = char0;
      char char1 = 'z';
      charArray0[1] = char1;
      char char2 = 'Z';
      charArray0[2] = char2;
      char char3 = '\'';
      charArray0[3] = char3;
      char char4 = '}';
      charArray0[4] = char4;
      char char5 = 'O';
      charArray0[5] = char5;
      long[] longArray0 = new long[10];
      longArray0[0] = (long) char4;
      long long0 = 0L;
      longArray0[1] = long0;
      long long1 = 1338L;
      longArray0[2] = long1;
      longArray0[3] = (long) int0;
      longArray0[4] = (long) int0;
      longArray0[5] = (long) char5;
      longArray0[6] = (long) int0;
      longArray0[7] = (long) charArray0[5];
      longArray0[8] = (long) char4;
      longArray0[9] = (long) char5;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(charArray0, longArray0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      tUnmodifiableCharLongMap0.hashCode();
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap(int0, float0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap1 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap1);
      long long2 = (-1L);
      tUnmodifiableCharLongMap1.containsValue(long2);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap2 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap1);
      char char6 = '~';
      tCharLongHashMap1.contains(char6);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap3 = new TUnmodifiableCharLongMap((TCharLongMap) tUnmodifiableCharLongMap2);
      assertNotSame(tUnmodifiableCharLongMap3, tUnmodifiableCharLongMap1);
  }

  @Test
  public void test8()  throws Throwable  {
      char[] charArray0 = new char[9];
      char char0 = '7';
      charArray0[0] = char0;
      char char1 = 'T';
      charArray0[1] = char1;
      char char2 = '>';
      charArray0[2] = char2;
      char char3 = '>';
      charArray0[3] = char3;
      char char4 = 'm';
      charArray0[4] = char4;
      char char5 = 'v';
      charArray0[5] = char5;
      char char6 = '=';
      charArray0[6] = char6;
      char char7 = 'g';
      charArray0[7] = char7;
      char char8 = '/';
      charArray0[8] = char8;
      long[] longArray0 = new long[9];
      longArray0[0] = (long) charArray0[2];
      longArray0[1] = (long) char1;
      longArray0[2] = (long) char5;
      longArray0[3] = (long) char7;
      longArray0[4] = (long) charArray0[8];
      longArray0[5] = (long) char4;
      long long0 = 0L;
      longArray0[6] = long0;
      longArray0[7] = (long) char6;
      longArray0[8] = (long) char1;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(charArray0, longArray0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      long long1 = tUnmodifiableCharLongMap0.getNoEntryValue();
      assertEquals(0L, long1);
  }

  @Test
  public void test9()  throws Throwable  {
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
  public void test10()  throws Throwable  {
      int int0 = 143;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int0, int0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap1 = new TUnmodifiableCharLongMap((TCharLongMap) tUnmodifiableCharLongMap0);
      int int1 = (-716);
      float float0 = (-1124.7814F);
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap(int1, float0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap2 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap1);
      TCharLongMap tCharLongMap0 = null;
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap3 = null;
      try {
        tUnmodifiableCharLongMap3 = new TUnmodifiableCharLongMap(tCharLongMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test11()  throws Throwable  {
      int int0 = 1284;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap();
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      TLongFunction tLongFunction0 = null;
      // Undeclared exception!
      try {
        tUnmodifiableCharLongMap0.transformValues(tLongFunction0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test12()  throws Throwable  {
      int int0 = 0;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int0, int0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      tUnmodifiableCharLongMap0.getNoEntryValue();
      TCharLongHashMap tCharLongHashMap1 = new TCharLongHashMap();
      tCharLongHashMap1.keys();
      char char0 = '=';
      tCharLongHashMap1.iterator();
      long long0 = 0L;
      tCharLongHashMap1.putIfAbsent(char0, long0);
      long[] longArray0 = new long[5];
      long long1 = 0L;
      longArray0[0] = long1;
      char char1 = 'o';
      long long2 = (-818L);
      // Undeclared exception!
      try {
        tUnmodifiableCharLongMap0.adjustOrPutValue(char1, long1, long2);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test13()  throws Throwable  {
      int int0 = (-1642);
      char char0 = '$';
      char[] charArray0 = new char[3];
      charArray0[0] = char0;
      char char1 = 'q';
      charArray0[1] = char1;
      charArray0[2] = char0;
      long[] longArray0 = new long[3];
      longArray0[0] = (long) char0;
      long long0 = 1547L;
      longArray0[1] = long0;
      long long1 = (-1711L);
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap((int) char0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      long long2 = (-1803L);
      // Undeclared exception!
      try {
        tUnmodifiableCharLongMap0.adjustOrPutValue(charArray0[0], long2, longArray0[0]);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test14()  throws Throwable  {
      int int0 = 1928;
      float float0 = 1.0F;
      char char0 = '\'';
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int0, float0, char0, (long) char0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      long long0 = tUnmodifiableCharLongMap0.get(char0);
      assertEquals(39L, long0);
  }

  @Test
  public void test15()  throws Throwable  {
      int int0 = 2002;
      int int1 = 0;
      float float0 = 6.6360896E8F;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int1, float0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      char char0 = 'e';
      // Undeclared exception!
      try {
        tUnmodifiableCharLongMap0.remove(char0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test16()  throws Throwable  {
      int int0 = 118;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap();
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      tUnmodifiableCharLongMap0.containsValue((long) int0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap1 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      // Undeclared exception!
      try {
        tUnmodifiableCharLongMap1.putAll((TCharLongMap) tCharLongHashMap0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test17()  throws Throwable  {
      char[] charArray0 = new char[4];
      char char0 = 'C';
      charArray0[0] = char0;
      char char1 = 'M';
      charArray0[1] = char1;
      char char2 = 'h';
      charArray0[2] = char2;
      char char3 = 'J';
      charArray0[3] = char3;
      long[] longArray0 = new long[9];
      longArray0[0] = (long) char2;
      longArray0[1] = (long) charArray0[0];
      longArray0[2] = (long) charArray0[2];
      longArray0[3] = (long) charArray0[1];
      longArray0[4] = (long) charArray0[0];
      longArray0[5] = (long) char0;
      longArray0[6] = (long) char0;
      long long0 = (-802L);
      longArray0[7] = long0;
      longArray0[8] = (long) char1;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(charArray0, longArray0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      char char4 = 'z';
      // Undeclared exception!
      try {
        tUnmodifiableCharLongMap0.remove(char4);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test18()  throws Throwable  {
      int int0 = 2045;
      TCharLongHashMap tCharLongHashMap0 = new TCharLongHashMap(int0);
      TUnmodifiableCharLongMap tUnmodifiableCharLongMap0 = new TUnmodifiableCharLongMap((TCharLongMap) tCharLongHashMap0);
      char[] charArray0 = tUnmodifiableCharLongMap0.keys();
      assertNotNull(charArray0);
  }
}
