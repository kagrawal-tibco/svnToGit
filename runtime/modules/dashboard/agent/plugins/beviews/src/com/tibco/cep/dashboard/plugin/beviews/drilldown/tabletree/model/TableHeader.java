package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import java.util.Iterator;
import java.util.List;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableMenu;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLTag;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.JSFunction;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableHeader implements HTMLRenderer {

	public static final String ID_PREFIX = "tableTree_TABLE_HEADER";
	public static final String IMG_ID_PREFIX = ID_PREFIX + "_IMG";
	public static final String TEXT_ID_PREFIX = ID_PREFIX + "_TEXT";

	public static final String INNER_HEADER_ROW_ID_PREFIX = ID_PREFIX + "_INNER_HEADER_ROW";
	public static final String GROUP_BY_START_ID_PREFIX = ID_PREFIX + "_GROUP_BY_START";
	public static final String GROUP_BY_END_ID_PREFIX = ID_PREFIX + "_GROUP_BY_END";
	public static final String GROUP_BY_IMG_ID_PREFIX = ID_PREFIX + "_GROUP_BY_IMG";

	private String imageSrc;
	private String imageStyle = "imageTableHeader";
	private boolean isImageOnRight = false;
	private String style = "trTableHeader";
	private TableTree table;
	private String text;
	private String textStyle = "textTableHeader";
	private String filterText;

	TableRowHeaderConfigurator headerConfigurator;

	// Header Menu related variables.
	private TableMenu userHeaderMenu;
	private String menuParam;
	private String menuId;
	private boolean headerMenu;

	private boolean groupByEnabled;
	private String groupByImageSrc;
	private String groupByDisabledImageSrc;
	private TableMenu groupByMenu;
	private List groupByFields;
	private String groupBySpacerSrc;
	private String timeFilterImageSrc;

	private static final String TBL_HEADER_ROW_TYPE = "table_header_row";

	/**
	 * @param table
	 */
	public TableHeader(TableTree table, TableRowHeaderConfigurator headerConfigurator) {
		this.table = table;
		this.headerConfigurator = headerConfigurator;
		imageSrc = table.getImagePath() + "/" + headerConfigurator.getRowUnSelectedImage();
		groupByImageSrc = table.getImagePath() + "/groupbyplus_up.gif";
		groupByDisabledImageSrc = table.getImagePath() + "/groupbyplus_up_disabled.gif";
		groupBySpacerSrc = table.getImagePath() + "/blank.gif";
		timeFilterImageSrc = table.getImagePath() + "/timefilterfunnel.gif";
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		render(buffer);
		return buffer;
	}

	private void render(StringBuffer buffer) {
		HTMLTag tagTR = new HTMLTag("tr");
		tagTR.addCustomAttribute("type", TBL_HEADER_ROW_TYPE);
		tagTR.setStyleClass(style);

		int colspan = table.getNumColumns() + 1;

		if (!table.isGrouped()) {
			renderImage(tagTR);
		} else {
			HTMLTag tagTD = tagTR.addTag("td");
			tagTD.setStyleClass("tdHeader");
			// colspan += 1;
		}

		HTMLTag tagTD = tagTR.addTag("td");
		tagTD.setStyleClass("tdHeader");
		tagTD.addAttribute("colspan", String.valueOf(colspan));

		renderInner(tagTD);
		if (table.getWidthType() == TableTree.WIDTH_NATURAL) {
			tagTR.addTag(table.getRightFillerCell());
		}
		buffer.append(tagTR.toString());
		/*
		 * buffer.append("<TR c_type='"+ TBL_HEADER_ROW_TYPE + "'>"); buffer.append("<TD class = 'tdHeader' colspan = '" + (getNumColumns() + 2) + "' >"); buffer.append(header.getHTML()); buffer.append("</TD>"); if (widthType
		 * == WIDTH_NATURAL) { addRightFillerCell(buffer); } buffer.append("</TR>");
		 */
	}

	private void renderInner(HTMLTag tagTDOuter) {
		HTMLTag tagTable = tagTDOuter.addTag("table");
		tagTable.addTableGeneralProps();
		HTMLTag tagTR = tagTable.addTag("tr");
		tagTR.setStyleClass(style);
		tagTR.setId(table.computeId(INNER_HEADER_ROW_ID_PREFIX));

		// renderImage(tagTR);

		renderText(tagTR);
		if (!table.isGrouped()) {
			if (getFilterText() != null) {
				renderFilterImage(tagTR);
			}
		}
		renderSpacer(tagTR);
		renderGroupbyHeader(tagTR);
		// buffer.append(tagTable.toString());
	}

	private void renderFilterImage(HTMLTag tagTR) {
		HTMLTag tagTD = tagTR.addTag("td");
		HTMLTag tagImg = tagTD.addTag("img");
		tagImg.setId(table.computeId(IMG_ID_PREFIX));
		tagImg.setComment("Time Filter Image");
		tagImg.addAttribute("src", timeFilterImageSrc);
		tagImg.addAttribute("border", "0");
		tagImg.addAttribute("align", "middle");
		tagImg.addAttribute("title", "Metric time filter set to " + getFilterText());
	}

	/**
	 * @param tagTR
	 */
	private void renderGroupbyHeader(HTMLTag tagTR) {
		if (!isGroupByEnabled()) {
			HTMLTag tagTDGroupByPlus = tagTR.addTag("td");
			HTMLTag tagImgGroupByPlus = tagTDGroupByPlus.addTag("img");
			tagImgGroupByPlus.addAttribute("src", groupBySpacerSrc);
			tagImgGroupByPlus.addAttribute("border", "0");
			tagImgGroupByPlus.addAttribute("width", "1");
			tagImgGroupByPlus.addAttribute("height", "22");
			return;
		}
		HTMLTag tagTD = tagTR.addTag("td");
		tagTD.setStyleClass("tbtr_groupByTextTD");
		tagTD.addAttribute("nowrap");
		tagTD.setContent("GROUP BY");

		renderGroupByFields(tagTR);

		HTMLTag tagTDGroupByPlus = tagTR.addTag("td");
		HTMLTag tagImgGroupByPlus = tagTDGroupByPlus.addTag("img");
		tagImgGroupByPlus.setId(table.computeId(GROUP_BY_IMG_ID_PREFIX));
		tagImgGroupByPlus.addCustomAttribute("enabled", "true");
		tagImgGroupByPlus.addCustomAttribute("disabled_src", groupByDisabledImageSrc);
		tagImgGroupByPlus.addCustomAttribute("enabled_src", groupByImageSrc);
		tagImgGroupByPlus.addAttribute("src", groupByImageSrc);
		tagImgGroupByPlus.addAttribute("border", "0");
		tagImgGroupByPlus.addStyle("cursor", "hand");
		tagImgGroupByPlus.addAttribute("onclick", groupByMenu.getShowMenuFunction().toString());
	}

	/**
	 * @param tagTR
	 */
	private void renderGroupByFields(HTMLTag tagTR) {
		HTMLTag tagGroupByStart = tagTR.addTag("td");
		tagGroupByStart.setId(table.computeId(GROUP_BY_START_ID_PREFIX));

		if (groupByFields == null)
			return;
		Iterator itGroupByFields = groupByFields.iterator();
		while (itGroupByFields.hasNext()) {
			TableGroupByField groupByField = (TableGroupByField) itGroupByFields.next();
			groupByField.renderCell(tagTR);
		}

		HTMLTag tagGroupByEnd = tagTR.addTag("td");
		tagGroupByEnd.setId(table.computeId(GROUP_BY_END_ID_PREFIX));
	}

	/**
	 * @param tagTR
	 */
	private void renderSpacer(HTMLTag tagTR) {
		HTMLTag tagTD = tagTR.addTag("td");
		tagTD.addAttribute("width", "100%");
	}

	private void renderImage(HTMLTag tagTR) {
		HTMLTag tagTD = tagTR.addTag("td");
		// tagTD.setId(table.computeId(IMG_ID_PREFIX));
		tagTD.setStyleClass(imageStyle);

		HTMLTag tagImg = tagTD.addTag("img");
		tagImg.setId(table.computeId(IMG_ID_PREFIX));
		tagImg.setComment("Table Header Image");
		tagImg.addAttribute("src", imageSrc);
		tagImg.addAttribute("width", "18");
		tagImg.addAttribute("height", "22");
		tagImg.addAttribute("border", "0");

		tagImg.addCustomAttribute("UnSelectedImage", table.getImagePath() + "/" + headerConfigurator.getRowUnSelectedImage());
		tagImg.addCustomAttribute("UnSelectedHoverImage", table.getImagePath() + "/" + headerConfigurator.getRowUnSelectedHoverImage());
		tagImg.addCustomAttribute("UnSelectedDownImage", table.getImagePath() + "/" + headerConfigurator.getRowUnSelectedMouseDownImage());

		JSFunction mouseOver = new JSFunction("Header_OnMouseOver");
		mouseOver.setReturn(false);
		mouseOver.addStringParam(table.getTableTreeName());
		mouseOver.addStringParam(table.getTablePath());
		tagImg.addAttribute("onmouseover", mouseOver.toString());

		JSFunction mouseOut = new JSFunction("Header_OnMouseOut");
		mouseOut.setReturn(false);
		mouseOut.addStringParam(table.getTableTreeName());
		mouseOut.addStringParam(table.getTablePath());
		tagImg.addAttribute("onmouseout", mouseOut.toString());

		JSFunction mouseDown = new JSFunction("Header_OnMouseDown");
		mouseDown.setReturn(false);
		mouseDown.addStringParam(table.getTableTreeName());
		mouseDown.addStringParam(table.getTablePath());
		tagImg.addAttribute("onmousedown", mouseDown.toString());

		attachOnclickHandler(tagImg);
	}

	private void attachOnclickHandler(HTMLTag tagImg) {
		if (!headerMenu)
			return;
		if (userHeaderMenu != null) {
			tagImg.addAttribute("onclick", userHeaderMenu.getShowMenuFunction().toString());
		} else {
			JSFunction jsFunction = new JSFunction("showHeaderMenu");
			jsFunction.addStringParam(menuId);
			jsFunction.addStringParam(table.getTableTreeName());
			jsFunction.addStringParam(table.getTablePath());
			jsFunction.addStringParam(menuParam);
			tagImg.addAttribute("onclick", jsFunction.toString());
		}
	}

	private void renderText(HTMLTag tagTR) {
		HTMLTag tagTD = tagTR.addTag("td");
		tagTD.addAttribute("align", "left");
		tagTD.setId(table.computeId(TEXT_ID_PREFIX));
		tagTD.setStyleClass(textStyle);
		tagTD.addAttribute("nowrap");
		HTMLTag tagNOBR = tagTD.addTag("nobr");
		tagNOBR.setContent("&nbsp;" + text);
	}

	public String getImage() {
		return imageSrc;
	}

	public String getImageStyle() {
		return imageStyle;
	}

	public String getStyle() {
		return style;
	}

	public String getText() {
		return text;
	}

	public String getTextStyle() {
		return textStyle;
	}

	public boolean isImageOnRight() {
		return isImageOnRight;
	}

	public void setImage(String image) {
		this.imageSrc = image;
	}

	public void setImageOnRight(boolean isImageOnRight) {
		this.isImageOnRight = isImageOnRight;
	}

	public void setImageStyle(String imageStyle) {
		this.imageStyle = imageStyle;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTextStyle(String textStyle) {
		this.textStyle = textStyle;
	}

	/**
	 * @param b
	 */
	public void setHeaderMenu(boolean b) {
		headerMenu = b;
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
	 * @param object
	 */
	public void setUserHeaderMenu(TableMenu menu) {
		userHeaderMenu = menu;
	}

	public void setGroupByFields(List groupByFields) {
		this.groupByFields = groupByFields;
	}

	public void setGroupByMenu(TableMenu groupByMenu) {
		this.groupByMenu = groupByMenu;
	}

	public boolean isGroupByEnabled() {
		return groupByEnabled;
	}

	public void setGroupByEnabled(boolean groupByEnabled) {
		this.groupByEnabled = groupByEnabled;
	}

	public TableMenu getGroupByMenu() {
		return groupByMenu;
	}

	public String getFilterText() {
		return filterText;
	}

	public void setFilterText(String filterText) {
		this.filterText = filterText;
	}
}
