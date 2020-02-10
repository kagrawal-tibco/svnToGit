package com.tibco.cep.kernel.core.base.tuple;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 25, 2004
 * Time: 6:15:58 PM
 * To change this template use Options | File Templates.
 */
public class TupleRowNoKey implements TupleRow {

    final public Handle[] objHandles;

    public TupleRowNoKey(Handle[] objs) {
        objHandles = new Handle[objs.length];
        for(int i = 0; i < objs.length; i++) {
            objHandles[i] = objs[i];
        }
    }

    public String toString() {
        if(objHandles == null) {
            return "[TupleRow handles=null]";
        }
        String objRow = "";
        for(int i = 0; i < objHandles.length; i++) {
            objRow += "'" + ((BaseHandle)objHandles[i]).printInfo() + "' ";
        }
        return "[TupleRow handles=" + objRow + "]";
    }

    public void removeTupleFromTable(short tableId) {
        ((TupleTable)JoinTable.getTable(tableId)).remove(this);
        for(int i = 0; i < objHandles.length; i++) {
            ((BaseHandle)objHandles[i]).unregisterTupleRow(tableId, this);
        }
    }

    public void associateTupleElements(short tableId) {
        for(int i = 0; i < objHandles.length; i++) {
            ((BaseHandle)objHandles[i]).registerTupleRow(tableId, this);
        }
    }

    public void unassociateTupleElements(short tableId) {
        for(int k = 0; k < objHandles.length; k++) {
            ((BaseHandle)objHandles[k]).unregisterTupleRows(tableId);
        }
    }
    
    public boolean isThreadLocal() {
        for(Handle h : objHandles) {
            if(h.getSharingLevel() == EntitySharingLevel.UNSHARED) return true;
        }
        return false;
    }
}