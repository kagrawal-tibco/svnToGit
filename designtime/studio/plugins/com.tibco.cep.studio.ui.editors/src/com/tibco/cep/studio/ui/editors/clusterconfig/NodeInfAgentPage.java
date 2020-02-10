package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.InfAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 8, 2009 3:17:29 PM
 */

public class NodeInfAgentPage extends NodeInfQueryAgentPage {

	private Button bConcRtc;
	public  Button bCheckForDuplicates;
	private Text tBwRepoUrl;
	private InfAgent infAgent;
	
	public NodeInfAgentPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
 	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		//PanelUiUtil.createSpacer(toolkit, client, 2);
		
		PanelUiUtil.createLabel(client, "BusinessWorks Repo URL: ");
		tBwRepoUrl = PanelUiUtil.createText(client);
		tBwRepoUrl.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateAgentBwRepoUrl(infAgent, tBwRepoUrl.getText());
			}
		});
		
		PanelUiUtil.createLabel(client, "Concurrent RTC: ");
		bConcRtc = PanelUiUtil.createCheckBox(client, "");
		bConcRtc.setLayoutData(new GridData());
		bConcRtc.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				modelmgr.updateAgentConcRtc(infAgent, bConcRtc.getSelection());
			}
		});

		PanelUiUtil.createLabel(client, "Check for Duplicates: ");
		bCheckForDuplicates = PanelUiUtil.createCheckBox(client, "");
		bCheckForDuplicates.setLayoutData(new GridData());
		String persistenceMode=modelmgr.getModel().om.cacheOm.bs.values.get(modelmgr.getModel().om.cacheOm.bs.PERSISTENCE_OPTION);
		if("Shared Nothing".equalsIgnoreCase(persistenceMode)){
			bCheckForDuplicates.setEnabled(false);
		}
		else{
			bCheckForDuplicates.setEnabled(true);
		}
		bCheckForDuplicates.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				modelmgr.updateAgentCheckDuplicates(infAgent, bCheckForDuplicates.getSelection());
			}
		});
		
		/*
		Composite infComp = new Composite(client, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		infComp.setLayoutData(gd);
		infComp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
		infComp.pack();
		*/
		
		createPropertiesGroup();
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		infAgent = (InfAgent) infQueryAgent;

		if (agent != null) {
			
			String persistenceMode=modelmgr.getModel().om.cacheOm.bs.values.get(modelmgr.getModel().om.cacheOm.bs.PERSISTENCE_OPTION);
			if("Shared Nothing".equalsIgnoreCase(persistenceMode)){
				bCheckForDuplicates.setEnabled(false);
				bCheckForDuplicates.setSelection(false);
			}
			else{
				bCheckForDuplicates.setEnabled(true);
				bCheckForDuplicates.setSelection(infAgent.checkForDuplicates);
			}
			
			tBwRepoUrl.setText(infAgent.bwRepoUrl);
			bConcRtc.setSelection(infAgent.concRtc);
		}
	}
}

