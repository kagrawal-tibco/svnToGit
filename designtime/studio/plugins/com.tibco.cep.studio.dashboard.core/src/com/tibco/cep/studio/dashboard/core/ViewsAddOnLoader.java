package com.tibco.cep.studio.dashboard.core;

import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.rt.AbstractAddOnLoader;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.dashboard.DashboardOntologyAdapter;

/*
@author ssailapp
@date Nov 9, 2010
 */

public class ViewsAddOnLoader extends AbstractAddOnLoader<DashboardOntologyAdapter> {

	private static final String VIEWS_PLUGIN_ID = "com.tibco.cep.studio.dashboard.common";//$NON-NLS-1$

	public ViewsAddOnLoader() {
	}

	@Override
	public AddOn getAddOn() throws Exception {
		return super.loadAddOn(VIEWS_PLUGIN_ID);
	}

	@Override
	public DashboardOntologyAdapter getOntology(String projectName) {
		return new DashboardOntologyAdapter(StudioProjectCache.getInstance().getIndex(projectName));
	}

//	@Override
//	public String getName() {
//		return AddonUtil.ADDON_VIEWS;
//	}
//
//	@Override
//	public String getAgentClass() {
//		return (AgentClass.AGENT_TYPE_DASHBOARD);
//	}

}
