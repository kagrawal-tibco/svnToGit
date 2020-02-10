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

public class NodeFunctionsGrpListPage extends NodeGroupsListPage {

	private ListProviderUi functionsGrpContainerListUi;
	private FunctionsGrpContainerListModel listmodel;
	
	public NodeFunctionsGrpListPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	protected void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(1, false));
		
		PanelUiUtil.createLabel(comp, "Function Collections: ");
		
		listmodel = new FunctionsGrpContainerListModel(modelmgr, viewer);
		functionsGrpContainerListUi = new ListProviderUi(comp, null, listmodel); 
		Composite listComp = functionsGrpContainerListUi.createListPanel(true, false);
		
		comp.pack();
		parent.pack();
	}
	
	protected void update() {
		String listNames[] = modelmgr.getFunctionsGroupNames();
		functionsGrpContainerListUi.setItems(listNames);
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
