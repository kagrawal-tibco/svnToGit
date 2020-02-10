package com.tibco.cep.dashboard.plugin.internal.vizengine;

import com.tibco.cep.dashboard.plugin.internal.DefaultPlugIn;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.SimpleResolverImpl;

public class DefaultPagePreprocessorResolver extends SimpleResolverImpl {

	public DefaultPagePreprocessorResolver() {
		super(DefaultPlugIn.PLUGIN_ID, ResolverType.PAGE_PREPROCESSOR, DefaultPagePreProcessor.class);
	}

}
