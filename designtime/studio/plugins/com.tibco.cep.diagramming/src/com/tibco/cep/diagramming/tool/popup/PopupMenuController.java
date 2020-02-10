package com.tibco.cep.diagramming.tool.popup;

import java.util.Hashtable;

import javax.swing.JPopupMenu;

import com.tibco.cep.diagramming.tool.SelectTool;

/**
 * This class controls the creation of popup menus and redirects
 * actions performed by the popup menus to the currently active
 * instance of ExSelectState.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public  class PopupMenuController extends ContextMenuController
{


	public PopupMenuController()
	{
		// create storage for popup menus we are about to create

		SelectTool.menus = new Hashtable(10, 0.8f);

		// we recognize 8 popup types: node, connector, edge, bend,
		// node label, edge label, graph, and tab

		JPopupMenu popup;

		// note: we're handling the listening to popup menus internally
		// in this state, so set listener argument as null

		popup = ResourceBundleManager.getResources().createPopup("popup.node");

		if (popup != null)
		{
			SelectTool.menus.put(EntityResourceConstants.NODE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.connector");

		if (popup != null)
		{
			SelectTool.menus.put(EntityResourceConstants.CONNECTOR_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.edge");

		if (popup != null)
		{
			SelectTool.menus.put(EntityResourceConstants.EDGE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.edgeLabel");

		if (popup != null)
		{
			SelectTool.menus.put(EntityResourceConstants.EDGE_LABEL_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.nodeLabel");

		if (popup != null)
		{
			SelectTool.menus.put(EntityResourceConstants.NODE_LABEL_POPUP, popup);
		}
		
		popup = ResourceBundleManager.getResources().createPopup("popup.connectorLabel");

		if (popup != null)
		{
			SelectTool.menus.put(EntityResourceConstants.CONNECTOR_LABEL_POPUP,	popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.graph");

		if (popup != null)
		{
			SelectTool.menus.put(EntityResourceConstants.GRAPH_POPUP, popup);
		}

		this.setPopupMenuListening();
	}
}