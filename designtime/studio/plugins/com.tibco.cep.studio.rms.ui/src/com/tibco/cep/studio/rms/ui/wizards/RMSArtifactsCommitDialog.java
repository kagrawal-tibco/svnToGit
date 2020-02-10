package com.tibco.cep.studio.rms.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.artifacts.RMSRepo;
import com.tibco.cep.studio.rms.artifacts.manager.IArtifactResourceChangeEvent;
import com.tibco.cep.studio.rms.artifacts.manager.IRemoteResourceDelta;
import com.tibco.cep.studio.rms.artifacts.manager.impl.ArtifactsSummaryChangeListener;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RemoteResourceChangeEvent;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RemoteResourceDelta;
import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils;
import com.tibco.cep.studio.rms.model.ArtifactCommitCompletionMetaData;
import com.tibco.cep.studio.rms.model.ArtifactCommitMetaData;
import com.tibco.cep.studio.rms.model.Error;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * @author aathalye
 * @author sasahoo
 *
 */
public class RMSArtifactsCommitDialog extends AbstractRMSArtifactsDialog {
	
	//The selected project in explorer
	private IProject explorerProject;
	
	private ArtifactSummaryEntry[] artifactsToCommit;
	
	private String loggedinUser;
	
	/**
	 * Only available is a project was first checkedout from RMS
	 */
	private String checkinURL;
	
	public RMSArtifactsCommitDialog getContainer(){
		return this;
	}
	
	/**
	 * @param parent
	 * @param title
	 * @param resource
	 * @param artifactsToCommit
	 */
	public RMSArtifactsCommitDialog(Shell parent, 
			                        String title,
			                        IResource resource,
			                        ArtifactSummaryEntry[] artifactsToCommit) {
		super(parent, title, true, false);
				
		this.artifactsToCommit = artifactsToCommit;
		
		this.explorerProject = resource.getProject();
		
		loggedinUser = AuthTokenUtils.getToken().getAuthen().getUser().getUsername();
		
		setWidth(100);
		
		setNeedsProgressMonitor(true);
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
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#createProjectSelectionArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createProjectSelectionArea(Composite parent) {
	}
	
	protected void createDialogAndButtonArea(Composite parent) {
		super.createDialogAndButtonArea(parent);
		
		populateArtifacts();
		
		//Selecting all Artifact by default
		handleSelectArtifacts(true);
		select.setSelection(true);
		if (checkedResources.size() > 0) {
			okButton.setEnabled(true);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#populateArtifacts()
	 */
	protected void populateArtifacts() {
		//In this case selected project will be the same as the one from checkout
		RMSRepo rmsRepo = RMSArtifactsSummaryManager.getInstance().getRMSRepoInfo(explorerProject.getName());
		if (rmsRepo == null) {
			//TODO error dialog
		} else {
			selectedProject = rmsRepo.getRmsProject();
			checkinURL = rmsRepo.getRepoURL();
			for (ArtifactSummaryEntry artifactSummaryEntry : artifactsToCommit) {
				//The operation is part of the artifact itself
				createArtifactsItem(artifactSummaryEntry.getArtifact(), 
						            artifactSummaryEntry.getOperationType());			
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		super.widgetSelected(e);
		sortColumns(e);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#performOk()
	 */
	@Override
	protected boolean performOk() {
		if (artifactOperations.size() > 0 && fetchedartifacts.size() > 0) {
			cancelPressed();
			return false;
		}
		
		Set<TableItem> selectedTableItems = checkedResources;
		if (checkedResources.size() == 0) {
			cancelPressed();
			return false;
		}
		
		for (TableItem tableItem : selectedTableItems) {
			artifactPaths.add(tableItem.getText(0));
			artifactExtns.add(tableItem.getText(2));
			operations.add(tableItem.getText(3));
		}

		showDetailsArea();

		artifactOperations.clear();
		fetchedartifacts.clear();
		if (needsProgressMonitor) {
			progressMonitorPart.setVisible(true);
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		if (!performOk()) {
			return;
		}
		getShell().setText(title);
		final String loggedInUsername = AuthTokenUtils.getLoggedinUser();
		//Get project selection
		if (needsProgressMonitor) {
			detailsTable.removeAll();
			StudioUIUtils.invokeOnDisplayThread(new Runnable() {
				public void run() {
					IRunnableWithProgress op = new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							
							monitor.beginTask("Commit Artifacts started", 100);
							monitor.setTaskName("Generating Pattern Id");
							String patternId = ArtifactsManagerUtils.generatePatternId(loggedInUsername);
							monitor.worked(10);
							List<Artifact> artifactsToCommitList = new ArrayList<Artifact>();

							SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 40);
							try {
								boolean isCheckInError = false;
								try {
									subMonitor.beginTask("Fetching Artifacts...",artifactPaths.size());
									
									for (int index = 0; index < artifactPaths.size(); index++) {	
										String artifactPath = artifactPaths.get(index);
										String artifactExtn = artifactExtns.get(index);
										String operation = operations.get(index);
	
										RMSArtifactsSummaryManager summaryManager = RMSArtifactsSummaryManager.getInstance();
										ArtifactSummaryEntry summaryEntry = summaryManager.getSummaryEntry(selectedProject, 
																										   artifactPath, 
																										   null, 
																										   true,
																										   true,
																										   true);
										Date updateTime = null;
										String lastCommitVersion = null;
										if (summaryEntry != null && summaryEntry.getArtifact() != null) {
											updateTime = summaryEntry.getArtifact().getUpdateTime();
											lastCommitVersion = summaryEntry.getArtifact().getCommittedVersion();
										}
										
										subMonitor.setTaskName("Fetching Artifacts..." + artifactPath);
	
										final ArtifactOperation artifactOperation = ArtifactOperation.get(operation);
										artifactOperations.add(artifactOperation);
	
										final Artifact artifact = ArtifactsFactory.eINSTANCE.createArtifact();
										// Load contents
										artifact.setArtifactPath(artifactPath);
										artifact.setArtifactExtension(artifactExtn);
										artifact.setUpdateTime(updateTime);
										artifact.setCommittedVersion(lastCommitVersion);
										artifactsToCommitList.add(artifact);
	
										// Do not send contents for deleted
										// artifacts
										if (!(ArtifactOperation.DELETE == artifactOperation)) {
											String contents = 
												ArtifactsManagerUtils.getEncodedArtifactContents(selectedProject, explorerProject, artifact);
											artifact.setArtifactContent(contents);
										}
	
										ArtifactCommitMetaData artifactCommitMetaData = new ArtifactCommitMetaData();
										artifactCommitMetaData.setArtifact(artifact);
										artifactCommitMetaData.setPatternId(patternId);
										artifactCommitMetaData.setArtifactOperation(artifactOperation);
										Object response = ArtifactsManagerClient.checkinArtifact(loggedInUsername,
												selectedProject, checkinURL, artifactCommitMetaData);
										fetchedartifacts.add(artifact);
										
										isCheckInError = (response instanceof Error) ? true : false;
										String message = (response instanceof Error) ? ((Error)response).getErrorString() : ((Artifact)response).getArtifactPath();
										updateItems(artifact, artifactOperation, isCheckInError, !isCheckInError, message);

										if (isCheckInError)
											break;
										
										artifact.setCommittedVersion(((Artifact)response).getCommittedVersion());
										subMonitor.worked(index);
									}
								} finally {
									subMonitor.done();
								} 	
								if (!isCheckInError) {
									try {
										monitor.setTaskName("Committing Done..Wait..Getting server response");
			
										ArtifactCommitCompletionMetaData artifactCompletionCommitMetaData = new ArtifactCommitCompletionMetaData();
										artifactCompletionCommitMetaData.setPatternId(patternId);
										artifactCompletionCommitMetaData.setCheckinComments(comments);
										artifactCompletionCommitMetaData.setArtifacts(artifactsToCommitList);
										artifactCompletionCommitMetaData.setUsername(loggedinUser);
										artifactCompletionCommitMetaData.setCheckinTime(new Date());
										Object response = ArtifactsManagerClient.completeCheckinArtifact(loggedInUsername,
															selectedProject,
															checkinURL,
															artifactCompletionCommitMetaData);
										if (response instanceof String) {
											// Successful commit
											fireCommitSuccessEvent(artifactCompletionCommitMetaData, explorerProject.getName());
										}
										for (Artifact artifact : artifactCompletionCommitMetaData.getArtifacts()) {
											getRespose(artifact);
										}
										createCompletionItem(response);
										monitor.worked(50);
										monitor.setTaskName("Artifacts successfully commited");
									} finally {
										monitor.done();										
									}
								}	
							} catch (Exception e) {
								RMSUIPlugin.log(e);
								updateItems(null, null, true, false, "Commit failed due to " + e.getMessage());
							}
						}
					};
					try {
						getContainer().run(true, true, op);
					} catch (InvocationTargetException e) {
						RMSUIPlugin.log(e);
						updateItems(null, null, true, false,
								"Commit failed due to " + e.getMessage());
					} catch (InterruptedException e) {
						RMSUIPlugin.log(e);
						updateItems(null, null, true, false,
								"Commit failed due to " + e.getMessage());
					}
				}
			}, false);
		}
	}
	
	/**
	 * @param artifactCommitCompletionMetaData
	 * @param project
	 */
	private void fireCommitSuccessEvent(ArtifactCommitCompletionMetaData artifactCommitCompletionMetaData,
			                            String project) {
		//Send out the event
		ArtifactsSummaryChangeListener summaryChangeListener = RMSArtifactsSummaryManager.getInstance().getSummaryChangeListener();
		
		for (Artifact artifact : artifactCommitCompletionMetaData.getArtifacts()) {
			IRemoteResourceDelta delta = new RemoteResourceDelta(artifact, project, RemoteResourceDelta.COMMIT);
			IArtifactResourceChangeEvent resourceChangeEvent = new RemoteResourceChangeEvent(delta, delta);
			summaryChangeListener.resourceChanged(resourceChangeEvent);
		}
	}
	
	/**
	 * @param fetchedArtifact
	 */
	private void getRespose(final Artifact fetchedArtifact) {
		StudioUIUtils.invokeOnDisplayThread(new Runnable() {
			public void run() {
				final TableItem detailsItem = new TableItem(detailsTable, SWT.NONE);
				detailsItem.setText(0, "Sending Content " + fetchedArtifact.getArtifactPath());
				detailsItem.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			}
		}, false);
	}
	
	/**
	 * TODO Automatically expand details section
	 * Create entry in the Details table for each artifact committed
	 * @param fetchedArtifact
	 * @param artifactOperation
	 */
	protected void createDetailsItem(Artifact fetchedArtifact,
			                       ArtifactOperation artifactOperation) {
		if (detailsTable == null || detailsTable.isDisposed()) {
			return;
		}
		TableItem detailsItem = new TableItem(detailsTable, SWT.NONE);

		switch (artifactOperation) {
		case ADD:
			detailsItem.setText(0, "Adding " + fetchedArtifact.getArtifactPath());
			detailsItem.setForeground(new Color(null, 0, 0, 255));
			break;
		case MODIFY:
			detailsItem.setText(0, "Modifying " + fetchedArtifact.getArtifactPath());
			detailsItem.setForeground(new Color(null, 153, 51, 0));
			break;
		case DELETE :
			detailsItem.setText(0, "Deleting " + fetchedArtifact.getArtifactPath());
			detailsItem.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_CYAN));
			break;		
		}
		detailsTable.redraw();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.AbstractRMSArtifactsDialog#disposeComponents
	 */
	@Override
	protected void disposeComponents() {
		if (commentsTextArea != null && !commentsTextArea.isDisposed()) {
			commentsTextArea.dispose();
		}
		if (commentsLabel != null && !commentsLabel.isDisposed()) {
			commentsLabel.dispose();
		}
		if (ckURLGroup != null && !ckURLGroup.isDisposed()) {
			ckURLGroup.dispose();
		}
	}

	/**
	 * @param responseObject
	 */
	private void createCompletionItem(Object responseObject) {
		if (responseObject instanceof String) {
			updateItems(null, null, false, true, "Commit Successful At " + (String)responseObject);
		} else if (responseObject instanceof Error) {
			updateItems(null, null, true, false, "Commit Failed due to " +  ((Error)responseObject).getErrorString());
		}
	}
}