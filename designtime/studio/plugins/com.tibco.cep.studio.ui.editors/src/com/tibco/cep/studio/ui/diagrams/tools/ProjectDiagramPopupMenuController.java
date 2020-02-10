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
 */
public class ProjectDiagramPopupMenuController extends ContextMenuController {

	@SuppressWarnings("unchecked")
	public ProjectDiagramPopupMenuController() {
		
		// Storage for popup menu

		ProjectSelectTool.menus = new Hashtable(10, 0.8f);

		JPopupMenu popup;
		
		popup = ResourceBundleManager.getResources().createPopup("popup.node.project.view");

		if (popup != null)
		{
			ProjectSelectTool.menus.put(EntityResourceConstants.NODE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.graph.project.view");

		if (popup != null)
		{
			ProjectSelectTool.menus.put(EntityResourceConstants.GRAPH_POPUP, popup);
		}
		
		this.setPopupMenuListening();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.popup.ContextMenuController#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	ProjectSelectTool currentSelectTool = (ProjectSelectTool) ProjectSelectTool.getTool();
	currentSelectTool.actionPerformed(e);
	String menuName = currentSelectTool.getActiveMenu();
	if (menuName != null)
		{
			JPopupMenu menu = (JPopupMenu)ProjectSelectTool.menus.get(menuName);
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
		if (ProjectSelectTool.menus != null)
		{
			Enumeration popups = ProjectSelectTool.menus.elements();
			while (popups.hasMoreElements())
			{
				JPopupMenu nextPopup = (JPopupMenu) popups.nextElement();
				this.setMenuElementListening(nextPopup);
			}
		}
	}
}
