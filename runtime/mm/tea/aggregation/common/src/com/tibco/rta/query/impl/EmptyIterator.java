package com.tibco.rta.query.impl;

import com.tibco.rta.query.Browser;



public class EmptyIterator<T> implements Browser<T> {


	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public T next() {
		return null;
	}

	@Override
	public void remove() {
		
	}

	@Override
	public void stop() {
		
	}

}
