package com.tibco.cep.studio.rms.ui.wizards;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class RMSArtifactsRevertDialog extends RMSArtifactsUpdateDialog {
	
	private ArtifactSummaryEntry[] artifactsToCommit;


	/**
	 * @param parent
	 * @param title
	 * @param resource
	 * @param projectsList
	 * @param artifactsToCommit
	 */
	public RMSArtifactsRevertDialog(Shell parent, 
			                        String title,
			                        IResource resource,
			                        String[] projectsList, 
			                        ArtifactSummaryEntry[] artifactsToCommit) {
		super(parent, title, resource, projectsList, true);
		this.artifactsToCommit = artifactsToCommit;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#populateArtifacts()
	 */
	protected void populateArtifacts() {
		for (ArtifactSummaryEntry artifactSummaryEntry : artifactsToCommit) {
			ArtifactOperation opType = artifactSummaryEntry.getOperationType();
			if (opType == ArtifactOperation.MODIFY || opType == ArtifactOperation.DELETE) {
				createArtifactsItem(artifactSummaryEntry.getArtifact(), 
						artifactSummaryEntry.getOperationType());
			}
		}
	}
	
	protected void createDialogAndButtonArea(Composite parent) {
		super.createDialogAndButtonArea(parent);
		
		populateArtifacts();
		
		//Selecting all Artifact by default
		handleSelectArtifacts(true);
		getArtifactsButton().setVisible(false);//hide getArtifacts button
		select.setSelection(true);
		if (checkedResources.size() > 0) {
			okButton.setEnabled(true);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsUpdateDialog#getContainer()
	 */
	public RMSArtifactsRevertDialog getContainer(){
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	public void modifyText(final ModifyEvent e){
		if (e.getSource() == projectsCombo) {
			try {
				IProject selectedProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectsCombo.getText());
				List<ArtifactSummaryEntry> artifactsToCommit = ArtifactsManagerUtils
						.listArtifactsToCommit(selectedProject);
				this.artifactsToCommit = artifactsToCommit.toArray(new ArtifactSummaryEntry[artifactsToCommit.size()]);
				
				artifactsTable.removeAll();
				populateArtifacts();
			} catch (IOException ioException) {
				RMSUIPlugin.log(ioException);
			}
		}
		super.modifyText(e);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsUpdateDialog#isRevert()
	 */
	public boolean isRevert() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsUpdateDialog#showEmptyArtifactErrorMessage()
	 */
	protected void showEmptyArtifactErrorMessage() {
		//required for update dialog.
	}
	
	@Override
	protected String getModifyOperationText() {
		return "Reverting ";
	}
}