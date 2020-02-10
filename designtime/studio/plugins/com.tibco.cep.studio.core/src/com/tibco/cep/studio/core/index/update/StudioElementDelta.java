package com.tibco.cep.studio.core.index.update;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl;

public class StudioElementDelta extends DesignerElementImpl implements IStudioElementDelta {

	// if this is a CHANGE delta, fOldChild is the removed child, fNewChild is the new one
	private DesignerElement fNewChild; 
	private DesignerElement fOldChild;
	private int fType;

	public StudioElementDelta(DesignerElement affectedChild, int type) {
		super();
		this.fNewChild = affectedChild;
		this.fType = type;
	}

	public StudioElementDelta(DesignerElement oldChild, DesignerElement newChild, int type) {
		super();
		this.fOldChild = oldChild;
		this.fNewChild = newChild;
		this.fType = type;
	}
	
	public DesignerElement getAffectedChild() {
		return fType == ADDED ? fNewChild : fOldChild;
	}

	public DesignerElement getAddedChild() {
		return fNewChild;
	}
	
	public DesignerElement getRemovedChild() {
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
		builder.append(getAffectedChild().getName());
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

	public void accept(IStructuredElementVisitor visitor) {
        visitor.preVisit(this);
        if (visitor.visit(this)) {
        	// visit eContents()?
        }
        visitor.postVisit(this);
	}

}
