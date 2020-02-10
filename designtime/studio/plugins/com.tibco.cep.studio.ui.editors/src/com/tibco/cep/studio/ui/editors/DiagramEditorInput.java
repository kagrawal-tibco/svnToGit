package com.tibco.cep.studio.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.tibco.cep.designtime.core.model.Entity;

/**
 * 
 * @author ggrigore
 *
 */
public class DiagramEditorInput implements IEditorInput {

	protected IProject project;
	protected IFile file;
	protected Entity entity;
	
	/**
	 * @param rule
	 * @param project
	 */
	public DiagramEditorInput(IFile rule, IProject project) {
		super();
		this.project = project;
		this.file = rule;
	}

	public IProject getProject() {
		return this.project;
	}
	
	public Entity getSelectedEntity() {
		return entity;
	}
	
	public void setSelectedEntity(Entity entity) {
		this.entity = entity;
	}
	
	public IFile getFile() {
		return this.file;
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
		// TODO Auto-generated method stub
		return file.getName();
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		// TODO Auto-generated method stub
		return file.getName();
	}

	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

}
