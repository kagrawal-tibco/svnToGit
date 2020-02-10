package com.tibco.cep.studio.ui.util;

import java.util.ArrayList;

import org.eclipse.core.resources.IFolder;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

/*
@author ssailapp
@date Jan 21, 2010 4:27:46 PM
 */

public class ProjectFolderContentProvider extends BaseWorkbenchContentProvider {

	public Object[] getChildren(Object element) {
		Object[] objects = super.getChildren(element);
		ArrayList<Object> retObj = new ArrayList<Object>();
		for (Object object: objects) {
			if (object instanceof IFolder)
				retObj.add(object);
		}
		return retObj.toArray(new Object[0]);
	}
	
}
