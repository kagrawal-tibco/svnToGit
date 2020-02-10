package com.tibco.cep.studio.cluster.topology.properties.filter;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentUnitImpl;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;

public class GeneralTopologyPropertyFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) toTest;
			toTest = selection.getFirstElement();
		}
		if (toTest instanceof TSGraph) {
			TSEGraph graph = (TSEGraph)toTest;
			if(graph.getUserObject() instanceof DeploymentUnitImpl){
				return false;
			}
			return true;
		}
		return false;
	}

}