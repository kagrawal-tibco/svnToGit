package com.tibco.cep.studio.ui.diagrams.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

public class NewEventFormAction implements IWorkbenchWindowActionDelegate {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
//		ConceptFormEditorInput input = new ConceptFormEditorInput(
//				Messages.CONCEPT_untitled);
		try {
//			page.openEditor(input, ConceptFormEditor.ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
