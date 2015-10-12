/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.decorator;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.decorator.TShortListDecorator;
import gnu.trove.list.TShortList;
import gnu.trove.list.array.TShortArrayList;
import gnu.trove.list.linked.TShortLinkedList;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TShortListDecoratorEvoSuite_Branch {

  @BeforeClass 
  public static void initEvoSuiteFramework(){ 
    org.evosuite.utils.LoggingUtils.loadLogbackForEvoSuite(); 
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


  //Test case number: 0
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TShortListDecorator.<init>()V: root-Branch
   */

  @Test
  public void test0()  throws Throwable  {
      TShortListDecorator tShortListDecorator0 = new TShortListDecorator();
  }

  //Test case number: 1
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TShortListDecorator.writeExternal(Ljava/io/ObjectOutput;)V: root-Branch
   * 2 gnu.trove.decorator.TShortListDecorator.<init>(Lgnu/trove/list/TShortList;)V: root-Branch
   */

  @Test
  public void test1()  throws Throwable  {
      short[] shortArray0 = new short[7];
      TShortArrayList tShortArrayList0 = TShortArrayList.wrap(shortArray0, (short)0);
      TShortLinkedList tShortLinkedList0 = new TShortLinkedList((TShortList) tShortArrayList0);
      TShortListDecorator tShortListDecorator0 = new TShortListDecorator((TShortList) tShortLinkedList0);
      ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream(142);
      ObjectOutputStream objectOutputStream0 = new ObjectOutputStream((OutputStream) byteArrayOutputStream0);
      tShortListDecorator0.writeExternal((ObjectOutput) objectOutputStream0);
      assertEquals("\uFFFD\uFFFD\u0000\u0005w\u0001\u0000sr\u0000&gnu.trove.list.linked.TShortLinkedList\uFFFDs\u01CE\uFFFD\uFFFD()\f\u0000\u0000xpw\u0015\u0000\u0000\u0000\u0000\u0000\u0000\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000x", byteArrayOutputStream0.toString());
      assertEquals(86, byteArrayOutputStream0.size());
  }

  //Test case number: 2
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TShortListDecorator.getList()Lgnu/trove/list/TShortList;: root-Branch
   */

  @Test
  public void test2()  throws Throwable  {
      short[] shortArray0 = new short[4];
      TShortArrayList tShortArrayList0 = new TShortArrayList(shortArray0);
      TShortListDecorator tShortListDecorator0 = new TShortListDecorator((TShortList) tShortArrayList0);
      TShortArrayList tShortArrayList1 = (TShortArrayList)tShortListDecorator0.getList();
      assertEquals(4, tShortArrayList1.size());
  }

  //Test case number: 3
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TShortListDecorator.readExternal(Ljava/io/ObjectInput;)V: root-Branch
   */

  @Test
  public void test3()  throws Throwable  {
      TShortArrayList tShortArrayList0 = new TShortArrayList(0);
      TShortListDecorator tShortListDecorator0 = new TShortListDecorator((TShortList) tShortArrayList0);
      // Undeclared exception!
      try {
        tShortListDecorator0.readExternal((ObjectInput) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  //Test case number: 4
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TShortListDecorator.add(ILjava/lang/Short;)V: root-Branch
   * 2 gnu.trove.decorator.TShortListDecorator.size()I: root-Branch
   */

//   @Test
//   public void test4()  throws Throwable  {
//       TShortArrayList tShortArrayList0 = new TShortArrayList(0);
//       TShortListDecorator tShortListDecorator0 = new TShortListDecorator((TShortList) tShortArrayList0);
//       Short short0 = new Short((short)999);
//       tShortListDecorator0.add(short0);
//       assertEquals(999, tShortArrayList0.sum());
//       assertEquals(false, tShortArrayList0.isEmpty());
//   }

  //Test case number: 5
  /*
   * 5 covered goals:
   * 1 gnu.trove.decorator.TShortListDecorator.get(I)Ljava/lang/Short;: I13 Branch 1 IF_ICMPNE L93 - true
   * 2 gnu.trove.decorator.TShortListDecorator.get(I)Ljava/lang/Short;: I13 Branch 1 IF_ICMPNE L93 - false
   * 3 gnu.trove.decorator.TShortListDecorator.remove(I)Ljava/lang/Short;: I13 Branch 3 IF_ICMPNE L115 - true
   * 4 gnu.trove.decorator.TShortListDecorator.remove(I)Ljava/lang/Short;: I13 Branch 3 IF_ICMPNE L115 - false
   * 5 gnu.trove.decorator.TShortListDecorator.size()I: root-Branch
   */

  @Test
  public void test5()  throws Throwable  {
      short[] shortArray0 = new short[7];
      shortArray0[0] = (short) (-5503);
      TShortArrayList tShortArrayList0 = TShortArrayList.wrap(shortArray0, (short)0);
      TShortLinkedList tShortLinkedList0 = new TShortLinkedList((TShortList) tShortArrayList0);
      TShortListDecorator tShortListDecorator0 = new TShortListDecorator((TShortList) tShortLinkedList0);
      LinkedList<Object> linkedList0 = new LinkedList<Object>();
      boolean boolean0 = tShortListDecorator0.retainAll((Collection<?>) linkedList0);
      assertEquals(true, tShortLinkedList0.isEmpty());
      assertEquals(true, boolean0);
  }

  //Test case number: 6
  /*
   * 1 covered goal:
   * 1 gnu.trove.decorator.TShortListDecorator.set(ILjava/lang/Short;)Ljava/lang/Short;: I15 Branch 2 IF_ICMPNE L101 - true
   */

  @Test
  public void test6()  throws Throwable  {
      short[] shortArray0 = new short[7];
      shortArray0[0] = (short) (-5503);
      TShortArrayList tShortArrayList0 = TShortArrayList.wrap(shortArray0, (short)0);
      TShortLinkedList tShortLinkedList0 = new TShortLinkedList((TShortList) tShortArrayList0);
      TShortListDecorator tShortListDecorator0 = new TShortListDecorator((TShortList) tShortLinkedList0);
      Short short0 = tShortListDecorator0.set((int) (short)0, (Short) shortArray0[5]);
      assertEquals("[null, null, null, null, null, null, null]", tShortListDecorator0.toString());
      assertEquals((short) (-5503), (short)short0);
  }

  //Test case number: 7
  /*
   * 2 covered goals:
   * 1 gnu.trove.decorator.TShortListDecorator.set(ILjava/lang/Short;)Ljava/lang/Short;: I15 Branch 2 IF_ICMPNE L101 - false
   * 2 gnu.trove.decorator.TShortListDecorator.<init>(Lgnu/trove/list/TShortList;)V: root-Branch
   */

  @Test
  public void test7()  throws Throwable  {
      short[] shortArray0 = new short[4];
      TShortArrayList tShortArrayList0 = new TShortArrayList(shortArray0);
      TShortListDecorator tShortListDecorator0 = new TShortListDecorator((TShortList) tShortArrayList0);
      tShortListDecorator0.set((int) (short)0, (Short) (short)408);
      assertEquals("{408, 0, 0, 0}", tShortArrayList0.toString());
      assertEquals("[408, null, null, null]", tShortListDecorator0.toString());
  }
}
