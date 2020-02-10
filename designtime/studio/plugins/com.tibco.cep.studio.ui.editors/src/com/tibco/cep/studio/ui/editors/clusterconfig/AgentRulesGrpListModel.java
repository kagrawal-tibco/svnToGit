package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RulesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.ListProviderModel;
import com.tibco.cep.studio.ui.util.ListProviderUi;

/*
@author ssailapp
@date Dec 14, 2009 6:29:03 PM
 */

public class AgentRulesGrpListModel implements ListProviderModel {

	private ClusterConfigModelMgr modelmgr;
	private TreeViewer viewer;
	private DashInfProcAgent agent;

	public AgentRulesGrpListModel(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		this.modelmgr = modelmgr;
		this.viewer = viewer;
	}

	@Override
	public void addItem(List list) {
		if (agent != null) {
			NewAgentRulesGrpDialog dialog = new NewAgentRulesGrpDialog(list.getShell());
			dialog.open(modelmgr.getRulesGroup(), agent.agentRulesGrpObj.agentRules);
			ArrayList<RulesGrp> selRulesGrp = dialog.getSelectedRulesGrp();
			for (RulesGrp rulesGrp: selRulesGrp) {
				list.add(rulesGrp.id);
				modelmgr.updateAgentRulesGroup(agent, rulesGrp);
				BlockUtil.refreshViewer(viewer);
			}
		}
	}

	@Override
	public Composite createConfigPanel(ListProviderUi listProvider) {
		return null;
	}

	@Override
	public void deleteItem(List list, String item) {
		//modelmgr.removeAgentRulesGrp(agent, item); //Old
		list.remove(item);
		BlockUtil.refreshViewer(viewer);
	}

	@Override
	public void onListItemSelectionChanged(String item) {
	}

	public void setParentAgent(DashInfProcAgent parentAgent) {
		this.agent = parentAgent;
	}

}
