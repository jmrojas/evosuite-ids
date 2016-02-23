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
// Copyright (c) 2008, Robert D. Eden All Rights Reserved.
// Copyright (c) 2009, Jeff Randall All Rights Reserved.
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

package com.examples.with.different.packagename.idnaming.gnu.trove.impl.unmodifiable;


//////////////////////////////////////////////////
// THIS IS A GENERATED CLASS. DO NOT HAND EDIT! //
//////////////////////////////////////////////////

////////////////////////////////////////////////////////////
// THIS IS AN IMPLEMENTATION CLASS. DO NOT USE DIRECTLY!  //
// Access to these methods should be through TCollections //
////////////////////////////////////////////////////////////


import com.examples.with.different.packagename.idnaming.gnu.trove.iterator.*;
import com.examples.with.different.packagename.idnaming.gnu.trove.procedure.*;
import com.examples.with.different.packagename.idnaming.gnu.trove.set.*;
import com.examples.with.different.packagename.idnaming.gnu.trove.list.*;
import com.examples.with.different.packagename.idnaming.gnu.trove.function.*;
import com.examples.with.different.packagename.idnaming.gnu.trove.map.*;
import com.examples.with.different.packagename.idnaming.gnu.trove.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Random;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.IOException;


public class TUnmodifiableShortByteMap implements TShortByteMap, Serializable {
	private static final long serialVersionUID = -1034234728574286014L;

	private final TShortByteMap m;

	public TUnmodifiableShortByteMap( TShortByteMap m ) {
		if ( m == null )
			throw new NullPointerException();
		this.m = m;
	}

	public int size()                       { return m.size(); }
	public boolean isEmpty()                { return m.isEmpty(); }
	public boolean containsKey( short key )   { return m.containsKey( key ); }
	public boolean containsValue( byte val ) { return m.containsValue( val ); }
	public byte get( short key)                { return m.get( key ); }

	public byte put( short key, byte value ) { throw new UnsupportedOperationException(); }
	public byte remove( short key ) { throw new UnsupportedOperationException(); }
	public void putAll( TShortByteMap m ) { throw new UnsupportedOperationException(); }
	public void putAll( Map<? extends Short, ? extends Byte> map ) { throw new UnsupportedOperationException(); }
	public void clear() { throw new UnsupportedOperationException(); }

	private transient TShortSet keySet = null;
	private transient TByteCollection values = null;

	public TShortSet keySet() {
		if ( keySet == null )
			keySet = TCollections.unmodifiableSet( m.keySet() );
		return keySet;
	}
	public short[] keys() { return m.keys(); }
	public short[] keys( short[] array ) { return m.keys( array ); }

	public TByteCollection valueCollection() {
		if ( values == null )
			values = TCollections.unmodifiableCollection( m.valueCollection() );
		return values;
	}
	public byte[] values() { return m.values(); }
	public byte[] values( byte[] array ) { return m.values( array ); }

	public boolean equals(Object o) { return o == this || m.equals(o); }
	public int hashCode()           { return m.hashCode(); }
	public String toString()        { return m.toString(); }
	public short getNoEntryKey()      { return m.getNoEntryKey(); }
	public byte getNoEntryValue()    { return m.getNoEntryValue(); }

	public boolean forEachKey( TShortProcedure procedure ) {
		return m.forEachKey( procedure );
	}
	public boolean forEachValue( TByteProcedure procedure ) {
		return m.forEachValue( procedure );
	}
	public boolean forEachEntry( TShortByteProcedure procedure ) {
		return m.forEachEntry( procedure );
	}

	public TShortByteIterator iterator() {
		return new TShortByteIterator() {
			TShortByteIterator iter = m.iterator();

			public short key() { return iter.key(); }
			public byte value() { return iter.value(); }
			public void advance() { iter.advance(); }
			public boolean hasNext() { return iter.hasNext(); }
			public byte setValue( byte val ) { throw new UnsupportedOperationException(); }
			public void remove() { throw new UnsupportedOperationException(); }
		};
	}

	public byte putIfAbsent( short key, byte value ) { throw new UnsupportedOperationException(); }
	public void transformValues( TByteFunction function ) { throw new UnsupportedOperationException(); }
	public boolean retainEntries( TShortByteProcedure procedure ) { throw new UnsupportedOperationException(); }
	public boolean increment( short key ) { throw new UnsupportedOperationException(); }
	public boolean adjustValue( short key, byte amount ) { throw new UnsupportedOperationException(); }
	public byte adjustOrPutValue( short key, byte adjust_amount, byte put_amount ) { throw new UnsupportedOperationException(); }
}
