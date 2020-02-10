package com.tibco.cep.studio.ui.statemachine.diagram.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditorInput;

public class SMOpenAction implements IWorkbenchWindowActionDelegate {

	private ISelection fSelection;

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
		StateMachine stateMachine = getStateMachine();
		if (stateMachine == null) {
			return;
		}
		
		IWorkbenchPage page = PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow().getActivePage();
		
		StructuredSelection selection = (StructuredSelection) fSelection;
		Object obj = selection.getFirstElement();
		if(obj instanceof IFile){
			IFile file = (IFile)selection.getFirstElement();
			if(file.getFileExtension().equals("statemachine")){
				StateMachineEditorInput input =
					new StateMachineEditorInput(file, stateMachine);
					try {
						page.openEditor(input, StateMachineEditor.ID);
					} catch (PartInitException e) {
						//System.out.println(e.getMessage());
					}		
			}else{
				System.err.println("Not a state machine resource!");
				return;
			}
		}else{
			System.err.println("Not a file resource!");
			return;
		}
	}

	private StateMachine getStateMachine() {
		if (fSelection == null || !(fSelection instanceof StructuredSelection)) {
			return null;
		}
		StructuredSelection selection = (StructuredSelection) fSelection;
		Object obj = selection.getFirstElement();
		if (!(obj instanceof IFile)) {
			return null;
		}
		EObject entityObj = IndexUtils.loadEObject(ResourceHelper.getLocationURI((IFile)obj));
		if (!(entityObj instanceof StateMachine)) {
			return null;
		}
		StateMachine stateMachine = (StateMachine) entityObj;
		return stateMachine;
	}

	public void init(IWorkbenchWindow window) {

	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.fSelection = selection;
	}
}
