package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.popupmenu.tag;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableMenu;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PopupMenu {

	private static final String PATH_SEPARATOR = "_";

	private final TableMenu tableMenu;

	private final PopupMenu parentPopupMenu;

	/**
	 *
	 */
	public PopupMenu(PopupMenu parentPopupMenu, TableMenu tableMenu) {
		this.parentPopupMenu = parentPopupMenu;
		this.tableMenu = tableMenu;
		setMenuId(tableMenu.getId());
		setMenuImg(tableMenu.isBImage());
		setReplaceOld(tableMenu.isReplaceOld());
	}

	private String deployPath = "";

	private boolean menuImg;

	private boolean replaceOld;

	private String menuId;

	private String menuDivStyle = "clsMenuDiv";

	private String menuTableStyle = "clsMenuTable";

	private String menuRowStyle = "clsMenuRow";

	private String menuImgCellStyle = "clsMenuImageTD";

	private String menuImgStyle = "clsMenuImg";

	private String menuImageSrc;

	private String subMenuImgCellStyle = "clsSubMenuImageTD";

	private String subMenuImgStyle = "clsSubMenuImg";

	private String subMenuImageSrc;

	private String menuTextCellStyle = "clsMenuNormalTD";

	private String menuText = "";

	public void outEndHtml(StringBuffer buffer) {
		outMenuContainer_End(buffer);
		if (!isRootMenu()) {
			outMenuItem_End(buffer);
		}
	}

	public void outStartHtml(StringBuffer buffer) {
		if (!isRootMenu()) {
			outMenuItem_Start(buffer);
		}
		outMenuContainer_Start(buffer);
	}

	/**
	 *
	 */
	private void outMenuContainer_Start(StringBuffer buffer) {
		buffer.append("<DIV style='position:absolute;z-index:9999;VISIBILITY: hidden;DISPLAY: none' class='" + menuDivStyle + "' id='" + computeId() + "' c_replaceold='" + isReplaceOld() + "'>");
		buffer.append("<TABLE cellspacing='0' cellpadding='0' class='" + menuTableStyle + "'>");
	}

	/**
	 * @return
	 */
	String computeId() {
		if (isRootMenu()) {
			return menuId;
		}
		return getParentMenuID() + PATH_SEPARATOR + menuId;
	}

	/**
	 *
	 */
	private void outMenuContainer_End(StringBuffer buffer) {
		buffer.append("</TABLE>");
		buffer.append("</DIV>");
	}

	/**
	 *
	 */
	private void outMenuItem_Start(StringBuffer buffer) {
		buffer.append("<TR class='" + menuRowStyle + "'");
		buffer.append(" onmouseover=" + mouseEnterMenu() + "");
		buffer.append(" onmouseout=" + mouseLeaveMenu() + "");
		buffer.append(" id='menuitem_" + computeId() + "'");
		buffer.append(" style='font-family: Verdana, Serif;font-size: 9;color: #000000;border-left-style: solid;border-left-width: 2px;border-left-color: #ffffff;background-color: #ffffff;cursor: pointer;'");
		buffer.append(">");

		if (isMenuImg()) {
			buffer.append("<TD class='" + menuImgCellStyle + "'>");
			buffer.append("<IMG class='" + menuImgStyle + "'");
			buffer.append(" src='" + getMenuImageSrc() + "'/>");
			buffer.append("</TD>");
		} else {
			buffer.append("<TD width='2' height='22'></TD>");
		}

		buffer.append("<TD  nowrap class='" + menuTextCellStyle + "'>");
		buffer.append(menuText);
		buffer.append("&nbsp;&nbsp;</TD>");
		buffer.append("<TD class='" + subMenuImgCellStyle + "'>");
		buffer.append("<IMG class='" + subMenuImgStyle + "'");
		buffer.append(" src='" + getSubMenuImageSrc() + "'/>");
	}

	/**
	 * @return
	 */
	private boolean isMenuImg() {
		return menuImg;
	}

	/**
	 * @return
	 */
	private String mouseLeaveMenu() {
		String parentWindowPath = getParentWindowPath();
		// parentWindowPath = parentWindowPath.substring(0, parentWindowPath.lastIndexOf('.'));
		String windowPath = parentWindowPath;
		if (windowPath.equals("parent")) {
			windowPath = "window";
		} else {
			windowPath = windowPath.substring(0, windowPath.lastIndexOf('.'));
			if (windowPath.equals("parent")) {
				windowPath = "window";
			}
		}

		String eventHandler = "\"" + parentWindowPath + ".onMouseOut(" + windowPath + ", this);";
		/*
		 * if (!isRootMenu()) { eventHandler += "return hideSubMenu('" + getRootMenuID(this) + "', '" + getParentMenuID() + "', '" + computeId() + "');"; }
		 */
		eventHandler += "\"";
		return eventHandler;
	}

	/**
	 * @return
	 */
	private String mouseEnterMenu() {
		String parentWindowPath = getParentWindowPath();
		// parentWindowPath = parentWindowPath.substring(0, parentWindowPath.lastIndexOf('.'));
		String windowPath = parentWindowPath;
		if (windowPath.equals("parent")) {
			windowPath = "window";
		} else {
			windowPath = windowPath.substring(0, windowPath.lastIndexOf('.'));
			if (windowPath.equals("parent")) {
				windowPath = "window";
			}
		}

		String eventHandler = "\"" + parentWindowPath + ".onMouseOver(" + windowPath + ", this);";
		if (!isRootMenu()) {
			eventHandler += "return " + parentWindowPath + ".showSubMenu(" + windowPath + ", this, '" + getRootMenuID(this) + "', '" + getParentMenuID() + "', '" + computeId() + "');";
		}
		eventHandler += "\"";
		return eventHandler;
	}

	private void outMenuItem_End(StringBuffer buffer) {
		buffer.append("</TD>");
		buffer.append("</TR>");
	}

	/**
	 * @return
	 */
	private boolean isRootMenu() {
		return (parentPopupMenu == null);
	}

	/**
	 * @return
	 */
	String getRootMenuID(PopupMenu child) {
		if (parentPopupMenu != null) {
			return getRootMenuID(parentPopupMenu);
		}
		return child.getMenuId();
	}

	/**
	 * @return
	 */
	private String getParentMenuID() {
		if (parentPopupMenu != null) {
			return parentPopupMenu.computeId();
		}
		return "";
	}

	/**
	 * @return
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @return
	 */
	public String getMenuDivStyle() {
		return menuDivStyle;
	}

	/**
	 * @return
	 */
	public String getMenuImageSrc() {
		if (menuImageSrc == null) {
			return getImagePath() + "/menuimg.gif";
		} else {
			return menuImageSrc;
		}
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
	public String getMenuTableStyle() {
		return menuTableStyle;
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
	 * @return
	 */
	public String getSubMenuImageSrc() {
		if (subMenuImageSrc == null) {
			return getImagePath() + "/rt_arrow.gif";
		} else {
			return subMenuImageSrc;
		}
	}

	/**
	 * @return
	 */
	public String getSubMenuImgCellStyle() {
		return subMenuImgCellStyle;
	}

	/**
	 * @return
	 */
	public String getSubMenuImgStyle() {
		return subMenuImgStyle;
	}

	/**
	 * @param string
	 */
	public void setMenuId(String string) {
		menuId = string;
	}

	/**
	 * @param string
	 */
	public void setMenuDivStyle(String string) {
		menuDivStyle = string;
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
	public void setMenuTableStyle(String string) {
		menuTableStyle = string;
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
	 * @param string
	 */
	public void setSubMenuImageSrc(String string) {
		subMenuImageSrc = string;
	}

	/**
	 * @param string
	 */
	public void setSubMenuImgCellStyle(String string) {
		subMenuImgCellStyle = string;
	}

	/**
	 * @param string
	 */
	public void setSubMenuImgStyle(String string) {
		subMenuImgStyle = string;
	}

	/**
	 * @param b
	 */
	public void setReplaceOld(boolean b) {
		replaceOld = b;
	}

	/**
	 * @param b
	 */
	public void setMenuImg(boolean b) {
		menuImg = b;
	}

	/**
	 * @param string
	 */
	public void setDeployPath(String string) {
		deployPath = string;
	}

	public String getImagePath() {
		return deployPath + "/drilldown/images";
	}

	public boolean isReplaceOld() {
		return replaceOld;
	}

	/**
	 * @param parentMenu
	 * @return
	 */
	public String getParentWindowPath() {
		if (parentPopupMenu != null) {
			return "parent." + parentPopupMenu.getParentWindowPath();
		}
		return "parent";
	}
}