package com.tibco.cep.studio.cluster.topology.handler;

import static com.tibco.cep.diagramming.utils.DiagramUtils.isClipBoardContentsAvailable;

import javax.swing.AbstractButton;

import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.tool.popup.SelectToolHandler;
import com.tibco.cep.studio.cluster.topology.tools.ClusterTopologySelectTool;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyConstants;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils;
import com.tomsawyer.graph.TSGraphMember;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

/**
 * @author hitesh
 *
 **/

public class ClusterTopologySelectToolHandler extends SelectToolHandler {

	public static void chooseState(AbstractButton button, TSEGraph graph,SelectTool tool)
	{
		String command = button.getActionCommand();
		TSEObject object = tool.hitObject;
		if( EntityResourceConstants.PASTE_GRAPH.equals(command)){
			
			button.setEnabled(isClipBoardContentsAvailable(((ClusterTopologySelectTool)tool).getClusterTopologyDiagramManager()));
			
		}
		//For Delete Menu item
		if(object instanceof TSGraphMember) {
		final TSGraphMember member = (TSGraphMember) object;	
		if (member instanceof TSENode){
			TSENode node = (TSENode)member;
		      if(EntityResourceConstants.DELETE_SELECTED.equals(command)){
				if((ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.CLUSTER_NODE) || 
					(ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.SITE_NODE)){
						button.setEnabled(false);
					} else {
						button.setEnabled(true);
					}
				} else if(EntityResourceConstants.CUT_GRAPH.equals(command)){
					if((ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.CLUSTER_NODE) || 
							(ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.SITE_NODE)){
								button.setEnabled(false);
							} else {
								button.setEnabled(true);
							}
					
				}else if(EntityResourceConstants.COPY_GRAPH.equals(command)){
					if((ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.CLUSTER_NODE) || 
							(ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.SITE_NODE)){
								button.setEnabled(false);
							} else {
								button.setEnabled(true);
							}
					
				}/*if( EntityResourceConstants.PASTE_GRAPH.equals(command)){
					
					button.setEnabled(isClipBoardContentsAvailable(((ClusterTopologySelectTool)tool).getClusterTopologyDiagramManager()));
					
				}*/
				}else if (member instanceof TSEEdge) {
					TSEEdge edge = (TSEEdge)member;
					TSENode sourceNode = (TSENode) edge.getSourceNode();
					TSENode targetNode = (TSENode) edge.getTargetNode();
					if(EntityResourceConstants.DELETE_SELECTED.equals(command)){
					if((ClusterTopologyUtils.getNodeType(sourceNode) == ClusterTopologyConstants.HOST_NODE)&& 
							(ClusterTopologyUtils.getNodeType(targetNode) == ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE)){
						button.setEnabled(true);
					} else {
						button.setEnabled(false);
					}
				    }else if(EntityResourceConstants.COPY_GRAPH.equals(command)
				    		||EntityResourceConstants.CUT_GRAPH.equals(command)){
				    	button.setEnabled(false);
				    }
			}
		}
	}
}
