package com.tibco.cep.studio.core.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.PlatformObject;

public class JarEntryFile extends PlatformObject implements IStorage {

	private String fJarFilePath;
	private String fJarEntryPath;
	private String fProjectName;
	private IPath fPath;
	private JarFile fJarFile;
	private Object input;
	private boolean hasRevision = false;
	private String revision; 

	/**
	 * @param jarFilePath
	 * @param jarEntryPath
	 * @param projectName
	 */
	public JarEntryFile(String jarFilePath, String jarEntryPath, String projectName) {
		this.fJarFilePath = jarFilePath;
		this.fJarEntryPath = jarEntryPath;
		this.fProjectName = projectName;
		this.fPath = new Path(jarEntryPath);
	}
	
	/**
	 * @param fileName
	 * @param path
	 * @param input
	 * @param projectName
	 * @param isExternalInput
	 */
	public JarEntryFile(String jarFilePath, 
			            String jarEntryPath, 
			            String projectName, 
			            Object input, 
			            String revision) {
		this (jarFilePath, jarEntryPath, projectName);
		this.input = input;
		this.revision = revision;
		if (revision != null) {
			hasRevision = true;
		}
	}
	
	public boolean hasRevision(){
		return hasRevision;
	}

	@Override
	public InputStream getContents() throws CoreException {
		try {
			if (input != null && input instanceof String) {
				byte[] contents = input.toString().getBytes();
				InputStream is = new ByteArrayInputStream(contents); 
				return is;
			}
			fJarFile = new JarFile(fJarFilePath);
			ZipEntry entry = fJarFile.getEntry(fJarEntryPath);
			if(entry == null) {
				if(fJarEntryPath.startsWith("/")){
					entry = fJarFile.getEntry(fJarEntryPath.substring(1));
				}
			}

			return fJarFile.getInputStream(entry);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CoreException(null);
		}
	}

	@Override
	public IPath getFullPath() {
		return fPath;
	}

	@Override
	public String getName() {
		if (hasRevision()) {
			return fPath.lastSegment() + " [Revision:" + revision + "]";
		}
		return fPath.lastSegment();
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

	public String getProjectName() {
		return fProjectName;
	}

	public String getJarFilePath() {
		return fJarFilePath;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof JarEntryFile)) {
			return false;
		}
		JarEntryFile file = (JarEntryFile) obj;
		if (!fProjectName.equals(file.getProjectName())) {
			return false;
		}
		if (fJarFilePath != null && !fJarFilePath.equals(file.getJarFilePath())) {
			return false;
		}
		if (fPath != null && !fPath.equals(file.getFullPath())) {
			return false;
		}
		return true;
	}
	
	@Override
	public Object getAdapter(Class adapter) {
		if (IProject.class.equals(adapter)) {
			return ResourcesPlugin.getWorkspace().getRoot().getProject(getProjectName());
		}
		return super.getAdapter(adapter);
	}

	public void closeJarFile() {
		if (fJarFile != null) {
			try {
				fJarFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
