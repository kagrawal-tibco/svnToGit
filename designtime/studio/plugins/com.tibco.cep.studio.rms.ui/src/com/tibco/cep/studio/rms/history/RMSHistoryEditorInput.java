package com.tibco.cep.studio.rms.history;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.tibco.cep.studio.rms.client.ui.RMSClientWorklistEditorInput;

/**
 * 
 * @author sasahoo
 *
 */
public class RMSHistoryEditorInput implements IEditorInput {

	private String extension;
	
	private String name;
	
	private String path;
	
	private String resourcePath;
	
	/**
	 * The RMS project name.
	 */
	private String repoProjectName;
	
	/**
	 * The local checked out project name.
	 */
	private String localProjectName;
	
	private String historyURL;
	
	/**
	 * 
	 * @param historyURL
	 * @param extension
	 * @param resourcePath
	 * @param repoProjectName
	 * @param localProjectName
	 */
	public RMSHistoryEditorInput(String historyURL, 
			                     String extension, 
			                     String resourcePath, 
			                     String repoProjectName,
			                     String localProjectName) {
		this.name = extension;
		this.extension = extension;
		this.resourcePath = resourcePath;
		this.repoProjectName = repoProjectName;
		this.localProjectName = localProjectName;
		this.historyURL = historyURL;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public void setProjectName(String projectName) {
		this.localProjectName = projectName;
	}

	public void setHistoryURL(String historyURL) {
		this.historyURL = historyURL;
	}

	/**
	 * @return the extension
	 */
	public final String getExtension() {
		return extension;
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return extension;
	}

	public void setExtensionName(String newName) {
		this.extension = newName;
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return "";
	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}
		if (obj instanceof RMSClientWorklistEditorInput) {
			RMSClientWorklistEditorInput input = (RMSClientWorklistEditorInput) obj;
			if (getFileName().equals(input.getFileName())) {
				return true;
			}
		}
		return false;
	}

	public int hashCode() {
		return extension.hashCode();
	}

	public String getFullPath() {
		return path;
	}

	public String getFileName() {
		return name;
	}

	public void setFullPath(String fullPath) {
		this.path = fullPath;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	

	/**
	 * @return the repoProjectName
	 */
	public final String getRepoProjectName() {
		return repoProjectName;
	}

	/**
	 * @return the localProjectName
	 */
	public final String getLocalProjectName() {
		return localProjectName;
	}

	/**
	 * @return the historyURL
	 */
	public final String getHistoryURL() {
		return historyURL;
	}
}