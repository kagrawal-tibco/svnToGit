package com.tibco.cep.studio.core.addon;

import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.rt.AbstractAddOnLoader;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.adapters.CoreOntologyAdapter;

public class CoreAddOnLoader extends AbstractAddOnLoader<CoreOntologyAdapter> implements IAddOnLoader<CoreOntologyAdapter> {

	private static final String CORE_PLUGIN_ID = "com.tibco.cep.studio.common"; //$NON-NLS-1$
	
	public CoreAddOnLoader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public AddOn getAddOn() throws Exception {
		return super.loadAddOn(CORE_PLUGIN_ID);
	}
	
	
	@Override
	public CoreOntologyAdapter getOntology(String projectName) {
		return new CoreOntologyAdapter(StudioProjectCache.getInstance().getIndex(projectName));
	}

}
