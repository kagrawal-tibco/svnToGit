package com.tibco.cep.studio.rms.ui.actions;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.rms.artifacts.RMSRepo;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;
import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.ui.utils.ActionConstants;
import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;
import com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsUpdateDialog;
import com.tibco.cep.studio.ui.StudioUIManager;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * @author aathalye
 *
 */
public class UpdateResourceAction extends AbstractRMSAction {

	private IStructuredSelection strSelection;
	private IProject project;
	private IEditorPart active;
	@Override
	public void init(IAction action) {
		StudioUIManager.getInstance().addAction(ActionConstants.UPDATE_ACTION, action);
		action.setEnabled(false);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.rms.actions.AbstractRMSAction#selectionChanged(org
	 * .eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
		getResourceFromEditor();
		if (_selection != null && _selection instanceof IStructuredSelection) {
			strSelection = (IStructuredSelection) _selection;
			if ((strSelection.getFirstElement() instanceof IProject)
					|| (strSelection.getFirstElement() instanceof IFolder
					|| active!=null)) {
				if(RMSUIUtils.islogged()){
					action.setEnabled(true);
				} else{
					action.setEnabled(false);
				}
			} else {
				action.setEnabled(false);
			}
		}
	}
	
	@Override
	public void getResourceFromEditor() {
		if (!PlatformUI.isWorkbenchRunning() || PlatformUI.getWorkbench().isClosing()) {
			return;
		}
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		if (page != null) {
			active = page.getActiveEditor();
			if (active != null) {
				project = StudioResourceUtils.getProjectForInput(active.getEditorInput());
				if(project != null){
					m_SelectedResource = project;
				}
			}
		}
	}
	
	@Override
	public void runWithEvent(IAction action, Event event) {
		try {
			super.run(action);
			if(project!=null){
			getResourceFromSelection();
			}
			if (m_SelectedResource == null) {
				return;
			}
			
			// Use the same URL as was used for successful login
//			String loginURL = RMSUtil.buildRMSURL();
			String[] projectsList = null;//ArtifactsManagerClient.fetchServedProjects(loginURL);
			String localProject = m_SelectedResource.getProject().getName();
			
			//Get URL same as the one with checkout
			RMSRepo rmsRepo = 
				RMSArtifactsSummaryManager.getInstance().getRMSRepoInfo(localProject);
			if (rmsRepo == null) {
				//Probably not checked out project
				String loginURL = RMSUtil.buildRMSURL();
				projectsList = ArtifactsManagerClient.fetchServedProjects(loginURL);
				RMSArtifactsUpdateDialog dialog = new RMSArtifactsUpdateDialog(
						shell, "Update", m_SelectedResource, projectsList, false);
				dialog.open();
			} else {
				projectsList = new String[] {rmsRepo.getRmsProject()};
				if (m_SelectedResource != null) {
					RMSArtifactsUpdateDialog dialog = new RMSArtifactsUpdateDialog(
							shell, "Update", m_SelectedResource, projectsList, true);
					dialog.open();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
