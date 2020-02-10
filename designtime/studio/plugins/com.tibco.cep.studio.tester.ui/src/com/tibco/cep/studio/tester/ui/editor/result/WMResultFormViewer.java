package com.tibco.cep.studio.tester.ui.editor.result;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.tester.core.model.ExecutionObjectType;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.editor.data.TestResultRoot;

/**
 * 
 * @author sasahoo
 *
 */
public class WMResultFormViewer extends AbstractWMResultViewer {
	
	/**
	 * @param resultEditor
	 */
	public WMResultFormViewer(WMResultEditor resultEditor) {
		
		this.wmResultEditor = resultEditor;
		this.ruleSessionName = wmResultEditor.getRuleSessionName();
		this.projectName = wmResultEditor.getProjectName();
		this.testerResultsObject = wmResultEditor.getTesterResultsObject();
		this.runTarget = wmResultEditor.getRunTarget();
		
		contents.clear();
	    
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
        this.projectPath = project.getLocation().toString();
        
    	try {
    		refreshObjects();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		super.createPartControl(container, "Working Memory Contents:" + ruleSessionName, 
				StudioTesterUIPlugin.getDefault().getImage("icons/wm_17x16.png"));
		super.createToolBarActions(); 
		getForm().updateToolBar();
		sashForm.setWeights(new int[] {60,100}); 
	}
	
	/**
	 * @throws Exception
	 */
	public void refreshObjects() throws Exception {
		for (ExecutionObjectType exObj : testerResultsObject.getExecutionObject()) {
			contents.add(exObj);
		}
		testResultRoot = new TestResultRoot(contents);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		managedForm.fireSelectionChanged(spart, event.getSelection());
	}

	
	public void refresh() {
		contents.clear();
		wmTreeViewer.setInput(null);
		if (testerResultsObject != null) {
			for (ExecutionObjectType exObj : testerResultsObject.getExecutionObject()) {
				contents.add(exObj);
			}
		}
		testResultRoot = new TestResultRoot(contents);
		wmTreeViewer.setInput(testResultRoot);
		wmTreeViewer.refresh();
		refreshButton.setEnabled(true);
		clearButton.setEnabled(true);
	}
}