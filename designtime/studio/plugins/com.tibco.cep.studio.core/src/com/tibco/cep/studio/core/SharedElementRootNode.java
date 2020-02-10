package com.tibco.cep.studio.core;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.core.index.model.DesignerProject;

public class SharedElementRootNode {

	private EList<DesignerProject> fChildren;

	public SharedElementRootNode(EList<DesignerProject> children) {
		this.fChildren = children;
	}

	public EList<DesignerProject> getChildren() {
		return fChildren;
	}
}
