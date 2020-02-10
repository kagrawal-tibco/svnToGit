package com.tibco.cep.studio.rms.client.ui;

import java.util.List;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactsType;
import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.ArtifactReviewTask;
import com.tibco.cep.studio.rms.model.ArtifactReviewTaskSummary;
import com.tibco.cep.studio.rms.model.CommittedArtifactDetails;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.rms.ui.actions.OpenReadOnlyArtifactEditorAction;
import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;

public class RMSClientWorklistFormViewer extends AbstractRMSClientFormViewer {
	
	private TreeViewer clientTaskViewer;
	
	private ClientDetailsPart clientDetailsPart;
	
	private SectionPart spart; 

	private Button refreshButton;
	
	private Button clearApprovedTasksButton;
	
	/**
	 * The root element
	 */
	private ArtifactReviewTaskSummary root;

	/**
	 * @param resultEditor
	 */
	public RMSClientWorklistFormViewer(RMSClientWorklistEditor resultEditor){
		this.editor = resultEditor;
	}

	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		super.createPartControl(container, "Worklist", RMSUIPlugin.getDefault().getImage("icons/worklist_editor.png"));
		super.createToolBarActions(); 
		sashForm.setWeights(new int[] {60,100}); 
	}

	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.NO_TITLE);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout flayout = new GridLayout(2, false);
		client.setLayout(flayout);
		toolkit.paintBordersFor(client);
		section.setClient(client);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		client.setLayoutData(gd);
		
		spart = new SectionPart(section);
		managedForm.addPart(spart);
		
		clientTaskViewer = new TreeViewer(client);
		clientTaskViewer.setContentProvider(new WorklistContentProvider(clientTaskViewer));
		clientTaskViewer.setLabelProvider(new WorklistLabelProvider(clientTaskViewer));
		clientTaskViewer.setUseHashlookup(true);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.FILL;
		clientTaskViewer.getControl().setLayoutData(gd);
		
		clientTaskViewer.setInput(getInitalInput());
		clientTaskViewer.getTree().addTreeListener(this);
		clientTaskViewer.addSelectionChangedListener(this);
		
		addContextMenu();
		
		Composite buttonsClient = toolkit.createComposite(client, SWT.WRAP);
		buttonsClient.setLayout(new GridLayout(1, false));
		buttonsClient.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		clearApprovedTasksButton = toolkit.createButton(buttonsClient, "Clear Approved Tasks", SWT.PUSH);
		clearApprovedTasksButton.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		clearApprovedTasksButton.addSelectionListener(this);
		
		refreshButton = toolkit.createButton(buttonsClient, "Refresh", SWT.PUSH);
		refreshButton.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		refreshButton.addSelectionListener(this);
	}
	
	public ArtifactReviewTaskSummary getInitalInput() {
		fetchTasks();
		return root;
	}
	
	/**
	 * @param detailsPart
	 */
	protected void registerPages(ClientDetailsPart detailsPart) {
		clientDetailsPart.registerPage(ArtifactReviewTask.class, new TaskDetailsPage(this));
		clientDetailsPart.registerPage(CommittedArtifactDetails.class, new CommittedArtifactDetailsPage(this));
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer#createDetailsPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	protected  void createDetailsPart(final IManagedForm managedForm, Composite parent) {
		clientDetailsPart = new ClientDetailsPart(managedForm, parent, SWT.NULL);
		managedForm.addPart(clientDetailsPart);
		registerPages(clientDetailsPart);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.TreeListener#treeExpanded(org.eclipse.swt.events.TreeEvent)
	 */
	@Override
	public void treeExpanded(TreeEvent e) {
		final TreeItem root = (TreeItem) e.item;
		if (root.getData() instanceof ArtifactReviewTask) {
			ArtifactReviewTask request = (ArtifactReviewTask)root.getData();
			if (request.getAllCommittedArtifacts() == null) {
				fetchDetails(request, root);
				return;
			}
			if (request.getAllCommittedArtifacts() != null && 
					request.getAllCommittedArtifacts().size() == 0) {
				fetchDetails(request, root);
			}
		}
	}
	
	/**
	 * @param request
	 * @param root
	 */
	private void fetchDetails(ArtifactReviewTask request, TreeItem root) {
		String baseURL = RMSUtil.buildRMSURL();
		//Get logged in user
		String loggedInUser = AuthTokenUtils.getLoggedinUser();
		try {
			List<CommittedArtifactDetails> allCommittedArtifacts = 
				ArtifactsManagerClient.fetchTaskDetails(baseURL, loggedInUser, request.getTaskId());
			request.setAllCommittedArtifacts(allCommittedArtifacts);
			for (CommittedArtifactDetails commitDetail : allCommittedArtifacts) {
				Artifact artifact = commitDetail.getArtifact();
				commitDetail.setParent(request);
				
				TreeItem item = new TreeItem(root, 0);
				item.setText(artifact.getArtifactPath());
				String artifactExtension = artifact.getArtifactExtension();
				ArtifactsType artifactsType = ArtifactsType.get(artifactExtension);
				item.setImage(RMSUIUtils.getArtifactImage(artifactsType));
				item.setData(commitDetail);
			}
		} catch (Exception e) {
			RMSUIPlugin.log(e);
		}
	}
	
	public void fetchTasks(){
		fetchTasks(false);
	}
	
	private void fetchTasks(boolean clearApprovedTasks) {
		String baseURL = RMSUtil.buildRMSURL();
		try {
			ArtifactReviewTaskSummary taskSummary = new ArtifactReviewTaskSummary();
			root = taskSummary;
			String userRolesCumulative = RMSUtil.getLoggedInUserRolesCumulative();
			ArtifactReviewTaskSummary roleTaskSummary = ArtifactsManagerClient.fetchTasksList(baseURL, userRolesCumulative, clearApprovedTasks);
			for (ArtifactReviewTask task : roleTaskSummary.getTaskList()) {
				taskSummary.addTask(task);
			}
			taskSummary.setDelegationRoles(roleTaskSummary.getDelegationRoles());
			clientTaskViewer.setInput(root);
		} catch(Exception e) {
			RMSUIPlugin.log(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		if (!clientTaskViewer.getSelection().isEmpty() && clientTaskViewer.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection)clientTaskViewer.getSelection();
			if (selection.size() == 1 ) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		}
	}
	
	private void addContextMenu() {
		final MenuManager popupMenu = new MenuManager();
		popupMenu.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager manager) {
				popupMenu.removeAll();
				if (clientTaskViewer.getSelection().isEmpty()) {
					return;
				}
				IStructuredSelection selection = 
					(IStructuredSelection)clientTaskViewer.getSelection();
				if (selection.size() == 1) {
					Object object = ((IStructuredSelection)clientTaskViewer.getSelection()).getFirstElement();
					if (object instanceof CommittedArtifactDetails) {
						popupMenu.add(new OpenReadOnlyArtifactEditorAction(clientTaskViewer));
					}
				}
			}
		});
	    Menu menu = popupMenu.createContextMenu(clientTaskViewer.getTree());
	    clientTaskViewer.getTree().setMenu(menu);
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() == refreshButton) {
			fetchTasks();
			refreshClientTaskViewer();
		} else if (e.getSource() == clearApprovedTasksButton){
			fetchTasks(true);
			refreshClientTaskViewer();
		}
	}
	
	public void refreshClientTaskViewer() {
		clientTaskViewer.refresh();
	}
	
	public LabelProvider getLabelProvider() {
		return (LabelProvider)clientTaskViewer.getLabelProvider();
	}
}