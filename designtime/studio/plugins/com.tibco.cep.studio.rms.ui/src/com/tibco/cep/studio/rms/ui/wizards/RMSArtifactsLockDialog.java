/**
 * 
 */
package com.tibco.cep.studio.rms.ui.wizards;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;

/**
 * @author hnembhwa
 *
 */
public class RMSArtifactsLockDialog extends AbstractRMSArtifactsDialog {
	
	private String username;
	
	private String projectName;
	
	private String artifactPath;
	
	private Shell shell;
	
	public RMSArtifactsLockDialog getContainer(){
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#createTableArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createTableArea(Composite parent) {
		
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(parent);

		return parent;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#performOk()
	 */
	@Override
	protected boolean performOk() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractArtifactsRunnableDialog#cancelPressed()
	 */
	@Override
	protected void cancelPressed() {
		close();
	}

	public RMSArtifactsLockDialog(Shell parentShell, 
			                      String username,
			                      String projectName,
			                      String artifactPath) {
		super(parentShell, "Lock " + artifactPath, true, false);
		setWidth(100);
		setNeedsProgressMonitor(false);
		this.username = username;
		this.projectName = projectName;
		this.artifactPath = artifactPath;
	}
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#createProjectSelectionArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createProjectSelectionArea(Composite parent) {
		// TODO Auto-generated method stub
		//DO nothing method
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		if(!performOk()) {
			return;
		}
		Object response;
		try {
			response = 
				ArtifactsManagerClient.
				lockArtifact(null, username, projectName, artifactPath, comments);
			if (response instanceof com.tibco.cep.studio.rms.model.Error) {
				com.tibco.cep.studio.rms.model.Error error = 
					(com.tibco.cep.studio.rms.model.Error)response;
				close();
				MessageDialog.openError(shell, error.getErrorCode(), error.getErrorString());
			} else {
				String lockRequestId = (String)response;
				close();
				MessageDialog.openInformation(shell, "LOCK_RESOURCE", "Lock Request Sent for approval with Request Id " + lockRequestId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void addColumns() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	protected void disposeComponents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void populateArtifacts() {
		//Do nothing here
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#createDetailsItem(com.tibco.cep.rms.artifacts.Artifact, com.tibco.cep.rms.artifacts.ArtifactOperation)
	 */
	@Override
	protected void createDetailsItem(Artifact fetchedArtifact,
			ArtifactOperation artifactOperation) {
		//Do nothing here
	}	
	
	
}
