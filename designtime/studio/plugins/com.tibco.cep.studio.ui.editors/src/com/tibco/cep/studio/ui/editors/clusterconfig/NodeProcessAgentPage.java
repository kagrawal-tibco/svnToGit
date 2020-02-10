package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Apr 27, 2011
 */

public class NodeProcessAgentPage extends NodeAgentAbstractPage {

	private ProcessAgent processAgent;
	private Group gJobManager;
	private Text tMaxActive, tJobQueueSize, tThreadPoolSize;
	
	public NodeProcessAgentPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		PanelUiUtil.createLabel(client, "Max Active: ");
		tMaxActive = PanelUiUtil.createText(client);
		tMaxActive.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateAgentMaxActive(processAgent, tMaxActive.getText());
			}
		});	

		gJobManager = new Group(client, SWT.NONE);
		gJobManager.setText("Job Manager");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gJobManager.setLayoutData(gd);
		gJobManager.setLayout(new GridLayout(2, false));
		
		PanelUiUtil.createLabel(gJobManager, "Job Pool Queue Size: ");		
		tJobQueueSize = PanelUiUtil.createText(gJobManager);
		tJobQueueSize.addListener(SWT.Modify, new Listener() {
				@Override
			public void handleEvent(Event event) {
				modelmgr.updateAgentJobQueueSize(processAgent, tJobQueueSize.getText());
			}
		});
		
		PanelUiUtil.createLabel(gJobManager, "Job Pool Thread Count: ");
		tThreadPoolSize = PanelUiUtil.createText(gJobManager);
		tThreadPoolSize.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateAgentThreadPoolSize(processAgent, tThreadPoolSize.getText());
			}
		});
		gJobManager.pack();

		createPropertiesGroup();
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		processAgent = (ProcessAgent) agent;
		if (processAgent != null) {
			tMaxActive.setText(processAgent.maxActive);
			tJobQueueSize.setText(processAgent.jobPoolQueueSize);
			tThreadPoolSize.setText(processAgent.jobPoolThreadCount);
		}
	}
}