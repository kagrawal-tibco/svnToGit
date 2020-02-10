package com.tibco.cep.dashboard.psvr.vizengine;

import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandlerFactory;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;

/**
 * @author anpatil
 *
 */
public class VisualizationHandlerFactory extends AbstractHandlerFactory {
    
    private static VisualizationHandlerFactory instance = null;

    public static final synchronized VisualizationHandlerFactory getInstance() {
        if (instance == null) {
            instance = new VisualizationHandlerFactory();
        }
        return instance;
    }
    
    private VisualizationHandlerFactory(){
    	super("visualizationhandlerfactory", "Visualization Handler Factory", ResolverType.VISUALIZATION_HANDLER);
    }

    public final VisualizationHandler getHandler(MALVisualization visualization) throws VisualizationException, PluginException{
    	return (VisualizationHandler) super.getHandler(visualization);
    }

}