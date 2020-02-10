package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.popupmenu.tag;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableMenu;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.popupmenu.model.MenuProperty;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PopupMenuItem {

	private final TableMenu menu;
	private final PopupMenu popupMenu;

	/**
	 *
	 */
	public PopupMenuItem(PopupMenu popupMenu, TableMenu menu, MenuProperty[] menuItems) {
		this.popupMenu = popupMenu;
		this.menu = menu;
		this.menuItems = menuItems;
		setMenuImg(menu.isBImage());
		setMenuText(menu.getText());
	}

	private String menuClickedEvent;
	private boolean menuImg;

	private boolean separator;

	private String menuSeparatorStyle = "clsMenuSeparator";

	private String menuRowStyle = "clsMenuRow";

	private String menuImgCellStyle = "clsMenuImageTD";

	private String menuImgStyle = "clsMenuImg";

	private String menuImageSrc;// = "tabletree/images/menuimg.gif";

	private String menuTextCellStyle = "clsMenuNormalTD";

	private String menuText = "";

	private String menuItemId = "";

	private String menuParam = "";

	private MenuProperty menuItems[] = null;
	private boolean menuEnable = true;

	public void outHtml(StringBuffer buffer) {
		if (isSeparator()) {
			outMenuSeparator(buffer);
		} else {
			if (menuItems == null) {
				outMenuItem(buffer);
			} else {
				outMenuItems(buffer);
			}
		}
	}

	/**
	 *
	 */
	private void outMenuItem(StringBuffer buffer) {
		buffer.append("<TR ");
		if (!menuEnable) {
			buffer.append(" class='" + menuRowStyle + "'");
		} else {
			buffer.append(" class='" + "clsMenuDisableTD" + "'");
		}

		buffer.append(" style='font-family: Verdana, Serif;font-size: 9;color: #000000;border-left-style: solid;border-left-width: 2px;border-left-color: #ffffff;background-color: #ffffff;cursor: pointer;'");

		buffer.append(" id='" + computeId() + "'");
		buffer.append(" onmouseover='" + mouseOverEvent() + "'");
		buffer.append(" onmouseout='" + mouseOutEvent() + "'");
		buffer.append(" onclick=\"" + menuClicked() + "\"");
		if (!menuEnable) {
			buffer.append(" c_enable='" + menuEnable + "'");
		}
		buffer.append(">");

		if (isMenuImg()) {
			if (menuEnable) {
				buffer.append("<TD class='" + menuImgCellStyle + "'>");
				buffer.append("<IMG class='" + menuImgStyle + "'");
			} else {
				buffer.append("<TD class='" + "clsMenuDisableTD" + "'>");
				buffer.append("<IMG class='" + "clsMenuDisableTD" + "'");
			}

			buffer.append(" src='" + getMenuImageSrc() + "'/>");
			buffer.append("</TD>");
		} else {
			buffer.append("<TD width='2' height='22'></TD>");
		}
		if (menuEnable) {
			buffer.append("<TD nowrap class='" + menuTextCellStyle + "' colspan='2'>");
		} else {
			buffer.append("<TD nowrap class='" + "clsMenuDisableTD" + "' colspan='2'>");
		}
		buffer.append(menuText);
		buffer.append("&nbsp;&nbsp;</TD>");
		buffer.append("</TR>");
	}

	/**
	 * @return
	 */
	private String computeId() {
		return "menuitem_" + getParentMenuID() + "_" + menuItemId;
	}

	private void outMenuSeparator(StringBuffer buffer) {
		buffer.append("<TR class='" + menuRowStyle + "'>");
		buffer.append("<TD height='5' colspan='3' class='" + menuSeparatorStyle + "'>");
		buffer.append("</TD>");
		buffer.append("</TR>");
	}

	/**
	 * @param buffer
	 */
	private void outMenuItems(StringBuffer buffer) {
		for (int i = 0; i < menuItems.length; i++) {
			menuText = menuItems[i].getMenuText();
			menuItemId = menuItems[i].getMenuId();
			menuParam = menuItems[i].getMenuParam();
			menuEnable = menuItems[i].isBEnable();
			menuClickedEvent = menuItems[i].getMenuClickedEvent();
			outMenuItem(buffer);
		}
	}

	/**
	 * @return
	 */
	private boolean isSeparator() {
		return separator;
	}

	/**
	 * @return
	 */
	public String getMenuImageSrc() {
		if (menuImageSrc == null) {
			return popupMenu.getImagePath() + "/menuimg.gif";
		}
		return menuImageSrc;
	}

	/**
	 * @return
	 */
	public String getMenuImgCellStyle() {
		return menuImgCellStyle;
	}

	/**
	 * @return
	 */
	public String getMenuImgStyle() {
		return menuImgStyle;
	}

	/**
	 * @return
	 */
	public String getMenuRowStyle() {
		return menuRowStyle;
	}

	/**
	 * @return
	 */
	public String getMenuSeparatorStyle() {
		return menuSeparatorStyle;
	}

	/**
	 * @return
	 */
	public String getMenuText() {
		return menuText;
	}

	/**
	 * @return
	 */
	public String getMenuTextCellStyle() {
		return menuTextCellStyle;
	}

	/**
	 * @param string
	 */
	public void setMenuImageSrc(String string) {
		menuImageSrc = string;
	}

	/**
	 * @param string
	 */
	public void setMenuImgCellStyle(String string) {
		menuImgCellStyle = string;
	}

	/**
	 * @param string
	 */
	public void setMenuImgStyle(String string) {
		menuImgStyle = string;
	}

	/**
	 * @param string
	 */
	public void setMenuRowStyle(String string) {
		menuRowStyle = string;
	}

	/**
	 * @param string
	 */
	public void setMenuSeparatorStyle(String string) {
		menuSeparatorStyle = string;
	}

	/**
	 * @param string
	 */
	public void setMenuText(String string) {
		menuText = string;
	}

	/**
	 * @param string
	 */
	public void setMenuTextCellStyle(String string) {
		menuTextCellStyle = string;
	}

	/**
	 * @param b
	 */
	public void setSeparator(boolean b) {
		separator = b;
	}

	/**
	 * @return
	 */
	public boolean isMenuImg() {
		return menuImg;
	}

	/**
	 * @param b
	 */
	public void setMenuImg(boolean b) {
		menuImg = b;
	}

	/**
	 * @return
	 */
	private String mouseOutEvent() {
		return "return " + getParentWindowPath() + ".onMouseOut(" + getParentWindowPath() + ", this);";
	}

	/**
	 * @return
	 */
	private String mouseOverEvent() {
		return "return " + getParentWindowPath() + ".onMouseOver(" + getParentWindowPath() + ", this);";
	}

	/**
	 * @return
	 */
	private String menuClicked() {
		if (menuClickedEvent == null) {
			return "return " + getParentWindowPath() + ".menuItemClicked(" + getParentWindowPath() + ", this, '" + getParentMenuID() + "', '" + getRootMenuID() + "', '" + menuItemId + "', '" + menuParam + "');";
		}
		return menuClickedEvent;
	}

	public String getMenuClickedEvent() {
		return menuClickedEvent;
	}

	public void setMenuClickedEvent(String menuClickedEvent) {
		this.menuClickedEvent = menuClickedEvent;
	}

	/**
	 * @param string
	 */
	public void setMenuItemId(String string) {
		menuItemId = string;
	}

	/**
	 * @param string
	 */
	public void setMenuParam(String string) {
		menuParam = string;
	}

	/**
	 * @return
	 */
	String getParentMenuID() {
		if (popupMenu != null) {
			return popupMenu.computeId();
		}
		return "";
	}

	/**
	 * @return
	 */
	String getRootMenuID() {
		if (popupMenu != null) {
			return popupMenu.getRootMenuID(popupMenu);
		}
		return "";
	}

	private String getParentWindowPath() {
		if (popupMenu != null) {
			return popupMenu.getParentWindowPath();
		}
		return "";
	}

}
