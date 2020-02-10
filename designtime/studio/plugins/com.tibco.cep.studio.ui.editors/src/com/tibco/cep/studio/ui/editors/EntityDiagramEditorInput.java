package com.tibco.cep.studio.ui.editors;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.DesignerElement;

/**
 * 
 * @author ggrigore
 *
 */
public class EntityDiagramEditorInput extends FileEditorInput {

	public static int counter = 0;
	private IProject project;
	private Entity entity;
	
	private IFile file;
	private Object invocationContext;
	
	public Entity getSelectedEntity() {
		return entity;
	}

	public void setSelectedEntity(Entity entity) {
		this.entity = entity;
	}

	public EntityDiagramEditorInput(IFile file, IProject project, Object invocationContext) {
		super(file);
		this.project = project;
		this.file = file;
		this.invocationContext = invocationContext;
	}
	
	public EntityDiagramEditorInput(IFile file, IProject project) {
		this(file, project, null);
	}
	
	public IProject getProject() {
		return this.project;
	}
	
	public IFile getFile() {
		return this.file;
	}
	
	public List<DesignerElement> getSelectedEntities() {
		if (this.invocationContext != null) {
			return (List<DesignerElement>) ((Object[])this.invocationContext)[0];
		}
		return null;
	}
	
	public List<IFile> getSelectedProcessFiles() {
		if (this.invocationContext != null) {
			return (List<IFile>) ((Object[])this.invocationContext)[1];
		}
		return null;
	}
	
}


