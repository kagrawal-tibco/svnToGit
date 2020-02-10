package com.tibco.cep.studio.ui.dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.ResourceWorkingSetFilter;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioResourceWorkingSetFilter extends ResourceWorkingSetFilter {

	  private Set<String> extensions;
	  /**
	 * @param extensions
	 */
	public StudioResourceWorkingSetFilter (Set<String> extensions) {
		  this.extensions = extensions;
	  }

	   /* (non-Javadoc)
	 * @see org.eclipse.ui.ResourceWorkingSetFilter#filter(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object[])
	 */
	public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
	        elements = super.filter(viewer, parent, elements);
	        List<Object> list = new ArrayList<Object>();
	        for (Object element: elements) {
	        	IFile file = (IFile)element;
	        	if (extensions.contains(file.getFileExtension())) {
	        		list.add(element);
	        	}
	        }
	        return list.toArray(new Object[list.size()]);
	    }
	
	
}
