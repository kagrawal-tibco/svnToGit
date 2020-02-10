package com.tibco.cep.studio.cluster.topology.properties.filter;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentUnitImpl;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyConstants;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

public class TargetFilesPropertyFilter implements IFilter{

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
			TSENode node = (TSENode)toTest;
			if (ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE) {
				return true;
			}
		}
		if (toTest instanceof TSEGraph) {
			TSEGraph graph = (TSEGraph)toTest;
			if(graph.getUserObject() instanceof DeploymentUnitImpl){
				return true;
			}
		}
		return false;
	}
}