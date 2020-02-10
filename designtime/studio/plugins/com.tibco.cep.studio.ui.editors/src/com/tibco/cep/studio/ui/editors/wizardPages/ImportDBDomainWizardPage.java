package com.tibco.cep.studio.ui.editors.wizardPages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import  com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.sharedresource.jdbc.JdbcConfigModelMgr;
import com.tibco.cep.sharedresource.jdbc.JdbcSSLSharedresourceHelper;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.forms.components.ConceptSelector;
import com.tibco.cep.studio.ui.forms.components.DBConceptSelector;
import com.tibco.cep.studio.ui.forms.components.DBConceptsOnlyViewerFilter;
import com.tibco.cep.studio.ui.util.Messages;

public class ImportDBDomainWizardPage extends WizardPage implements IDomainSourceWizardPage {

	private Text dbConceptPath;
	private Combo propertyCombo;
	private String projectName;
	private String domainDataType;

	// private Combo dataTypeCombo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */

	public ImportDBDomainWizardPage(String pageName) {
		super(pageName);
		setTitle(Messages.getString("ImportDomainWizard.page.Title"));
		setDescription(Messages.getString("import.domain.wizard.dbconcept.description"));
	}

	public Text getFileLocationText() {
		return dbConceptPath;
	}

	public Combo getPropertyCombo() {
		return propertyCombo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
	 */
	@Override
	public IWizardPage getNextPage() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		return validatePage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.tibco.cep.studio.ui.editors.wizardPages.IDomainSourceWizardPage#
	 * getDataSource()
	 */
	@SuppressWarnings("unchecked")
	public Object getDataSource() {
		Map<String, Object> dataSource = new HashMap<String, Object>();

		String path = dbConceptPath.getText();
		Concept c = IndexUtils.getConcept(projectName, path);
		PropertyMap extProps = c.getExtendedProperties();
		String jdbcResourcePath = "";

		for (Entity e : extProps.getProperties()) {
			if (e instanceof SimpleProperty) {
				SimpleProperty p = (SimpleProperty) e;
				String propValue = p.getValue().trim();
				if (p.getName().equalsIgnoreCase("SCHEMA_NAME")) {
					dataSource.put("schemaName", propValue);
				} else if (p.getName().equalsIgnoreCase("OBJECT_NAME")) {
					dataSource.put("objectName", propValue);
				} else if (p.getName().equalsIgnoreCase("OBJECT_TYPE")) {
					dataSource.put("objectType", propValue);
				} else if (p.getName().equalsIgnoreCase("PRIMARY_KEY_PROPS")) {
					// primary key props
				} else if (p.getName().equalsIgnoreCase("JDBC_RESOURCE")) {
					dataSource.put("jdbcResource", propValue);
					jdbcResourcePath = propValue;
				}
			}
		}

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);
		IResource resource = project.getFile(jdbcResourcePath);
		
		if(!resource.exists()){
			try {
				for(IResource member :project.members()){
					if(member.getName().contains(".projlib")){
						resource = project.getFile("/"+member.getName()+jdbcResourcePath);
						if(resource.exists()){
							jdbcResourcePath = "/"+member.getName()+jdbcResourcePath;
							dataSource.put("jdbcResource", jdbcResourcePath);
							break;
						}
					}
				}
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		JdbcConfigModelMgr modelMgr = new JdbcConfigModelMgr(resource);
		modelMgr.parseModel();

		dataSource.put("jdbcDriver", GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("driver")));
		dataSource.put("jdbcUser", GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("user")));
		dataSource.put("jdbcPassword", GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("password")));
		dataSource.put("jdbcUrl", GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("location")));

		int idx = propertyCombo.getSelectionIndex();
		String columnName = propertyCombo.getItem(idx);
		dataSource.put("columnName", columnName);

		PropertyDefinition propDef = c.getPropertyDefinition(columnName, false);
		PROPERTY_TYPES type = propDef.getType();

		dataSource.put("columnType", type.getLiteral());
		
		String useSsl = modelMgr.getStringValue("useSsl");
		useSsl = GvUtil.getGvDefinedValue(modelMgr.getProject(), useSsl);
		
		if ("true".equalsIgnoreCase(useSsl)) {
			try {
				dataSource.put(JdbcSSLConnectionInfo.KEY_JDBC_SSL_CONNECTION_INFO,
						JdbcSSLSharedresourceHelper.getSSLConnectionInfo(
								(String)modelMgr.getStringValue("user"),
								(String)modelMgr.getStringValue("password"),
								(String)modelMgr.getStringValue("location"),
								(String)modelMgr.getStringValue("driver"),
								modelMgr));
			} catch (Exception e) {
				String message = "JDBC configure SSL failed.\n" + e.toString();
				EditorsUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, EditorsUIPlugin.PLUGIN_ID, message, e));
			}
		}
		
		return dataSource;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label label = new Label(container, SWT.NONE);
		GridData gData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gData.horizontalIndent = 0;
		label.setLayoutData(gData);
		label.setText(Messages.getString("import.domain.db.concept.select"));

		Composite childContainer = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		childContainer.setLayout(layout);
		childContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		dbConceptPath = new Text(childContainer, SWT.BORDER);
		GridData dbConceptPathData = new GridData(GridData.FILL_HORIZONTAL);
		dbConceptPathData.widthHint = 150;
		dbConceptPath.setLayoutData(dbConceptPathData);
		dbConceptPath.addModifyListener(new ModifyListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.
			 * swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		});

		Button button = new Button(childContainer, SWT.NONE);
		button.setText(Messages.getString("Browse"));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					Concept c = null;
					List<Entity> elt = IndexUtils.getAllEntities(projectName,
							ELEMENT_TYPES.CONCEPT);
					for (Entity ety : elt) {
						if (ety instanceof Concept && DBConceptsOnlyViewerFilter.isDBConcept(((Concept) ety).getFullPath(), projectName)) {
							c = (Concept) ety;
							break;
						}
					}
					
					String conceptPath = (c != null && c.getFullPath() != null) ? c.getFullPath() : "";
					ConceptSelector picker = new DBConceptSelector(getShell(),
							projectName, conceptPath);
					if (picker.open() == Dialog.OK) {
						if (picker.getFirstResult() != null
								&& picker.getFirstResult() instanceof Concept) {
							Concept selected = (Concept) picker
									.getFirstResult();
							String path = selected.getFullPath();
							dbConceptPath.setText(path);
							showColumnProperties(selected);
						}
					}
					setPageComplete(validatePage());
				} catch (Exception e2) {
					StudioUIPlugin.log(e2);
				}
			}
		});

		/**
		 * Properties
		 */
		Label propLabel = new Label(container, SWT.NONE);
		propLabel.setLayoutData(gData);
		propLabel.setText("Properties:");

		propertyCombo = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
		gData.widthHint = 250;
		propertyCombo.setLayoutData(gData);

		// add listener
		propertyCombo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				String conceptPath = dbConceptPath.getText();
				Concept c = IndexUtils.getConcept(projectName, conceptPath);
				PropertyDefinition propDef = null;
				String propName = propertyCombo.getText();
				if (propName.equals("")) {
					propDef = c.getPropertyDefinitions(false).get(0);
					propName = propDef.getName();
				} else {
					propDef = c.getPropertyDefinition(propName, false);
				}
				domainDataType = propDef.getType().getLiteral();
				
				if("ContainedConcept".equals(domainDataType) || "ConceptReference".equals(domainDataType)){
					dbConceptPath.setText(propDef.getConceptTypePath());
					showColumnProperties(IndexUtils.getConcept(projectName, propDef.getConceptTypePath()));
				}else{
					dbConceptPath.setText(propDef.getOwnerPath());
				}
				DomainImportSourceSelectionWizardPage prevPage = (DomainImportSourceSelectionWizardPage) getPreviousPage();
				prevPage.setDomainDataType(DOMAIN_DATA_TYPES.get(domainDataType).getLiteral());
			}

		});

		validatePage();
		setErrorMessage(null);
		setMessage(null);

		setControl(container);
	}

	/**
	 * Display the column properties in the combo box
	 * 
	 * @param c
	 */
	private void showColumnProperties(Concept c) {
		// set props
		List<PropertyDefinition> defs = c.getProperties();
		propertyCombo.removeAll();
		for (PropertyDefinition d : defs) {
			propertyCombo.add(d.getName());
		}
		propertyCombo.select(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.ui.wizards.WizardNewFileCreationPage#validatePage()
	 */
	protected boolean validatePage() {
		if(propertyCombo.getSelectionIndex() != -1) {
			if(propertyCombo.getItem(propertyCombo.getSelectionIndex()) != null && !dbConceptPath.getText().equals("")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
