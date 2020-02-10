/**
 * 
 */
package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import com.tibco.cep.dashboard.plugin.beviews.BEViewsPlugIn;
import com.tibco.cep.dashboard.psvr.common.*;
import com.tibco.cep.dashboard.psvr.plugin.PropertyBasedResolver;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;

/**
 * @author anpatil
 *
 */
public class BEViewsVisualizationHandlerResolverImpl extends PropertyBasedResolver {
	
	public BEViewsVisualizationHandlerResolverImpl() throws FatalException{
		super(BEViewsPlugIn.PLUGIN_ID, ResolverType.VISUALIZATION_HANDLER,"beviewsvisualizationhandlers.map");
	}
	
}