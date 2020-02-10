package com.tibco.cep.studio.ui.editors.utils;

import static com.tibco.cep.studio.ui.editors.EditorsUIPlugin.PLUGIN_ID;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getPropertyImage;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.rule.ActionType;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.filter.EventNonPayloadFilter;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.forms.components.EntityFileInclusionFilter;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * 
 * @author sasahoo
 *
 */
public class DeclarationSelector extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	protected Set<String> extensions = new HashSet<String>();
	protected ELEMENT_TYPES declType;
	protected Table typeTable;
	protected String currentProjectName;
	protected Label messageLabel;
	protected Tree treeWidget;
	protected String type;
	protected String actionType; // for rule templates
	protected String idName;
	protected boolean resource;
	protected String superEvent;
	protected String baseEvent;
	protected ELEMENT_TYPES extnType;
	private Button isArrayButton;
	private boolean array = false;
	private boolean allowEntityType = true;
	private boolean allowArray = true;
	private boolean allowPayloadEvents;

	/**
	 * @param parent
	 * @param title
	 * @param currentProjectName
	 * @param declType
	 */
	public DeclarationSelector(Shell parent,
			String title, 
			String currentProjectName, 
			ELEMENT_TYPES declType) {
		this(parent, title, currentProjectName, declType, true, true);
	}
	
	/**
	 * @param parent
	 * @param title
	 * @param currentProjectName
	 * @param declType
	 * @param allowEntityType 
	 */
	public DeclarationSelector(Shell parent,
			                   String title, 
			                   String currentProjectName, 
			                   ELEMENT_TYPES declType, 
			                   boolean allowEntityType,
			                   boolean allowArray) {
		this(parent, title, currentProjectName, declType, null, null, true);
		this.allowEntityType = allowEntityType;
		this.allowArray = allowArray;
	}

	/**
	 * Constructor for Event Inheritance
	 * @param parent
	 * @param title
	 * @param currentProjectName
	 * @param declType
	 * @param baseEvent
	 * @param superEvent
	 */
	public DeclarationSelector(Shell parent,
							   String title,
							   String currentProjectName,
							   ELEMENT_TYPES declType,
							   String baseEvent,
							   String superEvent,
							   boolean allowPayloadEvents) {
		super(parent);
		setTitle(title);
		setMessage(Messages.getString("declration_selector_select_resource"));
		this.declType = declType;
		this.currentProjectName = currentProjectName;
		this.superEvent = superEvent;
		this.baseEvent = baseEvent;
		this.allowPayloadEvents = allowPayloadEvents;
		setValidator(this);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.BaseObjectSelector#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		// create a composite with standard margins and spacing
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(composite);

		if (declType == ELEMENT_TYPES.RULE_TEMPLATE) {
			Composite actionTypeGroup = new Composite(composite, SWT.NULL);
			GridLayout actionLayout = new GridLayout(2, false);
			actionLayout.marginHeight = 0;
			actionLayout.marginWidth = 0;
			
			actionTypeGroup.setLayout(actionLayout);
			actionTypeGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			Label actionTypeLabel = new Label(actionTypeGroup, SWT.NONE);
			actionTypeLabel.setText(Messages.getString("ruletemplate.action.type"));
			final Combo actionTypeCombo = new Combo(actionTypeGroup, SWT.READ_ONLY);
			actionTypeCombo.setItems(new String[] { ActionType.CREATE.getLiteral(), ActionType.MODIFY.getLiteral(), ActionType.CALL.getLiteral() });
			actionType = ActionType.CREATE.getLiteral();
			actionTypeCombo.addSelectionListener(new SelectionListener() {
			
				@Override
				public void widgetSelected(SelectionEvent e) {
					actionType = actionTypeCombo.getItem(actionTypeCombo.getSelectionIndex());
					updateTypeTable();
				}
			
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			actionTypeCombo.select(0);
		}
		
		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.getString("rule.declaration.type"));

		typeTable = new Table(composite, SWT.BORDER | SWT.SINGLE);
		typeTable.setLayout(new GridLayout());
		typeTable.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					if (isArrayButton != null) {
						isArrayButton.setEnabled(true);
					}
					resetWidget();
					resource = false;
					TableItem[]  items = typeTable.getSelection();
					String text = items[0].getText();
					if(text.equalsIgnoreCase("Concept")){
						resetResourceViewer("concept");
						if (declType != ELEMENT_TYPES.RULE_TEMPLATE) {
							getOkButton().setEnabled(true);
						}
						type = text;
						idName = text.substring(0, 1).toLowerCase();
					}else if(text.equalsIgnoreCase("Scorecard")){
						resetResourceViewer("scorecard");
					}else if(text.equalsIgnoreCase("Rule Function")){
						resetResourceViewer("rulefunction");
					}else if(text.equalsIgnoreCase("Simple Event") || text.equalsIgnoreCase("Event")){
						resetResourceViewer("event");
						if(declType != ELEMENT_TYPES.SIMPLE_EVENT){
							if (declType != ELEMENT_TYPES.RULE_TEMPLATE) {
								getOkButton().setEnabled(true);
							}
							switch(declType){
							case RULE:
							case RULE_TEMPLATE:
								type = "SimpleEvent";
								idName = text.substring(0, 1).toLowerCase();	
								break;
							case RULE_FUNCTION:	
								type = "Event";
								idName = text.substring(0, 1).toLowerCase();
								break;
							}
						}
					}else if(text.equalsIgnoreCase("Time Event")){
						resetResourceViewer(CommonIndexUtils.TIME_EXTENSION);
						if (declType != ELEMENT_TYPES.RULE_TEMPLATE) {
							getOkButton().setEnabled(true);
						}
						type = "TimeEvent";
						idName = text.substring(0, 1).toLowerCase();
					}else if(text.equalsIgnoreCase("SOAPEvent")){
						type = "SOAPEvent";
						updateStatus(new Status(Status.OK, PLUGIN_ID, Status.OK, emptyListMessage, null));
						if (declType != ELEMENT_TYPES.RULE_TEMPLATE) {
							getOkButton().setEnabled(true);
						}
					}else if(text.equalsIgnoreCase("Advisory Event")){
						updateStatus(new Status(Status.OK, PLUGIN_ID,Status.OK, emptyListMessage, null));
						if (declType != ELEMENT_TYPES.RULE_TEMPLATE) {
							getOkButton().setEnabled(true);
						}
						type = "AdvisoryEvent";
						idName = text.substring(0, 1).toLowerCase();
					}else{
						updateStatus(new Status(Status.OK, PLUGIN_ID, Status.OK, emptyListMessage, null));
						if (declType != ELEMENT_TYPES.RULE_TEMPLATE) {
							getOkButton().setEnabled(true);
						}
						type = text;
						idName = text.substring(0, 1).toLowerCase();
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}});

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		if(declType  == ELEMENT_TYPES.RULE_FUNCTION){
			data.heightHint = 138;
		}else{
			data.heightHint = 52;
		}
		typeTable.setLayoutData(data);
		//		typeTable.setFont(new Font(Display.getDefault(), "Tahoma", 10, SWT.NORMAL));
		TableColumn typeColumn = new TableColumn(typeTable, SWT.NULL);
		typeColumn.setWidth(convertWidthInCharsToPixels(fWidth));

		updateTypeTable();

		if (this.declType == ELEMENT_TYPES.RULE_FUNCTION) {
			createIsArrayButton(composite);
		}

		// Create a message and tree view area
		messageLabel = createMessageArea(composite);
		createTreeViewer(composite);

		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = convertHeightInCharsToPixels(fHeight);

		treeWidget = treeViewer.getTree();
		treeWidget.setLayoutData(data);
		treeWidget.setFont(parent.getFont());

		hookDoubleClickAction();
		// If the tree is empty then disable the controls
		if (treeIsEmpty) {
			messageLabel.setEnabled(false);
			treeWidget.setEnabled(false);
			updateStatus(new Status(Status.ERROR, PLUGIN_ID, Status.OK, emptyListMessage, null));
		}
		//For Event Inheritance
		try{
			if(superEvent!=null)
				if( !superEvent.equalsIgnoreCase("")){
					if(!superEvent.equalsIgnoreCase("SOAPEvent")){
						typeTable.select(0);
						resetResourceViewer("event");
						setInitialEntitySelection(currentProjectName, superEvent, ELEMENT_TYPES.SIMPLE_EVENT);

					}else{
						typeTable.select(1);
						updateStatus(new Status(Status.OK, PLUGIN_ID, Status.OK, emptyListMessage, null));
					}
				}
		}catch(Exception e){
			e.printStackTrace();
		}

 		return composite;
	}


	/**
	 * @param parent
	 */
	protected void createIsArrayButton(Composite parent) {
		if (!allowArray) {
			return;
		}
		isArrayButton = new Button(parent, SWT.CHECK);
		isArrayButton.setText(Messages.getString("rule.function.return.array.type"));
		isArrayButton.setSelection(array);
		isArrayButton.setEnabled(false);
		isArrayButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (isArrayButton.getSelection() ==  true ) {
					array = true;
				} else {
					array = false;
				}
			}});
	}
	
	private void resetWidget(){
		treeViewer.setInput(null);
		treeViewer.refresh();
		messageLabel.setEnabled(false);
		treeWidget.setEnabled(false);
		getOkButton().setEnabled(false);
	}
	
	/**
	 * @param extension
	 */
	private void resetResourceViewer(String extension){
		try{
			messageLabel.setEnabled(true);
			treeWidget.setEnabled(true);
			extensions.clear();
			if(declType == ELEMENT_TYPES.RULE_FUNCTION && extension.equalsIgnoreCase("event")){
				extensions.add(CommonIndexUtils.TIME_EXTENSION);
			}

			extensions.add(extension);
			for(ViewerFilter filter: treeViewer.getFilters()){
				treeViewer.removeFilter(filter);
			}
			treeViewer.addFilter(new StudioProjectsOnly(currentProjectName));

			if(declType == ELEMENT_TYPES.SIMPLE_EVENT){
				treeViewer.addFilter(new EntityFileInclusionFilter(extensions, baseEvent, superEvent, currentProjectName));
				treeViewer.addFilter(new OnlyFileInclusionFilter(extensions));
			}else{
				treeViewer.addFilter(new OnlyFileInclusionFilter(extensions));
			}

			treeViewer.addFilter(new EObjectFilter());

			//Including Project Library
			if(extension.equals("concept")) {
				extnType = ELEMENT_TYPES.CONCEPT;
				treeViewer.addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.CONCEPT));
			}
			if(extension.equals("rulefunction")) {
				extnType = ELEMENT_TYPES.RULE_FUNCTION;
				treeViewer.addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.RULE_FUNCTION));
			}
			if(extension.equals("scorecard")) {
				extnType = ELEMENT_TYPES.SCORECARD;
				treeViewer.addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.SCORECARD));
			}
			if(extension.equals("event")) {
				extnType = ELEMENT_TYPES.SIMPLE_EVENT;
				treeViewer.addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.SIMPLE_EVENT));
				if (!this.allowPayloadEvents) {
					treeViewer.addFilter(new EventNonPayloadFilter());
				}
			}
			if(extension.equals("time")) {
				extnType = ELEMENT_TYPES.TIME_EVENT;
				treeViewer.addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.TIME_EVENT));
			}

			treeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
			treeViewer.refresh();
			treeIsEmpty = (treeViewer.getTree().getItemCount() == 0);
		}catch(Exception e){
			e.printStackTrace();
		}

		updateStatus(new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID,
				Status.ERROR, Messages.getString("resource_Selector_Error_Message"), null));
	}
	
	private void hookDoubleClickAction() {
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			/* (non-Javadoc)
			 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
			 */
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object element = selection.getFirstElement();
				
				if (element instanceof SharedElementRootNode 
    					|| element instanceof DesignerProject 
    					|| element instanceof Folder 
    					|| element instanceof IFolder 
    					|| element instanceof IProject){
					if(!treeViewer.getExpandedState(element)){
						treeViewer.setExpandedState(element, false);	
					}else{
						treeViewer.setExpandedState(element, true);
					}
				}
			}
		});
	}
	
	private void updateTypeTable(){
		typeTable.removeAll();
		TableItem tableItem = null;
		switch(declType){
			case RULE:
				if (allowEntityType) {
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Concept");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/concept.png"));
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Scorecard");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/scorecard.png"));
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Simple Event");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/event.png"));
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Time Event");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/time-event.gif"));
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Advisory Event");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/event.png"));
				}
				break;
			case RULE_TEMPLATE:
				if (actionType.equals(ActionType.CALL.getLiteral())) {
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Rule Function");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/rule-function.png"));
					break;
				} else {
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Concept");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/concept.png"));
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Scorecard");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/scorecard.png"));
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Simple Event");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/event.png"));
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Time Event");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/time-event.gif"));
					break;
				}
			case RULE_FUNCTION:
				for(PROPERTY_TYPES property_types:PROPERTY_TYPES.values()){
					if(!property_types.getName().equalsIgnoreCase("ContainedConcept") && !property_types.getName().equalsIgnoreCase("ConceptReference")){
						Image image = getPropertyImage(property_types);
						if(image!=null){
							tableItem = new TableItem(typeTable, SWT.NONE);
							tableItem.setText(property_types.getName());
							tableItem.setImage(image);
						}
					}
				}
				if (allowEntityType) {
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Concept");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/concept.png"));
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Scorecard");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/scorecard.png"));
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Event");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/event.png"));
					tableItem = new TableItem(typeTable, SWT.NONE);
					tableItem.setText("Object");
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/no_type.png"));
				}
				break;
			case SIMPLE_EVENT:
				tableItem = new TableItem(typeTable, SWT.NONE);
				tableItem.setText("Simple Event");
				tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/event.png"));
				tableItem = new TableItem(typeTable, SWT.NONE);
				tableItem.setText("SOAPEvent");
				tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/event.png"));
				break;			
		}
	}
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
    	if (selection != null && selection.length == 1) {
    		Object obj = selection[0];
    		if (obj instanceof IFile) {
    			IFile file = (IFile) obj;
    			if (extnType == ELEMENT_TYPES.RULE_FUNCTION && IndexUtils.RULEFUNCTION_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
        			String statusMessage = MessageFormat.format( Messages.getString("resource_Selector_Message_format"),
        					new Object[] { (file.getFullPath()) });
        			type = "/"+file.getFullPath().removeFirstSegments(1).removeFileExtension().toString();
        			idName = file.getFullPath().removeFileExtension().lastSegment().toLowerCase();
        			resource = true;
        			return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
        		} else {
        			EObject entityObj = IndexUtils.loadEObject(ResourceHelper.getLocationURI((IFile)selection[0]));
        			Entity entity = (Entity)entityObj;
        			
        			String statusMessage = MessageFormat.format( Messages.getString("resource_Selector_Message_format"),
        					new Object[] { (entity != null ? entity.getFullPath()+"."+IndexUtils.getFileExtension(entity): "") });
        			
        			type = entity.getFullPath();
        			idName = entity.getName().toLowerCase();
        			resource = true;
        			return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
        		}
            } else if (obj instanceof SharedEntityElement && extnType != null 
            		&&((SharedEntityElement) obj).getElementType().equals(extnType)) {
            	Entity entity = ((SharedEntityElement) obj).getSharedEntity();
            	
            	String statusMessage = MessageFormat.format( Messages.getString("resource_Selector_Message_format"),
            			new Object[] { (entity != null ? entity.getFullPath()+"."+IndexUtils.getFileExtension(entity): "") });
            	
            	type = entity.getFullPath();
            	idName = entity.getName().toLowerCase();
            	resource = true;
            	return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            } else if (obj instanceof SharedRuleElement && extnType != null 
            		&&((SharedRuleElement) obj).getElementType().equals(extnType)) {
            	Compilable rule = ((SharedRuleElement) obj).getRule();
            	
            	String statusMessage = MessageFormat.format( Messages.getString("resource_Selector_Message_format"),
            			new Object[] { (rule != null ? rule.getFullPath()+"."+IndexUtils.getFileExtension(rule): "") });
            	
            	type = rule.getFullPath();
            	idName = rule.getName().toLowerCase();
            	resource = true;
            	return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            }
    	}
    	return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID,
        				  Status.ERROR, Messages.getString("resource_Selector_Error_Message"), null);
    }
	
	public boolean isResource() {
		return resource;
	}

	public String getIdName() {
		return idName;
	}

	public String getType() {
		return type;
	}
	
	public boolean isArray() {
		return array;
	}
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     */
    @Override
    public Object getFirstResult() {
    	return super.getFirstResult();
    }
    
	/**
	 * @return
	 */
	public DeclarationSelector getSelector(){
		return this;
	}

	public String getActionType() {
		return actionType;
	}
 }