/**
 * 
 */
package com.tibco.cep.studio.ui.forms.components ;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author mgujrath
 *
 */
public class EntityContentProvider implements IStructuredContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getElements(Object element) {
		// TODO Auto-generated method stub
		return ((List<?>)element).toArray();
	}

}
