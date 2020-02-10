package com.tibco.cep.runtime.model.event.impl;

import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.model.event.CommandEvent;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jan 23, 2009
 * Time: 5:23:58 PM
 * To change this template use File | Settings | File Templates.
 */

public  abstract class CommandEventImpl extends EntityImpl implements CommandEvent {
    CommandContext ctx;

    public CommandEventImpl() {
        super(-1, null);
    }

    public void delete() {
    }

    public boolean getRetryOnException() {
        return false;  
    }

    public void start(Handle handle) {
    }

    public void acknowledge() {
        if (ctx != null) {
            ctx.acknowledge();
        }
    }

    public CommandContext getContext() {
        return ctx;
    }

    public void setContext(CommandContext ctx) {
        this.ctx=ctx;
    }

    public void setTTL(long ttl) {
        throw new RuntimeException("TTL is not settable for " + this);
    }

    public long getTTL() {
        return 0;
    }

    public boolean hasExpiryAction() {
        return false;
    }

    public void onExpiry() {
        //no-op
    }
//
    public void toXiNode(XiNode node) throws Exception {
        baseXiNode(node);
    }

    public Object getPropertyValue(String name) throws NoSuchFieldException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setPropertyValue(String name, Object value) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

//
//
}

