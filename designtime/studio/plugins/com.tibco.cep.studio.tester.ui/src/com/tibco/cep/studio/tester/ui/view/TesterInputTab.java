package com.tibco.cep.studio.tester.ui.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
import com.tibco.cep.studio.debug.ui.views.NullDebugTarget;
import com.tibco.cep.studio.tester.ui.utils.Messages;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.cep.studio.tester.core.utils.TestDataAsserter;;

public class TesterInputTab extends AbstractTesterInputTab implements DisposeListener {
	
	private Set<String> emptyFiles = new HashSet<String>();
	private Map<String, String> entityURIMap = new HashMap<String, String>();
	private Map<String, TableItem> itemMap = new HashMap<String, TableItem>();

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.ui.views.InputTab#handleDebugTargetCreate(com.tibco.cep.studio.debug.core.model.IRuleRunTarget)
	 */
	@Override
	protected void handleDebugTargetCreate(IRuleRunTarget target) {
		
		project = target.getWorkspaceProject();
		try {
			targetProjectMap.put(target.getName(), project.getName());
			clusterName = target.getClusterName();
			if (clusterName != null) {
				targetClusterMap.put(target.getName(), clusterName);
			}

			targetRuleSessionsMap.put(target.getName(), target.getRuleSessionMap());

			targetAgentMap.put(target.getName(), target.isProcessAgent());
			//TODO: make other changes based on fProcessAgent flag

		} catch (DebugException e) {
			e.printStackTrace();
		}

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				toolBar.setEnabled(true);
				launchTargetName.setText("");
				ruleSessionName.setText("");
				populateTestData();
				select.setEnabled(true);
				refresh();
			}
		});
	}
	
	@Override
	public void dispose() {
		// Cleanup any debug listeners or other stuff
		// the view is getting disposed
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.ui.views.InputTab#handleDebugTargetRemove(com.tibco.cep.studio.debug.core.model.IRuleRunTarget)
	 */
	@Override
	protected void handleDebugTargetRemove(IRuleRunTarget target) {
		if(getDebugTarget() == target) {
			try {
				if (targetProjectMap.containsKey(target.getName())) {
					targetProjectMap.remove(target.getName());
				}
				if (targetClusterMap.containsKey(target.getName())) {
					targetClusterMap.remove(target.getName());
				}
				if (targetRuleSessionsMap.containsKey(target.getName())) {
					targetRuleSessionsMap.remove(target.getName());
				}
				if (targetAgentMap.containsKey(target.getName())) {
					targetAgentMap.remove(target.getName());
				}
			} catch (DebugException e) {
				e.printStackTrace();
			}
			setDebugTarget(null);
		}
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if(!launchTargetName.isDisposed()) {
					launchTargetName.setText("");
				}
				if(!ruleSessionName.isDisposed()) {
					ruleSessionName.setText("");	
				}
				
				for (TableItem item : checkedTestaData) {
					item.setChecked(false);
				}
				checkedTestaData.clear();
				select.setSelection(false);
				select.setEnabled(false);
				
				toolBar.setEnabled(false);
				testDataSelectTable.removeAll();
				for(Object button : itemButtonEditorMap.keySet()){
					itemButtonEditorMap.get(button).setVisible(false);
				}
				

				
			}
		});
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		Object source = e.getSource();
		if (source == launchTargetClearBtn) {
			setDebugTarget(NullDebugTarget.getInstance());
			launchTargetName.setText("");
			launchTargetName.setData(null);
			ruleSessionName.setText("");
		} else if( source == ruleSessionClearBtn) {
			ruleSessionName.setText("");
		} else if (source == launchTargetBrowseBtn ) {
			Object input = getInputView().getRuleSessionMap().keySet();
			TargetLabelProvider labelProvider = new TargetLabelProvider();
			ISelection selected = getPopupListSelection(launchTargetName,input,labelProvider);
			if(selected instanceof IStructuredSelection) {
				Object sel = ((IStructuredSelection)selected).getFirstElement();
				if(sel instanceof IRuleRunTarget) {
					setDebugTarget((IRuleRunTarget) sel);
					if(getDebugTarget() != null) {
						launchTargetName.setText(labelProvider.getText(getDebugTarget()));
						launchTargetName.setData(getDebugTarget());
						String launchTargetText = launchTargetName.getText();
						if (!testDataSelectTable.getData(CURRENT_SELECT_TABLE_PROJECT).toString().equals(targetProjectMap.get(launchTargetText))) {
							project = ResourcesPlugin.getWorkspace().getRoot().getProject(targetProjectMap.get(launchTargetText).toString());
							populateTestData();
							if (targetClusterMap.containsKey(launchTargetText)) {
								clusterName = targetClusterMap.get(targetClusterMap.get(launchTargetText));
							} else {
								clusterName = null;
							}
						}
					}
				}
			}
		}  else if ( source == ruleSessionBrowseBtn ) {
			Set<String> input = getInputView().getRuleSessions(getDebugTarget()).keySet();
			if (input.contains(PROCESS_AGENT_RULE_SESSION)) {
				input.remove(PROCESS_AGENT_RULE_SESSION);
			}
			ISelection selection = getPopupListSelection(ruleSessionName, input, null);
			if (selection instanceof IStructuredSelection) {
				String element = (String) ((IStructuredSelection)selection).getFirstElement();
				if (element != null) {
					ruleSessionName.setText(element);
				}
			}
		} else if (e.getSource() == select) {
			for (TableItem item : testDataSelectTable.getItems()) {
				item.setChecked(select.getSelection());
				if(select.getSelection()){
					checkedTestaData.add(item);
				} else if (!(checkedTestaData.add(item))){
					checkedTestaData.remove(item);
				}
			}
		} else if (e.item instanceof TableItem) {
			TableItem item = (TableItem)e.item;
			if (item.getChecked()) {
				if (!contains(checkedTestaData, item)) {
					checkedTestaData.add(item);
				}
			} else {
				if (contains(checkedTestaData, item)) {
					checkedTestaData.remove(item);
				}
			}
		} else if (source == assertButton) {
			checkedTestaData.clear();
			checkedTestaDataFiles.clear();
			entityURIMap.clear();
			itemMap.clear();
			for (TableItem item : testDataSelectTable.getItems()) {
				if(item.getChecked()) {
					checkedTestaData.add(item);
				} 
			}
			for (TableItem item : checkedTestaData) {
				item.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
				String filePath = item.getData().toString();
				entityURIMap.put(filePath, item.getText(0));
				itemMap.put(filePath, item);
				String destURI = item.getText(1);
				if (!checkedTestaDataFiles.containsKey(filePath)) {
					checkedTestaDataFiles.put(filePath, destURI);
				}
			}
			
			if (checkedTestaDataFiles.size() == 0) {
				MessageDialog.openInformation(getShell(), Messages.getString("testdata.input.start.test"), 
						Messages.getString("testdata.input.empty"));
				return;
			}
			emptyFiles.clear();
			for (String f : checkedTestaDataFiles.keySet()) {
				getEmptyTestDataFilesToAssert(f);
			}
			
			if (emptyFiles.size() > 0) {
				String emp = "";
				for (String s: emptyFiles) {
					itemMap.get(s).setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					emp = emp + "\n" + "\"" + entityURIMap.get(s) + "\"";
				}
				boolean withEmptyDataContinue = MessageDialog.openQuestion(getShell(), 
						Messages.getString("testdata.input.start.test"), Messages.getString("testdata.input.empty.entries", emp));
				if (!withEmptyDataContinue) {
					return;
				}
			}
			
			String session = "default";
			//Running Assert Test Job
			if (getDebugTarget() != null) {
				AssertTestDataJob assertJob = new AssertTestDataJob(getDebugTarget(), ruleSessionName.getText(), session);
				assertJob.setPriority(Job.LONG);
				assertJob.setUser(true);
				assertJob.schedule();
			}
		} 
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	@Override
	public void modifyText(ModifyEvent e) {
//		setErrorMessage(null);
		setValid(true);
		Object source = e.getSource();
		if( source == launchTargetName ) {
			if(launchTargetName.getText().isEmpty()) {
				setDebugTarget(null);
				//TODO
			}				
			
		} else if (source == ruleSessionName) {
			String target = launchTargetName.getText();
			Map<String, String> ruleSessionMap = null;
			if (targetRuleSessionsMap.containsKey(target)) {
				ruleSessionMap = targetRuleSessionsMap.get(target);
			}
			String rsName = ruleSessionName.getText();
			if (rsName.isEmpty()) {
//				setErrorMessage("RuleSession name cannot be null");
				setValid(false);
			} 
//			else if (targetAgentMap.containsKey(launchTargetName.getText()) && targetAgentMap.get(launchTargetName.getText())) {
				else if(ruleSessionMap != null && 
						ruleSessionMap.containsKey(rsName) && 
						ruleSessionMap.containsValue("com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession")) {
				MessageDialog.openInformation(getShell(), "Tester Input", "Process Agent is running. " +
						"There has been no test data assert.");
				setValid(false);
			} else {
				Map<String,String> sessions = getInputView().getRuleSessions(getDebugTarget());
				if(!sessions.containsKey(ruleSessionName.getText())) {
//					setErrorMessage("Invalid RuleSession name");
					setValid(false);
				} 
			}
			
		}
		if(!isValid()) {
			if(isActive() && !launchTargetName.getText().isEmpty()) {
//				showErrorMessage(null);
//				showErrorMessage();
			} else {
//				showErrorMessage(null);
			}
		} else {
//			showErrorMessage(null);
		}
		refresh();
		
	}
	
	private class AssertTestDataJob extends Job {

		private String testerSession;
		
		private String ruleSessionName;
				
		private IRuleRunTarget runTarget;
		
		/**
		 * @param runTarget
		 * @param session
		 */
		public AssertTestDataJob(IRuleRunTarget runTarget, String ruleSessionName, String testerSession) {
			super("Assert Test Data");
			this.testerSession = testerSession;
			this.ruleSessionName = ruleSessionName;
			this.runTarget = runTarget;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
		 */
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			return runAsserter(monitor, runTarget, ruleSessionName, testerSession);
		}
	}

	/**
	 * @param monitor
	 * @param session
	 * @return
	 */
	private IStatus runAsserter(IProgressMonitor monitor, IRuleRunTarget runTarget, String ruleSessionName, String testerSession) {
		try {
			monitor.beginTask("Assert Test Data started", 100);
			String inputDir = StudioUIPlugin.getDefault().getPreferenceStore().getString(StudioUIPreferenceConstants.TEST_DATA_INPUT_PATH);
			TestDataAsserter asserter = new TestDataAsserter(runTarget, testerSession, ruleSessionName, checkedTestaDataFiles, getClusterName(), getEntityURIMap(), inputDir);
			SubProgressMonitor converterSubProgressMonitor = new SubProgressMonitor(monitor, 20);
			converterSubProgressMonitor.setTaskName("Assert Entities");
			asserter.deployTestDataFiles(converterSubProgressMonitor);
			SubProgressMonitor assertSubProgressMonitor = new SubProgressMonitor(monitor, 80);
			asserter.doAssert(assertSubProgressMonitor, runTarget);
			monitor.done();
			return Status.OK_STATUS;
		} catch (Exception e1) {
			monitor.done();
			StudioDebugUIPlugin.log(e1);
			return new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID, "Assert Test Data failed",e1);
		} finally {
			monitor.done();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getEmptyTestDataFilesToAssert(String file) {
		File newFile = new File(file);
		if (newFile.exists() && newFile.length() > 0) {
			try {
				FileInputStream fis = new FileInputStream(newFile);
				XiNode rootNode = XiParserFactory.newInstance().parse(new InputSource(fis));
				XiNode firstChild = rootNode.getFirstChild();
				Iterator<XiNode> nodeIterator = firstChild.getChildren();
				boolean isSelect = false;
				while (nodeIterator.hasNext()) {
					XiNode node = nodeIterator.next();
					XiNode sAttribElement = node.getAttribute(ExpandedName.makeName("isSelected"));
					if (sAttribElement != null) {
						String select = node.getAttributeStringValue(ExpandedName.makeName("isSelected"));
						if (Boolean.parseBoolean(select)) {
							isSelect = true;
							break;
						}
					}
				}
				if (!isSelect) {
					emptyFiles.add(file);
				}
			} catch (IOException e) {
				StudioDebugUIPlugin.log(e);
			}
			catch (SAXException e) {
				StudioDebugUIPlugin.log(e);
			}
		}
	}
	

	@Override
	public Image getImage() {
		return StudioDebugUIPlugin.getImage("icons/genTestData16x16.png");
	}
	
	public Map<String, String> getEntityURIMap() {
		return entityURIMap;
	}
	
	@Override
	public void widgetDisposed(DisposeEvent e) {
		select.dispose();
		
		
	}

	@Override
	public void refreshControls() {
		// TODO Auto-generated method stub
		
	}
}