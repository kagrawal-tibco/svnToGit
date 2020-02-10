package com.tibco.cep.bpmn.ui.graph.tool;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JPopupMenu;

import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.tool.popup.ResourceBundleManager;

/**
 * 
 * @author ggrigore
 *
 */
public class BpmnPopupMenuController extends ContextMenuController {

	@SuppressWarnings("unchecked")
	public BpmnPopupMenuController() {

		// Storage for popup menu

		BpmnSelectTool.menus = new Hashtable<Object,Object>(10, 0.8f);

		JPopupMenu popup;

		popup = ResourceBundleManager.getResources().createPopup("popup.node.bpmn.task");

		
		if (popup != null)
		{
			BpmnSelectTool.menus.put(EntityResourceConstants.NODE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.edge.state.machine");

		if (popup != null)
		{
			BpmnSelectTool.menus.put(EntityResourceConstants.EDGE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.graph.bpmn.task");

		if (popup != null)
		{
			BpmnSelectTool.menus.put(EntityResourceConstants.GRAPH_POPUP, popup);
		}
		
		popup = ResourceBundleManager.getResources().createPopup("popup.bpmn.node.breakpoint");
		if (popup != null)
		{
			BpmnSelectTool.menus.put(EntityResourceConstants.PROCESS_BREAKPOINT_PROPERTIES, popup);
		}
		
		this.setPopupMenuListening();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.popup.ContextMenuController#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		BpmnSelectTool currentSelectTool = (BpmnSelectTool) BpmnSelectTool.getTool();
		currentSelectTool.actionPerformed(e);
		String menuName = currentSelectTool.getActiveMenu();
		if (menuName != null)
		{
			JPopupMenu menu = (JPopupMenu)BpmnSelectTool.menus.get(menuName);
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
		if (BpmnSelectTool.menus != null)
		{
			Enumeration<?> popups = BpmnSelectTool.menus.elements();
			while (popups.hasMoreElements())
			{
				JPopupMenu nextPopup = (JPopupMenu) popups.nextElement();
				this.setMenuElementListening(nextPopup);
			}
		}
	}	

}
