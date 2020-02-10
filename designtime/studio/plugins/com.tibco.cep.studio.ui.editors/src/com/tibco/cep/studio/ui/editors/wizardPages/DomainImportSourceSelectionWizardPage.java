package com.tibco.cep.studio.ui.editors.wizardPages;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.security.authz.permissions.actions.ActionsFactory;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.security.util.SecurityUtil;
import com.tibco.cep.studio.core.domain.importSource.DOMAIN_IMPORT_SOURCES;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;
import com.tibco.cep.studio.ui.wizards.StudioNewFileWizardPage;

public class DomainImportSourceSelectionWizardPage extends StudioNewFileWizardPage {
	
	private Text domainDescriptionText;
	
	private Combo dataTypeCombo;
	
	private Combo domainImportSourceCombo;
	
	private IProject project;
	 
	Pattern p = Pattern.compile("([A-Za-z_][A-Za-z0-9_]*)");
	
	public DomainImportSourceSelectionWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName, selection);
		setTitle(Messages.getString("ImportDomainWizard.page.Title"));
		setDescription(Messages.getString("ImportDomainWizard.Desc.Basic"));
	}
	
	public boolean canFlipToNextPage() {
		return validatePage();
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		createLabel(container, "Domain Import Source");
		DOMAIN_IMPORT_SOURCES[] domainImportSourcesEnums = DOMAIN_IMPORT_SOURCES.VALUES_ARRAY;
		String[] domainImportSources = new String[domainImportSourcesEnums.length];
		int i = 0;
		for (DOMAIN_IMPORT_SOURCES domainImportSource : domainImportSourcesEnums) {
			domainImportSources[i++] = domainImportSource.toString();
		}
		domainImportSourceCombo = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
		domainImportSourceCombo.setItems(domainImportSources);
		domainImportSourceCombo.setText(DOMAIN_IMPORT_SOURCES.EXCEL.toString());
		GridData domainImportSourceCombo_Data = new GridData(GridData.FILL_HORIZONTAL);
		domainImportSourceCombo.setLayoutData(domainImportSourceCombo_Data);
		
		createResourceContainer(container);
		createLabel(container, Messages.getString("wizard.desc"));
		domainDescriptionText = new Text(container, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		domainDescriptionText.setLayoutData(gd);
		
		
		createLabel(container, "Data Type");
		DOMAIN_DATA_TYPES[] domainDataTypeEnums = DOMAIN_DATA_TYPES.VALUES.toArray(new DOMAIN_DATA_TYPES[0]);
		String[] domainDataTypes = new String[domainDataTypeEnums.length];
		int loop = 0;
		for (DOMAIN_DATA_TYPES domainDataTypeEnum : domainDataTypeEnums) {
			domainDataTypes[loop++] = domainDataTypeEnum.toString();
		}
		dataTypeCombo = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
		dataTypeCombo.setItems(domainDataTypes);
		dataTypeCombo.setText(DOMAIN_DATA_TYPES.STRING.toString());
		GridData dataTypeCombo_Data = new GridData(GridData.FILL_HORIZONTAL);
		dataTypeCombo.setLayoutData(dataTypeCombo_Data);
				
		validatePage();
		setErrorMessage(null);
		setMessage(null);
		setControl(container);
		setPageComplete(false);
	}
	
	public DOMAIN_IMPORT_SOURCES getImportSource() {
		return DOMAIN_IMPORT_SOURCES.valueOf(domainImportSourceCombo.getText());
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.WizardNewFileCreationPage#validatePage()
	 */
	@SuppressWarnings("restriction")
	protected boolean validatePage() {
		//ACL Check
		if (Utils.isStandaloneDecisionManger()) {
			List<Role> roles = AuthTokenUtils.getLoggedInUserRoles();
			Permit permit = Permit.DENY;
			String operation = "create";
			String projectName = null;
			IResource resource  = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
			if (!(roles.isEmpty())) {
				IAction action = ActionsFactory.getAction(ResourceType.DOMAIN, operation);
				if (resource.exists()) {
					projectName = resource.getProject().getName();
				}
				permit = SecurityUtil.ensurePermission(projectName, getContainerFullPath().toOSString(), 
						ResourceType.DOMAIN, roles, action, PermissionType.BERESOURCE);
			}
			if (Permit.DENY.equals(permit)) {
				setErrorMessage(Messages.getString("access.resource.error", operation));
				return false;
			} else if(Permit.ALLOW.equals(permit)) {
				setErrorMessage(null);
				return true;
			}
		}
		String resourceName = getResourceContainer().getResource();
		if(resourceName.equals("")) {
			return false;
		}
		Matcher m = p.matcher(resourceName);
		if(!m.matches()) {
			String problemMessage =resourceName + " is not a valid file name";
			setErrorMessage(problemMessage);
			return false;
		}
		
		IResource resource  = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
		if (resource instanceof IProject) {
			setProject((IProject)resource);
		} else {
			setProject(resource.getProject());
		}
		
		return super.validatePage();
	}
	
	/**
	 * @return
	 */
	public IFile getModelFile() {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()
			   + StudioResourceUtils.getExtensionFor(StudioWorkbenchConstants._WIZARD_TYPE_NAME_DOMAIN)));
	}
	
	/**
	 * @return
	 */
	public String getDomainDescription() {
		return domainDescriptionText.getText();
	}
	
	/**
	 * @return
	 */
	public void setDomainDataType(String type) {
		dataTypeCombo.setText(type);
	}
	
	/**
	 * @return
	 */
	public String getDomainDataType() {
		return dataTypeCombo.getText();
	}
	
	public IProject getProject() {
		if (project == null && _currentProjectName != null) {
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(_currentProjectName);
		}
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}
}