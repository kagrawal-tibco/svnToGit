/**
 * 
 */
package com.tibco.cep.studio.ui.editors.retegraph;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * @author aathalye
 *
 */
public class ReteGraphActionDelegate implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {
	
	private ISelection _selection;
	private IProject project;
	private IStructuredSelection structuredSelection;
	private IWorkbenchPage page;
	private boolean createViewWhenAnalyze = false;
	
	public ReteGraphActionDelegate() {
	}

	public void run(IAction action) {
		
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
		if (_selection != null && _selection instanceof IStructuredSelection) {
			structuredSelection = (IStructuredSelection)_selection;
			Object firstElement = structuredSelection.getFirstElement();
			if (!(firstElement instanceof IProject)) {
				if (!StudioResourceUtils.isStudioProject(structuredSelection)) {
						return;
					}
				project = StudioResourceUtils.getCurrentProject(structuredSelection);
			} else {
				project = (IProject)firstElement;
			}
			if(project.getProject().isOpen()){
				IPreferenceStore prefStore = EditorsUIPlugin.getDefault().getPreferenceStore();
				createViewWhenAnalyze = prefStore.getBoolean(StudioPreferenceConstants.CREATE_VIEW);
				
				if(createViewWhenAnalyze) {
					final IFile projectViewFile = project.getFile(project.getName()+ Messages.getString("PV_extension"));
					if(projectViewFile.exists()){
						EditorUtils.refreshDiagramEditor(page, projectViewFile);
					}else{
						File mfile = new File(StudioResourceUtils.getCurrentWorkspacePath()+"/"+project.getName()+"/"+project.getName()+ Messages.getString("PV_extension"));
						if(!mfile.exists())	mfile.createNewFile();//creating the file
						IFile file = project.getFile(project.getName()+ ".projectview");
						EditorUtils.openDiagramEditor(page, file);
					}
				}
				
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(new Shell());
				dialog.setOpenOnRun(true);
				try {
					dialog.run(true, true, new IRunnableWithProgress() {
					
						@Override
						public void run(IProgressMonitor monitor) throws InvocationTargetException,
								InterruptedException {
							// TODO
						}
					});
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else 
				return; 
		}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	
	public void selectionChanged(IAction action, ISelection selection) {
		_selection = selection;
		if (!(_selection.isEmpty()) && _selection instanceof IStructuredSelection) {
			structuredSelection = (IStructuredSelection)_selection;
			if(structuredSelection.size() == 1){
				if (!(structuredSelection.getFirstElement() instanceof IProject)) {
					project = StudioResourceUtils.getCurrentProject(structuredSelection);
				} else {
					project = (IProject)structuredSelection.getFirstElement();
				}
				if(project != null && project.isOpen()){
					action.setEnabled(true);
				}else{
					action.setEnabled(false);
				}
			} else {
				action.setEnabled(false);
			}	
		} else if(_selection.isEmpty()) {
			action.setEnabled(false);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}
	
	
}
