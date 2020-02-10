package com.tibco.cep.dashboard.plugin.beviews.vizengine.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.CommandType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigUtils;

public abstract class MetricComponentActionConfigGenerator extends ActionConfigGenerator {

	private static final String CURRENTCOMPONENTMODEL_HELP_DYNPNAME = "currentcomponentmodel.help";

	protected ActionConfigType removeAction;
	protected ActionConfigType opticalZoomAction;
	protected ActionConfigType enabledDescriptionAction;
	protected ActionConfigType disabledDescriptionAction;

	@Override
	protected void init() {
		String[] componentIDDynParam = new String[] { "componentid", CURRENTCOMPONENT_ID_DYN_PARAM };

		String[][] removeDynamicParams = new String[][] { new String[] { "pageid", CURRENTPAGE_ID_DYN_PARAM }, new String[] { "panelid", CURRENTPANEL_ID_DYN_PARAM }, componentIDDynParam };
		removeAction = ActionConfigUtils.createActionConfig("Remove", CommandType.DELETE, false, new String[][] { new String[] { "command", "removecomponent" } }, removeDynamicParams, null, null);

		String[][] opticalZoomDynamicParams = new String[][] { componentIDDynParam };
		opticalZoomAction = ActionConfigUtils.createActionConfig("Enlarge", CommandType.SHOWDIALOG, false, new String[][] { new String[] { "dialogname", "overlay" } }, opticalZoomDynamicParams, null, null);

		String[][] helpDynamicParams = new String[][] { new String[] { "title", CURRENTCOMPONENT_TITLE_DYN_PARAM }, new String[] { "help", CURRENTCOMPONENTMODEL_HELP_DYNPNAME } };
		enabledDescriptionAction = ActionConfigUtils.createActionConfig("About this chart...", CommandType.SHOWDIALOG, false, new String[][] { new String[] { "dialogname", "helpdialog" } }, helpDynamicParams, null, null);
		disabledDescriptionAction = ActionConfigUtils.createActionConfig("About this chart...", CommandType.SHOWDIALOG, true, new String[][] { new String[] { "dialogname", "helpdialog" } }, helpDynamicParams, null, null);
	}

	protected ActionConfigType createQuickEditSet(MALComponent component, PresentationContext pCtx) throws VisualizationException {
		List<MALSeriesConfig> seriesConfigs = new ArrayList<MALSeriesConfig>();
		for (MALVisualization visualization : component.getVisualization()) {
			seriesConfigs.addAll(Arrays.asList(visualization.getSeriesConfig()));
		}
		return ActionUtils.createQuickEditSet(logger, component, seriesConfigs, pCtx);
	}

	@Override
	protected void shutdown() throws NonFatalException {
	}

}