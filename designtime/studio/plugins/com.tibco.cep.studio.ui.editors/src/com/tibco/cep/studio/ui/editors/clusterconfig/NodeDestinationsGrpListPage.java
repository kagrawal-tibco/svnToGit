package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.ListProviderUi;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 21, 2009 1:18:39 AM
 */

public class NodeDestinationsGrpListPage extends NodeGroupsListPage {

	private ListProviderUi destinationsGrpContainerListUi;
	private DestinationsGrpContainerListModel listmodel;
	
	public NodeDestinationsGrpListPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	protected void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(1, false));
		
		PanelUiUtil.createLabel(comp, "Input Destination Collections: ");
		
		listmodel = new DestinationsGrpContainerListModel(modelmgr, viewer);
		destinationsGrpContainerListUi = new ListProviderUi(comp, null, listmodel); 
		Composite listComp = destinationsGrpContainerListUi.createListPanel(true, false);
		
		comp.pack();
		parent.pack();
	}
	
	protected void update() {
		String listNames[] = modelmgr.getDestinationsGroupNames();
		destinationsGrpContainerListUi.setItems(listNames);
	}

	@Override
	public Listener getListener(Control field, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}
