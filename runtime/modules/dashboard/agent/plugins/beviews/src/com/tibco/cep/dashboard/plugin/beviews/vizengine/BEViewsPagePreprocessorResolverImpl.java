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
public class BEViewsPagePreprocessorResolverImpl extends PropertyBasedResolver {
	
	public BEViewsPagePreprocessorResolverImpl() throws FatalException{
		super(BEViewsPlugIn.PLUGIN_ID,ResolverType.PAGE_PREPROCESSOR,"beviewspageprocessors.map");
	}

}