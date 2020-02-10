package com.tibco.cep.query.rule;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 2, 2007
 * Time: 5:15:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryEvent extends EntityImpl implements Event {
    private List bindingParameters = new ArrayList();


    public QueryEvent(String extId) {
        super(extId);
        this.extId = extId;
    }

    public void start(Handle handle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getTTL() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
    
    public String getType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getPropertyValue(String name) throws NoSuchFieldException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setPropertyValue(String name, Object value) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean getRetryOnException() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasExpiryAction() {
        return false;
    }

    public void onExpiry() {

    }

    public void setTTL(long ttl) {
        throw new RuntimeException("TTL is not settable for " + this);
    }

    public void addBindingParameter(Object bindParameter) {
        this.bindingParameters.add(bindParameter);
    }
    public Object getBindingParameter(int index) {
        return this.bindingParameters.get(index);
    }
}
