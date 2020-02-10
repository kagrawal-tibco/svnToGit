package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElementList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;

/*
@author ssailapp
@date Feb 3, 2010 4:52:58 PM
 */

public class CacheOmPropertyTreeProviderModel extends PropertyTreeProviderModel {

	public CacheOmPropertyTreeProviderModel(ClusterConfigModelMgr modelmgr) {
		super(modelmgr);
	}
	
	public void updateModel(Tree tree) {
		PropertyElementList propList = modelmgr.getModel().om.cacheOm.cacheProps;
		propList.propertyList.clear();
    	for (TreeItem item: tree.getItems()) {
    		PropertyElement propElement = getPropertyElement(item); 
    		if (propElement != null)
    			propList.propertyList.add(propElement);
    	}
		modelmgr.updateCacheOmProperties(propList);
	}
}
