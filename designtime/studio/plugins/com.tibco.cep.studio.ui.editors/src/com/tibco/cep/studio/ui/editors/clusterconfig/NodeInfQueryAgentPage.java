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

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Feb 1, 2010 1:43:17 PM
 */

public class NodeInfQueryAgentPage extends NodeAgentAbstractPage {

	private Group gLocalCache, gSharedQueue;
	private Text tMaxSize, tEvictionTime, tMaxActive, tSharedQueueSize, tSharedQueueWorkers;
	protected DashInfProcQueryAgent infQueryAgent;
	
	public NodeInfQueryAgentPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
 	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		section.setText("Configuration");

		//PanelUiUtil.createSpacer(toolkit, client, 2);
		
		gLocalCache = new Group(client, SWT.NONE);
		gLocalCache.setText("Local Cache");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gLocalCache.setLayoutData(gd);
		gLocalCache.setLayout(new GridLayout(2, false));
		
		PanelUiUtil.createLabel(gLocalCache, "Max Size: ");		
		tMaxSize = PanelUiUtil.createText(gLocalCache);
		tMaxSize.addListener(SWT.Modify, new Listener() {
				@Override
			public void handleEvent(Event event) {
				modelmgr.updateAgentLocalCacheSize(infQueryAgent, tMaxSize.getText());
			}
		});
		
		PanelUiUtil.createLabel(gLocalCache, "Eviction Time: ");
		tEvictionTime = PanelUiUtil.createText(gLocalCache);
		tEvictionTime.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateAgentLocalCacheTime(infQueryAgent, tEvictionTime.getText());
			}
		});
		
		gSharedQueue = new Group(client, SWT.NONE);
		gSharedQueue.setText("Shared Queue");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gSharedQueue.setLayoutData(gd);
		gSharedQueue.setLayout(new GridLayout(2, false));
		
		PanelUiUtil.createLabel(gSharedQueue, "Queue Size: ");		
		tSharedQueueSize = PanelUiUtil.createText(gSharedQueue);
		tSharedQueueSize.addListener(SWT.Modify, new Listener() {
				@Override
			public void handleEvent(Event event) {
				modelmgr.updateAgentSharedQueueSize(infQueryAgent, tSharedQueueSize.getText());
			}
		});
		
		PanelUiUtil.createLabel(gSharedQueue, "Thread Count: ");
		tSharedQueueWorkers = PanelUiUtil.createText(gSharedQueue);
		tSharedQueueWorkers.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateAgentSharedQueueWorkers(infQueryAgent, tSharedQueueWorkers.getText());
			}
		});
		
		PanelUiUtil.createLabel(client, "Max Active: ");
		tMaxActive = PanelUiUtil.createText(client);
		tMaxActive.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateAgentMaxActive(infQueryAgent, tMaxActive.getText());
			}
		});		
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		infQueryAgent = (DashInfProcQueryAgent) agent;
		if (agent != null) {
			tMaxSize.setText(infQueryAgent.localCacheMaxSize);
			tEvictionTime.setText(infQueryAgent.localCacheEvictionTime);
			tMaxActive.setText(infQueryAgent.maxActive);
			tSharedQueueSize.setText(infQueryAgent.sharedQueueSize);
			tSharedQueueWorkers.setText(infQueryAgent.sharedQueueWorkers);
		}
	}
}
