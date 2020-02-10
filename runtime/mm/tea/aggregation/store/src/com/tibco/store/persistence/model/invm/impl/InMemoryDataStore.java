package com.tibco.store.persistence.model.invm.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.store.persistence.descriptor.ColumnDescriptor;
import com.tibco.store.persistence.descriptor.TableDescriptor;
import com.tibco.store.persistence.exceptions.DuplicateColumnException;
import com.tibco.store.persistence.exceptions.NoSuchTableException;
import com.tibco.store.persistence.model.MemStore;
import com.tibco.store.persistence.model.MemoryKey;
import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.model.invm.InMemorySearchResult;
import com.tibco.store.persistence.model.invm.InMemoryTable;
import com.tibco.store.persistence.model.listeners.MemoryTableListener;
import com.tibco.store.persistence.stats.MemTableStatsObservable;
import com.tibco.store.query.model.BinaryPredicate;
import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.impl.SimpleQueryExpression;

/**
 * The in memory data store viewed as a collection of various tables.
 * This can contain both partitioned as well as non-partitioned tables.
 *
 * @see com.tibco.store.persistence.model.MemTable
 */
public class InMemoryDataStore<M extends InMemoryTable> implements MemStore<InMemorySearchResult, M> {
   
	public static final InMemoryDataStore INSTANCE = new InMemoryDataStore();

    private InMemoryDataStore() {
    }

    private Map<String, M> memoryTables = new ConcurrentHashMap<String, M>();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends TableDescriptor<?>> M createTable(T tableDescriptor) {
        String tableName = tableDescriptor.getTableName();

        checkName(tableDescriptor.getTableName());
        if (memoryTables.containsKey(tableName)) {
            return memoryTables.get(tableName);
        }
        M inMemoryTable = (M) tableDescriptor.getTableFactory().createTable();
        memoryTables.put(tableDescriptor.getTableName(), inMemoryTable);
        return inMemoryTable;
    }

    @Override
    public void createIndex(String tableName, ColumnDescriptor columnDescriptor) throws NoSuchTableException, DuplicateColumnException {
        checkName(columnDescriptor.getName());
        checkName(tableName);

        if (!memoryTables.containsKey(tableName)) {
            throw new NoSuchTableException(String.format("Table %s does not exist", tableName));
        }
        M memoryTable = memoryTables.get(tableName);
        memoryTable.createIndex(columnDescriptor);
    }

    @Override
    public void createIndexes(String tableName, Collection<ColumnDescriptor> columnDescriptors) throws NoSuchTableException, DuplicateColumnException {
        if (columnDescriptors == null) {
            throw new IllegalArgumentException(
                    "column descriptors cannot be null");
        }
        for (ColumnDescriptor columnDescriptor : columnDescriptors) {
        	createIndex(tableName, columnDescriptor);
        }
    }

    @Override
    public M deleteTable(String name) {
        checkName(name);
        return memoryTables.remove(name);
    }

    @Override
    public void put(String tableName, MemoryTuple tuple) {
        checkName(tableName);
        M inMemoryTable = memoryTables.get(tableName);
        if (!checkNull(inMemoryTable)) {
            inMemoryTable.put(tuple);
        }
    }

    private boolean checkNull(Object obj) {
        return obj == null;
    }

    @Override
    public MemoryTuple get(String tableName, MemoryKey key) {
        checkName(tableName);
        M inMemoryTable = memoryTables.get(tableName);
        if (!checkNull(inMemoryTable)) {
            return inMemoryTable.get(key);
        }
        // TODO else log table does not exist
        return null;
    }

    private void checkName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(
                    "Region cannot have null or empty name");
        }
    }

    @Override
    public void remove(String tableName, MemoryTuple tuple) {
        checkName(tableName);
        M inMemoryTable = memoryTables.get(tableName);
        if (!checkNull(inMemoryTable)) {
            inMemoryTable.remove(tuple);
        }
        // TODO else log table does not exist
    }

    @Override
    public boolean isIndexed(String tableName, String columnName) {
        checkName(tableName);
        checkName(columnName);
        M inMemoryTable = memoryTables.get(tableName);
        return !checkNull(inMemoryTable) && inMemoryTable.isIndexed(columnName);
    }

    @Override
    public Iterator<String> getAllIndexes(String tableName) {
        checkName(tableName);
        M inMemoryTable = memoryTables.get(tableName);
        if (!checkNull(inMemoryTable)) {
            return inMemoryTable.getIndexNames();
        }
        return Collections.<String>emptyList().iterator();
    }

    @Override
    public InMemorySearchResult lookup(String tableName, Predicate predicate) {
        checkName(tableName);
        M inMemoryTable = memoryTables.get(tableName);
        if (!checkNull(inMemoryTable)) {
            return inMemoryTable.lookup(predicate);
        }

        return new InMemorySearchResultImpl(null);
    }

    @Override
    public int getCardinality(String tableName, String columnName) {
        checkName(tableName);
        M inMemoryTable = memoryTables.get(tableName);
        if (!checkNull(inMemoryTable)) {
            return inMemoryTable.getCardinality(columnName);
        }
        return 0;
    }

    @Override
    public void remove(String tableName, MemoryKey key) {
        checkName(tableName);
        M inMemoryTable = memoryTables.get(tableName);
        if (!checkNull(inMemoryTable)) {
            inMemoryTable.remove(key);
        }
    }

    public <P extends Predicate> void createJoinTable(String tableName,
                                                      P predicate) {
        // TODO implement this
    }

    @Override
    public Collection<MemoryTuple> getAllTuples(String tableName) {
        checkName(tableName);
        M inMemoryTable = memoryTables.get(tableName);
        if (!checkNull(inMemoryTable)) {
            return inMemoryTable.getAllTuples();
        }
        return Collections.<MemoryTuple>emptyList();
    }

    @Override
    public int getSizeOfTable(String tableName) {
        checkName(tableName);
        M inMemoryTable = memoryTables.get(tableName);
        if (!checkNull(inMemoryTable)) {
            return inMemoryTable.getTuplesCount();
        }
        return 0;
    }

    @Override
    public boolean isTableExist(String tableName) {
        return memoryTables.get(tableName) != null;
    }

    @Override
    public void clear(String tableName) {
        checkName(tableName);
        M inMemoryTable = memoryTables.get(tableName);
        if (!checkNull(inMemoryTable)) {
            inMemoryTable.clear();
        }
    }

    @Override
    public int getTupleCount(Predicate predicate) {
        if (predicate instanceof BinaryPredicate) {
            final SimpleQueryExpression queryExpression = ((BinaryPredicate<?>) predicate).getLeftExpression();
            String tableName = queryExpression.getTableName();
            checkName(tableName);
            M inMemoryTable = memoryTables.get(tableName);
            if (!checkNull(inMemoryTable)) {
                return inMemoryTable.getTupleCount(predicate);
            }
        }
        return 0;
    }

	@Override
	public void registerTableListener(String tableName, MemoryTableListener tableLisener) {
		checkName(tableName);
		M inMemoryTable = memoryTables.get(tableName);
		MemTableStatsObservable observable = (MemTableStatsObservable)inMemoryTable;
		observable.registerListener(tableLisener);
	}
}
