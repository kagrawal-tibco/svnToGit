package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.core.base.tuple.TupleRow;
import com.tibco.cep.kernel.core.base.tuple.TupleRowNoKey;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MigrationIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableNoKeyTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableNoKeyTableImpl;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Dec 23, 2008
 * Time: 12:37:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class TupleTableNoKeyTableImpl_CacheOnly implements TupleTableNoKeyTable
{
    private ThreadLocal<TupleTableNoKeyTable> tables;
    
    public TupleTableNoKeyTableImpl_CacheOnly() {
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
        TupleTableNoKeyTable table = getTableImpl();
        return table == null ? NULL_ITER : table.iterator();
    }

    public boolean isEmpty() {
        TupleTableNoKeyTable table = getTableImpl();
        return table == null || table.isEmpty();
    }

    public int size() {
        TupleTableNoKeyTable table = getTableImpl();
        return table == null ? 0 : table.size();
    }

    public boolean add(TupleRow row, JoinTable container) {
        return getOrCreateTableImpl().add(row, container);
    }

    public Handle[][] getAllRows() {
        TupleTableNoKeyTable table = getTableImpl();
        return table == null ? NULL_ARR : table.getAllRows();
    }

    public MigrationIterator<TupleRowNoKey> migrationIterator() {
        //cache only tables should be empty during hot deploy
        return MigrationIterator.NULL_ITER;
    }

    public TupleRow remove(TupleRow row) {
        TupleTableNoKeyTable table = getTableImpl();
        return table == null ? null : table.remove(row);
    }

    public void clearAllElements(JoinTable container) {
        TupleTableNoKeyTable table = getTableImpl();
        if(table != null) table.clearAllElements(container);
    }

    protected TupleTableNoKeyTable getTableImpl() {
        return tables.get();
    }

    protected TupleTableNoKeyTable getOrCreateTableImpl() {
        TupleTableNoKeyTable ret = tables.get();
        if(ret == null) {
            ret = new TupleTableNoKeyTableImpl();
            tables.set(ret);
        }
        return ret;
    }
}