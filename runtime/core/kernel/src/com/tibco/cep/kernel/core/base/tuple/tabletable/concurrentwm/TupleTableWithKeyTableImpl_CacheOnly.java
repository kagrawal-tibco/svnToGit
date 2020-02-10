package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.core.base.tuple.TupleRow;
import com.tibco.cep.kernel.core.base.tuple.TupleRowWithKey;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MigrationIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableWithKeyTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableWithKeyTableImpl;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Dec 23, 2008
 * Time: 12:37:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class TupleTableWithKeyTableImpl_CacheOnly implements TupleTableWithKeyTable
{
    private ThreadLocal<TupleTableWithKeyTable> tables;
    
    public TupleTableWithKeyTableImpl_CacheOnly() {
        _reset();
    }
    public void reset() {
        _reset();
    }
    private void _reset() {
        tables = new ThreadLocal();
    }
    
    public void lock() {}

    public void unlock() {}

    public String contentHashForm(JoinTable container) {
        return getOrCreateTableImpl().contentHashForm(container);
    }

    public String contentListForm(JoinTable container) {
        return getOrCreateTableImpl().contentListForm(container);
    }

    public TableIterator iterator() {
        TupleTableWithKeyTable table = getTableImpl();
        return table == null ? NULL_ITER : table.iterator();
    }

    public TableIterator keyIterator(int key) {
        TupleTableWithKeyTable table = getTableImpl();
        return table == null ? NULL_ITER : table.keyIterator(key);
    }

    public MigrationIterator<TupleRowWithKey> migrationIterator() {
        //cache only tables should be empty during hot deploy
        return MigrationIterator.NULL_ITER;
    }

    public boolean isEmpty() {
        TupleTableWithKeyTable table = getTableImpl();
        return table == null || table.isEmpty();
    }

    public int size() {
        TupleTableWithKeyTable table = getTableImpl();
        return table == null ? 0 : table.size();
    }

    public boolean add(TupleRow row, JoinTable container) {
        return getOrCreateTableImpl().add(row, container);
    }

    public Handle[][] getAllRows(int key) {
        TupleTableWithKeyTable table = getTableImpl();
        return table == null ? NULL_ARR : table.getAllRows(key);
    }

    public TupleRow remove(TupleRow row) {
        TupleTableWithKeyTable table = getTableImpl();
        return table == null ? null : table.remove(row);
    }

    public void clearAllElements(JoinTable container) {
        TupleTableWithKeyTable table = getTableImpl();
        if(table != null) table.clearAllElements(container);
    }

    protected TupleTableWithKeyTable getTableImpl() {
        return tables.get();
    }

    protected TupleTableWithKeyTable getOrCreateTableImpl() {
        TupleTableWithKeyTable ret = tables.get();
        if(ret == null) {
            ret = new TupleTableWithKeyTableImpl();
            tables.set(ret);
        }
        return ret;
    }
}