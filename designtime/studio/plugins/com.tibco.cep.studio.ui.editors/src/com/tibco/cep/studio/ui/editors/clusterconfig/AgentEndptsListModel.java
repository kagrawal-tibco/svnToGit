package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.ListProviderModel;
import com.tibco.cep.studio.ui.util.ListProviderUi;

/*
@author ssailapp
@date Dec 3, 2009 6:12:19 PM
 */

public class AgentEndptsListModel implements ListProviderModel {

		
	private ClusterConfigModelMgr modelmgr;

	public AgentEndptsListModel(ClusterConfigModelMgr modelmgr) {
		this.modelmgr = modelmgr;
	}

	@Override
	public void addItem(List list) {
		list.add("NewEp");
	}

	@Override
	public Composite createConfigPanel(ListProviderUi listProvider) {
		return null;
	}

	@Override
	public void deleteItem(List list, String item) {
		list.remove(item);
	}

	@Override
	public void onListItemSelectionChanged(String item) {
	}

}
