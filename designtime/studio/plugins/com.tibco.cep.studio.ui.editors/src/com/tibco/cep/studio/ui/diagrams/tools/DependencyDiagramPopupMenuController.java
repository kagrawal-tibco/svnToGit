/**
 * 
 */
package com.tibco.cep.studio.ui.diagrams.tools;

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
 */
public class DependencyDiagramPopupMenuController extends ContextMenuController {

	public DependencyDiagramPopupMenuController() {
		
		// Storage for popup menu

		DependencySelectTool.menus = new Hashtable(10, 0.8f);

		JPopupMenu popup;
		
		popup = ResourceBundleManager.getResources().createPopup("popup.node.dependency.view");

		if (popup != null)
		{
			DependencySelectTool.menus.put(EntityResourceConstants.NODE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.graph.dependency.view");

		if (popup != null)
		{
			DependencySelectTool.menus.put(EntityResourceConstants.GRAPH_POPUP, popup);
		}
		
		this.setPopupMenuListening();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.popup.ContextMenuController#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	DependencySelectTool currentSelectTool = (DependencySelectTool) DependencySelectTool.getTool();
	currentSelectTool.actionPerformed(e);
	String menuName = currentSelectTool.getActiveMenu();
	if (menuName != null)
		{
			JPopupMenu menu = (JPopupMenu)DependencySelectTool.menus.get(menuName);
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
		if (DependencySelectTool.menus != null)
		{
			Enumeration popups = DependencySelectTool.menus.elements();
			while (popups.hasMoreElements())
			{
				JPopupMenu nextPopup = (JPopupMenu) popups.nextElement();
				this.setMenuElementListening(nextPopup);
			}
		}
	}
}
