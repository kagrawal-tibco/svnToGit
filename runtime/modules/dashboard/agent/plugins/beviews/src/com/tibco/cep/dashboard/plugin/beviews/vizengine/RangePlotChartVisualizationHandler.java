package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.runtime.MasterCategorySet;
import com.tibco.cep.dashboard.plugin.beviews.runtime.MasterCategorySetCache;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALRangePlotChartSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALRangePlotChartVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.types.OrientationEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.RangePlotChartSeriesTypeEnum;
import com.tibco.cep.dashboard.psvr.ogl.model.ChartTypeConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.SeriesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationSeriesContainer;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ChartRenderingType;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryAxisMerger;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTick;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTicksGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DataConfigVisitor;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * @author apatil
 *
 */
public class RangePlotChartVisualizationHandler extends ChartVisualizationHandler {

	private static final String KEY_MASTER_ADD_ALL = RangePlotChartVisualizationHandler.class.getName()+"_master_add_all";

	private static final String[] SERIES_NAMES = new String[] { "minvalue", "currentvalue", "maxvalue" };

	private boolean applyCategoryAxisIntersection;

	@Override
	protected void init() {
		applyCategoryAxisIntersection = (Boolean) BEViewsProperties.RANGE_CHART_INTERSECT_DATA_ENABLED.getValue(properties);
	}

	protected ChartRenderingType getChartRenderingType(MALVisualization visualization) {
		MALRangePlotChartVisualization rangeChartVisualization = (MALRangePlotChartVisualization) visualization;
		if (rangeChartVisualization.getOrientation().getType() == OrientationEnum.HORIZONTAL_TYPE) {
			return ChartRenderingType.HORIZONTALRANGE;
		}
		return ChartRenderingType.VERTICALRANGE;
	}

	protected TypeSpecificAttribute[] getTypeSpecificAttributes(MALComponent component, MALVisualization visualization, PresentationContext ctx) {
		MALRangePlotChartVisualization rangeChartVisualization = (MALRangePlotChartVisualization) visualization;
		List<TypeSpecificAttribute> attrs = new LinkedList<TypeSpecificAttribute>();
		// plotshape
		String attrValue = rangeChartVisualization.getPlotShape().toString();
		attrs.add(createTypeSpecificAttribute("plotshape", attrValue));
		// plotshapedimension
		attrValue = Integer.toString(rangeChartVisualization.getPlotShapeDimension());
		attrs.add(createTypeSpecificAttribute("plotshapedimension", attrValue));
		// whiskerthickness
		attrValue = Integer.toString(rangeChartVisualization.getWhiskerThickness());
		attrs.add(createTypeSpecificAttribute("whiskerthickness", attrValue));
		// whiskerwidth
		attrValue = Integer.toString(rangeChartVisualization.getWhiskerWidth());
		attrs.add(createTypeSpecificAttribute("whiskerwidth", attrValue));
		// PATCH barwidth should be replaced by whiskerwidth
		attrs.add(createTypeSpecificAttribute("barwidth", attrValue));
		return attrs.toArray(new TypeSpecificAttribute[attrs.size()]);
	}

	public MALSeriesConfig[] getSeriesConfigs(MALVisualization visualization, List<String> seriesConfigNames) throws VisualizationException {
		MALSeriesConfig[] actualSeriesCfgs = visualization.getSeriesConfig();
		if (actualSeriesCfgs.length == 1) {
			// we have only one series config, lets check if its type is CURRENT
			MALRangePlotChartSeriesConfig actualConfig = (MALRangePlotChartSeriesConfig) actualSeriesCfgs[0];
			if (actualConfig.getType().getType() != RangePlotChartSeriesTypeEnum.CURRENT_TYPE) {
				throw new VisualizationException(visualization + " contains a single " + actualConfig + " which is not " + RangePlotChartSeriesTypeEnum.CURRENT_TYPE);
			}
		}
		// we have more then one series config
		HashMap<RangePlotChartSeriesTypeEnum, MALRangePlotChartSeriesConfig> rangeTypeToSeriesConfigMap = new LinkedHashMap<RangePlotChartSeriesTypeEnum, MALRangePlotChartSeriesConfig>(3);
		for (int i = 0; i < actualSeriesCfgs.length; i++) {
			MALRangePlotChartSeriesConfig actualConfig = (MALRangePlotChartSeriesConfig) actualSeriesCfgs[i];
			if (actualConfig.getType().getType() == RangePlotChartSeriesTypeEnum.MIN_TYPE) {
				if (rangeTypeToSeriesConfigMap.containsKey(RangePlotChartSeriesTypeEnum.MIN) == false) {
					rangeTypeToSeriesConfigMap.put(RangePlotChartSeriesTypeEnum.MIN, actualConfig);
				} else {
					logger.log(Level.WARN, "RangePlotChartVisualization[id=" + visualization.getId() + ",name=" + visualization.getName() + "] has more then one " + actualConfig.getType() + " series config, skipping "
							+ actualConfig + "...");
				}
			} else if (actualConfig.getType().getType() == RangePlotChartSeriesTypeEnum.CURRENT_TYPE) {
				if (rangeTypeToSeriesConfigMap.containsKey(RangePlotChartSeriesTypeEnum.CURRENT) == false) {
					rangeTypeToSeriesConfigMap.put(RangePlotChartSeriesTypeEnum.CURRENT, actualConfig);
				} else {
					logger.log(Level.WARN, "RangePlotChartVisualization[id=" + visualization.getId() + ",name=" + visualization.getName() + "] has more then one " + actualConfig.getType() + " series config, skipping "
							+ actualConfig + "...");
				}
			} else if (actualConfig.getType().getType() == RangePlotChartSeriesTypeEnum.MAX_TYPE) {
				if (rangeTypeToSeriesConfigMap.containsKey(RangePlotChartSeriesTypeEnum.MAX) == false) {
					rangeTypeToSeriesConfigMap.put(RangePlotChartSeriesTypeEnum.MAX, actualConfig);
				} else {
					logger.log(Level.WARN, "RangePlotChartVisualization[id=" + visualization.getId() + ",name=" + visualization.getName() + "] has more then one " + actualConfig.getType() + " series config, skipping "
							+ actualConfig + "...");
				}
			}
		}
		return rangeTypeToSeriesConfigMap.values().toArray(new MALRangePlotChartSeriesConfig[rangeTypeToSeriesConfigMap.size()]);
	}

	public VisualizationSeriesContainer[] getVisualizationSeriesContainers(MALComponent component, MALVisualization visualization, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException,
			MALException, IOException, PluginException, ElementNotFoundException {
		VisualizationSeriesContainer[] containers = super.getVisualizationSeriesContainers(component, visualization, seriesConfigNames, pCtx);
		ChartTypeConfig chartTypeConfig = (ChartTypeConfig) containers[0];
		SeriesConfig[] seriesConfig = chartTypeConfig.getSeriesConfig();
		if (seriesConfig.length == 1) {
			// we have only one series config, lets name it CURRENT
			seriesConfig[0].setName(SERIES_NAMES[1]);
		} else {
			for (int i = 0; i < seriesConfig.length; i++) {
				SeriesConfig config = seriesConfig[i];
				config.setName(SERIES_NAMES[i]);
			}
		}
		return containers;
	}

	@Override
	protected CategoryAxisMerger getCategoryAxisMerger(MALComponent component, MALVisualization visualization) {
		if (applyCategoryAxisIntersection == true) {
			return new RangeChartIntersectingCategoryAxisMerger(visualization);
		}
		return null;
	}

	public DataRow[] getVisualizationData(MALComponent component, MALVisualization visualization, SeriesDataHolder seriesDataHolder, CategoryTicksGenerator categoryTicksGenerator, PresentationContext pCtx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException, DataException, PluginException {
		try {
			if (applyCategoryAxisIntersection == true) {
				//force the category axis generator to compute the entire set of ticks since we could be applying an intersect
				SeriesDataHolder completeSeriesDataHolder = new SeriesDataHolder(component, pCtx);
				//create the data config visitor
				DataConfigVisitor categoryDataConfigVisitor = getDefaultCategoryDataConfigVisitor();
				//create the category axis merger
				CategoryAxisMerger categoryAxisMerger = getCategoryAxisMerger(component, visualization);
				//create a map of ticks to data columns
				for (MALSeriesConfig seriesConfig : getSeriesConfigs(visualization, null)) {
					//get the series data
					List<Tuple> data = completeSeriesDataHolder.getSeriesData(seriesConfig.getId());
					//generate the category axis for this series
					categoryTicksGenerator.generate(logger, (MALTwoDimSeriesConfig) seriesConfig, categoryDataConfigVisitor, data, categoryAxisMerger, pCtx);
				}
				pCtx.addAttribute(KEY_MASTER_ADD_ALL, Boolean.FALSE);
				MasterCategorySet masterCategorySet = MasterCategorySetCache.getInstance().getMasterCategorySet(pCtx.getSecurityToken(), component, pCtx);
				if (masterCategorySet.computeDifference(getLogger(), categoryTicksGenerator.getTicks()) != MasterCategorySet.DIFFERENCE.NONE) {
					//we have a change in the category axis ticks, since we are dealing with intersection mode, we may have not send the proper updates
					//so we forcefully send the complete set
					seriesDataHolder = completeSeriesDataHolder;
				}
			}
			//not generate the data rows for the requested series
			DataRow[] dataRows = super.getVisualizationData(component, visualization, seriesDataHolder, categoryTicksGenerator, pCtx);
			Iterator<String> seriesCfgIDs = seriesDataHolder.getSeriesCfgIds();
			while (seriesCfgIDs.hasNext()) {
				String seriesCfgID = (String) seriesCfgIDs.next();
				if (seriesDataHolder.getSeriesData(seriesCfgID).size() > 0) {
					MALRangePlotChartSeriesConfig seriesCfg = (MALRangePlotChartSeriesConfig) getMatchingSeriesConfig(visualization.getSeriesConfig(), seriesCfgID);
					String type = seriesCfg.getType().toString();
					DataRow dataRow = getMatchingDataRow(dataRows, seriesCfgID);
					if (dataRow != null) {
						dataRow.setTemplateID(type + "value");
					}
				}
			}
			return dataRows;
		} finally {
			if (applyCategoryAxisIntersection == true) {
				pCtx.removeAttribute(KEY_MASTER_ADD_ALL);
			}
		}
	}

	private MALSeriesConfig getMatchingSeriesConfig(MALSeriesConfig[] seriesConfig, String seriesCfgID) throws VisualizationException {
		for (int i = 0; i < seriesConfig.length; i++) {
			MALSeriesConfig config = seriesConfig[i];
			if (config.getId().equals(seriesCfgID) == true) {
				return config;
			}
		}
		throw new VisualizationException("could not find a series config with id[" + seriesCfgID + "]");
	}

	private DataRow getMatchingDataRow(DataRow[] dataRows, String id) throws VisualizationException {
		for (int i = 0; i < dataRows.length; i++) {
			DataRow dataRow = dataRows[i];
			if (dataRow.getTemplateID().equals(id) == true) {
				return dataRow;
			}
		}
		if (dataRows.length == 0) {
			return null;
		}
		throw new VisualizationException("could not find a data row matching the template id[" + id + "]");
	}

	class RangeChartIntersectingCategoryAxisMerger implements CategoryAxisMerger {

		private MALVisualization visualization;

		RangeChartIntersectingCategoryAxisMerger(MALVisualization visualization) {
			super();
			this.visualization = visualization;
		}

		@Override
		public void merge(Set<CategoryTick> master, Map<CategoryTick, Tuple> buckets, MALTwoDimSeriesConfig seriesConfig, PresentationContext pCtx) throws VisualizationException {
			MALSeriesConfig[] seriesConfigs = RangePlotChartVisualizationHandler.this.getSeriesConfigs(visualization, null);
			for (int i = 0; i < seriesConfigs.length; i++) {
				if (seriesConfigs[i] == seriesConfig) {
					if (i == 0) {
						Boolean addAll = (Boolean) pCtx.getAttribute(KEY_MASTER_ADD_ALL);
						if ( addAll == null) {
							addAll = Boolean.TRUE;
						}
						if (Boolean.TRUE.equals(addAll) == true){
							master.addAll(buckets.keySet());
						}
						else {
							master.retainAll(buckets.keySet());
							buckets.keySet().retainAll(master);
						}
					}
					else {
						master.retainAll(buckets.keySet());
						buckets.keySet().retainAll(master);
					}
					break;
				}
			}
		}

	}

}
