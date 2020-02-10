package com.tibco.cep.studio.ui.search;

import org.eclipse.emf.ecore.EObject;

public interface IStudioNestedMatch {

	public EObject getMatchedElement();
	public void setExact(boolean exact);
	public boolean isExact();
	
}
