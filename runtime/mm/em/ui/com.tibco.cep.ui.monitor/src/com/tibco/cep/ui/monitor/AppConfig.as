package com.tibco.cep.ui.monitor{
	import com.tibco.cep.ui.monitor.panes.chart.ChartStyle;
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.utils.Dictionary;
	
	
	public class AppConfig implements IUpdateable{
		
		private static var _instance:AppConfig;
		
		private var _chartStyles:Dictionary;
		private var _randomizeUpdates:Boolean;
		private var _updateInterval:uint;
		private var _failedPaneThreshold:Number; //percentage
		private var _companyLogoURL:String;
		
		public function AppConfig(){
			_chartStyles = new Dictionary(true);
			_randomizeUpdates = false;
			_updateInterval = 20;
			_failedPaneThreshold = 1.0;
			_companyLogoURL = "";
		}
		
		public static function get instance():AppConfig{
			if(_instance == null) _instance = new AppConfig();
			return _instance;
		}
		
		public function get randomizeUpdates():Boolean{ return _randomizeUpdates; }
		
		public function get updateInterval():uint{ return _updateInterval; }
		
		public function get failedPaneThreshold():Number{ return _failedPaneThreshold; }
		
		public function get companyLogoURL():String{ return _companyLogoURL; }
		
		public function getChartStyle(chartType:String):ChartStyle{
			if(_chartStyles[chartType] == undefined){
				Logger.log(this,"ERROR: No style for chart type \"" + chartType + "\"!");
				throw new Error("ERROR: AppConfig.getChartStyle - No style for chart type \"" + chartType + "\"!");
			}
			var returnVal:ChartStyle = _chartStyles[chartType] as ChartStyle;
			return returnVal;
		}

		public function update(operation:String, data:XML):void{
			_randomizeUpdates = new Boolean(data.demoMode == "true");
			_updateInterval = data.updateInterval;
			_failedPaneThreshold = data.failedPaneThreshold;
			_companyLogoURL = data.logoURL;
			for each(var styleXml:XML in data.chartStyles.chartStyle){
				var colors:Array = new Array();
				for each(var colorXml:XML in styleXml.dataColors.dataColor){
					colors.push(uint(colorXml));
				}
				var test:String = styleXml.@chartType;
				_chartStyles[String(styleXml.@chartType)] = 
					new ChartStyle(
						colors, 
						uint(styleXml.fontColor), 
						uint(styleXml.backgroundColor)
					);
			}
		}
		
		public function updateFailure(operation:String, message:String, code:uint):void{
			Util.errorMessage("ERROR: Failed to load Application Configuration XML file!\nMessage: " + message);
		}
		
	}
}