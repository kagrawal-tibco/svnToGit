package com.tibco.cep.studio.ui.filter;

import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.core.SharedElementRootNode;

public class ProjectDependenciesExclusionFilter extends StudioProjectsOnly {
	
	public ProjectDependenciesExclusionFilter(String currentProject){
		super(currentProject);
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean ret = super.select(viewer, parentElement, element);
		if(element instanceof SharedElementRootNode){
			ret = false;
		}
		return ret;
	}

	
}
