package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElementList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;

/*
@author ssailapp
@date Feb 3, 2010 1:48:19 PM
 */

public class AgentPropertyTreeProviderModel extends PropertyTreeProviderModel {
	
	private AgentClass agent;

	public AgentPropertyTreeProviderModel(ClusterConfigModelMgr modelmgr) {
		super(modelmgr);
	}
	
	public void updateModel(Tree tree) {
		PropertyElementList propList = agent.properties;
		propList.propertyList.clear();
    	for (TreeItem item: tree.getItems()) {
    		PropertyElement propElement = getPropertyElement(item); 
    		if (propElement != null)
    			propList.propertyList.add(propElement);
    	}
		modelmgr.updateAgentProperties(agent, propList);
	}

	public void setAgent(AgentClass agent) {
		this.agent = agent;
	}
}
