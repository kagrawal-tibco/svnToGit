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

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcAgent.AgentRulesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.ListProviderUi;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 14, 2009 6:21:36 PM
 */

public class NodeAgentRulesGrpListPage extends NodeGroupsListPage {

	private AgentRulesGrp agentRulesGrp;
	private ListProviderUi rulesGrpList;
	private AgentRulesGrpListModel listmodel;
	
	public NodeAgentRulesGrpListPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	protected void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(1, false));

		PanelUiUtil.createLabel(comp, "Rule Collections: ");
		
		listmodel = new AgentRulesGrpListModel(modelmgr, viewer);
		rulesGrpList = new ListProviderUi(comp, null, listmodel); 
		Composite listComp = rulesGrpList.createListPanel(true, false);
		
		comp.pack();
		parent.pack();
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			agentRulesGrp = ((AgentRulesGrp) ssel.getFirstElement());
		else
			agentRulesGrp = null;
		update();
	}
	
	protected void update() {
		String rulesGrp[] = modelmgr.getAgentRulesGrpNames(agentRulesGrp, false).toArray(new String[0]);
		rulesGrpList.setItems(rulesGrp);
		listmodel.setParentAgent(agentRulesGrp.parentAgent);
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
