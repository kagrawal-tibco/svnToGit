package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.runtime.MasterCategorySet;
import com.tibco.cep.dashboard.plugin.beviews.runtime.MasterCategorySetCache;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALBackgroundFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartCategoryGuidelineConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponentColorSet;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartValueGuidelineConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALForegroundFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALFormatStyle;
import com.tibco.cep.dashboard.psvr.mal.model.MALLegendFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALPlotAreaFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALSkin;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.types.AnchorEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.OrientationEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.RelativeAxisPositionEnum;
import com.tibco.cep.dashboard.psvr.ogl.model.CategoryAxisConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ChartConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ChartModel;
import com.tibco.cep.dashboard.psvr.ogl.model.ChartTypeConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.LegendConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PlotAreaConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PlotPatternConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TickLabelConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ValueAxisConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationSeriesContainer;
import com.tibco.cep.dashboard.psvr.ogl.model.types.AxisPositioning;
import com.tibco.cep.dashboard.psvr.ogl.model.types.DataType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.FontStyle;
import com.tibco.cep.dashboard.psvr.ogl.model.types.GradientDirection;
import com.tibco.cep.dashboard.psvr.ogl.model.types.LegendAnchor;
import com.tibco.cep.dashboard.psvr.ogl.model.types.LegendOrientation;
import com.tibco.cep.dashboard.psvr.ogl.model.types.LineStyle;
import com.tibco.cep.dashboard.psvr.ogl.model.types.PlacementStyle;
import com.tibco.cep.dashboard.psvr.ogl.model.types.RowConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.Scale;
import com.tibco.cep.dashboard.psvr.plugin.PlugIn;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTick;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTicksGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.ComponentHandler;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandler;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandlerFactory;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.ColorMap;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
class ChartComponentHandler extends ComponentHandler {

	ChartComponentHandler(Logger logger, PlugIn plugIn, Properties properties, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super();
		this.logger = logger;
		this.plugIn = plugIn;
		this.properties = properties;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
	}

	@Override
	protected VisualizationModel createVisualizationModel(MALComponent component, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			PluginException {
		MALSkin insightSkin = pCtx.getViewsConfigHelper().getInsightSkin();

		MALChartComponent chartComponent = (MALChartComponent) component;
		MALChartComponentColorSet chartComponentColorSet = (MALChartComponentColorSet) pCtx.getViewsConfigHelper().getComponentColorSet(component, component.getComponentColorSetIndex());
		// chart model
		ChartModel chartModel = new ChartModel();

		// chart config
		ChartConfig chartConfig = new ChartConfig();
		chartConfig.setComponentID(component.getId());
		// chart background color
		MALBackgroundFormat backGroundFormat = chartComponent.getBackground();

		chartConfig.setBackgroundColor(insightSkin.getComponentBackGroundColor());
		if (backGroundFormat != null && backGroundFormat.getGradientDirection() != null) {
			chartConfig.setGradientEndColor(insightSkin.getComponentBackGroundGradientEndColor());
			chartConfig.setGradientDirection(GradientDirection.valueOf(backGroundFormat.getGradientDirection().toString()));
		}

		// legend
		MALLegendFormat legendFormat = chartComponent.getLegend();
		if (legendFormat != null) {
			AnchorEnum anchorFormat = legendFormat.getAnchor();
			OrientationEnum orientationFormat = legendFormat.getOrientation();
			if (anchorFormat != null && orientationFormat != null) {
				if (AnchorEnum.NONE.equals(anchorFormat) == false && OrientationEnum.NONE.equals(orientationFormat) == false) {
					LegendConfig legendConfig = new LegendConfig();
					legendConfig.setAnchor(LegendAnchor.valueOf(anchorFormat.toString()));
					legendConfig.setOrientation(LegendOrientation.valueOf(orientationFormat.toString()));
					chartConfig.setLegendConfig(legendConfig);
				}
			} else if (anchorFormat == null && orientationFormat == null) {
				logger.log(Level.WARN, "Missing anchor and orientation in legendformat for component[id=" + component.getId() + ",name=" + component.getName() + "]");
			} else if (anchorFormat == null) {
				logger.log(Level.WARN, "Missing anchor in legendformat for component[id=" + component.getId() + ",name=" + component.getName() + "]");
			} else if (orientationFormat == null) {
				logger.log(Level.WARN, "Missing orientation in legendformat for component[id=" + component.getId() + ",name=" + component.getName() + "]");
			}
		}

		// plotareaspecification
		MALPlotAreaFormat plotAreaFormat = chartComponent.getPlotArea();
		if (plotAreaFormat != null) {
			PlotAreaConfig plotAreaConfig = new PlotAreaConfig();
			// plot background
			MALBackgroundFormat plotAreaBackGroundFormat = plotAreaFormat.getBackground();
			plotAreaConfig.setBackgroundColor(insightSkin.getVisualizationBackGroundColor());
			if (plotAreaBackGroundFormat != null && plotAreaBackGroundFormat.getGradientDirection() != null) {
				plotAreaConfig.setGradientEndColor(insightSkin.getVisualizationBackGroundGradientEndColor());
				plotAreaConfig.setGradientDirection(GradientDirection.valueOf(backGroundFormat.getGradientDirection().toString()));
			}

			// plot foreground
			MALForegroundFormat plotAreaForeGroundFormat = plotAreaFormat.getForeground();
			PlotPatternConfig plotPatternConfig = new PlotPatternConfig();
			plotPatternConfig.setColor(insightSkin.getVisualizationForeGroundColor());
			plotPatternConfig.setLines(LineStyle.valueOf(plotAreaForeGroundFormat.getLine().toString()));
			plotAreaConfig.setPlotPatternConfig(plotPatternConfig);
			chartConfig.setPlotAreaConfig(plotAreaConfig);
		}

		// chart category axis configuration
		MALChartCategoryGuidelineConfig malCategoryGuidelineCfg = chartComponent.getCategoryGuidelineConfig();
		if (malCategoryGuidelineCfg != null) {
			CategoryAxisConfig oglCategoryAxisCfg = new CategoryAxisConfig();
			String headerName = malCategoryGuidelineCfg.getHeaderName();
			if (headerName == null) {
				headerName = "";
			}
			oglCategoryAxisCfg.setName(headerName);
			MALFormatStyle categoryHdrFormatStyle = malCategoryGuidelineCfg.getHeaderFormatStyle();
			oglCategoryAxisCfg.setFontColor(ColorMap.toHex(chartComponentColorSet.getGuideLineLabelFontColor()));
			oglCategoryAxisCfg.setFontSize(categoryHdrFormatStyle.getFontSize());
			oglCategoryAxisCfg.setFontStyle(FontStyle.valueOf(categoryHdrFormatStyle.getFontStyle().toString()));

			RelativeAxisPositionEnum relativeCategoryAxisPosition = malCategoryGuidelineCfg.getRelativePosition();
			if (relativeCategoryAxisPosition == null) {
				relativeCategoryAxisPosition = RelativeAxisPositionEnum.BELOW;
			}
			oglCategoryAxisCfg.setRelativePosition(AxisPositioning.valueOf(relativeCategoryAxisPosition.toString()));
			TickLabelConfig categoryAxisTickLabelCfg = new TickLabelConfig();
			MALFormatStyle categoryLblFormatStyle = malCategoryGuidelineCfg.getLabelFormatStyle();
			categoryAxisTickLabelCfg.setFontColor(ColorMap.toHex(chartComponentColorSet.getGuideLineValueLabelFontColor()));
			categoryAxisTickLabelCfg.setFontSize(categoryLblFormatStyle.getFontSize());
			categoryAxisTickLabelCfg.setFontStyle(FontStyle.valueOf(categoryLblFormatStyle.getFontStyle().toString()));
			categoryAxisTickLabelCfg.setPlacement(PlacementStyle.valueOf(malCategoryGuidelineCfg.getPlacement().toString()));
			categoryAxisTickLabelCfg.setRotation(malCategoryGuidelineCfg.getRotation());
			categoryAxisTickLabelCfg.setSkipFactor(malCategoryGuidelineCfg.getSkipFactor());
			oglCategoryAxisCfg.setTickLabelConfig(categoryAxisTickLabelCfg);

			chartConfig.setCategoryAxisConfig(oglCategoryAxisCfg);
		}

		// value category axis configuration
		MALChartValueGuidelineConfig malValueAxisCfg = chartComponent.getValueGuidelineConfig();
		if (malValueAxisCfg != null) {
			ValueAxisConfig oglValueAxisCfg = new ValueAxisConfig();
			String headerName = malValueAxisCfg.getHeaderName();
			if (headerName == null) {
				headerName = "";
			}
			oglValueAxisCfg.setName(headerName);
			MALFormatStyle valueHdrFormatStyle = malValueAxisCfg.getHeaderFormatStyle();
			oglValueAxisCfg.setFontColor(ColorMap.toHex(chartComponentColorSet.getGuideLineLabelFontColor()));
			oglValueAxisCfg.setFontSize(valueHdrFormatStyle.getFontSize());
			oglValueAxisCfg.setFontStyle(FontStyle.valueOf(valueHdrFormatStyle.getFontStyle().toString()));

			oglValueAxisCfg.setScale(Scale.valueOf(malValueAxisCfg.getScale().toString()));
			oglValueAxisCfg.setNoOfVisualDivisions(malValueAxisCfg.getDivision());
			RelativeAxisPositionEnum relativeValueAxisPosition = malValueAxisCfg.getRelativePosition();
			if (relativeValueAxisPosition == null) {
				relativeValueAxisPosition = RelativeAxisPositionEnum.LEFT;
			}
			oglValueAxisCfg.setRelativePosition(AxisPositioning.valueOf(relativeValueAxisPosition.toString()));

			TickLabelConfig valueAxisTickLabelCfg = new TickLabelConfig();
			MALFormatStyle valueAxisLblFormatStyle = malValueAxisCfg.getLabelFormatStyle();
			valueAxisTickLabelCfg.setFontColor(ColorMap.toHex(chartComponentColorSet.getGuideLineValueLabelFontColor()));
			valueAxisTickLabelCfg.setFontSize(valueAxisLblFormatStyle.getFontSize());
			valueAxisTickLabelCfg.setFontStyle(FontStyle.valueOf(valueAxisLblFormatStyle.getFontStyle().toString()));
			oglValueAxisCfg.setTickLabelConfig(valueAxisTickLabelCfg);

			chartConfig.setValueAxisConfig(oglValueAxisCfg);
		}

		chartModel.setChartConfig(chartConfig);

		DataType categoryAxisDataType = null;
		// chart type config
		MALVisualization[] visualizations = chartComponent.getVisualization();
		for (int i = 0; i < visualizations.length; i++) {
			MALVisualization visualization = visualizations[i];
			if (visualization.getSeriesConfigCount() > 0) {
				CustomVisualizationHandler visualizationHandler = (CustomVisualizationHandler) VisualizationHandlerFactory.getInstance().getHandler(visualization);
				categoryAxisDataType = visualizationHandler.getCategoryAxisDataType(component, visualization, pCtx);
				VisualizationSeriesContainer[] visualizationSeriesContainer = visualizationHandler.getVisualizationSeriesContainers(chartComponent, visualization, seriesConfigNames, pCtx);
				// INFO hardcoding to pick a single visualization series
				// container for chart [this is the charttypeconfig]
				chartConfig.addChartTypeConfig((ChartTypeConfig) visualizationSeriesContainer[0]);
			}
		}

		chartModel.setHelp(component.getDescription());

		if (categoryAxisDataType != null) {
			chartConfig.getCategoryAxisConfig().setDataType(categoryAxisDataType);
		}

		return chartModel;

	}

	@Override
	public VisualizationData getVisualizationHeaderData(MALComponent component, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			DataException, PluginException {
		//create the data set , DO NOT use the incoming series config names
		SeriesDataHolder seriesDataHolder = new SeriesDataHolder(component, null, pCtx);
		//create the category axis model
		CategoryTicksGenerator categoryTicksGenerator = CategoryTicksGeneratorFactory.create(logger, component, pCtx);
		//now let each visualization create it's own header's
		MALVisualization[] visualizations = component.getVisualization();
		for (MALVisualization visualization : visualizations) {
			//get the original visualization handler
			VisualizationHandler visualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(visualization);
			//generate the header columns using the category axis model
			visualizationHandler.getVisualizationHeaderData(component, visualization, seriesDataHolder, categoryTicksGenerator, pCtx);
		}
		//create the master category set
		MasterCategorySet masterCategorySet = MasterCategorySetCache.getInstance().getMasterCategorySet(pCtx.getSecurityToken(), component, pCtx);
		//set ticks
		masterCategorySet.setTicks(new LinkedList<CategoryTick>((categoryTicksGenerator.getTicks())));
		//now we have a complete master category set , create a header row out of it
		if (masterCategorySet.ticks().isEmpty() == true) {
			return null;
		}
		//create the data row
		DataRow dataRow = createCategoryDataRow(component, visualizations[0], VisualizationHandlerFactory.getInstance().getHandler(visualizations[0]), pCtx);
		//add data columns to the data row
		for (CategoryTick categoryTick : masterCategorySet.ticks()) {
			DataColumn column = new DataColumn();
			column.setId(categoryTick.getId());
			column.setDisplayValue(categoryTick.getDisplayValue());
			column.setValue(categoryTick.getValue());
			dataRow.addDataColumn(column);
		}
		//create visualization data
		VisualizationData visualizationData = new VisualizationData();
		visualizationData.setComponentID(component.getId());
		visualizationData.addDataRow(dataRow);
		return visualizationData;
	}

	protected DataRow createCategoryDataRow(MALComponent component, MALVisualization visualization, VisualizationHandler visualizationHandler, PresentationContext pCtx) {
		DataRow dataRow = new DataRow();
		// INFO category axis values comes inside datarow[id='1',templatetype='header',visualtype='categoryaxis',templateid=null]
		// INFO hardcoding the header row id to be '1'
		dataRow.setId("1");
		dataRow.setTemplateType(RowConfigType.HEADER);
		dataRow.setTemplateID(visualizationHandler.getHeaderRowTemplateID(component, visualization, null, pCtx));
		dataRow.setVisualType(visualizationHandler.getHeaderRowVisualType(component, visualization, null, pCtx));
		return dataRow;
	}

	@Override
	public VisualizationData getVisualizationData(MALComponent component, SeriesDataHolder seriesDataHolder, PresentationContext pCtx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			PluginException, DataException {
		//create visualization data
		VisualizationData visualizationData = new VisualizationData();
		visualizationData.setComponentID(component.getId());
		//create the category axis model
		CategoryTicksGenerator categoryTicksGenerator = CategoryTicksGeneratorFactory.create(logger, component, pCtx);
		//now let each visualization create it's own header's
		MALVisualization[] visualizations = component.getVisualization();
		for (MALVisualization visualization : visualizations) {
			//get the original visualization handler
			VisualizationHandler visualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(visualization);
			//generate the data rows using the category axis model
			DataRow[] dataRows = visualizationHandler.getVisualizationData(component, visualization, seriesDataHolder, categoryTicksGenerator, pCtx);
			//add the data rows to the visualization data
			for (DataRow dataRow : dataRows) {
				visualizationData.addDataRow(dataRow);
			}
		}
		//get the master category axis set
		MasterCategorySet masterCategorySet = MasterCategorySetCache.getInstance().getMasterCategorySet(pCtx.getSecurityToken(), component, pCtx);
		//re-create the master tick set for current set of data if needed
		if (seriesDataHolder.hasAllSeries() == false) {
			//save the existing master category set, since the getVisualizationHeaderData(..) will wipe it out
			Set<CategoryTick> existingMasterTicks = new LinkedHashSet<CategoryTick>(masterCategorySet.ticks());
			//we don't have the full set , so we need to recompute the category axis ticks
			getVisualizationHeaderData(component, null, pCtx);
			//compare the current master category tick set with existing master category set
			MasterCategorySet.DIFFERENCE difference = masterCategorySet.computeDifference(logger, existingMasterTicks);
			if (difference.compareTo(MasterCategorySet.DIFFERENCE.NONE) != 0) {
				//do not set the existing master ticks , since the master category set is more up-to-date
				//just regenerate the master data row
				//since we have change in the master category set, we generate a new master category data row
				DataRow dataRow = createCategoryDataRow(component, visualizations[0], VisualizationHandlerFactory.getInstance().getHandler(visualizations[0]), pCtx);
				//add data columns to the data row
				for (CategoryTick categoryTick : masterCategorySet.ticks()) {
					DataColumn column = new DataColumn();
					column.setId(categoryTick.getId());
					column.setDisplayValue(categoryTick.getDisplayValue());
					column.setValue(categoryTick.getValue());
					dataRow.addDataColumn(column);
				}
				visualizationData.addDataRow(/*0, */dataRow);
			}
		}
		else {
			//the series data holder represents all the data
			//get the current master ticks
			Set<CategoryTick> currentMasterTicks = categoryTicksGenerator.getTicks();
			//compare the current master category tick set with existing master category set
			MasterCategorySet.DIFFERENCE difference = masterCategorySet.computeDifference(logger, currentMasterTicks);
			if (difference.compareTo(MasterCategorySet.DIFFERENCE.NONE) != 0) {
				//we have a difference, replace the existing set
				masterCategorySet.setTicks(currentMasterTicks);
				//since we have change in the master category set, we generate a new master category data row
				DataRow dataRow = createCategoryDataRow(component, visualizations[0], VisualizationHandlerFactory.getInstance().getHandler(visualizations[0]), pCtx);
				//add data columns to the data row
				for (CategoryTick categoryTick : masterCategorySet.ticks()) {
					DataColumn column = new DataColumn();
					column.setId(categoryTick.getId());
					column.setDisplayValue(categoryTick.getDisplayValue());
					column.setValue(categoryTick.getValue());
					dataRow.addDataColumn(column);
				}
				visualizationData.addDataRow(/*0, */dataRow);
			}
		}
		return visualizationData;
	}

	@Override
	protected void init() {
	}

	@Override
	protected void shutdown() {
	}
}