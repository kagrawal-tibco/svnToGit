package com.tibco.cep.decision.table.addon;

import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.rt.AbstractAddOnLoader;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.adapters.CoreOntologyAdapter;

/*
@author ssailapp
@date Feb 22, 2011
 */

public class DecisionManagerAddOnLoader extends AbstractAddOnLoader<CoreOntologyAdapter> implements IAddOnLoader<CoreOntologyAdapter> {
	
	public static final String DECISION_COMMON_PLUGIN_ID ="com.tibco.cep.decision.table.common";//$NON-NLS-1$

	public DecisionManagerAddOnLoader() {
	}
	
	
	@Override
	public AddOn getAddOn() throws Exception {
		return super.loadAddOn(DECISION_COMMON_PLUGIN_ID);
	}
	
	@Override
	public CoreOntologyAdapter getOntology(String projectName) {
		return new CoreOntologyAdapter(StudioProjectCache.getInstance().getIndex(projectName));
	}

//	@Override
//	public String getName() {
//		return AddonUtil.ADDON_DECISIONMANAGER;
//	}
//
//	@Override
//	public String getAgentClass() {
//		return null;
//	}

}
