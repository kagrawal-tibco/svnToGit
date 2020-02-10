package com.tibco.cep.dashboard.psvr.vizengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

/**
 * @author anpatil
 *
 */
public final class SeriesDataHolder {

	private Map<String, List<Tuple>> seriesCfgIDToDataMap;

	private String seriesCfgNames;

	private boolean hasAllSeries;

	public SeriesDataHolder(MALComponent component, PresentationContext pCtx) throws VisualizationException {
		this(component, null, pCtx);
	}

	public SeriesDataHolder(MALComponent component, List<String> updatedSeriesCfgNames, PresentationContext pCtx) throws VisualizationException {
		//get all the series configs in the component
		List<MALSeriesConfig> allSeriesConfigs = getAllSeriesConfigs(component.getVisualization());
		//decide if we want all series configs or not
		boolean useAll = updatedSeriesCfgNames == null || updatedSeriesCfgNames.isEmpty() == true;
		List<String> seriesNames = new ArrayList<String>(useAll == true ? allSeriesConfigs.size() : updatedSeriesCfgNames.size());
		seriesCfgIDToDataMap = new HashMap<String, List<Tuple>>();
		for (MALSeriesConfig seriesConfig : allSeriesConfigs) {
			if (useAll == true || updatedSeriesCfgNames.contains(seriesConfig.getName()) == true) {
				seriesNames.add(URIHelper.getURI(seriesConfig));
				try {
					DataSourceHandler dataSrcHandler = pCtx.getDataSourceHandler(seriesConfig);
					seriesCfgIDToDataMap.put(seriesConfig.getId(), dataSrcHandler.getData(pCtx));
				} catch (DataException e) {
					throw new VisualizationException("could not fetch data for " + URIHelper.getURI(seriesConfig), e);
				}
			}
		}
		hasAllSeries = seriesNames.size() == allSeriesConfigs.size();
		seriesCfgNames = useAll == true ? "all" : seriesNames.toString();
	}

	public SeriesDataHolder(MALComponent component, List<String> updatedSeriesCfgNames, Map<String, List<Tuple>> data) {
		//get all the series configs in the component
		List<MALSeriesConfig> allSeriesConfigs = getAllSeriesConfigs(component.getVisualization());
		//decide if we want all series configs or not
		boolean useAll = updatedSeriesCfgNames == null || updatedSeriesCfgNames.isEmpty() == true;
		List<String> seriesNames = new ArrayList<String>(useAll == true ? allSeriesConfigs.size() : updatedSeriesCfgNames.size());
		seriesCfgIDToDataMap = new HashMap<String, List<Tuple>>();
		for (MALSeriesConfig seriesConfig : allSeriesConfigs) {
			if (useAll == true || updatedSeriesCfgNames.contains(seriesConfig.getName()) == true) {
				seriesNames.add(URIHelper.getURI(seriesConfig));
				List<Tuple> seriesData = data.get(seriesConfig.getName());
				if (seriesData == null) {
					throw new IllegalArgumentException("No data found for " + seriesConfig);
				}
				seriesCfgIDToDataMap.put(seriesConfig.getId(), seriesData);
			}
		}
		hasAllSeries = seriesNames.size() == allSeriesConfigs.size();
		seriesCfgNames = useAll == true ? "all" : seriesNames.toString();
	}

	public SeriesDataHolder(String seriesID, List<Tuple> seriesData) {
		seriesCfgIDToDataMap = new HashMap<String, List<Tuple>>(1);
		List<Tuple> data = new LinkedList<Tuple>(seriesData);
		seriesCfgIDToDataMap.put(seriesID, data);
		seriesCfgNames = seriesID;
	}

	private List<MALSeriesConfig> getAllSeriesConfigs(MALVisualization[] visualizations) {
		List<MALSeriesConfig> seriesConfigList = new LinkedList<MALSeriesConfig>();
		for (int i = 0; i < visualizations.length; i++) {
			MALVisualization visualization = visualizations[i];
			MALSeriesConfig[] seriesConfigs = visualization.getSeriesConfig();
			for (int j = 0; j < seriesConfigs.length; j++) {
				seriesConfigList.add(seriesConfigs[j]);

			}
		}
		return seriesConfigList;
	}

	public Iterator<String> getSeriesCfgIds() {
		return seriesCfgIDToDataMap.keySet().iterator();
	}

	public List<Tuple> getSeriesData(String seriesCfgId) {
		List<Tuple> localDataList = seriesCfgIDToDataMap.get(seriesCfgId);
		if (localDataList == null) {
			return Collections.emptyList();
		}
		return localDataList;
	}

	public boolean contains(String seriesCfgId) {
		return seriesCfgIDToDataMap.containsKey(seriesCfgId);
	}

	public boolean hasAllSeries() {
		return hasAllSeries;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder("SeriesDataHolder[");
		buffer.append("seriesCfgNames=");
		buffer.append(seriesCfgNames);
		buffer.append("]");
		return buffer.toString();
	}
}