package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * @author sasahoo
 * 
 */
public class EntityFileCreationWizard extends StudioNewFileWizardPage {

	protected String _type;
	protected Text _typeDesc;
	protected IStructuredSelection _selection;
    protected IProject project;
	private boolean filterProjects;
  
    /*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public EntityFileCreationWizard(String pageName,
			IStructuredSelection selection, String type) {
		super(pageName, selection);
		_type = type;
		_selection = selection;
	}

	public EntityFileCreationWizard(String pageName,
			IStructuredSelection selection, String type, String currentProjectName) {
		super(pageName, selection);
		_type = type;
		_selection = selection;
		_currentProjectName = currentProjectName;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.WizardNewFileCreationPage#getNewFileLabel()
	 */
	@Override
	protected String getNewFileLabel() {
		return Messages.getString("Wizard_BE_New_Entity_Suffix_File_Name",_type);
	}
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		Composite controls = (Composite) super.getControl();
		createTypeDescControl(controls);
		setResourceExtension(StudioResourceUtils.getExtensionFor(_type));
		setControl(controls);
		
//		StudioWorkbenchUtils.isStandaloneDecisionManger(this, _type);
	}

	protected void createTypeDescControl(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.getString("wizard.desc"));
		_typeDesc = new Text(parent, SWT.BORDER);
//		_typeDesc.setBackground(ColorConstants.tooltipBackground);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		_typeDesc.setLayoutData(gd);
	}
	
	public IFile getModelFile() {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()
			   + StudioResourceUtils.getExtensionFor(_type)));
	}
	
	public String getTypeDesc() {
		return _typeDesc.getText();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.WizardNewFileCreationPage#validatePage()
	 */
	@Override
	protected boolean validatePage() {
		setErrorMessage(null);
		if(resourceContainer.getResourceName().length() == 0){
			setErrorMessage(Messages.getString("Empty_Name"));
			return false;
		}
		String resourceName = resourceContainer.getResourceName();
		int idx = resourceName.lastIndexOf('.');
		if (idx != -1) {
			String ext = resourceName.substring(idx+1);
			if (ext.equals(resourceContainer.getResourceExtension())) {
				resourceName = resourceName.substring(0, idx);
			}
		}
		if (!EntityNameHelper.isValidBEEntityIdentifier(resourceName)) {
			String problemMessage = Messages.getString("BE_Resource_invalidFilename", resourceContainer.getResourceName());
			setErrorMessage(problemMessage);
			return false;
		}
	
		//validation for empty container path
		if (getContainerFullPath() == null) {
			String problemMessage = Messages.getString("No_folder_specified");
			setMessage(problemMessage);
			setErrorMessage(null);
			return false;
		}
		//validation for Bad Folder
		if (!StudioUIUtils.isValidContainer(getContainerFullPath())) {
			String problemMessage = com.tibco.cep.studio.core.util.Messages.getString("Resource.folder.bad", getContainerFullPath());
			setErrorMessage(problemMessage);
			return false;
		}
		IResource resource  = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
		if (resource instanceof IProject) {
			setProject((IProject)resource);
		} else {
			setProject(resource.getProject());
		}
		delegateProject();
		StringBuilder duplicateFileName  = new StringBuilder("");
		if (isDuplicateBEResource(resource,resourceName,duplicateFileName)) {
			String problemMessage = Messages.getString("BE_Resource_FilenameExists", duplicateFileName ,resourceName);
			setErrorMessage(problemMessage);
			return false;
		}
		
		//check for invalid multibyte characters.
		String invalid=findInvalidCharacters(resourceName);
		if(invalid!=null){
			setErrorMessage("Invalid Character '"+invalid +"'");
			return false;
		}
		
		//BE-15428
		// ACL Check
		try {
			if (!checkValidAccess()) {
				return false;
			}
		} catch (Exception e) {
			// Handle exception if user performs operation in DM and is not
			// logged in RMS
			setErrorMessage(e.getMessage());
			setPageComplete(false);
			return false;
		}
		
		return super.validatePage();
	}

	protected void delegateProject(){
		//Override this for doing additional work with project change
	}
	
	//ACL check
	protected boolean checkValidAccess(){
		return true;
	}
	
	private String findInvalidCharacters(String name){
		String regex="^[_\\p{L}][_\\p{L}\\p{N}]*$";
		int beginIndex=0;
		String invalidCharacters=null;
		if(name.contains("µ")){
			invalidCharacters="µ";
			return invalidCharacters;
		}
		for(int len=1;len<=name.length();len++){
			if(!(name.substring(beginIndex, len)).matches(regex)){
				invalidCharacters=name.charAt(len-1)+"";
				break;
			}
		}
		return invalidCharacters;
	}
	
	public IProject getProject() {
		if (project == null && _currentProjectName != null) {
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(_currentProjectName);
		}
		return project;
	}

	public void setProject(IProject project) {
		if (!project.equals(this.project)) {
			this.project = project;
			if (this.filterProjects()) {
				this.resourceContainer.setCurrentProjectName(project);
			}
		}
	}
	
	public void setFilterProjects(boolean filterProjects) {
		this.filterProjects = filterProjects;
	}
	
	private boolean filterProjects() {
		return this.filterProjects;
	}

	@Override
	protected boolean isDuplicateBEResource(IResource resource,
			String resourceName, 
			StringBuilder duplicateFileName) {
		// use CommonUtil so that it only checks for BE resources
		return CommonUtil.isDuplicateResource(resource, resourceName, duplicateFileName);
	}

}