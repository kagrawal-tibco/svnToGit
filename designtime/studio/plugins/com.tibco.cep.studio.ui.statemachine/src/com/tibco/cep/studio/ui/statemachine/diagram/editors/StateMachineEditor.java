package com.tibco.cep.studio.ui.statemachine.diagram.editors;

import static com.tibco.cep.diagramming.utils.DiagramUtils.display;
import static com.tibco.cep.diagramming.utils.DiagramUtils.unselect;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getStateGraphPath;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditor;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.navigator.view.ProjectExplorer;
import com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler;
import com.tibco.cep.studio.ui.statemachine.StateMachinePlugin;
import com.tibco.cep.studio.ui.statemachine.commands.StateMachineCommandStackListener;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.handler.StateMachineDiagramEditorHandler;
import com.tibco.cep.studio.ui.statemachine.tabbed.properties.StateMachinePropertySheetPage;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
/**
 * 
 * @author ggrigore
 * 
 */

public class StateMachineEditor extends AbstractStateMachineEditorPart implements IGotoMarker{

	private StateMachinePropertySheetPage propertySheetPage;
	private HashMap<String, Action> handler;
    protected Concept editorConcept;
    protected ConceptFormEditor conceptFormEditor;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#createPages()
	 */
	@Override
	protected void createPages() {
		createUIEditorPage();
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
		updateTitle();
		setForm(stateMachineDiagramManager.getForm());
		setCatalogFunctionDrag(true);
		
		//Intitially make main Graph as workbench selection
		stateMachineDiagramManager.waitForInitComplete();
		if (stateMachineDiagramManager.getGraphManager() != null) {
			setWorkbenchSelection(stateMachineDiagramManager.getGraphManager().getMainDisplayGraph(), this);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.diagram.editors.AbstractStateMachineEditor#dispose()
	 */
	@Override
	public void dispose() {
		try{
			super.dispose();
			if (propertySheetPage != null) {
				propertySheetPage.removeEditor();
				propertySheetPage = null;
			}
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
			if (editingDomain != null && commandStackListener != null) {
				editingDomain.getCommandStack().removeCommandStackListener(commandStackListener);
			}
		}
		catch(Exception e){
			StateMachinePlugin.debug(e.getMessage());
		}

	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#createUIEditorPage()
	 */
	protected void createUIEditorPage() {
		try {
			addFormPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#addFormPage()
	 */
	@Override
	protected void addFormPage() throws PartInitException {
		stateMachineDiagramManager = new StateMachineDiagramManager(this);
		((StateMachineDiagramManager) stateMachineDiagramManager).createPartControl(getContainer());
		SWUIPageIndex = addPage(((StateMachineDiagramManager) stateMachineDiagramManager).getControl());
		this.setActivePage(SWUIPageIndex);
	}
	
	/**
	 * Update the editor's title based upon the content being edited.
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#updateTitle()
	 */
	@Override
	protected void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (IPropertySheetPage.class.equals(adapter))
			return getPropertySheetPage();
		else if (IGotoMarker.class.equals(adapter)) {
			return this;
		}else
			return super.getAdapter(adapter);
	}

	/**
	 * Get the new property sheet page for this editor.
	 * 
	 * @return the new property sheet page.
	 */
	public TabbedPropertySheetPage getPropertySheetPage() {
		if (propertySheetPage == null || propertySheetPage.getControl() == null) {
			propertySheetPage = new StateMachinePropertySheetPage(this);
		}
		return propertySheetPage;
	}

	/**
	 * @param id
	 * @param editor
	 * @return
	 */
	public Action getActionHandler(String id, StateMachineEditor editor) {
		if (handler == null) {
			handler = new HashMap<String, Action>();
		}
		if (handler.get(id) == null) {
			handler.put(id, new StateMachineDiagramEditorHandler(id,editor));
		}
		return handler.get(id);
	}
	
	@Override
	public void modified() {
		super.modified();
		updateSave();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave(progressMonitor);
		try {
			if(isMainChanged){
				reArrangeStateMachinesMain(stateMachine.getOwnerConcept(), stateMachine);
			}
			conceptFormEditor = EditorUtils.getConceptEditorReference(getSite().getPage(),getStateMachine().getOwnerConceptPath());
			if(conceptFormEditor!=null){
				conceptFormEditor.getConceptFromDesignViewer().getSmAssociationViewer().refresh();
			}
			if(oldOwnerConceptPath != null ) {
				ConceptFormEditor oldConceptFormEditor = EditorUtils.getConceptEditorReference(getSite().getPage(), oldOwnerConceptPath);
				if(oldConceptFormEditor != null){
					oldConceptFormEditor.getConcept().getStateMachinePaths().remove(stateMachine.getFullPath());
					oldConceptFormEditor.getConceptFromDesignViewer().getSmAssociationViewer().refresh();
					oldConceptFormEditor.setSmAssociationFlag(true);
					oldConceptFormEditor.getConceptFromDesignViewer().getRemoveSMAssociationButton()
					.setEnabled(oldConceptFormEditor.getConcept().getStateMachinePaths().size() > 0);
					oldConceptFormEditor.modified();
					getSite().getPage().saveEditor(oldConceptFormEditor, false);
				}else{
					Concept concept = IndexUtils.getConcept(stateMachine.getOwnerProjectName(), oldOwnerConceptPath);
					if(concept!=null){
						concept.getStateMachinePaths().remove(stateMachine.getFullPath());
						ModelUtils.saveEObject(concept);
					}
					try {
						IndexUtils.getFile(concept.getOwnerProjectName(), concept).refreshLocal(0, null);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			if(newOwnerConceptPath != null){
				ConceptFormEditor newConceptFormEditor = EditorUtils.getConceptEditorReference(getSite().getPage(), newOwnerConceptPath);
				if(newConceptFormEditor != null){
					newConceptFormEditor.getConcept().getStateMachinePaths().add(stateMachine.getFullPath());
					newConceptFormEditor.getConceptFromDesignViewer().getSmAssociationViewer().refresh();
					newConceptFormEditor.setSmAssociationFlag(true);
					newConceptFormEditor.getConceptFromDesignViewer().getRemoveSMAssociationButton()
					.setEnabled(newConceptFormEditor.getConcept().getStateMachinePaths().size() > 0);
					newConceptFormEditor.modified();
					getSite().getPage().saveEditor(newConceptFormEditor, false);
				}else{
					Concept concept = IndexUtils.getConcept(stateMachine.getOwnerProjectName(), newOwnerConceptPath);
					concept.getStateMachinePaths().add(stateMachine.getFullPath());
					ModelUtils.saveEObject(concept);
					try {
						IndexUtils.getFile(concept.getOwnerProjectName(), concept).refreshLocal(0, null);
					} catch (CoreException e) {
						e.printStackTrace();
					}
					if(getOwnerConceptTextField()!= null){
						getOwnerConceptTextField().setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					}
				}
			}
			//Refreshing the Project Explorer
			IViewPart view = 
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ProjectExplorer.ID);
			if(view != null) {
				((ProjectExplorer)view).getCommonViewer().refresh();
			}
			oldOwnerConceptPath = null;
			newOwnerConceptPath = null;
			isMainChanged = false;
			updateSave();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param concept
	 * @param stateMachine
	 * @throws IOException
	 */
	private void reArrangeStateMachinesMain(Concept concept, StateMachine stateMachine) throws IOException{
		String stateMachinePath = stateMachine.getFullPath();
		for(String path:concept.getStateMachinePaths()){
			StateMachineEditor stateMachineEditor = getStateMachineEditorReference(path);
			if(stateMachineEditor != null && stateMachineEditor.isDirty()){
				getSite().getPage().saveEditor(stateMachineEditor, false);
			}
			StateMachine sm = (StateMachine)IndexUtils.getEntity(stateMachine.getOwnerProjectName(), path, ELEMENT_TYPES.STATE_MACHINE); 
			if(sm != null && !path.equals(stateMachinePath) && sm.isMain()){
				sm.setMain(false);
				ModelUtils.saveEObject(sm);
//				sm.eResource().save(ModelUtils.getPersistenceOptions());
				try {
					IndexUtils.getFile(sm.getOwnerProjectName(), sm).refreshLocal(0, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param file
	 */
	private StateMachineEditor getStateMachineEditorReference(String stateMachinePath) {
		
		for (IEditorReference reference : getSite().getPage().getEditorReferences()) {
			try {
				if (reference.getEditorInput() instanceof StateMachineEditorInput) {
					StateMachineEditorInput stateMachineditorInput = 
						(StateMachineEditorInput)reference.getEditorInput();
					if (stateMachineditorInput.getStateMachine().getFullPath().equals(stateMachinePath)) {
						return ((StateMachineEditor)reference.getEditor(true));
					}
				}
			} catch (PartInitException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.IGraphDrawing#getPalette()
	 */
	@Override
	public PALETTE getPalette() {
		return PALETTE.STATE_MACHINE;
	}
	
    
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#getPartListener()
	 */
	@Override
	public AbstractEditorPartPaletteHandler getPartListener(){
		return smListener;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.ide.IGotoMarker#gotoMarker(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public void gotoMarker(IMarker marker) {
		try{
			if(marker == null) return;
//			if (!IResourceValidator.VALIDATION_MARKER_TYPE.equals(marker.getType())) {
//				return; 
//			}
			String stateGraphPath = marker.getAttribute(IResourceValidator.MARKER_STATE_GRAPH_PATH_ATTRIBUTE, null);
			boolean isTransition =  marker.getAttribute(IResourceValidator.MARKER_IS_TRANSITION_STATE_ATTRIBUTE, false);

			if(stateGraphPath != null){
				TSEGraph graph = (TSEGraph)((StateMachineDiagramManager) getStateMachineDiagramManager()).getGraphManager().getMainDisplayGraph();
				unselect(graph);
				if(isTransition){
					findStateEdge(stateGraphPath, graph);
				}else{
					findStateNode(stateGraphPath, graph);	
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param stateGraphPath
	 * @param graph
	 */
	@SuppressWarnings("unchecked")
	public void findStateNode(String stateGraphPath, TSEGraph graph){
		List<TSENode> list = graph.nodes();
		for(TSENode node:list){
			StateEntity stateEntity = (StateEntity)node.getUserObject();
			if(getStateGraphPath(stateEntity).equals(stateGraphPath)){
				display(node, stateMachineDiagramManager);
				break;
			}
			if(node.getChildGraph() != null){
				findStateNode(stateGraphPath, (TSEGraph)node.getChildGraph());
			}
		}
	}
	
	/**
	 * @param stateGraphPath
	 * @param graph
	 */
	@SuppressWarnings("unchecked")
	public void findStateEdge(String stateGraphPath, TSEGraph graph){
		List<TSENode> list = graph.nodes();
		List<TSEEdge> edgeList = graph.edges();
		for(TSEEdge edge:edgeList){
			StateEntity stateEntity = (StateEntity)edge.getUserObject();
			if(stateEntity.getName().equals(stateGraphPath)){
				display(edge, stateMachineDiagramManager);
				break;
			}
		}
		for(TSENode node:list){
			if(node.getChildGraph() != null){
				findStateEdge(stateGraphPath, (TSEGraph)node.getChildGraph());
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#doRefresh(org.eclipse.core.resources.IFile)
	 */
	public void doRefresh(IFile file){
		//getting the old State Machine copy Object
		if(getStateMachine() == null) return;
		StateMachine oldStateModel = (StateMachine)EcoreUtil.copy(getStateMachine());
		createModel(file);
		setEntity(null);
		stateMachine = getStateMachine();
		updateExtendedProperties();
		setStateMachine(stateMachine);
		((StateMachineEditorInput)getEditorInput()).setStateMachine(stateMachine);
		if(stateMachineDiagramManager == null) return;
		stateMachineDiagramManager.setStateMachine(stateMachine);
		//Check for difference between State Model object
		if(!oldStateModel.equals(stateMachine)){
			Set<TSENode> nodeSet = new HashSet<TSENode>(); // Existing Diagram Node Set
			Set<TSEGraph> graphSet = new HashSet<TSEGraph>();//Existing Diagram Edge Set
			Set<TSEEdge> edgeSet = new HashSet<TSEEdge>();// Existing Diagram Graph Set 
			
			TSEGraph graph = (TSEGraph)((StateMachineDiagramManager)getDiagramManager()).getGraphManager().getMainDisplayGraph();
			graph.setUserObject(stateMachine);
			
			//Traversing the diagram for getting artifacts
			traverseStateModelDiagram(graph, nodeSet, graphSet, edgeSet);
			
			Set<StateEntity> nodeModelSet = new HashSet<StateEntity>();//new Node Model set
			
			Map<String, StateEntity> GUIDNodeMap = new HashMap<String, StateEntity>();// for Node Map
			Map<String, StateTransition> GUIDEdgeMap = new HashMap<String, StateTransition>();//for edge map
			
			//Traversing the model for new model objects
			traverseStateModel(stateMachine, nodeModelSet);
			
			//Building GUID map for the new State Model Set 
			buildGUIDNodeMap(nodeModelSet, GUIDNodeMap);
			
			for(TSENode node:nodeSet){
				StateEntity sentity = (StateEntity)node.getUserObject();
				if(sentity != null){
					String GUID = sentity.getGUID();
					node.setUserObject(GUIDNodeMap.get(GUID));
					if(node.getChildGraph() != null){
						node.getChildGraph().setUserObject(sentity);
					}
				}
			}
			
			//Building GUID map for the new State Transition Model Set 
			buildGUIDEdgeMap(stateMachine.getStateTransitions(), GUIDEdgeMap);
			
			for(TSEEdge edge:edgeSet){
				StateTransition transition = (StateTransition)edge.getUserObject();
				String GUID = transition.getGUID();
				edge.setUserObject(GUIDEdgeMap.get(GUID));
			}
		}
	}
	
	/**
	 * @param graph
	 * @param nodeSet
	 * @param graphSet
	 * @param edgeSet
	 */
	@SuppressWarnings("unchecked")
	private void traverseStateModelDiagram(TSEGraph graph, 
			                        Set<TSENode> nodeSet, 
			                        Set<TSEGraph> graphSet, 
			                        Set<TSEEdge> edgeSet){
		graphSet.add(graph);
		edgeSet.addAll(graph.edges());
		for(Object obj: graph.nodes()){
			TSENode node = (TSENode)obj;
			nodeSet.add(node);
			if(node.getChildGraph() != null){
				traverseStateModelDiagram((TSEGraph)node.getChildGraph(), nodeSet, graphSet, edgeSet);
			}
		}
	}
	

	/**
	 * @param modelSet
	 * @param GUIDMap
	 */
	private void buildGUIDNodeMap(Set<StateEntity> modelSet, Map<String, StateEntity> GUIDMap){
		for(StateEntity stateEntity:modelSet){
			GUIDMap.put(stateEntity.getGUID(), stateEntity);
		}
	}
	
	/**
	 * @param modelSet
	 * @param GUIDMap
	 */
	private void buildGUIDEdgeMap(List<StateTransition> modelSet, Map<String, StateTransition> GUIDMap){
		for(StateTransition stateTransition:modelSet){
			GUIDMap.put(stateTransition.getGUID(), stateTransition);
		}
	}
	
	/**
	 * @param stateComposite
	 * @param stateEnity
	 */
	private void traverseStateModel(StateComposite stateComposite, Set<StateEntity> stateEnity){
		if (stateComposite.isConcurrentState()) {
			for (StateEntity obj: stateComposite.getRegions()) {
				stateEnity.add(obj); 
				if (obj instanceof StateComposite) {
					traverseStateModel((StateComposite)obj, stateEnity);
				}
			}
		} else {
			for (StateEntity obj: stateComposite.getStateEntities()) {
				stateEnity.add(obj); 
				if (obj instanceof StateComposite) {
					traverseStateModel((StateComposite)obj, stateEnity);
				}
			}
		}
	}
	
	@Override
	public void setFocus() {
		//Validate State Model after generated
		if (!stateMachineDiagramManager.isInitialized()) {
//			return;
		}
		stateMachineDiagramManager.validateStateModel();
		super.setFocus();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.AbstractResourceEditorPart#getPerspectiveId()
	 */
	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.diagramming.diagram.perspective";
	}
	
	public void enableEdit(boolean enabled) {
		updateSave();
		this.getActionHandler(ActionFactory.COPY.getId(), this).setEnabled(enabled);
		this.getActionHandler(ActionFactory.CUT.getId(), this).setEnabled(enabled);
		this.getActionHandler(ActionFactory.DELETE.getId(), this).setEnabled(enabled);
		this.getActionHandler(ActionFactory.PASTE.getId(), this).setEnabled(enabled);
		this.getActionHandler(ActionFactory.UNDO.getId(), this).setEnabled(enabled && stateMachineDiagramManager.canUndo());
		this.getActionHandler(ActionFactory.REDO.getId(), this).setEnabled(enabled && stateMachineDiagramManager.canRedo());
	
		//enableUndoRedoContext();
	}
	
	public void updateSave() {
		this.getActionHandler(ActionFactory.SAVE.getId(), this).setEnabled(isDirty());
	}
	
	public void enablePaste(boolean enabled) {
		this.getActionHandler(ActionFactory.PASTE.getId(), this).setEnabled(enabled);
	}
	
	public void enableUndoRedoContext() {
		this.getActionHandler(ActionFactory.UNDO.getId(), this).setEnabled(stateMachineDiagramManager.canUndo());
		this.getActionHandler(ActionFactory.REDO.getId(), this).setEnabled(stateMachineDiagramManager.canRedo());
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#getCommandStackListener()
	 */
	protected CommandStackListener getCommandStackListener() {
		commandStackListener = new StateMachineCommandStackListener(this);
		return commandStackListener;
	}
	
	/**
	 * @return
	 */
	public Composite getControl() {
		return getContainer();
	}

	public void propertySheetDisposed() {
		this.propertySheetPage = null;
	}
}