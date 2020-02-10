package com.tibco.cep.studio.core.refactoring;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class StudioRefactoringContext implements IRefactoringContext, IAdaptable {

	private Object fElement;
	private int fType;
	private String fProjectName;

	public StudioRefactoringContext(Object element, int type, String projectName) {
		this.fElement = element;
		this.fType = type;
		this.fProjectName = projectName;
	}

	@Override
	public Object getElement() {
		return fElement;
	}

	@Override
	public String getProjectName() {
		return fProjectName;
	}

	@Override
	public int getType() {
		return fType;
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (IResource.class.equals(adapter) || IFile.class.equals(adapter)) {
			Object element = getElement();
			if (element instanceof IResource) {
				return element;
			}
			if (element instanceof EntityElement) {
				element = ((EntityElement) element).getEntity();
			}
			if (element instanceof Entity) {
				element = IndexUtils.getRootContainer((EObject) element);
				return IndexUtils.getFile(((Entity) element).getOwnerProjectName(), (Entity) element);
			}
		}
		return null;
	}

}
