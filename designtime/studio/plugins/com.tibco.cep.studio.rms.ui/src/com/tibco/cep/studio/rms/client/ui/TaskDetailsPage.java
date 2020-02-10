/**
 * 
 */
package com.tibco.cep.studio.rms.client.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.ArtifactReviewTask;
import com.tibco.cep.studio.rms.model.ArtifactReviewTaskSummary;
import com.tibco.cep.studio.rms.response.impl.WorkitemDelegationResponse.DelegationStatus;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

/**
 * Handle details information at task level along with submits for the same
 * 
 *
 */
public class TaskDetailsPage extends AbstractClientDetailsPage {
	
	private static final String ARTIFACT_REVIEW_TASK = "ArtifactReviewTask";
	
	private static final String NO_ROLE_TABLE = "NoRoleTable";
	
	private static final String WITH_ROLE_TABLE = "WithRoleTable";
	
	/**
	 * Request id/Revision id/Task Id
	 */
	protected Text requestIdText;
	
	/**
	 * Username making the checkin
	 */
	protected Text userNameText;
	
	/**
	 * Name of the project
	 */
	protected Text projectText;
	
	/**
	 * Time of checkin
	 */
	protected Text checkinTimeText;
	
	/**
	 * Checkin Comments
	 */
	protected Text submitterCommentsText;
	
	private Button roleDelegateButton;
	
	private Table rolesTable;
	
	private boolean rolesShown = false;
	
	/**
	 * The model element for this page
	 * It should be the same instance as of @see {@link CommittedArtifactDetailsPage}#currentParent
	 */
	private ArtifactReviewTask artifactReviewTask;
	
	/**
	 * @param root
	 * @param toolkit
	 */
	private void createDelegateToPage(Composite root, FormToolkit toolkit) {
		Composite childContainer = toolkit.createComposite(root, SWT.NONE);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 170;
		childContainer.setLayoutData( data);
		childContainer.setLayout(new FillLayout());
		delegateToPage = toolkit.createPageBook(childContainer, SWT.NONE);
    	CreateRuleTablePage();
		CreateNoRuleTablePage(toolkit);
	}
	
	/**
	 * @param toolkit
	 */
	private void CreateNoRuleTablePage(FormToolkit toolkit) {
		Composite noRoleRoot = delegateToPage.createPage(NO_ROLE_TABLE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		noRoleRoot.setLayout(layout);
		GridData data =  new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
		noRoleRoot.setLayoutData(data);
		
		secSubmitButton = toolkit.createButton(noRoleRoot, "Submit", SWT.NONE);
		data = new GridData(SWT.RIGHT, SWT.TOP, true, true, 1, 1);
		data.widthHint = 80;
		data.heightHint = 25;
		secSubmitButton.setLayoutData(data);
		secSubmitButton.setEnabled(false);
		secSubmitButton.addSelectionListener(this);
	}
	
	private void CreateRuleTablePage() {
		Composite withRoleRoot = delegateToPage.createPage(WITH_ROLE_TABLE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		withRoleRoot.setLayout(layout);
		createRolesTable(withRoleRoot);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		try {
			IStructuredSelection sel = (IStructuredSelection)selection;
			if (sel.size() == 1) {
				Object selected = sel.getFirstElement();
				if (selected instanceof ArtifactReviewTask) {
					artifactReviewTask = (ArtifactReviewTask)selected;
					worklistDetailsPageBook.showPage(ARTIFACT_REVIEW_TASK);
					ArtifactReviewTaskSummary taskSummary = artifactReviewTask.getParent();
					String[] roles = taskSummary.getDelegationRoles();
					//Clear table of any existing roles
					TableItem[] existingTableItems = rolesTable.getItems();
					for (TableItem tableItem : existingTableItems) {
						tableItem.dispose();
					}
					if (roles != null) {
						for (String role : roles) {
							createItem(role);
						}
					}
					//If it is changed enable button
					if (artifactReviewTask.isChanged()) {
						submitButton.setEnabled(true);
					}
					update();
				}
			}
		} catch (Exception e) {
			RMSUIPlugin.log(e);
		}
	}
	
	protected Table createRolesTable(Composite parent) {
		rolesTable = new Table(parent, SWT.CHECK | SWT.BORDER | SWT.SINGLE);
		rolesTable.setLayout(new GridLayout());
		rolesTable.addSelectionListener(this);

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 138;
		
		rolesTable.setLayoutData(data);
		rolesTable.setFont(new Font(Display.getDefault(), "Tahoma", 10, SWT.NORMAL));
	
		rolesTable.setLinesVisible(true);
		rolesTable.setHeaderVisible(true);
		
		rolesTable.addSelectionListener(this);
		
		TableColumn resPathColumn = new TableColumn(rolesTable, SWT.NULL);
		resPathColumn.setText("Roles");
		
		autoTableLayout();
		
		return rolesTable;
	}
	
	protected void autoTableLayout() {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(rolesTable);
		for (int loop = 0; loop < rolesTable.getColumns().length; loop++) {
			autoTableLayout.addColumnData(new ColumnWeightData(1));
		}
		rolesTable.setLayout(autoTableLayout);
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.client.ui.AbstractClientDetailsPage#createDetailsPage(org.eclipse.ui.forms.widgets.FormToolkit)
	 */
	@Override
	protected void createDetailsPage(FormToolkit toolkit) {
		Composite root = worklistDetailsPageBook.createPage(ARTIFACT_REVIEW_TASK);
		root.setLayout(new GridLayout(2, false));
		
		toolkit.createLabel(root, "ID:");
		requestIdText = toolkit.createText(root, "", SWT.READ_ONLY);
		requestIdText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		toolkit.createLabel(root, "User:");
		userNameText = toolkit.createText(root, "", SWT.READ_ONLY);
		userNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		toolkit.createLabel(root, "Project:");
		projectText = toolkit.createText(root, "", SWT.READ_ONLY);
		projectText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		toolkit.createLabel(root, "Check-in Time:");
		checkinTimeText = toolkit.createText(root, "", SWT.READ_ONLY);
		checkinTimeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		toolkit.createLabel(root, "Submitter Comments:").setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		submitterCommentsText = toolkit.createText(root, "",SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		submitterCommentsText.addModifyListener(this);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 100;
		submitterCommentsText.setLayoutData(gd);

		roleDelegateButton = toolkit.createButton(root, "Delegate To >>", SWT.PUSH);
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gd.heightHint = 25;
		roleDelegateButton.setLayoutData(gd);
		roleDelegateButton.addSelectionListener(this);
		
		createDelegateToPage(root, toolkit);
		
		toolkit.createLabel(root, "");
		createButtonBar(toolkit, root);
		
		delegateToPage.showPage(NO_ROLE_TABLE);
	}

	/**
	 * 
	 */
	public TaskDetailsPage(RMSClientWorklistFormViewer clientWorklistFormViewer) {
		super(clientWorklistFormViewer);
	}

	/**
	 * Update the UI components upon opening the page
	 */
	@Override
	protected void update() {
		requestIdText.setText(artifactReviewTask.getTaskId());
		userNameText.setText(artifactReviewTask.getUsername());
		projectText.setText(artifactReviewTask.getProjectName());
		checkinTimeText.setText(artifactReviewTask.getCheckinTime().toString());
		String checkinComments = artifactReviewTask.getCheckinComments();
		if (checkinComments != null) {
			submitterCommentsText.setText(checkinComments);
		}
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	@Override
	public void modifyText(ModifyEvent e) {
		if (e.getSource() == submitterCommentsText) {
				//TODO
		}
		
	}
	
	/**
	 * 
	 * @param delegationRoles
	 * @return
	 */
	private Object executeWorkitemDelegation(List<String> delegationRoles) {
		try {
			return ArtifactsManagerClient.delegateWorkItem(RMSUtil.buildRMSURL(), 
					                                       artifactReviewTask.getTaskId(), 
					                                       delegationRoles.toArray(new String[delegationRoles.size()]));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		//Submit button at task level
		if (e.getSource() == submitButton || e.getSource() == secSubmitButton) {
			if (artifactReviewTask.isChanged()) {
				//Single one
				List<ArtifactReviewTask> changedTasks = new ArrayList<ArtifactReviewTask>(1);
				changedTasks.add(artifactReviewTask);
				
				//Getting the delegated roles
				List<String> delegationRoles = getDelegatedToRoles();
				Object response = executeWorkitemDelegation(delegationRoles);
				if (response == DelegationStatus.SUCCESS) {
//					MessageDialog.openInformation(this.managedForm., title, message)
					clientWorklistFormViewer.fetchTasks();
					clientWorklistFormViewer.refreshClientTaskViewer();
				}
			}
		} else if (e.getSource() == roleDelegateButton) {
			rolesShown = !rolesShown;
			roleDelegateButton.setText(rolesShown ? "Delegate To <<" : "Delegate To >>");
			rolesTable.setVisible(rolesShown);
			secSubmitButton.setVisible(!rolesShown);
			submitButton.setVisible(rolesShown);
			delegateToPage.showPage(rolesShown ? WITH_ROLE_TABLE : NO_ROLE_TABLE);
		} else if (e.item instanceof TableItem) {
			TableItem item = (TableItem)e.item;
			submitButton.setEnabled(item.getChecked());
			secSubmitButton.setEnabled(item.getChecked());
			artifactReviewTask.setChanged(item.getChecked());
		}
	}
	
	/**
	 * @param role
	 */
	protected void createItem(String role) {
		if (rolesTable == null || rolesTable.isDisposed()) {
			return;
		}
		TableItem item = new TableItem(rolesTable, SWT.CENTER);
		item.setText(0, role);
		rolesTable.redraw();
	}
	
	/**
	 * @return
	 */
	protected List<String> getDelegatedToRoles() {
		List<String> roles = new ArrayList<String>();
		if (rolesTable != null && !rolesTable.isDisposed()) {
			for (TableItem item : rolesTable.getItems()) {
				if (item.getChecked()) {
					roles.add(item.getText());
				}
			}
		}
		return roles;
	}
}
