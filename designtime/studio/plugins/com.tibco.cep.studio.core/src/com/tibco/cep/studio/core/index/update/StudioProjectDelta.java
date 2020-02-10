package com.tibco.cep.studio.core.index.update;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl;


public class StudioProjectDelta extends DesignerProjectImpl implements IStudioElementDelta {

	private DesignerProject fChangedProject;
	private int fType;

	public StudioProjectDelta(DesignerProject changedProject, int type) {
		this.fChangedProject = changedProject;
		this.fType = type;
	}

	public DesignerProject getChangedProject() {
		return fChangedProject;
	}

	public DesignerElement getAffectedChild() {
		return fChangedProject;
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
		builder.append("Project delta\n*");
		if(fChangedProject != null ){
			builder.append(fChangedProject.getName());
			EList<DesignerElement> entries = getEntries();
			for (DesignerElement designerElement : entries) {
				builder.append("\n\t->");
				if (designerElement instanceof FolderDelta) {
					builder.append(((FolderDelta) designerElement).print(2));
				} else {
					builder.append(designerElement.toString());
				}
			}
		}
		return builder.toString();
	}
	
}
