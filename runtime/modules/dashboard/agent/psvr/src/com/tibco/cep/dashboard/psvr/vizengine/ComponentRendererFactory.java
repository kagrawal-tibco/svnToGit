package com.tibco.cep.dashboard.psvr.vizengine;

import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandlerFactory;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;

/**
 * @author anpatil
 *
 */
public class ComponentRendererFactory extends AbstractHandlerFactory {

    private static ComponentRendererFactory instance = null;

    public static final synchronized ComponentRendererFactory getInstance() {
        if (instance == null) {
            instance = new ComponentRendererFactory();
        }
        return instance;
    }
    
    private ComponentRendererFactory(){
    	super("componentrendererfactory", "Component Renderer Factory", ResolverType.COMPONENT_RENDERNG);
    }
    
    public final ComponentRenderer getRenderer(MALComponent malComponent) throws PluginException {
    	return (ComponentRenderer) super.getHandler(malComponent);
    }

}