package com.tibco.cep.dashboard.plugin.beviews.mal;

import com.tibco.cep.dashboard.plugin.beviews.BEViewsPlugIn;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALExternalReference;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.SimpleResolverImpl;

public class BEViewsExternalReferenceResolverImpl extends SimpleResolverImpl {

	public BEViewsExternalReferenceResolverImpl() {
		super(BEViewsPlugIn.PLUGIN_ID, ResolverType.EXTERNAL_REFERENCE_HANDLER, BEViewsExternalReferenceProcessor.class);
	}

	@Override
	public boolean isAcceptable(MALElement element) {
		if (element instanceof MALExternalReference) {
			return true;
		}
		return false;
	}

}
