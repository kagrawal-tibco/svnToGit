package com.tibco.cep.studio.core.nature;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.BEClassPathContainer;
import com.tibco.cep.studio.core.IStudioProjectContributor;
import com.tibco.cep.studio.core.util.StudioCoreResourceUtils;
import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.core.util.StudioWorkbenchConstants;

public class StudioJavaProject implements IStudioProjectContributor {

	protected List<String> nautreList;
	protected List<String> builderList;
	private boolean containsJavaNatureOnImport;
	
	public StudioJavaProject() {
		nautreList = new ArrayList<String>();
		nautreList.add(JavaCore.NATURE_ID);
		builderList = new ArrayList<String>();
		builderList.add(JavaCore.BUILDER_ID);
	}

	@Override
	public List<String> getNauturesList() {
		return nautreList;
	}

	@Override
	public List<String> getBuildersList() {
		return builderList;
	}


	@SuppressWarnings("serial")
	@Override
	public void createProject(IProject project, boolean isImport, XPATH_VERSION xpathVersion) {
		try {
			if (containsJavaNatureOnImport) {
				StudioJavaUtil.setXPathVersion(project.getName(), xpathVersion);
				List<IFolder> javaFolders = StudioCoreResourceUtils.createJavaFolders(project);
				StudioCoreResourceUtils.createDefaultArtifacts(project, javaFolders, new ArrayList<IFile>(){});
				return;
			}
			IJavaProject javaProject = JavaCore.create(project); 
			// Create default folders
			List<IFolder> folders = StudioCoreResourceUtils.createJavaFolders(project);
			//Create default files
			List<IFile> files = StudioCoreResourceUtils.createDefaultJavaFiles(project);
			StudioCoreResourceUtils.createDefaultArtifacts(project,folders,files);
			
			IFolder binFolder = project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_JAVA_BIN);
			javaProject.setOutputLocation(binFolder.getFullPath(), null);
			
			List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
			//IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
			//LibraryLocation[] locations = JavaRuntime.getLibraryLocations(vmInstall);
			//for (LibraryLocation element : locations) {
			// entries.add(JavaCore.newLibraryEntry(element.getSystemLibraryPath(), null, null));
			//}
			entries.add(JavaCore.newContainerEntry(new Path("org.eclipse.jdt.launching.JRE_CONTAINER"), true));
			entries.add(JavaCore.newContainerEntry(new Path(BEClassPathContainer.BE_CLASSPATH_CONTAINER),true));
			//javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);
			IFolder sourceFolder = project.getFolder(StudioWorkbenchConstants._NAME_FOLDER_JAVA_SRC);
			IPackageFragmentRoot root = javaProject.getPackageFragmentRoot(sourceFolder);
//			IClasspathEntry[] oldEntries = null;
			
			//oldEntries = javaProject.getRawClasspath();
			//IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
			//System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
			entries.add(JavaCore.newSourceEntry(root.getPath()));
			//javaProject.setRawClasspath(newEntries, null);
			javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);
			List<IPath> paths =  new ArrayList<IPath>();
			paths.add(sourceFolder.getFullPath());
			StudioJavaUtil.addJavaSourceFolderEntry(javaProject, paths, xpathVersion);
			
			// Enable project specific annotation processing
			//AptConfig.setEnabled(javaProject,true);
		} 
		catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setContainsJavaNatureOnImport(boolean containsJavaNatureOnImport) {
		this.containsJavaNatureOnImport = containsJavaNatureOnImport;
	}

}
