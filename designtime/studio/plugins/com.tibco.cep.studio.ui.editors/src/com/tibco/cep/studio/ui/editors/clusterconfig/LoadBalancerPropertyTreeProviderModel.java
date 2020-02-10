package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElementList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;

/*
@author ssailapp
@date Feb 3, 2010 11:57:44 AM
 */

public class LoadBalancerPropertyTreeProviderModel extends PropertyTreeProviderModel {

	private LoadBalancerConfig loadBalancerConfig;

	public LoadBalancerPropertyTreeProviderModel(ClusterConfigModelMgr modelmgr) {
		super(modelmgr);
	}
	
	public void updateModel(Tree tree) {
		PropertyElementList propList = loadBalancerConfig.properties;
		propList.propertyList.clear();
    	for (TreeItem item: tree.getItems()) {
    		PropertyElement propElement = getPropertyElement(item); 
    		if (propElement != null)
    			propList.propertyList.add(propElement);
    	}
		modelmgr.updateLoadBalancerProperties(loadBalancerConfig, propList);
	}

	public void setLoadBalancerConfig(LoadBalancerConfig loadBalancerConfig) {
		this.loadBalancerConfig = loadBalancerConfig;
	}

}
