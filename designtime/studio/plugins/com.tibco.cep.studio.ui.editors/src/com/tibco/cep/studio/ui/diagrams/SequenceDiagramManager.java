package com.tibco.cep.studio.ui.diagrams;

import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.openError;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.diagramming.drawing.EntityLayoutManager;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.ui.RoundRectNodeUI;
import com.tibco.cep.diagramming.ui.SimpleRectNodeUI;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.StudioModelManager;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.diagrams.tools.SequenceSelectTool;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.sequence.SequenceDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tibco.cep.studio.util.StudioConfig;
import com.tomsawyer.drawing.geometry.shared.TSConstSize;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.complexity.TSEHidingManager;
import com.tomsawyer.graphicaldrawing.grid.TSGridPreferenceTailor;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.util.preference.TSPreferenceData;
/**
 * 
 * @author ggrigore
 *
 */
public class SequenceDiagramManager extends EntityDiagramManager<Entity> {

	private DesignerProject studioProject;
	private RuleElement rule;
	private EntityElement event;
	private HashMap<RuleElement, TSENode> ruleElements;
	private HashMap<JavaStaticFunction, TSENode> functionElements;
	protected SequenceSelectTool sequenceSelectTool;
	private List<TSEEdge> rfToModEntitiesEdgeList;
	
	// TODO: preference!
	private boolean showCatalogFunctions;
	private boolean showExpandedNames;
	private boolean showCatalogFuncReturnLinks = false;
	
	public final static TSEColor RULE_GROUP_COLOR = new TSEColor(196, 196, 196);
	public final static TSEColor RF_GROUP_COLOR = new TSEColor(214, 205, 160);

	public final static TSEColor START_RULE_COLUMN_COLOR = new TSEColor(68,152,255);
	public final static TSEColor oEND_COLUMN_COLOR = new TSEColor(224,251,255);
	public final static TSEColor oSTART_COLUMN_COLOR = new TSEColor(255, 220, 81);
	public final static TSEColor END_RULE_COLUMN_COLOR = new TSEColor(224,251,255);

	public final static TSEColor START_CATFUNC_COLUMN_COLOR = new TSEColor(255, 220, 81);
	public final static TSEColor END_CATFUNC_COLUMN_COLOR = new TSEColor(255, 255, 255);	
	
	public final static TSEColor THIN_NODE_COLOR = new TSEColor(224,251,255);
	public final static TSEColor VERTICAL_LINE_COLOR = new TSEColor(162, 176, 181);

	public final static TSConstSize THIN_NODE_SIZE = new TSConstSize(6, 80);
	public final static TSConstSize DUMMY_NODE_SIZE = new TSConstSize(5, 5);
	private final static int DEFAULT_NR_OF_EDGES = 6;
	private final static int EDGE_VERTICAL_SPACING = 16;
	
	private List<TSENode> elementNodes;
	private List<TSENode> thinNodes;
	private List<TSENode> dummyNodes;
	private HashMap<TSENode, LeftRightList> thinNodeEdgeMap;

	private static final int COLUMN_LINK = 0;
	private static final int FORWARD_LINK = 1;
	private static final int RETURN_LINK = 2;
	
	// TODO: preference:
	private static final int RULE_DEPTH = 1; // TSFindChildParent.FIND_DEPTH_ALL;
	
	private DependencyDiagramManager dependencyManager;	
	
	// this is the constructor I'm using...
	public SequenceDiagramManager(EditorPart editor) {
		super(editor);
		TSEImage.setLoaderClass(this.getClass());
		this.localInit();
		this.dependencyManager = new DependencyDiagramManager(this.editor);
	}

	public SequenceDiagramManager(IProject project, IProgressMonitor monitor) {
		super(project, monitor);
		TSEImage.setLoaderClass(this.getClass());
		this.localInit();
	}
	
//	protected void setPreferences() {
//		super.setPreferences();
//
//		TSPreferenceData prefData = this.drawingCanvas.getPreferenceData();
//		TSGridPreferenceTailor gridTailor = new TSGridPreferenceTailor(prefData);
//		TSSwingCanvasPreferenceTailor swingTailor = new TSSwingCanvasPreferenceTailor(prefData);
//		gridTailor.setLineGrid();
//		gridTailor.setSpacingX(25);
//		gridTailor.setSpacingY(25);
//		swingTailor.setAutoHideScrollBars(false);
//
//		// TODO: set the booleans above from preference store...
//	}

	private void localInit() {
		this.elementNodes = new LinkedList<TSENode>();
		this.thinNodes = new LinkedList<TSENode>();
		this.dummyNodes = new LinkedList<TSENode>();
		this.ruleElements = new HashMap<RuleElement, TSENode>();
		this.functionElements = new HashMap<JavaStaticFunction, TSENode>();
		this.thinNodeEdgeMap = new HashMap<TSENode, LeftRightList>();
		this.rfToModEntitiesEdgeList = new LinkedList<TSEEdge>();
		
		final IPreferenceStore prefStore = EditorsUIPlugin.getDefault().getPreferenceStore();
		this.showCatalogFunctions = prefStore.getBoolean(
			StudioPreferenceConstants.SEQUENCE_SHOW_CATALOG_FUNCTIONS);
		this.showExpandedNames = prefStore.getBoolean(
			StudioPreferenceConstants.SEQUENCE_SHOW_EXPANDED_NAMES);
		this.showCatalogFuncReturnLinks = prefStore.getBoolean(
			StudioPreferenceConstants.SEQUENCE_SHOW_CATFUNC_RETURNLINKS);
		
		this.initNodeUIs();
	}

	private void initNodeUIs() {
		// this.RULE_IMAGE = new TSEImage(this.getClass(),	"/icons/rules_48x48.png");
		StudioModelManager mgr = StudioCorePlugin.getDesignerModelManager();
		this.studioProject = mgr.getIndex(this.getProject());
	}

	// NOT needed because we do our own processing in populateTSNode
	public TSEColor getStartColor() {
		return null;
	}

	// NOT needed because we do our own processing in populateTSNode
	public TSEColor getEndColor() {
		return null;
	}

	// NOT needed because we do our own processing in populateTSNode
	protected TSEImage getEntityImage() {
		return null;
	}

	// NOT needed because we do our own processing
	public void populateTSNode(TSENode node, Entity entity) {
	}
	
	protected void setPreferences() {
		super.setPreferences();
		TSPreferenceData prefData = this.drawingCanvas.getPreferenceData();
		TSGridPreferenceTailor gridTailor = new TSGridPreferenceTailor(prefData);
		gridTailor.setNoGrid();
	}

	// For now we generate the sequence diagrams all the time, thus calling generateModel
	public void openModel() {
		long startTime = System.currentTimeMillis();
		this.generateModel();
		long endTime = System.currentTimeMillis();
		
		System.out.println("Generated diagram with "
				+ this.graphManager.numberOfNodes() + " nodes and "
				+ this.graphManager.numberOfEdges() + " edges in "
				+ (endTime - startTime) / 1000 + " seconds.");
//		System.out.println("Executing diagram layout on "
//				+ this.elementNodes.size()*3 + " sequence nodes, " +
//				+ this.graphManager.numberOfEdges() + " edges) ");
		
		int nrEdges = 3000;
		if (StudioConfig.getInstance().getProperty("be.studio.project.diagram.nrEdges", "3000") != null) {
			nrEdges = Integer.parseInt(
				StudioConfig.getInstance().getProperty("be.studio.project.diagram.nrEdges", "3000"));
		}
		if (this.graphManager.numberOfEdges()> nrEdges) {
			System.out.println("Hiding links due to large number of edges (" +
				this.graphManager.numberOfEdges() + ") greater than " + nrEdges + ".");
		
			
			List edgesToHideDueToLargeSize = null;
			if (this.rfToModEntitiesEdgeList.size() > nrEdges) {
				edgesToHideDueToLargeSize = this.rfToModEntitiesEdgeList;
			}
			else {
				edgesToHideDueToLargeSize = this.graphManager.getMainDisplayGraph().edges();
			}
			TSEHidingManager.getManager(this.graphManager).hide(null, edgesToHideDueToLargeSize);
		}
		
		this.getLayoutManager().callBatchGlobalLayout();
		this.layoutAndRefresh();
		if(this.rfToModEntitiesEdgeList.size() > nrEdges) {
		
	openError(getEditor().getSite().getShell(), "Warning Message"
							, "As the number of edges in this diagram exceeds the set limit (" + nrEdges + 
							"), the usage links are hidden. The current displayed edges are " + this.graphManager.numberOfEdges() + 
							". To modify the default number of edges, " +
							"set be.studio.project.diagram.nrEdges in studio.tra."
							 );
		}
		startTime = System.currentTimeMillis();
		System.out.println("Sequence diagram layout took: " + 
			(startTime - endTime) / 1000 + " seconds.");	
//		this.getLayoutManager().callBatchGlobalLayout();
	}

	// we always generate and never save sequence diagrams
	public void save() { }
	
	protected String getEntityTypeName() {
		return "rule";
	}

	public String getExtension() {
		return Messages.getString("RULE_extension");
	}
	
	@Override
	public ELEMENT_TYPES getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ELEMENT_TYPES[] getEntityTypes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// NOT needed because we do our own diagram creation...
	protected TSENode createEntity(Entity entity) {
		return null;
	}
	
	public void generateModel() {
		if (super.getEditor() == null) {
			return;
		}

		IFile file = ((SequenceDiagramEditorInput) editor.getEditorInput()).getFile();
		DesignerElement element = IndexUtils.getElement(file);
		if (element instanceof RuleElement) {
			this.rule = (RuleElement) element;
		} else if (element instanceof EntityElement && ((EntityElement)element).getEntity() instanceof Event) {
			this.event = (EntityElement)element;
		}
		
		if (this.studioProject == null) {
			System.out.println("Cannot find project.");
			return;
		}
		
		if (this.rule == null && this.event == null ) {
			System.out.println("Cannot find entity.");
			return;
		}
		
		if (this.rule != null) {
			this.generateRuleSequence();
		}
		else if (this.event != null) {
			this.generateEventSequence();
		}
	}
	
//	////////////////// Event Sequence related ////////////////////////////////
	
	private void generateEventSequence() {
		this.dependencyManager.initialize(this.graphManager, this.drawingCanvas);
		this.dependencyManager.setLayoutManager(this.layoutManager);
		this.dependencyManager.generateModel();
		this.dependencyManager.generateRules();
		//this.dependencyManager.generateArchives();		
		
		TSENode eventNode = (TSENode) dependencyManager.getNodeMap().get(this.event.getEntity().getGUID());
		if (eventNode == null) {
			System.out.println("Cannot find event in node map.");
			return;
		}
		
		// this.createHeaderNodes();
		TSENode sourceHeaderNode = this.createEventElementNode("Source");
		TSENode destHeaderNode = this.createEventElementNode("Destinations");
		TSENode eventHeaderNode = this.createEventElementNode("Event");
		TSENode ruleHeaderNode = this.createEventElementNode("Rules/Rule Functions");
		TSENode modEntityHeaderNode = this.createEventElementNode("Modified Entities");
		this.elementNodes.add(modEntityHeaderNode);
		this.elementNodes.add(ruleHeaderNode);
		this.elementNodes.add(eventHeaderNode);
		this.elementNodes.add(destHeaderNode);
		this.elementNodes.add(sourceHeaderNode);
		
		// this.createDummyNodes();
		TSENode sourceDummyNode = this.createSequenceNode(DUMMY_NODE_SIZE, END_RULE_COLUMN_COLOR);
		TSENode destDummyNode = this.createSequenceNode(DUMMY_NODE_SIZE, END_RULE_COLUMN_COLOR);
		TSENode eventDummyNode = this.createSequenceNode(DUMMY_NODE_SIZE, END_RULE_COLUMN_COLOR);
		TSENode ruleDummyNode = this.createSequenceNode(DUMMY_NODE_SIZE, END_RULE_COLUMN_COLOR);
		TSENode modEntityDummyNode = this.createSequenceNode(DUMMY_NODE_SIZE, END_RULE_COLUMN_COLOR);
		this.dummyNodes.add(modEntityDummyNode);	
		this.dummyNodes.add(ruleDummyNode);	
		this.dummyNodes.add(eventDummyNode);	
		this.dummyNodes.add(destDummyNode);	
		this.dummyNodes.add(sourceDummyNode);
		
		// place in similar area as for rule sequence constraint setting
		this.setEventConstraints();
		
		LinkedList<TSENode> misccolumn = new LinkedList<TSENode>();
		LinkedList<TSENode> othercolumn = new LinkedList<TSENode>();
		LinkedList<TSEEdge> edgesToDelete = new LinkedList<TSEEdge>();
		LinkedList<TSENode> neighbors = new LinkedList<TSENode>();
		LinkedList<TSENode> nodesToShow = new LinkedList<TSENode>();
		nodesToShow.add(eventNode);
		nodesToShow.addAll(this.elementNodes);
		nodesToShow.addAll(this.dummyNodes);
		
		misccolumn.clear();
		misccolumn.add(sourceHeaderNode);
		misccolumn.add(sourceDummyNode);
		this.setEventConstraints(misccolumn);

		// reverse link order of: event->destination
		othercolumn.add(sourceHeaderNode);
		misccolumn.clear();
		misccolumn.add(destHeaderNode);
		TSENode otherNode;
		TSEEdge currentEdge;
		Iterator edgeIter = eventNode.outEdges().iterator();
		while (edgeIter.hasNext()) {
			currentEdge = (TSEEdge) edgeIter.next();
			otherNode = (TSENode) currentEdge.getTargetNode();
			// if otherNode is a destination, create link in opposite direction
			// but if not (maybe a parent), do nothing
			if (otherNode.getUserObject() != null &&
				otherNode.getUserObject() instanceof Destination) {
				nodesToShow.add(otherNode);
				edgesToDelete.add(currentEdge);
				this.createEventSequenceLink(currentEdge, otherNode, eventNode);
				otherNode.findParents(neighbors, null, 1);
				// FOR CONSTRAINT: sourceHeaderNode, neighbors, sourceDummyNode
				othercolumn.addAll(this.removeEventsFromList(neighbors));
				nodesToShow.addAll(neighbors);
				// FOR CONSTRAINT: destHeaderNode, otherNode, destDummyNode
				misccolumn.add(otherNode);
			}
		}
		othercolumn.add(sourceDummyNode);
		misccolumn.add(destDummyNode);
		this.setEventConstraints(othercolumn);
		this.setEventConstraints(misccolumn);
		
		// FOR CONSTRAINT: eventHeaderNode, eventNode, eventDummyNode
		misccolumn.clear();
		misccolumn.add(eventDummyNode);
		misccolumn.add(eventNode);
		misccolumn.add(eventHeaderNode);
		this.setEventConstraints(misccolumn);

		// reverse link order of: rule/RF->event
		misccolumn.clear();
		misccolumn.add(ruleHeaderNode);
		
		LinkedList<TSENode> ruleNodesAffectedByEvent = new LinkedList<TSENode>();
		othercolumn.clear();
		othercolumn.add(modEntityHeaderNode);
//		String edgeLabel = null;
//		List<TSEEdgeLabel> edgeLabelList = null;
		edgeIter = eventNode.inEdges().iterator();
		while (edgeIter.hasNext()) {
			currentEdge = (TSEEdge) edgeIter.next();
			otherNode = (TSENode) currentEdge.getSourceNode();
			// if otherNode is a rule/RF (it could be a parent event), create link in opposite direction
			if (otherNode.getUserObject() != null &&
				(otherNode.getUserObject() instanceof Rule ||
					otherNode.getUserObject() instanceof RuleFunction)) {
				nodesToShow.add(otherNode);
				edgesToDelete.add(currentEdge);
				this.createEventSequenceLink(currentEdge, eventNode, otherNode);
				otherNode.findChildren(neighbors, null, RULE_DEPTH);
				nodesToShow.addAll(neighbors);
				// FOR CONSTRAINT: ruleHeaderNode, neighbors, ruleDummyNode,
				// but do this just for 1 level, not RULE_DEPTH! 
				othercolumn.addAll(this.removeEventsFromList(neighbors));
				misccolumn.add(otherNode);
				ruleNodesAffectedByEvent.add(otherNode);
			}
		}
		misccolumn.add(ruleDummyNode);
		othercolumn.add(modEntityDummyNode);
		this.setEventConstraints(misccolumn);
		this.setEventConstraints(othercolumn);
		
		edgeIter = edgesToDelete.iterator();
		while (edgeIter.hasNext()) {
			this.graphManager.discard((TSEEdge)edgeIter.next());
		}
		
		// deeply show the other entities the immediate rules/RFs of this event could modify 
		TSEHidingManager hideMgr = ((TSEHidingManager) TSEHidingManager.getManager(this.graphManager));
		this.graphManager.selectAll(true);
		hideMgr.hideSelected();
		this.graphManager.selectAll(false);
		hideMgr.unhide(nodesToShow, null, true);
		eventNode.setSelected(true);
		
		Iterator<TSENode> nodeIter = ruleNodesAffectedByEvent.iterator();
		while(nodeIter.hasNext()) {
			this.rfToModEntitiesEdgeList.addAll(nodeIter.next().outEdges());
		}

		layoutAndRefresh(true);
	}
	
	private TSEEdge createEventSequenceLink(TSEEdge currentEdge, TSENode srcNode, TSENode tgtNode) {
		String edgeLabel = null;
		List<TSEEdgeLabel> edgeLabelList = currentEdge.labels();
		if (edgeLabelList != null && !edgeLabelList.isEmpty()) {
			edgeLabel = ((TSEEdgeLabel)edgeLabelList.get(0)).getText();
		}
		return this.dependencyManager.createLink(
			srcNode, tgtNode, TSEPolylineEdgeUI.LINE_STYLE_SOLID, edgeLabel);
	}

	private void setEventConstraints() {
		this.setConstraints(this.elementNodes,
			TSLayoutConstants.ORIENTATION_HORIZONTAL,
			TSLayoutConstants.DIRECTION_RIGHT_TO_LEFT);
		this.setConstraints(this.dummyNodes,
			TSLayoutConstants.ORIENTATION_HORIZONTAL,
			TSLayoutConstants.DIRECTION_RIGHT_TO_LEFT);		
	}
	
	private void setEventConstraints(List list) {
		Iterator i = list.iterator();
		this.setConstraints(list,
			TSLayoutConstants.ORIENTATION_VERTICAL,
			TSLayoutConstants.DIRECTION_BOTTOM_TO_TOP);	
	}
	
	private List<TSENode> removeEventsFromList(List<TSENode> list) {
		LinkedList<TSENode> newList = new LinkedList<TSENode>();
		Iterator i = list.iterator();
		TSENode node;
		while (i.hasNext()) {
			node = (TSENode) i.next();
			if (node.getUserObject() != null &&
					node.getUserObject() instanceof EntityNodeData && 
					((EntityNodeData)node.getUserObject()).getUserObject() instanceof Event) {
				// we found an event, don't add it to the new list
			}
			else {
				newList.add(node);
			}
		}

		return newList;
	}
	
	private TSENode createEventElementNode(String label) {
		TSENode tsEventElement = (TSENode) this.graphManager.getMainDisplayGraph().addNode();
		this.populateTSNode(tsEventElement, label);
		return tsEventElement;
	}

//	// used by event sequence diagram only
	public void populateTSNode(TSENode node, String label) {
		RoundRectNodeUI ui = new RoundRectNodeUI();
		ui.setDrawGradient(true);
		ui.setDrawTag(true);
		// ui.setGradient(EventDiagramManager.START_COLOR, EventDiagramManager.END_COLOR);
		ui.setGradient(START_RULE_COLUMN_COLOR, END_RULE_COLUMN_COLOR);		
		ui.setBorderDrawn(true);
		node.setResizability(TSESolidObject.RESIZABILITY_TIGHT_FIT);
		node.setUI(ui);
//		node.addLabel().setText(label);
		node.setName(label);
	}
		
	
	//////////////// RULE SEQUENCE DIAGRAM CODE FROM HERE /////////////////////////
	
	private void generateRuleSequence() {
		TSENode rootNode = createTSSequenceColumn(this.rule);
		this.processRuleElement(rootNode, this.rule, true);

		this.getLayoutManager().setAllowNodeResizeHierarchicalOptions();
		this.setConnectors();
		
		// set alignment and sequence constraints on these node lists
		this.setRuleConstraints();
	}
	
	private void setRuleConstraints() {
		this.setConstraints(this.elementNodes,
			TSLayoutConstants.ORIENTATION_HORIZONTAL,
			TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
		this.setConstraints(this.thinNodes,
			TSLayoutConstants.ORIENTATION_HORIZONTAL,
			TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
		this.setConstraints(this.dummyNodes,
			TSLayoutConstants.ORIENTATION_HORIZONTAL,
			TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);		
	}

	private void processRuleElement(TSENode source, RuleElement ruleElement, boolean isRoot) {
		TSENode existingNode = (TSENode) this.ruleElements.get(ruleElement);
		if (existingNode != null) {
			// we've already processed this rule, link back to it in diagram
			createLink(source, existingNode, RETURN_LINK, ruleElement.getName());
			return;
		}
		this.ruleElements.put(ruleElement, source);
		
		TSENode node = null;
		if (isRoot) {
			node = source;
		}
		else {
			node = createTSSequenceColumn(ruleElement);
			createLink(source, node, FORWARD_LINK, ruleElement.getName());
		}

		ScopeBlock scope = ruleElement.getScope();
		// this is the root level, or global scope, and does not need to be processed,
		// as it contains no rule refs. Process child scopes instead.
		EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
		for (ScopeBlock scopeBlock : childScopeDefs) {
			processScope(node, scopeBlock);
		}
	}
	
	private void processScope(TSENode source, ScopeBlock scope) {
		EList<ElementReference> refs = scope.getRefs();
		for (ElementReference elementReference : refs) {
			Object resolvedElement = ElementReferenceResolver.resolveElement(elementReference, ElementReferenceResolver.createResolutionContext(scope));
			if (resolvedElement instanceof RuleElement) {
				RuleElement ruleElement = (RuleElement) resolvedElement;
				// recursively process this rule
				processRuleElement(source, ruleElement, false);
			} else if (resolvedElement instanceof JavaStaticFunction) {
				if (this.showCatalogFunctions) {
					JavaStaticFunction function = (JavaStaticFunction) resolvedElement;
					TSENode node = (TSENode) this.functionElements.get(function);
					if (node == null) {
						node = createTSSequenceColumn(function);
						this.functionElements.put(function, node);
					}
					String name;
					String returnName;
					if (this.showExpandedNames) {
						name = function.getName().toString();
						returnName = function.getReturnClass().getCanonicalName();
					}
					else {
						name = function.getName().getLocalName();
						returnName = function.getReturnClass().getSimpleName();
					}
					this.createLink(source, node, FORWARD_LINK, name);
					if (this.showCatalogFuncReturnLinks) {
						this.createLink(node, source, RETURN_LINK, returnName);
					}
				}
			} else if (resolvedElement != null) {
//				System.out.println("### reference to other type of object: "+resolvedElement);
			}
		}
		EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
		for (ScopeBlock scopeBlock : childScopeDefs) {
			processScope(source, scopeBlock);
		}
	}
	
	
	public TSENode createTSSequenceColumn(RuleElement element) {
		// first create top node with name of element
		TSENode elementNode = this.createElementNode(element);
		return this.processSequenceColumn(elementNode);
	}
	
	public TSENode createTSSequenceColumn(JavaStaticFunction function) {
		// first create top node with name of element
		TSENode elementNode = this.createFunctionNode(function);
		return this.processSequenceColumn(elementNode);
	}
	
	private TSENode processSequenceColumn(TSENode elementNode) {
		this.elementNodes.add(elementNode);
		
		// then create thin thin node
		TSENode thinNode = this.createSequenceNode(THIN_NODE_SIZE, THIN_NODE_COLOR);
		this.thinNodes.add(thinNode);
		
		// then create dummy bottom node
		TSENode dummyNode = this.createSequenceNode(DUMMY_NODE_SIZE, END_RULE_COLUMN_COLOR);
		this.dummyNodes.add(dummyNode);
		
		// create edge between thin node and element node
		TSEEdge topEdge = this.createLink(thinNode, elementNode, COLUMN_LINK);
		
		// create edge between dummy node and thin node
//		TSEEdge bottomEdge = this.createLink(dummyNode, thinNode, COLUMN_LINK);
		
		// add sequence constraints between (elementNode,thinNode,dummyNode);
		List<TSENode> nodes = new LinkedList<TSENode>();
		nodes.add(elementNode);
		nodes.add(thinNode);
		nodes.add(dummyNode);
		this.setConstraints(nodes,
			TSLayoutConstants.ORIENTATION_VERTICAL,
			TSLayoutConstants.DIRECTION_TOP_TO_BOTTOM);
		
		// set attachment sides for the two edges
		this.setAttachmentSides(topEdge, COLUMN_LINK);
//		this.setAttachmentSides(bottomEdge, COLUMN_LINK);
		
		return thinNode;
	}
	
	private TSENode createElementNode(RuleElement element) {
		TSENode tsRuleElement = (TSENode) this.graphManager.getMainDisplayGraph().addNode();
		this.populateTSNode(tsRuleElement, element);
		return tsRuleElement;
	}

	private TSENode createFunctionNode(JavaStaticFunction function) {
		TSENode tsRuleElement = (TSENode) this.graphManager.getMainDisplayGraph().addNode();
		this.populateTSNode(tsRuleElement, function);
		return tsRuleElement;
	}
	
	private TSENode createSequenceNode(TSConstSize size, TSEColor color) {
		TSENode node = (TSENode) this.graphManager.getMainDisplayGraph().addNode();
		node.setSize(size);
		SimpleRectNodeUI ui = new SimpleRectNodeUI();
		if (color != null) {
			ui.setFillColor(color);
		}
		node.setUI(ui);
		return node;
	}
	
	public void populateTSNode(TSENode node, RuleElement element) {
		//TSETextNodeUI ui = new TSETextNodeUI();
		RoundRectNodeUI ui = new RoundRectNodeUI();
		ui.setDrawGradient(true);
		ui.setDrawTag(true);
		ui.setGradient(START_RULE_COLUMN_COLOR, END_RULE_COLUMN_COLOR);
		ui.setBorderDrawn(true);
		node.setResizability(TSESolidObject.RESIZABILITY_TIGHT_FIT);
		node.setUI(ui);
		if (element != null && element.getName() != null) {
			String name = element.getName();
			node.setName(name);
			node.setUserObject(element);
		}
		else {
			node.setName("NO NAME");
		}
	}

	public void populateTSNode(TSENode node, JavaStaticFunction function) {
		//TSETextNodeUI ui = new TSETextNodeUI();
		RoundRectNodeUI ui = new RoundRectNodeUI();
		ui.setDrawGradient(true);
		ui.setDrawTag(true);
		ui.setGradient(START_CATFUNC_COLUMN_COLOR, END_CATFUNC_COLUMN_COLOR);
		ui.setBorderDrawn(true);
		node.setResizability(TSESolidObject.RESIZABILITY_TIGHT_FIT);
		node.setUI(ui);
		if (function != null && function.getName() != null) {
			String name;
			if (this.showExpandedNames) {
				name = function.getName().getExpandedForm();
			}
			else {
				name = function.getName().getLocalName();
			}
			node.setName(name);
			node.setUserObject(function);
		}
		else {
			node.setName("NO NAME");
		}
	}	
	
	private void setAttachmentSides(TSEEdge edge, int edgeStyle) {
		
		int srcSide;
		int tgtSide;
		
		switch (edgeStyle) {
		case COLUMN_LINK:
			srcSide = TSLayoutConstants.ATTACHMENT_SIDE_TOP;
			tgtSide = TSLayoutConstants.ATTACHMENT_SIDE_BOTTOM;
			break;
		case FORWARD_LINK:
			srcSide = TSLayoutConstants.ATTACHMENT_SIDE_RIGHT;
			tgtSide = TSLayoutConstants.ATTACHMENT_SIDE_LEFT;
			break;
		case RETURN_LINK:
			srcSide = TSLayoutConstants.ATTACHMENT_SIDE_LEFT;
			tgtSide = TSLayoutConstants.ATTACHMENT_SIDE_RIGHT;
			break;
		default:
			srcSide = TSLayoutConstants.ATTACHMENT_SIDE_ANY;
			tgtSide = TSLayoutConstants.ATTACHMENT_SIDE_ANY;
			break;
		}
		
		// if we have a loop
		if (edge.getSourceNode() == edge.getTargetNode()) {
			srcSide = TSLayoutConstants.ATTACHMENT_SIDE_RIGHT;
			tgtSide = TSLayoutConstants.ATTACHMENT_SIDE_RIGHT;
		}
		
		((EntityLayoutManager) this.getLayoutManager()).setEdgeAttachmentSides(
			edge, srcSide, tgtSide);
	}
	
	/*
	 * Add alignment and then sequence constraints for this list of nodes.
	 */
	private void setConstraints(List<TSENode> nodes, int orientation, int direction) {
		((EntityLayoutManager) this.getLayoutManager()).addAlignmentConstraint(
			nodes, orientation);
		((EntityLayoutManager) this.getLayoutManager()).addSequenceConstraint(
			nodes, direction);
	}

	public TSEEdge createLink(TSENode src, TSENode tgt, int edgeType) {
		TSEEdge edge = (TSEEdge) this.graphManager.addEdge(src, tgt);
		// TODO: maybe set the edge UI to be something else
		TSEPolylineEdgeUI edgeUI = new TSEPolylineEdgeUI();
		int edgeStyle;
		
		// this is only so we can ensure the order of the in/out edges
		LeftRightList srcList = this.thinNodeEdgeMap.get(src);
		LeftRightList tgtList = this.thinNodeEdgeMap.get(tgt);
		if (srcList == null) {
			srcList = new LeftRightList();
			this.thinNodeEdgeMap.put(src, srcList);
		}
		if (tgtList == null) {
			tgtList = new LeftRightList();
			this.thinNodeEdgeMap.put(tgt, tgtList);
		}

		if (edgeType == FORWARD_LINK) {
			srcList.addRight(edge);
			tgtList.addLeft(edge);
		}
		else if (edgeType == RETURN_LINK) {
			srcList.addLeft(edge);
			tgtList.addRight(edge);
		}
		// done with in/out edge order processing

		switch (edgeType) {
		case COLUMN_LINK:
			// edgeStyle = TSEDashedEdgeUI.LINE_STYLE_SHORT_DASH;
			edgeStyle = TSEPolylineEdgeUI.LINE_STYLE_SOLID;
			edgeUI.setLineColor(VERTICAL_LINE_COLOR);
			edgeUI.setArrowType(TSEEdgeUI.NO_ARROW);
			break;
		case FORWARD_LINK:
			edgeStyle = TSEPolylineEdgeUI.LINE_STYLE_SOLID;
			break;
		case RETURN_LINK:
			edgeStyle = TSEPolylineEdgeUI.LINE_STYLE_DOT;
			break;
		default:
			edgeStyle = TSEPolylineEdgeUI.LINE_STYLE_SOLID;
		}

		edgeUI.setLineStyle(edgeStyle);
		edge.setUI(edgeUI);
		this.setAttachmentSides(edge, edgeType);
		return edge;
	}

	public TSEEdge createLink(TSENode src, TSENode tgt, int edgeType, String desc) {
		TSEEdge edge = this.createLink(src, tgt, edgeType);
		(edge.addLabel()).setText(desc);
		return edge;
	}
	
	private void setConnectors() {
		TSENode node;
		Iterator<TSENode> nodeIter = this.thinNodes.iterator();
		while (nodeIter.hasNext()) {
			node = nodeIter.next();
			LeftRightList l = this.thinNodeEdgeMap.get(node);
			if (l == null) {
				System.err.println("No links exist for this sequence node.");
				return;
			}
			
			this.processSeqNodeSide(node, l.getLeft(), true);
			this.processSeqNodeSide(node, l.getRight(), false);

			((EntityLayoutManager) this.getLayoutManager()).setMovableConnectors(node, false);
		}
	}
	
	private void processSeqNodeSide(TSENode node, List<TSEEdge> list, boolean isLeft) {
		int nrOfEdges = list.size();
		
		if (nrOfEdges > DEFAULT_NR_OF_EDGES) {
			node.setHeight(node.getHeight() + (nrOfEdges - DEFAULT_NR_OF_EDGES) * EDGE_VERTICAL_SPACING);
		}
		
		double segment = 1.0 / (nrOfEdges + 1);
		double yCoord = 0.5;
		double xCoord = 0.5;
		if (isLeft) {
			xCoord = -0.5;
		}

		TSEConnector connector;
		TSEEdge edge;
		Iterator<TSEEdge> iter = list.iterator();
		while (iter.hasNext()) {
			edge = iter.next();
			yCoord -= segment;
			connector = (TSEConnector) node.addConnector();
			connector.setProportionalXOffset(xCoord);
			connector.setProportionalYOffset(yCoord);
			if (edge.getSourceNode() == node) {
				edge.setSourceConnector(connector);
			}
			else {
				edge.setTargetConnector(connector);
			}			
		}
	}

	private void layoutAndRefresh() {
		this.layoutAndRefresh(false);
	}
	
	private void layoutAndRefresh(boolean leftToRight) {
		EntityLayoutManager layMgr = (EntityLayoutManager) this.getLayoutManager();
		if (leftToRight) {
			layMgr.setLevelDirection(TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
			layMgr.setHierarchicalOptions();
		}
		this.drawingCanvas.drawGraph();
		this.drawingCanvas.repaint();
		IWorkbench workBench = PlatformUI.getWorkbench();
		if(workBench != null){
			IWorkbenchWindow workbenchWindow = workBench.getActiveWorkbenchWindow();
			if (workbenchWindow != null) {
				IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
				  if (workbenchPage != null) {
					IEditorPart editorPart = workbenchPage.getActiveEditor();
					if(editorPart != null){
						IEditorSite site = editorPart.getEditorSite();
						if (site != null) {
							refreshOverview(site, true, true);	
						}
					}
			    }
		    }	
		}
	}

	private RuleElement getRuleElement(String name) {
		EList<RuleElement> elements = this.studioProject.getRuleElements();
		for (RuleElement ruleElement : elements) {
			// System.out.println("searched thru: " + ruleElement.getName());
			if (name.equals(ruleElement.getName())) {
				return ruleElement;
			}
		}
		return null;
	}
	
	private EntityElement getEventElement(String name) {
		EList<EntityElement> elements = this.studioProject.getEntityElements();
		for (EntityElement eventElement : elements) {
			if (name.equals(eventElement.getName())) {
				if (eventElement instanceof Object) {
					return eventElement;
				}
			}
		}
		return null;
	}
	
	
	class LeftRightList {
		List<TSEEdge> rightList;
		List<TSEEdge> leftList;
		
		public LeftRightList() {
			this.rightList = new LinkedList<TSEEdge>();
			this.leftList = new LinkedList<TSEEdge>();
		}
	
		public void addRight(TSEEdge edge) {
			this.rightList.add(edge);
		}
		
		public void addLeft(TSEEdge edge) {
			this.leftList.add(edge);
		}
		
		public List<TSEEdge> getRight() {
			return this.rightList;
		}
		
		public List<TSEEdge> getLeft() {
			return this.leftList;
		}
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.diagrams.EntityDiagramManager#getSelectTool()
	 */
	@Override
	public SelectTool getSelectTool() {
		if (this.sequenceSelectTool == null) {
			this.sequenceSelectTool = new SequenceSelectTool(this);
		}
		return this.sequenceSelectTool;
	}

	@Override
	protected TSENode createSharedEntity(DesignerElement e) {
		// TODO Auto-generated method stub
		return null;
	}
}
