/**
 * 
 */
package com.tibco.cep.dashboard.plugin.beviews.runtime;

import com.tibco.cep.dashboard.plugin.beviews.BEViewsPlugIn;
import com.tibco.cep.dashboard.psvr.common.*;
import com.tibco.cep.dashboard.psvr.plugin.PropertyBasedResolver;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;

/**
 * @author anpatil
 *
 */
public class BEViewsComponentRenderingResolverImpl extends PropertyBasedResolver {
	

	public BEViewsComponentRenderingResolverImpl() throws FatalException{
		super(BEViewsPlugIn.PLUGIN_ID, ResolverType.COMPONENT_RENDERNG,"beviewscomponentrenderinghandlers.map");
	}

}
