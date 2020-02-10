package com.tibco.be.views.deprecated{
	
	import mx.charts.CategoryAxis;
	import mx.charts.ColumnChart;
	import mx.charts.LinearAxis;
	import mx.charts.series.ColumnSeries;
	import mx.collections.ArrayCollection;

	public class SynColumnChart extends ColumnChart{
		//reference of the synchart
		private var __parentRef:SynChart;
		private var chartData:ArrayCollection;
		
		public function SynColumnChart(){
			super();
			
			//initiate the chartData collection
			chartData = new ArrayCollection();
		}
		
		/**
		 * shows the data for the chart
		 * @public
		 * @param visualization data XML
		 */
		public function showData(vizDataXML:XML):void{
			var cXML:XML = __parentRef.componentConfig;
			var i:Number;
			var j:Number;
			
			//remove all previous serieses of the chart
			var blankSeries:Array = new Array();
			this.series = blankSeries;
			
			//retrieve the processed chart data
			chartData = ChartUtils.getProcessedChartData(cXML, vizDataXML);

			//axes
			//Horizontal Axis
			var hAxis:CategoryAxis = new CategoryAxis();
			hAxis.categoryField = "colID";
			this.horizontalAxis = hAxis;
			if (chartData.length != 0) {
				this.dataProvider = chartData[0].seriesData;
			}
			else{
				this.dataProvider = null;
			}
			//fetch the formatted renderer
			this.horizontalAxisRenderer = ChartUtils.getFormattedCatAxisRenderer();
			
			//Vertical Axis
			var vAxis:LinearAxis =  new LinearAxis();
			this.verticalAxis = vAxis;
			this.verticalAxisRenderer = ChartUtils.getFormattedLinearAxisRenderer();
			
			//run through each chart data and based on chart type attach the series data
			for(i=0;i<chartData.length;i++){
				var dObj:Object = chartData[i];
				var seriesRefArr:Array = this.series;
				
				//column chart
				if(dObj.chartType == "verticalbar"){
					var cSeries:ColumnSeries = new ColumnSeries();
					cSeries.dataProvider = dObj.seriesData;
					cSeries.yField = "value";
					cSeries.xField = "colID";
					
					//fill the series item
					cSeries.setStyle("fill", ChartUtils.getVerticalBarSeriesFill(dObj));
					
					seriesRefArr.push(cSeries);
					this.series = seriesRefArr;					
				}
			}//end of 'i' loop
		}//end of showData
		
		//---------------
		//PROPERTIES
		//---------------
		public function set parentRef(sChart:SynChart):void{
			__parentRef = sChart;
		}
		
		//---------------
		//END OF PROPERTIES
		//---------------		
	}
}