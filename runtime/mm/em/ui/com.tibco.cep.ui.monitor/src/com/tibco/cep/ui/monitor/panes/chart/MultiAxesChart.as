package com.tibco.cep.ui.monitor.panes.chart{
	
	import com.tibco.cep.ui.monitor.AppConfig;
	import com.tibco.cep.ui.monitor.panes.MetricPaneType;
	import com.tibco.cep.ui.monitor.util.RandomXMLGenerator;
	
	import mx.charts.AxisRenderer;
	import mx.charts.DateTimeAxis;
	import mx.charts.LineChart;
	import mx.charts.LinearAxis;
	import mx.charts.series.LineSeries;
	import mx.collections.XMLListCollection;
	import mx.graphics.Stroke;
	
	
	public class MultiAxesChart extends ChartPane{
		
		private static const SERIES1_NAME:String = "Latency"; 
		private static const SERIES2_NAME:String = "Throughput";
		
		//data providers for the two series that comprise this chart
		[Bindable]
		private var _series1DataProvider:XMLListCollection;
		[Bindable]
		private var _series2DataProvider:XMLListCollection;
		
		[Bindable]
		private var _haxis:DateTimeAxis;
		private var _haxisRenderer:AxisRenderer;
		private var _vaxis1:LinearAxis;
		private var _vaxis2:LinearAxis;
		
		public function MultiAxesChart(title:String=""){
			//creates chart and binds the cartesian chart series with the array containing the series
			super(new LineChart(), title);
			
			_type = MetricPaneType.RTC_TABLE;
			
			//create and set the horizontal axis
			var hAxis:DateTimeAxis = new DateTimeAxis();
			setDateTimeAxis(hAxis);
			_display.horizontalAxis=hAxis;
			
			//create and set the horizontal renderer
			var hAxisRenderer:AxisRenderer = new AxisRenderer();
			hAxisRenderer.axis = hAxis;
			hAxisRenderer.setStyle("showLabels", "true");
			
			//add horizontal renderer to display
			_display.horizontalAxisRenderers = [hAxisRenderer];
			
			//create and set the vertical axes		
			_vaxis1 = new LinearAxis();
			_vaxis2 = new LinearAxis();
			_vaxis1.title = SERIES1_NAME;
			_vaxis2.title = SERIES2_NAME;
			
			//create and set the vertical renderers
			var a1Renderer:AxisRenderer = new AxisRenderer();
			a1Renderer.axis = _vaxis1;
			a1Renderer.placement = "left";
			a1Renderer.setStyle("verticalAxisTitleAlignment","vertical");
			a1Renderer.setStyle("axisStroke", new Stroke(AppConfig.instance.getChartStyle(_type).getDataColor(0), 4));
			
			var a2Renderer:AxisRenderer = new AxisRenderer();
			a2Renderer.axis = _vaxis2;
			a2Renderer.placement = "right";
			a2Renderer.setStyle("axisStroke", new Stroke(AppConfig.instance.getChartStyle(_type).getDataColor(1), 4));

			//add vertical renderers to display
			_display.verticalAxisRenderers = [a1Renderer, a2Renderer];
			
			setSeries();
			
			addDisplay(_display);
			
			setChartStyle();
		}
		
		private function setSeries():void {
			//creates and sets chart series
			var series1:LineSeries = new LineSeries();
			var series2:LineSeries = new LineSeries();
			
			_series1DataProvider = new XMLListCollection();
			_series2DataProvider = new XMLListCollection();
			
			series1.dataProvider = _series1DataProvider;
			series1.displayName = SERIES1_NAME;
			series1.xField = SERIES_CATEGORY_NODE;
			series1.yField = SERIES_VALUE_NODE;
			series1.verticalAxis = _vaxis1;
			
			series2.dataProvider = _series2DataProvider;
			series2.displayName = SERIES2_NAME;
			series2.xField = SERIES_CATEGORY_NODE;
			series2.yField = SERIES_VALUE_NODE;
			series2.verticalAxis = _vaxis2;
			
			//resets and adds series to series set array
			if (_chartSeriesSet.length >0)
				_chartSeriesSet.splice(0,_chartSeriesSet.length);
					
			_chartSeriesSet.push(series1);
			_chartSeriesSet.push(series2);
		}
		
		protected override function fillDisplay(data:XML):void{
			if(!validateData(data)) return;
			_data = new XML(data);
			showDisplay();
		}
		
		protected override function updateDisplay(data:XML):void{
			if(!validateData(data)) return;
			if(RANDOMIZE_UPDATES){
				for each(var seriesXml:XML in _data.series){
					var list:XMLList;
					if(_data.series.@name != undefined){
						list = _data.series.(@name==seriesXml.@name).datapoint;
					}
					else{
						list = _data.series.datapoint;
					}
					var newNode:XML = RandomXMLGenerator.newLogicalTimeValue(list[list.length()-1]);
					list[list.length()] = newNode;
				}
			}
			else{
				_data = new XML(data);
			}
			showDisplay();
		}
		
		private function showDisplay():void{
			var dataPoint:XML;
			_series1DataProvider.removeAll();
			for each (dataPoint in _data.series.(@name == SERIES1_NAME).datapoint) {
				_series1DataProvider.addItem(dataPoint);
			}
			
			_series2DataProvider.removeAll();
			for each (dataPoint in _data.series.(@name == SERIES2_NAME).datapoint) {
				_series2DataProvider.addItem(dataPoint); 
			}
			_series1DataProvider.refresh();
			_series2DataProvider.refresh();
		}
		
	} //class
}