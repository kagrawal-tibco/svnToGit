package com.tibco.be.views.user.components.chart{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.user.components.chart.series.IBEVSeries;
	import com.tibco.be.views.utils.BEVUtils;
	import com.tibco.be.views.utils.Logger;
	
	import flash.utils.Dictionary;
	
	import mx.charts.ChartItem;
	
	public class DataPointActionContextProvider implements IActionContextProvider{
		
		//maps actionConfig XML to actionContext
		private var _configToContextMap:Dictionary;
		
		public function DataPointActionContextProvider(dataPoints:Array){
			_configToContextMap = new Dictionary();
			for each(var dataPoint:ChartItem in dataPoints){
				var series:IBEVSeries = dataPoint.element as IBEVSeries;
				if(series == null || series.actionConfig == null || series.seriesConfig == null){ continue; }
				var seriesId:String = String(series.seriesConfig.@name);
				var dataPointId:String = dataPoints.length > 1 ? getChartItemDataId(dataPoint):"";
				_configToContextMap[getDataPointContextId(seriesId, dataPointId)] = 
					new ActionContext(dataPoint, resolveDynamicParams(series, dataPoint));
			}
		}
		
		public function getActionContext(parentActionConfig:XML, actionConfig:XML):ActionContext{
			var aContext:ActionContext = null;
			var actionCfgID:String =  ""; 
			
			try{
				actionCfgID = actionConfig.@id;
				while(actionCfgID == ""){
					actionConfig = actionConfig.parent() as XML;
					if(actionConfig == null){ break; }
					actionCfgID = actionConfig.@id;
				}
				if(actionCfgID == ""){
					var nodes:XMLList = actionConfig.param.(@name == "seriesid"); 
					if(nodes.length() > 0){ actionCfgID = nodes[0]; }
				}
				
				if(_configToContextMap[actionCfgID]){
					aContext = _configToContextMap[actionCfgID] as ActionContext;
				}
			}
			catch(error:Error){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".getActionContext - " + error.message);
			}
			return aContext;
		}
		
		private function resolveDynamicParams(series:IBEVSeries, chartItem:ChartItem):DynamicParamsResolver{
			var resolvedParams:DynamicParamsResolver = new DynamicParamsResolver();
			//no point recursing through all the xml, let's just set what we know...
			//resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTPAGE_ID_PARAM, "");
			//resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTPANEL_ID_PARAM, "");
			//resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTCOMPONENT_ID_PARAM, "");
			//resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTCOMPONENT_TITLE_PARAM, "");
			//resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTCOMPONENT_HELP_PARAM, "");
			//resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTSERIES_ID_PARAM, "");
			//resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTDATAROW_ID_PARAM, "");
			//resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_FIELD_NAME_PARAM, "");
			if(chartItem.item is IChartDataPoint){
				var dataPoint:IChartDataPoint = chartItem.item as IChartDataPoint;
				resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_ID_PARAM, dataPoint.colID);
				resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_LINK_PARAM, dataPoint.link);
				resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_VALUE_PARAM, dataPoint.valueObj);
				if(dataPoint.typeSpecificAttribs["hrefprms"] != undefined){
					resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_TYPE_SPEC_HREF_PARAMS_PARAM, dataPoint.typeSpecificAttribs["hrefprms"]);
				}
				else if(dataPoint.typeSpecificAttribs["hrefparams"] != undefined){
					resolvedParams.setDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_TYPE_SPEC_HREF_PARAMS_PARAM, dataPoint.typeSpecificAttribs["hrefparams"]);
				}
			}
			return resolvedParams;
		}
		
		private function getChartItemDataId(chartItem:ChartItem):String{
			var id:String = "";
			if(chartItem != null && chartItem.item is IChartDataPoint){
				id = (chartItem.item as IChartDataPoint).colID;
			}
			return id;
		}
		
		private function getDataPointContextId(seriesId:String, colId:String):String{
			if(seriesId == null){ seriesId = ""; }
			if(colId == null || colId == ""){ return seriesId; }
			return seriesId + "." + colId;
		}

	}
}