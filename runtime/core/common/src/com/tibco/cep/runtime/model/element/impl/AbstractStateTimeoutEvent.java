package com.tibco.cep.runtime.model.element.impl;

import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineState;
import com.tibco.cep.runtime.model.event.EventDeserializer;
import com.tibco.cep.runtime.model.event.EventSerializer;
import com.tibco.cep.runtime.model.event.impl.TimeEventImpl;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 14, 2010
 * Time: 6:50:03 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class AbstractStateTimeoutEvent extends TimeEventImpl
{
    public long sm_id;              //todo - remove public later
    public String property_name;    //todo - remove public later
    protected static int COUNT_UNSET = Integer.MIN_VALUE;
    protected static int CLOSURE_UNPARSEABLE = Integer.MIN_VALUE + 1;
    transient private int count;
    
    //these superclass constructors are not currently used but would be used if a 
    //StateTimeoutEvent ever was stored in the cache and then deserialized
    public AbstractStateTimeoutEvent(long _id) { super(_id); superConsInit(); }
    public AbstractStateTimeoutEvent(long _id, String _extId) { super(_id, _extId); superConsInit(); }
    private void superConsInit() {
    	count = COUNT_UNSET;
    	closure = null;
    }

    public AbstractStateTimeoutEvent(long _id, long sm_id, String property_name, long next, int count) {
        super(_id);
        this.property_name=property_name;
        this.sm_id=sm_id;
        this.setScheduledTime(next);
        this.count = count;
        closure = null;
    }

    public boolean isRepeating() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getInterval() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getTTL() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
    
    public int getCount() {
    	if(count == COUNT_UNSET) {
    		try {
    			if(closure != null && closure.length() > 0) {
    				count = Integer.parseInt(closure);
    			}
    		} catch (NumberFormatException nfe) {
    			count = CLOSURE_UNPARSEABLE;
    		}
    	}
    	return count;
    }
    
    public String getClosure() {
    	if(closure == null && count != COUNT_UNSET) {
    		closure = String.valueOf(count);
    	}
    	return closure;
    }

    public void onExpiry() {
        //StateTimeoutEvent var = (StateTimeoutEvent) objects[0];
        RuleSession rs = RuleSessionManager.getCurrentRuleSession();
        StateMachineConceptImpl sm= (StateMachineConceptImpl) rs.getObjectManager().getElement(sm_id);
        if (sm != null) {
            PropertyStateMachineState property= (PropertyStateMachineState) sm.getProperty(property_name);
            //System.out.println("###Curr State =" + property.getCurrentState());
            if (property.isActive() || property.isReady()) {
                if (getCount() >= 0) {
                    if (property.getCount() == getCount()) {
                        Object [] args = new Object [] {sm.getParent()};
                        if(args[0] != null) {
                            property.timeout(args);
                        }
                    }
                } else {
                    Object [] args = new Object [] {sm.getParent()};
                    if(args[0] != null) {
                        property.timeout(args);
                    }
                }
            }
        }
    }

    public String getType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    /*
     * Current impl never serializes StateTimeouts.  It puts a SMTimeoutTask into the scheduler 
     * and a local 0 ttl State timeout event is asserted when that schedule expires. 
     */
    public void serialize(EventSerializer serializer) {
        _serialize(serializer);
        serializer.endEvent();
    }

    protected void _serialize(EventSerializer serializer) {
        super._serialize(serializer);

        serializer.startProperty("sm_id", 5, true);
        serializer.writeEntityRefProperty(sm_id);
        serializer.endProperty();

        serializer.startProperty("property_name", 6, true);
        serializer.writeStringProperty(property_name);
        serializer.endProperty();
    }

    public void deserialize(EventDeserializer deserializer) {
        _deserialize(deserializer);
        deserializer.endEvent();
    }

    protected void _deserialize(EventDeserializer deserializer) {
        super._deserialize(deserializer);

        if(deserializer.startProperty("sm_id", 5)) {
        	sm_id=deserializer.getEntityRefPropertyAsLong();
        }
        deserializer.endProperty();

        if(deserializer.startProperty("property_name", 6)) {
        	property_name=deserializer.getStringProperty();
        }
        deserializer.endProperty();
    }

    public boolean hasExpiryAction() {
        return true;
    }    
}
