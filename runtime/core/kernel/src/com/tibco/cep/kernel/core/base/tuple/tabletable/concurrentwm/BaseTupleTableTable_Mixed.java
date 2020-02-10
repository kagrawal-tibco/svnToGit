package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import java.util.ArrayList;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TupleRow;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableTable;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 25, 2009
 * Time: 6:52:21 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseTupleTableTable_Mixed<TABLE extends TupleTableTable> extends BaseMixedTable<TABLE> implements TupleTableTable
{
    protected ThreadLocal<TABLE> threadTables;
    //TODO will leak memory if threads die without warning, could replace with weak references
    protected ArrayList<TABLE> threadTablesList;

    protected BaseTupleTableTable_Mixed() {
        super();
        _reset();
    }

    //reset is synchronized by the caller
    private void _reset() {
        threadTables = new ThreadLocal();
        threadTablesList = new ArrayList();
    }
    
    public void reset() {
        super.reset();
        _reset();
    }

    
    protected TABLE getThreadTable() {
        return threadTables.get();
    }

    protected TABLE getOrCreateThreadTable() {
        TABLE ret = threadTables.get();

        if (ret == null) {
            ret = newSimpleTable();
            threadTables.set(ret);
            threadTablesList.add(ret);
        }
        return ret;
    }

    public boolean add(TupleRow row, JoinTable container) {
        if(isThreadLocal(row)) {
            return getOrCreateThreadTable().add(row, container);
        } else {
            try {
                lock();
                return getOrCreateSharedTable().add(row, container);
            } finally {
                unlock();
            }
        }
    }
    
    public TupleRow remove(TupleRow row) {
        if (isThreadLocal(row)) {
            return getOrCreateThreadTable().remove(row);
        } else {
            try {
                lock();
                return getOrCreateSharedTable().remove(row);
            } finally {
                unlock();
            }
        }
    }

    protected boolean isThreadLocal(TupleRow tupleRow) {
        return tupleRow.isThreadLocal();
    }

    //TODO already synchronized by the caller?
    public void clearAllElements(JoinTable container) {
        if(sharedTable != null) sharedTable.clearAllElements(container);
        for(TupleTableTable table : threadTablesList) {
            if(table != null) table.clearAllElements(container);
        }
    }
}