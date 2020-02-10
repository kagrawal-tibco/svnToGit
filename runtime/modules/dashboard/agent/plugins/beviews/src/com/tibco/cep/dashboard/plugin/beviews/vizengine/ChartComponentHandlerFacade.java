package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.List;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.types.ThresholdUnitEnum;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.ComponentHandler;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;

public class ChartComponentHandlerFacade extends ComponentHandler {

	private TimeSeriesChartComponentHandler timeSeriesChartComponentHandler;

	private ChartComponentHandler chartComponentHandler;

	@Override
	protected void init() {
		timeSeriesChartComponentHandler = new TimeSeriesChartComponentHandler(logger,plugIn,properties,exceptionHandler,messageGenerator);
		timeSeriesChartComponentHandler.init();

		chartComponentHandler = new /*NextGen*/ChartComponentHandler(logger,plugIn,properties,exceptionHandler,messageGenerator);
		chartComponentHandler.init();
	}

	@Override
	protected VisualizationModel createVisualizationModel(MALComponent component, List<String> seriesConfigNames, PresentationContext ctx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			PluginException {
		return getHandler(component, ctx).createVisualizationModel(component, seriesConfigNames, ctx);

	}

	@Override
	public VisualizationData getVisualizationData(MALComponent component, SeriesDataHolder seriesDataHolder, PresentationContext ctx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			PluginException, DataException {
		return getHandler(component, ctx).getVisualizationData(component, seriesDataHolder, ctx);
	}

	@Override
	public VisualizationData getVisualizationHeaderData(MALComponent component, List<String> seriesConfigNames, PresentationContext ctx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			DataException, PluginException {
		return getHandler(component, ctx).getVisualizationHeaderData(component, seriesConfigNames, ctx);
	}


	@Override
	protected void shutdown() throws NonFatalException {
		timeSeriesChartComponentHandler.shutdown();
		chartComponentHandler.shutdown();
	}

	protected ChartComponentHandler getHandler(MALComponent component, PresentationContext ctx) throws MALException, ElementNotFoundException {
		ChartComponentHandler handler = (ChartComponentHandler) ctx.getAttribute(component.getId());
		if (handler == null) {
			boolean isTimeSeries = isTimeSeries(component, ctx);
			if (isTimeSeries == true){
				handler = timeSeriesChartComponentHandler;
			}
			else {
				handler = chartComponentHandler;
			}
			ctx.addAttribute(component.getId(), handler);
		}
		return handler;
	}

	protected boolean isTimeSeries(MALComponent component, PresentationContext ctx) throws MALException, ElementNotFoundException {
		for (MALVisualization visualization : component.getVisualization()) {
			for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
				MALTwoDimSeriesConfig castedSeriesConfig = (MALTwoDimSeriesConfig) seriesConfig;
				MALFieldMetaInfo categoryField = ctx.resolveFieldRef(castedSeriesConfig.getCategoryDataConfig().getExtractor().getSourceField());
				if (categoryField.isDate() == false){
					return false;
				}
				if (castedSeriesConfig.getActionRule().getThresholdUnit().equals(ThresholdUnitEnum.COUNT) == true){
					return false;
				}
			}
		}
		return true;
	}
}
