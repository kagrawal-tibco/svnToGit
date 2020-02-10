package com.tibco.cep.kernel.helper;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.ExecutionContext;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 11, 2007
 * Time: 1:03:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventExpiryExecutionContext implements ExecutionContext {
    Event e;

    public EventExpiryExecutionContext(Event e) {
        this.e = e;
    }

    public Object getCause() {
        return e;
    }

    public Object getParams() {
        return null;
    }

    public String[] info() {
        String value = e.toString();
        if(value.startsWith("be.gen"))
            value = value.substring("be.gen".length() + 1);
        return new String[]{"EventExpiryExecuted=" + value};
    }
}
