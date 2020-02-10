package com.tibco.cep.studio.rms.ui.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.rms.ui.utils.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class RMSArtifactsCheckoutDialog extends RMSArtifactsUpdateDialog {
	
	private Text projectnameText;
	
	/**
	 * The text value
	 */
	private String projectName;
	
	/**
	 * List of local project names
	 */
	private List<String> projectNames = new ArrayList<String>();
	
	
	/**
	 * @param parent
	 * @param title
	 * @param resourceToUpdate
	 * @param projectsList
	 */
	public RMSArtifactsCheckoutDialog(Shell parent, 
			                          String title,
			                          String[] projectsList) {
		super(parent, title, projectsList);
	}
	
	@Override
	protected void createProjectSelectionArea(Composite parent) {
		super.createProjectSelectionArea(parent);
		//Project Name Label/Field
		new Label(ckURLGroup, SWT.NONE).setText(Messages.getString("PROJECTS_NAME"));
		projectnameText = new Text(ckURLGroup, SWT.SINGLE | SWT.BORDER);
		projectnameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (!(projectnameText.getText().isEmpty())) {
					
					if (projectName != null 
							&& !projectName.equals(projectnameText.getText())) {
						if (!getArtifactsButton.isEnabled()) {
							getArtifactsButton.setEnabled(true);
						}
					}
					
					projectnameText.setToolTipText("");
					projectnameText.setForeground(new Color(null, 0, 0, 0));
					IProject[] project = CommonUtil.getAllStudioProjects();
					for (int i = 0; i < project.length; i++) {
						String workspaceProjectName = project[i].getName();
						if (!(projectNames.contains(workspaceProjectName))) {
							projectNames.add(workspaceProjectName);
						}
						if (projectNames.contains(projectnameText.getText().toString())) {
							projectnameText.setForeground(new Color(null, 255, 0, 0));
							projectnameText.setToolTipText(projectnameText.getText().toString() + " already exists.");
							if (getCheckedResources().size() > 0) {
								getArtifactsButton.setEnabled(true);
							} else {
								getArtifactsButton.setEnabled(false);
							}
							okButton.setEnabled(shouldOkEnable());
						} else {
							projectnameText.setForeground(new Color(null, 0, 0, 0));
							projectnameText.setToolTipText("");
							getArtifactsButton.setEnabled(true);
							okButton.setEnabled(shouldOkEnable());
						}
					}
				} else {
					getArtifactsButton.setEnabled(false);
					okButton.setEnabled(shouldOkEnable());
				}
			}
		});
		projectsCombo.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				if (!(projectsCombo.getText().isEmpty())) {
					projectnameText.setText(projectsCombo.getText());
				} 
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		GridData project_name = new GridData(GridData.FILL_HORIZONTAL);
		projectnameText.setLayoutData(project_name);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#shouldOkEnable()
	 */
	@Override
	protected boolean shouldOkEnable() {
		return !projectNames.contains(projectnameText.getText().toString())
		        && super.shouldOkEnable();
	}

	@Override
	protected void populateArtifacts() {
		try {
			//Is this DM?
			boolean isDecisionManager = Utils.isStandaloneDecisionManger();
			Object response = 
				ArtifactsManagerUtils.getAllRelevantArtifacts(urlCombo.getText(),
						                                      AuthTokenUtils.getLoggedinUser(), 
						                                      projectsCombo.getText(), 
						                                      "/",
						                                      isDecisionManager);
			if (response instanceof com.tibco.cep.studio.rms.model.Error) {
				com.tibco.cep.studio.rms.model.Error error = (com.tibco.cep.studio.rms.model.Error)response;
				MessageDialog.openError(this.getShell(), error.getErrorCode(), error.getErrorString());
			} else if (response instanceof List) {
				fireProjectFetchSuccessEvent(urlCombo.getText());
				@SuppressWarnings("unchecked")
				List<Artifact> artifacts = (List<Artifact>)response;
				if (artifacts.isEmpty()) {
					MessageDialog.openError(this.getShell(), "GET_ARTIFACTS_LIST_ERROR", "No artifacts to fetch yet");
				}
				for (Artifact artifact : artifacts) {
					//All operations will be ADD type
					ArtifactOperation artifactOperation = ArtifactOperation.ADD;
					createArtifactsItem(artifact, artifactOperation);
				}
				getArtifactsButton.setEnabled(false);
			}
		} catch (Exception e) {
			RMSUIPlugin.log(e);
		}
	}
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsUpdateDialog#disposeComponents()
	 */
	@Override
	protected void disposeComponents() {
		projectName = projectnameText.getText();
		super.disposeComponents();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsUpdateDialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		if (!performOk()) {
			return;
		}
		//TODO Check if project already exists
		//TODO replace name here with one which will be typed in name field
		final IProject newProject = getProjectHandle(projectName);
		if (newProject.exists()) {
			return;
		}
		final File targetLocation = getTargetSaveLocation();
		final String loggedInUsername = AuthTokenUtils.getLoggedinUser();
		final String rootPath = new File(targetLocation, newProject.getName()).getAbsolutePath();
//		title = title  + "...";
		getShell().setText(title);
		if (needsProgressMonitor) {
			detailsTable.removeAll();
			StudioUIUtils.invokeOnDisplayThread(new Runnable(){
				public void run(){
					IRunnableWithProgress op = new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor)
						throws InvocationTargetException,
						InterruptedException {
							SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
							//Create studio project infrastructure first
							subMonitor.setTaskName("Creating studio project:");
							WorkspaceModifyOperation op = new StudioProjectCreationOperation(newProject, targetLocation, true);
							SubMonitor modificationOpMonitor = subMonitor.newChild(10);
							op.run(modificationOpMonitor);
							modificationOpMonitor.done();
							
							subMonitor.setTaskName("Checkout Started...");
							try {
								SubMonitor anotherMonitor = subMonitor.newChild(artifactPaths.size());
								for (int index = 0; index < artifactPaths.size(); index++) {
									String artifactPath = artifactPaths.get(index);
									String artifactExtn = artifactExtns.get(index);
									String operation = operations.get(index);

									anotherMonitor.setTaskName("Fetching Artifact..." + artifactPath);

									final ArtifactOperation artifactOperation = ArtifactOperation.get(operation);
									artifactOperations.add(artifactOperation);

									// Make remote call
									try {
										anotherMonitor.setTaskName("Saving Artifact..." + artifactPath);
										Artifact fetchedartifact = ArtifactsManagerUtils.getArtifactContents(
												selectedUrl, loggedInUsername, dropDownProject,
												artifactPath,
												artifactExtn);
										ArtifactsManagerUtils.saveContents(newProject.getName(),
												rootPath, fetchedartifact);
										fireRemoteArtifactEvent(newProject.getName(),
												fetchedartifact,
												artifactOperation);
										fetchedartifacts.add(fetchedartifact);

										updateItems(fetchedartifact,
												artifactOperation, false,
												false, null);

									} catch (Exception e) {
										RMSUIPlugin.log(e);
										updateItems(null, null, true, false,
												"Checkout failed due to " + e.getMessage());
									}
									anotherMonitor.worked(index);
								}
								//Fetch updated Access Control config file for DM
								if (Utils.isStandaloneDecisionManger()) {
									String aclFileContents = refreshAccessConfigFile(loggedInUsername, dropDownProject);
									saveAccessConfigFile(projectName, rootPath, aclFileContents);
								}
								//update rms repo information
								updateRMSRepoInfo();
								anotherMonitor.done();
							} catch (Exception e) {
								RMSUIPlugin.log(e);
							} finally {
								subMonitor.done();
							}
							monitor.setTaskName("Checkout Done..Wait..Workspace modifying");
							updateItems(null, null, false, true, "Completed.");
							monitor.setTaskName("Artifacts successfully Checkedout");
							
							try {
								refreshResource(newProject, monitor);
							} catch (CoreException e) {
								RMSUIPlugin.log(e);
								MessageDialog.openError(getShell(), "REFRESH_PROJECT", e.getMessage());
							}
							monitor.done();
						}
					};
					try {
						getContainer().run(true, true, op);
					} catch (InvocationTargetException e) {
						RMSUIPlugin.log(e);
						updateItems(null, null, true, false,
								"Update failed due to " + e.getMessage());
					} catch (InterruptedException e) {
						RMSUIPlugin.log(e);
						updateItems(null, null, true, false,
								"Update failed due to " + e.getMessage());
					}	
				}
			}, false);
		}
	}
	
	private IProject getProjectHandle(String projectName) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	}
	
	private File getTargetSaveLocation() {
		return new File(StudioResourceUtils.getCurrentWorkspacePath());
	}
	
	private void updateRMSRepoInfo() {
		RMSArtifactsSummaryManager.getInstance().createRMSRepoInfo(projectName, selectedUrl, dropDownProject);
	}
}
