package com.tibco.cep.bpmn.core.index.update;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.impl.FolderImpl;

public class FolderDelta extends FolderImpl implements IBpmnElementDelta {

	private ElementContainer fAffectedChild;
	private int fType;

	public FolderDelta(ElementContainer affectedChild, int type) {
		super();
		this.fAffectedChild = affectedChild;
		this.fType = type;
	}

	public DesignerElement getAffectedChild() {
		return fAffectedChild;
	}

	public int getType() {
		return fType;
	}

	public String print(int depth) {
		StringBuilder builder = new StringBuilder();
		if (fType == ADDED) {
			builder.append("+");
		} else if (fType == CHANGED) {
			builder.append("*");
		} else if (fType == REMOVED) {
			builder.append("-");
		}
		builder.append(fAffectedChild.getName());
		EList<DesignerElement> entries = getEntries();
		for (DesignerElement designerElement : entries) {
			builder.append('\n');
			builder.append(getTabs(depth));
			builder.append("->");
			if (designerElement instanceof FolderDelta) {
				builder.append(((FolderDelta) designerElement).print(depth+1));
			} else {
				builder.append(designerElement.toString());
			}
		}
		return builder.toString();
	}

	private String getTabs(int depth) {
		StringBuilder tabs = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			tabs.append('\t');
		}
		return tabs.toString();
	}

}
