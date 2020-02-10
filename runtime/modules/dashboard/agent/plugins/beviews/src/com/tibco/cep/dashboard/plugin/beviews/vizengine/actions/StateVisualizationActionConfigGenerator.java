package com.tibco.cep.dashboard.plugin.beviews.vizengine.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALStateVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGenerator;

public class StateVisualizationActionConfigGenerator extends ActionConfigGenerator {

	@Override
	protected void init() {
		// do nothing
	}

	@Override
	public List<ActionConfigType> generateActionConfigs(MALElement element, Map<String, String> dynParamSubMap, PresentationContext ctx) throws VisualizationException {
		MALStateVisualization stateVisualization = (MALStateVisualization) element;
		ArrayList<ActionConfigType> actionConfigs = new ArrayList<ActionConfigType>(2);
		actionConfigs.add(ActionUtils.createQuickEditSet(logger, (MALComponent) stateVisualization.getParent(), Arrays.asList(stateVisualization.getSeriesConfig()), ctx));
		actionConfigs.add(ActionUtils.createRelatedChartsSet(logger, stateVisualization, stateVisualization.getRelatedElement(), ctx));
		return actionConfigs;
	}

	@Override
	protected void shutdown() throws NonFatalException {
		// do nothing
	}

}
