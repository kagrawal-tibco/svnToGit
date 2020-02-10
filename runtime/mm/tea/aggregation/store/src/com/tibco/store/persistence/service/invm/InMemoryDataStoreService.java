package com.tibco.store.persistence.service.invm;


import java.util.Collection;

import com.tibco.store.persistence.model.MemoryKey;
import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.model.SearchResult;
import com.tibco.store.persistence.model.invm.impl.InMemoryDataStore;
import com.tibco.store.persistence.service.DataStoreService;
import com.tibco.store.query.model.BinaryPredicate;
import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.impl.SimpleQueryExpression;

public class InMemoryDataStoreService implements DataStoreService {
	
	private InMemoryDataStore inMemoryDataStore = InMemoryDataStore.INSTANCE;;

	@Override
	public void put(String tableName, MemoryTuple node) {
		inMemoryDataStore.put(tableName, node);
	}

	@Override
	public void remove(String tableName, MemoryTuple node) {
		inMemoryDataStore.remove(tableName, node);
	}

	@Override
    @SuppressWarnings("unchecked")
	public <S extends SearchResult> S lookup(Predicate predicate) {
        String tableName = null;
        if (predicate instanceof BinaryPredicate) {
            final SimpleQueryExpression queryExpression = ((BinaryPredicate) predicate).getLeftExpression();
            tableName = queryExpression.getTableName();
        }
		return (S) inMemoryDataStore.lookup(tableName, predicate);
	}

	@Override
	public MemoryTuple get(String tableName, MemoryKey key) {
		return inMemoryDataStore.get(tableName, key);
	}

	@Override
	public void remove(String tableName, MemoryKey key) {
		inMemoryDataStore.remove(tableName, key);
	}

	@Override
	public int getSizeOfTable(String tableName) {		
		return inMemoryDataStore.getSizeOfTable(tableName);
	}

	@Override
	public Collection<MemoryTuple> getAllTuples(String tableName) {
		return inMemoryDataStore.getAllTuples(tableName);
	}

	@Override
	public void clear(String tableName) {
		inMemoryDataStore.clear(tableName);
	}

	@Override
	public int getTupleCount(Predicate predicate) {
        return inMemoryDataStore.getTupleCount(predicate);
	}

}
