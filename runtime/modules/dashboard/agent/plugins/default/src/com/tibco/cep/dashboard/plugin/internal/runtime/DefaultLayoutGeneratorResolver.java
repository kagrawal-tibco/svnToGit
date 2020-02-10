package com.tibco.cep.dashboard.plugin.internal.runtime;

import com.tibco.cep.dashboard.plugin.internal.DefaultPlugIn;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.SimpleResolverImpl;

public class DefaultLayoutGeneratorResolver extends SimpleResolverImpl {

	public DefaultLayoutGeneratorResolver() {
		super(DefaultPlugIn.PLUGIN_ID, ResolverType.LAYOUT_GENERATOR, FlowLayoutGenerator.class);
	}

}
