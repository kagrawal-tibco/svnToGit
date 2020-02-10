package com.tibco.cep.bpmn.core.index.update;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

public class BpmnElementDelta implements IBpmnElementDelta {

	// if this is a CHANGE delta, fOldChild is the removed child, fNewChild is the new one
	private EObject fNewChild; 
	private EObject fOldChild;
	private int fType;

	public BpmnElementDelta(EObject affectedChild, int type) {
		super();
		this.fNewChild = affectedChild;
		this.fType = type;
	}

	public BpmnElementDelta(EObject oldChild, EObject newChild, int type) {
		super();
		this.fOldChild = oldChild;
		this.fNewChild = newChild;
		this.fType = type;
	}
	
	public EObject getAffectedChild() {
		return fType == ADDED ? fNewChild : fOldChild;
	}

	public EObject getAddedChild() {
		return fNewChild;
	}
	
	public EObject getRemovedChild() {
		return fOldChild;
	}
	
	public int getType() {
		return fType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (fType == ADDED) {
			builder.append("+");
		} else if (fType == CHANGED) {
			builder.append("*");
		} else if (fType == REMOVED) {
			builder.append("-");
		}
		
		builder.append((String)EObjectWrapper.wrap(getAddedChild()).getAttribute(BpmnMetaModelConstants.E_ATTR_NAME));
		if (fType == CHANGED) {
			builder.append(" (removedChild -- ");
			builder.append(fOldChild);
			builder.append("::addedChild -- ");
			builder.append(fNewChild);
			builder.append(')');
		} else if (fType == ADDED) {
			builder.append(" (addedChild -- ");
			builder.append(fNewChild);
			builder.append(')');
		}
		return builder.toString();
	}
	

}
