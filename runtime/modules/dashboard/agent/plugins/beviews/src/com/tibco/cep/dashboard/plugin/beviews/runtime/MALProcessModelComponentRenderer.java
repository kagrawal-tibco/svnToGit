package com.tibco.cep.dashboard.plugin.beviews.runtime;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.vizengine.ComponentRenderer;

public class MALProcessModelComponentRenderer extends ComponentRenderer {

	private static final String RENDERING_SWF = "processmodel.swf";

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
