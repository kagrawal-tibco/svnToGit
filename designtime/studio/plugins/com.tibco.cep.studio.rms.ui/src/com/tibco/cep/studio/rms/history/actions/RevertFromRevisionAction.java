package com.tibco.cep.studio.rms.history.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils;
import com.tibco.cep.studio.rms.history.RMSHistoryEditor;
import com.tibco.cep.studio.rms.history.RMSHistoryEditorInput;
import com.tibco.cep.studio.rms.history.model.RevisionDetailsItem;
import com.tibco.cep.studio.ui.StudioUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class RevertFromRevisionAction extends Action {
	
	private TableViewer viewer;
	private RMSHistoryEditor rmsHistoryEditor;
	
	public RevertFromRevisionAction(TableViewer viewer, RMSHistoryEditor rmsHistoryEditor) {
		super("Update changes from this revision");
		this.viewer = viewer;
		this.rmsHistoryEditor = rmsHistoryEditor;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			Object firstElement = structuredSelection.getFirstElement();
			if (firstElement instanceof RevisionDetailsItem) {
				RevisionDetailsItem revisionDetailsItem = (RevisionDetailsItem)firstElement;
				RMSHistoryEditorInput editorInput = (RMSHistoryEditorInput)rmsHistoryEditor.getEditorInput();
				String projectName = editorInput.getRepoProjectName();
				String url = editorInput.getHistoryURL();
				String artifactPath = revisionDetailsItem.getArtifactPath();
				String type = revisionDetailsItem.getArtifactType();
				String contents = ArtifactsManagerUtils.fetchRevisionContents(url, projectName, revisionDetailsItem).getArtifact().getArtifactContent();
				IFile file = (IFile)ValidationUtils.resolveResourceReference(artifactPath + "." + type, projectName);
				try {
					ByteArrayInputStream bos = null;
					if (file.exists()) {
						boolean overwrite = MessageDialog.openQuestion(rmsHistoryEditor.getSite().getShell(), "Overwite", 
						"Do you want to overwrite the existing file?");
						if (overwrite) {
							bos = new ByteArrayInputStream(contents.getBytes());
							file.setContents(bos, IResource.FORCE, new NullProgressMonitor());
							bos.close();
							file.refreshLocal(IFile.DEPTH_ONE, null);
						}
					}
				} catch (CoreException e) {
					StudioUIPlugin.log(e);
				}
				catch (IOException e) {
					StudioUIPlugin.log(e);
				}
			}
		}
	}
	
	public boolean isEnabled() {
		ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			int selectionSize = structuredSelection.size();
			if (selectionSize != 1) {
				setEnabled(false);
				return false;
			}
		}
		setEnabled(true);
		return true;
	}
}