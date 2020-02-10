package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.ListProviderModel;
import com.tibco.cep.studio.ui.util.ListProviderUi;

/*
@author ssailapp
@date Dec 15, 2009 5:26:33 PM
 */

public class AgentDestinationsGrpListModel implements ListProviderModel {

	private ClusterConfigModelMgr modelmgr;
	private TreeViewer viewer;
	private DashInfProcQueryAgent agent;

	public AgentDestinationsGrpListModel(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		this.modelmgr = modelmgr;
		this.viewer = viewer;
	}

	@Override
	public void addItem(List list) {
		if (agent != null) {
			NewAgentDestinationsGrpDialog dialog = new NewAgentDestinationsGrpDialog(list.getShell());
			dialog.open(modelmgr.getDestinationsGroup(), agent.agentDestinationsGrpObj.agentDestinations);
			ArrayList<DestinationsGrp> selDestinationsGrp = dialog.getSelectedDestinations();
			for (DestinationsGrp destinationsGrp: selDestinationsGrp) {
				list.add(destinationsGrp.id);
				modelmgr.updateAgentDestinationsGroup(agent, destinationsGrp);
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
		//modelmgr.removeAgentDestinationsGrp(agent, item); //Old
		list.remove(item);
		BlockUtil.refreshViewer(viewer);
	}

	@Override
	public void onListItemSelectionChanged(String item) {
	}

	public void setParentAgent(DashInfProcQueryAgent parentAgent) {
		this.agent = parentAgent;
	}

}
