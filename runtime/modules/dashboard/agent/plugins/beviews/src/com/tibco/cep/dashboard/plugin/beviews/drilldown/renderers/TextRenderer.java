package com.tibco.cep.dashboard.plugin.beviews.drilldown.renderers;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.ClassifierCellModel;

public class TextRenderer {

	public StringBuilder getHTML(ClassifierCellModel cellModel) {
		StringBuilder buffer = new StringBuilder("<font class=\"normal_text\">");
		buffer.append(cellModel.getDisplayValue());
		buffer.append("</font>");
		return buffer;
	}

}