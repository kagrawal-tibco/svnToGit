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
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.ListProviderUi;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Jan 15, 2010 10:08:33 PM
 */

public class NodeAgentShutdownFunctionsGrpPage extends NodeGroupsListPage {

	private AgentShutdownFunctionsGrp agentShutdownFnsGrp;
	private ListProviderUi shutdownFnsGrpList;
	private AgentFunctionsGrpListModel listmodel;
	private Label fnLabel;
	
	public NodeAgentShutdownFunctionsGrpPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	protected void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(1, false));

		fnLabel = PanelUiUtil.createLabel(comp, "Shutdown Functions: ");
		
		listmodel = new AgentFunctionsGrpListModel(modelmgr, viewer);
		shutdownFnsGrpList = new ListProviderUi(comp, null, listmodel); 
		Composite listComp = shutdownFnsGrpList.createListPanel(true, false);
		
		comp.pack();
		parent.pack();
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			agentShutdownFnsGrp = ((AgentShutdownFunctionsGrp) ssel.getFirstElement());
		else
			agentShutdownFnsGrp = null;
		update();
	}
	
	protected void update() {
		
		if ((agentShutdownFnsGrp instanceof AgentShutdownFunctionsGrp)
				&& AgentBlock.isProcessAgent(((AgentShutdownFunctionsGrp)agentShutdownFnsGrp).parentAgent)) {
			fnLabel.setText( "Shutdown Processes: ");
		
		}
		else {
			
			fnLabel.setText( "Shutdown Functions: ");
		}
		
		String functionsGrp[] = modelmgr.getAgentFunctionsGrpNames(agentShutdownFnsGrp).toArray(new String[0]);
		shutdownFnsGrpList.setItems(functionsGrp);
		listmodel.setParentAgent(agentShutdownFnsGrp.parentAgent);
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