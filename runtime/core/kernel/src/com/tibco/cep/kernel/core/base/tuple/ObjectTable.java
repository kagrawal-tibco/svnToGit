package com.tibco.cep.kernel.core.base.tuple;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableTable;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.Identifier;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Sep 16, 2004
 * Time: 7:40:25 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class ObjectTable extends JoinTable 
{
    public ObjectTable(Identifier[] identifiers) throws SetupException {
        super(identifiers);
    }

    public ObjectTable(Identifier[] identifiers, short Id) {
        super(identifiers, Id);
    }

    public TableIterator iterator() {
        return getTableImpl().iterator();
    }

    protected void clearAllElements() {
        return; //nothing to cleanup for ObjectTable
    }

    public boolean remove(Handle objHandle) {
        return getTableImpl().remove(objHandle);
    }
    
    public void reset() {
        getTableImpl().reset();
    }

    public boolean isEmpty() {
        return getTableImpl().isEmpty();
    }

    public int size() {
        return getTableImpl().size();
    }
    
    public void lock() {
        getTableImpl().lock();
    }
    public void unlock() {
        getTableImpl().unlock();
    }

    public String contentListForm() {
        return getTableImpl().contentListForm(this);
    }

    public String contentHashForm() {
        return getTableImpl().contentHashForm(this);
    }
    
    //may return null
    abstract protected ObjectTableTable getTableImpl();

    public void initTableImpl(WorkingMemoryImpl wmImpl) {
        if(wmImpl.isConcurrent()) {
            EntitySharingLevel lev = getRecursiveSharingLevel(wmImpl, idrs[0].getType());
            setTableImpl_Concurrent(lev);
        } else {
            setTableImpl_ST();
        }
    }
    
    protected abstract void setTableImpl_ST();
    protected abstract void setTableImpl_Concurrent(EntitySharingLevel lev);
}
