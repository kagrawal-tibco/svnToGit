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
public class ConceptDiagramPopupMenuController extends EntityPopupMenuController{

	@SuppressWarnings("unchecked")
	public ConceptDiagramPopupMenuController() {
		
		// Storage for popup menu

		ConceptSelectTool.menus = new Hashtable(10, 0.8f);

		JPopupMenu popup;
		if(!Utils.isStandaloneDecisionManger()){
			popup = ResourceBundleManager.getResources().createPopup("popup.node.concept.view");
			

			if (popup != null)
			{
				ConceptSelectTool.menus.put(EntityResourceConstants.NODE_POPUP, popup);
			
			}

			popup = ResourceBundleManager.getResources().createPopup("popup.graph.concept.view");

			if (popup != null)
			{
				ConceptSelectTool.menus.put(EntityResourceConstants.GRAPH_POPUP, popup);
			}
			
			popup = ResourceBundleManager.getResources().createPopup("popup.edge.concept.view");

			if (popup != null)
			{
				ConceptSelectTool.menus.put(EntityResourceConstants.EDGE_POPUP, popup);
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
	ConceptSelectTool currentSelectTool = (ConceptSelectTool) ConceptSelectTool.getTool();
	currentSelectTool.actionPerformed(e);
	String menuName = currentSelectTool.getActiveMenu();
	if (menuName != null)
		{
			JPopupMenu menu = (JPopupMenu)ConceptSelectTool.menus.get(menuName);
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
		if (ConceptSelectTool.menus != null)
		{
			Enumeration popups = ConceptSelectTool.menus.elements();
			while (popups.hasMoreElements())
			{
				JPopupMenu nextPopup = (JPopupMenu) popups.nextElement();
				this.setMenuElementListening(nextPopup);
			}
		}
	}
}
