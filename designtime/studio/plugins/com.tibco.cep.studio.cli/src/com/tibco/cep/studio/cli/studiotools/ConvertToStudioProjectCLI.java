/**
 * 
 */
package com.tibco.cep.studio.cli.studiotools;

import java.io.File;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.util.StudioProjectMigrationUtils;
import com.tibco.cep.studio.core.util.StudioProjectUtil;

/**
 * @author mgujrath
 *
 */
public class ConvertToStudioProjectCLI extends CoreCLI{
	
	public final static String OPERATION_CONVERT_TO_JAVA_PROJECT = "convertToJavaProject";
	private final static String FLAG_IMPORT_HELP = "-h";	//HELP
	private final static String FLAG_CONVERT_TO_STUDIO_PROJECT_PATH = "-p";	// Path of project in Studio
	private final static String FLAG_TARGET_DIR	= "-o";
	
	public ConvertToStudioProjectCLI() {
	}
	
	@Override
	public String[] getFlags() {
		// TODO Auto-generated method stub
		return new String[] { FLAG_IMPORT_HELP, FLAG_CONVERT_TO_STUDIO_PROJECT_PATH, FLAG_TARGET_DIR };
	}

	@Override
	public String getOperationFlag() {
		// TODO Auto-generated method stub
		return OPERATION_CONVERT_TO_JAVA_PROJECT;
	}

	@Override
	public String getOperationName() {
		return ("Convert to Java Project");
	}

	@Override
	public String getUsageFlags() {
		return ("[" + FLAG_IMPORT_HELP + "] " +
				FLAG_CONVERT_TO_STUDIO_PROJECT_PATH + " <javaProjDir> " + "[" + FLAG_TARGET_DIR + " <targetProjDir>] ");
	}

	@Override
	public String getHelp() {
		String helpMsg = "Usage: studio-tools " + getOperationCategory() + " " + getOperationFlag() + " " + getUsageFlags() + "\n" +
				"where \n" +
				"    -h (optional) prints this usage \n" +
				"    -p is the absolute path to the directory of the Java project to be converted to Studio Project \n" +
			    "    -o (optional) is the absolute path to the target directory of the Studio project to be converted.  If unspecified, the original project contents will be updated and the project will no longer be a simple java project \n "
				;
		
			return helpMsg;
	}

	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		if (argsMap.containsKey(FLAG_IMPORT_HELP)) {
			System.out.println(getHelp());
			return true;
		}

		String javaProj = argsMap.get(FLAG_CONVERT_TO_STUDIO_PROJECT_PATH);
		if (javaProj == null) {
			throw new Exception(Messages.getString("Import.ProjectPath.Invalid"));
		}

		File javaProjDir = new File(javaProj);
		if (!javaProjDir.exists()) {
			throw new Exception(Messages.getString("Import.ProjectPath.Invalid"));
		}
		
		String targetDir = argsMap.get(FLAG_TARGET_DIR);
		File tdDest=null;
		if(targetDir != null){
			tdDest=new File(targetDir);
		}

		IProject studioProjectInstance=null;
		String projectName = new File(javaProj).getName();
		IWorkspace workspace= ResourcesPlugin.getWorkspace(); 
		IWorkspaceDescription desc= workspace.getDescription(); 
		boolean isAutoBuilding= desc.isAutoBuilding(); 
		if (isAutoBuilding) { 
			desc.setAutoBuilding(false); 
			workspace.setDescription(desc); 
		} 
		studioProjectInstance = workspace.getRoot().getProject(projectName);

		File newSource=null;
		File targetDirFile = null;
		if (targetDir != null) {
			targetDirFile = new File(targetDir);
			
			if (targetDirFile.getName().equals(projectName)/* || targetDirFile.getParentFile().getName().equals(projectName)*/) {
				throw new Exception(Messages.getString("Import.Existing.TargetPath.ProjectName.Exists"));
			}
			
			if (!targetDirFile.getName().equals(javaProjDir.getName())) {
				// append the project name to the path, this is what is expected
				targetDirFile = new File(targetDirFile, javaProjDir.getName());
			}

			// check for existing resources
			if (targetDirFile.exists() && targetDirFile.list().length > 0) {
				throw new Exception(Messages.getString("Import.Existing.TargetPath.Exists"));
			}
			// check for overlapping target/source location
			if (targetDirFile.getAbsolutePath().startsWith(javaProjDir.getAbsolutePath())) {
				throw new Exception(Messages.getString("Import.Existing.TargetPath.Overlaps"));
			}
			
			StudioProjectUtil.createProject(studioProjectInstance, null, new NullProgressMonitor(), tdDest, false, true, false, null);
			StudioProjectMigrationUtils.copyProjectFiles(javaProjDir, targetDirFile, new NullProgressMonitor());
			
		} else {
			targetDirFile = javaProjDir;
			String srcPath=javaProjDir.getAbsolutePath();
			srcPath=srcPath+"_old";
			newSource=new File(srcPath);
			if (!newSource.exists()) {
				newSource.mkdirs();
			}
			StudioProjectMigrationUtils.copyProjectFiles(javaProjDir, newSource, new NullProgressMonitor());
			StudioProjectMigrationUtils.deleteDir(javaProjDir);
			javaProjDir.delete();

			StudioProjectUtil.createProject(studioProjectInstance, null, new NullProgressMonitor(),
					new File(javaProjDir.getParent()), false, true, false, XPATH_VERSION.XPATH_20);
			StudioProjectMigrationUtils.copyProjectFiles(newSource, javaProjDir, new NullProgressMonitor());
			StudioProjectMigrationUtils.deleteDir(newSource);
			newSource.delete();
		}
		
		if (studioProjectInstance!=null && studioProjectInstance.exists()) {
			studioProjectInstance.close(null);
			studioProjectInstance.delete(true, null);
		}
		
		System.out.println("The project is converted to studio project succesfully...");
		return true;
	}

	

}
