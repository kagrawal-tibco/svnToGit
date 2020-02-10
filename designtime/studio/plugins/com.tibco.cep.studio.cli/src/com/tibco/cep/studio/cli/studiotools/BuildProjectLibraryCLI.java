package com.tibco.cep.studio.cli.studiotools;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.IndexBuilder;
import com.tibco.cep.studio.core.projlib.ProjectLibraryFileSystem;
import com.tibco.cep.studio.core.util.DependencyUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

/*
@author rhollom
 */
public class BuildProjectLibraryCLI extends CoreCLI {

	// Create/import/remove project libraries
	private final static String OPERATION_BUILD_PROJECT_LIBRARY = "buildLibrary";
	private final static String FLAG_BUILDLIBRARY_HELP = "-h"; 	// Prints help
	private final static String FLAG_STUDIO_PROJECT_DIR = "-p";	// Path of project in Studio
	private final static String FLAG_PROJECT_LIB	= "-n";	// The absolute path of the target project library
	private final static String FLAG_RESOURCES = "-f";	// The resources to include
	private final static String FLAG_ADD_PROJECT_LIB = "-a";	// Adds the proj lib to the project
	private final static String FLAG_REMOVE_PROJECT_LIB = "-r";	// Removes the proj lib from the project
	private final static String FLAG_OVERWRITE_PROJECT_LIB = "-x";	// Whether to overwrite existing project libraries
	
	public BuildProjectLibraryCLI() {
	}
	
	@Override
	public String[] getFlags() {
		return new String[] { FLAG_BUILDLIBRARY_HELP, FLAG_STUDIO_PROJECT_DIR, FLAG_ADD_PROJECT_LIB, FLAG_REMOVE_PROJECT_LIB, 
				FLAG_OVERWRITE_PROJECT_LIB, FLAG_PROJECT_LIB, FLAG_RESOURCES};
	}
	
	@Override
	public String getHelp() {
		String helpMsg = "Usage: studio-tools " + getOperationFlag() + " " + OPERATION_BUILD_PROJECT_LIBRARY + " " + getUsageFlags() + "\n" +
			"where \n" +
			"    -h (optional) prints this usage \n" +
			"    -p is the absolute path to the directory of the Studio project\n" +
			"    -a (optional) adds the given project library to the project specified by p\n" +
			"    -r (optional) removes the given project library from the project specified by p\n" +
			"    -x (optional) overwrites the existing project library if it already exists\n" +
			"    -n is the absolute path of the project library to be created/added/removed\n" +
			"    -f (optional) is a comma separated list of project relative resources to include in the project library, for instance -f Concepts,TestRule.rule,Events\\TestEvent.event.  If unspecified, all project contents will be included\n";
		return helpMsg;
	}
	
	@Override
	public String getOperationFlag() {
		return OPERATION_BUILD_PROJECT_LIBRARY;
	}
	
	@Override
	public String getOperationName() {
		return ("Create TIBCO BusinessEvents Studio 5.6 Project Library");
	}
	
	public String getUsageFlags() {
		return ("[" + FLAG_BUILDLIBRARY_HELP + "] " +
			FLAG_STUDIO_PROJECT_DIR + " <studioProjDir> " +
			"[" + FLAG_ADD_PROJECT_LIB + "] " +
			"[" + FLAG_REMOVE_PROJECT_LIB + "] " +
			"[" + FLAG_OVERWRITE_PROJECT_LIB + "] " +
			"" + FLAG_PROJECT_LIB + " <projectLib> " +
			"[" + FLAG_RESOURCES + " <resources>] ");
	}
	
	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
    	if (checkIfExcludeOperation(argsMap))
    		return true;
    	
		if (argsMap.containsKey(FLAG_BUILDLIBRARY_HELP)) {
			System.out.println(getHelp());
			return true;
		}

		String studioProj = argsMap.get(FLAG_STUDIO_PROJECT_DIR);
		if (studioProj == null) {
			throw new IllegalArgumentException(Messages.getString("BuildLibrary.ProjectPath.Invalid"));
		}

		File studioProjDir = new File(studioProj);
		if (!studioProjDir.exists()) {
			throw new IllegalArgumentException(Messages.getString("BuildLibrary.ProjectPath.Invalid"));
		}
		StudioProjectConfiguration projectConfig = IndexBuilder.getProjectConfig(studioProjDir);
		if (projectConfig == null) {
			throw new IllegalArgumentException(Messages.getString("BuildLibrary.ProjectPath.Invalid"));
		}

		String resources = argsMap.get(FLAG_RESOURCES);
		boolean calculateDependencies = true;
		if (resources == null) {
			resources = ""; // add all project resources to project library
			calculateDependencies = false;
		}
		
		String addLib = argsMap.get(FLAG_ADD_PROJECT_LIB);
		boolean addProjLib = addLib != null ? true : false;
		String removeLib = argsMap.get(FLAG_REMOVE_PROJECT_LIB);
		boolean removeProjLib = removeLib != null ? true : false;
		if (addProjLib && removeProjLib) {
			throw new IllegalArgumentException(Messages.getString("BuildLibrary.AddRemove.Invalid"));
		}
		String projLibPath = argsMap.get(FLAG_PROJECT_LIB);
		if (projLibPath == null) {
			projLibPath = studioProjDir.getAbsolutePath();
		}
		if (!projLibPath.endsWith(".projlib")) {
			projLibPath += ".projlib";
		}
		if (addProjLib) {
			addProjectLibrary(projectConfig, projLibPath,studioProj);
			System.out.println(Messages.formatMessage("BuildLibrary.Add.Success", studioProj));
			return true;
		} else if (removeProjLib) {
			removeProjectLibrary(projectConfig, projLibPath);
			System.out.println(Messages.formatMessage("BuildLibrary.Remove.Success", studioProj));
			return true;
		}
		
		IndexBuilder builder = new IndexBuilder(studioProjDir);
		DesignerProject index = builder.loadProject();
		if (index == null) {
			System.err.println(Messages.formatMessage("BuildLibrary.ProjectLib.Index.Error", studioProj));
		}
		StudioProjectConfigurationManager.getInstance().getConfigurationsCache().put(studioProjDir.getName(), projectConfig);
		StudioProjectCache.getInstance().putIndex(index.getName(), index);
		String[] files = resources.split(",");
		List<File> allFiles = new ArrayList<File>();
		List<File> collectedFiles = new ArrayList<File>();
		for (String fileName : files) {
			collectFiles(new File(studioProjDir.getAbsoluteFile()+File.separator+fileName), collectedFiles);
		}
		for (File collectedFile : collectedFiles) {
			if (!allFiles.contains(collectedFile)) {
				allFiles.add(collectedFile);
			}
			if (calculateDependencies) {
				List<File> dependentResources = DependencyUtils.getDependentResources(studioProjDir, collectedFile, true);
				for (File file : dependentResources) {
					if (!allFiles.contains(file)) {
						if (!collectedFiles.contains(file)) {
							System.out.println(Messages.formatMessage("BuildLibrary.Dependent.Resource.Added", file.getAbsolutePath()));;
						}
						allFiles.add(file);
					}
				}
			}
		}
		
		try {
			File projLibFile = new File(projLibPath);
			if (projLibFile.getParentFile() != null && !projLibFile.getParentFile().exists()) {
				projLibFile.getParentFile().mkdirs();
			}
			if (projLibFile.exists()) {
				String overwrite = argsMap.get(FLAG_OVERWRITE_PROJECT_LIB);
				if (overwrite != null) {
					projLibFile.delete();
				} else {
					throw new Exception(Messages.formatMessage("BuildLibrary.ProjectLib.Exists", projLibPath));
				}
			}
			DependencyUtils.buildProjectLibrary(studioProjDir, projLibFile, allFiles);
		} catch (Exception e) {
			throw new Exception(Messages.getString("BuildLibrary.Project.Error") + " - " + e.getMessage(), e);
		}
		System.out.println(Messages.formatMessage("BuildLibrary.Success", projLibPath));
		return true;
	}

	private void removeProjectLibrary(StudioProjectConfiguration projectConfig,
			String projLibName) throws Exception {
		EList<ProjectLibraryEntry> projectLibEntries = projectConfig.getProjectLibEntries();
		ProjectLibraryEntry toBeRemoved = null;
		for (ProjectLibraryEntry entry : projectLibEntries) {
			String path = entry.getPath(entry.isVar());
			if (projLibName.equalsIgnoreCase(path)) {
				toBeRemoved = entry;
				break;
			}
		}
		if (toBeRemoved == null) {
			throw new Exception(Messages.formatMessage("BuildLibrary.ProjectLib.NotAdded", projLibName));
		}
		projectLibEntries.remove(toBeRemoved);
		saveConfiguration(projectConfig);
	}

	private void addProjectLibrary(StudioProjectConfiguration projectConfig,
			String projLibName, String projPath) throws Exception {
		File projLibFile = new File(projLibName);
		if (!projLibFile.exists()) {
			throw new Exception(Messages.formatMessage("BuildLibrary.ProjectLib.DNE", projLibName));
		}
		EList<ProjectLibraryEntry> projectLibEntries = projectConfig.getProjectLibEntries();
		for (ProjectLibraryEntry entry : projectLibEntries) {
			if (projLibName.equalsIgnoreCase(entry.getPath())) {
				throw new Exception(Messages.formatMessage("BuildLibrary.ProjectLib.AlreadyAdded", projLibName));
			}
		}
		ProjectLibraryEntry libraryEntry = ConfigurationFactory.eINSTANCE.createProjectLibraryEntry();
		libraryEntry.setPath(projLibName);
		libraryEntry.setEntryType(LIBRARY_PATH_TYPE.PROJECT_LIBRARY);
		libraryEntry.setTimestamp(projLibFile.lastModified());
		projectLibEntries.add(libraryEntry);
		saveConfiguration(projectConfig);
	
		String	projectName = new File(projPath).getName();
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if(project.exists()){
			project.close(null);
			project.delete(false, null);
		}
		IProjectDescription  desc=ResourcesPlugin.getWorkspace().newProjectDescription(project.getName()); 
		desc.setLocation(new Path(projPath)); 
		project.create(desc, null); 
		project.open(null);
		
		final IPath entryPath = new Path(projLibName);
		final File entryFile = entryPath.toFile();
		if (entryFile.exists() && entryFile.isFile()) {
			IFileStore fStore = EFS.getStore(entryFile.toURI());
			IFolder pFolder = project.getFolder(entryFile.getName());
			String path = String.format("/%s:%s", entryFile.toURI().getScheme(),entryFile.toURI().getSchemeSpecificPart());
			URI projlibURI = new URI(ProjectLibraryFileSystem.PROJLIB_SCHEME, null,path, null, null);
			pFolder.createLink(projlibURI, IResource.ALLOW_MISSING_LOCAL | IResource.REPLACE, new NullProgressMonitor());
		}
		
		if(project!=null){
			project.close(null);
			project.delete(true, null);
		}
	}

	private void saveConfiguration(StudioProjectConfiguration configuration) throws IOException {
		ModelUtils.saveEObject(configuration);
	}

	private void collectFiles(File file, List<File> collectedFiles) {
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (File subFile : listFiles) {
				collectFiles(subFile, collectedFiles);
			}
		} else {
			collectedFiles.add(file);
		}
	}

}
