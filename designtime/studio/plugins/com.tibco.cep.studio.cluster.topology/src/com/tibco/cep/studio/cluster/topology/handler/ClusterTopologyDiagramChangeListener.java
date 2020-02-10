package com.tibco.cep.studio.cluster.topology.handler;

import static com.tibco.cep.diagramming.utils.DiagramUtils.refreshDiagram;
import static com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils.editorDirty;
import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.tibco.cep.container.cep_containerVersion;
import com.tibco.cep.diagramming.drawing.DiagramChangeListener;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyDiagramManager;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditor;
import com.tibco.cep.studio.cluster.topology.model.Be;
import com.tibco.cep.studio.cluster.topology.model.DeploymentUnit;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitConfig;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitsConfig;
import com.tibco.cep.studio.cluster.topology.model.Software;
import com.tibco.cep.studio.cluster.topology.model.Ssh;
import com.tibco.cep.studio.cluster.topology.model.StartPuMethod;
import com.tibco.cep.studio.cluster.topology.model.UserCredentials;
import com.tibco.cep.studio.cluster.topology.model.impl.ClusterImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.DeployedFilesImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentMappingImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentMappingsImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentUnitImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.HostResourceImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.ProcessingUnitConfigImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.SiteImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.SoftwareImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.StartPuMethodImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.UserCredentialsImpl;
import com.tibco.cep.studio.cluster.topology.ui.SiteEntityFactory;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyConstants;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.drawing.geometry.shared.TSConstSize;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.ui.shared.TSUIConstants;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;

@SuppressWarnings("serial")
public class ClusterTopologyDiagramChangeListener extends DiagramChangeListener<ClusterTopologyDiagramManager> 
                                                   {
/*
	@Override
	protected Object clone() throws CloneNotSupportedException {
	if( instanceof DeploymentUnitImpl)
	{
		
	}
		return super.clone();
	}*/

	/**
	 * @param manager
	 */
	public static final String UNIQUE_HOST_ID = "HR_";
	public static final String UNIQUE_PROCESSING_UNIT = "PUID_";
	public static final String UNIQUE_QUERY_AGENT = "QueryAgent_";
	public static final String UNIQUE_INFERENCE_AGENT = "InferenceAgent_";
	public static final String UNIQUE_DASHBORAD_AGENT = "DashboardAgent_";
	public static final String UNIQUE_DEPLOYMENT_UNIT = "DU_";
	public static boolean UNDO_FlAG_ST =false;
	
	
	public ClusterTopologyDiagramChangeListener(ClusterTopologyDiagramManager manager) {
		super(manager);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onEdgeAdded(com.tomsawyer.graphicaldrawing.TSEEdge)
	 */
	@Override
	protected void onEdgeAdded(TSEEdge tsEdge) {
		super.onEdgeAdded(tsEdge);
	
    	
		TSENode srcNode = (TSENode)tsEdge.getSourceNode();
		TSENode targetNode = (TSENode)tsEdge.getTargetNode();
		
		if (srcNode.getUserObject() instanceof HostResourceImpl && 
				targetNode.getUserObject() instanceof DeploymentUnitImpl){
			HostResourceImpl hostResourceImpl = (HostResourceImpl)srcNode.getUserObject();
			DeploymentUnitImpl deploymentUnitImpl = (DeploymentUnitImpl)targetNode.getUserObject();
			DeploymentMappingImpl deploymentMappingImpl = manager.getFactory().createDeploymentMappingImpl(null);
			deploymentMappingImpl.setHostRef(hostResourceImpl.getHostResource());
			deploymentMappingImpl.setDeploymentUnitRef(deploymentUnitImpl.getDeploymentUnit());
			
			ClusterImpl clusterImpl = (ClusterImpl)manager.getClusterNode().getUserObject();
			DeploymentMappingsImpl deploymentMappingsImpl = clusterImpl.getDeploymentMappings();
			
			if (deploymentMappingsImpl == null) {
				deploymentMappingsImpl = manager.getFactory().createDeploymentMappingsImpl(null);
			}
			
			tsEdge.setUserObject(deploymentMappingImpl);
			
			
			deploymentMappingsImpl.getDeploymentMapping().add(deploymentMappingImpl.getDeploymentMapping());
			clusterImpl.setDeploymentMappings(deploymentMappingsImpl);
			
			editorDirty((ClusterTopologyEditor)manager.getEditor());
		}
		else if (srcNode.getUserObject() instanceof DeploymentUnitImpl && 
				targetNode.getUserObject() instanceof ClusterImpl){
//			DeploymentUnitImpl duResourceImpl = (DeploymentUnitImpl)srcNode.getUserObject();
//			ClusterImpl clusterImpl = (ClusterImpl)targetNode.getUserObject();
			// add to model....
		}		
		else {
			//ClusterTopologyPlugin.LOGGER.logError(null, "Illegal link between incompatible topology nodes.");
		}
	}

	//TODO - temporary way for editor dirty, needs to be modified when the model is in place.
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onNodeAdded(com.tomsawyer.graphicaldrawing.TSENode)
	 */
	@Override
	protected void onNodeAdded(TSENode tsNode) {
		super.LAYOUT_CHANGE_FLAG = false;
		super.onNodeAdded(tsNode);
		SiteEntityFactory siteFactory=new SiteEntityFactory();
	if (this.manager.isCutGraph() && this.manager.isPasteGraph()) {
			editClipBoardState(tsNode, this.manager.getCutMap(), false);
			
				try {
					if (afterEditNodeAdded(tsNode)) {
						//((ClusterTopologyEditor)this.manager.getEditor()).modified();
						//manager.createLink(tsNode, manager.getClusterNode());
						//editorDirty((ClusterTopologyEditor)manager.getEditor());
						return;
					}
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		if (this.manager.isCopyGraph() && this.manager.isPasteGraph()) {
			editClipBoardState(tsNode, this.manager.getCopyMap(), true);
			try {
				if (afterEditNodeAdded(tsNode)) { 
					//manager.createLink(tsNode, manager.getClusterNode());
					//editorDirty((ClusterTopologyEditor)manager.getEditor());
					return;
				}
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		if(tsNode == null) {
			return;
		}
		
		//Host Node 
		if(ClusterTopologyUtils.getNodeType(tsNode) == ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE){
			DeploymentUnitImpl deploymentUnitImpl=null;
			if(tsNode.getUserObject()==null){
			deploymentUnitImpl = manager.getFactory().createDeploymentUnitImpl(null);

			DeployedFilesImpl dfsImpl = manager.getFactory().createDeployedFilesImpl(null);			
			dfsImpl.setCddDeployed("");
			dfsImpl.setEarDeployed("");
			deploymentUnitImpl.setDeployedFiles(dfsImpl);
			
			ProcessingUnitsConfig pus = manager.getFactory().createProcessingUnitsConfigImpl(null,deploymentUnitImpl.getDeploymentUnit()).getProcessingUnitsConfig();
			deploymentUnitImpl.getDeploymentUnit().setProcessingUnitsConfig(pus);
		
//			ProcessingUnit pu = manager.getFactory().createProcessingUnitImpl(null).getProcessingUnit();
//			pu.setId("Process_UNKNOWN");
//			pu.setPuid("");
//			pus.getProcessingUnit().add(pu);
			
			
			String duName = tsNode.getName().toString();	
			if (tsNode.getName() != null) {
				if(manager instanceof ClusterTopologyDiagramManager) {
					ClusterTopologyDiagramManager clusterManager = (ClusterTopologyDiagramManager) manager;
					String duId = UNIQUE_DEPLOYMENT_UNIT + clusterManager.getNodeIdNumber(ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE);
					deploymentUnitImpl.setId(duId);
					duName = UNIQUE_DEPLOYMENT_UNIT + clusterManager.getDeploymentUnitName();
					deploymentUnitImpl.setName(duName);
					tsNode.setName(duName);
					//this.addNodeLabel(tsNode, duId);
					if( !clusterManager.addDeploymentUnit(deploymentUnitImpl.getDeploymentUnit()) ) {
						System.out.println("Cannot Add");
					}
				}
			}
		
			tsNode.setUserObject(deploymentUnitImpl);
			//Adding Machine Object to Host Child Graph
			if(tsNode.getChildGraph() != null){
				tsNode.getChildGraph().setUserObject(deploymentUnitImpl);
			}
			manager.createLink(tsNode, manager.getClusterNode());
			}else{
				deploymentUnitImpl=siteFactory.newDeploymentUnitImpl((DeploymentUnitImpl)tsNode.getUserObject());
				tsNode.setUserObject(deploymentUnitImpl);
			}
			ClusterImpl clusterImpl = (ClusterImpl)manager.getClusterNode().getUserObject();
			clusterImpl.getDeploymentUnits().getDeploymentUnit().add(deploymentUnitImpl.getDeploymentUnit());

			
		} else if(ClusterTopologyUtils.getNodeType(tsNode) == ClusterTopologyConstants.HOST_NODE){
			HostResourceImpl hostResourceImpl;
			if(tsNode.getUserObject()==null){
			 hostResourceImpl = manager.getFactory().createHostResourceImpl(null);
			tsNode.setUserObject(hostResourceImpl);
			
			UserCredentialsImpl userCredentialsImpl = manager.getFactory().createUserCredentialsImpl(null);
			UserCredentials uLogins = userCredentialsImpl.getUserCredentials();
			uLogins.setUsername("");
			uLogins.setPassword("");
			
			SoftwareImpl softwareImpl = manager.getFactory().createSoftwareImpl(null);
			
			Software sw = softwareImpl.getSoftware();
			List<Be> beList = sw.getBe();
			
			Be be = manager.getFactory().createBeImpl(null).getBe();
			be.setHome("");
			be.setVersion(cep_containerVersion.version);
			be.setTra("");
			beList.add(be);
			
			Ssh ssh = manager.getFactory().createSshImpl(null).getSsh();
			ssh.setPort("22");
			StartPuMethodImpl startPuMethodImpl = manager.getFactory().createStartPuMethodImpl(null);
			
			StartPuMethod startPuMethod =startPuMethodImpl.getStartPuMethod();
			if(startPuMethod!=null)
			{
			startPuMethod.setSsh(ssh);
			}
			if (tsNode.labels() != null && tsNode.labels().size() > 0) {
				String hostResourceName = ((TSENodeLabel) tsNode.labels().get(0)).getText();
				hostResourceImpl.setHostname(hostResourceName);
				if (hostResourceImpl.getId() == null || hostResourceImpl.getId().trim().length() == 0) {
				{
						String hrId = UNIQUE_HOST_ID + ((ClusterTopologyDiagramManager)manager).getNodeIdNumber(ClusterTopologyConstants.HOST_NODE);
						hostResourceImpl.setId(hrId);
						String hrName = UNIQUE_HOST_ID + manager.getHostResourceName();
						hostResourceImpl.setHostname(hrName);
						tsNode.setName(hrName);
						tsNode.setAttribute("Text_Orientation", new Integer(TSUIConstants.TOP_JUSTIFY));
						this.addNodeLabel(tsNode, hrName);
						((ClusterTopologyDiagramManager)manager).addHostResource(hostResourceImpl.getHostResource());
						
				}
				}
			}
			else {
				hostResourceImpl.setId("HR_id");
				hostResourceImpl.setHostname("hostname");
			}
			
			tsNode.setUserObject(hostResourceImpl);
			tsNode.setResizability(TSESolidObject.RESIZABILITY_TIGHT_FIT);
			hostResourceImpl.setOsType("");
			hostResourceImpl.setUserCredentials(userCredentialsImpl);
			hostResourceImpl.setSoftware(softwareImpl);
			hostResourceImpl.setStartPuMethod(startPuMethodImpl);
			
			
			
		}else{
			hostResourceImpl= siteFactory.newHostResourceImpl((HostResourceImpl) tsNode.getUserObject());
			tsNode.setUserObject(hostResourceImpl);
		}
			SiteImpl siteImpl = (SiteImpl)manager.getSiteNode().getUserObject();
			siteImpl.getHostResources().getHostResource().add(hostResourceImpl.getHostResource());
		}
		
		editorDirty((ClusterTopologyEditor)manager.getEditor());
		setWorkbenchSelection(tsNode, manager.getEditor());	
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				refreshDiagram(manager);
				refreshOverview(manager.getEditor().getEditorSite(), true, true);
				
			}
		});
		super.LAYOUT_CHANGE_FLAG = true;
	}
	
	

	private boolean afterEditNodeAdded(TSENode tsNode) throws CloneNotSupportedException {
		if(tsNode.getAttributeValue(ClusterTopologyConstants.NODE_TYPE)==null){
		 return false;
	}
	 tsNode.getOwnerGraph();
	 if(tsNode.getAttributeValue(ClusterTopologyConstants.NODE_TYPE).equals(ClusterTopologyConstants.PU_NODE)){
	    ProcessingUnitConfigImpl processingUnitConfigImpl=(ProcessingUnitConfigImpl) ((ProcessingUnitConfigImpl) tsNode.getUserObject());
		tsNode.setUserObject(processingUnitConfigImpl);
	 }else if(tsNode.getAttributeValue(ClusterTopologyConstants.NODE_TYPE).equals(ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE)){
		 
		 DeploymentUnitImpl deploymentUnitImpl=(DeploymentUnitImpl) ((DeploymentUnitImpl) tsNode.getUserObject());
		 tsNode.setUserObject(deploymentUnitImpl);
		 if(!manager.isDuLinkCopied()){
			 manager.createLink(tsNode, manager.getClusterNode());
		 }
		manager.refreshNode(tsNode);
		 //TSENestingManager.expand(tsNode);
	 }else if(tsNode.getAttributeValue(ClusterTopologyConstants.NODE_TYPE).equals(ClusterTopologyConstants.HOST_NODE) )
	 {
		HostResourceImpl hostResourceImpl=(HostResourceImpl) ((HostResourceImpl)tsNode.getUserObject());
		tsNode.setUserObject(hostResourceImpl);
	}
	 StudioUIUtils.invokeOnDisplayThread(new Runnable() {
			public void run() {
				((ClusterTopologyEditor) manager.getEditor()).modified();
			}
		}, false);
		return true;
	}
     
	
	private void editClipBoardState(TSENode tsNode, Map<String, Object> map,
			boolean b) {
		SiteEntityFactory siteFactory=new SiteEntityFactory();
		if (tsNode != null && tsNode.getName() == null) {
			return;
		}
		if (map != null && !map.containsKey(tsNode.getName().toString())) {
			return;
		}
		//tsNode.setUserObject(map.get(tsNode.getName().toString()));
		
		if (tsNode != null && tsNode.getName() != null) {
			if(manager instanceof ClusterTopologyDiagramManager) {
				ClusterTopologyDiagramManager clusterManager = (ClusterTopologyDiagramManager) manager;
				if(tsNode.getAttributeValue(ClusterTopologyConstants.NODE_TYPE).equals(ClusterTopologyConstants.PU_NODE))
				{
					DeploymentUnitImpl duimpl=(DeploymentUnitImpl) tsNode.getOwnerGraph().getUserObject();
					ProcessingUnitConfigImpl processingUnitConfigImpl=siteFactory.newProcessingUnitConfigImpl(
							((ProcessingUnitConfigImpl)map.get(tsNode.getName().toString())),duimpl);
					
					ProcessingUnitConfig pu=processingUnitConfigImpl.getProcessingUnitConfig();
					
					//DeploymentUnit du= processingUnitConfigImpl.getDeploymentUnitImpl().getDeploymentUnit();
					DeploymentUnit du= duimpl.getDeploymentUnit();
					String puId = UNIQUE_PROCESSING_UNIT + clusterManager.getNodeIdNumber(ClusterTopologyConstants.PU_NODE,du);
					tsNode.setName(puId);
					manager.addProcessingUnit(pu);
					pu.setId(puId);
					ClusterTopologyUtils.getPUIDSet().add(puId);
					//pu.setPuid("");
					tsNode.setUserObject(processingUnitConfigImpl);
					du.getProcessingUnitsConfig().getProcessingUnitConfig().add(pu);
					//ClusterDiagramDeploymentUnitPropertySection.populateProcessingUnit(pu.getId());
										
				}else if(tsNode.getAttributeValue(ClusterTopologyConstants.NODE_TYPE).equals(ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE))
				{
					DeploymentUnitImpl deploymentUnitImpl=siteFactory.newDeploymentUnitImpl((DeploymentUnitImpl)map.get(tsNode.getName().toString()));
					String duId = UNIQUE_DEPLOYMENT_UNIT + clusterManager.getNodeIdNumber(ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE);
					
					
					deploymentUnitImpl.setId(duId);
					String duName = UNIQUE_DEPLOYMENT_UNIT + clusterManager.getDeploymentUnitName();
					deploymentUnitImpl.setName(duName);
					tsNode.setName(duName);
					if( !clusterManager.addDeploymentUnit(deploymentUnitImpl.getDeploymentUnit()) ) {
						System.out.println("Cannot Add");
					}
					//This portion is for PU's to get added to the diagram
					tsNode.getChildGraph().edges().clear();
					tsNode.getChildGraph().nodes().clear();

					ClusterTopologyUtils.getDUIdsSet().add(deploymentUnitImpl.getDeploymentUnit().getId());
				ProcessingUnitsConfig pus=deploymentUnitImpl.getDeploymentUnit().getProcessingUnitsConfig();
				if(pus!=null){
					List<ProcessingUnitConfig>puList = pus.getProcessingUnitConfig();
					//DUName duName = new DUName(du.getName(),editor.getTitleToolTip());
					//duImpl.setDuName(duName);
					//ClusterTopologyUtils.getDUNamesSet().add(duName);
					
					ArrayList<TSENode>puNodeList = new ArrayList<TSENode>();
					for(ProcessingUnitConfig pu : puList){
					TSENode puNode = manager.addPU(tsNode, new ProcessingUnitConfigImpl(pu,deploymentUnitImpl));
					puNode.setName(pu.getId());
					puNodeList.add(puNode);
					ClusterTopologyUtils.getPUIDSet().add(pu.getId());
					}			
				}
					//TSENestingManager.expand(tsNode);
					tsNode.setUserObject(deploymentUnitImpl);
					ClusterImpl clusterImpl = (ClusterImpl)manager.getClusterNode().getUserObject();
					clusterImpl.getDeploymentUnits().getDeploymentUnit().add(deploymentUnitImpl.getDeploymentUnit());
				}else if(tsNode.getAttributeValue(ClusterTopologyConstants.NODE_TYPE).equals(ClusterTopologyConstants.HOST_NODE))
				{
					HostResourceImpl hostResourceImpl=siteFactory.newHostResourceImpl((HostResourceImpl)map.get(tsNode.getName().toString()));
					String huId = UNIQUE_HOST_ID + clusterManager.getNodeIdNumber(ClusterTopologyConstants.HOST_NODE);
					
					tsNode.setName(huId);
					hostResourceImpl.setId(huId);
					String hrName = UNIQUE_HOST_ID + manager.getHostResourceName();
					hostResourceImpl.setHostname(hrName);
					//tsNode.setName(hrName);
					this.addNodeLabel(tsNode, hrName);
					tsNode.setUserObject(hostResourceImpl);
					((ClusterTopologyDiagramManager)manager).addHostResource(hostResourceImpl.getHostResource());
					SiteImpl siteImpl = (SiteImpl)manager.getSiteNode().getUserObject();
					siteImpl.getHostResources().getHostResource().add(hostResourceImpl.getHostResource());
				}
			}
		}
		}

	private void addNodeLabel(TSENode node, final String value){
		final TSENodeLabel label;
		if (node.labels() != null && node.labels().size() > 0) {
			label = (TSENodeLabel) node.labels().get(0);
		}
		else {
			label = (TSENodeLabel) node.addLabel();
		}
		((TSEAnnotatedUI)label.getUI()).setTextAntiAliasingEnabled(true);
		label.setDefaultOffset();
		if (value != null && !value.isEmpty()) {
			label.setName((String) value);
			label.setSize(new TSConstSize(50.0,50.0));
		}
		
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onNodeDeleted(com.tomsawyer.graphicaldrawing.TSENode)
	 */
	@Override
	protected void onNodeDeleted(TSENode tsNode) {
		super.LAYOUT_CHANGE_FLAG = false;
		if( !(tsNode.getUserObject() != null && (tsNode.getUserObject() instanceof SiteImpl || tsNode.getUserObject() instanceof ClusterImpl)) ){
			super.onNodeDeleted(tsNode);
			if (tsNode.getUserObject() != null && tsNode.getUserObject() instanceof ProcessingUnitConfigImpl) {
				ProcessingUnitConfigImpl pu = (ProcessingUnitConfigImpl) tsNode.getUserObject();
				TSENode duNode = (TSENode) tsNode.getOwnerGraph().getParent();
				if(duNode != null ) {
					DeploymentUnitImpl du = (DeploymentUnitImpl) duNode.getUserObject();
					du.getProcessingUnitsConfig().getProcessingUnitConfig().remove(pu.getProcessingUnitConfig());
				}
			}
			if (tsNode.getUserObject() != null && tsNode.getUserObject() instanceof DeploymentUnitImpl) {
				DeploymentUnitImpl du = (DeploymentUnitImpl) tsNode.getUserObject();
				((ClusterTopologyDiagramManager)manager).removeDeploymentUnit(du.getDeploymentUnit());
				ClusterImpl clusterImpl = (ClusterImpl)manager.getClusterNode().getUserObject();
				clusterImpl.getDeploymentUnits().getDeploymentUnit().remove(du.getDeploymentUnit());
			}
			if (tsNode.getUserObject() != null && tsNode.getUserObject() instanceof HostResourceImpl) {
				HostResourceImpl hr = (HostResourceImpl) tsNode.getUserObject();
				((ClusterTopologyDiagramManager)manager).removeHostResource(hr.getHostResource());
				SiteImpl siteImpl = (SiteImpl)manager.getSiteNode().getUserObject();
				siteImpl.getHostResources().getHostResource().remove(hr.getHostResource());
			}
			//ClusterTopologyPlugin.LOGGER.logError(null, "deleted node");
			editorDirty((ClusterTopologyEditor)manager.getEditor());
		} else {
			manager.getCommandManager().undo();
			manager.getCommandManager().popUndoHistory();
			manager.getCommandManager().popUndoHistory();
		}
		super.LAYOUT_CHANGE_FLAG = true;
	}	
	
	protected void onEdgeDeleted(TSEEdge tsEdge) {
		super.onEdgeDeleted(tsEdge);
		
		Object userObj = tsEdge.getUserObject();
		
		DeploymentMappingImpl edgeImpl = null;
		
		if (userObj != null && userObj instanceof DeploymentMappingImpl) {
			edgeImpl = (DeploymentMappingImpl) userObj;
		}
		
		ClusterImpl clusterImpl = (ClusterImpl)manager.getClusterNode().getUserObject();
		DeploymentMappingsImpl deploymentMappingsImpl = clusterImpl.getDeploymentMappings();
		
		if (deploymentMappingsImpl == null) {
			deploymentMappingsImpl = manager.getFactory().createDeploymentMappingsImpl(null);
		}
		
		if (edgeImpl != null) {
			deploymentMappingsImpl.getDeploymentMapping().remove(edgeImpl.getDeploymentMapping());
//			clusterImpl.setDeploymentMappings(deploymentMappingsImpl);
		}
		
		editorDirty((ClusterTopologyEditor)manager.getEditor());
	}		
}
