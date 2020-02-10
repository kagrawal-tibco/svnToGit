/**
 * 
 */
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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.security.authz.permissions.actions.ActionsFactory;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.security.util.SecurityUtil;
import com.tibco.cep.studio.core.testdata.importSource.TESTDATA_IMPORT_SOURCES;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;
import com.tibco.cep.studio.ui.wizards.StudioNewFileWizardPage;
import com.tibco.cep.studio.ui.wizards.StudioResourceContainer;

/**
 * @author mgujrath
 *
 */
public class TestDataImportSourceSelectionWizardPage extends StudioNewFileWizardPage{
	
    private Text testDataDescriptionText;
	
	private Combo testDataImportSourceCombo;
	
	private IProject project;
	
	private Text fileLocationText;


	Pattern p = Pattern.compile("([A-Za-z_][A-Za-z0-9_]*)");
	
	public TestDataImportSourceSelectionWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName, selection);
		setTitle(Messages.getString("ImportTestDataWizard.page.Title"));
		setDescription(Messages.getString("ImportTestDataWizard.Desc.Basic"));
	}
	
	public boolean canFlipToNextPage() {
		return false;
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		createLabel(container, "Test Data Import Source");
		TESTDATA_IMPORT_SOURCES[] testDataImportSourcesEnums = TESTDATA_IMPORT_SOURCES.VALUES_ARRAY;
		String[] testDataImportSources = new String[testDataImportSourcesEnums.length];
		int i = 0;
		for (TESTDATA_IMPORT_SOURCES testDataImportSource : testDataImportSourcesEnums) {
			testDataImportSources[i++] = testDataImportSource.toString();
		}
		testDataImportSourceCombo = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
		testDataImportSourceCombo.setItems(testDataImportSources);
		testDataImportSourceCombo.setText(TESTDATA_IMPORT_SOURCES.EXCEL.toString());
		GridData testDataImportSourceCombo_Data = new GridData(GridData.FILL_HORIZONTAL);
		testDataImportSourceCombo.setLayoutData(testDataImportSourceCombo_Data);
		
		createResourceContainer(container);
				
		Label label = new Label(container, SWT.NONE);
		GridData gData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gData.horizontalIndent = 0;
		label.setLayoutData(gData);
		label.setText(Messages.getString("import.domain.file.select"));
		
		
		Composite childContainer = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		childContainer.setLayout(layout);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		fileLocationText = new Text(childContainer, SWT.BORDER);
		GridData fileLocationTextData = new GridData(GridData.FILL_HORIZONTAL);
		fileLocationTextData.widthHint = 150;
		fileLocationText.setLayoutData(fileLocationTextData);
		fileLocationText.addModifyListener(new ModifyListener() {

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		});
		
		Button button = new Button(childContainer, SWT.NONE);
		button.setText(Messages.getString("Browse"));
		button.addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
				String[] extensions = { "*.xls", "*.prr" };
				fd.setText(Messages.getString("import.domain.filedialog.title"));
				fd.setFilterExtensions(extensions);
				if (fileLocationText.getText() != null) {
					fd.setFileName(fileLocationText.getText());
				}
				String fileName = fd.open();
				if (fileName != null) {
					fileLocationText.setText(fileName);
				}
			}
		});
		validatePage();
		setErrorMessage(null);
		setMessage(null);
		setControl(container);
		setPageComplete(false);
	}
	
	
	
	public TESTDATA_IMPORT_SOURCES getImportSource() {
		return TESTDATA_IMPORT_SOURCES.valueOf(testDataImportSourceCombo.getText());
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		return true;
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
		return testDataDescriptionText.getText();
	}
	
	 
	public Text getFileLocationText() {
		return fileLocationText;
	}
	/**
	 * @return
	 */
	
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
