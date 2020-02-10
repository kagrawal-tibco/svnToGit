package com.tibco.rta.service.persistence.memory;

import java.util.Iterator;

import com.tibco.rta.Fact;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.impl.EmptyIterator;

public class FactBrowser implements Browser<Fact> {	
	Iterator<Fact> ite;
	Boolean isStopped;
	
	public FactBrowser(Iterator<Fact> ite) {
		this.ite = ite;

	}

	@Override
	public boolean hasNext() {
		return ite.hasNext();
	}

	@Override
	public Fact next() {		
		return ite.next();
	}

	@Override
	public void remove() {
		// Not supported
	}

	@Override
	public void stop() {
		isStopped = true;
		ite = new EmptyIterator<Fact>();
	}

}

