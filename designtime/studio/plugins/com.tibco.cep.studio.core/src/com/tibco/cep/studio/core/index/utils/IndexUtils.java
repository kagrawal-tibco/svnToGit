package com.tibco.cep.studio.core.index.utils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.java.JavaFactory;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener.UpdateIndexJob;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener.UpdateReferencedIndexJob;
import com.tibco.cep.studio.core.java.CommonJavaParserManager;
import com.tibco.cep.studio.core.projlib.ProjectLibraryConfigurationChangeListener.ProjectLibraryLinkResourceVisitor;
import com.tibco.cep.studio.core.util.ModelUtilsCore;

public class IndexUtils extends CommonIndexUtils {

	private final static QualifiedName loggedJavaError = new QualifiedName("com.tibco.be.navigator", "javaModelErrorLogged");


	public static RuleElement getRuleElement(String projectName, String rulePath, String ruleName, ELEMENT_TYPES ruleType) {
		waitForUpdate();
		return CommonIndexUtils.getRuleElement(projectName, rulePath, ruleName, ruleType);
	}

	public static List<DesignerElement> getAllElements(String projectName, String elementName) {
		waitForUpdate();
		return CommonIndexUtils.getAllElements(projectName, elementName);
	}

	public static List<DesignerElement> getAllElements(String projectName, ELEMENT_TYPES type) {
		waitForUpdate();
		return CommonIndexUtils.getAllElements(projectName, type);
	}

	public static List<DesignerElement> getAllElements(String projectName, ELEMENT_TYPES type , boolean waitForUpdate) {
		if (waitForUpdate) {
			waitForUpdate();
		}
		return CommonIndexUtils.getAllElements(projectName, type);
	}

	public static List<Entity> getAllEntities(String projectName, ELEMENT_TYPES type) {
		waitForUpdate();
		return CommonIndexUtils.getAllEntities(projectName, type);
	}

	public static <T extends Entity> List<T> getAllEntities(String projectName, ELEMENT_TYPES[] types , boolean waitForUpdate) {
		if (waitForUpdate) {
			waitForUpdate();
		}
		return CommonIndexUtils.getAllEntities(projectName, types, waitForUpdate);
	}

	public static <T extends Entity> List<T> getAllEntities(String projectName, ELEMENT_TYPES[] types) {
		return getAllEntities(projectName, types, true);
	}

	public static DesignerProject getIndex(String indexName) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(indexName);
		// this check is already done in StudioModelManager
		//		if (!project.isAccessible() || !CommonUtil.isStudioProject(project)) {
		//			return null;
		//		}
		return StudioCorePlugin.getDesignerModelManager().getIndex(project);
	}

	/**
	 * Allow finishing update jobs
	 */
	public static void waitForUpdate() {
		IJobManager jobManager = Job.getJobManager();
		Job[] jobsOnProject = jobManager.find(IndexResourceChangeListener.UPDATE_INDEX_FAMILY);        
		for (int i = 0; i < jobsOnProject.length; i++) {
			if (jobsOnProject[i] instanceof UpdateIndexJob) {
				UpdateIndexJob updateJob = (UpdateIndexJob) jobsOnProject[i];
				try {
					StudioCorePlugin.debug("waiting for update");
					updateJob.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		// if there is just one UpdateIndexJob and we call join() on that then it throws exception that Job is joining to itself
		//#IllegalStateException
	}

	/**
	 * wait until all update jobs are finished . Update of index as well as Referenced index
	 */
	public static void waitForUpdateAll() {
		IJobManager jobManager = Job.getJobManager();
		Job[] jobsOnProject = jobManager.find(IndexResourceChangeListener.UPDATE_INDEX_FAMILY);        
		for (int i = 0; i < jobsOnProject.length; i++) {
			if (jobsOnProject[i] instanceof UpdateIndexJob) {
				UpdateIndexJob updateJob = (UpdateIndexJob) jobsOnProject[i];
				try {
					System.out.println("waiting for update");
					updateJob.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (jobsOnProject[i] instanceof UpdateReferencedIndexJob) {
				UpdateReferencedIndexJob updateJob = (UpdateReferencedIndexJob) jobsOnProject[i];
				try {
					System.out.println("waiting for Index Reference update");
					updateJob.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
		// if there is just one UpdateIndexJob and we call join() on that then it throws exception that Job is joining to itself
		//#IllegalStateException
	}

	public static boolean isEMFType(IFile file) {
		return isEMFType(file.getFileExtension());
	}

	public static String getIndexLocationBaseDir() {
		if (StudioCorePlugin.getDefault() == null) {
			return null;
		}
		IPath path = StudioCorePlugin.getDefault().getStateLocation();
		String fileLoc = path.toOSString()+File.separator+"index"+File.separator;
		return fileLoc;
	}

	public static boolean isOntologyElement(IFile file) {
		return isOntologyElement(file.getFileExtension());
	}

	public static boolean isSupportedType(IFile file) {
		return isEntityType(file) || isArchiveType(file) || isImplementationType(file) || isRuleType(file);
	}

	public static boolean isEntityType(IFile file) {
		if (isRuleType(file)) {
			return false;
		}
		if (isArchiveType(file)) {
			return false;
		}
		if (isImplementationType(file)) {
			return false;
		}

		if (isQueryType(file)) {
			return false;
		}

		String extension = file.getFileExtension();
		// we know it is not a rule, an impl, or an archive, if
		// it exists in the extensions map, it must be
		// an entity type
		if (fileExtToElementType.containsKey(extension)) {
			return true;
		}
		return false;
	}

	public static boolean isArchiveType(IFile file) {
		return isArchiveType(file.getFileExtension());
	}

	public static boolean isImplementationType(IFile file) {
		return isImplementationType(file.getFileExtension());
	}

	public static boolean isRuleType(IFile file) {
		return isRuleType(file.getFileExtension());
	}

	public static boolean isQueryType(IFile file) {
		return isQueryType(file.getFileExtension());
	}

	public static boolean isProcessType(IFile file) {
		return isProcessType(file.getFileExtension());
	}

	public static boolean isJavaType(IFile file) {
		return isJavaType(file.getFileExtension());
	}
	
	public static boolean isJavaResource(IFile file) {
		if(file!= null && !file.exists()){
			return false;
		}
		IJavaElement jElement = JavaCore.create(file.getParent());
		if(jElement != null) {
			
			int elementType = jElement.getElementType();
			return  elementType == IJavaElement.PACKAGE_FRAGMENT || elementType == IJavaElement.PACKAGE_FRAGMENT_ROOT;
		}
		return false;
	}

	public static boolean isProjectLibType(IResource resource) {
		return resource.isLinked(IResource.CHECK_ANCESTORS) && CommonIndexUtils.isProjectLibraryURI(resource.getLocationURI());
	}

	/**
	 * Loads a java source file using JavaCore and finds its package information.
	 * @param file
	 * @return
	 */
	public static JavaResource loadJavaResource(IFile file) {
		IJavaElement jElement = JavaCore.create(file.getParent());
		JavaResource js = JavaFactory.eINSTANCE.createJavaResource();
		js.setFolder(IndexUtils.getFileFolder(file));
		js.setOwnerProjectName(file.getProject().getName());
		js.setName(file.getFullPath().removeFileExtension().lastSegment());
		js.setExtension(file.getFileExtension());
		try {
			InputStream is = file.getContents();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = is.read(data, 0, data.length)) != -1) {
			  baos.write(data, 0, nRead);
			}
			baos.flush();
			js.setContent(baos.toByteArray());
			String packageName = "";
			if(jElement.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
					packageName = ((IPackageFragment)jElement).getElementName();
			}
			js.setPackageName(packageName);
			
		} catch (Exception e) {
			StudioCorePlugin.log(e);
		}
		return js;
	}
	/**
	 * Loads a java source file using JavaCore and finds its package information.
	 * @param file
	 * @return
	 */
	public static JavaSource loadJavaEntity(IFile file) {
		if(!file.exists()){
			return null;
		}
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);
		JavaSource js = JavaFactory.eINSTANCE.createJavaSource();
		js.setFolder(IndexUtils.getFileFolder(file));
		js.setOwnerProjectName(file.getProject().getName());
		final String className = cu.getElementName();
		js.setName(className.substring(0, className.indexOf(IndexUtils.DOT)));
		try {
			if (!cu.getJavaProject().exists()) {
				Object sessionProperty = file.getProject().getSessionProperty(loggedJavaError);
				if (sessionProperty == null) {
					StudioCorePlugin.log("Project "+file.getProject().getName()+" does not contain Java Model");
					file.getProject().setSessionProperty(loggedJavaError, "true");
				}
				return js;
			}
			if (cu.isOpen()) {
				js.setFullSourceText(cu.getSource().getBytes("UTF-8"));
				IPackageDeclaration[] packages = cu.getPackageDeclarations();
				if(packages.length > 0) {
					js.setPackageName(packages[0].getElementName());
				}
			} else {
				// CompilationUnit was not open (not on the classpath), fall back to parsing directly (perhaps ignore this completely?)
				InputStream contents = null;
				try {
					contents = file.getContents();
					JavaSource javaSrc = CommonJavaParserManager.parseJavaInputStream(file.getProject().getName(), contents);
					javaSrc.setName(js.getName());
					javaSrc.setFolder(js.getFolder());
					if (javaSrc.getPackageName() == null
							|| javaSrc.getPackageName().isEmpty()) {
						javaSrc.setPackageName(getJavaPackageName(file
								.getProjectRelativePath().removeFileExtension()
								.toString(), file.getProject().getName()));
					}
					return javaSrc;
				} catch (Exception e) {
					StudioCorePlugin.log(e);
				} finally {
					contents.close();
				}

			}
			//			for(IType type:cu.getAllTypes()){
			//				IAnnotation typeAnnotation = type.getAnnotation(BEPackage.class.getCanonicalName());
			//				if(typeAnnotation != null) {
			//					IMemberValuePair[] mvp = typeAnnotation.getMemberValuePairs();
			//					// get static class functions
			//					for(IMethod m:type.getMethods()) {
			//						if(Flags.isStatic(m.getFlags())){
			//							IAnnotation fnAnnotation = m.getAnnotation(BEFunction.class.getCanonicalName());
			//							IMemberValuePair[] fnmvp = fnAnnotation.getMemberValuePairs();
			//						}
			//					}
			//					
			//				}
			//			}
		} catch (Exception e) {
			StudioCorePlugin.log(e);
		}
		return js;
	}
	
	public static String getJavaPackageName(String path, String projectName) {
		String packageName = path;
		String javaSrcFolderName = ModelUtilsCore.getJavaSrcFolderName(path,
				projectName);
		if (packageName.startsWith("/"))
			packageName = packageName.replaceFirst("/", "");
		if (packageName.endsWith("/"))
			packageName = packageName
					.substring(0, packageName.lastIndexOf("/"));
		packageName = packageName.replaceAll("/", ".");
		int len = javaSrcFolderName.length();
		if (packageName != null
				&& packageName.length() > javaSrcFolderName.length()) {
			len = len + 1;
		}
		packageName = packageName.substring(len);
		if (packageName != null && packageName.contains("."))
			packageName = packageName
					.substring(0, packageName.lastIndexOf("."));
		else {
			packageName = "";
		}
		return packageName;
	}
	
	/**
	 * @param projectName
	 * @param element
	 * @return
	 */
	public static IFile getFile(String projectName, Entity element) {
		if (element == null) {
			return null;
		}
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		try{
			return getFile(project,element);
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * @param project
	 * @param element
	 * @return
	 */
	public static IFile getFile(IProject project, Entity element) {
		if (element == null) {
			return null;
		}
		String fileExtension = getFileExtension(element);
		IContainer container = null;
		if(element.getFolder().trim().equals(PATH_SEPARATOR)) {
			container = project;
		} else {
			container = project.getFolder(element.getFolder());
		}
		IFile file = container.getFile(new Path(element.getName()+DOT+fileExtension));
		return file;
	}

	public static IFile getFile(String projectName, TypeElement element) {
		if (element == null) {
			return null;
		}
		if (element instanceof SharedElement) {
			// SharedElements do not have an IFile, return null in case there is an overlapping/duplicate resource on disk
			return null;
		}
		String fileExtension = getFileExtension(element.getElementType());
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		IContainer container = null;
		if(element.getFolder().trim().equals(PATH_SEPARATOR) || ((element.getFolder().trim().equals("")))) {
			container = project;
		} else {
			container = project.getFolder(element.getFolder());
		}
		IFile file = container.getFile(new Path(element.getName()+DOT+fileExtension));
		return file;
	}

	public static String getFileFolder(IFile file) {

		IPath path = file.getProjectRelativePath().makeAbsolute();
		path = path.removeLastSegments(1).addTrailingSeparator();
		String fullpath = path.toPortableString();

		if (file.isLinked(IFile.CHECK_ANCESTORS)) {

			path = path.removeFirstSegments(1);
			fullpath = path.toPortableString();

			if ( !fullpath.startsWith("/") )
				fullpath = "/" + fullpath;

		}

		if (path.isEmpty()) {
			return path.addTrailingSeparator().toPortableString();
		}
		return fullpath;
	}

	public static ELEMENT_TYPES getIndexType(IFile file) {
		if (fileExtToElementType.containsKey(file.getFileExtension())) {
			return ELEMENT_TYPES.get(getFileType(file).getValue());
		}
		if (EAR_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
			return ELEMENT_TYPES.ENTERPRISE_ARCHIVE;
		}
		return ELEMENT_TYPES.UNKNOWN;
	}

	public static File getIndexFile(String indexName) {
		return new File(getIndexLocation(indexName));
	}

	public static String getIndexLocation(String indexName) {
		if (getIndexLocationBaseDir() == null) {
			return null;
		}
		final File baseDir = new File(getIndexLocationBaseDir());
		final File file = new File(baseDir, indexName + IDX_EXTENSION);
		return file.getPath();
	}

	public static boolean indexFileExists(String indexName) {
		return getIndexFile(indexName).exists();
	}

	public static String getFullPath(IResource file) {
		IPath fullPath = file.getProjectRelativePath().makeAbsolute().removeFileExtension();
		String fullpath=fullPath.toPortableString();
		if (file.isLinked(IFile.CHECK_ANCESTORS)) {
			fullPath = fullPath.removeFirstSegments(1);
			fullpath=fullPath.toPortableString();
			if(!fullpath.startsWith("/"))
				fullpath ="/"+fullpath;
		}
		return fullpath;
	}

	public static DesignerElement getElement(IResource resource) {
		if (resource instanceof IFile && !isSupportedType((IFile)resource)) {
			return null;
		}
		IPath fullPath = resource.getProjectRelativePath().makeAbsolute().removeFileExtension();
		if (resource.isLinked(IResource.CHECK_ANCESTORS)) {
			fullPath = fullPath.removeFirstSegments(1);
		}
		String strfullpath=fullPath.toPortableString();
		return getElement(resource.getProject().getName(), strfullpath);
	}

	public static ELEMENT_TYPES getFileType(IFile file) {
		String fileExtension = file.getFileExtension();
		return fileExtToElementType.get(fileExtension);
	}

	public static ElementContainer getFolder(DesignerProject index, IContainer resource, boolean createIfAbsent, boolean isBinaryFile) {
		IPath fullPath = resource.getFullPath();
		fullPath = fullPath.removeFirstSegments(1); // remove project
		if (fullPath.segmentCount() == 0) {
			return index;
		}
		ElementContainer folder = null;
		if (resource.isLinked(IResource.CHECK_ANCESTORS)) {
			fullPath = fullPath.removeFirstSegments(1);
		}
		String resPath = fullPath.toFile().getPath();
		folder = getFolder(index, new com.tibco.cep.studio.common.util.Path(resPath), createIfAbsent, isBinaryFile);
		return folder;
	}

	public static ElementContainer getFolderForFile(DesignerProject index, IFile file) {
		return getFolderForFile(index, file, false);
	}

	public static ElementContainer getFolderForFile(DesignerProject index, IFile file, boolean createIfAbsent) {
		return getFolder(index, file.getParent(), createIfAbsent, false);
	}

	/**
	 * get index for a project depending the IResource passed
	 * @param resource
	 */
	public static DesignerProject getIndex(IResource resource){
		if (resource == null) return null;
		IProject project = resource.getProject();
		String projectName = project.getName();
		if (projectName == null) return null;
		return getIndex(projectName);
	}

	public static List<RuleElement> getRulesFromRuleSet(String projectName, String ruleSetURI) {
		List<RuleElement> rules = new ArrayList<RuleElement>();
		int index = ruleSetURI.lastIndexOf(".ruleset");
		if (index == -1) {
			// rule set URI is not valid
			return rules;
		}
		String folderPath = ruleSetURI.substring(0, index);
		ElementContainer folder = getFolderForFile(projectName, folderPath);
		if (folder == null) {
			System.out.println("Could not find folder "+folderPath);
			return rules;
		}
		EList<DesignerElement> entries = folder.getEntries();
		for (DesignerElement designerElement : entries) {
			if (designerElement.getElementType() == ELEMENT_TYPES.RULE
					|| designerElement.getElementType() == ELEMENT_TYPES.RULE_FUNCTION) {
				RuleElement ruleElement = (RuleElement) designerElement;
				rules.add(ruleElement);
			}
		}

		return rules;
	}

	//	public static ElementReference createElementReference(String identifier) {
	//		String[] split = identifier.split("\\.");
	//		ElementReference parent = null;
	//		for (String string : split) {
	//			ElementReference ref = IndexFactory.eINSTANCE.createElementReference();
	//			ref.setName(string);
	//			if (parent != null) {
	//				ref.setQualifier(parent);
	//			}
	//			parent = ref;
	//		}
	//		return parent;
	//	}

	public static boolean startsWithPath(String elementPath, IFolder folder) {
		return startsWithPath(elementPath, folder, false);
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

	/**
	 * Get the InputStream for the sharedElement.<BR>
	 * NOTE: It is up to the caller to close the returned stream
	 * @param sharedElement
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static InputStream getInputStream(SharedElement sharedElement) throws IOException {
		String archivePath = sharedElement.getArchivePath();
		String entryPath = sharedElement.getEntryPath()+sharedElement.getFileName();
		InputStream stream = null;
		JarFile jarFile = new JarFile(archivePath);
		JarEntry entry = (JarEntry) jarFile.getEntry(entryPath);
		stream = jarFile.getInputStream(entry);
		return stream;
	}

	/**
	 * Returns the actual size of the InputStream to determine the ReplaceEdit, as InputStream::available()
	 * does not return the proper number when non-ASCII characters are used.
	 * NOTE: This method reads from the input stream.  Clients are responsible for closing the stream.
	 * @param file
	 * @param fis
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws CoreException
	 * @throws IOException
	 */
	public static int getFileSize(IFile file, InputStream fis)
			throws UnsupportedEncodingException, CoreException, IOException {
		InputStreamReader reader = new InputStreamReader(fis, file.getCharset());
		// cannot use fis.available() here, as it does not give the proper value with UTF-8 encoding
		int size = 0;
		while (reader.read() != -1) {
			size++;
		}
		return size;
	}

	 /**
	  * Returns the linked resource given the element fullPath
	  * @param projectName
	  * @param fullPath
	  * @return
	  */
	 public static String getLinkedFileFolder(IPath path) {

		 IPath pathLoc = path.removeLastSegments(1);
		 pathLoc = pathLoc.removeFirstSegments(2);
		 return pathLoc.toPortableString();

	 }

	 public static IFile getLinkedResource(String projectName, String fullPath) {
		 IFile file = null;
		 try {
			 ProjectLibraryLinkResourceVisitor linkResourceVisitor = new ProjectLibraryLinkResourceVisitor();
			 IProject project = ResourcesPlugin.getWorkspace().getRoot()
					 .getProject(projectName);
			 project.accept(linkResourceVisitor);
			 List<IFile> linkedFiles = linkResourceVisitor.getLinkedFiles();
			 for (IFile f : linkedFiles) {
				 String lastSegment = f.getFullPath().lastSegment();
				 String linkFileFolder = getLinkedFileFolder(f.getFullPath());
				 String fPath = File.separator
						 + linkFileFolder/* f.getFullPath().segment(2) */
						 + File.separator
						 + lastSegment.substring(0,
								 lastSegment.indexOf(CommonIndexUtils.DOT));
				 if (linkFileFolder.isEmpty())
					 fPath = fPath.substring(fPath.indexOf(File.separator) + 1,
							 fPath.length());

				 fPath = fPath.replace(File.separator, "/");
				 if (fPath.equals(fullPath)) {
					 file = f;
					 break;
				 }
			 }
		 } catch (CoreException e) {
			 e.printStackTrace();
		 }
		 return file;
	 }

	 public static  String getSysIdforWsdlAndXsdInProjLib(SharedElement sharedElement){

		 String jarPath = sharedElement.getArchivePath().replaceAll("\\\\", "/");
		 String SysId = "jar:file:///" + jarPath + "!/"
				 + sharedElement.getEntryPath() + sharedElement.getFileName();

		 return SysId;

	 }

	 public static String getArchivePath(IResource file) {
		 IResource parent = getArchiveFile(file);
		 return parent.getLocation().toString();
	 }

	 public static IResource getArchiveFile(IResource file) {
		 IResource parent = file;
		 while (parent.getParent() != null && parent.getParent().isLinked(IResource.CHECK_ANCESTORS)) {
			 parent = parent.getParent();
		 }
		 return parent;
	 }


}
