package com.tibco.cep.kernel.core.base.tuple;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MigrationIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableWithKeyTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableWithKeyTableImpl;
import com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm.TupleTableWithKeyTableImpl_CacheOnly;
import com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm.TupleTableWithKeyTableImpl_MT;
import com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm.TupleTableWithKeyTableImpl_Mixed;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.Identifier;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 7, 2006
 * Time: 12:43:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class TupleTableWithKey extends TupleTable
{
    protected TupleTableWithKeyTable tupTable = null;

    public TupleTableWithKey(Identifier[] identifiers) throws SetupException {
        super(identifiers);
    }

    public TupleTableWithKey(Identifier[] identifiers, short Id) {
        super(identifiers, Id);
    }

    public Handle[][] getAllRows(int key) {  //this function is called another thread, need to sync the key table when read
        return tupTable.getAllRows(key);
    }

    public TableIterator keyIterator(int key) {
        return tupTable.keyIterator(key);
    }

    //may return null
    protected TupleTableWithKeyTable getTableImpl() {
        return tupTable;
    }

    protected void setTableImpl_ST() {
        //on first invocation objTable will be null
        //subsequent invocations are due to hot deploy
        if(tupTable == null) {
            tupTable = new TupleTableWithKeyTableImpl();
        }
        else if(tupTable.getClass() != TupleTableWithKeyTableImpl.class) {
            TupleTableWithKeyTable newTable = new TupleTableWithKeyTableImpl();
            migrateTable(tupTable, newTable);
            tupTable = newTable;
        }
    }

    protected void setTableImpl_Concurrent(EntitySharingLevel lev) {
        TupleTableWithKeyTable newTable = null;
        switch(lev) {
            case MIXED:
                if(tupTable == null || tupTable.getClass() != TupleTableWithKeyTableImpl_Mixed.class)
                    newTable = new TupleTableWithKeyTableImpl_Mixed();
                break;
            case UNSHARED:
                if(tupTable == null || tupTable.getClass() != TupleTableWithKeyTableImpl_CacheOnly.class)
                    newTable = new TupleTableWithKeyTableImpl_CacheOnly();
                break;
            case SHARED:
            default:
                if(tupTable == null || tupTable.getClass() != TupleTableWithKeyTableImpl_MT.class)
                    newTable = new TupleTableWithKeyTableImpl_MT();
                break;
        }

        if(newTable != null) {
            if(tupTable != null) {
                migrateTable(tupTable, newTable);
            }
            tupTable = newTable;
        }
    }

    protected void migrateTable(TupleTableWithKeyTable oldTable, TupleTableWithKeyTable newTable) {
        /**
         This is used for hot deploy when the table type has changed.
         With the current behavior, all cache-only (ie thread local) tables will be empty 
         during a hot deploy, so the result of oldTable.iterator should be the same no matter
         what thread calls it.
         */
        for(MigrationIterator<TupleRowWithKey> it = oldTable.migrationIterator(); it.hasNext();) {
            TupleRowWithKey row = it.next_migrate();
            //safeguard against cache only handles getting added to thread local storage on the hot deploy thread
            if(!row.isThreadLocal()) {
                newTable.add(new TupleRowWithKey(row.objHandles, row.rowKey), this);
            }
        }
        oldTable.clearAllElements(this);
    }
}
