package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MigrationIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableWithKeyTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableWithKeyTableImpl;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Dec 24, 2008
 * Time: 1:57:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTableWithKeyTableImpl_CacheOnly implements ObjectTableWithKeyTable
{
    private ThreadLocal<ObjectTableWithKeyTable> tables;
    
    public ObjectTableWithKeyTableImpl_CacheOnly() {
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
        ObjectTableWithKeyTable table = getTableImpl();
        return table == null ? NULL_ITER : table.iterator();
    }

    public TableIterator keyIterator(int key) {
        ObjectTableWithKeyTable table = getTableImpl();
        return table == null ? NULL_ITER : table.keyIterator(key);
    }

    public MigrationIterator<KeyTuple> migrationIterator() {
        //cache only tables should be empty during hot deploy
        return MigrationIterator.NULL_ITER;
    }

    public boolean isEmpty() {
        ObjectTableWithKeyTable table = getTableImpl();
        return table == null || table.isEmpty();
    }

    public int size() {
        ObjectTableWithKeyTable table = getTableImpl();
        return table == null ? 0 : table.size();
    }

    public void reset() {
        _reset();
    }
    private void _reset() {
        tables = new ThreadLocal();
    }

    public boolean add(Handle objHandle, int key) {
        return getOrCreateTableImpl().add(objHandle, key);
    }

    public Handle[] getAllHandles(int key) {
        ObjectTableWithKeyTable table = getTableImpl();
        return table == null ? NULL_ARR : table.getAllHandles(key);
    }

    public boolean remove(Handle objHandle) {
        ObjectTableWithKeyTable table = getTableImpl();
        return table == null || table.remove(objHandle);
    }

    protected ObjectTableWithKeyTable getTableImpl() {
        return tables.get();
    }

    protected ObjectTableWithKeyTable getOrCreateTableImpl() {
        ObjectTableWithKeyTable ret = tables.get();
        if(ret == null) {
            ret = new ObjectTableWithKeyTableImpl();
            tables.set(ret);
        }
        return ret;
    }
}