package com.tibco.cep.dashboard.psvr.vizengine;

import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandlerFactory;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;

/**
 * @author anpatil
 * 
 */
public class ComponentHandlerFactory extends AbstractHandlerFactory {

	private static ComponentHandlerFactory instance = null;

	public static final synchronized ComponentHandlerFactory getInstance() {
		if (instance == null) {
			instance = new ComponentHandlerFactory();
		}
		return instance;
	}

	private ComponentHandlerFactory() {
		super("componenthandlerfactory", "Component Handler Factory", ResolverType.COMPONENT_HANDLER);
	}

	public final ComponentHandler getHandler(MALComponent malComponent) throws VisualizationException, PluginException {
		return (ComponentHandler) super.getHandler(malComponent);
	}

}