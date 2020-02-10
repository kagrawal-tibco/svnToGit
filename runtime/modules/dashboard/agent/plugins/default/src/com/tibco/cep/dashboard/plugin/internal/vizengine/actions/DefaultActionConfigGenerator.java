package com.tibco.cep.dashboard.plugin.internal.vizengine.actions;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGenerator;

public class DefaultActionConfigGenerator extends ActionConfigGenerator {

	@Override
	public List<ActionConfigType> generateActionConfigs(MALElement element, Map<String, String> dynParamSubMap, PresentationContext ctx) throws VisualizationException {
		return Collections.emptyList();
	}

	@Override
	protected void init() {

	}

	@Override
	protected void shutdown() throws NonFatalException {

	}

}