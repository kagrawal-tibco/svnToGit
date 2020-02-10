package com.tibco.cep.studio.ui.diagrams.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tibco.cep.studio.ui.editors.concepts.ConceptDiagramEditor;

/**
 * 
 * @author ggrigore
 *
 */
public class SaveConceptActionDelegate implements IWorkbenchWindowActionDelegate {

		private IWorkbenchWindow window;	
	
		public void dispose() {
			// TODO Auto-generated method stub
		}

		public void init(IWorkbenchWindow window) {
			this.window = window;
		}

		public void run(IAction action) {
			
			IWorkbenchPage page = this.window.getActivePage();
			IEditorPart activeEditorPart = page.getActiveEditor();
			ConceptDiagramEditor conceptDiagramEditor = null;
			if (activeEditorPart instanceof ConceptDiagramEditor) {
				conceptDiagramEditor = (ConceptDiagramEditor) activeEditorPart;
				conceptDiagramEditor.doSave(null);	
			}
			else {
				System.err.println("No concept diagram active.");
			}
		}

		public void selectionChanged(IAction action, ISelection selection) {
			// TODO Auto-generated method stub
		}
}
