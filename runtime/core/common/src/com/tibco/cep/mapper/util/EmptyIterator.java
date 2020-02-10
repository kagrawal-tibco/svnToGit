/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.util;

import java.util.Iterator;

public class EmptyIterator implements Iterator
{
	public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

	private EmptyIterator() {
	}

	public final boolean hasNext() {
		return false;
	}

	public final Object next() {
		return null;
	}

	public void remove()
		throws UnsupportedOperationException, IllegalStateException
	{
		throw new UnsupportedOperationException("EmptyIterator does not support the removal operation.");
	}
}
