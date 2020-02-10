package com.tibco.cep.studio.ui.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.util.MutableOntologyConverter;
import com.tibco.cep.studio.core.util.StudioProjectMigrationUtils;
import com.tibco.cep.studio.core.util.StudioProjectUtil;

public class CreateStudioProjectOperation extends WorkspaceModifyOperation {

	private IProject fProject;
	private File fImportedFile;
	private File fTargetLocation;
	private boolean fUseDefaults;
	private boolean isImport = false;
	private Map<String, String> httpProperties = null;
	private XPATH_VERSION xpath_version = XPATH_VERSION.XPATH_20;

	/**
	 * @param newProj
	 * @param targetLocation
	 * @param useDefaults
	 */
	public CreateStudioProjectOperation(IProject newProj, 
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
	 * @param httpProperties
	 * @param xpath_version
	 * @param useDefaults
	 */
	public CreateStudioProjectOperation(IProject newProj, 
			                              File fileToImport, 
			                              File targetLocation,
			                              Map<String, String> httpProperties,
			                              XPATH_VERSION xpath_version,
			                              boolean useDefaults) {
		super();
		this.fProject = newProj;
		this.fImportedFile = fileToImport;
		this.fTargetLocation = targetLocation;
		this.fUseDefaults = useDefaults;
		this.httpProperties = httpProperties;
		this.xpath_version = xpath_version;
		isImport = true;
	}

	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
		if(isImport){
			convertOntology(monitor);
			fProject.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			StudioProjectMigrationUtils.convertStudioProject(fTargetLocation, this.httpProperties, xpath_version, monitor);
			fProject.refreshLocal(IResource.DEPTH_INFINITE, monitor);
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
	
	
	public IStatus convertOntology(IProgressMonitor monitor) {
		try {
			monitor.beginTask("Importing " + fImportedFile.getName(), 100);
			SubProgressMonitor spm = new SubProgressMonitor(monitor, 80);
			MutableOntologyConverter.convertOntology(fImportedFile, fProject.getName(), fTargetLocation, spm);
			
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
		if (!fProject.exists()) {
			
			StudioProjectUtil.createProject(fProject, null, monitor, fTargetLocation, isImport, false, fUseDefaults, xpath_version);
			
//			boolean addJavaNature = false; // TODO : Set based on system property or flag on wizard?
//			//Adding ProjectNature for the Designer Project
//			IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(fProject.getName());
//			if (!fUseDefaults) {
//				String fPath = fTargetLocation.getAbsolutePath();
//				if (!isImport) {
//					fPath += File.separator +  fProject.getName();
//				}
//				Path path = new Path(fPath);
//				description.setLocation(path);
//			}
//
//			String[] natures = description.getNatureIds();
//
//			/*int delta = addJavaNature ? 2 : 1;
//			String[] newNatures = new String[natures.length + delta];
//			System.arraycopy(natures, 0, newNatures, 0, natures.length);
//
//			newNatures[natures.length] = StudioProjectNature.STUDIO_NATURE_ID;
//			if (addJavaNature) {
//				newNatures[natures.length + 1] = JavaCore.NATURE_ID;
//			}*/
//			
//			List<String> natureList = StudioProjectUtil.getStudioProjectNatures();
//			String[] newNatures = new String[natures.length + natureList.size()];
//			System.arraycopy(natures, 0, newNatures, 0, natures.length);
//			for(int iNature=0; iNature < natureList.size() ; iNature++){
//				newNatures[natures.length+iNature] = natureList.get(iNature);
//			}
//
//			description.setNatureIds(newNatures);
//			addBuilder(description, addJavaNature);
//			fProject.create(description, new SubProgressMonitor(monitor, 5)); 
//			fProject.open(new SubProgressMonitor(monitor, 5));
//			
//			StudioProjectUtil.creatProject(fProject);
//			createDefaultArtifacts();
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

//	private void addBuilder(IProjectDescription desc, boolean addJavaBuilder) throws CoreException {
//		ICommand[] commands = desc.getBuildSpec();
//		boolean hasStudioBuilder = false;
//		boolean hasJavaBuilder = !addJavaBuilder;
//		
//		for (int i = 0; i < commands.length; ++i) {
//			if (commands[i].getBuilderName().equals(StudioProjectBuilder.BUILDER_ID)) {
//				if (hasJavaBuilder) {
//					return;
//				}
//				hasStudioBuilder = true;
//			}
//			if (commands[i].getBuilderName().equals(JavaCore.BUILDER_ID)) {
//				if (hasStudioBuilder) {
//					return;
//				}
//				hasJavaBuilder = true;
//			}
//		}
//
//		/*int delta = addJavaBuilder ? 2 : 1;
//		ICommand[] newCommands = new ICommand[commands.length + delta];
//		System.arraycopy(commands, 0, newCommands, 0, commands.length);
//		ICommand command = desc.newCommand();
//		command.setBuilderName(StudioProjectBuilder.BUILDER_ID);
//		newCommands[newCommands.length - delta] = command;
//		if (addJavaBuilder) {
//			ICommand javacommand = desc.newCommand();
//			javacommand.setBuilderName(JavaCore.BUILDER_ID);
//			newCommands[newCommands.length - 1] = javacommand;
//		}*/
//		
//		List<String> builderList = StudioProjectUtil.getStudioProjectBuilders();
//		ICommand[] newCommands = new ICommand[commands.length + builderList.size()];
//		System.arraycopy(commands, 0, newCommands, 0, commands.length);
//		for(int iBuilder=0; iBuilder < builderList.size() ; iBuilder++){
//			ICommand command = desc.newCommand();
//			command.setBuilderName(builderList.get(iBuilder));
//			newCommands[commands.length+iBuilder] = command;
//		}
//
//		desc.setBuildSpec(newCommands);
//	}

	


}
