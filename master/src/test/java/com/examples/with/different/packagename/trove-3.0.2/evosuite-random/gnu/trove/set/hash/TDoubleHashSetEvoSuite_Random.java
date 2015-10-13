/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.set.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.TDoubleCollection;
import gnu.trove.impl.sync.TSynchronizedDoubleSet;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.set.TDoubleSet;
import gnu.trove.set.hash.TDoubleHashSet;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.NoSuchElementException;
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

public class TDoubleHashSetEvoSuite_Random {

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
      int int0 = 0;
      double[] doubleArray0 = new double[7];
      doubleArray0[0] = (double) int0;
      doubleArray0[1] = (double) int0;
      doubleArray0[2] = (double) int0;
      doubleArray0[3] = (double) int0;
      doubleArray0[4] = (double) int0;
      doubleArray0[5] = (double) int0;
      doubleArray0[6] = (double) int0;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      boolean boolean0 = true;
      tDoubleHashSet0.reenableAutoCompaction(boolean0);
      TDoubleHashSet tDoubleHashSet1 = new TDoubleHashSet(int0);
      int int1 = 855;
      float float0 = 356.13162F;
      TDoubleHashSet tDoubleHashSet2 = new TDoubleHashSet(int1, float0);
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
  public void test1()  throws Throwable  {
      int int0 = 0;
      float float0 = 0.0F;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(int0, float0, (double) float0);
      tDoubleHashSet0.trimToSize();
      int int1 = (-1021);
      int int2 = (-1146);
      float float1 = 1538.0278F;
      TDoubleHashSet tDoubleHashSet1 = new TDoubleHashSet(int2, float1);
      tDoubleHashSet1.trimToSize();
      TDoubleHashSet tDoubleHashSet2 = new TDoubleHashSet(int1);
      int int3 = tDoubleHashSet1.capacity();
      assertEquals("{}", tDoubleHashSet1.toString());
      assertEquals(3, int3);
      
      TDoubleHashSet tDoubleHashSet3 = new TDoubleHashSet((TDoubleCollection) tDoubleHashSet2);
      boolean boolean0 = tDoubleHashSet2.addAll((TDoubleCollection) tDoubleHashSet3);
      boolean boolean1 = tDoubleHashSet3.retainAll(tDoubleHashSet2._set);
      assertTrue(boolean1 == boolean0);
      assertEquals(0.0, tDoubleHashSet3.getNoEntryValue(), 0.01D);
      assertEquals(23, tDoubleHashSet3.capacity());
  }

  @Test
  public void test2()  throws Throwable  {
      int int0 = 0;
      double[] doubleArray0 = new double[8];
      doubleArray0[0] = (double) int0;
      double double0 = 1.0;
      doubleArray0[1] = double0;
      double double1 = (-255.98569237168925);
      doubleArray0[2] = double1;
      doubleArray0[3] = (double) int0;
      doubleArray0[4] = (double) int0;
      doubleArray0[5] = (double) int0;
      doubleArray0[6] = (double) int0;
      doubleArray0[7] = (double) int0;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      TDoubleArrayList tDoubleArrayList0 = TDoubleArrayList.wrap(doubleArray0);
      boolean boolean0 = tDoubleHashSet0.containsAll((TDoubleCollection) tDoubleArrayList0);
      assertEquals("{-255.98569237168925,1.0,0.0}", tDoubleHashSet0.toString());
      
      float float0 = 0.0F;
      double double2 = 1.0;
      float float1 = (-1041.7018F);
      TDoubleHashSet tDoubleHashSet1 = new TDoubleHashSet(int0, float1);
      tDoubleHashSet1.getAutoCompactionFactor();
      TDoubleHashSet tDoubleHashSet2 = new TDoubleHashSet(int0, float0, double2);
      LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
      boolean boolean1 = tDoubleHashSet2.containsAll((Collection<?>) linkedList0);
      assertTrue(boolean1 == boolean0);
      
      int int1 = 0;
      TDoubleHashSet tDoubleHashSet3 = new TDoubleHashSet(int1, int1, int1);
      tDoubleHashSet3.compact();
      assertEquals(3, tDoubleHashSet3.capacity());
      assertEquals("{}", tDoubleHashSet3.toString());
      assertFalse(tDoubleHashSet3.equals(tDoubleHashSet0));
  }

  @Test
  public void test3()  throws Throwable  {
      LinkedList<TDoubleHashSet> linkedList0 = new LinkedList<TDoubleHashSet>();
      // Undeclared exception!
      try {
        linkedList0.pop();
        fail("Expecting exception: NoSuchElementException");
      
      } catch(NoSuchElementException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test4()  throws Throwable  {
    Future<?> future = executor.submit(new Runnable(){ 
            public void run() { 
        try {
          int int0 = 1678;
          TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(int0, int0);
          double[] doubleArray0 = new double[6];
          doubleArray0[0] = (double) int0;
          String string0 = "";
          File file0 = new File(string0, string0);
          FileOutputStream fileOutputStream0 = null;
          try {
            fileOutputStream0 = new FileOutputStream(file0);
            fail("Expecting exception: SecurityException");
          
          } catch(SecurityException e) {
             //
             // Security manager blocks (\"java.io.FilePermission\" \"/\" \"write\")
             // java.lang.Thread.getStackTrace(Thread.java:1567)
             // org.evosuite.sandbox.MSecurityManager.checkPermission(MSecurityManager.java:303)
             // java.lang.SecurityManager.checkWrite(SecurityManager.java:979)
             // java.io.FileOutputStream.<init>(FileOutputStream.java:203)
             // java.io.FileOutputStream.<init>(FileOutputStream.java:165)
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
  public void test5()  throws Throwable  {
      int int0 = (-1634);
      double[] doubleArray0 = new double[7];
      doubleArray0[0] = (double) int0;
      doubleArray0[1] = (double) int0;
      doubleArray0[2] = (double) int0;
      doubleArray0[3] = (double) int0;
      double double0 = 0.0;
      doubleArray0[4] = double0;
      double double1 = (-1.0);
      doubleArray0[5] = double1;
      doubleArray0[6] = (double) int0;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      tDoubleHashSet0.contains(doubleArray0[1]);
      assertEquals("{-1.0,-1634.0,0.0}", tDoubleHashSet0.toString());
      
      LinkedList<Double> linkedList0 = new LinkedList<Double>();
      TDoubleHashSet tDoubleHashSet1 = new TDoubleHashSet((Collection<? extends Double>) linkedList0);
      TDoubleHashSet tDoubleHashSet2 = new TDoubleHashSet(tDoubleHashSet1._set);
      assertEquals("{0.0}", tDoubleHashSet2.toString());
      
      boolean boolean0 = tDoubleHashSet1.containsAll((Collection<?>) linkedList0);
      assertEquals(true, boolean0);
      
      TDoubleHashSet tDoubleHashSet3 = new TDoubleHashSet(int0, int0, int0);
      TDoubleCollection tDoubleCollection0 = null;
      tDoubleHashSet3.reenableAutoCompaction(boolean0);
      // Undeclared exception!
      try {
        tDoubleHashSet3.containsAll(tDoubleCollection0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test6()  throws Throwable  {
      TDoubleCollection tDoubleCollection0 = null;
      TDoubleHashSet tDoubleHashSet0 = null;
      try {
        tDoubleHashSet0 = new TDoubleHashSet(tDoubleCollection0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test7()  throws Throwable  {
      double[] doubleArray0 = new double[7];
      double double0 = (-1.0);
      doubleArray0[0] = double0;
      double double1 = 849.6044040709642;
      doubleArray0[1] = double1;
      double double2 = (-781.747916592722);
      doubleArray0[2] = double2;
      double double3 = 0.0;
      doubleArray0[3] = double3;
      double double4 = (-390.1131943655506);
      doubleArray0[4] = double4;
      double double5 = 54.58508647400789;
      doubleArray0[5] = double5;
      double double6 = 2.2250738585072014E-308;
      doubleArray0[6] = double6;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      double[] doubleArray1 = tDoubleHashSet0.toArray(doubleArray0);
      assertNotNull(doubleArray1);
      
      int int0 = 0;
      TDoubleHashSet tDoubleHashSet1 = new TDoubleHashSet(int0);
      TSynchronizedDoubleSet tSynchronizedDoubleSet0 = new TSynchronizedDoubleSet((TDoubleSet) tDoubleHashSet1);
      int int1 = 1536;
      tDoubleHashSet1.ensureCapacity(int1);
      tSynchronizedDoubleSet0.add((double) int0);
      tDoubleHashSet1.addAll((TDoubleCollection) tSynchronizedDoubleSet0);
      TDoubleHashSet tDoubleHashSet2 = new TDoubleHashSet((TDoubleCollection) tSynchronizedDoubleSet0);
      double double7 = (-1347.2159743076845);
      TDoubleHashSet tDoubleHashSet3 = new TDoubleHashSet((TDoubleCollection) tDoubleHashSet1);
      boolean boolean0 = tDoubleHashSet2.add(double7);
      assertEquals(3203, tDoubleHashSet1.capacity());
      assertEquals(true, boolean0);
  }

  @Test
  public void test8()  throws Throwable  {
      double[] doubleArray0 = new double[3];
      double double0 = 0.0;
      doubleArray0[0] = double0;
      int int0 = (-1);
      float float0 = 1657.1901F;
      int int1 = 0;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(int1, int0);
      TDoubleHashSet tDoubleHashSet1 = new TDoubleHashSet(int0, float0, (double) float0);
      LinkedList<TDoubleHashSet> linkedList0 = new LinkedList<TDoubleHashSet>();
      TDoubleHashSet tDoubleHashSet2 = new TDoubleHashSet(doubleArray0);
      tDoubleHashSet1.trimToSize();
      doubleArray0[0] = double0;
      doubleArray0[1] = double0;
      doubleArray0[2] = double0;
      boolean boolean0 = tDoubleHashSet1.removeAll((Collection<?>) linkedList0);
      doubleArray0[1] = double0;
      doubleArray0[2] = double0;
      double double1 = 0.0;
      doubleArray0[0] = double1;
      boolean boolean1 = tDoubleHashSet1.containsAll((TDoubleCollection) tDoubleHashSet2);
      int int2 = (-1655);
      TDoubleArrayList tDoubleArrayList0 = new TDoubleArrayList(doubleArray0);
      TDoubleHashSet tDoubleHashSet3 = new TDoubleHashSet((TDoubleCollection) tDoubleArrayList0);
      byte[] byteArray0 = tDoubleHashSet3._states;
      assertEquals("{0.0}", tDoubleHashSet3.toString());
      
      TDoubleHashSet tDoubleHashSet4 = new TDoubleHashSet();
      float float1 = 0.0F;
      TDoubleHashSet tDoubleHashSet5 = new TDoubleHashSet(int2, float1);
      tDoubleHashSet1.compact();
      assertEquals(3, tDoubleHashSet1.capacity());
      
      tDoubleHashSet5.compact();
      double double2 = 1597.7057149886455;
      int int3 = tDoubleHashSet5.hashCode();
      assertEquals(0, int3);
      
      tDoubleArrayList0.isEmpty();
      doubleArray0[1] = double2;
      double double3 = 0.0;
      doubleArray0[2] = double3;
      double double4 = 485.7493131339258;
      boolean boolean2 = tDoubleHashSet5.remove(double4);
      assertTrue(boolean2 == boolean1);
      
      boolean boolean3 = tDoubleHashSet5.containsAll(doubleArray0);
      assertEquals("{}", tDoubleHashSet5.toString());
      assertTrue(boolean3 == boolean0);
      
      TDoubleHashSet tDoubleHashSet6 = new TDoubleHashSet(doubleArray0);
      assertEquals("{1597.7057149886455,0.0}", tDoubleHashSet6.toString());
  }

  @Test
  public void test9()  throws Throwable  {
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet();
      double[] doubleArray0 = new double[3];
      double double0 = (-486.1472854189421);
      doubleArray0[0] = double0;
      double double1 = 9.63907350112534;
      doubleArray0[1] = double1;
      double double2 = 621.4189310191994;
      doubleArray0[2] = double2;
      boolean boolean0 = tDoubleHashSet0.retainAll(doubleArray0);
      assertEquals(false, boolean0);
      assertEquals(23, tDoubleHashSet0.capacity());
  }

  @Test
  public void test10()  throws Throwable  {
      int int0 = 261;
      float float0 = 0.0F;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(int0, float0);
      LinkedList<Double> linkedList0 = new LinkedList<Double>();
      tDoubleHashSet0.containsAll((Collection<?>) linkedList0);
      String string0 = "Jtb^U";
      PrintStream printStream0 = null;
      try {
        printStream0 = new PrintStream(string0, string0);
        fail("Expecting exception: UnsupportedEncodingException");
      
      } catch(UnsupportedEncodingException e) {
         //
         // Jtb^U
         //
      }
  }

  @Test
  public void test11()  throws Throwable  {
      double[] doubleArray0 = new double[4];
      double double0 = (-357.2025608778588);
      doubleArray0[0] = double0;
      double double1 = (-749.3084013187171);
      doubleArray0[1] = double1;
      double double2 = 1168.2316043224434;
      doubleArray0[2] = double2;
      double double3 = 4.9E-324;
      doubleArray0[3] = double3;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      FileDescriptor fileDescriptor0 = FileDescriptor.out;
      FileInputStream fileInputStream0 = new FileInputStream(fileDescriptor0);
      PushbackInputStream pushbackInputStream0 = new PushbackInputStream((InputStream) fileInputStream0);
      ObjectInputStream objectInputStream0 = null;
      try {
        objectInputStream0 = new ObjectInputStream((InputStream) pushbackInputStream0);
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Bad file descriptor
         //
      }
  }

  @Test
  public void test12()  throws Throwable  {
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet();
      tDoubleHashSet0.isEmpty();
      int int0 = 1658;
      tDoubleHashSet0.hashCode();
      float float0 = 1948.1111F;
      int int1 = 10;
      TDoubleHashSet tDoubleHashSet1 = new TDoubleHashSet(int1, (float) int0, (double) float0);
      tDoubleHashSet0.hashCode();
      TDoubleHashSet tDoubleHashSet2 = new TDoubleHashSet(int0, float0);
      tDoubleHashSet2.tempDisableAutoCompaction();
      assertEquals(0, tDoubleHashSet2.size());
  }
}
