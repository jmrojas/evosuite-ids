/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.unmodifiable;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.TLongCollection;
import gnu.trove.impl.sync.TSynchronizedLongList;
import gnu.trove.impl.sync.TSynchronizedLongSet;
import gnu.trove.impl.sync.TSynchronizedRandomAccessLongList;
import gnu.trove.impl.unmodifiable.TUnmodifiableLongCollection;
import gnu.trove.impl.unmodifiable.TUnmodifiableLongList;
import gnu.trove.impl.unmodifiable.TUnmodifiableLongSet;
import gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessLongList;
import gnu.trove.iterator.TLongIterator;
import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import gnu.trove.list.linked.TLongLinkedList;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;
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

public class TUnmodifiableLongCollectionEvoSuite_Random {

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
      TLongArrayList tLongArrayList0 = new TLongArrayList();
      int int0 = (-1935);
      int int1 = 415;
      // Undeclared exception!
      try {
        tLongArrayList0.subList(int0, int1);
        fail("Expecting exception: IndexOutOfBoundsException");
      
      } catch(IndexOutOfBoundsException e) {
         //
         // begin index can not be < 0
         //
      }
  }

  @Test
  public void test1()  throws Throwable  {
      TLongArrayList tLongArrayList0 = new TLongArrayList();
      TUnmodifiableLongList tUnmodifiableLongList0 = new TUnmodifiableLongList((TLongList) tLongArrayList0);
      String string0 = tUnmodifiableLongList0.toString();
      assertEquals("{}", string0);
  }

  @Test
  public void test2()  throws Throwable  {
      long[] longArray0 = new long[10];
      long long0 = 564L;
      longArray0[0] = long0;
      long long1 = 1304L;
      longArray0[1] = long1;
      long long2 = 0L;
      longArray0[2] = long2;
      long long3 = 508L;
      longArray0[3] = long3;
      long long4 = (-18L);
      longArray0[4] = long4;
      long long5 = 1L;
      longArray0[5] = long5;
      long long6 = (-1L);
      longArray0[6] = long6;
      long long7 = (-855L);
      longArray0[7] = long7;
      long long8 = (-232L);
      longArray0[8] = long8;
      long long9 = (-2022L);
      longArray0[9] = long9;
      TLongHashSet tLongHashSet0 = new TLongHashSet(longArray0);
      TUnmodifiableLongSet tUnmodifiableLongSet0 = new TUnmodifiableLongSet((TLongSet) tLongHashSet0);
      TLongIterator tLongIterator0 = tUnmodifiableLongSet0.iterator();
      assertEquals(1L, tLongIterator0.next());
  }

  @Test
  public void test3()  throws Throwable  {
      int int0 = (-1727);
      long long0 = 905L;
      TLongArrayList tLongArrayList0 = null;
      try {
        tLongArrayList0 = new TLongArrayList(int0, long0);
        fail("Expecting exception: NegativeArraySizeException");
      
      } catch(NegativeArraySizeException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test4()  throws Throwable  {
      int int0 = 0;
      TLongHashSet tLongHashSet0 = new TLongHashSet(int0);
      TUnmodifiableLongCollection tUnmodifiableLongCollection0 = new TUnmodifiableLongCollection((TLongCollection) tLongHashSet0);
      tUnmodifiableLongCollection0.getNoEntryValue();
      int int1 = 0;
      int int2 = 0;
      TLongArrayList tLongArrayList0 = new TLongArrayList(int2);
      TUnmodifiableLongList tUnmodifiableLongList0 = new TUnmodifiableLongList((TLongList) tLongArrayList0);
      tUnmodifiableLongList0.iterator();
      float float0 = (-1212.2322F);
      long long0 = 0L;
      TLongHashSet tLongHashSet1 = new TLongHashSet(int1, float0, long0);
      tUnmodifiableLongList0.getNoEntryValue();
      TUnmodifiableLongCollection tUnmodifiableLongCollection1 = new TUnmodifiableLongCollection((TLongCollection) tLongHashSet1);
      // Undeclared exception!
      try {
        tUnmodifiableLongCollection1.removeAll(tLongHashSet1._set);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test5()  throws Throwable  {
      TLongHashSet tLongHashSet0 = new TLongHashSet();
      TLongArrayList tLongArrayList0 = new TLongArrayList();
      TSynchronizedRandomAccessLongList tSynchronizedRandomAccessLongList0 = new TSynchronizedRandomAccessLongList((TLongList) tLongArrayList0);
      TUnmodifiableLongList tUnmodifiableLongList0 = new TUnmodifiableLongList((TLongList) tSynchronizedRandomAccessLongList0);
      tUnmodifiableLongList0.containsAll((TLongCollection) tLongHashSet0);
      TSynchronizedLongSet tSynchronizedLongSet0 = new TSynchronizedLongSet((TLongSet) tLongHashSet0);
      LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
      tUnmodifiableLongList0.containsAll((Collection<?>) linkedList0);
      // Undeclared exception!
      try {
        tUnmodifiableLongList0.addAll(tLongHashSet0._set);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test6()  throws Throwable  {
      TLongCollection tLongCollection0 = null;
      TUnmodifiableLongCollection tUnmodifiableLongCollection0 = null;
      try {
        tUnmodifiableLongCollection0 = new TUnmodifiableLongCollection(tLongCollection0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test7()  throws Throwable  {
      long[] longArray0 = new long[6];
      long long0 = 1L;
      longArray0[0] = long0;
      longArray0[1] = long0;
      longArray0[2] = long0;
      longArray0[3] = long0;
      longArray0[4] = long0;
      long long1 = 0L;
      longArray0[5] = long1;
      long long2 = (-106L);
      longArray0[0] = long2;
      TLongLinkedList tLongLinkedList0 = new TLongLinkedList();
      long long3 = (-1609L);
      longArray0[0] = long2;
      longArray0[1] = long3;
      longArray0[2] = long3;
      longArray0[3] = long2;
      longArray0[4] = long2;
      longArray0[5] = long3;
      // Undeclared exception!
      try {
        tLongLinkedList0.indexOf(long3);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test8()  throws Throwable  {
      TLongSet tLongSet0 = null;
      TUnmodifiableLongSet tUnmodifiableLongSet0 = null;
      try {
        tUnmodifiableLongSet0 = new TUnmodifiableLongSet(tLongSet0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test9()  throws Throwable  {
    Future<?> future = executor.submit(new Runnable(){ 
            public void run() { 
        try {
          int int0 = 1721;
          float float0 = 0.0F;
          TLongHashSet tLongHashSet0 = new TLongHashSet(int0, float0);
          TUnmodifiableLongSet tUnmodifiableLongSet0 = new TUnmodifiableLongSet((TLongSet) tLongHashSet0);
          String string0 = "`tmr3'! 6>CwV";
          PrintStream printStream0 = null;
          try {
            printStream0 = new PrintStream(string0);
            fail("Expecting exception: SecurityException");
          
          } catch(SecurityException e) {
             //
             // Security manager blocks (\"java.io.FilePermission\" \"`tmr3'! 6>CwV\" \"write\")
             // java.lang.Thread.getStackTrace(Thread.java:1567)
             // org.evosuite.sandbox.MSecurityManager.checkPermission(MSecurityManager.java:303)
             // java.lang.SecurityManager.checkWrite(SecurityManager.java:979)
             // java.io.FileOutputStream.<init>(FileOutputStream.java:203)
             // java.io.FileOutputStream.<init>(FileOutputStream.java:104)
             // java.io.PrintStream.<init>(PrintStream.java:208)
             // sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
             // sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:57)
             // sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
             // java.lang.reflect.Constructor.newInstance(Constructor.java:525)
             // org.evosuite.testcase.ConstructorStatement$1.execute(ConstructorStatement.java:226)
             // org.evosuite.testcase.AbstractStatement.exceptionHandler(AbstractStatement.java:144)
             // org.evosuite.testcase.ConstructorStatement.execute(ConstructorStatement.java:188)
             // org.evosuite.testcase.TestRunnable.call(TestRunnable.java:291)
             // org.evosuite.testcase.TestRunnable.call(TestRunnable.java:1)
             // java.util.concurrent.FutureTask$Sync.innerRun(FutureTask.java:334)
             // java.util.concurrent.FutureTask.run(FutureTask.java:166)
             // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1110)
             // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:603)
             // java.lang.Thread.run(Thread.java:722)
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
      int int0 = (-1170);
      TLongArrayList tLongArrayList0 = null;
      try {
        tLongArrayList0 = new TLongArrayList(int0, int0);
        fail("Expecting exception: NegativeArraySizeException");
      
      } catch(NegativeArraySizeException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test11()  throws Throwable  {
      TLongHashSet tLongHashSet0 = new TLongHashSet();
      float float0 = (-78.05044F);
      // Undeclared exception!
      try {
        tLongHashSet0.setAutoCompactionFactor(float0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Factor must be >= 0: -78.05044
         //
      }
  }

  @Test
  public void test12()  throws Throwable  {
      long[] longArray0 = new long[10];
      long long0 = (-398L);
      longArray0[0] = long0;
      long long1 = 9223372036854775807L;
      longArray0[1] = long1;
      long long2 = (-890L);
      longArray0[2] = long2;
      long long3 = 1565L;
      longArray0[3] = long3;
      long long4 = 1L;
      TLongHashSet tLongHashSet0 = new TLongHashSet();
      TLongArrayList tLongArrayList0 = new TLongArrayList();
      TUnmodifiableRandomAccessLongList tUnmodifiableRandomAccessLongList0 = new TUnmodifiableRandomAccessLongList((TLongList) tLongArrayList0);
      boolean boolean0 = tUnmodifiableRandomAccessLongList0.isEmpty();
      assertEquals(true, boolean0);
      
      TUnmodifiableLongCollection tUnmodifiableLongCollection0 = new TUnmodifiableLongCollection((TLongCollection) tLongHashSet0);
      TLongCollection tLongCollection0 = null;
      // Undeclared exception!
      try {
        tUnmodifiableLongCollection0.retainAll(tLongCollection0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test13()  throws Throwable  {
      TLongArrayList tLongArrayList0 = new TLongArrayList();
      int int0 = 0;
      TLongArrayList tLongArrayList1 = (TLongArrayList)tLongArrayList0.subList(int0, int0);
      TUnmodifiableLongCollection tUnmodifiableLongCollection0 = new TUnmodifiableLongCollection((TLongCollection) tLongArrayList1);
      // Undeclared exception!
      try {
        tUnmodifiableLongCollection0.retainAll((TLongCollection) tLongArrayList0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test14()  throws Throwable  {
      int int0 = 0;
      TLongCollection tLongCollection0 = null;
      TUnmodifiableLongCollection tUnmodifiableLongCollection0 = null;
      try {
        tUnmodifiableLongCollection0 = new TUnmodifiableLongCollection(tLongCollection0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test15()  throws Throwable  {
      TLongSet tLongSet0 = null;
      TLongArrayList tLongArrayList0 = null;
      try {
        tLongArrayList0 = new TLongArrayList((TLongCollection) tLongSet0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test16()  throws Throwable  {
      long[] longArray0 = new long[2];
      long long0 = 1088L;
      longArray0[0] = long0;
      long long1 = (-1L);
      longArray0[1] = long1;
      TLongArrayList tLongArrayList0 = TLongArrayList.wrap(longArray0, longArray0[1]);
      int int0 = 1742;
      long long2 = 0L;
      TLongHashSet tLongHashSet0 = new TLongHashSet(int0, (float) longArray0[1], long2);
      String string0 = tLongHashSet0.toString();
      TSynchronizedLongList tSynchronizedLongList0 = new TSynchronizedLongList((TLongList) tLongArrayList0, (Object) string0);
      int int1 = 1674;
      // Undeclared exception!
      try {
        tSynchronizedLongList0.subList(int0, int1);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // end index 1674 greater than begin index 1742
         //
      }
  }
}
