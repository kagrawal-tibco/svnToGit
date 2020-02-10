package com.tibco.cep.studio.ui.actions;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.util.StudioProjectMigrationUtils;
import com.tibco.cep.studio.core.util.StudioProjectUtil;

public class ImportExistingStudioProjectOperation extends WorkspaceModifyOperation {

	private IProject fProject;
	private File fImportedFile;
	private File fTargetLocation;
	private boolean fUseDefaults;
	private boolean isImport = false;
	private Map<String, String> httpProperties = null;
	private XPATH_VERSION xpathVersion;

	/**
	 * @param newProj
	 * @param targetLocation
	 * @param useDefaults
	 */
	public ImportExistingStudioProjectOperation(IProject newProj, 
			                              File targetLocation, 
			                              boolean useDefaults) {
		super();
		this.fProject = newProj;
		this.fUseDefaults = useDefaults;
		this.fTargetLocation = targetLocation;
	}
	
	/**
	 * @param newProj
	 * @param fileToImport
	 * @param targetLocation
	 * @param useDefaults
	 */
	public ImportExistingStudioProjectOperation(IProject newProj, 
			                              File fileToImport, 
			                              File targetLocation, 
			                              Map<String, String> httpProperties,
			                              boolean useDefaults, 
			                              XPATH_VERSION xpath_version) {
		super();
		this.fProject = newProj;
		this.fImportedFile = fileToImport;
		this.fTargetLocation = targetLocation;
		this.fUseDefaults = useDefaults;
		this.httpProperties = httpProperties;
		isImport = true;
		this.xpathVersion = xpath_version;
	}

	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
		if(isImport){
			migrateProject(monitor);
		}else{
			createProject(monitor);
		}
	}

	public IStatus createProject(IProgressMonitor monitor){
		try {
			
			monitor.beginTask("Creating " + fProject.getName(), 100);
			doProjectCreation(monitor);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		monitor.done();
		return Status.OK_STATUS;
	}
	
	
	public IStatus migrateProject(IProgressMonitor monitor) {
		try {
			monitor.beginTask("Migrating " + fImportedFile.getName(), 5);
			StudioProjectMigrationUtils.copyProjectFiles(fImportedFile, fTargetLocation, monitor);
			SubProgressMonitor spm = new SubProgressMonitor(monitor, 3);
			// Call project file migration extension elements
			StudioProjectMigrationUtils.convertStudioProject(fTargetLocation, httpProperties, xpathVersion, spm);
			spm.done();
			doProjectCreation(monitor);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		monitor.done();
		return Status.OK_STATUS;
	}
	
	/**
	 * @param monitor
	 * @throws Exception
	 */
	protected void doProjectCreation(final IProgressMonitor monitor)throws Exception{
		monitor.setTaskName("Creating project");
		if (!fProject.exists()) {
			// Use existing .project settings if they exist
			String projectFilePath = fImportedFile.getAbsolutePath() + File.separator + ".project";
			File projectFile = new File(projectFilePath);
			IProjectDescription description = null;
			if (projectFile.exists()) {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(projectFile);
					description = ResourcesPlugin.getWorkspace().loadProjectDescription(fis);
				} catch (Exception e) {
				} finally {
					fis.close();
				}
			}
			IPath parentDirpath = new Path(fTargetLocation.getAbsolutePath());
			parentDirpath = parentDirpath.removeLastSegments(1);
			
			StudioProjectUtil.createProject(fProject, description, monitor, parentDirpath.toFile(), false, isImport, fUseDefaults, xpathVersion);

//			if (description == null) {
//				description = ResourcesPlugin.getWorkspace().newProjectDescription(fProject.getName());
//				String[] natures = description.getNatureIds();
//
//				String[] newNatures = new String[natures.length + 1];
//				System.arraycopy(natures, 0, newNatures, 0, natures.length);
//
//				newNatures[natures.length] = StudioProjectNature.STUDIO_NATURE_ID;
//				
//				description.setNatureIds(newNatures);
//				addBuilder(description);
//			}
//			if (!fUseDefaults) {
//				Path path = new Path(fTargetLocation.getAbsolutePath());
//				description.setLocation(path);
//			}
//			fProject.create(description, new SubProgressMonitor(monitor, 5)); 
//			fProject.open(new SubProgressMonitor(monitor, 5));
//			monitor.setTaskName("Refreshing project");
//			fProject.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(monitor, 5));
		}
	}

//	private void addBuilder(IProjectDescription desc) throws CoreException {
//		ICommand[] commands = desc.getBuildSpec();
//		for (int i = 0; i < commands.length; ++i) {
//			if (commands[i].getBuilderName().equals(StudioProjectBuilder.BUILDER_ID)) {
//				return;
//			}
//		}
//
//		ICommand[] newCommands = new ICommand[commands.length + 1];
//		System.arraycopy(commands, 0, newCommands, 0, commands.length);
//		ICommand command = desc.newCommand();
//		command.setBuilderName(StudioProjectBuilder.BUILDER_ID);
//		newCommands[newCommands.length - 1] = command;
//		desc.setBuildSpec(newCommands);
//	}

}
