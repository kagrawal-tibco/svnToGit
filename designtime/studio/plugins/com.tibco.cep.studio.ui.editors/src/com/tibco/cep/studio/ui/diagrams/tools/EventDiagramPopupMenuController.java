package com.tibco.cep.studio.ui.diagrams.tools;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JPopupMenu;

import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.tool.popup.ResourceBundleManager;
import com.tibco.cep.studio.core.util.Utils;
/**
 * 
 * @author hitesh
 *
 */
public class EventDiagramPopupMenuController extends EntityPopupMenuController {

	@SuppressWarnings("unchecked")
	public EventDiagramPopupMenuController() {
		
		// Storage for popup menu

		EventSelectTool.menus = new Hashtable(10, 0.8f);

		JPopupMenu popup;
		
		if(!Utils.isStandaloneDecisionManger()){
		popup = ResourceBundleManager.getResources().createPopup("popup.node.event.view");

		if (popup != null)
		{
			EventSelectTool.menus.put(EntityResourceConstants.NODE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.graph.event.view");

		if (popup != null)
		{
			EventSelectTool.menus.put(EntityResourceConstants.GRAPH_POPUP, popup);
		}
		
		this.setPopupMenuListening();
		
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.popup.ContextMenuController#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	EventSelectTool currentSelectTool = (EventSelectTool) EventSelectTool.getTool();
	currentSelectTool.actionPerformed(e);
	String menuName = currentSelectTool.getActiveMenu();
	if (menuName != null)
		{
			JPopupMenu menu = (JPopupMenu)EventSelectTool.menus.get(menuName);
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
		if (EventSelectTool.menus != null)
		{
			Enumeration popups = EventSelectTool.menus.elements();
			while (popups.hasMoreElements())
			{
				JPopupMenu nextPopup = (JPopupMenu) popups.nextElement();
				this.setMenuElementListening(nextPopup);
			}
		}
	}
}
