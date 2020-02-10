package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author sasahoo
 *
 */
public class VariableMappingPropertyFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IGraphSelection) {
			IGraphSelection selection = (IGraphSelection) toTest;
			toTest = selection.getGraphObject();
		}
		if (toTest instanceof TSGraph || toTest instanceof TSENode) {
			if(toTest instanceof TSGraph) {
				TSGraph tsg = (TSGraph) toTest;
				if(tsg.getUserObject() != null && tsg.getUserObject() instanceof EObject) {
					EObjectWrapper<EClass, EObject> userObjWrapper = 
						EObjectWrapper.wrap((EObject)tsg.getUserObject());
					if(userObjWrapper.isInstanceOf(BpmnModelClass.LANE) || 
							userObjWrapper.isInstanceOf(BpmnModelClass.SUB_PROCESS)||
							userObjWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
						return true;
					} 
				}
			}
			if(toTest instanceof TSENode) {
				TSENode node = (TSENode) toTest;
				if(node.getUserObject() != null && node.getUserObject() instanceof EObject) {
					EObjectWrapper<EClass, EObject> userObjWrapper = 
						EObjectWrapper.wrap((EObject)node.getUserObject());
					if( userObjWrapper.isInstanceOf(BpmnModelClass.SUB_PROCESS)) {
					   return true;
					}
				}
			}
		}
		return false;
	}

}