package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author majha
 *
 */
public class ReplyEventPropertyFilter implements IFilter{
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
				if(BpmnModelClass.SEND_TASK.isSuperTypeOf(userObjWrapper.getEClassType())) {
					return true;
				}else if(BpmnModelClass.END_EVENT.isSuperTypeOf(userObjWrapper.getEClassType())){
					EList<EObject> listAttribute = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
					for (EObject eObject : listAttribute) {
						if (eObject.eClass().equals(
								BpmnModelClass.MESSAGE_EVENT_DEFINITION)
								|| eObject.eClass().equals(
										BpmnModelClass.SIGNAL_EVENT_DEFINITION))
							return true;
					}
				}
			}
		}
		return false;
	}
}