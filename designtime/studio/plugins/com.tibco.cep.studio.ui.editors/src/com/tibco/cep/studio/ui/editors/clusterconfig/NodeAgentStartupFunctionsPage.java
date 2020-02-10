package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentShutdownFunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentStartupFunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.ListProviderUi;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 21, 2009 5:11:59 PM
 */

public class NodeAgentStartupFunctionsPage extends NodeGroupsListPage {

	private AgentStartupFunctionsGrp agentStartupFnsGrp;
	private ListProviderUi startupFnsGrpList;
	private AgentFunctionsGrpListModel listmodel;
	private Label fnLabel;
	
	public NodeAgentStartupFunctionsPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	protected void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(1, false));

		fnLabel = PanelUiUtil.createLabel(comp, "Startup Functions: ");
		
		listmodel = new AgentFunctionsGrpListModel(modelmgr, viewer);
		startupFnsGrpList = new ListProviderUi(comp, null, listmodel); 
		Composite listComp = startupFnsGrpList.createListPanel(true, false);
		
		comp.pack();
		parent.pack();
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			agentStartupFnsGrp = ((AgentStartupFunctionsGrp) ssel.getFirstElement());
		else
			agentStartupFnsGrp = null;
		update();
	}
	
	protected void update() {
		
		
		if ((agentStartupFnsGrp instanceof AgentStartupFunctionsGrp) && 
				AgentBlock.isProcessAgent(((AgentStartupFunctionsGrp)agentStartupFnsGrp).parentAgent)) {
			fnLabel.setText("Startup Processes: ");
		}
		else {
			fnLabel.setText("Startup Functions: ");
		}
		
		String functionsGrp[] = modelmgr.getAgentFunctionsGrpNames(agentStartupFnsGrp).toArray(new String[0]);
		startupFnsGrpList.setItems(functionsGrp);
		listmodel.setParentAgent(agentStartupFnsGrp.parentAgent);
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
