package com.tibco.cep.studio.ui.wizards.imports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.wizards.StudioNewFileWizardPage;


public class ProjectLibraryImportWizardPage extends StudioNewFileWizardPage {
	
	protected FileFieldEditor editor;
    protected String projectName;
    private Composite fileSelectionArea;
    
	public ProjectLibraryImportWizardPage(String pageName, IStructuredSelection selection, IProject project) {
		super(pageName, selection);
		setTitle(pageName); //NON-NLS-1
		setDescription(Messages.getString("importwizard.projectlib.studio.page.description")); //NON-NLS-1
		if(project != null){
			projectName = project.getName();
		}
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		if(projectName == null){
			editor.setEnabled(false, fileSelectionArea);
		}
		validatePage();
		resourceContainer.getTreeViewer().addFilter(new ViewerFilter() {
		
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (element instanceof IProject) {
					return ((IProject) element).isOpen() && CommonUtil.isStudioProject((IProject) element);
				}
				return false;
			}
		});
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createAdvancedControls(org.eclipse.swt.widgets.Composite)
	 */	
	protected void createAdvancedControls(Composite parent) {
		fileSelectionArea = new Composite(parent, SWT.NONE);
		GridData fileSelectionData = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL);
		fileSelectionArea.setLayoutData(fileSelectionData);

		GridLayout fileSelectionLayout = new GridLayout();
		fileSelectionLayout.numColumns = 3;
		fileSelectionLayout.makeColumnsEqualWidth = false;
		fileSelectionLayout.marginWidth = 0;
		fileSelectionLayout.marginHeight = 0;
		fileSelectionArea.setLayout(fileSelectionLayout);
		
		editor = new FileFieldEditor("fileSelect","Select File: ",fileSelectionArea); //NON-NLS-1 //NON-NLS-2
		editor.getTextControl(fileSelectionArea).addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				IPath path = new Path(ProjectLibraryImportWizardPage.this.editor.getStringValue());
				if(path.lastSegment() != null){
					setFileName(path.lastSegment());
					validateContinerPath();
					validatePage();
				}
			}
		});
		//String[] extensions = new String[] { "*.zip;*.tar;*.jar" }; //NON-NLS-1
		String[] extensions = new String[] { "*.projlib;" }; //NON-NLS-1
		editor.setFileExtensions(extensions);
		fileSelectionArea.moveAbove(null);

	}
	
	private void validateContinerPath(){
		if(getContainerFullPath() !=  null){
			IResource resource  = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
			if(resource.exists()){
				if(resource instanceof IProject){
					projectName = resource.getName();
					((ProjectLibraryImportWizard)getWizard()).setProject((IProject)resource);
				}else{
					projectName = resource.getProject().getName();
					((ProjectLibraryImportWizard)getWizard()).setProject(resource.getProject());
				}
			}
		}
	}
	
	@Override
	protected boolean validatePage() {
		validateContinerPath();
		if(projectName == null) {
			setErrorMessage(Messages.getString("No_folder_specified"));
			setPageComplete(false);
			return false;
		}else{
			if(editor != null){
				editor.setEnabled(true, fileSelectionArea);
			}
		}
		
		if(getProjLibLocation() != null ){
			File file = new File(getProjLibLocation());
			if (!file.exists()) {
				setErrorMessage(Messages.getString("importwizard.projectlib.studio.resourceDNE.error"));
				return false;
			}
			if(!file.isFile()){
				setErrorMessage(Messages.getString("importwizard.projectlib.studio.resourceDNE.error"));
				return false;
			}
			if(file.isFile() && !getProjLibLocation().endsWith("projlib")){
				setErrorMessage(Messages.getString("importwizard.projectlib.studio.resourceDNE.error"));
				return false;
			}
			if (((ProjectLibraryImportWizard)getWizard()).buildPathEntryExists(projectName, getProjLibLocation())) {
				setErrorMessage(Messages.getString("importwizard.projectlib.studio.duplicateentry.error"));
				return false;
			}
		}
		return super.validatePage();
	}
	
	 /* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createLinkTarget()
	 */
	protected void createLinkTarget() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getInitialContents()
	 */
	protected InputStream getInitialContents() {
		try {
			return new FileInputStream(new File(editor.getStringValue()));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getNewFileLabel()
	 */
	protected String getNewFileLabel() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validateLinkedResource()
	 */
	protected IStatus validateLinkedResource() {
		return new Status(IStatus.OK, "com.tibco.test", IStatus.OK, "", null); //NON-NLS-1 //NON-NLS-2
	}
	
	protected String getProjLibLocation(){
		if (editor != null){
			return editor.getStringValue();
		}
		return null;
	}
}
