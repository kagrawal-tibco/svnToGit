package com.tibco.cep.studio.core.util;

import static com.tibco.cep.studio.core.utils.ModelUtils.getPersistenceOptions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;

import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class StudioCoreResourceUtils{
	
	private static List<String> dmDisabledExtensions = new ArrayList<String>();
	
	/**
	 * This returns List of Shared Global Elements for the given GV container
	 * @param container
	 * @param list
	 */
	private static void collectSharedElements(ElementContainer container, List<SharedElement> list){
		DesignerElement defaultVarEntry = container.getEntries().get(0);
		 if (defaultVarEntry != null && defaultVarEntry instanceof SharedElement) {
		     SharedElement defaultVarElement = (SharedElement)defaultVarEntry;
		     list.add(defaultVarElement);
		 }
		 if (container.getEntries().size() >= 2) {
			 for (DesignerElement element : container.getEntries()) {
				 if (element instanceof ElementContainer) {
					 collectSharedElements((ElementContainer)element, list);
				 }
			 }
		 }
	}
	
	/**
	 * @param perspectiveId
	 * @return
	 */
	public static boolean containsDesignerNavigator(String perspectiveId){
		if (perspectiveId.equals("com.tibco.cep.studio.application.perspective")
				|| perspectiveId.equals("com.tibco.cep.diagramming.diagram.perspective")
				|| perspectiveId.equals("com.tibco.cep.decision.table.perspective")) {
			return true;
		}
		return false;
	}

	/**
	 * @param project
	 * @return
	 */
	public static List<IFile> createDefaultFiles(IProject project) {
		ArrayList<IFile> files = new ArrayList<IFile>();
		files.add(project.getFile(project.getName()+ StudioWorkbenchConstants.CONCEPT_VIEW_EXTENSION));
		files.add(project.getFile(project.getName()+StudioWorkbenchConstants.EVENT_VIEW_EXTENSION));
		files.add(project.getFile(project.getName()+StudioWorkbenchConstants.PROJECT_VIEW_EXTENSION));
		return files;
	}
	
	/**
	 * @param project
	 * @return
	 */
	public static List<IFile> createDefaultJavaFiles(IProject project) {
		ArrayList<IFile> files = new ArrayList<IFile>();
		return files;
	}

	/**
     * 
     * @param file
     * @return
     */
	public static String getFolder(IFile file) {
		return "/" + file.getProjectRelativePath().toPortableString().substring(0, file.getProjectRelativePath().toPortableString().indexOf(file.getName()));

    }
	
	
	/**
	 * @param project
	 * @return
	 */
	public static List<IFolder> createJavaFolders(IProject project) {
		ArrayList<IFolder> folders = new ArrayList<IFolder>();
		
		folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_JAVA_BIN));
		folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_JAVA_SRC));
		return folders;
	}
	
	
	public static void createDefaultArtifacts(IProject project, List<IFolder> folders,List<IFile> files) {
		try {
			for (IFolder folder:folders) {
				if (!folder.exists()) {
					folder.create(IFolder.FORCE, true, null);
				}
			}
			for (IFile file:files) {
				if (!file.exists()) {
					ByteArrayInputStream bi = new ByteArrayInputStream("".getBytes());
					file.create(bi, true, null);
				}
			}
			project.refreshLocal(IProject.DEPTH_INFINITE, null);
		
		} catch (Exception e) {
			e.printStackTrace();
//			resultError();
		}
	}


	

	/**
	 * @param project
	 * @return
	 */
	public static String getCurrentProjectBaseURI(IProject project){
		IPath path = new Path(ResourceHelper.getLocationURI(project).getPath());
		path = path.removeLastSegments(1);
//		if (Platform.OS_WIN32.equals(Platform.getOS())) {
//			path = path.substring(1, path.lastIndexOf("/"));
//		} else {
//			path = path.substring(0, path.lastIndexOf("/"));
//		}
		return path.toOSString();
	}

	public static String getCurrentWorkspacePath() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		return workspace.getRoot().getLocation().toPortableString();
	}

	/**
	 * @return
	 */
	public static List<String> getDecisionManagerDisabledFileExtensions () {
		if (dmDisabledExtensions.size() == 0 ) {
			dmDisabledExtensions.add(CommonIndexUtils.CONCEPT_EXTENSION);
			dmDisabledExtensions.add(CommonIndexUtils.SCORECARD_EXTENSION);
			dmDisabledExtensions.add(CommonIndexUtils.EVENT_EXTENSION);
			dmDisabledExtensions.add(CommonIndexUtils.TIME_EXTENSION);
			dmDisabledExtensions.add(CommonIndexUtils.RULE_EXTENSION);
			dmDisabledExtensions.add(CommonIndexUtils.RULEFUNCTION_EXTENSION);
		}
		return dmDisabledExtensions;
	}
	
	public static String getProjectPathFor(IProject project) {
		StringBuffer path = new StringBuffer(getCurrentWorkspacePath());
		path.append(project.getFullPath().toPortableString());
		return path.toString();
	}
	
	/**
	 * @param path
	 * @return
	 */
	public static IResource getResourcePathFromContainerPath(IPath path) {
		IResource resource = null;
		if(path!= null){
			switch (path.segmentCount()) {
				case 0 :
					resource = ResourcesPlugin.getWorkspace().getRoot();
					break;
				case 1 :
					resource = ResourcesPlugin.getWorkspace().getRoot().getProject(path.lastSegment());
					break;
				default :
					resource = ResourcesPlugin.getWorkspace().getRoot().getFolder(path);
			}
		}
		return resource;
	}
	
	/**
	 * @param folder
	 * @param fileName
	 * @param duplicateFile
	 * @return
	 */
	public static boolean isDuplicateBEResource(IResource folder, String fileName, StringBuilder duplicateFile) {
		Object[] object = CommonUtil.getResources((IContainer)folder);

		for(Object obj : object){
			if(!(obj instanceof IContainer)){
				//			}
				//			if(obj instanceof IFile){
				DesignerElement element = IndexUtils.getElement((IFile)obj);
				if(element != null){
					if(element.getName().equalsIgnoreCase(fileName)){
						duplicateFile.append(((IFile)obj).getName());
						return true;
					}
				}else{
					//Handling NonEntityResources
					IFile file = (IFile)obj;
					String name = file.getName().replace("." + file.getFileExtension(), "");
					if(name.equalsIgnoreCase(fileName)){
						duplicateFile.append(((IFile)obj).getName());
						return true;
					}

				}
			}
		}
		return false;
	}

	/**
	 * @param value
	 * @return
	 */
	public static boolean isStringEmpty(String value) {
		if (value == null || value.trim().length() <= 0)
			return true;
		return false;
	}

	/**
	 * 
	 * @param entity
	 * @param baseURI
	 * @param monitor
	 */
	public static void persistEntity(com.tibco.cep.designtime.core.model.Entity entity, String baseURI,IProject project, IProgressMonitor monitor ) throws Exception {
		persistEntity(entity, baseURI, project, monitor, getPersistenceOptions());
    }
	public static void persistEntity(com.tibco.cep.designtime.core.model.Entity entity, String baseURI, IProject project, IProgressMonitor monitor, Map<?, ?> options) throws IOException {
		String folder = entity.getFolder();
		String extension = IndexUtils.getFileExtension(entity);
		persistEntity(entity, entity.getName(), folder, extension, baseURI, project, monitor, options);
	}
	/**
	 * @param entity
	 * @param name
	 * @param folder
	 * @param extension
	 * @param baseURI
	 * @param project
	 * @param monitor
	 * @param options
	 * @throws IOException
	 */
	public static void persistEntity(EObject entity, 
			                         String name, 
			                         String folder, 
			                         String extension, 
			                         String baseURI, 
			                         IProject project, 
			                         IProgressMonitor monitor, 
			                         Map<?, ?> options) throws IOException {
		if (monitor != null) {
			monitor.subTask("persisting " + name);
		}
		URI uri = URI.createFileURI(baseURI + "/" + project.getName() + folder + name + "." + extension);
		ResourceSet resourceSet = new ResourceSetImpl();// using XMI
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(entity);
		resource.save(options);
		if (monitor != null) {
			monitor.worked(1);
		}
	}
	
	/**
	 * @param entity
	 * @param name
	 * @param folder
	 * @param extension
	 * @param baseURI
	 * @param project
	 * @param monitor
	 * @throws IOException
	 */
	public static void persistEntity(EObject entity, 
									 String name, 
									 String folder, 
									 String extension, 
									 String baseURI, 
									 String projectName, 
									 IProgressMonitor monitor) throws IOException {
		if (monitor != null) {
			monitor.subTask("persisting " + name);
		}
		URI uri = URI.createFileURI(baseURI + "/" + projectName + folder + name + "." + extension);
		XMLResource resource = (XMLResource)new XMLResourceFactoryImpl().createResource(uri);
		resource.getContents().add(entity);
		resource.save(getPersistenceOptions(resource));
		if (monitor != null) {
			monitor.worked(1);
		}
	}
	
	/**
	 * This returns List of Shared Global Elements for the given project Name
	 * @param projectName
	 * @return List of Shared Global Variable Elements
	 */
	public static void getProjectLibraryGVList(String projectName, Map<String, List<SharedElement>> map) {
		DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex(projectName);
		if(index != null){
			EList<DesignerProject> referencedProjects = index.getReferencedProjects();
			if (referencedProjects.size() > 0) {
				for (DesignerProject project : referencedProjects) {
					for (DesignerElement element : project.getEntries()) {
						if (element instanceof ElementContainer) {
							 ElementContainer container = (ElementContainer) element;
							 if (element.getName().equals("defaultVars")) {
								 List<SharedElement> list = new ArrayList<SharedElement>();
								 collectSharedElements(container, list);
								 map.put(project.getArchivePath(), list);
							 }
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param project
	 * @return
	 */
	public static List<IFolder> createDefaultFolders(IProject project) {
		ArrayList<IFolder> folders = new ArrayList<IFolder>();
		
		folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_CONCEPTS));
		folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_EVENTS));
		folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_CHANNELS));
		folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_RULES));
		folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_RULE_FUNCTIONS));
		folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_DASHBOARDS));
		folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_DEPLOYMENT));
		folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_SHARED_RESOURCE));
		folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_DEFAULT_VARS));
		folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_TEST_DATA));
	//	String input_dir = StudioUIPlugin.getDefault().getPreferenceStore().getString(StudioUIPreferenceConstants.TEST_DATA_INPUT_PATH);
	//	folders.add(project.getFolder(input_dir.substring(1)));
		
		if (AddonUtil.isProcessAddonInstalled()) {
			folders.add(project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_PROCESSES));
		}
		
		
		return folders;
	}	

}