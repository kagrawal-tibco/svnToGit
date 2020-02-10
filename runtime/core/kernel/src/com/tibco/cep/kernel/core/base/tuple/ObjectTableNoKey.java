package com.tibco.cep.kernel.core.base.tuple;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableNoKeyTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableNoKeyTableImpl;
import com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm.ObjectTableNoKeyTableImpl_CacheOnly;
import com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm.ObjectTableNoKeyTableImpl_MT;
import com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm.ObjectTableNoKeyTableImpl_Mixed;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.Identifier;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 16, 2006
 * Time: 1:07:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTableNoKey extends ObjectTable
{
    protected ObjectTableNoKeyTable objTable = null;
    
    public ObjectTableNoKey(Identifier[] identifiers) throws SetupException {
        super(identifiers);
    }

    public ObjectTableNoKey(Identifier[] identifiers, short Id) {
        super(identifiers, Id);
    }

    public boolean add(Handle objHandle) {
        return objTable.add(objHandle);
    }

    public TableIterator keyIterator(int key) {
        throw new RuntimeException("ProgramError: keyIterator is not implemented in ObjectTableNoKey");
    }

    public Handle[] getAllHandles() {  //this function is called another thread, need to sync the object table when read
        return objTable.getAllHandles();
    }
    
    //may return null
    protected ObjectTableNoKeyTable getTableImpl() {
        return objTable;
    }

    protected void setTableImpl_ST() {
        //on first invocation objTable will be null
        //subsequent invocations are due to hot deploy
        if(objTable == null) {
            objTable = new ObjectTableNoKeyTableImpl();
        }
        else if(objTable.getClass() != ObjectTableNoKeyTableImpl.class) {
            ObjectTableNoKeyTable newTable = new ObjectTableNoKeyTableImpl();
            migrateTable(objTable, newTable);
            objTable = newTable;
        }
    }
    
    protected void setTableImpl_Concurrent(EntitySharingLevel lev) {
        ObjectTableNoKeyTable newTable = null;
        switch(lev) {
            case MIXED:
                if(objTable == null || objTable.getClass() != ObjectTableNoKeyTableImpl_Mixed.class)
                    newTable = new ObjectTableNoKeyTableImpl_Mixed();
                break;
            case UNSHARED:
                if(objTable == null || objTable.getClass() != ObjectTableNoKeyTableImpl_CacheOnly.class)
                    newTable = new ObjectTableNoKeyTableImpl_CacheOnly();
                break;
            case SHARED:
            default:
                if(objTable == null || objTable.getClass() != ObjectTableNoKeyTableImpl_MT.class)
                    newTable = new ObjectTableNoKeyTableImpl_MT();
                break;
        }
        
        if(newTable != null) {
            if(objTable != null) {
                migrateTable(objTable, newTable);
            }
            objTable = newTable;
        }
    }
    
    protected static void migrateTable(ObjectTableNoKeyTable oldTable, ObjectTableNoKeyTable newTable) {
        /**
         This is used for hot deploy when the table type has changed.
         With the current behavior, all cache-only (ie thread local) tables will be empty 
         during a hot deploy, so the result of oldTable.iterator should be the same no matter
         what thread calls it.
         */
        for(TableIterator it = oldTable.iterator(); it.hasNext();) {
            Handle next = it.next()[0];
            oldTable.remove(next);
            //safeguard against cache only handles getting added to thread local storage on the hot deploy thread
            if(next.getSharingLevel() == EntitySharingLevel.SHARED) {
                newTable.add(next);
            }
        }
    }
}