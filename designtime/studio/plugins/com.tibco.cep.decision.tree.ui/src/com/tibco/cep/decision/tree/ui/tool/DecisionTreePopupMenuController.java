package com.tibco.cep.decision.tree.ui.tool;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JPopupMenu;

import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.tool.popup.ResourceBundleManager;

public class DecisionTreePopupMenuController extends ContextMenuController {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DecisionTreePopupMenuController() {

		// Storage for popup menu

		DecisionTreeSelectTool.menus = new Hashtable(10, 0.8f);
		JPopupMenu popup;

		popup = ResourceBundleManager.getResources().createPopup("popup.node.decision.tree");
		if (popup != null) {
			DecisionTreeSelectTool.menus.put(EntityResourceConstants.NODE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.edge.decision.tree");
		if (popup != null) {
			DecisionTreeSelectTool.menus.put(EntityResourceConstants.EDGE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.graph.decision.tree");
		if (popup != null) {
			DecisionTreeSelectTool.menus.put(EntityResourceConstants.GRAPH_POPUP, popup);
		}

		this.setPopupMenuListening();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.diagramming.tool.popup.ContextMenuController#actionPerformed
	 * (java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		DecisionTreeSelectTool currentSelectTool = (DecisionTreeSelectTool) DecisionTreeSelectTool.getTool();
		currentSelectTool.actionPerformed(e);
		String menuName = currentSelectTool.getActiveMenu();
		if (menuName != null) {
			JPopupMenu menu = (JPopupMenu) DecisionTreeSelectTool.menus.get(menuName);
			if (menu != null) {
				menu.setInvoker(null);
			}
			currentSelectTool.setActiveMenu(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.tool.popup.ContextMenuController#
	 * setPopupMenuListening()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void setPopupMenuListening() {
		if (DecisionTreeSelectTool.menus != null) {
			Enumeration popups = DecisionTreeSelectTool.menus.elements();
			while (popups.hasMoreElements()) {
				JPopupMenu nextPopup = (JPopupMenu) popups.nextElement();
				this.setMenuElementListening(nextPopup);
			}
		}
	}
}
