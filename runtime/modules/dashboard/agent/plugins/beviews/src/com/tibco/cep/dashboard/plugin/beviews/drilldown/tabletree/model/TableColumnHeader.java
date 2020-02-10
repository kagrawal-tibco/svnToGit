package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.JSFunction;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.TableTreeUtils;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableColumnHeader implements HTMLRenderer {
	private static final int MOVE_RIGHT = 0;
	private static final int MOVE_LEFT = 1;
	public static final String ID_PREFIX = "tableTree_COLUMN_HEADER";
	public static final String ID_PREFIX_SORT_IMAGE = ID_PREFIX + "_SORT_IMAGE";
	private static final String ID_PREFIX_COL_HDR_IMAGE = ID_PREFIX + "_IMAGE";
	public static final String ID_PREFIX_TH = ID_PREFIX + "_TH";
	public static final String ID_PREFIX_TABLE = ID_PREFIX + "_TABLE";

	private boolean colMenu = false;
	private String menuParam = "";
	private String menuId = "";
	private Object userColMenu;

	private String unSelectedImage;
	private String selectedImage;
	private boolean bColImage;

	private String ascendingImage;
	private String ascendingStyle;
	private Map<Object, Object> attributes = new HashMap<Object, Object>();
	private Map<Object, Object> events;
	private String descendingImage;
	private String descendingStyle;

	private String image;
	private int index;
	private boolean isImageOnRight = false;
	private boolean isSortable = true;
	private TableTree table;
	private String text;
	private String unSortedImage;
	private String unSortedStyle;
	private int width = 10;
	private String sortLink;

	public static final int SORTED_NONE = 0;
	public static final int SORTED_ASC = 1;
	public static final int SORTED_DESC = -1;
	private int sortState = SORTED_NONE;

	private static final String COL_HEADER_HEIGHT = "18";
	private int firstSortOrder = TableModel.NO_ORDER;

	public TableColumnHeader(TableTree table, int index) {
		super();
		this.table = table;
		this.index = index;
	}

	public String getAscendingImage() {

		return (ascendingImage != null ? ascendingImage : table.getColumnHeaders().getAscendingImage());
	}

	public String getAscendingStyle() {
		return (ascendingStyle != null ? ascendingStyle : table.getColumnHeaders().getAscendingStyle());
	}

	public Map<Object, Object> getAttributes() {
		return attributes;
	}

	public String getDescendingImage() {
		return (descendingImage != null ? descendingImage : table.getColumnHeaders().getDescendingImage());
	}

	public String getDescendingStyle() {
		return (descendingStyle != null ? descendingStyle : table.getColumnHeaders().getDescendingStyle());
	}

	public Map<Object, Object> getEvents() {
		return events;
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		render(buffer);
		return buffer;
	}

	private void render(StringBuffer buffer) {
		TableTreeUtils.outTagStart(buffer, "TH");
		TableTreeUtils.outAttribute(buffer, "id", table.computeId(ID_PREFIX_TH, getIndex()));
		TableTreeUtils.outAttribute(buffer, "class", "thColumnHeader");
		if (table.isSortable()) {
			TableTreeUtils.outAttribute(buffer, "title", "Click to sort");
		}
		TableTreeUtils.outTagStartEnd(buffer);

		buffer.append("<TABLE cellspacing='0' cellpadding='0' width='100%' height='100%'");
		TableTreeUtils.outAttribute(buffer, "id", table.computeId(ID_PREFIX_TABLE, getIndex()));
		outSortStyles(buffer); // insert styles
		outOnClick(buffer); // insert onclick
		outStyle(buffer);
		buffer.append(" >");

		buffer.append("<TR>");
		if (getIndex() > 0) {
			renderResizeCell(buffer, 5, MOVE_RIGHT);
		}
		if (table.getSelectionMode().equalsIgnoreCase(TableTreeConstants.TABLE_SELECTION_SELECT_ROW) == false) {
			renderSelectionCell(buffer);
		}

		renderTitleCell(buffer);
		renderSortCell(buffer);
		if (getIndex() < table.getNumColumns() - 1) {
			renderResizeCell(buffer, 5, MOVE_LEFT);
		}
		buffer.append("</TR>");
		buffer.append("</TABLE>");

		TableTreeUtils.outTagEnd(buffer, "TH");
	}

	/**
	 * @param buffer
	 */
	private void renderResizeCell(StringBuffer buffer, int width, int moveDir) {

		// String functionParam = "'" + table.getTableTreeName() + "', '" + table.getTablePath() + "', " + moveDir + ", " + getIndex();

		TableTreeUtils.outTagStart(buffer, "TD");
		TableTreeUtils.outAttribute(buffer, "height", COL_HEADER_HEIGHT);
		TableTreeUtils.outAttribute(buffer, "width", new Integer(width));
		TableTreeUtils.outAttribute(buffer, "style", "cursor:default");

		// TableTreeUtils.outAttribute(buffer, "onmouseenter", "return column_mouseEnter(" + functionParam + ");");
		// TableTreeUtils.outAttribute(buffer, "onmouseover", "return column_mouseOver(" + functionParam + ");");
		// TableTreeUtils.outAttribute(buffer, "onmouseout", "return column_mouseOut(" + functionParam + ");");
		// TableTreeUtils.outAttribute(buffer, "onmousedown", "return column_mouseDown(" + functionParam + ");");
		// TableTreeUtils.outAttribute(buffer, "onmouseup", "return column_mouseUp(" + functionParam + ");");

		TableTreeUtils.outTagStartEnd(buffer);
		buffer.append("&nbsp;");
		TableTreeUtils.outTagEnd(buffer, "TD");
	}

	/**
	 * @param buffer
	 */
	private void renderSortCell(StringBuffer buffer) {
		buffer.append("<TD ");
		TableTreeUtils.outAttribute(buffer, "height", COL_HEADER_HEIGHT);
		TableTreeUtils.outAttribute(buffer, "width", "14");
		buffer.append(">");

		outSortImage(buffer);
		// close the whole TD tag
		buffer.append("</TD>");
	}

	/**
	 * @param buffer
	 */
	private void renderTitleCell(StringBuffer buffer) {
		buffer.append("<TD ");
		TableTreeUtils.outAttribute(buffer, "id", table.computeId(ID_PREFIX, index));
		// TableTreeUtils.outAttribute(buffer, "width", "100%");
		TableTreeUtils.outAttribute(buffer, "height", COL_HEADER_HEIGHT);

		TableTreeUtils.outAttribute(buffer, "onMouseDown", " return sortColumn_mouseDown()");

		// starts appending the values inside the TD start tag
		outContextMenu(buffer);
		// outOnClick(buffer);
		TableTreeUtils.outAttribute(buffer, "style", "cursor:default");

		// outSortStyles(buffer);

		TableTreeUtils.outAttributes(attributes, buffer);
		TableTreeUtils.outEvents(events, buffer);
		// close the start TD tag
		buffer.append(">");

		outText(buffer);
		// close the whole TD tag
		buffer.append("</TD>");
	}

	/**
	 * @param buffer
	 */
	private void renderSelectionCell(StringBuffer buffer) {

		buffer.append("<TD ");
		TableTreeUtils.outAttribute(buffer, "width", "16");
		TableTreeUtils.outAttribute(buffer, "height", COL_HEADER_HEIGHT);
		TableTreeUtils.outAttribute(buffer, "class", "colHeader");
		buffer.append(">");

		outImage(buffer);

		// close the whole TD tag
		buffer.append("</TD>");
	}

	public String getImage() {
		return image;
	}

	public int getIndex() {
		return index;
	}

	public String getText() {
		return text;
	}

	public String getUnSortedImage() {
		return (unSortedImage != null ? unSortedImage : table.getColumnHeaders().getUnSortedImage());
	}

	public String getUnSortedStyle() {
		return (unSortedStyle != null ? unSortedStyle : table.getColumnHeaders().getUnSortedStyle());
	}

	public int getWidth() {
		return width;
	}

	public boolean isImageOnRight() {
		return isImageOnRight;
	}

	public boolean isSortable() {
		return isSortable;
	}

	private void outImage(StringBuffer buffer) {
		// if(bColImage){
		buffer.append("<!--Col Header Image--><img src=");
		buffer.append("\"" + getUnSelectedImage());
		buffer.append("\" id=\"" + table.computeId(ID_PREFIX_COL_HDR_IMAGE, getIndex()) + "\"");
		TableTreeUtils.outAttribute(buffer, "valign", "bottom");
		buffer.append(" c_SelectedImage=\"" + getSelectedImage() + "\"");
		buffer.append(" c_UnSelectedImage=\"" + getUnSelectedImage() + "\"");
		buffer.append(" onclick=\"colHeader_Clicked('" + table.getTableTreeName() + "', '" + table.getTablePath() + "', '" + getIndex() + "');\"");
		outContextMenu(buffer);
		TableTreeUtils.outAttribute(buffer, "height", COL_HEADER_HEIGHT);
		buffer.append(" border=\"0\"/>");
		// }
	}

	/**
	 * @return
	 */
	private String getSelectedImage() {
		if (selectedImage == null) {
			return table.getImagePath() + "/coloumn_selected.gif";
		}
		return selectedImage;
	}

	/**
	 * @return
	 */
	private String getUnSelectedImage() {
		if (unSelectedImage == null) {
			return table.getImagePath() + "/rhunselected.gif";
		}
		return unSelectedImage;
	}

	private void outOnClick(StringBuffer buffer) {
		if (!table.isSortable())
			return;
		buffer.append(" onclick=\"");
		if (sortLink != null) {
			JSFunction jsFunction = new JSFunction("sortTableByColumn");
			jsFunction.setReturn(true);
			jsFunction.addStringParam(table.getTableTreeName());
			jsFunction.addStringParam(table.getTablePath());
			jsFunction.addNonStringParam(index);
			jsFunction.addNonStringParam(sortState);
			jsFunction.addNonStringParam(getNextSortState());
			jsFunction.addStringParam(sortLink);
			/*
			 * buffer.append("return sortColumn('" + table.computeId(TableTree.MAIN_TABLE_ID_PREFIX) + "','" + table.computeId(ID_PREFIX_TABLE, index) + "','" + table.computeId(ID_PREFIX_SORT_IMAGE, index) +"', " + index +
			 * ")");
			 */
			buffer.append(jsFunction.toString());
		} else {
			buffer.append("return sortColumn('" + table.computeId(TableTree.MAIN_TABLE_ID_PREFIX) + "','" + table.computeId(ID_PREFIX_TABLE, index) + "','" + table.computeId(ID_PREFIX_SORT_IMAGE, index) + "', " + index + ")");
		}
		buffer.append("\"");
	}

	/**
	 * @return
	 */
	private int getNextSortState() {
		if (sortState == SORTED_NONE) {
			// No Sorting set. Hence next order is the configured order.
			if (firstSortOrder == SORTED_ASC || firstSortOrder == SORTED_DESC) {
				return firstSortOrder;
			}
			// No first sort order is defined. Hence using Default next sort order
			return SORTED_ASC;
		}

		if (sortState == SORTED_ASC) {
			// Already Sorted in ascending. Hence next is Descending.
			return SORTED_DESC;
		}

		if (sortState == SORTED_DESC) {
			// Already Sorted in descending. Hence next is Ascending.
			return SORTED_ASC;
		}

		return SORTED_NONE;
	}

	private void outSortImage(StringBuffer buffer) {
		buffer.append(" <img ");
		TableTreeUtils.outAttribute(buffer, "id", table.computeId(ID_PREFIX_SORT_IMAGE, getIndex()));
		TableTreeUtils.outAttribute(buffer, "valign", "bottom");
		TableTreeUtils.outAttribute(buffer, "src", getSortImage());
		TableTreeUtils.outAttribute(buffer, "width", "14");
		TableTreeUtils.outAttribute(buffer, "height", COL_HEADER_HEIGHT);
		TableTreeUtils.outAttribute(buffer, "onselectstart", "return false;");
		buffer.append("/>");
	}

	/**
	 * @return
	 */
	private String getSortImage() {
		if (sortState == SORTED_NONE) {
			return getUnSortedImage();
		} else if (sortState == SORTED_ASC) {
			return getAscendingImage();
		} else if (sortState == SORTED_DESC) {
			return getDescendingImage();
		}
		return getUnSortedImage();
	}

	private void outSortStyles(StringBuffer buffer) {
		if (isSortable()) {
			TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.SORT_ABLE, "true");
			TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.SORT_STATE, String.valueOf(sortState));
			TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.SORT_DESCENDING_STYLE, getDescendingStyle());
			TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.SORT_ASCENDING_STYLE, getAscendingStyle());
			TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.SORT_DESCENDING_IMAGE, getDescendingImage());
			TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.SORT_ASCENDING_IMAGE, getAscendingImage());
		}
		outDataType(buffer);
		TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.SORT_UNSORTED_STYLE, getUnSortedStyle());
		TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.SORT_UNSORTED_IMAGE, getUnSortedImage());

	}

	private void outStyle(StringBuffer buffer) {
		if (getUnSortedStyle() != null) {
			buffer.append(" class=\"" + getSortStyle() + "\" ");
		}
	}

	/**
	 * @return
	 */
	private String getSortStyle() {
		if (sortState == SORTED_NONE) {
			return getUnSortedStyle();
		} else if (sortState == SORTED_ASC) {
			return getAscendingStyle();
		} else if (sortState == SORTED_DESC) {
			return getDescendingStyle();
		}
		return getUnSortedStyle();
	}

	private void outText(StringBuffer buffer) {
		buffer.append("<NOBR>" + text);
		buffer.append("</NOBR>");
	}

	private void outDataType(StringBuffer buffer) {
		TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.COLUMN_DATA_TYPE, table.getColumn(getIndex()).getColumnDataType());
	}

	public void setAscendingImage(String ascendingImage) {
		this.ascendingImage = ascendingImage;
	}

	public void setAscendingStyle(String ascendingStyle) {
		this.ascendingStyle = ascendingStyle;
	}

	public void setAttributes(Map<Object, Object> attributes) {
		this.attributes = attributes;
	}

	public void setDescendingImage(String descendingImage) {
		this.descendingImage = descendingImage;
	}

	public void setDescendingStyle(String descendingStyle) {
		this.descendingStyle = descendingStyle;
	}

	public void setEvents(Map<Object, Object> events) {
		this.events = events;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setImageOnRight(boolean isImageOnRight) {
		this.isImageOnRight = isImageOnRight;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setSortable(boolean isSortable) {
		this.isSortable = isSortable;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUnSortedImage(String unSortedImage) {
		this.unSortedImage = unSortedImage;
	}

	public void setUnSortedStyle(String style) {
		this.unSortedStyle = style;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	private void outContextMenu(StringBuffer buffer) {
		if (!colMenu)
			return;
		buffer.append(" oncontextmenu=");

		if (userColMenu != null) {
			buffer.append("\"" + userColMenu + "\"");
		} else {
			buffer.append("\"");
			buffer.append("showColumnMenu(");
			buffer.append("'" + menuId + "'");
			buffer.append(", '" + table.getTableTreeName() + "'");
			buffer.append(", '" + table.getTablePath() + "'");
			buffer.append(", '" + index + "'");
			buffer.append(", '" + menuParam + "'");
			buffer.append(");");
			buffer.append("\"");
		}
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuParam() {
		return menuParam;
	}

	public void setMenuParam(String menuParam) {
		this.menuParam = menuParam;
	}

	public void setColumnMenu(boolean columnChooserMenu) {
		this.colMenu = columnChooserMenu;
	}

	/**
	 * @return
	 */
	public boolean isBColImage() {
		return bColImage;
	}

	/**
	 * @param b
	 */
	public void setBColImage(boolean b) {
		bColImage = b;
	}

	/**
	 * @param string
	 */
	public void setSelectedImage(String string) {
		selectedImage = string;
	}

	/**
	 * @param string
	 */
	public void setUnSelectedImage(String string) {
		unSelectedImage = string;
	}

	/**
	 * @param sortLink
	 */
	public void setServerSortLink(String sortLink) {
		this.sortLink = sortLink;
	}

	public void setSortedState(int sortState) {
		this.sortState = sortState;
	}

	/**
	 * @param firstSortOrder
	 */
	public void setFirstSortOrder(int firstSortOrder) {
		this.firstSortOrder = firstSortOrder;
	}

}
