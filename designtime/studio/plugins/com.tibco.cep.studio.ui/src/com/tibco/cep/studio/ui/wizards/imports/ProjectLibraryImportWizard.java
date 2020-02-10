package com.tibco.cep.studio.ui.wizards.imports;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.update.IBuildPathEntryDelta;
import com.tibco.cep.studio.common.configuration.update.IStudioProjectConfigurationDelta;
import com.tibco.cep.studio.common.configuration.update.ProjectLibraryEntryDelta;
import com.tibco.cep.studio.common.configuration.update.StudioProjectConfigurationDelta;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.util.StudioProjectUtil;
import com.tibco.cep.studio.ui.util.Messages;


public class ProjectLibraryImportWizard extends Wizard implements IImportWizard {
	
	private ProjectLibraryImportWizardPage mainPage;
	private IProject project;
	private StudioProjectConfigurationDelta configurationDelta;
	
	public ProjectLibraryImportWizard() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		/*
		IFile file = mainPage.createNewFile();
        if (file == null)
            return false;
            */
		// get the Project Lib Location
		final String projLibLocation = mainPage.getProjLibLocation();		
		if (projLibLocation == null || projLibLocation.trim().length() == 0){
			return true;
		}
		String projectName = project.getName();	
		addProjectLibrary(projectName, projLibLocation);
//		ProjectLibraryResourceProvider.getInstance().updateProjectLibraryIndex(projLibLocation, projectName);
        return true;
	}
	
	private StudioProjectConfigurationDelta getConfigurationDelta(String projectName) {
		if (this.configurationDelta == null) {
			this.configurationDelta = new StudioProjectConfigurationDelta(StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName), 
					IStudioProjectConfigurationDelta.CHANGED);
		}
		return this.configurationDelta;
	}

	private void addProjectLibrary(String projectName, String libPath) {
		
		if (buildPathEntryExists(projectName, libPath)) {
			// don't add duplicate entries
			return;
		}
		ProjectLibraryEntry entry = ConfigurationFactory.eINSTANCE.createProjectLibraryEntry();
		entry.setEntryType(LIBRARY_PATH_TYPE.PROJECT_LIBRARY);
		entry.setPath(libPath);
		File file = new File(libPath);
		entry.setTimestamp(file.lastModified());
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
        StudioProjectConfigurationDelta configDelta = getConfigurationDelta(projectName);
		ProjectLibraryEntryDelta delta = new ProjectLibraryEntryDelta(entry, IBuildPathEntryDelta.ADDED);
		configDelta.getProjectLibEntries().add(delta);
		configuration.getProjectLibEntries().add(entry);
		try {
			StudioProjectUtil.checkConfiguration(configuration);
			StudioProjectConfigurationManager.getInstance().saveConfiguration(projectName, configuration);
			StudioProjectConfigurationManager.getInstance().fireDelta(configDelta);
			GlobalVariablesMananger.getInstance().updateGlobalVariables(projectName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (MessageDialog.openQuestion(getShell(), Messages.getString("project.buildpath.project.rebuild.title"), 
				Messages.getString("project.buildpath.project.rebuild"))) {
			String name = project.getName();
			final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
			Job buildJob = new Job("Build project") {
			
				@Override
				protected IStatus run(final IProgressMonitor monitor) {
					try {
						project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
					} catch (CoreException e) {
						e.printStackTrace();
					}
					return Status.OK_STATUS;
				}
			};
			buildJob.schedule();
		}

	}
	
	protected boolean buildPathEntryExists(String name, String jarPath) {
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(name);
		EList<ProjectLibraryEntry> entries = configuration.getProjectLibEntries();
		for (ProjectLibraryEntry entry : entries) {
			String path = entry.getPath(entry.isVar());
			if (path.equals(jarPath)) {
				return true;
			}
		}
		return false;
	}

	 
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof IResource){
			IResource resource = (IResource)firstElement;
			project = resource.getProject();
		}
		setWindowTitle("File Import Wizard"); //NON-NLS-1
		setNeedsProgressMonitor(true);
		mainPage = new ProjectLibraryImportWizardPage("Import Project Library", selection, project); //NON-NLS-1
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        super.addPages(); 
        addPage(mainPage);        
    }

    public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

}
