package com.tibco.cep.runtime.service.cluster.filters;

import com.tibco.cep.runtime.service.cluster.events.EventTuple;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;

public class AvailableEventFilter implements Filter {

	private static final long serialVersionUID = -2432365502095975682L;

    protected boolean filter(EventTuple event) {
        return event.isAvailable();
    }
    
    @Override
    public boolean evaluate(Object o, FilterContext context) {
        if (o instanceof EventTuple) {
//        	System.err.println("### IS EVENTTUPLE");
            return filter(((EventTuple)o));
        }
//        System.err.println("### IS NOT EVENTTUPLE " + o.getClass().getName());
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
