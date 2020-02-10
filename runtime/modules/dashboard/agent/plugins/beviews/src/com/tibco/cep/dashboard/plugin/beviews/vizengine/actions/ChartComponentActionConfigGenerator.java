package com.tibco.cep.dashboard.plugin.beviews.vizengine.actions;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;

public class ChartComponentActionConfigGenerator extends MetricComponentActionConfigGenerator {

	@Override
	public List<ActionConfigType> generateActionConfigs(MALElement element, Map<String, String> dynParamSubMap, PresentationContext ctx) throws VisualizationException {
		MALChartComponent component = (MALChartComponent) element;
		List<ActionConfigType> children = new LinkedList<ActionConfigType>();

		children.add(opticalZoomAction);
		children.add(createQuickEditSet(component, ctx));
		children.add(ActionUtils.createRelatedChartsSet(logger, component, component.getRelatedElement(), ctx));
		children.add(removeAction);

		// Anand modified the logic to enable the menu item if the description is present
		String description = component.getDescription();
		if (StringUtil.isEmptyOrBlank(description) == true) {
			children.add(disabledDescriptionAction);
		} else {
			children.add(enabledDescriptionAction);
		}
		return children;

	}

}
