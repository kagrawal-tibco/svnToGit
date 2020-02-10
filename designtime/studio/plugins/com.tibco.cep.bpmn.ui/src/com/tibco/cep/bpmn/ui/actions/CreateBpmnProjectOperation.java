package com.tibco.cep.bpmn.ui.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.studio.core.builder.StudioProjectBuilder;
import com.tibco.cep.studio.core.nature.StudioProjectNature;
import com.tibco.cep.studio.core.util.MutableOntologyConverter;

public class CreateBpmnProjectOperation extends WorkspaceModifyOperation {

	private IProject fProject;
	private File fImportedFile;
	private File fTargetLocation;
	private boolean fUseDefaults;
	private boolean isImport = false;

	/**
	 * @param newProj
	 * @param targetLocation
	 * @param useDefaults
	 */
	public CreateBpmnProjectOperation(IProject newProj, 
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
	public CreateBpmnProjectOperation(IProject newProj, 
			                              File fileToImport, 
			                              File targetLocation, 
			                              boolean useDefaults) {
		super();
		this.fProject = newProj;
		this.fImportedFile = fileToImport;
		this.fTargetLocation = targetLocation;
		this.fUseDefaults = useDefaults;
		isImport = true;
	}

	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
		if(isImport){
			convertOntology(monitor);
		}else{
			createProject(monitor);
		}
	}

	public IStatus createProject(IProgressMonitor monitor){
		try {
			
			monitor.beginTask("Creating " + fProject.getName(), 100);
			doProjectCreation(monitor);
			
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		monitor.done();
		return Status.OK_STATUS;
	}
	
	
	public IStatus convertOntology(IProgressMonitor monitor) {
		try {
			monitor.beginTask("Importing " + fImportedFile.getName(), 100);
			SubProgressMonitor spm = new SubProgressMonitor(monitor, 80);
			MutableOntologyConverter.convertOntology(fImportedFile, fProject.getName(), fTargetLocation, spm);
			
			doProjectCreation(monitor);
		
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		monitor.done();
		return Status.OK_STATUS;
	}
	
	/**
	 * @param monitor
	 * @throws Exception
	 */
	protected void doProjectCreation(final IProgressMonitor monitor)throws Exception{
		if (!fProject.exists()) {
			//Adding ProjectNature for the Designer Project
			IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(fProject.getName());
			if (!fUseDefaults) {
				Path path = new Path(fTargetLocation.getAbsolutePath());
				description.setLocation(path);
			}

			String[] natures = description.getNatureIds();

			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);

			newNatures[natures.length-1] = StudioProjectNature.STUDIO_NATURE_ID;
			
			description.setNatureIds(newNatures);
			addBuilder(description);
			fProject.create(description, new SubProgressMonitor(monitor, 5)); 
			fProject.open(new SubProgressMonitor(monitor, 5));
			// this is now done by defining content types
//			fProject.setDefaultCharset(ModelUtils.DEFAULT_ENCODING, new SubProgressMonitor(monitor, 5));
//			fProject.accept(new IResourceVisitor() {
//			
//				@Override
//				public boolean visit(IResource resource) throws CoreException {
//					if (!(resource instanceof IFile)) {
//						return true;
//					}
//					IFile file = (IFile) resource;
//					if (IndexUtils.isSupportedType(file)) {
//						file.setCharset(ModelUtils.DEFAULT_ENCODING, new NullProgressMonitor());
//					}
//					return false;
//				}
//			});
//			fNewProject.refreshLocal(IResource.DEPTH_ONE, monitor);
		}
	}

	private void addBuilder(IProjectDescription desc) throws CoreException {
		ICommand[] commands = desc.getBuildSpec();
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(StudioProjectBuilder.BUILDER_ID)) {
				return;
			}
		}

		ICommand[] newCommands = new ICommand[commands.length + 1];
		System.arraycopy(commands, 0, newCommands, 0, commands.length);
		ICommand command = desc.newCommand();
		command.setBuilderName(StudioProjectBuilder.BUILDER_ID);
		newCommands[newCommands.length - 1] = command;
		desc.setBuildSpec(newCommands);
	}

}
