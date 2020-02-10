package com.tibco.cep.dashboard.psvr.vizengine.formatters;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.config.ConfigurationProperties;
import com.tibco.cep.dashboard.psvr.alerts.AlertActionable;
import com.tibco.cep.dashboard.psvr.alerts.AlertEvaluator;
import com.tibco.cep.dashboard.psvr.alerts.EvalException;
import com.tibco.cep.dashboard.psvr.alerts.ExecException;
import com.tibco.cep.dashboard.psvr.alerts.VisualAlertResult;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSearchPageManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALFormatStyle;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ColumnConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ColumnType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.FontStyle;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.EngineHandler;
import com.tibco.cep.dashboard.psvr.vizengine.LinkGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.LinkGeneratorFactory;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.OutputFieldValueProcessor.KEY;
import com.tibco.cep.dashboard.security.SecurityToken;

/**
 * @author anpatil
 *
 */
public abstract class DataFormatHandler extends EngineHandler {

	private String baseURI;

	@Override
	protected void init() {
		baseURI = String.valueOf(ConfigurationProperties.PULL_REQUEST_BASE_URL.getValue(properties));
	}

	public abstract ColumnConfig getColumnConfig(String id, MALDataFormat dataFormat, DataConfigVisitor visitor, PresentationContext ctx);

	public abstract String getDisplayValue(MALDataFormat dataFormat, Tuple tuple, DataConfigVisitor visitor, PresentationContext ctx) throws VisualizationException;

	public DataColumn getData(String id, String templateid, MALDataFormat dataFormat, Tuple tuple, boolean processCorePropsOnly, DataConfigVisitor visitor, PresentationContext ctx) throws VisualizationException,
			MALException, ElementNotFoundException {
		return getData(id, templateid, dataFormat, null, tuple, processCorePropsOnly, visitor, ctx);
	}

	public abstract DataColumn getData(String id, String templateid, MALDataFormat dataFormat, String displayValue, Tuple tuple, boolean processCorePropsOnly, DataConfigVisitor visitor, PresentationContext ctx)
			throws VisualizationException, MALException, ElementNotFoundException;

	protected ColumnConfig createDataColumnConfig(String id, MALDataFormat dataFormat, ColumnType columnType, String fontColor) {
		ColumnConfig column = new ColumnConfig();
		// INFO borderconfig and colorconfig is to be set by the final consumer
		// of the visualization aka private apis and logic handlers
		MALFormatStyle formatStyle = dataFormat.getFormatStyle();
		// INFO Check later is formatStyle being optional is OK in columnconfig
		if (formatStyle != null) {
			column.setFontColor(ColorMap.toHex(fontColor));
			column.setFontSize(formatStyle.getFontSize());
			column.setFontStyle(FontStyle.valueOf(formatStyle.getFontStyle().toString()));
		}
		column.setId(id);
		column.setType(columnType);
		return column;
	}

	protected TypeSpecificAttribute createTypeSpecificAttribute(String name, String value) {
		TypeSpecificAttribute attribute = new TypeSpecificAttribute();
		attribute.setName(name);
		attribute.setContent(value);
		return attribute;
	}

	protected Object[] getProcessedOutputFieldDisplayValues(Tuple tuple, DataSourceHandler dataSrcHandler, OutputFieldValueProcessor processor, PresentationContext ctx) {
		return getProcessedOutputFldValues(tuple, dataSrcHandler, processor, OutputFieldValueProcessor.KEY.DISPLAY, ctx);
	}

	protected Object[] getProcessedOutputFieldToolTipValues(Tuple tuple, DataSourceHandler dataSrcHandler, OutputFieldValueProcessor processor, PresentationContext ctx) {
		return getProcessedOutputFldValues(tuple, dataSrcHandler, processor, OutputFieldValueProcessor.KEY.TOOLTIP, ctx);
	}

	private Object[] getProcessedOutputFldValues(Tuple tuple, DataSourceHandler dataSrcHandler, OutputFieldValueProcessor processor, KEY key, PresentationContext ctx) {
		if (processor == null) {
			return null;
		}
		TupleSchema schema = tuple.getSchema();
		int count = tuple.getFieldCount();
		Object[] values = new Object[count];
		for (int i = 0; i < count; i++) {
			values[i] = tuple.getFieldValueByPosition(i);
			values[i] = processor.process(schema.getFieldNameByPosition(i), (FieldValue) values[i]).get(key);
		}
		return values;
	}

	protected String generateLink(MALSeriesConfig seriesConfig, Tuple tuple, PresentationContext pCtx) throws VisualizationException {
		if (pCtx.getViewsConfigHelper().getPagesByType(MALSearchPageManager.DEFINITION_TYPE).length == 0) {
			return null;
		}
		SecurityToken token = pCtx.getSecurityToken();
		String componentId = seriesConfig.getParent().getParent().getId();
		String seriesConfigID = seriesConfig.getId();
		String tupleId = String.valueOf(tuple.getId());
		return LinkGeneratorFactory.getInstance().generateDrillLink(baseURI, token, componentId, seriesConfigID, tupleId).toString();
	}

	protected String generateURLRefParams(DataSourceHandler dataSourceHandler, MALFieldMetaInfo actualOutputField, Tuple tuple, PresentationContext pCtx) throws VisualizationException {
		if (pCtx.generateAdditionalOutputs() == false) {
			return null;
		}
		LinkGenerator linkGenerator = new LinkGenerator();
		linkGenerator.addParameter("tupid", String.valueOf(tuple.getId()));
		String seriesCfgID = (String) pCtx.getAttribute(ActionConfigGenerator.CURRENTSERIES_ID_DYN_PARAM);
		if (seriesCfgID != null && seriesCfgID.trim().length() != 0) {
			linkGenerator.addParameter("seriescfgid", seriesCfgID);
		}
		return linkGenerator.toString();
	}

	protected MALSeriesConfig getSeriesConfig(MALDataConfig dataConfig) {
		MALSeriesConfig seriesConfig = (MALSeriesConfig) dataConfig.getParent();
		if (seriesConfig == null) {
			throw new IllegalStateException("Inconsistent component hierarchy, " + dataConfig + " does not have a parent");
		}
		return seriesConfig;
	}

	protected MALDataConfig getDataConfig(MALDataFormat dataFormat) {
		MALDataConfig dataConfig = (MALDataConfig) dataFormat.getParent();
		if (dataConfig == null) {
			throw new IllegalStateException("Inconsistent component hierarchy, " + dataFormat + " does not have a parent");
		}
		return dataConfig;
	}

	protected MALFieldMetaInfo getSourceField(MALDataConfig dataConfig, PresentationContext ctx) throws VisualizationException {
		try {
			// get source field
			return ctx.resolveFieldRef(dataConfig.getExtractor().getSourceField());
		} catch (MALException e) {
			throw new VisualizationException(getMessage("data.format.fieldresolution.failure", ctx.getLocale(), ctx.getMessageGeneratorArgs(e, URIHelper.getURI(dataConfig.getExtractor()))), e);
		} catch (ElementNotFoundException e) {
			throw new VisualizationException(getMessage("data.format.fieldresolution.notfound", ctx.getLocale(), ctx.getMessageGeneratorArgs(e, URIHelper.getURI(dataConfig.getExtractor()))), e);
		}
	}

	@Override
	protected void shutdown() throws NonFatalException {
		// do nothing
	}

	protected String getValue(VisualAlertResult alertResult, String key, String defaultValue) {
		String value = null;
		if (alertResult != null) {
			value = alertResult.getValue(key);
		}
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	protected VisualAlertResult getVisualAlertResults(MALDataFormat dataFormat, AlertEvaluator alertEvaluator, MALFieldMetaInfo field, Tuple tuple, PresentationContext ctx) throws VisualizationException {
		try {
			if (alertEvaluator == null) {
				return null;
			}
			AlertActionable[] results = alertEvaluator.evaluate(field, tuple);
			if (results == null) {
				return null;
			}
			return (VisualAlertResult) results[0].execute(ctx);
		} catch (EvalException e) {
			throw new VisualizationException("could not evaluate visual rules for " + URIHelper.getURI(dataFormat), e);
		} catch (ExecException e) {
			throw new VisualizationException("could not execute visual rules for " + URIHelper.getURI(dataFormat), e);
		}
	}
}
