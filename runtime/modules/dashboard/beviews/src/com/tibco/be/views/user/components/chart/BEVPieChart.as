package com.tibco.be.views.user.components.chart{
	
	import com.tibco.be.views.user.components.chart.series.BEVPieSeries;
	import com.tibco.be.views.user.components.chart.series.BEVSeriesConfig;
	
	import flash.geom.Rectangle;
	
	import mx.charts.HitData;
	import mx.charts.PieChart;
	import mx.charts.series.items.PieSeriesItem;
	import mx.collections.ArrayCollection;

	public class BEVPieChart extends BEVChart{
		
		private var _isStyleSet:Boolean = false;
		private var _currentItem:PieSeriesItem;
		private var _currentColors:Array;
		
		public function BEVPieChart(config:XML){
			super(new PieChart(), config);
		}
		
		private function get myChart():PieChart{ return _chart as PieChart; }
		
		override public function buildSeriesSet(compConfigXML:XML):void{
			var seriesConfigs:ArrayCollection = initChartSeriesConfigs(compConfigXML);
			var pieSeries:BEVPieSeries; //only one series per pie chart	
			for(var i:int=0; i < seriesConfigs.length; i++){
				var seriesConfig:BEVSeriesConfig = seriesConfigs[i] as BEVSeriesConfig;
				if(seriesConfig.chartType == BEVChartFactory.PIE_CHART){
					pieSeries = new BEVPieSeries(seriesConfig);
					pieSeries.dataProvider = _dataProvider.getSeriesData(seriesConfig.seriesID);
					break;
				}
			}
			myChart.series = [pieSeries];
		}
		
		override public function handleDataSet(dataXML:XML):void{
			super.handleDataSet(dataXML);
			if(dataXML == null || dataXML.children().length() == 0){
				return;
			}
			for each(var series:BEVPieSeries in myChart.series){ //should only loop once
				_currentColors = ChartUtils.getDataColumnColors(dataXML);
				series.setStyle("fills", _currentColors);
			}
			_isStyleSet = true;
		}
		
		override public function updateChartData(componentData:XML):void{
			var initNumCategories:int = _dataProvider.categoryAxisData.length;
			super.updateChartData(componentData);
			var currentNumCategories:int = _dataProvider.categoryAxisData.length;
			var newColors:Array = ChartUtils.getDataColumnColors(componentData);
			if(currentNumCategories > 0 && (chartColorsChanged(newColors) || initNumCategories != currentNumCategories || !_isStyleSet)){
				_currentColors = newColors;
				for each(var series:BEVPieSeries in myChart.series){ //should only loop once
					series.setStyle("fills", _currentColors);
				}
			}
		}
		
		override public function getItemsInRegion(value:Rectangle, target:Object=null):Array{
			if(_currentItem != null && (target as BEVPieSeries)){
				return [_currentItem];
			}
			return [];
		}
		
		override protected function dataTipFunciton(hitData:HitData):String{
			_currentItem = hitData.chartItem as PieSeriesItem;
			return super.dataTipFunciton(hitData);
		}
		
		private function chartColorsChanged(newColors:Array):Boolean{
			if(newColors == null && _currentColors == null){ return false; }
			if(newColors != null && _currentColors == null){ return true; }
			if(newColors.length != _currentColors.length){ return true; }
			for(var i:int = 0; i < newColors.length; i++){
				if(newColors[i] as uint != _currentColors[i] as uint){ return true; }
			}
			return false;
		}
				
	}
	
}