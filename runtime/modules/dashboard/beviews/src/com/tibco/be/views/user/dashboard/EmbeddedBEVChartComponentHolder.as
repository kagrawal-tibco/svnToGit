package com.tibco.be.views.user.dashboard{
	
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.utils.UserUtils;
	
	public class EmbeddedBEVChartComponentHolder extends BEVChartComponentHolder{
		
		public function EmbeddedBEVChartComponentHolder(component:BEVComponent){
			super();
			_component = component;
		}
		
		override public function getActionContext(parentActionConfig:XML, actionConfig:XML):ActionContext{
		 	var resolvingMap:DynamicParamsResolver = new DynamicParamsResolver();
		 	var seriesID:String = new String(actionConfig.param.(@name=="seriesid")[0]);
		 	resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTCOMPONENT_ID_PARAM, component.componentId);
		 	resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTSERIES_ID_PARAM, seriesID == "null" ? "":seriesID);
		 	resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTPANEL_ID_PARAM, component.componentContainer.containerId);
		 	resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTPAGE_ID_PARAM, UserUtils.getParentDashboard(component.componentContainer).containerId);
		 	resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTCOMPONENT_TITLE_PARAM, component.componentTitle);
		 	resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTCOMPONENT_HELP_PARAM, component.componentHelp);
		 	return new ActionContext(component,resolvingMap);
		 }		
		
	}
}