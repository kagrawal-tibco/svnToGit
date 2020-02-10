package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graph.TSGraphMember;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graph.TSNode;

/**
 * 
 * @author ggrigore
 *
 */
public class ProcessPropertyFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IGraphSelection) {
			IGraphSelection selection = (IGraphSelection) toTest;
			toTest = selection.getGraphObject();
		}
		if (toTest instanceof TSGraph) {
			TSGraph tsg = (TSGraph) toTest;
			if(tsg.getUserObject() != null && tsg.getUserObject() instanceof EObject) {
				EObjectWrapper<EClass, EObject> userObjWrapper = 
					EObjectWrapper.wrap((EObject)tsg.getUserObject());
				if(userObjWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
					return true;
				} 
				if(userObjWrapper.isInstanceOf(BpmnModelClass.LANE)) {
					TSGraphMember parent = tsg.getParent();
					if(parent != null && parent instanceof TSNode) {
						@SuppressWarnings("unused")
						TSNode parentNode = (TSNode) parent;
						TSGraphObject parentOwner = parent.getOwner();
						if(parentOwner != null && parentOwner instanceof TSGraph) {
							TSGraph parentGraph = (TSGraph) parentOwner;
							if(parentGraph.getParent() == null) { // root graph for process
								// then this lane is a top level lane
								EObject process = (EObject) parentGraph.getUserObject();
								EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);
								if(processWrapper.isInstanceOf(BpmnModelClass.PROCESS)){
									return true;
								}								
							}
						}
					}
				}
			}
		}
		return false;
	}

}