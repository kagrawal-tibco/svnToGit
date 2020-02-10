/**
 * 
 */
package com.tibco.cep.studio.rms.client.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.ArtifactAuditTrailRecord;
import com.tibco.cep.studio.rms.model.ArtifactReviewTask;
import com.tibco.cep.studio.rms.model.ArtifactReviewerMetadata;
import com.tibco.cep.studio.rms.model.ArtifactStatusChangeDetails;
import com.tibco.cep.studio.rms.model.ArtifactStatusChangeMetadata;
import com.tibco.cep.studio.rms.model.ArtifactWorkflowInfo;
import com.tibco.cep.studio.rms.model.CommittedArtifactDetails;
import com.tibco.cep.studio.rms.model.event.IWorklistModelChangeListener;
import com.tibco.cep.studio.rms.model.event.ModelChangeEvent;
import com.tibco.cep.studio.rms.model.event.ModelChangeEvent.Features;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

/**
 * Handle detail information of checked in artifact as well as
 * submits for the same.
 * 
 *
 */
public class CommittedArtifactDetailsPage extends AbstractClientDetailsPage implements IWorklistModelChangeListener {
	
	private static final String COMMITTED_ARTIFACT_DETAILS = "CommittedArtifactDetails";
	
	private static final String REVEIWER_PROPERTY = "reveiwer";
	
	private static final String ROLE_PROPERTY = "role";
	
	private static final String COMMENTS_PROPERTY = "Reviewer comments";
	
	private static final  String OLD_STATUS_PROPERTY = "oldStatus";
	
	private static final  String NEW_STATUS_PROPERTY = "newStatus";
	
	private static final  String TIME_PROPERTY = "time";
	
	/**
	 * Path of the checked in artifact
	 */
	protected Text resourceText;
	
	/**
	 * Type of the artifact
	 */
	protected Text typeText;
	
		
	/**
	 * Current status of checked in artifact
	 */
	protected Text statusText;
	
	/**
	 * List of applicable stages
	 */
	protected Combo actionCombo;
	
	/**
	 * Reviewer's comments
	 */
	protected Text approverCommentsText;
	
	/**
	 * Message from server
	 */
	protected Text messageText;
	
	private Section auditTrailSection;
	
	private TableViewer auditTrailViewer;
	
	/**
	 * The model element for this page
	 */
	private CommittedArtifactDetails committedArtifactDetails;
	
	/**
	 * Parent for the page's model
	 */
	private ArtifactReviewTask currentParent;
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		try {
			committedArtifactDetails = null;
			IStructuredSelection sel = (IStructuredSelection)selection;
			if (sel.size() == 1) {
				Object selected = sel.getFirstElement();
				if (selected instanceof CommittedArtifactDetails) {
					committedArtifactDetails = (CommittedArtifactDetails)selected;
					//Change this also
					currentParent = committedArtifactDetails.getParent();
					committedArtifactDetails.addWorklistModelChangeListener(this);
					committedArtifactDetails.addWorklistModelChangeListener((WorklistLabelProvider)clientWorklistFormViewer.getLabelProvider());
					//Collapse audit trail section
//					if (auditTrailSection.isExpanded()) {
						//Also flush the viewer
						auditTrailViewer.setInput(null);
//						auditTrailSection.setExpanded(false);
//					}
					populateAuditTrail();	
					submitButton.setEnabled(committedArtifactDetails.isChanged());
					worklistDetailsPageBook.showPage(COMMITTED_ARTIFACT_DETAILS);
					refreshDetails();
				}
			}
		} catch(Exception e) {
			RMSUIPlugin.log(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	@Override
	public void modifyText(ModifyEvent e) {
		//Only modifications for this page should come
		if (e.getSource() == approverCommentsText) {
			String reviewerComments = approverCommentsText.getText();
			if (reviewerComments != null && !reviewerComments.isEmpty()) {
				committedArtifactDetails.setVolatileReviewerComments(reviewerComments);
				clientWorklistFormViewer.refreshClientTaskViewer();
			}
		}
		if (e.getSource() == actionCombo) {
			String actionText = actionCombo.getText();
			if (actionText != null && !actionText.isEmpty()) {
				if (!committedArtifactDetails.getStatus().equals(actionText)) {
					committedArtifactDetails.setChanged(true);
					//Set changed status here
					committedArtifactDetails.setVolatileStatus(actionText);
					currentParent.setChanged(true);
					clientWorklistFormViewer.refreshClientTaskViewer();
				}
			}
		}
	}
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.model.event.IWorklistModelChangeListener#modelChanged(com.tibco.cep.studio.rms.model.event.ModelChangeEvent)
	 */
	public void modelChanged(ModelChangeEvent modelChangeEvent) {
		Features feature = modelChangeEvent.getFeature();
		
		switch (feature) {
		case STATUS_CHANGE :
			boolean changed = committedArtifactDetails.isChanged();
			submitButton.setEnabled(changed);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() == submitButton) {
			//Single one
			List<ArtifactStatusChangeDetails> detailsToSubmit = getDetailsToSubmit(currentParent);
			boolean success = true;
			try {
				executeSubmit(detailsToSubmit);
				//Refresh details
				refreshDetails();
				//Reset changed status after successful submit
				committedArtifactDetails.setChanged(false);
			} catch (Exception re) {
				success = false;
				RMSUIPlugin.log(re);
			}
			clientWorklistFormViewer.refreshClientTaskViewer();
			
			if (success) {
				MessageDialog.openInformation( Display.getDefault().getActiveShell(), "Submit Artifact", "Artifacts submitted successfully.");
				//refresh Audit Trail viewer
				populateAuditTrail();
			}
		}
	}
	
	/**
	 * @param request
	 * @param root
	 */
	private void refreshDetails() throws Exception {
		String baseURL = RMSUtil.buildRMSURL();
		String loggedInUserName = AuthTokenUtils.getLoggedinUser();
		CommittedArtifactDetails refreshedDetails = 
			ArtifactsManagerClient.refreshCommittedArtifactDetails(baseURL, loggedInUserName,
					currentParent.getTaskId(),
					committedArtifactDetails.getArtifact().getArtifactPath());
		refreshedDetails.getModelListeners().addAll(committedArtifactDetails.getModelListeners());
		refreshedDetails.setParent(committedArtifactDetails.getParent());
		committedArtifactDetails = refreshedDetails;
		update();
	}
	
	private List<ArtifactStatusChangeDetails> getDetailsToSubmit(ArtifactReviewTask changedTask) {
		List<ArtifactStatusChangeMetadata> statusChangeMetadatas = new ArrayList<ArtifactStatusChangeMetadata>(1);

		List<ArtifactStatusChangeDetails> allChangedDetails = new ArrayList<ArtifactStatusChangeDetails>(1);

		Artifact artifact = committedArtifactDetails.getArtifact();
		String loggedInUser = AuthTokenUtils.getLoggedinUser();
		String loggedInUserRolesCumulative = RMSUtil.getLoggedInUserRolesCumulative();

		ArtifactReviewerMetadata reviewerMetadata = new ArtifactReviewerMetadata(
				loggedInUser, loggedInUserRolesCumulative, committedArtifactDetails.getVolatileStatus(),
				committedArtifactDetails.getStatus(), committedArtifactDetails.getVolatileReviewerComments());
		ArtifactStatusChangeMetadata statusChangeMetadata = new ArtifactStatusChangeMetadata(
				artifact, committedArtifactDetails.getArtifactOperation(),
				reviewerMetadata);
		statusChangeMetadatas.add(statusChangeMetadata);

		ArtifactStatusChangeDetails statusChangeDetails = new ArtifactStatusChangeDetails(
				Long.parseLong(changedTask.getTaskId()), changedTask
						.getPatternId(), changedTask.getProjectName(),
				statusChangeMetadatas);

		allChangedDetails.add(statusChangeDetails);

		return allChangedDetails;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CommittedArtifactDetailsPage)) {
			return false;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.client.ui.AbstractClientDetailsPage#createDetailsPage(org.eclipse.ui.forms.widgets.FormToolkit)
	 */
	@Override
	protected void createDetailsPage(FormToolkit toolkit) {
		final Composite composite = worklistDetailsPageBook.createPage(COMMITTED_ARTIFACT_DETAILS);
		
		composite.setLayout(new GridLayout(1, false));
		
		Composite root = toolkit.createComposite(composite);
		root.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		root.setLayoutData(gd);
		
		toolkit.createLabel(root, "Resource:");
		resourceText = toolkit.createText(root, "", SWT.READ_ONLY);
		resourceText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		toolkit.createLabel(root, "Type:");
		typeText = toolkit.createText(root, "", SWT.READ_ONLY);
		typeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		toolkit.createLabel(root, "Status:");
		statusText = toolkit.createText(root, "", SWT.READ_ONLY);
		statusText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		toolkit.createLabel(root, "Action:");
		actionCombo = new Combo(root, SWT.READ_ONLY);
		actionCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		actionCombo.addModifyListener(this);
		
		toolkit.createLabel(root, "Reviewer Comments:").setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		approverCommentsText = toolkit.createText(root, "",SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 50;
		approverCommentsText.setLayoutData(gd);
		approverCommentsText.addModifyListener(this);
		
//		toolkit.createLabel(root, "Message:").setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
//		messageText = toolkit.createText(root, "",SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
//		
//		gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.heightHint = 50;
//		messageText.setLayoutData(gd);
		
		createButtonBar(toolkit, composite);
		
		//Audit Trail
		createAuditTrailComposite(toolkit, composite);
		
	}
	

	/**
	 * @param toolkit
	 * @param composite
	 */
	protected void createAuditTrailComposite(FormToolkit toolkit, final Composite composite) {
		auditTrailSection = toolkit.createSection(composite, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		auditTrailSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		auditTrailSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		auditTrailSection.setText("Audit Trail");
		auditTrailSection.setFont(new org.eclipse.swt.graphics.Font(Display.getCurrent(), new FontData("Tahoma", 8, SWT.BOLD)));
		auditTrailSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if (e.getState() == false) {
					auditTrailViewer.setInput(null);
					auditTrailViewer.refresh();
				} else {
					composite.layout();
					populateAuditTrail();
				}
			}
		});

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		auditTrailSection.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		auditTrailSection.setLayoutData(gd);

		Composite sectionClient = toolkit.createComposite(auditTrailSection);
		auditTrailSection.setClient(sectionClient);
		sectionClient.setLayout(new GridLayout(1, false));
		sectionClient.setLayoutData(new GridData(GridData.FILL_BOTH));

		Table table = toolkit.createTable(sectionClient, SWT.FULL_SELECTION | SWT.MULTI);
		table.setLinesVisible(true);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 200;
		table.setLayoutData(gd);
		table.setHeaderVisible(true);

		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText("Reveiwer");
		column.setMoveable(false);
		column.setResizable(true);
		
		column = new TableColumn(table, SWT.NONE);
		column.setText("Role");
		column.setMoveable(false);
		column.setResizable(true);

		column = new TableColumn(table, SWT.NONE);
		column.setText("Reviewer Comments");
		column.setMoveable(false);
		column.setResizable(true);
		
		column = new TableColumn(table, SWT.NONE);
		column.setText("Old Status");
		column.setMoveable(false);
		column.setResizable(true);
		
		column = new TableColumn(table, SWT.NONE);
		column.setText("New Status");
		column.setMoveable(false);
		column.setResizable(true);
		
		column = new TableColumn(table, SWT.NONE);
		column.setText("Time");
		column.setMoveable(false);
		column.setResizable(true);

		auditTrailViewer = new TableViewer(table);
		auditTrailViewer.setLabelProvider(new ClientDetailsViewerLabelProvider(false));
		auditTrailViewer.setContentProvider(new ClientDetailsViewerContentProvider());
		auditTrailViewer.setCellModifier(new ClientDetailsViewerCellModifier(auditTrailViewer));
		auditTrailViewer.setCellEditors(new CellEditor[] { null, null, new TextCellEditor(table), null, null, null});
		auditTrailViewer.setColumnProperties(new String[] {REVEIWER_PROPERTY ,
						                                  ROLE_PROPERTY,
						                                  COMMENTS_PROPERTY, 
						                                  OLD_STATUS_PROPERTY, 
						                                  NEW_STATUS_PROPERTY, 
						                                  TIME_PROPERTY });
		auditTrailViewer.addSelectionChangedListener(this);
		auditTrailViewer.getTable().setLinesVisible(false);
		//TableEditor tableEditor = RMSUIUtils.createTableViewer(auditTrailViewer, true, false); 

		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(table);
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		
		table.setLayout(autoTableLayout);
	}

	/**
	 * @param clientTaskViewer
	 */
	public CommittedArtifactDetailsPage(RMSClientWorklistFormViewer clientWorklistFormViewer) {
		super(clientWorklistFormViewer);
	}

	/**
	 * Update the UI components upon opening the page
	 */
	@Override
	protected void update() {
		Artifact artifact = committedArtifactDetails.getArtifact();
		resourceText.setText(artifact.getArtifactPath());
		typeText.setText(committedArtifactDetails.getArtifactOperation().getLiteral());
//		versionText.setText(string);
		statusText.setText(committedArtifactDetails.getStatus());
		statusText.setEditable(false);
		//Get applicable stages
		String[] stages = getApplicableStages();
		actionCombo.setItems(stages);
		actionCombo.setEnabled(stages.length > 0);
		String reviewerComments = 
			(committedArtifactDetails.getVolatileReviewerComments() == null) ? "" : committedArtifactDetails.getVolatileReviewerComments();
		approverCommentsText.setText(reviewerComments);
//		messageText.setText(string);
		
	}
	
	private String[] getApplicableStages() {
		if (currentParent == null) {
			currentParent = committedArtifactDetails.getParent();
		}
		superParent = currentParent.getParent();
		ArtifactWorkflowInfo workflowInfo = committedArtifactDetails.getWorkflowInfo();
		List<String> stagesList = workflowInfo.getStages();
		String[] stages = 
			stagesList.toArray(new String[stagesList.size()]);
		return stages;
	}
	
	protected void populateAuditTrail() {
		try {
			List<ArtifactAuditTrailRecord> auditTrailHistory = getAuditTrailHistory();
			for (ArtifactAuditTrailRecord auditTrailRecord : auditTrailHistory) {
				AuditTrailItem auditTrailItem = getAuditTrailItem(auditTrailRecord);
				auditTrailViewer.add(auditTrailItem);
			}
		} catch(Exception e) {
			RMSUIPlugin.log(e);
		}
	}
	
	private List<ArtifactAuditTrailRecord> getAuditTrailHistory() {
		try {
			return ArtifactsManagerClient.fetchAuditTrail(null, 
					 currentParent.getPatternId(), committedArtifactDetails.getArtifact().getArtifactPath());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private AuditTrailItem getAuditTrailItem(ArtifactAuditTrailRecord auditTrailRecord) {
		return new AuditTrailItem(auditTrailRecord.getReviewerUsername(), 
				                  auditTrailRecord.getReviewerRole(),
				                  auditTrailRecord.getReviewerComments(),
				                  auditTrailRecord.getOldStatus(),
				                  auditTrailRecord.getNewStatus(),
				                  "");
				                  
	}
}
