package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;

import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPageVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationSeriesContainer;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTicksGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;

/**
 * @author apatil
 *
 */
public class PageVisualizationHandler extends TextVisualizationHandler {

	@Override
	public VisualizationSeriesContainer[] getVisualizationSeriesContainers(MALComponent component, MALVisualization visualization, PresentationContext pCtx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException, PluginException {
		return super.getVisualizationSeriesContainers(component, visualization, pCtx);
	}

	@Override
	public DataRow[] getVisualizationData(MALComponent component, MALVisualization visualization, SeriesDataHolder seriesDataHolder, CategoryTicksGenerator categoryTicksGenerator, PresentationContext pCtx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException, DataException, PluginException {
		DataRow[] dataRows = super.getVisualizationData(component, visualization, seriesDataHolder, categoryTicksGenerator, pCtx);
		if (dataRows != null && dataRows.length != 0) {
			MALPageVisualization pageSetVisualization = (MALPageVisualization) visualization;
			if (pageSetVisualization.getRelatedElementCount() != 1) {
				throw new VisualizationException(pageSetVisualization + " has inconsistent page set reference");
			}
			MALPage page = (MALPage) pageSetVisualization.getRelatedElement(0);
			String pageSetID = page.getId();
			String pageSetName = page.getDisplayName();
			if (pageSetName == null) {
				pageSetName = page.getName();
			}
			// we set the pageset id as the id
			dataRows[0].setId(pageSetID);
		}
		// PATCH we will reset the link, this might cause performance issue.
		// We shd not generate link in first place
		for (int i = 0; i < dataRows.length; i++) {
			DataRow row = dataRows[i];
			DataColumn[] columns = row.getDataColumn();
			for (int j = 0; j < columns.length; j++) {
				DataColumn column = columns[j];
				column.setLink(null);
			}
		}
		return dataRows;
	}
}
