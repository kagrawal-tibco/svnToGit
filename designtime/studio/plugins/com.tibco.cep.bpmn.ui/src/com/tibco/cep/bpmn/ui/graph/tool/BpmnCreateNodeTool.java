package com.tibco.cep.bpmn.ui.graph.tool;

import static com.tibco.cep.diagramming.tool.CreateToolUtils.handleOnPaletteToolSelection;
import static com.tibco.cep.diagramming.tool.CreateToolUtils.removeCreateToolListeners;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractNodeCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tibco.cep.bpmn.ui.graph.rule.DiagramRuleSet;
import com.tibco.cep.bpmn.ui.graph.rule.GenericNodeRule;
import com.tibco.cep.diagramming.tool.CreateToolListener;
import com.tibco.cep.diagramming.tool.ICreateTool;
import com.tibco.cep.diagramming.utils.TSImages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.TSCommandManager;
import com.tomsawyer.interactive.command.editing.TSEAddNodeCommand;
import com.tomsawyer.interactive.command.editing.TSEInsertNodeCommand;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;

/**
 * 
 * @author ggrigore
 *
 */
public class BpmnCreateNodeTool extends TSECreateNodeTool implements ICreateTool, IPartListener {
	// moved logic to diagram change listener. So this class is not needed now,
	// not until we want to customize something else about when we add nodes
	
	private boolean isEditorEnabled = true;
	private BpmnDiagramManager bpmnGraphDiagramManager;
	private DiagramRuleSet ruleSet;
	@SuppressWarnings("unused")
	private TSConstPoint lastMouseHitPoint;
	private CreateToolListener createDiagramToolKeyListener;
	
	public BpmnCreateNodeTool(	BpmnDiagramManager bpmnGraphDiagramManager, boolean isEditorEnabled) {
		this.bpmnGraphDiagramManager = bpmnGraphDiagramManager;
		this.isEditorEnabled = isEditorEnabled;
		this.ruleSet = new DiagramRuleSet();
		this.addAllRules();
		createDiagramToolKeyListener = new CreateToolListener(bpmnGraphDiagramManager, this);
		bpmnGraphDiagramManager.getDiagramEditorControl().addKeyListener(createDiagramToolKeyListener);
		if (bpmnGraphDiagramManager.getEditor() != null)
			bpmnGraphDiagramManager.getEditor().getEditorSite().getPage().addPartListener(this);
		bpmnGraphDiagramManager.addCreateToolPartListener(this);
	}
	
	public BpmnDiagramManager getBpmnGraphDiagramManager() {
		return bpmnGraphDiagramManager;
	}
	
	public boolean isEditorEnabled() {
		return isEditorEnabled;
	}
	
	@Override
	protected TSEAddNodeCommand newAddNodeCommand(TSEGraph ownerGraph, double x, double y) {
		EClass nodeType = (EClass) getVirtualNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		ENamedElement extType = (EClass) getVirtualNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		AbstractNodeCommandFactory cf = (AbstractNodeCommandFactory) getBpmnGraphDiagramManager().getModelGraphFactory().getCommandFactory(nodeType,extType);
		TSEGraph graph = getGraphAtPoint(new TSConstPoint(x,y));
		IGraphCommand<TSENode> cmd = cf.getCommand(IGraphCommand.COMMAND_ADD,graph,x,y);
		return  (TSEAddNodeCommand) cmd.getAdapter(TSEAddNodeCommand.class);	
	}
	
	@Override
	protected TSEInsertNodeCommand newInsertNodeCommand(TSEGraph selectedGraph,
			TSENode virtualNode) {

		EClass nodeType = (EClass) getVirtualNode().getAttributeValue(
				BpmnUIConstants.NODE_ATTR_TYPE);
		ENamedElement extType = (EClass) getVirtualNode().getAttributeValue(
				BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		AbstractNodeCommandFactory cf = (AbstractNodeCommandFactory) getBpmnGraphDiagramManager()
				.getModelGraphFactory().getCommandFactory(nodeType, extType);
		IGraphCommand<TSENode> cmd = cf.getCommand(
				IGraphCommand.COMMAND_INSERT, selectedGraph, virtualNode);
		return (TSEInsertNodeCommand) cmd
				.getAdapter(TSEInsertNodeCommand.class);

	}
	

	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool#createNode(com.tomsawyer.drawing.geometry.shared.TSConstPoint)
	 */
	public void createNode(com.tomsawyer.drawing.geometry.shared.TSConstPoint point) {
		if (isEditorEnabled) {
			//Resetting the Cursor
			if(getActionCursor()!=null){
				Cursor c = new Cursor(Cursor.DEFAULT_CURSOR);
				this.setActionCursor(c);
				this.setCursor(c);
			}
			
			if (this.isActionAllowed()) {
				if (!this.checkIntermediateEvent())
					super.createNode(point);
			} else{
				cancelAction();
			}			
			
		} else {
			if (this.getBpmnGraphDiagramManager().isResetToolOnChange()) {
				getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
				resetPaletteSelection();
			}
		}
		
	}
	
	
	/**
	 * @param graph
	 * @param point
	 * @return
	 */
	public TSENode createNode(TSEGraph graph, com.tomsawyer.drawing.geometry.shared.TSConstPoint point){
		TSEAddNodeCommand command = new TSEAddNodeCommand(graph ,point.getX(),point.getY());
		TSCommandManager commandManager = bpmnGraphDiagramManager.getCommandManager();
		commandManager.transmit(command);
		return command.getNode();
	}

	@SuppressWarnings("rawtypes")
	private boolean isActionAllowed() {
		// At this point we know we want to add a node. Grab the virtual
		// node that the user has been dragging.
		TSENode virtualNode = this.getVirtualNode();

		// Find all edges that the node intersects.
		TSEHitTesting hitTesting = this.getHitTesting();

		List edges = new Vector();

		hitTesting.findObjectsIntersectingRect(
				virtualNode.getBounds(),
				this.getGraph(),
				true,
				null,
				edges,
				null,
				null,
				null,
				null,
				null,
				null);	

		// If we are not over an edge, we use the base class' createNode
		// method which manages undo/redo using the swing canvas's
		// addNode method. If we are over an edge, you could instead allocate
		// a custom command command and transmit it through the canvas.
		boolean	isAllowed  = getRuleSet().isAllowed(virtualNode);
		if (!isAllowed) {
			try {
				Toolkit tk = Toolkit.getDefaultToolkit();
				Image image = BpmnImages.createIcon(
						"icons/Invalid10x10.png").getImage();
				Cursor c = tk.createCustomCursor(image, new Point(0, 0),
						"invalid");
				this.setActionCursor(c);
				this.setCursor(c);
			} catch (Exception e) {
				BpmnUIPlugin.log(e);
			}
		}
		return isAllowed;
	}
	
	/**
	 * @return
	 */
	public DiagramRuleSet getRuleSet() {
		return ruleSet;
	}
	
	/**
	 * 
	 */
	private void addAllRules() {
		getRuleSet().addRule(new GenericNodeRule(this.ruleSet));

	}

	public void onMousePressed(MouseEvent event) {
		try {
			if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
				// On right click, the default select tool reset. 
				getSwingCanvas().getToolManager().setActiveTool(
					TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
				resetPaletteSelection();
			} else {
				if (event.getSource() != null) {
					// if we are adding regions, don't call super if it's not legal...
					if (this.getGraphManager().getNodeBuilder() instanceof AbstractNodeUIFactory) {
						TSConstPoint point = this.getWorldPoint(event);
						TSEGraph graph = getGraphAtPoint(point);
						if (this.isValidRegionGraph(graph)) {
							lastMouseHitPoint = this.getNonalignedWorldPoint(event);
//							if (!this.checkIntermediateEvent(event)) {
								super.onMousePressed(event);
//							}
						}
						else {
							this.cancelAddRegionAction();
						}
					}
					// we're not adding a region node, just act as straight pass-thru
					else {
						super.onMousePressed(event);
					}
				}
				else {
					super.onMousePressed(event);
				}
			}
		} catch (Exception e){
			BpmnUIPlugin.log(e);
		}
	}
	
	private boolean checkIntermediateEvent() {
		// TODO: first check if we're adding intermediate events.
		// If not, return false right away.
		
		// get the point where the mouse is pressed.
//		TSEHitTesting hitTesting = this.getHitTesting();
//		TSToolPreferenceTailor tailor = new TSToolPreferenceTailor(
//			this.getSwingCanvas().getPreferenceData());
//		TSEObject object = hitTesting.getGraphObjectAt(
//			lastMouseHitPoint, this.getGraph(), tailor.isNestedGraphInteractionEnabled());
//		TSENode node = null;
//
//		if (object instanceof TSENode) {
//			node = (TSENode) object;
//		}
//		else if (object instanceof TSEConnector) {
//			node = (TSENode) ((TSEConnector) object).getOwner();
//		}
//
//		if (node != null) {
//			EClass nodeType = (EClass)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
//			if (nodeType != null && 
//					(BpmnModelClass.ACTIVITY.isSuperTypeOf(nodeType) )) {
//				EClass type = (EClass) getVirtualNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
//				EClass extType = (EClass) getVirtualNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
//				String toolId = (String)getVirtualNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
//				String attachedResource = (String)getVirtualNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
//				if(BpmnModelClass.CATCH_EVENT.isSuperTypeOf(type) ){
//					if(node.connectors().size() == 0){
//						AbstractConnectorCommandFactory cf = (AbstractConnectorCommandFactory) getBpmnGraphDiagramManager().getModelGraphFactory().getCommandFactory(BpmnModelClass.BOUNDARY_EVENT,extType);
//						IGraphCommand<TSEConnector> cmd = cf.getCommand(IGraphCommand.COMMAND_INSERT,node, attachedResource, toolId);
//						TSEAddConnectorCommand addCommand = (TSEAddConnectorCommand) cmd.getAdapter(TSEAddConnectorCommand.class);
//						bpmnGraphDiagramManager.executeCommand((TSCommand)addCommand);
//						getBpmnGraphDiagramManager().getModelGraphFactory().buildNodeRegistry();
//					}else
//						this.cancelAddRegionAction();
//					
//					return true;	
//				}
//				
//			}
//		}
		
		return false;
	}
	

	private TSEGraph getGraphAtPoint(TSConstPoint point) {
		TSEHitTesting hitTesting = this.getSwingCanvas().getHitTesting();
		TSEGraph graph = hitTesting.getGraphAt(point, this.getGraph());
		return graph;
	}
	
	
	private void cancelAddRegionAction() {
		this.cancelAction();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = TSImages.createIcon("icons/Invalid10x10.png").getImage();
		Cursor c = tk.createCustomCursor(image, new Point(0,0),"invalid");
		this.setActionCursor(c);
		this.setCursor(c);
	}
	
	private boolean isValidRegionGraph(TSEGraph graph) {
		Object graphObj = graph.getUserObject();
		if(graphObj instanceof EObject) {
			EObject userObj = (EObject) graphObj;
			if (userObj != null
					&& (BpmnModelClass.LANE.isSuperTypeOf(userObj.eClass())
					|| BpmnModelClass.SUB_PROCESS.isSuperTypeOf(userObj.eClass()))) {
				return true;
			}		
		}
		return false;
	}

	public void resetPaletteSelection(){
		StudioUIUtils.resetPaletteSelection();
	}
	
	
	@Override
	public void partActivated(IWorkbenchPart part) {
		controlKeylistnerMap.clear();
		paletteEntryList.clear();
		handleOnPaletteToolSelection(part, bpmnGraphDiagramManager, this, paletteEntryList, controlKeylistnerMap);
	}
	
	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		removeCreateToolListeners(controlKeylistnerMap);
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		removeCreateToolListeners(controlKeylistnerMap);
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		if (!bpmnGraphDiagramManager.getDiagramEditorControl().isDisposed()) {
			bpmnGraphDiagramManager.getDiagramEditorControl().removeKeyListener(createDiagramToolKeyListener);
		}
		createDiagramToolKeyListener = null;
		if (bpmnGraphDiagramManager.getEditor() != null) {
			bpmnGraphDiagramManager.getEditor().getEditorSite().getPage().removePartListener(this);
		}
		bpmnGraphDiagramManager = null;
		ruleSet = null;
	}

}
