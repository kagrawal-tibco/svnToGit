package com.tibco.cep.studio.core.dependency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.studio.core.StudioCorePlugin;


public abstract class DefaultDependencyCalculator implements IDependencyCalculator {

	protected static final List<IResource> EMPTY_LIST = new ArrayList<IResource>(); 
	protected static List<String> fDependentProjectLibraries = new ArrayList<String>(); 
	
	protected String fValidExtensions;
	private String[] fSplitExtensions;
	
	public static void clear() {
		fDependentProjectLibraries.clear();
	}
	
	@Override
	public String getValidExtensions() {
		return fValidExtensions;
	}

	@Override
	public void setValidExtensions(String extensions) {
		this.fValidExtensions = extensions;
	}
	
	protected boolean enablesFor(IResource resource) {
		if (resource == null) {
			return false;
		}
		if (fValidExtensions == null || "*".equals(fValidExtensions)) {
			return true;
		}
		if (fValidExtensions.indexOf(',') == -1) {
			return fValidExtensions.equalsIgnoreCase(resource.getFileExtension());
		}
		if (fSplitExtensions == null) {
			fSplitExtensions = fValidExtensions.split(",");
		}
		if (fSplitExtensions != null && fSplitExtensions.length > 0) {
			for (String ext : fSplitExtensions) {
				if (ext.trim().equalsIgnoreCase(resource.getFileExtension())) {
					return true;
				}
			}
		}
		return false;
	}

	protected void addDependency(List<Object> dependencies, Object file) {
		if (file == null || !fileExists(file)) {
			return;
		}
		if (!dependencies.contains(file)) {
			dependencies.add(file);
		}
	}
	
	private boolean fileExists(Object file) {
		if (file instanceof IFile) {
			return ((IFile) file).exists();
		}
		if (file instanceof File) {
			return ((File) file).exists();
		}
		return false;
	}
	
	protected boolean enablesFor(File resource) {
		if (resource == null) {
			return false;
		}
		if (fValidExtensions == null || "*".equals(fValidExtensions)) {
			return true;
		}
		int idx = resource.getName().lastIndexOf('.');
		if (idx < 0) {
			return false;
		}
		String extension = resource.getName().substring(idx+1);
		
		if (fValidExtensions.indexOf(',') == -1) {
			return fValidExtensions.equalsIgnoreCase(extension);
		}
		if (fSplitExtensions == null) {
			fSplitExtensions = fValidExtensions.split(",");
		}
		if (fSplitExtensions != null && fSplitExtensions.length > 0) {
			for (String ext : fSplitExtensions) {
				if (ext.trim().equalsIgnoreCase(ext)) {
					return true;
				}
			}
		}
		return false;
	}

	protected void logDependentProjLib(String path) {
		if (fDependentProjectLibraries.contains(path)) {
			return; // already warned
		}
		String msg = "Project Library export: Dependency exists on a shared element, need to include project library '"+path+"' when using this project library";
		StudioCorePlugin.log(new Status(Status.WARNING, StudioCorePlugin.PLUGIN_ID, msg));
		fDependentProjectLibraries.add(path);
	}

}
