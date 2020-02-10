package com.tibco.cep.dashboard.plugin.internal.vizengine.formatters;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.FieldValueArray;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.psvr.alerts.VisualAlertResult;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALConstantValueOption;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALFieldReferenceValueOption;
import com.tibco.cep.dashboard.psvr.mal.model.MALProgressBarFieldFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALValueOption;
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
import com.tibco.cep.kernel.service.logging.Level;

/**
 * @author apatil
 *
 */
public class ProgressBarFormatHandler extends DataFormatHandler {

	private static final String DEFAULT_PROG_BAR_COLOR = "red";

	private static final FieldValue ZERO = new FieldValue(BuiltInTypes.DOUBLE, 0.0, false);

//	private static final FieldValue ONE = new FieldValue(BuiltInTypes.DOUBLE, 1.0, false);

	public ColumnConfig getColumnConfig(String id, MALDataFormat dataFormat, DataConfigVisitor visitor, PresentationContext ctx) {
		// create column
		ColumnConfig column = createDataColumnConfig(id, dataFormat, ColumnType.PROGRESS, ctx.getViewsConfigHelper().getInsightSkin().getFontColor());
		// check if the showvalue is on
		MALProgressBarFieldFormat trueFieldFormatter = (MALProgressBarFieldFormat) dataFormat;
		// INFO moving from getShowTextValue to getShowLabel
		if (trueFieldFormatter.getShowLabel() == true) {
			column.addTypeSpecificAttribute(createTypeSpecificAttribute("showvalue", Boolean.toString(trueFieldFormatter.getShowLabel())));
			// check the anchor value
			column.addTypeSpecificAttribute(createTypeSpecificAttribute("textanchor", trueFieldFormatter.getTextValueAnchor().toString()));
		}
		// PORT threshold value
		// PATCH what about colorspecification and border config
		return column;
	}

	@Override
	public String getDisplayValue(MALDataFormat dataFormat, Tuple tuple, DataConfigVisitor visitor, PresentationContext ctx) throws VisualizationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DataColumn getData(String id, String templateid, MALDataFormat dataFormat, String displayValue, Tuple tuple, boolean processCorePropsOnly, DataConfigVisitor visitor, PresentationContext ctx)
			throws VisualizationException, MALException, ElementNotFoundException {
		// get data config
		MALDataConfig dataConfig = getDataConfig(dataFormat);
		// get series config
		MALSeriesConfig seriesConfig = getSeriesConfig(dataConfig);
		// get data source handler
		DataSourceHandler dataSourceHandler = ctx.getDataSourceHandler(seriesConfig);
		// get source field
		MALFieldMetaInfo sourceField = getSourceField(dataConfig, ctx);
		String sourceFldName = sourceField.getName();
		FieldValue actualFieldValue = tuple.getFieldValueByName(sourceFldName);

		MALProgressBarFieldFormat trueFieldFormat = (MALProgressBarFieldFormat) dataFormat;

		//calculate the percentage value
		double minValue = getValueOption(tuple, trueFieldFormat.getMinValue(), ctx);
		if (Double.isNaN(minValue) == true) {
			minValue = 0.0;
		}
		double maxValue = getValueOption(tuple, trueFieldFormat.getMaxValue(), ctx);
		if (Double.isNaN(maxValue) == true) {
			maxValue = 100.0;
		}
		FieldValue valueAsPercentage = getValueAsPercentage(URIHelper.getURI(dataFormat), sourceFldName, actualFieldValue, minValue, maxValue, ctx);
		double actualPercentageValue = (Double)valueAsPercentage.getValue()*100;

		DataColumn dataColumn = new DataColumn();
		dataColumn.setTemplateID(templateid);

		// id
		dataColumn.setId(id);

		// value
		//INFO setting the value in the data column to the unformatted percent value e.g. 1200 or 55.66 instead of the actual field value
		dataColumn.setValue(String.valueOf(actualPercentageValue));

		//create new tuple using the percentage value instead of the field value
		HashMap<String, FieldValue> overridingValues = new HashMap<String, FieldValue>();
		overridingValues.put(sourceField.getId(), new FieldValue(BuiltInTypes.DOUBLE, actualPercentageValue));
		OverridingTuple overridingTuple = new OverridingTuple(tuple,overridingValues);

		VisualAlertResult alertResult = getVisualAlertResults(dataFormat, visitor.getAlertEvaluator(), sourceField, overridingTuple, ctx);

		String baseColor = getValue(alertResult, VisualAlertResult.COLOR_FORMAT_KEY, DEFAULT_PROG_BAR_COLOR);
		// INFO moving from getShowTextValue to getShowLabel
		if (trueFieldFormat.getShowLabel() == true) {
			if (displayValue == null) {
				displayValue = NumberFormat.getPercentInstance(ctx.getLocale()).format(valueAsPercentage.getValue());
			}
			// display value
			dataColumn.setDisplayValue(displayValue);

			if (processCorePropsOnly == false) {
				String fontColor = getValue(alertResult, VisualAlertResult.FONTCOLOR_FORMAT_KEY, null);
				String fontStyle = getValue(alertResult, VisualAlertResult.FONTSTYLE_FORMAT_KEY, null);
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
		// Front end needs color in hex only. Map it.
		dataColumn.setBaseColor(ColorMap.toHex(baseColor));

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
				String exMsg = getMessage("dataformathandler.progbar.tooltipformat.failure", ctx.getLocale(), ctx.getMessageGeneratorArgs(ex, URIHelper.getURI(dataFormat), tupleValues, toolTipFormat));
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

	private double getValueOption(Tuple tuple, MALValueOption valueOption, PresentationContext ctx) throws VisualizationException, MALException, ElementNotFoundException {
		if (valueOption instanceof MALConstantValueOption) {
			return ((MALConstantValueOption) valueOption).getValue();
		}
		if (valueOption instanceof MALFieldReferenceValueOption) {
			MALFieldMetaInfo fieldRef = ctx.resolveFieldRef(((MALFieldReferenceValueOption) valueOption).getField());
			if (fieldRef.isNumeric() == false) {
				throw new VisualizationException(fieldRef + " is not numeric");
			}
			FieldValue fieldValue = tuple.getFieldValueByName(fieldRef.getName());
			return Double.parseDouble(fieldValue.toString());
		}
		return Double.NaN;
	}

	private FieldValue getValueAsPercentage(String dataFormatPath, String fieldName, FieldValue fieldValue, double minValue, double maxValue, PresentationContext ctx) {
		double synDouble = 0.0;
		try {
			synDouble = (Double) BuiltInTypes.DOUBLE.valueOf(fieldValue.getValue().toString());
		} catch (NullPointerException ex) {
			String msg = getMessage("dataformathandler.progbar.invalid.field.value", ctx.getLocale(), ctx.getMessageGeneratorArgs(null, dataFormatPath, fieldName, fieldValue.toString()));
			logger.log(Level.WARN, msg);
		} catch (NumberFormatException ex) {
			String msg = getMessage("dataformathandler.progbar.invalid.field.datatype", ctx.getLocale(), ctx.getMessageGeneratorArgs(null, dataFormatPath, fieldName, fieldValue.getClass().getName()));
			logger.log(Level.WARN, msg);
		}
		double compensation = 0 - minValue;
		double compensatedMinValue = minValue + compensation;
		double compensatedMaxValue = maxValue + compensation;
		if (compensatedMinValue == compensatedMaxValue) {
			return ZERO;
		}
		if (compensatedMaxValue == 0) {
			return ZERO;
		}
		return new FieldValue(BuiltInTypes.DOUBLE, (synDouble + compensation) / compensatedMaxValue);
	}

	class OverridingTuple extends Tuple {

		private static final long serialVersionUID = -273334558419451818L;

		private Tuple tuple;
		private Map<String, FieldValue> overridingValues;

		OverridingTuple(Tuple tuple,Map<String,FieldValue> overridingValues){
			if (tuple == null){
				throw new IllegalArgumentException("Tuple cannot be null");
			}
			if (overridingValues == null){
				throw new IllegalArgumentException("Overriding values cannot be null");
			}
			this.tuple = tuple;
			this.overridingValues = overridingValues;
			this.schema = tuple.getSchema();
		}

		@Override
		public Object clone() throws CloneNotSupportedException {
			return new OverridingTuple((Tuple) this.tuple.clone(),new HashMap<String, FieldValue>(this.overridingValues));
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((overridingValues == null) ? 0 : overridingValues.hashCode());
			result = prime * result + ((tuple == null) ? 0 : tuple.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!super.equals(obj)) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			OverridingTuple other = (OverridingTuple) obj;
			if (overridingValues == null) {
				if (other.overridingValues != null) {
					return false;
				}
			} else if (!overridingValues.equals(other.overridingValues)) {
				return false;
			}
			if (tuple == null) {
				if (other.tuple != null) {
					return false;
				}
			} else if (!tuple.equals(other.tuple)) {
				return false;
			}
			return true;
		}

		@Override
		public FieldValueArray getFieldArrayValueByID(String fieldID) {
			FieldValueArray valueArray = (FieldValueArray) overridingValues.get(fieldID);
			if (valueArray != null){
				return valueArray;
			}
			return super.getFieldArrayValueByID(fieldID);
		}

		@Override
		public int getFieldCount() {
			return tuple.getFieldCount();
		}

		@Override
		public FieldValue getFieldValueByID(String fieldID) {
			FieldValue value = (FieldValue) overridingValues.get(fieldID);
			if (value != null){
				return value;
			}
			return super.getFieldValueByID(fieldID);
		}

		@Override
		public String getId() {
			return tuple.getId();
		}

		@Override
		public boolean isDirty() {
			return tuple.isDirty();
		}

		@Override
		public TupleSchema getSchema() {
			return tuple.getSchema();
		}

		@Override
		public long getTimestamp() {
			return tuple.getTimestamp();
		}

		@Override
		public String getTypeId() {
			return tuple.getTypeId();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("OverridingTuple[");
			sb.append("tuple="+this.tuple);
			sb.append(",overridingvalues="+this.overridingValues);
			sb.append("]");
			return sb.toString();
		}

	}

}
