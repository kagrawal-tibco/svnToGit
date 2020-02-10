package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Apr 28, 2010 5:39:38 PM
 */

public class NodeMMAgentPage extends NodeAgentAbstractPage {

	private Text tInfAgent, tQueryAgent;
	private Button bInfAgentBrowse, bQueryAgentBrowse;
	private MMAgent mmAgent;
	
	public NodeMMAgentPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		PanelUiUtil.createLabel(client, "Inference Agent:  ");
		Composite infComp = new Composite(client, SWT.NONE);
		infComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		infComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tInfAgent = PanelUiUtil.createText(infComp);
		tInfAgent.addListener(SWT.Modify, getAgentRefModifyListener(tInfAgent, true));
		tInfAgent.setEnabled(false);
		bInfAgentBrowse = PanelUiUtil.createBrowsePushButton(infComp, tInfAgent);
		bInfAgentBrowse.addListener(SWT.Selection, getAgentSelectionListener(infComp.getShell(), true));
		
		PanelUiUtil.createLabel(client, "Query Agent:  ");
		Composite queryComp = new Composite(client, SWT.NONE);
		queryComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		queryComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tQueryAgent = PanelUiUtil.createText(queryComp);
		tQueryAgent.addListener(SWT.Modify, getAgentRefModifyListener(tQueryAgent, false));
		tQueryAgent.setEnabled(false);
		bQueryAgentBrowse = PanelUiUtil.createBrowsePushButton(queryComp, tQueryAgent);
		bQueryAgentBrowse.addListener(SWT.Selection, getAgentSelectionListener(queryComp.getShell(), false));
		
		createPropertiesGroup();
	}

	private Listener getAgentRefModifyListener(final Text tField, final boolean isInf) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateAgentInfQueryRef(mmAgent, tField.getText(), isInf);
			}
		};
		return listener;
	}

	private Listener getAgentSelectionListener(final Shell shell, final boolean isInf) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				AgentSelectionDialog dialog = new AgentSelectionDialog(shell, modelmgr);
				AgentClass curAgent = modelmgr.getAgentClass(isInf? tInfAgent.getText(): tQueryAgent.getText());
				ArrayList<AgentClass> curAgentsList = new ArrayList<AgentClass>();
				if (curAgent != null)
					curAgentsList.add(curAgent);
				String type = isInf? AgentClass.AGENT_TYPE_INFERENCE: AgentClass.AGENT_TYPE_QUERY;
				dialog.open(modelmgr.getAgentClasses(type), curAgentsList);
				ArrayList<AgentClass> selAgentClass = dialog.getSelectedAgentClass();
				if (selAgentClass.size() > 0) {
					if (isInf) {
						tInfAgent.setText(((AgentClass)selAgentClass.get(0)).name);
					} else {
						tQueryAgent.setText(((AgentClass)selAgentClass.get(0)).name);
					}
				}
			}
		};
		return listener;
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		mmAgent = (MMAgent) agent;
		if (mmAgent != null) {
			tInfAgent.setText(mmAgent.infAgentRef);
			tQueryAgent.setText(mmAgent.queryAgentRef);
		}
	}
}