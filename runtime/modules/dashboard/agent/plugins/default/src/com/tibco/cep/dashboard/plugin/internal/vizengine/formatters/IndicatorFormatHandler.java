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
import com.tibco.cep.dashboard.psvr.mal.model.MALIndicatorFieldFormat;
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
public class IndicatorFormatHandler extends DataFormatHandler {

	private static final String DEFAULT_COLOR = "-1"; //which means NONE , -2 means blank

	public ColumnConfig getColumnConfig(String id, MALDataFormat dataFormat, DataConfigVisitor visitor, PresentationContext ctx) {
		ColumnConfig dataColumnCfg = createDataColumnConfig(id, dataFormat, ColumnType.INDICATOR, ctx.getViewsConfigHelper().getInsightSkin().getFontColor());
		// check if the showvalue is on
		MALIndicatorFieldFormat trueFieldFormat = (MALIndicatorFieldFormat) dataFormat;
		//INFO moving from getShowTextValue to getShowLabel
		if (trueFieldFormat.getShowLabel() == true) {
			dataColumnCfg.addTypeSpecificAttribute(createTypeSpecificAttribute("showvalue", Boolean.toString(trueFieldFormat.getShowLabel())));
			// check the anchor value
			dataColumnCfg.addTypeSpecificAttribute(createTypeSpecificAttribute("textanchor", trueFieldFormat.getTextValueAnchor().toString()));
		}
		return dataColumnCfg;

	}

	@Override
	public String getDisplayValue(MALDataFormat dataFormat, Tuple tuple, DataConfigVisitor visitor, PresentationContext ctx) throws VisualizationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DataColumn getData(String id, String templateid, MALDataFormat dataFormat, String displayValue, Tuple tuple, boolean processCorePropsOnly,
			DataConfigVisitor visitor, PresentationContext ctx) throws VisualizationException, MALException, ElementNotFoundException {
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
			Date fldDateValue = (Date) ((FieldValue) actualFieldValue).getValue();
			actualFieldValue = new Long(fldDateValue.getTime());
		}

		// check if the showvalue is on
		MALIndicatorFieldFormat indicatorFormat = (MALIndicatorFieldFormat) dataFormat;
		DataColumn dataColumn = new DataColumn();
		dataColumn.setTemplateID(templateid);

		// id
		dataColumn.setId(id);

		// value
		dataColumn.setValue(actualFieldValue.toString());

		VisualAlertResult visualAlertResult = getVisualAlertResults(dataFormat, visitor.getAlertEvaluator(), sourceField, tuple, ctx);
		// indicator color
		String baseColor = getValue(visualAlertResult, VisualAlertResult.COLOR_FORMAT_KEY, DEFAULT_COLOR);
		//INFO moving from getShowTextValue to getShowLabel
		if (indicatorFormat.getShowLabel() == true) {
			if (displayValue == null) {
				String displayFormat = getValue(visualAlertResult, VisualAlertResult.TRANSFORMATION_FORMAT_KEY, dataFormat.getDisplayFormat());
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
						String exMsg = getMessage("dataformathandler.indicator.displayvalueformat.failure", ctx.getLocale(), ctx.getMessageGeneratorArgs(ex, URIHelper.getURI(dataFormat), tupleValues, displayFormat));
						throw new VisualizationException(exMsg, ex);
					}
				}
			}
			// display value
			dataColumn.setDisplayValue(displayValue);

			if (processCorePropsOnly == false) {
				String fontColor = getValue(visualAlertResult, VisualAlertResult.FONTCOLOR_FORMAT_KEY, null);
				String fontStyle = getValue(visualAlertResult, VisualAlertResult.FONTSTYLE_FORMAT_KEY, null);
				// overridden font
				if (fontColor != null && fontStyle != null) {
					Font overRiddenFont = new Font();
					overRiddenFont.setColor(ColorMap.toHex(fontColor));
					overRiddenFont.setStyle(FontStyle.valueOf(fontStyle));
					dataColumn.setFont(overRiddenFont);
				}
			}
		}

		// baseColor
		dataColumn.setBaseColor(ColorMap.toHex(baseColor));

		if (processCorePropsOnly == true) {
			return dataColumn;
		}

		// tool tip
		String toolTipFormat = getValue(visualAlertResult, VisualAlertResult.TOOLTIP_FORMAT_KEY, dataFormat.getToolTipFormat());
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
				String exMsg = getMessage("dataformathandler.indicator.tooltipformat.failure", ctx.getLocale(), ctx.getMessageGeneratorArgs(ex, URIHelper.getURI(dataFormat), tupleValues, toolTipFormat));
				throw new VisualizationException(exMsg, ex);
			}
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
