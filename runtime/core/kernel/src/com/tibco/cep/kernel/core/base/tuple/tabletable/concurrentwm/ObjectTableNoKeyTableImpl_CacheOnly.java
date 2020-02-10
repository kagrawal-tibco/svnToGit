package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableNoKeyTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableNoKeyTableImpl;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Dec 23, 2008
 * Time: 12:37:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTableNoKeyTableImpl_CacheOnly implements ObjectTableNoKeyTable
{
    private ThreadLocal<ObjectTableNoKeyTableImpl> tables;
    
    public ObjectTableNoKeyTableImpl_CacheOnly() {
        _reset();
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
        ObjectTableNoKeyTable table = getTableImpl();
        return table == null ? NULL_ITER : table.iterator();
    }

    public boolean isEmpty() {
        ObjectTableNoKeyTable table = getTableImpl();
        return table == null || table.isEmpty();
    }

    public int size() {
        ObjectTableNoKeyTable table = getTableImpl();
        return table == null ? 0 : table.size();
    }
    public void reset() {
        _reset();
    }
    private void _reset() {
        tables = new ThreadLocal();
    }
    
    public boolean add(Handle objHandle) {
        return getOrCreateTableImpl().add(objHandle);
    }

    public Handle[] getAllHandles() {
        ObjectTableNoKeyTable table = getTableImpl();
        return table == null ? NULL_ARR : table.getAllHandles();
    }

    public boolean remove(Handle objHandle) {
        ObjectTableNoKeyTable table = getTableImpl();
        return table == null || table.remove(objHandle);
    }

    protected ObjectTableNoKeyTableImpl getTableImpl() {
        return tables.get();
    }

    protected ObjectTableNoKeyTable getOrCreateTableImpl() {
        ObjectTableNoKeyTableImpl ret = tables.get();
        if(ret == null) {
            ret = new ObjectTableNoKeyTableImpl();
            tables.set(ret);
        }
        return ret;
    }
}