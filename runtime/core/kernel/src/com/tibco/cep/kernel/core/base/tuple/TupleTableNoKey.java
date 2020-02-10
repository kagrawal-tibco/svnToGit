package com.tibco.cep.kernel.core.base.tuple;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MigrationIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableNoKeyTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableNoKeyTableImpl;
import com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm.TupleTableNoKeyTableImpl_CacheOnly;
import com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm.TupleTableNoKeyTableImpl_MT;
import com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm.TupleTableNoKeyTableImpl_Mixed;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.Identifier;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 25, 2004
 * Time: 6:15:48 PM
 * To change this template use Options | File Templates.
 */
public class TupleTableNoKey extends TupleTable 
{
    protected TupleTableNoKeyTable tupTable = null;
    
    public TupleTableNoKey(Identifier[] identifiers) throws SetupException {
        super(identifiers);
    }

    public TupleTableNoKey(Identifier[] identifiers, short Id) {
        super(identifiers, Id);
    }

    public TableIterator keyIterator(int key) {
        throw new RuntimeException("ProgramError: keyIterator is not implemented in TupleTableNoKey");
    }
    
    public Handle[][] getAllRows() {
        return tupTable.getAllRows();
    }
    
    protected TupleTableNoKeyTable getTableImpl() {
        return tupTable;
    }
    
    protected void setTableImpl_ST() {
        //on first invocation objTable will be null
        //subsequent invocations are due to hot deploy
        if(tupTable == null) {
            tupTable = new TupleTableNoKeyTableImpl();
        }
        else if(tupTable.getClass() != TupleTableNoKeyTableImpl.class) {
            TupleTableNoKeyTable newTable = new TupleTableNoKeyTableImpl();
            migrateTable(tupTable, newTable);
            tupTable = newTable;
        }
    }
    
    protected void setTableImpl_Concurrent(EntitySharingLevel lev) {
        TupleTableNoKeyTable newTable = null;
        switch(lev) {
            case MIXED:
                if(tupTable == null || tupTable.getClass() != TupleTableNoKeyTableImpl_Mixed.class)
                    newTable = new TupleTableNoKeyTableImpl_Mixed();
                break;
            case UNSHARED:
                if(tupTable == null || tupTable.getClass() != TupleTableNoKeyTableImpl_CacheOnly.class)
                    newTable = new TupleTableNoKeyTableImpl_CacheOnly();
                break;
            case SHARED:
            default:
                if(tupTable == null || tupTable.getClass() != TupleTableNoKeyTableImpl_MT.class)
                    newTable = new TupleTableNoKeyTableImpl_MT();
                break;
        }

        if(newTable != null) {
            if(tupTable != null) {
                migrateTable(tupTable, newTable);
            }
            tupTable = newTable;
        }
    }

    protected void migrateTable(TupleTableNoKeyTable oldTable, TupleTableNoKeyTable newTable) {
        /**
         This is used for hot deploy when the table type has changed.
         With the current behavior, all cache-only (ie thread local) tables will be empty 
         during a hot deploy, so the result of oldTable.iterator should be the same no matter
         what thread calls it.
         */
        for(MigrationIterator<TupleRowNoKey> it = oldTable.migrationIterator(); it.hasNext();) {
            TupleRowNoKey row = it.next_migrate();
            //safeguard against cache only handles getting added to thread local storage on the hot deploy thread
            if(!row.isThreadLocal()) {
                newTable.add(new TupleRowNoKey(row.objHandles), this);
            }
        }
        oldTable.clearAllElements(this);
    }
}
