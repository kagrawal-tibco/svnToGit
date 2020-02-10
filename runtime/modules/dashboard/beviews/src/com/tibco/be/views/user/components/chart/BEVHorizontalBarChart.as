package com.tibco.be.views.user.components.chart{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.chart.series.BEVBarSeries;
	import com.tibco.be.views.user.components.chart.series.BEVSeriesConfig;
	import com.tibco.be.views.user.utils.ChartMinMax;
	
	import mx.charts.BarChart;
	import mx.charts.renderers.CrossItemRenderer;
	import mx.charts.series.BarSet;
	import mx.collections.ArrayCollection;
	import mx.core.ClassFactory;
	import mx.graphics.Stroke;
	
	public class BEVHorizontalBarChart extends BEVCartesianChart implements ISeriesDataModifier{
		
		//user params
		private var _barWidth:Number;
		private var _overlap:Number;
		
		//BEV specified
		private var _clusterWidthRatio:Number;
		
		public function BEVHorizontalBarChart(config:XML){
			super(new BarChart(), config);
			_barWidth = -1;
			_overlap = 0;
			_clusterWidthRatio = 0.8;
			_chart.seriesFilters = [];
		}
		
		private function get myChart():BarChart{ return _chart as BarChart; }
		
		override public function setDimensions(x:Number, y:Number, width:Number, height:Number):void{
			super.setDimensions(x, y, width, height);
			ChartUtils.configureBarOverlap(_chart.series, _dataProvider.categoryAxisData.length, plotArea.width, _clusterWidthRatio, _overlap, _barWidth, false);
		}
		
		override public function setChartBoundsAndInterval(minMax:ChartMinMax, forceZeroOrigin:Boolean=false):void{
			if(_overlap == 1){
				minMax.min = minMax.minCategorySum;
				minMax.max = minMax.maxCategorySum;
			}
			super.setChartBoundsAndInterval(minMax, true);
			if(_gridLines != null){
				_gridLines.setStyle("verticalOriginStroke", new Stroke(_gridLineColor, 2, 0.8));
			}
		}
		
		override protected function prepAndConfigureAxes(chartConfig:XML):void{
			var hAxisCfg:XML = new XML(chartConfig.valueaxisconfig);
			var vAxisCfg:XML = new XML(chartConfig.categoryaxisconfig);
			configureAxes(hAxisCfg, vAxisCfg);
		}
		
		override protected function configurePlotArea(chartConfig:XML):void{
			super.configurePlotArea(chartConfig);
			_gridLines.setStyle("horizontalShowOrigin", false);
		}
				
		override public function buildSeriesSet(compConfigXML:XML):void{
			var seriesConfigs:ArrayCollection = initChartSeriesConfigs(compConfigXML);
			var seriesSet:Array = new Array();
			var modifierUsed:Boolean = false;
			for(var i:int=0; i < seriesConfigs.length ;i++){
				var seriesConfig:BEVSeriesConfig = seriesConfigs[i] as BEVSeriesConfig;
				if(seriesConfig.chartType == BEVChartFactory.HORIZONTAL_BAR_CHART){
					if(seriesConfig.anchor == "Q2"){
						_dataProvider.addSeriesValueModifier(seriesConfig.seriesID, this);
						modifierUsed = true; //don't set style multiple times
					}
					var barSeries:BEVBarSeries = new BEVBarSeries(seriesConfig, _barWidth, _overlap);
					barSeries.dataProvider = _dataProvider.getSeriesData(seriesConfig.seriesID);
					seriesSet.push(barSeries);					
				}
			}
			
			if(_overlap == 1){
				var barSet:BarSet = new BarSet();
				barSet.type = "stacked";
				barSet.allowNegativeForStacked = true;
				barSet.series = seriesSet;
				myChart.series = [barSet];
			}
			else{
				myChart.series = seriesSet;
			}
			
			if(modifierUsed){ _gridLines.setStyle("verticalShowOrigin", true); }
		}
		
		override protected function setTypeSpecificProperties(properties:XMLList):void{
			if(properties == null){
				error(DefaultLogEvent.WARNING, "Null properties list!", "setTypeSpecificProperties");
				return;
			}
			myChart.setStyle("itemRenderer", new ClassFactory(CrossItemRenderer));
			for each(var property:XML in properties){
				switch(String(property.@name)){
					case("width"):
						//horizontal bars are rendered at half specified height for some reason, correct with *2
						_barWidth = new Number(property)*2;
						break;
					case("overlappercentage"):
						_overlap = new Number(property)/100;
						break;
				}
			}
		}
		
		override public function updateChartData(componentData:XML):void{
			super.updateChartData(componentData);
			ChartUtils.configureBarOverlap(_chart.series, _dataProvider.categoryAxisData.length, plotArea.width, _clusterWidthRatio, _overlap, _barWidth, false);
		}
		
		//ISeriesDataModifier
		public function modifyData(dataColumn:DataColumn):void{
			dataColumn.value = -dataColumn.value;
		}
		
		public function modifyDataXML(dataXML:XML):void{
			var nVal:Number = parseFloat(dataXML.value);
			if(!isNaN(nVal)){ dataXML.value = -nVal; }
		}
		//END ISeriesDataModifier
		
	}
}