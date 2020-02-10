package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.runtime.CategoryValuesConsolidator;
import com.tibco.cep.dashboard.plugin.beviews.runtime.CategoryValuesConsolidator.DIFFERENCE;
import com.tibco.cep.dashboard.plugin.beviews.runtime.CategoryValuesConsolidatorCache;
import com.tibco.cep.dashboard.plugin.internal.vizengine.formatters.FormatterUtils;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.plugin.PlugIn;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTicksGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.DataConfigHandler;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandler;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandlerFactory;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DefaultDataConfigVisitor;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
class TimeSeriesChartComponentHandler extends ChartComponentHandler {

	private TimeAxisGenerator timeAxisGenerator;

	private Map<VisualizationHandler, TimeSeriesVisualizationHandlerProxy> visualizationHandlerProxies;

	TimeSeriesChartComponentHandler(Logger logger, PlugIn plugIn, Properties properties, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super(logger,plugIn,properties,exceptionHandler,messageGenerator);
		timeAxisGenerator = new TimeAxisGenerator();
		visualizationHandlerProxies = new HashMap<VisualizationHandler, TimeSeriesVisualizationHandlerProxy>();
	}

	@Override
	protected void init() {
		try {
			timeAxisGenerator.init(logger, properties);
		} catch (VisualizationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public VisualizationData getVisualizationHeaderData(MALComponent component, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException, MALException, ElementNotFoundException, DataException,
			PluginException, IOException {

		MALChartComponent chartComponent = (MALChartComponent) component;

		VisualizationData headerVisualizationData = new VisualizationData();
		headerVisualizationData.setComponentID(component.getId());

		List<Date> timeAxisValues = new LinkedList<Date>();
		/* boolean timeAxisHasBeenBucketed = */timeAxisGenerator.generateTimeAxis(chartComponent, timeAxisValues, pCtx);
		if (timeAxisValues.size() == 0) {
			return headerVisualizationData;
		}

		SeriesDataHolder seriesDataHolder = new SeriesDataHolder(chartComponent, seriesConfigNames, pCtx);
		DataRow headerRow = generateHeaderRow(chartComponent, seriesDataHolder, timeAxisValues, pCtx);

		if (headerRow != null) {
			headerVisualizationData.addDataRow(headerRow);
		}

		return headerVisualizationData;
	}

	private DataRow generateHeaderRow(MALChartComponent component, SeriesDataHolder seriesDataHolder, List<Date> timeAxisValues, PresentationContext ctx) throws VisualizationException, MALException, ElementNotFoundException, DataException, PluginException, IOException {

		Iterator<Date> timeAxisValuesIterator = timeAxisValues.iterator();

		MALTwoDimSeriesConfig seriesConfig = null;
		TupleSchema referenceTupleSchema = null;
		MALDataConfig categoryDataConfig = null;
		MALFieldMetaInfo categoryField = null;

		for (int i = 0; i < component.getVisualizationCount(); i++) {
			MALVisualization visualization = component.getVisualization(i);
			for (int j = 0; j < visualization.getSeriesConfigCount(); j++) {
				seriesConfig = (MALTwoDimSeriesConfig) visualization.getSeriesConfig(j);
				List<Tuple> seriesData = seriesDataHolder.getSeriesData(seriesConfig.getId());
				if (seriesData != null && seriesData.isEmpty() == false) {
					referenceTupleSchema = (seriesData.get(0)).getSchema();
					categoryDataConfig = seriesConfig.getCategoryDataConfig();
					categoryField = ctx.resolveFieldRef(categoryDataConfig.getExtractor().getSourceField());
					// we found a match we need to break out
					i = component.getVisualizationCount();
					break;
				} else {
					seriesConfig = null;
				}
			}
		}

		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Going to use " + URIHelper.getURI(seriesConfig) + " to generate standardized time axis for " + component);
			logger.log(Level.DEBUG, "Going to use " + referenceTupleSchema + " as reference tuple schema to generate standardized time axis for " + component);
		}

		Map<String,FieldValue> tupleValues = new HashMap<String, FieldValue>();
		//lets add all values as null
		int fieldCount = referenceTupleSchema.getFieldCount();
		for (int i = 0; i < fieldCount; i++) {
			TupleSchemaField schemaField = referenceTupleSchema.getFieldByPosition(i);
			tupleValues.put(schemaField.getFieldName(),new FieldValue(schemaField.getFieldDataType(),true));
		}

		DataRow dataRow = createCategoryDataRow(component, (MALVisualization) seriesConfig.getParent(), getVisualizationHandlerProxy((MALVisualization) seriesConfig.getParent()), ctx);

		Map<Object, Object> categoryValues = new LinkedHashMap<Object, Object>();

		while (timeAxisValuesIterator.hasNext()) {
			Date categoryValue = timeAxisValuesIterator.next();
			tupleValues.put(categoryField.getId(), new FieldValue(BuiltInTypes.DATETIME,categoryValue));
			Tuple tuple = new Tuple(referenceTupleSchema,tupleValues);
			DefaultDataConfigVisitor dataConfigVisitor = new DefaultDataConfigVisitor(FormatterUtils.getDefaultNullCategoryImpl());
			String categoryDisplayValue = DataConfigHandler.getInstance().getDisplayValue(categoryDataConfig, tuple, dataConfigVisitor, ctx);
			DataColumn dataColumn = DataConfigHandler.getInstance().getData(Long.toString(categoryValue.getTime()), null, categoryDisplayValue, categoryDataConfig, tuple, true, dataConfigVisitor, ctx);
			dataRow.addDataColumn(dataColumn);
			categoryValues.put(categoryValue, dataColumn);
		}

		//since we are in the get header data API, we are getting the latest category values, so we override the existing values
		CategoryValuesConsolidator categoryValuesConsolidator = CategoryValuesConsolidatorCache.getInstance().getConsolidator(ctx.getSecurityToken(), component, ctx);
		categoryValuesConsolidator.setCategoryValues(categoryValues);

		return dataRow;
	}

	@Override
	public VisualizationData getVisualizationData(MALComponent component, SeriesDataHolder seriesDataHolder, PresentationContext ctx) throws VisualizationException, IOException, MALException, ElementNotFoundException, DataException, PluginException {
		MALChartComponent chartComponent = (MALChartComponent) component;

		VisualizationData visualizationData = new VisualizationData();
		visualizationData.setComponentID(component.getId());

		List<Date> timeAxisValues = new LinkedList<Date>();
		/*boolean timeAxisHasBeenBucketed = */timeAxisGenerator.generateTimeAxis(chartComponent, timeAxisValues, ctx);
//		if (timeAxisValues.size() == 0) {
//			return visualizationData;
//		}

		CategoryValuesConsolidator categoryValuesConsolidator = CategoryValuesConsolidatorCache.getInstance().getConsolidator(ctx.getSecurityToken(), component, ctx);
		DIFFERENCE difference = categoryValuesConsolidator.compare(timeAxisValues);
		logger.log(Level.DEBUG, "Found " + difference + " as the difference in the category values for " + component);

		if (difference.equals(CategoryValuesConsolidator.DIFFERENCE.NONE) == false) {
			DataRow headerRow = generateHeaderRow(chartComponent, seriesDataHolder, timeAxisValues, ctx);
			if (headerRow == null) {
				return visualizationData;
			}
			visualizationData.addDataRow(headerRow);
		}

		ctx.addAttribute(TimeSeriesVisualizationHandlerProxy.PCTX_STANDARDIZED_TIMEAXIS_ATTR_KEY, timeAxisValues);
//		ctx.addAttribute(TimeSeriesVisualizationHandlerProxy.PCTX_TIMEAXIS_IS_BUCKETED_ATTR_KEY, new Boolean(timeAxisHasBeenBucketed));

		CategoryTicksGenerator dummyCategoryTicksGenerator = CategoryTicksGeneratorFactory.create(logger, component, ctx);
		MALVisualization[] visualizations = chartComponent.getVisualization();
		for (int i = 0; i < visualizations.length; i++) {
			MALVisualization visualization = visualizations[i];
			TimeSeriesVisualizationHandlerProxy visualizationHandler = getVisualizationHandlerProxy(visualization);
			DataRow[] dataRows = visualizationHandler.getVisualizationData(component, visualization, seriesDataHolder, dummyCategoryTicksGenerator, ctx);
			for (int k = 0; k < dataRows.length; k++) {
				visualizationData.addDataRow(dataRows[k]);
			}
		}
		return visualizationData;
	}

	private synchronized TimeSeriesVisualizationHandlerProxy getVisualizationHandlerProxy(MALVisualization visualization) throws VisualizationException, IOException, PluginException {
		VisualizationHandler handler = VisualizationHandlerFactory.getInstance().getHandler(visualization);
		TimeSeriesVisualizationHandlerProxy handlerProxy = visualizationHandlerProxies.get(handler);
		if (handlerProxy == null) {
			handlerProxy = new TimeSeriesVisualizationHandlerProxy((ChartVisualizationHandler) handler);
			visualizationHandlerProxies.put(handler, handlerProxy);
		}
		return handlerProxy;
	}

}