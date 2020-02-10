package com.tibco.cep.studio.cluster.topology.properties;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.container.cep_containerVersion;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyDiagramManager;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditor;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditorInput;
import com.tibco.cep.studio.cluster.topology.model.Cluster;
import com.tibco.cep.studio.cluster.topology.model.impl.ClusterImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.ClusterTopology;
import com.tibco.cep.studio.cluster.topology.model.impl.ClusterTopologyFactory;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentMappingImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentUnitImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.HostResourceImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.ProcessingUnitConfigImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.SiteImpl;
import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.ToolBarProvider;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSEObject;

public class AbstractClusterTopologyPropertySection extends AbstractPropertySection implements SelectionListener{

	protected ClusterDiagramPropertySheetPage propertySheetPage;
	protected TSEGraph tseGraph;
	protected TSENode tseNode;
	protected TSEdge tseEdge;
	protected TSEObject tseObject;
	protected List<?> nodeList;
	protected IProject project;
	protected ClusterTopologyEditor editor;
	
	protected ClusterTopology clusterTopology;

	protected ClusterImpl cluster;
	protected DeploymentUnitImpl deploymentUnit;
	protected DeploymentMappingImpl deploymentMapping;
	protected HostResourceImpl hostResource;

	protected ProcessingUnitConfigImpl processingUnit;
	protected SiteImpl site;
	protected ClusterTopologyFactory factory;
	
    protected final java.awt.Font awtFont = new java.awt.Font("Tahoma", 0, 11); 
    protected ToolItem addPUButton;
    protected ToolItem removePUButton;
   
    protected Cluster topologyCluster;
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.propertySheetPage = (ClusterDiagramPropertySheetPage) tabbedPropertySheetPage;
	}
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#setInput(org.eclipse.ui.IWorkbenchPart,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		
		if(getPart() instanceof ClusterTopologyEditor){
			editor = (ClusterTopologyEditor) getPart();
		}
		
		factory = getManager().getFactory();
		project = ((ClusterTopologyEditorInput)editor.getEditorInput()).getFile().getProject();
		
		//Now we have only one Cluster
		if (this.getManager().getSiteNode() != null) {
			topologyCluster = ((SiteImpl)getManager().getSiteNode().getUserObject()).getClusters().getCluster().get(0); 
		}
		else {
			return;
		}
		
		if (((IStructuredSelection) selection).getFirstElement() instanceof TSEGraph) {
			tseGraph = (TSEGraph) ((IStructuredSelection) selection).getFirstElement();
			nodeList = ((IStructuredSelection) selection).toList();
			tseNode = null;
			tseEdge = null;
			
			//For Host Graph
			if(tseGraph.getUserObject() instanceof DeploymentUnitImpl){
				deploymentUnit = (DeploymentUnitImpl)tseGraph.getUserObject();
				cluster= null;
				hostResource = null;
				processingUnit = null;
				site = null;
			}
		}
		if (((IStructuredSelection) selection).getFirstElement() instanceof TSEdge) {
			tseEdge = (TSEdge) ((IStructuredSelection) selection).getFirstElement();
			nodeList = ((IStructuredSelection) selection).toList();

			tseNode = null;
			tseGraph = null;
		}
		if (((IStructuredSelection) selection).getFirstElement() instanceof TSENode) {
			tseNode = (TSENode) ((IStructuredSelection) selection).getFirstElement();
			nodeList = ((IStructuredSelection) selection).toList();
			if( tseNode.getUserObject() instanceof ClusterTopology){
				clusterTopology = (ClusterTopology)tseNode.getUserObject();
				if(clusterTopology instanceof ClusterImpl){
					cluster = (ClusterImpl)clusterTopology;
					deploymentUnit = null;
					hostResource = null;
					processingUnit = null;
					site = null;
				}
				if(clusterTopology instanceof DeploymentUnitImpl){
					deploymentUnit = (DeploymentUnitImpl)clusterTopology;
					cluster= null;
					hostResource = null;
					processingUnit = null;
					site = null;
				}
				if(clusterTopology instanceof HostResourceImpl){
					hostResource = (HostResourceImpl)clusterTopology;
					cluster= null;
					deploymentUnit = null;
					processingUnit = null;
					site = null;
				}
				if(clusterTopology instanceof ProcessingUnitConfigImpl){
					processingUnit = (ProcessingUnitConfigImpl)clusterTopology;
					cluster= null;
					deploymentUnit = null;
					hostResource = null;
					site = null;
				}
				if(clusterTopology instanceof SiteImpl){
					site = (SiteImpl)clusterTopology;
					cluster= null;
					deploymentUnit = null;
					hostResource = null;
					processingUnit = null;
				}
			}
			tseEdge = null;
			tseGraph = null;
		}
	}
	
//	/**
//	 * @param variable
//	 * @param attribute
//	 */
//	protected void setStringField(Text variable, String attribute) {
//		Object value = tseNode.getAttributeValue(attribute);
//		if (value != null) {
//			variable.setText((String) value);
//		}
//		else {
//			variable.setText("");
//		}
//	}
//	
//	protected void setStringField(Button variable, String attribute) {
//		Object value = tseNode.getAttributeValue(attribute);
//		if (value != null) {
//			variable.setSelection((Boolean)value);
//		}
//		else {
//			variable.setText("");
//		}
//	}
//	
//	/**
//	 * @param variable
//	 * @param attribute
//	 */
//	protected void setStringField(CCombo variable, String attribute) {
//
//		if (variable == null) {
//			return;
//		}
//		
//		Object value = tseNode.getAttributeValue(attribute);
//		if (value != null) {
//			variable.setText((String) value);
//		}
//		else {
//			variable.setText("");
//		}
//	}
//
//	protected void setStringField(Text variable, int value) {
//		variable.setText(String.valueOf(value));
//	}
//	
//	protected void setComboField(CCombo combo, List list) {
//		if (list != null & list.size() > 0) {
//			String[] itemArray = new String[list.size()];
//			itemArray = new String[list.size()];
//			list.toArray(itemArray);
//			combo.setItems(itemArray);
//		}
//	}
	
	/**
	 * @param parent
	 * @param labels
	 * @return
	 */
	protected int getStandardLabelWidth(Composite parent, String[] labels) {
		int standardLabelWidth = STANDARD_LABEL_WIDTH;
		GC gc = new GC(parent);
		int indent = gc.textExtent("XXX").x; //$NON-NLS-1$
		for (int i = 0; i < labels.length; i++) {
			int width = gc.textExtent(labels[i]).x;
			if (width + indent > standardLabelWidth) {
				standardLabelWidth = width + indent;
			}
		}
		gc.dispose();
		return standardLabelWidth;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
//		asyncEditorModified();
	}
	
	protected ClusterTopologyDiagramManager getManager() {
		return (ClusterTopologyDiagramManager) this.editor.getDiagramManager();
	}
	
	protected ToolBar createPropertyToolbar(Composite parent) {
        ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.FLAT);
        toolBar.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
        toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	        
        addPUButton = new ToolItem(toolBar, SWT.PUSH);
        Image addImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_ADD);
        addPUButton.setImage(addImg);
        addPUButton.setToolTipText("Add");
        addPUButton.setText("Add");
        
        addPUButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				add();
			}
		});
        
        removePUButton = new ToolItem(toolBar, SWT.PUSH);
        Image delImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_DELETE);
        removePUButton.setImage(delImg);
        removePUButton.setToolTipText("Delete");
        removePUButton.setText("Delete");
        removePUButton.setEnabled(false);
        removePUButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				remove();
			}
		});
        toolBar.pack();
        return toolBar;
	}
	
	protected void add(){
		
	}
	protected void remove(){
		
	}

	protected Cluster getCluster() {
		return topologyCluster;
	}
	
    public TSENodeLabel getSelectedObjectLabel() {
		TSENodeLabel selectedNodeLabel = null;    	
		if (this.editor != null && this.getManager() != null) {
			List<?> selectedNodes = this.getManager().getSelectedNodes();
			TSENode selectedNode = null;
			if (selectedNodes != null && selectedNodes.size() > 0) {
				selectedNode = (TSENode) selectedNodes.get(selectedNodes.size() - 1);
				if (selectedNode.labels() != null && selectedNode.labels().size() > 0) {
					selectedNodeLabel = (TSENodeLabel) selectedNode.labels().get(0);
				}
				else {
					selectedNodeLabel = (TSENodeLabel) selectedNode.addLabel();
				}
			}
		}    	
		return selectedNodeLabel;
    }
    
    public TSENode getSelectedObjectName() {
		TSENode selectedNode = null;    	
		if (this.editor != null && this.getManager() != null) {
			List<?> selectedNodes = this.getManager().getSelectedNodes();
			if (selectedNodes != null && selectedNodes.size() > 0) {
				selectedNode = (TSENode) selectedNodes.get(selectedNodes.size() - 1);
				return selectedNode;
			}
		}    	
		return selectedNode;
    }    
	
	/**
	 * @return the editor
	 */
	public ClusterTopologyEditor getEditor() {
		return editor;
	}

	/**
	 * @param checkStr
	 * @return
	 */
	public boolean isNumeric(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase("")) {
				int val = Integer.parseInt(checkStr);
				if (val <  0) {
					return false;
				}
			}
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

	/**
	 * @param checkStr
	 * @return
	 */
	public boolean isDouble(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase(""))
				Double.parseDouble(checkStr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

	/**
	 * @param checkStr
	 * @return
	 */
	public boolean isLong(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase(""))
				Long.parseLong(checkStr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	
	/**
	 * @param text
	 */
	protected void checkValidBEVersionField(Text text) {
		if (text.getText().trim().equals(cep_containerVersion.version)) {
			text.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			text.setToolTipText("");
			text.setEnabled(false);
		} else {
			text.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			String problemMessage = Messages.getString("invalid_BE_Version", text.getText());
			text.setToolTipText(problemMessage);
			text.setEnabled(true);
		}
	}
	
//	protected int getNumberOfMachines() {
//		int nrMachines = 0;
//		TSEGraph mainGraph = null;
//		if (tseGraph == null) {
//			mainGraph = (TSEGraph) tseNode.getOwnerGraph();
//		}
//		else {
//			mainGraph = tseGraph;
//		}
//		List<TSENode>nodes = mainGraph.nodes();
//		for (TSENode node : nodes) {
//			Object typeObject = node.getAttributeValue(ClusterTopologyConstants.NODE_TYPE);
//			if (typeObject != null) {
//				int type = ((Integer) typeObject).intValue();
//				if (type == ClusterTopologyConstants.MACHINE_NODE) {
//					nrMachines++;
//				}
//			}
//		}
//		return nrMachines;
//	}	
//	
//	protected int getNumberOfUnits() {
//		int nrUnits = 0;
//		if (tseGraph != null) {
//			List<TSENode>nodes = tseGraph.nodes();
//			for (TSENode node : nodes) {
//				if (node.hasChildGraph()) {
//					nrUnits += node.getChildGraph().numberOfNodes();
//				}
//			}
//		}		
//		return nrUnits;
//	}	
}