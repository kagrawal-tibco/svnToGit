package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.ListProviderModel;
import com.tibco.cep.studio.ui.util.ListProviderUi;

/*
@author ssailapp
@date Dec 16, 2009 4:03:20 PM
 */

public class GroupsListModel implements ListProviderModel {

	private ClusterConfigModelMgr modelmgr;
	private TreeViewer viewer;
	
	public GroupsListModel(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		this.modelmgr = modelmgr;
		this.viewer = viewer;
	}
	
	@Override
	public void addItem(List list) {
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
