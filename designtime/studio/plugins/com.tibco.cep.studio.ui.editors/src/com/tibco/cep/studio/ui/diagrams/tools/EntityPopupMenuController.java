package com.tibco.cep.studio.ui.diagrams.tools;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JPopupMenu;

import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.tool.popup.ResourceBundleManager;

public class EntityPopupMenuController extends ContextMenuController {

	public EntityPopupMenuController()
	{
		// create storage for popup menus we are about to create

		EntitySelectTool.menus = new Hashtable(10, 0.8f);

		// we recognize 8 popup types: node, connector, edge, bend,
		// node label, edge label, graph, and tab

		JPopupMenu popup;

		// note: we're handling the listening to popup menus internally
		// in this state, so set listener argument as null

		popup = ResourceBundleManager.getResources().createPopup("popup.node");

		if (popup != null)
		{
			EntitySelectTool.menus.put(EntityResourceConstants.NODE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.connector");

		if (popup != null)
		{
			EntitySelectTool.menus.put(EntityResourceConstants.CONNECTOR_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.edge");

		if (popup != null)
		{
			EntitySelectTool.menus.put(EntityResourceConstants.EDGE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.edgeLabel");

		if (popup != null)
		{
			EntitySelectTool.menus.put(EntityResourceConstants.EDGE_LABEL_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.nodeLabel");

		if (popup != null)
		{
			EntitySelectTool.menus.put(EntityResourceConstants.NODE_LABEL_POPUP, popup);
		}
		
		popup = ResourceBundleManager.getResources().createPopup("popup.connectorLabel");

		if (popup != null)
		{
			EntitySelectTool.menus.put(EntityResourceConstants.CONNECTOR_LABEL_POPUP,	popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.graph");

		if (popup != null)
		{
			EntitySelectTool.menus.put(EntityResourceConstants.GRAPH_POPUP, popup);
		}

		this.setPopupMenuListening();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.popup.ContextMenuController#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	/*EntitySelectTool currentSelectTool = (EntitySelectTool) EntitySelectTool.getTool();
	currentSelectTool.actionPerformed(e);
	String menuName = currentSelectTool.getActiveMenu();
	if (menuName != null)
		{
		  JPopupMenu menu = (JPopupMenu)EntitySelectTool.menus.get(menuName);
		  if (menu != null) {
			   menu.setInvoker(null); 
		  }
		  currentSelectTool.setActiveMenu(null);
		}*/
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.popup.ContextMenuController#setPopupMenuListening()
	 */
	@Override
	public void setPopupMenuListening() {
		if (EntitySelectTool.menus != null)
		{
			Enumeration popups = EntitySelectTool.menus.elements();
			while (popups.hasMoreElements())
			{
				JPopupMenu nextPopup = (JPopupMenu) popups.nextElement();
				this.setMenuElementListening(nextPopup);
			}
		}
	}
}
