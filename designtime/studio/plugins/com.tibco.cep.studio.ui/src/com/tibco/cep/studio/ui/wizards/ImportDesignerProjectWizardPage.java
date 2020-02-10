package com.tibco.cep.studio.ui.wizards;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;

import com.tibco.cep.studio.ui.util.Messages;

public class ImportDesignerProjectWizardPage extends WizardImportProjectCreationPage {
	
	private static final String[] ARCHIVE_EXTENSIONS = new String[] { "*.ear;*.dat", "*.ear", "*.dat", "*.*" } ; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	
	protected FileFieldEditor editor;

	public ImportDesignerProjectWizardPage(String pageName) {
		super(pageName,true);
		setTitle(pageName); //NON-NLS-1
		setDescription(Messages.getString("DesignerProjectImportWizardPage.Description")); //NON-NLS-1 //$NON-NLS-1$
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Group group = new Group(composite, SWT.NULL);
		group.setText(Messages.getString("DesignerProjectImportWizardPage.Info.3.0")); //$NON-NLS-1$
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		createAdvancedControls(group);
		
		Group targetGroup = new Group(composite, SWT.NULL);
		targetGroup.setText(Messages.getString("DesignerProjectImportWizardPage.Info.4.0")); //$NON-NLS-1$
		targetGroup.setLayout(new GridLayout());
		targetGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		super.createControl(targetGroup);
		
		setControl(composite);
	}

	protected Composite createAdvancedControls(Composite parent) {
		Composite dirSelectionArea = new Composite(parent, SWT.NONE);
		GridData dirSelectionData = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL);
		dirSelectionArea.setLayoutData(dirSelectionData);

		GridLayout dirSelectionLayout = new GridLayout();
		dirSelectionLayout.numColumns = 3;
		dirSelectionLayout.makeColumnsEqualWidth = false;
		dirSelectionLayout.marginWidth = 0;
		dirSelectionLayout.marginHeight = 0;
		dirSelectionArea.setLayout(dirSelectionLayout);
		
		editor = new FileFieldEditor("fileSelect",Messages.getString("DesignerProjectImportWizardPage.Info.ArchiveLocation"),dirSelectionArea) {

			@Override
			protected String changePressed() {
		        File f = new File(getTextControl().getText());
		        if (!f.exists()) {
					f = null;
				}
		        if (f != null && f.isDirectory()) {
		        	File file = getFileFromDir(f);
		        	return file == null ? null : file.getAbsolutePath();
		        } 
				return super.changePressed();
			}

			private File getFileFromDir(File startingDirectory) {

				FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
				if (startingDirectory != null) {
					dialog.setFilterPath(startingDirectory.getPath());
				}
				dialog.setFilterExtensions(ARCHIVE_EXTENSIONS);
				String file = dialog.open();
				if (file != null) {
					file = file.trim();
					if (file.length() > 0) {
						return new File(file);
					}
				}

				return null;
			}
			
		};
		editor.setFileExtensions(ARCHIVE_EXTENSIONS);
		editor.getTextControl(dirSelectionArea).addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				boolean complete = validatePage();
				setPageComplete(complete);
				String fileName = editor.getStringValue();
				if (fileName != null) {
					File file = new File(fileName);	
					if(file != null && file.exists() && file.isFile()) {
						String canonicalFileName = file.getName();
						if (canonicalFileName.endsWith(".ear")) {
							projectNameField.setText(canonicalFileName.substring(0, canonicalFileName.lastIndexOf(".ear")));
						} else {
							projectNameField.setText(file.getParentFile().getName());
						}
						xpathVersionCombo.setEnabled(true);
					}
				}
			}
		});

		return dirSelectionArea;
		
	}
	
    /*
     * see @DialogPage.setVisible(boolean)
     */
    public void setVisible(boolean visible) {
        super.setVisible(visible);
//        if (visible) {
//			editor.setFocus();
//		}
    }

    public File getExistingDesignerProjectArchive() {
		if (editor == null || editor.getStringValue() == null) {
			return null;
		}
		String fileName = editor.getStringValue();
		if (fileName.endsWith(".dat") && fileName.lastIndexOf(File.separator) != -1) { //$NON-NLS-1$
			fileName = fileName.substring(0, fileName.lastIndexOf(File.separator));
		}
		return new File(fileName);
	}
	
	@Override
	protected boolean validatePage() {
		if (editor.getStringValue() == null || editor.getStringValue().length() == 0) {
			setErrorMessage(Messages.getString("DesignerProjectImportWizardPage.Error.InvalidArchiveLocation")); 
			return false;
		}
		
		if (!new File(editor.getStringValue()).exists()) {
			setErrorMessage(Messages.getString("DesignerProjectImportWizardPage.Error.InvalidArchiveLocation")); 
			return false;
		}
		
		// check whether the project name is valid
		final IStatus nameStatus= ResourcesPlugin.getWorkspace().validateName(getProjectName(), IResource.PROJECT);
		if (!nameStatus.isOK()) {
			String problemMessage = Messages.getString("BE_Project_invalidname", getProjectName());
//			setErrorMessage(nameStatus.getMessage());
			setErrorMessage(problemMessage);
			setPageComplete(false);
			return false;
		}
		
		 //checking duplicate project names irrespective of cases
        for(IProject project:ResourcesPlugin.getWorkspace().getRoot().getProjects()){
        	if(project.getName().equalsIgnoreCase(getProjectName())){
        		 setErrorMessage("A project with that name already exists in the workspace.");
                 return false;
        	}
        }
		
        // check for existing resources in the output path
        IPath locationPath = getLocationPath();
        if (locationPath != null) {
        	locationPath = locationPath.append(getProjectName());
        	if (locationPath.toFile().exists()) {
        		// location already exists
    			String problemMessage = Messages.getString("BE_Project_location_exists", locationPath.toString());
        		setErrorMessage(problemMessage);
        		return false;
        	}
        }
        
		// for now, as shared resources are not imported from ear files
//		if (editor.getStringValue().toLowerCase().endsWith(".ear")) {
//			String problemMessage = Messages.getString("DesignerProjectImportWizardPage.Unsupported.EAR.error");
//    		setErrorMessage(problemMessage);
//    		return false;
//		}

//		if(!EntityNameHelper.isValidBEProjectIdentifier(getProjectName())){
//			String problemMessage = Messages.getString("BE_Project_invalidname", getProjectName());
//			setErrorMessage(problemMessage);
//			return false;
//		}
		
		return super.validatePage();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validateLinkedResource()
	 */
	protected IStatus validateLinkedResource() {
		return new Status(IStatus.OK, "com.tibco.cep.studio.ui", IStatus.OK, "", null); //NON-NLS-1 //NON-NLS-2 //$NON-NLS-1$ //$NON-NLS-2$
	}
}