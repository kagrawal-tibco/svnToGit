package com.tibco.cep.studio.rms.ui.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.security.authz.permissions.actions.ActionsFactory;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.security.util.SecurityUtil;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.rms.ui.utils.ActionConstants;
import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;
import com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsCommitDialog;
import com.tibco.cep.studio.ui.StudioUIManager;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * @author aathalye
 *
 */
public class CommitResourceAction extends AbstractRMSAction {

	private IStructuredSelection strSelection;
	private IProject project;
	private IEditorPart active;
	
	@Override
	public void runWithEvent(IAction action, Event event) {
		try {
			super.run(action);
			
			// If decision manager check for commit permission
			if (Utils.isStandaloneDecisionManger() && !checkCommitActionAccess()) {
				return;							
	    	}
			
			ArtifactSummaryEntry[] artifactsToCommit = getArtifactsToCommit();
			if ((m_SelectedResource != null) && (artifactsToCommit != null)) {
				RMSArtifactsCommitDialog dialog = new RMSArtifactsCommitDialog(
						shell, "Commit", m_SelectedResource, artifactsToCommit);
				dialog.open();
			}
		} catch (Exception e) {
			RMSUIPlugin.log(e);
		}
	}
	
	
	public void init(IAction action) {
		StudioUIManager.getInstance().addAction(ActionConstants.COMMIT_ACTION, action);
		action.setEnabled(false);
	}
	
	private ArtifactSummaryEntry[] getArtifactsToCommit() throws IOException {
		//Only use project as selection if nothing is selected from the explorer.
		//The explorer selection takes precedence.
		if (project == null || !_selection.isEmpty()) {
			getResourceFromSelection();
		}
		if (m_SelectedResource != null) {
			List<ArtifactSummaryEntry> artifactsToCommit = ArtifactsManagerUtils.listArtifactsToCommit(m_SelectedResource);
			List<ArtifactSummaryEntry> artifactsCanCommit = new ArrayList<ArtifactSummaryEntry>();
			Iterator<ArtifactSummaryEntry> itr = artifactsToCommit.iterator();
			while (itr.hasNext()) {
				ArtifactSummaryEntry entry = itr.next();
				boolean canCommit = ArtifactsManagerUtils.isCommitSupported(entry.getOperationType(), entry.getArtifact().getArtifactType());
				if (canCommit){
					artifactsCanCommit.add(entry);
				}
			}
			//Remove duplicates for now.
			//TODO Such a check should not be required.
			Set<ArtifactSummaryEntry> uniqueArtifactsToCommit = new HashSet<ArtifactSummaryEntry>();
			uniqueArtifactsToCommit.addAll(artifactsCanCommit);
			return uniqueArtifactsToCommit.toArray(new ArtifactSummaryEntry[uniqueArtifactsToCommit.size()]);	
		}
		
		return null;
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
				if (project != null) {
					m_SelectedResource = project;
				}
			}
		}
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
				action.setEnabled(RMSUIUtils.islogged());
			} else {
				action.setEnabled(false);
			}
		}
	}
	
	/**
	 * Check if the specified project has permission to commit artifacts
	 * 
	 * @return
	 */
	private boolean checkCommitActionAccess() {
		boolean hasAccess = true;
		try {
			if (project == null || !_selection.isEmpty()) {
				getResourceFromSelection();
			}
			
			String resourcePath = null;
			if (m_SelectedResource != null) {
				project = m_SelectedResource.getProject();
				
				resourcePath = m_SelectedResource.getProjectRelativePath().removeFileExtension().toString();
				if (!resourcePath.startsWith(CommonIndexUtils.PATH_SEPARATOR)) {
					resourcePath = CommonIndexUtils.PATH_SEPARATOR + resourcePath;
				}
				
				if (!checkValidAccess(project.getName(), ResourceType.PROJECT, resourcePath, "commit")) {
					MessageDialog.openError(Display.getDefault().getActiveShell(), "Access Denied", "Access Denied to Commit Project - " + project.getName());
					hasAccess = false;
				}
			}
		} catch(Exception ex) {
			hasAccess = false;
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Access Denied", ex.getMessage());
			StudioCorePlugin.log(ex);
		}
		return hasAccess;
	}
	
	/**
	 * Check for valid permissions
	 * 
	 * @param projectName
	 * @param resourceType
	 * @param resourcePath
	 * @param commitAction
	 * @return
	 */
	private boolean checkValidAccess(String projectName, ResourceType resourceType, String resourcePath, String commitAction) {
		List<Role> roles = AuthTokenUtils.getLoggedInUserRoles();
		Permit permit = Permit.DENY;
		if (!(roles.isEmpty()) && !(resourcePath.isEmpty())) {
			com.tibco.cep.security.authz.permissions.actions.IAction action = ActionsFactory.getAction(resourceType, commitAction);
			permit = SecurityUtil.ensurePermission(projectName, resourcePath, resourceType, roles, action, PermissionType.BERESOURCE);
		}
		if (Permit.DENY.equals(permit)) {
			return false;
		}
		return true;
	}
}
