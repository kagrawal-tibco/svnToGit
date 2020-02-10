package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableNoKeyTableImpl;
import com.tibco.cep.kernel.core.rete.DefaultGuard;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Dec 22, 2008
 * Time: 1:48:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTableNoKeyTableImpl_MT extends ObjectTableNoKeyTableImpl
{
    private DefaultGuard iteratorLock = new DefaultGuard();

    public ObjectTableNoKeyTableImpl_MT() {
        super();
    }

    public void lock() {
        iteratorLock.lock();
    }

    public void unlock() {
        iteratorLock.unlock();
    }

    public  boolean add(Handle objHandle) {
        try {
            lock();
            return super.add(objHandle);
        } finally {
            unlock();
        }
    }

    public boolean remove(Handle objHandle) {
        try {
            lock();
            return super.remove(objHandle);
        } finally {
            unlock();
        }
    }

    //reset is synchronized by the caller
    //public void reset() {
    //  super.reset();
    //}
    
    public Handle[] getAllHandles() {
        try {
            lock();
            return super.getAllHandles();
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
