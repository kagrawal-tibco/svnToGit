package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.plugin.internal.vizengine.formatters.FormatterUtils;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartCategoryDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponentColorSet;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartValueDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartValueGuidelineConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALFormatStyle;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesColor;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.types.FontStyleEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.ScaleEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.SeriesAnchorEnum;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.ChartTypeConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.SeriesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TickLabelConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.ValueAxisConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ValueLabelConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationSeriesContainer;
import com.tibco.cep.dashboard.psvr.ogl.model.types.AxisPositioning;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ChartRenderingType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.FontStyle;
import com.tibco.cep.dashboard.psvr.ogl.model.types.Quadrant;
import com.tibco.cep.dashboard.psvr.ogl.model.types.RowConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.Scale;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryAxisMerger;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTick;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTicksGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.DataConfigHandler;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGeneratorFactory;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigUtils;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.ColorMap;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DataConfigVisitor;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DefaultDataConfigVisitor;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.OutputFieldValueProcessor;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
public abstract class ChartVisualizationHandler extends CustomVisualizationHandler {

	@Override
	public VisualizationSeriesContainer[] getVisualizationSeriesContainers(MALComponent component, MALVisualization visualization, PresentationContext pCtx) throws VisualizationException, IOException, MALException,
			PluginException, ElementNotFoundException {
		return getVisualizationSeriesContainers(component, visualization, null, pCtx);
	}

	@Override
	public VisualizationSeriesContainer[] getVisualizationSeriesContainers(MALComponent component, MALVisualization visualization, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException,
			IOException, MALException, PluginException, ElementNotFoundException {
		MALChartVisualization chartVisualization = (MALChartVisualization) visualization;
		MALChartComponentColorSet chartComponentColorSet = (MALChartComponentColorSet) pCtx.getViewsConfigHelper().getComponentColorSet(component, component.getComponentColorSetIndex());
		ChartTypeConfig chartTypeConfig = new ChartTypeConfig();
		chartTypeConfig.setType(getChartRenderingType(visualization));
		MALChartValueGuidelineConfig malCustomValueGuidelineCfg = (MALChartValueGuidelineConfig) chartVisualization.getValueGuidelineConfig();
		if (malCustomValueGuidelineCfg != null) {

			ValueAxisConfig customValueAxisConfig = createCustomValueAxisCfg(malCustomValueGuidelineCfg, chartComponentColorSet);
			chartTypeConfig.setValueAxisConfig(customValueAxisConfig);
		}
		TypeSpecificAttribute[] typeSpecificAttributes = getTypeSpecificAttributes(component, visualization, pCtx);
		chartTypeConfig.setTypeSpecificAttribute(typeSpecificAttributes);

		MALSeriesConfig[] seriesConfigs = getSeriesConfigs(chartVisualization, seriesConfigNames);
		int seriesColorIndex = visualization.getSeriesColorIndex();
		if (seriesColorIndex == -1) {
			seriesColorIndex = component.getSeriesColorIndex();
			if (seriesColorIndex == -1) {
				seriesColorIndex = 0;
			}
		}
		// we need to change colors based on visualization
		int seriesColorIdxAdjustment = 0;
		MALVisualization[] visualizations = component.getVisualization();
		for (int i = 0; i < visualizations.length; i++) {
			if (visualizations[i] == visualization) {
				break;
			} else {
				seriesColorIdxAdjustment = seriesColorIdxAdjustment + visualizations[i].getSeriesConfigCount();
			}
		}
		seriesColorIndex = seriesColorIndex + seriesColorIdxAdjustment;
		if (seriesColorIndex >= chartComponentColorSet.getSeriesColorCount()) {
			logger.log(Level.WARN, getMessage("visualizationhandler.chart.seriescfg.invalidseriescolorindex", pCtx.getLocale(), pCtx.getMessageGeneratorArgs(null, component.toString(), chartComponentColorSet.getName(), Integer.toString(seriesColorIndex))));
			seriesColorIndex = 0;
		}
		// PATCH remove this code before GA
		boolean showValuesHACK = true;
		HashMap<String, String> dynParamSubMap = new HashMap<String, String>();
		dynParamSubMap.put(ActionConfigGenerator.CURRENTCOMPONENT_ID_DYN_PARAM, component.getId());
		for (int i = 0; i < seriesConfigs.length; i++) {
			MALChartSeriesConfig malChartSeriesConfig = (MALChartSeriesConfig) seriesConfigs[i];
			SeriesConfig oglChartSeriesConfig = createChartSeriesConfig(malChartSeriesConfig, chartComponentColorSet, seriesColorIndex);

			if (pCtx.generateAdditionalOutputs() == true) {
				ActionConfigGenerator[] seriesActionCfgGenerators = ActionConfigGeneratorFactory.getInstance().getGenerators(malChartSeriesConfig);
				// Anand - Added on 11/23/06 bug # 6521 - START
				dynParamSubMap.put(ActionConfigGenerator.CURRENTSERIES_ID_DYN_PARAM, malChartSeriesConfig.getId());
				// Anand - Added on 11/23/06 bug # 6521 - START
				List<ActionConfigType> actionConfigs = new LinkedList<ActionConfigType>();
				for (ActionConfigGenerator actionConfigGenerator : seriesActionCfgGenerators) {
					actionConfigs.addAll(actionConfigGenerator.generateActionConfigs(malChartSeriesConfig, dynParamSubMap, pCtx));
				}
				oglChartSeriesConfig.setActionConfig(ActionConfigUtils.createActionConfigSet("ROOT", false, actionConfigs));
			}
			//INFO moved from using getValueLabelStyle to DataFormatter.getShowLabel()
			MALDataFormat valueLabelFormatter = malChartSeriesConfig.getValueDataConfig()[0].getFormatter();
			if (valueLabelFormatter == null || valueLabelFormatter.getShowLabel() == false) {
				showValuesHACK = false;
			}
//			if (malChartSeriesConfig.getValueDataConfig()[0].getFormatter().getShowLabel() != null && showValuesHACK == false) {
//				showValuesHACK = true;
//			}
			chartTypeConfig.addSeriesConfig(oglChartSeriesConfig);
			seriesColorIndex++;
			if (seriesColorIndex >= chartComponentColorSet.getSeriesColorCount()) {
				seriesColorIndex = 0;
			}
		}
		chartTypeConfig.addTypeSpecificAttribute(createTypeSpecificAttribute("showvalues", Boolean.toString(showValuesHACK)));
		return new VisualizationSeriesContainer[] { chartTypeConfig };
	}

	/**
	 * @param malChartSeriesConfig
	 * @return
	 */
	protected SeriesConfig createChartSeriesConfig(MALChartSeriesConfig malChartSeriesConfig, MALChartComponentColorSet componentColorSet, int seriesColorIndex) {
		SeriesConfig oglChartSeriesConfig = new SeriesConfig();
		oglChartSeriesConfig.setName(malChartSeriesConfig.getId());
		String displayName = malChartSeriesConfig.getDisplayName();
		if (displayName == null || displayName.trim().length() == 0) {
			displayName = malChartSeriesConfig.getName();
		}
		oglChartSeriesConfig.setDisplayName(displayName);
		oglChartSeriesConfig.setTooltip(malChartSeriesConfig.getToolTip());
		//INFO sending valuelabelconfig using series's value config/formatter/showLabel & value config/formatter/fontsize|fontstyle
		MALDataFormat valueAxisFormatter = malChartSeriesConfig.getValueDataConfig()[0].getFormatter();
		if (valueAxisFormatter != null && valueAxisFormatter.getShowLabel() == true) {
			ValueLabelConfig oglValueLabelConfig = new ValueLabelConfig();
			//oglValueLabelConfig.setAnchor(LabelAnchor.valueOf(malValueLabelStyle.getAnchor().toString()));
			oglValueLabelConfig.setFontColor(ColorMap.toHex(componentColorSet.getDataPointLabelFontColor()));
			int fontSize = valueAxisFormatter.getFormatStyle() != null ? valueAxisFormatter.getFormatStyle().getFontSize() : 10;
			oglValueLabelConfig.setFontSize(fontSize);
			FontStyleEnum malFontStyle = valueAxisFormatter.getFormatStyle() != null ? valueAxisFormatter.getFormatStyle().getFontStyle() : FontStyleEnum.NORMAL;
			oglValueLabelConfig.setFontStyle(FontStyle.valueOf(malFontStyle.toString()));
			oglChartSeriesConfig.setValueLabelConfig(oglValueLabelConfig);
		}
		MALSeriesColor seriesColor = componentColorSet.getSeriesColor(seriesColorIndex);
		oglChartSeriesConfig.setBaseColor(seriesColor.getBaseColor());
		oglChartSeriesConfig.setHighLightColor(ColorMap.toHex(seriesColor.getHighlightColor()));
		SeriesAnchorEnum seriesAnchor = malChartSeriesConfig.getAnchor();
		if (seriesAnchor == null) {
			seriesAnchor = SeriesAnchorEnum.Q1;
		}
		oglChartSeriesConfig.setAnchor(Quadrant.valueOf(seriesAnchor.toString()));
		return oglChartSeriesConfig;
	}

	/**
	 * @param malCustomValueGuidelineCfg
	 * @return
	 */
	private ValueAxisConfig createCustomValueAxisCfg(MALChartValueGuidelineConfig malCustomValueGuidelineCfg, MALChartComponentColorSet componentColorSet) {
		ValueAxisConfig customValueAxisConfig = new ValueAxisConfig();

		if (malCustomValueGuidelineCfg.getHeaderName() != null) {
			customValueAxisConfig.setName(malCustomValueGuidelineCfg.getHeaderName());
			MALFormatStyle valueHdrFormatStyle = malCustomValueGuidelineCfg.getHeaderFormatStyle();
			customValueAxisConfig.setFontColor(ColorMap.toHex(componentColorSet.getGuideLineLabelFontColor()));
			customValueAxisConfig.setFontSize(valueHdrFormatStyle.getFontSize());
			customValueAxisConfig.setFontStyle(FontStyle.valueOf(valueHdrFormatStyle.getFontStyle().toString()));
		}
		customValueAxisConfig.setScale(Scale.valueOf(malCustomValueGuidelineCfg.getScale().toString()));
		customValueAxisConfig.setNoOfVisualDivisions(malCustomValueGuidelineCfg.getDivision());
		if (malCustomValueGuidelineCfg.getRelativePosition() != null) {
			customValueAxisConfig.setRelativePosition(AxisPositioning.valueOf(customValueAxisConfig.getRelativePosition().toString()));
		}
		TickLabelConfig valueAxisTickLabelCfg = new TickLabelConfig();
		MALFormatStyle valueAxisLblFormatStyle = malCustomValueGuidelineCfg.getLabelFormatStyle();
		valueAxisTickLabelCfg.setFontColor(ColorMap.toHex(componentColorSet.getGuideLineValueLabelFontColor()));
		valueAxisTickLabelCfg.setFontSize(valueAxisLblFormatStyle.getFontSize());
		valueAxisTickLabelCfg.setFontStyle(FontStyle.valueOf(valueAxisLblFormatStyle.getFontStyle().toString()));
		customValueAxisConfig.setTickLabelConfig(valueAxisTickLabelCfg);
		return customValueAxisConfig;
	}

	@Override
	public DataRow[] getVisualizationHeaderData(MALComponent component, MALVisualization visualization, SeriesDataHolder seriesDataHolder, CategoryTicksGenerator categoryTicksGenerator, PresentationContext pCtx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException, DataException, PluginException {
		//header data row
		DataRow headerDataRow = new DataRow();
		//create the data config visitor
		DataConfigVisitor categoryDataConfigVisitor = getDefaultCategoryDataConfigVisitor();
		//create the category axis merger
		CategoryAxisMerger categoryAxisMerger = getCategoryAxisMerger(component, visualization);
		//create a map of ticks to data columns
		Map<CategoryTick,DataColumn> tickToDataColumnMap = new HashMap<CategoryTick, DataColumn>();
		//go through series configs in the visualization
		MALSeriesConfig[] seriesConfigs = getSeriesConfigs(visualization, null);
		for (MALSeriesConfig seriesConfig : seriesConfigs) {
			//get the series data
			List<Tuple> data = seriesDataHolder.getSeriesData(seriesConfig.getId());
			//generate the category axis for this series
			Map<CategoryTick, Tuple> buckets = categoryTicksGenerator.generate(logger, (MALTwoDimSeriesConfig) seriesConfig, categoryDataConfigVisitor, data, categoryAxisMerger, pCtx);
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG,"Generated "+buckets+" for "+URIHelper.getURI(seriesConfig));
			}
			//get the category data config
			MALChartCategoryDataConfig categoryDataConfig = getCategoryDataConfig((MALChartVisualization)visualization, (MALChartSeriesConfig) seriesConfig);
			//go over the buckets to generate the data columns
			for (Map.Entry<CategoryTick, Tuple> bucket : buckets.entrySet()) {
				CategoryTick categoryTick = bucket.getKey();
				//create the unique version of the category display value
				DataColumn dataColumn = DataConfigHandler.getInstance().getData(categoryTick.getId(), null, categoryTick.getDisplayValue(), categoryDataConfig, bucket.getValue(), true, categoryDataConfigVisitor, pCtx);
				tickToDataColumnMap.put(categoryTick, dataColumn);
			}
		}
		for (CategoryTick tick : categoryTicksGenerator.getTicks()) {
			headerDataRow.addDataColumn(tickToDataColumnMap.get(tick));
		}
		return new DataRow[]{headerDataRow};
	}

	protected CategoryAxisMerger getCategoryAxisMerger(MALComponent component, MALVisualization visualization) {
		return null;
	}

	protected DefaultDataConfigVisitor getDefaultCategoryDataConfigVisitor() {
		return new DefaultDataConfigVisitor(FormatterUtils.getDefaultNullCategoryImpl());
	}

	/**
	 * @param visualization
	 * @param malChartSeriesConfig
	 * @return
	 */
	protected MALChartCategoryDataConfig getCategoryDataConfig(MALChartVisualization visualization, MALChartSeriesConfig malChartSeriesConfig) {
		MALChartCategoryDataConfig categoryDataConfig = (MALChartCategoryDataConfig) malChartSeriesConfig.getCategoryDataConfig();
		if (categoryDataConfig == null) {
			categoryDataConfig = visualization.getSharedCategoryDataConfig();
		}
		return categoryDataConfig;
	}

	@Override
	public DataRow[] getVisualizationData(MALComponent component, MALVisualization visualization, SeriesDataHolder seriesDataHolder, CategoryTicksGenerator categoryTicksGenerator, PresentationContext pCtx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException, DataException, PluginException {
		List<DataRow> dataRows = new LinkedList<DataRow>();
		DataConfigVisitor categoryDataConfigVisitor = getDefaultCategoryDataConfigVisitor();
		//create the category axis merger
		CategoryAxisMerger categoryAxisMerger = getCategoryAxisMerger(component, visualization);
		//go through series configs in the visualization
		MALSeriesConfig[] seriesConfigs = getSeriesConfigs(visualization, null);
		for (int i = 0; i < seriesConfigs.length; i++) {
			MALChartSeriesConfig seriesConfig = (MALChartSeriesConfig) seriesConfigs[i];
			if (seriesDataHolder.contains(seriesConfig.getId()) == true) {
				//get the series data
				List<Tuple> data = seriesDataHolder.getSeriesData(seriesConfig.getId());
				//generate the category axis for this series
				Map<CategoryTick, Tuple> buckets = categoryTicksGenerator.generate(logger, seriesConfig, categoryDataConfigVisitor, data, categoryAxisMerger, pCtx);
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG,"Generated "+buckets+" for "+URIHelper.getURI(seriesConfig));
				}
				//create the series data row
				DataRow seriesDataRow = new DataRow();
				seriesDataRow.setId(Integer.toString(i + 1));
				seriesDataRow.setTemplateID(seriesConfig.getId());
				seriesDataRow.setTemplateType(RowConfigType.DATA);
				seriesDataRow.setVisualType(getChartRenderingType(visualization).toString());
				//get the value data configuration
				MALChartValueDataConfig valueDataConfig = (MALChartValueDataConfig) seriesConfig.getValueDataConfig()[0];
				//find the scale to use
				ScaleEnum scale = getScaleConfiguration((MALChartComponent) component, ((MALChartVisualization) visualization));
				//get the actual value field
				MALFieldMetaInfo valueField = pCtx.resolveFieldRef(valueDataConfig.getExtractor().getSourceField());
				//get the out put processor
				OutputFieldValueProcessor outputFldValProcessor = FormatterUtils.getDefaultNullValueDataHandler(component, valueField, scale);
				//go over the buckets to generate the data columns
				int tickCnt = 0;
				for (Map.Entry<CategoryTick, Tuple> bucket : buckets.entrySet()) {
					CategoryTick categoryTick = bucket.getKey();
					//create the unique version of the category display value
					DefaultDataConfigVisitor valueDataConfigVisitor = new DefaultDataConfigVisitor(pCtx.getVisualEvaluator(seriesConfig), outputFldValProcessor);
					DataColumn dataColumn = DataConfigHandler.getInstance().getData(categoryTick.getId(), seriesConfig.getId(), valueDataConfig, bucket.getValue(), false, valueDataConfigVisitor, pCtx);
					if (dataColumn.getBaseColor() == null) {
						dataColumn.setBaseColor(ColorMap.toHex(getDefaultBaseColor(component, visualization, pCtx, tickCnt)));
					}
					if (dataColumn.getHighLightColor() == null) {
						dataColumn.setHighLightColor(ColorMap.toHex(getDefaultHighlightColor(component, visualization, pCtx, tickCnt)));
					}
					seriesDataRow.addDataColumn(dataColumn);
					tickCnt++;
				}
				//add the series data row to the list
				dataRows.add(seriesDataRow);
			}
		}
		return dataRows.toArray(new DataRow[dataRows.size()]);
	}

	protected ScaleEnum getScaleConfiguration(MALChartComponent component, MALChartVisualization chartVisualization) {
		// scale is normal by default
		ScaleEnum scale = null;
		MALChartValueGuidelineConfig customValueGuidelineConfig = (MALChartValueGuidelineConfig) chartVisualization.getValueGuidelineConfig();
		if (customValueGuidelineConfig != null && customValueGuidelineConfig.getScale() != null) {
			scale = customValueGuidelineConfig.getScale();
		}
		if (scale == null) {
			MALChartValueGuidelineConfig stdValueGuidelineConfig = component.getValueGuidelineConfig();
			scale = stdValueGuidelineConfig.getScale();
		}
		if (scale == null) {
			// if no scale then we keep normal
			scale = ScaleEnum.NORMAL;
		}
		return scale;
	}

	@Override
	public String getHeaderRowTemplateID(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext ctx) {
		return "categoryaxisvalue";
	}

	@Override
	public String getHeaderRowVisualType(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext ctx) {
		return "categoryaxis";
	}

	protected TypeSpecificAttribute createTypeSpecificAttribute(String name, String value) {
		TypeSpecificAttribute typeSpecificAttribute = new TypeSpecificAttribute();
		typeSpecificAttribute.setName(name);
		typeSpecificAttribute.setContent(value);
		return typeSpecificAttribute;
	}

	/**
	 * @param visualization
	 * @return
	 */
	protected abstract ChartRenderingType getChartRenderingType(MALVisualization visualization);

	/**
	 * @param visualization
	 * @param ctx
	 * @return
	 * @throws MALException
	 */
	protected abstract TypeSpecificAttribute[] getTypeSpecificAttributes(MALComponent component, MALVisualization visualization, PresentationContext ctx) throws MALException;

	protected String getDefaultBaseColor(MALComponent component, MALVisualization visualization, PresentationContext ctx, int increment) throws MALException {
		return null;
	}

	protected String getDefaultHighlightColor(MALComponent component, MALVisualization visualization, PresentationContext ctx, int increment) throws MALException {
		return null;
	}

	@Override
	protected void init() {
	}

	@Override
	protected void shutdown() throws NonFatalException {
	}

	Logger getLogger(){
		return logger;
	}

	Properties getProperties(){
		return properties;
	}

}