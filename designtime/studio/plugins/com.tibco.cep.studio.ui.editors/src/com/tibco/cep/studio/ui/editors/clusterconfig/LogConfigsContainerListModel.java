package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.ListProviderModel;
import com.tibco.cep.studio.ui.util.ListProviderUi;

/*
@author ssailapp
@date Mar 30, 2010 2:14:25 PM
 */

public class LogConfigsContainerListModel implements ListProviderModel {

	private ClusterConfigModelMgr modelmgr;
	private TreeViewer viewer;
	
	public LogConfigsContainerListModel(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		this.modelmgr = modelmgr;
		this.viewer = viewer;
	}
	
	public void addItem(List list) {
	}

	@Override
	public Composite createConfigPanel(ListProviderUi listProvider) {
		return null;
	}

	@Override
	public void deleteItem(List list, String name) {
	}

	@Override
	public void onListItemSelectionChanged(String item) {
	}
}


