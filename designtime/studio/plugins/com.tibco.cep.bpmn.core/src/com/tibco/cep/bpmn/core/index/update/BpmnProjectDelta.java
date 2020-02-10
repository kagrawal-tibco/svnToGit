package com.tibco.cep.bpmn.core.index.update;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;


public class BpmnProjectDelta implements IBpmnElementDelta {

	private EObject fChangedProject;
	private int fType;

	public BpmnProjectDelta(EObject changedProject, int type) {
		this.fChangedProject = changedProject;
		this.fType = type;
	}

	public EObject getChangedProject() {
		return fChangedProject;
	}

	public EObject getAffectedChild() {
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
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(fChangedProject);
			builder.append(wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME).toString());
			for (TreeIterator<EObject> contents = fChangedProject.eAllContents();contents.hasNext();) {
				EObject eObj = contents.next();
				builder.append("\n\t->");
				builder.append(eObj.toString());				
			}
//			EList<DesignerElement> entries = getEntries();
//			for (DesignerElement designerElement : entries) {
//				builder.append("\n\t->");
//				if (designerElement instanceof FolderDelta) {
//					builder.append(((FolderDelta) designerElement).print(2));
//				} else {
//					builder.append(designerElement.toString());
//				}
//			}
		}
		return builder.toString();
	}
	
}
