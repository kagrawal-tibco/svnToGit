package com.tibco.cep.studio.rms.ui.wizards;

import static java.io.File.separator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.security.SecurityPlugin;
import com.tibco.cep.security.authz.core.ACL;
import com.tibco.cep.security.authz.core.ACLManager;
import com.tibco.cep.security.authz.core.impl.ACLManagerImpl;
import com.tibco.cep.security.authz.utils.ACLConstants;
import com.tibco.cep.security.authz.utils.ACLUtils;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.artifacts.RMSRepo;
import com.tibco.cep.studio.rms.artifacts.manager.IArtifactResourceChangeEvent;
import com.tibco.cep.studio.rms.artifacts.manager.IRemoteResourceDelta;
import com.tibco.cep.studio.rms.artifacts.manager.impl.ArtifactsSummaryChangeListener;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RemoteResourceChangeEvent;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RemoteResourceDelta;
import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.core.RMSCorePlugin;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.preferences.util.RMSPreferenceUtils;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.rms.ui.listener.IUpdateCompletionListener;
import com.tibco.cep.studio.rms.ui.listener.UpdateCompletionEvent;
import com.tibco.cep.studio.rms.ui.listener.impl.UpdatePreferenceListener;
import com.tibco.cep.studio.rms.ui.preferences.RMSPreferenceConstants;
import com.tibco.cep.studio.rms.ui.utils.Messages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * Handle updates
 * @author sasahoo
 *
 */
public class RMSArtifactsUpdateDialog extends AbstractRMSArtifactsDialog {
	
	//The selected resource in explorer
	protected IResource selectedResource;
	
	protected Button getArtifactsButton; 
	
	/**
	 * The project selected either through direct selection or resource selection
	 */
	protected IProject projectSelection;
	
	protected String selectedUrl;
	
	protected String dropDownProject;
	
	protected IPreferenceStore preferenceStore = RMSUIPlugin.getDefault().getPreferenceStore();
	
	/**
	 * Whether project selection for RMS should be enabled
	 */
	private boolean disableProjectSelection;
	
	protected List<IUpdateCompletionListener> updateCompletionListeners = new ArrayList<IUpdateCompletionListener>();

	/**
	 * 
	 * @param parent
	 * @param title
	 * @param resourceToUpdate -> Folder/project
	 * @param projectsList
	 */
	public RMSArtifactsUpdateDialog(Shell parent, 
			                        String title,
			                        IResource resourceToUpdate,
			                        String[] projectsList,
			                        boolean disableProjectSelection) {
		this(parent, title, projectsList);
		this.selectedResource = resourceToUpdate;
		projectSelection = selectedResource.getProject();
		this.disableProjectSelection = disableProjectSelection;
		
	}
	
	public RMSArtifactsUpdateDialog(Shell parent, 
                                    String title,
                                    String[] projectsList) {
		super(parent, title);
		this.projectsList = projectsList;
		updateCompletionListeners.add(new UpdatePreferenceListener());
		setNeedsProgressMonitor(true);
	}
	
	public RMSArtifactsUpdateDialog getContainer(){
		return this;
	}

	
	@Override
	protected void addColumns() {
		TableColumn resPathColumn = new TableColumn(artifactsTable, SWT.NULL);
		resPathColumn.setText("Path");
		resPathColumn.pack();
		resPathColumn.addSelectionListener(this);
		TableColumn typeColumn = new TableColumn(artifactsTable, SWT.NULL);
		typeColumn.setText("Type");
		typeColumn.addSelectionListener(this);
		
		TableColumn extensionColumn = new TableColumn(artifactsTable, SWT.Hide);
		extensionColumn.setText("Extension");
		extensionColumn.addSelectionListener(this);

		TableColumn changeTypeColumn = new TableColumn(artifactsTable, SWT.NULL);
		changeTypeColumn.setText("Change Type");
		changeTypeColumn.addSelectionListener(this);
		
		autoTableLayout();
		
		
		artifactsTable.setSortColumn(resPathColumn);
		artifactsTable.setSortDirection(SWT.UP);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#createDialogAndButtonArea(org.eclipse.swt.widgets.Composite)
	 */
	protected void createDialogAndButtonArea(Composite parent) {
		super.createDialogAndButtonArea(parent);
	}
	
	@Override
	protected void createProjectSelectionArea(Composite parent) {
		ckURLGroup = new Group(parent, SWT.SHADOW_ETCHED_IN);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		ckURLGroup.setLayout(layout);
		String baseURL = null;
		
		int numberOfCheckoutURLS = preferenceStore.getInt(RMSPreferenceConstants.RMS_CHECKOUT_URLS_SIZE);
		String[] checkoutURLS = 
			RMSPreferenceUtils.getCheckoutURLs(numberOfCheckoutURLS).toArray(new String[0]);
		
		if (projectSelection != null) {
			//Update case
			String localProject = projectSelection.getName();
			//Get URL same as the one with checkout
			RMSRepo rmsRepo = RMSArtifactsSummaryManager.getInstance().getRMSRepoInfo(localProject);
			baseURL = (rmsRepo != null) ? rmsRepo.getRepoURL() : RMSUtil.buildRMSURL();
		} else {
			//Checkout case
			baseURL = RMSUtil.buildRMSURL();
		}
		new Label(ckURLGroup, SWT.NONE).setText(Messages.getString("URL"));
		urlCombo = new Combo(ckURLGroup, SWT.BORDER);
		urlCombo.setItems(checkoutURLS);
		urlCombo.setText(baseURL);
		urlCombo.setEnabled(!disableProjectSelection);
		if (!disableProjectSelection) {
			urlCombo.addTraverseListener(this);
		}
		urlCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(ckURLGroup, SWT.NONE).setVisible(false);
		new Label(ckURLGroup, SWT.NONE).setText(Messages.getString("PROJECTS_LIST"));
		projectsCombo = new Combo(ckURLGroup, SWT.READ_ONLY);
		if (projectsList != null) {
			projectsCombo.setItems(projectsList);
		}
		projectsCombo.setEnabled(!disableProjectSelection);
		if (!disableProjectSelection) {
			projectsCombo.addTraverseListener(this);
			projectsCombo.addSelectionListener(this);
			projectsCombo.addModifyListener(this);
		} else {
			//Set the first item as text
			projectsCombo.setText(projectsList[0]);
			selectedProject = projectsList[0];
		}
		
		GridData projects_Combo_data = new GridData(GridData.FILL_HORIZONTAL);
		projectsCombo.setLayoutData(projects_Combo_data);
		getArtifactsButton = new Button(ckURLGroup, SWT.NONE);
		getArtifactsButton.setText("Get Artifacts...");
		getArtifactsButton.setEnabled(disableProjectSelection);
		getArtifactsButton.addSelectionListener(this);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#populateArtifacts()
	 */
	
	protected void populateArtifacts() {
		String updatePath = "/" + selectedResource.getFullPath().removeFileExtension()
											.removeFirstSegments(1);
				
		// TODO Handle single file update
		String localProject = projectSelection.getName();
		try {
			//Is this DM?
			boolean isDecisionManager = Utils.isStandaloneDecisionManger();
			Object response = ArtifactsManagerUtils
					.getAllRelevantArtifacts(urlCombo.getText(), 
							                 AuthTokenUtils.getLoggedinUser(), 
							                 projectsCombo.getText(), 
							                 updatePath,
							                 isDecisionManager);
			if (response instanceof com.tibco.cep.studio.rms.model.Error) {
				com.tibco.cep.studio.rms.model.Error error = 
					(com.tibco.cep.studio.rms.model.Error)response;
				MessageDialog.openError(this.getShell(), error.getErrorCode(), error.getErrorString());
			} else if (response instanceof List) {
				fireProjectFetchSuccessEvent(urlCombo.getText());
				@SuppressWarnings("unchecked")
				List<Artifact> artifacts = (List<Artifact>)response;
				if (artifacts.isEmpty()) {
					showEmptyArtifactErrorMessage();
				}
				for (Artifact artifact : artifacts) {
					ArtifactOperation artifactOperation = getOperationType(localProject, artifact);
					if (isRevert() && artifactOperation != ArtifactOperation.ADD) {
						continue;
					} 
					createArtifactsItem(artifact, artifactOperation);
				}
				// case to handle deleted artifacts
				for (Artifact artifact : ArtifactsManagerUtils.getDeletedArtifacts(localProject, artifacts, updatePath)){
					createArtifactsItem(artifact, ArtifactOperation.DELETE);
				}
			}
		} catch (Exception e) {
			RMSUIPlugin.log(e);
		}
	}
	
	protected void fireProjectFetchSuccessEvent(String repoURL) {
		UpdateCompletionEvent updateCompletionEvent = new UpdateCompletionEvent(repoURL);
		for (IUpdateCompletionListener updateCompletionListener : updateCompletionListeners) {
			updateCompletionListener.updateCompleted(updateCompletionEvent);
		}
	}
	
	protected void showEmptyArtifactErrorMessage() {
		MessageDialog.openError(this.getShell(), "GET_ARTIFACTS_LIST_ERROR", "No artifacts to fetch yet");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		if (!performOk()) {
			return;
		}
		final String loggedInUsername = AuthTokenUtils.getLoggedinUser();
		final String localProject = projectSelection.getName();
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
							monitor.beginTask("Update Artifacts started", 100);
														
							monitor.setTaskName("Fetching project:" + dropDownProject);
							monitor.worked(5);
							// Get root path
							String rootPath = projectSelection.getLocation().toString();

							monitor.setTaskName("Fetching project rootpath:");
							monitor.worked(5);
							monitor.setTaskName("Fetching project rootpath:" + rootPath);

							SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 90);
							
							try {
								subMonitor.beginTask("Fetching Artifacts...", artifactPaths.size());
								for (int index = 0; index < artifactPaths.size(); index++) {
									String artifactPath = artifactPaths.get(index);
									String artifactExtn = artifactExtns.get(index);
									String operation = operations.get(index);

									subMonitor.setTaskName("Fetching Artifact..." + artifactPath);

									ArtifactOperation artifactOperation = ArtifactOperation.get(operation);
									artifactOperations.add(artifactOperation);

									// Make remote call
									try {
										subMonitor.setTaskName("Saving Artifact..." + artifactPath);
										Artifact fetchedartifact = null;
										if (!(artifactOperation == ArtifactOperation.DELETE) || isRevert()){
											fetchedartifact = ArtifactsManagerUtils.getArtifactContents(selectedUrl, 
													loggedInUsername, dropDownProject,
													artifactPath,
													artifactExtn);
											ArtifactsManagerUtils.saveContents(localProject,
													rootPath, fetchedartifact);
											fetchedartifacts.add(fetchedartifact);
											
											// case of reverting deleted artifacts
											if (artifactOperation == ArtifactOperation.DELETE && isRevert()) artifactOperation = ArtifactOperation.ADD;
										} else {
											fetchedartifact = ArtifactsFactory.eINSTANCE.createArtifact();
											fetchedartifact.setArtifactPath(artifactPath);
											fetchedartifact.setArtifactExtension(artifactExtn);
											
											ArtifactsManagerUtils.deleteArtifact(localProject, rootPath, artifactPath + "." + artifactExtn);
										}
										
										fireRemoteArtifactEvent(projectSelection.getName(),
												fetchedartifact,
												artifactOperation);

										updateItems(fetchedartifact,
												artifactOperation, false,
												false, null);
									} catch (Exception e) {
										RMSUIPlugin.log(e);
										updateItems(null, null, true, false,
												"Update failed due to "
														+ e.getMessage());
									}
									subMonitor.worked(index);
								}
								//Fetch updated Access Control config file for DM
								if (Utils.isStandaloneDecisionManger()) {
									String aclFileContents = refreshAccessConfigFile(loggedInUsername, dropDownProject);
									saveAccessConfigFile(localProject, rootPath, aclFileContents);
								}
							} catch (Exception e) {
								RMSUIPlugin.log(e);
							} finally {
								subMonitor.done();
							}
							monitor.setTaskName("Upating Done..Wait..Workspace modifying");
							updateItems(null, null, false, true, "Completed..");
							monitor.setTaskName("Artifacts successfully updated");
							
							try {
								refreshResource(selectedResource, monitor);
							} catch (CoreException e) {
								RMSUIPlugin.log(e);
								MessageDialog.openError(getShell(), "REFRESH_RESOURCE", e.getMessage());
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
	
	

	/**
	 * 
	 */
	@Override
	protected void disposeComponents() {
		selectedUrl = urlCombo.getText();
		dropDownProject = projectsCombo.getText();
		//Dispose
		if (ckURLGroup != null && !ckURLGroup.isDisposed()) {
			ckURLGroup.dispose();
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() == getArtifactsButton) {
			//Clear previous entries
			artifactsTable.removeAll();
			populateArtifacts();
			
			//Selecting all Artifact by default
			handleSelectArtifacts(true);
			select.setSelection(true);
		}
		if (e.getSource() == projectsCombo) {
			if (projectsCombo.getText().isEmpty()) {
				getArtifactsButton.setEnabled(false);
			} else {
				getArtifactsButton.setEnabled(true);
			}
		}
		super.widgetSelected(e);
		sortColumns(e);
	} 
	
	protected void createDetailsItem(Artifact fetchedArtifact, 
			                         ArtifactOperation artifactOperation) {
		if (detailsTable == null || detailsTable.isDisposed()) {
			return;
		}
		if (fetchedArtifact == null || artifactOperation == null) {
			return;
		}
		TableItem detailsItem = new TableItem(detailsTable, SWT.NONE);
		
		switch (artifactOperation) {
		case ADD :
			detailsItem.setText(0, "Adding " + fetchedArtifact.getArtifactPath());
			detailsItem.setForeground(new Color(null, 0, 0, 255));
			break;
		case MODIFY :
			detailsItem.setText(0, getModifyOperationText() + fetchedArtifact.getArtifactPath());
			detailsItem.setForeground(new Color(null, 153, 51, 0));
			break;	
		case DELETE :
			detailsItem.setText(0, "Deleting " + fetchedArtifact.getArtifactPath());
			detailsItem.setForeground(new Color(null, 255, 0, 0));
			break;		
		}
	}
	
	protected String getModifyOperationText(){
		return "Updating ";
	}

	/**
	 * @param project
	 * @param fetchedArtifact
	 * @param artifactRemoteOperation
	 */
	protected void fireRemoteArtifactEvent(String project, 
			                               Artifact fetchedArtifact,
			                               ArtifactOperation artifactRemoteOperation) {
		//Send out the event
		ArtifactsSummaryChangeListener summaryChangeListener = RMSArtifactsSummaryManager.getInstance().getSummaryChangeListener();
		
		int deltaKind = 0;
		switch (artifactRemoteOperation) {
		
		case ADD :
			deltaKind = IRemoteResourceDelta.REMOTE_ADDED;
			break;
		case MODIFY :
			deltaKind = IRemoteResourceDelta.REMOTE_MODIFIED;
			break;
		case DELETE :
			deltaKind = IRemoteResourceDelta.REMOTE_REMOVED;
			break;
		}
		
		IRemoteResourceDelta delta = new RemoteResourceDelta(fetchedArtifact, project, deltaKind);
		IArtifactResourceChangeEvent resourceChangeEvent = new RemoteResourceChangeEvent(delta, delta);
		
		summaryChangeListener.resourceChanged(resourceChangeEvent);
	}
	
	/**
	 * Return the {@link ArtifactOperation} type once an artifact entry
	 * is fetched based on its information available locally.
	 * @param artifact
	 * @return
	 */
	private ArtifactOperation getOperationType(String project, Artifact artifact) {
		if (ArtifactsManagerUtils.isArtifactPresent(project, artifact.getArtifactPath())) {
			//Modify
			return ArtifactOperation.MODIFY;
		}
		return ArtifactOperation.ADD;
	}
	
	protected void refreshResource(IResource resource, IProgressMonitor monitor) throws CoreException {
		resource.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}
	
	protected String refreshAccessConfigFile(String loggedInUsername, String projectName) throws Exception {
		return (String)ArtifactsManagerClient.refreshAccessConfig(loggedInUsername, projectName, selectedUrl);
	}
	
	protected void saveAccessConfigFile(String projectName, String projectRootPath, String fileContents) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(fileContents.getBytes("UTF-8"));
		ACLManager aclManager = new ACLManagerImpl();
		aclManager.load(bis, false, null);
		ACL acl = aclManager.getConfiguredACL();
		//Write this to file
		String aclFilePath = projectRootPath + File.separator + projectName + ".ac";
		FileOutputStream fos = new FileOutputStream(aclFilePath);
		ACLUtils.writeACL(acl, fos, ACLConstants.ENCRYPT_MODE, null);
		
	}

	/**
	 * @return the getArtifactsButton
	 */
	public Button getArtifactsButton() {
		return getArtifactsButton;
	}
	
	public boolean isRevert() {
		return false;
	}
}