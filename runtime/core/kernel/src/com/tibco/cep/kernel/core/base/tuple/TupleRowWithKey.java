package com.tibco.cep.kernel.core.base.tuple;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 7, 2006
 * Time: 12:43:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class TupleRowWithKey extends TupleRowNoKey {
    public int   rowKey;

    public TupleRowWithKey(Handle[] objs, int key) {
        super(objs);
        rowKey  = key;
    }

    public String toString() {
        if(objHandles == null) {
            return "[TupleRow rowKey=0 ; handles=null]";
        }
        String objRow = "";
        for(int i = 0; i < objHandles.length; i++) {
            objRow += "'" + ((BaseHandle)objHandles[i]).printInfo() + "' ";
        }
        return "[TupleRow rowKey=" + rowKey + "; handles=" + objRow + "]";
    }

    public int hashCode() {
        return rowKey;
    }
}
