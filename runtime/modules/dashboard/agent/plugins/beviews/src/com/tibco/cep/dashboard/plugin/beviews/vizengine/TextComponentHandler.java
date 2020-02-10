package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.tibco.cep.dashboard.plugin.beviews.runtime.MasterCategorySet;
import com.tibco.cep.dashboard.plugin.beviews.runtime.MasterCategorySetCache;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALBackgroundFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.RowConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TextConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TextModel;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationSeriesContainer;
import com.tibco.cep.dashboard.psvr.ogl.model.types.GradientDirection;
import com.tibco.cep.dashboard.psvr.ogl.model.types.RowConfigType;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTick;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTicksGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.ComponentHandler;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandler;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandlerFactory;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * @author apatil
 *
 */
public class TextComponentHandler extends ComponentHandler {

	protected VisualizationModel createVisualizationModel(MALComponent component, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			PluginException {
		MALTextComponent textComponent = (MALTextComponent) component;
		MALVisualization[] visualizations = textComponent.getVisualization();
		TextModel textModel = new TextModel();

		TextConfig textConfig = new TextConfig();
		textConfig.setComponentID(component.getId());
// PATCH hard coding the background to the one of the first visualization in the text component
		MALBackgroundFormat backGroundFormat = textComponent.getBackground();
		textConfig.setBackgroundColor(pCtx.getViewsConfigHelper().getInsightSkin().getComponentBackGroundColor());
		if (backGroundFormat != null && backGroundFormat.getGradientDirection() != null) {
			textConfig.setGradientEndColor(pCtx.getViewsConfigHelper().getInsightSkin().getComponentBackGroundGradientEndColor());
			textConfig.setGradientDirection(GradientDirection.valueOf(backGroundFormat.getGradientDirection().toString()));
		}

		if (visualizations.length == 1) {
			// we have a single visualization, simple to deal with
			VisualizationHandler textVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(visualizations[0]);
			VisualizationSeriesContainer[] rowConfigs = textVisualizationHandler.getVisualizationSeriesContainers(textComponent, visualizations[0], pCtx);
			for (int i = 0; i < rowConfigs.length; i++) {
				textConfig.addRowConfig((RowConfig) rowConfigs[i]);
			}
		} else {
			// we have more then one visualization, we use the first
			// visualization to get the config
			VisualizationHandler textVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(visualizations[0]);
			VisualizationSeriesContainer[] rowConfigs = textVisualizationHandler.getVisualizationSeriesContainers(textComponent, visualizations[0], pCtx);
//	if (rowConfigs.length > 1) {
//		//ok we have more then one row config which means we have a header and data
//		String[] args = new String[] { textComponent.getName() };
//		String exMsg = getMessage("componenthandler.text.multivisualization.invalidshowheader", pCtx.getLocale(), args);
//		throw new VisualizationException(exMsg);
//	}
			for (int i = 0; i < rowConfigs.length; i++) {
				textConfig.addRowConfig((RowConfig) rowConfigs[i]);
			}
		}
		textModel.setTextConfig(textConfig);
		return textModel;
	}

	public VisualizationData getVisualizationHeaderData(MALComponent component, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			PluginException, DataException {
		MALTextComponent textComponent = (MALTextComponent) component;
		MALVisualization[] visualizations = textComponent.getVisualization();
		VisualizationData dataModel = null;
// we always stick with the first visualization so in case of multi-text
// visualization we are OK
		VisualizationHandler textVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(visualizations[0]);
// INFO we can pass null series data holder for text visualization
		DataRow[] headerDataRows = textVisualizationHandler.getVisualizationHeaderData(component, visualizations[0], null, CategoryTicksGeneratorFactory.create(logger, component, pCtx), pCtx);
		DataColumn[] dataColumns = null;
		if (headerDataRows != null && headerDataRows.length > 0) {
			dataColumns = headerDataRows[0].getDataColumn();
		}
		if (dataColumns != null) {
			dataModel = new VisualizationData();
			dataModel.setComponentID(component.getId());
			DataRow dataRow = new DataRow();
			dataRow.setId("1");
			dataRow.setTemplateID(textVisualizationHandler.getHeaderRowTemplateID(component, visualizations[0], null, pCtx));
			dataRow.setTemplateType(RowConfigType.HEADER);
			dataRow.setVisualType(textVisualizationHandler.getHeaderRowVisualType(component, visualizations[0], null, pCtx));
			dataRow.setDataColumn(dataColumns);
			dataModel.addDataRow(dataRow);
			if (visualizations.length == 1) {
				// we have a single visualization, we create the master category
				MasterCategorySet masterCategorySet = MasterCategorySetCache.getInstance().getMasterCategorySet(pCtx.getSecurityToken(), component, pCtx);
				//clear the master category set, since we will be building a new one
				masterCategorySet.clear();
			}
		}
		return dataModel;
	}

	public VisualizationData getVisualizationData(MALComponent component, SeriesDataHolder seriesDataHolder, PresentationContext pCtx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			PluginException, DataException {
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Received " + seriesDataHolder + " for " + component);
		}
		MALTextComponent textComponent = (MALTextComponent) component;
		MALVisualization[] visualizations = textComponent.getVisualization();
		VisualizationData dataModel = new VisualizationData();
		String componentID = component.getId();
		dataModel.setComponentID(componentID);
		if (visualizations.length == 1) {
			// we have a single visualization, simple to deal with
			MALTextVisualization textVisualization = ((MALTextVisualization) visualizations[0]);
			VisualizationHandler textVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(textVisualization);
			CategoryTicksGenerator categoryTicksGenerator = CategoryTicksGeneratorFactory.create(logger, component, pCtx);
			DataRow[] dataRows = textVisualizationHandler.getVisualizationData(component, textVisualization, seriesDataHolder, categoryTicksGenerator, pCtx);
			Set<CategoryTick> ticks = categoryTicksGenerator.getTicks();
			if (ticks == null) {
				throw new IllegalStateException("could not find category axis ticks for " + component);
			}
			//create the master tick set for current set of data
			Collection<CategoryTick> currentMasterTicks = null;
			if (seriesDataHolder.hasAllSeries() == true) {
				//the incoming series data holder is the complete data set, so we use that category axis set
				currentMasterTicks = ticks;
			} else {
				//we don't have the full set , so we need to recompute the category axis ticks
				//PATCH this is so bad , we are recomputing unnecessarily
				categoryTicksGenerator = CategoryTicksGeneratorFactory.create(logger, component, pCtx);
				textVisualizationHandler.getVisualizationData(component, textVisualization, new SeriesDataHolder(component, pCtx), categoryTicksGenerator, pCtx);
				currentMasterTicks = categoryTicksGenerator.getTicks();
			}
			//get the master category axis set
			MasterCategorySet masterCategorySet = MasterCategorySetCache.getInstance().getMasterCategorySet(pCtx.getSecurityToken(), textComponent, pCtx);
			//compare the current master category tick set with existing master category set
			MasterCategorySet.DIFFERENCE difference = masterCategorySet.computeDifference(logger, currentMasterTicks);
			if (difference.compareTo(MasterCategorySet.DIFFERENCE.NONE) != 0) {
				//we have a difference, replace the existing set
				masterCategorySet.setTicks(currentMasterTicks);
				//since we have change in the master category set, we generate a new master category data row
				DataRow masterCategoryRow = createMasterCategoryDataRow(masterCategorySet.ticks(), textComponent, textVisualization, null, pCtx);
				dataModel.addDataRow(masterCategoryRow);
			}
			//add the dataRows to dataModel updating the sort order on the way
			int sortOrder = 0;
			for (CategoryTick categoryTick : currentMasterTicks) {
				//PATCH find the data row matching the category tick, not a good piece of code
				for (DataRow dataRow : dataRows) {
					if (dataRow.getId().equals(categoryTick.getId()) == true) {
						dataRow.setSortOrder(sortOrder);
						dataModel.addDataRow(dataRow);
						break;
					}
				}
				sortOrder++;
			}
		} else {
			// we have more then one visualization, we need to find out which
			// visualization is capable of processing the data
			List<MALTextVisualization> affectedVisualizations = getAffectedVisualizations(visualizations, seriesDataHolder);
			Iterator<MALTextVisualization> affectedVisualizationsIter = affectedVisualizations.iterator();
			while (affectedVisualizationsIter.hasNext()) {
				MALTextVisualization affectedVisualization = affectedVisualizationsIter.next();
				VisualizationHandler textVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(affectedVisualization);
				CategoryTicksGenerator categoryTicksGenerator = CategoryTicksGeneratorFactory.create(logger, component, pCtx);
				DataRow[] dataRows = textVisualizationHandler.getVisualizationData(component, affectedVisualization, seriesDataHolder, categoryTicksGenerator, pCtx);
				for (int j = 0; j < dataRows.length; j++) {
					dataModel.addDataRow(dataRows[j]);
				}
			}
		}
		return dataModel;
	}

	private DataRow createMasterCategoryDataRow(Collection<CategoryTick> ticks, MALTextComponent component, MALTextVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext pCtx)
			throws VisualizationException, PluginException {
		TextVisualizationHandler textVisualizationHandler = (TextVisualizationHandler) VisualizationHandlerFactory.getInstance().getHandler(visualization);
		DataRow masterCategoryRow = new DataRow();
		masterCategoryRow.setId("-1");
		masterCategoryRow.setTemplateID(textVisualizationHandler.getDateRowTemplateID(component, visualization, seriesConfig, pCtx));
		masterCategoryRow.setTemplateType(RowConfigType.HEADER);
		masterCategoryRow.setVisualType("categorycolumn");
		for (CategoryTick categoryTick : ticks) {
			DataColumn dataColumn = new DataColumn();
			dataColumn.setId(categoryTick.getId());
			dataColumn.setDisplayValue(categoryTick.getDisplayValue());
			masterCategoryRow.addDataColumn(dataColumn);
		}
		return masterCategoryRow;
	}

	private List<MALTextVisualization> getAffectedVisualizations(MALVisualization[] visualizations, SeriesDataHolder seriesDataHolder) {
		List<MALTextVisualization> affectedVisualizations = new LinkedList<MALTextVisualization>();
		for (int i = 0; i < visualizations.length; i++) {
			MALTextVisualization visualization = (MALTextVisualization) visualizations[i];
			MALSeriesConfig[] seriesConfigs = visualization.getSeriesConfig();
			for (int j = 0; j < seriesConfigs.length; j++) {
				MALSeriesConfig seriesConfig = seriesConfigs[j];
				if (seriesDataHolder.contains(seriesConfig.getId()) == true) {
					affectedVisualizations.add(visualization);
					continue;
				}
			}
		}
		return affectedVisualizations;
	}

	@Override
	protected void init() {
	}

	@Override
	protected void shutdown() {
	}

}
