package com.tibco.be.views.user.components.chart{
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.utils.Logger;
	
	
	public class BEVChartFactory{
		
		public static const VERTICAL_BAR_CHART:String = "verticalbar";
 		public static const HORIZONTAL_BAR_CHART:String = "horizontalbar";
 		public static const PIE_CHART:String = "pie";
 		public static const LINE_CHART:String = "line";
 		public static const AREA_CHART:String = "area";
 		public static const VERTICAL_RANGE_CHART:String = "verticalrange";
 		public static const HORIZONTAL_RANGE_CHART:String = "horizontalrange";
 		public static const SCATTER_CHART:String = "scatter";
 		
 		private static var _instance:BEVChartFactory;
		
		function BEVChartFactory(){
			
		}
		
		public static function get instance():BEVChartFactory{
			if(_instance == null){
				_instance = new BEVChartFactory();
			}
			return _instance;
		}
		
		public function buildChart(componentConfig:XML):BEVChart{
			var chartConfig:XML = componentConfig.chartconfig[0];
			try{
				if( (chartConfig.charttypeconfig as XMLList).length() > 1 ){
					return new BEVOverlayChart(componentConfig);
				}
				var type:String = chartConfig.charttypeconfig[0].@type;
		 		switch(type){
		 			case(VERTICAL_BAR_CHART):
		 				return new BEVVerticalBarChart(componentConfig);
		 			case(HORIZONTAL_BAR_CHART):
		 				return new BEVHorizontalBarChart(componentConfig);
		 			case(PIE_CHART):
		 				return new BEVPieChart(componentConfig);
		 			case(LINE_CHART):
		 				return new BEVLineChart(componentConfig);
		 			case(AREA_CHART):
		 				return new BEVAreaChart(componentConfig);
	 				case(HORIZONTAL_RANGE_CHART):
		 				//return new BEViewsHRangeChart(componentConfig);
		 			case(VERTICAL_RANGE_CHART):
		 				return new BEVVRangeChart(componentConfig);
		 			case(SCATTER_CHART):
		 				return new BEVScatterChart(componentConfig);
		 			default:
		 				trace("BEViewsChartFactory failed to create chart of type '" + type + "'!");
				}
			}
			catch(error:Error){
				Logger.log(DefaultLogEvent.WARNING, "Creation of Chart Component (" + type + ") failed due to " + error.message);
			}
			return null;
		}

	}
}