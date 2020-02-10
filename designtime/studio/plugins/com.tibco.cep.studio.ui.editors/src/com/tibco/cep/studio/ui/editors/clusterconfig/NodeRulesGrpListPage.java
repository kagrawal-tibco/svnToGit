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
@date Dec 21, 2009 1:13:40 AM
 */

public class NodeRulesGrpListPage extends NodeGroupsListPage {

	private ListProviderUi rulesGrpContainerListUi;
	private RulesGrpContainerListModel listmodel;
	
	public NodeRulesGrpListPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	protected void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(1, false));
		
		PanelUiUtil.createLabel(comp, "Rule Collections: ");
	
		listmodel = new RulesGrpContainerListModel(modelmgr, viewer);
		rulesGrpContainerListUi = new ListProviderUi(comp, null, listmodel); 
		Composite listComp = rulesGrpContainerListUi.createListPanel(true, false);
		
		comp.pack();
		parent.pack();
	}

	protected void update() {
		String ruleGrpNames[] = modelmgr.getRulesGroupNames();
		rulesGrpContainerListUi.setItems(ruleGrpNames);
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
