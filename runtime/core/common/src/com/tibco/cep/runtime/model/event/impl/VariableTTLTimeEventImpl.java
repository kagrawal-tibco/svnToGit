package com.tibco.cep.runtime.model.event.impl;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 28, 2007
 * Time: 6:58:24 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class VariableTTLTimeEventImpl extends TimeEventImpl {
    protected long ttl = 0;

    public VariableTTLTimeEventImpl() {
        super();
    }

    public VariableTTLTimeEventImpl(long id) {
        super(id);
    }

    public VariableTTLTimeEventImpl(long _id, java.lang.String _extId) {
        super(_id, _extId);

    }
    
    public long getTTL() {
        return ttl;
    }

    public void setTTL(long _ttl) {
        this.ttl = _ttl;
    }

    protected void setTTL_deserialize(long _ttl) {
        setTTL(_ttl);
    }

    public boolean getRetryOnException() {
        return true;
    }
    
    //will be overridden by the generated classes
    public VariableTTLTimeEventImpl cloneTimeEvent(long id) {
        try {
            VariableTTLTimeEventImpl te = getClass().getConstructor(long.class, String.class).newInstance(id, getExtId());
            te.setTTL(getTTL());
            if(getClosure() != null) te.setClosure(getClosure());
            te.setScheduledTime(getScheduledTimeMillis());
            return te;
        } catch (InvocationTargetException e) {
            return null;
        } catch (NoSuchMethodException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}
