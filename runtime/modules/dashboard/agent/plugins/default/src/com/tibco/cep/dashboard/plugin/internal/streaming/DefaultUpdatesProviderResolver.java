package com.tibco.cep.dashboard.plugin.internal.streaming;

import com.tibco.cep.dashboard.plugin.internal.DefaultPlugIn;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.SimpleResolverImpl;

public class DefaultUpdatesProviderResolver extends SimpleResolverImpl {

	protected DefaultUpdatesProviderResolver() {
		super(DefaultPlugIn.PLUGIN_ID, ResolverType.PUB_SUB_HANDLER, DefaultPubSubHandler.class);
	}
	
}
