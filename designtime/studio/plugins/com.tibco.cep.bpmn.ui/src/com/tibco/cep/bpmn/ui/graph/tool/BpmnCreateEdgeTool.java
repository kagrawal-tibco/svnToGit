package com.tibco.cep.bpmn.ui.graph.tool;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractEdgeCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tibco.cep.bpmn.ui.graph.rule.AssociationRule;
import com.tibco.cep.bpmn.ui.graph.rule.ConnectorSequenceRule;
import com.tibco.cep.bpmn.ui.graph.rule.DiagramRuleSet;
import com.tibco.cep.bpmn.ui.graph.rule.FlowNodeRule;
import com.tibco.cep.bpmn.ui.graph.rule.MultipleSequenceflowRule;
import com.tibco.cep.diagramming.tool.CreateEdgeTool;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.drawing.TSConnector;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.TSCommandManager;
import com.tomsawyer.interactive.command.editing.TSEAddEdgeCommand;

public class BpmnCreateEdgeTool extends CreateEdgeTool {

	private DiagramRuleSet ruleSet;
	@SuppressWarnings("unused")
	private DiagramRuleSet rulesetForDrag;
	@SuppressWarnings("unused")
	private boolean isEditorEnabled = true;
	private BpmnDiagramManager bpmnGraphDiagramManager;
	private MultipleSequenceflowRule multipleSequenceflowRule;
	private ConnectorSequenceRule connectorSequenceRule;
	
	private static Map<JComponent, JToolTip> toolTipMap = new HashMap<JComponent, JToolTip>();
	private Popup tooltipContainer;


	public BpmnCreateEdgeTool(BpmnDiagramManager bpmnGraphDiagramManager, boolean isEditorEnabled) {
		super(bpmnGraphDiagramManager);
		this.bpmnGraphDiagramManager = bpmnGraphDiagramManager;
		this.isEditorEnabled = isEditorEnabled;
		this.ruleSet = new DiagramRuleSet();
		this.addAllRules();
		multipleSequenceflowRule = new MultipleSequenceflowRule(this.ruleSet, false);
		connectorSequenceRule = new ConnectorSequenceRule(this.ruleSet);
	}

	/**
	 * @return
	 */
	public BpmnDiagramManager getBpmnGraphDiagramManager() {
		return bpmnGraphDiagramManager;
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
		getRuleSet().addRule(new FlowNodeRule(this.ruleSet));
		getRuleSet().addRule(new AssociationRule(this.ruleSet));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.diagramming.tool.CreateEdgeTool#onMouseReleased(java.awt
	 * .event.MouseEvent)
	 */
	public void onMouseReleased(MouseEvent event) {
		if (tooltipContainer != null) {
			tooltipContainer.hide();
		}
		// Resetting the Cursor
		if (getActionCursor() != null && getActionCursor().getName().equalsIgnoreCase("Invalid")) {
			// Cursor c = new Cursor(Cursor.DEFAULT_CURSOR);
			this.setActionCursor(createEdgeCursor);
			this.setCursor(createEdgeCursor);
		}

		// We consume left mouse button events
		if ((event.getModifiers() & MouseEvent.BUTTON1_MASK) != 0 && !event.isPopupTrigger()) {
			if (this.isBuildingEdge()) {
				TSConstPoint point = this.getWorldPoint(event);
				boolean foundTarget = this.possibleTargetAt(point);
				if (foundTarget) {
					boolean canConnectEdge = this.canConnectEdge(point);
					if (canConnectEdge || true) {
						// if found a possible target while
						// building and can connect the edge then connect the
						// edge
						if (this.getTargetNode() == null)
							return; // Fixed NPE Null Target Selection
						this.addDirtyRegion(this.getTargetNode());

						if ((!event.isShiftDown()) && (!event.isControlDown())) {
							this.getSwingCanvas().deselectAll(false);
						}

						if (this.isActionAllowed()) {
							this.connectEdge();
							this.setBuildingEdge(false);
						} else {
							if (connectorSequenceRule.getValidSequenceMessage() != null) {
								String tooltip = "<html><b>Connection Error:</b><br>" + connectorSequenceRule.getValidSequenceMessage() +"</html>";
								showToolTip(getSwingCanvas(), tooltip , event.getXOnScreen(), event.getYOnScreen());
							}
							if (multipleSequenceflowRule.getValidSequenceMessage() != null) {
								String tooltip = "<html><b>Connection Error:</b><br>" + multipleSequenceflowRule.getValidSequenceMessage()+"</html>";
								showToolTip(getSwingCanvas(),  tooltip, event.getXOnScreen(), event.getYOnScreen());
							}
						}
					}
				}
				// allow user to add a bend by dragging from the source node
				else if (this.inFirstClick) {
					if (this.mouseMoved) {
						this.addBendPoint(point);
					} else {
						this.cancelAction();
					}
				}

				this.inFirstClick = false;
			}
		} else {
			super.onMouseReleased(event);
		}
	}
	
	@Override
	public void addBendPoint(TSConstPoint point) {
		if (tooltipContainer != null) {
			tooltipContainer.hide();
		}
		super.addBendPoint(point);
	}
	
	@Override
	public void cancelAction() {
		if (tooltipContainer != null) {
			tooltipContainer.hide();
		}
		super.cancelAction();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.tool.CreateEdgeTool#connectEdge()
	 */
	public TSEEdge connectEdge() {
		if (this.isActionAllowed()) {
			this.setBuildingEdge(false);
			return super.connectEdge();
		} else {
			this.cancelAction();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.tool.CreateEdgeTool#isActionAllowed()
	 */
	public boolean isActionAllowed() {
		TSENode srcTSNode = this.getSourceNode();
		TSENode tgtTSNode = this.getTargetNode();
		TSEEdge edge = this.getEdge();
		boolean connectedToConnector = false;
		if (getSourceConnector() != null) {
			TSConnector sourceConnector = getSourceConnector();
			Object userObject = sourceConnector.getUserObject();
			if (userObject != null && (userObject instanceof EObject)) {
				EObject connec = (EObject) userObject;
				if (connec.eClass().equals(BpmnModelClass.BOUNDARY_EVENT))
					connectedToConnector = true;
			}
		}
		boolean isAllowed = true;
		if (connectedToConnector) {
			isAllowed = connectorSequenceRule.isAllowed(new Object[] { getSourceConnector(), tgtTSNode, edge });
		} else {
			isAllowed = getRuleSet().isAllowed(srcTSNode, tgtTSNode, edge);
			EClass edgeType = (EClass) edge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			if (isAllowed && edgeType.equals(BpmnModelClass.SEQUENCE_FLOW)) {
				isAllowed = multipleSequenceflowRule.isAllowed(new Object[] { srcTSNode, tgtTSNode, edge });
			}
		}
		if (!isAllowed) {
			try {
				Toolkit tk = Toolkit.getDefaultToolkit();
				Image image = BpmnImages.createIcon("icons/Invalid10x10.png").getImage();
				Cursor c = tk.createCustomCursor(image, new Point(0, 0), "invalid");
				this.setActionCursor(c);
				this.setCursor(c);
			} catch (Exception e) {
				BpmnUIPlugin.log(e);
			}
		}

		return isAllowed;
	}
	
	/**
	 * Show Tooltip for action not allowed
	 * @param component
	 * @param tip
	 * @param x
	 * @param y
	 */
	public void showToolTip(final JComponent component, String tip, int x, int y) {
		JToolTip tooltip = toolTipMap.get(component);
		if (tooltip == null) {
			tooltip = component.createToolTip();
			toolTipMap.put(component, tooltip);
		} 
		tooltip.setTipText(tip);
		tooltipContainer = PopupFactory.getSharedInstance().getPopup(component, tooltip, x, y);
		tooltipContainer.show(); 
	}


	@SuppressWarnings("rawtypes")
	@Override
	protected TSEAddEdgeCommand newAddEdgeCommand(TSENode sourceNode, TSENode targetNode, List bendList) {
		boolean connectedToConnector = false;
		if (getSourceConnector() != null) {
			TSConnector sourceConnector = getSourceConnector();
			Object userObject = sourceConnector.getUserObject();
			if (userObject != null && (userObject instanceof EObject)) {
				EObject connec = (EObject) userObject;
				if (connec.eClass().equals(BpmnModelClass.BOUNDARY_EVENT))
					connectedToConnector = true;
			}
		}
		EClass sourceNodeType = (EClass) sourceNode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		EClass targetNodeType = (EClass) targetNode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		AbstractEdgeCommandFactory cf = null;
		if (BpmnModelClass.TEXT_ANNOTATION.isSuperTypeOf(sourceNodeType) || BpmnModelClass.TEXT_ANNOTATION.isSuperTypeOf(targetNodeType)) {
			// at least one needs to be an artifact
			cf = getBpmnGraphDiagramManager().getModelGraphFactory().getEdgeCommandFactory(BpmnModelClass.ASSOCIATION);
		} else if (BpmnModelClass.FLOW_NODE.isSuperTypeOf(sourceNodeType) && BpmnModelClass.FLOW_NODE.isSuperTypeOf(targetNodeType)) {
			// both nodes need to be a flow node
			cf = getBpmnGraphDiagramManager().getModelGraphFactory().getEdgeCommandFactory(BpmnModelClass.SEQUENCE_FLOW);
		}
		IGraphCommand<TSEEdge> cmd = cf.getCommand(IGraphCommand.COMMAND_ADD, sourceNode, targetNode, bendList, connectedToConnector);
		TSEAddEdgeCommand command = (TSEAddEdgeCommand) cmd.getAdapter(TSEAddEdgeCommand.class);

		@SuppressWarnings("unused")
		TSCommandManager commandManager = getSwingCanvas().getCommandManager();
		// commandManager.transmit(command);

		// getBpmnGraphDiagramManager().refreshEdge(command.getEdge());
		return command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.diagramming.tool.CreateNodeTool#resetPaletteSelection()
	 */
	@Override
	public void resetPaletteSelection() {
		StudioUIUtils.resetPaletteSelection();
	}

}
