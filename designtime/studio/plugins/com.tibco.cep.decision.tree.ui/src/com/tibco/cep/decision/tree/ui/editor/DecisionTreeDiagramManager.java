package com.tibco.cep.decision.tree.ui.editor;

import static com.tibco.cep.diagramming.utils.DiagramUtils.gridType;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.STATEMACHINE_FIX_LABELS;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.STATEMACHINE_LINES;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.STATEMACHINE_NONE;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.STATEMACHINE_POINTS;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.editGraph;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.openError;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.be.util.BidiMap;
import com.tibco.be.util.DualHashBidiMap;
import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.action.Action;
import com.tibco.cep.decision.tree.common.model.node.action.Assignment;
import com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTableAction;
import com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction;
import com.tibco.cep.decision.tree.common.model.node.action.CallRF;
import com.tibco.cep.decision.tree.common.model.node.condition.BoolCond;
import com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond;
import com.tibco.cep.decision.tree.common.model.node.terminal.Break;
import com.tibco.cep.decision.tree.common.model.node.terminal.End;
import com.tibco.cep.decision.tree.common.model.node.terminal.Start;
import com.tibco.cep.decision.tree.ui.DecisionTreeUIPlugin;
import com.tibco.cep.decision.tree.ui.nodeactions.AbstractNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.ActionNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.AssignmentNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.BooleanConditionNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.BreakNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.CallRFNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.CallTableNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.CallTreeNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.EndNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.StartNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.SwitchConditionNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.TransitionEdgeCreator;
import com.tibco.cep.decision.tree.ui.tool.DecisionTreeCreateEdgeTool;
import com.tibco.cep.decision.tree.ui.tool.DecisionTreeCreateNodeTool;
import com.tibco.cep.decision.tree.ui.tool.DecisionTreeMoveSelectedTool;
import com.tibco.cep.decision.tree.ui.tool.DecisionTreePasteTool;
import com.tibco.cep.decision.tree.ui.tool.DecisionTreeReconnectEdgeTool;
import com.tibco.cep.decision.tree.ui.tool.DecisionTreeSelectTool;
import com.tibco.cep.decision.tree.ui.util.Messages;
import com.tibco.cep.diagramming.DiagrammingPlugin;
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
import com.tibco.cep.diagramming.ui.ChildGraphNodeUI;
import com.tibco.cep.diagramming.ui.FinalStateNodeUI;
import com.tibco.cep.diagramming.ui.InitialNodeUI;
import com.tibco.cep.diagramming.ui.PolylineEdgeUI;
import com.tibco.cep.diagramming.ui.RoundRectNodeUI;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.ui.overview.OverviewUtils;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.util.StudioConfig;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graph.TSNode;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder;
import com.tomsawyer.graphicaldrawing.ui.TSEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;
import com.tomsawyer.interactive.command.TSCommandInterface;
import com.tomsawyer.interactive.swing.TSSwingCanvasPreferenceTailor;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateEdgeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEMoveSelectedTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.tool.TSToolManager;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.util.preference.TSPreferenceData;

/*
 @author ssailapp
 @date Sep 14, 2011
 */

@SuppressWarnings("rawtypes")
public class DecisionTreeDiagramManager extends DiagramManager implements ISelectionProvider, IPropertyChangeListener {

	public static final String CLASS = DecisionTreeDiagramManager.class.getName();
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
	protected Collection<ISelectionChangedListener> selectionChangedListeners = new ArrayList<ISelectionChangedListener>();
	private Composite swtAwtComponent;
	private DecisionTree decisionTree;

	protected BidiMap<String, TSEObject> fGUIDStateCache = new DualHashBidiMap<String, TSEObject>();

	private Map<String, String> tagMap = new HashMap<String, String>();// This
																		// map
																		// for
																		// old
																		// and
																		// new
																		// tag
																		// for
																		// cut/copy/paste

	@SuppressWarnings("rawtypes")
	protected HashMap startNodes = new HashMap();
	@SuppressWarnings("rawtypes")
	protected HashMap endNodeLists = new HashMap();

	static public RoundRectNodeUI stateUI;
	private InitialNodeUI initUI;
	private FinalStateNodeUI endUI;

	private TSECurvedEdgeUI edgeCurvedUI;
	private TSEPolylineEdgeUI edgeUI;

	private IProject project;
	private EditorPart editor;

	protected DecisionTreeSelectTool decisionTreeSelectTool;
	protected DecisionTreeMoveSelectedTool decisionTreeMoveSelectedTool;
	protected DecisionTreeCreateEdgeTool decisionTreeCreateEdgeTool;
	protected DecisionTreeCreateNodeTool decisionTreeCreateNodeTool;
	protected DecisionTreeReconnectEdgeTool decisionTreeReconnectEdgeTool;

	protected String projectName;
	private IPreferenceStore prefStore;

	@SuppressWarnings("unused")
	private boolean curved = false;
	private DecisionTreeSelectionChangeListener treeSelectionListener;
	private DecisionTreeDiagramChangeListener treeDiagramListener;

	protected DecisionTreePasteTool decisionTreePasteTool;
	private TSEGraph graph;
	/* // TODO - TSV 9.2
	 private TSDecisionGraph graph;
	 	
	 */
	@SuppressWarnings("unused")
	private TSENode rootNode;

	public DecisionTreeDiagramManager(EditorPart editor) {
		this.editor = editor;
		if (editor != null) {
			if (editor.getEditorInput() instanceof DecisionTreeEditorInput) {
				this.decisionTree = ((DecisionTreeEditorInput) editor.getEditorInput()).getDecisionTree();
				this.project = ((DecisionTreeEditorInput) editor.getEditorInput()).getProject();
				this.projectName = this.project.getName();
			} else {
				this.decisionTree = ((DecisionTreeEditor) editor).getDecisionTree();
				this.projectName = decisionTree.getOwnerProject();
			}
		}	
		this.prefStore = DecisionTreeUIPlugin.getDefault().getPreferenceStore();
	}

	protected void initUI() {

		ChildGraphNodeUI.displayCompleteName = Boolean.parseBoolean(StudioConfig.getInstance().getProperty(
				"be.studio.diagram.displayCompleteExpandedNodeName", "false"));

		this.initUI = new InitialNodeUI();
		this.initUI.setBorderColor(InitialNodeUI.BORDER_COLOR);
		this.endUI = new FinalStateNodeUI();
		this.endUI.setStateType(FinalStateNodeUI.STATE_TYPE_END);

		this.edgeCurvedUI = new TSECurvedEdgeUI();
		this.edgeCurvedUI.setAntiAliasingEnabled(true);
		this.edgeCurvedUI.setCurvature(100);

		this.edgeUI = new TSEPolylineEdgeUI();
		this.edgeUI.setAntiAliasingEnabled(true);

		curved = isCurved();

		this.entityEdgeList = new LinkedList<TSEEdge>();
	}

	@Override
	protected void registerListeners() {
		super.registerListeners();
		DecisionTreeUIPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(this);
		// addPropertyChangeListener(DecisionTreeUIPlugin.getDefault().getPreferenceStore());
	}

	@Override
	public void unregisterListeners() {
		super.unregisterListeners();
		DecisionTreeUIPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(this);
	}

	public void postPopulateDrawingCanvas() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (getDrawingCanvas().getZoomLevel() > 1.0) {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							getDrawingCanvas().setZoomLevelInteractive(1.0);
						}
					});
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getSelectTool()
	 */
	public SelectTool getSelectTool() {
		if (this.decisionTreeSelectTool == null) {
			this.decisionTreeSelectTool = new DecisionTreeSelectTool(this);
		}
		return this.decisionTreeSelectTool;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.diagramming.drawing.DiagramManager#getMoveSelectedTool()
	 */
	@Override
	public TSEMoveSelectedTool getMoveSelectedTool() {
		boolean fixLabels = this.prefStore.getBoolean(StudioPreferenceConstants.STATEMACHINE_FIX_LABELS);
		if (fixLabels) {
			if (this.decisionTreeMoveSelectedTool == null) {
				this.decisionTreeMoveSelectedTool = new DecisionTreeMoveSelectedTool();
			}
			return this.decisionTreeMoveSelectedTool;
		} else {
			return super.getMoveSelectedTool();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getNodeTool()
	 */
	public CreateNodeTool getNodeTool() {
		if (this.decisionTreeCreateNodeTool == null) {
			this.decisionTreeCreateNodeTool = new DecisionTreeCreateNodeTool(this,
					((DecisionTreeEditor) editor).isEnabled());
		}
		return this.decisionTreeCreateNodeTool;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getEdgeTool()
	 */
	public CreateEdgeTool getEdgeTool() {
		if (this.decisionTreeCreateEdgeTool == null) {
			this.decisionTreeCreateEdgeTool = new DecisionTreeCreateEdgeTool(this,
					((DecisionTreeEditor) editor).isEnabled());
		}
		return this.decisionTreeCreateEdgeTool;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.diagramming.drawing.DiagramManager#getReconnectEdgeTool()
	 */
	public ReconnectEdgeTool getReconnectEdgeTool() {
		if (this.decisionTreeReconnectEdgeTool == null) {
			this.decisionTreeReconnectEdgeTool = new DecisionTreeReconnectEdgeTool(this);
		}
		return this.decisionTreeReconnectEdgeTool;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.diagramming.drawing.DiagramManager#getDiagramChangeListener
	 * ()
	 */
	@Override
	public DiagramChangeListener<? extends DiagramManager> getDiagramChangeListener() {
		if (this.treeDiagramListener == null) {
			this.treeDiagramListener = new DecisionTreeDiagramChangeListener(this);
		}
		return this.treeDiagramListener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.diagramming.drawing.DiagramManager#getDiagramSelectionListener
	 * ()
	 */
	@Override
	public SelectionChangeListener getDiagramSelectionListener() {
		if (this.treeSelectionListener == null) {
			this.treeSelectionListener = new DecisionTreeSelectionChangeListener(this);
		}
		return this.treeSelectionListener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#setPreferences()
	 */
	protected void setPreferences() {
		super.setPreferences();
		String gridType = prefStore.getString(StudioPreferenceConstants.STATEMACHINE_GRID);
		TSPreferenceData prefData = this.drawingCanvas.getPreferenceData();
		TSSwingCanvasPreferenceTailor swingTailor = new TSSwingCanvasPreferenceTailor(prefData);

		// to set grid type, configurable through state model preference page

		if (gridType.equalsIgnoreCase(StudioPreferenceConstants.STATEMACHINE_NONE)) {
			gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.none"),
					this.drawingCanvas);
		} else if (gridType.equalsIgnoreCase(StudioPreferenceConstants.STATEMACHINE_LINES)) {
			gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.lines"),
					this.drawingCanvas);
		} else if (gridType.equalsIgnoreCase(StudioPreferenceConstants.STATEMACHINE_POINTS)) {
			gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.points"),
					this.drawingCanvas);
		}

		swingTailor.setAutoHideScrollBars(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#openModel()
	 */
	public void openModel() {
		generateModel();
	}

	@SuppressWarnings("unused")
	public void validateTreeModel() {
		if (getEditor().getEditorInput() instanceof FileEditorInput) {
			IFile file = ((FileEditorInput) getEditor().getEditorInput()).getFile();
			ValidationContext vldContext = new ValidationContext(file, IResourceDelta.CHANGED,
					IncrementalProjectBuilder.FULL_BUILD);
			/*
			 * TODO ReadOnlyTreeModelValidator smValidator = new
			 * ReadOnlyTreeModelValidator(this);
			 * smValidator.validate(vldContext);
			 */
		}
	}

	public void generateModel() {
		graph = (TSEGraph) graphManager.getMainDisplayGraph();
		/* // TODO - TSV 9.2
		graph = (TSDecisionGraph) graphManager.getMainDisplayGraph();
		*/
				
		if (decisionTree == null)
			return;

		graph.setUserObject(decisionTree);
		createNodes();
		createEdges();

		// setTreeConstraints(rootNode);
		// getLayoutManager().callBatchGlobalLayout();

		setNewTreeLayout();
	}

	private void setNewTreeLayout() {
		DecisionTreeLayoutManager dTreeLayoutManager = (DecisionTreeLayoutManager) getLayoutManager();

		// dTreeLayoutManager.setHierarchicalLayoutOptions();
		// dTreeLayoutManager.setOrthogonalLayoutOptions();
		// dTreeLayoutManager.callBatchGlobalLayout();

		// dTreeLayoutManager.setDecisionOptions();

		long startTime = System.currentTimeMillis();
		dTreeLayoutManager.callGlobalLayout();
		long layoutTime = System.currentTimeMillis();
		System.out.println("Layout done in " + (layoutTime - startTime) + " msec.");

		// dTreeLayoutManager.callGlobalRouting();
		// dTreeLayoutManager.callGlobalRoutingAllEdges();
		System.out.println("Routing done in " + (System.currentTimeMillis() - layoutTime) + " msec.");
	}

	@SuppressWarnings("unused")
	private void setTreeConstraints(TSENode rootNode) {
		TSENode currentNode = rootNode;
		List<TSNode> columnList = new LinkedList<TSNode>();
		List<TSNode> rowList = new LinkedList<TSNode>();
		List<TSENode> topLevelList = new LinkedList<TSENode>();
		List<TSENode> leftMarginList = new LinkedList<TSENode>();
		topLevelList.add(currentNode);
		leftMarginList.add(currentNode);
		TSENode firstChild = null;
		TSENode secondChild = null;

		if (currentNode != null) {
			columnList.clear();
			rowList.clear();

			firstChild = null;
			secondChild = null;
			if (getDegree(currentNode) > 0) {
				firstChild = (TSENode) ((TSEEdge) outEdge1(currentNode)).getTargetNode();
				if (getDegree(currentNode) > 1) {
					secondChild = (TSENode) ((TSEEdge) outEdge2(currentNode)).getTargetNode();
				}
			}

			if (firstChild != null) {
				topLevelList.add(firstChild);
				rowList.add(currentNode);
				rowList.add(firstChild);
				((DecisionTreeLayoutManager) getLayoutManager()).addSequenceConstraint(rowList,
						TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
				setTreeConstraints(firstChild);
			}

			if (secondChild != null) {
				leftMarginList.add(secondChild);
				columnList.add(currentNode);
				columnList.add(secondChild);
				((DecisionTreeLayoutManager) getLayoutManager()).addSequenceConstraint(columnList,
						TSLayoutConstants.DIRECTION_TOP_TO_BOTTOM);
				setTreeConstraints(secondChild);
			}
		}

		((DecisionTreeLayoutManager) getLayoutManager()).addAlignmentConstraintTopAligned(topLevelList,
				TSLayoutConstants.ORIENTATION_HORIZONTAL);
		((DecisionTreeLayoutManager) getLayoutManager()).addAlignmentConstraintTopAligned(leftMarginList,
				TSLayoutConstants.ORIENTATION_VERTICAL);

		// Testing
		// graph.updateLayout();
		// LayoutManager mgr = ((DecisionTreeLayoutManager) getLayoutManager());
	}

	private void addDegree(TSENode node, TSEEdge edge) {
		TSEEdge edge1 = (TSEEdge) node.getAttributeValue("edge1");
		if (edge1 == null) {
			node.setAttribute("edge1", edge);
			return;
		}

		TSEEdge edge2 = (TSEEdge) node.getAttributeValue("edge2");
		if (edge2 == null) {
			node.setAttribute("edge2", edge);
			return;
		}
		DiagrammingPlugin.log("Exception when processing edges.");
	}

	private TSEEdge outEdge1(TSENode node) {
		return (TSEEdge) node.getAttributeValue("edge1");
	}

	private TSEEdge outEdge2(TSENode node) {
		return (TSEEdge) node.getAttributeValue("edge2");
	}

	private int getDegree(TSENode node) {
		int degree = 0;
		TSEEdge edge1 = (TSEEdge) node.getAttributeValue("edge1");
		if (edge1 != null) {
			degree++;
		}

		TSEEdge edge2 = (TSEEdge) node.getAttributeValue("edge2");
		if (edge2 != null) {
			degree++;
		}
		return degree;
	}

	public EditorPart getEditor() {
		return editor;
	}

	private IEditorInput getEditorInput() {
		if (editor != null) {
			return editor.getEditorInput();
		}
		return null;
	}

	private IFile getEditorFile() {
		if (getEditorInput() instanceof FileEditorInput) {
			return ((FileEditorInput) getEditorInput()).getFile();
		}
		return null;
	}

	public void save() {
		try {
			IFile file = getEditorFile();
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("dtree", new XMIResourceFactoryImpl());

			ResourceSet resourceSet = new ResourceSetImpl();
			URI fileURI = URI.createFileURI(new File(file.getLocation().toString()).getAbsolutePath());
			Resource resource = resourceSet.createResource(fileURI);

			// Resource resource =
			// resSet.createResource(URI.createURI(file.getLocation().toString()));
			resource.getContents().add(decisionTree);

			for (NodeElement node : decisionTree.getNodes()) {
				resource.getContents().add(node);
			}

			for (Edge edge : decisionTree.getEdges()) {
				resource.getContents().add(edge);
			}

			try {
				resource.save(Collections.EMPTY_MAP);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// InputStream is =
			// CommonIndexUtils.getEObjectInputStream(decisionTree);
			// ByteArrayInputStream is = new
			// ByteArrayInputStream(contents.getBytes(ModelUtils.DEFAULT_ENCODING));
			// file.setContents(is, true, true, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createNodes() {
		Start startElement = (Start) decisionTree.getStartNode();
		StartNodeCreator startNodeCreator = new StartNodeCreator(layoutManager, startElement);
		TSENode node = startNodeCreator.addNode(graph);
		node.setID(0);
		rootNode = node;

		if (decisionTree.getNodes() != null) {
			for (NodeElement nodeElement : decisionTree.getNodes()) {
				node = null;
				AbstractNodeCreator nodeCreator = null;
				if (nodeElement instanceof BoolCond) {
					nodeCreator = new BooleanConditionNodeCreator(layoutManager, nodeElement);
				} else if (nodeElement instanceof SwitchCond) {
					nodeCreator = new SwitchConditionNodeCreator(layoutManager, nodeElement);
				} else if (nodeElement instanceof Action) {
					nodeCreator = new ActionNodeCreator(layoutManager, nodeElement);
				} else if (nodeElement instanceof Assignment) {
					nodeCreator = new AssignmentNodeCreator(layoutManager, nodeElement);
				} else if (nodeElement instanceof CallDecisionTableAction) {
					nodeCreator = new CallTableNodeCreator(layoutManager, nodeElement);
				} else if (nodeElement instanceof CallDecisionTreeAction) {
					nodeCreator = new CallTreeNodeCreator(layoutManager, nodeElement);
				} else if (nodeElement instanceof CallRF) {
					nodeCreator = new CallRFNodeCreator(layoutManager, nodeElement);
				} else if (nodeElement instanceof Start) {
					if (nodeElement.getId() != 0) {
						nodeCreator = new StartNodeCreator(layoutManager, nodeElement);
					}
				} else if (nodeElement instanceof Break) {
					nodeCreator = new BreakNodeCreator(layoutManager, nodeElement);
				} else if (nodeElement instanceof End) {
					nodeCreator = new EndNodeCreator(layoutManager, nodeElement);
				}
				if (nodeCreator != null) {
					node = nodeCreator.addNode(graph);
					if (node != null) {
						node.setID(Long.valueOf(nodeElement.getId()));
					}
				}
			}
		}

		/*
		 * TSEAddNodeCommand command = new
		 * TSEAddNodeCommand(graph,point.getX(),point.getY()); TSCommandManager
		 * commandManager = getSwingCanvas().getCommandManager();
		 * commandManager.transmit(command); return command.getNode();
		 */
	}

	private void createEdges() {
		if (decisionTree.getEdges() != null) {
			for (Edge edgeElement : decisionTree.getEdges()) {
				NodeElement srcNodeElement = edgeElement.getSrc();
				NodeElement tgtNodeElement = edgeElement.getTgt();
				if (srcNodeElement == null || tgtNodeElement == null)
					continue;
				TSENode srcNode = getNode(srcNodeElement.getId());
				TSENode tgtNode = getNode(tgtNodeElement.getId());

				// if (srcNode == null || tgtNode == null)
				// continue;
				TSEConnector srcConnector, tgtConnector;
				if (edgeElement.getName() == null || edgeElement.getName().equals("yes")) {
					srcConnector = (TSEConnector) srcNode.addConnector();
					srcConnector.setProportionalXOffset(0.5);
					srcConnector.setProportionalYOffset(0);

					tgtConnector = (TSEConnector) tgtNode.addConnector();
					tgtConnector.setProportionalXOffset(-0.5);
					tgtConnector.setProportionalYOffset(0);
				} else {
					srcConnector = (TSEConnector) srcNode.addConnector();
					srcConnector.setProportionalXOffset(0);
					srcConnector.setProportionalYOffset(-0.5);

					tgtConnector = (TSEConnector) tgtNode.addConnector();
					tgtConnector.setProportionalXOffset(0);
					tgtConnector.setProportionalYOffset(0.5);
				}
				createTreeEdge(srcNode, srcConnector, tgtNode, tgtConnector, edgeElement);
			}
		}
	}

	private TSENode getNode(long id) {
		TSENode node = null;
		for (Object obj : graph.nodes()) {
			node = (TSENode) obj;
			if (node.getID() == id)
				return node;
		}
		return node;
	}

	private TSEEdge createTreeEdge(TSENode srcNode, TSEConnector srcConnector, TSENode tgtNode,
			TSEConnector tgtConnector, Edge edgeElement) {
		TSEEdge edge = (TSEEdge) graphManager.addEdge(srcNode, tgtNode);
		edge.setSourceConnector(srcConnector);
		edge.setTargetConnector(tgtConnector);

		edge.setUserObject(edgeElement);

		String edgeName = edgeElement.getName();
		PolylineEdgeUI edgeUI = new PolylineEdgeUI();
		if (edgeName.isEmpty() || edgeName.equals("yes"))
			edgeUI.setLineColor(new TSEColor(0, 64, 0));
		else
			edgeUI.setLineColor(TSEColor.red);
		edge.setUI(edgeUI);

		// edge.setUserObject(null);
		/*
		 * TSEEdgeLabel edgeLabel = (TSEEdgeLabel) edge.addLabel();
		 * edgeLabel.setName(edgeName);
		 * 
		 * if (condition.getExpr().length() > LABEL_LENGTH_THRESHOLD) {
		 * edgeLabel.setName(condition.getExpr().substring(0,
		 * LABEL_LENGTH_THRESHOLD).concat("...")); } else {
		 * edgeLabel.setName(condition.getExpr()); }
		 * 
		 * if (comment != null) { edgeLabel.setTooltipText(comment); }
		 * 
		 * TSETextLabelUI labelUI = new TSETextLabelUI();
		 * labelUI.setBorderDrawn(true);
		 * //labelUI.setBorderColor(LABEL_BORDER_COLOR);
		 * //labelUI.setFillColor(LABEL_FILL_COLOR);
		 * labelUI.setTransparent(false);
		 * labelUI.setTextAntiAliasingEnabled(true); edgeLabel.setUI(labelUI);
		 */

		/*
		 * GlowSelectedEdgeUI edgeUI = new GlowSelectedEdgeUI();
		 * edgeUI.setAntiAliasingEnabled(true); edge.setUI(edgeUI);
		 */

		/*
		 * if (manager.isMergeConditionLinks()) {
		 * edge.setSourceConnector((TSEConnector)srcNode.connectors().get(0)); }
		 * 
		 * lastNode = tgtNode; if (firstTime) { rootNode = srcNode; firstTime =
		 * false; }
		 * 
		 * actions++;
		 */
		addDegree(srcNode, edge);
		return edge;
	}

	protected void createInitialModel() {
	}

	// TODO Logic has to be here for creating initial model , if no model
	// available
	protected boolean isValidModelFile() {
		return true;
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

	public LayoutManager getLayoutManager() {
		if (this.layoutManager == null) {
			this.layoutManager = new DecisionTreeLayoutManager(this);
		}
		return this.layoutManager;
	}

	@SuppressWarnings("unchecked")
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#cutGraph()
	 */
	public void cutGraph() {
		if (!((DecisionTreeEditor) editor).isEnabled()) {
			return;
		}
		super.cutGraph();
		editGraph(EDIT_TYPES.CUT, getEditor().getEditorSite(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#copyGraph()
	 */
	public void copyGraph() {
		if (!((DecisionTreeEditor) editor).isEnabled()) {
			return;
		}
		super.copyGraph();
		editGraph(EDIT_TYPES.COPY, getEditor().getEditorSite(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#pasteGraph()
	 */
	public void pasteGraph() {
		if (!((DecisionTreeEditor) editor).isEnabled()) {
			return;
		}
		super.pasteGraph();
		editGraph(EDIT_TYPES.PASTE, getEditor().getEditorSite(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#delete()
	 */
	public void delete() {
		if (!((DecisionTreeEditor) editor).isEnabled()) {
			return;
		}
		editGraph(EDIT_TYPES.DELETE, getEditor().getEditorSite(), this);
	}

	/*
	 * public CommandStack getcommandStack() { EditingDomain
	 * editingDomain=((DecisionTreeEditor)this.getEditor()).getEditingDomain();
	 * return (editingDomain.getCommandStack()); } public void emfUndo() {
	 * 
	 * getcommandStack().undo(); } public void emfRedo() {
	 * getcommandStack().redo(); } public boolean emfCanUndo() { return
	 * getcommandStack().canUndo(); } public boolean emfCanRedo() { return
	 * getcommandStack().canRedo(); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#validateCutEdit()
	 */
	@Override
	public boolean validateCutEdit(List<TSENode> selectedNodes, List<TSEEdge> selectedEdges) {
		if (selectedNodes.size() > 0 && selectedEdges.size() > 0) {
			setCutGraph(false);
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"),
					Messages.getString("error_message"));
			return false;
		}
		if (differentOwnerGraph(selectedNodes, selectedEdges)) {
			setCutGraph(false);
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"),
					Messages.getString("error_message"));
			return false;
		}
		for (TSENode node : selectedNodes) {
			boolean valid = validateCommonEditGraph(node);
			if (!valid) {
				setCutGraph(false);
				return valid;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#validateCopyEdit()
	 */
	@Override
	public boolean validateCopyEdit(List<TSENode> selectedNodes, List<TSEEdge> selectedEdges) {
		if (selectedNodes.size() > 0 && selectedEdges.size() > 0) {
			setCopyGraph(false);
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"),
					Messages.getString("error_message"));
			return false;
		}
		if (differentOwnerGraph(selectedNodes, selectedEdges)) {
			setCopyGraph(false);
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"),
					Messages.getString("error_message"));
			return false;
		}
		for (TSENode node : selectedNodes) {
			boolean valid = validateCommonEditGraph(node);
			if (!valid) {
				setCopyGraph(false);
				return valid;
			}

		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#validatePasteEdit()
	 */
	@Override
	public boolean validatePasteEdit(List<TSENode> selectedNodes, List<TSEEdge> selectedEdges) {
		if (isCopyGraph() || (isCutGraph())) {
			return true;
		} else {
			openError(getEditor().getSite().getShell(), Messages.getString("error_title"),
					Messages.getString("error_message"));
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.diagramming.drawing.DiagramManager#validateDeleteEdit()
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
		for (TSENode node : selectedNodes) {
			boolean valid = validateCommonEditGraph(node);
			if (!valid) {
				return valid;
			}
		}
		return true;
	}

	@SuppressWarnings("unused")
	private boolean allEndStateNodes() {
		/*for (TSENode node : selectedNodes) {
			
			 * if (!(node.getUserObject() instanceof StateEnd)) { return false;
			 * }
			 
		}
		*/return true;
	}

	/**
	 * @param node
	 * @return
	 */
	private boolean validateCommonEditGraph(TSENode node) {
		/*
		 * TODO - StateEntity entity = (StateEntity)node.getUserObject(); if
		 * (entity instanceof StateStart) {
		 * openError(getEditor().getSite().getShell(),
		 * Messages.getString("error_title"),
		 * Messages.getString("error_message")); return false; }else if (entity
		 * instanceof StateEnd) { if (!isCopyGraph()) { int size =
		 * getStateEndSize
		 * (((StateComposite)node.getOwnerGraph().getUserObject())
		 * .getStateEntities()); if (size == 1 || selectedNodes.size() == size)
		 * { Object userObject = node.getOwnerGraph().getUserObject(); if
		 * (userObject instanceof DecisionTree) {
		 * openError(getEditor().getSite().getShell(),
		 * Messages.getString("error_title"),
		 * Messages.getString("error_message")); return false; } else if
		 * (userObject instanceof StateComposite) { if
		 * (((StateComposite)userObject).isRegion()) {
		 * openError(getEditor().getSite().getShell(),
		 * Messages.getString("error_title"),
		 * Messages.getString("error_message")); return false; }
		 * 
		 * } } else { return true; } } } else if (entity instanceof
		 * StateComposite) { if (!isCopyGraph()) { StateComposite composite
		 * =(StateComposite) node.getOwnerGraph().getUserObject(); if
		 * (composite.isConcurrentState()) { if
		 * (((composite.getRegions().size())-selectedNodes.size()) < 2) {
		 * openError(getEditor().getSite().getShell(),
		 * Messages.getString("error_title"),
		 * Messages.getString("error_message")); return false; } } } }
		 */
		return true;
	}

	/**
	 * Check for different owner Graph
	 * 
	 * @return
	 */
	public boolean differentOwnerGraph(List<TSENode> sNodes, List<TSEEdge> sEdges) {
		Set<TSGraph> set = new HashSet<TSGraph>();
		if (sNodes.size() > 0) {
			for (TSENode node : sNodes) {
				set.add(node.getOwnerGraph());
			}
		}
		if (sEdges.size() > 0) {
			for (TSEEdge edge : sEdges) {
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
		// for (Iterator listeners = selectionChangedListeners.iterator();
		// listeners
		// .hasNext();) {
		// ISelectionChangedListener listener = (ISelectionChangedListener)
		// listeners
		// .next();
		// listener
		// .selectionChanged(new SelectionChangedEvent(this, selection));
		// }
	}

	@Override
	public void addToEditGraphMap(Map<String, Object> map, List<TSENode> selectedNodes, List<TSEEdge> selectedEdges,
			boolean isCopy) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#
	 * handleTransitionsEditGraphMap(java.util.Map, java.util.List,
	 * java.util.List, boolean)
	 */
	@SuppressWarnings("unused")
	@Override
	public boolean handleTransitionsEditGraphMap() {
		Map<String, Object> map = null;
		if (this.isCutGraph() && this.isPasteGraph()) {
			map = getCutMap();
		}

		if (this.isCopyGraph() && this.isPasteGraph()) {
			map = getCopyMap();
		}
		//boolean isTransitionState = true;
		/*
		 * for (Object object: map.values()) { if (!(object instanceof
		 * StateTransition)) { isTransitionState = false; return false; } } if
		 * (isTransitionState) { for (Object obj: getEdgesClipBoard()) { TSEEdge
		 * tsEdge = (TSEEdge) obj; StateTransition transition =
		 * getClipBoardTransitionState(tsEdge,map); String fromGUID =
		 * transition.getFromState().getGUID(); String toGUID =
		 * transition.getToState().getGUID(); TSENode from = null; TSENode to =
		 * null; for (Object object:tsEdge.getOwnerGraph().nodeSet) { TSENode
		 * node = (TSENode)object; State state = (State)node.getUserObject(); if
		 * (state.getGUID().equals(fromGUID)) { from = node; break; }
		 * 
		 * }
		 * 
		 * for (Object object:tsEdge.getOwnerGraph().nodeSet) { TSENode node =
		 * (TSENode)object; State state = (State)node.getUserObject(); if
		 * (state.getGUID().equals(toGUID)) { to = node; break; } } if (curved)
		 * { this.createStateTransition(from, to, edgeCurvedUI, transition); }
		 * else { this.createStateTransition(from, to, edgeUI, transition);
		 * 
		 * }
		 * 
		 * //TODO getDecisionTree().getStateTransitions().add(transition); }
		 * 
		 * StudioUIUtils.invokeOnDisplayThread(new Runnable() { public void
		 * run() { ((DecisionTreeEditor)getEditor()).modified(); } }, false);
		 * 
		 * return true; }
		 */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.diagramming.drawing.DiagramManager#setActiveTransitionTool
	 * ()
	 */
	public void setActiveTransitionTool() {
		DrawingCanvas canvas = getDrawingCanvas();
		TSToolManager toolManager = (TSToolManager) canvas.getToolManager();
		TSEGraphManager graphManager = getGraphManager();
		TSECreateEdgeTool createEdgeTool = TSEditingToolHelper.getCreateEdgeTool(toolManager);
		TransitionEdgeCreator objectBuilder = new TransitionEdgeCreator();
		TSECurvedEdgeUI objectUI = new TSECurvedEdgeUI();
		graphManager.setEdgeBuilder((TSEdgeBuilder) objectBuilder);
		((TSEEdgeUI) objectUI).setAntiAliasingEnabled(true);
		graphManager.getEdgeBuilder().setEdgeUI((TSEdgeUI) objectUI);
		toolManager.setActiveTool(createEdgeTool);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getPasteTool()
	 */
	public TSEPasteTool getPasteTool() {
		if (decisionTreePasteTool == null) {
			decisionTreePasteTool = new DecisionTreePasteTool(this);
			;
		}
		return decisionTreePasteTool;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.diagramming.drawing.DiagramManager#resetPaletteSelection()
	 */
	protected void resetPaletteSelection() {
		StudioUIUtils.resetPaletteSelection();
	}

	/*
	 * TODO public void setDecisionTree(DecisionTree decisionTree) {
	 * this.decisionTree = decisionTree; }
	 */

	public TSENode getStartNode(TSEGraph graph) {
		return (TSENode) this.startNodes.get(graph);
	}

	public BidiMap<String, TSEObject> getGUIDGraphMap() {
		return fGUIDStateCache;
	}

	public TSEGraphManager createGraphManager() {
		return new TSEGraphManager();
		/* // TODO - TSV 9.2
		return new TSDecisionGraphManager();
		*/
	}

	@Override
	public void dispose() {
		unregisterListeners();
		disposeTools();
		super.dispose();
		stateUI = null;
		initUI = null;
		endUI = null;
		edgeCurvedUI = null;
		edgeUI = null;
		editor = null;

		decisionTree = null;
		treeDiagramListener = null;
		treeSelectionListener = null;

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
		if (this.decisionTreeCreateEdgeTool != null) {
			this.decisionTreeCreateEdgeTool.dispose();
			this.decisionTreeCreateEdgeTool = null;
		}
		if (this.decisionTreeCreateNodeTool != null) {
			this.decisionTreeCreateNodeTool.dispose();
			this.decisionTreeCreateNodeTool = null;
		}
		if (this.decisionTreePasteTool != null) {
			this.decisionTreePasteTool.dispose();
			this.decisionTreePasteTool = null;
		}
		if (this.decisionTreeReconnectEdgeTool != null) {
			this.decisionTreeReconnectEdgeTool.dispose();
			this.decisionTreeReconnectEdgeTool = null;
		}
		if (this.decisionTreeSelectTool != null) {
			this.decisionTreeSelectTool.dispose();
			this.decisionTreeSelectTool = null;
		}

		this.decisionTreeMoveSelectedTool = null;
		super.disposeTools();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		try {
			String property = event.getProperty();
			IPreferenceStore prefstore = DecisionTreeUIPlugin.getDefault().getPreferenceStore();
			if (property.equals(STATEMACHINE_FIX_LABELS)) {
				boolean fixLabels = prefstore.getBoolean(StudioPreferenceConstants.STATEMACHINE_FIX_LABELS);
				TSToolManager toolmanager = this.drawingCanvas.getToolManager();
				if (fixLabels) {
					if (this.decisionTreeMoveSelectedTool == null) {
						this.decisionTreeMoveSelectedTool = new DecisionTreeMoveSelectedTool();
					}
					TSEditingToolHelper.registerMoveSelectedTool(toolmanager, decisionTreeMoveSelectedTool);
				} else {
					TSEditingToolHelper.registerMoveSelectedTool(toolmanager, super.getMoveSelectedTool());
				}
			}

			// curved = isCurved();
			// for (TSEEdge edge : entityEdgeList) {
			// if (curved) {
			// edge.setUI((TSEObjectUI) edgeCurvedUI.clone());
			// }
			// else {
			// edge.setUI((TSEObjectUI) edgeUI.clone());
			// }
			// }

			property = prefstore.getString(StudioPreferenceConstants.STATEMACHINE_GRID);
			if (property.equals(STATEMACHINE_NONE)) {
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.none"),
						this.drawingCanvas);
			} else if (property.equals(STATEMACHINE_LINES)) {
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.lines"),
						this.drawingCanvas);
			} else if (property.equals(STATEMACHINE_POINTS)) {
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.points"),
						this.drawingCanvas);
			}
			DiagramUtils.refreshDiagram((DecisionTreeDiagramManager) ((DecisionTreeEditor) getEditor())
					.getDecisionTreeDiagramManager());
			OverviewUtils.refreshOverview(getEditor().getEditorSite(), true, true);
		} catch (Exception e) {
			DecisionTreeUIPlugin.debug(this.getClass().getName(), e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#
	 * traversePathAttributeNodeType(com.tomsawyer.graphicaldrawing.TSENode,
	 * boolean)
	 */
	public void traversePathAttributeNodeType(TSENode node, boolean isCopy) {
		/*
		 * TODO if (overlapNodesFixed) { tagMap.clear(); String oldTag =
		 * node.getText(); String newTag =
		 * DecisionTreeDiagramEditHandler.generateUniqueTag(node, isCopy,
		 * oldTag);//add unique tag tagMap.put(oldTag, newTag);
		 * traverseGraph(node, isCopy, oldTag, newTag); }
		 */
	}

	public DecisionTree getDecisionTree() {
		return decisionTree;
	}

	public Map<String, String> getTagMap() {
		return tagMap;
	}

	/**
	 * Check whether link type is curved or not
	 * 
	 * @return
	 */
	private boolean isCurved() {
		// String linkType =
		// DiagrammingPlugin.getDefault().getPreferenceStore().getString(DiagramPreferenceConstants.LINK_TYPE);
		// if (linkType.equals(DiagramPreferenceConstants.LINK_TYPE_CURVED)) {
		// return true;
		// }
		return true;
	}

}
