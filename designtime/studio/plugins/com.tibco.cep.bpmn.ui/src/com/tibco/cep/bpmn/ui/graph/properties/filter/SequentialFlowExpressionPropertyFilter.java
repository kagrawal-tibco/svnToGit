package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tomsawyer.graphicaldrawing.TSEEdge;

/**
 * 
 * @author ggrigore
 *
 */
public class SequentialFlowExpressionPropertyFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IGraphSelection) {
			IGraphSelection selection = (IGraphSelection) toTest;
			toTest = selection.getGraphObject();
		}
		
		if (toTest instanceof TSEEdge) {
			TSEEdge tsg = (TSEEdge) toTest;
			EObjectWrapper<EClass, EObject> userObjWrapper = 
				EObjectWrapper.wrap((EObject)tsg.getUserObject());
			if(BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(userObjWrapper.getEClassType())) {
				EObject source = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
				if(source.eClass().equals(BpmnModelClass.EXCLUSIVE_GATEWAY)||
						source.eClass().equals(BpmnModelClass.INCLUSIVE_GATEWAY)||
						source.eClass().equals(BpmnModelClass.COMPLEX_GATEWAY)){
					EObjectWrapper<EClass, EObject> sourceWrap = 
							EObjectWrapper.wrap(source);
					EObject attribute = sourceWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT);
					if(attribute != null){
						EObjectWrapper<EClass, EObject> attributeWrap = 
								EObjectWrapper.wrap(attribute);
						String id = attributeWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
						String seqId = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
						if(id.equals(seqId)){
							return false;
						}
					}
					return true;
					
				}else if(source.eClass().equals(BpmnModelClass.PARALLEL_GATEWAY)){
					return false;// expression not allowed for outgoing sequnce of parallel gateway
				}
				return false;
			}
		}
		return false;
	}

}