package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.popupmenu.model;

/**
 * @author rajesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MenuProperty {
	
	private String menuText = "";
	private String menuId = "";
	private String menuParam = null;
	private String menuClickedEvent = null;
	
	private boolean bEnable = true;
	

	/**
	 * 
	 */
	public MenuProperty(String menuText) {
		this(menuText, menuText);
	}

	/**
	 * 
	 */
	public MenuProperty(String menuId, String menuText) {
		this(menuId, menuText, null);
	}

	/**
	 * 
	 */
	public MenuProperty(String menuId, String menuText, String menuParam) {
		this.menuId = menuId;
		this.menuText = menuText;
		this.menuParam = menuParam;
	}

	/**
	 * 
	 */
	public MenuProperty(String menuId, String menuText, String menuParam, String menuClickedEvent) {
		this.menuId = menuId;
		this.menuText = menuText;
		this.menuParam = menuParam;
		this.menuClickedEvent = menuClickedEvent;
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
	public String getMenuParam() {
		return menuParam;
	}

	/**
	 * @return
	 */
	public String getMenuText() {
		return menuText;
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
	public void setMenuParam(String string) {
		menuParam = string;
	}

	/**
	 * @param string
	 */
	public void setMenuText(String string) {
		menuText = string;
	}

	/**
	 * @return
	 */
	public String getMenuClickedEvent() {
		return menuClickedEvent;
	}

	/**
	 * @param string
	 */
	public void setMenuClickedEvent(String string) {
		menuClickedEvent = string;
	}

	public boolean isBEnable()
	{
		return bEnable;
	}
	public void setBEnable(boolean enable)
	{
		bEnable = enable;
	}
}
