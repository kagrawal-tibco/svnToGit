package com.tibco.cep.studio.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.JavaClasspathEntry;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.nature.StudioProjectNature;
import com.tibco.cep.studio.core.utils.CommonJavaUtil;

/**
 * @author Pranab Dhar <br>
 *         Provides utility functions for JDT integration
 */
public class StudioJavaUtil extends CommonJavaUtil {

	private StudioJavaUtil() {super();}


	public static List<ClassPathListElement> getEntries(IProject project) throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);
		return getEntries(javaProject);
	}


	public static List<ClassPathListElement> getEntries(IJavaProject javaProject) throws JavaModelException {
		IClasspathEntry[] classpathEntries= javaProject.getRawClasspath();
		ArrayList<ClassPathListElement> newClassPath= new ArrayList<ClassPathListElement>();
		for (int i= 0; i < classpathEntries.length; i++) {
			IClasspathEntry curr= classpathEntries[i];
			newClassPath.add(ClassPathListElement.createFromExisting(curr, javaProject));
		}
		return newClassPath;
	}


	/**
	 * Find out if the specified resource is excluded or not
	 * @param resource
	 * @param project
	 * @return <code>true</code> if the resource is excluded, <code> false</code> otherwise
	 * @throws JavaModelException
	 */
	public static boolean isExcluded(IResource resource,IProject project) throws JavaModelException {
		IPackageFragmentRoot root = getFragmentRoot(resource, project);
		if(root == null)
			return false;
		String fragmentName = getFragmentName(resource.getFullPath(),project.getFullPath());
		fragmentName = completeFragmentName(fragmentName);
		IClasspathEntry entry = root.getRawClasspathEntry();
		return entry != null && containsPath(new Path(fragmentName), entry.getExclusionPatterns());
	}

	/**
	 * Find out if the provided path is in the list of paths in the array
	 * @param path
	 * @param listOfPaths
	 * @return <code>true</code> if the path is found in the list <code>false</code> otherwise
	 */
	public static boolean containsPath(IPath path,IPath[] listOfPaths) {
		if(path == null)
			return false;
		if (path.getFileExtension() == null)
			path= new Path(completeFragmentName(path.toString()));
		for (int i= 0; i < listOfPaths.length; i++) {
			if (listOfPaths[i].equals(path))
				return true;
		}
		return false;
	}

	/**
	 * Add a '/' if the fileName does not end with .java extension
	 * @param fileName
	 * @return
	 */
	private static String completeFragmentName(String fileName) {
		if(!JavaCore.isJavaLikeFileName(fileName)){
			fileName = fileName + ("/"); //$NON-NLS-1$
			fileName.replace('.', '/');
			return fileName;
		}
		return fileName;
	}

	/**
	 * Returns a string corresponding to the <code>path</code> with the <code>rootPath</code>'s number of segments removed.
	 * @param resourcePath resource path to remove segments from
	 * @param rootPath rootPath to give the number of segments to be removed
	 * @return a string path described above.
	 */
	private static String getFragmentName(IPath resourcePath,IPath rootPath ){
		return resourcePath.removeFirstSegments(rootPath.segmentCount()).toString();
	}

	/*
	 * Find all folders that are on the build path and
	 * <code>path</code> is a prefix of those folders
	 * path entry, that is, all folders which are a
	 * subfolder of <code>path</code>.
	 *
	 * For example, if <code>path</code>=/MyProject/src
	 * then all folders having a path like /MyProject/src/*,
	 * where * can be any valid string are returned if
	 * they are also on the project's build path.
	 *
	 * @param path absolute path
	 * @param javaProject the Java project
	 * @param monitor progress monitor, can be <code>null</code>
	 * @return an array of paths which belong to subfolders
	 * of <code>path</code> and which are on the build path
	 * @throws JavaModelException
	 */
	public static List<IPath> getFoldersOnClasspath(IPath path,IJavaProject javaProject) throws JavaModelException {
		List<IPath> sourceFolders= new ArrayList<IPath>();
		IClasspathEntry[] cpEntries= javaProject.getRawClasspath();
		for (int i= 0; i < cpEntries.length; i++) {
			IPath classPath= cpEntries[i].getPath();
			if (path.isPrefixOf(classPath) && path.segmentCount() + 1 == classPath.segmentCount())
				sourceFolders.add(new Path(completeFragmentName(classPath.lastSegment())));
		}
		return sourceFolders;
	}

	/**
	 * Resets inclusion and exclusion filters for the given <code>IJavaElement</code>
	 *
	 * @param element element to reset it's filters
	 * @param entry the <code>CPListElement</code> to reset its filters for
	 * @param project the Java project
	 * @throws JavaModelException
	 */	
	public static void resetFilters(IJavaElement element,ClassPathListElement entry,IJavaProject project) throws JavaModelException {
		List<IPath> exclusionList= getFoldersOnClasspath(element.getPath(), project);
		IPath outputLocation= (IPath) entry.getAttribute(ClassPathListElement.OUTPUT);
		if (outputLocation != null) {
			IPath[] exclusionPatterns= (IPath[]) entry.getAttribute(ClassPathListElement.EXCLUSION);
			if (containsPath(new Path(completeFragmentName(outputLocation.lastSegment())), exclusionPatterns)) {
				exclusionList.add(new Path(completeFragmentName(outputLocation.lastSegment())));
			}
		}
		IPath[] exclusions= exclusionList.toArray(new IPath[exclusionList.size()]);

		entry.setAttribute(ClassPathListElement.INCLUSION, new IPath[0]);
		entry.setAttribute(ClassPathListElement.EXCLUSION, exclusions);
	}

	/**
	 * Gets the source folder of a given <code>IResource</code> element starting
	 * with the resource parent
	 * 
	 * @param resource
	 *            the resource to get the fragment root from
	 * @param project
	 *            the studio project
	 * @return resolved fragment root , or <code>null</code> if the resource is
	 *         not in the source folder
	 * @throws JavaModelException
	 */
	public static IPackageFragmentRoot getFragmentRoot(IResource resource, IProject project) throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);
		IJavaElement element = null;
		if (resource.getFullPath().equals(javaProject.getPath())) {
			return javaProject.getPackageFragmentRoot(resource);
		}
		IContainer resourceContainer = resource.getParent();
		do {
			if (resourceContainer == null)
				return null;

			if (resourceContainer instanceof IFolder) {
				element = JavaCore.create((IFolder) resourceContainer);
			}
			if (resourceContainer.getFullPath().equals(javaProject.getPath())) {
				element = javaProject;
				break;
			}
			resourceContainer = resourceContainer.getParent();
			if (resourceContainer == null)
				return null;
		} while(element == null || !(element instanceof IPackageFragmentRoot));

		if (element instanceof IJavaProject) {
			if (!isSourceFolder(javaProject)) {
				return null;
			}
			element = javaProject.getPackageFragmentRoot(javaProject.getResource());
		}
		return (IPackageFragmentRoot) element;
	}

	/**
	 * Checks whether the <code>IJavaProject</code> is a source folder
	 * 
	 * @param javaProject
	 *            the project to test
	 * @return <code>true</code> if the project is source folder
	 *         <code>false</code> otherwise.
	 * @throws JavaModelException
	 */
	public static boolean isSourceFolder(IJavaProject javaProject) throws JavaModelException {
		return getClassPathEntryForPath(javaProject.getPath(), javaProject, IClasspathEntry.CPE_SOURCE) != null;
	}

	/**
	 * Gets the <code>IClasspathEntry</code> for the given path by looking up
	 * all build path entries in the given <code>IJavaProject</code>
	 * 
	 * @param path
	 * @param project
	 * @param entryKind
	 * @return the <code>IClasspathEntry</code> for the <code>path</code> or
	 *         <code>null</code> if there is no entry
	 * @throws JavaModelException
	 */
	public static IClasspathEntry getClassPathEntryForPath(IPath path, IJavaProject project, int entryKind) throws JavaModelException {
		IClasspathEntry[] entries = project.getRawClasspath();
		for (IClasspathEntry entry : entries) {
			if (entry.getPath().equals(path) && entry.getEntryKind() == entryKind) {
				return entry;
			}
		}
		return null;
	}

	/**
	 * @param path
	 * @param project
	 * @param entryKind
	 * @throws JavaModelException
	 */
	public static void removeClassPathEntryForPath(IPath path, IJavaProject project, int entryKind) throws JavaModelException {
		IClasspathEntry[] entries = project.getRawClasspath();
		IClasspathEntry entry = getClassPathEntryForPath(path, project, entryKind);
		ArrayList<IClasspathEntry> list = new ArrayList<IClasspathEntry>();
		for (IClasspathEntry e: entries) {
			list.add(e);
		}
		list.remove(entry);
		project.setRawClasspath(list.toArray(new IClasspathEntry[list.size()]), null);
	}

	/**
	 * @param javaProject
	 * @param path
	 */
	public static void addJavaSourceFolderEntry(IJavaProject javaProject, List<IPath> paths, XPATH_VERSION version) {
		try {
			StudioProjectConfiguration pconfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(javaProject.getElementName());
			for (IPath path : paths) {
				if (isJavaClassFolderEntryPresent(pconfig, path.toString())) {
					continue;
				}
				JavaSourceFolderEntry srcFolderEntry = ConfigurationFactory.eINSTANCE.createJavaSourceFolderEntry();
				srcFolderEntry.setEntryType(LIBRARY_PATH_TYPE.JAVA_SOURCE_FOLDER);
				srcFolderEntry.setPath(path.toString());
				srcFolderEntry.setReadOnly(true);
				pconfig.getJavaSourceFolderEntries().add(srcFolderEntry);
			}
			if (version != null) {
				pconfig.setXpathVersion(version);
			}
			StudioProjectConfigurationManager.getInstance().saveConfiguration(javaProject.getElementName(), pconfig);
		} catch (JavaModelException e) {
			StudioCorePlugin.log(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isJavaClassFolderEntryPresent(StudioProjectConfiguration pconfig, String path) {
		for (JavaSourceFolderEntry entry : pconfig.getJavaSourceFolderEntries()) {
			if (entry.getPath().equals(path)) {
				return true;
			}
		}
		return false;
	}
	
	public static void setXPathVersion(String projectName, XPATH_VERSION version) {
		try {
			if (version != null) {
				StudioProjectConfiguration pconfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
				pconfig.setXpathVersion(version);
				StudioProjectConfigurationManager.getInstance().saveConfiguration(projectName, pconfig);
			}
		} catch (JavaModelException e) {
			StudioCorePlugin.log(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Load Java configurations from an {@link IProject}
	 * @param project
	 */
	public static void loadJavaConfiguration(IProject project) {
		try {
			if(!project.isOpen())
				return;
			boolean hasJavaNature = project.hasNature(JavaCore.NATURE_ID);
			boolean hasStudioNature = project.hasNature(StudioProjectNature.STUDIO_NATURE_ID);
			if(!hasJavaNature || !hasStudioNature)
				return;
			/**
			 * Check if the project has annotation processor turned on , if not turn it on
			 */
			IJavaProject javaProject = JavaCore.create(project);
			if(javaProject != null){

				//				boolean isAnnotationEnabled =  AptConfig.isEnabled(javaProject);
				//				if(!isAnnotationEnabled)
				//					AptConfig.setEnabled(javaProject, true);
			}

			/**
			 * Visit all java files to get their {@link IClasspathEntry} data
			 */
			ClassPathEntryVisitor visitor = new ClassPathEntryVisitor(project);
			project.accept(visitor);
			List<IClasspathEntry> entries = visitor.getEntries();
			StudioProjectConfiguration pconfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getName());
			EList<JavaClasspathEntry> cpEntries = pconfig.getJavaClasspathEntries();
			cpEntries.clear();
			for(IClasspathEntry entry:entries){
				JavaClasspathEntry cpEntry = ConfigurationFactory.eINSTANCE.createJavaClasspathEntry();
				cpEntry.setEntryType(LIBRARY_PATH_TYPE.JAVA_CLASSPATH_ENTRY);
				if(entry.getPath() !=null){
					cpEntry.setPath(entry.getPath().toString());
				}
				cpEntry.setReadOnly(true);
				if(entry.getSourceAttachmentPath() != null){
					cpEntry.setSourceFolder(entry.getSourceAttachmentPath().makeAbsolute().toString());
				}
				if(entry.getOutputLocation() != null){
					cpEntry.setOutputLocation(entry.getOutputLocation().toString());
				}
				if(entry.getSourceAttachmentRootPath() != null){
					cpEntry.setSourceAttachmentRootPath(entry.getSourceAttachmentRootPath().makeAbsolute().toString());
				}

				for(IPath exPath:entry.getExclusionPatterns()){
					cpEntry.getExclusionPattern().add(exPath.toString());
				}
				for(IPath exPath:entry.getInclusionPatterns()){
					cpEntry.getInclusionPattern().add(exPath.toString());
				}
			}
		} catch (CoreException e) {
			StudioCorePlugin.log(e);
		}

	}

	public static class ClassPathEntryVisitor implements IResourceVisitor {
		private IJavaProject javaProject;
		private List<IClasspathEntry> entries;
		private List<IResource> resources;
		public ClassPathEntryVisitor(IProject project) {
			this.javaProject = JavaCore.create(project);
			this.entries = new ArrayList<IClasspathEntry>();
			this.resources = new ArrayList<IResource>();
		}

		public List<IClasspathEntry> getEntries() {
			return entries;
		}

		public List<IResource> getResources() {
			return resources;
		}

		@Override
		public boolean visit(IResource resource) throws CoreException {
			if(resource instanceof IProject) {
				boolean hasJavaNature = ((IProject)resource).hasNature(JavaCore.NATURE_ID);
				return  hasJavaNature && ((IProject)resource).getFullPath().equals(javaProject.getProject().getFullPath());
			}
			if(resource instanceof IFolder){
				IClasspathEntry entry = getClassPathEntryForPath(resource.getFullPath(), javaProject, IClasspathEntry.CPE_SOURCE);
				if(entry != null){
					entries.add(entry);
					return true;
				}
			}
			if(resource instanceof IFile){
				boolean isExcluded = isExcluded(resource, javaProject.getProject());
				IPackageFragmentRoot root = getFragmentRoot(resource, javaProject.getProject());
				if(!isExcluded && root != null){
					resources.add(resource);
					return false;
				}
			}

			return false;
		}


	}

	/**
	 * @author Pranab Dhar
	 * Load all Java Configurations
	 *
	 */
	public static class LoadAllJavaConfigurations extends Job {
		public static final String LOAD_ALL_JAVA_CONFIGS = "LOAD_ALL_JAVA_CONFIGS"; //$NON-NLS-1$
		IProject[] projects = null;
		public LoadAllJavaConfigurations(IProject[] projects) {
			super(LOAD_ALL_JAVA_CONFIGS);
			this.projects = projects;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			for(IProject project:projects){
				loadJavaConfiguration(project);
			}
			return Status.OK_STATUS;
		}


	}

	/**
	 *  Load Java Configurations
	 */
	public static void loadJavaConfiguration() {

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		LoadAllJavaConfigurations job = new LoadAllJavaConfigurations(projects);
		job.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
		job.schedule();

	}


	public static List<IClasspathEntry> getThirdPartyCustomClassPathEntries(StudioProjectConfiguration studioProjectConfig) {
		List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
		if (studioProjectConfig != null) {
			// third party jars
			EList<ThirdPartyLibEntry> tpentries = studioProjectConfig.getThirdpartyLibEntries();
			if (tpentries != null) {
				for (ThirdPartyLibEntry libEntry : tpentries) {
					IPath projLibPath = new Path(libEntry.getPath(libEntry.isVar()));
					entries.add(JavaCore.newLibraryEntry(projLibPath,null,null));
				}
			}
			// custom functions jars
			EList<CustomFunctionLibEntry> projLibEntries = studioProjectConfig.getCustomFunctionLibEntries();
			if (projLibEntries != null) {
				for (CustomFunctionLibEntry libEntry : projLibEntries) {
					IPath projLibPath = new Path(libEntry.getPath(libEntry.isVar()));
					entries.add(JavaCore.newLibraryEntry(projLibPath,null,null));
				}
			}

		}
		return entries;
	}

	/**
	 *  TODO 
	 * @param iMethod
	 */
	public static String getJavaTaskReturnTypesDemo(IMethod iMethod, String annotationName) {
		String returnType = null;
		try {
			returnType = iMethod.getReturnType();

			//			if (isJavaBoolean(returnType)) {
			//				//TODO
			//			} else if (isJavaClass(returnType)) {
			//				//TODO
			//			} else if (isJavaArray(returnType)) {
			//				//TODO
			//			} else if (isJavaPrimitive(returnType)) {
			//				try {
			//					//            		 if (returnType.charAt(0) == Signature.C_BYTE) {
			//					//        				 //TODO
			//					//            		 }
			//					//            		 if (returnType.charAt(0) == Signature.C_SHORT) {
			//					//            			  //TODO
			//					//            		 }
			//					if (returnType.charAt(0) == Signature.C_INT) {
			//						//TODO
			//					}
			//					if (returnType.charAt(0) == Signature.C_LONG) {
			//						//TODO
			//					}
			//					//            		 if (returnType.charAt(0) == Signature.C_FLOAT) {
			//					//            		 }
			//					if (returnType.charAt(0) == Signature.C_DOUBLE) {
			//						//TODO
			//					}
			//				} catch (NumberFormatException nfe) {
			//					// TODO Auto-generated catch block
			//					nfe.printStackTrace();
			//				}
			//			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnType;
	}

	/**
	 * 
	 * @param returnType
	 * @return
	 */
	public static boolean isJavaString(String returnType) {
		int signatureKind = Signature.getTypeSignatureKind(returnType);
		if (signatureKind == Signature.CLASS_TYPE_SIGNATURE) {
			if (returnType.charAt(0) == Signature.C_RESOLVED) {
				return Signature.toString(returnType).equals(java.lang.String.class.getCanonicalName());
			}
			if (returnType.charAt(0) == Signature.C_UNRESOLVED) {
				return Signature.toString(returnType).equals(java.lang.String.class.getSimpleName());
			}
		}
		return false;
	}

	/**
	 * 
	 * @param returnType
	 * @return
	 */
	public static boolean isJavaClass(String returnType) {
		int signatureKind = Signature.getTypeSignatureKind(returnType);
		if (signatureKind == Signature.CLASS_TYPE_SIGNATURE) {
			if (returnType.charAt(0) == Signature.C_RESOLVED) {
				returnType = Signature.getTypeErasure(returnType);
				return Signature.toString(returnType).equals(java.lang.Class.class.getCanonicalName());
			}
			if (returnType.charAt(0) == Signature.C_UNRESOLVED) {
				returnType = Signature.getTypeErasure(returnType);
				return Signature.toString(returnType).equals(java.lang.Class.class.getSimpleName());
			}
		}
		return false;
	}

	/**
	 * 
	 * @param returnType
	 * @return
	 */
	public static boolean isJavaBoolean(String returnType) {
		int signatureKind = Signature.getTypeSignatureKind(returnType);
		if (signatureKind == Signature.CLASS_TYPE_SIGNATURE) {
			if (returnType.charAt(0) == Signature.C_RESOLVED) {
				return Signature.toString(returnType).equals(java.lang.Boolean.class.getCanonicalName());
			}
			if (returnType.charAt(0) == Signature.C_UNRESOLVED) {
				return Signature.toString(returnType).equals(java.lang.Boolean.class.getSimpleName());
			}
		}
		if (signatureKind == Signature.BASE_TYPE_SIGNATURE) {
			return returnType.charAt(0) == Signature.C_BOOLEAN;
		}
		return false;
	}

	/**
	 * 
	 * @param returnType
	 * @return
	 */
	public static boolean isJavaArray(String returnType) {
		return Signature.getTypeSignatureKind(returnType) == Signature.ARRAY_TYPE_SIGNATURE;
	}

	/**
	 * 
	 * @param returnType
	 * @return
	 */
	public static boolean isJavaPrimitive(String returnType) {
		return Signature.getTypeSignatureKind(returnType) == Signature.BASE_TYPE_SIGNATURE;
	}

	/**
	 * 
	 * @param javaElement
	 * @param annotationName
	 * @return
	 */
	public static boolean isAnnotatedJavaTaskSource(ICompilationUnit javaElement, String annotationName) {
		return isAnnotationPresent(javaElement, annotationName);
	}

	public static boolean isAnnotationPresent(IJavaElement javaElement, String annotationName) {
		if (javaElement == null) {
			return false;
		}
		if (javaElement.getElementType() == IJavaElement.COMPILATION_UNIT && ((ICompilationUnit)javaElement).findPrimaryType() != null ) {
			return isAnnotationPresent(((ICompilationUnit)javaElement).findPrimaryType(), annotationName);
		}

		int elementType = javaElement.getElementType();

		if (elementType == IJavaElement.PACKAGE_DECLARATION
				|| elementType == IJavaElement.TYPE
				|| elementType == IJavaElement.METHOD
				|| elementType == IJavaElement.LOCAL_VARIABLE
				|| elementType == IJavaElement.FIELD) {

			List<Annotation> annotations = getAnnotations(javaElement);
			for (Annotation annotation : annotations) {
				if (getAnnotationName(annotation).equals(annotationName)) {
					return true;
				}
			}
		}
		return false;
	}

	public static String getAnnotationName(Annotation annotation) {
		Name annotationTypeName = annotation.getTypeName();
		return annotationTypeName.getFullyQualifiedName();
	}

	@SuppressWarnings("unchecked")
	public static List<Annotation> getAnnotations(IJavaElement javaElement) {
		if (javaElement.getElementType() == IJavaElement.PACKAGE_DECLARATION) {
			ICompilationUnit source = getCompilationUnitFromJavaElement(javaElement);
			CompilationUnit compilationUnit = getAST(source, false);
			PackageDeclaration packageDeclaration = compilationUnit.getPackage();
			return packageDeclaration.annotations();
		}


		if (javaElement.getElementType() == IJavaElement.TYPE) {
			IType type = (IType) javaElement;
			AbstractTypeDeclaration typeDeclaration = getTypeDeclaration(type);
			if (typeDeclaration != null) {
				return extractAnnotations(typeDeclaration.modifiers());
			}
		}

		if (javaElement.getElementType() == IJavaElement.METHOD) {
			IMethod method = (IMethod) javaElement;
			MethodDeclaration methodDeclaration = getMethodDeclaration(method);
			if (methodDeclaration != null) {
				return extractAnnotations(methodDeclaration.modifiers());
			}
		}

		if (javaElement.getElementType() == IJavaElement.FIELD) {
			IField field = (IField) javaElement;
			FieldDeclaration fieldDeclaration = getFieldDeclaration(field);
			if (fieldDeclaration != null) {
				return extractAnnotations(fieldDeclaration.modifiers());
			}
		}

		if (javaElement.getElementType() == IJavaElement.LOCAL_VARIABLE) {
			SingleVariableDeclaration singleVariableDeclaration = getSingleVariableDeclaration((ILocalVariable) javaElement);
			if (singleVariableDeclaration != null) {
				return extractAnnotations(singleVariableDeclaration.modifiers());
			}
		}

		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	public static FieldDeclaration getFieldDeclaration(IField field) {
		AbstractTypeDeclaration typeDeclaration = getTypeDeclaration(field.getDeclaringType());
		if (typeDeclaration != null) {
			List<BodyDeclaration> bodyDeclarations = typeDeclaration.bodyDeclarations();
			for (BodyDeclaration bodyDeclaration : bodyDeclarations) {
				if (bodyDeclaration instanceof FieldDeclaration) {
					FieldDeclaration fieldDeclaration = (FieldDeclaration) bodyDeclaration;
					if (compareFieldNames(fieldDeclaration, field)) {
						return fieldDeclaration;
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static boolean compareFieldNames(FieldDeclaration fieldDeclaration, IField field) {
		List<VariableDeclarationFragment> fragments = fieldDeclaration.fragments();
		for (VariableDeclarationFragment variableDeclarationFragment : fragments) {
			if (variableDeclarationFragment.getName().getIdentifier().equals(field.getElementName())) {
				return true;
			}
		}
		return false;
	}

	private static List<Annotation> extractAnnotations(List<IExtendedModifier> extendedModifiers) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		for (IExtendedModifier extendedModifier : extendedModifiers) {
			if (extendedModifier.isAnnotation()) {
				annotations.add((Annotation) extendedModifier);
			}
		}
		return annotations;
	}

	private static CompilationUnit getAST(ICompilationUnit source, boolean resolveBindings) {
		final ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setResolveBindings(resolveBindings);
		parser.setSource(source);
		return (CompilationUnit) parser.createAST(null);
	}

	public static SingleVariableDeclaration getSingleVariableDeclaration(ILocalVariable javaElement) {
		if (javaElement instanceof ILocalVariable && javaElement.getParent() instanceof IMethod) {
			ILocalVariable localVariable = javaElement;
			IMethod method = (IMethod) localVariable.getParent();
			MethodDeclaration methodDeclaration = getMethodDeclaration(method);

			@SuppressWarnings("unchecked")
			List<SingleVariableDeclaration> parameters = methodDeclaration.parameters();
			for (SingleVariableDeclaration singleVariableDeclaration : parameters) {
				if (singleVariableDeclaration.getName().getIdentifier().equals(localVariable.getElementName())) {
					return singleVariableDeclaration;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static MethodDeclaration getMethodDeclaration(IMethod method) {
		AbstractTypeDeclaration typeDeclaration = getTypeDeclaration(method.getDeclaringType());
		if (typeDeclaration != null) {
			List<BodyDeclaration> bodyDeclarations = typeDeclaration.bodyDeclarations();
			for (BodyDeclaration bodyDeclaration : bodyDeclarations) {
				if (bodyDeclaration instanceof MethodDeclaration) {
					MethodDeclaration methodDeclaration = (MethodDeclaration) bodyDeclaration;
					if (compareMethods(methodDeclaration, method)) {
						return methodDeclaration;
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static AbstractTypeDeclaration getTypeDeclaration(IType type) {
		CompilationUnit compilationUnit = getAST(type.getCompilationUnit(), false);
		List<TypeDeclaration> types = compilationUnit.types();
		for (AbstractTypeDeclaration abstractTypeDeclaration : types) {
			if (compareTypeNames(abstractTypeDeclaration, type)) {
				return abstractTypeDeclaration;
			}
		}
		return null;
	}

	public static boolean compareTypeNames(AbstractTypeDeclaration abstractTypeDeclaration, IType type) {
		return abstractTypeDeclaration.getName().getIdentifier().equals(type.getElementName());
	}

	@SuppressWarnings("unchecked")
	public static boolean compareMethods(MethodDeclaration methodDeclaration, IMethod method) {
		if (methodDeclaration.getName().getIdentifier().equals(method.getElementName())) {
			String[] parametetTypes = method.getParameterTypes();
			List<SingleVariableDeclaration> methodDeclarationParameters = methodDeclaration.parameters();
			if (parametetTypes.length == methodDeclarationParameters.size()) {
				for (int i = 0; i < parametetTypes.length; i++) {
					String simpleName1 = Signature.toString(parametetTypes[i]);
					String simpleName2 = methodDeclarationParameters.get(i).getType().toString();
					if (!simpleName1.equals(simpleName2)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	public static ICompilationUnit getCompilationUnitFromJavaElement(IJavaElement javaElement) {
		switch(javaElement.getElementType()) {
		case IJavaElement.COMPILATION_UNIT:
			return (ICompilationUnit) javaElement;
		case IJavaElement.PACKAGE_DECLARATION:
			IPackageDeclaration packageDeclaration = (IPackageDeclaration) javaElement;
			return (ICompilationUnit) packageDeclaration.getParent();
		case IJavaElement.TYPE:
			IType type = (IType) javaElement;
			return type.getCompilationUnit();
		case IJavaElement.METHOD:
			IMethod method = (IMethod) javaElement;
			return method.getCompilationUnit();
		case IJavaElement.FIELD:
			IField field = (IField) javaElement;
			return field.getCompilationUnit();
		case IJavaElement.LOCAL_VARIABLE:
			ILocalVariable localVariable = (ILocalVariable) javaElement;
			if (localVariable.getParent() instanceof IMethod) {
				return getCompilationUnitFromJavaElement(localVariable.getParent());
			}
		default:
			return JavaCore.createCompilationUnitFrom((IFile) javaElement.getResource());
		}
	}

	/**
	 * 
	 * @param file
	 * @param annotationName
	 * @return
	 */
	public static boolean isAnnotatedJavaTaskSource(IFile file, String annotationName) {
		ICompilationUnit javaElement = JavaCore.createCompilationUnitFrom(file);
		if  (javaElement == null) {
			return false;
		}
		return isAnnotationPresent(javaElement, annotationName);
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isJavaSource(IFile file) {
		if (file.getFileExtension().equals(CommonIndexUtils.JAVA_EXTENSION)) {
			ICompilationUnit javaElement = JavaCore.createCompilationUnitFrom(file);
			if  (javaElement == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param file
	 * @return
	 */
	public static boolean hasJavaSourceErrors(IFile file) {
		try {
			IMarker[] markers = file.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			for (IMarker m: markers) {
				System.out.println(m.getType());
				if (m.getType().equalsIgnoreCase("Java Problem")) {
					return true;
				}
			}
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param project
	 * @return
	 */
	public static IJavaProject getJavaProject(IProject project) {
		try {
			if (project.hasNature(JavaCore.NATURE_ID)) {
				return JavaCore.create(project);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param project
	 * @param classPackage
	 * @return
	 */
	public static IFile getAnnotatedFile(IProject project, String classPackage) {
		if (classPackage.isEmpty()) return null;
		IJavaProject jproject = getJavaProject(project);
		if (jproject != null) {
			IFolder folder = project.getFolder("javaSrc");
			IPackageFragmentRoot srcFolder =jproject.getPackageFragmentRoot(folder);
			String packageFolder = classPackage.substring(0, classPackage.lastIndexOf(CommonIndexUtils.DOT) - 1);
			String className = classPackage.substring(classPackage.lastIndexOf(CommonIndexUtils.DOT) + 1);
			try {
				for (ICompilationUnit unit:srcFolder.getPackageFragment(packageFolder).getCompilationUnits()) {
					if (unit.getElementName().equals(className)) {
						return (IFile)unit.getResource();
					}
				}
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	/**
	 * 
	 * @param javaFile
	 * @return
	 */
	public static String getFullyQualifiedJavaClass(IFile javaFile) {
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(javaFile);
		String name = javaFile.getName().replace(CommonIndexUtils.DOT + CommonIndexUtils.JAVA_EXTENSION , "");
		String ele = null;
		try {
			ele = (cu.getPackageDeclarations()[0]).getElementName() + CommonIndexUtils.DOT + name;
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ele;
	}

	/**
	 * @param uri
	 * @param projectName
	 * @return
	 */
	public static boolean isInsideJavaSourceFolder(String uri, String projectName) {
		boolean present = false;
		StudioProjectConfiguration pconfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		for (JavaSourceFolderEntry entry : pconfig.getJavaSourceFolderEntries()) {
			if (uri.contains(entry.getPath())) {
				present= true;
				break;
			}
		}
		return present;
	}
	
	/**
	 * @param uri
	 * @param projectName
	 * @param isCommandline
	 * @return
	 */
	public static boolean isInsideJavaSourceFolder(String uri, String projectName, boolean isCommandline) {
		boolean present = false;
		StudioProjectConfiguration pconfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		for (JavaSourceFolderEntry entry : pconfig.getJavaSourceFolderEntries()) {
			String path = entry.getPath();
			if (isCommandline) {
			    path = entry.getPath().replace("/", "\\");
			}
			if (uri.contains(path)) {
				present= true;
				break;
			}
		}
		return present;
	}
	
	public static String getJavaSourceFolderPath(String uri, String projectName) {
		String src = "";
		StudioProjectConfiguration pconfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		for (JavaSourceFolderEntry entry : pconfig.getJavaSourceFolderEntries()) {
			if (uri.contains(entry.getPath())) {
				src = entry.getPath();
				break;
			}
		}
		return src;
	}

	/**
	 * @param resource
	 * @param newElementPath
	 * @return
	 */
	public static boolean isTargetJavaSourceFolder(IResource resource, String newElementPath) {
		String projectName = resource.getProject().getName();
		String uri = "/" + projectName + newElementPath;
		return isInsideJavaSourceFolder(uri, projectName);
	}
	
	/**
	 * @param resource
	 * @return
	 */
	public static boolean isAnnotatedStudioJavaSource(IResource resource) {

		if (!StudioJavaUtil.isJavaSource((IFile)resource)) {
			return false;
		}

		String uri = ((IFile)resource).getFullPath().toString();
		if (!StudioJavaUtil.isInsideJavaSourceFolder(uri, resource.getProject().getName())) {
			return false;
		}

		if (StudioJavaUtil.isAnnotatedJavaTaskSource((IFile)resource, CommonIndexUtils.ANNOTATION_BPMN_JAVA_CLASS_TASK)
				|| StudioJavaUtil.isAnnotatedJavaTaskSource((IFile)resource, CommonIndexUtils.ANNOTATION_JAVA_CUSTOM_FUNCTION_CLASS)) {
			return true;
		}

		return false;
	}

}
