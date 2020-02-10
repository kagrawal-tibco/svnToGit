package com.tibco.be.views.user.components.chart{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.chart.series.BEVColumnSeries;
	import com.tibco.be.views.user.components.chart.series.BEVSeriesConfig;
	import com.tibco.be.views.user.utils.ChartMinMax;
	
	import mx.charts.chartClasses.CartesianChart;
	import mx.charts.series.ColumnSet;
	import mx.collections.ArrayCollection;
	import mx.graphics.Stroke;

	public class BEVVerticalBarChart extends BEVCartesianChart implements ISeriesDataModifier{

		//user params
		private var _overlap:Number; //if %30, means %30 of bar is overlapping, thus %70 is shown
		private var _columnWidth:Number;
		
		//BEV specified
		private var _clusterWidthRatio:Number;
		
		//other
		private var _invertedSeries:ArrayCollection;
		
        public function BEVVerticalBarChart(config:XML){
			super(new CartesianChart(), config);
			_overlap = 0;
			_columnWidth = -1;
			_clusterWidthRatio = 0.8;
		}
		
		private function get myChart():CartesianChart{ return _chart as CartesianChart; }
		
		override public function setDimensions(x:Number, y:Number, width:Number, height:Number):void{
			super.setDimensions(x, y, width, height);
			ChartUtils.configureBarOverlap(_chart.series, _dataProvider.categoryAxisData.length, plotArea.width, _clusterWidthRatio, _overlap, _columnWidth);
		}
				
		override public function setChartBoundsAndInterval(minMax:ChartMinMax, forceZeroOrigin:Boolean=false):void{
			if(_overlap == 1){
				minMax.min = minMax.minCategorySum;
				minMax.max = minMax.maxCategorySum;
			}
			super.setChartBoundsAndInterval(minMax, true);
			if(_gridLines != null){
				_gridLines.setStyle("horizontalOriginStroke", new Stroke(_gridLineColor, 2, 0.8));
			} 
		}
		
		override public function buildSeriesSet(compConfigXML:XML):void{
			var seriesConfigs:ArrayCollection = initChartSeriesConfigs(compConfigXML);
			var seriesSet:Array = new Array();
			for(var i:int=0; i < seriesConfigs.length; i++){
				var seriesConfig:BEVSeriesConfig = seriesConfigs[i] as BEVSeriesConfig;
				if(seriesConfig.chartType == BEVChartFactory.VERTICAL_BAR_CHART){
					if(seriesConfig.anchor == "Q4"){
						_dataProvider.addSeriesValueModifier(seriesConfig.seriesID, this);
					}
					var columnSeries:BEVColumnSeries = new BEVColumnSeries(seriesConfig, _columnWidth, _overlap);
					columnSeries.dataProvider = _dataProvider.getSeriesData(seriesConfig.seriesID);
					seriesSet.push(columnSeries);
				}
			}
			if(_overlap == 1){
				var colSet:ColumnSet = new ColumnSet();
				colSet.type = "stacked";
				colSet.allowNegativeForStacked = true;
				colSet.series = seriesSet;
				myChart.series = [colSet];
			}
			else{
				myChart.series = seriesSet;
			}
			
		}
		
		override protected function configurePlotArea(chartConfig:XML):void{
			super.configurePlotArea(chartConfig);
			_gridLines.setStyle("verticalShowOrigin", false);
		}
		
		override protected function setTypeSpecificProperties(properties:XMLList):void{
			if(properties == null){
				error(DefaultLogEvent.WARNING, "Null properties list!", "setTypeSpecificProperties");
				return;
			}
			for each(var property:XML in properties){
				switch(String(property.@name)){
					case("width"):
						_columnWidth = new Number(property);
						break;
					case("overlappercentage"):
						_overlap = new Number(property)/100;
						break;
				}
			}
		}
		
		override public function updateChartData(componentData:XML):void{
			super.updateChartData(componentData);
			ChartUtils.configureBarOverlap(_chart.series, _dataProvider.categoryAxisData.length, plotArea.width, _clusterWidthRatio, _overlap, _columnWidth);
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