package com.tibco.be.views.user.components.processmodel{
	
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.user.components.processmodel.view.PMNode;
	import com.tibco.be.views.user.utils.UserUtils;
	
	public class PMNodeActionContextProvider implements IActionContextProvider{
		
		//maps actionConfig XML to actionContext
		private var _actionContext:ActionContext;
		
		public function PMNodeActionContextProvider(pmNode:PMNode, pmNodeData:XML){
			_actionContext = new ActionContext(pmNode, resolveDynamicParams(pmNodeData));
		}
		
		public function getActionContext(parentActionConfig:XML, actionConfig:XML):ActionContext{
			return _actionContext;
		}
		
		private function resolveDynamicParams(pmNodeData:XML):DynamicParamsResolver{
			var resolvingMap:DynamicParamsResolver = new DynamicParamsResolver();
		 	resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_LINK_PARAM, new String(pmNodeData.link));
		 	if(pmNodeData.typespecificattribute != undefined && pmNodeData.typespecificattribute.(@name=="hrefprms").length() > 0){
				resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_TYPE_SPEC_HREF_PARAMS_PARAM, new String(pmNodeData.typespecificattribute.(@name=="hrefprms")));
			}
			else if(pmNodeData.typespecificattribute != undefined && pmNodeData.typespecificattribute.(@name=="hrefparams").length() > 0){
				resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_TYPE_SPEC_HREF_PARAMS_PARAM, new String(pmNodeData.typespecificattribute.(@name=="hrefparams")));
			}
		 	return resolvingMap;
		}

	}
}