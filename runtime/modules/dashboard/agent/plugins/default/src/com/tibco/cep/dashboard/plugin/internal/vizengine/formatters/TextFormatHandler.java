package com.tibco.cep.dashboard.plugin.internal.vizengine.formatters;

import java.util.Date;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.alerts.VisualAlertResult;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ColumnConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.Font;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ColumnType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.FontStyle;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.util.DashboardMessageFormat;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.ColorMap;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DataConfigVisitor;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DataFormatHandler;

/**
 * @author apatil
 *
 */
public class TextFormatHandler extends DataFormatHandler {

	@Override
	public ColumnConfig getColumnConfig(String id, MALDataFormat dataFormat, DataConfigVisitor visitor, PresentationContext ctx) {
		return createDataColumnConfig(id, dataFormat, ColumnType.TEXT, ctx.getViewsConfigHelper().getInsightSkin().getFontColor());
	}

	@Override
	public String getDisplayValue(MALDataFormat dataFormat, Tuple tuple, DataConfigVisitor visitor, PresentationContext ctx) throws VisualizationException {
		//INFO adding support for getShowLabel
		if (dataFormat.getShowLabel() == false) {
			return "";
		}
		// get data config
		MALDataConfig dataConfig = getDataConfig(dataFormat);
		// get series config
		MALSeriesConfig seriesConfig = getSeriesConfig(dataConfig);
		// get data source handler
		DataSourceHandler dataSourceHandler = ctx.getDataSourceHandler(seriesConfig);
		// get source field
		MALFieldMetaInfo sourceField = getSourceField(dataConfig, ctx);
		//get visual alerts
		VisualAlertResult alertResult = getVisualAlertResults(dataFormat, visitor.getAlertEvaluator(), sourceField, tuple, ctx);
		//get the display format
		String displayFormat = getValue(alertResult, VisualAlertResult.TRANSFORMATION_FORMAT_KEY, dataFormat.getDisplayFormat());
		if (displayFormat == null) {
			//display format is "", which means raw toString with NULL support
			String sourceFldName = sourceField.getName();
			FieldValue value = tuple.getFieldValueByName(sourceFldName);
			if (value.isNull() == true) {
				Object[] values = getProcessedOutputFieldDisplayValues(tuple, dataSourceHandler, visitor.getFieldValueProcessor(), ctx);
				return String.valueOf(values[tuple.getSchema().getFieldPositionByName(sourceFldName)]);
			}
			if (tuple.getSchema().getFieldDataTypeByName(sourceFldName) == BuiltInTypes.DATETIME){
				return ctx.getCustomDateFormat().format(tuple.getFieldValueByName(sourceFldName));
			}
			return tuple.getFieldValueByName(sourceFldName).toString();
		}
		//we have a proper format
		try {
			return DashboardMessageFormat.format(displayFormat, tuple, getProcessedOutputFieldDisplayValues(tuple, dataSourceHandler, visitor.getFieldValueProcessor(), ctx), ctx.getCustomDateFormat());
		} catch (IllegalArgumentException ex) {
			int count = tuple.getFieldCount();
			StringBuilder tupleValues = new StringBuilder();
			for (int i = 0; i < count; i++) {
				tupleValues.append(tuple.getFieldValueByPosition(i).toString());
				if (i + 1 < count) {
					tupleValues.append(",");
				}
			}
			String exMsg = getMessage("dataformathandler.text.displayvalueformat.failure", ctx.getLocale(), ctx.getMessageGeneratorArgs(ex, URIHelper.getURI(dataFormat), tupleValues, displayFormat));
			throw new VisualizationException(exMsg, ex);
		}
	}

	@Override
	public DataColumn getData(String id, String templateid, MALDataFormat dataFormat, String displayValue, Tuple tuple, boolean processCorePropsOnly, DataConfigVisitor visitor, PresentationContext ctx) throws VisualizationException, MALException, ElementNotFoundException {
		// get data config
		MALDataConfig dataConfig = getDataConfig(dataFormat);
		// get series config
		MALSeriesConfig seriesConfig = getSeriesConfig(dataConfig);
		// get data source handler
		DataSourceHandler dataSourceHandler = ctx.getDataSourceHandler(seriesConfig);
		// get source field
		MALFieldMetaInfo sourceField = getSourceField(dataConfig, ctx);

		// change output depending on the source field type
		String sourceFldName = sourceField.getName();
		Object actualFieldValue = tuple.getFieldValueByName(sourceFldName);
		if (((FieldValue) actualFieldValue).getDataType() == BuiltInTypes.DATETIME) {
			FieldValue synDate = (FieldValue) actualFieldValue;
			actualFieldValue = ((Date) synDate.getValue()).getTime();
		}

		DataColumn dataColumn = new DataColumn();
		dataColumn.setTemplateID(templateid);

		// id
		dataColumn.setId(id);

		// value
		dataColumn.setValue(actualFieldValue.toString());

		VisualAlertResult alertResult = getVisualAlertResults(dataFormat, visitor.getAlertEvaluator(), sourceField, tuple, ctx);
		// display value
		if (displayValue == null) {
			//INFO adding support for getShowLabel
			if (dataFormat.getShowLabel() == false) {
				displayValue = "";
			} else {
				String displayFormat = getValue(alertResult, VisualAlertResult.TRANSFORMATION_FORMAT_KEY, dataFormat.getDisplayFormat());
				if (displayFormat == null) {
					//display format is "", which means raw toString with NULL support
					FieldValue value = tuple.getFieldValueByName(sourceFldName);
					if (value.isNull() == true) {
						Object[] values = getProcessedOutputFieldDisplayValues(tuple, dataSourceHandler, visitor.getFieldValueProcessor(), ctx);
						displayValue = String.valueOf(values[tuple.getSchema().getFieldPositionByName(sourceFldName)]);
					}
					else if (tuple.getSchema().getFieldDataTypeByName(sourceFldName) == BuiltInTypes.DATETIME){
						displayValue = ctx.getCustomDateFormat().format(tuple.getFieldValueByName(sourceFldName));
					}
					else {
						displayValue = tuple.getFieldValueByName(sourceFldName).toString();
					}
				}
				else {
					try {
						displayValue = DashboardMessageFormat.format(displayFormat, tuple, getProcessedOutputFieldDisplayValues(tuple, dataSourceHandler, visitor.getFieldValueProcessor(), ctx), ctx.getCustomDateFormat());
					} catch (IllegalArgumentException ex) {
						int count = tuple.getFieldCount();
						StringBuilder tupleValues = new StringBuilder();
						for (int i = 0; i < count; i++) {
							tupleValues.append(tuple.getFieldValueByPosition(i).toString());
							if (i + 1 < count) {
								tupleValues.append(",");
							}
						}
						String exMsg = getMessage("dataformathandler.text.displayvalueformat.failure", ctx.getLocale(), ctx.getMessageGeneratorArgs(ex, URIHelper.getURI(dataFormat), tupleValues, displayFormat));
						throw new VisualizationException(exMsg, ex);
					}
				}
			}
		}
		dataColumn.setDisplayValue(displayValue);

		if (processCorePropsOnly == true) {
			return dataColumn;
		}

		// tool tip
		String toolTipFormat = getValue(alertResult, VisualAlertResult.TOOLTIP_FORMAT_KEY, dataFormat.getToolTipFormat());
		if (toolTipFormat != null) {
			try {
				String tooltip = DashboardMessageFormat.format(toolTipFormat, tuple, getProcessedOutputFieldToolTipValues(tuple, dataSourceHandler, visitor.getFieldValueProcessor(), ctx), ctx.getCustomDateFormat());
				dataColumn.setTooltip(tooltip);
			} catch (IllegalArgumentException ex) {
				int count = tuple.getFieldCount();
				StringBuilder tupleValues = new StringBuilder();
				for (int i = 0; i < count; i++) {
					tupleValues.append(tuple.getFieldValueByPosition(i).toString());
					if (i + 1 < count) {
						tupleValues.append(",");
					}
				}
				String exMsg = getMessage("dataformathandler.text.tooltipformat.failure", ctx.getLocale(), ctx.getMessageGeneratorArgs(ex, URIHelper.getURI(dataFormat), tupleValues, toolTipFormat));
				throw new VisualizationException(exMsg, ex);
			}
		}

		String baseColor = getValue(alertResult, VisualAlertResult.COLOR_FORMAT_KEY, null);
		String highLightColor = getValue(alertResult, VisualAlertResult.HIGHLIGHT_COLOR_FORMAT_KEY, VisualAlertResult.DEFAULT_HIGHLIGHT_COLOR);
		String fontColor = getValue(alertResult, VisualAlertResult.FONTCOLOR_FORMAT_KEY, null);
		String fontStyle = getValue(alertResult, VisualAlertResult.FONTSTYLE_FORMAT_KEY, null);

		if (baseColor != null && highLightColor != null) {
			dataColumn.setBaseColor(ColorMap.toHex(baseColor));
			dataColumn.setHighLightColor(ColorMap.toHex(highLightColor));
		}

		// overridden font
		if (fontColor != null && fontStyle != null) {
			Font overRiddenFont = new Font();
			overRiddenFont.setColor(ColorMap.toHex(fontColor));
			overRiddenFont.setStyle(FontStyle.valueOf(fontStyle));
			dataColumn.setFont(overRiddenFont);
		}

		String link = generateLink(seriesConfig, tuple, ctx);
		if (link != null) {
			dataColumn.setLink(link);
		}

		String urlRefParams = generateURLRefParams(dataSourceHandler, sourceField, tuple, ctx);
		if (urlRefParams != null) {
			TypeSpecificAttribute urlRefParamsAttr = new TypeSpecificAttribute();
			urlRefParamsAttr.setName("hrefprms");
			urlRefParamsAttr.setContent(urlRefParams);
			dataColumn.addTypeSpecificAttribute(urlRefParamsAttr);
		}
		return dataColumn;
	}

}