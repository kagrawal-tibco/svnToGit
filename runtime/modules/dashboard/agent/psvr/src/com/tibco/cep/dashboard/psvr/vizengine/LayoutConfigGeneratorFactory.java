package com.tibco.cep.dashboard.psvr.vizengine;

import com.tibco.cep.dashboard.psvr.mal.model.MALLayout;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandlerFactory;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;

/**
 * @author anpatil
 *
 */
public class LayoutConfigGeneratorFactory extends AbstractHandlerFactory {

    private static LayoutConfigGeneratorFactory instance = null;

    public static final synchronized LayoutConfigGeneratorFactory getInstance() {
        if (instance == null) {
            instance = new LayoutConfigGeneratorFactory();
        }
        return instance;
    }
    
    private LayoutConfigGeneratorFactory(){
    	super("layoutconfiggeneratorfactory", "LayoutConfig Generator Factory", ResolverType.LAYOUT_GENERATOR);
    }
    
    public final LayoutConfigGenerator getGenerator(MALLayout malLayout) throws PluginException {
    	return (LayoutConfigGenerator) super.getHandler(malLayout);
    }

}