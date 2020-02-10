package com.tibco.cep.dashboard.plugin.beviews.runtime;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.vizengine.ComponentRenderer;

public class MALPageSelectorComponentRenderer extends ComponentRenderer {

	private static final String RENDERING_SWF = "pageselector.swf";

	@Override
	protected void init() {
	}
	
	@Override
	public String getComponentRenderer() {
		return RENDERING_SWF;
	}

	@Override
	protected void shutdown() throws NonFatalException {
	}

}
