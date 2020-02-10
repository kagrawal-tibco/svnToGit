package com.tibco.cep.studio.ui.diagrams.actions;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class GenerateConceptDiagramActionDelegate implements IWorkbenchWindowActionDelegate,IObjectActionDelegate {

	private ISelection _selection;
	private IStructuredSelection strSelection;
	private IProject project;	
	private IWorkbenchPage page;

	public void init(IWorkbenchWindow window) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {

		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			if(_selection != null && _selection instanceof IStructuredSelection){
				strSelection = (IStructuredSelection)_selection;
				if(!(strSelection.getFirstElement() instanceof IProject)){
					if (!StudioResourceUtils.isStudioProject(strSelection)) {
						return;
					}
					project = StudioResourceUtils.getCurrentProject(strSelection);
				}else{
					project = (IProject)strSelection.getFirstElement();
				}
				final IFile conceptViewFile = project.getFile(project.getName()+ ".conceptview");
				if(conceptViewFile.exists()){
					EditorUtils.refreshDiagramEditor(page, conceptViewFile);
				}else{
					IWorkspace workSpace = ResourcesPlugin.getWorkspace();
					if(workSpace != null){
						String projectPath = workSpace.getRoot().getProject(project.getName()).getLocation().toString();
						File mfile = new File(projectPath + "/"+ project.getName()+ ".conceptview");
						if (!mfile.exists()) {
							try {
								mfile.createNewFile();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}

					IFile file = project.getFile(project.getName()+ ".conceptview");
					EditorUtils.openDiagramEditor(page, file);
				}
				
			}else
				return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		_selection = selection;
		if (!(_selection.isEmpty()) && _selection instanceof IStructuredSelection) {
			strSelection = (IStructuredSelection)_selection;
			if(strSelection.size() == 1){
				if (!(strSelection.getFirstElement() instanceof IProject)) {
					project = StudioResourceUtils.getCurrentProject(strSelection);
				} else {
					project = (IProject)strSelection.getFirstElement();
				}
				if(project != null && project.isOpen()){
					action.setEnabled(true);
				}else{
					action.setEnabled(false);
				}
			} else {
				action.setEnabled(false);
			}	
		} else if (_selection.isEmpty()) {
			action.setEnabled(false);
		}
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}
}
