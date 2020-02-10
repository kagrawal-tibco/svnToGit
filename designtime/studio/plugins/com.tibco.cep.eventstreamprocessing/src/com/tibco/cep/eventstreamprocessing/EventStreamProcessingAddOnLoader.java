package com.tibco.cep.eventstreamprocessing;

import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.rt.AbstractAddOnLoader;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.adapters.CoreOntologyAdapter;

/*
@author ssailapp
@date Nov 9, 2010
 */


public class EventStreamProcessingAddOnLoader extends AbstractAddOnLoader<CoreOntologyAdapter> implements IAddOnLoader<CoreOntologyAdapter> {

	private static final String ESP_PLUGIN_ID = "com.tibco.cep.eventstreamprocessing.common";//$NON-NLS-1$
	public EventStreamProcessingAddOnLoader() {
	}
	@Override
	public AddOn getAddOn() throws Exception {
		return super.loadAddOn(ESP_PLUGIN_ID);
	}
	
	@Override
	public CoreOntologyAdapter getOntology(String projectName) {
		return new CoreOntologyAdapter(StudioProjectCache.getInstance().getIndex(projectName));
	}

//	@Override
//	public String getName() {
//		return AddonUtil.ADDON_EVENTSTREAMPROCESSING;
//	}
//
//	@Override
//	public String getAgentClass() {
//		return (AgentClass.AGENT_TYPE_QUERY);	
//	}
}
