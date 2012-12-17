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
package org.evosuite.sandbox;

/**
 * <p>EvosuiteFile class.</p>
 *
 * @author Andrey Tarasevich
 */
public class EvosuiteFile {
	
	private String fileName;
	
	private String content;
	
	/**
	 * <p>Constructor for EvosuiteFile.</p>
	 *
	 * @param fileName a {@link java.lang.String} object.
	 * @param content a {@link java.lang.String} object.
	 */
	public EvosuiteFile(String fileName, String content){
		this.fileName = fileName;
		this.content = content;
	}

	/**
	 * <p>Getter for the field <code>fileName</code>.</p>
	 *
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * <p>Getter for the field <code>content</code>.</p>
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
}
