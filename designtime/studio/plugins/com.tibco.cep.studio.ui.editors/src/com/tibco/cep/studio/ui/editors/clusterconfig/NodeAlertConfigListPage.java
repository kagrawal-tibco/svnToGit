package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.AlertConfigList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.ListProviderUi;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Apr 28, 2010 6:45:00 PM
 */

public class NodeAlertConfigListPage extends NodeGroupsListPage {

	private AlertConfigList alertConfigList;
	private ListProviderUi alertConfigListUi;
	private GroupsListModel listmodel;
	
	public NodeAlertConfigListPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	protected void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(1, false));
		
		PanelUiUtil.createLabel(comp, "Alert Configurations: ");
		listmodel = new GroupsListModel(modelmgr, viewer);
		alertConfigListUi = new ListProviderUi(comp, null, listmodel); 
		Composite listComp = alertConfigListUi.createListPanel(true, false);
		
		comp.pack();
		parent.pack();
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1) { 
			Object selObj = ssel.getFirstElement();
			if (selObj instanceof AlertConfigList) {
				alertConfigList = (AlertConfigList) selObj;
			} else {
				alertConfigList = null;
			}
		}
		update();
	}

	protected void update() {
		if (alertConfigList == null)
			return;
		String names[] = modelmgr.getAlertConfigListIds(alertConfigList).toArray(new String[0]);
		alertConfigListUi.setItems(names);
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
