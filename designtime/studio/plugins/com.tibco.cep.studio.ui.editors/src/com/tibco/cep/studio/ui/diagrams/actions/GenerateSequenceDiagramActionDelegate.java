package com.tibco.cep.studio.ui.diagrams.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
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

import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.editors.sequence.SequenceDiagramEditor;
import com.tibco.cep.studio.ui.editors.sequence.SequenceDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * 
 * @author ggrigore
 *
 */
public class GenerateSequenceDiagramActionDelegate implements IWorkbenchWindowActionDelegate,IObjectActionDelegate {

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
					project = StudioResourceUtils.getCurrentProject(strSelection);///////
				} else {
					project = (IProject)strSelection.getFirstElement();
				}
				
				IFile file = (IFile)strSelection.getFirstElement();
				IEditorPart editor = EditorUtils.getSequenceDiagramEditorOpen(page, IndexUtils.getFullPath(file));
				if(editor == null){
					SequenceDiagramEditorInput input = new SequenceDiagramEditorInput(file, project);
					try {
						page.openEditor(input, SequenceDiagramEditor.ID);
					} catch (PartInitException e) {
						e.printStackTrace();
					}		
				}else{
					page.activate(editor);
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
