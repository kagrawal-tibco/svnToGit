package com.tibco.cep.studio.ui.diagrams.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.ui.editors.EntityDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.concepts.ConceptDiagramEditor;
import com.tibco.cep.studio.ui.editors.utils.Messages;

public class OpenConceptActionDelegate implements
		IWorkbenchWindowActionDelegate {

	private ISelection fSelection;
	IResource project;	

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {

		IWorkbenchPage page =
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		this.project = this.getProject();
		if (this.project == null) {
			System.err.println(Messages.getString("ERROR_PROJECT"));
			return;
		}
		else {
			StructuredSelection selection = (StructuredSelection) fSelection;
			Object obj = selection.getFirstElement();
			if(obj instanceof IFile){
				IFile file = (IFile)selection.getFirstElement();
				if(file.getFileExtension().equals(Messages.getString("CONCEPT_extension"))){
					EntityDiagramEditorInput input = new EntityDiagramEditorInput(file,(IProject) this.project);
						try {
							page.openEditor(input, ConceptDiagramEditor.ID);
						} catch (PartInitException e) {
							System.out.println(e.getMessage());
						}		
				}else{
					System.err.println(Messages.getString("CONCEPT_ERROR1"));
					return;
				}
			}else{
				System.err.println(Messages.getString("ERROR_FILE"));
				return;
			}
			
		}
	}
	
	private IResource getProject() {
		if (fSelection == null || !(fSelection instanceof StructuredSelection)) {
			return null;
		}
		StructuredSelection selection = (StructuredSelection) fSelection;
		Object obj = selection.getFirstElement();
		if (!(obj instanceof IResource)) {
			return null;
		}
		else {
			return ((IResource)obj).getProject();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.fSelection = selection;
	}

}
