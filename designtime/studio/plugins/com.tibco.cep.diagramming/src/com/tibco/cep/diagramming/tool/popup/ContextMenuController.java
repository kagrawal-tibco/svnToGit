package com.tibco.cep.diagramming.tool.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.MenuElement;

import com.tibco.cep.diagramming.tool.SelectTool;

public class ContextMenuController implements ActionListener{

	/**
	 * This method is called when an action is performed 
	 * through the popup menus
	 */
	public void actionPerformed(ActionEvent e)
	{
		// Tomahawk app = Tomahawk.getInstance();

		// if (app.hasCanvas())
		{
			// TODO: get current select tool in case there are multiple windows open
			// SelectTool currentSelectTool = (SelectTool) app.getCanvas().getToolManager().getDefaultTool();

			SelectTool currentSelectTool = SelectTool.getTool();
			
			// pass the action to the currently active
			// instance of SelectTool
			currentSelectTool.actionPerformed(e);
			
			// Now, cut the reference from the popup menu
			// to the tool, to avoid memory leak
			
			String menuName = currentSelectTool.getActiveMenu();
			
			if (menuName != null)
			{
				JPopupMenu menu = (JPopupMenu)SelectTool.menus.get(menuName);
			
				if (menu != null)
				{
					menu.setInvoker(null);
				}
				currentSelectTool.setActiveMenu(null);
			}
		}
	}


	/**
	 * This method adds this popup menu controller to listen to the 
	 * the popup menus and their subelements
	 */
	public void setPopupMenuListening()
	{
		if (SelectTool.menus != null)
		{
			Enumeration<?> popups = SelectTool.menus.elements();

			while (popups.hasMoreElements())
			{
				JPopupMenu nextPopup = (JPopupMenu) popups.nextElement();

				this.setMenuElementListening(nextPopup);
			}
		}
	}

	/**
	 * This method adds this popup menu controller to listen to the given
	 * menu element and its sub elements
	 */
	public void setMenuElementListening(MenuElement menuElement)
	{
		if (menuElement == null)
		{
		}
		else if (menuElement instanceof JPopupMenu || menuElement instanceof JMenu)
		{
			MenuElement[] subElements = menuElement.getSubElements();

			for (int i = 0; i < subElements.length; i++)
			{
				if (!(subElements[i] instanceof JSeparator))
				{
					this.setMenuElementListening(subElements[i]);
				}
			}
		}
		else if (menuElement instanceof JMenuItem)
		{
			int i = ((JMenuItem) menuElement).getActionListeners().length;
			if(i < 1) {
			((JMenuItem) menuElement).addActionListener(this);
			}
		}
	}
}
