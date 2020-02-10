package com.tibco.cep.studio.ui.diagrams.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;


public class NewEventDiagramAction implements IWorkbenchWindowActionDelegate {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
//		ConceptDiagramEditorInput input = new ConceptDiagramEditorInput(
//				Messages.CONCEPT_untitled,
//				(IProject) null);
//		try {
//			page.openEditor(input, ConceptDiagramEditor.ID);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
