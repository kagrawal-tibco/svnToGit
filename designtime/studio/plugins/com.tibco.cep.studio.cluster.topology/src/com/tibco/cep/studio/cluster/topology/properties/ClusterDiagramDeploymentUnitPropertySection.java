package com.tibco.cep.studio.cluster.topology.properties;
import static com.tibco.cep.diagramming.utils.DiagramUtils.refreshDiagram;
import static com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils.editorDirty;
import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingUtilities;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.diagramming.AbstractOverview;
import com.tibco.cep.studio.cluster.topology.ClusterTopologyPlugin;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyDiagramManager;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditor;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitConfig;
import com.tibco.cep.studio.cluster.topology.model.impl.ProcessingUnitConfigImpl;
import com.tibco.cep.studio.cluster.topology.ui.MachineNodeUI;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyConstants;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils;
import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tibco.cep.studio.ui.overview.CommonOverview;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterDiagramDeploymentUnitPropertySection extends AbstractClusterTopologyPropertySection {

	protected Text duNameText;
	protected Text cddText;
	protected Text earText;
	protected CCombo pusCombo;
	protected Composite composite;
	protected Table puUnitTable;

	public static final String UNIQUE_HOST_ID = "HR_";
	public static final String UNIQUE_PROCESSING_UNIT = "PUID_";
	public static final String UNIQUE_QUERY_AGENT = "QueryAgent_";
	public static final String UNIQUE_INFERENCE_AGENT = "InferenceAgent_";
	public static final String UNIQUE_DASHBORAD_AGENT = "DashboardAgent_";
	public static final String UNIQUE_DEPLOYMENT_UNIT = "DU_";
	
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		parent.setLayout(new GridLayout(1, false));
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));

		getWidgetFactory().createLabel(composite, Messages.getString("deploymentunit.name"),  SWT.NONE);
		duNameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		
		duNameText.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				String oldName = deploymentUnit.getName();
				String newName = duNameText.getText();
				ClusterTopologyDiagramManager clusterManager = (ClusterTopologyDiagramManager) getManager();
				if (!newName.equalsIgnoreCase(oldName != null ? oldName : "" )) {
					if( !clusterManager.checkDuplicateDeploymentUnit(newName) ) {
						clusterManager.removeDeploymentUnit(deploymentUnit.getDeploymentUnit());
						deploymentUnit.getDeploymentUnit().setName(newName);
						deploymentUnit.setName(newName);
						clusterManager.addDeploymentUnit(deploymentUnit.getDeploymentUnit());
						duNameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
						TSENode duNameNode = getSelectedObjectName();
						if (duNameNode != null) {
							duNameNode.setName(newName);
							getManager().refreshNode(duNameNode);
						}
						duNameText.setToolTipText("");
					} else {
						duNameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.duplicate.global.var.entry", newName, "Deployment Unit");
						duNameText.setToolTipText(problemMessage);
						deploymentUnit.getDeploymentUnit().setName(oldName);
					}
				}
			}});
			
		GridData gd = new GridData();
		gd.widthHint = 615;
		duNameText.setLayoutData(gd);


		getWidgetFactory().createLabel(composite, Messages.getString("deploymentunit.deployedCDD"),  SWT.NONE);
		cddText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		
		cddText.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				cddText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
				cddText.setToolTipText("");
				String cddMaster = cddText.getText();
				if (!cddMaster.isEmpty()) {
					if (cddMaster.endsWith(".cdd")/* && new File (cddMaster).exists()*/) {
						//TODO
					} else {
						cddText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = Messages.getString("invalid_cdd", cddMaster);
						cddText.setToolTipText(problemMessage);
						return;
					}
				}
				
				if(!cddMaster.equalsIgnoreCase(deploymentUnit.getDeploymentUnit().getDeployedFiles().getCddDeployed())){
					deploymentUnit.getDeployedFiles().setCddDeployed(cddMaster);
					deploymentUnit.notifyObservers();
				}
			}});
			
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		cddText.setLayoutData(gd);


		getWidgetFactory().createLabel(composite, Messages.getString("deploymentunit.deployedEAR"),  SWT.NONE);
		earText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		
		earText.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				earText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
				earText.setToolTipText("");
				String earMaster = earText.getText();
				if (!earMaster.isEmpty()) {
					if (earMaster.endsWith(".ear") /*&& new File (earMaster).exists()*/) {
						//TODO
					} else {
						earText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = Messages.getString("invalid_ear", earMaster);
						earText.setToolTipText(problemMessage);
						return;
					}
				}
				if(!earMaster.equalsIgnoreCase(deploymentUnit.getDeploymentUnit().getDeployedFiles().getEarDeployed())){
					deploymentUnit.getDeployedFiles().setEarDeployed(earMaster);
					deploymentUnit.notifyObservers();
				}

			}});
			
		gd = new GridData();
		gd.widthHint = 615;
		earText.setLayoutData(gd);

		getWidgetFactory().createLabel(composite, "",  SWT.NONE);

		Composite subcomposite = getWidgetFactory().createComposite(composite);

		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		subcomposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 150;
		subcomposite.setLayoutData(gd);

		createPropertyToolbar(subcomposite);

		puUnitTable = new Table(subcomposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE);
		puUnitTable.setLayout(new GridLayout());
		gd = new GridData();
		gd.heightHint = 50;
		gd.widthHint = 400;
		puUnitTable.setLayoutData(gd);

		TableColumn termColumn = new TableColumn(puUnitTable, SWT.NULL);
		termColumn.setText(Messages.getString("deploymentunit.pus"));

		puUnitTable.setLinesVisible(true);
		puUnitTable.setHeaderVisible(true);

		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(puUnitTable);
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		puUnitTable.setLayout(autoTableLayout);
		
		puUnitTable.addListener(SWT.MouseDoubleClick, new Listener(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			@Override
			public void handleEvent(Event event) {
				handlePUUnitTableDoubleClick();
			}});
	}

	private void handlePUUnitTableDoubleClick(){
		if(puUnitTable.getSelectionCount() == 1){
			TableItem item = puUnitTable.getItem(puUnitTable.getSelectionIndex());
			String puUnitId = item.getText();
			if (tseNode != null) {
				TSEGraph graph = (TSEGraph)tseNode.getChildGraph();
				selectNode(graph, tseNode, puUnitId);
			}
			if(tseGraph != null){
				selectNode(tseGraph, null, puUnitId);
			}
		}
	}

//	private TSENode findNode(TSEGraph graph, String puUnitId){
//		for(Object obj : graph.nodes()){
//			TSENode node = (TSENode)obj;
//			ProcessingUnitConfigImpl pUConfig = (ProcessingUnitConfigImpl)node.getUserObject();
//			if(pUConfig.getId().equals(puUnitId)){
//				return node;
//			}
//		}
//		return null;
//	}

	/**
	 * @param graph
	 * @param puUnitId
	 */
	private void selectNode(TSEGraph graph, TSENode deployNode, String puUnitId){
		for(Object obj : graph.nodes()){
			TSENode node = (TSENode)obj;
			ProcessingUnitConfigImpl pUConfig = (ProcessingUnitConfigImpl)node.getUserObject();
			if(pUConfig.getId().equals(puUnitId)){
				displayAndRefresh(deployNode, node);//Select Node
				editor.setFocus();//to show Property Page associated with not type
				break;
			}
		}
	}

	/**
	 * @param dpNode
	 * @param puNode
	 */
	private void displayAndRefresh(final TSENode dpNode,
			final TSENode puNode) {
		try{
			//If Deployment node is collapsed, then expand it to select PU Node 
			if (TSENestingManager.isCollapsed(dpNode)) {
				TSENestingManager.expand(dpNode);
				dpNode.discardAllLabels();
				MachineNodeUI subprocessUI = new MachineNodeUI();
				dpNode.setUI((TSEObjectUI)subprocessUI.clone());
			}
			
			IWorkbenchPage page = editor.getEditorSite().getPage();
			if (page != null) {
				IViewReference overViewReference = page.findViewReference(CommonOverview.ID);
				if (overViewReference != null && (overViewReference.getView(false)) instanceof CommonOverview) {
					final AbstractOverview overview = (AbstractOverview) overViewReference.getView(false);
					final IEditorPart activeEditorPart = page.getActiveEditor();
					if(overview != null ){
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								if(dpNode != null && dpNode.isSelected()){
									dpNode.setSelected(false);
								}
								if(!puNode.isSelected()){
									puNode.setSelected(true);
								}
								//TSConstPoint constPoint = null;
								// constPoint= ((TSENode)object).getCenter();
								//DrawingCanvas drawingCanvas = getManager().getDrawingCanvas();
								//drawingCanvas.scrollBy(30, 30, true);
								//drawingCanvas.centerPointInCanvas(constPoint, true);
								refreshDiagram(getManager());
								refreshOverview(overview, activeEditorPart, true, true);
							}
						});
					}
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cluster.topology.properties.AbstractClusterTopologyPropertySection#add()
	 */
	protected void add() {
		removePUButton.setEnabled(true);
		ProcessingUnitConfigImpl processingUnitConfigImpl = factory.createProcessingUnitConfigImpl(null,deploymentUnit);
		ProcessingUnitConfig pu = processingUnitConfigImpl.getProcessingUnitConfig();
		String ID = UNIQUE_PROCESSING_UNIT + ((ClusterTopologyDiagramManager)getManager()).getNodeIdNumber(ClusterTopologyConstants.PU_NODE, deploymentUnit.getDeploymentUnit());
		
		((ClusterTopologyDiagramManager)getManager()).addProcessingUnit(pu);
		pu.setId(ID);
		ClusterTopologyUtils.getPUIDSet().add(ID);
		//PUName puName=new PUName(deploymentUnit.getDuName().getDUName(),editor.getTitleToolTip(),pu.getId());
		//ClusterTopologyUtils.getPUNamesSet().add(puName);
		pu.setPuid("");
		deploymentUnit.getProcessingUnitsConfig().getProcessingUnitConfig().add(pu);
		//processingUnitConfigImpl.setPUName(puName);
		populateProcessingUnit(pu.getId());//TODO: may populate puId
		TSENode node =null;
		//For Machine Unit Child Graph
		if(tseGraph != null){
			node = getManager().addPU(tseGraph, processingUnitConfigImpl);
			node.setName(ID);
			
		}
		//For Machine Unit Node
		if(tseNode != null){
			 node = getManager().addPU(tseNode, processingUnitConfigImpl);
			node.setName(ID);
			
			
		}
		/*if(!((DiagrammingPlugin.getDefault().getPreferenceStore()).getString(RUN_LAYOUT_ON_CHANGES).equalsIgnoreCase(INCREMENTAL))
				|| (DiagrammingPlugin.getDefault().getPreferenceStore()).getString(RUN_LAYOUT_ON_CHANGES).equalsIgnoreCase(FULL)){
			DiagramUtils.incrementalLayoutAction(getEditor().getSite().getPage());
		}*/
		if(node!=null && node.getUserObject() instanceof ProcessingUnitConfigImpl){
			setWorkbenchSelection(node.getOwnerGraph(),((ClusterTopologyDiagramManager)getManager()).getEditor());
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cluster.topology.properties.AbstractClusterTopologyPropertySection#remove()
	 */
	protected void remove() {
		if(puUnitTable.getItemCount() > 0 && puUnitTable.getSelectionIndex() != -1){
			TableItem item = puUnitTable.getItem(puUnitTable.getSelectionIndex());
			String puId = item.getText();
			int index = 0;
			for(ProcessingUnitConfig processingUnitConfig : deploymentUnit.getProcessingUnitsConfig().getProcessingUnitConfig()){
				if (processingUnitConfig == null) {
					continue;
				}
				else if(processingUnitConfig.getId().equals(puId)) {
					break;
				}
				index ++;
			}
			//deploymentUnit.getProcessingUnitsConfig().getProcessingUnitConfig().remove(index);
			ProcessingUnitConfig pu = deploymentUnit.getProcessingUnitsConfig().getProcessingUnitConfig().remove(index);
			if (tseNode != null) {
				TSEGraph childGraph = (TSEGraph)tseNode.getChildGraph();
				updateDeploymentUnitChildGraph(childGraph, puId);
			}
			if (tseGraph != null) { 
				updateDeploymentUnitChildGraph(tseGraph, puId);
			}
			puUnitTable.remove(index);
			((ClusterTopologyDiagramManager)getManager()).removeProcessingUnit(pu);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					refreshDiagram(getManager());
				}
			});
			if(puUnitTable.getItemCount() == 0) {
				removePUButton.setEnabled(false);
			}
			
			refreshOverview(getEditor().getEditorSite(), true, true);
			
		}
		/*if(!((DiagrammingPlugin.getDefault().getPreferenceStore()).getString(RUN_LAYOUT_ON_CHANGES).equalsIgnoreCase(INCREMENTAL))
				|| (DiagrammingPlugin.getDefault().getPreferenceStore()).getString(RUN_LAYOUT_ON_CHANGES).equalsIgnoreCase(FULL)){
			DiagramUtils.incrementalLayoutAction(getEditor().getSite().getPage());
			}*/
		if (tseNode != null) {
			setWorkbenchSelection(tseNode,((ClusterTopologyDiagramManager)getManager()).getEditor());
		}else if (tseGraph != null) { 
			setWorkbenchSelection(tseGraph,((ClusterTopologyDiagramManager)getManager()).getEditor());
		}
		
	}
	

	/**
	 * @param graph
	 * @param puId
	 */
	private void updateDeploymentUnitChildGraph(TSEGraph graph, String puId){
		TSENode node = null;
		for(Object object : graph.nodes()){
			node = (TSENode)object;
			if(node.getUserObject() != null){
				ProcessingUnitConfigImpl processingUnitConfigImpl = (ProcessingUnitConfigImpl)node.getUserObject();
				if(processingUnitConfigImpl.getId().equals(puId)){
					break;
				}
			}
		}

		if (node != null) {
			graph.remove(node);
			editorDirty((ClusterTopologyEditor)getManager().getEditor());			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					refreshDiagram(getManager());
				}
			});	
		}
	}

	/**
	 * @param puName
	 */
	private void populateProcessingUnit(String puId){
			removePUButton.setEnabled(true);
		TableItem item = new TableItem(puUnitTable, SWT.CENTER);
		item.setImage(ClusterTopologyPlugin.getDefault().getImage("icons/processingunits_16x16.png"));
		item.setText(0, puId);
	}

	/**
	 * 
	 */
	private void populateProcessingUnits(){
		for(ProcessingUnitConfig processingUnitConfig: deploymentUnit.getProcessingUnitsConfig().getProcessingUnitConfig()){
			populateProcessingUnit(processingUnitConfig.getId());
		}
	}

	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		puUnitTable.removeAll();
		removePUButton.setEnabled(false);
		if (tseNode != null) {
			if(deploymentUnit != null){
				String duName = deploymentUnit.getName();
				duNameText.setText(duName != null ? duName : "" );
				TSENode duNameNode = getSelectedObjectName();
				if (duNameNode != null) {
					// duNameNode.setTag(duNameText.getText());
					getManager().refreshNode(duNameNode);
				}
				cddText.setText(deploymentUnit.getDeployedFiles().getCddDeployed());
				earText.setText(deploymentUnit.getDeployedFiles().getEarDeployed());
				populateProcessingUnits();
			}
		}

		if (tseEdge != null) { }

		if (tseGraph != null) { 
			if(deploymentUnit != null){
				String duName = deploymentUnit.getName();
				duNameText.setText(duName != null ? duName : "" );
				TSENode duNameNode = getSelectedObjectName();
				if (duNameNode != null) {
					// duNameNode.setTag(duNameText.getText());
					getManager().refreshNode(duNameNode);
				}
				cddText.setText(deploymentUnit.getDeployedFiles().getCddDeployed());
				earText.setText(deploymentUnit.getDeployedFiles().getEarDeployed());
				populateProcessingUnits();
			}
		}
	}

	/*
	private String getDeploymentUnidtId(){
		DeploymentUnit du = cluster.getDeploymentUnits().getDeploymentUnit()();
		if(miRef != null &&   miRef.getRef() != null){
			MachineInfo machineInfo = (MachineInfo) miRef.getRef();
			String machineIdRef = machineInfo.getId();
			return machineIdRef;
		}
		return null;
	}

	private void setDeploymentUnidtId(String value) {
		MachineInfoRef miRef = machine.getMachineInfoRef();
		if(miRef != null &&   miRef.getRef() != null){
			MachineInfo machineInfo = (MachineInfo) miRef.getRef();
			machineInfo.setId(value);
		}
	}
	*/
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		super.widgetSelected(e);
	}

	/**
	 * @param puConfigs
	 * @param tag
	 * @return
	 */
	public static String getUniqueTag(Collection<ProcessingUnitConfig> puConfigs, String tag){
		List<Integer> noList = new ArrayList<Integer>(); 
		for(ProcessingUnitConfig puConfig: puConfigs){
			if(puConfig.getId().startsWith(tag)){
				String no = puConfig.getId().substring(puConfig.getId().indexOf("_") + 1);
				noList.add(StudioUIUtils.getValidNo(no));
			}
		}
		if(noList.size() > 0){
			java.util.Arrays.sort(noList.toArray());
			int no = noList.get(noList.size()-1).intValue();no++;
			return tag + no;
		}
		return tag + 0;
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean isDuplicatePU(String id){
		for(ProcessingUnitConfig proConfigImpl : deploymentUnit.getProcessingUnitsConfig().getProcessingUnitConfig()){
			if(proConfigImpl.getId().equals(id)){
				return true;
			}
		}
		return false;
	}


	/**
	 * @return
	 */
	//	protected TSENode getClusterNode() {

	//		return (TSENode) tseNode.outEdge().getTargetNode();
	//	}
}