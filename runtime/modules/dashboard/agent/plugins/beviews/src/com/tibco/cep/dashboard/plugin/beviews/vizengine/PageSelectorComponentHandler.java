package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPageSelectorComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPageVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.GlobalRowConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PageSetRowConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PageSetSelectorConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PageSetSelectorModel;
import com.tibco.cep.dashboard.psvr.ogl.model.RowConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationSeriesContainer;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ColumnType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.RowConfigType;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.ComponentHandler;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandler;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandlerFactory;

/**
 * @author apatil
 *
 */
public class PageSelectorComponentHandler extends ComponentHandler {

	private static final String TEXT_VISUALIZATION_DEF_TYPE = "TextVisualization";
	private static final String PAGE_VISUALIZATION_DEF_TYPE = "PageVisualization";
	private static final String GLOBAL_ROW_TEMPLATE_ID = "1";
	private static final String PAGESETROW_VISUAL_TYPE = "pagesetrow";
	private static final String GLOBALROW_VISUAL_TYPE = "globalrow";

	protected VisualizationModel createVisualizationModel(MALComponent component, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			PluginException {

		MALPageSelectorComponent pageSetSelectorComponent = (MALPageSelectorComponent) component;

		// Create the textModel to represent the PageSetSelector Component
		PageSetSelectorModel pageSetModel = new PageSetSelectorModel();

		// Create the Config Object
		PageSetSelectorConfig pageSetSelectorConfig = new PageSetSelectorConfig();
		pageSetSelectorConfig.setComponentID(component.getId());
		pageSetSelectorConfig.setBackgroundColor(pCtx.getViewsConfigHelper().getInsightSkin().getComponentBackGroundColor());

		// Get the Visualizations to return as row configs
		MALTextVisualization globalVisualization = getGlobalVisualization(pageSetSelectorComponent, pCtx);
		List<MALVisualization> pageSetVisualizations = getPageSetVisualizations(pageSetSelectorComponent, pCtx);

		if (globalVisualization != null) {
			// Process the Global Visualizations
			VisualizationHandler globalVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(globalVisualization);
			VisualizationSeriesContainer[] globalConfigs = globalVisualizationHandler.getVisualizationSeriesContainers(pageSetSelectorComponent, globalVisualization, pCtx);
			RowConfig globalConfig = (RowConfig) globalConfigs[0];
			if (globalConfig.getColumnConfigCount() != 1) {
				throw new VisualizationException("componenthandler.pageset.invalidglobalvisualization.specified");
			}
			if (globalConfig.getColumnConfig(0).getType().getType() != ColumnType.INDICATOR_TYPE) {
				throw new VisualizationException("componenthandler.pageset.invalidglobalvisualization.specified");
			}

			GlobalRowConfig globalRowConfig = new GlobalRowConfig();
			globalRowConfig.setColumnConfig(globalConfig.getColumnConfig(0));
			pageSetSelectorConfig.setGlobalRowConfig(globalRowConfig);
		}

		Iterator<MALVisualization> pageSetVisualizationsIter = pageSetVisualizations.iterator();
		while (pageSetVisualizationsIter.hasNext()) {
			MALPageVisualization pageSetVisualization = (MALPageVisualization) pageSetVisualizationsIter.next();
			if (pageSetVisualization.getRelatedElementCount() != 1) {
				throw new VisualizationException(pageSetVisualization + " has inconsistent page set reference");
			}
			MALPage page = (MALPage) pageSetVisualization.getRelatedElement(0);
			PageSetRowConfig pageSetRowConfig = new PageSetRowConfig();
			String title = page.getDisplayName();
			if (title == null || title.trim().length() == 0) {
				title = page.getName();
			}
			pageSetRowConfig.setTitle(title);
			pageSetRowConfig.setTooltip("Click to load " + title);
			pageSetRowConfig.setPagesetID(page.getId());
			VisualizationHandler pagesetVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(pageSetVisualization);
			VisualizationSeriesContainer[] pagesetConfigs = pagesetVisualizationHandler.getVisualizationSeriesContainers(pageSetSelectorComponent, pageSetVisualization, pCtx);
			if (pagesetConfigs.length != 1) {
				throw new VisualizationException("componenthandler.pageset.invalidpagesetvisualization.specified");
			}
			RowConfig pagesetConfig = (RowConfig) pagesetConfigs[0];
			if (/* pagesetConfig.getColumnConfigCount() == 0 || */pagesetConfig.getColumnConfigCount() > 2) {
				throw new VisualizationException("componenthandler.pageset.invalidpagesetvisualization.specified");
			}
			pageSetRowConfig.setColumnConfig(pagesetConfig.getColumnConfig());
			pageSetSelectorConfig.addPageSetRowConfig(pageSetRowConfig);
		}
		pageSetModel.setPageSetSelectorConfig(pageSetSelectorConfig);
		return pageSetModel;
	}

	/**
	 * @param visualizations
	 * @param ctx
	 * @return
	 */
	private List<MALVisualization> getPageSetVisualizations(MALComponent component, PresentationContext ctx) {
		MALVisualization[] visualizations = component.getVisualization();
		List<MALVisualization> pageSetVisualizations = new ArrayList<MALVisualization>();
		for (int i = 0; i < visualizations.length; i++) {
			MALVisualization visualization = visualizations[i];
			if (visualization.getDefinitionType().equalsIgnoreCase(PAGE_VISUALIZATION_DEF_TYPE) == true) {
				pageSetVisualizations.add(visualization);
			}
		}
		return pageSetVisualizations;
	}

	/**
	 * @param visualization
	 * @return
	 * @throws VisualizationException
	 */
	private MALTextVisualization getGlobalVisualization(MALComponent component, PresentationContext pCtx) {
		MALVisualization[] visualizations = component.getVisualization();
		for (int i = 0; i < visualizations.length; i++) {
			MALVisualization visualization = visualizations[i];
			if (visualization.getDefinitionType().equalsIgnoreCase(TEXT_VISUALIZATION_DEF_TYPE) == true) {
				return (MALTextVisualization) visualization;
			}
		}
		return null;
		// String displayName = component.getDisplayName();
		// if (displayName == null || displayName.trim().length() == 0){
		// displayName = component.getName();
		// }
		// String exMsg = getMessage("componenthandler.pageset.noglobalvisualization.specified",pCtx.getLocale(),new String[]{displayName});
		// throw new VisualizationException(exMsg);
	}

	public VisualizationData getVisualizationHeaderData(MALComponent component, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException {
		// Nothing in this is a header hence returning null.
		return null;
	}

	public VisualizationData getVisualizationData(MALComponent component, SeriesDataHolder seriesDataHolder, PresentationContext pCtx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			PluginException, DataException {
		MALPageSelectorComponent pageSelectorComponent = (MALPageSelectorComponent) component;

		// Create the pagesetDataModel to represent the Data for the PageSetSelector Component
		VisualizationData pagesetDataModel = new VisualizationData();
		pagesetDataModel.setComponentID(component.getId());

		// Get the Visualizations to return as data rows
		MALTextVisualization globalVisualization = getGlobalVisualization(pageSelectorComponent, pCtx);
		List<MALVisualization> pageSetVisualizations = getPageSetVisualizations(pageSelectorComponent, pCtx);

		if (globalVisualization != null) {
			// Process the Global Visualizations
			VisualizationHandler globalVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(globalVisualization);
			DataRow[] globalDataRows = globalVisualizationHandler.getVisualizationData(component, globalVisualization, seriesDataHolder, null, pCtx);
			if (globalDataRows.length > 0) {
				DataRow globalIndicatorDataRow = globalDataRows[0];
				globalIndicatorDataRow.setTemplateID(GLOBAL_ROW_TEMPLATE_ID);
				globalIndicatorDataRow.setTemplateType(RowConfigType.HEADER);
				globalIndicatorDataRow.setVisualType(GLOBALROW_VISUAL_TYPE);
				pagesetDataModel.addDataRow(globalIndicatorDataRow);
			}
		}

		// Process the Pageset Visualizations
		VisualizationHandler pagesetVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(pageSetVisualizations.get(0));
		Iterator<MALVisualization> pageSetVisualizationIter = pageSetVisualizations.iterator();
		while (pageSetVisualizationIter.hasNext()) {
			MALPageVisualization pageSetVisualization = (MALPageVisualization) pageSetVisualizationIter.next();
			DataRow[] pagesetDataRows = pagesetVisualizationHandler.getVisualizationData(component, pageSetVisualization, seriesDataHolder, null, pCtx);
			if (pagesetDataRows != null && pagesetDataRows.length != 0) {
				pagesetDataRows[0].setVisualType(PAGESETROW_VISUAL_TYPE);
				pagesetDataModel.addDataRow(pagesetDataRows[0]);
			}
		}
		return pagesetDataModel;
	}

	@Override
	protected void init() {
	}

	@Override
	protected void shutdown() {
	}

}