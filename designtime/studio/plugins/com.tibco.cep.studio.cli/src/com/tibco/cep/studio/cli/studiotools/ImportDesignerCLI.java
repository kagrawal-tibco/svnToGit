package com.tibco.cep.studio.cli.studiotools;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.util.MutableOntologyConverter;
import com.tibco.cep.studio.core.util.StudioProjectMigrationUtils;
import com.tibco.cep.studio.core.util.StudioProjectUtil;

/*
@author ssailapp
@date Sep 8, 2009 5:23:34 PM
 */

public class ImportDesignerCLI extends CoreCLI {

	// Import Designer flags
	private final static String OPERATION_IMPORT = "importDesigner";
	private final static String FLAG_IMPORT_DESIGNER_PROJECT = "-d";	// Location of the Designer project archive
	private final static String FLAG_IMPORT_STUDIO_PROJECT_NAME = "-n";	// Name of new project in Studio
	private final static String FLAG_IMPORT_STUDIO_PROJECT = "-p";	// Path of new project in Studio
	private final static String FLAG_XPATH_VERSION	= "-xp";	// xpath version
	
	public ImportDesignerCLI() {
	}
	
	@Override
	public String[] getFlags() {
		return new String[] { FLAG_IMPORT_DESIGNER_PROJECT,
							  FLAG_IMPORT_STUDIO_PROJECT_NAME, 
							  FLAG_IMPORT_STUDIO_PROJECT, 
							  FLAG_XPATH_VERSION };
	}
	
	@Override
	public String getHelp() {
		String helpMsg = "Usage: studio-tools " + getOperationFlag() + " " + OPERATION_IMPORT + " " + getUsageFlags() + "\n" +
			"where, \n" +
			"    -d   is the absolute path to the Designer project directory \n" + 
			"    -n   (optional) is the name of the Studio project to be created \n" +
			"    -p   is the absolute path to the directory where the Studio project will be created \n" +
		    "    -xp {1.0|2.0} (optional) is the xpath version to be compatible for the Studio project\n" ; 
		return helpMsg;
	}
	
	@Override
	public String getOperationFlag() {
		return OPERATION_IMPORT;
	}
	
	@Override
	public String getOperationName() {
		return ("Import Designer Project");
	}
	
	public String getUsageFlags() {
		return (FLAG_IMPORT_DESIGNER_PROJECT + " <designerProjDir> " + 
				"[" + FLAG_IMPORT_STUDIO_PROJECT_NAME + " <studioProjName>] " + 
				FLAG_IMPORT_STUDIO_PROJECT + " <studioProjDir>" +
				"[" + FLAG_XPATH_VERSION + " <XPATH version>] ");
	}
	
	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		String designerProj = argsMap.get(FLAG_IMPORT_DESIGNER_PROJECT);
		if (designerProj == null) {
			throw new Exception(Messages.getString("Import.DesignerArchive.Invalid"));
		}
		File designerProjDir = new File(designerProj);
		if (!designerProjDir.exists()) {
			throw new Exception(Messages.getString("Import.DesignerArchive.NotFound"));
		}
		
		String xpathv = argsMap.get(FLAG_XPATH_VERSION);
		XPATH_VERSION xpathversion = null;
		if (xpathv != null) {
			xpathversion = XPATH_VERSION.get(xpathv);
			if (xpathversion == null) {
				throw new Exception(Messages.getString("Import.xpath.Invalid"));
			}
		}

		String studioProj = argsMap.get(FLAG_IMPORT_STUDIO_PROJECT);
		if (studioProj == null) {
			throw new Exception(Messages.getString("Import.ProjectPath.Invalid"));
		}

		String studioProjName = argsMap.get(FLAG_IMPORT_STUDIO_PROJECT_NAME);
		if (studioProjName == null) {
			studioProjName = new File(studioProj).getName();
			if (studioProjName == null) {
				throw new Exception(Messages.getString("Import.ProjectName.Invalid"));
			}
		}
		
		File studioProjDir = new File(studioProj);
		if (!studioProjDir.exists())
			studioProjDir.mkdirs();
		
		//Set the transformer for AIX
		if(System.getProperty("os.name").equals("AIX") || System.getProperty("os.arch").equals("s390x")){
			System.setProperty("javax.xml.transform.TransformerFactory","org.apache.xalan.processor.TransformerFactoryImpl");
		}

		try {
			MutableOntologyConverter.convertOntology(designerProjDir, null, studioProjDir, null);
			String projectName = studioProjDir.getName();
			IWorkspace workspace= ResourcesPlugin.getWorkspace(); 
			IWorkspaceDescription desc= workspace.getDescription(); 
			boolean isAutoBuilding= desc.isAutoBuilding(); 
			if (isAutoBuilding) { 
				desc.setAutoBuilding(false); 
				workspace.setDescription(desc); 
			} 
			IProject studioProjectInstance = workspace.getRoot().getProject(projectName);
			try {
				StudioProjectUtil.createProject(studioProjectInstance, null, new NullProgressMonitor(), studioProjDir, true, true, false, xpathversion);
				//StudioProjectMigrationUtils.copyProjectFiles(studioProjDir, targetDirFile, new NullProgressMonitor());
				String studioProjectConfigFileLocation = studioProjDir + "/" + StudioProjectConfigurationManager.STUDIO_PROJECT_CONFIG_FILENAME;
				String coreInternalLibs[] = getCoreInternalLibsList();
				StudioProjectConfigurationManager.getInstance().createProjectConfiguration(null, studioProjectConfigFileLocation, coreInternalLibs, xpathversion);
				StudioProjectMigrationUtils.convertStudioProject(studioProjDir, null, xpathversion, new NullProgressMonitor());
			} catch (Exception e) {
				throw new Exception(Messages.getString("Import.Existing.Project.Error") + " - " + e.getMessage(), e);
			}
			finally {
				if(studioProjectInstance!=null && studioProjectInstance.exists()){
					studioProjectInstance.close(null);
					studioProjectInstance.delete(true, null);
				}
			}
			System.out.println("\n" + Messages.getString("Import.Success"));
		} catch (Exception e) {
			throw new Exception(Messages.getString("Import.ConvertOntology.Error") + " - " + e.getMessage(), e);
		}
		return true;
	}

	private String[] getCoreInternalLibsList() {
		String beHome = System.getProperty("BE_HOME");
		ArrayList<String> libsList = new ArrayList<String>();
		if (beHome == null)
			return null;
		String rootDirs[] = { "/hotfix/", "/" };
		String libDirs[] = { "lib", "lib/ext", "lib/ext/tibco", "lib/ext/tpcl" };

		for (String rootDir: rootDirs) {
			for (String libDir: libDirs) {
				File dir = new File(beHome + rootDir + libDir);
				FilenameFilter jarFileFilter = new FilenameFilter(){
					@Override
					public boolean accept(File dir, String name) {
						return (name.endsWith(".jar"));
					}
				};
				File files[] = dir.listFiles(jarFileFilter);
				if (files == null) {
					continue;
				}
				for (File file: files) {
					libsList.add(file.getAbsolutePath().replace('\\', '/'));
				}
			}
		}
		return (libsList.toArray(new String[0]));
	}
}
