package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.plugin.internal.vizengine.formatters.FormatterUtils;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALFormatStyle;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextCategoryDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextCategoryGuidelineConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextComponentColorSet;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextValueDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextValueGuidelineConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.types.FieldAlignmentEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.FontStyleEnum;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.BorderConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ColumnConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.RowConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationSeriesContainer;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ColumnAlignment;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ColumnType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.FontStyle;
import com.tibco.cep.dashboard.psvr.ogl.model.types.RowConfigType;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTick;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTicksGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.DataConfigHandler;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandler;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGeneratorFactory;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigUtils;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.ColorMap;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DataConfigVisitor;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DefaultDataConfigVisitor;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.OutputFieldValueProcessor;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * @author apatil
 *
 */
public class TextVisualizationHandler extends VisualizationHandler {

	protected static final String HEADER_ROW_CONFIG_ID = "1";
	protected static final String HEADER_ROW_VISUAL_TYPE = "text";

	protected static final String DATA_ROW_CONFIG_ID = "2";
	protected static final String DATA_ROW_VISUAL_TYPE = "text";

	protected static final String CATEGORY_COL_ID = "0";

	private static final DefaultDataConfigVisitor DEFAULT_DATA_CONFIG_VISITOR = new DefaultDataConfigVisitor(FormatterUtils.getDefaultNullCategoryImpl());

	@Override
	public VisualizationSeriesContainer[] getVisualizationSeriesContainers(MALComponent component, MALVisualization visualization, PresentationContext pCtx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException, PluginException {
		MALTextComponentColorSet componentColorSet = (MALTextComponentColorSet) pCtx.getViewsConfigHelper().getComponentColorSet(component, component.getComponentColorSetIndex());
		MALTextVisualization textVisualization = (MALTextVisualization) visualization;
		BorderConfig dataColumnBorderConfig = null;
		String headerTextFontColor = null;
		String cellTextFontColor = null;
		if (componentColorSet != null) {
			headerTextFontColor = componentColorSet.getHeaderTextFontColor();
			dataColumnBorderConfig = new BorderConfig();
			dataColumnBorderConfig.setBottom(componentColorSet.getRowSeparatorColor());
			cellTextFontColor = componentColorSet.getCellTextFontColor();
		}

		RowConfig hdrRowConfig = null;
		boolean showHeader = textVisualization.getShowHeader();
		if (showHeader == true) {
			// we need to create a header config
			hdrRowConfig = new RowConfig();
			hdrRowConfig.setId(HEADER_ROW_CONFIG_ID);
			hdrRowConfig.setTemplateType(RowConfigType.HEADER);
			hdrRowConfig.setVisualType(HEADER_ROW_VISUAL_TYPE);
			if (componentColorSet != null) {
				hdrRowConfig.setBackground(componentColorSet.getHeaderColor());
			}
		}
		RowConfig dataRowConfig = new RowConfig();
		dataRowConfig.setId(DATA_ROW_CONFIG_ID);
		dataRowConfig.setTemplateType(RowConfigType.DATA);
		dataRowConfig.setVisualType(DATA_ROW_VISUAL_TYPE);
		if (componentColorSet != null) {
			dataRowConfig.setBackground(componentColorSet.getCellColor());
			dataRowConfig.setRollover(componentColorSet.getRowRollOverColor());
			dataRowConfig.setPress(componentColorSet.getCellColor());
			dataRowConfig.setSelectedRollover(componentColorSet.getRowRollOverColor());
			dataRowConfig.setSelectedPress(componentColorSet.getCellColor());
		}

		MALTextCategoryGuidelineConfig categoryGuidelineCfg = (MALTextCategoryGuidelineConfig) textVisualization.getCategoryGuidelineConfig();

		if (categoryGuidelineCfg != null) {
			FieldAlignmentEnum categoryHdrAlignment = categoryGuidelineCfg.getHeaderAlignment();
			String categoryHdrWidth = categoryGuidelineCfg.getWidth();
			if (showHeader == true) {
				// add category header column config to header row config
				MALFormatStyle headerFormatStyle = categoryGuidelineCfg.getHeaderFormatStyle();

				ColumnConfig categoryHdrColumnCfg = createColumnConfig(CATEGORY_COL_ID, categoryHdrAlignment, headerFormatStyle, categoryHdrWidth, headerTextFontColor);
				hdrRowConfig.addColumnConfig(categoryHdrColumnCfg);
			}
			// lets add the category value column config to data row config
			MALFormatStyle labelFormatStyle = categoryGuidelineCfg.getLabelFormatStyle();

			ColumnConfig categoryDataColumnCfg = createColumnConfig(CATEGORY_COL_ID, categoryGuidelineCfg.getLabelAlignment(), labelFormatStyle, categoryHdrWidth, cellTextFontColor);
			if (dataColumnBorderConfig != null) {
				categoryDataColumnCfg.setBorderConfig(dataColumnBorderConfig);
			}
			dataRowConfig.addColumnConfig(categoryDataColumnCfg);
		}
		MALTextValueGuidelineConfig valueGuidelineConfig = (MALTextValueGuidelineConfig) textVisualization.getValueGuidelineConfig();

		MALFormatStyle defaultHeaderFormatStyle = null;
		if (valueGuidelineConfig != null) {
			defaultHeaderFormatStyle = valueGuidelineConfig.getHeaderFormatStyle();
		}

		FieldAlignmentEnum valueHdrAlignment = null;
		if (valueGuidelineConfig != null) {
			valueHdrAlignment = valueGuidelineConfig.getHeaderAlignment();
		}

		int valueColumnCfgID = 1;
		MALSeriesConfig[] textSeriesConfigs = textVisualization.getSeriesConfig();
		ActionConfigGenerator[] seriesActionCfgGenerators = null;
		HashMap<String, String> dynParamSubMap = new HashMap<String, String>();
		dynParamSubMap.put(ActionConfigGenerator.CURRENTCOMPONENT_ID_DYN_PARAM, component.getId());
		// for text visualization the datarow id is the category value
		// TODO improve the datacolumn design to include category value in it or some other better design
		dynParamSubMap.put(ActionConfigGenerator.CURRENTDATACOLUMN_ID_DYN_PARAM, ActionConfigGenerator.CURRENTDATAROW_ID_DYN_PARAM);

		for (int i = 0; i < textSeriesConfigs.length; i++) {
			MALTextSeriesConfig seriesConfig = (MALTextSeriesConfig) textSeriesConfigs[i];
			if (i == 0 && component.getDefinitionType().equals("TextComponent") == true) {
				seriesActionCfgGenerators = ActionConfigGeneratorFactory.getInstance().getGenerators(seriesConfig);
			}
			MALDataConfig[] valueDataConfigs = seriesConfig.getValueDataConfig();
			for (int j = 0; j < valueDataConfigs.length; j++) {
				MALTextValueDataConfig valueDataConfig = (MALTextValueDataConfig) valueDataConfigs[j];
				// INFO we compensate the column config id for presence of category column (always)
				if (showHeader == true) {
					// header column config
					MALFormatStyle headerFormatStyle = valueDataConfig.getHeaderFormatStyle();
					if (headerFormatStyle == null) {
						headerFormatStyle = defaultHeaderFormatStyle;
					}
					ColumnConfig valueHdrColumnCfg = createColumnConfig(Integer.toString(valueColumnCfgID), valueHdrAlignment, headerFormatStyle, valueDataConfig.getWidth(), headerTextFontColor);
					hdrRowConfig.addColumnConfig(valueHdrColumnCfg);
				}
				// data column config
				ColumnConfig valueDataColumnCfg = DataConfigHandler.getInstance().getColumnConfig(Integer.toString(valueColumnCfgID), seriesConfig, valueDataConfig, pCtx);
				// INFO we need to set width and alignment outside of the DataConfigHandler, since DataConfigHandler handles the generic DataConfig
				valueDataColumnCfg.setWidth(valueDataConfig.getWidth().endsWith("%") ? valueDataConfig.getWidth() : valueDataConfig.getWidth() + "%");
				valueDataColumnCfg.setAlign(ColumnAlignment.valueOf(valueDataConfig.getAlignment().toString()));
				valueDataColumnCfg.setFontColor(ColorMap.toHex(cellTextFontColor));
				if (dataColumnBorderConfig != null) {
					valueDataColumnCfg.setBorderConfig(dataColumnBorderConfig);
				}
				if (pCtx.generateAdditionalOutputs() == true && seriesActionCfgGenerators != null) {
					// Anand - Added on 11/23/06 bug # 6521 - START
					if (component.getVisualizationCount() == 1) {
						dynParamSubMap.put(ActionConfigGenerator.CURRENTSERIES_ID_DYN_PARAM, seriesConfig.getId());
					}
					// Anand - Added on 11/23/06 bug # 6521 - START
					List<ActionConfigType> actionConfigs = new LinkedList<ActionConfigType>();
					for (ActionConfigGenerator seriesActionCfgGenerator : seriesActionCfgGenerators) {
						actionConfigs.addAll(seriesActionCfgGenerator.generateActionConfigs(seriesConfig, dynParamSubMap, pCtx));
					}
					valueDataColumnCfg.setActionConfig(ActionConfigUtils.createActionConfigSet("ROOT", false, actionConfigs));
				}
				dataRowConfig.addColumnConfig(valueDataColumnCfg);
				valueColumnCfgID++;
			}
		}
		if (hdrRowConfig == null) {
			return new VisualizationSeriesContainer[] { dataRowConfig };
		}
		return new VisualizationSeriesContainer[] { hdrRowConfig, dataRowConfig };
	}

	/**
	 * @param colConfigID
	 * @param fieldAlignment
	 * @param formatStyle
	 * @param categoryGuidelineCfg
	 * @param width
	 * @param fontColor
	 * @return
	 */
	private ColumnConfig createColumnConfig(String colConfigID, FieldAlignmentEnum fieldAlignment, MALFormatStyle formatStyle, String width, String fontColor) {
		ColumnConfig columnConfig = new ColumnConfig();
		columnConfig.setId(colConfigID);
		columnConfig.setType(ColumnType.TEXT);
		if (fieldAlignment == null) {
			columnConfig.setAlign(ColumnAlignment.CENTER);
		} else {
			columnConfig.setAlign(ColumnAlignment.valueOf(fieldAlignment.toString()));
		}
		columnConfig.setFontColor(ColorMap.toHex(fontColor));
		columnConfig.setFontSize(formatStyle.getFontSize());
		columnConfig.setFontStyle(FontStyle.valueOf(formatStyle.getFontStyle().toString()));
		if (width.endsWith("%") == true) {
			columnConfig.setWidth(width);
		} else {
			columnConfig.setWidth(width + "%");
		}
		return columnConfig;
	}

	@Override
	public DataRow[] getVisualizationHeaderData(MALComponent component, MALVisualization visualization, SeriesDataHolder seriesDataHolder, CategoryTicksGenerator categoryTicksGenerator, PresentationContext pCtx)
			throws VisualizationException {
		MALTextVisualization textVisualization = (MALTextVisualization) visualization;
		boolean showHeader = textVisualization.getShowHeader();
		if (showHeader == false) {
			return null;
		}
		List<DataColumn> dataColumns = new LinkedList<DataColumn>();
		MALTextValueGuidelineConfig valueGuidelineCfg = (MALTextValueGuidelineConfig) textVisualization.getValueGuidelineConfig();
		MALFormatStyle HACKEDValueColHdrStyle = null;
		if (valueGuidelineCfg != null) {
			HACKEDValueColHdrStyle = valueGuidelineCfg.getHeaderFormatStyle();
		}
		MALTextCategoryGuidelineConfig categoryGuidelineCfg = (MALTextCategoryGuidelineConfig) textVisualization.getCategoryGuidelineConfig();
		if (categoryGuidelineCfg != null) {
			DataColumn categoryHdrDataColumn = new DataColumn();
			categoryHdrDataColumn.setId(CATEGORY_COL_ID);
			String headerName = categoryGuidelineCfg.getHeaderName();
			categoryHdrDataColumn.setDisplayValue(getHACKEDDisplayValue(categoryGuidelineCfg.getHeaderFormatStyle().getFontStyle(), headerName));
			categoryHdrDataColumn.setValue(headerName);
			categoryHdrDataColumn.setTooltip(headerName);
			dataColumns.add(categoryHdrDataColumn);
		}
		int valueHdrDataColumnID = 1;
		MALSeriesConfig[] textSeriesConfigs = textVisualization.getSeriesConfig();
		for (int i = 0; i < textSeriesConfigs.length; i++) {
			MALTextSeriesConfig seriesConfig = (MALTextSeriesConfig) textSeriesConfigs[i];
			MALDataConfig[] valueDataConfigs = seriesConfig.getValueDataConfig();
			for (int j = 0; j < valueDataConfigs.length; j++) {
				MALTextValueDataConfig valueDataConfig = (MALTextValueDataConfig) valueDataConfigs[j];
				DataColumn valueHdrDataColumn = new DataColumn();
				String headerName = valueDataConfig.getHeaderName();
				MALFormatStyle ANOTHERHACKEDHeaderFormatStyle = valueDataConfig.getHeaderFormatStyle();
				if (ANOTHERHACKEDHeaderFormatStyle == null) {
					ANOTHERHACKEDHeaderFormatStyle = HACKEDValueColHdrStyle;
				}
				if (ANOTHERHACKEDHeaderFormatStyle != null) {
					valueHdrDataColumn.setDisplayValue(getHACKEDDisplayValue(ANOTHERHACKEDHeaderFormatStyle.getFontStyle(), headerName));
				} else {
					valueHdrDataColumn.setDisplayValue(headerName);
				}
				valueHdrDataColumn.setId(Integer.toString(valueHdrDataColumnID));
				valueHdrDataColumn.setTooltip(headerName);
				valueHdrDataColumn.setValue(headerName);
				dataColumns.add(valueHdrDataColumn);
				valueHdrDataColumnID++;
			}
		}
		return EngineUtils.convertToSingleItemDataRowArray(dataColumns.toArray(new DataColumn[dataColumns.size()]));// ;
	}

	private String getHACKEDDisplayValue(FontStyleEnum fontStyle, String displayValue) {
		if (displayValue == null) {
			return displayValue;
		}
		return displayValue;
		// switch (fontStyle.getType()){
		// case FontStyleEnum.BOLD_TYPE :
		// return "<b>"+displayValue+"</b>";
		// case FontStyleEnum.ITALIC_TYPE :
		// return "<i>"+displayValue+"</i>";
		// case FontStyleEnum.BOLD_ITALIC_TYPE :
		// return "<b><i>"+displayValue+"</i></b>";
		// case FontStyleEnum.NORMAL_TYPE :
		// return displayValue;
		// default :
		// return displayValue;
		// }
	}

	@Override
	public DataRow[] getVisualizationData(MALComponent component, MALVisualization visualization, SeriesDataHolder seriesDataHolder, CategoryTicksGenerator categoryTicksGenerator, PresentationContext pCtx)
			throws VisualizationException, IOException, MALException, ElementNotFoundException, DataException, PluginException {
		LinkedHashMap<CategoryTick, DataRow> categoryTickToDataRowMap = new LinkedHashMap<CategoryTick, DataRow>();
		MALTextVisualization textVisualization = (MALTextVisualization) visualization;
		// get the affected series configs
		List<MALSeriesConfig> affectedSeriesConfigs = getAffectedSeriesConfig(textVisualization, seriesDataHolder);
		Iterator<MALSeriesConfig> affectedSeriesConfigsIter = affectedSeriesConfigs.iterator();
		while (affectedSeriesConfigsIter.hasNext()) {
			// go over each affected series config
			MALTextSeriesConfig seriesConfig = (MALTextSeriesConfig) affectedSeriesConfigsIter.next();
			String seriesCfgID = seriesConfig.getId();
			if (component.getVisualizationCount() > 1) {
				pCtx.addAttribute(ActionConfigGenerator.CURRENTSERIES_ID_DYN_PARAM, seriesCfgID);
			}
			// get the data for the series config
			List<Tuple> tuples = seriesDataHolder.getSeriesData(seriesCfgID);
			// generate the category axis
			Map<CategoryTick, Tuple> buckets = categoryTicksGenerator.generate(logger, seriesConfig, DEFAULT_DATA_CONFIG_VISITOR, tuples, null, pCtx);
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG,"Generated "+buckets+" for "+URIHelper.getURI(seriesConfig));
			}
			// find the value column index for the series config
			int valueColumnIndex = getValueColumnStartIndex(textVisualization, seriesConfig, pCtx);
			// get the category data configuration for the series config (category data configuration can be null for multi-visualization text component)
			MALTextCategoryDataConfig categoryDataConfig = (MALTextCategoryDataConfig) seriesConfig.getCategoryDataConfig();
			// get the value data configurations for the series config
			MALDataConfig[] valueDataConfigs = seriesConfig.getValueDataConfig();
			int noOfValueColumns = valueDataConfigs.length;
			//now iterate over the buckets
			for (Map.Entry<CategoryTick, Tuple> bucket : buckets.entrySet()) {
				CategoryTick categoryTick = bucket.getKey();
				Tuple tuple = bucket.getValue();
				//see if a data row already exists for the tick
				DataRow dataRow = categoryTickToDataRowMap.get(categoryTick);
				DataColumn categoryDataCol = null;
				if (dataRow == null) {
					//we did not find a data row for the tick , so we create one
					dataRow = new DataRow();
					dataRow.setId(categoryTick.getId());
					dataRow.setTemplateID(DATA_ROW_CONFIG_ID);
					dataRow.setTemplateType(RowConfigType.DATA);
					dataRow.setVisualType(DATA_ROW_VISUAL_TYPE);
					if (categoryDataConfig != null) {
						categoryDataCol = DataConfigHandler.getInstance().getData(CATEGORY_COL_ID, null, categoryTick.getDisplayValue(), categoryDataConfig, tuple, true, DEFAULT_DATA_CONFIG_VISITOR, pCtx);
						dataRow.addDataColumn(categoryDataCol);
					}
					//put the data row in the map against the tick
					categoryTickToDataRowMap.put(categoryTick, dataRow);
				}
				for (int j = 0; j < noOfValueColumns; j++) {
					MALDataConfig valueDataConfig = valueDataConfigs[j];
					MALFieldMetaInfo valueSrcFld = pCtx.resolveFieldRef(valueDataConfig.getExtractor().getSourceField());
					OutputFieldValueProcessor outputFldValProccesor = FormatterUtils.getDefaultNullValueDataHandler(component, valueSrcFld, null);
					DataConfigVisitor dataConfigVisitor = new DefaultDataConfigVisitor(pCtx.getVisualEvaluator(seriesConfig), outputFldValProccesor);
					DataColumn valueDataColumn = DataConfigHandler.getInstance().getData(Integer.toString(valueColumnIndex), null, valueDataConfig, tuple, false, dataConfigVisitor, pCtx);
//						MALDataFormat formatter = valueDataConfig.getFormatter();
//						if (formatter != null && formatter.getFormatStyle() != null) {
//							valueDataColumn.setDisplayValue(getHACKEDDisplayValue(formatter.getFormatStyle().getFontStyle(), valueDataColumn.getDisplayValue()));
//						}
					// Anand - Added on 11/23/06 bug # 6521 - START
					if (component.getVisualizationCount() > 1) {
						TypeSpecificAttribute typeSpecificAttribute = new TypeSpecificAttribute();
						typeSpecificAttribute.setName("seriesid");
						typeSpecificAttribute.setContent(seriesCfgID);
						valueDataColumn.addTypeSpecificAttribute(typeSpecificAttribute);
					}
					// Anand - Added on 11/23/06 bug # 6521 - START
					dataRow.addDataColumn(valueDataColumn);
					valueColumnIndex++;
				}
				// Anand - Added on 10/17/2007 to fix bug # 8047 - START
//				if (categoryDataCol != null && categoryDataCol.getLink() == null) {
//					MALTextSeriesConfig firstSeriesConfig = (MALTextSeriesConfig) component.getVisualization(0).getSeriesConfig(0);
//					if (firstSeriesConfig == seriesConfig) {
//						categoryDataCol.setLink(dataRow.getDataColumn(1).getLink());
//					} else {
//						MALVisualization[] visualizations = component.getVisualization();
//						for (int i = 0; i < visualizations.length; i++) {
//							MALSeriesConfig[] seriesConfigs = visualizations[i].getSeriesConfig();
//							for (int k = 0; k < seriesConfigs.length; k++) {
//								String categoryLink = generateCategoryLink(component, (MALTextSeriesConfig) seriesConfigs[k], categoryTick.getValue(), pCtx);
//								if (categoryLink != null) {
//									categoryDataCol.setLink(categoryLink);
//									i = visualizations.length;
//									break;
//								}
//							}
//						}
//					}
//				}
				// Anand - Added on 10/17/2007 to fix bug # 8047 - END
				valueColumnIndex = valueColumnIndex - noOfValueColumns;
			}
		}
		return categoryTickToDataRowMap.values().toArray(new DataRow[categoryTickToDataRowMap.size()]);
	}

	// Anand - Added on 10/17/2007 to fix bug # 8047
	protected String generateCategoryLink(MALComponent component, MALTextSeriesConfig seriesConfig, String categoryValue, PresentationContext ctx) throws VisualizationException, MALException, ElementNotFoundException,
			IOException, DataException {
		DataSourceHandler dataSourceHandler = ctx.getDataSourceHandler(seriesConfig);
		List<Tuple> seriesData = dataSourceHandler.getData(ctx);
		Iterator<Tuple> seriesDataIterator = seriesData.iterator();
		while (seriesDataIterator.hasNext()) {
			Tuple tuple = seriesDataIterator.next();
			String currCategoryValue = DataConfigHandler.getInstance().getDisplayValue(seriesConfig.getCategoryDataConfig(), tuple, new DefaultDataConfigVisitor(FormatterUtils.getDefaultNullCategoryImpl()), ctx);
			if (categoryValue.equals(currCategoryValue) == true) {
				MALDataConfig valueDataConfig = seriesConfig.getValueDataConfig(0);
				MALFieldMetaInfo valueSrcFld = ctx.resolveFieldRef(valueDataConfig.getExtractor().getSourceField());
				OutputFieldValueProcessor outputFldValProccesor = FormatterUtils.getDefaultNullValueDataHandler(component, valueSrcFld, null);
				DataColumn valueDataColumn = DataConfigHandler.getInstance().getData(Integer.toString(0), null, valueDataConfig, tuple, false, new DefaultDataConfigVisitor(outputFldValProccesor), ctx);
				return valueDataColumn.getLink();
			}
		}
		return null;
	}

	/**
	 * @param textVisualization
	 * @param seriesConfig
	 * @param pCtx
	 * @return
	 * @throws VisualizationException
	 */
	protected int getValueColumnStartIndex(MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext pCtx) throws VisualizationException {
		int valueColumnIndex = 1;
		MALSeriesConfig[] seriesConfigs = visualization.getSeriesConfig();
		for (int i = 0; i < seriesConfigs.length; i++) {
			MALTextSeriesConfig config = (MALTextSeriesConfig) seriesConfigs[i];
			if (config.getId().equals(seriesConfig.getId()) == true) {
				return valueColumnIndex;
			}
			valueColumnIndex = valueColumnIndex + config.getValueDataConfigCount();
		}
		throw new VisualizationException(getMessage("visualizationhandler.chart.seriescfg.notfound", pCtx.getLocale(), pCtx.getMessageGeneratorArgs(null, seriesConfig.getName(), URIHelper.getURI(visualization))));
	}

	/**
	 * @param textVisualization
	 * @param seriesDataHolder
	 * @return
	 */
	protected List<MALSeriesConfig> getAffectedSeriesConfig(MALVisualization textVisualization, SeriesDataHolder seriesDataHolder) {
		List<MALSeriesConfig> affectedSeriesConfigs = new LinkedList<MALSeriesConfig>();
		MALSeriesConfig[] seriesConfigs = textVisualization.getSeriesConfig();
		for (int j = 0; j < seriesConfigs.length; j++) {
			MALSeriesConfig seriesConfig = seriesConfigs[j];
			if (seriesDataHolder.contains(seriesConfig.getId()) == true) {
				affectedSeriesConfigs.add(seriesConfig);
			}
		}
		return affectedSeriesConfigs;
	}

	@Override
	public String getHeaderRowTemplateID(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext ctx) {
		return HEADER_ROW_CONFIG_ID;
	}

	@Override
	public String getHeaderRowVisualType(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext ctx) {
		return HEADER_ROW_VISUAL_TYPE;
	}

	String getDateRowTemplateID(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext ctx) {
		return DATA_ROW_CONFIG_ID;
	}

	String getDataRowVisualType(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext ctx) {
		return DATA_ROW_VISUAL_TYPE;
	}

	@Override
	protected void init() {
	}

	@Override
	protected void shutdown() {
	}
}