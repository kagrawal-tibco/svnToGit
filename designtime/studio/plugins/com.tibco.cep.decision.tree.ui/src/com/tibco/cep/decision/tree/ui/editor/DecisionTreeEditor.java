package com.tibco.cep.decision.tree.ui.editor;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.decision.tree.ui.DecisionTreeUIPlugin;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditor;
import com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

public class DecisionTreeEditor extends AbstractDecisionTreeEditorPart implements IGotoMarker {

	private DecisionTreePropertySheetPage propertySheetPage;
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
		setForm(decisionTreeDiagramManager.getForm());
		setCatalogFunctionDrag(true);
		
		//Intitially make main Graph as workbench selection
		setWorkbenchSelection(decisionTreeDiagramManager.getGraphManager().getMainDisplayGraph(), this);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.diagram.editors.AbstractDecisionTreeEditor#dispose()
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
			DecisionTreeUIPlugin.debug(e.getMessage());
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
		decisionTreeDiagramManager = new DecisionTreeDiagramManager(this);
		((DecisionTreeDiagramManager) decisionTreeDiagramManager).createPartControl(getContainer());
		SWUIPageIndex = addPage(((DecisionTreeDiagramManager) decisionTreeDiagramManager).getControl());
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
	@SuppressWarnings({"rawtypes"})
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
			propertySheetPage = new DecisionTreePropertySheetPage(this);
		}
		return propertySheetPage;
	}

	/**
	 * @param id
	 * @param editor
	 * @return
	 */
	public Action getActionHandler(String id, DecisionTreeEditor editor) {
		if (handler == null) {
			handler = new HashMap<String, Action>();
		}
		if (handler.get(id) == null) {
			handler.put(id, new DecisionTreeDiagramEditorHandler(id,editor));
		}
		return handler.get(id);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		//super.doSave(progressMonitor);
		saving = true;
		try {
			decisionTreeDiagramManager.save();
			if (isDirty()) {
				isModified = false;
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			saving = false;
		}
	}
	

	@SuppressWarnings("unused")
	private DecisionTreeEditor getDecisionTreeEditorReference(String decisionTreePath) {
		/*
		for (IEditorReference reference : getSite().getPage().getEditorReferences()) {
			try {
				if (reference.getEditorInput() instanceof DecisionTreeEditorInput) {
					DecisionTreeEditorInput decisionTreeditorInput = (DecisionTreeEditorInput)reference.getEditorInput();
					if (decisionTreeditorInput.getDecisionTree().getFullPath().equals(decisionTreePath)) {
						return ((DecisionTreeEditor)reference.getEditor(true));
					}
				}
			} catch (PartInitException e) {
				e.printStackTrace();
				return null;
			}
		}
		*/
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.IGraphDrawing#getPalette()
	 */
	@Override
	public PALETTE getPalette() {
		return PALETTE.TREE;
	}
	
    
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#getPartListener()
	 */
	@Override
	public AbstractEditorPartPaletteHandler getPartListener(){
		return dtListener;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.ide.IGotoMarker#gotoMarker(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public void gotoMarker(IMarker marker) {
		/* TODO:
		try{
			if(marker == null) return;
//			if (!IResourceValidator.VALIDATION_MARKER_TYPE.equals(marker.getType())) {
//				return; 
//			}
			String stateGraphPath = marker.getAttribute(IResourceValidator.MARKER_STATE_GRAPH_PATH_ATTRIBUTE, null);
			boolean isTransition =  marker.getAttribute(IResourceValidator.MARKER_IS_TRANSITION_STATE_ATTRIBUTE, false);

			if(stateGraphPath != null){
				TSEGraph graph = (TSEGraph)((DecisionTreeDiagramManager) getDecisionTreeDiagramManager()).getGraphManager().getMainDisplayGraph();
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
		*/
	}
	
	
	/**
	 * @param graph
	 */
	@SuppressWarnings("unchecked")
	public void unselect(TSEGraph graph){
		List<TSENode> list = graph.nodes();
		unselectTransitionEdges(graph);
		for(TSENode node:list){
			if(node.isSelected()){
				node.setSelected(false);
			}
			if(node.getChildGraph() != null){
				unselect((TSEGraph)node.getChildGraph());
			}
		}
	}
	
	/**
	 * @param graph
	 */
	@SuppressWarnings("unchecked")
	public void unselectTransitionEdges(TSEGraph graph){
		List<TSEEdge> list = graph.edges();
		for(TSEEdge edge:list){
			if(edge.isSelected()){
				edge.setSelected(false);
			}
		}
	}
		
	/**
	 * @param object
	 */
	public void display(final TSEObject object){
			SwingUtilities.invokeLater(new Runnable()
			{
				/* (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				public void run()
				{
					TSConstPoint constPoint = null;
					if(object instanceof TSENode){
						((TSENode)object).setSelected(true);
						 constPoint= ((TSENode)object).getCenter();
					}
					if(object instanceof TSEEdge){
						((TSEEdge)object).setSelected(true);
						 constPoint= ((TSEEdge)object).getSourcePoint();
					}
					DrawingCanvas drawingCanvas = ((DecisionTreeDiagramManager) getDecisionTreeDiagramManager()).getDrawingCanvas();
//					drawingCanvas.drawGraph();
//					drawingCanvas.repaint();
					drawingCanvas.scrollBy(30, 30, true);
					drawingCanvas.centerPointInCanvas(constPoint, true);
//					drawingCanvas.setVisible(true);
				}
			});
	    }
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#doRefresh(org.eclipse.core.resources.IFile)
	 */
	public void doRefresh(IFile file){
		/* TODO - 
		if(getDecisionTree() == null) return;
		DecisionTree oldTreeModel = (DecisionTree)EcoreUtil.copy(getDecisionTree());
		createModel(file);
		setEntity(null);
		decisionTree = getDecisionTree();
		updateExtendedProperties();
		setDecisionTree(decisionTree);
		((DecisionTreeEditorInput)getEditorInput()).setDecisionTree(decisionTree);
		if(decisionTreeDiagramManager == null) return;
		decisionTreeDiagramManager.setDecisionTree(decisionTree);
		//Check for difference between State Model object
		if(!oldTreeModel.equals(decisionTree)){
			Set<TSENode> nodeSet = new HashSet<TSENode>(); // Existing Diagram Node Set
			Set<TSEGraph> graphSet = new HashSet<TSEGraph>();//Existing Diagram Edge Set
			Set<TSEEdge> edgeSet = new HashSet<TSEEdge>();// Existing Diagram Graph Set 
			
			TSEGraph graph = (TSEGraph)((DecisionTreeDiagramManager)getDiagramManager()).getGraphManager().getMainDisplayGraph();
			graph.setUserObject(decisionTree);
			
			//Traversing the diagram for getting artifacts
			traverseTreeModelDiagram(graph, nodeSet, graphSet, edgeSet);
			
			Set<StateEntity> nodeModelSet = new HashSet<StateEntity>();//new Node Model set
			
			Map<String, StateEntity> GUIDNodeMap = new HashMap<String, StateEntity>();// for Node Map
			Map<String, StateTransition> GUIDEdgeMap = new HashMap<String, StateTransition>();//for edge map
			
			//Traversing the model for new model objects
			traverseTreeModel(decisionTree, nodeModelSet);
			
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
			buildGUIDEdgeMap(decisionTree.getStateTransitions(), GUIDEdgeMap);
			
			for(TSEEdge edge:edgeSet){
				StateTransition transition = (StateTransition)edge.getUserObject();
				String GUID = transition.getGUID();
				edge.setUserObject(GUIDEdgeMap.get(GUID));
			}
		}
		*/
	}
	
	/**
	 * @param graph
	 * @param nodeSet
	 * @param graphSet
	 * @param edgeSet
	 */
	@SuppressWarnings({"unchecked", "unused"})
	private void traverseTreeModelDiagram(TSEGraph graph, 
			                        Set<TSENode> nodeSet, 
			                        Set<TSEGraph> graphSet, 
			                        Set<TSEEdge> edgeSet){
		graphSet.add(graph);
		edgeSet.addAll(graph.edges());
		for(Object obj: graph.nodes()){
			TSENode node = (TSENode)obj;
			nodeSet.add(node);
			if(node.getChildGraph() != null){
				traverseTreeModelDiagram((TSEGraph)node.getChildGraph(), nodeSet, graphSet, edgeSet);
			}
		}
	}
		
	@Override
	public void setFocus() {
		//Validate State Model after generated
		decisionTreeDiagramManager.validateTreeModel();
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
		this.getActionHandler(ActionFactory.COPY.getId(), this).setEnabled(enabled);
		this.getActionHandler(ActionFactory.CUT.getId(), this).setEnabled(enabled);
		this.getActionHandler(ActionFactory.DELETE.getId(), this).setEnabled(enabled);
		this.getActionHandler(ActionFactory.UNDO.getId(), this).setEnabled(	decisionTreeDiagramManager.canUndo());
		this.getActionHandler(ActionFactory.REDO.getId(), this).setEnabled(	decisionTreeDiagramManager.canRedo());
	
		//enableUndoRedoContext();
	}
	
	public void enableUndoRedoContext() {
		this.getActionHandler(ActionFactory.UNDO.getId(), this).setEnabled(editingDomain.getCommandStack().canUndo());
		this.getActionHandler(ActionFactory.REDO.getId(), this).setEnabled(editingDomain.getCommandStack().canRedo());
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#getCommandStackListener()
	 */
	protected CommandStackListener getCommandStackListener() {
		commandStackListener = new DecisionTreeCommandStackListener(this);
		return commandStackListener;
	}
	
	/**
	 * @return
	 */
	public Composite getControl() {
		return getContainer();
	}
}