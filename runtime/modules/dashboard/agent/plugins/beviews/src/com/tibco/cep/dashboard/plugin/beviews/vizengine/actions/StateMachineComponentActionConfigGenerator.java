package com.tibco.cep.dashboard.plugin.beviews.vizengine.actions;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALStateMachineComponent;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.CommandType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigUtils;

public class StateMachineComponentActionConfigGenerator extends ActionConfigGenerator {

	private static final String CURRENTCOMPONENTMODEL_HELP_DYNPNAME = "currentcomponentmodel.help";

	protected ActionConfigType enabledDescriptionAction;
	protected ActionConfigType disabledDescriptionAction;

	@Override
	protected void init() {
		String[][] helpDynamicParams = new String[][] { new String[] { "title", CURRENTCOMPONENT_TITLE_DYN_PARAM }, new String[] { "help", CURRENTCOMPONENTMODEL_HELP_DYNPNAME } };
		enabledDescriptionAction = ActionConfigUtils.createActionConfig("About this component...", CommandType.SHOWDIALOG, false, new String[][] { new String[] { "dialogname", "helpdialog" } }, helpDynamicParams, null, null);
		disabledDescriptionAction = ActionConfigUtils.createActionConfig("About this component...", CommandType.SHOWDIALOG, true, new String[][] { new String[] { "dialogname", "helpdialog" } }, helpDynamicParams, null, null);
	}

	@Override
	public List<ActionConfigType> generateActionConfigs(MALElement element, Map<String, String> dynParamSubMap, PresentationContext pCtx) throws VisualizationException {
		MALStateMachineComponent component = (MALStateMachineComponent) element;

		List<ActionConfigType> children = new LinkedList<ActionConfigType>();

		String description = component.getDescription();
		if (StringUtil.isEmptyOrBlank(description) == true) {
			children.add(disabledDescriptionAction);
		} else {
			children.add(enabledDescriptionAction);
		}

		return children;
	}

	@Override
	protected void shutdown() throws NonFatalException {
	}



}