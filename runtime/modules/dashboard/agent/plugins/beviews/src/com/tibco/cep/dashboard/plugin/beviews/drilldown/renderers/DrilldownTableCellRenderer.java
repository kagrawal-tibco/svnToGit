package com.tibco.cep.dashboard.plugin.beviews.drilldown.renderers;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableCellModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableCellRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.ClassifierCellModel;
import com.tibco.cep.dashboard.psvr.ogl.model.ColumnConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ColumnType;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class DrilldownTableCellRenderer implements TableCellRenderer {

	private static final IndicatorRenderer INDICATION_RENDERER = new IndicatorRenderer();

	private static final ProgressBarRenderer PROGRESS_BAR_RENDERER = new ProgressBarRenderer();

	private static final TextRenderer TEXT_RENDERER = new TextRenderer();

	private Logger logger;

	private ExceptionHandler exceptionHandler;

	@SuppressWarnings("unused")
	private MessageGenerator messageGenerator;

	/**
	 * @param request
	 */
	public DrilldownTableCellRenderer(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
	}

	public StringBuffer getHTML(Object userObject) {
		StringBuffer buffer = new StringBuffer();
		if (userObject == null) {
			if (logger.isEnabledFor(Level.DEBUG) == true)
				logger.log(Level.DEBUG, "User Object is null, returning a string buffer containing 'null'...");
			buffer.append("null");
		} else if (userObject instanceof ClassifierCellModel) {
			try {
				ClassifierCellModel cellModel = (ClassifierCellModel) userObject;
				buffer.append(getHTML(cellModel));
			} catch (Exception e) {
				exceptionHandler.handleException("could not generate HTML for " + userObject, e);
				buffer.append("");
			}
		} else if (userObject instanceof TableCellModel) {
			buffer.append("<font class=\"normal_text\">" + ((TableCellModel) userObject).getDisplayValue() + "&nbsp;</font>");
		} else {
			buffer.append("<font class=\"normal_text\">" + userObject + "&nbsp;</font>");
		}
		return buffer;
	}

	private StringBuilder getHTML(ClassifierCellModel cellModel) {
		ColumnConfig columnConfig = cellModel.getColumnConfig();
		ColumnType columnType = columnConfig.getType();
		if (columnType == ColumnType.INDICATOR) {
			return INDICATION_RENDERER.getHTML(cellModel);
		} else if (columnType == ColumnType.PROGRESS) {
			return PROGRESS_BAR_RENDERER.getHTML(cellModel);
		} else if (columnType == ColumnType.TEXT) {
			return TEXT_RENDERER.getHTML(cellModel);
		}
		return null;
	}
}