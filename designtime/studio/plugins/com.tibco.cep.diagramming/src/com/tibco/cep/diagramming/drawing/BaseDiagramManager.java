package com.tibco.cep.diagramming.drawing;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.eclipse.swt.widgets.Display;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.tool.CreateEdgeTool;
import com.tibco.cep.diagramming.tool.CreateNodeTool;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.diagramming.tool.ReconnectEdgeTool;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.studio.util.StudioConfig;
import com.tomsawyer.application.reference.TSApplicationReference;
import com.tomsawyer.drawing.events.TSDrawingChangeEvent;
import com.tomsawyer.drawing.events.TSLayoutEvent;
import com.tomsawyer.drawing.events.TSLayoutEventListener;
import com.tomsawyer.graph.events.TSGraphChangeEvent;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.events.TSEEventManager;
import com.tomsawyer.graphicaldrawing.events.TSESelectionChangeEvent;
import com.tomsawyer.graphicaldrawing.events.TSEViewportChangeEvent;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.interactive.command.TSCommandInterface;
import com.tomsawyer.interactive.command.TSCommandManager;
import com.tomsawyer.interactive.service.TSEAllOptionsServiceInputData;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateEdgeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEEditTextTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEMoveSelectedTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool;
import com.tomsawyer.interactive.swing.editing.tool.TSETransferSelectedTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;
import com.tomsawyer.interactive.swing.viewing.tool.TSESelectTool;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;
import com.tomsawyer.interactive.tool.TSToolManager;
import com.tomsawyer.licensing.TSLicenseManager;
import com.tomsawyer.service.layout.TSLayoutInputTailor;

public abstract class BaseDiagramManager implements IGraphDrawing, TSLayoutEventListener/*,IDiagramManager*/ {

	protected LayoutManager layoutManager;
	protected IDiagramManager diagramManager;
	protected TSEGraphManager graphManager;
	protected TSEOverviewComponent overviewComponent;
	protected DrawingCanvas drawingCanvas;
	protected DiagramViewPortChangeListener viewPortListener;
	protected MouseListener activationMouseListener;
	protected DiagramChangeListener<DiagramManager> changeListener;
	protected SelectionChangeListener selectionListener;
	protected List<TSENode> selectedNodes = new ArrayList<TSENode>();
	protected List<TSENodeLabel> selectedNodeLabels = new ArrayList<TSENodeLabel>();
	protected List<TSEEdgeLabel> selectedEdgeLabels = new ArrayList<TSEEdgeLabel>();
	protected List<TSEEdge> selectedEdges = new ArrayList<TSEEdge>();
	protected List<TSEEdge> edgesClipBoard = new ArrayList<TSEEdge>();
 	public static boolean layoutGoinOn = false;
 	protected SelectTool selectTool;
 	protected TSEMoveSelectedTool moveSelectedTool;
 	protected TSETransferSelectedTool transferSelectedTool;
	protected CreateNodeTool createNodeTool;
	protected TSEPasteTool pasteTool;
	protected TSEEditTextTool editTextTool;
	protected CreateEdgeTool createEdgeTool;
	protected ReconnectEdgeTool reConnectEdgeTool;
	protected boolean cutGraph = false;
	protected boolean copyGraph = false;
	protected boolean pasteGraph = false;
	public  boolean REFRESH_ACTION = false;
	protected String selectedPaletteEntry = null;
	
	protected Map<String, Object> cutMap = new HashMap<String, Object>(); 
	protected Map<String, Object> copyMap = new HashMap<String, Object>();
	
	protected IDiagramModelAdapter diagramModelAdapter;
	
	public boolean layout_On_Diagram_Change=false;
	
	protected boolean web = false;
	protected volatile boolean isInitialized = false;

	
	public boolean isWeb() {
		return web;
	}

	public void setWeb(boolean web) {
		this.web = web;
	}

	public BaseDiagramManager() {
		if(org.eclipse.swt.widgets.Display.getCurrent() != null) {
			initializeGraphicsEnvironment();
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					initialize();
					
				}
			});
		} else
		initialize();
	}

	public boolean isInitialized() {
		return isInitialized;
	}
	
	public static void initializeGraphicsEnvironment() {
		GraphicsEnvironment.getLocalGraphicsEnvironment();
	}
	
	public IDiagramModelAdapter getDiagramModelAdapter() {
		return this.diagramModelAdapter;
	}
	
	protected void initialize() {
		Runnable init = new Runnable() {
			
			@Override
			public void run() {
				TSLicenseManager.setUserName(System.getProperty("user.name"));
				TSLicenseManager.initTSSLicensing();
				TSEImage.setLoaderClass(this.getClass());		
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						BaseDiagramManager.this.graphManager = createGraphManager();
						BaseDiagramManager.this.drawingCanvas = new DrawingCanvas(BaseDiagramManager.this.graphManager);
						BaseDiagramManager.this.graphManager.assignSimpleUIs();
						BaseDiagramManager.this.setGraphManager(BaseDiagramManager.this.graphManager);
						BaseDiagramManager.this.diagramModelAdapter = new SwingDiagramModelAdapter(BaseDiagramManager.this.graphManager);
						BaseDiagramManager.this.drawingCanvas.getInnerCanvas().addMouseListener(getDiagramMouseListener());
						
						while (!isInitialized) {
							synchronized (BaseDiagramManager.this) {
								try {
									isInitialized = true;
									BaseDiagramManager.this.notifyAll();
								} catch (Exception e) {}
								
							}
						}
						
					}
				});
			}
		};
		
		init.run();
	

}
	
	public void setDiagramModelAdapter(IDiagramModelAdapter adapter) {
		this.diagramModelAdapter = adapter;
	}
	

	
	public TSEGraphManager createGraphManager() {
		return new TSEGraphManager();
	}
	
	public boolean validateCutEdit(List<TSENode> selectedNodes , List<TSEEdge> selectedEdges ){
		return true;
	}
	
	public boolean validateCopyEdit(List<TSENode> selectedNodes , List<TSEEdge> selectedEdges ){
		return true;
	}
	
	public boolean validatePasteEdit(List<TSENode> selectedNodes , List<TSEEdge> selectedEdges ){
		return true;
	}
	
	public boolean validateDeleteEdit(List<TSENode> selectedNodes , List<TSEEdge> selectedEdges){
		return true;
	}
	
	// Can override these methods
	public void cutGraph() { 
		setCutGraph(true);
	}

	public void copyGraph() {
		setCopyGraph(true);
	}
	public void pasteGraph() {
		setPasteGraph(true);
	}
	
	public void delete() {}
	
	public void setActiveTransitionTool() {}

	/**
	 * @return {@link Component}
	 */
	protected JComponent createDrawingCanvas() {
		this.initUI();
		setLayoutManager(this.getLayoutManager());
		if (SwingUtilities.isEventDispatchThread()) {
			Display.getDefault().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					registerTools();
				}
			});
		} else {
			registerTools();
		}
		setPreferences();
		try {
			if(isValidModelFile()){
				openModel();
			}else{
				createInitialModel();
			}
		} catch (Exception e) {
			DiagrammingPlugin.log(e);
		}
		registerListeners();
		return this.drawingCanvas;
	}

	protected void createInitialModel() throws Exception{}
	
	protected boolean isValidModelFile(){
		return false;
	}
	
	protected void setPreferences() {

	}
	
	protected void registerTools() {
		// Registering the tools with the Swing canvas' tool manager
		waitForInitComplete();
		TSToolManager toolManager = this.drawingCanvas.getToolManager();
		TSViewingToolHelper.registerAll(toolManager);
		TSEditingToolHelper.registerAll(toolManager);
		TSEditingToolHelper.registerTransferSelectedTool(toolManager, getTransferSelectedTool());
		TSEditingToolHelper.registerMoveSelectedTool(toolManager, getMoveSelectedTool());
		TSViewingToolHelper.registerSelectTool(toolManager, getSelectTool());
		//System.out.println("Diagram manager: "+this.getDiagramManager());
	
		TSEditingToolHelper.registerCreateEdgeTool(toolManager, getEdgeTool());
		TSEditingToolHelper.registerCreateNodeTool(toolManager, getNodeTool());
		//TSEditingToolHelper.registerSelectTool(this.drawingCanvas.getToolManager(),	getSelectTool());
		TSEditingToolHelper.registerPasteTool(this.drawingCanvas.getToolManager(), getPasteTool());
		TSEditingToolHelper.registerReconnectEdgeTool(this.drawingCanvas.getToolManager(), getReconnectEdgeTool());
		TSEditingToolHelper.registerEditTextTool(this.drawingCanvas.getToolManager(), getEditTextTool());
		TSEditingToolHelper.registerTransferSelectedTool(this.drawingCanvas.getToolManager(), getTransferSelectedTool());

		if (StudioConfig.getInstance().getProperty("be.studio.diagram.enableBendPointAdding", "true") != null) {
			boolean addPNodes = Boolean.parseBoolean(
				StudioConfig.getInstance().getProperty("be.studio.diagram.enableBendPointAdding", "true"));
			if (!addPNodes) {
				TSEditingToolHelper.registerCreatePNodeTool(this.drawingCanvas.getToolManager(), null);				
			}
		}

		// Use the select tool by default
		toolManager.setActiveTool(TSViewingToolHelper.getSelectTool(toolManager));
		toolManager.setDefaultTool(TSViewingToolHelper.getSelectTool(toolManager));

	}
	
	public TSESelectTool getSelectTool(){
		if (this.selectTool == null) {
			this.selectTool = new SelectTool();
		}
		return this.selectTool;
	}
	
	public TSEMoveSelectedTool getMoveSelectedTool(){
		if (this.moveSelectedTool == null) {
			this.moveSelectedTool = new TSEMoveSelectedTool();
			this.moveSelectedTool.setAdjustToGrid(false);
		}
		return this.moveSelectedTool;
	}
	
	public TSEPasteTool getPasteTool(){
		if (this.pasteTool == null) {
			this.pasteTool = new TSEPasteTool();
		}
		return this.pasteTool;
	}

	public TSEEditTextTool getEditTextTool(){
		if (this.editTextTool == null) {
			this.editTextTool = new TSEEditTextTool();
		}
		return this.editTextTool;
	}
	
	
	public TSETransferSelectedTool getTransferSelectedTool(){
		if (this.transferSelectedTool == null) {
			this.transferSelectedTool = new TSETransferSelectedTool();
		}
		return this.transferSelectedTool;
	}
	
	public TSECreateNodeTool getNodeTool(){
		return this.createNodeTool;
	}
	
	public TSECreateEdgeTool getEdgeTool(){
		return this.createEdgeTool;
	}
	
	public ReconnectEdgeTool getReconnectEdgeTool(){
		if (this.reConnectEdgeTool == null) {
			this.reConnectEdgeTool = new ReconnectEdgeTool();
		}
		return this.reConnectEdgeTool;
	}
	
	public boolean getRefreshAction() {
		return REFRESH_ACTION;
	}
	
	public void setRefreshAction(boolean refresh) {
		REFRESH_ACTION = refresh;
	}
	
	protected void registerListeners() {
		((TSEEventManager) this.graphManager.getEventManager()).addSelectionChangeListener(
				this.graphManager,
				getDiagramSelectionListener(),
				TSESelectionChangeEvent.NODE_SELECTION_CHANGED |
				TSESelectionChangeEvent.EDGE_SELECTION_CHANGED |
				TSESelectionChangeEvent.GRAPH_SELECTION_CHANGED|
				TSESelectionChangeEvent.ANY_CHANGE);	

		((TSEEventManager) this.graphManager.getEventManager()).addGraphChangeListener(
				this.graphManager,
				getDiagramChangeListener(),
				TSGraphChangeEvent.ANY_NODE | TSGraphChangeEvent.ANY_EDGE | TSGraphChangeEvent.EDGE_ENDNODE_CHANGED);

		((TSEEventManager) this.graphManager.getEventManager()).addDrawingChangeListener(
				this.graphManager,
				getDiagramChangeListener(),
				TSDrawingChangeEvent.NODE_MOVED |
				TSDrawingChangeEvent.NODE_RESIZED |
				TSDrawingChangeEvent.BEND_DISCARDED |
				TSDrawingChangeEvent.BEND_INSERTED |
				TSDrawingChangeEvent.BEND_MOVED |
				TSDrawingChangeEvent.BEND_REMOVED |
				TSDrawingChangeEvent.EDGE_ENDCONNECTOR_CHANGED |
				TSDrawingChangeEvent.LABEL_RENAMED |
				TSDrawingChangeEvent.LABEL_MOVED);
		((TSEEventManager) this.graphManager.getEventManager()).addViewportChangeListener(
				this.drawingCanvas,
				getDiagramViewPortChangeListener(),
				TSEViewportChangeEvent.ZOOM |
				TSEViewportChangeEvent.PAN |
				TSEViewportChangeEvent.ANY_CHANGE);
	}
	
	protected void addlayoutListener(){
		((TSEEventManager) this.graphManager.getEventManager()).addLayoutListener(this.graphManager, this, TSLayoutEvent.POST_LAYOUT);
	}
	
	public DiagramChangeListener<? extends DiagramManager> getDiagramChangeListener() {
		return this.changeListener;
	}
	
	protected DiagramViewPortChangeListener getDiagramViewPortChangeListener() {
		return this.viewPortListener;
	}
	
	public  SelectionChangeListener getDiagramSelectionListener() {
		return this.selectionListener;
	}
	
	
    public boolean layoutDiagramOnChange() {
		return false;
    }	
	
	public LayoutManager getLayoutManager() {
		return this.layoutManager;
	}
	
	public void setLayoutManager(LayoutManager mgr) {
		this.layoutManager = mgr;
	}

	public IDiagramManager getDiagramManager() {
		return diagramManager;
	}

	/**
	 * @param diagramManager
	 */
	public void setDiagramManager(IDiagramManager diagramManager) {
		this.diagramManager = diagramManager;
	}

	/**
	 * @param graphManager
	 */
	public void setGraphManager(TSEGraphManager graphManager) {
		final TSEGraphManager graphManager1=graphManager;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				graphManager1.assignSimpleUIs();	
				if (drawingCanvas != null) {
					drawingCanvas.setGraphManager(graphManager1);
				}
			}
		});

	}

	public TSEGraphManager getGraphManager() {
		return graphManager;
	}

	public void setSwingCanvas(DrawingCanvas drawingCanvas) {
		this.drawingCanvas = drawingCanvas;
	}

	public DrawingCanvas getDrawingCanvas() {
		return drawingCanvas;
	}
	
	public TSEOverviewComponent getOverviewComponent() {
		return this.overviewComponent;
	}
	
	public TSEAllOptionsServiceInputData getServiceInputData() {
		TSEAllOptionsServiceInputData result = null;

		if (drawingCanvas != null)
		{
			result = this.getServiceInputData(drawingCanvas.getGraphManager());
		}
		return result;

	}

	private TSEAllOptionsServiceInputData getServiceInputData(
			TSEGraphManager graphManager) {
		TSEAllOptionsServiceInputData result = null;

		if (this.drawingCanvas != null)
		{
			result = (TSEAllOptionsServiceInputData)
				TSApplicationReference.getInstance().
					getReference(
						graphManager,
						TSApplicationReference.LAYOUT_INPUT_DATA);

			if (result == null)
			{
				result = new TSEAllOptionsServiceInputData(
					graphManager);
				TSApplicationReference.getInstance().
					setReference(
						graphManager,
						TSApplicationReference.LAYOUT_INPUT_DATA,
						result);
				TSLayoutInputTailor tailor = new TSLayoutInputTailor(result);
				tailor.setGraphManager(graphManager);
			}
		}

		return result;
	}

	public void initOverviewComponent() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				overviewComponent = new TSEOverviewComponent(drawingCanvas);
				overviewComponent.setVisible(true);	
				}	
			});
	}
	
	public List<TSENode> getSelectedNodes() {
		selectedNodes.clear();
		if (getGraphManager() == null) {
			return selectedNodes;
		}
		for(Object obj:  getGraphManager().selectedNodes()){
			TSENode node = (TSENode)obj;
			selectedNodes.add(node);
		}
		return selectedNodes;
	}
	
	public List<TSENodeLabel> getSelectedNodeLabels() {
		selectedNodeLabels.clear();
		for(Object obj:  getGraphManager().selectedNodeLabels()){
			TSENodeLabel nodeLabel = (TSENodeLabel)obj;
			selectedNodeLabels.add(nodeLabel);
		}
		return selectedNodeLabels;
	}
	
	public List<TSEEdge> getSelectedEdges() {
		selectedEdges.clear();
		if (getGraphManager() == null) {
			return selectedEdges;
		}
		for(Object obj:  getGraphManager().selectedEdges(true)){
			TSEEdge edge = (TSEEdge)obj;
			selectedEdges.add(edge);
		}
		return selectedEdges;
	}
	
	public List<TSEEdgeLabel> getSelectedEdgeLabels() {
		selectedEdgeLabels.clear();
		for(Object obj:  getGraphManager().selectedEdgeLabels(true)){
			TSEEdgeLabel edgeLabel = (TSEEdgeLabel)obj;
			selectedEdgeLabels.add(edgeLabel);
		}
		return selectedEdgeLabels;
	}
	
//	/**
//	 * @return
//	 */
//	public IPreferenceStore getPreferenceStore() {
//		return this.diagramPrefStore;
//	}
//	
//	/**
//	 * @param store
//	 */
//	public void setPreferenceStore(IPreferenceStore store) {
//		this.diagramPrefStore = store;
//	}
	
//	public void clearCache(){
//		selectedNodes.clear();
//		selectedEdges.clear();
//	}
//	
//
//	public void removeFromCache(TSEObject o){
//		if(o instanceof TSENode){
//			if(selectedNodes.contains(o)){
//				selectedNodes.remove(o);
//			}
//		}
//		if(o instanceof TSEEdge){
//			if(selectedEdges.contains(o)){
//				selectedEdges.remove(o);
//			}
//		}
//	}
//	
//	public void addToCache(TSEObject o){
//		if (o instanceof TSENode){
//			if(!selectedNodes.contains((TSENode)o)){
//				selectedNodes.add((TSENode)o);
//			}
//		}
//		if (o instanceof TSEEdge){
//			if(!selectedEdges.contains((TSEEdge)o)){
//				selectedEdges.add((TSEEdge)o);
//			}
//		}
//	}
	
//	@SuppressWarnings("serial")
//	protected Container getSwingContainer(Composite parent) {
//		Frame frame = SWT_AWT.new_Frame(parent);
//		new SyncXErrorHandler().installHandler();
//		
//		Panel panel = new Panel(new BorderLayout()) {
//			public void update(java.awt.Graphics g) {
//				paint(g);
//			}
//		};
//		frame.add(panel);
//		frame.setFocusable(true);
//		final JRootPane root = new JRootPane();
//		panel.add(root);
////		SwingUtilities.invokeLater(new Runnable() {
////		
////			@Override
////			public void run() {
//				root.requestFocusInWindow();
////			}
////		});
//		pane = root.getContentPane();
//		return pane;
//	}
	
	//Override this
	protected void initUI() { }
	//Override this
	public void openModel() throws Exception {};

	public boolean isPasteGraph() {
		return pasteGraph;
	}

	public void setPasteGraph(boolean pasteGraph) {
		this.pasteGraph = pasteGraph;
	}
	
	public boolean isCopyGraph() {
		return copyGraph;
	}

	public void setCopyGraph(boolean copyGraph) {
		this.copyGraph = copyGraph;
		if (copyGraph) {
			this.cutGraph = false;
			if (this.cutMap != null) {
				this.cutMap.clear();
			}
		}
	}

	public boolean isCutGraph() {
		return cutGraph;
	}

	public void setCutGraph(boolean cutGraph) {
		this.cutGraph = cutGraph;
		if (cutGraph) {
			this.copyGraph = false;
			if (this.copyMap != null) {
				this.copyMap.clear();
			}
		}
	}

	public Map<String, Object> getCutMap() {
		return cutMap;
	}

	public void setCutMap(Map<String, Object> cutMap) {
		this.cutMap = cutMap;
	}

	public Map<String, Object> getCopyMap() {
		return copyMap;
	}

	public void setCopyMap(Map<String, Object> copyMap) {
		this.copyMap = copyMap;
	}

//	public EditorPart getEditor() {
//		return editor;
//	}
	
	/**
	 * @param map
	 * @param selectedNodes
	 * @param selectedEdges
	 * @param isCopy
	 */
	public void addToEditGraphMap(Map<String, Object> map, List<TSENode> selectedNodes, List<TSEEdge> selectedEdges,  boolean isCopy){
		//Override this for Graph Cut/Copy/Paste Functionality 
	}
	
	/**
	 * @return
	 */
	public boolean handleTransitionsEditGraphMap(){
		//Override this for handling Transitions Cut/Copy/Paste Functionality 
		return false;
	}

	public List<TSEEdge> getEdgesClipBoard() {
		return edgesClipBoard;
	}
	
	public void resetDiagramEditor() {
//		try{
//			//Resetting the select tool & palette
//			DrawingCanvas drawingCanvas = getDrawingCanvas();
//			if(drawingCanvas != null){
//				drawingCanvas.getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(drawingCanvas.getToolManager()));
//				resetPaletteSelection();
//			}
//	
//			EditorPart editorPart = getEditor();
//			if (editorPart != null){
//				IWorkbenchPartSite partSite = editorPart.getSite();
//				if (partSite != null) {
//					IWorkbenchWindow workBenchWindow = partSite.getWorkbenchWindow();
//					 if(workBenchWindow != null) {
//						 final IWorkbenchPage page = workBenchWindow.getActivePage();
//						 if(page != null){
//								final IViewPart part = page.findView("com.tibco.cep.studio.projectexplorer.view");
//								// To fix invalid thread access exception
//								Thread currentThread = Thread.currentThread();
//								Display display = PlatformUI.getWorkbench().getDisplay();
//								if(currentThread == display.getThread()){
//									page.activate(part);
//									page.activate(this.getEditor());
//								} else {
//									display.asyncExec(new Runnable(){
//										public void run() {
//											page.activate(part);
//											page.activate(getEditor());
//										}
//									});
//								}
//							}
//					 }
//				}
//			}	
//		}catch(Exception e){
//			e.printStackTrace();
//		}
	}
	
	protected void resetPaletteSelection(){}

	@Override
	public PALETTE getPalette() {
		return null;
	}

	public String getSelectedPaletteEntry() {
		return this.selectedPaletteEntry;
	}

	public void setSelectedPaletteEntry(String selectedPaletteEntry) {
		this.selectedPaletteEntry = selectedPaletteEntry;
	}
	
//	public boolean isResetToolOnChange() {
//		return this.diagramPrefStore.getBoolean(RESET_TOOL_AFTER_CHANGES);
//	}
//	
//	public void dispose() {
//		disposeTools();
//		if (managedForm != null && managedForm.getToolkit() != null) {
//			managedForm.dispose();
//			managedForm = null;
//		}
//		if (layoutManager != null) {
//			layoutManager.dispose();
//			layoutManager = null;
//		}
//        if (diagramManager != null) {
//			diagramManager = null;
//		}
//		if (drawingCanvas != null) {
//			drawingCanvas.getInnerCanvas().removeMouseListener(getDiagramMouseListener());
//			drawingCanvas.dispose();
//			drawingCanvas = null;
//		}
//		if (graphManager != null) {
//			graphManager.getMainDisplayGraph().nodeSet.clear();
//			graphManager.getMainDisplayGraph().edgeSet.clear();
//			graphManager.setMainDisplayGraph(null);
//			graphManager.getEventManager().removeAllListeners(this);
//			graphManager.getEventManager().removeAllListeners(graphManager);
//			graphManager.setNodeBuilder(null);
//			graphManager.setEdgeBuilder(null);
//			graphManager.setLabelBuilder(null);
//			graphManager.setConnectorBuilder(null);
//			graphManager.setGraphBuilder(null);
//			graphManager.dispose();
//			graphManager = null;
//		}
//		if (createToolPartListeners != null) {
//			createToolPartListeners.clear();
//			createToolPartListeners = null;
//		}
//		if (overviewComponent != null) {
//			overviewComponent.removeAll();
//			overviewComponent = null;
//		}
//		if (pane != null) {
//			DiagramUtils.disposePanel(pane);
//			pane = null;
//		}
//		if (this.diagramModelAdapter != null) {
//			this.diagramModelAdapter = null;
//		}
//		if (this.editTailor != null) {
//			this.editTailor = null;
//		}
//		if (this.intrTailor != null) {
//			this.intrTailor = null;
//		}
//		editor = null;
//		viewPortListener = null;
//		selectionListener = null;
//		animationPreferenceTailor = null;
//		diagramPrefStore = null;
//		selectedEdgeLabels = null;
//		selectedEdges = null;
//		selectedNodeLabels = null;
//		selectedNodes = null;
//	}
/*	public void emfUndo() {
		
	}
	public void emfRedo() {
		
	}
	public boolean emfCanUndo() {
		return true;
		
	}
	public boolean emfCanRedo() {
		return true;
	}
*/
	public TSCommandInterface undo() {
		return this.drawingCanvas.getCommandManager().undo();
	}
	
	public TSCommandInterface redo() {
		return this.drawingCanvas.getCommandManager().redo();
	}
	
	public TSCommandManager getCommandManager() {
		return this.drawingCanvas.getCommandManager();
	}
	
	public boolean canUndo() {
		return this.drawingCanvas.getCommandManager().canUndo();
	}
	
	public boolean canRedo() {
		return this.drawingCanvas.getCommandManager().canRedo();
	}
	
	/**
	 * @return whether command execution was successful
	 */
	public boolean executeCommand(TSCommand command) {
		int status = this.drawingCanvas.getCommandManager().transmit(command);
		if (status == TSCommandManager.COMMAND_ACCEPTED) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void clearUndoHistory() {
		this.drawingCanvas.getCommandManager().clearUndoHistory();
	}

//	@Override
//	public void propertyChange(PropertyChangeEvent event) {
//		try {
//			String property = event.getProperty();
//			
//			IPreferenceStore prefstore=(DiagrammingPlugin.getDefault().getPreferenceStore());
//			if(property.equals(UNDO_LIMIT)){
//				int undoLimit=prefstore.getInt(UNDO_LIMIT);
//				intrTailor.setUndoLimit(undoLimit);
//				
//			}
//			if (property.equals(RESET_TOOL_AFTER_CHANGES)) {
//				isResetToolOnChange();
//			
//			}
//			if (property.equals(ANIMATION_LAYOUT_ALLOW)) {
//				boolean allow = prefstore.getBoolean(ANIMATION_LAYOUT_ALLOW);
//				setAnimationPreferences(allow);
//			}
//			if (property.equals(LAYOUT_ANIMATION_DURATION)) {
//				int layoutDuration = prefstore.getInt(LAYOUT_ANIMATION_DURATION);
//				animationPreferenceTailor.setLayoutAnimationDuration(layoutDuration);
//			}
//			/*if (property.equals(VIEWPORT_CHANGE_DURATION)) {
//				int viewportChangeDuration = prefstore.getInt(VIEWPORT_CHANGE_DURATION);
//				animationPreferenceTailor.setViewportChangeAnimationDuration(viewportChangeDuration);
//			}*/
//			if (property.equals(ANIMATION_LAYOUT_FADE)) {
//				boolean fade =  prefstore.getBoolean(ANIMATION_LAYOUT_FADE);
//				animationPreferenceTailor.setLayoutFadeAnimation(fade);
//			}
//			if (property.equals(ANIMATION_LAYOUT_INTERPOLATION)) {
//				boolean interpolation = prefstore.getBoolean(ANIMATION_LAYOUT_INTERPOLATION);
//				animationPreferenceTailor.setLayoutInterpolationAnimation(interpolation);
//			}
//		
//			if (property.equals(ANIMATION_LAYOUT_VIEWPORT_CHANGE)) {
//				boolean viewportChange = prefstore.getBoolean(ANIMATION_LAYOUT_VIEWPORT_CHANGE);
//				animationPreferenceTailor.setViewportChangeAnimation(viewportChange);
//				int viewportChangeDuration = prefstore.getInt(VIEWPORT_CHANGE_DURATION);
//				animationPreferenceTailor.setViewportChangeAnimationDuration(viewportChangeDuration);
//				
//				/*if(viewportChange)
//				{
//				animationPreferenceTailor.setViewportChangeAnimationDuration(viewportChangeDuration);
//				}else{
//					animationPreferenceTailor.setViewportChangeAnimationDuration(0);
//				}
//				*/	
//				
//			/**
//			 * For Grid preference for Concept,Event,Project,StateModel
//			 */
//		
//				
//			}
//			if (property.equals(VIEWPORT_CHANGE_DURATION)) {
//				/*int viewportChangeDuration = prefstore.getInt(VIEWPORT_CHANGE_DURATION);
//				if(viewportChange)
//				{
//				animationPreferenceTailor.setViewportChangeAnimationDuration(viewportChangeDuration);
//				}else{
//					animationPreferenceTailor.setViewportChangeAnimationDuration(0);
//				}*/
//					
//			}
//		} catch (Exception e) {
//			DiagrammingPlugin.debug(this.getClass().getName(), e.getLocalizedMessage(), e.getMessage());
//		}
//	}
//	
//	/**
//	 * Set Animation preferences
//	 * @param allow
//	 */
//	private void setAnimationPreferences(boolean allow) {
//		IPreferenceStore prefstore=(DiagrammingPlugin.getDefault().getPreferenceStore());
//		if (allow){
//     		int layoutDuration =         prefstore.getInt(LAYOUT_ANIMATION_DURATION);
////     		int viewportChangeDuration = prefstore.getInt(VIEWPORT_CHANGE_DURATION);
//     		boolean fade =               prefstore.getBoolean(ANIMATION_LAYOUT_FADE);
//     		boolean interpolation =      prefstore.getBoolean(ANIMATION_LAYOUT_INTERPOLATION);
//     		boolean viewportChange =     prefstore.getBoolean(ANIMATION_LAYOUT_VIEWPORT_CHANGE);
//     		animationPreferenceTailor.setLayoutFadeAnimation(fade);
//    		animationPreferenceTailor.setLayoutInterpolationAnimation(interpolation);
//    		animationPreferenceTailor.setViewportChangeAnimation(viewportChange);
//			animationPreferenceTailor.setLayoutAnimationDuration(layoutDuration);
//			/*if(viewportChange)
//			{
//			animationPreferenceTailor.setViewportChangeAnimationDuration(viewportChangeDuration);
//			}else{
//				animationPreferenceTailor.setViewportChangeAnimationDuration(0);
//				
//			}
//			*/return;
//		}
//     	animationPreferenceTailor.setLayoutFadeAnimation(false);
//    	animationPreferenceTailor.setLayoutInterpolationAnimation(false);
//    	animationPreferenceTailor.setViewportChangeAnimation(false);
//     	animationPreferenceTailor.setLayoutAnimationDuration(0);
//		animationPreferenceTailor.setViewportChangeAnimationDuration(0);
//     	
//	}
	
	/**
	 * Traversing Nodes to set Path Attributes
	 * @param node
	 * @param isCopy
	 */
	public void traversePathAttributeNodeType(TSENode node, boolean isCopy) {
		
	}
	
//	public void unregisterListeners() {
//		
//		((TSEEventManager) this.graphManager.getEventManager()).removeAllSelectionChangeListeners(this.graphManager);
//		((TSEEventManager) this.graphManager.getEventManager()).removeAllGraphChangeListeners(this.graphManager);
//		((TSEEventManager) this.graphManager.getEventManager()).removeAllDrawingChangeListeners(this.graphManager);
//		((TSEEventManager) this.graphManager.getEventManager()).removeAllViewportChangeListeners(this.graphManager);
//
//		DiagrammingPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(this);
//	}
	
	/**
	 * @return
	 */
	public boolean isSelectedMultipleGraph() {
		int size = getSelectedNodes().size() + getSelectedEdges().size() ;
		if (size > 1){
			return true;
		}
		return false;
	}
	
	public boolean isLAYOUT_CHANGE_FLAG() {
		return getDiagramChangeListener().LAYOUT_CHANGE_FLAG;
	}

	@Override
	public void onLayout(TSLayoutEvent arg0) {
//		IViewPart view = getEditor().getSite().getPage().findView(PaletteView.ID);
//		if (view != null) {
//			PaletteView paletteView = (PaletteView) view;
//			paletteView.setCursor(false);
//			
//		}
	}
	
	public void onNodeAdded(TSENode tsNode){
		
	}
	
	public void onNodeMoved(TSENode tsNode){
		
	}
	
	public void onNodeLabelMoved(TSENodeLabel label){
		
	}
	
	public void onEdgeMoved(TSEEdge edge){
		if (isLAYOUT_CHANGE_FLAG()) {
			layoutDiagramOnChange();
		}
	}
	
	protected void disposeTools() {
		if (createEdgeTool != null) {
			createEdgeTool.dispose();
			createEdgeTool = null;
		}
		if (reConnectEdgeTool != null) {
			reConnectEdgeTool.dispose();
			reConnectEdgeTool = null;
		}
		if (selectTool != null) {
			selectTool.dispose();
			selectTool = null;
		}
		if (selectTool != null) {
			selectTool.dispose();
			selectTool = null;
		}
		if (moveSelectedTool != null) {
			moveSelectedTool = null;
		}
		if (transferSelectedTool != null) {
			transferSelectedTool = null;
		}
		if (createNodeTool != null) {
			createNodeTool.dispose();
			createNodeTool = null;
		}
		if (pasteTool != null) {
			pasteTool = null;
		}
		if (editTextTool != null) {
			editTextTool = null;
		}
	}

	public void onZoom() {
		// TODO Auto-generated method stub
		
	}
	
	protected MouseListener getDiagramMouseListener() {
		return activationMouseListener;
	}
	
	public void refreshNode(TSENode node) {
		
	}
	
	public void refreshEdge(TSEEdge edge) {
		
	}
	
	public Object getDiagramEditor() {
		return null;
	}
	
	public Object getDiagramEditorSite() {
		return null;
	}

	public boolean isDisplayFullName() {
		return false;
	}
	
	public boolean isfillTaskIcons() {
		return false;
	}
	
	public void waitForInitComplete() {
		int counter = 0;

		if (!SwingUtilities.isEventDispatchThread()) {
			synchronized (this) {
				while (!isInitialized && counter < 5) {
					try {
						wait(500);
					} 
					catch (Exception e) {
					}
					counter++;
				}
			}
		}
		if (!isInitialized) {
			DiagrammingPlugin.log("Timeout while waiting for diagram to initialize.  You may need to reopen the editor or restart to resolve");
		}
	}
}