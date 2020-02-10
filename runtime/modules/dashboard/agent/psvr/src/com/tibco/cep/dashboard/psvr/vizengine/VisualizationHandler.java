package com.tibco.cep.dashboard.psvr.vizengine;

import java.io.IOException;
import java.util.List;

import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationSeriesContainer;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

/**
 * @author apatil
 *
 */
public abstract class VisualizationHandler extends EngineHandler {

	public abstract VisualizationSeriesContainer[] getVisualizationSeriesContainers(MALComponent component, MALVisualization visualization, PresentationContext pCtx) throws VisualizationException,
			IOException, MALException, ElementNotFoundException, PluginException;

	public abstract DataRow[] getVisualizationHeaderData(MALComponent component, MALVisualization visualization, SeriesDataHolder seriesDataHolder, CategoryTicksGenerator categoryTicksGenerator, PresentationContext pCtx)
			throws VisualizationException, IOException, MALException, ElementNotFoundException, DataException, PluginException;

	public abstract DataRow[] getVisualizationData(MALComponent component, MALVisualization visualization, SeriesDataHolder seriesDataHolder, CategoryTicksGenerator categoryTicksGenerator, PresentationContext pCtx) throws VisualizationException,
			IOException, MALException, ElementNotFoundException, DataException, PluginException;

	public abstract String getHeaderRowTemplateID(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext ctx);

	public abstract String getHeaderRowVisualType(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext ctx);

	public VisualizationSeriesContainer[] getVisualizationSeriesContainers(MALComponent chartComponent, MALVisualization visualization, List<String> seriesConfigNames, PresentationContext ctx)
			throws VisualizationException, IOException, MALException, ElementNotFoundException, PluginException {
		return getVisualizationSeriesContainers(chartComponent, visualization, ctx);
	}

	public MALSeriesConfig[] getSeriesConfigs(MALVisualization visualization, List<String> seriesConfigNames) throws VisualizationException {
		return visualization.getSeriesConfig();
	}

}