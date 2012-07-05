/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 *
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package org.evosuite.ma.gui;

import javax.swing.JFrame;

import org.evosuite.ma.Editor;


/**
 * <p>TestEditorGUI interface.</p>
 *
 * @author Yury Pavlov
 */
public interface TestEditorGUI {

	/**
	 * <p>createMainWindow</p>
	 *
	 * @param editor a {@link org.evosuite.ma.Editor} object.
	 */
	public void createMainWindow(final Editor editor);

	/**
	 * <p>getMainFrame</p>
	 *
	 * @return a {@link javax.swing.JFrame} object.
	 */
	public JFrame getMainFrame();
	
}
