package com.tibco.cep.studio.ui.diagrams;


import static com.tibco.cep.diagramming.utils.DiagramUtils.gridType;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.DEPENDENCY_GRID;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.DEPENDENCY_LINES;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.DEPENDENCY_NONE;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.DEPENDENCY_POINTS;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.ui.diagrams.tools.DependencySelectTool;
import com.tibco.cep.studio.ui.editors.DiagramEditorInput;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.dependency.DependencyDiagramEditor;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.overview.OverviewUtils;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.complexity.TSEHidingManager;
import com.tomsawyer.interactive.swing.TSSwingCanvasPreferenceTailor;
import com.tomsawyer.util.preference.TSPreferenceData;
/**
 * 
 * @author ggrigore  
 * @author hitesh
 *
 */
public class DependencyDiagramManager extends ProjectDiagramManager {

	private TypeElement entity;
	private boolean showImmediateDependencyOnly;
	private int edgeCount = 1;
	private int DEPENDENCY_LEVEL = 1; 
	private IPreferenceStore prefStore;
	protected DependencySelectTool dependencySelectTool;
	
	// this is the constructor I'm using...
	public DependencyDiagramManager(EditorPart editor) {
		super(editor);
		TSEImage.setLoaderClass(this.getClass());
		this.localInit();
	}

	public DependencyDiagramManager(IProject project, IProgressMonitor monitor) {
		super(project, monitor);
		TSEImage.setLoaderClass(this.getClass());
		this.localInit();
	}

	private void localInit() {
		this.prefStore = EditorsUIPlugin.getDefault().getPreferenceStore();
		this.showImmediateDependencyOnly = this.prefStore.getBoolean(StudioPreferenceConstants.DEPENDENCY_IMMEDIATE);
	}


	public void openModel() {
		this.openEntireModel();
		this.showDependencyModel();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#setPreferences()
	 */
	@Override
	protected void setPreferences() {
		super.setPreferences();
		String gridType = this.prefStore.getString(StudioPreferenceConstants.DEPENDENCY_GRID);
		TSPreferenceData prefData = this.drawingCanvas.getPreferenceData();
		TSSwingCanvasPreferenceTailor swingTailor = new TSSwingCanvasPreferenceTailor(prefData);
		if(gridType.equalsIgnoreCase(StudioPreferenceConstants.DEPENDENCY_NONE)){
			gridType(Messages.getString("studio.preference.diagram.none"), this.drawingCanvas);
		}else if(gridType.equalsIgnoreCase(StudioPreferenceConstants.DEPENDENCY_LINES)){
			gridType(Messages.getString("studio.preference.diagram.lines"), this.drawingCanvas);
		} else if(gridType.equalsIgnoreCase(StudioPreferenceConstants.DEPENDENCY_POINTS)){
			gridType(Messages.getString("studio.preference.diagram.points"), this.drawingCanvas);
		}
		swingTailor.setAutoHideScrollBars(false);
	}
	
	protected void openEntireModel() {
		long startTime = System.currentTimeMillis();
		this.generateModel();
		super.generateRules();
		//super.generateArchives();		
		long endTime = System.currentTimeMillis();
		
		System.out.println("Generated diagram with "
				+ this.graphManager.numberOfNodes() + " nodes and "
				+ this.graphManager.numberOfEdges() + " edges in "
				+ (endTime - startTime) / 1000 + " seconds.");
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getSelectTool()
	 */
	public SelectTool getSelectTool(){
		if (this.dependencySelectTool == null) {
			this.dependencySelectTool = new DependencySelectTool(this);
		}
		return this.dependencySelectTool;
	}
	
	private void showDependencyModel() {
		// only show dependencies for this entity
		this.calculateDependencies();
		
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		System.out.println("Dependency diagram layout took: " + (endTime-startTime)/1000 + " seconds.");	
	}

	// we always generate and never save dependency diagrams
	public void save() { }

	public void generateModel() {
		if (super.getEditor() == null) {
			return;
		}
		
		super.generateModel();

		IFile entityFile = ((DiagramEditorInput) editor.getEditorInput()).getFile();
		String entityFullPath = entityFile.getFullPath().toString();
		String entityPath = entityFullPath.substring(this.project.getFullPath().toString().length(),
			entityFullPath.length() - entityFile.getFileExtension().length() - 1);

		this.entity = this.getEntity(entityPath);
		if (this.entity == null) {
			this.entity = this.getRule(entityPath);
		}
		if (this.entity == null) {
			this.entity = this.getArchive(entityPath);
		}
		if (this.entity == null) {
			this.entity = this.getDecision(entityPath);
		}
		
		if (this.studioProject == null) {
			System.out.println("Cannot find Studio project.");
		}
		else if (this.entity == null) {
			System.out.println("Cannot find selected entity in project.");
		}
	}
	
	public List<TSENode> findBARSwithAllRulesets(TSENode entityNode) {
		LinkedList<TSENode> barsWithAllRulesets = new LinkedList<TSENode>();
		Iterator iter = this.studioProject.getArchiveElements().iterator();
		while (iter.hasNext()) {
			ArchiveElement ar = (ArchiveElement) iter.next();
			ArchiveResource arResource = ar.getArchive();
			Iterator i = arResource.eContents().iterator();
			while (i.hasNext()) {
				Object resource = i.next();
				if (resource instanceof BusinessEventsArchiveResource) {
					BusinessEventsArchiveResource bar = (BusinessEventsArchiveResource) resource;
					if (bar.isAllRuleSets()) {
						// this bar is also affected by the rule we are creating dependency graph for
						TSENode barNode = (TSENode) this.nodeMap.get(ar.getFolder() + ar.getName() + "/" + bar.getName());
						if (barNode == null) {
							System.err.println("Cannot find bar including all rulesets, skipping.");
						}
						else {
							barsWithAllRulesets.add(barNode);
							// TODO: if draw all edges in project view preferences was set, do NOT add this edge as well
							//Temp fix to add only one edge
							if(edgeCount == 1) {
								this.graphManager.addEdge(entityNode, barNode);
							}
						}
					}
				}
			}
		}
		if (barsWithAllRulesets.isEmpty()) {
			barsWithAllRulesets = null;
		}
		edgeCount = 0;
		return barsWithAllRulesets;
	}
	
	public void calculateDependencies() {
		System.out.println("Before hiding, diagram has "
				+ graphManager.numberOfNodes() + " nodes and "
				+ graphManager.numberOfEdges() + " edges.");

		TSENode node = findEntityNode();
		if (node == null) {
			System.err.println("WARNING: project entity not found, showing entire project view.");
		}
		else {
			TSEHidingManager hideMgr = ((TSEHidingManager) TSEHidingManager.getManager(graphManager));
			graphManager.selectAll(true);
			hideMgr.hideSelected();
			graphManager.selectAll(false);
			LinkedList<TSENode> list = new LinkedList<TSENode>();
			list.add(node);
			List<TSENode> barsWithAllRulesets = findBARSwithAllRulesets(node);
			if (barsWithAllRulesets != null) {
				list.addAll(barsWithAllRulesets);
			}
			hideMgr.unhide(list, null, true);
			hideMgr.findHiddenNeighbors(node, getDependencyLevel());
			hideMgr.unhide(hideMgr.getResultNodeList(), hideMgr.getResultEdgeList(), true);
			node.setSelected(true);
			System.out.println("Diagram now has "
					+ graphManager.numberOfNodes() + " nodes and "
					+ graphManager.numberOfEdges() + " edges.");
		}
		
		this.layoutAndRefresh();
	}
	
	private TSENode findEntityNode() {
		if (this.entity == null) {
			return null;
		}
		else {
			TSENode node = null;
			if (this.entity instanceof EntityElement) {
				node = (TSENode) this.nodeMap.get(
					((EntityElement) this.entity).getEntity().getGUID());
			} 
			else if (this.entity instanceof RuleElement) {
				Compilable rule = ((RuleElement) this.entity).getRule();
				if (rule instanceof RuleFunction) {
					RuleFunction rf = (RuleFunction) rule;
					String rfPath = rf.getFolder() + rf.getName();	
					node = (TSENode) this.nodeMap.get(rfPath);
				}
				else if (rule instanceof Rule) {
					Rule rl = (Rule) rule;
					String rlPath = rl.getFolder() + rl.getName();
					node = (TSENode) this.nodeMap.get(rlPath);
				}
			}
			else if (this.entity instanceof ArchiveElement) {
				node = (TSENode) this.nodeMap.get(
					this.entity.getFolder() + this.entity.getName());
			}else if(this.entity instanceof DecisionTableElement){
				node = (TSENode) this.nodeMap.get(
						this.entity.getFolder() + this.entity.getName());
			}
			
			return node;
		}
	}

	public void setDependencyLevel(final int value) {
		this.DEPENDENCY_LEVEL = value;
		Display.getDefault().asyncExec(new Runnable(){
			public void run() {
				calculateDependencies();
			}
		});
	}
	
	public int getDependencyLevel() {
		return DEPENDENCY_LEVEL;
	}
	
	private EntityElement getEntity(String name) {
		if (this.studioProject == null) {
			System.err.println("Null project, entity cannot be found.");
			return null;
		}
		EList<EntityElement> elements = this.studioProject.getEntityElements();
		for (int i=0; i<elements.size(); i++) {
			EntityElement entityElement = elements.get(i);
			if (name.equals(entityElement.getFolder() + entityElement.getName())) {
				return entityElement;
			}
		}
		return null;
	}
	
	private RuleElement getRule(String name) {
		if (this.studioProject == null) {
			System.err.println("Null project, rule cannot be found.");
			return null;
		}
		EList<RuleElement> elements = this.studioProject.getRuleElements();
		for (RuleElement ruleElement : elements) {
			if (name.equals(ruleElement.getFolder() + ruleElement.getName())) {
				return ruleElement;
			}
		}
		return null;
	}
	
	private ArchiveElement getArchive(String name) {
		if (this.studioProject == null) {
			System.err.println("Null project, archive cannot be found.");
			return null;
		}
		EList<ArchiveElement> elements = this.studioProject.getArchiveElements();
		for (ArchiveElement archiveElement : elements) {
			if (name.equals(archiveElement.getFolder() + archiveElement.getName())) {
				return archiveElement;
			}
		}
		return null;
	}
	
	private DecisionTableElement getDecision(String name){
	if (this.studioProject == null) {
			System.err.println("Null project, archive cannot be found.");
			return null;
		}
		EList<DecisionTableElement> elements = this.studioProject.getDecisionTableElements();
		for (DecisionTableElement decisionElement : elements) {
			if (name.equals(decisionElement.getFolder() + decisionElement.getName())) {
				return decisionElement;
			}
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager#getDiagramSelectionListener()
	 */
	@Override
	public SelectionChangeListener getDiagramSelectionListener() {
		if (this.selectionListener == null) {
			this.selectionListener = new SelectionChangeListener(this);
		}
		return this.selectionListener;
	}	
	@Override
	public void propertyChange(
			PropertyChangeEvent event){
		super.propertyChange(event);
		try{
			String property=event.getProperty();
			property = prefStore.getString(DEPENDENCY_GRID);
			if(property.equals(DEPENDENCY_NONE)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.none"), this.drawingCanvas);
			}else if(property.equals(DEPENDENCY_LINES)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.lines"), this.drawingCanvas);
			}else if(property.equals(DEPENDENCY_POINTS)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.points"), this.drawingCanvas);
			}
			DiagramUtils.refreshDiagram((DependencyDiagramManager)(((DependencyDiagramEditor)editor).getDiagramManager()));
			OverviewUtils.refreshOverview(getEditor().getEditorSite(), true, true);
		}
		catch(Exception e)
		{
			EditorsUIPlugin.debug(this.getClass().getName(), e.getLocalizedMessage());
		}
		
	}

	@Override
	protected void registerListeners() {
		// TODO Auto-generated method stub
		super.registerListeners();
		addPropertyChangeListener(prefStore);
	}
}
