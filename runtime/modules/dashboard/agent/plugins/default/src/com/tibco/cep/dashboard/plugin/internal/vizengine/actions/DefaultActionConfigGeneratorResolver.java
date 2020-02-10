package com.tibco.cep.dashboard.plugin.internal.vizengine.actions;

import com.tibco.cep.dashboard.plugin.internal.DefaultPlugIn;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.SimpleResolverImpl;


public class DefaultActionConfigGeneratorResolver extends SimpleResolverImpl {

	public DefaultActionConfigGeneratorResolver() {
		super(DefaultPlugIn.PLUGIN_ID,ResolverType.ACTIONCONFIG_GENERATOR, DefaultActionConfigGenerator.class);
	}

}
