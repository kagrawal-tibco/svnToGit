package com.tibco.cep.studio.ui.diagrams;


import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_TOOLTIPS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramUtils.gridType;
import static com.tibco.cep.studio.ui.editors.utils.StudioEditorDiagramUIUtils.loadDefaultPreferencesMap;
import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.PROJECT_GRID;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.PROJECT_LINES;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.PROJECT_NONE;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.PROJECT_POINTS;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createChannelTooltip;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createDecisionTableToolTip;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createDestinationTooltip;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createDomainModelTooltip;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createRuleFunctionToolTip;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createRuleToolTip;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createScoreCardTooltip;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createStateMachineTooltip;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.openError;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.be.parser.semantic.FunctionRec;
import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.impl.SimplePropertyImpl;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.EntityLayoutManager;
import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.ui.NoteNodeUI;
import com.tibco.cep.diagramming.utils.DiagramConstants;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.sharedresource.SharedResourcePlugin;
import com.tibco.cep.sharedresource.model.SharedResourcePropertyUtil;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.functions.model.EMFMetricMethodModelFunction;
import com.tibco.cep.studio.core.index.StudioModelManager;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.impl.EntityElementImpl;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.update.IStudioElementDelta;
import com.tibco.cep.studio.core.index.update.IStudioModelChangedListener;
import com.tibco.cep.studio.core.index.update.StudioElementDelta;
import com.tibco.cep.studio.core.index.update.StudioModelChangedEvent;
import com.tibco.cep.studio.core.index.update.StudioModelDelta;
import com.tibco.cep.studio.core.index.update.StudioProjectDelta;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.BaseRulesParser;
import com.tibco.cep.studio.ui.diagrams.tools.ProjectSelectTool;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.project.ProjectAnalyzer;
import com.tibco.cep.studio.ui.editors.project.ProjectDiagramEditor;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.editors.utils.ProjectDiagramUtils;
import com.tibco.cep.studio.ui.editors.utils.StudioEditorDiagramUIUtils;
import com.tibco.cep.studio.ui.overview.OverviewUtils;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tibco.cep.studio.util.StudioConfig;
import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.graph.TSFindChildParent;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.complexity.TSEHidingManager;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEChildGraphUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEImageNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;
import com.tomsawyer.interactive.swing.TSSwingCanvasPreferenceTailor;
import com.tomsawyer.util.preference.TSPreferenceData;
/**
 *
 * @author ggrigore
 *
 */

public class ProjectDiagramManager extends EntityDiagramManager<Entity> {

	//Shared Resources GUID Map<resource name  and
	private Map<String,String> SharedResourceGUIDMap = new HashMap<String, String>();;

	private static String sharedResourceNodeAtrribute = "SHARED_RESOURCE_PATH";

	private TSEImage SCORECARD_IMAGE;
	private TSEImage RULEFUNCTION_IMAGE;
	private TSEImage VRULEFUNCTION_IMAGE;
	@SuppressWarnings("unused")
	private TSEImage RULESET_IMAGE;
	private TSEImage RULE_IMAGE;
	private TSEImage DESTINATION_IMAGE;
	private TSEImage CHANNEL_IMAGE;
	private TSEImage EAR_IMAGE;
	private TSEImage BAR_IMAGE;
	private TSEImage SAR_IMAGE;
	private TSEImage STATEMACHINE_IMAGE;
	private TSEImage DECISION_TABLE_IMAGE;
	private TSEImage DOMAIN_MODEL_IMAGE;
	//Shared Resources
	private TSEImage JDBC_CONNECTION_IMAGE;
	private TSEImage HTTP_CONNECTION_IMAGE;
	private TSEImage HAWK_IMAGE;
	private TSEImage AS_IMAGE;
	private TSEImage FTL_IMAGE;
	private TSEImage IDENTITY_RESOURCE_IMAGE;
	private TSEImage JMS_APPLICATION_PROPERTIES_IMAGE;
	private TSEImage JMS_CONNECTION_IMAGE;
	private TSEImage JNDI_CONFIGURATION_IMAGE;
	private TSEImage RENDEZVOUS_TRANSPORT_IMAGE;
	private TSEImage RULESERVICEPROVIDER_CONFIGURATION_IMAGE;
	private TSEImage MQTT_IMAGE; 

	private List<TSENode> sharedResources;
	private List<TSENode> concepts;
	private List<TSENode> metrics;
	private List<TSENode> events;
	private List<TSENode> rules;
	private List<TSENode> rulefunctions;
	private List<TSEEdge> scopeLinks;
	private List<TSEEdge> usageLinks;
	//private List<TSENode> archives;
	private List<TSENode> scoreCards;
	private List<TSENode> channels;
	private List<TSENode> destinations;
	private List<TSENode> stateMachines;
	private List<TSENode> decisionTables;
	private List<StringBuffer> toolTips;
	private List<TSENode> domains;
	private List<TSEEdge> allLinks;

//	private List<TSENode> sharedSharedResources;
	private List<TSENode> sharedDecisionTables;
	private List<TSENode> sharedentities;
	private List<TSENode> sharedRules;
	private List<TSENode> sharedElement;
	private List<TSENode> sharedrulefunctions;

	private HashMap<RuleElement, TSENode> ruleElements = new HashMap<RuleElement, TSENode>();
	private List<RuleElement> processedRuleElements = new LinkedList<RuleElement>();

	//Default Map set for the preferences for defaults apply.
	private Map<String, Boolean> defaultMap = new HashMap<String, Boolean>();
	private Map<String,Boolean> projectDiagramMap =  new HashMap<String,Boolean>();
	private Map<String, Set<TSENode>> processChildNodesMap;
	private boolean groupConceptsInSameArea = false;
	private boolean groupEventsInSameArea = false;
	private boolean groupRulesInSameArea = false;
	private boolean groupRuleFunctionsInSameArea = false;

	private TSENode conceptGroupNode;
	private TSENode eventGroupNode;
	private TSENode ruleGroupNode;
	private TSENode ruleFunctionGroupNode;

	private boolean LAYOUT_HIERARCHICAL = false;

	protected DesignerProject studioProject;
	private IStudioModelChangedListener modelChangeListener = new DiagramUpdateListener();

	public final int EAR_TYPE = 0;
	public final int BAR_TYPE = 1;
	public final int SAR_TYPE = 2;
	public final TSEColor CONCEPT_GROUP_COLOR = new TSEColor(255, 201, 95);
	public final TSEColor EVENT_GROUP_COLOR = new TSEColor(177, 202, 186);
	public final TSEColor RULE_GROUP_COLOR = new TSEColor(196, 196, 196);
	public final TSEColor RF_GROUP_COLOR = new TSEColor(214, 205, 160);

	protected ProjectSelectTool projectSelectTool;

	public boolean viewsAddOn = false;

	protected ProjectAnalyzer projectAnalyzer;
	private String projectViewInfo;
	private boolean analysisWhileCreatingView = true;
	private IPreferenceStore prefStore;

	private int DEPENDENCY_LEVEL = 1;

	private HashMap< String, Integer> defintionsMap = new HashMap<String, Integer>();

	public static final String NODE = " Total Nodes";
	public static final String EDGE = " Total Edges";
	public static final String CONCEPT = " Concepts";
	public static final String METRIC = " Metrics";
	public static final String EVENT = " Events";
	public static final String RULE = " Rules";
	public static final String RULEFUNCTION = " Rule Functions";
	public static final String STATEMODEL = " State Models";
	public static final String SCORECARD = " ScoreCards";
	public static final String DESTINATION = " Destinations";
	public static final String DECISIONTABLE = " Decision Tables";
	public static final String DOMAINMODEL = " Domain Models";
	public static final String CHANNEL = " Channels";

	// TODO : Extract this (and all references) to extension point
	public static final String PROCESS = " Processes";

	private static final String DASHBOARD_PLUGIN_ID = "com.tibco.cep.studio.dashboard.ui";

	private static boolean USELABELS = true;

	private Map<TSENode, Set<String>> usesLinksMap;
	private Map<TSENode, TSENode> callsLinksMap;

	public ProjectDiagramManager() {
		TSEImage.setLoaderClass(this.getClass());
		this.localInit();
	}

	public ProjectDiagramManager(EditorPart editor) {
		super(editor);
		TSEImage.setLoaderClass(this.getClass());
		this.localInit();
	}

	public ProjectDiagramManager(IProject project, IProgressMonitor monitor) {
		super(project, monitor);
		TSEImage.setLoaderClass(this.getClass());
		this.localInit();
	}

	public static String getSharedResourceNodeAtrribute() {
		return sharedResourceNodeAtrribute;
	}

	@Override
	protected void registerListeners() {
		super.registerListeners();
		addPropertyChangeListener(prefStore);
	}

	@Override
	public void dispose() {
		unregisterListeners();
		disposeTools();
		super.dispose();
		disposeImages();

		this.concepts = null;
		this.events = null;
		this.rules = null;
		this.rulefunctions = null;
		this.scopeLinks = null;
		this.usageLinks = null;
		this.scoreCards = null;
		this.channels = null;
		this.destinations = null;
		this.stateMachines = null;
		this.decisionTables = null;
		this.toolTips = null;
		this.domains = null;
		this.allLinks = null;
		this.usesLinksMap = null;
		this.callsLinksMap = null;
		if (this.ruleElements != null) {
			this.ruleElements.clear();
			this.ruleElements = null;
		}
		this.sharedResources = null;
		this.projectDiagramSelectionChangeListener = null;
		this.modelChangeListener = null;
	}

	private void disposeImages() {
		SCORECARD_IMAGE = null;
		RULEFUNCTION_IMAGE = null;
		VRULEFUNCTION_IMAGE = null;
		RULESET_IMAGE = null;
		RULE_IMAGE = null;
		DESTINATION_IMAGE = null;
		CHANNEL_IMAGE = null;
		EAR_IMAGE = null;
		BAR_IMAGE = null;
		SAR_IMAGE = null;
		STATEMACHINE_IMAGE = null;
		DECISION_TABLE_IMAGE = null;
		DOMAIN_MODEL_IMAGE = null;

		JDBC_CONNECTION_IMAGE = null;
		HTTP_CONNECTION_IMAGE = null;
		HAWK_IMAGE = null;
		AS_IMAGE = null;
		IDENTITY_RESOURCE_IMAGE = null;
		JMS_APPLICATION_PROPERTIES_IMAGE = null;
		JMS_CONNECTION_IMAGE = null;
		JNDI_CONFIGURATION_IMAGE = null;
		RENDEZVOUS_TRANSPORT_IMAGE = null;
		RULESERVICEPROVIDER_CONFIGURATION_IMAGE = null;
	}


	@Override
	protected void disposeTools() {
		super.disposeTools();
		if (this.projectSelectTool != null) {
			this.projectSelectTool.dispose();
			this.projectSelectTool = null;
		}
	}

	@Override
	public void unregisterListeners() {
		super.unregisterListeners();
		StudioCorePlugin.getDefault().removeDesignerModelChangedListener(this.modelChangeListener);
	}

	public String getFileName() {
		return this.getProjectLocation() + "/" + this.getProjectName() + this.getExtension();
	}

	private void localInit() {
		this.concepts = new LinkedList<TSENode>();
		this.metrics = new LinkedList<TSENode>();
		this.events = new LinkedList<TSENode>();
		this.rules = new LinkedList<TSENode>();
		this.rulefunctions = new LinkedList<TSENode>();
		this.scopeLinks = new LinkedList<TSEEdge>();
		this.usageLinks = new LinkedList<TSEEdge>();
		//this.archives = new LinkedList<TSENode>();
		this.scoreCards = new LinkedList<TSENode>();
		this.channels = new LinkedList<TSENode>();
		this.destinations = new LinkedList<TSENode>();
		this.stateMachines = new LinkedList<TSENode>();
		this.decisionTables = new LinkedList<TSENode>();
		this.toolTips = new LinkedList<StringBuffer>();
		this.domains = new LinkedList<TSENode>();
		this.allLinks = new LinkedList<TSEEdge>();
		this.usesLinksMap = new HashMap<TSENode, Set<String>>();
		this.callsLinksMap = new HashMap<TSENode, TSENode>();

		this.sharedRules = new LinkedList<TSENode>();
		this.sharedElement = new LinkedList<TSENode>();
		this.sharedDecisionTables = new LinkedList<TSENode>();
		this.sharedentities = new LinkedList<TSENode>();

		initExtensions();

		this.initNodeUIs();
		prefStore = EditorsUIPlugin.getDefault().getPreferenceStore();
		StudioCorePlugin.getDefault().addDesignerModelChangedListener(this.modelChangeListener);
		USELABELS = Boolean.parseBoolean(
				StudioConfig.getInstance().getProperty("be.studio.project.diagram.useLabels", "true"));

		Bundle viewsBundle = Platform.getBundle(DASHBOARD_PLUGIN_ID);
		if(viewsBundle != null)
			this.viewsAddOn = true;


	}

	private void initExtensions() {
		IProjectDiagramContributor[] projectDiagramContributors = ProjectDiagramUtils.getProjectDiagramContributors();
		for (IProjectDiagramContributor projectDiagramContributor : projectDiagramContributors) {
			projectDiagramContributor.initialize();
		}
	}

	private void clearExtensions() {
		IProjectDiagramContributor[] projectDiagramContributors = ProjectDiagramUtils.getProjectDiagramContributors();
		for (IProjectDiagramContributor projectDiagramContributor : projectDiagramContributors) {
			projectDiagramContributor.clear();
		}
	}

	private void addExtensionInfo(HashMap<String, Integer> defintionsMap) {
		IProjectDiagramContributor[] projectDiagramContributors = ProjectDiagramUtils.getProjectDiagramContributors();
		for (IProjectDiagramContributor projectDiagramContributor : projectDiagramContributors) {
			projectDiagramContributor.addDiagramInfo(defintionsMap);
		}
	}

	private void appendExtensionInfo(StringBuilder builder) {
		IProjectDiagramContributor[] projectDiagramContributors = ProjectDiagramUtils.getProjectDiagramContributors();
		for (IProjectDiagramContributor projectDiagramContributor : projectDiagramContributors) {
			projectDiagramContributor.appendDiagramLayoutInfo(builder);
		}
	}

	public void setShowProcesses(boolean showProcesses) {
		IProjectDiagramContributor[] projectDiagramContributors = ProjectDiagramUtils.getProjectDiagramContributors();
		for (IProjectDiagramContributor projectDiagramContributor : projectDiagramContributors) {
			if (PROCESS.equals(projectDiagramContributor.getContentType())) {
				projectDiagramContributor.setShowNodes(showProcesses);
			}
		}
	}

	public void setShowProcessLinks(boolean showProcessLinks) {
		IProjectDiagramContributor[] projectDiagramContributors = ProjectDiagramUtils.getProjectDiagramContributors();
		for (IProjectDiagramContributor projectDiagramContributor : projectDiagramContributors) {
			if (PROCESS.equals(projectDiagramContributor.getContentType())) {
				projectDiagramContributor.setShowLinks(showProcessLinks);
			}
		}
	}

	public void setShowProcessExpanded(boolean expandProcesses) {
		IProjectDiagramContributor[] projectDiagramContributors = ProjectDiagramUtils.getProjectDiagramContributors();
		for (IProjectDiagramContributor projectDiagramContributor : projectDiagramContributors) {
			if (PROCESS.equals(projectDiagramContributor.getContentType())) {
				projectDiagramContributor.setShowNodesExpanded(expandProcesses);
			}
		}
	}

	protected void setPreferences() {
		super.setPreferences();
		String gridType = prefStore.getString(StudioPreferenceConstants.PROJECT_GRID);
		TSPreferenceData prefData = this.drawingCanvas.getPreferenceData();
		TSSwingCanvasPreferenceTailor swingTailor = new TSSwingCanvasPreferenceTailor(prefData);
		if(gridType.equalsIgnoreCase(StudioPreferenceConstants.PROJECT_NONE)){
			gridType(Messages.getString("studio.preference.diagram.none"), this.drawingCanvas);
		}else if(gridType.equalsIgnoreCase(StudioPreferenceConstants.PROJECT_LINES)){
			gridType(Messages.getString("studio.preference.diagram.lines"), this.drawingCanvas);
		} else if(gridType.equalsIgnoreCase(StudioPreferenceConstants.PROJECT_POINTS)){
			gridType(Messages.getString("studio.preference.diagram.points"), this.drawingCanvas);
		}
		swingTailor.setAutoHideScrollBars(false);
	}

	private void initNodeUIs() {
		this.SCORECARD_IMAGE = new TSEImage(this.getClass(),"/icons/scorecard48.png");
		this.RULEFUNCTION_IMAGE = new TSEImage(this.getClass(),"/icons/rule-function_48x48.png");
		this.VRULEFUNCTION_IMAGE = new TSEImage(this.getClass(),"/icons/VirtualRuleFunction_48x48.png");
		this.RULESET_IMAGE = new TSEImage(this.getClass(),"/icons/rules-set.png");
		this.RULE_IMAGE = new TSEImage(this.getClass(),"/icons/rules_48x48.png");
		this.DESTINATION_IMAGE = new TSEImage(this.getClass(),"/icons/destination.png");
		this.CHANNEL_IMAGE = new TSEImage(this.getClass(), "/icons/channel.png");
		this.BAR_IMAGE = new TSEImage(this.getClass(), "/icons/bearchive.png");
		this.EAR_IMAGE = new TSEImage(this.getClass(), "/icons/eararchive.gif");
		this.SAR_IMAGE = new TSEImage(this.getClass(), "/icons/sararchive.gif");
		this.STATEMACHINE_IMAGE = new TSEImage(this.getClass(),"/icons/state_machine_48x48.png");
		this.DECISION_TABLE_IMAGE = new TSEImage(this.getClass(), "/icons/table_48x48.png");
		this.DOMAIN_MODEL_IMAGE= new TSEImage(this.getClass(),"/icons/domainModelView_48x45.png");

		this.JDBC_CONNECTION_IMAGE=new TSEImage(SharedResourcePlugin.class,"/icons/jdbcconnection.gif");
		this.HTTP_CONNECTION_IMAGE=new TSEImage(SharedResourcePlugin.class,"/icons/httpconnection.gif");
		this.IDENTITY_RESOURCE_IMAGE=new TSEImage(SharedResourcePlugin.class,"/icons/identity.gif");
		this.HAWK_IMAGE = new TSEImage(this.getClass(),"/icons/hawk.gif");
		this.AS_IMAGE = new TSEImage(this.getClass(),"/icons/asconnection.png");
		this.FTL_IMAGE = new TSEImage(this.getClass(),"/icons/ftl.gif");
		this.MQTT_IMAGE = new TSEImage(this.getClass(),"/icons/mqtt.gif");
		this.JMS_APPLICATION_PROPERTIES_IMAGE=new TSEImage(SharedResourcePlugin.class,"/icons/jmsapplication.gif");
		this.JMS_CONNECTION_IMAGE=new TSEImage(SharedResourcePlugin.class,"/icons/jmsconfiguration.gif");
		this.JNDI_CONFIGURATION_IMAGE=new TSEImage(SharedResourcePlugin.class,"/icons/jndiconfiguration.gif");
		this.RENDEZVOUS_TRANSPORT_IMAGE=new TSEImage(SharedResourcePlugin.class,"/icons/rvtransport.gif");
		this.RULESERVICEPROVIDER_CONFIGURATION_IMAGE=new TSEImage(SharedResourcePlugin.class,"/icons/sharedRSP_16x16.png");

		StudioModelManager mgr = StudioCorePlugin.getDesignerModelManager();
		this.studioProject = mgr.getIndex(this.getProject());
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getSelectTool()
	 */
	public SelectTool getSelectTool(){
		if (this.projectSelectTool == null) {
			this.projectSelectTool = new ProjectSelectTool(this);
		}
		return this.projectSelectTool;
	}

	private ProjectDiagramSelectionChangeListener projectDiagramSelectionChangeListener;
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getDiagramSelectionListener()
	 */
	@Override
	public SelectionChangeListener getDiagramSelectionListener() {
		if (this.projectDiagramSelectionChangeListener == null) {
			this.projectDiagramSelectionChangeListener = new ProjectDiagramSelectionChangeListener(this);
		}
		return this.projectDiagramSelectionChangeListener;
	}

	public TSEColor getStartColor() {
		return EntityNodeUI.START_COLOR;
	}

	public TSEColor getEndColor() {
		return EntityNodeUI.END_COLOR;
	}

	// NOT needed because we do our own processing in populateTSNode
	protected TSEImage getEntityImage() {
		return EntityNodeUI.CONCEPT_IMAGE;
	}

	public boolean refresh;

	private boolean hidden=false;

	public boolean isRefresh() {
		return refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	// NOT needed because we do our own processing
	public void populateTSNode(TSENode node, Entity entity) { }

	// TODO: temporarily change this until we get XML persistence done
	public void openModel() {
		this.concepts.clear();
		this.metrics.clear();
		this.events.clear();
		this.rules.clear();
		this.rulefunctions.clear();
		this.scopeLinks.clear();
		this.usageLinks.clear();
		//this.archives.clear();
		this.scoreCards.clear();
		this.channels.clear();
		this.destinations.clear();
		this.stateMachines.clear();
		this.decisionTables.clear();
		this.toolTips.clear();
		this.domains.clear();
		this.nodeMap.clear();
		this.allLinks.clear();
		this.usesLinksMap.clear();
		this.callsLinksMap.clear();
		this.SharedResourceGUIDMap.clear();

		this.sharedRules.clear();
		this.sharedElement.clear();
		this.sharedDecisionTables.clear();
		this.sharedentities.clear();

		clearExtensions();

		if(!isRefresh()){
			loadDefaultPreferencesMap(defaultMap);
			projectDiagramMap.clear();
			projectDiagramMap.putAll(defaultMap);
		}



		groupConceptsInSameArea = projectDiagramMap.get(DiagramConstants.PALETTE_GROUP_CONCEPTS_TITLE);
		groupEventsInSameArea = projectDiagramMap.get(DiagramConstants.PALETTE_GROUP_EVENTS_TITLE);
		groupRulesInSameArea = projectDiagramMap.get(DiagramConstants.PALETTE_GROUP_RULES_TITLE);
		groupRuleFunctionsInSameArea =  projectDiagramMap.get(DiagramConstants.PALETTE_GROUP_RULE_FUNCTIONS_TITLE);
//		collapseProcesses = !projectDiagramMap.get(DiagramConstants.PALETTE_SHOW_PROCESS_EXPANDED_TITLE);

		long startTime = System.currentTimeMillis();

		if (this.studioProject != null) {
			super.generateModel();
			generateRules();
			//generateArchives();
			generateContributorExtensions();

			// now hide everything except the selected items, IF we have such selected items
			// i.e. showing element diagram of selected entities
			if ((this.selectedEntities != null && this.selectedEntities.size() > 0) ||
					(this.selectedProcessFiles != null && this.selectedProcessFiles.size() > 0)) {
				this.showSelectedStudioExplorerItems();

				// TODO: duplicated into here from else block below to avoid NPE:
				long endTime = System.currentTimeMillis();
				buildProjectViewInfo(startTime, endTime);
			}
			/////////////// TODO: added this block in else
			else {
				long endTime = System.currentTimeMillis();
				buildProjectViewInfo(startTime, endTime);
				StudioEditorDiagramUIUtils.doApply(this, projectDiagramMap);
				startTime = System.currentTimeMillis();
				System.out.println("Project diagram layout took: " + (startTime - endTime) / 1000 + " seconds.");
			}

			//Preference option - default: analysis is performed while creating a view.
			analysisWhileCreatingView = prefStore.getBoolean(StudioPreferenceConstants.RUN_ANALYSIS);
			if(analysisWhileCreatingView){
				projectAnalyzer = new ProjectAnalyzer(project, new NullProgressMonitor());
				projectAnalyzer.analyzeProject(new ArrayList<String>());
			}
		}
		
	
	
		
	}

	private void showSelectedStudioExplorerItems() {
		List<TSENode> tsNodes = this.findCorrespondingTSNodes(this.selectedEntities, this.selectedProcessFiles);

		if (processChildNodesMap != null) {
			Set<String> keySet = processChildNodesMap.keySet();
			for (String string : keySet) {
				tsNodes.addAll(processChildNodesMap.get(string));
			}
		}

		this.hideUnSelectedItems(tsNodes);
		this.layoutAndRefresh();
	}

	private List<TSENode> findCorrespondingTSNodes(List<DesignerElement> studioItems, List<IFile> processFiles) {
		List<TSENode> selectedTSObjects = new LinkedList<TSENode>();
		Iterator<DesignerElement> elementIter = studioItems.iterator();
		DesignerElement studioElement;
		String uniqueID = null;

		while (elementIter.hasNext()) {
			studioElement = elementIter.next();
			ELEMENT_TYPES type = studioElement.getElementType();

			if (type == ELEMENT_TYPES.RULE_FUNCTION ||
					type == ELEMENT_TYPES.RULE) {
				RuleElement rf = (RuleElement) studioElement;
				uniqueID = rf.getFolder() + rf.getName();
			}
			else if (type == ELEMENT_TYPES.CONCEPT ||
					type == ELEMENT_TYPES.SIMPLE_EVENT ||
					type == ELEMENT_TYPES.TIME_EVENT ||
					type == ELEMENT_TYPES.SCORECARD ||
					type == ELEMENT_TYPES.STATE_MACHINE ||
					type == ELEMENT_TYPES.DOMAIN ||
					type == ELEMENT_TYPES.CHANNEL ||
					type == ELEMENT_TYPES.DESTINATION) {
				EntityElement ee = (EntityElement) studioElement;
				uniqueID = ((Entity)ee.getEntity()).getGUID();
			}
			else if (type == ELEMENT_TYPES.DECISION_TABLE) {
				if(studioElement instanceof DecisionTableElement){
					Implementation implementation = (Implementation) ((DecisionTableElement)studioElement).getImplementation();
					uniqueID = implementation.getFolder() + implementation.getName();
				}
			}
			else {
				System.err.println("Selected project item type is unknown!");
				continue;
			}

			if (this.nodeMap.containsKey(uniqueID)) {
				selectedTSObjects.add(this.nodeMap.get(uniqueID));
			}
			else {
				System.err.println("Unable to find known selected project item in node map!");
				continue;
			}
		}

		//Fpor
		for (IFile file : selectedProcessFiles) {
			String fullPath =  file.getProjectRelativePath().removeFileExtension().toString();
			if (fullPath.endsWith("/")) {
				fullPath = fullPath.substring(0, fullPath.lastIndexOf('/'));
			}
			int idx = fullPath.lastIndexOf('/');
			if (idx >= 0) {
				idx++;
				String folder = fullPath.substring(0, idx);
				String elementName = fullPath.substring(idx);
				uniqueID = "/" + folder + elementName;
			} else {
				uniqueID = "/" + file.getName().substring(0, file.getName().indexOf(file.getFileExtension())-1);
			}
			if (this.nodeMap.containsKey(uniqueID)) {
				selectedTSObjects.add(this.nodeMap.get(uniqueID));
			}
			else {
				System.err.println("Unable to find known selected project item in node map!");
				continue;
			}
		}

		return selectedTSObjects;
	}

	public void hideUnSelectedItems(List<TSENode> selectedTSNodes) {
		String dependencyOption = this.prefStore.getString(StudioPreferenceConstants.PROJECT_LEVELS);
		if (dependencyOption.equalsIgnoreCase(StudioPreferenceConstants.PROJECT_ONE)) {
			this.setDependencyLevel(1);
		}
		else if (dependencyOption.equalsIgnoreCase(StudioPreferenceConstants.PROJECT_TWO)) {
			this.setDependencyLevel(2);
		}
		else if (dependencyOption.equalsIgnoreCase(StudioPreferenceConstants.PROJECT_ALL)) {
			this.setDependencyLevel(TSFindChildParent.FIND_DEPTH_ALL);
		}
		int noNodes = graphManager.numberOfNodes();
		int noEdges = graphManager.numberOfEdges();

		// first hide everything
		TSEHidingManager hideMgr = ((TSEHidingManager) TSEHidingManager.getManager(graphManager));
		graphManager.selectAll(true);
		hideMgr.hideSelected();
		graphManager.selectAll(false);
		LinkedList<TSENode> nodesToShow = new LinkedList<TSENode>();

		// now prepare list of what to unhide
		for (TSENode node : selectedTSNodes) {
			nodesToShow.add(node);
			hideMgr.findHiddenNeighbors(node, this.getDependencyLevel());
			nodesToShow.addAll(hideMgr.getResultNodeList());
		}

		hideMgr.unhide(nodesToShow, null, true);

		int noHiddenNodes = noNodes - graphManager.numberOfNodes();
		int noHiddenEdges = noEdges - graphManager.numberOfEdges();
		System.out.println(noHiddenNodes + " nodes and " +
				noHiddenEdges + " edges were filtered out.");
		System.out.println("Diagram now has "
				+ graphManager.numberOfNodes() + " nodes and "
				+ graphManager.numberOfEdges() + " edges.");
	}

	/**
	 * @param startTime
	 * @param endTime
	 */
	public void buildProjectViewInfo(long startTime, long endTime){

		defintionsMap.clear();
		defintionsMap.put(NODE, this.graphManager.numberOfNodes());
		defintionsMap.put(EDGE,  this.graphManager.numberOfEdges());
		defintionsMap.put(CONCEPT,  this.concepts.size());
		defintionsMap.put(METRIC,  this.metrics.size());
		defintionsMap.put(EVENT,  this.events.size());
		defintionsMap.put(RULE,  this.rules.size());
		defintionsMap.put(RULEFUNCTION,  this.rulefunctions.size());
		defintionsMap.put(STATEMODEL,  this.stateMachines.size());
		defintionsMap.put(SCORECARD,  this.scoreCards.size());
		defintionsMap.put(CHANNEL,  this.channels.size());
		defintionsMap.put(DESTINATION,  this.destinations.size());
		defintionsMap.put(DECISIONTABLE,  this.decisionTables.size());
		defintionsMap.put(DOMAINMODEL,  this.domains.size());

		addExtensionInfo(defintionsMap);

		StringBuilder builder = new StringBuilder();
		builder.append(Messages.getString("project.view.main.description",
				this.graphManager.numberOfNodes(),
				this.graphManager.numberOfEdges(), (endTime - startTime) / 1000 ));
		builder.append( "\n");
		builder.append( Messages.getString("project.view.sub.description",
				this.concepts.size(),
				this.metrics.size(),
				this.events.size(),
				this.rules.size(),
				this.rulefunctions.size(),
				this.graphManager.numberOfEdges(),
				this.stateMachines.size() ,
				//this.archives.size() ,
				this.scoreCards.size(),
				this.channels.size(),
				this.destinations.size(),
				this.decisionTables.size(),
				this.toolTips.size()));
		appendExtensionInfo(builder);
		projectViewInfo = builder.toString();
	}

	@SuppressWarnings("unchecked")
	private TSENode groupNodes(List<TSENode> objects, String title, TSEColor groupColor) {
		// ((EntityLayoutManager) this.getLayoutManager()).addGroupConstraint(objects);

		TSENode groupNode = (TSENode) this.graphManager.getMainDisplayGraph().addNode();
		TSEGraph childGraph = (TSEGraph) this.graphManager.addGraph();

		this.graphManager.getEventManager().setFireEvents(false);

		Iterator<TSENode> i = objects.iterator();
		TSENode currNode;
		List<TSEEdge> edgeList = new LinkedList<TSEEdge>();
		while (i.hasNext()) {
			currNode = (TSENode) i.next();
			edgeList.addAll(currNode.inEdges());
			edgeList.addAll(currNode.outEdges());
			if(!hidden){
			this.graphManager.getMainDisplayGraph().remove(currNode);
			}else{
			this.graphManager.getMainDisplayGraph().hideGraph().remove(currNode);
			}

			childGraph.insert(currNode);
		}

		// now also move the edges that we saved
		Iterator edgeIter = edgeList.iterator();
		TSEEdge currEdge;
		while (edgeIter.hasNext()) {
			currEdge = (TSEEdge) edgeIter.next();
			if(!hidden){
			this.graphManager.getMainDisplayGraph().remove(currEdge);
			}
			this.graphManager.insert(currEdge);
		}

		// for concept and event diagrams we have a certain orientation
		if (groupColor == CONCEPT_GROUP_COLOR || groupColor == EVENT_GROUP_COLOR) {
			((EntityLayoutManager) this.getLayoutManager()).setHierarchicalOptions(childGraph);
		} else {
			if (this.LAYOUT_HIERARCHICAL) {
				((EntityLayoutManager) this.getLayoutManager()).setMainGraphHierarchicalOptions(childGraph);
			} else {
				((EntityLayoutManager) this.getLayoutManager()).setMainGraphOrthogonalOptions(childGraph);
			}
		}

		groupNode.setChildGraph(childGraph);
		groupNode.setName(title);
		TSENestingManager.expand(groupNode);
		((TSEChildGraphUI) groupNode.getUI()).setFillColor(groupColor);

		this.graphManager.getEventManager().setFireEvents(true);

		return groupNode;
	}

	@SuppressWarnings("unchecked")
	private void ungroupNodes(TSENode node, List<TSENode> objects) {

		try{
			if(node == null)return;
			this.graphManager.getEventManager().setFireEvents(false);

			TSEGraph mainGraph = (TSEGraph) this.graphManager.getMainDisplayGraph();
			Iterator<TSENode> i = objects.iterator();
			TSENode currNode;
			List<TSEEdge> edgeList = new LinkedList<TSEEdge>();
			while (i.hasNext()) {
				currNode = (TSENode) i.next();
				edgeList.addAll(currNode.inEdges());
				edgeList.addAll(currNode.outEdges());
				if(currNode!=null)
					node.getChildGraph().remove(currNode);
				mainGraph.insert(currNode);
			}
			Iterator edgeIter = edgeList.iterator();
			TSEEdge currEdge;
			while (edgeIter.hasNext()) {
				currEdge = (TSEEdge) edgeIter.next();
				//TODO: To investigate why this is written
				//Commented as there is no need to keep this
				//				mainGraph.remove(currEdge);
				this.graphManager.insert(currEdge);
			}

			if(node ==  conceptGroupNode || node ==  eventGroupNode){
				restoreRealtionshipLinks(objects);
			}

			this.graphManager.discard(node.getChildGraph());

			node.setChildGraph(null);
			mainGraph.discard(node);
			node = null;

			this.graphManager.getEventManager().setFireEvents(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This will restore all internal relationship links
	 * between the Concepts and events
	 * @param objects
	 */
	private void restoreRealtionshipLinks(List<TSENode> objects){
		for(TSENode tsEntityNode:objects){
			EntityNodeData entityNodeData = (EntityNodeData)tsEntityNode.getUserObject();

			if(entityNodeData.getUserObject() instanceof Concept){
				Concept concept= (Concept)entityNodeData.getUserObject();
				if(concept.getSuperConcept()!=null){
					Concept parent = concept.getSuperConcept();
					super.createRelationship(tsEntityNode, parent, DrawingCanvas.INHERITANCE_LINK_TYPE, "Inherits From");
				}
				for(PropertyDefinition property:concept.getProperties()){
					if(property.getType()== PROPERTY_TYPES.CONCEPT_REFERENCE){
						createRelationship(tsEntityNode, IndexUtils.getConcept(
								getProjectName(), property.getConceptTypePath()),
								DrawingCanvas.REFERENCE_LINK_TYPE, property.getName());
					}
					if(property.getType()== PROPERTY_TYPES.CONCEPT){
						createRelationship(tsEntityNode, IndexUtils.getConcept(
								getProjectName(), property.getConceptTypePath()),
								DrawingCanvas.CONTAINMENT_LINK_TYPE, property.getName());
					}
				}
			}else if(entityNodeData.getUserObject() instanceof Event){
				Event event= (Event)entityNodeData.getUserObject();
				if(event.getSuperEvent()!=null){
					Event parent = event.getSuperEvent();
					super.createRelationship(tsEntityNode, parent, DrawingCanvas.INHERITANCE_LINK_TYPE, "Inherits From");
				}
			}
		}
	}

	//	public void save() {
	//		// System.out.println("Not saving to .projectview");
	//	}

	@SuppressWarnings("unchecked")
	public void generateRules() {
		Iterator iter = this.studioProject.getRuleElements().iterator();
		while (iter.hasNext()) {
			RuleElement re = (RuleElement) iter.next();
			Compilable rule = re.getRule();
			if (rule instanceof RuleFunction) {
				if (((RuleFunction) rule).isVirtual()) {
					this.createRuleFunction((RuleFunction) rule, true);
				} else {
					this.createRuleFunction((RuleFunction) rule, false);
				}
			} else if (rule instanceof Rule) {
				this.createRule((Rule) rule);
			}
		}
	}


	@SuppressWarnings("unchecked")
	/*public void generateArchives() {
		Iterator iter = this.studioProject.getArchiveElements().iterator();
		while (iter.hasNext()) {
			ArchiveElement ar = (ArchiveElement) iter.next();
			ArchiveResource arResource = ar.getArchive();
			TSENode tsArchive = this.createArchive(arResource, ar.getFolder() + ar.getName(), this.EAR_TYPE);
			Iterator i = arResource.eContents().iterator();
			while (i.hasNext()) {
				Object resource = i.next();
				if (resource instanceof BusinessEventsArchiveResource) {
					BusinessEventsArchiveResource bar = (BusinessEventsArchiveResource) resource;
					TSENode tsBAR = this.createArchive(bar, ar.getFolder() + ar.getName() + "/" + bar.getName(), this.BAR_TYPE);
					this.allLinks.add(this.createLink(tsArchive, tsBAR, TSEPolylineEdgeUI.LINE_STYLE_DASH_DOT));

					if (bar.isAllRuleSets()) {
						//						if (this.isShowArchiveAllRulesLinks()) {
						//							// TODO: get all rule elements and process them
						//						}
						//						else {
						this.createNote(tsBAR, "All Rulesets");
						//						}
					} else {
						//						if (this.isShowArchiveRulesLinks()) {
						EList<String> includedRuleSets = bar.getIncludedRuleSets();
						for (String ruleSet : includedRuleSets) {
							List<RuleElement> rulesFromRuleSet = IndexUtils.getRulesFromRuleSet(this.getProjectName(), ruleSet);
							for (RuleElement ruleElement : rulesFromRuleSet) {
								processRuleElement(tsBAR, ruleElement);
							}
						}
						//						}
					}

					Iterator destIter = bar.getInputDestinations().iterator();
					InputDestination inputDest;
					TSENode tsNode;

					while (destIter.hasNext()) {
						inputDest = (InputDestination) destIter.next();
						Destination destination = IndexUtils.getDestination(this.getProjectName(), inputDest.getDestinationURI());
						if (destination == null) {
							// could not find destination
							// System.out.println("Could not find destination "+inputDest.getDestinationURI());
							continue;
						}
						if (this.nodeMap.containsKey(destination.getGUID())) {
							tsNode = this.nodeMap.get(destination.getGUID());
						} else {
							tsNode = this.createDestination(destination);
						}

						//						if (this.isShowArchiveDestLinks()) {
						this.allLinks.add(this.createLink(tsBAR, tsNode, TSEPolylineEdgeUI.LINE_STYLE_DOT));
						//						}
					}

					this.processStartShutdownFunctions("Startup", tsBAR, bar.getStartupActions());
					this.processStartShutdownFunctions("Shutdown", tsBAR, bar.getShutdownActions());
				} else if (resource instanceof SharedArchive) {
					SharedArchive sar = (SharedArchive) resource;
					TSENode tsSAR = this.createArchive(sar,
							ar.getFolder() + ar.getName() + "/" + sar.getName(), this.SAR_TYPE);
					this.allLinks.add(this.createLink(tsArchive, tsSAR, TSEPolylineEdgeUI.LINE_STYLE_DASH_DOT));
					// TODO, maybe:
					sar.getSharedResources();
				} else {
					System.err.println("Found unknown type of archive!");
				}
			}
		}
	}*/
	protected void generateContributorExtensions() {
		IProjectDiagramContributor[] contributors = ProjectDiagramUtils.getProjectDiagramContributors();
		for (IProjectDiagramContributor contributor : contributors) {
			contributor.generateContent(this);
		}
	}

	private void processRuleElement(TSENode tsBAR, RuleElement ruleElement) {
		Compilable rule = ruleElement.getRule();
		TSENode ruleNode = null;
		if (rule instanceof RuleFunction) {
			if (((RuleFunction) rule).isVirtual()) {
				ruleNode = this.createRuleFunction((RuleFunction) rule ,true);
			} else {
				ruleNode = this.createRuleFunction((RuleFunction) rule, false );
			}
		} else if (rule instanceof Rule) {
			ruleNode = this.createRule((Rule) rule );
		}
		if (ruleNode != null) {
			this.allLinks.add(this.createLink(tsBAR, ruleNode, TSEPolylineEdgeUI.LINE_STYLE_DOT));
		}
	}

	@SuppressWarnings("unchecked")
	private void processStartShutdownFunctions(String description, TSENode tsBAR, List RFs) {
		Iterator rfIter = RFs.iterator();
		TSENode tsRF = null;
		while (rfIter.hasNext()) {
			String startupFunc = (String) rfIter.next();
			// System.out.println(description + startupFunc);
			RuleElement rf = (RuleElement)
			IndexUtils.getRuleElement(this.getProjectName(), startupFunc, ELEMENT_TYPES.RULE_FUNCTION);
			if (rf == null) {
				System.err.println("Cannot find RF (startup/shutdown)!!!");
			} else {
				// System.out.println("BAR has startup func of: " + rf.getName());
				if (!this.nodeMap.containsKey(startupFunc)) {
					// System.err.println("RF not discovered yet: " + startupFunc);
					tsRF = this.createRuleFunction((RuleFunction) rf.getRule(), ((RuleFunction) rf.getRule()).isVirtual() );
				}
				tsRF = this.nodeMap.get(startupFunc);
				if (tsRF == null) {
					System.err.println("Still cannot find startup Rule Function!");
					continue;
				}
				this.allLinks.add(this.createLink(tsBAR, tsRF, TSEPolylineEdgeUI.LINE_STYLE_DOT,
						description));
			}
		}
	}

	// creating Shared Entity for artifacts which are imported from the project library
	protected TSENode createSharedEntity(DesignerElement entity){
		String entityGuid = sharedentityMap.get(entity.toString());
		if (nodeMap.containsKey(entityGuid)) {
			return nodeMap.get(entityGuid);
		}
		TSENode tsEvent = null;
		tsEvent = (TSENode) graphManager.getMainDisplayGraph().addNode();
		this.sharedentities.add(tsEvent);
		String newGUID = GUIDGenerator.getGUID();
		this.nodeMap.put(newGUID, tsEvent);
		this.sharedentityMap.put(entity.toString(),newGUID);
		this.populateTSNode(tsEvent,(SharedElement)entity);
		return tsEvent;

	}

	protected TSENode createEntity(Entity entity) {
		if (entity instanceof Event) {
			return this.createEvent((Event) entity );
		} else if (entity instanceof Scorecard) {
			return this.createScorecard((Scorecard) entity );
		} else if (entity instanceof Concept) {
			return this.createConcept((Concept) entity );
		}else if (entity instanceof StateMachine) {

			if(!this.nodeMap.containsKey(((StateMachine) entity).getGUID())){
				return this.createStateMachine((StateMachine) entity );
			}else{
				return null;
			}

		} else if (entity instanceof Channel) {
			return this.createChannel((Channel) entity );
		} else if (entity instanceof Destination) {
			return this.createDestination((Destination) entity );
		} else if (entity instanceof Domain) {
			if(!this.nodeMap.containsKey(((Domain) entity).getGUID())){
				return this.createDomain((Domain) entity );
			}else{
				return null;
			}

		}else if (entity instanceof Rule) {
			return this.createRule((Rule) entity );
		} else if (entity instanceof RuleFunction) {
			if (((RuleFunction) entity).isVirtual()) {
				return this.createRuleFunction((RuleFunction) entity, true );
			} else {
				return this.createRuleFunction((RuleFunction) entity, false );
			}
		} else if (entity instanceof RuleSet) {
			return this.createRuleSet((RuleSet) entity );
		} else if(AddonUtil.isViewsAddonInstalled()){
			if (entity instanceof Metric) {
				return this.createConcept((Metric) entity );
			} else {
				return null;
			}
			}else {
			return null;
		}
	}

//	protected TSENode createProcess(EObject processObj) {
//		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.useInstance(processObj);
//		String name = (String) processWrapper.getAttribute("name");
//		System.out.println("Creating process: " + name);
//
//		String processPath = processWrapper.getAttribute("folder") + "/" + name;
//		if (this.nodeMap.containsKey(processPath)) {
//			return this.nodeMap.get(processPath);
//		}
//		TSENode tsProcess = (TSENode) graphManager.getMainDisplayGraph().addNode();
//		tsProcess.setChildGraph(this.graphManager.addGraph());
//		this.processes.add(tsProcess);
//		this.nodeMap.put(processPath, tsProcess);
//		if(processChildNodesMap == null){
//			processChildNodesMap = new HashMap<String, Set<TSENode>>();
//		}
//		Set<TSENode> nodeSet = new HashSet<TSENode>();
//		nodeSet.add(tsProcess);
//		processChildNodesMap.put(processPath, nodeSet);
//		this.populateTSNode(tsProcess, processWrapper, nodeSet);
//		this.addLinksToOtherArtifacts(tsProcess, processObj);
//		this.getLayoutManager().setLeftToRightOrthHierarchicalOptions((TSEGraph) tsProcess.getChildGraph());
//		if (this.collapseProcesses) {
//			TSENestingManager.getManager(this.graphManager).collapseNode(tsProcess);
//		}
//		else {
//			TSENestingManager.getManager(this.graphManager).expandNode(tsProcess);
//		}
//
//		return tsProcess;
//	}

	public void setShowConcepts(boolean showConcepts) {
		if (!showConcepts) {
			this.showObjects(this.concepts, true);
			hidden=true;
		}
		else{
			this.showObjects(this.concepts, false);
			//hidden=false;
		}
	}
	public void setShowMetrics(boolean showMetrics) {
		if (!showMetrics) {
			this.showObjects(this.metrics, true);
		}
		else{
			this.showObjects(this.metrics, false);

		}
	}

	public void setShowRuleFunctions(boolean showRuleFunctions) {
		if (!showRuleFunctions) {
			this.showObjects(this.rulefunctions, true);
			hidden=true;
		}
		else {
			this.showObjects(this.rulefunctions, false);
			//hidden=false;
		}
	}

	public void setShowArchiveAllRulesLinks(boolean showArchiveAllRulesLinks) {
		if(showArchiveAllRulesLinks){
			for (int i = 0; i < this.allLinks.size(); i++) {
				this.allLinks.get(i).setSelected(true);
			}
		} else {
			for (int i = 0; i < this.allLinks.size(); i++) {
				if(this.allLinks.get(i) != null){
					this.allLinks.get(i).setSelected(false);
				}
			}
		}
	}

	public void setShowEvents(boolean showEvents) {
		if (!showEvents) {
			this.showObjects(this.events, true);
			hidden=true;
		}
		else{
			this.showObjects(this.events, false);
		}
	}

	public void setShowRules(boolean showRules) {
		if (!showRules) {
			this.showObjects(this.rules, true);
			hidden=true;
		}
		else{
			this.showObjects(this.rules, false);
			//hidden=false;
		}
	}

	public void setShowStateMachines(boolean showStateMachines) {
		if (!showStateMachines) {
			this.showObjects(this.stateMachines, true);
		}
		else{
			this.showObjects(this.stateMachines, false);
		}
	}

	public void setShowDecisionTables(boolean showDecisionTables) {
		if (!showDecisionTables) {
			this.showObjects(this.decisionTables, true);
		}
		else{
			this.showObjects(this.decisionTables, false);
		}
	}
	public void setShowScoreCards(boolean showScoreCards) {
		if (!showScoreCards) {
			this.showObjects(this.scoreCards, true);
		}
		else {
			this.showObjects(this.scoreCards, false);
		}
	}

	public void setGroupConceptsInSameArea(boolean groupConceptsInSameArea) {
		if(isRefresh()){
			if(groupConceptsInSameArea){
				this.conceptGroupNode = this.groupNodes(this.concepts, "Concepts", this.CONCEPT_GROUP_COLOR);
				return;
			}
		}

		if (!this.groupConceptsInSameArea && groupConceptsInSameArea ) {
			this.conceptGroupNode = this.groupNodes(this.concepts, "Concepts", this.CONCEPT_GROUP_COLOR);
		}
		else if (this.groupConceptsInSameArea && !groupConceptsInSameArea) {
			this.ungroupNodes(this.conceptGroupNode, this.concepts);
		}

		this.groupConceptsInSameArea = groupConceptsInSameArea;
	}

	public void setGroupEventsInSameArea(boolean groupEventsInSameArea) {
		if(isRefresh()){
			if(groupEventsInSameArea){
				this.eventGroupNode = this.groupNodes(this.events, "Events", this.EVENT_GROUP_COLOR);
				return;
			}
		}

		if (!this.groupEventsInSameArea && groupEventsInSameArea ) {
			this.eventGroupNode = this.groupNodes(this.events, "Events", this.EVENT_GROUP_COLOR);
		}
		else if (this.groupEventsInSameArea && !groupEventsInSameArea) {
			this.ungroupNodes(this.eventGroupNode, this.events);
		}
		this.groupEventsInSameArea = groupEventsInSameArea;
	}

	public void setGroupRulesInSameArea(boolean groupRulesInSameArea) {
		if(isRefresh()){
			if(groupRulesInSameArea){
				this.ruleGroupNode = this.groupNodes(this.rules, "Rules", this.RULE_GROUP_COLOR);
				return;
			}
		}
		if (!this.groupRulesInSameArea && groupRulesInSameArea ) {
			this.ruleGroupNode = this.groupNodes(this.rules, "Rules", this.RULE_GROUP_COLOR);
		}
		else if (this.groupRulesInSameArea && !groupRulesInSameArea) {
			this.ungroupNodes(this.ruleGroupNode, this.rules);
		}
		this.groupRulesInSameArea = groupRulesInSameArea;
	}

	public void setGroupRuleFunctionsInSameArea(boolean groupRuleFunctionsInSameArea) {
		if(isRefresh()){
			if(groupRuleFunctionsInSameArea){
				this.ruleFunctionGroupNode = this.groupNodes(this.rulefunctions, "RuleFunctions", this.RF_GROUP_COLOR);
				return;
			}
		}
		if (!this.groupRuleFunctionsInSameArea && groupRuleFunctionsInSameArea) {
			this.ruleFunctionGroupNode = this.groupNodes(this.rulefunctions, "RuleFunctions", this.RF_GROUP_COLOR);
		}
		else if (this.groupRuleFunctionsInSameArea && !groupRuleFunctionsInSameArea) {
			this.ungroupNodes(this.ruleFunctionGroupNode, this.rulefunctions);
		}
		this.groupRuleFunctionsInSameArea = groupRuleFunctionsInSameArea;
	}

	public void setShowArchives(boolean showArchives) {
		/*if (!showArchives) {
			this.showObjects(this.archives, true);
		}
		else if (showArchives) {
			this.showObjects(this.archives, false);
		}*/
	}

	public void setShowChannels(boolean showChannels) {
		if (!showChannels) {
			this.showObjects(this.channels, true);
			this.showObjects(this.sharedResources, true);
			this.showObjects(this.destinations, true);
		}
		else if (showChannels) {
			this.showObjects(this.channels, false);
			this.showObjects(this.sharedResources, false);
			this.showObjects(this.destinations, false);
		}
	}

	public void setShowDomainModels(boolean showDomainModels) {
		if (!showDomainModels) {
			this.showObjects(this.domains, true);
		}
		else if (showDomainModels) {
			this.showObjects(this.domains, false);
		}
	}

	public void setShowArchivedDestinations(boolean showArchivedDestinations) {
		if (!showArchivedDestinations) {
			this.showObjects(this.destinations, true);
		}
		else if (showArchivedDestinations) {
			this.showObjects(this.destinations, false);
		}
	}

	public void showObjects(List<TSENode> objects, boolean hide) {
		if (hide) {
			TSEHidingManager.getManager(this.graphManager).hide(objects, null);
		}
		else {
			TSEHidingManager.getManager(this.graphManager).unhide(objects, null, true);
		}
	}

	public void setShowScopeLinks(boolean showScopeLinks) {
		if (!showScopeLinks) {
			this.showLinks(this.scopeLinks, true);
		}
		else if (showScopeLinks) {
			this.showLinks(this.scopeLinks, false);
		}
	}

	public void setShowUsageLinks(boolean showUsageLinks) {
		if (!showUsageLinks) {
			this.showLinks(this.usageLinks, true);
		}
		else if (showUsageLinks) {
			this.showLinks(this.usageLinks, false);
		}
	}

	public void setShowTooltips(boolean showTooltips) {
		if (projectDiagramMap.get(PALETTE_SHOW_TOOLTIPS_TITLE) && !showTooltips) {
			((TSEHidingManager) TSEHidingManager.getManager(this.graphManager)).hide(this.toolTips, null);
		}
		else if (!projectDiagramMap.get(PALETTE_SHOW_TOOLTIPS_TITLE) && showTooltips) {
			((TSEHidingManager) TSEHidingManager.getManager(this.graphManager)).unhide(this.toolTips, null, true);
		}
	}

	public ELEMENT_TYPES getEntityType() {
		return null;
	}

	protected String getEntityTypeName() {
		return "project";
	}

	public String getExtension() {
		return Messages.getString("PV_extension");
	}

	public void layoutAndRefresh() {

		int nrEdges = 3000;
		int nrMaxEdges = 1500;

		//Preference option (in project preference page) to execute fast layout
		boolean fastLayout = prefStore.getBoolean(StudioPreferenceConstants.FAST_LAYOUT);
		if(fastLayout){
			if(StudioConfig.getInstance().getProperty("be.studio.project.diagram.nrEdges", "3000") != null){
				nrEdges = Integer.parseInt(
						StudioConfig.getInstance().getProperty("be.studio.project.diagram.nrEdges", "3000"));
			}
				if(this.graphManager.numberOfEdges() > nrEdges){
					System.out.println("Filtering out usage links due to large diagram.");

					this.showLinks(this.usageLinks, true);
					System.out.println("After hiding usage links, we have: " + this.graphManager.numberOfEdges() + " edges.");
					openError(getEditor().getSite().getShell(), "Warning Message"
							, "As the number of edges in this diagram exceeds the set limit (" + nrEdges +
							"), the usage links are hidden. The current displayed edges are " + this.graphManager.numberOfEdges() +
							". To modify the default number of edges, " +
							"set be.studio.project.diagram.nrEdges in studio.tra."
							 );

					if(StudioConfig.getInstance().getProperty("be.studio.project.diagram.nrMaxEdges", "1500") != null){
						nrMaxEdges = Integer.parseInt(
								StudioConfig.getInstance().getProperty("be.studio.project.diagram.nrMaxEdges", "1500"));
						if (this.graphManager.numberOfEdges() > nrMaxEdges) {
							System.out.println("Executing fast symmetric layout due to large diagram despite filtering.");
							this.getLayoutManager().setSymmetricOptions();
						}
					}
				}
			}


		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				getLayoutManager().callBatchGlobalLayout();
				drawingCanvas.drawGraph();
				drawingCanvas.repaint();
			}
		});
		IWorkbench workBench = PlatformUI.getWorkbench();
		if(workBench != null){
			IWorkbenchWindow workBenchWindow = workBench.getActiveWorkbenchWindow();
			if (workBenchWindow != null){
				IWorkbenchPage page = workBenchWindow.getActivePage();
				if(page!=null){
					IEditorPart editorPart = page.getActiveEditor();
					if(editorPart != null){
						IEditorSite editorSite = editorPart.getEditorSite();
						if(editorSite != null){
							refreshOverview(editorSite, true, true);
						}
					}
				}
			}
		}
	}

	/**
	 *
	 * @param Concept concept
	 * @return
	 */
	private TSENode createConcept(Concept concept) {
		try
		{
		if (concept.getGUID() != null && this.nodeMap.containsKey(concept.getGUID())) {
			return this.nodeMap.get(concept.getGUID());
		}

		TSENode tsConcept = (TSENode) this.graphManager.getMainDisplayGraph().addNode();
		if(AddonUtil.isViewsAddonInstalled()){
		if(concept.isMetric()){
		this.metrics.add(tsConcept);
		}else{
		this.concepts.add(tsConcept);
		}}else{
		this.concepts.add(tsConcept);
		}
		this.nodeMap.put(concept.getGUID(), tsConcept);

		String parentPath = ((Concept) concept).getSuperConceptPath();
		Concept parent = null;
		if (parentPath != null && parentPath.length() > 0) {
			parent = IndexUtils.getConcept(getProjectName(), parentPath);
		}

		if (parent != null && this.isGeneratingModel) {
			this.allLinks.add(this.createRelationship(tsConcept, parent, DrawingCanvas.INHERITANCE_LINK_TYPE, "Inherits From"));
		}

		this.populateTSNode(tsConcept, (Concept) concept);

		{
			//StateMachine
			StateMachine sm;
			TSENode tsSM;

			for (String stateMachine:concept.getStateMachinePaths()) {
				Entity smEntity = IndexUtils.getEntity(getProjectName(), stateMachine);
				//To make sure it is state machine and to avoid class cast exception
				if(smEntity instanceof StateMachine){
					sm = (StateMachine)smEntity;
					if(this.nodeMap.containsKey(sm.getGUID())){
						tsSM = (TSENode)this.nodeMap.get(sm.getGUID());
					}else{
						tsSM = this.createStateMachine(sm);
					}
					this.allLinks.add(this.createLink(tsConcept, tsSM, TSEPolylineEdgeUI.LINE_STYLE_DOT));
				}
			}
		}
		{
			//Domain
			EList<PropertyDefinition> propDefns = concept.getAllProperties();
			for(PropertyDefinition propDef:propDefns){
				EList<DomainInstance> dmInstances = propDef.getDomainInstances();

				for(DomainInstance instance:dmInstances){
					Domain dm;
					TSENode tsDM;
					dm = (Domain)IndexUtils.getEntity(getProjectName(), instance.getResourcePath());
					if (dm != null) {
						if(this.nodeMap.containsKey(dm.getGUID())){
							tsDM = (TSENode)this.nodeMap.get(dm.getGUID());
						}else{
							tsDM = this.createDomain(dm);
						}
						this.allLinks.add(this.createLink(tsConcept, tsDM, TSEPolylineEdgeUI.LINE_STYLE_DOT));
					}
					else {
						System.err.println("Warning: skipping invalid domain model " + instance.getResourcePath());
					}
				}
			}
		}
		createSharedResource(concept, tsConcept);
		return tsConcept;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * @author smarathe
	 *  Creates a dependency between DBConcept and the respective Shared Resource
	 * @param concept @link Concept
	 * @param tsConcept @link TSENode
	 */
	private void createSharedResource(Concept concept, TSENode tsConcept ) {

		//TODO@Sridhar : Provide API for accesing properties of Shared Resources

		PropertyMap propertyMap = concept.getExtendedProperties();
		if (propertyMap != null) {
			EList<Entity> list = propertyMap.getProperties();
			ListIterator<Entity> listIterator = list.listIterator();
			while(listIterator.hasNext()) {
				Entity entity = listIterator.next();
				if (entity instanceof SimplePropertyImpl) {
					SimplePropertyImpl property = (SimplePropertyImpl)entity;
					TSENode tsSr;
					if (property.getName().equals("JDBC_RESOURCE")) {
						String propertyValue = property.getValue();
						if (propertyValue != null) {
							if (SharedResourceGUIDMap == null) {
								SharedResourceGUIDMap = new HashMap<String,String>();
							}
							if (SharedResourceGUIDMap.containsKey(propertyValue)) {
								String guid=SharedResourceGUIDMap.get(propertyValue);
								tsSr=nodeMap.get(guid);
							} else {
								tsSr = (TSENode) graphManager.getMainDisplayGraph().addNode();
								 tsSr.setAttribute(sharedResourceNodeAtrribute, getProject().getName() + propertyValue);
								if(sharedResources == null) {
									sharedResources = new ArrayList<TSENode>();
								}
								sharedResources.add(tsSr);
								SharedResourceGUIDMap.put(propertyValue,GUIDGenerator.getGUID());
								this.nodeMap.put(SharedResourceGUIDMap.get(propertyValue), tsSr);
								TSEImage imageType = this.JDBC_CONNECTION_IMAGE;
								String name = propertyValue;
								while (name.contains("/")) {
									name = name.substring(name.indexOf("/")+1);
								}
								if (name.contains(".")) {
									name = name.substring(0,name.indexOf("."));
								}
								showToolTip(tsSr, SharedResourcePropertyUtil.getProperties(getProject().findMember(new Path(propertyValue))));
								this.populateTSNode(tsSr, name,imageType );
							}
							this.allLinks.add(this.createLink(tsConcept,tsSr, TSEPolylineEdgeUI.LINE_STYLE_DOT));
						}
					}
				}
			}
		}
	}


	@SuppressWarnings("unchecked")
	private TSENode createRuleSet(RuleSet entity ) {
		
		
		// System.out.println("Creating ruleset: " + entity.getName());
		TSENode tsRuleSet = (TSENode) graphManager.getMainDisplayGraph().addNode();

		this.populateTSNode(tsRuleSet, (Entity) entity, new TSEImage("/icons/rules-set.png"));

		Iterator iter = entity.getRules().iterator();
		Rule rule;
		TSENode tsRule;
		while (iter.hasNext()) {
			rule = (Rule) iter.next();
			tsRule = this.createRule(rule);
			this.allLinks.add(this.createLink(tsRuleSet, tsRule, TSEPolylineEdgeUI.LINE_STYLE_DOT));
			System.out.println("Created link between ruleset: " + entity.getName() + " and rule " + rule.getName());
		}

		return tsRuleSet;
	}

	private TSENode createRuleFunction(RuleFunction entity, boolean isVirtual ) {
		
		
		
			
		String rfPath = entity.getFolder() + entity.getName();
		if (this.nodeMap.containsKey(rfPath)) {
			return this.nodeMap.get(rfPath);
		}
		TSENode tsRF = (TSENode) graphManager.getMainDisplayGraph().addNode();
		this.rulefunctions.add(tsRF);
		TSEImage image;

		if (isVirtual) {
			image = this.VRULEFUNCTION_IMAGE;
		} else {
			image = this.RULEFUNCTION_IMAGE;
		}

		this.populateTSNode(tsRF, (Entity) entity, image);
		this.nodeMap.put(rfPath, tsRF);

		//		if (this.showScopeLinks) {
		this.addCompilableScopeLinks(tsRF);
		//		}

		this.addCompilableUsageLinks(tsRF);

		return tsRF;
	}

	
	
	@Override
	protected TSENode createImplementation(Implementation implementation ) {

		//		if (! this.showDecisionTables) {
		//			return null;
		//		}

		String implPath = implementation.getFolder() + implementation.getName();
		if (this.nodeMap.containsKey(implPath)) {
			return this.nodeMap.get(implPath);
		}
		TSEImage image = this.DECISION_TABLE_IMAGE;
		TSENode tsImplementation = (TSENode) this.graphManager.getMainDisplayGraph().addNode();
		this.decisionTables.add(tsImplementation);
		this.populateTSNode(tsImplementation, implementation, image);

		// System.out.println("adding table: " + implPath);
		this.nodeMap.put(implPath, tsImplementation);
		//Parent rule function
		String rfPath = implementation.getImplements();
		String projectLocation = getProjectLocation();
		String ruleFuncFile = projectLocation + rfPath + ".rulefunction";
		try {
			RuleFunction ruleFunction = IndexUtils.parseRuleFunctionFile(getProjectName(), ruleFuncFile);

			if (ruleFunction != null && isGeneratingModel) {
				this.createRelationship(tsImplementation,
						ruleFunction,
						DrawingCanvas.INHERITANCE_LINK_TYPE,
				"implements");
			}
		} catch (Exception e) {
			// could be due to a FileNotFoundException if the rule file was deleted
		}
		return tsImplementation;
	}

	private TSENode createRule(Rule entity ) {
		
		
		String rulePath = entity.getFolder() + entity.getName();
		if (this.nodeMap.containsKey(rulePath)) {
			return this.nodeMap.get(rulePath);
		}
		TSENode tsRule = (TSENode) graphManager.getMainDisplayGraph().addNode();
		tsRule.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
		this.rules.add(tsRule);
		this.nodeMap.put(rulePath, tsRule);
		this.populateTSNode(tsRule, (Entity) entity, this.RULE_IMAGE);

		//		if (this.showScopeLinks) {
		this.addCompilableScopeLinks(tsRule);
		//		}

		this.addCompilableUsageLinks(tsRule);
		
		return tsRule;
	}

	private TSENode createScorecard(Scorecard entity ) {
		
		
		TSENode tsScorecard = (TSENode) graphManager.getMainDisplayGraph().addNode();
		this.scoreCards.add(tsScorecard);
		this.populateTSNode(tsScorecard, (Entity) entity, this.SCORECARD_IMAGE);
		this.nodeMap.put(entity.getGUID(), tsScorecard);

		return tsScorecard;
	}

	private TSENode createDestination(Destination entity ) {
		
		TSENode tsDest = (TSENode) graphManager.getMainDisplayGraph().addNode();
		// TODO: GUID is guaranteed to be unique, use this instead of event URI!
		this.nodeMap.put(entity.getGUID(), tsDest);
		this.destinations.add(tsDest);
		// this.nodeMap.put(entity.getEventURI(), tsDest);
		this.populateTSNode(tsDest, (Entity) entity, this.DESTINATION_IMAGE);

		String eventURI = entity.getEventURI();
		Event event = (Event) IndexUtils.getEntity(this.getProjectName(), eventURI, ELEMENT_TYPES.SIMPLE_EVENT);

		TSENode tsEvent = null;
		if (event != null && nodeMap.containsKey(event.getGUID())) {
			tsEvent = nodeMap.get(event.getGUID());
		} else if (event != null) {
			// System.out.println("Event not discovered yet!");
			tsEvent = this.createEvent(event );
		}

		if (event != null) {
			TSEEdge edge = this.createLink(tsDest, tsEvent, TSEPolylineEdgeUI.LINE_STYLE_SHORT_DASH);
			this.allLinks.add(edge);
			(edge.addLabel()).setName("Default Event");
		}

		return tsDest;
	}

	// TODO: copied from EventDiagramManager
	protected TSENode createEvent(Event entity ) {

		// changed
		if (nodeMap.containsKey(entity.getGUID())) {
			return nodeMap.get(entity.getGUID());
		}

		TSENode tsEvent = (TSENode) graphManager.getMainDisplayGraph().addNode();
		this.events.add(tsEvent);
		nodeMap.put(entity.getGUID(), tsEvent);

		this.populateTSNode(tsEvent, (Event) entity);
		// DIFF
		String parentPath = ((Event) entity).getSuperEventPath();
		Event parent = null;
		if (parentPath != null && parentPath.length() > 0) {
			// DIFF
			parent = IndexUtils.getSimpleEvent(getProjectName(), parentPath);
		}

		if (parent != null && isGeneratingModel) {
			this.allLinks.add(this.createRelationship(tsEvent, parent, DrawingCanvas.INHERITANCE_LINK_TYPE, "Inherits From"));
		}

		return tsEvent;
	}

	/**
	 * Create Shared Resource associated with the Channel
	 * @author smarathe
	 * @param entity {@link Channel}
	 * @param tsChannelNode {@link TSENode}
	 */
	private void createSharedResource (Channel entity,TSENode tsChannelNode ) {

		// TODO@Sridhar : Provide API for accesing properties of Shared Resources
		DriverConfig dc = entity.getDriver();
		if (dc != null) {
			String name = "";
			TSEImage imageType = null;
			String reference = dc.getReference();
			TSENode tsSr = null;
			if (reference != null && !reference.equals("")) {
				if (SharedResourceGUIDMap == null) {
					SharedResourceGUIDMap = new HashMap<String,String>();
				}
				if (SharedResourceGUIDMap.containsKey(reference)) {
					String guid = SharedResourceGUIDMap.get(reference);
					 tsSr = this.nodeMap.get(guid);
				} else {
					tsSr = (TSENode) graphManager.getMainDisplayGraph().addNode();

					tsSr.setAttribute(sharedResourceNodeAtrribute, reference);
					if(sharedResources == null) {
						sharedResources = new ArrayList<TSENode>();
					}
					sharedResources.add(tsSr);
					String guid = GUIDGenerator.getGUID();
					SharedResourceGUIDMap.put(reference,guid);
					this.nodeMap.put(guid, tsSr);
					IFile sharedFile = this.getProject().getFile(reference);
					SharedElement element = null;
					if (!sharedFile.exists()) {
						element = CommonIndexUtils.getSharedElement(entity.getOwnerProjectName(), reference);
					}
					String extension = sharedFile.getFileExtension();
					if(extension.equals("sharedhttp")) {
						imageType = this.HTTP_CONNECTION_IMAGE;
						showIdentityResource(tsSr, sharedFile);
					}else if(extension.equals("sharedjmscon")) {
						imageType = this.JMS_CONNECTION_IMAGE;
						showIdentityResource(tsSr, sharedFile);
					}else if(extension.equals("rvtransport")) {
						imageType = this.RENDEZVOUS_TRANSPORT_IMAGE;
					}else if(extension.equals("hawk")){
						imageType = this.HAWK_IMAGE;
					}
					else if(extension.equals("sharedascon") || extension.equals("as3")){
						imageType = this.AS_IMAGE;
					}
					else if(extension.equals("ftl")){
						imageType = this.FTL_IMAGE;
					}else if(extension.equals("sharedmqttcon")){
						imageType = this.MQTT_IMAGE;
					}
					name = reference;
					while (name.contains("/")) {
						name = name.substring(name.indexOf("/")+1);
					}
					if (name.contains(".")) {
						name = name.substring(0,name.indexOf("."));
					}
					this.populateTSNode(tsSr, element== null ? name : element, imageType );
					showToolTip(tsSr, SharedResourcePropertyUtil.getProperties(sharedFile));
				}
				TSEEdge tsEdge = this.createLink(tsChannelNode,tsSr, TSEPolylineEdgeUI.LINE_STYLE_DOT);
				this.allLinks.add(tsEdge);
			}
		}
	}


	private void showToolTip(TSENode tsSr, Map<String, String> properties) {
	
		StringBuffer toolTip = new StringBuffer("");
		Iterator it = properties.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        toolTip.append("<p><b>" + pairs.getKey() + "</b>: " + pairs.getValue() + "</p>");
	      
	    }
	    tsSr.setTooltipText(toolTip.toString());

	}

	private boolean showIdentityResource(TSENode sharedResourceNode, IFile file) {
		String filePath = file.getLocation().toOSString();
		if (filePath == null || new File(filePath).length() == 0)
			return false;
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setNamespaceAware(true);
			DocumentBuilder builder = fact.newDocumentBuilder();
			FileInputStream fis = new FileInputStream(filePath);
			Document doc = builder.parse(fis);
			Element root = doc.getDocumentElement();
			NodeList fileNodeList = root.getChildNodes();
			for (int n=0; n<fileNodeList.getLength(); n++) {
				Node fileNode = fileNodeList.item(n);

				if (fileNode == null || !isValidFileNode(fileNode)) {
					continue;
				}
				if(isIdentityNode(sharedResourceNode, fileNode, file)) {
					return true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	private boolean isIdentityNode(TSENode sharedResourceNode, Node fileNode, IFile file) {
		String fileNodeName = fileNode.getLocalName();
		Node childNode = null;
		if(fileNodeName.equals("identity")) {
			String reference = "";
			NodeList nodeList = fileNode.getChildNodes();
			if(nodeList != null) {
				if(nodeList.item(0) != null){
					reference = (nodeList.item(0)).getNodeValue();
				}
			}
			boolean isUseSsl = isUseSsl(file);
			if(!isUseSsl) {
				reference = "";
			}
			TSENode tsSr = null;
			if (reference != null && !reference.equals("")) {
				if (SharedResourceGUIDMap == null) {
					SharedResourceGUIDMap = new HashMap<String,String>();
				}
				if (SharedResourceGUIDMap.containsKey(reference)) {
					String guid = SharedResourceGUIDMap.get(reference);
					 tsSr = this.nodeMap.get(guid);

				} else {
				    tsSr = (TSENode) graphManager.getMainDisplayGraph().addNode();
				    String name = reference;
					while (name.contains("/")) {
						name = name.substring(name.indexOf("/")+1);
					}
					if (name.contains(".")) {
						name = name.substring(0,name.indexOf("."));
					}
				    this.populateTSNode(tsSr, name, new TSEImage(this.IDENTITY_RESOURCE_IMAGE));
				    showToolTip(tsSr, SharedResourcePropertyUtil.getProperties(getProject().findMember(new Path(reference))));
				    tsSr.setAttribute(sharedResourceNodeAtrribute, getProject().getName() + reference);
				    if(sharedResources == null) {
				    	sharedResources = new ArrayList<TSENode>();
				    }
				    sharedResources.add(tsSr);
				    String guid = GUIDGenerator.getGUID();
					SharedResourceGUIDMap.put(reference,guid);
					this.nodeMap.put(guid, tsSr);
				}
			this.createLink(sharedResourceNode, tsSr, TSEPolylineEdgeUI.LINE_STYLE_DOT);
			return true;
			} else if(reference.equals("")){
				return false;
			}
		}
		childNode = fileNode.getFirstChild();
		while(fileNode.hasChildNodes()) {
			while(childNode != null && !isValidFileNode(childNode)) {
				childNode = childNode.getNextSibling();
			}
			if (childNode != null && isValidFileNode(childNode)) {
				if(!isIdentityNode(sharedResourceNode, childNode, file)) {
					childNode = childNode.getNextSibling();
				} else {
					return true;
				}
			} else {
				return false;
			}
		}
		if(childNode != null && childNode.getNextSibling() != null) {
			while(childNode == null || !isValidFileNode(childNode)) {
				childNode = childNode.getNextSibling();
			}
			if (fileNode != null || isValidFileNode(fileNode)) {
				isIdentityNode(sharedResourceNode, childNode, file);
			}
		}

		return false;
	}

	private boolean isUseSsl(IFile file) {
		String filePath = file.getLocation().toOSString();
		if (filePath == null || new File(filePath).length() == 0)
			return false;
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setNamespaceAware(true);
			DocumentBuilder builder = fact.newDocumentBuilder();
			FileInputStream fis = new FileInputStream(filePath);
			Document doc = builder.parse(fis);
			Element root = doc.getDocumentElement();
			NodeList fileNodeList = root.getChildNodes();
			return isUseSsl(fileNodeList);


		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private boolean isUseSsl(NodeList fileNodeList) {
		for (int n=0; n<fileNodeList.getLength(); n++) {
			Node fileNode = fileNodeList.item(n);
			if(isUseSsl(fileNode)) {
				return true;
			} else if (fileNode.hasChildNodes())
				if(!isUseSsl(fileNode.getChildNodes())) {
					continue;
				} else {
					return true;
				}
			}
		return false;
	}

	private boolean isUseSsl(Node fileNode) {

		if(fileNode == null || !isValidFileNode(fileNode)) {
				return false;
		} else if(fileNode.getLocalName().equals("useSsl")) {
			NodeList nodeListSSL = fileNode.getChildNodes();
			String useSSL = nodeListSSL.item(0).getNodeValue();
			if (useSSL.equals("false"))
				return false;
			else
				return true;
		}
		return false;
	}

	private static boolean isValidFileNode(Node node) {
		if (node != null)
			return (isValidFileNode(node.getLocalName()));
		return false;
	}

	private static boolean isValidFileNode(String name) {
		if (name == null)
			return false;
		String ignList[] = { "#text", "#comment" };
		for (String ign: ignList) {
			if (ign.equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private TSENode createChannel(Channel entity ) {

		if (nodeMap.containsKey(entity.getGUID())) {
			return nodeMap.get(entity.getGUID());
		}
		TSENode tsCh = (TSENode) graphManager.getMainDisplayGraph().addNode();
		this.nodeMap.put(entity.getGUID(), tsCh);
		this.channels.add(tsCh);
		this.populateTSNode(tsCh, (Entity) entity, this.CHANNEL_IMAGE);
		createSharedResource(entity, tsCh );
		Iterator iter = entity.getDriver().getDestinations().iterator();
		Destination dest;
		TSENode tsDest;
		while (iter.hasNext()) {
			dest = (Destination) iter.next();
			tsDest = this.createDestination(dest);
			this.allLinks.add(this.createLink(tsCh, tsDest, TSEPolylineEdgeUI.LINE_STYLE_DOT));
		}
		return tsCh;
	}
	
	

	//TODO Fix this as per new model
	private TSENode createStateMachine(StateMachine sm) {
		TSENode tsSM = (TSENode) this.graphManager.getMainDisplayGraph().addNode();
		this.nodeMap.put(sm.getGUID(), tsSM);
		this.stateMachines.add(tsSM);
		this.populateTSNode(tsSM, sm);
		return tsSM;
	}

	private TSENode createDomain(Domain dm) {
		TSENode tsDM = (TSENode) this.graphManager.getMainDisplayGraph().addNode();
		this.nodeMap.put(dm.getGUID(), tsDM);
		this.domains.add(tsDM);
		this.populateTSNode(tsDM, dm);

		String domainParentPath = dm.getSuperDomainPath();
		Domain domainParent = null;
		if (domainParentPath != null && domainParentPath.length() > 0) {
			domainParent = IndexUtils.getDomain(getProjectName(), domainParentPath);
		}

		if (domainParent != null && isGeneratingModel) {
			this.allLinks.add(this.createRelationship(tsDM, domainParent, DrawingCanvas.INHERITANCE_LINK_TYPE, "Inherits From"));
		}

		return tsDM;
	}

	/*private TSENode createArchive(ArchiveResource ar, String path, int type) {
		TSENode tsArchive = (TSENode) graphManager.getMainDisplayGraph().addNode();
		TSEImage image;

		switch (type) {
		case EAR_TYPE:
			image = this.EAR_IMAGE;
			break;
		case BAR_TYPE:
			image = this.BAR_IMAGE;
			break;
		case SAR_TYPE:
			image = this.SAR_IMAGE;
			break;
		default:
			image = null;
		System.out.println("unknown type!");
		}
		this.archives.add(tsArchive);
		this.nodeMap.put(path, tsArchive);
		this.populateTSNode(tsArchive, ar, image);
		return tsArchive;
	}*/

	public TSENode createNote(TSENode annotatedNode, String noteText) {
		TSENode tsNote = (TSENode) graphManager.getMainDisplayGraph().addNode();
		tsNote.setName(noteText);
		NoteNodeUI nodeUI = new NoteNodeUI();
		tsNote.setUI(nodeUI);

		// tsNote.setSize(40, 25);
		tsNote.setShape(TSPolygonShape.fromString("[ 6 (0, 0) (100, 0) (100, 75) (85, 75) (85, 100) (0, 100) ]"));
		tsNote.setResizability(TSESolidObject.RESIZABILITY_TIGHT_FIT);
		if (annotatedNode != null) {
			TSEEdge edge = this.createLink(annotatedNode, tsNote, TSEPolylineEdgeUI.LINE_STYLE_SHORT_DASH);
			this.allLinks.add(edge);
			if (edge.getUI() instanceof TSEPolylineEdgeUI) {
				((TSEPolylineEdgeUI) edge.getUI()).setLineColor(TSEColor.darkYellow);
			}
		}
		return tsNote;
	}

	// TODO: copied from ConceptDiagramManager
	@SuppressWarnings("unchecked")
	public void populateTSNode(TSENode node, Concept concept) {

		StringBuffer tooltip = null;
		tooltip = new StringBuffer(ConceptDiagramManager.createToolTip(concept));

		EntityNodeData cndata = new EntityNodeData(node);

		Iterator propIter = concept.getProperties().iterator();
		PropertyDefinition property;
		String name;

		while (propIter.hasNext()) {
			property = (PropertyDefinition) propIter.next();
			name = property.getName();

			if (property.isArray()) {
				name += "[ ]";
			}

			if (property.getHistorySize() > 0) {
				name += " (" + property.getHistorySize() + ")";
			}

			switch (property.getType()) {
			case CONCEPT_REFERENCE:
				cndata.addAttribute(name, EntityNodeItem.CONCEPTREF_ATTRIBUTE, property);
				if (this.isGeneratingModel) {
					this.createRelationship(node, IndexUtils.getConcept(
							getProjectName(), property.getConceptTypePath()),
							DrawingCanvas.REFERENCE_LINK_TYPE, property.getName());
				}
				tooltip.append("<p>" + name + " : reference</p>");
				break;
			case CONCEPT:
				cndata.addAttribute(name, EntityNodeItem.CONCEPTCON_ATTRIBUTE, property);
				if (this.isGeneratingModel) {
					this.createRelationship(node, IndexUtils.getConcept(
							getProjectName(), property.getConceptTypePath()),
							DrawingCanvas.CONTAINMENT_LINK_TYPE, property.getName());
				}
				tooltip.append("<p>" + name + " : containment</p>");
				break;
			case DATE_TIME:
				cndata.addAttribute(name, EntityNodeItem.DATETIME_ATTRIBUTE, property);
				tooltip.append("<p>" + name + " : DateTime</p>");
				break;
			case BOOLEAN:
				cndata.addAttribute(name, EntityNodeItem.BOOLEAN_ATTRIBUTE, property);
				tooltip.append("<p>" + name + " : Boolean</p>");
				break;
			case DOUBLE:
				cndata.addAttribute(name, EntityNodeItem.DOUBLE_ATTRIBUTE, property);
				tooltip.append("<p>" + name + " : Double</p>");
				break;
			case LONG:
				cndata.addAttribute(name, EntityNodeItem.LONG_ATTRIBUTE, property);
				tooltip.append("<p>" + name + " : Long</p>");
				break;
			case INTEGER:
				cndata.addAttribute(name, EntityNodeItem.INTEGER_ATTRIBUTE, property);
				tooltip.append("<p>" + name + " : Integer</p>");
				break;
			case STRING:
				cndata.addAttribute(name, EntityNodeItem.STRING_ATTRIBUTE, property);
				tooltip.append("<p>" + name + " : String</p>");
				break;
			default:
				System.err.println("WARNING: unknown property type!");
			}
		}

		//		if (this.showTooltips) {
		node.setTooltipText(tooltip.toString());


		cndata.setCollapsed(true);
		cndata.setEntity(concept.getName(), concept);
		cndata.setLabel(concept.getName());
		cndata.setUserObject(concept);

		node.setName(concept.getName());
		node.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
		if(AddonUtil.isViewsAddonInstalled()){
		if(concept.isMetric()) {
			node.setUI((TSEObjectUI) this.metricEntityUI.clone());
		} else {
			node.setUI((TSEObjectUI) this.entityUI.clone());
		}}else {
			node.setUI((TSEObjectUI) this.entityUI.clone());
		}
		this.toolTips.add(tooltip);
		this.entityNodes.add(node);
	}

	// TODO: copied from EventDiagramManager
	// TODO: fix timeEventUI
	// TODO: fix entityNode list, need several, one is no longer enough
	@SuppressWarnings("unchecked")
	public void populateTSNode(TSENode node, Event event) {

		StringBuffer tooltip = null;

		//		if (this.showTooltips) {
		tooltip = new StringBuffer(EventDiagramManager.createToolTip(getProjectName(), event));
		//		}
		EntityNodeData cndata = new EntityNodeData(node);

		// DIFF:
		Iterator propIter = event.getProperties().iterator();
		PropertyDefinition property;
		String name;

		while (propIter.hasNext()) {
			property = (PropertyDefinition) propIter.next();
			name = property.getName();

			switch (property.getType()) {
			case DATE_TIME:
				cndata.addAttribute(name, EntityNodeItem.DATETIME_ATTRIBUTE, property);
				tooltip.append("<p>" + name + " : DateTime</p>");
				break;
			case BOOLEAN:
				cndata.addAttribute(name, EntityNodeItem.BOOLEAN_ATTRIBUTE, property);
				tooltip.append("<p>" + name + " : Boolean</p>");
				break;
			case DOUBLE:
				cndata.addAttribute(name, EntityNodeItem.DOUBLE_ATTRIBUTE, property);
				tooltip.append("<p>" + name + " : Double</p>");
				break;
			case LONG:
				cndata.addAttribute(name, EntityNodeItem.LONG_ATTRIBUTE, property);
				tooltip.append("<p>" + name + " : Long</p>");
				break;
			case INTEGER:
				cndata.addAttribute(name, EntityNodeItem.INTEGER_ATTRIBUTE, property);
				tooltip.append("<p>" + name + " : Integer</p>");
				break;
			case STRING:
				cndata.addAttribute(name, EntityNodeItem.STRING_ATTRIBUTE, property);
				tooltip.append("<p>" + name + " : String</p>");
				break;
			default:
				System.err.println("WARNING: unknown property type!");
			}

			if (event.getPayload() != null) {
				cndata.addAttribute("Payload", EntityNodeItem.ATTRIBUTE, event.getPayload());
			}
		}
		node.setTooltipText(tooltip.toString());

		cndata.setCollapsed(true);
		cndata.setEntity(event.getName(), event);
		cndata.setLabel(event.getName());
		cndata.setUserObject(event);

		node.setName(event.getName());

		if (IndexUtils.getElementType(event) == ELEMENT_TYPES.TIME_EVENT) {
			// TODO: this is slow, should instantiate once
			EntityNodeUI nodeUI = new EntityNodeUI();
			nodeUI.setImage(EntityNodeUI.TIME_EVENT_IMAGE);
			nodeUI.setGradient(EventDiagramManager.START_COLOR, EventDiagramManager.END_COLOR);
			nodeUI.setBorderColor(EventDiagramManager.START_COLOR);
			node.setUI(nodeUI);
		} else {
			// wrong: node.setUI((TSEObjectUI) this.entityUI.clone());
			// TODO: very similar to above, need to improve:
			EntityNodeUI nodeUI = new EntityNodeUI();
			nodeUI.setImage(EntityNodeUI.SIMPLE_EVENT_IMAGE);
			nodeUI.setGradient(EventDiagramManager.START_COLOR, EventDiagramManager.END_COLOR);
			nodeUI.setBorderColor(EventDiagramManager.START_COLOR);
			node.setUI(nodeUI);
		}

		this.toolTips.add(tooltip);
		this.entityNodes.add(node);
	}

	// This is for all other types, like channels, destinations, bars...
	public void populateTSNode(TSENode node, Object entity, TSEImage image) {
		TSEImageNodeUI ui = new TSEImageNodeUI();
		ui.setImage(image);
		ui.setBorderDrawn(false);
		node.setUI(ui);

		String name = null;
		@SuppressWarnings("unused")
		String guid = null;
		StringBuffer tooltip = null;

		// TODO: guid not necessary
		if (entity instanceof Entity) {
			name = ((Entity) entity).getName();
			guid = ((Entity) entity).getGUID();

			if(entity instanceof Scorecard) {
				tooltip = new StringBuffer(createScoreCardTooltip((Scorecard)entity));
				node.setTooltipText(tooltip.toString());
			}
			else if(entity instanceof Channel) {
				tooltip = new StringBuffer(createChannelTooltip((Channel)entity));
				node.setTooltipText(tooltip.toString());
			} else if(entity instanceof Destination){
				tooltip = new StringBuffer(createDestinationTooltip((Destination)entity));
				node.setTooltipText(tooltip.toString());
			}else if(entity instanceof Rule){
				tooltip=new StringBuffer(createRuleToolTip((Rule)entity));
				node.setTooltipText(tooltip.toString());
			}else if(entity instanceof RuleFunction){
				tooltip=new StringBuffer(createRuleFunctionToolTip((RuleFunction)entity));
				node.setTooltipText(tooltip.toString());
			}

		} else if (entity instanceof ArchiveResource) {
			name = ((ArchiveResource) entity).getName();
			guid = ((ArchiveResource) entity).getName();
		} else if (entity instanceof Implementation) {
			Implementation implementation = (Implementation)entity;
			name = implementation.getName();
			guid = implementation.getFolder() + name;
			tooltip=new StringBuffer(createDecisionTableToolTip((Implementation)entity));
			node.setTooltipText(tooltip.toString());
		} else if (entity instanceof SharedElement) {
			SharedElement implementation = (SharedElement)entity;
			name = implementation.getName();
			guid = implementation.getEntryPath() + name;
		}
		
		else
		{
			name=(String)entity;
			guid=SharedResourceGUIDMap.get(name);
		}
		node.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);

		// TODO: TSLabelBuilder builder = this.graphManager.getLabelBuilder();

		if (USELABELS) {
			TSENodeLabel label = (TSENodeLabel) node.addLabel();
			label.setName(name);
			if((getLayoutManager() != null) && (label != null)){
				getLayoutManager().setNodeLabelOptions(label);
			}
		}
		else {
			node.setName(name);
		}

		node.setUserObject(entity);

		// TODO: this is suspect!
		// this.nodeMap.put(guid, node);
	}

	

	// for Shared Elements
	// This is for all other types, like channels, destinations, bars...
	public void populateTSNode(TSENode node, SharedElement entity) {
		TSEImageNodeUI ui = new TSEImageNodeUI();
		ELEMENT_TYPES type=	entity.getElementType();
		if(type == ELEMENT_TYPES.CONCEPT ){

			/*Image base = new Image(device,"/icons/concept.png");
			ImageDescriptor impose =StudioUIPlugin.getImageDescriptor("/icons/eararchive.gif");
			TSEImage image = new TSEImage(new DecorationOverlayIcon(base, impose,IDecoration.TOP_LEFT).createImage().getImageData());
			 */
			ui.setImage(EntityNodeUI.CONCEPT_IMAGE);
		} else if(type == ELEMENT_TYPES.DESTINATION){
			ui.setImage(this.DESTINATION_IMAGE);
		} else if(type == ELEMENT_TYPES.SCORECARD){
			ui.setImage(this.SCORECARD_IMAGE);
		} else if(type == ELEMENT_TYPES.SIMPLE_EVENT){
			ui.setImage(EntityNodeUI.SIMPLE_EVENT_IMAGE);
		} else if(type == ELEMENT_TYPES.TIME_EVENT){
			ui.setImage(EntityNodeUI.TIME_EVENT_IMAGE);
		} else if(type == ELEMENT_TYPES.RULE){
			ui.setImage(this.RULE_IMAGE);
		} else if(type == ELEMENT_TYPES.RULE_FUNCTION){
			ui.setImage(this.RULEFUNCTION_IMAGE);
		} else if(type == ELEMENT_TYPES.DECISION_TABLE){
			ui.setImage(this.DECISION_TABLE_IMAGE);
		} else if(type == ELEMENT_TYPES.STATE_MACHINE){
			ui.setImage(this.STATEMACHINE_IMAGE);
		} else if(type == ELEMENT_TYPES.CHANNEL){
			ui.setImage(this.CHANNEL_IMAGE);
		}  else if(type == ELEMENT_TYPES.DOMAIN){
			ui.setImage(this.DOMAIN_MODEL_IMAGE);
		}


		ui.setBorderDrawn(true);
		node.setUI(ui);

		String name = null;
		@SuppressWarnings("unused")
		String guid = null;
		StringBuffer tooltip = null;

		// TODO: guid not necessary
		name = entity.getName();
		//guid = ((Entity) entity).getGUID();
		node.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);

		// TODO: TSLabelBuilder builder = this.graphManager.getLabelBuilder();

		if (USELABELS) {
			TSENodeLabel label = (TSENodeLabel) node.addLabel();
			label.setName(name);
			if((getLayoutManager() != null) && (label != null)){
				getLayoutManager().setNodeLabelOptions(label);
			}
		}
		else {
			node.setName(name);
		}
		node.setUserObject(entity);

		// TODO: this is suspect!
		// this.nodeMap.put(guid, node);
	}

	//for state machine
	public void populateTSNode(TSENode node, StateMachine sm) {
		TSEImageNodeUI ui = new TSEImageNodeUI();
		ui.setImage(this.STATEMACHINE_IMAGE);
		ui.setBorderDrawn(false);
		node.setUI(ui);
		StringBuffer tooltip = null;
		tooltip = new StringBuffer(createStateMachineTooltip(sm));
		node.setTooltipText(tooltip.toString());
		node.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
		if (USELABELS) {
			TSENodeLabel label = (TSENodeLabel) node.addLabel();
			label.setName(sm.getName());
			if((getLayoutManager() != null) && (label != null)){
				getLayoutManager().setNodeLabelOptions(label);
			}
		}
		else {
			node.setName(sm.getName());
		}
		node.setUserObject(sm);
	}

	//for domain model
	public void populateTSNode(TSENode node, Domain dm) {
		TSEImageNodeUI ui = new TSEImageNodeUI();
		ui.setImage(this.DOMAIN_MODEL_IMAGE);
		ui.setBorderDrawn(false);
		node.setUI(ui);
		StringBuffer tooltip = null;
		tooltip = new StringBuffer(createDomainModelTooltip(dm));
		node.setTooltipText(tooltip.toString());
		node.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
		if (USELABELS) {
			TSENodeLabel label = (TSENodeLabel) node.addLabel();
			label.setName(dm.getName());
			if((getLayoutManager() != null) && (label != null)){
				getLayoutManager().setNodeLabelOptions(label);
			}
		}
		else {
			node.setName(dm.getName());
		}
		node.setUserObject(dm);
	}

	public void populateTSNode(TSENode node, Implementation impl) {
		TSEImageNodeUI ui = new TSEImageNodeUI();
		ui.setImage(this.DECISION_TABLE_IMAGE);
		ui.setBorderDrawn(false);
		node.setUI(ui);
		if (USELABELS) {
			TSENodeLabel label = (TSENodeLabel) node.addLabel();
			label.setName(impl.getName());
			if((getLayoutManager() != null) && (label != null)){
				getLayoutManager().setNodeLabelOptions(label);
			}
		}
		else {
			node.setName(impl.getName());
		}
		node.setUserObject(impl);
	}

	// XYZ
	public TSEEdge createLink(TSENode src, TSENode tgt, int edgeType, String desc) {
		TSEEdge edge = this.createLink(src, tgt, edgeType);
		(edge.addLabel()).setName(desc);
		return edge;
	}

	public TSEEdge createLink(TSENode src, TSENode tgt, int edgeType) {
		TSEEdge edge = (TSEEdge) this.graphManager.addEdge(src, tgt);
		// TODO: maybe set the edge UI to be something else
		TSEPolylineEdgeUI edgeUI = new TSEPolylineEdgeUI();
		edgeUI.setLineStyle(edgeType);
		if (edge == null) {
			System.err.println("edge is NULL!!!");
		}else{
			edge.setUI(edgeUI);
		}
		return edge;
	}

	private void addCompilableScopeLinks(TSENode tsRule) {
		
		
		Compilable rule = (Compilable) tsRule.getUserObject();
		Entity entity = null;
		//		Collection<Symbol> symbols = rule.getSymbols().getSymbolMap().values();
		for (Symbol symbol : rule.getSymbols().getSymbolList()) {
			DesignerElement element = IndexUtils.getElement(
					this.getProjectName(), symbol.getType());
			if(element instanceof SharedElement){
				this.createLinkToSharedEntity(tsRule, (SharedElement)element, symbol.getIdName(), this.scopeLinks);
			} else if (element instanceof EntityElement) {
				entity = ((EntityElement) element).getEntity();
				this.createLinkToEntity(tsRule, entity, symbol.getIdName(), this.scopeLinks);
			} else {
				// System.err.println("Could not resolve symbol: "	+ symbol.getType());
				continue;
			}

			
		}
	}

	public void showLinks(List<TSEEdge> objects, boolean hide) {
		if (hide) {
			TSEHidingManager.getManager(this.graphManager).hide(null, objects);
		}
		else {
			TSEHidingManager.getManager(this.graphManager).unhide(null, objects, true);
		}
	}


	////////////// Links from R/RFs to items they use inside ///////////////////

	/**
	 * This method creates links from rules/rule functions to other RFs/Entities that the
	 * rule/RF may create or modify.
	 *
	 * This is different than the addCompilableScopeLinks() method which just adds links from
	 * the rules/RFs to the entities they have in their scope.
	 */
	private void addCompilableUsageLinks(TSENode tsRule) {
		RuleElement re;
		// here we get a Rule (which implements Entity and extends Compilable)
		Compilable r = (Compilable) tsRule.getUserObject();
		re = this.getRuleElement(r.getFolder() + r.getName());
		if (re == null) {
			System.err.println("Could not find R/RF: "+ r.getFolder() + r.getName());
			return;
		}
	
		ScopeBlock scope = re.getScope();
		// this is the root level, or global scope, and does not need to be processed,
		// as it contains no rule refs. Process child scopes instead.
		EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
		for (ScopeBlock scopeBlock : childScopeDefs) {
			processScope(tsRule, scopeBlock, true,re);
		}
	}

	private void processScope(TSENode source, ScopeBlock scope, boolean isRoot, RuleElement re) {
		EList<ElementReference> refs = scope.getRefs();
		for (ElementReference elementReference : refs) {
			boolean isContinue = true;
			Object resolvedElement = ElementReferenceResolver.resolveElement(
					elementReference,
					ElementReferenceResolver.createResolutionContext(scope));
			if ((scope.getType() == BaseRulesParser.DECLARE_SCOPE || scope
					.getType() == BaseRulesParser.SCOPE_SCOPE)
					&& (resolvedElement instanceof EntityElementImpl)) {
				EList<GlobalVariableDef> globalVariables = re
						.getGlobalVariables();
				for (GlobalVariableDef globalVariableDef : globalVariables) {
					String gvType = globalVariableDef.getType();
					String gv = gvType.substring(gvType.indexOf(".")+1);
					if ((gv.equalsIgnoreCase(elementReference.getName()))) {
						isContinue = false;
					}
				}
			}
			if (isContinue) {
				processRuleElementUsed(elementReference, source,
						resolvedElement, isRoot);
			}
		}
	}

	private void createUsesLink(TSENode source, Entity entity){
		Set<String> value = new HashSet<String>();
		if((source != null) && (entity != null)){
			if(this.usesLinksMap.containsKey(source)){
				value=this.usesLinksMap.get(source);
				if((this.usesLinksMap.get(source)).contains(entity.getName())){
					return;
				}
				else{
					value.add(entity.getName());
					this.usesLinksMap.put(source, value);
					this.createLinkToEntity(source, entity, "uses", this.usageLinks);
				}
			} else{
				value.add(entity.getName());
				this.usesLinksMap.put(source, value);
				this.createLinkToEntity(source, entity, "uses", this.usageLinks);
			}

		}
	}

	private TSEEdge createCallsLink(TSENode source, TSENode existingNode){
		TSEEdge edge = null;
		if((source != null) && (existingNode != null)){
			if(this.callsLinksMap.containsKey(source)){
				if(this.callsLinksMap.get(source).equals(existingNode)){
					return null;
				}
				else{
					this.callsLinksMap.put(source, existingNode);
					edge = this.createLink(source, existingNode, TSEPolylineEdgeUI.LINE_STYLE_DOT, "calls");
				}
			} else{
				this.callsLinksMap.put(source, existingNode);
				edge = this.createLink(source, existingNode, TSEPolylineEdgeUI.LINE_STYLE_DOT, "calls");
			}
		}
		return edge;
	}

	private void processRuleElementUsed(ElementReference reference, TSENode source, Object resolvedElement, boolean isRoot) {
		// if it is an entity (concept, event, scorecard)
		if (!(resolvedElement instanceof RuleElement)) {
			if (resolvedElement != null) {
				if(resolvedElement instanceof SharedEntityElement){
					createLinkToSharedEntity(source,(SharedElement) resolvedElement, "uses", this.usageLinks);
				}else if (resolvedElement instanceof EntityElement) {
					Entity entity = ((EntityElement) resolvedElement).getEntity();
					createUsesLink(source, entity);
				} else if(resolvedElement instanceof PropertyDefinition) {
					if(((PropertyDefinition) resolvedElement).getOwner() instanceof Scorecard){
						Entity entity = ((Scorecard)(((PropertyDefinition) resolvedElement).getOwner()));
						createUsesLink(source, entity);
					}
				} else if (resolvedElement instanceof FunctionRec) {
					String fnName = ((FunctionRec) resolvedElement).function.getName().localName;
					if (isMetricFunction(fnName)) { // this check might not really be necessary, since only metric calls resolve to FunctionRecs
						if (reference.getQualifier() != null && reference.getQualifier().getBinding() instanceof EntityElement) {
							Entity entity = ((EntityElement) reference.getQualifier().getBinding()).getEntity();
							createUsesLink(source, entity);
						}
					}
				}
			}
			return;
		}

		// otherwise, if it's a rule or a rule function:
		RuleElement ruleElement = (RuleElement) resolvedElement;
		TSENode reNode = this.getRuleElementNode(ruleElement);

		if (! this.processedRuleElements.contains(ruleElement) || isRoot) {
			this.processedRuleElements.add(ruleElement);
			TSEEdge edge = createCallsLink(source, reNode);
			if(edge != null){
				this.usageLinks.add(edge);
				this.allLinks.add(edge);
			}
			// recursively process the Rule/RF that this rule calls/invokes itself.
			ScopeBlock scope = ruleElement.getScope();
			EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
			for (ScopeBlock scopeBlock : childScopeDefs) {
				processScope(reNode, scopeBlock, false,ruleElement);
			}
		}
	}

	private boolean isMetricFunction(String fnName) {
		return Arrays.asList(EMFMetricMethodModelFunction.getAvailableFunctions()).contains(fnName);
	}

	// XYZ
	public void createLinkToEntity(TSENode srcNode, Entity entity, String label, List edgeList) {
		TSENode tsEntity = null;
		if (this.nodeMap.containsKey(entity.getGUID())) {
			tsEntity = this.nodeMap.get(entity.getGUID());
		} else {
			// it means we have not discovered that entity type yet
			// so let's create it, after we check what type it is
			if (entity instanceof Scorecard) {
				tsEntity = this.createScorecard((Scorecard) entity);
			} else if (entity instanceof Concept) {
				tsEntity = this.createConcept((Concept) entity);
			} else if (entity instanceof Event) {
				tsEntity = this.createEvent((Event) entity);
			} else {
				System.err.println("Unknown entity type in scope!");
			}
		}

		if(tsEntity != null) {
			TSEEdge edge = this.createLink(srcNode, tsEntity,
					TSEPolylineEdgeUI.LINE_STYLE_DOT, label);
			edgeList.add(edge);
			this.allLinks.add(edge);
		}
	}
	public void createLinkToSharedEntity(TSENode srcNode, SharedElement entity, String label, List edgeList) {
		TSENode tsEntity = null;
		String entityGuid;
		entityGuid = sharedentityMap.get(entity.toString());
		if (this.nodeMap.containsKey(entityGuid)) {
			tsEntity = this.nodeMap.get(entityGuid);
		}else {
			// it means we have not discovered that entity type yet
			// so let's create it, after we check what type it is
			if(entity instanceof SharedEntityElement){
				tsEntity = this.createSharedEntity((DesignerElement) entity);
			}else {
				System.err.println("Unknown entity type in scope!");
			}
		}

		if(tsEntity != null) {
			TSEEdge edge = this.createLink(srcNode, tsEntity,
					TSEPolylineEdgeUI.LINE_STYLE_DOT, label);
			edgeList.add(edge);
			this.allLinks.add(edge);
		}
	}


	public void createConnectorLinkToEntity(TSENode srcNode, TSEConnector connector, Entity entity, String label, List edgeList) {
		TSENode tsEntity = null;
		if (this.nodeMap.containsKey(entity.getGUID())) {
			tsEntity = this.nodeMap.get(entity.getGUID());
		} else {
			// it means we have not discovered that entity type yet
			// so let's create it, after we check what type it is
			if (entity instanceof Scorecard) {
				tsEntity = this.createScorecard((Scorecard) entity);
			} else if (entity instanceof Concept) {
				tsEntity = this.createConcept((Concept) entity);
			} else if (entity instanceof Event) {
				tsEntity = this.createEvent((Event) entity);
			} else {
				System.err.println("Unknown entity type in scope!");
			}
		}

		if(tsEntity != null) {
			TSEEdge edge = this.createLink(srcNode, tsEntity,
					TSEPolylineEdgeUI.LINE_STYLE_DOT, label);
			edge.setSourceConnector(connector);
			edgeList.add(edge);
			this.allLinks.add(edge);
		}
	}

	// XYZ
	public void createLinkToRuleElement(TSENode processTaskNode, RuleElement re, String label, List edgeList) {
		TSENode tsRE = this.getRuleElementNode(re);

		if (tsRE != null) {
			TSEEdge edge = this.createLink(processTaskNode, tsRE, TSEPolylineEdgeUI.LINE_STYLE_DOT, label);
			edgeList.add(edge);
			this.allLinks.add(edge);
		}
	}

	public RuleElement getRuleElement(String name) {
		EList<RuleElement> elements = this.studioProject.getRuleElements();
		for (RuleElement ruleElement : elements) {
			if (name.equals(ruleElement.getFolder() + ruleElement.getName())) {
				return ruleElement;
			}
		}
		return null;
	}

	private TSENode getRuleElementNode(RuleElement ruleElement) {
		TSENode existingNode = (TSENode) this.ruleElements.get(ruleElement);
		if (existingNode == null) {
			// this rule element has not been discovered yet, so let's create a node for it
			Entity entity = (Entity)ruleElement.getRule();
			if (entity instanceof Scorecard) {
				existingNode = this.createScorecard((Scorecard) entity);
			} else if (entity instanceof Concept) {
				existingNode = this.createConcept((Concept) entity);
			} else if (entity instanceof Event) {
				existingNode = this.createEvent((Event) entity);
			} else if (entity instanceof RuleFunction) {
				existingNode = this.createRuleFunction((RuleFunction) entity, ((RuleFunction)entity).isVirtual());
			} else if (entity instanceof Rule) {
				existingNode = this.createRule((Rule) entity);
			} else {
				System.err.println("Unknown entity type in scope: " + entity);
			}

			this.ruleElements.put(ruleElement, existingNode);
		}

		return existingNode;
	}



	///////////////////// Analyzer functions ///////////////////////

	public static final int RULES_ONLY = 0;
	public static final int ALL_RFS = 1;
	public static final int VRFS_ONLY = 2;
	public static final int RFS_ONLY = 3;

	/**
	 * @param flag RULES_ONLY, ALL_RFS, VRFS_ONLY, RFS_ONLY
	 * @return
	 */
	public List getCompilables(int flag) {
		LinkedList list = new LinkedList();
		Iterator iter = this.studioProject.getRuleElements().iterator();
		while (iter.hasNext()) {
			RuleElement re = (RuleElement) iter.next();
			Compilable rule = re.getRule();
			if (rule instanceof RuleFunction) {
				if ((flag & ALL_RFS) == 0) {
					list.add(rule);
				}
				else {
					if (((RuleFunction) rule).isVirtual()) {
						if ((flag & VRFS_ONLY) == 0) {
							list.add(rule);
						}
					}
					else {
						if ((flag & RFS_ONLY) == 0) {
							list.add(rule);
						}
					}
				}
			} else if (rule instanceof Rule) {
				if ((flag & RULES_ONLY) == 0) {
					list.add(rule);
				}
			}
		}

		return list;
	}

	public class DiagramUpdateListener implements IStudioModelChangedListener {

		public void modelChanged(StudioModelChangedEvent event) {
			try{
				final StudioModelDelta delta = event.getDelta();
				List<StudioProjectDelta> changedProjects = delta.getChangedProjects();
				for (StudioProjectDelta designerProjectDelta : changedProjects) {
					//					System.out.println(designerProjectDelta.toString());
					DesignerProject changedProject = designerProjectDelta.getChangedProject();
					if (changedProject==null || changedProject.getName()==null)
						continue;
					if (!changedProject.getName().equals(getProjectName())) {
						break; // not interested in these changes
					}
					if (designerProjectDelta.getType() == IStudioElementDelta.ADDED
							|| designerProjectDelta.getType() == IStudioElementDelta.REMOVED) {
						break; // the entire project was added/removed, refresh not (totally) applicable
					}
					List<DesignerElement> elementsToRefresh = new ArrayList<DesignerElement>();
					collectElementsToRefresh(designerProjectDelta, elementsToRefresh);
					for (DesignerElement designerElement : elementsToRefresh) {
						switch (((IStudioElementDelta) designerElement).getType()) {
						case IStudioElementDelta.ADDED:
							processAddedElement((IStudioElementDelta) designerElement);
							break;

						case IStudioElementDelta.REMOVED:
							processRemovedElement((IStudioElementDelta) designerElement);
							break;

						case IStudioElementDelta.CHANGED:
							processChangedElement((IStudioElementDelta) designerElement);
							break;

						default:
							break;
						}
					}
					//					layoutAndRefresh();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		private void processAddedElement(IStudioElementDelta delta) {
			DesignerElement affectedChild = delta.getAffectedChild();
			if (affectedChild instanceof EntityElement) {
				// this causes a TSRuntimeException
				//				createEntity(((EntityElement) affectedChild).getEntity());
			}
		}

		private void processChangedElement(IStudioElementDelta delta) {
			if (delta instanceof StudioElementDelta) {
				StudioElementDelta changeDelta = (StudioElementDelta) delta;
				@SuppressWarnings("unused")
				DesignerElement removedChild = changeDelta.getRemovedChild();
				@SuppressWarnings("unused")
				DesignerElement addedChild = changeDelta.getAddedChild();
			}
		}

		private void processRemovedElement(IStudioElementDelta delta) {
			@SuppressWarnings("unused")
			DesignerElement removedElement = delta.getAffectedChild();
			// remove the node corresponding to this element
		}

		protected void collectElementsToRefresh(ElementContainer delta, List<DesignerElement> elementsToRefresh) {
			EList<DesignerElement> entries = delta.getEntries();
			for (DesignerElement designerElement : entries) {
				if (designerElement instanceof ElementContainer) {
					collectElementsToRefresh((ElementContainer) designerElement, elementsToRefresh);
				} else if (designerElement instanceof StudioElementDelta) {
					elementsToRefresh.add(designerElement);
				}
			}
		}

	}

	public Map<String, Boolean> getDefaultMap() {
		return defaultMap;
	}

	public Map<String, Boolean> getProjectDiagramMap() {
		return projectDiagramMap;
	}

	//TODO: The Following has to be implemented

	public void setShowArchiveRulesLinks(boolean showArchiveRulesLinks) {
		//TODO
	}

	public void setShowRulesInFolders(boolean showRulesInFolders) {
		//TODO
	}

	//	public void setShowCollapsedStateMachines(boolean showCollapsedStateMachines) {
	//		//TODO
	//	}

	public ELEMENT_TYPES[] getEntityTypesDESIRED() {
		return new ELEMENT_TYPES[] { ELEMENT_TYPES.CONCEPT,
				ELEMENT_TYPES.SIMPLE_EVENT, ELEMENT_TYPES.TIME_EVENT,
				ELEMENT_TYPES.SCORECARD, ELEMENT_TYPES.STATE_MACHINE,
				ELEMENT_TYPES.CHANNEL, ELEMENT_TYPES.DESTINATION,
				ELEMENT_TYPES.RULE, ELEMENT_TYPES.RULE_SET,
				ELEMENT_TYPES.RULE_FUNCTION };
	}

	public ELEMENT_TYPES[] getEntityTypes() {
		return new ELEMENT_TYPES[] {ELEMENT_TYPES.CONCEPT,
				ELEMENT_TYPES.METRIC,
				ELEMENT_TYPES.RULE,
				ELEMENT_TYPES.RULE_FUNCTION,
				ELEMENT_TYPES.SIMPLE_EVENT,
				ELEMENT_TYPES.TIME_EVENT,
				ELEMENT_TYPES.STATE_MACHINE,
				ELEMENT_TYPES.CHANNEL,
				ELEMENT_TYPES.DESTINATION,
				ELEMENT_TYPES.SCORECARD,
				ELEMENT_TYPES.DECISION_TABLE,
				ELEMENT_TYPES.DOMAIN};
	}

	public String getProjectViewInfo() {
		return projectViewInfo;
	}

	public HashMap<String, Integer> getDefintionsMap() {
		return defintionsMap;
	}

	public void setDependencyLevel(int value) {
		this.DEPENDENCY_LEVEL = value;
	}

	public int getDependencyLevel() {
		return this.DEPENDENCY_LEVEL;
	}

	/**
	 * set the process children map
	 * @param processChildNodesMap
	 */
	public void setProcessChildNodesMap(Map<String, Set<TSENode>> processChildNodesMap) {
		this.processChildNodesMap = processChildNodesMap;
	}
	@Override
	public void propertyChange(
			PropertyChangeEvent event){
		super.propertyChange(event);
		try{
			String property=event.getProperty();
			ProjectDiagramManager programDiagramManager=(ProjectDiagramManager)(((ProjectDiagramEditor)editor).getDiagramManager());
			property = prefStore.getString(PROJECT_GRID);
			if(property.equals(PROJECT_NONE)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.none"), this.drawingCanvas);
			}else if(property.equals(PROJECT_LINES)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.lines"), this.drawingCanvas);
			}else if(property.equals(PROJECT_POINTS)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.points"), this.drawingCanvas);
			}
			DiagramUtils.refreshDiagram(programDiagramManager);
			OverviewUtils.refreshOverview(getEditor().getEditorSite(), true, true);
		}
		catch(Exception e)
		{
			EditorsUIPlugin.debug(this.getClass().getName(), e.getLocalizedMessage());
		}

	}
	
}