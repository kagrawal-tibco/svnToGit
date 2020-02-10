package com.tibco.cep.studio.ui.statemachine.diagram.tool;

import static com.tibco.cep.diagramming.utils.DiagramUtils.refreshDiagram;
import static com.tibco.cep.studio.core.index.utils.CommonIndexUtils.getEntity;
import static com.tibco.cep.studio.core.index.utils.IndexUtils.getFile;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.openEditor;
import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineConstants.STATE_TYPE;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.editGraph;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.ui.ChildGraphNodeUI;
import com.tibco.cep.diagramming.ui.FinalStateNodeUI;
import com.tibco.cep.diagramming.ui.ImageNodeUI;
import com.tibco.cep.diagramming.ui.RoundRectNodeUI;
import com.tibco.cep.diagramming.ui.SubStateNodeUI;
import com.tibco.cep.studio.ui.statemachine.StateMachinePlugin;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineLayoutManager;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.CompositeStateNodeCreator;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.CompositeStateNodeGraphUI;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.ConcurrentStateNodeCreator;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.ConcurrentStateNodeUI;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.FinalStateNodeCreator;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.RegionGraphNodeUI;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.RegionNodeCreator;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.SimpleStateNodeCreator;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.StateChildGraphNodeUI;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.SubStateMachineCreator;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.TransitionStateEdgeCreator;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.TSEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.TSNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateEdgeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.swing.tool.TSToolPreferenceTailor;
import com.tomsawyer.interactive.tool.TSToolManager;

/**
 * @author hnembhwa
 *
 **/
public class StateMachineSelectTool extends SelectTool {

	protected StateMachineDiagramManager stateMachineDiagramManager;
	protected ChildGraphNodeUI compositeUI;
	public static StateMachineSelectTool tool;
	protected StateMachinePopupMenuController stateMachinePopupMenuController;
	protected TSENodeUI collapsedNodeUI;
	protected CompositeStateNodeGraphUI stateNodeUI;
	protected ConcurrentStateNodeUI concurrentStateNodeUI;
	protected RegionGraphNodeUI regionGraphNodeUI;

	
	public StateMachineSelectTool(StateMachineDiagramManager stateMachineDiagramManager) {
		this.stateMachineDiagramManager = stateMachineDiagramManager;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				compositeUI = new ChildGraphNodeUI();
				compositeUI.setOuterRoundRect(true);
				compositeUI.setBorderDrawn(true);
				compositeUI.setDrawChildGraphMark(false);
				getPopupMenuController();
			}
		});
		tool = this;
	}
	
	/**
	 * This method responds to the mouse button being pressed in
	 * the client area of the swing canvas.
	 */
	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.viewing.tool.TSESelectTool#onMousePressed(java.awt.event.MouseEvent)
	 */
	public void onMousePressed(MouseEvent event) {
		try{
			tool = this; //resetting Active Select Tool when mouse pressed.
			
			if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
				StateMachineSelectToolHandler.popupTriggered(event, this.getGraph(), this);
			}
			else {
				TSEHitTesting hitTesting = this.getHitTesting();

				// get the point where the mouse is pressed.
				TSConstPoint point = this.getNonalignedWorldPoint(event);
				// TSConstPoint wp = new TSConstPoint(event.getPoint().getX(), event.getPoint().getY());

				TSToolPreferenceTailor tailor = new TSToolPreferenceTailor(this.getSwingCanvas().getPreferenceData());

				TSEObject object = hitTesting.getGraphObjectAt(point, this.getGraph(), tailor.isNestedGraphInteractionEnabled());

				TSENode node = null;
				TSENodeUI nodeUI = null;

				if (object instanceof TSENode) {
					node = (TSENode) object;
					nodeUI = (TSENodeUI) node.getNodeUI();
				}

				boolean checkNode = (node != null) && (nodeUI != null);
				boolean checkNodeUI = (nodeUI instanceof StateChildGraphNodeUI);
				boolean clickedOnChildGraphMark = (checkNode) && (checkNodeUI);

				if (event.getClickCount() == 1 && clickedOnChildGraphMark ) {
					boolean clickedOnMark = nodeUI.getChildGraphMarkBounds().contains(point);
					if (clickedOnMark) {
						if (TSENestingManager.isCollapsed((TSENode)node)) {
							node.discardAllLabels();
							node.setUI((TSEObjectUI)compositeUI.clone());
							TSENestingManager.expand(node);
							refreshOverview(stateMachineDiagramManager.getEditor().getEditorSite(), true, true);
						}
						else {
							TSENestingManager.collapse(node);
							node.setAttribute(StateMachineUtils.isExpanded, false);
							
							//When collapsed creating image node
							if(node.getAttributeValue(STATE_TYPE).equals(STATE.COMPOSITE)){
								node.setSize(48,48);
								//Modified by Anand - 01/13/2011 to fix BE-10420
								this.collapsedNodeUI = new ImageNodeUI(new TSEImage(StateMachinePlugin.getDefault().getImageURL("/icons/composite_48x48.png")));
								// TODO: this.collapsedNodeUI = new CollapsedSubprocessNodeUI();
							}
							if(node.getAttributeValue(STATE_TYPE).equals(STATE.CONCURRENT)){
								node.setSize(48,48);
								//Modified by Anand - 01/13/2011 to fix BE-10420								
								this.collapsedNodeUI = new ImageNodeUI(new TSEImage(StateMachinePlugin.getDefault().getImageURL("/icons/concurrent_48x48.png")));
								// TODO: this.collapsedNodeUI = new CollapsedSubprocessNodeUI();
							}
							if(node.getAttributeValue(STATE_TYPE).equals(STATE.REGION)){
								node.setSize(48,45);
								//Modified by Anand - 01/13/2011 to fix BE-10420								
								this.collapsedNodeUI = new ImageNodeUI(new TSEImage(StateMachinePlugin.getDefault().getImageURL("/icons/region_48x45.png")));
								// TODO: this.collapsedNodeUI = new CollapsedSubprocessNodeUI();
							}
							TSENodeLabel nodeLabel = (TSENodeLabel) node.addLabel();
							nodeLabel.setName(node.getName().toString());
							nodeLabel.setDefaultOffset();
							((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
							((StateMachineLayoutManager)(stateMachineDiagramManager.getLayoutManager())).setNodeLabelOptions(nodeLabel);
							node.setUI((TSEObjectUI) this.collapsedNodeUI.clone());
							//refreshOverview(stateMachineDiagramManager.getEditor().getEditorSite(), true, true);
						}
						refreshDiagram(stateMachineDiagramManager);
						refreshOverview(stateMachineDiagramManager.getEditor().getEditorSite(), true, true);
					}
					else {
						super.onMousePressed(event);
					}
				}
				else if (event.getClickCount() == 2 && true) {
					if (node !=null) {
						//Opening Owner State Model for Call State Model
						if(node.getUserObject() instanceof StateSubmachine){
							StateSubmachine callStateMachine = (StateSubmachine)node.getUserObject();
							if(callStateMachine.getOwnerStateMachine() != null){
								try{
								// 	String projectName = stateMachineDiagramManager.getStateMachine().getOwnerProjectName();
									String projectName = callStateMachine.getOwnerProjectName();
									//Entity entity =  getEntity(projectName, callStateMachine.getOwnerStateMachine().getFullPath());
									Entity entity =  getEntity(projectName, callStateMachine.getURI());
									openEditor(stateMachineDiagramManager.getEditor().getSite().getPage(), getFile(projectName, entity));
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						}
						if(node.getChildGraph() != null && TSENestingManager.isCollapsed(node)){
							TSENestingManager.expand(node);
							node.setAttribute(StateMachineUtils.isExpanded, true);
							//node.setUI((TSEObjectUI)compositeUI.clone());
							if(node.getAttributeValue(STATE_TYPE).equals(STATE.COMPOSITE)){
								stateNodeUI = new CompositeStateNodeGraphUI();
								node.setUI(stateNodeUI);
								
							}
							if(node.getAttributeValue(STATE_TYPE).equals(STATE.CONCURRENT)){
								concurrentStateNodeUI = new ConcurrentStateNodeUI();
								node.setUI((TSEObjectUI)concurrentStateNodeUI);
							}
							if(node.getAttributeValue(STATE_TYPE).equals(STATE.REGION)){
								regionGraphNodeUI = new RegionGraphNodeUI();
								node.setUI((TSEObjectUI)regionGraphNodeUI);
							}
							stateMachineDiagramManager.getLayoutManager().callBatchIncrementalLayout();
							//refreshOverview(stateMachineDiagramManager.getEditor().getEditorSite(), true, true);
							
						}
		                //discarding all labels if Expanded Node has  				
						if(node.getChildGraph() != null && TSENestingManager.isExpanded(node)){
							node.discardAllLabels();
						}
						/*System.out.println(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
						if(PlatformUI.getWorkbench().getActiveWorkbenchWindow()!=null){
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(IPageLayout.ID_PROP_SHEET);
						}*/
						/*SwingUtilities.invokeLater(new Runnable(){
							public void run(){
								try {
									stateMachineDiagramManager.getEditor().getSite().getPage().showView(IPageLayout.ID_PROP_SHEET);
								} catch (PartInitException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						});*/
						
						refreshDiagram(stateMachineDiagramManager);
						refreshOverview(stateMachineDiagramManager.getEditor().getEditorSite(), true, true);
					}
				}
				else
				{
					super.onMousePressed(event);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void onMouseMoved(MouseEvent event) {
		TSEHitTesting hitTesting = this.getHitTesting();
		TSConstPoint point = this.getNonalignedWorldPoint(event);
	
		TSToolPreferenceTailor tailor = new TSToolPreferenceTailor(this.getSwingCanvas().getPreferenceData());
		TSEObject object = hitTesting.getGraphObjectAt(point, this.getGraph(), tailor.isNestedGraphInteractionEnabled());
		
		if (object == null){
			return;
		}
		// TODO: set tooltips only if data changed instead of calculating it every time
		// now, it's inefficient but simple and guaranteed to be up to date
		if (object instanceof TSENode) {
			setNodeTooltip((TSENode) object);
		}
		else if (object instanceof TSEEdge) {
			setEdgeTooltip((TSEEdge) object);
		}
		
		super.onMouseMoved(event);
	}

	protected void setNodeTooltip(TSENode node) {
		Object obj = node.getUserObject(); 
		if (obj instanceof StateStart) {
			StateMachineUtils.setNodeTooltip(node, (StateStart) obj);
		}
		else if (obj instanceof StateEnd) {
			StateMachineUtils.setNodeTooltip(node, (StateEnd) obj);
		}
		else if (obj instanceof StateSimple) {
			StateMachineUtils.setNodeTooltip(node, (StateSimple) obj, true);
		}
		else if (obj instanceof StateSubmachine) {
			StateMachineUtils.setNodeTooltip(node, (StateSubmachine) obj);
		}
		else if (obj instanceof StateComposite) {
			StateMachineUtils.setNodeTooltip(node, (StateComposite) obj);
		}
		
	}
	
	protected void setEdgeTooltip(TSEEdge edge) {
		Object obj;
		obj = edge.getUserObject();
		if (obj instanceof StateTransition) {
			StateMachineUtils.setEdgeTooltip(edge, (StateTransition)obj);
		}
	}

	public void actionPerformed(ActionEvent action) {
		String command = action.getActionCommand();

		if(EntityResourceConstants.SM_SIMPLE_STATE.equals(command)) {
			this.setActiveDiagramTool(command);
		}
		else if(EntityResourceConstants.SM_END_STATE.equals(command)) {
			this.setActiveDiagramTool(command);
		}
		else if(EntityResourceConstants.SM_COMPOSITE_STATE.equals(command)) {
			this.setActiveDiagramTool(command);
		}
		else if(EntityResourceConstants.SM_CONCURRENT_STATE.equals(command)) {
			this.setActiveDiagramTool(command);
		}
		else if(EntityResourceConstants.SM_CALL_STATE_MODEL.equals(command)) {
			this.setActiveDiagramTool(command);
		}
		else if(EntityResourceConstants.SM_REGION_STATE.equals(command)) {
			this.setActiveDiagramTool(command);
		}
		else if(EntityResourceConstants.SM_TRANSITION.equals(command)) {
			this.setActiveDiagramTool(command);
		}
		else {
			super.actionPerformed(action);
		}
	}

	
	/**
	 * This method sets the enabled or disabled state of the popup menu items.
	 * It is called before the popup menu is displayed.
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#setPopupState(javax.swing.JPopupMenu)
	 */
	public void setPopupState(JPopupMenu popup) {
		for (int i = popup.getComponentCount() - 1; i >= 0; --i) {
			Component element = popup.getComponent(i);

			if (element instanceof JMenu) {
				this.setMenuState((JMenu) element);
			} else if (element instanceof JMenuItem) {
				StateMachineSelectToolHandler.chooseState((JMenuItem) element, 
						                                  this.getGraph(), 
						                                  this, 
						                                  ((StateMachineEditor)stateMachineDiagramManager.getEditor()).isEnabled());
			}
		}
	}

	/**
	 * This method sets the state of all items in the input popup menu based on
	 * their action commands.
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#setMenuState(javax.swing.JMenu)
	 */
	public void setMenuState(JMenu menu) {
		for (int i = menu.getMenuComponentCount() - 1; i >= 0; --i) {
			JMenuItem item = menu.getItem(i);

			// With Swing 1.1.1, iterating through the items of a
			// submenu appears to return the separators as well as the
			// menu items. So we skip the null items.

			if (item != null) {
				if ((item != menu) && (item instanceof JMenu)) {
					this.setMenuState((JMenu) item);
				} else {
					StateMachineSelectToolHandler.chooseState(item, 
															  this.getGraph(), 
															  this, 
															  ((StateMachineEditor)stateMachineDiagramManager.getEditor()).isEnabled());
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#delete()
	 */
	@Override
	protected void deleteDiagramComponents(){	
	   editGraph(EDIT_TYPES.DELETE, stateMachineDiagramManager.getEditor().getEditorSite(), stateMachineDiagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#cut()
	 */
	protected void cutGraph(){
		stateMachineDiagramManager.setCutGraph(true);
		editGraph(EDIT_TYPES.CUT, stateMachineDiagramManager.getEditor().getEditorSite(), stateMachineDiagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#copy()
	 */
	protected void copyGraph(){
		stateMachineDiagramManager.setCopyGraph(true);
		editGraph(EDIT_TYPES.COPY, stateMachineDiagramManager.getEditor().getEditorSite(), stateMachineDiagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#paste()
	 */
	protected void pasteGraph(){
		stateMachineDiagramManager.setPasteGraph(true);
		editGraph(EDIT_TYPES.PASTE, stateMachineDiagramManager.getEditor().getEditorSite(), stateMachineDiagramManager);
	}
	
	public static StateMachineSelectTool getTool() {
		return tool;
	}
	
	protected ContextMenuController getPopupMenuController() {
		if (this.stateMachinePopupMenuController == null) {
			this.stateMachinePopupMenuController = new StateMachinePopupMenuController();
		}
		return (this.stateMachinePopupMenuController);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#getPage()
	 */
	@Override
	public IWorkbenchPage getPage() {
		return stateMachineDiagramManager.getEditor().getSite().getPage();
	}

	public StateMachineDiagramManager getStateMachineDiagramManager() {
		return stateMachineDiagramManager;
	}
	
    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.tool.SelectTool#setActiveDiagramTool(java.lang.String)
     */
    @Override
    protected void setActiveDiagramTool(String command) {
    	
    	DrawingCanvas canvas = stateMachineDiagramManager.getDrawingCanvas();
		TSToolManager toolManager = (TSToolManager) canvas.getToolManager();
		TSEGraphManager graphManager = stateMachineDiagramManager.getGraphManager();
		LayoutManager layoutManager = stateMachineDiagramManager.getLayoutManager();
		
    	if(EntityResourceConstants.SM_SIMPLE_STATE.equals(command)) {
    		createStateNode(graphManager, toolManager, new SimpleStateNodeCreator(layoutManager), new RoundRectNodeUI());
    	}
    	else if(EntityResourceConstants.SM_COMPOSITE_STATE.equals(command)) {
    		createStateNode(graphManager, toolManager, new CompositeStateNodeCreator(layoutManager), new CompositeStateNodeGraphUI());
    	}
    	else if(EntityResourceConstants.SM_CONCURRENT_STATE.equals(command)) {
    		createStateNode(graphManager, toolManager, new ConcurrentStateNodeCreator(layoutManager), new ConcurrentStateNodeUI());
    	}
    	else if(EntityResourceConstants.SM_CALL_STATE_MODEL.equals(command)) {
    		createStateNode(graphManager, toolManager, new SubStateMachineCreator(layoutManager),new SubStateNodeUI());
    	}
    	else if(EntityResourceConstants.SM_END_STATE.equals(command)) {
    		createStateNode(graphManager, toolManager, new FinalStateNodeCreator(layoutManager), new FinalStateNodeUI());
    	}
    	else if(EntityResourceConstants.SM_REGION_STATE.equals(command)) {
    		createStateNode(graphManager, toolManager, new RegionNodeCreator(layoutManager), new RegionGraphNodeUI());
    	}
    	else if(EntityResourceConstants.SM_TRANSITION.equals(command)) {
    		TSECreateEdgeTool createEdgeTool = TSEditingToolHelper.getCreateEdgeTool(toolManager);
    		TransitionStateEdgeCreator  objectBuilder = new TransitionStateEdgeCreator();
    		TSECurvedEdgeUI objectUI = new TSECurvedEdgeUI();
    		graphManager.setEdgeBuilder((TSEdgeBuilder) objectBuilder);
    		((TSEEdgeUI)objectUI).setAntiAliasingEnabled(true);
    		graphManager.getEdgeBuilder().setEdgeUI((TSEdgeUI) objectUI);
    		toolManager.setActiveTool(createEdgeTool);
    	}
    	else {
    		System.out.println("Unknown command when setting diagram tool: " + command);
    	}
    }

    /**
     * @param graphManager
     * @param toolManager
     * @param objectBuilder
     * @param objectUI
     */
    private void createStateNode(TSEGraphManager graphManager, 
	    		                 TSToolManager toolManager, 
	    		                 Object objectBuilder, 
	    		                 Object objectUI) {
    	TSECreateNodeTool createTool = TSEditingToolHelper.getCreateNodeTool(toolManager);
		graphManager.setNodeBuilder((TSNodeBuilder) objectBuilder);
		graphManager.getNodeBuilder().setNodeUI((TSNodeUI) objectUI);	
		toolManager.setActiveTool(createTool);
    }
    
	
	@Override
	public void dispose() {
		if (stateMachineDiagramManager != null) {
			DrawingCanvas canvas = stateMachineDiagramManager.getDrawingCanvas();
			TSToolManager toolManager = (TSToolManager) canvas.getToolManager();
			toolManager.setActiveTool(null);
		}

		super.dispose();
		stateMachineDiagramManager = null;
		tool = null;
		compositeUI = null;
		stateMachinePopupMenuController = null;
		collapsedNodeUI = null;
		stateNodeUI = null;
		concurrentStateNodeUI = null;
		regionGraphNodeUI = null;

	}

}