/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.map.hash.TByteShortHashMap;
import gnu.trove.map.hash.TLongDoubleHashMap;
import gnu.trove.set.hash.TLongHashSet;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class TPrimitiveHashEvoSuite_DefUse {

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
   * 13 covered goals:
   * 1 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method removeAt(I)V
	Use 7 for Parameter-Variable "removeAt(I)V_LV_1" in removeAt(I)V.4 root-Branch Line 118
   * 2 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method removeAt(I)V
	Use 8 for Parameter-Variable "removeAt(I)V_LV_1" in removeAt(I)V.10 root-Branch Line 119
   * 3 gnu.trove.impl.hash.TPrimitiveHash.<init>(I)V: root-Branch
   * 4 gnu.trove.impl.hash.TPrimitiveHash.removeAt(I)V: root-Branch
   * 5 INTRA_METHOD-Definition-Use-Pair
	Definition 1 for Local-Variable "<init>(IF)V_LV_1" in <init>(IF)V.9 root-Branch Line 95
	Use 3 for Local-Variable "<init>(IF)V_LV_1" in <init>(IF)V.18 root-Branch Line 97
   * 6 INTRA_METHOD-Definition-Use-Pair
	Definition 3 for Local-Variable "setUp(I)I_LV_2" in setUp(I)I.5 root-Branch Line 133
	Use 10 for Local-Variable "setUp(I)I_LV_2" in setUp(I)I.9 root-Branch Line 134
   * 7 INTRA_METHOD-Definition-Use-Pair
	Definition 3 for Local-Variable "setUp(I)I_LV_2" in setUp(I)I.5 root-Branch Line 133
	Use 11 for Local-Variable "setUp(I)I_LV_2" in setUp(I)I.14 root-Branch Line 135
   * 8 PARAMETER-Definition-Use-Pair
	Parameter-Definition 2 for method <init>(IF)V
	Use 4 for Parameter-Variable "<init>(IF)V_LV_2" in <init>(IF)V.20 root-Branch Line 97
   * 9 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method setUp(I)I
	Use 9 for Parameter-Variable "setUp(I)I_LV_1" in setUp(I)I.3 root-Branch Line 133
   * 10 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method <init>(IF)V
	Use 1 for Parameter-Variable "<init>(IF)V_LV_1" in <init>(IF)V.7 root-Branch Line 95
   * 11 PARAMETER-Definition-Use-Pair
	Parameter-Definition 2 for method <init>(IF)V
	Use 2 for Parameter-Variable "<init>(IF)V_LV_2" in <init>(IF)V.13 root-Branch Line 96
   * 12 gnu.trove.impl.hash.TPrimitiveHash.setUp(I)I: root-Branch
   * 13 gnu.trove.impl.hash.TPrimitiveHash.<init>(IF)V: root-Branch
   */

  @Test
  public void test0()  throws Throwable  {
      TLongDoubleHashMap tLongDoubleHashMap0 = new TLongDoubleHashMap(1109, 0.0F, (long) 1109, 1946.226774876109);
      TLongHashSet tLongHashSet0 = new TLongHashSet(tLongDoubleHashMap0._set);
      tLongHashSet0.removeAll(tLongDoubleHashMap0._set);
      assertEquals(true, tLongHashSet0.isEmpty());
      assertEquals(23, tLongHashSet0.capacity());
  }

  //Test case number: 1
  /*
   * 9 covered goals:
   * 1 INTRA_CLASS-Definition-Use-Pair
	Definition 4 for Field-Variable "gnu/trove/impl/hash/TPrimitiveHash._states" in setUp(I)I.11 root-Branch Line 134
	Use 5 for Field-Variable "gnu/trove/impl/hash/TPrimitiveHash._states" in capacity()I.3 root-Branch Line 108
   * 2 gnu.trove.impl.hash.TPrimitiveHash.capacity()I: root-Branch
   * 3 INTRA_METHOD-Definition-Use-Pair
	Definition 1 for Local-Variable "<init>(IF)V_LV_1" in <init>(IF)V.9 root-Branch Line 95
	Use 3 for Local-Variable "<init>(IF)V_LV_1" in <init>(IF)V.18 root-Branch Line 97
   * 4 INTRA_METHOD-Definition-Use-Pair
	Definition 3 for Local-Variable "setUp(I)I_LV_2" in setUp(I)I.5 root-Branch Line 133
	Use 10 for Local-Variable "setUp(I)I_LV_2" in setUp(I)I.9 root-Branch Line 134
   * 5 INTRA_METHOD-Definition-Use-Pair
	Definition 3 for Local-Variable "setUp(I)I_LV_2" in setUp(I)I.5 root-Branch Line 133
	Use 11 for Local-Variable "setUp(I)I_LV_2" in setUp(I)I.14 root-Branch Line 135
   * 6 PARAMETER-Definition-Use-Pair
	Parameter-Definition 2 for method <init>(IF)V
	Use 4 for Parameter-Variable "<init>(IF)V_LV_2" in <init>(IF)V.20 root-Branch Line 97
   * 7 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method setUp(I)I
	Use 9 for Parameter-Variable "setUp(I)I_LV_1" in setUp(I)I.3 root-Branch Line 133
   * 8 PARAMETER-Definition-Use-Pair
	Parameter-Definition 1 for method <init>(IF)V
	Use 1 for Parameter-Variable "<init>(IF)V_LV_1" in <init>(IF)V.7 root-Branch Line 95
   * 9 PARAMETER-Definition-Use-Pair
	Parameter-Definition 2 for method <init>(IF)V
	Use 2 for Parameter-Variable "<init>(IF)V_LV_2" in <init>(IF)V.13 root-Branch Line 96
   */

  @Test
  public void test1()  throws Throwable  {
      TByteShortHashMap tByteShortHashMap0 = new TByteShortHashMap(0, 0);
      tByteShortHashMap0.clear();
      assertEquals(3, tByteShortHashMap0.capacity());
  }
}
