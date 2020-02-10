package com.tibco.cep.dashboard.plugin.beviews.drilldown.renderers;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLTag;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.ClassifierCellModel;
import com.tibco.cep.dashboard.psvr.ogl.model.ColumnConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;

class IndicatorRenderer {

	private static final String ANCHOR_EAST = "east";

	@SuppressWarnings("unused")
	private static final String ANCHOR_WEST = "west";

	private static final String INDICATOR_COLOR_RED = "red";

	private static final String INDICATOR_COLOR_BLUE = "blue";

	private static final String INDICATOR_COLOR_GREEN = "green";

	private static final String INDICATOR_COLOR_YELLOW = "yellow";

	private static final String INDICATOR_RED_IMAGE_NAME = "red_ind.gif";

	private static final String INDICATOR_BLUE_IMAGE_NAME = "blue_ind.gif";

	private static final String INDICATOR_GREEN_IMAGE_NAME = "green_ind.gif";

	private static final String INDICATOR_YELLOW_IMAGE_NAME = "yellow_ind.gif";

	private static final String INDICATOR_TRANSPARENT_IMAGE_NAME = "none_ind.gif";

	private static final String RELATIVE_IMAGE_PATH = "img/executive/drilldown/";

	public StringBuilder getHTML(ClassifierCellModel cellModel) {
		StringBuilder buffer = new StringBuilder();
		render(buffer, cellModel);
		return buffer;
	}

	private void render(StringBuilder buffer, ClassifierCellModel cellModel) {
		boolean showValue = false;
		String textAnchor = null;

		ColumnConfig columnConfig = cellModel.getColumnConfig();
		TypeSpecificAttribute[] attributes = columnConfig.getTypeSpecificAttribute();
		for (int i = 0; i < attributes.length; i++) {
			TypeSpecificAttribute attribute = attributes[i];
			if (attribute.getName().equals("showvalue")) {
				showValue = new Boolean(attribute.getContent()).booleanValue();
			} else if (attribute.getName().equals("textanchor")) {
				textAnchor = attribute.getContent();
			}
		}

		HTMLTag table = new HTMLTag("TABLE");
		table.addTableGeneralProps();
		table.addAttribute("width", "100%");
		HTMLTag tr = table.addTag("TR");

		if (showValue) {
			if (ANCHOR_EAST.equals(textAnchor)) {
				renderSeparator(tr);
				renderIndicator(tr, cellModel);
				renderSeparator(tr);
				renderText(tr, cellModel);
				renderSeparator(tr);
			} else {
				renderSeparator(tr);
				renderText(tr, cellModel);
				renderSeparator(tr);
				renderIndicator(tr, cellModel);
				renderSeparator(tr);

			}
		} else {
			renderSeparator(tr);
			renderIndicator(tr, cellModel);
			renderSeparator(tr);
		}
		buffer.append(table.toString());
	}

	/**
	 * @param tr
	 * @param cellModel
	 */
	private void renderText(HTMLTag tr, ClassifierCellModel cellModel) {
		HTMLTag tagTDText = tr.addTag("td");
		tagTDText.setStyleClass("indicator_text");
		tagTDText.setContent(cellModel.getDisplayValue().toString());
		tagTDText.addAttribute("width", "100%");
	}

	/**
	 * @param tr
	 * @param cellModel
	 * @throws RenderingException
	 */
	private void renderIndicator(HTMLTag tr, ClassifierCellModel cellModel) {
		HTMLTag tdObject = tr.addTag("TD");
		// tdObject.addAttribute("width", "100%");
		tdObject.addAttribute("ALIGN", "CENTER");
		tdObject.addTag(getIndicatorImgTag(cellModel));
	}

	/**
	 * @param tr
	 */
	private void renderSeparator(HTMLTag tr) {
		HTMLTag tagTDSeparator = tr.addTag("td");
		tagTDSeparator.setStyleClass("indicator_text");
		tagTDSeparator.setContent("&nbsp;");
	}

	private HTMLTag getIndicatorImgTag(ClassifierCellModel cellModel) {
		HTMLTag tagImg = new HTMLTag("img");
		tagImg.addAttribute("src", RELATIVE_IMAGE_PATH + getIndicatorColorImage(cellModel));
		tagImg.setStyleClass("pagelettext");
		return tagImg;
	}

	private String getIndicatorColorImage(ClassifierCellModel cellModel) {
		String indicatorColor = cellModel.getDataColumn().getBaseColor();
		if (INDICATOR_COLOR_RED.equals(indicatorColor)) {
			return INDICATOR_RED_IMAGE_NAME;
		} else if (INDICATOR_COLOR_BLUE.equals(indicatorColor)) {
			return INDICATOR_BLUE_IMAGE_NAME;
		} else if (INDICATOR_COLOR_YELLOW.equals(indicatorColor)) {
			return INDICATOR_YELLOW_IMAGE_NAME;
		} else if (INDICATOR_COLOR_GREEN.equals(indicatorColor)) {
			return INDICATOR_GREEN_IMAGE_NAME;
		} else {
			return INDICATOR_TRANSPARENT_IMAGE_NAME;
		}
	}
}