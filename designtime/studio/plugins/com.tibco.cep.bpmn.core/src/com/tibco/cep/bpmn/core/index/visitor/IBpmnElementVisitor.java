package com.tibco.cep.bpmn.core.index.visitor;

import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.emf.ecore.EObject;

public interface IBpmnElementVisitor extends IResourceVisitor {
	
	
	public boolean visitBaseElement(EObject eObj);
	public boolean visitOperation(EObject obj);
	public boolean visitDocumentation(EObject obj);
	public boolean visitRelationship(EObject obj);
	public boolean visitRootElement(EObject obj);

}
