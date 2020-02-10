package com.tibco.cep.studio.cluster.topology.tools;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.List;

import com.tibco.cep.diagramming.tool.CreateEdgeTool;
import com.tibco.cep.diagramming.utils.TSImages;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyDiagramManager;
import com.tibco.cep.studio.cluster.topology.model.DeploymentMapping;
import com.tibco.cep.studio.cluster.topology.model.impl.ClusterImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentMappingsImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentUnitImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.HostResourceImpl;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyConstants;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddEdgeCommand;
/**
 * @author hitesh
 *
 */
public class ClusterTopologyCreateEdgeTool extends CreateEdgeTool {
	
	private ClusterTopologyDiagramManager manager;
	
	public ClusterTopologyCreateEdgeTool(ClusterTopologyDiagramManager manager) {
		super(manager);
		this.manager = manager;
	}

	//To allow edge creation only between host and cluster
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateEdgeTool#isActionAllowed()
	 */
	@Override
	public boolean isActionAllowed() {
		TSENode srcTSNode = this.getSourceNode();
		TSENode tgtTSNode = this.getTargetNode();
		
		if (ClusterTopologyUtils.getNodeType(srcTSNode) == ClusterTopologyConstants.HOST_NODE &&
				ClusterTopologyUtils.getNodeType(tgtTSNode) == ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE) {
			if (isMappingAvailable(srcTSNode, tgtTSNode)) {
				this.cancelAddRegionAction();
				return false;
			}
			return true;
		}
		else {
			this.cancelAddRegionAction();
			return false;
		}
	}
	
	/**
	 * @param srcNode
	 * @param targetNode
	 * @return
	 */
	private boolean isMappingAvailable(TSENode srcNode, TSENode targetNode) {
		HostResourceImpl hostResourceImpl = (HostResourceImpl)srcNode.getUserObject();
		DeploymentUnitImpl deploymentUnitImpl = (DeploymentUnitImpl)targetNode.getUserObject();

		ClusterImpl clusterImpl = (ClusterImpl)manager.getClusterNode().getUserObject();
		DeploymentMappingsImpl deploymentMappingsImpl = clusterImpl.getDeploymentMappings();
		if (deploymentMappingsImpl != null 
				&& deploymentMappingsImpl.getDeploymentMapping() != null) {
			for (DeploymentMapping map : deploymentMappingsImpl.getDeploymentMapping()) {
				if (map.getDeploymentUnitRef() == deploymentUnitImpl.getDeploymentUnit() 
						&& map.getHostRef() == hostResourceImpl.getHostResource()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void cancelAddRegionAction() {
		this.cancelAction();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = TSImages.createIcon("icons/Invalid10x10.png").getImage();
		Cursor c = tk.createCustomCursor(image, new Point(0,0),"invalid");
		this.setActionCursor(c);
		this.setCursor(c);
	}	

	@SuppressWarnings("rawtypes")
	@Override
	protected TSEAddEdgeCommand newAddEdgeCommand(TSENode arg0, TSENode arg1,
			List arg2) {
		// TODO Auto-generated method stub
		return super.newAddEdgeCommand(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateNodeTool#resetPaletteSelection()
	 */
	@Override
	public void resetPaletteSelection(){
		StudioUIUtils.resetPaletteSelection();
	}
}