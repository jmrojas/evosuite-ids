/**
 * Copyright (C) 2010-2016 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * This file was automatically generated by EvoSuite
 * Mon Dec 14 19:50:46 GMT 2015
 */

package org.evosuite.runtime.util;

import org.junit.Test;
import static org.junit.Assert.*;
import static shaded.org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.util.Inputs;
import org.junit.runner.RunWith;
import shaded.org.evosuite.runtime.EvoRunner;
import shaded.org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true) 
public class Inputs_ESTest extends Inputs_ESTest_scaffolding {

  @Test
  public void test0()  throws Throwable  {
      Inputs inputs0 = new Inputs();
  }

  @Test
  public void test1()  throws Throwable  {
      Object[] objectArray0 = new Object[1];
      Object object0 = new Object();
      objectArray0[0] = object0;
      Inputs.checkNull(objectArray0);
  }

  @Test
  public void test2()  throws Throwable  {
      try { 
        Inputs.checkNull((Object[]) null);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // No inputs to check
         //
         assertThrownBy("org.evosuite.runtime.util.Inputs", e);
      }
  }

  @Test
  public void test3()  throws Throwable  {
      Object[] objectArray0 = new Object[1];
      try { 
        Inputs.checkNull(objectArray0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Null input in position 0
         //
         assertThrownBy("org.evosuite.runtime.util.Inputs", e);
      }
  }
}
