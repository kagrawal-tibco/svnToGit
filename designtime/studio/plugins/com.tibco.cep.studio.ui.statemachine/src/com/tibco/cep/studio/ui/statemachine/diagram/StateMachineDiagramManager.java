package com.tibco.cep.studio.ui.statemachine.diagram;

import static com.tibco.cep.diagramming.utils.DiagramUtils.gridType;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.STATEMACHINE_FIX_LABELS;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.STATEMACHINE_LINES;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.STATEMACHINE_NONE;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.STATEMACHINE_POINTS;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineConstants.STATE_TYPE;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.createTooltip;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.getStateEndSize;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.overlapNodesFixed;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.editGraph;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getStateGraphPath;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.openError;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.be.util.BidiMap;
import com.tibco.be.util.DualHashBidiMap;
import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.diagramming.drawing.DiagramChangeListener;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tibco.cep.diagramming.tool.CreateEdgeTool;
import com.tibco.cep.diagramming.tool.CreateNodeTool;
import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.diagramming.tool.ReconnectEdgeTool;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.ui.AnimatedEdgeUI;
import com.tibco.cep.diagramming.ui.ChildGraphNodeUI;
import com.tibco.cep.diagramming.ui.FinalStateNodeUI;
import com.tibco.cep.diagramming.ui.InitialNodeUI;
import com.tibco.cep.diagramming.ui.RoundRectNodeUI;
import com.tibco.cep.diagramming.ui.SubStateNodeUI;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.ui.overview.OverviewUtils;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tibco.cep.studio.ui.statemachine.StateMachinePlugin;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditorInput;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachineCreateEdgeTool;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachineCreateNodeTool;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachineMoveSelectedTool;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachinePasteTool;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachineReconnectEdgeTool;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachineSelectTool;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.CompositeStateNodeGraphUI;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.ConcurrentStateNodeUI;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.RegionGraphNodeUI;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.TransitionStateEdgeCreator;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.Messages;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE;
import com.tibco.cep.studio.ui.statemachine.diagram.xml.StateTransitionXMLWriter;
import com.tibco.cep.studio.ui.statemachine.diagram.xml.StateXMLWriter;
import com.tibco.cep.studio.ui.statemachine.validation.ReadOnlyStateModelValidator;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.util.StudioConfig;
import com.tomsawyer.drawing.geometry.shared.TSOvalShape;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.TSEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSELabelUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;
import com.tomsawyer.graphicaldrawing.xml.TSEVisualizationXMLWriter;
import com.tomsawyer.interactive.command.TSCommandInterface;
import com.tomsawyer.interactive.swing.TSSwingCanvasPreferenceTailor;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateEdgeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEMoveSelectedTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.tool.TSToolManager;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.util.preference.TSPreferenceData;
/**
 * 
 * @author ggrigore
 * @author hitesh
 * 
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class StateMachineDiagramManager extends DiagramManager	implements ISelectionProvider,IPropertyChangeListener{

	public static final String CLASS = StateMachineDiagramManager.class.getName();	
	protected List<TSEEdge> entityEdgeList;
	private ISelection editorselection;
	
	/**
	 * This listens to which ever viewer is active.
	 */
	protected ISelectionChangedListener selectionChangedListener;
    
	/**
	 * This keeps track of all the
	 * {@link org.eclipse.jface.viewers.ISelectionChangedListener}s that are
	 * listening to this editor.
	 */

	protected Collection selectionChangedListeners = new ArrayList();
	private Composite swtAwtComponent;
	private StateMachine stateMachine;
	
	protected BidiMap<String, TSEObject> fGUIDStateCache = new DualHashBidiMap<String,TSEObject>();
	
	private Map<String, String> tagMap = new HashMap<String, String>();//This map for old and new tag for cut/copy/paste 
	
	protected HashMap startNodes = new HashMap();
	protected HashMap endNodeLists = new HashMap();
	
	private CompositeStateNodeGraphUI compositeUI;
	private ConcurrentStateNodeUI concurrentUI;
	private RegionGraphNodeUI regionGraphNodeUI;
	
	static public RoundRectNodeUI stateUI;
//	private SimpleStateNodeUI stateUI;
	private SubStateNodeUI subStateUI;
	private InitialNodeUI initUI;
	private FinalStateNodeUI endUI;

	private TSECurvedEdgeUI edgeCurvedUI;
	private TSEPolylineEdgeUI edgeUI;
	
	
	private IProject project;
	private EditorPart editor;
	
	protected StateMachineSelectTool stateMachineSelectTool;
	protected StateMachineMoveSelectedTool stateMachineMoveSelectedTool;
	protected StateMachineCreateEdgeTool stateMachineCreateEdgeTool;
	protected StateMachineCreateNodeTool stateMachineCreateNodeTool;
	protected StateMachineReconnectEdgeTool stateMachineReconnectEdgeTool;
	
	protected String projectName; 
	private IPreferenceStore prefStore;
	
	private boolean curved = false;
	
	protected StateMachinePasteTool stateMachinePasteTool;
	public EditorPart getEditor() {
		return editor;
	}

	private StateMachineSelectionChangeListener smSelectionListener;
	private StateMachineDiagramChangeListener smChangeListener;	
	
	public StateMachineDiagramManager(EditorPart editor) {
		this.editor = editor;
		if (editor != null && editor.getEditorInput() instanceof StateMachineEditorInput) {
			this.stateMachine = ((StateMachineEditorInput) editor.getEditorInput()).getStateMachine();
			this.project = ((StateMachineEditorInput) editor.getEditorInput()).getProject();
			this.projectName = this.project.getName();
		}else{
			this.stateMachine = ((StateMachineEditor)editor).getStateMachine();
			this.projectName = stateMachine.getOwnerProjectName();
		}
		this.prefStore = StateMachinePlugin.getDefault().getPreferenceStore();
	}
	
	public static TSEShapeNodeUI getStateNodeUI() {
		if (stateUI == null) {
			stateUI = new RoundRectNodeUI();
			stateUI.setDrawShadow(true);
			stateUI.setBorderColor(new TSEColor(0,0,255));
			// TODO: make these colors static to save memory, rest has to be cloned.
			stateUI.setGradient(new TSEColor(96, 105, 190), new TSEColor(221, 239, 255));
//			this.stateUI = new SimpleStateNodeUI();			
		}
		return (TSEShapeNodeUI) stateUI.clone();
	}

	protected void initUI() {
		getStateNodeUI();
		
		ChildGraphNodeUI.displayCompleteName = Boolean.parseBoolean(
			StudioConfig.getInstance().getProperty("be.studio.diagram.displayCompleteExpandedNodeName", "false"));
		
		this.compositeUI = new CompositeStateNodeGraphUI();
		this.compositeUI.setOuterRoundRect(true);
//		this.compositeUI.setFillColor(new TSEColor(191, 167, 232));
		this.compositeUI.setBorderDrawn(true);
		this.compositeUI.setDrawChildGraphMark(false);

		this.concurrentUI = new ConcurrentStateNodeUI();
		this.concurrentUI.setOuterRoundRect(true);
		this.concurrentUI.setFillColor(new TSEColor(218, 182, 135));
		this.concurrentUI.setBorderDrawn(true);
		this.concurrentUI.setDrawChildGraphMark(false);
		
		this.regionGraphNodeUI = new RegionGraphNodeUI();
		this.regionGraphNodeUI.setOuterRoundRect(true);
//		this.regionGraphNodeUI.setFillColor(new TSEColor(191, 167, 232));
		this.regionGraphNodeUI.setBorderDrawn(true);
		this.regionGraphNodeUI.setDrawChildGraphMark(false);

		this.subStateUI = new SubStateNodeUI();
		
		this.initUI = new InitialNodeUI();
		// this.initUI.setBorderColor(new TSEColor(FinalStateNodeUI.GLOW_OUTER_HIGH_COLOR));
		this.initUI.setBorderColor(InitialNodeUI.BORDER_COLOR);
		this.endUI = new FinalStateNodeUI();
		this.endUI.setStateType(FinalStateNodeUI.STATE_TYPE_END);

		this.edgeCurvedUI = new TSECurvedEdgeUI();
		this.edgeCurvedUI.setAntiAliasingEnabled(true);
		this.edgeCurvedUI.setCurvature(100);
		
		this.edgeUI = new TSEPolylineEdgeUI();
		this.edgeUI.setAntiAliasingEnabled(true);
		
		curved = isCurved();
		
		this.entityEdgeList=new LinkedList();
	}
	
	
	@Override
	protected void registerListeners() {

		super.registerListeners();
		//StateMachinePlugin.getDefault().getPreferenceStore().addPropertyChangeListener(this);
		addPropertyChangeListener(StateMachinePlugin.getDefault().getPreferenceStore());
	}

	
	@Override
	public void unregisterListeners() {
		super.unregisterListeners();
		StateMachinePlugin.getDefault().getPreferenceStore().removePropertyChangeListener(this);
	}
	
	public void postPopulateDrawingCanvas() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				waitForInitComplete();
				if (getDrawingCanvas().getZoomLevel() > 1.0) {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							getDrawingCanvas().setZoomLevelInteractive(1.5);
						}
					});
				}
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getSelectTool()
	 */
	public SelectTool getSelectTool(){
		if (this.stateMachineSelectTool == null) {
			this.stateMachineSelectTool = new StateMachineSelectTool(this);
		}
		return this.stateMachineSelectTool;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getMoveSelectedTool()
	 */
	@Override
	public TSEMoveSelectedTool getMoveSelectedTool() {
		boolean fixLabels = this.prefStore.getBoolean(StudioPreferenceConstants.STATEMACHINE_FIX_LABELS);
		if(fixLabels){
			if (this.stateMachineMoveSelectedTool == null) {
				this.stateMachineMoveSelectedTool = new StateMachineMoveSelectedTool();
			}
			return this.stateMachineMoveSelectedTool;
		}else{
			return super.getMoveSelectedTool();
			
		}
	
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getNodeTool()
	 */
	public CreateNodeTool getNodeTool(){
		if (this.stateMachineCreateNodeTool == null) {
			this.stateMachineCreateNodeTool = new StateMachineCreateNodeTool(this, ((StateMachineEditor)editor).isEnabled());
		}
		return this.stateMachineCreateNodeTool;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getEdgeTool()
	 */
	public CreateEdgeTool getEdgeTool(){
		if (this.stateMachineCreateEdgeTool == null) {
			this.stateMachineCreateEdgeTool = new StateMachineCreateEdgeTool(this,((StateMachineEditor)editor).isEnabled());
		}
		return this.stateMachineCreateEdgeTool;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getReconnectEdgeTool()
	 */
	public ReconnectEdgeTool getReconnectEdgeTool(){
		if (this.stateMachineReconnectEdgeTool == null) {
			this.stateMachineReconnectEdgeTool = new StateMachineReconnectEdgeTool(this);
		}
		return this.stateMachineReconnectEdgeTool;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getDiagramChangeListener()
	 */
	@Override
	public DiagramChangeListener<? extends DiagramManager> getDiagramChangeListener() {
		if (this.smChangeListener == null) {
			this.smChangeListener = new StateMachineDiagramChangeListener(this);
		}
		return this.smChangeListener;
	}
    
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getDiagramSelectionListener()
	 */
	@Override
	public SelectionChangeListener getDiagramSelectionListener() {
		if (this.smSelectionListener == null) {
			this.smSelectionListener = new StateMachineSelectionChangeListener(this);
		}
		return this.smSelectionListener;
	}	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#setPreferences()
	 */
	protected void setPreferences() {
		super.setPreferences();
		String gridType = prefStore.getString(StudioPreferenceConstants.STATEMACHINE_GRID);
		TSPreferenceData prefData = this.drawingCanvas.getPreferenceData();
		TSSwingCanvasPreferenceTailor swingTailor = new TSSwingCanvasPreferenceTailor(prefData);
		
		//to set grid type, configurable through state model preference page 
		
		if(gridType.equalsIgnoreCase(StudioPreferenceConstants.STATEMACHINE_NONE)){
			gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.none"), this.drawingCanvas);
		}else if(gridType.equalsIgnoreCase(StudioPreferenceConstants.STATEMACHINE_LINES)){
			gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.lines"), this.drawingCanvas);
		} else if(gridType.equalsIgnoreCase(StudioPreferenceConstants.STATEMACHINE_POINTS)){
			gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.points"), this.drawingCanvas);
		}
		
		swingTailor.setAutoHideScrollBars(false);
	}	

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#openModel()
	 */
	public void openModel() {
//		File file = new File(StudioWorkbenchUtils.getCurrentWorkspacePath() +	"/" + projectName + 
//				this.stateMachine.getFolder() +this.stateMachine.getName() + Messages.getString("SM_extension"));
//		TSEVisualizationXMLReader reader = new TSEVisualizationXMLReader(file);
//		reader.setNodeReader(new StateXMLReader(this));
//		reader.setEdgeReader(new StateTransitionXMLReader(this));
//		reader.setGraphManager(this.graphManager);
//		reader.setServiceInputData(this.getLayoutManager().getInputData());
//		try {
//			reader.read();
//		} catch (FileNotFoundException e) {
//			System.err.println("State machine diagram not found, generating one.");
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.err.println("Error opening state machine diagram!");
//			e.printStackTrace();
//		}
		this.generateModel();
		
		//Validate State Model after generated
//		validateStateModel();
		getLayoutManager().callBatchGlobalLayout();
	}

	/**
	 * Validate State Model when Diagram Initialize
	 */
	public void validateStateModel(){
		if(getEditor().getEditorInput() instanceof FileEditorInput){
			IFile file = ((FileEditorInput)getEditor().getEditorInput()).getFile();
			ValidationContext vldContext = new ValidationContext(file, IResourceDelta.CHANGED, IncrementalProjectBuilder.FULL_BUILD);
			ReadOnlyStateModelValidator smValidator = new ReadOnlyStateModelValidator(this);
			smValidator.validate(vldContext);
		}
	}
	
	public void generateModel() {
		this.generateStates(null, this.stateMachine);
		this.generateTransitions();
				
		Iterator iter = this.startNodes.keySet().iterator();
		TSEGraph graph = null;
		TSENode startNode;
		List endNodes;
		List tempList;
		while (iter.hasNext()) {
			graph = (TSEGraph) iter.next();
			startNode = (TSENode) this.startNodes.get(graph);
			endNodes = (List) this.endNodeLists.get(graph);
			if (startNode != null && endNodes != null && endNodes.size() > 0) {
				tempList = new LinkedList();
				tempList.add(startNode);
				tempList.addAll(endNodes);
				((StateMachineLayoutManager) this.getLayoutManager()).addSequenceConstraint(
					tempList, TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
			}
		}
//		this.save();
	}

	
	public void save() {
		TSEVisualizationXMLWriter writer = null;
		File f = new File(StudioResourceUtils.getCurrentWorkspacePath() + "/" +
				projectName + this.stateMachine.getFolder() +
			this.stateMachine.getName() + Messages.getString("SM_extension"));
		
		try {
			writer = new TSEVisualizationXMLWriter(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		writer.setNodeWriter(new StateXMLWriter());
		writer.setEdgeWriter(new StateTransitionXMLWriter());
		writer.setGraphManager(this.graphManager);
		writer.setServiceInputData(this.getLayoutManager().getInputData());
		writer.setPreferenceData(this.drawingCanvas.getPreferenceData());
		writer.setIndenting(false);
		writer.write();
	}	

	@SuppressWarnings("static-access")
	public void generateStates(TSGraph tsGraph, StateComposite currentStateMachine) {

		TSEGraph graph = (TSEGraph) tsGraph;
		if (tsGraph == null) {
			graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
			graph.setUserObject(stateMachine);
		}

		// iterate through all the states and create them
		Iterator stateIter = null;
		if(currentStateMachine.isConcurrentState()){
			stateIter = ((StateComposite) currentStateMachine).getRegions().iterator();
		}else{
			stateIter = ((StateComposite) currentStateMachine).getStateEntities().iterator();
		}
		
		TSENode tsNode = null;
	
		while (stateIter.hasNext()) {
			StateEntity entity = (StateEntity) stateIter.next();
			if (entity instanceof StateStart) {
				tsNode = this.createStartEndState(graph, this.initUI,(State) entity);
			} else if (entity instanceof StateEnd) {
				tsNode = this.createStartEndState(graph, this.endUI,(State) entity);
			} else if (entity instanceof StateComposite) {
				if (entity instanceof StateSubmachine) {
					tsNode = this.createSubState(graph, this.subStateUI,(State) entity);
				} else {
					tsNode = this.createComplexState(graph, (State) entity);
					// recursively process composite state
					this.generateStates(tsNode.getChildGraph(),(StateComposite) entity);
				}
			} else {
				tsNode = this.createSimpleState(graph, this.stateUI,(State) entity);
				/*
				 *TSEAddNodeCommand command = new TSEAddNodeCommand(graph,point.getX(),point.getY());
		TSCommandManager commandManager = getSwingCanvas().getCommandManager();
		commandManager.transmit(command);
		return command.getNode();
				 * 
				 */
			}

			if (this.fGUIDStateCache.containsKey(entity.getGUID())) {
				StateMachinePlugin.logErrorMessage(MessageFormat.format("[{0}] Duplicate GUID in State Machine {1}",CLASS,entity.getGUID()));
			}
			this.fGUIDStateCache.put(entity.getGUID(), tsNode);
		}
		
		// now iterate through all the states again and only find the concurrent
		// ones so we can set hierarchical constraints, now that the child graphs
		// are populated TODO: instead of this, just save the sequence constraint,
		// and then as you add nodes to child graphs, add those nodes to the sequence constraint.
		TSEGraph childGraph;
		stateIter = ((StateComposite)currentStateMachine).getStateEntities().iterator();	
		while (stateIter.hasNext()) {
			StateEntity entity = (StateEntity) stateIter.next();
			if ((entity instanceof StateComposite) &&
				((StateComposite) entity).isConcurrentState()) {
				tsNode = (TSENode) this.fGUIDStateCache.get(entity.getGUID());
				childGraph = (TSEGraph) tsNode.getChildGraph();
				if (childGraph == null) {
					StateMachinePlugin.logErrorMessage(MessageFormat.format("[{0}] Concurrent state machine has no states.",CLASS));
				}
				else {
					((StateMachineLayoutManager) this.layoutManager).configureConcurrentOptions(tsNode);				
				}
			}
		}
	}

	private void generateTransitions() {

		StateTransition transition;
		State fromState;
		State toState;
		TSENode fromTSNode = null;
		TSENode toTSNode = null;
		Iterator<StateTransition> transIter = 
			this.stateMachine.getStateTransitions().iterator();
		

		while (transIter.hasNext()) {
			transition = transIter.next();
			fromState = transition.getFromState();
			toState = transition.getToState();

			if(fromState.getGUID() != null){
				if (!this.fGUIDStateCache.containsKey(fromState.getGUID())) {
					StateMachinePlugin.logErrorMessage(MessageFormat.format("[{0}] Missing source node: {1}",fromState.getName()));					
					continue;
				} else {
					fromTSNode = (TSENode) fGUIDStateCache.get(fromState.getGUID());
				}
			}
			
			if(toState != null){
			if(toState.getGUID() != null){
				if (!this.fGUIDStateCache.containsKey(toState.getGUID())) {
					StateMachinePlugin.logErrorMessage(MessageFormat.format("[{0}] Missing target node: [{1}]",toState.getName()));					
					continue;
				} else {
					toTSNode = (TSENode) fGUIDStateCache.get(toState.getGUID());
				}
			}
			}
			if(curved){
		    	this.createStateTransition(fromTSNode, toTSNode, this.edgeCurvedUI, transition);
			}
			else {
				this.createStateTransition(fromTSNode, toTSNode, this.edgeUI, transition);
			}
		}
	}

	public TSENode createSimpleState(TSEGraph graph, TSENodeUI ui, State smNode) {
		TSENode tsNode = (TSENode) graph.addNode();
		
		this.decorateStateNode(tsNode, smNode, ui);
		tsNode.setAttribute(STATE_TYPE, STATE.SIMPLE);
		TSENodeLabel nodeLabel = (TSENodeLabel) tsNode.addLabel();
		((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		nodeLabel.setName(smNode.getName());
		if(nodeLabel != null){
			getLayoutManager().setNodeLabelOptions(nodeLabel);
		}
		tsNode.setTooltipText(createTooltip(smNode));
		return tsNode;
	}
	
	private void decorateStateNode(TSENode tsNode, State smNode, TSENodeUI ui) {
		tsNode.setUserObject(smNode);
		tsNode.setName(smNode.getName());
		tsNode.setSize(40, 20);
		tsNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		ui.setTextAntiAliasingEnabled(true);
		tsNode.setUI((TSEObjectUI) ui.clone());		
	}

	private TSENode createComplexState(TSEGraph graph, State smNode) {
		TSENode tsNode = (TSENode) graph.addNode();
		tsNode.setTooltipText(createTooltip(smNode));

		TSEGraph childGraph = (TSEGraph) this.graphManager.addGraph();
		childGraph.setUserObject(smNode);
		tsNode.setChildGraph(childGraph);

		TSENodeUI ui = null;
		if (((StateComposite) smNode).isConcurrentState()) {
			ui = this.concurrentUI;
			tsNode.setAttribute(STATE_TYPE, STATE.CONCURRENT);
			((StateMachineLayoutManager) this.layoutManager).configureConcurrentOptions(tsNode);
		} else if (((StateComposite) smNode).isRegion()) {
			ui = this.regionGraphNodeUI;
			tsNode.setAttribute(STATE_TYPE, STATE.REGION);
			((StateMachineLayoutManager) this.layoutManager).configureCompositeRegionOptions(tsNode);
		} else {
			ui = this.compositeUI;
			tsNode.setAttribute(STATE_TYPE, STATE.COMPOSITE);
			((StateMachineLayoutManager) this.layoutManager).configureCompositeRegionOptions(tsNode);
		}
		ui.setTextAntiAliasingEnabled(true);
		this.decorateStateNode(tsNode, smNode, ui);
		TSENestingManager.expand(tsNode);

		return tsNode;
	}

	/**
	 * @param graph
	 * @param ui
	 * @param smNode
	 * @return
	 */
	private TSENode createSubState(TSEGraph graph, TSENodeUI ui, State smNode) {
		TSENode tsNode = (TSENode) graph.addNode();
		this.decorateSubNode(tsNode, smNode, ui);
		tsNode.setAttribute(STATE_TYPE, STATE.SUBSTATEMACHINE);
		ui.setTextAntiAliasingEnabled(true);
		
		TSENodeLabel nodeLabel = (TSENodeLabel) tsNode.addLabel();
		nodeLabel.setName(smNode.getName());
		((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		tsNode.setTooltipText(createTooltip(smNode));
		return tsNode;
	}
	
	/**
	 * @param tsNode
	 * @param smNode
	 * @param ui
	 */
	private void decorateSubNode(TSENode tsNode, State smNode, TSENodeUI ui) {
		tsNode.setName(smNode.getName());
		tsNode.setUserObject(smNode);
		tsNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
//		((TSENodeLabel) tsNode.addLabel()).setText(smNode.getName());
		tsNode.setSize(45, 35);
		tsNode.setUI((TSEObjectUI) ui.clone());
	}	

	/**
	 * @param graph
	 * @param ui
	 * @param smNode
	 * @return
	 */
	private TSENode createStartEndState(TSEGraph graph, TSENodeUI ui, State smNode) {
		TSENode tsNode = (TSENode) graph.addNode();
		tsNode.setName(smNode.getName());
		if(ui instanceof InitialNodeUI){
			tsNode.setAttribute(STATE_TYPE, STATE.START);
			this.startNodes.put(graph, tsNode);
		}
		else if(ui instanceof FinalStateNodeUI){
			tsNode.setAttribute(STATE_TYPE, STATE.END);
			List endList = (List) this.endNodeLists.get(graph);
			if (endList == null) {
				endList = new LinkedList();
			}
			endList.add(tsNode);
			this.endNodeLists.put(graph, endList);
		}
		ui.setTextAntiAliasingEnabled(true);
		this.decorateStartEndNode(tsNode, smNode, ui);
		tsNode.setTooltipText(createTooltip(smNode));
		return tsNode;
	}
	
	/**
	 * @param tsNode
	 * @param smNode
	 * @param ui
	 */
	private void decorateStartEndNode(TSENode tsNode, State smNode, TSENodeUI ui) {
		tsNode.setUserObject(smNode);
		tsNode.setName(smNode.getName());
		TSENodeLabel nodeLabel = (TSENodeLabel) tsNode.addLabel();
		((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		nodeLabel.setName(smNode.getName());
		tsNode.setSize(tsNode.getWidth() * 0.65, tsNode.getHeight() * 0.65);
		tsNode.setShape(TSOvalShape.getInstance());
		tsNode.setUI((TSEObjectUI) ui.clone());
		tsNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		if (nodeLabel !=  null){
			getLayoutManager().setNodeLabelOptions(nodeLabel);
		}
	}
	

	/**
	 * @param from
	 * @param to
	 * @param edgetypeUI
	 * @param transition
	 * @return
	 */
	public TSEEdge createStateTransition(TSENode from, TSENode to,	TSEEdgeUI edgetypeUI, StateTransition transition) {
		TSEEdge edge = (TSEEdge) this.graphManager.addEdge(from, to);
		
		edge.setName(transition.getName());
		edge.setUserObject(transition);
		edgetypeUI.setBendWidth(1);
		edgetypeUI.setBendHeight(1);
		edgetypeUI.setArrowHeight(3);
		edgetypeUI.setArrowWidth(3);
		edge.setUI((TSEObjectUI) edgetypeUI.clone());
		edge.discardAllLabels();
		entityEdgeList.add(edge);
		//If Transition label not available, then create a label with transition name 
		TSEEdgeLabel edgeLabel = (TSEEdgeLabel) edge.addLabel();
		TSELabelUI ui = (TSELabelUI) edgeLabel.getUI();
		ui.setTextAntiAliasingEnabled(true);
		
		if (transition.getLabel() != null) {
			edgeLabel.setName(transition.getLabel());
		}else{
			edgeLabel.setName(transition.getName());
		}
		
		return edge;
	}
	
    public void DISABLEDsetActiveTransitions(List<TSEEdge> edgeList, boolean enabled) {
    	TSEEdgeUI edgeUI;
    	
    	if (enabled) {
    		edgeUI = new AnimatedEdgeUI();
    		((AnimatedEdgeUI) edgeUI).setLineStyle(TSEPolylineEdgeUI.LINE_STYLE_SHORT_DASH);
    	}
    	else {
    		edgeUI = (TSEEdgeUI) this.edgeCurvedUI.clone();
    	}
    	
    	for (TSEEdge edge : edgeList) {
    		edge.setUI((TSEEdgeUI) edgeUI.clone());
    	}
    }	
	
//	private String createTooltip(State smNode) {
//		String tooltip;
//		// tooltip  = "<p><b>Full path: </b>" + smNode.getFolder() +
//		//	smNode.getName() + "</p>";
//		tooltip = "<p><b>Name: </b>" + smNode.getName() + "</p>";
//		tooltip += "<p><b>Description: </b>" + smNode.getDescription() + "</p>";
////		tooltip += "<p><b>Timeout: </b>" + smNode.getTimeout();
//
////		String unit = smNode.getTimeoutUnits().getLiteral();
////		tooltip += " " + unit + "</p>";		
//		return tooltip;
//	}
	
//	public void populateTSNode(TSENode tsNode, State smNode) {
//
//		if (smNode instanceof StateStart) {
//			this.decorateStartEndNode(tsNode, smNode, this.initUI);
//		}
//		else if (smNode instanceof StateEnd) {
//			this.decorateStartEndNode(tsNode, smNode, this.endUI);
//		}
//		else if (smNode instanceof StateComposite) {
//			if (smNode instanceof StateSubmachine) {
//				// substate
//				this.decorateSubNode(tsNode, smNode, this.subStateUI);
//			} else {
//				// complex
//				if (((StateComposite) smNode).isConcurrentState()) {
//					this.decorateStateNode(tsNode, smNode, this.concurrentUI);
//				} else {
//					this.decorateStateNode(tsNode, smNode, this.compositeUI);
//				}				
//			}
//		} else {
//			// simple
//			this.decorateStateNode(tsNode, smNode, this.stateUI);
//		}
//	}

	// TODO: We should create a start and an end node
	protected void createInitialModel() {
	}
	
	//TODO Logic has to be here for creating initial model , if no model available
	protected boolean isValidModelFile(){
		return true;
	}
	
	public StateMachine getStateMachine() {
		return this.stateMachine;
	}

	public void setFocus() {
		this.swtAwtComponent.setFocus();
	}

	public TSEGraph getCompositeGraph() {
		return null;
	}

	public void setCompositeGraph(TSEGraph compositeGraph) {
	}

	public TSEGraph getConcurrentGraph() {
		return null;
	}

	public void setConcurrentGraph(TSEGraph concurrentGraph) {
	}

	public LayoutManager getLayoutManager() {
		if (this.layoutManager == null) {
			this.layoutManager = new StateMachineLayoutManager(this);
		}
		return this.layoutManager;
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	public ISelection getSelection() {
		// TODO Auto-generated method stub
		return editorselection;
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
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
		if(!((StateMachineEditor)editor).isEnabled()){
			return;
		}
		super.cutGraph();
		editGraph(EDIT_TYPES.CUT, getEditor().getEditorSite(), this);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#copyGraph()
	 */
	public void copyGraph() {
		if(!((StateMachineEditor)editor).isEnabled()){
			return;
		}
		super.copyGraph();
		editGraph(EDIT_TYPES.COPY, getEditor().getEditorSite(), this);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#pasteGraph()
	 */
	public void pasteGraph() {
		if(!((StateMachineEditor)editor).isEnabled()){
			return;
		}
		super.pasteGraph();
		editGraph(EDIT_TYPES.PASTE, getEditor().getEditorSite(), this);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#delete()
	 */
	public void delete() {
		if(!((StateMachineEditor)editor).isEnabled()){
			return;
		}
		editGraph(EDIT_TYPES.DELETE, getEditor().getEditorSite(), this);
	}
	/*public CommandStack getcommandStack()
	{
	EditingDomain editingDomain=((StateMachineEditor)this.getEditor()).getEditingDomain();
	return (editingDomain.getCommandStack());
	}
	public void emfUndo() {
		
		getcommandStack().undo();
	}
	public void emfRedo() {
		getcommandStack().redo();
	}
	public boolean emfCanUndo() {
		return getcommandStack().canUndo();
	}
	public boolean emfCanRedo() {
		return getcommandStack().canRedo();
	}*/
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#validateCutEdit()
	 */
	@Override
	public boolean validateCutEdit(List<TSENode> selectedNodes , List<TSEEdge> selectedEdges ){
		if (selectedNodes.size() > 0 && selectedEdges.size() > 0) {
			setCutGraph(false);
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
			return false;
		}
		if(differentOwnerGraph(selectedNodes,selectedEdges )) {
			setCutGraph(false);
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
			return false;
		}
		for(TSENode node: selectedNodes) {
			boolean valid = validateCommonEditGraph(node);
			if(!valid) {
				setCutGraph(false);
				return valid;
			}
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#validateCopyEdit()
	 */
	@Override
	public boolean validateCopyEdit(List<TSENode> selectedNodes , List<TSEEdge> selectedEdges ) {
		if (selectedNodes.size() > 0 && selectedEdges.size() > 0) {
			setCopyGraph(false);
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
			return false;
		}
		if (differentOwnerGraph(selectedNodes, selectedEdges)) {
			setCopyGraph(false);
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
			return false;
		}
		for(TSENode node: selectedNodes) {
			boolean valid = validateCommonEditGraph(node);
			if (!valid) {
				setCopyGraph(false);
				return valid;
			}
			
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#validatePasteEdit()
	 */
	@Override
	public boolean validatePasteEdit(List<TSENode> selectedNodes , List<TSEEdge> selectedEdges ) {
		if (isCopyGraph() || (isCutGraph())) {
			return true;
		} else {
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
			return false; 
		} 
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#validateDeleteEdit()
	 */
	@Override
	public boolean validateDeleteEdit(List<TSENode> selectedNodes, List<TSEEdge> selectedEdges) {
		setCopyGraph(false);
		setCutGraph(false);
		setPasteGraph(false);
		if (allEndStateNodes() && selectedNodes.size() > 1) {
			boolean valid = validateCommonEditGraph(selectedNodes.get(0));
			if (!valid) {
				return valid;
			}
		}
		for(TSENode node: selectedNodes) {
			boolean valid = validateCommonEditGraph(node);
			if (!valid) {
				return valid;
			}
		} 
		return true;
	}
	
	private boolean allEndStateNodes() {
		for(TSENode node: selectedNodes) {
			if (!(node.getUserObject() instanceof StateEnd)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param node
	 * @return
	 */
	private boolean validateCommonEditGraph(TSENode node) {
		StateEntity entity = (StateEntity)node.getUserObject();
		if (entity instanceof StateStart) {
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
			return false;
		}else if (entity instanceof StateEnd) {
			if (!isCopyGraph()) {
				int size = getStateEndSize(((StateComposite)node.getOwnerGraph().getUserObject()).getStateEntities());
				if (size == 1 || selectedNodes.size() == size) {
					Object userObject = node.getOwnerGraph().getUserObject();
					if (userObject instanceof StateMachine) {
						openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
						return false;
					} else if (userObject instanceof StateComposite) {
						if (((StateComposite)userObject).isRegion()) {
							openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
							return false;	
						}
						
					}
				} else {
					return true;
				}
			}
		} else if (entity instanceof StateComposite) {
			if (!isCopyGraph()) {
				StateComposite composite =(StateComposite) node.getOwnerGraph().getUserObject();
				if (composite.isConcurrentState()) {
				if (((composite.getRegions().size())-selectedNodes.size()) < 2) {
						openError(getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Check for different owner Graph
	 * @return
	 */
	public boolean differentOwnerGraph(List<TSENode> sNodes , List<TSEEdge> sEdges) {
		Set<TSGraph> set = new HashSet<TSGraph>();
		if (sNodes.size() >0) {
			for (TSENode node: sNodes) {
				set.add(node.getOwnerGraph());
			}
		}
		if (sEdges.size() >0) {
			for (TSEEdge edge: sEdges) {
				set.add(edge.getOwnerGraph());
			}
		}
		if (set.size() == 1) {
			return false;
		}
		return true;
	}

	

	public void setSelection(ISelection selection) {
		editorselection = selection;
//		for (Iterator listeners = selectionChangedListeners.iterator(); listeners
//		.hasNext();) {
//		ISelectionChangedListener listener = (ISelectionChangedListener) listeners
//			.next();
//		listener
//			.selectionChanged(new SelectionChangedEvent(this, selection));
//	}
	}
	
	@Override
	public void addToEditGraphMap(Map<String, Object> map, List<TSENode> selectedNodes, List<TSEEdge> selectedEdges, boolean isCopy) {
		StateMachineUtils.addToEditGraphMap(map, selectedNodes, selectedEdges,getStateMachine(), isCopy);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#handleTransitionsEditGraphMap(java.util.Map, java.util.List, java.util.List, boolean)
	 */
	@Override
	public boolean handleTransitionsEditGraphMap() {
		Map<String, Object> map =  null;
		if (this.isCutGraph() && this.isPasteGraph()) {
			map = getCutMap();
		}
		
		if (this.isCopyGraph() && this.isPasteGraph()) {
			map = getCopyMap();
		}
		boolean isTransitionState = true; 
		for (Object object: map.values()) {
			if (!(object instanceof StateTransition)) {
				isTransitionState =  false;
				return false;
			}
		}
		if (isTransitionState) {
			for (Object obj: getEdgesClipBoard()) {
				TSEEdge tsEdge  = (TSEEdge) obj;
				StateTransition transition = getClipBoardTransitionState(tsEdge,map);
				String fromGUID = transition.getFromState().getGUID();
				String toGUID = transition.getToState().getGUID();
				TSENode from = null;
				TSENode to = null;
				for (Object object:tsEdge.getOwnerGraph().nodeSet) {
					TSENode node = (TSENode)object;
					State state = (State)node.getUserObject();
					if (state.getGUID().equals(fromGUID)) {
						from = node;
						break;
					}

				}

				for (Object object:tsEdge.getOwnerGraph().nodeSet) {
					TSENode node = (TSENode)object;
					State state = (State)node.getUserObject();
					if (state.getGUID().equals(toGUID)) {
						to = node;
						break;
					}
				}
				if (curved) {
					this.createStateTransition(from, to, edgeCurvedUI, transition);
				}
				else {
					this.createStateTransition(from, to, edgeUI, transition);

				}
				
				getStateMachine().getStateTransitions().add(transition);
			}
			
			StudioUIUtils.invokeOnDisplayThread(new Runnable() {
				public void run() {
					((StateMachineEditor)getEditor()).modified();
				}
			}, false);
			
			return true;
		}
		return false;
	}
	
	/**
	 * @param tsNode
	 * @param map
	 * @param isCopy
	 */
	private StateTransition getClipBoardTransitionState(TSEEdge tsEdge, Map<String, Object> map) {
		if (map.containsKey(tsEdge.getName().toString())) {
			StateTransition stateTransition  = (StateTransition)map.get(tsEdge.getName().toString());
			if (stateTransition != null) {
				StateTransition newTransition = (StateTransition)EcoreUtil.copy(stateTransition);
				String tag = tsEdge.getName().toString();
				String newTag = StudioUIUtils.generateUniqueName(tag);
				newTransition.setName(newTag);
				newTransition.setGUID(GUIDGenerator.getGUID());
				newTransition.setLabel(newTag);
				return newTransition;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#setActiveTransitionTool()
	 */
	public void setActiveTransitionTool() {
		DrawingCanvas canvas = getDrawingCanvas();
		TSToolManager toolManager = (TSToolManager) canvas.getToolManager();
		TSEGraphManager graphManager = getGraphManager();
		TSECreateEdgeTool createEdgeTool = TSEditingToolHelper.getCreateEdgeTool(toolManager);
		TransitionStateEdgeCreator  objectBuilder = new TransitionStateEdgeCreator();
		TSECurvedEdgeUI objectUI = new TSECurvedEdgeUI();
		graphManager.setEdgeBuilder((TSEdgeBuilder) objectBuilder);
		((TSEEdgeUI)objectUI).setAntiAliasingEnabled(true);
		graphManager.getEdgeBuilder().setEdgeUI((TSEdgeUI) objectUI);
		toolManager.setActiveTool(createEdgeTool);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getPasteTool()
	 */
	public TSEPasteTool getPasteTool() {
		if (stateMachinePasteTool == null) {
			stateMachinePasteTool = new StateMachinePasteTool(this);;
		}
		return stateMachinePasteTool;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#resetPaletteSelection()
	 */
	protected void resetPaletteSelection() {
		StudioUIUtils.resetPaletteSelection();
	}

	/**
	 * @param stateMachine
	 */
	public void setStateMachine(StateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}
	
	
	public TSENode getStartNode(TSEGraph graph) {
		return (TSENode) this.startNodes.get(graph);
	}

	public BidiMap<String, TSEObject> getGUIDGraphMap() {
		return fGUIDStateCache;
	}
	
	@Override
	public void dispose() {
		unregisterListeners();
		disposeTools();
		super.dispose();
		compositeUI = null;
		concurrentUI = null;
		regionGraphNodeUI = null;
		stateUI = null;
		subStateUI = null;
		initUI = null;
		endUI = null;
		edgeCurvedUI = null;
		edgeUI = null;
		editor = null;
		
		stateMachine = null;
		smChangeListener = null;
		smSelectionListener = null;
		
		if (layoutManager != null) {
			layoutManager = null;
		}
		if (fGUIDStateCache != null) {
			fGUIDStateCache.clear();
			fGUIDStateCache = null;
		}
		if (endNodeLists != null) {
			endNodeLists.clear();
			endNodeLists = null;
		}
		if (startNodes != null) {
			startNodes.clear();
			startNodes = null;
		}
		if (entityEdgeList != null) {
			entityEdgeList.clear();
			entityEdgeList = null;
		}
		if (tagMap != null) {
			tagMap.clear();
			tagMap = null;
		}
		if (copyMap != null) {
			copyMap.clear();
			copyMap = null;
		}
		if (cutMap != null) {
			cutMap.clear();
			cutMap = null;
		}
		diagramPrefStore = null;
	}

	@Override
	protected void disposeTools() {
		if (this.stateMachineCreateEdgeTool != null) {
			this.stateMachineCreateEdgeTool.dispose();
			this.stateMachineCreateEdgeTool = null;
		}
		if (this.stateMachineCreateNodeTool != null) {
			this.stateMachineCreateNodeTool.dispose();
			this.stateMachineCreateNodeTool = null;
		}
		if (this.stateMachinePasteTool != null) {
			this.stateMachinePasteTool.dispose();
			this.stateMachinePasteTool = null;
		}
		if (this.stateMachineReconnectEdgeTool != null) {
			this.stateMachineReconnectEdgeTool.dispose();
			this.stateMachineReconnectEdgeTool = null;
		}
		if (this.stateMachineSelectTool != null) {
			this.stateMachineSelectTool.dispose();
			this.stateMachineSelectTool = null;
		}
		
		this.stateMachineMoveSelectedTool = null;
		super.disposeTools();
	}

	@Override
	public void propertyChange(
			PropertyChangeEvent event) {
		super.propertyChange(event);
		try{
			String property=event.getProperty();
			IPreferenceStore prefstore=StateMachinePlugin.getDefault().getPreferenceStore();
			if(property.equals(STATEMACHINE_FIX_LABELS))
			{
				boolean fixLabels=prefstore.getBoolean(StudioPreferenceConstants.STATEMACHINE_FIX_LABELS);
				TSToolManager toolmanager=this.drawingCanvas.getToolManager();
				if(fixLabels){
					if (this.stateMachineMoveSelectedTool == null) {
						this.stateMachineMoveSelectedTool = new StateMachineMoveSelectedTool();
					}
					TSEditingToolHelper.registerMoveSelectedTool
					(toolmanager, stateMachineMoveSelectedTool);
				}else {
					TSEditingToolHelper.registerMoveSelectedTool(toolmanager, super.getMoveSelectedTool()) ;
				}
			}
			
//			curved = isCurved();
//			for (TSEEdge edge : entityEdgeList) {
//				if (curved) {
//					edge.setUI((TSEObjectUI) edgeCurvedUI.clone());
//				}
//				else {
//					edge.setUI((TSEObjectUI) edgeUI.clone());
//				}
//			}
			
			property = prefstore.getString(StudioPreferenceConstants.STATEMACHINE_GRID);
			if(property.equals(STATEMACHINE_NONE)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.none"), this.drawingCanvas);
			}else if(property.equals(STATEMACHINE_LINES)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.lines"), this.drawingCanvas);
			}else if(property.equals(STATEMACHINE_POINTS)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.points"), this.drawingCanvas);
			}
			DiagramUtils.refreshDiagram((StateMachineDiagramManager)((StateMachineEditor)getEditor()).getStateMachineDiagramManager());
			OverviewUtils.refreshOverview(getEditor().getEditorSite(), true, true);
		}
		catch(Exception e)
		{
			StateMachinePlugin.debug(this.getClass().getName(), e.getLocalizedMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#traversePathAttributeNodeType(com.tomsawyer.graphicaldrawing.TSENode, boolean)
	 */
	public void traversePathAttributeNodeType(TSENode node, boolean isCopy) {
		if (overlapNodesFixed) {
			tagMap.clear();
			String oldTag = node.getText();
			String newTag = StateMachineDiagramEditHandler.generateUniqueTag(node, isCopy, oldTag);//add unique tag
			tagMap.put(oldTag, newTag);
			traverseGraph(node, isCopy, oldTag, newTag);
		}
	}
	
	/**
	 * @param node
	 * @param isCopy
	 * @param oldTag
	 * @param newTag
	 */
	private void traverseGraph(TSENode node, boolean isCopy, String oldTag, String newTag) {
		StateEntity stateEntity = (StateEntity)node.getUserObject();
		String path = getStateGraphPath(stateEntity, getStateMachine(), oldTag, newTag);
		node.setAttribute(StateMachineUtils.STATE_GRAPH_PATH, path);
		if (node.getChildGraph() != null) {
			node.getChildGraph().setAttribute(StateMachineUtils.STATE_GRAPH_PATH, path);
			for (Object cnode : node.getChildGraph().nodes()) {
				traverseGraph((TSENode)cnode, isCopy, oldTag, newTag);
			}
		}
	}

	public Map<String, String> getTagMap() {
		return tagMap;
	}
	
	/**
	 * Check whether link type is curved or not
	 * @return
	 */
	private boolean isCurved() {
//		String linkType = DiagrammingPlugin.getDefault().getPreferenceStore().getString(DiagramPreferenceConstants.LINK_TYPE);
//		if (linkType.equals(DiagramPreferenceConstants.LINK_TYPE_CURVED)) {
//			return true;
//		} 
		return true;
	}
}