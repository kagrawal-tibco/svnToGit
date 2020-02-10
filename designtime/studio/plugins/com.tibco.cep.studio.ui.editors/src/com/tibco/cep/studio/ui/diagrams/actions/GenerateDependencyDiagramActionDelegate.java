package com.tibco.cep.studio.ui.diagrams.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.editors.dependency.DependencyDiagramEditor;
import com.tibco.cep.studio.ui.editors.dependency.DependencyDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author ggrigore 
 *
 */
public class GenerateDependencyDiagramActionDelegate implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {

	private ISelection _selection;
	private IStructuredSelection strSelection;
	private IProject project;	
	private IFile entity;
	private IWorkbenchPage page;

	public void init(IWorkbenchWindow window) {
	}

	public void run(IAction action) {
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			if(_selection != null && _selection instanceof IStructuredSelection){
				strSelection = (IStructuredSelection)_selection;
				
				if(!(strSelection.getFirstElement() instanceof IProject)){
					if (!StudioResourceUtils.isStudioProject(strSelection)) {
						return;
					}
					project = StudioResourceUtils.getCurrentProject(strSelection);///////
				} else {
					project = (IProject)strSelection.getFirstElement();
				}
			
				if (strSelection.getFirstElement() instanceof IFile) {
					entity = (IFile) strSelection.getFirstElement(); 
					if (!entity.getFileExtension().equalsIgnoreCase("concept") &&
							!entity.getFileExtension().equalsIgnoreCase("event") &&
							!entity.getFileExtension().equalsIgnoreCase(CommonIndexUtils.TIME_EXTENSION) &&
							!entity.getFileExtension().equalsIgnoreCase("channel") &&
							!entity.getFileExtension().equalsIgnoreCase("archive") &&
							!entity.getFileExtension().equalsIgnoreCase("scorecard") &&
							!entity.getFileExtension().equalsIgnoreCase("rule") &&
							!entity.getFileExtension().equalsIgnoreCase("rulefunction") &&
							!entity.getFileExtension().equalsIgnoreCase("statemachine") &&
							!entity.getFileExtension().equalsIgnoreCase("domain")) {				
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"Dependency Diagram",
							"Can only generate dependency diagrams for BE entities.");
						return;
					}
				}

				IFile file = (IFile)strSelection.getFirstElement();
				final IEditorPart editor = EditorUtils.getDependencyDiagramEditorOpen(page, IndexUtils.getFullPath(file));
				if(editor == null){
					DependencyDiagramEditorInput input = new DependencyDiagramEditorInput(file, project);
					try {
						page.openEditor(input, DependencyDiagramEditor.ID);
					} catch (PartInitException e) {
						e.printStackTrace();
					}		
				}else{
					StudioUIUtils.invokeOnDisplayThread(new Runnable(){
						public void run() {
							page.activate(editor);	
						}
					}, false);
				}
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		_selection = selection;
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub	
	}

}
