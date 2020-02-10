package com.tibco.cep.studio.tester.ui.editor.result;

import static com.tibco.cep.studio.ui.navigator.util.ProjectExplorerUtils.refreshView;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.tester.core.model.ExecutionObjectType;
import com.tibco.cep.studio.tester.core.model.ReteObjectType;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.editor.data.TestResultRoot;

/**
 * 
 * @author sasahoo
 *
 */
public class TestResultFormViewer extends AbstractResultFormViewer {
	
	
	/**
	 * @param resultEditor
	 */
	public TestResultFormViewer(TestResultEditor resultEditor) {
		try {
			init(resultEditor);
		} catch (Exception e) {
			StudioTesterUIPlugin.log(e);
		}
	}
	
	/**
	 * @param resultEditor
	 * @throws Exception
	 */
	private void init(TestResultEditor resultEditor) throws Exception {
		this.editor = resultEditor;
		this.testerResultsObject = resultEditor.getTesterResultsObject();
		this.runName = resultEditor.getTesterRunURI();
		this.ruleSessionName = resultEditor.getRuleSessionName();
		
		if (asserted == null) {
			asserted = new ArrayList<ExecutionObjectType>();
		} 
		if (modified == null) {
			modified = new ArrayList<ExecutionObjectType>();
		} 
		if (retracted == null) {
			retracted = new ArrayList<ExecutionObjectType>(); 
		}
		if (fired == null) {
			fired = new ArrayList<ExecutionObjectType>(); 
		}
	
		asserted.clear();
		modified.clear();
		retracted.clear();
		fired.clear();
		
		projectName = editor.getProjectName();
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
        projectPath = project.getLocation().toString();
		
		refreshObjects();
        
        StudioTesterUIPlugin.debug("Current Project path:{0}", projectPath);
        
        project.refreshLocal(IProject.DEPTH_INFINITE, null);
        refreshView( getEditor().getSite().getPage(), project);
 	}
	
	/**
	 * @throws Exception
	 */
	public void refreshObjects() throws Exception {
		for (ExecutionObjectType exObj : testerResultsObject.getExecutionObject()) {
			ReteObjectType rObj = exObj.getReteObject();
			cacheObject(exObj, rObj.getChangeType());
		}
		aRoot = new TestResultRoot(asserted);
		mRoot = new TestResultRoot(modified);
		rRoot = new TestResultRoot(retracted);
		rulesRoot = new TestResultRoot(fired);
	}
	
	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		super.createPartControl(container, "Test Result: " + editor.getTesterRunURI(), 
				StudioTesterUIPlugin.getDefault().getImage("icons/test.gif"));
		super.createToolBarActions(); 
		sashForm.setWeights(new int[] {45,100}); 
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		managedForm.fireSelectionChanged(spart, event.getSelection());
	}
	
	/**
	 * @param rObj
	 * @param reteChangeType
	 */
	public void cacheObject(ExecutionObjectType exObj, ReteObjectType.ReteChangeType reteChangeType ) {
		switch(reteChangeType) {
		case ASSERT:
			asserted.add(exObj);
			break;
		case MODIFY:
			modified.add(exObj);
			break;
		case RETRACT:
			retracted.add(exObj);
			break;
		case SCHEDULEDTIMEEVENT:
			asserted.add(exObj);
			break;
		case RULEFIRED:
			// this will be handled upon the RULEEXECUTION case
			break;
		case RULEEXECUTION:
			fired.add(exObj);
			break;
		default:
			break;
		}
	}
	
	/**
	 * @param resultEditor
	 */
	public void refresh(final TestResultEditor resultEditor) {
		Display.getDefault().asyncExec(new Runnable(){

			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				try {
					init(resultEditor);
					if (aRoot != null) {
						newTreeViewer.setInput(aRoot);
					}
					if (mRoot != null) {
						modTreeViewer.setInput(mRoot);
					}
					if (rRoot != null) { 
						delTreeViewer.setInput(rRoot);
					}
					if (rulesRoot != null) { 
						rulesTreeViewer.setInput(rulesRoot);
					}
					newTreeViewer.refresh();
					modTreeViewer.refresh();
					delTreeViewer.refresh();
					rulesTreeViewer.refresh();
				} catch (Exception e) {
					StudioTesterUIPlugin.debug(this.getClass().getName(), "Test Result Error", e);
				}
			}
		});
	}
}