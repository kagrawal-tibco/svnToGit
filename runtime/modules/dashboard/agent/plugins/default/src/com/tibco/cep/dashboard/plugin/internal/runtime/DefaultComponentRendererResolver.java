package com.tibco.cep.dashboard.plugin.internal.runtime;

import com.tibco.cep.dashboard.plugin.internal.DefaultPlugIn;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.SimpleResolverImpl;

public class DefaultComponentRendererResolver extends SimpleResolverImpl {

	public DefaultComponentRendererResolver() {
		super(DefaultPlugIn.PLUGIN_ID, ResolverType.COMPONENT_RENDERNG, DefaultComponentRenderer.class);
	}

}