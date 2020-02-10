package com.tibco.cep.runtime.service.cluster.filters;

import com.tibco.cep.runtime.service.cluster.events.EventTuple;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 5, 2008
 * Time: 2:26:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentEventsFilter implements Filter {
    int agentId;

    public AgentEventsFilter() {
    }

    public AgentEventsFilter(int agentId) {
        this.agentId=agentId;
    }

    protected boolean filter(EventTuple event) {
        return event.isOwned(agentId);
    }

    public boolean evaluate(Object o, FilterContext context) {
        if (o instanceof EventTuple) {
            return filter(((EventTuple)o));
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

