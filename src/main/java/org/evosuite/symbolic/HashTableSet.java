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
package org.evosuite.symbolic;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class HashTableSet<E> extends AbstractSet<E> implements Cloneable, Set<E> {

	protected Hashtable<E, E> table;

	public HashTableSet() {
		this.table = new Hashtable<E, E>();
	}

	@Override
	public boolean add(E e) {
		return null == table.put(e, e);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return super.containsAll(c);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		HashTableSet<E> ret = new HashTableSet<E>();
		ret.table = (Hashtable<E, E>) this.table.clone();
		return ret;
	}

	@Override
	public boolean contains(Object o) {
		return table.containsKey(o);
	}

	@Override
	public boolean remove(Object o) {
		return null != table.remove(o);
	}

	@Override
	public Iterator<E> iterator() {
		return table.keySet().iterator();
	}

	@Override
	public int size() {
		return table.size();
	}

}
