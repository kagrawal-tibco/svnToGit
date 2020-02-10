package com.tibco.cep.studio.ui.statemachine.diagram.tool;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JPopupMenu;

import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.tool.popup.ResourceBundleManager;

public class StateMachinePopupMenuController extends ContextMenuController {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public StateMachinePopupMenuController() {
		
		// Storage for popup menu

		StateMachineSelectTool.menus = new Hashtable(10, 0.8f);

		JPopupMenu popup;
		
		popup = ResourceBundleManager.getResources().createPopup("popup.node.state.machine");

		if (popup != null)
		{
			StateMachineSelectTool.menus.put(EntityResourceConstants.NODE_POPUP, popup);
		}

//		popup = ResourceBundleManager.getResources().createPopup("popup.connector");
//
//		if (popup != null)
//		{
//			StateMachineSelectTool.menus.put(EntityResourceConstants.CONNECTOR_POPUP, popup);
//		}

		popup = ResourceBundleManager.getResources().createPopup("popup.edge.state.machine");

		if (popup != null)
		{
			StateMachineSelectTool.menus.put(EntityResourceConstants.EDGE_POPUP, popup);
		}

//		popup = ResourceBundleManager.getResources().createPopup("popup.edgeLabel");
//
//		if (popup != null)
//		{
//			StateMachineSelectTool.menus.put(EntityResourceConstants.EDGE_LABEL_POPUP, popup);
//		}
//
//		popup = ResourceBundleManager.getResources().createPopup("popup.nodeLabel");
//
//		if (popup != null)
//		{
//			StateMachineSelectTool.menus.put(EntityResourceConstants.NODE_LABEL_POPUP, popup);
//		}
//		
//		popup = ResourceBundleManager.getResources().createPopup("popup.connectorLabel");
//
//		if (popup != null)
//		{
//			StateMachineSelectTool.menus.put(EntityResourceConstants.CONNECTOR_LABEL_POPUP,	popup);
//		}

		popup = ResourceBundleManager.getResources().createPopup("popup.graph.state.machine");

		if (popup != null)
		{
			StateMachineSelectTool.menus.put(EntityResourceConstants.GRAPH_POPUP, popup);
		}

		this.setPopupMenuListening();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.popup.ContextMenuController#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	StateMachineSelectTool currentSelectTool = (StateMachineSelectTool) StateMachineSelectTool.getTool();
	currentSelectTool.actionPerformed(e);
	String menuName = currentSelectTool.getActiveMenu();
	if (menuName != null)
		{
			JPopupMenu menu = (JPopupMenu)StateMachineSelectTool.menus.get(menuName);
			if (menu != null) {
				menu.setInvoker(null); 
			}
			currentSelectTool.setActiveMenu(null);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.popup.ContextMenuController#setPopupMenuListening()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void setPopupMenuListening() {
		if (StateMachineSelectTool.menus != null)
		{
			Enumeration popups = StateMachineSelectTool.menus.elements();
			while (popups.hasMoreElements())
			{
				JPopupMenu nextPopup = (JPopupMenu) popups.nextElement();
				this.setMenuElementListening(nextPopup);
			}
		}
	}	
	
}
