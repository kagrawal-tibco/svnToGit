package com.tibco.cep.studio.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;

import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.be.parser.semantic.JavaCustomFunctionsFactory;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.common.configuration.update.IBuildPathEntryDelta;
import com.tibco.cep.studio.common.configuration.update.IStudioProjectConfigurationDelta;
import com.tibco.cep.studio.common.configuration.update.ProjectLibraryEntryDelta;
import com.tibco.cep.studio.common.configuration.update.StudioProjectConfigurationDelta;
import com.tibco.cep.studio.core.BEClassPathContainer;
import com.tibco.cep.studio.core.IStudioProjectContributor;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.builder.StudioProjectBuilder;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.nature.StudioProjectNature;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

public class StudioProjectUtil {

	private static final String STUDIO_PROJECT_PROPERTY_PROVIDER = "com.tibco.cep.studio.core.studioProjectContributor" ; //$NON-NLS-1$
	private static final String STUDIO_PROJECT_PROPERTY_ATTRIBUTE = "studioProjectContributor"; //$NON-NLS-1$


	public static List<String> getStudioProjectNatures(){
		List<String>  studioProjectNatures = new ArrayList<String>();
		try{
			IExtensionRegistry reg = Platform.getExtensionRegistry();
			if (reg != null) {
				IConfigurationElement[] extensions = reg.getConfigurationElementsFor(STUDIO_PROJECT_PROPERTY_PROVIDER);
				
				for (int i = 0; i < extensions.length; i++) {
					IConfigurationElement element = extensions[i];
					final Object o = element.createExecutableExtension(STUDIO_PROJECT_PROPERTY_ATTRIBUTE);
					if (o != null && o instanceof IStudioProjectContributor) {
						if(((IStudioProjectContributor)o).getNauturesList()!=null){
							studioProjectNatures.addAll(((IStudioProjectContributor)o).getNauturesList());
						}
					}
				}
				if(studioProjectNatures.indexOf(StudioProjectNature.STUDIO_NATURE_ID)>0){
					Collections.rotate(studioProjectNatures,studioProjectNatures.indexOf(StudioProjectNature.STUDIO_NATURE_ID));
				}
				return studioProjectNatures;
			}
		} catch (CoreException e) {
			StudioCorePlugin.log(e);
		}
		return studioProjectNatures;
	}

	public static List<String> getStudioProjectBuilders(){

		List<String>  studioProjectNatures = new ArrayList<String>();
		try{
			IExtensionRegistry reg = Platform.getExtensionRegistry();
			if (reg != null) {
				IConfigurationElement[] extensions = reg.getConfigurationElementsFor(STUDIO_PROJECT_PROPERTY_PROVIDER);
				for (int i = 0; i < extensions.length; i++) {
					IConfigurationElement element = extensions[i];
					final Object o = element.createExecutableExtension(STUDIO_PROJECT_PROPERTY_ATTRIBUTE);
					if (o != null && o instanceof IStudioProjectContributor) {
						if(((IStudioProjectContributor)o).getBuildersList()!=null){
							studioProjectNatures.addAll(((IStudioProjectContributor)o).getBuildersList());
						}
					}
				}
				return studioProjectNatures;
			}
		} catch (CoreException e) {
			StudioCorePlugin.log(e);
		}
		return studioProjectNatures;
	}

	public static void creatProject(IProject project, boolean isImport, boolean containsJavaNatureOnImport, XPATH_VERSION xpathVersion) {
		try{
			IExtensionRegistry reg = Platform.getExtensionRegistry();
			if (reg != null) {
				IConfigurationElement[] extensions = reg.getConfigurationElementsFor(STUDIO_PROJECT_PROPERTY_PROVIDER);
				for (int i = 0; i < extensions.length; i++) {
					IConfigurationElement element = extensions[i];
					final Object o = element.createExecutableExtension(STUDIO_PROJECT_PROPERTY_ATTRIBUTE);
					if (o != null && o instanceof IStudioProjectContributor) {
						IStudioProjectContributor studioProjectContributor = (IStudioProjectContributor)o;
						studioProjectContributor.setContainsJavaNatureOnImport(containsJavaNatureOnImport);
						studioProjectContributor.createProject(project, isImport, xpathVersion);
					}
				}
			}
		} catch (CoreException e) {
			StudioCorePlugin.log(e);
		}
	}

	//	public static void addBuilder(IProjectDescription desc, boolean addJavaBuilder) throws CoreException {
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


	public static void addBuilder(IProjectDescription desc) throws CoreException {
		ICommand[] commands = desc.getBuildSpec();
		boolean hasStudioBuilder = false;
		boolean hasJavaBuilder = false;

		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(StudioProjectBuilder.BUILDER_ID)) {
				hasStudioBuilder = true;
			}
			if (commands[i].getBuilderName().equals(JavaCore.BUILDER_ID)) {
				hasJavaBuilder = true;
			}
		}
		List<String> builderList = StudioProjectUtil.getStudioProjectBuilders();
		if (hasStudioBuilder) {
			builderList.remove(StudioProjectBuilder.BUILDER_ID);
		}
		if (hasJavaBuilder) {
			builderList.remove(JavaCore.BUILDER_ID);
		}
		ICommand[] newCommands = new ICommand[commands.length + builderList.size()];
		System.arraycopy(commands, 0, newCommands, 0, commands.length);
		for(int iBuilder=0; iBuilder < builderList.size() ; iBuilder++){
			ICommand command = desc.newCommand();
			command.setBuilderName(builderList.get(iBuilder));
			newCommands[commands.length+iBuilder] = command;
		}

		desc.setBuildSpec(newCommands);
	}


	/**
	 * @param fProject
	 * @param monitor
	 * @param fTargetLocation
	 * @param isImport
	 * @param fUseDefaults
	 * @throws CoreException
	 */
	public static void createProject(IProject fProject, 
			IProjectDescription description,
			IProgressMonitor monitor, 
			File fTargetLocation, 
			boolean isDesignerProjectImport, 
			boolean isExistingProjectImport,
			boolean fUseDefaults, 
			XPATH_VERSION xpathVersion) throws CoreException {
		if (description == null) {
			//Adding ProjectNature for the Project, if decsriptor not available
			description = ResourcesPlugin.getWorkspace().newProjectDescription(fProject.getName());
		} 
		if (!fUseDefaults) {
			String fPath = fTargetLocation.getAbsolutePath();
			if (!isDesignerProjectImport) {
				fPath += File.separator +  fProject.getName();
			}
			Path path = new Path(fPath);
			description.setLocation(path);
		}
		boolean containsJavaNatureOnImport = false;
		String[] natures = description.getNatureIds();
		List<String> existingNatures = Arrays.asList(natures);
		if (isExistingProjectImport) {
			if (existingNatures.contains(JavaCore.NATURE_ID)) {
				containsJavaNatureOnImport = true;
			}
		}
		if (!containsJavaNatureOnImport) {
			List<String> natureList = getStudioProjectNatures();
			String[] newNatures = new String[natures.length + natureList.size()];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			for(int iNature=0; iNature < natureList.size() ; iNature++) {
				newNatures[natures.length+iNature] = natureList.get(iNature);
			}
			Set<String> set = new HashSet<String>();
			for (int k = 0; k < newNatures.length ; k++) {
				set.add(newNatures[k]);
			}
			description.setNatureIds(set.toArray(new String[set.size()]));
			addBuilder(description);
		}
		fProject.create(description, new SubProgressMonitor(monitor, 5));
		fProject.open(new SubProgressMonitor(monitor, 5));
		if (isExistingProjectImport && !containsJavaNatureOnImport) {
			fProject.setDescription(description, new SubProgressMonitor(monitor, 5));
		}
		creatProject(fProject, isExistingProjectImport, containsJavaNatureOnImport, xpathVersion);
		monitor.setTaskName("Refreshing project");
		fProject.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(monitor, 5));
		
		updateProjectLibrary(fProject.getName());
	}
	
	/* Added for BE-22483 BE Studio: Studio Project throws validation errors for project Library */
	
	public static void updateProjectLibrary(String projectName) {
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		List<String> libPathList =  new ArrayList<String>();
		for (ProjectLibraryEntry entry : configuration.getProjectLibEntries()) {
			libPathList.add(entry.getPath());
		}
		if (libPathList.size() == 0) {
			return;
		}
		StudioProjectConfigurationDelta configDelta = 
				new StudioProjectConfigurationDelta(StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName), 
						IStudioProjectConfigurationDelta.CHANGED);
		configuration.getProjectLibEntries().clear();
		for (String libPath: libPathList) {
			ProjectLibraryEntry entry = ConfigurationFactory.eINSTANCE.createProjectLibraryEntry();
			entry.setEntryType(LIBRARY_PATH_TYPE.PROJECT_LIBRARY);
			entry.setPath(libPath);
			File file = new File(libPath);
			entry.setTimestamp(file.lastModified());
			ProjectLibraryEntryDelta delta = new ProjectLibraryEntryDelta(entry, IBuildPathEntryDelta.ADDED);
			configDelta.getProjectLibEntries().add(delta);
			configuration.getProjectLibEntries().add(entry);
		}
		try {
			checkConfiguration(configuration);
			StudioProjectConfigurationManager.getInstance().saveConfiguration(projectName, configuration);
			StudioProjectConfigurationManager.getInstance().fireDelta(configDelta);
			GlobalVariablesMananger.getInstance().updateGlobalVariables(projectName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void checkConfiguration(StudioProjectConfiguration configuration) throws Exception {

		EList<CustomFunctionLibEntry> customLibEntries = configuration.getCustomFunctionLibEntries();
		if (customLibEntries.size() == 0) {
			// fire change event, as custom function jars have been removed
			StudioCorePlugin.getDefault().fireCatalogChangedEvent(null);
		}
		List<String> catalogNames = new ArrayList<String>();
		List<String> categoryNames = new ArrayList<String>();
		for (CustomFunctionLibEntry bpe : customLibEntries) {		
			
			String filePath = bpe.getPath(bpe.isVar());
			File file = new File(filePath);
			JarFile archiveFile = new JarFile(file);
			XiNode document = null;
			try {
				document = FunctionsCatalogManager.parseArchiveFile(archiveFile);
			} catch (Exception e) {
				throw new Exception("Could not read custom functions from "+archiveFile.getName()+".  Ensure that it has a functions.catalog file and that it is properly formatted.  See the Error Log for more details", e);
			}
			if (document == null) {
				throw new Exception("Could not read custom functions from "+archiveFile.getName()+".  Ensure that it has a functions.catalog file and that it is properly formatted");
			}
			XiNode catalogNode =  XiChild.getChild(document, ExpandedName.makeName("catalog"));
			String catalogName = catalogNode.getAttributeStringValue(ExpandedName.makeName("name"));
			if (catalogNames.contains(catalogName)) {
				throw new Exception("Duplicate root catalog name in custom function jar '"+archiveFile.getName()+"'.  Catalog '"+catalogName+"' exists in two or more custom function jars");
			}
			catalogNames.add(catalogName);
			Iterator iterator = XiChild.getIterator(catalogNode, ExpandedName.makeName("category"));
			while (iterator.hasNext()) {
				XiNode n = (XiNode) iterator.next();
				String categoryName = XiChild.getChild(n, ExpandedName.makeName("name")).getStringValue();
				if (categoryNames.contains(categoryName)) {
					throw new Exception("Duplicate category name in custom function jar '"+archiveFile.getName()+"'.  Category '"+categoryName+"' exists in two or more custom function jars");
				}
				categoryNames.add(categoryName);
			}

			FunctionsCatalog catalog = new FunctionsCatalog();
			JavaCustomFunctionsFactory.loadFunctionCategoriesFromDocument(catalog, document, archiveFile,configuration,null);
		}
	}
	

	@SuppressWarnings("serial")
	public static void addStudioJavaProjectNature(IProject fProject) {
		try {
			IProjectDescription description = fProject.getDescription();
			String[] natures = fProject.getDescription().getNatureIds();
			List<String> existingNatures = Arrays.asList(natures);
			List<String> natureList = getStudioProjectNatures();
			if (!existingNatures.contains(JavaCore.NATURE_ID)) {
				String[] newNatures = new String[natures.length + natureList.size()];
				System.arraycopy(natures, 0, newNatures, 0, natures.length);
				for(int iNature=0; iNature < natureList.size() ; iNature++) {
					newNatures[natures.length+iNature] = natureList.get(iNature);
				}
				Set<String> set = new HashSet<String>();
				for (int k = 0; k < newNatures.length ; k++) {
					set.add(newNatures[k]);
				}
				description.setNatureIds(set.toArray(new String[set.size()]));
				addBuilder(description);

				fProject.setDescription(description, new NullProgressMonitor());

				List<IFolder> javaFolders = StudioCoreResourceUtils.createJavaFolders(fProject);
				StudioCoreResourceUtils.createDefaultArtifacts(fProject, javaFolders, new ArrayList<IFile>(){});
				IJavaProject javaProject = JavaCore.create(fProject); 
				List<IFolder> folders = StudioCoreResourceUtils.createJavaFolders(fProject);
				List<IFile> files = StudioCoreResourceUtils.createDefaultJavaFiles(fProject);
				StudioCoreResourceUtils.createDefaultArtifacts(fProject,folders,files);

				IFolder binFolder = fProject.getFolder(StudioWorkbenchConstants._NAME_FOLDER_JAVA_BIN);
				javaProject.setOutputLocation(binFolder.getFullPath(), null);

				List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
				entries.add(JavaCore.newContainerEntry(new Path("org.eclipse.jdt.launching.JRE_CONTAINER"), true));
				entries.add(JavaCore.newContainerEntry(new Path(BEClassPathContainer.BE_CLASSPATH_CONTAINER),true));
				IFolder sourceFolder = fProject.getFolder(StudioWorkbenchConstants._NAME_FOLDER_JAVA_SRC);
				IPackageFragmentRoot root = javaProject.getPackageFragmentRoot(sourceFolder);
				entries.add(JavaCore.newSourceEntry(root.getPath()));
				javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);
				fProject.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			}
			
			if (!existingNatures.contains(StudioProjectNature.STUDIO_NATURE_ID)) {
				boolean containsJavaNatureOnImport = false;
				natures = description.getNatureIds();
				existingNatures = Arrays.asList(natures);
				if (existingNatures.contains(JavaCore.NATURE_ID)) {
					containsJavaNatureOnImport = true;
				}
				natureList = getStudioProjectNatures();
				String[] newNatures = new String[natures.length + natureList.size()];
				System.arraycopy(natures, 0, newNatures, 0, natures.length);
				for(int iNature=0; iNature < natureList.size() ; iNature++) {
					newNatures[natures.length+iNature] = natureList.get(iNature);
				}
				Set<String> set = new HashSet<String>();
				for (int k = 0; k < newNatures.length ; k++) {
					set.add(newNatures[k]);
				}
				description.setNatureIds(set.toArray(new String[set.size()]));
				addBuilder(description);

				fProject.setDescription(description, new NullProgressMonitor());
				creatProject(fProject, true, containsJavaNatureOnImport, null);
				fProject.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				
				IJavaProject javaProject = JavaCore.create(fProject);
				List<IPath> paths =  new ArrayList<IPath>();
				for (IPackageFragmentRoot root : javaProject.getAllPackageFragmentRoots()) {
					IClasspathEntry entry = StudioJavaUtil.getClassPathEntryForPath(root.getPath(), javaProject, IClasspathEntry.CPE_SOURCE);
					if (entry != null) {
						paths.add(root.getPath());
					}
				}
				StudioJavaUtil.addJavaSourceFolderEntry(javaProject, paths, XPATH_VERSION.XPATH_20);
			}
			
		} 
		catch (CoreException e) {
			e.printStackTrace();
		}

	}
}