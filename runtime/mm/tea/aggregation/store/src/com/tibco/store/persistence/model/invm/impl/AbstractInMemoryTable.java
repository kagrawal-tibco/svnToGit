package com.tibco.store.persistence.model.invm.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.tibco.store.persistence.descriptor.ColumnDescriptor;
import com.tibco.store.persistence.descriptor.TableDescriptor;
import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.model.invm.InMemoryTable;
import com.tibco.store.persistence.model.listeners.MemoryTableListener;
import com.tibco.store.persistence.model.listeners.TupleAddedEvent;
import com.tibco.store.persistence.model.listeners.TupleRemovedEvent;
import com.tibco.store.persistence.stats.MemTableStatsObservable;

public abstract class AbstractInMemoryTable<T extends TableDescriptor> implements InMemoryTable, MemTableStatsObservable {

    protected List<MemoryTableListener> tableListenerRegistry = new ArrayList<MemoryTableListener>();

    protected String tableName;

    protected Collection<String> indexes = new HashSet<String>();

    protected T tableDescriptor;

    protected AbstractInMemoryTable(T tableDescriptor) {
        this.tableDescriptor = tableDescriptor;
    }

    @Override
    public synchronized void registerListener(MemoryTableListener tableListener) {
        tableListenerRegistry.add(tableListener);
    }

    @Override
    public synchronized void unRegisterListener(MemoryTableListener tableListener) {
        tableListenerRegistry.remove(tableListener);
    }

    protected void publishTupleAdded(MemoryTuple tuple) {
        // iterate through all listeners and invoke onTupleAdded(e)
        for (MemoryTableListener tableListener : tableListenerRegistry) {
            tableListener.onTupleAdded(new TupleAddedEvent(tuple));
        }
    }
    
    protected void publishTupleRemoved(MemoryTuple tuple) {
        // iterate through all listeners and invoke onTupleRemoved(e)
        for (MemoryTableListener tableListener : tableListenerRegistry) {
            tableListener.onTupleRemoved(new TupleRemovedEvent(tuple));
        }
    }
    
    
    public String getTableName() {
        return tableDescriptor.getTableName();
    }
    
    @Override
    public Iterator<String> getIndexNames() {
        return indexes.iterator();
    }
    
    @Override
    public boolean isIndexed(String indexName) {
        // TODO case sensitive or not decide later
        for (String index : indexes) {
            if (index.equals(indexName)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void clear() {
        // iterate through all listeners and invoke onTableCleared
        for (MemoryTableListener tableListener : tableListenerRegistry) {
            tableListener.onTableCleared();
        }
    }
    
    public abstract void createIndex(ColumnDescriptor columnDescriptor);
}