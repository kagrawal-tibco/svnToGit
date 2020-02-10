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
@date Apr 28, 2011
 */

public class NodeProcessesGrpListPage extends NodeGroupsListPage {

	private ListProviderUi processsGrpContainerListUi;
	private ProcessesGrpContainerListModel listmodel;
	
	public NodeProcessesGrpListPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	protected void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(1, false));
		
		PanelUiUtil.createLabel(comp, "Process Definitions: ");
	
		listmodel = new ProcessesGrpContainerListModel(modelmgr, viewer);
		processsGrpContainerListUi = new ListProviderUi(comp, null, listmodel); 
		Composite listComp = processsGrpContainerListUi.createListPanel(true, false);
		
		comp.pack();
		parent.pack();
	}

	protected void update() {
		String processGrpNames[] = modelmgr.getProcessesGroupNames();
		processsGrpContainerListUi.setItems(processGrpNames);
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