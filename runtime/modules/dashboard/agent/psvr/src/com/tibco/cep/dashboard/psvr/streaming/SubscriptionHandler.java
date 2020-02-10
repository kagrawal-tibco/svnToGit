package com.tibco.cep.dashboard.psvr.streaming;

import java.security.Principal;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.security.SecurityToken;

/**
 * @author apatil
 *
 */
public abstract class SubscriptionHandler {

    protected SecurityToken token;
    protected Principal preferredRole;
	protected TokenRoleProfile tokenRoleProfile;

    protected void process(Map<String, String> subscription, PresentationContext ctx) throws StreamingException, DataException, PluginException{
    	doProcess(subscription,ctx);
    }

    protected abstract void doProcess(Map<String, String> subscription, PresentationContext ctx) throws StreamingException, DataException, PluginException;

	protected abstract DataSourceUpdateHandler[] getUpdateHandlers();

    protected abstract VisualizationData[] processUpdate(String subscriptionName) throws StreamingException;

    protected abstract void shutdown();

    protected abstract boolean isEquivalentTo(Map<String, String> request);

    protected abstract String getSubscribedTarget();

    public abstract MALComponent[] getComponents();

}
