package com.tibco.cep.bpmn.core.index.visitor;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants.BASE_ELEMENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants.DOCUMENTATION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants.EXTENSION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants.OPERATION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants.RELATIONSHIP;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants.ROOT_ELEMENT;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.builder.BpmnResourceVisitor;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

public class BpmnElementVisitor extends BpmnResourceVisitor implements IBpmnElementVisitor {
	
	public BpmnElementVisitor(boolean countOnly,IProgressMonitor monitor, boolean resolve) {
		super(countOnly,monitor, resolve);
	}
	
	
	
	public boolean visit(EObject eObj) {
		EObjectWrapper<EClass, EObject> eWrapper = EObjectWrapper.wrap(eObj);
		if(eWrapper.isInstanceOf(BASE_ELEMENT)) {
			return visitBaseElement(eObj);
		} else if(eWrapper.isInstanceOf(EXTENSION)) {
			return visitExtension(eObj);
		}
		return true;
	}
	
	public boolean visitBaseElement(EObject eObj) {
		EObjectWrapper<EClass, EObject> eWrapper = EObjectWrapper.wrap(eObj);
		if(eWrapper.isInstanceOf(ROOT_ELEMENT)) {
			return visitRootElement(eObj);
		} else if(eWrapper.isInstanceOf(RELATIONSHIP)) {
			return visitRelationship(eObj);
		} else if(eWrapper.isInstanceOf(DOCUMENTATION)) {
			return visitDocumentation(eObj);
		} else if(eWrapper.isInstanceOf(OPERATION)) {
			return visitOperation(eObj);
		} 
		return true;
	}

	public boolean visitExtension(EObject obj) {
		return true;
	}

	public boolean visitOperation(EObject obj) {
		return true;
	}

	public boolean visitDocumentation(EObject obj) {
		return true;
	}

	public boolean visitRelationship(EObject obj) {
		return true;
	}

	 public boolean visitRootElement(EObject obj) {
		return true;		
	}

}
