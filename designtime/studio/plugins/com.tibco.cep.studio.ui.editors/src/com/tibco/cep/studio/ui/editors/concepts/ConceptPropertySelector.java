package com.tibco.cep.studio.ui.editors.concepts;

import static com.tibco.cep.studio.ui.editors.EditorsUIPlugin.PLUGIN_ID;
import static com.tibco.cep.studio.ui.editors.EditorsUIPlugin.getImageDescriptor;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getPropertyImage;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.forms.components.ConceptContainmentFilter;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * This Selector is exclusively for Concept Property Type 
 * @author sasahoo
 *
 */
public class ConceptPropertySelector extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	protected Set<String> extensions = new HashSet<String>();
	protected Table typeTable;
	protected String currentProjectName;
	protected Label messageLabel;
	protected Tree treeWidget;
	protected boolean resource;
	private String value;
	private String propertyType;
	private String conceptBasePath;
	private List<PROPERTY_TYPES> displayTypes; // the types to display, or null to display all property types

	public ConceptPropertySelector(Shell parent,
            String currentProjectName,
            String conceptBasePath,
            String value,
            String propertyType,
            List<PROPERTY_TYPES> displayTypes) {
		super(parent);
		setTitle(Messages.getString("CONCEPT_PROPERTY_WIZARD_TITLE"));
		setMessage(Messages.getString("declration_selector_select_resource"));
		this.currentProjectName = currentProjectName;
		this.conceptBasePath = conceptBasePath;
		this.value = value;
		this.propertyType = propertyType;
		this.displayTypes = displayTypes;
		setValidator(this);
	}
	
	/**
	 * @param parent
	 * @param title
	 * @param currentProjectName
	 * @param value
	 * @param propertyType
	 */
	public ConceptPropertySelector(Shell parent,
			                       String currentProjectName,
			                       String conceptBasePath,
			                       String value,
			                       String propertyType) {
		this(parent, currentProjectName, conceptBasePath, value, propertyType, null);
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

		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.getString("rule.declaration.type"));
		
		typeTable = new Table(composite, SWT.BORDER | SWT.SINGLE);
		typeTable.setLayout(new GridLayout());
		typeTable.addSelectionListener(new SelectionAdapter(){

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					resetWidget();
					resource = false;
					TableItem[]  items = typeTable.getSelection();
					String text = items[0].getText();
					propertyType = text;
					if(text.equalsIgnoreCase("ContainedConcept")){
						resetResourceViewer("concept", "ContainedConcept");
					}else if(text.equalsIgnoreCase("ConceptReference")){
						resetResourceViewer("concept", "ConceptReference");
					}else{
						value = text;
						updateStatus(new Status(Status.OK, PLUGIN_ID, Status.OK/*Status.ERROR*/, emptyListMessage, null));
						getOkButton().setEnabled(true);
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}});

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = 120;
		typeTable.setLayoutData(data);
		typeTable.setFont(new Font(Display.getDefault(), "Tahoma", 10, SWT.NORMAL));
		TableColumn typeColumn = new TableColumn(typeTable, SWT.NULL);
		typeColumn.setWidth(convertWidthInCharsToPixels(fWidth));
		
		updateTypeTable();

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
			updateStatus(new Status(Status.OK, PLUGIN_ID, Status.OK, emptyListMessage, null));
		}

		try{
			if(propertyType != null){
				typeTable.select(getIndex(propertyType));
				if(propertyType.equalsIgnoreCase(PROPERTY_TYPES.CONCEPT.getName())||
						propertyType.equalsIgnoreCase(PROPERTY_TYPES.CONCEPT_REFERENCE.getName())){
					if(value != null){
						resetResourceViewer("concept", propertyType);
						setInitialEntitySelection(currentProjectName, value, ELEMENT_TYPES.CONCEPT);
					}
				}else{
					updateStatus(new Status(Status.OK, PLUGIN_ID, Status.OK, emptyListMessage, null));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return composite;
	}
	
	private int getIndex(String type){
		int k = 0;
		for(TableItem item:typeTable.getItems()){
			if(item.getText().equals(type)){
				return k;
			}
			k++;
		}
		return -1;
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
	private void resetResourceViewer(String extension, String type){
		messageLabel.setEnabled(true);
		treeWidget.setEnabled(true);
		extensions.clear();
		extensions.add(extension);
		for(ViewerFilter filter: treeViewer.getFilters()){
			treeViewer.removeFilter(filter);
		}
		treeViewer.addFilter(new StudioProjectsOnly(currentProjectName));
		
		
		if(type.equals("ContainedConcept")){
			treeViewer.addFilter(new ConceptContainmentFilter(extensions, conceptBasePath, value, currentProjectName));	
			treeViewer.addFilter(new OnlyFileInclusionFilter(extensions));
		}else{
			treeViewer.addFilter(new OnlyFileInclusionFilter(extensions));
		}
		
		treeViewer.addFilter(new EObjectFilter());
		
		//Including ProjectLibrary Filter
		treeViewer.addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.CONCEPT));
		
		treeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
		treeViewer.refresh();
		treeIsEmpty = (treeViewer.getTree().getItemCount() == 0);
		
		updateStatus(new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID,
				  Status.ERROR, Messages.getString("resource_Selector_Error_Message"), null));
	}
	
	private void hookDoubleClickAction() {
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object element = selection.getFirstElement();
				if (element instanceof IFolder || element instanceof IProject){
					if(treeViewer.getExpandedState(element)){
						treeViewer.setExpandedState(element, false);	
					}else{
						treeViewer.setExpandedState(element, true);
					}
				}
			}
		});
	}
	
	private void updateTypeTable(){
		TableItem tableItem = null;
		for(PROPERTY_TYPES property_types:PROPERTY_TYPES.values()){
			if (!shouldDisplay(property_types)) {
				continue;
			}
			Image image = getPropertyImage(property_types);
			if(image!=null){
				tableItem = new TableItem(typeTable, SWT.NONE);
				tableItem.setText(property_types.getName());
				tableItem.setImage(image);
			}
		}
		
		// Bug Fix - for BE- 19119. Concept Reference and Contained Concept entries are being duplicated.
		
		/*if (shouldDisplay(PROPERTY_TYPES.CONCEPT)) {
			tableItem = new TableItem(typeTable, SWT.NONE);
			tableItem.setText("ContainedConcept");
			tableItem.setImage(getImageDescriptor("icons/iconConcept16.gif").createImage());
		}
		if (shouldDisplay(PROPERTY_TYPES.CONCEPT_REFERENCE)) {
			tableItem = new TableItem(typeTable, SWT.NONE);
			tableItem.setText("ConceptReference");
			tableItem.setImage(getImageDescriptor("icons/iconConceptRef16.gif").createImage());
		}*/
	}
	
    private boolean shouldDisplay(PROPERTY_TYPES property_type) {
    	if (displayTypes == null) {
    		return true;
    	}
    	return displayTypes.contains(property_type);
	}

	/* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
    	if (selection != null && selection.length == 1) {
    		if (selection[0] instanceof IFile) {
    			EObject entityObj = IndexUtils.loadEObject(ResourceHelper.getLocationURI((IFile)selection[0]));
    			Entity entity = (Entity)entityObj;
    			
    			String statusMessage = MessageFormat.format( Messages.getString("resource_Selector_Message_format"),
    					new Object[] { (entity != null ? entity.getFullPath()+"."+IndexUtils.getFileExtension(entity): "") });
    			
    			value = entity.getFullPath();
    			resource = true;
    			return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
    		} else if (selection[0] instanceof SharedEntityElement) {
    			Entity entity =  ((SharedEntityElement) selection[0]).getSharedEntity();
    			String statusMessage = MessageFormat.format( Messages.getString("resource_Selector_Message_format"),
    					new Object[] { (entity != null ? entity.getFullPath()+"."+IndexUtils.getFileExtension(entity): "") });
    			value = entity.getFullPath();
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     */
    @Override
    public Object getFirstResult() {
    	return super.getFirstResult();
    }

 }