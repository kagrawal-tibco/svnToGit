package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tomsawyer.graphicaldrawing.TSEConnector;

/**
 * 
 * @author ggrigore
 *
 */
public class BoundaryEventPropertyFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IGraphSelection) {
			IGraphSelection selection = (IGraphSelection) toTest;
			toTest = selection.getGraphObject();
		}
		if (toTest instanceof TSEConnector) {
			TSEConnector tsg = (TSEConnector) toTest;
			if(tsg.getUserObject() != null && tsg.getUserObject() instanceof EObject) {
				EObjectWrapper<EClass, EObject> userObjWrapper = 
					EObjectWrapper.wrap((EObject)tsg.getUserObject());
				if(BpmnModelClass.BOUNDARY_EVENT.isSuperTypeOf(userObjWrapper.getEClassType())) {
					EList<EObject> listAttribute = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
					if(listAttribute.size() >0){
						EObject eObject = listAttribute.get(0);
						EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
						if(BpmnModelClass.TIMER_EVENT_DEFINITION.isSuperTypeOf(wrap.getEClassType())){
							return false;
						}
							
					}else{
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}

}