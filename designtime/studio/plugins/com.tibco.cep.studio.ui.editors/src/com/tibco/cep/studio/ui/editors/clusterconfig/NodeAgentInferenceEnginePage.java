package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent.InferenceEngine;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Apr 28, 2011
 */

public class NodeAgentInferenceEnginePage extends NodeAgentAbstractPage {

	private Group gLocalCache, gSharedQueue;
	private Text tMaxSize, tEvictionTime, tSharedQueueSize, tSharedQueueWorkers;
	private Button bConcRtc, bCheckForDuplicates;
	private InferenceEngine infEngine;
	
	public NodeAgentInferenceEnginePage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
		displayNameField = false;
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
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
				modelmgr.updateAgentLocalCacheSize(infEngine, tMaxSize.getText());
			}
		});
		
		PanelUiUtil.createLabel(gLocalCache, "Eviction Time: ");
		tEvictionTime = PanelUiUtil.createText(gLocalCache);
		tEvictionTime.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateAgentLocalCacheTime(infEngine, tEvictionTime.getText());
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
				modelmgr.updateAgentSharedQueueSize(infEngine, tSharedQueueSize.getText());
			}
		});
		
		PanelUiUtil.createLabel(gSharedQueue, "Thread Count: ");
		tSharedQueueWorkers = PanelUiUtil.createText(gSharedQueue);
		tSharedQueueWorkers.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateAgentSharedQueueWorkers(infEngine, tSharedQueueWorkers.getText());
			}
		});
		
		Composite infComp = new Composite(client, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		infComp.setLayoutData(gd);
		infComp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
		
		PanelUiUtil.createLabel(infComp, "Concurrent RTC: ");
		bConcRtc = PanelUiUtil.createCheckBox(infComp, "");
		bConcRtc.setLayoutData(new GridData());
		bConcRtc.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				modelmgr.updateAgentConcRtc(infEngine, bConcRtc.getSelection());
			}
		});

		PanelUiUtil.createLabel(infComp, "Check for Duplicates: ");
		bCheckForDuplicates = PanelUiUtil.createCheckBox(infComp, "");
		bCheckForDuplicates.setLayoutData(new GridData());
		bCheckForDuplicates.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				modelmgr.updateAgentCheckDuplicates(infEngine, bCheckForDuplicates.getSelection());
			}
		});
		
		infComp.pack();
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			infEngine = ((InferenceEngine) ssel.getFirstElement());
		else
			infEngine = null;
		if (infEngine != null) {
			tMaxSize.setText(infEngine.localCacheMaxSize);
			tEvictionTime.setText(infEngine.localCacheEvictionTime);
			tSharedQueueSize.setText(infEngine.sharedQueueSize);
			tSharedQueueWorkers.setText(infEngine.sharedQueueWorkers);
			bConcRtc.setSelection(infEngine.concRtc);
			bCheckForDuplicates.setSelection(infEngine.checkForDuplicates);
		}
	}
}