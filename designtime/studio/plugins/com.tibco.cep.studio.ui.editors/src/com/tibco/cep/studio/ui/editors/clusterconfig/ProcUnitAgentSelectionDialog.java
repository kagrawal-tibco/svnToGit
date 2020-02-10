package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;

/*
@author ssailapp
@date May 3, 2010 6:44:43 PM
 */

public class ProcUnitAgentSelectionDialog extends AgentSelectionDialog {

	public ProcUnitAgentSelectionDialog(Shell parent, ClusterConfigModelMgr modelmgr) {
		super(parent, modelmgr);
	}
	
	@Override
	protected boolean isAgentExcludedFromList(AgentClass agentClass, ArrayList<?> filterGrp) {
		// Do not filter off existing agent classes. There can be any number of agent classes of same type in the PU.
		// Also, a Cache Agent class must be alone in its PU.
		
		if (AddonUtil.isExpressEdition() && agentClass instanceof CacheAgent)
			return true;
		if (AgentBlock.isQueryAgent(agentClass) && !modelmgr.isQueryAgentEnabled())
			return true;
		if (AgentBlock.isDashboardAgent(agentClass) && !modelmgr.isDashboardAgentEnabled())
			return true;
			
		if ( (agentClass instanceof CacheAgent) && filterGrp.size() > 0) {
			if (tooltip.equals(""))
				tooltip = "Cache agent can't be added as it must be alone in its Processing Unit. \n";
			return true;
		}

		for (Map<String, String> map: (ArrayList<Map<String, String>>) filterGrp) {
			String filterGrpAgentName = map.get(ProcessingUnit.AGENT_REF);
			/*
			if (agentClass.name.equalsIgnoreCase(filterGrpAgentName))
				return true;
			*/	
			 AgentClass filterGrpAgent = modelmgr.getAgentClass(filterGrpAgentName);
			 if (filterGrpAgent instanceof CacheAgent) {
				 tooltip = "No more agents can be added, as Cache agent must be alone in its Processing Unit. \n";
				 return true;
			 }
		}
		return false;
	}
	
}
