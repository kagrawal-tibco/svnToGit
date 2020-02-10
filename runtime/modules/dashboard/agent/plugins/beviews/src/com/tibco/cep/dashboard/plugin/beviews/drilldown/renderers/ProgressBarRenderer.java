package com.tibco.cep.dashboard.plugin.beviews.drilldown.renderers;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLTag;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.ClassifierCellModel;
import com.tibco.cep.dashboard.psvr.ogl.model.ColumnConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;

public class ProgressBarRenderer {

	private static final String ANCHOR_EAST = "east";

	@SuppressWarnings("unused")
	private static final String ANCHOR_WEST = "west";

	private String verticalImage = "vertical.gif";

	private static final String BAR_COLOR_IMAGE_UNFILLED = "gray_bar.gif";

	private static final String BAR_COLOR_IMAGE_RED = "red_bar.gif";

	private static final String BAR_COLOR_IMAGE_BLUE = "blue_bar.gif";

	private static final String BAR_COLOR_IMAGE_YELLOW = "yellow_bar.gif";

	private static final String BAR_COLOR_RED = "red";

	private static final String BAR_COLOR_BLUE = "blue";

	private static final String BAR_COLOR_YELLOW = "yellow";

	private String barColorImage = BAR_COLOR_IMAGE_BLUE;

	private static final String RELATIVE_IMAGE_PATH = "img/executive/drilldown/";

	public StringBuilder getHTML(ClassifierCellModel cellModel) {
		StringBuilder buffer = new StringBuilder();
		render(buffer, cellModel);
		return buffer;
	}

	/**
	 * @param buffer
	 * @param cellModel
	 * @throws RenderingConfigException
	 */
	private void render(StringBuilder buffer, ClassifierCellModel cellModel) {
		boolean showValue = false;
		String textAnchor = null;
		int threshold = 0;

		ColumnConfig columnConfig = cellModel.getColumnConfig();
		TypeSpecificAttribute[] attributes = columnConfig.getTypeSpecificAttribute();
		for (int i = 0; i < attributes.length; i++) {
			TypeSpecificAttribute attribute = attributes[i];
			if (attribute.getName().equals("showvalue")) {
				showValue = new Boolean(attribute.getContent()).booleanValue();
			} else if (attribute.getName().equals("textanchor")) {
				textAnchor = attribute.getContent();
			} else if (attribute.getName().equals("threshold")) {
				threshold = Integer.parseInt(attribute.getContent());
			}
		}

		HTMLTag tagTable = new HTMLTag("table");
		tagTable.addTableGeneralProps();
		tagTable.addAttribute("width", "100%");
		HTMLTag tagTR = tagTable.addTag("tr");

		if (showValue) {
			if (ANCHOR_EAST.equals(textAnchor)) {
				renderSeparator(tagTR);
				renderProgressBar(tagTR, cellModel, threshold);
				renderSeparator(tagTR);
				renderText(tagTR, cellModel);
				renderSeparator(tagTR);
			} else {
				renderSeparator(tagTR);
				renderText(tagTR, cellModel);
				renderSeparator(tagTR);
				renderProgressBar(tagTR, cellModel, threshold);
				renderSeparator(tagTR);
			}
		} else {
			renderSeparator(tagTR);
			renderProgressBar(tagTR, cellModel, threshold);
			renderSeparator(tagTR);
		}

		buffer.append(tagTable.toString());
	}

	/*
	 * HTML to generate <table border="0" cellspacing="0" cellpadding="0"> <tr> <td style="width:50px"></td> <td align="center" width="3"><img src="vertical.gif" width="3" height="3"></td> <td style="width:150px"></td> </tr>
	 * <tr> <td colspan="3" style="background-image:url(gray_bar.gif)"><img src="blue_bar.gif" width="67%" height="11"></img></td> </tr> <tr> <td ></td> <td align="center" width="3"><img src="vertical.gif" width="3"
	 * height="3"></td> <td ></td> </tr> </table>
	 */

	/**
	 * @param tagTR
	 */
	private void renderSeparator(HTMLTag tagTR) {
		HTMLTag tagTDSeparator = tagTR.addTag("td");
		tagTDSeparator.setStyleClass("progressbar_text");
		tagTDSeparator.setContent("&nbsp;");

	}

	private void renderProgressBar(HTMLTag tagTR, ClassifierCellModel cellModel, int threshold) {
		HTMLTag tagTDPB = tagTR.addTag("td");
		tagTDPB.addAttribute("width", "100%");

		HTMLTag tagTable = tagTDPB.addTag("table");
		tagTable.addTableGeneralProps();
		tagTable.addAttribute("width", "100%");

		renderTopRow(tagTable, threshold);
		renderMiddleRow(tagTable, cellModel);
		renderBottomRow(tagTable, threshold);
	}

	/**
	 * @param tagTable
	 * @param cellModel
	 */
	private void renderTopRow(HTMLTag tagTable, int threshold) {
		renderThresholdBars(tagTable, threshold);
	}

	/*
	 * <tr> <td colspan="3" style="background-image:url(gray_bar.gif)"><img src="blue_bar.gif" width="67%" height="11"></img></td> </tr>
	 */

	private void renderMiddleRow(HTMLTag tagTable, ClassifierCellModel cellModel) {
		HTMLTag tagTR = tagTable.addTag("tr");
		HTMLTag tagTD = tagTR.addTag("td");
		tagTD.addAttribute("width", "100%");
		tagTD.addAttribute("colspan", "3");
		tagTD.addStyle("background-image", "url(" + RELATIVE_IMAGE_PATH + BAR_COLOR_IMAGE_UNFILLED + ")");

		HTMLTag tagImg = tagTD.addTag("img");
		tagImg.addAttribute("src", RELATIVE_IMAGE_PATH + getBarColorImage(cellModel));
		tagImg.addAttribute("width", cellModel.getDataColumn().getDisplayValue()); // This we get as percentage
		tagImg.addAttribute("height", "11");
	}

	/**
	 * @param tagTable
	 * @param cellModel
	 */
	private void renderBottomRow(HTMLTag tagTable, int threshold) {
		renderThresholdBars(tagTable, threshold);
	}

	/*
	 * <tr> <td style="width:50px"></td> <td align="center" width="3"><img src="vertical.gif" width="3" height="3"></td> <td style="width:150px"></td> </tr>
	 */

	private void renderThresholdBars(HTMLTag tagTable, int threshold) {
		HTMLTag tagTR = tagTable.addTag("tr");
		HTMLTag tagTD1 = tagTR.addTag("td");
		tagTD1.addStyle("width", threshold + "%");

		HTMLTag tagTD2 = tagTR.addTag("td");
		tagTD2.addAttribute("align", "center");
		tagTD2.addAttribute("width", "3");

		HTMLTag tagImg = tagTD2.addTag("img");
		tagImg.addAttribute("src", RELATIVE_IMAGE_PATH + verticalImage);
		tagImg.addAttribute("width", "3");
		tagImg.addAttribute("height", "3");

		HTMLTag tagTD3 = tagTR.addTag("td");
		tagTD3.addStyle("width", (100 - threshold) + "%");
	}

	/**
	 * @param cellModel
	 * @param tagTDText
	 */
	private void renderText(HTMLTag tagTR, ClassifierCellModel cellModel) {
		HTMLTag tagTDText = tagTR.addTag("td");
		tagTDText.setStyleClass("progressbar_text");
		tagTDText.setContent(cellModel.getDataColumn().getDisplayValue() + "&nbsp;");
	}

	/**
	 * @param cellModel
	 * @return Returns the barColorImage.
	 */
	private String getBarColorImage(ClassifierCellModel cellModel) {
		String barColor = cellModel.getDataColumn().getBaseColor();
		if (BAR_COLOR_RED.equals(barColor)) {
			barColorImage = BAR_COLOR_IMAGE_RED;
		} else if (BAR_COLOR_BLUE.equals(barColor)) {
			barColorImage = BAR_COLOR_IMAGE_BLUE;
		} else if (BAR_COLOR_YELLOW.equals(barColor)) {
			barColorImage = BAR_COLOR_IMAGE_YELLOW;
		} else {
			barColorImage = BAR_COLOR_IMAGE_BLUE;
		}
		return barColorImage;
	}
}