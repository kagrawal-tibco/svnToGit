package com.tibco.cep.studio.ui.wizards;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.IOverwriteQuery;

import com.tibco.cep.cep_commonVersion;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.util.Messages;


public class ImportExistingStudioProjectPage extends WizardPage implements IOverwriteQuery {
	
//	private static final String[] ARCHIVE_EXTENSIONS = new String[] { "*.ear;*.project", "*.ear", "*.project", "*.*" } ; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
//	private static final String[] ARCHIVE_EXTENSIONS = new String[] { "*.project", "*.*" } ; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	
	private Text directoryPathField;
	private Text projectNameField;
	private Button copyIntoWorkspaceButton;
	private Text importLocationField;
	private Combo xpathVersionCombo;
	private boolean canFlipToNextPage = false;
	private static String previouslyBrowsedDirectory;

	public ImportExistingStudioProjectPage(String pageName) {
		super(pageName);
		setTitle(pageName); //NON-NLS-1
		setDescription(Messages.getString("ExistingStudioProjectImportWizardPage.Description")); //NON-NLS-1 //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#isCurrentPage()
	 */
	public boolean isCurrentPage() {
		return super.isCurrentPage();
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (directoryPathField != null && directoryPathField.getText() != null && directoryPathField.getText().length() > 0) {
			updateProjectInfo(directoryPathField.getText(), true);
		}

	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label projectLocationLabel = new Label(composite, SWT.NULL);
		projectLocationLabel.setText(Messages.getString("ExistingStudioProjectImportWizardPage.Info.ArchiveLocation"));
		
		directoryPathField = new Text(composite, SWT.BORDER);
		directoryPathField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		directoryPathField.addModifyListener(new ModifyListener() {
		
			@Override
			public void modifyText(ModifyEvent e) {
				updateProjectInfo(directoryPathField.getText(), false);
				validatePage();
			}
		});
		
		Button browseDirectoryButton = new Button(composite, SWT.NULL);
		browseDirectoryButton.setText("Browse...");
		browseDirectoryButton.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseDirectory();
			}
		
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		Group targetGroup = new Group(composite, SWT.NULL);
		targetGroup.setText(Messages.getString("ExistingStudioProjectImportWizardPage.Info.5.6")); //$NON-NLS-1$
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		targetGroup.setLayout(layout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		targetGroup.setLayoutData(data);
		
		Label projectNameLabel = new Label(targetGroup, SWT.NULL);
		projectNameLabel.setText("Project name:");
		projectNameField = new Text(targetGroup, SWT.BORDER);
		projectNameField.setEnabled(false);
		projectNameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Label xpathVersionLabel = new Label(targetGroup,SWT.NULL);
		xpathVersionLabel.setText(Messages.getString("ImportExistingProjectDetailsPage.xpathVersion"));
		xpathVersionCombo = new Combo(targetGroup, SWT.DROP_DOWN|SWT.READ_ONLY);
		GridData gd = new GridData();
		gd.widthHint = 100;
		gd.verticalIndent = 10;
		xpathVersionCombo.setLayoutData(gd);
		xpathVersionCombo.setEnabled(false);
		xpathVersionCombo.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				final Combo eventSource = (Combo) e.widget;
				final int selectionIndex = eventSource.getSelectionIndex();
				if(xpathVersionCombo.getItemCount() == 0) {
					((ImportExistingStudioProjectWizard)getWizard()).setXpathVersion(XPATH_VERSION.XPATH_10);
				} else {
					((ImportExistingStudioProjectWizard)getWizard()).setXpathVersion(XPATH_VERSION.get(selectionIndex));
				}
				
			}
		});
		
		copyIntoWorkspaceButton = new Button(targetGroup, SWT.CHECK);
		copyIntoWorkspaceButton.setText("Copy project into workspace");
		copyIntoWorkspaceButton.setSelection(true);
		GridData btnData = new GridData(GridData.FILL_HORIZONTAL);
		btnData.horizontalSpan = 2;
		copyIntoWorkspaceButton.setLayoutData(btnData);
		copyIntoWorkspaceButton.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				importLocationField.setEnabled(!copyIntoWorkspaceButton.getSelection());
				if (copyIntoWorkspaceButton.getSelection()) {
					if (getProjectHandle() == null) {
						importLocationField.setText(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());
					} else {
						importLocationField.setText(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString()+"/"+getProjectHandle().getName());
					}
				} else {
					importLocationField.setText(directoryPathField.getText());
				}
			}
		
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		Label importLocationLabel = new Label(targetGroup, SWT.NULL);
		importLocationLabel.setText("Import location:");
		importLocationField = new Text(targetGroup, SWT.BORDER);
		importLocationField.setEnabled(false);
		importLocationField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		importLocationField.setEnabled(false);
		importLocationField.setText(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());
		importLocationField.addModifyListener(new ModifyListener() {
		
			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		
		if(directoryPathField.getText().length() > 0) {
			this.canFlipToNextPage = true;
			getContainer().updateButtons();
		}
		
		xpathVersionCombo.removeAll();
		for(XPATH_VERSION x:XPATH_VERSION.VALUES) {
			xpathVersionCombo.add(x.getLiteral());
		}
		
		setControl(composite);
	}

	private void browseDirectory() {

		DirectoryDialog dialog = new DirectoryDialog(getShell());
		dialog
				.setMessage("Select root directory of the projects to import");

		String dirName = directoryPathField.getText().trim();
		if (dirName.length() == 0) {
			dirName = previouslyBrowsedDirectory;
		}
		if (dirName == null) {
			dirName = "";
		}
		if (dirName.length() == 0) {
			dialog.setFilterPath(ResourcesPlugin.getWorkspace()
					.getRoot().getLocation().toOSString());
		} else {
			File path = new File(dirName);
			if (path.exists()) {
				dialog.setFilterPath(new Path(dirName).toOSString());
			}
		}

		String selectedDirectory = dialog.open();
		if (selectedDirectory != null) {
			previouslyBrowsedDirectory = selectedDirectory;
			directoryPathField.setText(previouslyBrowsedDirectory);
			updateProjectInfo(selectedDirectory, false);
		}

	}
	
	private void updateProjectInfo(String selectedDirectory, boolean visibilityCheck) {
		xpathVersionCombo.setEnabled(true);
		IProject projectHandle = getProjectHandle();
		if (projectHandle != null) {
			projectNameField.setText(projectHandle.getName());
		} else {
			return;
		}
		if (!visibilityCheck) {
			File file = new File(selectedDirectory + "/.beproject");
			if (file.exists()) {
				try {
					EObject eObj = IndexUtils.loadEObject(file.toURI());
					if (eObj instanceof StudioProjectConfiguration) {
						StudioProjectConfiguration config = (StudioProjectConfiguration) eObj;
						XPATH_VERSION version = config.getXpathVersion();
						if (version == XPATH_VERSION.XPATH_10) {
							xpathVersionCombo.select(0);
						} else {
							xpathVersionCombo.select(1);
						}
					}
				} catch (Exception e) {
				}
			}
		}
		
		if (!copyIntoWorkspaceButton.getSelection()) {
			importLocationField.setText(selectedDirectory);
		} else {
			importLocationField.setText(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString()+"/"+projectHandle.getName());
		}

	}

    public File getExistingStudioProjectArchive() {
		if (directoryPathField == null || directoryPathField.getText() == null) {
			return null;
		}
		String fileName = directoryPathField.getText();
		if (fileName.endsWith(".project") && fileName.lastIndexOf(File.separator) != -1) { //$NON-NLS-1$
			fileName = fileName.substring(0, fileName.lastIndexOf(File.separator));
		}
		return new File(fileName);
	}
    
	protected boolean validatePage() {
        setErrorMessage(null);
        setMessage(null);
		if (directoryPathField.getText() == null || directoryPathField.getText().length() == 0) {
			setMessage(Messages.getString("ExistingStudioProjectImportWizardPage.SelectProjectToImport")); 
			setPageComplete(false);
			this.canFlipToNextPage = false;
			getContainer().updateButtons();
			return false;
		}
		
		if (!new File(directoryPathField.getText()).exists()) {
			setErrorMessage(Messages.getString("ExistingStudioProjectImportWizardPage.Error.InvalidArchiveLocation")); 
			setPageComplete(false);
			this.canFlipToNextPage = false;
			getContainer().updateButtons();
			return false;
		}
		
		if (!projectFileExists()) {
			setErrorMessage(Messages.getString("ExistingStudioProjectImportWizardPage.Error.InvalidArchiveLocation")); 
			setPageComplete(false);
			this.canFlipToNextPage = false;
			getContainer().updateButtons();
			return false;
		}
		
		// check whether the project name is valid
		final IStatus nameStatus= ResourcesPlugin.getWorkspace().validateName(getProjectName(), IResource.PROJECT);
		if (!nameStatus.isOK()) {
			String problemMessage = Messages.getString("BE_Project_invalidname", getProjectName());
//			setErrorMessage(nameStatus.getMessage());
			setErrorMessage(problemMessage);
			setPageComplete(false);
			this.canFlipToNextPage = false;
			getContainer().updateButtons();
			return false;
		}
		
		 //checking duplicate project names irrespective of cases
		for(IProject project:ResourcesPlugin.getWorkspace().getRoot().getProjects()){
			if(project.getName().equalsIgnoreCase(getProjectName())){
				setErrorMessage("A project with that name already exists in the workspace.");
				setPageComplete(false);
				this.canFlipToNextPage = false;
				getContainer().updateButtons();
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
    			setPageComplete(false);
    			
    			this.canFlipToNextPage = false;
    			getContainer().updateButtons();
        		return false;
        	}
        }
        
        if (importLocationField.getText() == null) {
        	String problemMessage = Messages.getString("ExistingStudioProjectImportWizardPage.InvalidTargetLocation.error");
        	setErrorMessage(problemMessage);
			setPageComplete(false);
			this.canFlipToNextPage = false;
			getContainer().updateButtons();
        	return false;
        }
        
        Path origPath = new Path(directoryPathField.getText());
        Path targetPath = new Path(importLocationField.getText());
        if (origPath.isPrefixOf(targetPath) && !origPath.equals(targetPath)) {
        	String problemMessage = Messages.getString("ExistingStudioProjectImportWizardPage.OverlappingLocation.error");
        	setErrorMessage(problemMessage);
        	setPageComplete(false);
        	this.canFlipToNextPage = false;
        	getContainer().updateButtons();
        	return false;
        }

        if (directoryPathField.getText().equalsIgnoreCase(importLocationField.getText())) {
        	String problemMessage = Messages.getString("ExistingStudioProjectImportWizardPage.ModifyContents.warning");
        	setMessage(problemMessage, WARNING);
            setPageComplete(true);
            this.canFlipToNextPage = true;
            getContainer().updateButtons();
    		return true;
        } else if (new File(importLocationField.getText()).exists()) {
        	if (!getProjectName().equals(targetPath.lastSegment())) {
        		if (origPath.removeLastSegments(1).equals(targetPath)) {
        			String problemMessage = Messages.getString("ExistingStudioProjectImportWizardPage.ModifyContents.warning");
        			setMessage(problemMessage, WARNING);
        			setPageComplete(true);
        			this.canFlipToNextPage = true;
        			getContainer().updateButtons();
        			return true;
        		} else if (new File(importLocationField.getText(), getProjectName()).exists()) {
        			String problemMessage = Messages.getString("ExistingStudioProjectImportWizardPage.TargetExists.error");
        			setErrorMessage(problemMessage);
        			setPageComplete(false);
        			this.canFlipToNextPage = false;
        			getContainer().updateButtons();
        			return false;
        		}
        	} else {
        		String problemMessage = Messages.getString("ExistingStudioProjectImportWizardPage.TargetExists.error");
        		setErrorMessage(problemMessage);
        		setPageComplete(false);
        		this.canFlipToNextPage = false;
        		getContainer().updateButtons();
        		return false;
        	}
        }
        
        if (!getProjectName().equals(targetPath.lastSegment())) {
        	String problemMessage = Messages.getString("ExistingStudioProjectImportWizardPage.AppendProjectName.info");
        	setMessage(problemMessage, INFORMATION);
        	setPageComplete(true);
        	this.canFlipToNextPage = true;
        	getContainer().updateButtons();
        	return true;
        }
        
//		if (directoryPathField.getStringValue().toLowerCase().endsWith(".ear")) {
//			String problemMessage = Messages.getString("ExistingStudioProjectImportWizardPage.Unsupported.EAR.error");
//    		setErrorMessage(problemMessage);
//    		return false;
//		}
        setPageComplete(true);
    	this.canFlipToNextPage = true;
        getContainer().updateButtons();
		return true;
	}

	private boolean projectFileExists() {
		String projectFilePath = directoryPathField.getText() + File.separator + ".project";
		File projectFile = new File(projectFilePath);
		return projectFile.exists();
	}

	IPath getLocationPath() {
		if (importLocationField.getText() != null) {
			IPath p = new Path(importLocationField.getText());
			if (!getProjectName().equalsIgnoreCase(p.lastSegment())) {
				p = p.append(getProjectName());
			}
			return p;
		}
		return null;
	}

	String getProjectName() {
		if (directoryPathField.getText() != null) {
			String path = directoryPathField.getText();
			Path p = new Path(path);
			return p.lastSegment();
		}
		return projectNameField.getText();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validateLinkedResource()
	 */
	protected IStatus validateLinkedResource() {
		return new Status(IStatus.OK, "com.tibco.cep.studio.ui", IStatus.OK, "", null); //NON-NLS-1 //NON-NLS-2 //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public String queryOverwrite(String pathString) {
		return "Overwriiiite?";
	}

	public boolean importIntoWorkspace() {
		return copyIntoWorkspaceButton.getSelection();
	}

	public IProject getProjectHandle() {
		if (getProjectName() == null || getProjectName().length() == 0) {
			return null;
		}
		return ResourcesPlugin.getWorkspace().getRoot().getProject(
				getProjectName());
	}	
	
	@Override
	public boolean canFlipToNextPage() {
		if(this.canFlipToNextPage) {
			if(this.getNextPage() == null) {
				return false;
			}
		}
		return this.canFlipToNextPage;
	}
	
	
}