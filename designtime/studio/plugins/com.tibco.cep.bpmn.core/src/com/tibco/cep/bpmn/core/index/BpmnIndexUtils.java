package com.tibco.cep.bpmn.core.index;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.nature.BpmnProjectNatureManager;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ModelUtilsCore;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class BpmnIndexUtils extends BpmnCommonIndexUtils{
	
	public static Map<String,URI> getIndexLocationMap() {
		IProject[] studioProjects = BpmnProjectNatureManager.getInstance().getAllBpmnProjects();
		Map<String,URI> locMap = new HashMap<String,URI>();
		for(IProject project:studioProjects) {
			URI fileURI = URI.createFileURI(BpmnIndexUtils.getIndexLocation(project.getName()));
			locMap.put(project.getName(), fileURI);
		}
		return locMap;
	}
	
	public static boolean isBpmnType(IResource file) {
		if(file.getFileExtension() != null) {
			return extensionMap.values().contains(file.getFileExtension().toLowerCase());
		}
		return false;
	}
	
	public static boolean isBpmnProcess(IResource file) {
		return BPMN_PROCESS_EXTENSION.equalsIgnoreCase(file.getFileExtension());
	}
	
	
	
	
	public static EObject getIndex(IResource resource){
		if (resource == null) return null;
		IProject project = resource.getProject();
		String projectName = project.getName();
		if (projectName == null) return null;
		
		EObject index = BpmnCorePlugin.getDefault().getBpmnModelManager().getBpmnIndex(projectName);
		
		return index;
	}
	
//	/**
//	 * @param projectName
//	 * @return
//	 */
//	public static EObject getIndex(String projectName) {
//		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
//		if (!project.isAccessible() || !CommonUtil.isStudioProject(project)) {
//			return null;
//		}
//		return BpmnCorePlugin.getDefault().getBpmnModelManager().getBpmnIndex(projectName);
//	}
	
	public static File getIndexFile(String indexName) {
		return new File(getIndexLocation(indexName));
	}
	
	public static String getIndexLocation(String indexName) {
		final File baseDir = new File(getIndexLocationBaseDir());
		final File file = new File(baseDir, indexName +"."+BPMN_INDEX_EXTENSION);
		return file.getPath();
	}

	public static boolean indexFileExists(String indexName) {
		return getIndexFile(indexName).exists();
	}
	
	public static String getIndexLocationBaseDir() {
		IPath path = BpmnCorePlugin.getDefault().getStateLocation();
		String fileLoc = path.toOSString()+File.separator+"index"+File.separator;
		return fileLoc;
	}
	
	public static String getFileFolder(IFile file) {
		IPath path = file.getProjectRelativePath().makeAbsolute();
		path = path.removeLastSegments(1).addTrailingSeparator();
		if (path.isEmpty()) {
			return path.addTrailingSeparator().toPortableString();
		}
		return path.toPortableString();		
	}
	
	
	public static String getFullPath(IResource file) {
		IPath fullPath = file.getProjectRelativePath();
		return fullPath.makeAbsolute().toPortableString();
	}
	
	
	public static String getSerializableURI(String projectName,EObject element) {
		IFile file = getFile(projectName,element);
		if(file != null && file.exists()) {
			return file.getProjectRelativePath().makeAbsolute().removeFileExtension().toPortableString();
		}
		return null;
	}
	
	
	/**
	 * @param projectName
	 * @param element
	 * @return
	 */
	public static IFile getFile(String projectName, EObject element) {
		if (element == null) {
			return null;
		}
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
        final String elementPath = getElementPath(element);
        if (null == elementPath) {
            return null;
        }
        IPath filePath = new Path(elementPath);
		IFile file = project.getFile(filePath);
		if(file == null || !file.exists()){
			Resource eResource = element.eResource();
			if( eResource != null){
				URI uri = eResource.getURI();
				if(uri != null){
					String platformString = uri.toPlatformString(false);
					if(platformString != null)
						file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformString));
				}
			}
		}
		return file;
	}
	
	public static IFile getFile(String projectName, String path, String extension) {
		if (path == null || path.isEmpty()) {
			return null;
		}
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		IPath filePath = new Path(path + CommonIndexUtils.DOT + extension);
		return project.getFile(filePath);
	}
	
	/**
	 * @param resource
	 * @param resolve TODO
	 * @return
	 * @throws Exception 
	 */
	public static EObject loadEObject(IResource resource, boolean resolve) throws Exception {
		EList<EObject> res = ECoreHelper.deserializeModelXMI(resource, resolve);
		if(res.size() > 0) {
			return res.get(0);
		}
		return null;
	}
	
	public static EObject getElement(IResource resource) {
		if (resource instanceof IFile && !isBpmnType((IFile)resource)) {
			return null;
		}
		return getElement(resource.getProject().getName(), getFullPath(resource));
	}
	
	
	public static boolean startsWithPath(String elementPath, IFolder folder, boolean isFolderElementPath) {
		if (elementPath == null || folder == null) {
			return false;
		}
		if (elementPath.startsWith(PATH_SEPARATOR)) {
			elementPath = elementPath.substring(1);
		}
		IPath folderPath = folder.getProjectRelativePath();
		
		StringTokenizer st = new StringTokenizer(elementPath, PATH_SEPARATOR);
		if (folderPath.segmentCount() > st.countTokens()) {
			return false; // folder path is longer than the element path, element path can't possibly start with folder path
		}
		String[] segments = folderPath.segments();
		for (String segment : segments) {
			if (st.hasMoreTokens()) {
				String tkn = st.nextToken();
				if (!tkn.equalsIgnoreCase(segment)) {
					return false;
				}
			} else {
				return false;
			}
		}
		// tokenizer must have at least one more token, or else this is not a folder, it is an entity
		if (isFolderElementPath) {
			return true;
		}
		if (!isFolderElementPath && st.hasMoreTokens()) {
			return true;
		}
		return false;
	}
	
	public static boolean startsWithPath(String elementPath, IFolder folder) {
		return startsWithPath(elementPath, folder, false);
	}

	public static JavaSource getJavaResource(String path, IProject projectName){
		Object input = projectName;
		String project = ((IProject)input).getName();
		IProject project1= ResourcesPlugin.getWorkspace().getRoot().getProject(project);
	//	IFile file = project1.getFile(new Path(resourceText.getText().trim() + CommonIndexUtils.DOT + CommonIndexUtils.JAVA_EXTENSION ));
		IFile file = project1.getFile(new Path(path+ CommonIndexUtils.DOT+ CommonIndexUtils.JAVA_EXTENSION ));
		if (file != null) {
    		setInitialSelection(file);
    	}
    		//IndexUtils iu = new IndexUtils();	
		return IndexUtils.loadJavaEntity(file);
		//	return file;
		
	}
	
	public static void setInitialSelection(Object selection) {
        setInitialSelections(new Object[] { selection });
    }
 
	public static void setInitialSelections(Object[] selectedElements) {
	 List initialSelections = new ArrayList();
		initialSelections = new ArrayList(selectedElements.length);
		for (int i = 0; i < selectedElements.length; i++) {
			initialSelections.add(selectedElements[i]);
		}
	}
	
	public static boolean isSubProcessTriggerByEvent(EObject eobjp){
		EObjectWrapper<EClass, EObject> userObjWrapper = 
				EObjectWrapper.wrap( eobjp );
		boolean triggeredByEvent =false;
		if ( userObjWrapper != null && userObjWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT) ) {
			 triggeredByEvent = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
		}
		return triggeredByEvent;
	}
	
//	public static String getJavaPackageName(String path, String projectName) {
//		String packageName = path;
//		String javaSrcFolderName = ModelUtilsCore.getJavaSrcFolderName(path,
//				projectName);
//		if (packageName.startsWith("/"))
//			packageName = packageName.replaceFirst("/", "");
//		if (packageName.endsWith("/"))
//			packageName = packageName
//					.substring(0, packageName.lastIndexOf("/"));
//		packageName = packageName.replaceAll("/", ".");
//		int len = javaSrcFolderName.length();
//		if (packageName != null
//				&& packageName.length() > javaSrcFolderName.length()) {
//			len = len + 1;
//		}
//		packageName = packageName.substring(len);
//		if (packageName != null && packageName.contains("."))
//			packageName = packageName
//					.substring(0, packageName.lastIndexOf("."));
//		else {
//			packageName = "";
//		}
//		return packageName;
//	}

}
