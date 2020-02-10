/**
 * 
 */
package com.tibco.cep.dashboard.plugin.beviews.vizengine.actions;

import com.tibco.cep.dashboard.plugin.beviews.BEViewsPlugIn;
import com.tibco.cep.dashboard.psvr.common.*;
import com.tibco.cep.dashboard.psvr.plugin.PropertyBasedResolver;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;


/**
 * @author anpatil
 * 
 */
public class BEViewsActionConfigGeneratorResolverImpl extends PropertyBasedResolver {

	public BEViewsActionConfigGeneratorResolverImpl() throws FatalException {
		super(BEViewsPlugIn.PLUGIN_ID, ResolverType.ACTIONCONFIG_GENERATOR, "beviewsactionconfiggenerators.map");
	}
}
