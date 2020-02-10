package com.tibco.cep.studio.ui.editors.domain;

import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.DETAILSINFO_PAGE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.DETAILS_PAGE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.FALSE_VAL;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.KIND_RANGE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.KIND_SIMPLE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TRUE_VAL;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_BOOLEAN;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_DATE;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_INT;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_LONG;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_REAL;
import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.TYPE_STRING;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.addHyperLinkFieldListener;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createLinkField;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getSubDomains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.domain.DomainPackage;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import  com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.sharedresource.jdbc.JdbcConfigModelMgr;
import com.tibco.cep.sharedresource.jdbc.JdbcSSLSharedresourceHelper;
import com.tibco.cep.studio.core.domain.importHandler.DomainConfiguration;
import com.tibco.cep.studio.core.domain.importHandler.DomainModelImportHandlerFactory;
import com.tibco.cep.studio.core.domain.importHandler.IDomainModelImportHandler;
import com.tibco.cep.studio.core.domain.importSource.DOMAIN_IMPORT_SOURCES;
import com.tibco.cep.studio.core.domain.importSource.IDomainImportSource;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.ValidationError;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainFormViewer extends AbstractDomainFormViewer {
	
	public DomainFormViewer(DomainFormEditor editor) {
		this.editor = editor;
//		if (editor != null && editor.getEditorInput() != null) {
//			domain = ((DomainFormEditorInput) editor.getEditorInput()).getDomain();
//		}else{
			domain = editor.getDomain();
//		}
	}
	
	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		super.createPartControl(container, Messages.getString("domain.editor.title") + " " + domain.getName(),EditorsUIPlugin.getDefault().getImage("icons/domainModelView_16x15.png"));
		
		//Show the Reload Domain button only when the required extended properties are available.
		if (domain.getExtendedProperties() != null && domain.getExtendedProperties().getProperties() != null) {
			for (int i = 0; i < domain.getExtendedProperties().getProperties().size(); i++) {
				Entity prop = domain.getExtendedProperties().getProperties().get(i);
				if ("DOMAIN_RELOAD_DB_QUERY".equals(prop.getName())) {
					getForm().getToolBarManager().add(createReloadDomainAction(editor, getForm(), editor.getProject()));//Add the Reload button
				}
			}
		}
		
		dependencyDiagramAction = EditorUtils.createDependencyDiagramAction(editor, getForm(), editor.getProject());
		getForm().getToolBarManager().add(dependencyDiagramAction);
		
		super.createToolBarActions();
		DomainViewerListeners.addPagesWidgetsListeners(this);
		DomainViewerListeners.addRangeModifyListener(this);
		DomainUtils.populateExistingDomainValues(domain, viewer);
		
		/*getRemoveRowButton().setEnabled(viewer.getTable().getItemCount()>0);*/
		
		List<DomainEntry> entries = domain.getEntries();
		List<DomainInstance> domainInstances = domain.getInstances();
		domainDataTypesCombo.setEnabled((entries.size() == 0 
				&& domain.getSuperDomainPath() == null && domainInstances.size() == 0) ? true : false);
		
		//Handling Duplicate button for boolean domain data type
		if (domain.getDataType() == DOMAIN_DATA_TYPES.BOOLEAN 
				&& viewer.getTable().getItemCount() == 2) {
			getAddRowButton().setEnabled(false);
		} 
		
		 //Making readonly widgets
		if(!getEditor().isEnabled()){
			readOnlyWidgets();
		}
	}
	
	/**
	 * Create an action for Domain Model Reload (from datasource)
	 * @param editor
	 * @param form
	 * @param project
	 * @return
	 */
	private Action createReloadDomainAction(final IEditorPart editor, final ScrolledForm form,
			final IProject project) {
		Action reloadDomainAction = new Action("reloadDomain", Action.AS_PUSH_BUTTON) {
			public void run() {
				if(!MessageDialog.openConfirm(getEditor().getEditorSite().getShell(), Messages.getString("reload.domain.confirm.dialog.title"), 
						Messages.getString("reload.domain.confirm.dialog.text"))) {
					return;
				};
				
				IWorkbenchPage page = editor.getEditorSite().getWorkbenchWindow().getActivePage();
				IFile file = ((FileEditorInput) editor.getEditorInput()).getFile();
				boolean reloadSuccess = reloadDomainModel(page, file, project);
				if (reloadSuccess) {
					MessageDialog.openInformation(getEditor().getEditorSite().getShell(),
							Messages.getString("reload.domain.success.dialog.title"), Messages.getString("reload.domain.success.dialog.text"));
				}
				form.reflow(true);
			}
		};
		reloadDomainAction.setChecked(false);
		reloadDomainAction.setToolTipText(Messages.getString("reload.domain.tooltip"));
		reloadDomainAction.setImageDescriptor(EditorsUIPlugin.getImageDescriptor("icons/refresh_domain.png"));
		return reloadDomainAction;
	}
	
	/**
	 * Populates driver, username, password and other such properties.
	 * @param sharedResourcePath
	 * @param projectName
	 * @param queryString
	 * @return
	 */
	private Map<String, Object> populateDataSource(String sharedResourcePath, String projectName, String queryString) {
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).getFile(sharedResourcePath);
		JdbcConfigModelMgr modelMgr = new JdbcConfigModelMgr(resource);
		modelMgr.parseModel();
		
		Map<String, Object> dataSource = new HashMap<String, Object>();
		dataSource.put("driver", modelMgr.getStringValue("driver"));
		dataSource.put("username", modelMgr.getStringValue("user"));
		dataSource.put("password", modelMgr.getStringValue("password"));
		dataSource.put("url", modelMgr.getStringValue("location"));
		dataSource.put("query", queryString);
		
		String useSsl = modelMgr.getStringValue("useSsl");
		useSsl = GvUtil.getGvDefinedValue(modelMgr.getProject(), useSsl);
		
		if ("true".equalsIgnoreCase(useSsl)) {
			try {
				JdbcSSLConnectionInfo sslConnInfo = JdbcSSLSharedresourceHelper.getSSLConnectionInfo(
						modelMgr.getStringValue("user"),
						modelMgr.getStringValue("password"),
						modelMgr.getStringValue("location"),
						modelMgr.getStringValue("driver"),
						modelMgr);
				dataSource.put(JdbcSSLConnectionInfo.KEY_JDBC_SSL_CONNECTION_INFO, sslConnInfo);
			} catch (Exception e) {
				String message = "JDBC configure SSL failed.\n" + e.toString();
				EditorsUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, EditorsUIPlugin.PLUGIN_ID, message, e));
			}
		}
		
		return dataSource;
	}
	
	/**
	 * Reloads the Domain Model from its datasource (db).
	 * @param page
	 * @param file
	 * @param project
	 * @return
	 */
	public boolean reloadDomainModel(final IWorkbenchPage page, IFile file, IProject project) {
		try {
			List<ValidationError> validationErrors = new ArrayList<ValidationError>(0);
			IDomainModelImportHandler<Map<String, Object>, IDomainImportSource<Map<String, Object>>> domainModelImportHandler;
			
			String sharedResourcePath = null;
			@SuppressWarnings("unused")
			DOMAIN_IMPORT_SOURCES importDataSource = null;
			String reloadQuery = null;
			for (Iterator<Entity> iterator = domain.getExtendedProperties().getProperties().iterator(); iterator.hasNext();) {
				SimpleProperty prop = (SimpleProperty) iterator.next();
				if ("JDBC_RESOURCE".equals(prop.getName())) {
					sharedResourcePath = prop.getValue();
				}
				else if ("DOMAIN_RELOAD_DB_QUERY".equals(prop.getName())) {
					reloadQuery = prop.getValue();
				}
				else if ("DOMAIN_IMPORT_SOURCE".equals(prop.getName())) {
					if (DOMAIN_IMPORT_SOURCES.DATABASE_CONCEPT.name().equals(prop.getValue())) {
						importDataSource = DOMAIN_IMPORT_SOURCES.DATABASE_CONCEPT;
					}
					else if (DOMAIN_IMPORT_SOURCES.DATABASE_TABLE.name().equals(prop.getValue())) {
						importDataSource = DOMAIN_IMPORT_SOURCES.DATABASE_TABLE;
					}
				}
			}
			domainModelImportHandler = DomainModelImportHandlerFactory.INSTANCE.getImportHandler(
					DOMAIN_IMPORT_SOURCES.DATABASE_TABLE, validationErrors,
					populateDataSource(sharedResourcePath, DomainFormViewer.this.editor.getProject().getName(), reloadQuery));
			
			DomainConfiguration domainConfiguration = new DomainConfiguration();
			domainConfiguration.setDomainDataType(DomainFormViewer.this.editor.getDomain().getDataType());
			domainConfiguration.setDomainName(DomainFormViewer.this.editor.getDomain().getName());
			domainConfiguration.setDomainDescription(DomainFormViewer.this.editor.getDomain().getDescription());
			domainConfiguration.setDomainFolderPath(DomainFormViewer.this.editor.getDomain().getFolder());
			domainConfiguration.setProjectDirectoryPath(DomainFormViewer.this.editor.getProject().getLocation().toPortableString());
			
			domainModelImportHandler.importDomain(domainConfiguration);
			
			DomainFormViewer.this.editor.getDomain().getEntries().clear();
			DomainFormViewer.this.editor.getDomain().getEntries().addAll(domainModelImportHandler.getImportedDomain().getEntries());
			
			while (viewer.getElementAt(0) != null) {//Remove all existing Domain Entries
				viewer.remove(viewer.getElementAt(0)); 
			}
			DomainUtils.populateExistingDomainValues(domain, viewer);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(getEditor().getEditorSite().getShell(),
					Messages.getString("reload.domain.success.dialog.title"), "Failed to reload Domain Model from source. Check if source (database) is up and running.\n\nERROR: " + e.getMessage());
			return false;
		} finally {
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer#createGeneralPart(org.eclipse.ui.forms.widgets.ScrolledForm, org.eclipse.ui.forms.widgets.FormToolkit)
	 */
	@Override
	protected void createGeneralPart(final ScrolledForm form,final FormToolkit toolkit) {
		
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		section.setText(Messages.getString("GENERAL_SECTION_TITLE"));
		section.setDescription(Messages.getString("GENERAL_SECTION_DESCRIPTION"));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		section.setLayoutData(gd);
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		sectionClient.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 50;
		sectionClient.setLayoutData(gd);

		toolkit.createLabel(sectionClient,Messages.getString("Description"),SWT.NONE).setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		domainDescText = toolkit.createText(sectionClient,"", SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		domainDescText.setText(domain.getDescription()!=null?domain.getDescription():"");
		domainDescText.addModifyListener(new ModifyListener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e) {
				try{
					if(!domain.getDescription().equalsIgnoreCase(domainDescText.getText().trim())){
						EditorUtils.executeCommand(editor, new SetCommand(editor.getEditingDomain(),domain,
								ModelPackage.eINSTANCE.getEntity_Description(),domainDescText.getText()));
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
              }
			});
		gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.widthHint = 600;
		gd.heightHint = 30;
		domainDescText.setLayoutData(gd);
		
		toolkit.createLabel(sectionClient, Messages.getString("domain.editor.data.type"));
		Object[] values = DOMAIN_DATA_TYPES.VALUES.toArray();
		String[] domainDataTypes = new String[values.length];
		int i = 0;
		for (Object obj : values) {
			domainDataTypes[i++] = obj.toString();
		}
		domainDataTypesCombo = new Combo(sectionClient, SWT.BORDER | SWT.READ_ONLY);
		domainDataTypesCombo.setItems(domainDataTypes);
		domainDataTypesCombo.setText(domain.getDataType() != null? domain.getDataType().getName()
				                                               : DOMAIN_DATA_TYPES.get(DOMAIN_DATA_TYPES.STRING_VALUE).getName());
		setSelectedDataType(domain.getDataType() != null? domain.getDataType()
                					: DOMAIN_DATA_TYPES.get(DOMAIN_DATA_TYPES.STRING_VALUE));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint=250;
		domainDataTypesCombo.setLayoutData(gd);
		domainDataTypesCombo.addSelectionListener(new SelectionAdapter(){

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					if(isDomainAssociated(domain.getOwnerProjectName(), domain.getFullPath())){
						MessageDialog.openError(getEditor().getEditorSite().getShell(), 
								Messages.getString("domain.datatype.selection.message.dialog.title"), 
								Messages.getString("domain.datatype.selection.message.dialog.description"));
						domainDataTypesCombo.select(getDataTypeSelection(domain.getDataType().getName()));
						return;
					}
					if(getSubDomains(domain.getFullPath(), domain.getOwnerProjectName()).size() >0){
						MessageDialog.openError(getEditor().getEditorSite().getShell(), 
								Messages.getString("domain.datatype.selection.message.dialog.title"), 
								Messages.getString("domain.datatype.selection.message.dialog.description2"));
						domainDataTypesCombo.select(getDataTypeSelection(domain.getDataType().getName()));
					}
					if(!domain.getDataType().getName().equalsIgnoreCase(domainDataTypesCombo.getText().trim())){
						EditorUtils.executeCommand(editor, new SetCommand(editor.getEditingDomain(),domain,
								DomainPackage.eINSTANCE.getDomain_DataType(),DOMAIN_DATA_TYPES.get(domainDataTypesCombo.getText().trim())));
						valueColumn.setImage(EditorUtils.getImage(DOMAIN_DATA_TYPES.get(domainDataTypesCombo.getText())));
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
			}});
		
//		toolkit.createLabel(sectionClient,Messages.getString("InheritsFrom"),SWT.NONE);
		inheritlink = createLinkField(toolkit, sectionClient, Messages.getString("InheritsFrom"));

		Composite inheritComposite = toolkit.createComposite(sectionClient);
		layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		inheritComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		inheritComposite.setLayoutData(gd);
		
		domainInheritsText = toolkit.createText(inheritComposite,"", SWT.SINGLE | SWT.BORDER /*| SWT.READ_ONLY*/);
		domainInheritsText.setText(domain.getSuperDomainPath() != null? domain.getSuperDomainPath() : "");
		domainInheritsText.addModifyListener(new ModifyListener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e) {
				onModifyInheritsText(e);
				try {
					domainInheritsText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					domainInheritsText.setToolTipText("");
					if(domain.getSuperDomainPath() ==  null && !domainInheritsText.getText().trim().equalsIgnoreCase("")){
						Command command=new SetCommand(editor.getEditingDomain(),domain, DomainPackage.eINSTANCE.getDomain_SuperDomainPath(),domainInheritsText.getText()) ;			
						EditorUtils.executeCommand(editor, command);
						domainDataTypesCombo.setEnabled(false);
						return;
					}
					//Checking valid Inherit Path
					if(!domainInheritsText.getText().trim().equals("") && 
							IndexUtils.getEntity(domain.getOwnerProjectName(), domainInheritsText.getText().trim(), ELEMENT_TYPES.DOMAIN) == null){
						domainInheritsText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", domainInheritsText.getText(), Messages.getString("InheritsFrom"));
						domainInheritsText.setToolTipText(problemMessage);
//						return;
					}
					if(IndexUtils.getEntity(domain.getOwnerProjectName(), domainInheritsText.getText().trim(), ELEMENT_TYPES.DOMAIN) != null){
					if(((Domain)IndexUtils.getEntity(domain.getOwnerProjectName(), domainInheritsText.getText().trim(), ELEMENT_TYPES.DOMAIN)).getDataType() != domain.getDataType())
					{
						domainInheritsText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid_Datatype.entry", domainInheritsText.getText(), domain.getFullPath());
						domainInheritsText.setToolTipText(problemMessage);
					}
					}
					if(domain.getSuperDomainPath() ==  null && domainInheritsText.getText().trim().equals("")){
						if(domain.getEntries().size() >0 ){
							domainDataTypesCombo.setEnabled(false);
						}else{
							domainDataTypesCombo.setEnabled(true);
						}
						return;
					}
					
					if(!domain.getSuperDomainPath().equalsIgnoreCase(domainInheritsText.getText())){	
						Object value = domainInheritsText.getText().trim().equalsIgnoreCase("")? null: domainInheritsText.getText().trim();
						Command command =new SetCommand(editor.getEditingDomain(),domain, DomainPackage.eINSTANCE.getDomain_SuperDomainPath(),value) ;			
						EditorUtils.executeCommand(editor, command);
						if(domain.getSuperDomainPath() == null){
							if(domain.getEntries().size() > 0 ){
								domainDataTypesCombo.setEnabled(false);
							}else{
								domainDataTypesCombo.setEnabled(true);
							}
						}else {
						domainDataTypesCombo.setEnabled(false);
						}
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.widthHint = 545;
		domainInheritsText.setLayoutData(gd);

		addHyperLinkFieldListener(inheritlink, domainInheritsText, editor, editor.getProject().getName(), false, false);
		
		
		button = new Button(inheritComposite, SWT.NONE);
		button.setText(Messages.getString("BROWSE_BUTTON_TEXT"));
		button.addSelectionListener(new SelectionAdapter(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					DomainSelector picker = new DomainSelector(editor.getSite().getShell(),editor.getProject().getName(),
																domain,	domainInheritsText.getText().trim());
					if (picker.open() == Dialog.OK) {
						if(picker.getFirstResult()!=null){
							String path = picker.getFirstResult().toString();
							domainInheritsText.setText(path);
						}
					}
				}
				catch(Exception e2){
					e2.printStackTrace();
				}				
			}});
		createDomainViewerPart(form, toolkit);
		toolkit.paintBordersFor(sectionClient);
	}
	
	private int getDataTypeSelection(String s){
		int i = 0;
		for(String item:domainDataTypesCombo.getItems()){
			if(item.equals(s)){
				return i;
			}
			i++;
		}
		return -1;
	}
	
	/**
	 * @param projectName
	 * @param path
	 * @return
	 */
	private boolean isDomainAssociated(String projectName, String path){
		List<Entity> list = IndexUtils.getAllEntities(projectName, ELEMENT_TYPES.CONCEPT);
		for(Entity ent:list){
			if(ent instanceof Concept){
				Concept  concept = (Concept)ent;
				for(DomainInstance instance:concept.getAllDomainInstances()){
					if(instance.getResourcePath().equals(path)){
						return true;
					}
				}
			}
			if(ent instanceof Event){
				Event  concept = (Event)ent;
				for(DomainInstance instance:concept.getAllDomainInstances()){
					if(instance.getResourcePath().equals(path)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean onModifyInheritsText(ModifyEvent modifyEvent) {
		return false;
	}
	/**
	 * @param form
	 * @param toolkit
	 */
	protected void createDomainViewerPart(final ScrolledForm form,final FormToolkit toolkit) {
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR );
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		section.setText(Messages.getString("domain.editor.DomainEntries"));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		section.setLayoutData(gd);
	}
		
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer#createMasterPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent,	Section.NO_TITLE);
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		sectionClient.setLayout(new GridLayout(1, false));
		sectionClient.setLayoutData(new GridData(GridData.FILL_BOTH));
		createToolbar(sectionClient, true);
		Table table = toolkit.createTable(sectionClient, SWT.FULL_SELECTION	| SWT.MULTI);
		table.setLinesVisible(true);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 300;
		table.setLayoutData(gd);
		table.setHeaderVisible(true);

		descColumn = new TableColumn(table, SWT.NONE);
		descColumn.setText(Messages.getString("Description"));
		descColumn.setWidth(200);
		descColumn.setMoveable(false);
		descColumn.setResizable(true);
		descColumn.setImage(EditorsUIPlugin.getDefault().getImage("icons/description_16x16.png"));
		
		valueColumn = new TableColumn(table, SWT.NONE);
		valueColumn.setText(Messages.getString("Value"));
		valueColumn.setWidth(223);
		valueColumn.setMoveable(false);
		valueColumn.setResizable(true);
		valueColumn.setImage(EditorUtils.getImage(DOMAIN_DATA_TYPES.get(domainDataTypesCombo.getText())));
		
		viewer = new TableViewer(table);
		viewer.setLabelProvider(new DomainViewerLabelProvider(this));
		viewer.setContentProvider(new DomainViewerContentProvider());
		viewer.setCellModifier(new DomainViewerCellModifier(viewer));
		viewer.setCellEditors(new CellEditor[] { new TextCellEditor(table),null });
		viewer.setColumnProperties(new String[] { NAME_PROPERTY,VALUE_PROPERTY });
		viewer.addSelectionChangedListener(new DomainViewerSelectionChangeListener(this));
		viewer.getTable().setLinesVisible(false);
		tableEditor = DomainUtils.createTableViewer(viewer, getEditor()); 
		
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(table);
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		table.setLayout(autoTableLayout);
		
		toolkit.paintBordersFor(sectionClient);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer#createDetailsPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	protected  void createDetailsPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		detailsSection = toolkit.createSection(parent,ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE	| ExpandableComposite.EXPANDED);
		detailsSection.setText(Messages.getString("Details"));
		detailsSection.marginHeight = 10;
		detailsSection.addExpansionListener(new ExpansionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.ui.forms.events.ExpansionAdapter#expansionStateChanged(org.eclipse.ui.forms.events.ExpansionEvent)
			 */
			public void expansionStateChanged(ExpansionEvent e) {
				managedForm.getForm().reflow(true);
			}
		});
		Composite detailsClient = toolkit.createComposite(detailsSection);
		detailsSection.setClient(detailsClient);
		GridData gd = new GridData(GridData.FILL_BOTH);
		detailsSection.setLayoutData(gd);
		detailsClient.setLayout(new FillLayout());
		viewerScrollPageBook = toolkit.createPageBook(detailsClient, SWT.NONE);
		createDetailsBook(toolkit, viewerScrollPageBook.createPage(DETAILS_PAGE));
		createEmptyEntryDetailsInfo(toolkit, viewerScrollPageBook.createPage(DETAILSINFO_PAGE));
		viewerScrollPageBook.showPage(DETAILSINFO_PAGE);
		toolkit.paintBordersFor(detailsClient);
	}
	
	/**
	 * creating details page book
	 * @param toolkit
	 * @param details
	 */
	private void createDetailsBook(FormToolkit toolkit, Composite details) {
		details.setLayout(new FormLayout());
		FormData fd;
		singeBut = toolkit.createButton(details, Messages.getString("domain.details.single"), SWT.RADIO);
		fd = new FormData();
		fd.top = new FormAttachment(0, 5);
		fd.left = new FormAttachment(0, 5);
		singeBut.setLayoutData(fd);
		rangeBut = toolkit.createButton(details, Messages.getString("domain.details.range"), SWT.RADIO);
		fd = new FormData();
		fd.top = new FormAttachment(0, 5);
		fd.left = new FormAttachment(20, 0);
//		fd.left = new FormAttachment(60, 0);
		rangeBut.setLayoutData(fd);
		
		separator = toolkit.createCompositeSeparator(details);
		fd = new FormData();
		fd.left = new FormAttachment(0, 5);
		fd.right = new FormAttachment(100, -5);
		
		fd.top = new FormAttachment(rangeBut, 5, SWT.BOTTOM);
		fd.bottom = new FormAttachment(rangeBut, 6, SWT.BOTTOM);
		separator.setLayoutData(fd);
		valueScrollPageBook = toolkit.createPageBook(details, SWT.None);
		fd = new FormData();
		fd.top = new FormAttachment(separator, 5);
		fd.left = new FormAttachment(0, 0);
		fd.right = new FormAttachment(100, -0);
		fd.bottom = new FormAttachment(100, -0);
		valueScrollPageBook.setLayoutData(fd);
		createSimplePages(toolkit);
		createRangePages(toolkit);
		valueScrollPageBook.showEmptyPage();
	}
	
	/**
	 * creating simple pages
	 * @param toolkit
	 */
	private void createSimplePages(FormToolkit toolkit) {
		{
			Composite simplePage = valueScrollPageBook.createPage(KIND_SIMPLE+ TYPE_STRING);
			simplePage.setLayout(new GridLayout(2, false));
			toolkit.createLabel(simplePage, Messages.getString("domain.details.simple.string"));
			//simpleValueText = toolkit.createText(simplePage, ""); 
			simpleValueText = toolkit.createText(simplePage, "", SWT.BORDER);
			simpleValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		{
			Composite intPage = valueScrollPageBook.createPage(KIND_SIMPLE + TYPE_INT);
			intPage.setLayout(new GridLayout(2, false));
			toolkit.createLabel(intPage,Messages.getString("domain.details.simple.integer"));
			simpleIntValueText = toolkit.createText(intPage, "", SWT.BORDER); 
			simpleIntValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		{
			Composite realPage = valueScrollPageBook.createPage(KIND_SIMPLE + TYPE_REAL);
			realPage.setLayout(new GridLayout(2, false));
			toolkit.createLabel(realPage, Messages.getString("domain.details.simple.real"));
			simpleDoubleValueText = toolkit.createText(realPage, "", SWT.BORDER); 
			simpleDoubleValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		{
			Composite root = valueScrollPageBook.createPage(KIND_SIMPLE + TYPE_LONG);
			root.setLayout(new GridLayout(2, false));
			toolkit.createLabel(root,Messages.getString("domain.details.simple.long")).setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 2));
			simpleLongValueText = toolkit.createText(root, "", SWT.BORDER); 
			simpleLongValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		{
			Composite root = valueScrollPageBook.createPage(KIND_SIMPLE	+ TYPE_DATE);
			root.setLayout(new GridLayout(3, false));
			toolkit.createLabel(root, Messages.getString("domain.details.simple.datetime"));
			simpleDateValueText = toolkit.createText(root, "", SWT.BORDER);
			simpleDateValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			simpleDateValueText.setEditable(false);
			simpleDatebutton = toolkit.createButton(root,"", SWT.PUSH);
			simpleDatebutton.setImage(EditorsUIPlugin.getDefault().getImage("icons/calendar_16x16.png"));
		}
		{
			Composite root = valueScrollPageBook.createPage(KIND_SIMPLE	+ TYPE_BOOLEAN);
			root.setLayout(new GridLayout(2, false));
			booleanLabel = toolkit.createLabel(root, Messages.getString("domain.details.simple.boolean"));
			booleanLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 2));
			boolTrueBtn = toolkit.createButton(root, TRUE_VAL, SWT.RADIO);
			boolTrueBtn.setEnabled(false);
			boolFalseBtn = toolkit.createButton(root, FALSE_VAL, SWT.RADIO);
			boolFalseBtn.setEnabled(false);
		}
	}

	/**
	 * creating Range pages
	 * @param toolkit
	 */
	private void createRangePages(FormToolkit toolkit) {
		{
			Composite root = valueScrollPageBook.createPage(KIND_RANGE + TYPE_INT);
			root.setLayout(new GridLayout(3, false));
			toolkit.createLabel(root, Messages.getString("domain.details.lower"));
			intRangeLoText = toolkit.createText(root, "", SWT.BORDER); 
			intRangeLoText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			intRangeLoIncBtn = toolkit.createButton(root, Messages.getString("domain.details.included"), SWT.CHECK);
			toolkit.createLabel(root, Messages.getString("domain.details.upper"));
			intRangeUpText = toolkit.createText(root, "", SWT.BORDER);
			intRangeUpText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			intRangeUpIncBtn = toolkit.createButton(root, Messages.getString("domain.details.included"), SWT.CHECK);
		}
		{
			Composite root = valueScrollPageBook.createPage(KIND_RANGE+ TYPE_REAL);
			root.setLayout(new GridLayout(3, false));
			toolkit.createLabel(root, Messages.getString("domain.details.lower"));
			realRangeLoText = toolkit.createText(root, "", SWT.BORDER); 
			realRangeLoText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			realRangeLoIncBtn = toolkit.createButton(root,  Messages.getString("domain.details.included"),SWT.CHECK);
			toolkit.createLabel(root, Messages.getString("domain.details.upper"));
			realRangeUpText = toolkit.createText(root, "", SWT.BORDER);
			realRangeUpText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			realRangeUpIncBtn = toolkit.createButton(root,  Messages.getString("domain.details.included"),SWT.CHECK);
		}
		{
			Composite root = valueScrollPageBook.createPage(KIND_RANGE + TYPE_LONG);
			root.setLayout(new GridLayout(3, false));
			toolkit.createLabel(root, Messages.getString("domain.details.lower"));
			longRangeLoText = toolkit.createText(root, "", SWT.BORDER);
			longRangeLoText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			longRangeLoIncBtn = toolkit.createButton(root,  Messages.getString("domain.details.included"),SWT.CHECK);
			toolkit.createLabel(root, Messages.getString("domain.details.upper"));
			longRangeUpText = toolkit.createText(root, "", SWT.BORDER);
			longRangeUpText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			longRangeUpIncBtn = toolkit.createButton(root,  Messages.getString("domain.details.included"),SWT.CHECK);
		}
		{
			Composite root = valueScrollPageBook.createPage(KIND_RANGE+ TYPE_DATE);
			root.setLayout(new GridLayout(4, false));
			toolkit.createLabel(root, Messages.getString("domain.details.lower"));
			dateTimeRangeLoText = toolkit.createText(root, "", SWT.BORDER); 
			dateTimeRangeLoText.setLayoutData(new GridData(	GridData.FILL_HORIZONTAL));
			dateTimeRangeLoText.setEditable(false);
			loDatebutton = toolkit.createButton(root, "", SWT.PUSH);
			loDatebutton.setImage(EditorsUIPlugin.getDefault().getImage("icons/calendar_16x16.png"));
			dateTimeRangeLoIncBtn = toolkit.createButton(root, Messages.getString("domain.details.included"),SWT.CHECK);
			toolkit.createLabel(root,Messages.getString("domain.details.upper"));
			dateTimeRangeUpText = toolkit.createText(root, "", SWT.BORDER); 
			dateTimeRangeUpText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			dateTimeRangeUpText.setEditable(false);
			hiDatebutton = toolkit.createButton(root, "", SWT.PUSH);
			hiDatebutton.setImage(EditorsUIPlugin.getDefault().getImage("icons/calendar_16x16.png"));
			dateTimeRangeUpIncBtn = toolkit.createButton(root,  Messages.getString("domain.details.included"),SWT.CHECK);
		}
	}
	
	/**
	 * @param changedConcept
	 */
	public void doRefresh(Domain changedDomain){
		this.domain = changedDomain;
		Display.getDefault().asyncExec(new Runnable(){
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				if(domain.getSuperDomainPath()!= null){
					if(!domainInheritsText.isDisposed()){
						domainInheritsText.setText(domain.getSuperDomainPath());
					}
				}else{
					if(!domainInheritsText.isDisposed()){
						domainInheritsText.setText("");
					}
				}
				if (editor.isDirty()) {
					editor.setModified(false);
					editor.firePropertyChange();
				}
			}});
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer#add()
	 */
	@Override
	protected void add() {
		final String domainDataType = getDomainDataTypesCombo().getText();
		final TableViewer viewer = getViewer();
		final ScrolledPageBook viewerScrollPageBook= getViewerScrollPageBook();
		DomainViewerItem item = null;
		if (getTableEditor()!=null &&
				getTableEditor().getEditor()!=null
				&&  !getTableEditor().getEditor().isDisposed()) {
			     getTableEditor().getEditor().dispose();
		}

		DOMAIN_DATA_TYPES domain_data_types = DOMAIN_DATA_TYPES.get(domainDataType);
		switch(domain_data_types) {
		case STRING:
			item = getItem("", "");
			break;
		case INTEGER:
			item = getItem("", "");
			break;
		case DOUBLE:
			item = getItem("", "");
			break;
		case LONG:
			item = getItem("", "");
			break;
		case BOOLEAN:
			TableItem[] items = viewer.getTable().getItems();
			if (viewer.getTable().getItemCount() == 0) {
				item = getItem("", "true");
			}
			if (viewer.getTable().getItemCount() == 1) {
				if (items[0].getText(1).equalsIgnoreCase("true"))
					item = getItem("", "false");
				if (items[0].getText(1).equalsIgnoreCase("false"))
					item = getItem("", "true");
			}
			break;
		case DATE_TIME:
			item = getItem("", DomainUtils.getFormattedDateTime());
			break;
		}
		if (item != null) {
			viewer.add(item);
			viewer.getTable().setFocus();
			viewer.editElement(item, 1);
			viewerScrollPageBook.showPage(DETAILS_PAGE);
			if (item!=null && new StructuredSelection(item)!=null) {
				viewer.setSelection(new StructuredSelection(item));
			}
			//Domain Editor dirty
			getEditor().modified();
			int itemCount = viewer.getTable().getItemCount();
			getRemoveRowButton().setEnabled(itemCount > 0);
			getDuplicateButton().setEnabled(itemCount > 0);
			getDomainDataTypesCombo().setEnabled(itemCount == 0);
			
			//For Boolean data types
			if (domain_data_types == DOMAIN_DATA_TYPES.BOOLEAN 
					&& viewer.getTable().getItemCount() == 2) {
				getAddRowButton().setEnabled(false);
				getDuplicateButton().setEnabled(false);
			} 
		}
		
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer#remove()
	 */
	@Override
	protected void remove() {
		final TableViewer viewer = getViewer();
		final ScrolledPageBook viewerScrollPageBook= getViewerScrollPageBook();
		if(getTableEditor()!=null &&
				getTableEditor().getEditor()!=null
				&&  !getTableEditor().getEditor().isDisposed()){
			getTableEditor().getEditor().dispose();
		}
		if (viewer.getSelection() != null) {

			IStructuredSelection selItem = (IStructuredSelection) viewer.getSelection();

			if (selItem.getFirstElement() == null) {
				return;
			}
			
			int oldcnt = viewer.getTable().getItemCount();
			int k = viewer.getTable().getSelectionIndex();
									
			viewer.remove(selItem.getFirstElement());

			if (viewer.getTable().getItemCount() > 0) {
				if (k-1 != -1) {
					setSelection(viewer, viewerScrollPageBook, k-1);
				}
				if (k !=-1) {
					setSelection(viewer, viewerScrollPageBook, k);
				} 
			}
			int newcnt = viewer.getTable().getItemCount();
			if (oldcnt > newcnt) {
				getEditor().modified();//Domain Editor dirty
			}
			getAddRowButton().setEnabled(true);
			getRemoveRowButton().setEnabled(newcnt > 0);
			getDuplicateButton().setEnabled(newcnt > 0);
			getDomainDataTypesCombo().setEnabled(newcnt == 0 && domain.getSuperDomainPath() == null);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer#duplicate()
	 */
	@Override
	protected void duplicate() {
		final String domainDataType = getDomainDataTypesCombo().getText();
		final TableViewer viewer = getViewer();
		DOMAIN_DATA_TYPES domain_data_types = DOMAIN_DATA_TYPES.get(domainDataType);
		if (domain_data_types == DOMAIN_DATA_TYPES.BOOLEAN) {
			if (viewer.getTable().getItemCount() == 2) {
				return;
			}
		} 						
		if (!viewer.getSelection().isEmpty()) {
			IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
			for (Object obj  : sel.toList()) {
				if (obj instanceof DomainViewerItem) {
					DomainViewerItem item = (DomainViewerItem) obj;
					viewer.add(new DomainViewerItem(item.entryName, item.entryValue));					
				} else {
					viewer.add(obj);
				}
			}
			getEditor().modified();
		}
		viewer.getTable().forceFocus();			
		
		//For Boolean data types
		if (domain_data_types == DOMAIN_DATA_TYPES.BOOLEAN 
				&& viewer.getTable().getItemCount() == 2) {
			getAddRowButton().setEnabled(false);
			getDuplicateButton().setEnabled(false);
		} 
		
	}
	
	/**
	 * @param viewer
	 * @param pagebook
	 * @param index
	 */
	private void setSelection(TableViewer viewer, ScrolledPageBook pagebook, int index) {
		viewer.getTable().select(index);
		DomainViewerItem item =(DomainViewerItem)viewer.getElementAt(index);
		pagebook.showPage(DETAILS_PAGE);
		if (item!=null && new StructuredSelection(item)!=null) {
			viewer.setSelection(new StructuredSelection(item));
			viewer.getTable().forceFocus();			
		}
	}
	
	/**
	 * @param description
	 * @param value
	 * @return
	 */
	private DomainViewerItem getItem(String description, String value) {
		return new DomainViewerItem(description,value);
	}
}