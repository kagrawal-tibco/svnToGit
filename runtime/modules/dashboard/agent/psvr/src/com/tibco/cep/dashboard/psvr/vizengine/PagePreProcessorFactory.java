package com.tibco.cep.dashboard.psvr.vizengine;

import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandlerFactory;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;

/**
 * @author anpatil
 *
 */
public class PagePreProcessorFactory extends AbstractHandlerFactory {

    private static PagePreProcessorFactory instance = null;

    public static final synchronized PagePreProcessorFactory getInstance() {
        if (instance == null) {
            instance = new PagePreProcessorFactory();
        }
        return instance;
    }
    
    private PagePreProcessorFactory(){
    	super("pagepreprocessorfactory", "Page PreProcessor Factory", ResolverType.PAGE_PREPROCESSOR);
    }
    
    public final PagePreProcessor getPreProcessor(MALPage page) throws PluginException {
    	return (PagePreProcessor) super.getHandler(page);
    }

}