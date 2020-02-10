package com.tibco.cep.dashboard.plugin.beviews.runtime;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.vizengine.ComponentRenderer;

public class MALQueryManagerComponentRenderer extends ComponentRenderer {

	private static final String RENDERING_PROVIDER = "com.tibco.cep.dashboard.plugin.beviews.querymgr.QueryManagerComponentContentProvider";

	@Override
	protected void init() {
	}
	
	@Override
	public String getComponentRenderer() {
		return RENDERING_PROVIDER;
	}

	@Override
	protected void shutdown() throws NonFatalException {
	}

}
