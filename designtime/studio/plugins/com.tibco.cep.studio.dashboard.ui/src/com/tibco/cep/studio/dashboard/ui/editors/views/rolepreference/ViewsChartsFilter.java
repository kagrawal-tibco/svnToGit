package com.tibco.cep.studio.dashboard.ui.editors.views.rolepreference;

import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class ViewsChartsFilter extends ViewerFilter {
	
	private List<LocalElement> elementsToShow;

	public ViewsChartsFilter(List<LocalElement> elementsToShow){
		if (elementsToShow == null) {
			throw new IllegalArgumentException();
		}
		this.elementsToShow = elementsToShow;
		
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return elementsToShow.contains(element);
	}

}