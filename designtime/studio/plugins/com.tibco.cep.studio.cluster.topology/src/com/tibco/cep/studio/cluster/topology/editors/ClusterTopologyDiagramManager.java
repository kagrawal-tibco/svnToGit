package com.tibco.cep.studio.cluster.topology.editors;

import static com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils.CLUSTER_TOPOLOGY_JAXB_PACKAGE;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.editGraph;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.openError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.diagramming.drawing.DiagramChangeListener;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tibco.cep.diagramming.tool.CreateEdgeTool;
import com.tibco.cep.diagramming.tool.CreateNodeTool;
import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.ui.NoTagImageNodeUI;
import com.tibco.cep.studio.cluster.topology.ClusterTopologyPlugin;
import com.tibco.cep.studio.cluster.topology.handler.ClusterTopologyDiagramChangeListener;
import com.tibco.cep.studio.cluster.topology.handler.ClusterTopologyModelChangeListener;
import com.tibco.cep.studio.cluster.topology.model.Cluster;
import com.tibco.cep.studio.cluster.topology.model.Clusters;
import com.tibco.cep.studio.cluster.topology.model.DeployedFiles;
import com.tibco.cep.studio.cluster.topology.model.DeploymentMapping;
import com.tibco.cep.studio.cluster.topology.model.DeploymentMappings;
import com.tibco.cep.studio.cluster.topology.model.DeploymentUnit;
import com.tibco.cep.studio.cluster.topology.model.DeploymentUnits;
import com.tibco.cep.studio.cluster.topology.model.HostResource;
import com.tibco.cep.studio.cluster.topology.model.HostResources;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitConfig;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitsConfig;
import com.tibco.cep.studio.cluster.topology.model.Site;
import com.tibco.cep.studio.cluster.topology.model.impl.ClusterImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.ClusterTopologyFactory;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentUnitImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.HostResourceImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.ProcessingUnitConfigImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.SiteImpl;
import com.tibco.cep.studio.cluster.topology.tools.ClusterTopologyCreateEdgeTool;
import com.tibco.cep.studio.cluster.topology.tools.ClusterTopologyCreateNodeTool;
import com.tibco.cep.studio.cluster.topology.tools.ClusterTopologyPasteTool;
import com.tibco.cep.studio.cluster.topology.tools.ClusterTopologySelectTool;
import com.tibco.cep.studio.cluster.topology.ui.BusNodeUI;
import com.tibco.cep.studio.cluster.topology.ui.MachineNodeUI;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyConstants;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils;
import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.shared.TSUIConstants;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEImageNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;
import com.tomsawyer.interactive.command.TSCommandInterface;
import com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool;

/**
 * 
 * @author ggrigore
 * 
 */
public class ClusterTopologyDiagramManager extends DiagramManager{

	private Composite swtAwtComponent;
	private ClusterTopologyLayoutManager layoutManager;
	private ClusterTopologyEditor editor;
	private String filePath;
	
	protected ClusterTopologyCreateNodeTool clusterTopologyCreateNodeTool;
	protected ClusterTopologyCreateEdgeTool clusterTopologyCreateEdgeTool;
	protected ClusterTopologySelectTool clusterTopologySelectTool;
	protected ClusterTopologyPasteTool clusterTopologyPasteTool;
	private ClusterTopologySelectionChangeListener selectionChangeListener;
	private ClusterTopologyDiagramChangeListener diagramChangeListener;	
	
	private ClusterTopologyModelChangeListener modelChangeListener;
	private ClusterTopologyFactory factory;
	private Site site;
	
	private TSENode siteNode;
	
	

	static {
		TSEImage.setLoaderClass(ClusterTopologyDiagramManager.class);
	}
	
	public static final String UNIQUE_HOST_ID = "HR_";
	public static final String UNIQUE_PROCESSING_UNIT = "PUID_";
	public static final String UNIQUE_QUERY_AGENT = "QueryAgent_";
	public static final String UNIQUE_INFERENCE_AGENT = "InferenceAgent_";
	public static final String UNIQUE_DASHBORAD_AGENT = "DashboardAgent_";
	public static final String UNIQUE_DEPLOYMENT_UNIT = "DU_";
	
	private List<DeploymentUnit> deploymentUnitsListDiagram = new ArrayList<DeploymentUnit>();
	private List<ProcessingUnitConfig> processingUnitsListDiagram = new ArrayList<ProcessingUnitConfig>();
	private List <HostResource> hostResourcesListDiagram = new ArrayList<HostResource>();
	
	private static TSEImage DU_IMAGE = new TSEImage("/icons/host_48x48.png");
	// private static TSEImage HOST_IMAGE = new TSEImage("/icons/black-server-icon.png");
	public static TSEImage HOST_IMAGE = new TSEImage("/icons/host_48x48.png");
	
	private static TSEImage PU_IMAGE = new TSEImage("/icons/processingunit.png");
	// private static TSEImage SITE_IMAGE = new TSEImage("/icons/activesite_48x48.png");
	// private static TSEImage SITE_IMAGE = new TSEImage("/icons/Globe48x48.png");
	private static TSEImage SITE_IMAGE = new TSEImage("/icons/site.png");
	
	private static TSEColor BUS_COLOR = new TSEColor(147, 199, 255);
	
	private Map<String, TSENode> hostResourceNodesMap = new HashMap<String, TSENode>();
	private Map<String, TSENode> deploymentUnitNodesMap = new HashMap<String, TSENode>();
	private boolean duLinkCopied = false;

	
	//public static int EdgeLabel=0;
	public EditorPart getEditor() {
		return editor;
	}
	
	public ClusterTopologyDiagramManager(EditorPart editor) {
		this.editor = (ClusterTopologyEditor) editor;
		this.filePath = ((FileEditorInput) editor.getEditorInput()).getPath().toPortableString();
	}
	
	protected boolean isValidModelFile(){
		return true;
	}	

	
	/**
	 * Right now this is just a sample. We should however start with
	 * a cluster and a host already added, for convenience.
	 */
	protected void createInitialModel() { }
	
	public void openModel() throws Exception {
		if (this.filePath.trim().length() == 0) {
			this.createInitialModel();
		}
		else {
			if(modelChangeListener == null){
				modelChangeListener = new ClusterTopologyModelChangeListener((ClusterTopologyEditor)this.getEditor());
			}
			if(factory == null){
				factory = new ClusterTopologyFactory(modelChangeListener);
			}
			parseFile(this.filePath);
 		}
	}
	private void parseFile(String filePath) throws Exception {
		
//		Site site = unmarshalFile(filePath);
		File topoConfigFile = new File(filePath);
		if (topoConfigFile.isDirectory()) {
			throw new Exception("The site topology file cannot be a directory");
		}
		if (!topoConfigFile.exists()) {
			throw new Exception("The site topology does not exist");
		}
		if (!topoConfigFile.canRead()) {
			throw new Exception("The site topology file does not have read access");
		}
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(CLUSTER_TOPOLOGY_JAXB_PACKAGE,ClusterTopologyPlugin.getDefault().getClass().getClassLoader());
		    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		    site = (Site)unmarshaller.unmarshal(topoConfigFile);			
		} catch (Throwable je) {
	    	je.printStackTrace();
			throw new Exception(je);
	    }
	    siteNode = this.createNode(ClusterTopologyConstants.SITE_NODE, factory.createSiteImpl(site));
	 
	    try {
	    	parseHostResources(site);
	    	parseClusters(site, siteNode);
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
		// now layout diagram
		getLayoutManager().callBatchGlobalLayout();
	
	}

	public void save() throws JAXBException, 
	                          FileNotFoundException, 
	                          IOException, 
	                          org.eclipse.core.runtime.CoreException {
		JAXBContext jaxbContext = JAXBContext.newInstance(CLUSTER_TOPOLOGY_JAXB_PACKAGE,ClusterTopologyPlugin.getDefault().getClass().getClassLoader());
		Marshaller marshaller=jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		FileOutputStream file = new FileOutputStream(filePath);
		marshaller.marshal(site, file);		
		file.flush();
		file.close();
		((FileEditorInput) editor.getEditorInput()).getFile().refreshLocal(0, null);
		
	}

	/**
	 * @param node
	 * @param value
	 */
	private void setNodeProperty(TSENode node, Object value) {
		if(value instanceof SiteImpl){
			SiteImpl impl = (SiteImpl)value;
			addNodeLabel(node, impl.getName());
		}
		if(value instanceof ClusterImpl){
			ClusterImpl impl = (ClusterImpl)value;
			addNodeLabel(node, impl.getName());
		}
		if(value instanceof HostResourceImpl){
			HostResourceImpl impl = (HostResourceImpl)value;
			String hostName = impl.getHostname();
			if(hostName == null){
				hostName = "HostX";
			}
			addNodeLabel(node, hostName);
		}
		if(value instanceof DeploymentUnitImpl){
			DeploymentUnitImpl impl = (DeploymentUnitImpl)value;
			String duName = impl.getName();
			if(duName == null){
				duName = "Deployment UnitX";
			}
			//node.setName(duName);
		}
		if(value instanceof ProcessingUnitConfigImpl){
			ProcessingUnitConfigImpl impl = (ProcessingUnitConfigImpl)value;
			String id = impl.getId();
			if(id == null){
				id = "Processing Unit ConfigX";
			}
//			node.setName(impl.getId());
			//addNodeLabel(node, id);
		}
	}
	
	public boolean addDeploymentUnit(DeploymentUnit du) {
		if(deploymentUnitsListDiagram.contains(du)) {
			return false;
		} else {
			deploymentUnitsListDiagram.add(du);
			return true;
		}
		
	}
	
	public boolean checkDuplicateDeploymentUnit( String duName) {
		for (DeploymentUnit du : deploymentUnitsListDiagram) {
			if(du.getName().equals(duName)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean removeDeploymentUnit(DeploymentUnit du) {
		if(deploymentUnitsListDiagram.contains(du)) {
			deploymentUnitsListDiagram.remove(du);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addProcessingUnit(ProcessingUnitConfig pu) {
		if(processingUnitsListDiagram.contains(pu)) {
			return false;
		} else {
			processingUnitsListDiagram.add(pu);
			return true;
		}
		
	}
	
	public boolean checkDuplicateProcessingUnit(DeploymentUnit du, String puName ) {
		ProcessingUnitsConfig puConfig = du.getProcessingUnitsConfig();
		List<?> puList = puConfig.getProcessingUnitConfig();
		for (Object objPUConfig : puList ) {
			ProcessingUnitConfig pu = (ProcessingUnitConfig) objPUConfig; 
			if(pu.getId().equals(puName)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean removeProcessingUnit(ProcessingUnitConfig pu) {
		if(processingUnitsListDiagram.contains(pu)) {
			processingUnitsListDiagram.remove(pu);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addHostResource(HostResource hr) {
		if(hostResourcesListDiagram.contains(hr)) {
			return false;
		} else {
			hostResourcesListDiagram.add(hr);
			return true;
		}
		
	}
	
	public boolean checkDuplicateHostResource(String hrName) {
		for ( HostResource hr : hostResourcesListDiagram ) {
			if(hr.getHostname().equals(hrName)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean removeHostResource(HostResource hr) {
		if(hostResourcesListDiagram.contains(hr)) {
			hostResourcesListDiagram.remove(hr);
			return true;
		} else {
			return false;
		}
	}
	
	public int getNodeIdNumber(int nodeType, DeploymentUnit du) {
		List<Integer> noList = new ArrayList<Integer>();
		String numStr;
		Iterator<?> PUIDs = du.getProcessingUnitsConfig().getProcessingUnitConfig().iterator();
		String puId;
		while (PUIDs.hasNext()) {
			ProcessingUnitConfig pUIDConfig = (ProcessingUnitConfig) PUIDs.next();
			puId = pUIDConfig.getId();
			if (puId.startsWith(UNIQUE_PROCESSING_UNIT)) {
				numStr = puId.substring(puId.indexOf("_") + 1);
				noList.add(getValidNo(numStr));
			}
		}
		if (noList.size() > 0) {
			Object[] noArray = noList.toArray();
			java.util.Arrays.sort(noArray);
			int num = ((Integer)noArray[noArray.length - 1]).intValue();
			num++;
			return num;
		}
		return 0;
	}
	public int getNodeIdNumber(int nodeType) {
		List<Integer> noList = new ArrayList<Integer>();
		String numStr;
		switch (nodeType) {
		case ClusterTopologyConstants.HOST_NODE :
			Iterator<HostResource> HRIDs = hostResourcesListDiagram.iterator();
			String hrId;
			while (HRIDs.hasNext()) {
				hrId = HRIDs.next().getId();
				if (hrId.startsWith(UNIQUE_HOST_ID)) {
					numStr = hrId.substring(hrId.indexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
			break;
		case ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE :
			Iterator<DeploymentUnit> DUIDs = deploymentUnitsListDiagram.iterator();
			String duId;
			while (DUIDs.hasNext()) {
				DeploymentUnit du = DUIDs.next();
				if(du != null) {
					duId = du.getId();
					if (duId.startsWith(UNIQUE_DEPLOYMENT_UNIT)) {
						numStr = duId.substring(duId.indexOf("_") + 1);
						noList.add(getValidNo(numStr));
					}
				}
			}
			break;
		case ClusterTopologyConstants.PU_NODE :
			Iterator<ProcessingUnitConfig> PUIDs = processingUnitsListDiagram.iterator();
			String puId;
			while (PUIDs.hasNext()) {
				puId = PUIDs.next().getPuid();
				if (puId.startsWith(UNIQUE_PROCESSING_UNIT)) {
					numStr = puId.substring(puId.indexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
			break;
		default :
			break;
		}
		
		if (noList.size() > 0) {
			Object[] noArray = noList.toArray();
			java.util.Arrays.sort(noArray);
			int num = ((Integer)noArray[noArray.length - 1]).intValue();
			num++;
			return num;
		}
		return 0;
	}
	
	public String getHostResourceName() {
		
		Iterator<HostResource> HRIDs = hostResourcesListDiagram.iterator();
		List<Integer> noList = new ArrayList<Integer>();
		String hrId;
		String numStr;
		while (HRIDs.hasNext()) {
			hrId = HRIDs.next().getHostname();
			if (hrId.startsWith(UNIQUE_HOST_ID)) {
				numStr = hrId.substring(hrId.indexOf("_") + 1);
				noList.add(getValidNo(numStr));
			}
		}
		if (noList.size() > 0) {
			Object[] noArray = noList.toArray();
			java.util.Arrays.sort(noArray);
			int num = ((Integer)noArray[noArray.length - 1]).intValue();
			num++;
			return "" + num;
		}
		return "0";
	}
		
		public static int getValidNo(String no) {
			int n;
			try {
				n = Integer.parseInt(no);
			} catch (Exception e) {
				return 0;
			}
			return n;
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
		}
		getLayoutManager().setNodeLabelOptions(label);
	}
	
	private TSENode createNode(int type, Object object) {
		TSENode node = this.createNode(
			(TSEGraph) this.graphManager.getMainDisplayGraph(), type, object);
		
		return node;
	}

	/**
	 * This method creates the right type of a node. If a host node, it creates a child
	 * graph and expands it, otherwise we just create simple nodes.
	 * Also, if the host node has no PUs inside, then we collapse it by default.
	 */
	private TSENode createNode(TSEGraph graph, int type, /* HostResourceImpl */ Object object) {
		TSENode node = (TSENode) graph.addNode();
		node.setAttribute(ClusterTopologyConstants.NODE_TYPE, type);
		node.setUserObject(object);
		TSENodeUI nodeUI;
		TSEGraph duNodeGraph;
		setNodeProperty(node, object);
		if (type == ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE) {
			nodeUI = new TSEImageNodeUI();
			graphManager = (TSEGraphManager) graph.getOwnerGraphManager();
			duNodeGraph = (TSEGraph) graphManager.addGraph();
			node.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
			((TSEImageNodeUI) nodeUI).setImage(DU_IMAGE);
			duNodeGraph.setUserObject(object);
			//this.addNodeLabel(node, ((DeploymentUnitImpl) object).getName());
			node.setChildGraph(duNodeGraph);
			((ClusterTopologyLayoutManager) this.getLayoutManager()).configureChildGraphLayoutOptions(duNodeGraph);
			//TSENestingManager.collapse(node);
			node.setUI(nodeUI);
		}
		if (type == ClusterTopologyConstants.HOST_NODE) {
			nodeUI = new NoTagImageNodeUI(HOST_IMAGE);
			//((TSEImageNodeUI) nodeUI).setImage(HOST_IMAGE);
			this.addNodeLabel(node, ((HostResourceImpl) object).getHostname());
			node.setUI(nodeUI);
			node.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
		}
		else if (type == ClusterTopologyConstants.PU_NODE) {
			nodeUI = new TSEImageNodeUI();
			((TSEImageNodeUI) nodeUI).setImage(PU_IMAGE);
			//this.addNodeLabel(node, ((ProcessingUnitConfigImpl) object).getId());
			node.setUI(nodeUI);
			node.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
		}
		else if (type == ClusterTopologyConstants.CLUSTER_NODE) {
			
			nodeUI = new BusNodeUI();
			((BusNodeUI)nodeUI).setFillColor(BUS_COLOR);
			node.setUI(nodeUI);
			node.setWidth(400.0);
		}
		else if (type == ClusterTopologyConstants.SITE_NODE) {
			nodeUI = new TSEImageNodeUI();
			((TSEImageNodeUI) nodeUI).setImage(SITE_IMAGE);
			node.setUI(nodeUI);
			node.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
			
		}		
		else {
			nodeUI = new TSEShapeNodeUI();
		}
		
		return node;		
	}
	
	public TSENode addPU(TSENode muNode, Object object) {
		TSEGraph hostChildGraph = (TSEGraph) muNode.getChildGraph();
		if (hostChildGraph == null) {
			hostChildGraph = (TSEGraph) this.graphManager.addGraph();
		}

		// expand the node if it's collapsed, because now we have something to show
//		if (TSENestingManager.isCollapsed(muNode)) {
//			TSENestingManager.expand(muNode);
//			MachineNodeUI nodeUI = new MachineNodeUI();
//			muNode.setUI(nodeUI);
//		}
		
		TSENode node = this.createNode(hostChildGraph, ClusterTopologyConstants.PU_NODE, object);
		node.setUserObject(object);
		node.setAttribute(ClusterTopologyConstants.NODE_TYPE, ClusterTopologyConstants.PU_NODE);
		return node;
	}
	
	public TSENode addPU(TSEGraph hostChildGraph, Object object) {
		
		// expand the node if it's collapsed, because now we have something to show
//		if (TSENestingManager.isCollapsed(muNode)) {
//			TSENestingManager.expand(muNode);
//			MachineNodeUI nodeUI = new MachineNodeUI();
//			muNode.setUI(nodeUI);
//		}
		
		TSENode node = this.createNode(hostChildGraph, ClusterTopologyConstants.PU_NODE, object);
		node.setUserObject(object);
		return node;
	}
	
//	@SuppressWarnings("unchecked")
//	private List<TSENode> getPUNodes(TSENode muNode) {
//		if (muNode.getChildGraph() != null) {
//			return muNode.getChildGraph().nodes();
//		}
//		else {
//			return null;
//		}
//	}
	@Override
	public void addToEditGraphMap(Map<String, Object> map, List<TSENode> selectedNodes, List<TSEEdge> selectedEdges, boolean isCopy) {
		ClusterTopologyUtils.addToEditGraphMap(map, selectedNodes);
	}


	public TSEEdge createLink(TSENode srcNode, TSENode tgtNode) {
		return this.createLink(srcNode, tgtNode,null );/*"edgeLabel_"+EdgeLabel*/
	}
	
	private TSEEdge createLink(TSENode srcNode, TSENode tgtNode, String label) {
		//EdgeLabel=EdgeLabel+1;
		TSEEdge edge = (TSEEdge) this.graphManager.addEdge(srcNode, tgtNode);
		if (label != null) {
			((TSEEdgeLabel) edge.addLabel()).setName(label);
		}
		
		// AnimatedEdgeUI edgeUI = new AnimatedEdgeUI();
		TSECurvedEdgeUI edgeUI = new TSECurvedEdgeUI();
		edgeUI.setAntiAliasingEnabled(true);
		edgeUI.setCurvature(100);
		edge.setUI(edgeUI);
		return edge;
	}	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getSelectTool()
	 */
	public SelectTool getSelectTool() {
		if (this.clusterTopologySelectTool == null) {
			this.clusterTopologySelectTool = new ClusterTopologySelectTool(this);
		}
		return this.clusterTopologySelectTool;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getNodeTool()
	 */
	public CreateNodeTool getNodeTool(){
		if (this.clusterTopologyCreateNodeTool == null) {
			this.clusterTopologyCreateNodeTool = new ClusterTopologyCreateNodeTool(this);
		}
		return this.clusterTopologyCreateNodeTool;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getEdgeTool()
	 */
	public CreateEdgeTool getEdgeTool(){
		if (this.clusterTopologyCreateEdgeTool == null) {
			this.clusterTopologyCreateEdgeTool = new ClusterTopologyCreateEdgeTool(this);
		}
		return this.clusterTopologyCreateEdgeTool;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getPasteTool()
	 */
	public TSEPasteTool getPasteTool() {
		if (clusterTopologyPasteTool == null) {
			clusterTopologyPasteTool = new ClusterTopologyPasteTool(this);;
		}
		return clusterTopologyPasteTool;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getDiagramChangeListener()
	 */
	@Override
	public DiagramChangeListener<? extends DiagramManager> getDiagramChangeListener() {
		if (this.diagramChangeListener == null) {
			this.diagramChangeListener = new ClusterTopologyDiagramChangeListener(this);
		}
		return this.diagramChangeListener;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getDiagramSelectionListener()
	 */
	@Override
	public SelectionChangeListener getDiagramSelectionListener() {
		if (this.selectionChangeListener == null) {
			this.selectionChangeListener = new ClusterTopologySelectionChangeListener(this);
		}
		return this.selectionChangeListener;
	}
	
	public void setFocus() {
		this.swtAwtComponent.setFocus();
	}

	public LayoutManager getLayoutManager() {
		if (this.layoutManager == null) {
			this.layoutManager = new ClusterTopologyLayoutManager(this);
		}
		return this.layoutManager;
	}
	public TSCommandInterface undo() {
		
		TSCommandInterface command = super.undo();
		
		// TODO: do something here...
		return command;
	}
	
	public TSCommandInterface redo() {
		TSCommandInterface command = super.redo();
		// TODO: do something here...
		return command;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#cutGraph()
	 */
	public void cutGraph() {
		super.cutGraph();
		List<?> selectedNodeList = this.graphManager.selectedNodes();
		for (Object objNode: selectedNodeList) {
			TSENode node = (TSENode) objNode;
			if (ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.CLUSTER_NODE ||
				ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.SITE_NODE) {
				openError(getEditor().getSite().getShell(), "Error Message"
						, "Cannot Cut Site and Cluster Node.Reset the selection.");
				return;
			}
			
		}
		List<?> selectedEdgeList = this.graphManager.selectedEdges(false);
		for (Object objEdge: selectedEdgeList) {
			TSEEdge edge = (TSEEdge) objEdge;
			if (ClusterTopologyUtils.getNodeType((TSENode)edge.getSourceNode()) == ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE &&
				ClusterTopologyUtils.getNodeType((TSENode)edge.getTargetNode()) == ClusterTopologyConstants.CLUSTER_NODE) {
				duLinkCopied= true;
				
			}
		}
		editGraph(EDIT_TYPES.CUT, getEditor().getEditorSite(), this);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#copyGraph()
	 */
	public void copyGraph() {
		super.copyGraph();
		List<?> selectedNodeList = this.graphManager.selectedNodes();		
		for (Object objNode: selectedNodeList) {
			TSENode node = (TSENode) objNode;
			if (ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.CLUSTER_NODE ||
				ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.SITE_NODE) {
				openError(getEditor().getSite().getShell(), "Error Message"
						, "Cannot Copy Site and Cluster Node.Reset the selection.");
				return;
			}
		}
		
		List<?> selectedEdgeList = this.graphManager.selectedEdges(false);
		for (Object objEdge: selectedEdgeList) {
			TSEEdge edge = (TSEEdge) objEdge;
			if (ClusterTopologyUtils.getNodeType((TSENode)edge.getSourceNode()) == ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE &&
				ClusterTopologyUtils.getNodeType((TSENode)edge.getTargetNode()) == ClusterTopologyConstants.CLUSTER_NODE) {
				setDuLinkCopied(true);
			}
		}
		if(selectedEdgeList.size() == 0){
			setDuLinkCopied(false);
		}
		editGraph(EDIT_TYPES.COPY, getEditor().getEditorSite(), this);
	}
	
	public boolean isDuLinkCopied() {
		return duLinkCopied;
	}

	public void setDuLinkCopied(boolean duLinkCopied) {
		this.duLinkCopied = duLinkCopied;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#pasteGraph()
	 */
	public void pasteGraph() {
		super.pasteGraph();
		editGraph(EDIT_TYPES.PASTE, getEditor().getEditorSite(), this);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#delete()
	 */
	public void delete() {
		editGraph(EDIT_TYPES.DELETE, getEditor().getEditorSite(), this);
	}
	@Override
	public boolean validateCopyEdit(List<TSENode> selectedNodes,
			List<TSEEdge> selectedEdges) {
		// TODO Auto-generated method stub
		return super.validateCopyEdit(selectedNodes, selectedEdges);
	}

	@Override
	public boolean validateCutEdit(List<TSENode> selectedNodes,
			List<TSEEdge> selectedEdges) {
		// TODO Auto-generated method stub
		return super.validateCutEdit(selectedNodes, selectedEdges);
	}

	@Override
	public boolean validatePasteEdit(List<TSENode> selectedNodes,
			List<TSEEdge> selectedEdges) {
		// TODO Auto-generated method stub
		return super.validatePasteEdit(selectedNodes, selectedEdges);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#validateDeleteEdit(java.util.List, java.util.List)
	 */
	@Override
	public boolean validateDeleteEdit(List<TSENode> selectedNodes, List<TSEEdge> selectedEdges) {
		for(TSENode node: selectedNodes){
			boolean valid = validateCommonEditGraph(node);
			if(!valid){
				return valid;
			}
		}
		for(TSEEdge edge : selectedEdges){
			boolean valid = validateCommonEditGraph(selectedNodes, edge);
			if(!valid){
				return valid;
			}
		}
		return true;
	}
	
	//if target node is CLUSTER node, then do not allow to delete the edges. 
	private boolean validateCommonEditGraph(List<TSENode> selectedNodes, TSEEdge edge) {
		TSENode targetNode = (TSENode) edge.getTargetNode();
		TSENode sourceNode = (TSENode) edge.getSourceNode();
		boolean valid = validateCommonEditGraph(selectedNodes, sourceNode, targetNode);
		if(!valid){
			return valid;
		}
		return true;
	}

	/**
	 * @param node
	 * @return
	 */
	private boolean validateCommonEditGraph(TSENode node){
		if (ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.CLUSTER_NODE) {
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message_invalid_operation"));
			return false;
		} else if (ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.SITE_NODE) {
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message_invalid_operation"));
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCommonEditGraph(List<TSENode> selectedNodes, TSENode sourceNode, TSENode targetNode){
		if (ClusterTopologyUtils.getNodeType(targetNode) == ClusterTopologyConstants.CLUSTER_NODE) {
			if(selectedNodes.contains(sourceNode)) {
				return true;
			} else {
				openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message_invalid_operation"));
				return false;
			}
		} else if (ClusterTopologyUtils.getNodeType(targetNode) == ClusterTopologyConstants.SITE_NODE) {
			if(selectedNodes.contains(sourceNode)) {
				return true;
			} else {
				openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message_invalid_operation"));
				return false;
			}
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public TSENode getClusterNode() {
		List<TSENode> clusterNodeList = new ArrayList<TSENode>();
		List<TSENode> nodeList = this.graphManager.getMainDisplayGraph().nodes();
		for (TSENode node: nodeList) {
			if (ClusterTopologyUtils.getNodeType(node) == ClusterTopologyConstants.CLUSTER_NODE) {
				clusterNodeList.add(node);				
			}
		}
		// TODO: temporarily return the first cluster node as currently there is only one 
		return clusterNodeList.get(0);
	}
	
	/**
	 * Parsing all host info using models
	 * @param site
	 * @return
	 */
	private void parseHostResources(Site site) {
		HostResources hostResources = site.getHostResources();
		if (hostResources != null) {
			List<?> hostResourceList = hostResources.getHostResource();
			for (Object objHostResource : hostResourceList) {
				HostResource hostResource = (HostResource) objHostResource;
				TSENode hostNode = this.createNode(ClusterTopologyConstants.HOST_NODE, factory.createHostResourceImpl(hostResource));
				hostNode.setName(hostResource.getId());
				hostNode.setAttribute("Text_Orientation", new Integer(TSUIConstants.TOP_JUSTIFY));
	    		//hostNode.setUserObject(hostResource);
				this.hostResourceNodesMap.put(hostResource.getId(), hostNode);
	    		hostResourcesListDiagram.add(hostResource);
	    	}
		}
	}
	
	/**
	 * Parsing all cluster info using models
	 * @param site
	 * @param machineInfoNodeList
	 * @return
	 */
	private void parseClusters(Site site, TSENode siteNode) {
		Clusters clusters = site.getClusters();
		List<?> clusterList = clusters.getCluster();
		DeploymentUnits deploymentUnits;
		List<?> deploymentUnitList;
		DeploymentMappings deploymentMappings;
		List<?> deploymentMappingList;
		@SuppressWarnings("unused")
		DeployedFiles dfs;
		ProcessingUnitsConfig pus;
		List<?> puList;
		DeploymentUnit deploymentUnit;
		HostResource hostResource;
		ArrayList<TSENode> puNodeList;
		TSENode clusterNode;
		TSENode duNode;
		TSENode puNode;
		TSENode hrNode;
		TSEEdge edge;
		String duIdRef = null;
		String hrIdRef = null;
		for (Object objCluster : clusterList) {
			Cluster cluster = (Cluster) objCluster;
			clusterNode = this.createNode(ClusterTopologyConstants.CLUSTER_NODE, factory.createClusterImpl(cluster));
			// all clusters should be linked to site
			this.createLink(clusterNode, siteNode);
			// create machine unit nodes and set node properties
			deploymentUnits = cluster.getDeploymentUnits();
			if (deploymentUnits != null){
				deploymentUnitList = deploymentUnits.getDeploymentUnit();
				for (Object objDU : deploymentUnitList) {
					DeploymentUnit du = (DeploymentUnit) objDU;
					dfs = du.getDeployedFiles();
					DeploymentUnitImpl duImpl =  factory.createDeploymentUnitImpl(du);
					duNode = this.createNode(ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE,duImpl);
					duNode.setUserObject(duImpl);
					/*if((du.getId()).equalsIgnoreCase(du.getName())){
					duNode.setName(du.getId());
					}else{*/
						duNode.setName(du.getName());
					//}
					deploymentUnitsListDiagram.add(du);
					
					// add pu nodes to this deployment unit and set node properties
					pus = du.getProcessingUnitsConfig();
					if(pus!=null){
					puList = pus.getProcessingUnitConfig();
					//DUName duName = new DUName(du.getName(),editor.getTitleToolTip());
					//duImpl.setDuName(duName);
					//ClusterTopologyUtils.getDUNamesSet().add(duName);
					ClusterTopologyUtils.getDUIdsSet().add(du.getId());
					puNodeList = new ArrayList<TSENode>();
					for(Object objPU : puList) {
						ProcessingUnitConfig pu = (ProcessingUnitConfig) objPU;
						puNode = this.addPU(duNode, factory.createProcessingUnitConfigImpl(pu,duImpl));
						puNode.setName(pu.getId());
						puNodeList.add(puNode);
						ClusterTopologyUtils.getPUIDSet().add(pu.getId());
						//PUName puName = new PUName(duImpl.getDuName().getDUName(),editor.getTitleToolTip(),pu.getId());
						//ClusterTopologyUtils.getPUNamesSet().add(puName);
					}
					}
					TSENestingManager.expand(duNode);
					MachineNodeUI nodeUI = new MachineNodeUI();
					duNode.setUI(nodeUI);
					
					// all machine units should be linked to this cluster
					this.createLink(duNode, clusterNode);
					
					this.deploymentUnitNodesMap.put(du.getId(), duNode);
				}
			}
			
			// link host resources to deployment units
			deploymentMappings = cluster.getDeploymentMappings();
			if (deploymentMappings != null){
				HashMap<String, ArrayList<String>> idMap = new HashMap<String, ArrayList<String>>();
				ArrayList<String> hrIdList;
				// get id mappings
				deploymentMappingList = deploymentMappings.getDeploymentMapping();				
				for (Object objDm : deploymentMappingList) {
					DeploymentMapping dm = (DeploymentMapping) objDm;
					if(dm.getDeploymentUnitRef() != null){
						deploymentUnit = (DeploymentUnit) dm.getDeploymentUnitRef();
						duIdRef = deploymentUnit.getId();
					}
										
					if(dm.getHostRef() != null){
						hostResource = (HostResource) dm.getHostRef();
						hrIdRef = hostResource.getId();
					}
					
					if (duIdRef != null && duIdRef.trim().length() > 0
							&& hrIdRef != null && hrIdRef.trim().length() > 0) {
						
						duNode = deploymentUnitNodesMap.get(duIdRef);
						if (duNode != null) {
							hrIdList = idMap.get(duIdRef);
							if (hrIdList == null) {
								hrNode = hostResourceNodesMap.get(hrIdRef);
								if (hrNode != null) {
									// create link
									edge = this.createLink(hrNode, duNode);
									edge.setUserObject(this.factory.createDeploymentMappingImpl(dm));
									
									hrIdList = new ArrayList<String>();
									idMap.put(duIdRef, hrIdList);
								}
							} else if (!hrIdList.contains(hrIdRef)) {// not allow duplicated mappings
								hrNode = hostResourceNodesMap.get(hrIdRef);
								if (hrNode != null) {
									// create link
									edge = this.createLink(hrNode, duNode);
									edge.setUserObject(this.factory.createDeploymentMappingImpl(dm));
									
									hrIdList.add(hrIdRef);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void refreshLabel(final TSENodeLabel nodeLabel) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				drawingCanvas.addInvalidRegion(nodeLabel.getBounds());
				drawingCanvas.drawGraph();
				drawingCanvas.repaint();
			}
		});
	}	

	public void refreshNode(final TSENode node) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				drawingCanvas.addInvalidRegion(node.getBounds());
				drawingCanvas.drawGraph();
				drawingCanvas.repaint();
			}
		});
	}	
	
	public ClusterTopologyFactory getFactory() {
		return factory;
	}

	public TSENode getSiteNode() {
		return siteNode;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#resetPaletteSelection()
	 */
	@Override
	protected void resetPaletteSelection() {
		StudioUIUtils.resetPaletteSelection();
	}

	public String getDeploymentUnitName() {

		Iterator<DeploymentUnit> DUIDs = deploymentUnitsListDiagram.iterator();
		String duId;
		List<Integer> noList = new ArrayList<Integer>();
		while (DUIDs.hasNext()) {
			DeploymentUnit du = DUIDs.next();
			if(du != null) {
				duId = du.getName();
				if (duId.startsWith(UNIQUE_DEPLOYMENT_UNIT)) {
					String numStr = duId.substring(duId.indexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
		}
		if (noList.size() > 0) {
			Object[] noArray = noList.toArray();
			java.util.Arrays.sort(noArray);
			int num = ((Integer)noArray[noArray.length - 1]).intValue();
			num++;
			return "" + num;
		}
		return "0";
	}



}