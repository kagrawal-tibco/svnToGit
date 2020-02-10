package com.tibco.cep.bpmn.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;

/**
 * 
 * @author majha
 *
 */
public interface IBpmnUiIndexUpdateHandler {
	
	public void onIndexUpdate(IProject project, EObject index) throws Exception;

}