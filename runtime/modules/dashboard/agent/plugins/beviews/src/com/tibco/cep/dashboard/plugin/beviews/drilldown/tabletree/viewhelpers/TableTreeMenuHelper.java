package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.viewhelpers;

import java.util.Iterator;
import java.util.List;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableMenu;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.popupmenu.tag.PopupMenu;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.popupmenu.tag.PopupMenuItem;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;

public class TableTreeMenuHelper {
	public static String getMenuHtml(BizSessionRequest request){
		List menus = (List)request.getAttribute("menus");
		if (menus == null)
		{
			return "";
		}
		Iterator itMenus = menus.iterator();
		StringBuffer buffer = new StringBuffer();
		while (itMenus.hasNext())
		{
			TableMenu tableMenu = (TableMenu)itMenus.next();
			PopupMenu popupMenu = new PopupMenu(null, tableMenu);
			popupMenu.outStartHtml(buffer);
			List<TableMenu> subMenus0 = tableMenu.getSubMenus();
			Iterator<TableMenu> itSubMenus0 = subMenus0.iterator();
			while (itSubMenus0.hasNext())
			{
				TableMenu subMenu0 = itSubMenus0.next();
				PopupMenu subPopupMenu0 = new PopupMenu(popupMenu, subMenu0);
				subPopupMenu0.outStartHtml(buffer);
				List<TableMenu> subMenus1 = subMenu0.getSubMenus();
				Iterator<TableMenu> itSubMenus1 = subMenus1.iterator();
				while (itSubMenus1.hasNext())
				{
					TableMenu subMenu1 = itSubMenus1.next();
					PopupMenu subPopupMenu1 = new PopupMenu(subPopupMenu0, subMenu1);
					subPopupMenu1.outStartHtml(buffer);
					//List subMenus2 = subMenu1.getSubMenus();
					PopupMenuItem popupMenuItem = new PopupMenuItem(subPopupMenu1, subMenu1, subMenu1.getMenuItems());
					popupMenuItem.outHtml(buffer);
					subPopupMenu1.outEndHtml(buffer);
				}
				PopupMenuItem popupMenuItem = new PopupMenuItem(subPopupMenu0, subMenu0, subMenu0.getMenuItems());
				popupMenuItem.outHtml(buffer);
				subPopupMenu0.outEndHtml(buffer);
			}
			PopupMenuItem popupMenuItem = new PopupMenuItem(popupMenu, tableMenu, tableMenu.getMenuItems());
			popupMenuItem.outHtml(buffer);
			popupMenu.outEndHtml(buffer);
		}
		return buffer.toString();
	}
}
