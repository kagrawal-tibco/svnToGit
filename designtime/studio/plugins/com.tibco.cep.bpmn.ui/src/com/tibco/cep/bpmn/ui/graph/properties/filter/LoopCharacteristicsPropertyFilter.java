package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author majha
 *
 */
public class LoopCharacteristicsPropertyFilter implements IFilter{
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
				if(BpmnModelClass.ACTIVITY.isSuperTypeOf(userObjWrapper.getEClassType())) {
//					if ( userObjWrapper.isInstanceOf( BpmnModelClass.RECEIVE_TASK )) {
//						return false ;
//					}
					return true;
				}
			}
		}else if (toTest instanceof TSEGraph) {
			TSEGraph tsg = (TSEGraph) toTest;
			if(tsg.getUserObject() != null && tsg.getUserObject() instanceof EObject) {
				EObjectWrapper<EClass, EObject> userObjWrapper = 
					EObjectWrapper.wrap((EObject)tsg.getUserObject());
				if(userObjWrapper.isInstanceOf(BpmnModelClass.SUB_PROCESS)) {
					return true;
				}
			}
		}else if(toTest instanceof TSENode) {
			TSENode tsg = (TSENode) toTest;
			if(tsg.getUserObject() != null && tsg.getUserObject() instanceof EObject) {
				EObjectWrapper<EClass, EObject> userObjWrapper = 
					EObjectWrapper.wrap((EObject)tsg.getUserObject());
				if(userObjWrapper.isInstanceOf(BpmnModelClass.JAVA_TASK)) {
					return true;
				}
			}
		}
		return false;
	}
}