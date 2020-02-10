package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.ListProviderModel;
import com.tibco.cep.studio.ui.util.ListProviderUi;

/*
@author ssailapp
@date Apr 28, 2011
 */

public class ProcessesGrpContainerListModel implements ListProviderModel {

	private ClusterConfigModelMgr modelmgr;
	private TreeViewer viewer;
	
	public ProcessesGrpContainerListModel(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
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
		BlockUtil.refreshViewer(viewer);
	}

	@Override
	public void onListItemSelectionChanged(String item) {
	}
}