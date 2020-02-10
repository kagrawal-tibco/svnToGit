package com.tibco.store.persistence.model.invm;

import java.util.Collection;
import java.util.Iterator;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.model.SearchResult;

public interface InMemorySearchResult extends SearchResult {

	public Collection<MemoryTuple> getTuples();

    public int getCount();

	public Iterator<MemoryTuple> getTupleIterator();
}
