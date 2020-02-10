package com.tibco.cep.studio.rms.ui.actions;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;

import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.artifacts.RMSRepo;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.rms.ui.utils.ActionConstants;
import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;
import com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsRevertDialog;
import com.tibco.cep.studio.ui.StudioUIManager;

/**
 * 
 * @author sasahoo
 *
 */
public class RMSRevertAction extends AbstractRMSAction {

	private IStructuredSelection strSelection;
	
	@Override
	public void runWithEvent(IAction action, Event event) {
		try {
			ArtifactSummaryEntry[] artifactsToCommit = getArtifactsToRevert();
			String[] projectsList = getProjectList();
			if ((m_SelectedResource != null) && (artifactsToCommit != null)) {
				RMSArtifactsRevertDialog dialog = new RMSArtifactsRevertDialog(
						shell, "Revert", m_SelectedResource, projectsList, artifactsToCommit);
				dialog.open();
			}
		} catch (Exception e) {
			RMSUIPlugin.log(e);
		}
	}
	
	@Override
	public void init(IAction action) {
		StudioUIManager.getInstance().addAction(ActionConstants.REVERT_ACTION, action);
		action.setEnabled(false);
	}
	
	private ArtifactSummaryEntry[] getArtifactsToRevert() throws IOException {
		getResourceFromSelection();
		if (m_SelectedResource != null) {
		List<ArtifactSummaryEntry> artifactsToCommit = ArtifactsManagerUtils
					.listArtifactsToCommit(m_SelectedResource);
			return artifactsToCommit.toArray(new ArtifactSummaryEntry[artifactsToCommit.size()]);	
		}
		return null;
	}
	
	/**
	 * Get the list of projects based on the selected resource
	 * @return
	 */
	private String[] getProjectList(){
		String localProject = m_SelectedResource.getProject().getName();
		
		//Get URL same as the one with checkout
		RMSRepo rmsRepo = 
			RMSArtifactsSummaryManager.getInstance().getRMSRepoInfo(localProject);
		return new String[] {rmsRepo.getRmsProject()};
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
		if (_selection != null && _selection instanceof IStructuredSelection) {
			strSelection = (IStructuredSelection) _selection;
			if (strSelection.getFirstElement() instanceof IResource) {
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
}
