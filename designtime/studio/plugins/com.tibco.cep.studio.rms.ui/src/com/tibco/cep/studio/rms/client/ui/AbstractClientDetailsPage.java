package com.tibco.cep.studio.rms.client.ui;

import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.ArtifactReviewTaskSummary;
import com.tibco.cep.studio.rms.model.ArtifactStatusChangeDetails;

public abstract class AbstractClientDetailsPage implements IDetailsPage, ModifyListener, 
                                                           SelectionListener, ISelectionChangedListener, IExpansionListener {
	
	protected IManagedForm managedForm;
	
	protected ScrolledPageBook worklistDetailsPageBook;
	
	protected ScrolledPageBook delegateToPage;
	
	protected IProject project;
	protected HashMap<Object, Object> controls;
	protected Composite details;
	
	/**
	 * The shared tree viewer
	 */
	protected RMSClientWorklistFormViewer clientWorklistFormViewer;
	
	protected static final int HEIGHTHINTnormal = 200;
	protected static final int HEIGHTHINTmin = 3;
	protected static final int SIZEaugend = 5;
	protected static final int LAYOUTmultiplier = 30;
	
	public static final Font mandatoryFont = new Font(Display.getDefault(),"Tahoma", 11, SWT.BOLD);
	public static final java.awt.Font font = new java.awt.Font("Tahoma", 0, 11);
	
	
	protected Button submitButton;
	
	protected Button secSubmitButton;
	
	/**
	 * The super model object
	 */
	protected ArtifactReviewTaskSummary superParent;

	/**
	 * @param clientTaskViewer
	 */
	protected AbstractClientDetailsPage(RMSClientWorklistFormViewer clientWorklistFormViewer) {
		this.clientWorklistFormViewer = clientWorklistFormViewer;
	}

	protected abstract void createDetailsPage(FormToolkit toolkit);
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	public void createContents(Composite parent) {
		controls = new HashMap<Object, Object>();
		TableWrapLayout layout1 = new TableWrapLayout();
		parent.setLayout(layout1);
		FormToolkit toolkit = managedForm.getToolkit();
		
		Section section  =  toolkit.createSection(parent, Section.EXPANDED | Section.TITLE_BAR);
		section.setText("Details");
		section.setFont(new org.eclipse.swt.graphics.Font(Display.getCurrent(), new FontData("Tahoma", 8, SWT.BOLD)));
		
		TableWrapData td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		section.setLayoutData(td);
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		sectionClient.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = 50;
		sectionClient.setLayoutData(layoutData);
		details = toolkit.createComposite(sectionClient);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 700;
		details.setLayoutData(data);
		details.setLayout(new FillLayout());
		worklistDetailsPageBook = toolkit.createPageBook(details, SWT.NONE);
		
		//createRequestPage(toolkit, ARTIFACT_REVIEW_TASK);	
		//createDetailPage(toolkit, COMMITTED_ARTIFACT_DETAILS);
		createDetailsPage(toolkit);
		
		worklistDetailsPageBook.showEmptyPage();
		toolkit.paintBordersFor(sectionClient);
	}
	
	
	/**
	 * @param toolkit
	 * @param parent
	 * @param span
	 */
	protected void createSpacer(FormToolkit toolkit, Composite parent, int span) {
		Label spacer = toolkit.createLabel(parent, "");
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		spacer.setLayoutData(gd);
	}
	
	/**
	 * @param toolkit
	 * @param parent
	 * @return
	 */
	protected Control createButtonBar(FormToolkit toolkit, Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1; 
		layout.marginWidth = 0;
		composite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END
				| GridData.VERTICAL_ALIGN_CENTER);
		composite.setLayoutData(data);
		submitButton = new Button(composite, SWT.PUSH);
		submitButton.setText("Submit");
		submitButton.addSelectionListener(this);
		data = new GridData();
		data.widthHint = 80;
		data.heightHint = 25;
		submitButton.setLayoutData(data);
		submitButton.setEnabled(false);
		return composite;
	}
	
	@Override
	public void commit(boolean onSave) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(IManagedForm form) {
		this.managedForm = form;
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isStale() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#refresh()
	 */
	@Override
	public void refresh() {
		update();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean setFormInput(Object input) {
		return false;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.events.IExpansionListener#expansionStateChanged(org.eclipse.ui.forms.events.ExpansionEvent)
	 */
	@Override
	public void expansionStateChanged(ExpansionEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.events.IExpansionListener#expansionStateChanging(org.eclipse.ui.forms.events.ExpansionEvent)
	 */
	@Override
	public void expansionStateChanging(ExpansionEvent e) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Updates to UI components
	 */
	protected abstract void update();
	
	protected Object executeSubmit(List<ArtifactStatusChangeDetails> detailsToSubmit) {
		//TODO Add PB here
		try {
			return ArtifactsManagerClient.changeArtifactStatus(RMSUtil.buildRMSURL(),
					                                           AuthTokenUtils.getLoggedinUser(),
					                                           detailsToSubmit);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}