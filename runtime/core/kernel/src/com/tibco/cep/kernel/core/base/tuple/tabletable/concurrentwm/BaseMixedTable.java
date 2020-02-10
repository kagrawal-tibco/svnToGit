package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MixedIteratorLock;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TableTable;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 6, 2009
 * Time: 3:25:30 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseMixedTable<TABLE extends TableTable> implements TableTable
{
    protected TABLE sharedTable;
    protected MixedIteratorLock iteratorLock = new MixedIteratorLock();

    protected BaseMixedTable() {
        _reset();
    }
    
    private void _reset() {
        sharedTable = null;
    }

    //reset is synchronized by the caller
    public void reset() {
        _reset();
    }
    
    public void lock() {
        iteratorLock.lock();
    }
    
    public void unlock() {
        iteratorLock.unlock();
    }
    
    public String contentHashForm(JoinTable container) {
        StringBuilder sb = new StringBuilder();
        try {
            lock();
            if (sharedTable != null) {
                sb.append("shared table:\n");
                sb.append(sharedTable.contentHashForm(container));
                sb.append("\n");
            }
        } finally {
            unlock();
        }
        TableTable threadTable = getThreadTable();
        if (threadTable != null) {
            if (sb.length() > 0) sb.append("\n");
            sb.append("thread local table:\n");
            sb.append(threadTable.contentHashForm(container));
        }
        return sb.toString();
    }

    public String contentListForm(JoinTable container) {
        StringBuilder sb = new StringBuilder();
        try {
            lock();
            if (sharedTable != null) {
                sb.append("shared table:\n");
                sb.append(sharedTable.contentListForm(container));
            }
        } finally {
            unlock();
        }
        TableTable threadTable = getThreadTable();
        if (threadTable != null) {
            if (sb.length() > 0) sb.append("\n");
            sb.append("thread local table:\n");
            sb.append(threadTable.contentListForm(container));
        }
        return sb.toString();
    }

    abstract protected TABLE getThreadTable();

    abstract protected TABLE getOrCreateThreadTable();

    abstract protected TABLE newSimpleTable();

    protected TABLE getSharedTable() {
        return sharedTable;
    }

    //must lock before calling this
    protected TABLE getOrCreateSharedTable() {
        if (sharedTable == null) {
            sharedTable = newSimpleTable();
        }
        return sharedTable;
    }

    public boolean isEmpty() {
        TableTable threadTable = getThreadTable();
        if (threadTable != null && !threadTable.isEmpty()) return false;
        try {
            lock();
            if (sharedTable != null) {
                return sharedTable.isEmpty();
            }
        } finally {
            unlock();
        }
        return true;
    }

    public int size() {
        int ret = 0;
        TableTable threadTable = getThreadTable();
        if (threadTable != null) ret += threadTable.size();
        try {
            lock();
            if (sharedTable != null) ret += sharedTable.size();
        } finally {
            unlock();
        }
        return ret;
    }
}
