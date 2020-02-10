package com.tibco.cep.kernel.core.base;

import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Oct 6, 2006
 * Time: 11:56:49 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractElementHandle extends BaseHandle {

    AbstractElementHandle nextHandle;

    protected AbstractElementHandle(AbstractElementHandle _next, TypeInfo _typeInfo) {
        super(_typeInfo);
        nextHandle = _next;
        
    }

//    public AbstractElementHandle nextHandle() {
//        return nextHandle;
//    }
//
//    public void setNextHandle(AbstractElementHandle _next) {
//        nextHandle = _next;
//    }

    abstract public long getElementId();

}
