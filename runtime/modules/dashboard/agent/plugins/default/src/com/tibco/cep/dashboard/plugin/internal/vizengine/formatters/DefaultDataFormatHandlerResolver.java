package com.tibco.cep.dashboard.plugin.internal.vizengine.formatters;

import com.tibco.cep.dashboard.plugin.internal.DefaultPlugIn;
import com.tibco.cep.dashboard.psvr.plugin.PropertyBasedResolver;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;

public class DefaultDataFormatHandlerResolver extends PropertyBasedResolver {

	public DefaultDataFormatHandlerResolver() {
		super(DefaultPlugIn.PLUGIN_ID, ResolverType.DATAFORMAT_HANDLER, "defaultdataformathandlers.map");
	}

}
