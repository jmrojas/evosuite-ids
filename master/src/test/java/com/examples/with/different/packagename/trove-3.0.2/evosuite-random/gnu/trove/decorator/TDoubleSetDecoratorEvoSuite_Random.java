/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.decorator;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.decorator.TDoubleSetDecorator;
import gnu.trove.set.TDoubleSet;
import gnu.trove.set.hash.TDoubleHashSet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.TreeSet;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TDoubleSetDecoratorEvoSuite_Random {

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
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      HashSet<String> hashSet0 = new HashSet<String>();
      // Undeclared exception!
      try {
        tDoubleSetDecorator0.hashCode();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test1()  throws Throwable  {
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      String string0 = ")g|R xmqj";
      try {
        Double.valueOf(string0);
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // For input string: \")g|R xmqj\"
         //
      }
  }

  @Test
  public void test2()  throws Throwable  {
      HashSet<Double> hashSet0 = new HashSet<Double>();
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet((Collection<? extends Double>) hashSet0);
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator((TDoubleSet) tDoubleHashSet0);
      Locale locale0 = Locale.KOREAN;
      String string0 = locale0.getLanguage();
      boolean boolean0 = tDoubleSetDecorator0.equals((Object) string0);
      assertEquals(false, boolean0);
  }

  @Test
  public void test3()  throws Throwable  {
      int int0 = 1;
      double[] doubleArray0 = new double[3];
      doubleArray0[0] = (double) int0;
      doubleArray0[1] = (double) int0;
      doubleArray0[2] = (double) int0;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator((TDoubleSet) tDoubleHashSet0);
      TreeSet<Integer> treeSet0 = new TreeSet<Integer>();
      tDoubleSetDecorator0.removeAll((Collection<?>) treeSet0);
      TDoubleHashSet tDoubleHashSet1 = new TDoubleHashSet(int0);
      TDoubleSetDecorator tDoubleSetDecorator1 = new TDoubleSetDecorator((TDoubleSet) tDoubleHashSet1);
      double[] doubleArray1 = new double[6];
      doubleArray1[0] = (double) int0;
      doubleArray1[1] = (double) int0;
      doubleArray1[2] = (double) int0;
      double double0 = 1596.2174619281443;
      doubleArray1[3] = double0;
      doubleArray1[4] = (double) int0;
      doubleArray1[5] = (double) int0;
      tDoubleHashSet1.removeAll(doubleArray1);
      tDoubleSetDecorator1.toArray();
      tDoubleHashSet1.toString();
      TDoubleSetDecorator tDoubleSetDecorator2 = new TDoubleSetDecorator((TDoubleSet) tDoubleHashSet1);
      TDoubleHashSet tDoubleHashSet2 = (TDoubleHashSet)tDoubleSetDecorator2.getSet();
      TDoubleSetDecorator tDoubleSetDecorator3 = new TDoubleSetDecorator((TDoubleSet) tDoubleHashSet2);
      TDoubleHashSet tDoubleHashSet3 = (TDoubleHashSet)tDoubleSetDecorator3.getSet();
      assertFalse(tDoubleSetDecorator2.equals(tDoubleSetDecorator0));
      assertNotNull(tDoubleHashSet3);
      assertFalse(tDoubleSetDecorator3.equals(tDoubleSetDecorator0));
  }

  @Test
  public void test4()  throws Throwable  {
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet();
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      HashSet<Object> hashSet0 = new HashSet<Object>();
      // Undeclared exception!
      try {
        tDoubleSetDecorator0.retainAll((Collection<?>) hashSet0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test5()  throws Throwable  {
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      double double0 = 0.0;
      Double double1 = new Double(double0);
      // Undeclared exception!
      try {
        tDoubleSetDecorator0.add(double1);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test6()  throws Throwable  {
      int int0 = (-1134);
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(int0, int0, int0);
      double[] doubleArray0 = new double[7];
      double double0 = 458.2165675209519;
      doubleArray0[0] = double0;
      doubleArray0[1] = (double) int0;
      double double1 = 1926.9272779979378;
      doubleArray0[2] = double1;
      doubleArray0[3] = (double) int0;
      doubleArray0[4] = (double) int0;
      double double2 = 1761.1584023055311;
      doubleArray0[5] = double2;
      doubleArray0[6] = (double) int0;
      tDoubleHashSet0.toArray(doubleArray0);
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator((TDoubleSet) tDoubleHashSet0);
      tDoubleSetDecorator0.clear();
      assertEquals(true, tDoubleSetDecorator0.isEmpty());
  }

  @Test
  public void test7()  throws Throwable  {
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      double double0 = 0.0;
      Double double1 = new Double(double0);
      // Undeclared exception!
      try {
        tDoubleSetDecorator0.add(double1);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test8()  throws Throwable  {
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      String string0 = "from > to : ";
      try {
        Double.valueOf(string0);
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // For input string: \"from > to :\"
         //
      }
  }

  @Test
  public void test9()  throws Throwable  {
      TDoubleSet tDoubleSet0 = null;
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator(tDoubleSet0);
      LinkedHashSet<Object> linkedHashSet0 = new LinkedHashSet<Object>();
      boolean boolean0 = tDoubleSetDecorator0.remove((Object) linkedHashSet0);
      assertEquals(false, boolean0);
  }

  @Test
  public void test10()  throws Throwable  {
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      // Undeclared exception!
      try {
        tDoubleSetDecorator0.isEmpty();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test11()  throws Throwable  {
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      Double double0 = null;
      tDoubleSetDecorator0.add(double0);
      String string0 = "";
      // Undeclared exception!
      try {
        tDoubleSetDecorator0.toString();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test12()  throws Throwable  {
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      TreeSet<TDoubleHashSet> treeSet0 = new TreeSet<TDoubleHashSet>();
      Double double0 = null;
      boolean boolean0 = tDoubleSetDecorator0.add(double0);
      int int0 = 1;
      ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream(int0);
      PrintStream printStream0 = new PrintStream((OutputStream) byteArrayOutputStream0, boolean0);
      Locale locale0 = Locale.PRC;
      String string0 = "";
      Object[] objectArray0 = new Object[9];
      objectArray0[0] = (Object) string0;
      objectArray0[1] = (Object) boolean0;
      objectArray0[2] = (Object) byteArrayOutputStream0;
      objectArray0[3] = (Object) printStream0;
      objectArray0[4] = (Object) treeSet0;
      objectArray0[5] = (Object) int0;
      objectArray0[6] = (Object) double0;
      objectArray0[7] = (Object) byteArrayOutputStream0;
      objectArray0[8] = (Object) double0;
      PrintStream printStream1 = printStream0.printf(locale0, string0, objectArray0);
      PrintStream printStream2 = printStream1.format(string0, objectArray0);
      PrintStream printStream3 = printStream2.format(locale0, string0, objectArray0);
      ObjectOutputStream objectOutputStream0 = new ObjectOutputStream((OutputStream) printStream3);
      tDoubleSetDecorator0.writeExternal((ObjectOutput) objectOutputStream0);
      // Undeclared exception!
      try {
        tDoubleSetDecorator0.removeAll((Collection<?>) treeSet0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test13()  throws Throwable  {
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      // Undeclared exception!
      try {
        tDoubleSetDecorator0.size();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test14()  throws Throwable  {
      double[] doubleArray0 = new double[1];
      double double0 = 1.0;
      doubleArray0[0] = double0;
      TDoubleHashSet tDoubleHashSet0 = new TDoubleHashSet(doubleArray0);
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator((TDoubleSet) tDoubleHashSet0);
      tDoubleSetDecorator0.clear();
      int int0 = (-1202);
      TDoubleSetDecorator tDoubleSetDecorator1 = new TDoubleSetDecorator();
      TDoubleSetDecorator tDoubleSetDecorator2 = new TDoubleSetDecorator();
      // Undeclared exception!
      try {
        tDoubleSetDecorator2.clear();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test15()  throws Throwable  {
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      double double0 = 0.0;
      // Undeclared exception!
      try {
        tDoubleSetDecorator0.iterator();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test16()  throws Throwable  {
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      TreeSet<Double> treeSet0 = new TreeSet<Double>();
      tDoubleSetDecorator0.containsAll((Collection<?>) treeSet0);
      tDoubleSetDecorator0.getSet();
      // Undeclared exception!
      try {
        tDoubleSetDecorator0.clear();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test17()  throws Throwable  {
      TDoubleSetDecorator tDoubleSetDecorator0 = new TDoubleSetDecorator();
      double double0 = Double.POSITIVE_INFINITY;
      Double double1 = new Double(double0);
      // Undeclared exception!
      try {
        tDoubleSetDecorator0.add(double1);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }
}
