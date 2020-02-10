package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationSeriesContainer;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ChartRenderingType;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTicksGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;

/**
 * @author apatil
 *
 */
class TimeSeriesVisualizationHandlerProxy extends ChartVisualizationHandler {

	static final String PCTX_STANDARDIZED_TIMEAXIS_ATTR_KEY = TimeAxisGenerator.class.getName();
//
//    static final String PCTX_TIMEAXIS_IS_BUCKETED_ATTR_KEY = "timeaxisisbucketed";
//
//    private static final String PCTX_REVERSE_LOOKUPTABLE_ATTR_KEY = "reverselookuptable";

	private ChartVisualizationHandler chartVisualizationHandler;

	TimeSeriesVisualizationHandlerProxy(ChartVisualizationHandler chartVisualizationHandler) {
		this.chartVisualizationHandler = chartVisualizationHandler;
		TimeSeriesBucketedData.init(this.chartVisualizationHandler.getProperties());
	}

	@Override
	public String getHeaderRowTemplateID(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext ctx) {
		return chartVisualizationHandler.getHeaderRowTemplateID(component, visualization, seriesConfig, ctx);
	}

	@Override
	public String getHeaderRowVisualType(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext ctx) {
		return chartVisualizationHandler.getHeaderRowVisualType(component, visualization, seriesConfig, ctx);
	}

	@Override
	public MALSeriesConfig[] getSeriesConfigs(MALVisualization visualization, List<String> seriesConfigNames) throws VisualizationException {
		return chartVisualizationHandler.getSeriesConfigs(visualization, seriesConfigNames);
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataRow[] getVisualizationData(MALComponent component, MALVisualization visualization, SeriesDataHolder seriesDataHolder, CategoryTicksGenerator categoryTicksGenerator, PresentationContext ctx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException, DataException, PluginException {
		LinkedList<DataRow> dataRowList = new LinkedList<DataRow>();
		//we should use a treemap , since we know that the data column is time in milliseconds
		Map<String, DataColumn> bucketedDataColsMap = new TreeMap<String, DataColumn>();
		//get the standard time axis
		List<Date> standardizedTimeAxis = (List<Date>) ctx.getAttribute(PCTX_STANDARDIZED_TIMEAXIS_ATTR_KEY);
		//generate data rows for each series in the visualization
		for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
			MALTwoDimSeriesConfig castedSeriesConfig = (MALTwoDimSeriesConfig) seriesConfig;
			DataRow seriesDataRow = null;
			List<Tuple> data = seriesDataHolder.getSeriesData(seriesConfig.getId());
			if (data != null && data.size() > 0) {
				bucketedDataColsMap.clear();
				//bucket the data based on the time axis
				TimeSeriesBucketedData bucketedData = TimeSeriesBucketedData.getTimeSeriesBucketedData(this.chartVisualizationHandler.getLogger(), castedSeriesConfig, standardizedTimeAxis, data, ctx);
				// first we handle perfect match
				Map<Date, Tuple> exactMatchSet = bucketedData.getExactMatch();
				if (exactMatchSet != null) {
					seriesDataRow = getVisualizationData(component, visualization, seriesConfig, exactMatchSet, categoryTicksGenerator, ctx)[0];
					DataColumn[] dataColumns = seriesDataRow.getDataColumn();
					for (int k = 0; k < dataColumns.length; k++) {
						DataColumn dataColumn = dataColumns[k];
						//by now the datacolumn has the id set to time in milliseconds
						bucketedDataColsMap.put(dataColumn.getId(), seriesDataRow.getDataColumn(k));
					}
					seriesDataRow.clearDataColumn();
				}
				List<TIME_SERIES_BUCKET_KEY> setKeys = bucketedData.getSetKeys();
				boolean prepForMultiValues = true;
				if (setKeys.size() == 1) {
					//we have only a single value to be shown in the bucket
					prepForMultiValues = false;
				}
				Iterator<TIME_SERIES_BUCKET_KEY> setKeysIterator = setKeys.iterator();
				while (setKeysIterator.hasNext()) {
					TIME_SERIES_BUCKET_KEY setKey = setKeysIterator.next();
					Map<Date, Tuple> setData = bucketedData.getSet(setKey);
					if (setData != null) {
						DataRow dataRow = getVisualizationData(component, visualization, seriesConfig, setData, categoryTicksGenerator, ctx)[0];
						DataColumn[] dataColumns = dataRow.getDataColumn();
						for (int k = 0; k < dataColumns.length; k++) {
							DataColumn dataColumn = dataColumns[k];
							DataColumn masterDataCol = bucketedDataColsMap.get(dataColumn.getId());
							if (masterDataCol == null) {
								if (prepForMultiValues == true) {
									escape(dataColumn);
								}
								bucketedDataColsMap.put(dataColumn.getId(), dataColumn);
							} else {
								mergeDataColumns(masterDataCol, dataColumn);
							}
						}
						if (seriesDataRow == null) {
							seriesDataRow = dataRow;
							seriesDataRow.clearDataColumn();
						}
					}
				}
				DataColumn[] dataColumns = bucketedDataColsMap.values().toArray(new DataColumn[bucketedDataColsMap.size()]);
				seriesDataRow.setDataColumn(dataColumns);
				dataRowList.add(seriesDataRow);
			}
		}
		return dataRowList.toArray(new DataRow[dataRowList.size()]);
	}

	private DataRow[] getVisualizationData(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, Map<Date, Tuple> bucketedData, CategoryTicksGenerator categoryTicksGenerator, PresentationContext ctx) throws VisualizationException,
			IOException, MALException, ElementNotFoundException, DataException, PluginException {
		//get the data row from the actual visualization handler
		SeriesDataHolder dataHolder = new SeriesDataHolder(seriesConfig.getId(), new ArrayList<Tuple>(bucketedData.values()));
		DataRow[] visualizationData = this.chartVisualizationHandler.getVisualizationData(component, visualization, dataHolder, categoryTicksGenerator, ctx);
		//update the column ids to match the time series axis [which is the key of the bucketedData]
		if (visualizationData != null && visualizationData.length != 0) {
			ArrayList<Date> keyRefDates = new ArrayList<Date>(bucketedData.keySet());
			DataRow seriesVizDataRow = visualizationData[0];
			DataColumn[] dataColumns = seriesVizDataRow.getDataColumn();
			for (int i = 0; i < dataColumns.length; i++) {
				DataColumn dataColumn = dataColumns[i];
				Date refDate = keyRefDates.get(i);
				dataColumn.setId(Long.toString(refDate.getTime()));
			}
		}

		return visualizationData;
	}

	private void mergeDataColumns(DataColumn masterDataCol, DataColumn slaveDataCol) {
		// displayvalue
		masterDataCol.setDisplayValue(merge(masterDataCol.getDisplayValue(), slaveDataCol.getDisplayValue()));
		// value
		masterDataCol.setValue(merge(masterDataCol.getValue(), slaveDataCol.getValue()));
		// baseColor
		masterDataCol.setBaseColor(merge(masterDataCol.getBaseColor(), slaveDataCol.getBaseColor()));
		// highlightcolor
		masterDataCol.setHighLightColor(merge(masterDataCol.getHighLightColor(), slaveDataCol.getHighLightColor()));
		// tooltip
		masterDataCol.setTooltip(merge(masterDataCol.getTooltip(), slaveDataCol.getTooltip()));
		// link
		masterDataCol.setLink(merge(masterDataCol.getLink(), slaveDataCol.getLink()));
		// typespecificattributes
		TypeSpecificAttribute[] masterTypeSpecificAttributes = masterDataCol.getTypeSpecificAttribute();
		for (int j = 0; j < masterTypeSpecificAttributes.length; j++) {
			TypeSpecificAttribute masterTypeSpecificAttribute = masterTypeSpecificAttributes[j];
			TypeSpecificAttribute slaveTypeSpecificAttribute = slaveDataCol.getTypeSpecificAttribute(j);
			masterTypeSpecificAttribute.setContent(merge(masterTypeSpecificAttribute.getContent(), slaveTypeSpecificAttribute.getContent()));
		}
	}

	private void escape(DataColumn masterDataCol) {
		// displayvalue
		masterDataCol.setDisplayValue(escapeStr(masterDataCol.getDisplayValue()));
		// value
		masterDataCol.setValue(escapeStr(masterDataCol.getValue()));
		// baseColor
		masterDataCol.setBaseColor(escapeStr(masterDataCol.getBaseColor()));
		// highlightcolor
		masterDataCol.setHighLightColor(escapeStr(masterDataCol.getHighLightColor()));
		// tooltip
		masterDataCol.setTooltip(escapeStr(masterDataCol.getTooltip()));
		// link
		masterDataCol.setLink(escapeStr(masterDataCol.getLink()));
		// typespecificattributes
		TypeSpecificAttribute[] masterTypeSpecificAttributes = masterDataCol.getTypeSpecificAttribute();
		for (int j = 0; j < masterTypeSpecificAttributes.length; j++) {
			TypeSpecificAttribute masterTypeSpecificAttribute = masterTypeSpecificAttributes[j];
			masterTypeSpecificAttribute.setContent(escapeStr(masterTypeSpecificAttribute.getContent()));
		}
	}

	private String escapeStr(String str) {
		String escapedStr = str;
		if (escapedStr == null) {
			escapedStr = "";
		} else {
			escapedStr = str.replace("\"", "\\\"");
		}
		return "\"" + escapedStr + "\"";
	}

	private String merge(String masterValue, String slaveValue) {
		StringBuilder writer = new StringBuilder();
		writer.append(masterValue);
		writer.append(",");
		writer.append(escapeStr(slaveValue));
		return writer.toString();
	}

	@Override
	public DataRow[] getVisualizationHeaderData(MALComponent component, MALVisualization visualization, SeriesDataHolder seriesDataHolder, CategoryTicksGenerator categoryTicksGenerator, PresentationContext ctx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException {
		throw new UnsupportedOperationException();
	}

	@Override
	public VisualizationSeriesContainer[] getVisualizationSeriesContainers(MALComponent component, MALVisualization visualization, List<String> seriesConfigNames, PresentationContext ctx) throws VisualizationException,
			IOException, MALException, PluginException {
		throw new UnsupportedOperationException();
	}

	@Override
	public VisualizationSeriesContainer[] getVisualizationSeriesContainers(MALComponent component, MALVisualization visualization, PresentationContext ctx) throws VisualizationException, IOException, MALException,
			PluginException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected ChartRenderingType getChartRenderingType(MALVisualization visualization) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected TypeSpecificAttribute[] getTypeSpecificAttributes(MALComponent component, MALVisualization visualization, PresentationContext ctx) throws MALException {
		throw new UnsupportedOperationException();
	}
}
