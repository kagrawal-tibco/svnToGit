package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.plugin.internal.vizengine.formatters.FormatterUtils;
import com.tibco.cep.dashboard.psvr.alerts.AlertEvaluator;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALIndicatorFieldFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALOneDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALProgressBarFieldFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALStateVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.ActivityConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ColumnConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationSeriesContainer;
import com.tibco.cep.dashboard.psvr.ogl.model.types.RowConfigType;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTicksGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.DataConfigHandler;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandler;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGeneratorFactory;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigUtils;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DefaultDataConfigVisitor;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.OutputFieldValueProcessor;
import com.tibco.cep.designtime.core.model.states.State;

public class StateVisualizationHandler extends VisualizationHandler {

	private static final String DATA_ROW_CONFIG_ID = "1";

	private static final String DATA_ROW_VISUAL_TYPE = "activity";

	private static final String FLAG_DATA_COLUMN_CONFIG_ID = "1";

	private static final String CONTENT_DATA_COLUMN_CONFIG_ID = "2";

	@Override
	protected void init() {
	}

	@Override
	public String getHeaderRowTemplateID(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext ctx) {
		throw new UnsupportedOperationException("getHeaderRowTemplateID");
	}

	@Override
	public String getHeaderRowVisualType(MALComponent component, MALVisualization visualization, MALSeriesConfig seriesConfig, PresentationContext ctx) {
		throw new UnsupportedOperationException("getHeaderRowVisualType");
	}

	@Override
	public VisualizationSeriesContainer[] getVisualizationSeriesContainers(MALComponent component, MALVisualization visualization, PresentationContext ctx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException, PluginException {

		MALStateVisualization stateVisualization = (MALStateVisualization) visualization;
		State state = StateMachineIndex.getInstance(ctx).findState(stateVisualization.getStateRefID());

		ActivityConfig activityConfig = new ActivityConfig();
		String stateTitle = stateVisualization.getDisplayName();
		if (StringUtil.isEmptyOrBlank(stateTitle) == true) {
			stateTitle = state.getName();
		}
		activityConfig.setTitle(stateTitle);
		activityConfig.setActivityID(state.getGUID());

		boolean indicatorAdded = false;
		boolean contentAdded = false;
		MALSeriesConfig[] seriesConfigs = stateVisualization.getSeriesConfig();
		for (MALSeriesConfig seriesConfig : seriesConfigs) {
			MALOneDimSeriesConfig oneDimSeriesConfig = (MALOneDimSeriesConfig) seriesConfig;
			MALDataConfig[] valueDataConfigs = oneDimSeriesConfig.getValueDataConfig();
			if (valueDataConfigs.length > 2) {
				MessageGeneratorArgs args = ctx.getMessageGeneratorArgs(null, URIHelper.getURI(seriesConfig));
				throw new VisualizationException(getMessage("visualizationhandler.activity.invalidcolumnconfigcount", ctx.getLocale(), args));
			}
			for (MALDataConfig dataConfig : valueDataConfigs) {
				// data column config
				String dataColTypeId = getColumnID(dataConfig);
				if (dataColTypeId == FLAG_DATA_COLUMN_CONFIG_ID) {
					if (indicatorAdded == true) {
						MessageGeneratorArgs args = ctx.getMessageGeneratorArgs(null, URIHelper.getURI(seriesConfig));
						throw new VisualizationException(getMessage("visualizationhandler.activity.multipleindicatordefinitions", ctx.getLocale(), args));
					} else {
						indicatorAdded = true;
					}
				} else if (dataColTypeId == CONTENT_DATA_COLUMN_CONFIG_ID) {
					if (contentAdded == true) {
						MessageGeneratorArgs args = ctx.getMessageGeneratorArgs(null, URIHelper.getURI(seriesConfig));
						throw new VisualizationException(getMessage("visualizationhandler.activity.multiplecontentdefinitions", ctx.getLocale(), args));
					} else {
						contentAdded = true;
					}
				}
				String columnID = state.getGUID() + "@" + dataColTypeId;
				ColumnConfig valueDataColumnCfg = DataConfigHandler.getInstance().getColumnConfig(columnID, seriesConfig, dataConfig, ctx);
				if (ctx.generateAdditionalOutputs() == true) {
			        HashMap<String, String> dynParamSubMap = new HashMap<String, String>();
			        dynParamSubMap.put(ActionConfigGenerator.CURRENTCOMPONENT_ID_DYN_PARAM, component.getId());
			        dynParamSubMap.put(ActionConfigGenerator.CURRENTSERIES_ID_DYN_PARAM, seriesConfig.getId());
					ActionConfigGenerator[] seriesActionCfgGenerators = ActionConfigGeneratorFactory.getInstance().getGenerators(seriesConfig);
					List<ActionConfigType> actionConfigs = new LinkedList<ActionConfigType>();
					for (ActionConfigGenerator seriesActionCfgGenerator : seriesActionCfgGenerators) {
						actionConfigs.addAll(seriesActionCfgGenerator.generateActionConfigs(seriesConfig, dynParamSubMap, ctx));
					}
					valueDataColumnCfg.setActionConfig(ActionConfigUtils.createActionConfigSet("ROOT", false, actionConfigs));
				}
				activityConfig.addColumnConfig(valueDataColumnCfg);
			}
		}
        if (ctx.generateAdditionalOutputs() == true){
        	ActionConfigGenerator[] actionCfgGenerators = ActionConfigGeneratorFactory.getInstance().getGenerators(visualization);
            List<ActionConfigType> actionConfigs = new LinkedList<ActionConfigType>();
            for (ActionConfigGenerator actionCfgGenerator : actionCfgGenerators) {
            	actionConfigs.addAll(actionCfgGenerator.generateActionConfigs(visualization, null, ctx));
			}
            activityConfig.setActionConfig(ActionConfigUtils.createActionConfigSet("ROOT", false, actionConfigs));
        }
		return new VisualizationSeriesContainer[] { activityConfig };
	}

	/**
	 * @param valueDataConfig
	 * @return
	 */
	private String getColumnID(MALDataConfig valueDataConfig) {
		MALDataFormat formatter = valueDataConfig.getFormatter();
		// we have to handle each formatter type in the specified sequence
		// since progressbar extends indicator which extends text
		if (formatter instanceof MALProgressBarFieldFormat) {
			return CONTENT_DATA_COLUMN_CONFIG_ID;
		} else if (formatter instanceof MALIndicatorFieldFormat) {
			return FLAG_DATA_COLUMN_CONFIG_ID;
		}
		return CONTENT_DATA_COLUMN_CONFIG_ID;
	}

	@Override
	public DataRow[] getVisualizationHeaderData(MALComponent component, MALVisualization visualization, SeriesDataHolder seriesDataHolder, CategoryTicksGenerator categoryTicksGenerator, PresentationContext ctx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException {
		return null;
	}

	@Override
	public DataRow[] getVisualizationData(MALComponent component, MALVisualization visualization, SeriesDataHolder seriesDataHolder, CategoryTicksGenerator categoryTicksGenerator, PresentationContext ctx) throws VisualizationException, IOException, MALException,
			ElementNotFoundException, DataException {

		MALStateVisualization stateVisualization = (MALStateVisualization) visualization;

		DataRow activityDataRow = new DataRow();
		activityDataRow.setId(stateVisualization.getStateRefID());
		activityDataRow.setTemplateID(DATA_ROW_CONFIG_ID);
		activityDataRow.setTemplateType(RowConfigType.DATA);
		activityDataRow.setVisualType(DATA_ROW_VISUAL_TYPE);

		Iterator<String> affectedSeriesCfgIdsIterator = seriesDataHolder.getSeriesCfgIds();
		while (affectedSeriesCfgIdsIterator.hasNext()) {
			String affectedSeriesCfgId = (String) affectedSeriesCfgIdsIterator.next();
			MALOneDimSeriesConfig affectedSeriesCfg = null;
			for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
				if (seriesConfig.getId().equals(affectedSeriesCfgId) == true){
					affectedSeriesCfg = (MALOneDimSeriesConfig) seriesConfig;
					break;
				}

			}
			if (affectedSeriesCfg != null) {
				MALDataConfig[] valueDataConfigs = affectedSeriesCfg.getValueDataConfig();
				if (valueDataConfigs.length > 2) {
					MessageGeneratorArgs args = ctx.getMessageGeneratorArgs(null, URIHelper.getURI(affectedSeriesCfg));
					throw new VisualizationException(getMessage("visualizationhandler.activity.invalidcolumnconfigcount", ctx.getLocale(), args));
				}
				List<Tuple> dataset = seriesDataHolder.getSeriesData(affectedSeriesCfgId);
				Tuple tuple = null;
				for (Tuple data : dataset) {
					if (tuple == null || tuple.getTimestamp() < data.getTimestamp()){
						tuple = data;
					}
				}
				if (tuple != null){
					AlertEvaluator visualEvaluator = ctx.getVisualEvaluator(affectedSeriesCfg);
					for (MALDataConfig dataConfig : valueDataConfigs) {
	                    MALFieldMetaInfo sourceField = ctx.resolveFieldRef(dataConfig.getExtractor().getSourceField());
	                    OutputFieldValueProcessor outputFldValProccesor = FormatterUtils.getDefaultNullValueDataHandler(component,sourceField,null);
		                String columnID = stateVisualization.getStateRefID()+"@"+getColumnID(dataConfig);
						DataColumn valueDataColumn = DataConfigHandler.getInstance().getData(columnID,null,dataConfig,tuple,false, new DefaultDataConfigVisitor(visualEvaluator,outputFldValProccesor), ctx);
		                activityDataRow.addDataColumn(valueDataColumn);
					}
				}
			}
		}
        if (activityDataRow.getDataColumnCount() == 0){
            return null;
        }
        return new DataRow[]{activityDataRow};

	}

	@Override
	protected void shutdown() throws NonFatalException {
	}

}