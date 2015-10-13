/*
 * This file was automatically generated by EvoSuite
 */

package gnu.trove.impl;

import org.junit.Test;
import static org.junit.Assert.*;
import gnu.trove.impl.PrimeFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class PrimeFinderEvoSuite_DefUse {

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
   * 5 covered goals:
   * 1 PARAMETER-Definition-Use-Pair
	Parameter-Definition 0 for method nextPrime(I)I
	Use 2 for Parameter-Variable "nextPrime(I)I_LV_0" in nextPrime(I)I.3 root-Branch Line 150
   * 2 INTRA_METHOD-Definition-Use-Pair
	Definition 1 for Local-Variable "nextPrime(I)I_LV_1" in nextPrime(I)I.5 root-Branch Line 150
	Use 3 for Local-Variable "nextPrime(I)I_LV_1" in nextPrime(I)I.8 root-Branch Line 151
   * 3 INTRA_METHOD-Definition-Use-Pair
	Definition 1 for Local-Variable "nextPrime(I)I_LV_1" in nextPrime(I)I.5 root-Branch Line 150
	Use 4 for Local-Variable "nextPrime(I)I_LV_1" in nextPrime(I)I.12 Branch 1f Line 154
   * 4 INTRA_METHOD-Definition-Use-Pair
	Definition 2 for Local-Variable "nextPrime(I)I_LV_1" in nextPrime(I)I.16 Branch 1f Line 154
	Use 6 for Local-Variable "nextPrime(I)I_LV_1" in nextPrime(I)I.20 root-Branch Line 156
   * 5 gnu.trove.impl.PrimeFinder.nextPrime(I)I: I9 Branch 1 IFGE L151 - false
   */

  @Test
  public void test0()  throws Throwable  {
      int int0 = PrimeFinder.nextPrime(15);
      assertEquals(17, int0);
  }

  //Test case number: 1
  /*
   * 4 covered goals:
   * 1 INTRA_METHOD-Definition-Use-Pair
	Definition 1 for Local-Variable "nextPrime(I)I_LV_1" in nextPrime(I)I.5 root-Branch Line 150
	Use 6 for Local-Variable "nextPrime(I)I_LV_1" in nextPrime(I)I.20 root-Branch Line 156
   * 2 gnu.trove.impl.PrimeFinder.nextPrime(I)I: I9 Branch 1 IFGE L151 - true
   * 3 INTRA_METHOD-Definition-Use-Pair
	Definition 1 for Local-Variable "nextPrime(I)I_LV_1" in nextPrime(I)I.5 root-Branch Line 150
	Use 3 for Local-Variable "nextPrime(I)I_LV_1" in nextPrime(I)I.8 root-Branch Line 151
   * 4 PARAMETER-Definition-Use-Pair
	Parameter-Definition 0 for method nextPrime(I)I
	Use 2 for Parameter-Variable "nextPrime(I)I_LV_0" in nextPrime(I)I.3 root-Branch Line 150
   */

  @Test
  public void test1()  throws Throwable  {
      int int0 = PrimeFinder.nextPrime(17);
      assertEquals(17, int0);
  }
}
