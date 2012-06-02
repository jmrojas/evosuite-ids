/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package de.unisb.cs.st.evosuite.utils;

import java.io.Serializable;

public class PassiveChangeListener<T> implements Listener<T>, Serializable {

	private static final long serialVersionUID = -8661407199741916844L;

	protected boolean changed = false;

	/**
	 * Returns whether the listener received any event since the last call of
	 * this method. Resets this listener such that another call of this method
	 * without receiving an event in between will return false.
	 * 
	 * @return
	 */
	public boolean hasChanged() {
		boolean result = changed;
		changed = false;
		return result;
	}

	@Override
	public void receiveEvent(T event) {
		changed = true;
	}

}
