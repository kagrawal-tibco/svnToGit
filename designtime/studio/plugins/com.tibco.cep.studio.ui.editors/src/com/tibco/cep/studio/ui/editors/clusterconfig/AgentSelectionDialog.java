package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashboardAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.InfAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.QueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;

/*
@author ssailapp
@date Dec 22, 2009 3:17:51 AM
 */

public class AgentSelectionDialog extends ConfigElementSelectionDialog {

	protected ArrayList<AgentClass> selAgentClass;
	protected ClusterConfigModelMgr modelmgr;
	protected String tooltip = "";
	
	public AgentSelectionDialog(Shell parent, ClusterConfigModelMgr modelmgr) {
		super(parent);
		selAgentClass = new ArrayList<AgentClass>();
		this.modelmgr = modelmgr;
	}
	
	public void open(ArrayList<?> agentClasses, ArrayList<?> filterGrp) {
		initDialog("Agent Classes Selector", false);
		tooltip = "";
		for (Object agentClass: agentClasses) {
			if (!isAgentExcludedFromList((AgentClass)agentClass, filterGrp)) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setImage(getAgentClassImage((AgentClass)agentClass));
				item.setText(((AgentClass)agentClass).name);
				item.setData(agentClass);
			}
		}
		
		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				for (TableItem item: table.getItems()) {
					if (item.getChecked()) 
						selAgentClass.add((AgentClass)item.getData());
				}
				dialog.dispose();
			}
		});

		openDialog(tooltip);
	}
	
	private Image getAgentClassImage(AgentClass agent) {
		if (agent instanceof InfAgent) {
			return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_INF_AGENT));
		} else if (agent instanceof CacheAgent) {
			return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_CACHE_AGENT));
		} else if (agent instanceof QueryAgent) {
			return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_QUERY_AGENT));
		} else if (agent instanceof DashboardAgent) {
			return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_DASHBOARD_AGENT));
		} else if (agent instanceof MMAgent) {
			return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_MM_AGENT));
		}
		return ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_AGENT);
	}
	
	protected boolean isAgentExcludedFromList(AgentClass agentClass, ArrayList<?> filterGrp) {
		if (filterGrp == null)
			return false;
		for (AgentClass agent: (ArrayList<AgentClass>) filterGrp) {
			if (agentClass.name.equalsIgnoreCase(agent.name))
				return true;
		}
		return false;
	}
	
	public ArrayList<AgentClass> getSelectedAgentClass() {
		return selAgentClass;
	}

}
