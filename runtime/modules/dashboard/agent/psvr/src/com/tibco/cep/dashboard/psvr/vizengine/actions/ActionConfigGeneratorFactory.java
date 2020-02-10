package com.tibco.cep.dashboard.psvr.vizengine.actions;

import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandlerFactory;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;

/**
 * @author apatil
 * 
 */
public class ActionConfigGeneratorFactory extends AbstractHandlerFactory {

	private static ActionConfigGeneratorFactory instance = null;

	public static final synchronized ActionConfigGeneratorFactory getInstance() {
		if (instance == null) {
			instance = new ActionConfigGeneratorFactory();
		}
		return instance;
	}

	private ActionConfigGeneratorFactory() {
		super("actionconfiggeneratorfactory", "ActionConfig Generator Factory", ResolverType.ACTIONCONFIG_GENERATOR);
	}

	public final ActionConfigGenerator[] getGenerators(MALElement element) throws VisualizationException, PluginException {
		AbstractHandler[] handlers = super.getHandlers(element);
		ActionConfigGenerator[] generators = new ActionConfigGenerator[handlers.length];
		for (int i = 0; i < generators.length; i++) {
			generators[i] = (ActionConfigGenerator) handlers[i];

		}
		return generators;
	}

}