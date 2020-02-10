package com.tibco.cep.studio.ui.dialog;

import static com.tibco.cep.studio.ui.StudioUIPlugin.PLUGIN_ID;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getPropertyImage;

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
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;


/**
 * 
 * @author sasahoo
 *
 */
public class ReturnTypeSelector extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	private static final String VOID = "void";
	private static final String ICONS_NO_TYPE_PNG = "icons/no_type.png"; //$NON-NLS-1$
	private static final String ICONS_APPICON16X16_GIF = "icons/appicon16x16.gif"; //$NON-NLS-1$
	private static final String ICONS_EVENT_PNG = "icons/event.png"; //$NON-NLS-1$
	private static final String ICONS_CONCEPT_PNG = "icons/concept.png"; //$NON-NLS-1$
	private static final String BRACE_START = "["; //$NON-NLS-1$
	private static final String BRACE_END = "]"; //$NON-NLS-1$
	private static final String TYPE_CONCEPT = "Concept"; //$NON-NLS-1$
	private static final String TYPE_EVENT = "Event"; //$NON-NLS-1$
	private static final String TYPE_PROCESS = "Process"; //$NON-NLS-1$
	
	protected Set<String> extensions = new HashSet<String>();
	protected Table typeTable;
	protected String currentProjectName;
	protected Label messageLabel;
	protected Tree treeWidget;
	protected boolean resource;
	private String value;
	private String propertyType;
	private Button isArrayButton;
	private boolean isArray = false;

	/**
	 * @param parent
	 * @param currentProjectName
	 * @param value
	 * @param propertyType
	 * @param isArrayReturnType
	 */
	public ReturnTypeSelector(Shell parent,
			                       String currentProjectName,
			                       String value,
			                       String propertyType,
			                       boolean isArrayReturnType) {
		super(parent);
		setTitle(Messages.getString("rulefunction.return.type.wizard.title")); //$NON-NLS-1$
		setMessage(Messages.getString("declration_selector_select_resource")); //$NON-NLS-1$
		this.currentProjectName = currentProjectName;
		this.value = value;
		if (value.endsWith(BRACE_END)
				&& (propertyType.equalsIgnoreCase(TYPE_CONCEPT) || 
						propertyType.equalsIgnoreCase(TYPE_EVENT) || 
						propertyType.equalsIgnoreCase(TYPE_PROCESS))) {
			
			this.value = value.substring(0, value.indexOf(BRACE_START));
		}
		this.propertyType = propertyType;
		isArray = isArrayReturnType;
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

		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.getString("rule.declaration.type")); //$NON-NLS-1$
		
		typeTable = new Table(composite, SWT.BORDER | SWT.SINGLE);
		typeTable.setLayout(new GridLayout());
		typeTable.addSelectionListener(new SelectionAdapter(){

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					resetWidget();
					resource = false;
					TableItem[]  items = typeTable.getSelection();
					String text = items[0].getText();
					propertyType = text;
					
					if (!isArrayButton.isEnabled()) {
						isArrayButton.setEnabled(true);
					}
					
					if (text.equalsIgnoreCase(TYPE_CONCEPT)) {
						value = text;
						resetResourceViewer(CommonIndexUtils.CONCEPT_EXTENSION);
						getOkButton().setEnabled(true);
					} else if(text.equalsIgnoreCase(TYPE_EVENT)) {
						value = text;
						resetResourceViewer(CommonIndexUtils.EVENT_EXTENSION);
						getOkButton().setEnabled(true);
					} else if(text.equalsIgnoreCase(TYPE_PROCESS)) {
						value = text;
						resetResourceViewer(CommonIndexUtils.PROCESS_EXTENSION);
						getOkButton().setEnabled(true);
					} else {
						value = text;
						if (value.equalsIgnoreCase(VOID)) {
							isArrayButton.setSelection(false);
							isArray = false;
							isArrayButton.setEnabled(false);
						}
						updateStatus(new Status(Status.OK, PLUGIN_ID, Status.OK, emptyListMessage, null));
						getOkButton().setEnabled(true);
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}});

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = 132;
		typeTable.setLayoutData(data);
//		typeTable.setFont(new Font(Display.getDefault(), "Tahoma", 10, SWT.NORMAL));
		TableColumn typeColumn = new TableColumn(typeTable, SWT.NULL);
		typeColumn.setWidth(convertWidthInCharsToPixels(fWidth));
		
		updateTypeTable();
		
		createIsArrayButton(composite);

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
				if(propertyType.equalsIgnoreCase(TYPE_CONCEPT)){
					if(value != null){
						resetResourceViewer(CommonIndexUtils.CONCEPT_EXTENSION);
						setInitialEntitySelection(currentProjectName, value, ELEMENT_TYPES.CONCEPT);
					}
				} else if(propertyType.equalsIgnoreCase(TYPE_EVENT)){
					if(value != null){
						resetResourceViewer(CommonIndexUtils.EVENT_EXTENSION);
						setInitialEntitySelection(currentProjectName, value, ELEMENT_TYPES.SIMPLE_EVENT);
					}
				} else if(propertyType.equalsIgnoreCase(TYPE_PROCESS)){
					if(value != null){
						resetResourceViewer(CommonIndexUtils.PROCESS_EXTENSION);
						setInitialEntitySelection(currentProjectName, value, ELEMENT_TYPES.PROCESS);
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
	
	/**
	 * @param parent
	 */
	protected void createIsArrayButton(Composite parent) {
		isArrayButton = new Button(parent, SWT.CHECK);
		isArrayButton.setText(Messages.getString("rule.function.return.array.type"));
		isArrayButton.setSelection(isArray);
		isArrayButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (isArrayButton.getSelection() ==  true ) {
					isArray = true;
				} else {
					isArray = false;
				}
			}});
		
		if (value.equalsIgnoreCase(VOID)) {
			isArrayButton.setEnabled(false);
		}
	}
	
	private int getIndex(String type){
		int k = 0;
		for(TableItem item:typeTable.getItems()){
			if(item.getText().equalsIgnoreCase(type)){
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
	private void resetResourceViewer(String extension){
		messageLabel.setEnabled(true);
		treeWidget.setEnabled(true);
		extensions.clear();
		extensions.add(extension);
		for(ViewerFilter filter: treeViewer.getFilters()){
			treeViewer.removeFilter(filter);
		}
		treeViewer.addFilter(new StudioProjectsOnly(currentProjectName));
		treeViewer.addFilter(new OnlyFileInclusionFilter(extensions));
		treeViewer.addFilter(new EObjectFilter());
		
		//Including Project Library
		if(extension.equals(TYPE_CONCEPT)) {
			treeViewer.addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.CONCEPT));
		}
		if(extension.equals(TYPE_EVENT)) {
			treeViewer.addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.SIMPLE_EVENT));
		}		
		if(extension.equals(TYPE_PROCESS)) {
			treeViewer.addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.PROCESS));
		}

		treeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
		treeViewer.refresh();
		treeIsEmpty = (treeViewer.getTree().getItemCount() == 0);
		
		updateStatus(new Status(Status.ERROR, PLUGIN_ID,
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
		TableItem tableItem = null;
		for(PROPERTY_TYPES property_types:PROPERTY_TYPES.values()){
			if (property_types == PROPERTY_TYPES.CONCEPT_REFERENCE
					|| property_types == PROPERTY_TYPES.CONCEPT) {
				continue;
			}
			Image image = getPropertyImage(property_types);
			if(image!=null){
				tableItem = new TableItem(typeTable, SWT.NONE);
				tableItem.setText(property_types.getName());
				tableItem.setImage(image);
			}
		}
		tableItem = new TableItem(typeTable, SWT.NONE);
		tableItem.setText(TYPE_CONCEPT);
		tableItem.setImage(StudioUIPlugin.getDefault().getImage(ICONS_CONCEPT_PNG));
		tableItem = new TableItem(typeTable, SWT.NONE);
		tableItem.setText(TYPE_EVENT);
		tableItem.setImage(StudioUIPlugin.getDefault().getImage(ICONS_EVENT_PNG));
		
		if (AddonUtil.isProcessAddonInstalled()) {
			tableItem = new TableItem(typeTable, SWT.NONE);
			tableItem.setText(TYPE_PROCESS);
			tableItem.setImage(StudioUIPlugin.getDefault().getImage(ICONS_APPICON16X16_GIF));
		}
		
		tableItem = new TableItem(typeTable, SWT.NONE);
		tableItem.setText(VOID);
		tableItem.setImage(StudioUIPlugin.getDefault().getImage(ICONS_NO_TYPE_PNG));
		tableItem = new TableItem(typeTable, SWT.NONE);
		tableItem.setText("Object");
		tableItem.setImage(StudioUIPlugin.getDefault().getImage(ICONS_NO_TYPE_PNG));
	}
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
	public IStatus validate(Object[] selection) {
		if (selection != null && selection.length == 1) {
			if (selection[0] instanceof IFile) {
				IFile file = (IFile)selection[0];
				String statusMessage = "";
				if (file.getFileExtension().equals(CommonIndexUtils.PROCESS_EXTENSION)) {
					String p = file.getFullPath().toPortableString();
					String f = p.replace("/" +file.getProject().getName(), "");
					value = f.replace(CommonIndexUtils.DOT + CommonIndexUtils.PROCESS_EXTENSION, "");
					statusMessage = MessageFormat.format( Messages.getString("resource_Selector_Message_format"), //$NON-NLS-N$
							new Object[] {f});
				} else {
					EObject entityObj = IndexUtils.loadEObject(ResourceHelper.getLocationURI((IFile)selection[0]));
					Entity entity = (Entity)entityObj;
					statusMessage = MessageFormat.format( Messages.getString("resource_Selector_Message_format"), //$NON-NLS-N$
							new Object[] { (entity != null ? entity.getFullPath()+"."+IndexUtils.getFileExtension(entity): "") });
					value = entity.getFullPath();
				}
				resource = true;
				return new Status(Status.OK, PLUGIN_ID, Status.OK, statusMessage, null);
			} else if (selection[0] instanceof SharedEntityElement) {
				Entity entity =  ((SharedEntityElement) selection[0]).getSharedEntity();
				String statusMessage = MessageFormat.format( Messages.getString("resource_Selector_Message_format"),
						new Object[] { (entity != null ? entity.getFullPath()+"."+IndexUtils.getFileExtension(entity): "") });
				value = entity.getFullPath();
				resource = true;
				return new Status(Status.OK, PLUGIN_ID, Status.OK, statusMessage, null);
			}
		}
    	return new Status(Status.ERROR, PLUGIN_ID,
        				  Status.ERROR, Messages.getString("resource_Selector_Error_Message"), null);
    }
    
	public boolean isResource() {
		return resource;
	}

	public boolean isArray() {
		return isArray;
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