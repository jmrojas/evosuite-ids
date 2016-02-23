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
///////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2009, Rob Eden All Rights Reserved.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
///////////////////////////////////////////////////////////////////////////////

package com.examples.with.different.packagename.idnaming.gnu.trove.queue;


import com.examples.with.different.packagename.idnaming.gnu.trove.TFloatCollection;

import java.io.Serializable;

/**
 * Interface for Trove queue implementations.
 *
 * @see java.util.Queue
 */
public interface TFloatQueue extends TFloatCollection {
	/**
	 * Retrieves and removes the head of this queue. This method differs from
	 * {@link #poll} only in that it throws an exception if this queue is empty.
	 */
	public float element();


	/**
	 * Inserts the specified element into this queue if it is possible to do so
	 * immediately without violating capacity restrictions. When using a
	 * capacity-restricted queue, this method is generally preferable to
	 * {@link #add}, which can fail to insert an element only by throwing an exception.
	 *
	 * @param e		The element to add.
	 *
	 * @return	<tt>true</tt> if the element was added to this queue, else <tt>false</tt>
	 */
	public boolean offer( float e );


	/**
	 * Retrieves, but does not remove, the head of this queue, or returns
	 * {@link #getNoEntryValue} if this queue is empty.
	 *
	 * @return	the head of this queue, or {@link #getNoEntryValue} if this queue is empty 
	 */
	public float peek();


	/**
	 * Retrieves and removes the head of this queue, or returns {@link #getNoEntryValue}
	 * if this queue is empty.
	 *
	 * @return	the head of this queue, or {@link #getNoEntryValue} if this queue is empty
	 */
	public float poll();
}
