package com.tibco.store.persistence.model;

import com.tibco.store.persistence.descriptor.ColumnDescriptor;
import com.tibco.store.persistence.descriptor.TableDescriptor;
import com.tibco.store.persistence.exceptions.DuplicateColumnException;
import com.tibco.store.persistence.exceptions.NoSuchTableException;
import com.tibco.store.persistence.model.listeners.MemoryTableListener;
import com.tibco.store.query.model.Predicate;

import java.util.Collection;
import java.util.Iterator;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 30/11/13
 * Time: 7:02 PM
 * <p/>
 * Abstract data store to be used for persistence. Can be loosely viewed
 * as a collection of memory regions.
 * TODO more lookup methods needed with relations specified.
 * @param <S>
 *
 * @see MemTable
 */
public interface MemStore<S extends SearchResult, R extends MemTable<S>> {

    /**
     * Create table in store with the specified name.
     * Name should be unique in the mem store.
     *
     * @param tableDescriptor - Metadata about table.
     * @return
     */
    public <T extends TableDescriptor<?>> R createTable(T tableDescriptor);

    /**
     * @param tableName - Name of an existing table.
     * @param columnDescriptor - Metadata about columns.
     */
    public void createIndex(String tableName, ColumnDescriptor columnDescriptor) throws NoSuchTableException, DuplicateColumnException;

    /**
     * @param tableName - Name of an existing table.
     * @param columnDescriptors - Metadata about columns.
     */
    public void createIndexes(String tableName, Collection<ColumnDescriptor> columnDescriptors) throws NoSuchTableException, DuplicateColumnException;


    /**
     *  Remove table
     * @param name
     */
    public R deleteTable(String name);

    /**
     * Puts a tuple into the appropriate table.
     *
     * @param tableName
     * @param tuple
     */
    public void put(String tableName, MemoryTuple tuple);
    

    /**
     * Puts a tuple into the appropriate table.
     * @param tableName
     * @param tuple
     */
    public void remove(String tableName, MemoryTuple tuple);


    /**
     * removes tuple from appropriate table
     * @param tableName
     * @param key
     */
    public void remove(String tableName, MemoryKey key);

    
    /**
     * Get the tuple identified by key
     * @param tableName
     * @param key
     */
    public MemoryTuple get(String tableName, MemoryKey key);

    /**
     * Check to see if a column in a table is indexed.
     *
     * @param tableName
     * @param columnName
     * @return
     */
    public boolean isIndexed(String tableName, String columnName);

    /**
     * All indexes on this table.
     *
     * @param tableName
     * @return
     */
    public Iterator<String> getAllIndexes(String tableName);


    /**
     * Result of query on this table
     *
     * @param tableName
     * @return S
     */
	public S lookup(String tableName, Predicate predicate);

    /**
     * Get Cardinality for the column in the table
     *
     * @param tableName
     * @return columnName
     */
	public int getCardinality(String tableName, String columnName);


    /**
     * Get size of the table
     *
     * @param tableName
     * @return size of table
     */
	public int getSizeOfTable(String tableName);


    /**
     * Get all tuples in the table
     *
     * @param tableName
     * @return Collection<MemoryTuple>
     */	
	public Collection<MemoryTuple> getAllTuples(String tableName);


    /**
     * if table exist or not
     *
     * @param tableName
     * @return is table exists
     */	
	public boolean isTableExist(String tableName);
	
	public void clear(String tableName);
	
	   /**
     * Get tuple count for the predicate
     *
     * @param predicate
     * @return count
     */	
	public int getTupleCount(Predicate predicate);
	
	/**
	 * Register table listener on given table for getting notifications for tuple added/deleted, 
	 * table cleared etc 
	 * @param tableLisener
	 */
	public void registerTableListener(String tableName, MemoryTableListener tableLisener);
}
