package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TupleRow;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableNoKeyTableImpl;
import com.tibco.cep.kernel.core.rete.DefaultGuard;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Dec 22, 2008
 * Time: 1:48:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class TupleTableNoKeyTableImpl_MT extends TupleTableNoKeyTableImpl
{
    private DefaultGuard iteratorLock = new DefaultGuard();

    public TupleTableNoKeyTableImpl_MT() {
        super();
    }

    public void lock() {
        iteratorLock.lock();
    }

    public void unlock() {
        iteratorLock.unlock();
    }

    public  boolean add(TupleRow row, JoinTable container) {
        try {
            lock();
            return super.add(row, container);
        } finally {
            unlock();
        }
    }

    public TupleRow remove(TupleRow row) {
        try {
            lock();
            return super.remove(row);
        } finally {
            unlock();
        }
    }

    //reset is synchronized by the caller
    //public void reset() {
    //  super.reset();
    //}

    public Handle[][] getAllRows() {
        try {
            lock();
            return super.getAllRows();
        } finally {
            unlock();
        }
    }
    
    public boolean isEmpty() {
        try {
            lock();
            return super.isEmpty();
        } finally {
            unlock();
        }
    }

    public int size() {
        try {
            lock();
            return super.size();
        } finally {
            unlock();
        }
    }

    public String contentHashForm(JoinTable container) {
        try {
            lock();
            return super.contentHashForm(container);
        } finally {
            unlock();
        }
    }

    public String contentListForm(JoinTable container) {
        try {
            lock();
            return super.contentListForm(container);
        } finally {
            unlock();
        }
    }
}