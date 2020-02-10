package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tibco.cep.bpmn.ui.graph.model.BpmnSupportedEmfType;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author ggrigore
 *
 */
public class ServiceTaskPropertyFilter implements IFilter{
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IGraphSelection) {
			IGraphSelection selection = (IGraphSelection) toTest;
			toTest = selection.getGraphObject();
		}
		if (toTest instanceof TSENode) {
			TSENode tsg = (TSENode) toTest;
			if(tsg.getUserObject() != null && tsg.getUserObject() instanceof EObject) {
				EObjectWrapper<EClass, EObject> userObjWrapper = 
					EObjectWrapper.wrap((EObject)tsg.getUserObject());
				if (BpmnModelClass.SERVICE_TASK.isSuperTypeOf(userObjWrapper
						.getEClassType())) {
					BpmnSupportedEmfType bpmnSupportedEmfType = BpmnSupportedEmfType.getSupportedTypeMap().get(userObjWrapper.getEClassType().getName());
					if(bpmnSupportedEmfType == null)
						return false;
					
					return bpmnSupportedEmfType.canAttachBEResource();
				}
			}
		}
		return false;
	}
}