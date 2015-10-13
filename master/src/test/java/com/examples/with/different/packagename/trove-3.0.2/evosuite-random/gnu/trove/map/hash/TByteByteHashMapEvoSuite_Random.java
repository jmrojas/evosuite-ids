/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.map.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.map.TByteByteMap;
import gnu.trove.map.hash.TByteByteHashMap;
import gnu.trove.procedure.TByteProcedure;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PrintStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.Locale;
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

public class TByteByteHashMapEvoSuite_Random {

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
      int int0 = 830;
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap(int0);
      byte[] byteArray0 = new byte[4];
      byte byte0 = (byte)0;
      byteArray0[0] = byte0;
      byte byte1 = (byte)1;
      byteArray0[1] = byte1;
      byte byte2 = (byte)0;
      byteArray0[2] = byte2;
      byte byte3 = (byte)0;
      byteArray0[3] = byte3;
      byte[] byteArray1 = tByteByteHashMap0.values(byteArray0);
      assertNotNull(byteArray1);
      assertEquals(1759, tByteByteHashMap0.capacity());
      assertSame(byteArray0, byteArray1);
  }

  @Test
  public void test1()  throws Throwable  {
    Future<?> future = executor.submit(new Runnable(){ 
            public void run() { 
        try {
          int int0 = 214;
          TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap(int0);
          String string0 = "|]zz{i#";
          boolean boolean0 = true;
          FileOutputStream fileOutputStream0 = null;
          try {
            fileOutputStream0 = new FileOutputStream(string0, boolean0);
            fail("Expecting exception: SecurityException");
          
          } catch(SecurityException e) {
             //
             // Security manager blocks (\"java.io.FilePermission\" \"|]zz{i#\" \"write\")
             // java.lang.Thread.getStackTrace(Thread.java:1567)
             // org.evosuite.sandbox.MSecurityManager.checkPermission(MSecurityManager.java:303)
             // java.lang.SecurityManager.checkWrite(SecurityManager.java:979)
             // java.io.FileOutputStream.<init>(FileOutputStream.java:203)
             // java.io.FileOutputStream.<init>(FileOutputStream.java:136)
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
  public void test2()  throws Throwable  {
      byte[] byteArray0 = new byte[7];
      byte byte0 = (byte) (-81);
      byteArray0[0] = byte0;
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap();
      byte byte1 = tByteByteHashMap0.putIfAbsent(byte0, byte0);
      tByteByteHashMap0.iterator();
      byte byte2 = (byte)0;
      byteArray0[1] = byte2;
      byte byte3 = (byte)109;
      byteArray0[2] = byte3;
      byte byte4 = (byte)0;
      byteArray0[3] = byte4;
      byte byte5 = (byte)0;
      byteArray0[4] = byte5;
      byteArray0[0] = byte1;
      byte byte6 = (byte)114;
      byteArray0[1] = byte6;
      byteArray0[2] = byte5;
      byteArray0[3] = byte5;
      byteArray0[4] = byte1;
      assertEquals(false, tByteByteHashMap0.isEmpty());
      
      byteArray0[5] = byte3;
      byteArray0[6] = byte4;
      byte byte7 = (byte)0;
      byteArray0[5] = byte7;
      byte byte8 = (byte) (-55);
      byteArray0[6] = byte8;
      TByteByteHashMap tByteByteHashMap1 = new TByteByteHashMap(byteArray0, byteArray0);
      tByteByteHashMap1.keys(byteArray0);
      assertEquals("{114=114, -55=-55, 0=0}", tByteByteHashMap1.toString());
  }

  @Test
  public void test3()  throws Throwable  {
      int int0 = 295;
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap(int0);
      TByteByteHashMap tByteByteHashMap1 = new TByteByteHashMap((TByteByteMap) tByteByteHashMap0);
      tByteByteHashMap0.compact();
      Locale locale0 = Locale.PRC;
      byte byte0 = (byte) (-1);
      tByteByteHashMap0.remove(byte0);
      char char0 = 's';
      String string0 = locale0.getExtension(char0);
      AbstractMap.SimpleEntry<Object, Integer> abstractMap_SimpleEntry0 = new AbstractMap.SimpleEntry<Object, Integer>((Object) string0, (Integer) int0);
      abstractMap_SimpleEntry0.setValue((Integer) int0);
      tByteByteHashMap1.clear();
      assertEquals(23, tByteByteHashMap1.capacity());
      assertEquals(3, tByteByteHashMap0.capacity());
  }

  @Test
  public void test4()  throws Throwable  {
      int int0 = 474;
      byte byte0 = (byte)47;
      byte[] byteArray0 = new byte[10];
      byteArray0[0] = byte0;
      byteArray0[1] = byte0;
      byteArray0[2] = byte0;
      byteArray0[3] = byte0;
      byteArray0[4] = byte0;
      byteArray0[5] = byte0;
      byteArray0[6] = byte0;
      byteArray0[7] = byte0;
      byte byte1 = (byte) (-5);
      byteArray0[8] = byte1;
      byteArray0[9] = byte0;
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap(byteArray0, byteArray0);
      byte[] byteArray1 = tByteByteHashMap0.values(byteArray0);
      assertEquals(2, tByteByteHashMap0.size());
      assertSame(byteArray0, byteArray1);
      assertNotNull(byteArray1);
      assertEquals("{-5=-5, 47=47}", tByteByteHashMap0.toString());
      
      TByteByteHashMap tByteByteHashMap1 = new TByteByteHashMap(int0, (float) int0, byte0, byte0);
      String string0 = tByteByteHashMap1.toString();
      assertNotNull(string0);
      assertEquals("{}", string0);
  }

  @Test
  public void test5()  throws Throwable  {
      int int0 = 0;
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap(int0);
      tByteByteHashMap0.getAutoCompactionFactor();
      int int1 = 1;
      byte byte0 = (byte)95;
      int int2 = 437;
      byte[] byteArray0 = new byte[3];
      byteArray0[0] = byte0;
      byteArray0[1] = byte0;
      byteArray0[2] = byte0;
      tByteByteHashMap0._set = byteArray0;
      TByteByteHashMap tByteByteHashMap1 = new TByteByteHashMap(int2);
      byte byte1 = (byte)66;
      TByteProcedure tByteProcedure0 = null;
      tByteByteHashMap1.forEachKey(tByteProcedure0);
      tByteByteHashMap1.adjustOrPutValue(byte0, byte0, byte1);
      TByteByteMap tByteByteMap0 = null;
      TByteByteHashMap tByteByteHashMap2 = null;
      try {
        tByteByteHashMap2 = new TByteByteHashMap(tByteByteMap0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test6()  throws Throwable  {
      int int0 = 1286;
      float float0 = (-1.0F);
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap(int0, float0);
      byte byte0 = (byte) (-128);
      byte byte1 = (byte)0;
      boolean boolean0 = tByteByteHashMap0.adjustValue(byte0, byte1);
      assertEquals(false, boolean0);
      assertEquals(3, tByteByteHashMap0.capacity());
  }

  @Test
  public void test7()  throws Throwable  {
      byte[] byteArray0 = new byte[4];
      byte byte0 = (byte) (-128);
      byteArray0[0] = byte0;
      byte byte1 = (byte)0;
      byteArray0[1] = byte1;
      byte byte2 = (byte)1;
      byteArray0[2] = byte2;
      byte byte3 = (byte)87;
      byteArray0[3] = byte3;
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap(byteArray0, byteArray0);
      float float0 = 1.0F;
      tByteByteHashMap0.setAutoCompactionFactor(float0);
      TByteByteMap tByteByteMap0 = null;
      TByteByteHashMap tByteByteHashMap1 = new TByteByteHashMap();
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
  public void test8()  throws Throwable  {
      int int0 = (-1643);
      byte byte0 = (byte)0;
      byte byte1 = (byte)79;
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap((int) byte1);
      tByteByteHashMap0.compact();
      int int1 = (-1748);
      float float0 = (-552.105F);
      TByteByteHashMap tByteByteHashMap1 = new TByteByteHashMap(int1, float0);
      tByteByteHashMap1.keys();
      tByteByteHashMap1.increment(byte0);
      TByteByteHashMap tByteByteHashMap2 = new TByteByteHashMap(int0, (float) int0, byte0, byte1);
      tByteByteHashMap2.values();
      AbstractMap.SimpleImmutableEntry<Byte, TByteByteHashMap> abstractMap_SimpleImmutableEntry0 = new AbstractMap.SimpleImmutableEntry<Byte, TByteByteHashMap>((Byte) byte1, tByteByteHashMap1);
      PipedInputStream pipedInputStream0 = new PipedInputStream();
      DataInputStream dataInputStream0 = new DataInputStream((InputStream) pipedInputStream0);
      try {
        dataInputStream0.readUTF();
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Pipe not connected
         //
      }
  }

  @Test
  public void test9()  throws Throwable  {
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap();
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
  public void test10()  throws Throwable  {
      int int0 = (-588);
      float float0 = 586.0787F;
      byte byte0 = (byte)1;
      byte byte1 = (byte) (-17);
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap(int0, float0, byte0, byte1);
      ByteArrayInputStream byteArrayInputStream0 = new ByteArrayInputStream(tByteByteHashMap0._set, (int) byte0, (int) byte0);
      ObjectInputStream objectInputStream0 = null;
      try {
        objectInputStream0 = new ObjectInputStream((InputStream) byteArrayInputStream0);
        fail("Expecting exception: EOFException");
      
      } catch(EOFException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test11()  throws Throwable  {
      int int0 = 0;
      float float0 = 10.0F;
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap(int0, float0);
      byte byte0 = (byte) (-63);
      tByteByteHashMap0.getNoEntryValue();
      byte byte1 = tByteByteHashMap0.get(byte0);
      int int1 = 1857;
      byte[] byteArray0 = new byte[8];
      byteArray0[0] = byte1;
      byteArray0[1] = byte1;
      byteArray0[2] = byte1;
      byte byte2 = (byte) (-58);
      byteArray0[3] = byte2;
      byteArray0[4] = byte1;
      byteArray0[5] = byte0;
      byteArray0[6] = byte0;
      byteArray0[7] = byte0;
      ByteArrayInputStream byteArrayInputStream0 = new ByteArrayInputStream(byteArray0);
      ObjectInputStream objectInputStream0 = null;
      try {
        objectInputStream0 = new ObjectInputStream((InputStream) byteArrayInputStream0);
        fail("Expecting exception: StreamCorruptedException");
      
      } catch(StreamCorruptedException e) {
         //
         // invalid stream header: 000000C6
         //
      }
  }

  @Test
  public void test12()  throws Throwable  {
      int int0 = 17135863;
      float float0 = 0.0F;
      byte byte0 = (byte) (-61);
      byte byte1 = (byte)105;
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap(int0, float0, byte0, byte1);
      String string0 = "(gTn($R<";
      FileInputStream fileInputStream0 = null;
      try {
        fileInputStream0 = new FileInputStream(string0);
        fail("Expecting exception: FileNotFoundException");
      
      } catch(FileNotFoundException e) {
         //
         // (gTn($R< (No such file or directory)
         //
      }
  }

  @Test
  public void test13()  throws Throwable  {
      int int0 = (-1);
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap(int0, int0);
      TByteByteHashMap tByteByteHashMap1 = new TByteByteHashMap((TByteByteMap) tByteByteHashMap0);
      TByteByteHashMap tByteByteHashMap2 = new TByteByteHashMap((TByteByteMap) tByteByteHashMap1);
      byte byte0 = (byte) (-65);
      tByteByteHashMap2.put(byte0, byte0);
      assertEquals(7, tByteByteHashMap2.capacity());
      
      int int1 = (-1862);
      TByteByteHashMap tByteByteHashMap3 = new TByteByteHashMap(int1, int1);
      tByteByteHashMap3.capacity();
      assertFalse(tByteByteHashMap3.equals(tByteByteHashMap2));
  }

  @Test
  public void test14()  throws Throwable  {
      int int0 = 1066;
      TByteByteHashMap tByteByteHashMap0 = new TByteByteHashMap(int0, int0);
      tByteByteHashMap0.valueCollection();
      tByteByteHashMap0.isEmpty();
      tByteByteHashMap0.getAutoCompactionFactor();
      byte byte0 = (byte)76;
      String string0 = " greater than end index ";
      PrintStream printStream0 = null;
      try {
        printStream0 = new PrintStream(string0, string0);
        fail("Expecting exception: UnsupportedEncodingException");
      
      } catch(UnsupportedEncodingException e) {
         //
         //  greater than end index 
         //
      }
  }

//   @Test
//   public void test15()  throws Throwable  {
//       Byte byte0 = null;
//       byte byte1 = (byte)36;
//       byte byte2 = (byte)0;
//       TByteByteHashMap tByteByteHashMap0 = null;
//       try {
//         tByteByteHashMap0 = new TByteByteHashMap((int) byte0, (float) byte0);
//         fail("Expecting exception: IllegalArgumentException");
//       
//       } catch(IllegalArgumentException e) {
//          //
//          // no message in exception (getMessage() returned null)
//          //
//       }
//   }
}
