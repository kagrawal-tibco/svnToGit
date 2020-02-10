/**
 * 
 */
package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import com.tibco.cep.dashboard.plugin.beviews.BEViewsPlugIn;
import com.tibco.cep.dashboard.psvr.common.FatalException;
import com.tibco.cep.dashboard.psvr.plugin.PropertyBasedResolver;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;

/**
 * @author anpatil
 *
 */
public class BEViewsComponentHandlerResolverImpl extends PropertyBasedResolver {
	
	public BEViewsComponentHandlerResolverImpl() throws FatalException{
		super(BEViewsPlugIn.PLUGIN_ID,ResolverType.COMPONENT_HANDLER,"beviewscomponenthandlers.map");
	}

}