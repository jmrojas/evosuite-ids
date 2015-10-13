/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.sync;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.impl.sync.SynchronizedSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class SynchronizedSetEvoSuite_Random {

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
      TreeSet<Object> treeSet0 = new TreeSet<Object>();
      LinkedHashSet<Integer> linkedHashSet0 = new LinkedHashSet<Integer>();
      HashSet<Object> hashSet0 = new HashSet<Object>();
      String string0 = linkedHashSet0.toString();
      SynchronizedSet<Object> synchronizedSet0 = new SynchronizedSet<Object>(hashSet0, string0);
      String string1 = synchronizedSet0.toString();
      SynchronizedSet<Object> synchronizedSet1 = new SynchronizedSet<Object>(hashSet0, string1);
      synchronizedSet1.iterator();
      String string2 = linkedHashSet0.toString();
      SynchronizedSet<Object> synchronizedSet2 = new SynchronizedSet<Object>(treeSet0, string2);
      LinkedHashSet<Object> linkedHashSet1 = new LinkedHashSet<Object>();
      synchronizedSet0.removeAll((Collection<?>) hashSet0);
      synchronizedSet2.isEmpty();
      boolean boolean0 = synchronizedSet2.removeAll((Collection<?>) linkedHashSet1);
      TreeSet<String> treeSet1 = new TreeSet<String>();
      String string3 = synchronizedSet0.toString();
      SynchronizedSet<String> synchronizedSet3 = new SynchronizedSet<String>(treeSet1, string3);
      Object[] objectArray0 = new Object[2];
      objectArray0[0] = (Object) synchronizedSet0;
      objectArray0[1] = (Object) string0;
      synchronizedSet3.toArray(objectArray0);
      String string4 = linkedHashSet1.toString();
      String string5 = (String)synchronizedSet1.mutex;
      hashSet0.add((Object) string5);
      SynchronizedSet<Object> synchronizedSet4 = new SynchronizedSet<Object>(synchronizedSet2, string4);
      boolean boolean1 = synchronizedSet4.contains((Object) string2);
      assertTrue(boolean1 == boolean0);
  }

  @Test
  public void test1()  throws Throwable  {
      TreeSet<Integer> treeSet0 = new TreeSet<Integer>();
      TreeSet<Object> treeSet1 = new TreeSet<Object>();
      String string0 = treeSet1.toString();
      SynchronizedSet<Integer> synchronizedSet0 = new SynchronizedSet<Integer>(treeSet0, string0);
      synchronizedSet0.toString();
      Object object0 = new Object();
      SynchronizedSet<Object> synchronizedSet1 = new SynchronizedSet<Object>(treeSet1, object0);
      // Undeclared exception!
      try {
        synchronizedSet1.remove(object0);
        fail("Expecting exception: ClassCastException");
      
      } catch(ClassCastException e) {
         //
         // java.lang.Object cannot be cast to java.lang.Comparable
         //
      }
  }

  @Test
  public void test2()  throws Throwable  {
      int int0 = (-1);
      LinkedHashSet<String> linkedHashSet0 = null;
      try {
        linkedHashSet0 = new LinkedHashSet<String>(int0, int0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal initial capacity: -1
         //
      }
  }

  @Test
  public void test3()  throws Throwable  {
      TreeSet<Integer> treeSet0 = new TreeSet<Integer>();
      LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
      String string0 = linkedList0.toString();
      SynchronizedSet<Integer> synchronizedSet0 = new SynchronizedSet<Integer>(treeSet0, string0);
      Object object0 = null;
      // Undeclared exception!
      try {
        synchronizedSet0.contains(object0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test4()  throws Throwable  {
      HashSet<String> hashSet0 = new HashSet<String>();
      TreeSet<Object> treeSet0 = new TreeSet<Object>();
      String string0 = treeSet0.toString();
      SynchronizedSet<String> synchronizedSet0 = new SynchronizedSet<String>(hashSet0, string0);
      synchronizedSet0.add(string0);
      HashSet<Object> hashSet1 = new HashSet<Object>();
      LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
      String string1 = linkedList0.toString();
      SynchronizedSet<Object> synchronizedSet1 = new SynchronizedSet<Object>(hashSet1, string1);
      synchronizedSet1.toArray();
      LinkedHashSet<Object> linkedHashSet0 = new LinkedHashSet<Object>();
      String string2 = linkedHashSet0.toString();
      SynchronizedSet<Object> synchronizedSet2 = new SynchronizedSet<Object>(treeSet0, string2);
      String string3 = synchronizedSet2.toString();
      SynchronizedSet<Object> synchronizedSet3 = new SynchronizedSet<Object>(treeSet0, string3);
      String string4 = synchronizedSet3.toString();
      synchronizedSet3.add((Object) string4);
      String string5 = linkedList0.toString();
      SynchronizedSet<Object> synchronizedSet4 = new SynchronizedSet<Object>(hashSet1, string5);
      TreeSet<Integer> treeSet1 = new TreeSet<Integer>();
      HashSet<Integer> hashSet2 = new HashSet<Integer>();
      String string6 = hashSet2.toString();
      SynchronizedSet<Integer> synchronizedSet5 = new SynchronizedSet<Integer>(treeSet1, string6);
      SynchronizedSet<Integer> synchronizedSet6 = new SynchronizedSet<Integer>(synchronizedSet5, string5);
      String string7 = (String)synchronizedSet6.mutex;
      synchronizedSet6.equals((Object) string7);
      synchronizedSet4.add((Object) string7);
      synchronizedSet4.add((Object) string7);
      Object object0 = new Object();
      synchronizedSet4.isEmpty();
      SynchronizedSet<Object> synchronizedSet7 = new SynchronizedSet<Object>(hashSet1, object0);
      SynchronizedSet<Object> synchronizedSet8 = new SynchronizedSet<Object>(synchronizedSet7, object0);
      synchronizedSet8.clear();
      assertTrue(synchronizedSet7.equals(synchronizedSet1));
      assertTrue(synchronizedSet8.equals(synchronizedSet7));
  }

  @Test
  public void test5()  throws Throwable  {
      TreeSet<String> treeSet0 = new TreeSet<String>();
      HashSet<Integer> hashSet0 = new HashSet<Integer>();
      hashSet0.isEmpty();
      TreeSet<Object> treeSet1 = new TreeSet<Object>();
      String string0 = "";
      SynchronizedSet<Object> synchronizedSet0 = new SynchronizedSet<Object>(treeSet1, string0);
      Object object0 = null;
      SynchronizedSet<String> synchronizedSet1 = new SynchronizedSet<String>(treeSet0, object0);
      // Undeclared exception!
      try {
        synchronizedSet0.add(object0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test6()  throws Throwable  {
      TreeSet<Object> treeSet0 = new TreeSet<Object>();
      LinkedHashSet<Object> linkedHashSet0 = new LinkedHashSet<Object>();
      HashSet<Integer> hashSet0 = new HashSet<Integer>();
      String string0 = hashSet0.toString();
      SynchronizedSet<Object> synchronizedSet0 = new SynchronizedSet<Object>(linkedHashSet0, string0);
      String string1 = synchronizedSet0.toString();
      SynchronizedSet<Object> synchronizedSet1 = new SynchronizedSet<Object>(treeSet0, string1);
      LinkedList<Object> linkedList0 = new LinkedList<Object>();
      synchronizedSet1.retainAll((Collection<?>) linkedList0);
      String string2 = linkedHashSet0.toString();
      SynchronizedSet<Object> synchronizedSet2 = new SynchronizedSet<Object>(treeSet0, string2);
      Object[] objectArray0 = synchronizedSet2.toArray();
      assertNotNull(objectArray0);
  }

  @Test
  public void test7()  throws Throwable  {
      LinkedHashSet<String> linkedHashSet0 = new LinkedHashSet<String>();
      HashSet<Integer> hashSet0 = new HashSet<Integer>();
      String string0 = hashSet0.toString();
      linkedHashSet0.toArray();
      SynchronizedSet<String> synchronizedSet0 = new SynchronizedSet<String>(linkedHashSet0, string0);
      synchronizedSet0.toString();
      Set<Object> set0 = null;
      LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
      String string1 = linkedList0.toString();
      SynchronizedSet<Integer> synchronizedSet1 = new SynchronizedSet<Integer>(hashSet0, string1);
      String string2 = (String)synchronizedSet1.mutex;
      SynchronizedSet<Object> synchronizedSet2 = new SynchronizedSet<Object>(set0, string2);
      SynchronizedSet<Object> synchronizedSet3 = new SynchronizedSet<Object>(synchronizedSet2, string2);
      SynchronizedSet<Object> synchronizedSet4 = new SynchronizedSet<Object>(synchronizedSet3, string2);
      // Undeclared exception!
      try {
        synchronizedSet4.addAll((Collection<?>) linkedHashSet0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test8()  throws Throwable  {
      HashSet<Object> hashSet0 = new HashSet<Object>();
      LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
      linkedList0.toString();
      Object object0 = null;
      LinkedHashSet<String> linkedHashSet0 = new LinkedHashSet<String>();
      SynchronizedSet<String> synchronizedSet0 = new SynchronizedSet<String>(linkedHashSet0, object0);
      linkedHashSet0.clear();
      SynchronizedSet<Object> synchronizedSet1 = new SynchronizedSet<Object>(hashSet0, object0);
      // Undeclared exception!
      try {
        synchronizedSet1.isEmpty();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test9()  throws Throwable  {
      HashSet<Integer> hashSet0 = new HashSet<Integer>();
      String string0 = "W4DV {Ac8ER gucZl";
      SynchronizedSet<Integer> synchronizedSet0 = new SynchronizedSet<Integer>(hashSet0, string0);
      String string1 = synchronizedSet0.toString();
      assertNotSame(string0, string1);
  }

  @Test
  public void test10()  throws Throwable  {
      LinkedHashSet<Integer> linkedHashSet0 = new LinkedHashSet<Integer>();
      Set<Object> set0 = null;
      TreeSet<Object> treeSet0 = new TreeSet<Object>();
      LinkedHashSet<Object> linkedHashSet1 = new LinkedHashSet<Object>();
      String string0 = linkedHashSet1.toString();
      treeSet0.add((Object) string0);
      String string1 = treeSet0.toString();
      treeSet0.descendingSet();
      SynchronizedSet<Object> synchronizedSet0 = new SynchronizedSet<Object>(set0, string1);
      // Undeclared exception!
      try {
        synchronizedSet0.toString();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test11()  throws Throwable  {
      int int0 = (-1);
      HashSet<Object> hashSet0 = null;
      try {
        hashSet0 = new HashSet<Object>(int0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal initial capacity: -1
         //
      }
  }

  @Test
  public void test12()  throws Throwable  {
      LinkedHashSet<Object> linkedHashSet0 = new LinkedHashSet<Object>();
      String string0 = linkedHashSet0.toString();
      boolean boolean0 = linkedHashSet0.add((Object) string0);
      Object object0 = null;
      LinkedHashSet<Integer> linkedHashSet1 = new LinkedHashSet<Integer>();
      SynchronizedSet<Integer> synchronizedSet0 = new SynchronizedSet<Integer>(linkedHashSet1, object0);
      Object[] objectArray0 = new Object[5];
      objectArray0[0] = (Object) boolean0;
      objectArray0[1] = (Object) linkedHashSet0;
      objectArray0[2] = (Object) string0;
      objectArray0[3] = (Object) boolean0;
      objectArray0[4] = (Object) linkedHashSet1;
      // Undeclared exception!
      try {
        synchronizedSet0.toArray(objectArray0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test13()  throws Throwable  {
      int int0 = (-1054);
      LinkedHashSet<Integer> linkedHashSet0 = null;
      try {
        linkedHashSet0 = new LinkedHashSet<Integer>(int0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal initial capacity: -1054
         //
      }
  }

  @Test
  public void test14()  throws Throwable  {
      Set<Integer> set0 = null;
      TreeSet<Object> treeSet0 = new TreeSet<Object>();
      String string0 = treeSet0.toString();
      HashSet<Object> hashSet0 = new HashSet<Object>();
      HashSet<Integer> hashSet1 = new HashSet<Integer>();
      String string1 = hashSet1.toString();
      SynchronizedSet<Object> synchronizedSet0 = new SynchronizedSet<Object>(hashSet0, string1);
      String string2 = (String)synchronizedSet0.mutex;
      SynchronizedSet<Object> synchronizedSet1 = new SynchronizedSet<Object>(treeSet0, string2);
      synchronizedSet1.remove((Object) string2);
      SynchronizedSet<Integer> synchronizedSet2 = new SynchronizedSet<Integer>(set0, string0);
      // Undeclared exception!
      try {
        synchronizedSet2.retainAll((Collection<?>) treeSet0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test15()  throws Throwable  {
      Set<String> set0 = null;
      TreeSet<Object> treeSet0 = new TreeSet<Object>();
      String string0 = treeSet0.toString();
      boolean boolean0 = false;
      NavigableSet<Object> navigableSet0 = treeSet0.headSet((Object) string0, boolean0);
      Object object0 = null;
      SynchronizedSet<Object> synchronizedSet0 = new SynchronizedSet<Object>(navigableSet0, object0);
      // Undeclared exception!
      try {
        synchronizedSet0.toString();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test16()  throws Throwable  {
      TreeSet<String> treeSet0 = new TreeSet<String>();
      Set<Object> set0 = null;
      Object object0 = new Object();
      SynchronizedSet<Object> synchronizedSet0 = new SynchronizedSet<Object>(set0, object0);
      SynchronizedSet<Object> synchronizedSet1 = new SynchronizedSet<Object>(synchronizedSet0, object0);
      String[] stringArray0 = new String[7];
      String string0 = "bOe'I%PWi";
      stringArray0[0] = string0;
      String string1 = "";
      stringArray0[1] = string1;
      String string2 = "";
      stringArray0[2] = string2;
      String string3 = "";
      stringArray0[3] = string3;
      String string4 = "%eF)4*";
      stringArray0[4] = string4;
      String string5 = "HcFA $r";
      stringArray0[5] = string5;
      String string6 = "";
      stringArray0[6] = string6;
      // Undeclared exception!
      try {
        synchronizedSet1.toArray(stringArray0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test17()  throws Throwable  {
      int int0 = 0;
      HashSet<Integer> hashSet0 = new HashSet<Integer>();
      String string0 = "";
      SynchronizedSet<Integer> synchronizedSet0 = new SynchronizedSet<Integer>(hashSet0, string0);
      synchronizedSet0.size();
      LinkedHashSet<Integer> linkedHashSet0 = null;
      try {
        linkedHashSet0 = new LinkedHashSet<Integer>(int0, int0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal load factor: 0.0
         //
      }
  }

  @Test
  public void test18()  throws Throwable  {
      Set<Integer> set0 = null;
      LinkedHashSet<Object> linkedHashSet0 = new LinkedHashSet<Object>();
      String string0 = linkedHashSet0.toString();
      SynchronizedSet<Integer> synchronizedSet0 = new SynchronizedSet<Integer>(set0, string0);
      // Undeclared exception!
      try {
        synchronizedSet0.containsAll((Collection<?>) linkedHashSet0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test
  public void test19()  throws Throwable  {
      Set<Object> set0 = null;
      TreeSet<Integer> treeSet0 = new TreeSet<Integer>();
      HashSet<Object> hashSet0 = new HashSet<Object>();
      TreeSet<Object> treeSet1 = new TreeSet<Object>();
      TreeSet<Object> treeSet2 = new TreeSet<Object>((SortedSet<Object>) treeSet1);
      String string0 = treeSet2.toString();
      SynchronizedSet<Object> synchronizedSet0 = new SynchronizedSet<Object>(hashSet0, string0);
      synchronizedSet0.iterator();
      Object object0 = null;
      SynchronizedSet<Integer> synchronizedSet1 = new SynchronizedSet<Integer>(treeSet0, object0);
      // Undeclared exception!
      try {
        synchronizedSet1.contains(object0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }
}
