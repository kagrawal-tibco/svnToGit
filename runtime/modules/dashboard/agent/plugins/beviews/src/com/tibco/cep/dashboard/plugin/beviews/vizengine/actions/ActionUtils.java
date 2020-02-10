package com.tibco.cep.dashboard.plugin.beviews.vizengine.actions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.mal.MALComponentGallery;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALFlowLayoutConstraint;
import com.tibco.cep.dashboard.psvr.mal.model.MALLayoutConstraint;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.CommandType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

class ActionUtils {
	
	static ActionConfigType createRelatedChartsSet(Logger logger,MALElement parent,MALElement[] elements,PresentationContext ctx) throws VisualizationException{
		if (elements == null || elements.length == 0){
            return ActionConfigUtils.createActionConfigSet("Related Charts", true, null);
		}
		List<ActionConfigType> actions = new ArrayList<ActionConfigType>(elements.length);
		TokenRoleProfile profile = ctx.getTokenRoleProfile();
		ViewsConfigHelper viewsConfigHelper = profile.getViewsConfigHelper();
		MALComponentGallery gallery = profile.getComponentGallery();
		
		for (MALElement element : elements) {
			//find the element in the gallery 
			MALComponent component = gallery.searchComponent(element.getId());
			if (component == null){
				component = viewsConfigHelper.getComponentById(element.getId());
			}
			if (component != null){
				String[][] parameters = new String[8][2];
				//name
				parameters[0] = new String[]{"name", component.getName()};
				//title
				parameters[1] = new String[]{"title", component.getDisplayName()};
				//id
				parameters[2] = new String[]{"componentid", component.getId()};
				//type
				parameters[3] = new String[]{"componenttype", component.getDefinitionType()};
				//INFO re-think harding to using MALFlowLayoutConstraint
				MALLayoutConstraint layoutConstraints = component.getLayoutConstraint();
				int rowSpan = 1;
				int colSpan = 1;
				if (layoutConstraints != null && layoutConstraints instanceof MALFlowLayoutConstraint) {
					MALFlowLayoutConstraint flowLayoutConstraints = (MALFlowLayoutConstraint) layoutConstraints;
					rowSpan = flowLayoutConstraints.getComponentRowSpan();
					colSpan = flowLayoutConstraints.getComponentColSpan();
				}
				//rowspan
				parameters[4] = new String[]{"rowspan", Integer.toString(rowSpan)};
				//colspan
				parameters[5] = new String[]{"colspan", Integer.toString(colSpan)};
				parameters[6] = new String[]{"width", Integer.toString(colSpan * viewsConfigHelper.getComponentWidthUnit(component.getDefinitionType()))};
				parameters[7] = new String[]{"height", Integer.toString(rowSpan * viewsConfigHelper.getComponentHeightUnit(component.getDefinitionType()))};
				actions.add(ActionConfigUtils.createActionConfig(component.getDisplayName(), CommandType.LAUNCHCOMPONENT, false, null, null, parameters, null));
			}
			else if (logger.isEnabledFor(Level.WARN) == true) {
                logger.log(Level.WARN, "Skipping " + component + " as related component for " + parent + " since it is not in the gallery for " + ctx.getSecurityToken());
            }
		}
		return ActionConfigUtils.createActionConfigSet("Related Charts", actions.isEmpty(), actions);
	}

	
	static ActionConfigType createQuickEditSet(Logger logger, MALComponent component, List<MALSeriesConfig> seriesConfigs, PresentationContext ctx) throws VisualizationException {
		List<ActionConfigType> children = new LinkedList<ActionConfigType>();
		ActionConfigType filterEditSet = createFilterEditSet(component, seriesConfigs, ctx);
		if (filterEditSet != null){
			children.add(filterEditSet);
		}
		return ActionConfigUtils.createActionConfigSet("Quick Edit", children.isEmpty(), children);
	}

	private static ActionConfigType createFilterEditSet(MALComponent component, List<MALSeriesConfig> seriesConfigs, PresentationContext ctx) throws VisualizationException {
		List<ActionConfigType> filterEditActionConfigs = new LinkedList<ActionConfigType>();
		int disabledCnt = 0;
		for (MALSeriesConfig seriesConfig : seriesConfigs) {
			ActionConfigType actionConfig = null;
			String displayName = seriesConfig.getDisplayName();
			if (StringUtil.isEmptyOrBlank(displayName) == true){
				displayName = seriesConfig.getName();
			}
			if (seriesConfig.getActionRule().getDataSource().getParamsCount() == 0) {
				actionConfig = ActionConfigUtils.createActionConfig(displayName, CommandType.SHOWDIALOG, true, null, null, null, null);
				disabledCnt++;
			} else {
				String[][] basicparams = new String[][] {
						// component id
						new String[] { "componentid", component.getId() },
						// series config id
						new String[] { "seriesid", seriesConfig.getId() } 
				};
				actionConfig = ActionConfigUtils.createActionConfig(displayName, CommandType.SHOWDIALOG, false, new String[][] { new String[] { "dialogname", "filtereditor" } }, null, basicparams, null);
			}
			filterEditActionConfigs.add(actionConfig);
		}
		if (disabledCnt == filterEditActionConfigs.size()){
			return null;
		}
		return ActionConfigUtils.createActionConfigSet("Filters", false, filterEditActionConfigs);
	}
}
