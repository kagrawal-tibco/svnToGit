package com.tibco.cep.dashboard.plugin.beviews.streaming;

import com.tibco.cep.dashboard.plugin.beviews.BEViewsPlugIn;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.SimpleResolverImpl;
import com.tibco.cep.designtime.core.model.element.Metric;

public class BEViewsUpdatesProviderResolverImpl extends SimpleResolverImpl{

	public BEViewsUpdatesProviderResolverImpl() {
		super(BEViewsPlugIn.PLUGIN_ID, ResolverType.PUB_SUB_HANDLER, BEViewsPubSubHandler.class);
	}
	
	@Override
	public boolean isAcceptable(MALElement element) {
		if (element instanceof MALSourceElement){
			if (((MALSourceElement)element).getSource() instanceof Metric){
				return true;
			}
		}
		return false;
	}	

}
