package com.tibco.cep.studio.cluster.topology.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.cluster.topology.model.impl.ClusterImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.ClusterTopology;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentUnitImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.HostResourceImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.SiteImpl;
import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tomsawyer.graphicaldrawing.TSENode;

public class ClusterDiagramEdgeGeneralPropertySection extends AbstractClusterTopologyPropertySection {
	
	protected Text fromText;
	protected Text toText;
    protected Composite composite;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
		getWidgetFactory().createLabel(composite, Messages.getString("From_Node"),  SWT.NONE);
		fromText = getWidgetFactory().createText(composite,"",  SWT.BORDER);

		fromText.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
			}});
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		fromText.setLayoutData(gd);
		fromText.setEnabled(false);
	
		getWidgetFactory().createLabel(composite, Messages.getString("To_Node"),  SWT.NONE);
		toText = getWidgetFactory().createText(composite,"",  SWT.BORDER);

		toText.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
			}});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		toText.setLayoutData(gd);
		toText.setEnabled(false);
	}
	
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {		
		if (tseEdge != null) {
			TSENode sourceNode = (TSENode) tseEdge.getSourceNode();
			TSENode targetNode = (TSENode) tseEdge.getTargetNode();
			if( sourceNode.getUserObject() instanceof ClusterTopology){
				clusterTopology = (ClusterTopology)sourceNode.getUserObject();
				if(clusterTopology instanceof ClusterImpl){
					cluster = (ClusterImpl)clusterTopology;
					fromText.setText(cluster.getName());
					deploymentUnit = null;
					hostResource = null;
					processingUnit = null;
					site = null;
					
					if( targetNode.getUserObject() instanceof ClusterTopology){
						clusterTopology = (ClusterTopology)targetNode.getUserObject();
						if(clusterTopology instanceof SiteImpl){
							site = (SiteImpl)clusterTopology;
							toText.setText(site.getName());
						}
					}
				}
				if(clusterTopology instanceof DeploymentUnitImpl){
					deploymentUnit = (DeploymentUnitImpl)clusterTopology;
					fromText.setText(deploymentUnit.getName());
					cluster= null;
					hostResource = null;
					processingUnit = null;
					site = null;
					
					if( targetNode.getUserObject() instanceof ClusterTopology){
						clusterTopology = (ClusterTopology)targetNode.getUserObject();
						if(clusterTopology instanceof ClusterImpl){
							cluster = (ClusterImpl)clusterTopology;
							toText.setText(cluster.getName());
						}
					}
				}
				if(clusterTopology instanceof HostResourceImpl){
					hostResource = (HostResourceImpl)clusterTopology;
					fromText.setText(hostResource.getHostname());
					cluster= null;
					deploymentUnit = null;
					processingUnit = null;
					site = null;
					
					if( targetNode.getUserObject() instanceof ClusterTopology){
						clusterTopology = (ClusterTopology)targetNode.getUserObject();
						if(clusterTopology instanceof DeploymentUnitImpl){
							deploymentUnit = (DeploymentUnitImpl)clusterTopology;
							toText.setText(deploymentUnit.getName());
						}
					}
				}
			}
		}
		
		if (tseNode != null) { }
		if (tseGraph != null) { }
	}
}