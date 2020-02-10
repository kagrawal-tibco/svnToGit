package com.tibco.cep.bpmn.ui.graph.tool;

import static com.tibco.cep.diagramming.utils.DiagramUtils.isClipBoardContentsAvailable;

import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JPopupMenu;

import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.tool.popup.SelectToolHandler;
import com.tibco.cep.diagramming.ui.RoundRectNodeUI;
import com.tomsawyer.drawing.TSEdgeLabel;
import com.tomsawyer.graph.TSGraphMember;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.complexity.TSEHidingManager;

/**
 * 
 * @author ggrigore
 *
 */
public class BpmnSelectToolHandler extends SelectToolHandler{

	public static void chooseState(AbstractButton button, TSEGraph graph, SelectTool tool, boolean isEditorEnabled)
	{
		String command = button.getActionCommand();
		TSEHidingManager hidingManager = (TSEHidingManager) TSEHidingManager.getManager(tool.getSwingCanvas().getGraphManager());

		if (EntityResourceConstants.HIT_IN_BREAKPOINT.equals(command)){
			if(!isEditorEnabled){
				button.setEnabled(false);
				return;
			}
			enableDisableMenu(EntityResourceConstants.HIT_IN_BREAKPOINT, tool, button);
		}

		if (EntityResourceConstants.HIT_OUT_BREAKPOINT.equals(command)){
			if(!isEditorEnabled){
				button.setEnabled(false);
				return;
			}
			enableDisableMenu(EntityResourceConstants.HIT_OUT_BREAKPOINT, tool, button);
		}

		if(EntityResourceConstants.UNDO.equals(command)){
			if(!isEditorEnabled){
				button.setEnabled(false);
				return;
			}
			//enableDisableMenu(EntityResourceConstants.UNDO, tool, button);
			button.setEnabled(true);
		}
		//For Delete Menu item
		if(EntityResourceConstants.DELETE_SELECTED.equals(command)){
			if(!isEditorEnabled){
				button.setEnabled(false);
				return;
			}
			enableDisableMenu(EntityResourceConstants.DELETE_SELECTED, tool, button);
		}
		if(EntityResourceConstants.CUT_GRAPH.equals(command)){
			if(!isEditorEnabled){
				button.setEnabled(false);
				return;
			}
			enableDisableMenu(EntityResourceConstants.CUT_GRAPH, tool, button);
		}
		if( EntityResourceConstants.COPY_GRAPH.equals(command)){
			if(!isEditorEnabled){
				button.setEnabled(false);
				return;
			}
			enableDisableMenu(EntityResourceConstants.COPY_GRAPH, tool, button);
		}
		if( EntityResourceConstants.PASTE_GRAPH.equals(command)){
			if(!isEditorEnabled){
				button.setEnabled(false);
				return;
			}
			button.setEnabled(isClipBoardContentsAvailable(((BpmnSelectTool)tool).getBpmnGraphDiagramManager()));
		}
		if (EntityResourceConstants.CREATE_CHILD_GRAPH.equals(command))
		{
			TSGraphMember member = (TSGraphMember) tool.hitObject;

			if (member instanceof TSENode)
			{
				button.setEnabled(!member.hasChildGraph());
			}
			else if (member instanceof TSEEdge)
			{
				button.setEnabled(!member.hasChildGraph() &&
						!((TSEEdge) member).isMetaEdge());
			}
		}
		else if (EntityResourceConstants.DELETE_CHILD_GRAPH.equals(command) ||
				EntityResourceConstants.GOTO_CHILD.equals(command))
		{
			TSGraphMember member = (TSGraphMember) tool.hitObject;

			if (member instanceof TSENode)
			{
				button.setEnabled(member.hasChildGraph() &&
						(!((TSENode) member).isFolderNode() ||
								!EntityResourceConstants.DELETE_CHILD_GRAPH.equals(command)));
			}
			else if (member instanceof TSEEdge)
			{
				button.setEnabled(member.hasChildGraph() &&
						!((TSEEdge) member).isMetaEdge());
			}
			else if (member == null)
			{
				button.setEnabled(tool.hitGraph.isChildGraph() &&
						!tool.hitGraph.isMainDisplayGraph());
			}
		}
		else if (EntityResourceConstants.GOTO_PARENT.equals(command))
		{
			button.setEnabled(graph.isOwned() &&
					graph.getParent() != null &&
					graph.getParent().isOwned());
		}
		else if (EntityResourceConstants.GOTO_ROOT.equals(command))
		{
			button.setEnabled(graph.isOwned() &&
					graph.getParent() != null &&
					graph.getParent().isOwned());
		}
		else if (EntityResourceConstants.EXPAND.equals(command))
		{
			TSGraphMember member = (TSGraphMember) tool.hitObject;

			if (member instanceof TSENode)
			{
				TSENode node = (TSENode) member;
				button.setEnabled(node.hasChildGraph() &&
						!node.isExpanded());
			}
		}
		else if (EntityResourceConstants.COLLAPSE.equals(command))
		{
			TSGraphMember member = (TSGraphMember) tool.hitObject;

			if (member instanceof TSENode)
			{
				TSENode node = (TSENode) member;
				button.setEnabled(node.hasChildGraph() &&
						node.isExpanded());
			}
			else if (member == null)
			{
				button.setEnabled((tool.hitGraph.isChildGraph()) &&
						!tool.hitGraph.isMainDisplayGraph());
			}
		}
		else if (EntityResourceConstants.ADD_NODE_CONNECTOR.equals(
				command))
		{
			TSGraphMember member = (TSGraphMember) tool.hitObject;

			boolean enable = false;

			if (member instanceof TSENode)
			{
				TSENode node = (TSENode) member;
				enable = true;

				Iterator<?> connectorIter = node.connectors().iterator();

				while (connectorIter.hasNext() && enable)
				{
					TSEConnector currentConnector =
							(TSEConnector) connectorIter.next();

					// if there is another connector do not place another
					// connector at the same point

					if (currentConnector.getBounds().contains(
							tool.hitPosition))
					{
						enable = false;
					}
				}
			}

			button.setEnabled(enable);
		}
		else if (command.startsWith(EntityResourceConstants.DELETE_NODE_CONNECTORS))
		{
			TSEObject object = tool.hitObject;

			boolean enable = false;

			if (object instanceof TSENode)
			{
				TSENode node = (TSENode) object;

				if (node.connectors().size() > 0)
				{
					enable = true;
				}
			}

			button.setEnabled(enable);
		}
		else if (command.startsWith(EntityResourceConstants.ADD_EDGE_LABEL))
		{
			button.setEnabled(true);
		}
		else if (EntityResourceConstants.HIDE_CHILDREN_ONE_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findChildren(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_CHILDREN_N_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findChildren(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_CHILDREN_ALL_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findChildren(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_PARENTS_ONE_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findParents(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_PARENTS_N_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findParents(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_PARENTS_ALL_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findParents(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_NEIGHBORS_ONE_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findNeighbors(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_NEIGHBORS_N_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findNeighbors(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_NEIGHBORS_ALL_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findNeighbors(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_INCIDENT_EDGES.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			button.setEnabled(node.inDegree() + node.outDegree() > 0);
		}
		else if (EntityResourceConstants.UNHIDE_CHILDREN_ONE_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenChildren(node));
		}
		else if (EntityResourceConstants.UNHIDE_CHILDREN_N_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenChildren(node));
		}
		else if (EntityResourceConstants.UNHIDE_CHILDREN_ALL_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenChildren(node));
		}
		else if (EntityResourceConstants.UNHIDE_PARENTS_ONE_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenParents(node));
		}
		else if (EntityResourceConstants.UNHIDE_PARENTS_N_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenParents(node));
		}
		else if (EntityResourceConstants.UNHIDE_PARENTS_ALL_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenParents(node));
		}
		else if (EntityResourceConstants.UNHIDE_NEIGHBORS_ONE_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenNeighbors(node));
		}
		else if (EntityResourceConstants.UNHIDE_NEIGHBORS_N_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenNeighbors(node));
		}
		else if (EntityResourceConstants.UNHIDE_NEIGHBORS_ALL_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenNeighbors(node));
		}
		else if (EntityResourceConstants.UNHIDE_INCIDENT_EDGES.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenIncidentEdges(node));
		}
		else if (EntityResourceConstants.FOLD_CHILDREN_ONE_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findChildren(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_CHILDREN_N_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findChildren(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_CHILDREN_ALL_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findChildren(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_PARENTS_ONE_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findParents(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_PARENTS_N_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findParents(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_PARENTS_ALL_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findParents(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_NEIGHBORS_ONE_LEVEL.equals(
				command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findNeighbors(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_NEIGHBORS_N_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findNeighbors(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_NEIGHBORS_ALL_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List<Object> nodeList = new Vector<Object>();
			node.findNeighbors(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.UNFOLD.equals(
				command))
		{
			button.setEnabled(tool.hitObject instanceof TSENode &&	((TSENode) tool.hitObject).isFolderNode());
		}
		else if (EntityResourceConstants.OPEN_URL.equals(command))
		{
			if (tool.hitObject != null)
			{
				button.setEnabled(tool.hitObject.getURL() != null);
			}
			else
			{
				button.setEnabled(graph.getURL() != null);
			}
		}
	}

	// Disables cut/copy/delete and other things for various types of BPMN flow elements.
	
	public static void enableDisableMenu(String type, SelectTool tool, AbstractButton button) {
		TSEObject object = tool.hitObject;
		if(object instanceof TSGraphMember) {
			final TSGraphMember member = (TSGraphMember) object;	
			if (member instanceof TSENode) {
				TSENode node = (TSENode) member;
				// TODO: this logic needs to be done
				if (type == EntityResourceConstants.CUT_GRAPH) {
					if(node.getUserObject() instanceof StateStart){
						button.setEnabled(false);
					}
				}
				else if (type == EntityResourceConstants.COPY_GRAPH) {
					if (node.getUserObject() instanceof StateStart){
						button.setEnabled(false);
					}
					else {
						button.setEnabled(true);
					}
				}
				else if (type == EntityResourceConstants.DELETE_SELECTED) {
				}
				else if (type == EntityResourceConstants.HIT_IN_BREAKPOINT ||
						type == EntityResourceConstants.HIT_OUT_BREAKPOINT) {
					if (node.getUI() instanceof RoundRectNodeUI /* ||
						node.getUI() instanceof ShapeNodeUI */) {
//						System.out.println("enabling...");
						button.setEnabled(true);
					}
					else {
//						System.out.println("disabling...");
						button.setEnabled(false);
					}
				}
			}
			else if (member instanceof TSEEdge) {
				if(type == EntityResourceConstants.CUT_GRAPH) { }
				if(type == EntityResourceConstants.COPY_GRAPH) { }
				if(type == EntityResourceConstants.DELETE_SELECTED) { }
			}
		} else if(object instanceof TSEdgeLabel) {
			return;
		} else if(object instanceof TSENodeLabel) {
			return;
		}
	}

	/**
	 * @param event
	 * @param graph
	 * @param tool
	 * @param menu
	 * @param inputBreakpoint
	 * @param inputBreakpointToggle
	 * @param outputBreakpoint
	 * @param outputBreakpointToggle
	 */
	public static void popupTriggered(MouseEvent event, 
									  TSEGraph graph, 
									  SelectTool tool, 
									  JPopupMenu menu,
									  boolean inputBreakpoint, 
									  boolean inputBreakpointToggle, 
									  boolean outputBreakpoint, 
									  boolean outputBreakpointToggle)	{
		if (menu != null) {
			tool.showPopup(menu, event.getPoint());
		} else if (inputBreakpoint && inputBreakpointToggle) {
			tool.showPopup(EntityResourceConstants.PROCESS_BREAKPOINT_PROPERTIES, event.getPoint());
		} else if (outputBreakpoint && outputBreakpointToggle) {
			tool.showPopup(EntityResourceConstants.PROCESS_BREAKPOINT_PROPERTIES, event.getPoint());
		} else if (!inputBreakpoint && ! outputBreakpoint) {
			SelectToolHandler.popupTriggered(event, graph, tool);
		}
	}
}