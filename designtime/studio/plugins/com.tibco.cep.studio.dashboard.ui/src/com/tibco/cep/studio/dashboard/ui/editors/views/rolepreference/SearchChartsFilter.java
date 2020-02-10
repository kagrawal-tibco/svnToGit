package com.tibco.cep.studio.dashboard.ui.editors.views.rolepreference;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class SearchChartsFilter extends ViewerFilter {
	
	private String searchText;
	
	public SearchChartsFilter(String searchText){
		this.searchText = searchText;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof LocalElement) {
			LocalElement localElement = (LocalElement) element;
			if (localElement.getName().toLowerCase().indexOf(searchText) != -1) {
				return true;
			}
			try {
				if (localElement.getPropertyValue(LocalConfig.PROP_KEY_DISPLAY_NAME).toLowerCase().indexOf(searchText) != -1) {
					return true;
				}
			} catch (Exception ignore) {
			}
		}
		return false;
	}

}