package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.popupmenu.model.MenuProperty;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLTag;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.JSFunction;

/**
 * @author rajesh
 * 
 */
public class TableMenu {
	
	private static final String CHILD_MENU_ID = "_child_menu_";
	
	List<TableMenu> subMenus = new ArrayList<TableMenu>();
	
	List<Object> showMenuParameters = new ArrayList<Object>();
	
	List<MenuProperty> menuItems = new ArrayList<MenuProperty>();

	private String type;
	
	private String id;
	
	private String text;
	
	private boolean bImage;
	
	private String showMenuFunctionName;
	
	private boolean replaceOld;
	
	private TableMenu parentMenu;

	public TableMenu(String id, String type, String text, boolean bImage) {
		this.type = type;
		if (id == null) {
			this.id = CHILD_MENU_ID;
		} else {
			this.id = id;
		}
		this.text = text;
		this.bImage = bImage;
	}

	/**
	 * 
	 */
	public TableMenu(String type, String text, boolean bImage) {
		this(null, type, text, bImage);
	}

	/**
	 * 
	 */
	public TableMenu(String text, boolean bImage) {
		this(null, null, text, bImage);
	}

	/**
	 * @return Returns the bImage.
	 */
	public boolean isBImage() {
		return bImage;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return Returns the menuItems.
	 */
	public MenuProperty[] getMenuItems() {
		return menuItems.toArray(new MenuProperty[0]);
	}

	/**
	 * @return Returns the subMenus.
	 */
	public List<TableMenu> getSubMenus() {
		return subMenus;
	}

	/**
	 * @return Returns the text.
	 */
	public String getText() {
		return text;
	}

	public void addMenuItem(MenuProperty menuProperty) {
		menuItems.add(menuProperty);
	}

	public void addSubMenu(TableMenu tableMenu) {
		subMenus.add(tableMenu);
		tableMenu.parentMenu = this;
		tableMenu.id = id + CHILD_MENU_ID;
	}

	@Override
	public String toString() {
		HTMLTag tagMenu = new HTMLTag("syndera:popupmenu");
		tagMenu.addAttribute("menuId", id);
		tagMenu.addAttribute("menuImg", String.valueOf(bImage));
		tagMenu.addAttribute("menuText", text);
		return tagMenu.toString();
	}

	public void addShowMenuParameters(Object param) {
		showMenuParameters.add(param);
	}

	public JSFunction getShowMenuFunction() {
		JSFunction showMenuFunction = new JSFunction(showMenuFunctionName);
		showMenuFunction.addStringParam(id);
		showMenuFunction.addStringParam(type);
		Iterator itShowMenuParams = showMenuParameters.iterator();
		while (itShowMenuParams.hasNext()) {
			Object param = itShowMenuParams.next();
			if (param instanceof Integer) {
				showMenuFunction.addNonStringParam(param.toString());
			} else {
				showMenuFunction.addStringParam(param.toString());
			}
		}
		return showMenuFunction;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param showMenuFunctionName
	 */
	public void setShowMenuFunctionName(String showMenuFunctionName) {
		this.showMenuFunctionName = showMenuFunctionName;
	}

	/**
	 * @param menuProperties
	 */
	public void addMenuItems(MenuProperty[] menuProperties) {
		for (int i = 0; i < menuProperties.length; i++) {
			MenuProperty property = menuProperties[i];
			addMenuItem(property);
		}
	}

	@Override
	public boolean equals(Object arg0) {
		TableMenu menu = (TableMenu) arg0;
		return id.equals(menu.getId());
	}

	public void setReplaceOld(boolean b) {
		replaceOld = b;
	}

	public boolean isReplaceOld() {
		return replaceOld;
	}

	public TableMenu getParentMenu() {
		return parentMenu;
	}

}
