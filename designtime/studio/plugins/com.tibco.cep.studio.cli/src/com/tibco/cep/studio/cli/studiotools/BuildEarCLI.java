package com.tibco.cep.studio.cli.studiotools;


import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.impl.DecisionTableElementImpl;
import com.tibco.cep.studio.core.index.model.impl.EntityElementImpl;
import com.tibco.cep.studio.core.index.model.impl.SharedDecisionTableElementImpl;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.cep.studio.core.util.packaging.impl.EMFEarPackager;
import com.tibco.cep.studio.core.util.packaging.impl.PackagingHelper;

/*
@author ssailapp
@date Sep 8, 2009 3:12:11 PM
 */

public class BuildEarCLI extends CoreCLI {

	// Build EAR flags
	private final static String OPERATION_BUILDEAR = "buildEar";
	private final static String FLAG_BUILDEAR_HELP = "-h"; 	// Prints help
	private final static String FLAG_BUILDEAR_OVERWRITE = "-x"; 	// Overwrite output file if is exists
	private final static String FLAG_BUILDEAR_OUTPUTFILE = "-o"; 	// Output file for the ear  
	private final static String FLAG_BUILDEAR_PROJECTPATH = "-p"; 	// Path of the Studio project
	private final static String FLAG_BUILDEAR_STRICT = "-strict";	//parameter to enable reserved words check
	private final static String FLAG_BUILDEAR_PROJECT_LIB_PATH= "-pl"; 	// Project Libs <plibpath><path sep><plibpath>
	private final static String FLAG_BUILDEAR_PROJECTNAME = "-n"; 	// Name of the Studio project
	private final static String FLAG_GENERATE_CLASS_EXTENDED_CP = "-cp"; // extended classpath
	private final static String FLAG_GENERATE_USE_LEGACY_COMPILER = "-lc"; // use legacy compiler
	private final static String FLAG_INCLUDE_JAVA_CP = "-jc"; // include java classpath
	
	private final static String EAR_FILE_EXTENSION = ".ear";
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0 || 
				(args.length == 1 && args[0].trim().endsWith("help"))) {
			StudioCommandLineInterpreter.printPrologue(new String[]{});
			System.out.println(new BuildEarCLI().getHelp());
			return;
		}
		String newArgs[] = new String[args.length+2];
		newArgs[0] = CoreCLI.OPERATION_CATEGORY_CORE;
		newArgs[1] = OPERATION_BUILDEAR; 
		System.arraycopy(args, 0, newArgs, 2, args.length);
		StudioCommandLineInterpreter.executeCommandLine(newArgs);
	}
	
	@Override
	public String[] getFlags() {
		return new String[] { FLAG_BUILDEAR_HELP,
				FLAG_BUILDEAR_OVERWRITE, 
				FLAG_GENERATE_USE_LEGACY_COMPILER,
				FLAG_INCLUDE_JAVA_CP,
				FLAG_BUILDEAR_OUTPUTFILE,
				FLAG_BUILDEAR_PROJECTPATH,
				FLAG_BUILDEAR_PROJECTNAME,
				FLAG_BUILDEAR_PROJECT_LIB_PATH,
				FLAG_GENERATE_CLASS_EXTENDED_CP,
				FLAG_BUILDEAR_STRICT};
	}

	@Override
	public String getHelp() {
		String helpMsg = "Usage: buildear " + getUsageFlags() + "\n" +
				"where, \n" +
				"	-h (optional) prints this usage \n" +
				"	-x (optional) overwrites the specified output file if it exists. \n" +
				"	-lc (optional) use legacy compiler \n" +
				"	-jc (optional) include the java classpath of the project, if available\n" +
				"	-o (optional) specifies the output file for the archive. If not specified, the output file is named as <projectName>.ear. \n" +
				"	-p specifies project \n"+
				"   -pl (optional) specifies the list of project library file path separated by a path separator \n"+
				"   -cp (optional) extended classpath";
        return helpMsg;
	}

	@Override
	public String getOperationFlag() {
		return OPERATION_BUILDEAR;
	}

	@Override
	public String getOperationName() {
		return ("Build Enterprise Archive");
	}
	
	public String getUsageFlags() {
		return ("[" + FLAG_BUILDEAR_HELP + "] " +
				"[" + FLAG_BUILDEAR_OVERWRITE + "] " +
				"[" + FLAG_GENERATE_USE_LEGACY_COMPILER + "] " +
				"[" + FLAG_INCLUDE_JAVA_CP + "] " +
				"[" + FLAG_BUILDEAR_OUTPUTFILE + " <outputArchiveFile>] " +
				FLAG_BUILDEAR_PROJECTPATH + " <projectDir> "+
				FLAG_BUILDEAR_PROJECT_LIB_PATH+ "<project lib path><path separator><project lib path>"+
				FLAG_GENERATE_CLASS_EXTENDED_CP+ "<extended classpath>");		
	}

	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		if (argsMap.containsKey(FLAG_BUILDEAR_HELP)) {
			System.out.println(getHelp());
			return true;
		}
		
		boolean overwriteEarFile = false;
		if (argsMap.containsKey(FLAG_BUILDEAR_OVERWRITE)) {
			overwriteEarFile = true;
		}

		String projectPath = argsMap.get(FLAG_BUILDEAR_PROJECTPATH);
		if (projectPath == null) {
			throw new Exception(Messages.getString("BuildEar.ProjectPath.Invalid"));
		}
		if (!new File(projectPath).exists()) {
			throw new Exception(Messages.getString("BuildEar.ProjectPath.NotFound"));
		}

		String projectName = argsMap.get(FLAG_BUILDEAR_PROJECTNAME);
		
		String earFilePath = argsMap.get(FLAG_BUILDEAR_OUTPUTFILE);
		if (earFilePath == null) {
			earFilePath = projectPath + "/" + new File(projectPath).getName() + EAR_FILE_EXTENSION;
		}
		if (new File(earFilePath).exists()) {
			if (!overwriteEarFile) {
				overwriteEarFile = ConsoleInput.readYesNo(Messages.getString("BuildEar.EarFilePath.Exists"),false);
				if(!overwriteEarFile) {
					return true;
				}
				
			}
		}
		String projLibPath = argsMap.get(FLAG_BUILDEAR_PROJECT_LIB_PATH);
		String [] projlibpaths = null;
		if (projLibPath != null) {
			projlibpaths = projLibPath.split(File.pathSeparator);
			for (String plibpath:projlibpaths) {
				if (!new File(plibpath).exists()) {
					throw new Exception(Messages.getString("BuildEar.ProjectLibPath.NotFound"));
				}
			}
		}
		
		String extendedCP = argsMap.get(FLAG_GENERATE_CLASS_EXTENDED_CP);
		
		boolean useLegacyCompiler = argsMap.containsKey(FLAG_GENERATE_USE_LEGACY_COMPILER);
		boolean includeJavaClasspath = argsMap.containsKey(FLAG_INCLUDE_JAVA_CP);
			
		try {
			if (projectName == null) {
				projectName = new File(projectPath).getName();
			}
			setTempDir(projectName); //Set tmp dir
			
			final EMFProject project = new EMFProject(projectPath);
			/**
			 * There is no need to set name for the index as it will be derived from the repo 
			 * path passed to the EMFProject in the preload section
			 */
			if (projlibpaths!=null && projlibpaths.length>0) {
				project.setProjectLibraries(projlibpaths);
			}
			project.setStudionProjectConfigManager(StudioProjectConfigurationManager.getInstance());
			project.load();
			project.removeStudionProjectConfigManager();
			/**
			 * The project load should take care of initializing the projectConfiguration and
			 * EnterpriseArchiveConfiguration names if .beproject is not found for RMS deployments
			 * as the project name will be derived from the .ear file name or the unzipped EAR folder
			 */
			project.getProjectConfiguration().getEnterpriseArchiveConfiguration().setPath(earFilePath);
			project.getProjectConfiguration().getEnterpriseArchiveConfiguration().setOverwrite(overwriteEarFile);
			
			PackagingHelper.addExtendedClasspathEntires(extendedCP,project.getName());
			PackagingHelper.addExtendedClasspathEntires(System.getProperty("java.class.path"),project.getName());
			PackagingHelper.loadExtendedClasspathOntologyFunctions(extendedCP,project);
			
			checkDuplicateDTInProjectLibrary(project.getName());
			if (argsMap.containsKey(FLAG_BUILDEAR_STRICT)) {
				checkForReservedWords(project.getName());
			}
			
			boolean delProject = false;
			if (includeJavaClasspath) {
				delProject = ensureProjectExists(projectPath, project.getName());
			}
			
			EMFEarPackager packager = new EMFEarPackager(project, true,extendedCP, useLegacyCompiler);
			packager.close();
			
			if (delProject) {
				// the IProject was created in order to obtain the java classpath.  Now remove the project after the run
				final IProject wsProj = ResourcesPlugin.getWorkspace().getRoot().getProject(project.getName());
				if (wsProj.exists()) {
					IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
						
						@Override
						public void run(IProgressMonitor monitor) throws CoreException {
							try {
								System.out.println("\n" + Messages.getString("BuildEar.CleanUp"));
								wsProj.delete(false, true, null);
							} catch (CoreException e) {
								e.printStackTrace();
							}
						}
					};
					try {
						ResourcesPlugin.getWorkspace().run(runnable, ResourcesPlugin.getWorkspace().getRoot(), IWorkspace.AVOID_UPDATE, null);
					} catch ( Exception e) {
						// Exceptions are handled by runnable
					}
				}
			}
			System.out.println("\n" + Messages.getString("BuildEar.Success"));
		} catch (Exception e) {
			throw new Exception(Messages.getString("BuildEar.Error") + " - " + e.getMessage(),e);
		}
		return true;
	}

	/**
	 * ensure that the project exists so that the java classpath can be added to the build path
	 * return whether the project was created
	 * @param projectPath
	 * @param name
	 * @return
	 */
	private boolean ensureProjectExists(final String projectPath, String name) {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		if (!project.exists()) {
			// delete the project after running in this case?
			final boolean[] status = new boolean[1];
			IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
				
				@Override
				public void run(IProgressMonitor monitor) throws CoreException {
					try {
						IProjectDescription description = ResourcesPlugin.getWorkspace().loadProjectDescription(new Path(projectPath).append(IProjectDescription.DESCRIPTION_FILE_NAME));
						project.create(description , null);
						project.open(null);
						status[0] = project.isAccessible();
					} catch (CoreException e) {
					}
				}
			};
			try {
				ResourcesPlugin.getWorkspace().run(runnable, ResourcesPlugin.getWorkspace().getRoot(), IWorkspace.AVOID_UPDATE, null);
			} catch ( Exception e) {
				// Exceptions are handled by runnable
			}
			return status[0];
		} 

		return false;
	}

	/**
	 * Set the user temp directory to project specific folder
	 * 
	 * @param projectName
	 */
	private void setTempDir(String projectName){
		String tmpDirPath = System.getProperty("java.io.tmpdir");
		if (!tmpDirPath.endsWith(File.separator)) {
			tmpDirPath = tmpDirPath + File.separator;
		}
		tmpDirPath = tmpDirPath + projectName;
		System.setProperty("java.io.tmpdir", tmpDirPath);		
	}
	
	/**
	 * Check for duplicate DT's
	 * 
	 * @param projectName
	 */
	private static void checkDuplicateDTInProjectLibrary(String projectName) throws Exception {
    	DecisionTableElement dtImpl, compareDT = null;
    	TableImpl table1, table2 = null;
    	
    	List<DesignerElement> tables = IndexUtils.getAllElements(projectName, ELEMENT_TYPES.DECISION_TABLE);
    	
    	for (DesignerElement de : tables) {
    		if (de instanceof DecisionTableElement) {
    			dtImpl = ((DecisionTableElement)de);
    			table1 = (TableImpl) ((dtImpl instanceof SharedDecisionTableElementImpl) ? ((SharedDecisionTableElementImpl)dtImpl).getSharedImplementation() : ((DecisionTableElementImpl)dtImpl).getImplementation());
    			for (DesignerElement compareDE : tables) {
    				if (de != compareDE && compareDE instanceof DecisionTableElement) {
    					compareDT = ((DecisionTableElement)compareDE);
    					if ((dtImpl.getFolder()+dtImpl.getName()).equals(compareDT.getFolder()+compareDT.getName())) {    						
    						table2 = (TableImpl) ((compareDT instanceof SharedDecisionTableElementImpl) ? ((SharedDecisionTableElementImpl)compareDT).getSharedImplementation() : ((DecisionTableElementImpl)compareDT).getImplementation());
    						if (table1.getImplements().equals(table2.getImplements())) {
    							throw new Exception("Duplicate Decision Table '" + table2.getName() + "' found.");
    						}
    					}
    				}
    			}
    		}
    	}
    }
	
	private static void checkForReservedWords(String projectName) throws Exception{
		EntityElementImpl entityImpl = null;
    	List<DesignerElement> eventList = IndexUtils.getAllElements(projectName, ELEMENT_TYPES.SIMPLE_EVENT);
    	List<DesignerElement> conceptList = IndexUtils.getAllElements(projectName, ELEMENT_TYPES.CONCEPT);
     	for (DesignerElement de : eventList) {
    		if (de instanceof EntityElementImpl) {
    			entityImpl = ((EntityElementImpl)de);
    			Event event=(Event) entityImpl.getEntity();
    			EList<PropertyDefinition> propertyList=event.getAllUserProperties();
     			for(PropertyDefinition pd : propertyList){
    				if(EntityNameHelper.KEYWORDS.contains(pd.getName())){
    					throw new Exception("Cannot build Ear, Reserved keyword '" + pd.getName() + "' found.");
    				}
    			}
     		}
    	}
     	for (DesignerElement de : conceptList) {
    		if (de instanceof EntityElementImpl) {
    			entityImpl = ((EntityElementImpl)de);
    			Concept concept=(Concept) entityImpl.getEntity();
    			EList<PropertyDefinition> propertyList=concept.getAllProperties();
     			for(PropertyDefinition pd : propertyList){
    				if(EntityNameHelper.KEYWORDS.contains(pd.getName())){
    					throw new Exception("Cannot build Ear, Reserved keyword '" + pd.getName() + "' found.");
    				}
    			}
     		}
    	}
	}
}
