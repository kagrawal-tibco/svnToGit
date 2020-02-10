package com.tibco.cep.runtime.service.cluster.filters;

import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.events.EventTuple;
import com.tibco.cep.runtime.service.om.api.Invocable;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 5, 2008
 * Time: 2:12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReleaseEvent implements Invocable {

	private static final long serialVersionUID = -3207107179500510615L;

	int agentId;
    transient Logger m_logger;


    public ReleaseEvent(int agentId) {
        this.agentId=agentId;
        m_logger=CacheClusterProvider.getInstance().getCacheCluster().getRuleServiceProvider().getLogger(ReleaseEvent.class);
    }

    public ReleaseEvent() {
    }

    @Override
    public Object invoke(Map.Entry entry) {

        if (m_logger != null) {
        	m_logger.log(Level.DEBUG,"process called " + entry.getValue());
        }
        if (entry.getValue() instanceof EventTuple) {
            EventTuple tuple = (EventTuple) entry.getValue();
            if (tuple.isOwned(agentId)) {
                tuple.releaseAgentId();
                entry.setValue(tuple);
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
