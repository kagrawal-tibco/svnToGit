package com.tibco.be.views.user.components.chart.series{
	import flash.events.MouseEvent;
	
	import mx.charts.chartClasses.Series;
	
	
	public class BEVSeriesUtil{
		
		public static function getActionConfig(seriesConfig:XML):XML{
			var actionConfig:XML;
			if(seriesConfig.actionconfig.length() > 0){
				actionConfig = new XML(seriesConfig.actionconfig[0]);
				actionConfig.@id = seriesConfig.@name;
				actionConfig.@iconColor = seriesConfig.@basecolor;
			}
			else{
				actionConfig = new XML("<actionconfig />");
			}
			return actionConfig;
		}

	}
}