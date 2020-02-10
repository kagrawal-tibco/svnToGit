/**
 * 
 */
package com.tibco.cep.studio.rms.cli;

import java.io.File;
import java.util.Map;

import com.tibco.be.rms.repo.ManagedEMFProject;
import com.tibco.cep.studio.cli.studiotools.Messages;
import com.tibco.cep.studio.core.util.packaging.impl.EMFEarPackager;
import com.tibco.cep.studio.core.util.packaging.impl.PackagingHelper;

/**
 * CLI class to build an EARs for a specified project
 * 
 * @author Vikram Patil
 */
public class RMSBuildEarCLI extends RMSCLI {

	// Build EAR flags
	private final static String OPERATION_BUILDEAR = "buildEar";
	private final static String FLAG_BUILDEAR_HELP = "-h"; 	// Prints help
	private final static String FLAG_BUILDEAR_OVERWRITE = "-x"; 	// Overwrite output file if is exists
	private final static String FLAG_BUILDEAR_OUTPUTFILE = "-o"; 	// Output file for the ear  
	private final static String FLAG_BUILDEAR_PROJECTPATH = "-p"; 	// Path of the Studio project
	private final static String FLAG_BUILDEAR_PROJECT_LIB_PATH= "-pl"; 	// Project Libs <plibpath><path sep><plibpath>
	private final static String FLAG_BUILDEAR_PROJECTNAME = "-n"; 	// Name of the Studio project
	private final static String FLAG_GENERATE_CLASS_EXTENDED_CP = "-cp"; // extended classpath
	private final static String FLAG_GENERATE_DT_CLASS_USEFS = "-lc"; // use legacy compiler

	private final static String EAR_FILE_EXTENSION = ".ear";
		
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getFlags()
	 */
	@Override
	public String[] getFlags() {
		return new String[] { FLAG_BUILDEAR_HELP,
				FLAG_BUILDEAR_OVERWRITE, 
				FLAG_BUILDEAR_OUTPUTFILE,
				FLAG_BUILDEAR_PROJECTPATH,
				FLAG_BUILDEAR_PROJECTNAME,
				FLAG_GENERATE_DT_CLASS_USEFS,
				FLAG_BUILDEAR_PROJECT_LIB_PATH,
				FLAG_GENERATE_CLASS_EXTENDED_CP};
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getOperationFlag()
	 */
	@Override
	public String getOperationFlag() {
		return OPERATION_BUILDEAR;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getOperationName()
	 */
	@Override
	public String getOperationName() {
		return ("Build Enterprise Archive for RMS");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getUsageFlags()
	 */
	@Override
	public String getUsageFlags() {
		return ("[" + FLAG_BUILDEAR_HELP + "] " +
				"[" + FLAG_BUILDEAR_OVERWRITE + "] " +
				"[" + FLAG_GENERATE_DT_CLASS_USEFS + "] " +
				"[" + FLAG_BUILDEAR_OUTPUTFILE + " <outputArchiveFile>] " +
				FLAG_BUILDEAR_PROJECTPATH + " <projectDir> " + 
				FLAG_BUILDEAR_PROJECT_LIB_PATH+ "<project lib path><path separator><project lib path>"+
				FLAG_GENERATE_CLASS_EXTENDED_CP+ "<extended classpath>");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getHelp()
	 */
	@Override
	public String getHelp() {
		String helpMsg = "Usage: buildear " + getUsageFlags() + "\n" +
				"where, \n" +
				"	-h (optional) prints this usage \n" +
				"	-x (optional) overwrites the specified output file if it exists. \n" +
				"	-o (optional) specifies the output file for the archive. If not specified, the output file is named as <projectName>.ear. \n" +
				"	-p specifies project \n"+
				"	-lc Use legacy compiler \n"+
				"   -pl (optional) specifies the list of project library file path separated by a path separator \n"+
				"   -cp extended classpath";
        return helpMsg;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#runOperation(java.util.Map)
	 */
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
		if (new File(earFilePath).exists() && !overwriteEarFile) {
			throw new Exception(Messages.getString("BuildEar.EarFilePath.Exists"));
		}
			
		String extendedCP = argsMap.get(FLAG_GENERATE_CLASS_EXTENDED_CP);
		
		final boolean useLegacyCompiler = argsMap.containsKey(FLAG_GENERATE_DT_CLASS_USEFS);
		
		String projLibPath = argsMap.get(FLAG_BUILDEAR_PROJECT_LIB_PATH);
		String [] projlibpaths = null;
		if(projLibPath!=null){
			projlibpaths = projLibPath.split(File.pathSeparator);
			for(String plibpath:projlibpaths){
				if (!new File(plibpath).exists()) {
					throw new Exception("Project Library path not found");
				}
			}
		}
		
		try {
			if (projectName == null) {
				projectName = new File(projectPath).getName();
			}
			final ManagedEMFProject project = new ManagedEMFProject(projectPath, ".");
			if(projlibpaths!=null && projlibpaths.length>0){
				project.setProjectLibraries(projlibpaths);
			}
			project.load();
			
			project.getProjectConfiguration().getEnterpriseArchiveConfiguration().setPath(earFilePath);
			project.getProjectConfiguration().getEnterpriseArchiveConfiguration().setOverwrite(overwriteEarFile);
			
			PackagingHelper.addExtendedClasspathEntires(extendedCP,project.getName());
			PackagingHelper.loadExtendedClasspathOntologyFunctions(extendedCP,project);
			
			EMFEarPackager packager = new EMFEarPackager(project, true,extendedCP, useLegacyCompiler);
			packager.close();
			
			System.out.println("\n" + Messages.getString("BuildEar.Success"));
		} catch (Exception e) {
			throw new Exception(Messages.getString("BuildEar.Error") + " - " + e.getMessage(),e);
		}
		return true;
	}

}
