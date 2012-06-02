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

import java.util.concurrent.locks.*;

public class SimpleCondition {
	private Lock lock;
	private Condition condition;
	private boolean wasSignaled = false;
	
	public SimpleCondition() {
		this.lock = new ReentrantLock();
		this.condition = this.lock.newCondition();
		this.lock.lock();
	}
	
	public void awaitUninterruptibly() {
		this.condition.awaitUninterruptibly();
		this.lock.unlock();
	}
	
	public synchronized void signal() {
		this.lock.lock();

		try {
			this.condition.signal();
			this.wasSignaled = true;
		} finally {
			this.lock.unlock();
		}
	}

	public synchronized boolean wasSignaled() {
		return this.wasSignaled;
	}
}
