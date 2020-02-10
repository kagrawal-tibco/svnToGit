package com.tibco.cep.bpmn.core.index;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;

public interface IBpmnIndexUpdate {
	
	public void onIndexUpdate(IProject project, EObject index) throws Exception;

}
