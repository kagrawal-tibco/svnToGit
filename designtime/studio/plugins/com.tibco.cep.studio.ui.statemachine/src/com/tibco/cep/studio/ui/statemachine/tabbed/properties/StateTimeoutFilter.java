package com.tibco.cep.studio.ui.statemachine.tabbed.properties;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author sasahoo
 *
 */
public class StateTimeoutFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) toTest;
			toTest = selection.getFirstElement();
		}
		if (toTest instanceof TSENode) {
			TSENode  tSENode = (TSENode) toTest;
			Object object= tSENode.getUserObject();
           
			if(object instanceof StateSubmachine)
				return false;
			
			if(object instanceof StateSimple || object instanceof StateComposite){
				return true;
			}
		}
		if (toTest instanceof TSEGraph) {
			TSEGraph  tSEGraph = (TSEGraph) toTest;
			Object object= tSEGraph.getUserObject();
			
			if (object instanceof StateMachine){
				return true;
			}
			if (object instanceof StateComposite){
				return true;
			}
			return false;
		}
		return false;
	}

}
