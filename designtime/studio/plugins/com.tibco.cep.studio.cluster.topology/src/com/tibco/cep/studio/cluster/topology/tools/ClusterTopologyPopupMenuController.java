package com.tibco.cep.studio.cluster.topology.tools;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JPopupMenu;

import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.tool.popup.ResourceBundleManager;

/**
 * @author hitesh
 *
 **/

public class ClusterTopologyPopupMenuController extends ContextMenuController {

	public ClusterTopologyPopupMenuController() {
		// create storage for popup menus we are about to create

		ClusterTopologySelectTool.menus = new Hashtable<String,JPopupMenu>(10, 0.8f);

		// we recognize 8 popup types: node, connector, edge, bend,
		// node label, edge label, graph, and tab

		JPopupMenu popup;

		// note: we're handling the listening to popup menus internally
		// in this state, so set listener argument as null

		popup = ResourceBundleManager.getResources().createPopup("popup.node.cluster.view");

		if (popup != null)
		{
			ClusterTopologySelectTool.menus.put(EntityResourceConstants.NODE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.edge.cluster.view");

		if (popup != null)
		{
			ClusterTopologySelectTool.menus.put(EntityResourceConstants.EDGE_POPUP, popup);
		}
		
		popup = ResourceBundleManager.getResources().createPopup("popup.graph.cluster.view");

		if (popup != null)
		{
			ClusterTopologySelectTool.menus.put(EntityResourceConstants.GRAPH_POPUP, popup);
		}

		this.setPopupMenuListening();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.popup.ContextMenuController#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	ClusterTopologySelectTool currentSelectTool = (ClusterTopologySelectTool) ClusterTopologySelectTool.getTool();
	currentSelectTool.actionPerformed(e);
	String menuName = currentSelectTool.getActiveMenu();
	if (menuName != null)
		{
		  JPopupMenu menu = (JPopupMenu)ClusterTopologySelectTool.menus.get(menuName);
		  if (menu != null) {
			   menu.setInvoker(null); 
		  }
		  currentSelectTool.setActiveMenu(null);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.popup.ContextMenuController#setPopupMenuListening()
	 */
	@Override
	public void setPopupMenuListening() {
		if (ClusterTopologySelectTool.menus != null)
		{
			Enumeration<?> popups = ClusterTopologySelectTool.menus.elements();
			while (popups.hasMoreElements())
			{
				JPopupMenu nextPopup = (JPopupMenu) popups.nextElement();
				this.setMenuElementListening(nextPopup);
			}
		}
	}
}
