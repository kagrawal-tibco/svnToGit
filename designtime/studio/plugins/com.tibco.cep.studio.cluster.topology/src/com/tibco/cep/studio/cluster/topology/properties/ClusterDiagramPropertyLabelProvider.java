package com.tibco.cep.studio.cluster.topology.properties;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.cluster.topology.ClusterTopologyPlugin;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditor;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditorInput;
import com.tibco.cep.studio.cluster.topology.model.impl.ClusterImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.ClusterTopology;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentUnitImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.HostResourceImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.ProcessingUnitConfigImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.SiteImpl;
import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

public class ClusterDiagramPropertyLabelProvider extends LabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String text = null;
		Object selElement = null;
		if (element instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) element;
			selElement = selection.getFirstElement();
		}
		if (selElement instanceof TSENode) {
			TSENode node = (TSENode)selElement;
			return getLabel(node);
		}
		if (selElement instanceof TSEGraph) {
			TSEGraph graph = (TSEGraph)selElement;
			if(graph.getUserObject() instanceof DeploymentUnitImpl){
//				return getMachineLabel((DeploymentUnitImpl)graph.getUserObject());
				return ((DeploymentUnitImpl)graph.getUserObject()).getId();
			}
			ClusterTopologyEditor editor = (ClusterTopologyEditor)
			                        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			return Messages.getString("cluster.topology.general.property.section.title", 
					((ClusterTopologyEditorInput)editor.getEditorInput()).getFile().getProject().getName());
		}
		if (selElement instanceof TSEdge) {   
			TSEdge edge = (TSEdge)selElement;
			return edge.getText()!= null && !edge.getText().trim().equals("") ? edge.getText():"Link";
		}
		return text != null ? text : ""/*super.getText(element)*/;
	}
	
//	private String getMachineLabel(DeploymentUnitImpl du){
//		MachineInfoRef miRef = machine.getMachineInfoRef();
//		if(miRef != null &&   miRef.getRef() != null){
//			MachineInfo machineInfo = (MachineInfo) miRef.getRef();
//			String machineIdRef = machineInfo.getId();
//			return Messages.getString("property.machine.unit", machineIdRef);
//		}
//		return Messages.getString("property.machine.unit", "Machine Unit_Unknown");
//	}
	
	/**
	 * @param node
	 * @return
	 */
	private String getLabel(TSENode node){
		ClusterTopology clusterTopology = (ClusterTopology)node.getUserObject();
		if(clusterTopology instanceof ClusterImpl){
			ClusterImpl cluster = (ClusterImpl)clusterTopology;
			return Messages.getString("property.cluster", cluster.getName());
		}
		if(clusterTopology instanceof DeploymentUnitImpl){
//			return getMachineLabel((DeploymentUnitImpl)clusterTopology);
			return Messages.getString("property.deployment.unit",((DeploymentUnitImpl)clusterTopology).getName());
		}
		if(clusterTopology instanceof HostResourceImpl){
			HostResourceImpl hostResource = (HostResourceImpl)clusterTopology;
			return Messages.getString("property.host", hostResource.getHostname());
		}
		if(clusterTopology instanceof ProcessingUnitConfigImpl){
			ProcessingUnitConfigImpl processingUnit = (ProcessingUnitConfigImpl)clusterTopology;
			return Messages.getString("property.processing.unit", processingUnit.getId());
		}
		if(clusterTopology instanceof SiteImpl){
			SiteImpl site = (SiteImpl)clusterTopology;
			return Messages.getString("property.site", site.getName());
		}
		return "";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		Image img = null;
		
		if (element instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) element;
			element = selection.getFirstElement();
		}
		if (element instanceof TSENode) {
			TSENode node = (TSENode)element;
			ClusterTopology clusterTopology = (ClusterTopology)node.getUserObject();
			if(clusterTopology instanceof ClusterImpl){
				return ClusterTopologyPlugin.getDefault().getImage("icons/cluster_16x16.png");
			}
			if(clusterTopology instanceof DeploymentUnitImpl){
				return ClusterTopologyPlugin.getDefault().getImage("icons/cpu.gif");
			}
			if(clusterTopology instanceof HostResourceImpl){
				return ClusterTopologyPlugin.getDefault().getImage("icons/host_16x16.png");
			}
			if(clusterTopology instanceof ProcessingUnitConfigImpl){
				return ClusterTopologyPlugin.getDefault().getImage("icons/processingunits_16x16.png");
			}
			if(clusterTopology instanceof SiteImpl){
				return ClusterTopologyPlugin.getDefault().getImage("icons/activesite_16x16.gif");
			}
		}
		if (element instanceof TSEdge) {   
			return ClusterTopologyPlugin.getDefault().getImage("icons/transition.png");
		}
		if (element instanceof TSEGraph) {
				TSEGraph graph = (TSEGraph)element;
				if(graph.getUserObject() instanceof DeploymentUnitImpl){
					return ClusterTopologyPlugin.getDefault().getImage("icons/host_16x16.png");
				}
			return ClusterTopologyPlugin.getDefault().getImage("icons/cluster_16x16.png");
		}
		
		return img != null ? img : super.getImage(element);
	}
}
