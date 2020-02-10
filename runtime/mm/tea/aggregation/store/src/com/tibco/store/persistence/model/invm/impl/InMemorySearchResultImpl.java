package com.tibco.store.persistence.model.invm.impl;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.model.invm.InMemorySearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class InMemorySearchResultImpl implements InMemorySearchResult {

	private Collection<MemoryTuple> result;
	
	public InMemorySearchResultImpl(Collection<MemoryTuple> result) {
        if (result == null) {
            result = new ArrayList<MemoryTuple>(0);
        }
		this.result = result;
	}
	
	@Override
	public Iterator<MemoryTuple> getTupleIterator() {
		return result.iterator();
	}

	public Collection<MemoryTuple> getTuples() {
		return result;
	}


    public int getCount() {
        return result.size();
    }
}
