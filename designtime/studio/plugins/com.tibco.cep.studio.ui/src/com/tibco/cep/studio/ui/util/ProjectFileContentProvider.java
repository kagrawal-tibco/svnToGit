package com.tibco.cep.studio.ui.util;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

/*
@author ssailapp
@date Jan 21, 2010 4:35:58 PM
 */

public class ProjectFileContentProvider extends BaseWorkbenchContentProvider {

	private String[] filters;
	
	public ProjectFileContentProvider(String[] filters) {
		super();
		this.filters = filters;
	}
	
	public Object[] getChildren(Object element) {
		Object[] objects = super.getChildren(element);
		ArrayList<Object> retObj = new ArrayList<Object>();
		for (Object object: objects) {
			if (object instanceof IFile) {
				IFile file = (IFile) object;
				if (filters != null) {
					for (String filter: filters) {
						if (filter.equalsIgnoreCase(file.getFileExtension()))
							retObj.add(object);		
					}
				}
			} else if (object instanceof IFolder) {
				if (getChildren(object).length > 0)
					retObj.add(object);
			}
		}
		return retObj.toArray(new Object[0]);
	}
	
}
