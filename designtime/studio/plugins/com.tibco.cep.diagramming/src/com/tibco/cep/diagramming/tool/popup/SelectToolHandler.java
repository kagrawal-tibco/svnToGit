package com.tibco.cep.diagramming.tool.popup;

import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.SwingUtilities;

import com.tibco.cep.diagramming.tool.SelectTool;
import com.tomsawyer.drawing.TSPNode;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graph.TSGraphMember;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEConnectorLabel;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.complexity.TSEHidingManager;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.tool.TSToolPreferenceTailor;

/**
 * 
 * @author ggrigore
 *
 */
public class SelectToolHandler {

	public static TSEObject tseObject;
	/**
	 * This method responds to a mouse event that activates popup
	 * menus. Depending on if the mouse is over a graph, edge,
	 * node, connector, or label, a popup is activated.
	 *
	 * @param
	 *		event the mouse event that invoked tool method.
	 */
	public static void popupTriggered(MouseEvent event, TSEGraph graph, SelectTool tool)
	{
			// cancel the current action of the
			// underlying tool of the swing canvas
			// Activator.LOGGER.logDebug("popup triggered!");
	
			tool.getSwingCanvas().cancelAction();
	
			// get the screen position of the click
	
			tool.clickPoint = event.getPoint();
			SwingUtilities.convertPointToScreen(tool.clickPoint, event.getComponent());
	
			// get the corresponding world point
			TSConstPoint point = tool.getNonalignedWorldPoint(event);
	
			// see if the user has hit an object in the graph
			TSEHitTesting hitTesting = tool.getHitTesting();
	
			// see if the user has hit an object in the graph
			TSToolPreferenceTailor tailor =	new TSToolPreferenceTailor(tool.getSwingCanvas().getPreferenceData());
			TSEObject object =	hitTesting.getGraphObjectAt(point, graph,	tailor.isNestedGraphInteractionEnabled());
			
			tseObject = object;
			
			if (tailor.isNestedGraphInteractionEnabled())
			{	
				tool.hitGraph = hitTesting.getGraphAt(point, graph);
			}
			else
			{
				tool.hitGraph = graph;
			}
	
			if (object != null)
			{
				tool.hitGraph = (TSEGraph) object.getOwnerGraph();
			}
			
			if (object == null)
			{
				// check to see if we clicked on a grapple of a
				// selected object. If so, use that object.
				TSESolidObject grappleOwner = hitTesting.getOwnerOfGrappleAt(point,graph,true);
				if ((grappleOwner != null) && (grappleOwner.isSelected()))
				{
					object = grappleOwner;
				}
			}
	
			// see if we clicked on the frame of an expanded node.
			// If so, use that node
			if ((object == null) && (tool.hitGraph != graph))
			{
				TSENode expandedNode = (TSENode) tool.hitGraph.getParent();
				
				if (expandedNode.intersectsNodeFrame(point))
				{
					object = expandedNode;
				}
			}
			
			tool.hitGraph.setSelected(true);
			
			// didn't hit an object, so select the graph
			
			if (object == null)
			{
				tool.hitObject = null;
				tool.showPopup(EntityResourceConstants.GRAPH_POPUP, event.getPoint());
			}
			else
			{
				// if the object the user clicked on was selected,
				// nothing changes, however if it was not, then it
				// becomes the only selected object of the graph,
				// provided the control key was not pressed, in which
				// case we also select tool new object
	
				if (!object.isSelected())
				{
					if (!event.isControlDown())
					{
						tool.getSwingCanvas().deselectAll(false);
					}
					
					tool.getSwingCanvas().selectObject(object, true);
				}
				
				// store the world location at which tool event occurred
				tool.hitPosition = point;
	
				// depending on the type of the selected object
				// show the appropriate menu
				
				if (object.isSelected()) {
//					CR 1-A79RU9 - commented the deselectAll because this allows to select multiple nodes at a time
//					tool.getSwingCanvas().deselectAll(false);
					tool.getSwingCanvas().selectObject(object, true);
					tool.getSwingCanvas().drawGraph();
					tool.getSwingCanvas().repaint();
				}
	
				if (object instanceof TSENode)
				{
					tool.hitObject = object;
					tool.showPopup(EntityResourceConstants.NODE_POPUP, event.getPoint());
				}
				else if (object instanceof TSEConnector)
				{
					tool.hitObject = object;
					tool.showPopup(EntityResourceConstants.CONNECTOR_POPUP, event.getPoint());
				}
				else if (object instanceof TSEEdge)
				{
					tool.hitObject = object;
					tool.showPopup(EntityResourceConstants.EDGE_POPUP, event.getPoint());
				}
				else if (object instanceof TSEEdgeLabel)
				{
					tool.hitObject = object;
					tool.showPopup(EntityResourceConstants.EDGE_LABEL_POPUP, event.getPoint());
				}
				else if (object instanceof TSENodeLabel)
				{
					tool.hitObject = object;
					tool.showPopup(EntityResourceConstants.NODE_LABEL_POPUP, event.getPoint());
				}
				else if (object instanceof TSEConnectorLabel)
				{
					tool.hitObject = object;
					tool.showPopup(EntityResourceConstants.CONNECTOR_LABEL_POPUP, event.getPoint());
				}
				else if (object instanceof TSPNode)
				{
					tool.hitObject = object;
					tool.showPopup(EntityResourceConstants.BEND_POPUP, event.getPoint());
				}
			}
		}
	
	/**
	 * This method sets the enabled state of the button based on
	 * its action command.
	 */
	@SuppressWarnings("rawtypes")
	public static void chooseState(AbstractButton button, TSEGraph graph,SelectTool tool)
	{
		String command = button.getActionCommand();
		
		TSEHidingManager hidingManager = (TSEHidingManager) TSEHidingManager.getManager(tool.getSwingCanvas().getGraphManager());

		if (EntityResourceConstants.CREATE_CHILD_GRAPH.equals(command))
		{
			TSGraphMember member = (TSGraphMember) tool.hitObject;

			if (member instanceof TSENode)
			{
				button.setEnabled(!member.hasChildGraph());
			}
			else if (member instanceof TSEEdge)
			{
				button.setEnabled(!member.hasChildGraph() && !((TSEEdge) member).isMetaEdge());
			}
		}
		else if (EntityResourceConstants.DELETE_CHILD_GRAPH.equals(command) ||
				EntityResourceConstants.GOTO_CHILD.equals(command))
		{
			TSGraphMember member = (TSGraphMember) tool.hitObject;
			
			if (member instanceof TSENode)
			{
				button.setEnabled(member.hasChildGraph() &&
					(!((TSENode) member).isFolderNode() || !EntityResourceConstants.DELETE_CHILD_GRAPH.equals(command)));
			}
			else if (member instanceof TSEEdge)
			{
				button.setEnabled(member.hasChildGraph() && !((TSEEdge) member).isMetaEdge());
			}
			else if (member == null)
			{
				button.setEnabled(tool.hitGraph.isChildGraph() && !tool.hitGraph.isMainDisplayGraph());
			}
		}
		else if (EntityResourceConstants.GOTO_PARENT.equals(command))
		{
			button.setEnabled(graph.isOwned() &&
					graph.getParent() != null && graph.getParent().isOwned());
		}
		else if (EntityResourceConstants.GOTO_ROOT.equals(command))
		{
			button.setEnabled(graph.isOwned() &&
					graph.getParent() != null && graph.getParent().isOwned());
		}
		else if (EntityResourceConstants.EXPAND.equals(command))
		{
			TSGraphMember member = (TSGraphMember) tool.hitObject;

			if (member instanceof TSENode)
			{
				TSENode node = (TSENode) member;
				button.setEnabled(node.hasChildGraph() && !node.isExpanded());
			}
		}
		else if (EntityResourceConstants.COLLAPSE.equals(command))
		{
			TSGraphMember member = (TSGraphMember) tool.hitObject;

			if (member instanceof TSENode)
			{
				TSENode node = (TSENode) member;
				button.setEnabled(node.hasChildGraph() && node.isExpanded());
			}
			else if (member == null)
			{
				button.setEnabled((tool.hitGraph.isChildGraph()) && !tool.hitGraph.isMainDisplayGraph());
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
					TSEConnector currentConnector = (TSEConnector) connectorIter.next();

					// if there is another connector do not place another
					// connector at the same point

					if (currentConnector.getBounds().contains(tool.hitPosition))
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
		else if (EntityResourceConstants.HIDE_CHILDREN_ONE_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findChildren(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_CHILDREN_N_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findChildren(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_CHILDREN_ALL_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findChildren(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_PARENTS_ONE_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findParents(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_PARENTS_N_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findParents(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_PARENTS_ALL_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findParents(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_NEIGHBORS_ONE_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findNeighbors(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_NEIGHBORS_N_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findNeighbors(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_NEIGHBORS_ALL_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findNeighbors(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.HIDE_INCIDENT_EDGES.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			button.setEnabled(node.inDegree() + node.outDegree() > 0);
		}
		else if (EntityResourceConstants.UNHIDE_CHILDREN_ONE_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenChildren(node));
		}
		else if (EntityResourceConstants.UNHIDE_CHILDREN_N_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenChildren(node));
		}
		else if (EntityResourceConstants.UNHIDE_CHILDREN_ALL_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenChildren(node));
		}
		else if (EntityResourceConstants.UNHIDE_PARENTS_ONE_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenParents(node));
		}
		else if (EntityResourceConstants.UNHIDE_PARENTS_N_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenParents(node));
		}
		else if (EntityResourceConstants.UNHIDE_PARENTS_ALL_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenParents(node));
		}
		else if (EntityResourceConstants.UNHIDE_NEIGHBORS_ONE_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;
			button.setEnabled(hidingManager.hasHiddenNeighbors(node));
		}
		else if (EntityResourceConstants.UNHIDE_NEIGHBORS_N_LEVEL.equals(command))
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

			List nodeList = new Vector();
			node.findChildren(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_CHILDREN_N_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findChildren(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_CHILDREN_ALL_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findChildren(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_PARENTS_ONE_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findParents(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_PARENTS_N_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findParents(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_PARENTS_ALL_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findParents(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_NEIGHBORS_ONE_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findNeighbors(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_NEIGHBORS_N_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findNeighbors(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.FOLD_NEIGHBORS_ALL_LEVEL.equals(command))
		{
			TSENode node = (TSENode) tool.hitObject;

			List nodeList = new Vector();
			node.findNeighbors(nodeList, null, 1);

			button.setEnabled(!nodeList.isEmpty());
		}
		else if (EntityResourceConstants.UNFOLD.equals(command))
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
		else
		{
			// Tomahawk.getInstance().chooseState(button);
		}

		// The following are handled in other places
		// such as the new property inspector, but are left
		// here to serve as an example if the functionality to
		// be implemented should be done from the popup menus.

//		else if (SelectToolConstants.COPY_GRAPH.equals(command))
//		{
//			button.setEnabled(
//				tool.getGraph().hasSelectedEdges() ||
//				tool.getGraph().hasSelectedNodes());
//		}
//		else if (SelectToolConstants.CUT_GRAPH.equals(command))
//		{
//			button.setEnabled(
//				tool.getGraph().hasSelectedEdges() ||
//				tool.getGraph().hasSelectedNodes());
//		}
//		else if (SelectToolConstants.PASTE_GRAPH.equals(command))
//		{
//			button.setEnabled(
//				tool.getSwingCanvas().canPaste());
//		}
//		else if (SelectToolConstants.DELETE_BACKGROUND_IMAGE.equals(
//			command))
//		{
//			button.setEnabled(
//				tool.getGraph().getGraphUI().
//					getBackgroundImage() != null);
//		}
//		else if (command.startsWith(SelectToolConstants.EDGE_ARROW))
//		{
//			if (button instanceof JCheckBoxMenuItem)
//			{
//				JCheckBoxMenuItem item = (JCheckBoxMenuItem) button;
//
//				// find out the type of the arrow of the edge the user
//				// clicked on has
//
//				int edgeType =
//					tool.hitObject.getEdgeUI().getArrowType();
//
//				int itemType =
//					command.charAt(command.length() - 1) - '0';
//
//				item.setState(itemType == edgeType);
//			}
//		}
	}
}
