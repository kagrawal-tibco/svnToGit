package com.tibco.cep.decision.tree.ui.tool;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.editGraph;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.decision.tree.ui.editor.DecisionTreeDiagramManager;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreeEditor;
import com.tibco.cep.decision.tree.ui.nodeactions.EndNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.TransitionEdgeCreator;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.ui.ChildGraphNodeUI;
import com.tibco.cep.diagramming.ui.FinalStateNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.TSEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.TSNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateEdgeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.swing.tool.TSToolPreferenceTailor;
import com.tomsawyer.interactive.tool.TSToolManager;

public class DecisionTreeSelectTool extends SelectTool {

	protected DecisionTreeDiagramManager decisionTreeDiagramManager;
	protected ChildGraphNodeUI compositeUI;
	public static DecisionTreeSelectTool tool;
	protected DecisionTreePopupMenuController decisionTreePopupMenuController;
	protected TSENodeUI collapsedNodeUI;
	
	public DecisionTreeSelectTool(DecisionTreeDiagramManager decisionTreeDiagramManager) {
		this.decisionTreeDiagramManager = decisionTreeDiagramManager;
		this.compositeUI = new ChildGraphNodeUI();
		this.compositeUI.setOuterRoundRect(true);
		this.compositeUI.setBorderDrawn(true);
		this.compositeUI.setDrawChildGraphMark(false);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
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
	@SuppressWarnings("unused")
	public void onMousePressed(MouseEvent event) {
		try{
			tool = this; //resetting Active Select Tool when mouse pressed.
			
			if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
				DecisionTreeSelectToolHandler.popupTriggered(event, this.getGraph(), this);
			}
			else {
				TSEHitTesting hitTesting = this.getHitTesting();

				// get the point where the mouse is pressed.
				TSConstPoint point = this.getNonalignedWorldPoint(event);
				// TSConstPoint wp = new TSConstPoint(event.getPoint().getX(), event.getPoint().getY());

				TSToolPreferenceTailor tailor = new TSToolPreferenceTailor(this.getSwingCanvas().getPreferenceData());

				TSEObject object = hitTesting.getGraphObjectAt(point, this.getGraph(), tailor.isNestedGraphInteractionEnabled());

				TSENode node = null;
				TSNodeUI nodeUI = null;

				if (object instanceof TSENode) {
					node = (TSENode) object;
					nodeUI = (TSNodeUI) node.getNodeUI();
				}

				/* TODO
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
							refreshOverview(decisionTreeDiagramManager.getEditor().getEditorSite(), true, true);
						}
						else {
							TSENestingManager.collapse(node);
							node.setAttribute(DecisionTreeUtils.isExpanded, false);
							
							//When collapsed creating image node
							if(node.getAttributeValue(STATE_TYPE).equals(STATE.COMPOSITE)){
								node.setSize(48,48);
								//Modified by Anand - 01/13/2011 to fix BE-10420
								this.collapsedNodeUI = new ImageNodeUI(new TSEImage(DecisionTreePlugin.getDefault().getImageURL("/icons/composite_48x48.png")));
								// TODO: this.collapsedNodeUI = new CollapsedSubprocessNodeUI();
							}
							if(node.getAttributeValue(STATE_TYPE).equals(STATE.CONCURRENT)){
								node.setSize(48,48);
								//Modified by Anand - 01/13/2011 to fix BE-10420								
								this.collapsedNodeUI = new ImageNodeUI(new TSEImage(DecisionTreePlugin.getDefault().getImageURL("/icons/concurrent_48x48.png")));
								// TODO: this.collapsedNodeUI = new CollapsedSubprocessNodeUI();
							}
							if(node.getAttributeValue(STATE_TYPE).equals(STATE.REGION)){
								node.setSize(48,45);
								//Modified by Anand - 01/13/2011 to fix BE-10420								
								this.collapsedNodeUI = new ImageNodeUI(new TSEImage(DecisionTreePlugin.getDefault().getImageURL("/icons/region_48x45.png")));
								// TODO: this.collapsedNodeUI = new CollapsedSubprocessNodeUI();
							}
							TSENodeLabel nodeLabel = (TSENodeLabel) node.addLabel();
							nodeLabel.setName(node.getName().toString());
							nodeLabel.setDefaultOffset();
							((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
							((DecisionTreeLayoutManager)(decisionTreeDiagramManager.getLayoutManager())).setNodeLabelOptions(nodeLabel);
							node.setUI((TSEObjectUI) this.collapsedNodeUI.clone());
							//refreshOverview(decisionTreeDiagramManager.getEditor().getEditorSite(), true, true);
						}
						refreshDiagram(decisionTreeDiagramManager);
						refreshOverview(decisionTreeDiagramManager.getEditor().getEditorSite(), true, true);
					}
					else {
						super.onMousePressed(event);
					}
				}
				else if (event.getClickCount() == 2 && true) {
					if (node !=null) {
						//Opening Owner State Model for Call State Model
						if(node.getUserObject() instanceof StateSubmachine){
							StateSubmachine callDecisionTree = (StateSubmachine)node.getUserObject();
							if(callDecisionTree.getOwnerDecisionTree() != null){
								try{
									String projectName = decisionTreeDiagramManager.getDecisionTree().getOwnerProjectName();
									//Entity entity =  getEntity(projectName, callDecisionTree.getOwnerDecisionTree().getFullPath());
									Entity entity =  getEntity(projectName, callDecisionTree.getURI());
									openEditor(decisionTreeDiagramManager.getEditor().getSite().getPage(), getFile(projectName, entity));
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						}
						if(node.getChildGraph() != null && TSENestingManager.isCollapsed(node)){
							TSENestingManager.expand(node);
							node.setAttribute(DecisionTreeUtils.isExpanded, true);
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
							decisionTreeDiagramManager.getLayoutManager().callBatchIncrementalLayout();
							//refreshOverview(decisionTreeDiagramManager.getEditor().getEditorSite(), true, true);
							
						}
		                //discarding all labels if Expanded Node has  				
						if(node.getChildGraph() != null && TSENestingManager.isExpanded(node)){
							node.discardAllLabels();
						}
						
						refreshDiagram(decisionTreeDiagramManager);
						refreshOverview(decisionTreeDiagramManager.getEditor().getEditorSite(), true, true);
					}
				}
				else
				{
					super.onMousePressed(event);
				}
				*/
				super.onMousePressed(event);
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
		node.getUserObject(); 
	}
	
	protected void setEdgeTooltip(TSEEdge edge) {
		edge.getUserObject();
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
				DecisionTreeSelectToolHandler.chooseState((JMenuItem) element, 
						                                  this.getGraph(), 
						                                  this, 
						                                  ((DecisionTreeEditor)decisionTreeDiagramManager.getEditor()).isEnabled());
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
					DecisionTreeSelectToolHandler.chooseState(item, 
															  this.getGraph(), 
															  this, 
															  ((DecisionTreeEditor)decisionTreeDiagramManager.getEditor()).isEnabled());
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#delete()
	 */
	@Override
	protected void deleteDiagramComponents(){	
	   editGraph(EDIT_TYPES.DELETE, decisionTreeDiagramManager.getEditor().getEditorSite(), decisionTreeDiagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#cut()
	 */
	protected void cutGraph(){
		decisionTreeDiagramManager.setCutGraph(true);
		editGraph(EDIT_TYPES.CUT, decisionTreeDiagramManager.getEditor().getEditorSite(), decisionTreeDiagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#copy()
	 */
	protected void copyGraph(){
		decisionTreeDiagramManager.setCopyGraph(true);
		editGraph(EDIT_TYPES.COPY, decisionTreeDiagramManager.getEditor().getEditorSite(), decisionTreeDiagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#paste()
	 */
	protected void pasteGraph(){
		decisionTreeDiagramManager.setPasteGraph(true);
		editGraph(EDIT_TYPES.PASTE, decisionTreeDiagramManager.getEditor().getEditorSite(), decisionTreeDiagramManager);
	}
	
	public static DecisionTreeSelectTool getTool() {
		return tool;
	}
	
	protected ContextMenuController getPopupMenuController() {
		if (this.decisionTreePopupMenuController == null) {
			this.decisionTreePopupMenuController = new DecisionTreePopupMenuController();
		}
		return (this.decisionTreePopupMenuController);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#getPage()
	 */
	@Override
	public IWorkbenchPage getPage() {
		return decisionTreeDiagramManager.getEditor().getSite().getPage();
	}

	public DecisionTreeDiagramManager getDecisionTreeDiagramManager() {
		return decisionTreeDiagramManager;
	}
	
    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.tool.SelectTool#setActiveDiagramTool(java.lang.String)
     */
    @Override
    protected void setActiveDiagramTool(String command) {
    	
    	DrawingCanvas canvas = decisionTreeDiagramManager.getDrawingCanvas();
		TSToolManager toolManager = (TSToolManager) canvas.getToolManager();
		TSEGraphManager graphManager = decisionTreeDiagramManager.getGraphManager();
		LayoutManager layoutManager = decisionTreeDiagramManager.getLayoutManager();
		
    	if(EntityResourceConstants.SM_SIMPLE_STATE.equals(command)) {
    		//createStateNode(graphManager, toolManager, new SimpleTreeNodeCreator(layoutManager), new RoundRectNodeUI());
    	}
    	else if(EntityResourceConstants.SM_END_STATE.equals(command)) {
    		createStateNode(graphManager, toolManager, new EndNodeCreator(layoutManager, null), new FinalStateNodeUI());
    	}
    	else if(EntityResourceConstants.SM_TRANSITION.equals(command)) {
    		TSECreateEdgeTool createEdgeTool = TSEditingToolHelper.getCreateEdgeTool(toolManager);
    		TransitionEdgeCreator  objectBuilder = new TransitionEdgeCreator();
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
		if (decisionTreeDiagramManager != null) {
			DrawingCanvas canvas = decisionTreeDiagramManager.getDrawingCanvas();
			TSToolManager toolManager = (TSToolManager) canvas.getToolManager();
			toolManager.setActiveTool(null);
		}

		super.dispose();
		decisionTreeDiagramManager = null;
		tool = null;
		compositeUI = null;
		decisionTreePopupMenuController = null;
		collapsedNodeUI = null;
	}
}