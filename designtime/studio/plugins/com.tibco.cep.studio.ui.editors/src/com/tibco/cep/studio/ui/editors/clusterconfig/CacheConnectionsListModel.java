package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.ListProviderModel;
import com.tibco.cep.studio.ui.util.ListProviderUi;

/*
@author ssailapp
@date Dec 4, 2009 4:12:35 PM
 */

public class CacheConnectionsListModel implements ListProviderModel {

	private ClusterConfigModelMgr modelmgr;

	public CacheConnectionsListModel(ClusterConfigModelMgr modelmgr) {
		this.modelmgr = modelmgr;
	}

	@Override
	public void addItem(List list) {
		list.add("new Connection");
	}

	@Override
	public Composite createConfigPanel(ListProviderUi listProvider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteItem(List list, String item) {
		list.remove(item);
	}

	@Override
	public void onListItemSelectionChanged(String item) {
		// TODO Auto-generated method stub
		
	}

}
