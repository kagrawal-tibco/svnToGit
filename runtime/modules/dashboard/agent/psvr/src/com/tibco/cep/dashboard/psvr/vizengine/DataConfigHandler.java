package com.tibco.cep.dashboard.psvr.vizengine;

import java.util.Date;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ColumnConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DataConfigVisitor;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DataFormatHandler;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DataFormatHandlerFactory;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.OutputFieldValueProcessor;

/**
 * @author apatil
 * 
 */
public class DataConfigHandler extends EngineHandler {

	private static DataConfigHandler instance = null;

	public static final synchronized DataConfigHandler getInstance() {
		if (instance == null) {
			instance = new DataConfigHandler();
		}
		return instance;
	}

	private DataConfigHandler() {
	}
	
	public ColumnConfig getColumnConfig(String id, MALSeriesConfig seriesConfig, MALDataConfig dataConfig, PresentationContext ctx) throws VisualizationException, PluginException {
		return getColumnConfig(id, seriesConfig, dataConfig, null, ctx);
	}

	public ColumnConfig getColumnConfig(String id, MALSeriesConfig seriesConfig, MALDataConfig dataConfig, DataConfigVisitor visitor, PresentationContext ctx) throws VisualizationException, PluginException {
		MALDataFormat formatter = dataConfig.getFormatter();
		DataFormatHandler dataFormatHandler = DataFormatHandlerFactory.getInstance().getHandler(formatter);
		ColumnConfig columnConfig = dataFormatHandler.getColumnConfig(id, formatter, visitor, ctx);
		columnConfig.setId(id);
		return columnConfig;
	}

	public DataColumn getData(String id, String templateid, MALDataConfig dataConfig, Tuple tuple, boolean processCorePropsOnly, DataConfigVisitor visitor, PresentationContext ctx) throws MALException,
			ElementNotFoundException, VisualizationException {
		MALFieldMetaInfo sourceField = ctx.resolveFieldRef(dataConfig.getExtractor().getSourceField());
		MALDataFormat formatter = dataConfig.getFormatter();
		DataFormatHandler dataFormatHandler = getFormatHandler(formatter);
		FieldValue fieldValue = tuple.getFieldValueByName(sourceField.getName());

		// if the field value is not null and we have a dataformathandler
		// then we delegate the job to dataformathandler
		if (/*fieldValue.isNull() == false &&*/ dataFormatHandler != null) {
			return dataFormatHandler.getData(id, templateid, formatter, tuple, processCorePropsOnly, visitor, ctx);
		}

		// we either don't have a dataformathandler or the field value is null
		// in any case we need the values

		// we always get the raw value for the 'value' in the data column
		String value = getProcessingValue(fieldValue);
		// we get the default display value
		String displayValue = getDisplayValue(fieldValue, ctx);
		// the tooltip is the default display value
		String tooltip = displayValue;
		// if we have the processor, then we use it
		if (visitor.getFieldValueProcessor() != null) {
			Map<OutputFieldValueProcessor.KEY, Object> valueMap = visitor.getFieldValueProcessor().process(sourceField.getName(), fieldValue);
			if (valueMap.containsKey(OutputFieldValueProcessor.KEY.DISPLAY) == true) {
				displayValue = valueMap.get(OutputFieldValueProcessor.KEY.DISPLAY).toString();
			}
			if (valueMap.containsKey(OutputFieldValueProcessor.KEY.TOOLTIP) == true) {
				tooltip = (String) valueMap.get(OutputFieldValueProcessor.KEY.TOOLTIP).toString();
			}
		}

		DataColumn dataColumn = new DataColumn();
		dataColumn.setId(id);
		if (templateid != null) {
			dataColumn.setTemplateID(templateid);
		}
		dataColumn.setDisplayValue(displayValue);
		dataColumn.setValue(value);
		dataColumn.setTooltip(tooltip);
		return dataColumn;

	}

	public String getDisplayValue(MALDataConfig dataConfig, Tuple tuple, DataConfigVisitor visitor, PresentationContext ctx) throws MALException, ElementNotFoundException, VisualizationException {
		MALFieldMetaInfo sourceField = ctx.resolveFieldRef(dataConfig.getExtractor().getSourceField());
		MALDataFormat formatter = dataConfig.getFormatter();
		DataFormatHandler dataFormatHandler = getFormatHandler(formatter);
		FieldValue fieldValue = tuple.getFieldValueByName(sourceField.getName());

		// if the field value is not null and we have a dataformathandler
		// then we delegate the job to dataformathandler
		if (/*fieldValue.isNull() == false && */dataFormatHandler != null) {
			return dataFormatHandler.getDisplayValue(formatter, tuple, visitor, ctx);
		}

		// we either don't have a dataformathandler or the field value is null
		// in any case we need the values

		// we get the default display value
		String displayValue = getDisplayValue(fieldValue, ctx);

		// if we have the processor, then we use it
		if (visitor.getFieldValueProcessor() != null) {
			Map<OutputFieldValueProcessor.KEY, Object> valueMap = visitor.getFieldValueProcessor().process(sourceField.getName(), fieldValue);
			if (valueMap.containsKey(OutputFieldValueProcessor.KEY.DISPLAY) == true) {
				displayValue = valueMap.get(OutputFieldValueProcessor.KEY.DISPLAY).toString();
			}
		}
		return displayValue;
	}

	public DataColumn getData(String id, String templateid, String displayValue, MALDataConfig dataConfig, Tuple tuple, boolean processCorePropsOnly, DataConfigVisitor visitor, PresentationContext ctx)
			throws MALException, ElementNotFoundException, VisualizationException {
		MALFieldMetaInfo sourceField = ctx.resolveFieldRef(dataConfig.getExtractor().getSourceField());
		MALDataFormat formatter = dataConfig.getFormatter();
		DataFormatHandler dataFormatHandler = getFormatHandler(formatter);
		FieldValue fieldValue = tuple.getFieldValueByName(sourceField.getName());

		// if the fieldvalue is not null and we have a dataformathandler
		// then we delegate the job to dataformathandler
		if (/*fieldValue.isNull() == false &&*/ dataFormatHandler != null) {
			return dataFormatHandler.getData(id, templateid, formatter, displayValue, tuple, processCorePropsOnly, visitor, ctx);
		}

		// we either don't have a dataformathandler or the fieldvalue is null
		// in any case we need the values

		// we always get the raw value for the 'value' in the datacolumn
		String value = getProcessingValue(fieldValue);
		// the tool tip is the default display value
		String tooltip = displayValue;

		// if we have the processor, then we use it
		if (visitor.getFieldValueProcessor() != null) {
			Map<OutputFieldValueProcessor.KEY, Object> valueMap = visitor.getFieldValueProcessor().process(sourceField.getName(), fieldValue);
			if (valueMap.containsKey(OutputFieldValueProcessor.KEY.DISPLAY) == true) {
				displayValue = valueMap.get(OutputFieldValueProcessor.KEY.DISPLAY).toString();
			}
		}

		DataColumn dataColumn = new DataColumn();
		dataColumn.setId(id);
		if (templateid != null) {
			dataColumn.setTemplateID(templateid);
		}

		dataColumn.setDisplayValue(displayValue);
		dataColumn.setValue(value);
		dataColumn.setTooltip(tooltip);
		return dataColumn;

	}

	private String getDisplayValue(FieldValue fieldValue, PresentationContext ctx) {
		if (fieldValue.isNull() == true) {
			return "null";
		}
		String displayValue = fieldValue.toString();
		if (fieldValue.getDataType() == BuiltInTypes.DATETIME && ctx.getCustomDateFormat() != null) {
			displayValue = ctx.getCustomDateFormat().format(fieldValue.getValue());
		}
		return displayValue;
	}

	private String getProcessingValue(FieldValue fieldValue) {
		if (fieldValue.getDataType() == BuiltInTypes.DATETIME) {
			Date fldValue = (Date) fieldValue.getValue();
			return Long.toString(fldValue.getTime());
		}
		return fieldValue.toString();
	}

	protected DataFormatHandler getFormatHandler(MALDataFormat formatter) throws VisualizationException {
		if (formatter == null) {
			return null;
		}
		// if (formatter.getDisplayFormat() == null) {
		// return null;
		// }
		try {
			return DataFormatHandlerFactory.getInstance().getHandler(formatter);
		} catch (PluginException e) {
			throw new VisualizationException("could not create a format handler for " + formatter, e);
		}
	}

	@Override
	protected void init() {
	}

	@Override
	protected void shutdown() {
	}
}