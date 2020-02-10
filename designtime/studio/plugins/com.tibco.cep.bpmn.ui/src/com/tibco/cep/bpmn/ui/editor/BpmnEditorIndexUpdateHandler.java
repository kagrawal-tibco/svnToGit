package com.tibco.cep.bpmn.ui.editor;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.IBpmnUiIndexUpdateHandler;

public class BpmnEditorIndexUpdateHandler implements IBpmnUiIndexUpdateHandler {

	public BpmnEditorIndexUpdateHandler() {
	}

	@Override
	public void onIndexUpdate(final IProject project,final EObject index) throws Exception {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
		
			@Override
			public void run() {
				try {
					IWorkbenchWindow awbWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					
					if(awbWindow != null) {
						IWorkbenchPage ap = awbWindow.getActivePage();	
						if(ap != null) {
							IEditorReference[] editorRefs = ap.getEditorReferences();
							for(IEditorReference eref:editorRefs) {
								IEditorPart editor = eref.getEditor(false);
								if(editor != null && editor instanceof BpmnEditor) {
									BpmnEditor bpmnEditor = (BpmnEditor) editor;
									bpmnEditor.bpmnGraphDiagramManager.waitForInitComplete();
									IProject editorProject = (IProject) bpmnEditor.getAdapter(IProject.class);
									if(editorProject == project) {
										bpmnEditor.onIndexUpdate(editorProject, index);
									}
								}
							}
						}
					}		
				} catch (Exception e) {
					BpmnUIPlugin.log(e);
				}
				
			}
		});

	}

}
