package com.tibco.cep.kernel.core.base.tuple;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MigrationIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableWithKeyTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableWithKeyTableImpl;
import com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm.ObjectTableWithKeyTableImpl_CacheOnly;
import com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm.ObjectTableWithKeyTableImpl_MT;
import com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm.ObjectTableWithKeyTableImpl_Mixed;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.Identifier;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 16, 2006
 * Time: 1:01:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTableWithKey extends ObjectTable
{
    protected ObjectTableWithKeyTable objTable = null;
    
    public ObjectTableWithKey(Identifier[] identifiers) throws SetupException {
        super(identifiers);
    }

    public ObjectTableWithKey(Identifier[] identifiers, short Id) {
        super(identifiers, Id);
    }

    public boolean add(Handle objHandle, int key) {
        return objTable.add(objHandle, key); 
    }
    
    public Handle[] getAllHandles(int key) {
        return objTable.getAllHandles(key);
    }

    public TableIterator keyIterator(int key) {
        return objTable.keyIterator(key);
    }
    
    protected ObjectTableWithKeyTable getTableImpl() {
        return objTable;
    }

    protected void setTableImpl_ST() {
        //on first invocation objTable will be null
        //subsequent invocations are due to hot deploy
        if(objTable == null) {
            objTable = new ObjectTableWithKeyTableImpl();
        }
        else if(objTable.getClass() != ObjectTableWithKeyTableImpl.class) {
            ObjectTableWithKeyTable newTable = new ObjectTableWithKeyTableImpl();
            migrateTable(objTable, newTable);
            objTable = newTable;
        }
    }

    protected void setTableImpl_Concurrent(EntitySharingLevel lev) {
        ObjectTableWithKeyTable newTable = null;
        switch(lev) {
            case MIXED:
                if(objTable == null || objTable.getClass() != ObjectTableWithKeyTableImpl_Mixed.class)
                    newTable = new ObjectTableWithKeyTableImpl_Mixed();
                break;
            case UNSHARED:
                if(objTable == null || objTable.getClass() != ObjectTableWithKeyTableImpl_CacheOnly.class)
                    newTable = new ObjectTableWithKeyTableImpl_CacheOnly();
                break;
            case SHARED:
            default:
                if(objTable == null || objTable.getClass() != ObjectTableWithKeyTableImpl_MT.class)
                    newTable = new ObjectTableWithKeyTableImpl_MT();
                break;
        }

        if(newTable != null) {
            if(objTable != null) {
                migrateTable(objTable, newTable);
            }
            objTable = newTable;
        }
    }

    protected void migrateTable(ObjectTableWithKeyTable oldTable, ObjectTableWithKeyTable newTable) {
        /**
         This is used for hot deploy when the table type has changed.
         With the current behavior, all cache-only (ie thread local) tables will be empty 
         during a hot deploy, so the result of oldTable.iterator should be the same no matter
         what thread calls it.
         */
        for(MigrationIterator<ObjectTableWithKeyTable.KeyTuple> it = oldTable.migrationIterator(); it.hasNext();) {
            ObjectTableWithKeyTable.KeyTuple tup = it.next_migrate();
            oldTable.remove(tup.handle);
            //safeguard against cache only handles getting added to thread local storage on the hot deploy thread
            if(tup.handle.getSharingLevel() == EntitySharingLevel.SHARED) {
                newTable.add(tup.handle, tup.key);
            }
        }
    }
}
