package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLTag;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.JSFunction;

/**
 * @author rajesh
 *
 */
public class TableFooter implements HTMLRenderer {
	private TableTree table;

	private boolean appendMode;

	private int totalCount;
	private int pageSize;
	private int currentCount;
	private int startIndex;
	private int endIndex;

	private String showNextUrl;
	private String showPrevUrl;
	private String showAppendUrl;

	private static final String SHOW_PREV = "prev";
	private static final String SHOW_NEXT = "next";
	private static final String SHOW_APPEND = "append";

	/**
	 * @param b
	 * @param tableTree
	 * @param totalRowCount
	 * @param pageSizeCount
	 * @param displayedSpan
	 */
	public TableFooter(TableTree table, boolean appendMode, int totalCount, int pageSize, int[] displayedSpan) {
		this.table = table;
		this.appendMode = appendMode;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.startIndex = displayedSpan[0];
		this.endIndex = displayedSpan[1];
		this.currentCount = endIndex - startIndex + 1;
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		render(buffer);
		return buffer;
	}

	/**
	 * @param buffer
	 */
	private void render(StringBuffer buffer) {
		HTMLTag tagTRMain = new HTMLTag("tr");
		tagTRMain.setStyleClass("tbtr_footerrow");
		tagTRMain.setId(table.computeId("FOOTER_ROW"));
		tagTRMain.addCustomAttribute("type", TableTree.TBL_FOOTER_ROW_TYPE);
		tagTRMain.addCustomAttribute("dispcount", String.valueOf(currentCount));

		// For offsetting it.
		tagTRMain.addTag("td");

		HTMLTag tagTDContent = tagTRMain.addTag("td");
		tagTDContent.addAttribute("colspan", String.valueOf(table.getNumColumns() + 1));
		tagTDContent.addStyle("height", "30px");
		tagTDContent.addAttribute("valign", "middle");

		HTMLTag tagTable = tagTDContent.addTag("table");
		tagTable.addTableGeneralProps();
		tagTable.setStyleClass("tbtr_footertable");

		HTMLTag tagTR = tagTable.addTag("tr");

		renderInfoCell(tagTR);

		if (appendMode) {
			renderNavigationAppendButton(tagTR);
		} else {
			renderNavigationPrevButton(tagTR);
			renderNavigationNextButton(tagTR);
		}

		renderSeparatorCell(tagTR);
		buffer.append(tagTRMain.toString());
	}

	/**
	 * @param tagTR
	 */
	private void renderNavigationPrevButton(HTMLTag tagTR) {
		renderSeparatorCell(tagTR);
		renderButtonLeftCell(tagTR, SHOW_PREV, false);
		renderPrevButtonCell(tagTR);
		renderButtonRightCell(tagTR, SHOW_PREV, false);
	}

	/**
	 * @param tagTR
	 */
	private void renderNavigationNextButton(HTMLTag tagTR) {
		renderSeparatorCell(tagTR);
		renderButtonLeftCell(tagTR, SHOW_NEXT, true);
		renderNextButtonCell(tagTR);
		renderButtonRightCell(tagTR, SHOW_NEXT, true);
	}

	/**
	 * @param tagTR
	 */
	private void renderNavigationAppendButton(HTMLTag tagTR) {
		renderSeparatorCell(tagTR);
		renderButtonLeftCell(tagTR, SHOW_APPEND, true);
		renderAppendButtonCell(tagTR);
		renderButtonRightCell(tagTR, SHOW_APPEND, true);
	}

	/**
	 * @param tagTR
	 */
	private void renderSeparatorCell(HTMLTag tagTR) {
		HTMLTag tagTD = tagTR.addTag("td");
		HTMLTag tagImg = tagTD.addTag("img");
		tagImg.addAttribute("src", table.getImagePath() + "/blank.gif");
		tagImg.addAttribute("width", "5");
		tagImg.addAttribute("height", "15");
	}

	/**
	 * @param tagTR
	 * @param show_next2
	 */
	private void renderButtonLeftCell(HTMLTag tagTR, String btnType, boolean bVisible) {
		HTMLTag tagTD = tagTR.addTag("td");
		tagTD.setId(table.computeId("FOOTER_LEFT_TD_" + btnType));
		tagTD.setVisible(bVisible);
		HTMLTag tagImg = tagTD.addTag("img");
		tagImg.setId(table.computeId("FOOTER_LEFT_IMG_" + btnType));
		tagImg.addAttribute("src", table.getImagePath() + "/footer_button_left.gif");
		tagImg.addCustomAttribute("src_disable", table.getImagePath() + "/footer_button_left_d.gif");
		tagImg.addCustomAttribute("src_enable", table.getImagePath() + "/footer_button_left.gif");
	}

	/**
	 * @param tagTR
	 * @param show_next2
	 */
	private void renderButtonRightCell(HTMLTag tagTR, String btnType, boolean bVisible) {
		HTMLTag tagTD = tagTR.addTag("td");
		tagTD.setId(table.computeId("FOOTER_RIGHT_TD_" + btnType));
		tagTD.setVisible(bVisible);
		HTMLTag tagImg = tagTD.addTag("img");
		tagImg.setId(table.computeId("FOOTER_RIGHT_IMG_" + btnType));
		tagImg.addAttribute("src", table.getImagePath() + "/footer_button_right.gif");
		tagImg.addCustomAttribute("src_disable", table.getImagePath() + "/footer_button_right_d.gif");
		tagImg.addCustomAttribute("src_enable", table.getImagePath() + "/footer_button_right.gif");
	}

	/**
	 * @param tagTR
	 */
	private void renderInfoCell(HTMLTag tagTR) {
		HTMLTag tagTD = tagTR.addTag("td");
		tagTD.setId(table.computeId("FOOTER_INFO_CELL"));
		tagTD.addAttribute("nowrap");
		tagTD.setStyleClass("tbtr_footerinfocell");
		tagTD.setContent(getInfoMessage());
	}

	/**
	 * @return
	 */
	private String getInfoMessage() {
		if (appendMode) {
			return "Displaying " + currentCount + " of " + totalCount;
		} else {
			return startIndex + " to " + endIndex + " (" + totalCount + ")";
		}
	}

	/**
	 * @param tagTR
	 */
	private void renderAppendButtonCell(HTMLTag tagTR) {
		HTMLTag tagTD = tagTR.addTag("td");
		tagTD.setId(table.computeId("FOOTER_APPEND_BUTTON_CELL"));
		tagTD.addAttribute("nowrap");
		tagTD.setStyleClass("tbtr_footerallcell_enable");
		tagTD.setContent(getShowAppendMessage());
		tagTD.addAttribute("onclick", getShowNavigationClickHandler("showAppendRowSet", showAppendUrl));
	}

	/**
	 * @param tagTR
	 */
	private void renderNextButtonCell(HTMLTag tagTR) {
		HTMLTag tagTD = tagTR.addTag("td");
		tagTD.setId(table.computeId("FOOTER_NEXT_BUTTON_CELL"));
		tagTD.addAttribute("nowrap");
		tagTD.setStyleClass("tbtr_footerallcell_enable");
		tagTD.setContent(getShowNextMessage());
		tagTD.addAttribute("onclick", getShowNavigationClickHandler("showNextRowSet", showNextUrl));
	}

	/**
	 * @param tagTR
	 */
	private void renderPrevButtonCell(HTMLTag tagTR) {
		HTMLTag tagTD = tagTR.addTag("td");
		tagTD.setId(table.computeId("FOOTER_PREV_BUTTON_CELL"));
		tagTD.addAttribute("nowrap");
		tagTD.setVisible(false);
		tagTD.setStyleClass("tbtr_footerallcell_enable");
		tagTD.setContent(getShowPrevMessage());
		tagTD.addAttribute("onclick", getShowNavigationClickHandler("showPrevRowSet", showPrevUrl));
	}

	/**
	 * @return
	 */
	private String getShowNavigationClickHandler(String functionName, String contentLoadUrl) {
		JSFunction showPrevFunction = new JSFunction(functionName);
		showPrevFunction.addStringParam(table.getTableTreeName());
		showPrevFunction.addStringParam(table.getTablePath());
		showPrevFunction.addStringParam(contentLoadUrl);
		return showPrevFunction.toString();
	}

	/**
	 * @return
	 */
	private String getShowNextMessage() {
		return ">>";
	}

	/**
	 * @return
	 */
	private String getShowPrevMessage() {
		return "<<";
	}

	/**
	 * @return
	 */
	private String getShowAppendMessage() {
		return "Show next " + pageSize;
	}

	public void setShowNextUrl(String showNextUrl) {
		this.showNextUrl = showNextUrl;
	}

	public void setShowPrevUrl(String showPrevUrl) {
		this.showPrevUrl = showPrevUrl;
	}

	/**
	 * @param commandLink
	 */
	public void setShowAppendUrl(String showAppendUrl) {
		this.showAppendUrl = showAppendUrl;

	}
}
