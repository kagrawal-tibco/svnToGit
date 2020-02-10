package com.tibco.cep.runtime.service.cluster.filters;

import java.util.Map.Entry;

import com.tibco.cep.runtime.service.cluster.events.EventTuple;
import com.tibco.cep.runtime.service.om.api.Invocable;

public class AcquireEvent implements Invocable {

	private static final long serialVersionUID = 3364804950692832699L;

	int agentId;
    public AcquireEvent (int agentId) {
    	this.agentId = agentId;
    }

	@Override
	public Object invoke(Entry entry) throws Exception {
        if (entry.getValue() instanceof EventTuple) {
            EventTuple tuple= (EventTuple) entry.getValue();
            if (tuple.acquire(agentId)) {
                entry.setValue(tuple);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
	}

}
