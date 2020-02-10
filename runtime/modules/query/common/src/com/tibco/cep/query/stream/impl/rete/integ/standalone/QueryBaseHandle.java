package com.tibco.cep.query.stream.impl.rete.integ.standalone;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;

/*
 * Author: Ashwin Jayaprakash Date: Mar 7, 2008 Time: 2:58:22 PM
 */

public class QueryBaseHandle extends BaseHandle {
    protected QueryTypeInfo typeInfo;

    protected WorkingMemory workingMemory;

    public QueryBaseHandle(QueryTypeInfo typeInfo) {
        super(null);
        this.typeInfo = typeInfo;
        this.workingMemory = typeInfo.getWorkingMemory();
    }

    @Override
    public QueryTypeInfo getTypeInfo() {
        return typeInfo;
    }

    @Override
    public WorkingMemory getWorkingMemory() {
        return workingMemory;
    }

    public void discard() {
        typeInfo = null;
        workingMemory = null;
    }

    //---------

    /**
     * @throws UnsupportedOperationException
     */
    public Object getObject() {
        throw new UnsupportedOperationException(
                "This method() should not be called on QueryBasedHandle");
    }

    public boolean hasRef() {
        throw new UnsupportedOperationException(
                "This method() should not be called on QueryBasedHandle");
    }
}
