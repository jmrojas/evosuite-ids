/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.decorator;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.decorator.TShortSetDecorator;
import gnu.trove.set.hash.TShortHashSet;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.TreeSet;
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

public class TShortSetDecoratorEvoSuite_Random {

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
      TShortSetDecorator tShortSetDecorator0 = new TShortSetDecorator();
      // Undeclared exception!
      try {
        tShortSetDecorator0.toString();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test1()  throws Throwable  {
      TShortSetDecorator tShortSetDecorator0 = new TShortSetDecorator();
      int int0 = 0;
      Object object0 = null;
      short short0 = (short) (-1499);
      TreeSet<Short> treeSet0 = new TreeSet<Short>();
      tShortSetDecorator0.containsAll((Collection<?>) treeSet0);
      Short short1 = new Short(short0);
      // Undeclared exception!
      try {
        tShortSetDecorator0.add(short1);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test2()  throws Throwable  {
      int int0 = 0;
      float float0 = 0.0F;
      short short0 = (short) (-28195);
      TShortSetDecorator tShortSetDecorator0 = new TShortSetDecorator();
      tShortSetDecorator0.getSet();
      TShortHashSet tShortHashSet0 = new TShortHashSet(int0, float0, short0);
      OutputStream outputStream0 = null;
      ObjectOutputStream objectOutputStream0 = null;
      try {
        objectOutputStream0 = new ObjectOutputStream(outputStream0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test3()  throws Throwable  {
      short[] shortArray0 = new short[4];
      TShortSetDecorator tShortSetDecorator0 = new TShortSetDecorator();
      tShortSetDecorator0.getSet();
      short short0 = (short) (-1936);
      shortArray0[0] = short0;
      // Undeclared exception!
      try {
        tShortSetDecorator0.toString();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test4()  throws Throwable  {
      TShortSetDecorator tShortSetDecorator0 = new TShortSetDecorator();
      LinkedHashSet<TShortHashSet>[] linkedHashSetArray0 = (LinkedHashSet<TShortHashSet>[]) Array.newInstance(LinkedHashSet.class, 8);
      LinkedHashSet<TShortHashSet> linkedHashSet0 = new LinkedHashSet<TShortHashSet>();
      linkedHashSetArray0[0] = linkedHashSet0;
      LinkedHashSet<TShortHashSet> linkedHashSet1 = new LinkedHashSet<TShortHashSet>();
      linkedHashSetArray0[1] = linkedHashSet1;
      LinkedHashSet<TShortHashSet> linkedHashSet2 = new LinkedHashSet<TShortHashSet>();
      linkedHashSetArray0[2] = linkedHashSet2;
      LinkedHashSet<TShortHashSet> linkedHashSet3 = new LinkedHashSet<TShortHashSet>();
      linkedHashSetArray0[3] = linkedHashSet3;
      LinkedHashSet<TShortHashSet> linkedHashSet4 = new LinkedHashSet<TShortHashSet>();
      linkedHashSetArray0[4] = linkedHashSet4;
      LinkedHashSet<TShortHashSet> linkedHashSet5 = new LinkedHashSet<TShortHashSet>();
      linkedHashSetArray0[5] = linkedHashSet5;
      LinkedHashSet<TShortHashSet> linkedHashSet6 = new LinkedHashSet<TShortHashSet>();
      linkedHashSetArray0[6] = linkedHashSet6;
      LinkedHashSet<TShortHashSet> linkedHashSet7 = new LinkedHashSet<TShortHashSet>();
      linkedHashSetArray0[7] = linkedHashSet7;
      // Undeclared exception!
      try {
        tShortSetDecorator0.toArray(linkedHashSetArray0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test5()  throws Throwable  {
      TShortSetDecorator tShortSetDecorator0 = new TShortSetDecorator();
      Short[] shortArray0 = new Short[6];
      short short0 = (short) (-1067);
      Short short1 = new Short(short0);
      shortArray0[0] = short1;
      short short2 = (short)0;
      Short short3 = new Short(short2);
      shortArray0[1] = short3;
      short short4 = (short)869;
      Short short5 = new Short(short4);
      shortArray0[2] = short5;
      short short6 = (short)1971;
      Short short7 = new Short(short6);
      shortArray0[3] = short7;
      short short8 = (short)1856;
      Short short9 = new Short(short8);
      shortArray0[4] = short9;
      Short short10 = new Short((short) shortArray0[3]);
      shortArray0[5] = short10;
      // Undeclared exception!
      try {
        tShortSetDecorator0.toArray(shortArray0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test6()  throws Throwable  {
    Future<?> future = executor.submit(new Runnable(){ 
            public void run() { 
          TShortSetDecorator tShortSetDecorator0 = new TShortSetDecorator();
          FileDescriptor fileDescriptor0 = new FileDescriptor();
          FileOutputStream fileOutputStream0 = null;
          try {
            fileOutputStream0 = new FileOutputStream(fileDescriptor0);
            fail("Expecting exception: SecurityException");
          
          } catch(SecurityException e) {
             //
             // Security manager blocks (\"java.lang.RuntimePermission\" \"writeFileDescriptor\")
             // java.lang.Thread.getStackTrace(Thread.java:1567)
             // org.evosuite.sandbox.MSecurityManager.checkPermission(MSecurityManager.java:303)
             // java.lang.SecurityManager.checkWrite(SecurityManager.java:954)
             // java.io.FileOutputStream.<init>(FileOutputStream.java:244)
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
      } 
    }); 
    future.get(6000, TimeUnit.MILLISECONDS); 
  }

  @Test
  public void test7()  throws Throwable  {
      TShortSetDecorator tShortSetDecorator0 = new TShortSetDecorator();
      Short short0 = null;
      tShortSetDecorator0.add(short0);
      tShortSetDecorator0.add(short0);
      // Undeclared exception!
      try {
        tShortSetDecorator0.isEmpty();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test8()  throws Throwable  {
      TShortSetDecorator tShortSetDecorator0 = new TShortSetDecorator();
      tShortSetDecorator0.getSet();
      TShortHashSet tShortHashSet0 = null;
      try {
        tShortHashSet0 = new TShortHashSet((Collection<? extends Short>) tShortSetDecorator0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test9()  throws Throwable  {
      TShortSetDecorator tShortSetDecorator0 = new TShortSetDecorator();
      // Undeclared exception!
      try {
        tShortSetDecorator0.toArray();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test10()  throws Throwable  {
      TShortSetDecorator tShortSetDecorator0 = new TShortSetDecorator();
      int int0 = 0;
      ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream(int0);
      String string0 = "";
      try {
        byteArrayOutputStream0.toString(string0);
        fail("Expecting exception: UnsupportedEncodingException");
      
      } catch(UnsupportedEncodingException e) {
         //
         // 
         //
      }
  }
}
