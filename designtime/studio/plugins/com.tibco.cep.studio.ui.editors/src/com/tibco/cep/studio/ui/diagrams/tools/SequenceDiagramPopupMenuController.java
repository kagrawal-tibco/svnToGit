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
 * @author hnembhwa
 *
 */
public class SequenceDiagramPopupMenuController extends ContextMenuController {

	@SuppressWarnings("unchecked")
	public SequenceDiagramPopupMenuController() {
		
		// Storage for popup menu

		SequenceSelectTool.menus = new Hashtable(10, 0.8f);

		JPopupMenu popup;
		
		popup = ResourceBundleManager.getResources().createPopup("popup.node.sequence.view");

		if (popup != null)
		{
			SequenceSelectTool.menus.put(EntityResourceConstants.NODE_POPUP, popup);
		}

		popup = ResourceBundleManager.getResources().createPopup("popup.graph.sequence.view");

		if (popup != null)
		{
			SequenceSelectTool.menus.put(EntityResourceConstants.GRAPH_POPUP, popup);
		}
		
		this.setPopupMenuListening();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.popup.ContextMenuController#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	SequenceSelectTool currentSelectTool = (SequenceSelectTool) SequenceSelectTool.getTool();
	currentSelectTool.actionPerformed(e);
	String menuName = currentSelectTool.getActiveMenu();
	if (menuName != null)
		{
			JPopupMenu menu = (JPopupMenu)SequenceSelectTool.menus.get(menuName);
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
		if (SequenceSelectTool.menus != null)
		{
			Enumeration popups = SequenceSelectTool.menus.elements();
			while (popups.hasMoreElements())
			{
				JPopupMenu nextPopup = (JPopupMenu) popups.nextElement();
				this.setMenuElementListening(nextPopup);
			}
		}
	}
}
