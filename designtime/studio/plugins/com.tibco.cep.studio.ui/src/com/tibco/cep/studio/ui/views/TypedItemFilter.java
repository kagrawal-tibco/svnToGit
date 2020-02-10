package com.tibco.cep.studio.ui.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

@SuppressWarnings("rawtypes")
public class TypedItemFilter extends ViewerFilter {
	
	private Class[] acceptedTypes;
	private Object[] rejectedItems;
	
	

	/**
	 * @param acceptedTypes
	 */
	public TypedItemFilter(Class[] acceptedTypes) {
		this(acceptedTypes,null);
	}



	/**
	 * @param acceptedTypes
	 * @param rejectedItems
	 */
	public TypedItemFilter(Class[] acceptedTypes, Object[] rejectedItems) {
		super();
		this.acceptedTypes = acceptedTypes;
		this.rejectedItems = rejectedItems;
	}



	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (rejectedItems != null) {
			for (int i= 0; i < rejectedItems.length; i++) {
				if (element.equals(rejectedItems[i])) {
					return false;
				}
			}
		}
		for (int i= 0; i < acceptedTypes.length; i++) {
			if (acceptedTypes[i].isInstance(element)) {
				return true;
			}
		}
		return false;
	}

}
