package com.tibco.cep.kernel.core.base.tuple;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableTable;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.Identifier;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 7, 2006
 * Time: 1:11:10 AM
 * To change this template use File | Settings | File Templates.
 */
abstract public class TupleTable extends JoinTable 
{
    public TupleTable(Identifier[] identifiers) throws SetupException {
        super(identifiers);
    }

    public TupleTable(Identifier[] identifiers, short Id) {
        super(identifiers, Id);
    }

    public boolean isEmpty() {
        return getTableImpl().isEmpty();
    }

    public int size() {
        return getTableImpl().size();
    }

    public TableIterator iterator() {
        return getTableImpl().iterator();
    }

  public String contentListForm() {
        return getTableImpl().contentListForm(this);
    }

    public String contentHashForm() {
        return getTableImpl().contentHashForm(this);
    }
    
    protected void clearAllElements() {
        getTableImpl().clearAllElements(this);
    }
    
    public boolean add(TupleRow row) {
        return getTableImpl().add(row, this); 
    }

    public TupleRow remove(TupleRow row) {
        return getTableImpl().remove(row);
    }
    
    public void reset() {
        getTableImpl().reset();
    }

    public void lock() {
        getTableImpl().lock();
    }
    
    public void unlock() {
        getTableImpl().unlock();
    }
    
    //may return null
    protected abstract TupleTableTable getTableImpl();

    public void initTableImpl(WorkingMemoryImpl wmImpl) {
        if(wmImpl.isConcurrent()) {
            EntitySharingLevel sharingLevel = EntitySharingLevel.SHARED;
            //For tuples, if any one of the identifier's recursive level is mixed
            //the the entire tuple is mixed, else if any one is unshared (cache-only), the entire tuple is unshared.
            //At runtime any element of the tuple being unshared will make the tuple cache-only.
            //All elements at runtime are either shared or unshared, mixed is only meaningful when creating the JoinTables
            for (Identifier id : idrs) {
                EntitySharingLevel lev = getRecursiveSharingLevel(wmImpl, id.getType());
                if(lev == EntitySharingLevel.MIXED) {
                    sharingLevel = EntitySharingLevel.MIXED;
                    break;
                } else if(lev == EntitySharingLevel.UNSHARED) {
                    //don't break after this since another elements mixed status will override it
                    sharingLevel = EntitySharingLevel.UNSHARED;
                }
            }

            setTableImpl_Concurrent(sharingLevel);
        } else {
            setTableImpl_ST();
        }
    }
    
    protected abstract void setTableImpl_ST();
    protected abstract void setTableImpl_Concurrent(EntitySharingLevel lev);
}
