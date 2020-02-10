package com.tibco.cep.studio.rms.client.ui;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.tibco.cep.studio.rms.model.ArtifactReviewTask;

public class RMSClientWorklistEditorInput implements IEditorInput {

	private String extension;
	
	private String name;
	
	private String path;
	
	private List<ArtifactReviewTask> data;

	public RMSClientWorklistEditorInput(String extension) {
		this.name = extension;
		this.extension = extension;
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

	public void setInputData(List<ArtifactReviewTask> tasks) {
		this.data = tasks;
	}

	/**
	 * @return the data
	 */
	public final List<ArtifactReviewTask> getData() {
		return data;
	}
}