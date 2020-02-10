package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableMenu;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.TableTreeUtils;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableRowHeader implements HTMLRenderer {

	private boolean rowMenu = false;
	private String menuParam = "";
	private String menuId = "";
	private static final String ID_PREFIX = "tableTree_RowHeader_Image";
	private int rowIndex;
	private TableMenu userRowMenu;

	private TableTree table;

	TableRowHeaderConfigurator configurator;

	/**
	 * @param tableGroupHeader
	 */
	public TableRowHeader(TableTree table, TableRowHeaderConfigurator configurator) {
		this.configurator = configurator;
		this.table = table;
	}

	/**
     *
     */
	public TableRowHeader() {
		super();
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		render(buffer);
		return buffer;
	}

	private void render(StringBuffer buffer) {
		buffer.append("<!--Row Header Image--><img src=");
		buffer.append("\"" + configurator.getImagePath() + "/" + configurator.getRowUnSelectedImage());
		buffer.append("\" id=\"" + table.computeId(ID_PREFIX, rowIndex) + "\"");
		buffer.append(" c_SelectedImage=\"" + configurator.getImagePath() + "/" + configurator.getRowSelectedImage() + "\"");
		buffer.append(" c_UnSelectedImage=\"" + configurator.getImagePath() + "/" + configurator.getRowUnSelectedImage() + "\"");

		TableTreeUtils.outAttribute(buffer, "c_SelectedHoverImage", configurator.getImagePath() + "/" + configurator.getRowSelectedHoverImage());
		TableTreeUtils.outAttribute(buffer, "c_UnSelectedHoverImage", configurator.getImagePath() + "/" + configurator.getRowUnSelectedHoverImage());

		TableTreeUtils.outAttribute(buffer, "c_SelectedDownImage", configurator.getImagePath() + "/" + configurator.getRowSelectedMouseDownImage());

		TableTreeUtils.outAttribute(buffer, "c_UnSelectedDownImage", configurator.getImagePath() + "/" + configurator.getRowUnSelectedMouseDownImage());

		TableTreeUtils.outAttribute(buffer, "c_isSelected", "false");

		if (configurator.getRowUnSelectedHoverImage() != null && configurator.getRowSelectedHoverImage() != null) {
			buffer.append(" onMouseOver=\"onrowHeader_MouseOver('" + table.getTableTreeName() + "', '" + table.getTablePath() + "', '" + rowIndex + "');\"");
			buffer.append(" onMouseOut=\"onrowHeader_MouseOut('" + table.getTableTreeName() + "', '" + table.getTablePath() + "', '" + rowIndex + "');\"");
		}

		if (configurator.getRowUnSelectedMouseDownImage() != null && configurator.getRowSelectedMouseDownImage() != null) {
			buffer.append(" onMouseDown=\"onrowHeader_MouseDown('" + table.getTableTreeName() + "', '" + table.getTablePath() + "', '" + rowIndex + "');\"");
		}
		buffer.append(" onclick=\"rowHeader_Clicked('" + table.getTableTreeName() + "', '" + table.getTablePath() + "', '" + rowIndex + "');");

		outContextMenu(buffer);
		buffer.append("\"");
		buffer.append(" border=\"0\"/>");
	}

	private void outContextMenu(StringBuffer buffer) {
		if (!rowMenu)
			return;
		if (userRowMenu != null) {
			userRowMenu.getShowMenuFunction().setReturn(false);
			buffer.append(userRowMenu.getShowMenuFunction().toString());
			userRowMenu.getShowMenuFunction().setReturn(true);
		} else {
			buffer.append("showRowMenu(");
			buffer.append("'" + menuId + "'");
			buffer.append(", '" + table.getTableTreeName() + "'");
			buffer.append(", '" + table.getTablePath() + "'");
			buffer.append(", '" + rowIndex + "'");
			buffer.append(", '" + menuParam + "'");
			buffer.append(");");
		}
	}

	/**
	 * @param string
	 */
	public void setRowIndex(int index) {
		rowIndex = index;
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
	 * @param b
	 */
	public void setRowMenu(boolean b) {
		rowMenu = b;
	}

	/**
	 * @return
	 */
	public Object getUserRowMenu() {
		return userRowMenu;
	}

	/**
	 * @param object
	 */
	public void setUserRowMenu(TableMenu menu) {
		userRowMenu = menu;
	}
}
