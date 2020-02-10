package com.tibco.cep.ui.monitor.panes.chart{
	import com.tibco.cep.ui.monitor.AppConfig;
	import com.tibco.cep.ui.monitor.containers.MetricPaneLegendItem;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import mx.charts.AxisRenderer;
	import mx.charts.DateTimeAxis;
	import mx.charts.HitData;
	import mx.charts.Legend;
	import mx.charts.chartClasses.CartesianChart;
	import mx.charts.chartClasses.IAxis;
	import mx.charts.chartClasses.Series;
	import mx.charts.series.AreaSeries;
	import mx.charts.series.ColumnSeries;
	import mx.charts.series.LineSeries;
	import mx.graphics.Stroke;

	public class ChartPane extends MetricPane{
		
		protected static const SERIES_CATEGORY_NODE:String = "category";
		protected static const SERIES_VALUE_NODE:String = "value";
		
		protected var _display:CartesianChart;
		protected var _legend:Legend;
		
		[Bindable]
		protected var _chartSeriesSet:Array; // array of Series objects that define the chart data
		
		[Bindable]
        protected var _data:XML;
        
        protected var _hAxis:IAxis;
        protected var _vAxis:IAxis;
        
		
		public function ChartPane(display:CartesianChart, title:String=""){
			super(title);
			_display = display;
			//binds the cartesian chart series with the array containing the series. The array is updated 
			//with the new data for each chart type
			_chartSeriesSet = new Array();
			_display.series = _chartSeriesSet;
		}

		protected function setHAxisRenderer(hAxis:IAxis, hAxisRenderer:AxisRenderer):void {
			var stroke:Stroke = new Stroke();
			stroke.color = 0x515e7f;
			stroke.weight = 1;
			stroke.caps = "none";
			
			hAxisRenderer.axis = hAxis;
			hAxisRenderer.setStyle("axisStroke", stroke);
			hAxisRenderer.setStyle("tickPlacement ", "inside");
			hAxisRenderer.setStyle("tickStroke", stroke);
			hAxisRenderer.setStyle("tickLength", 3);
		}
		
		protected function setVAxisRenderer(vAxis:IAxis, vAxisRenderer:AxisRenderer):void {
			var stroke:Stroke = new Stroke();
			stroke.color = 0x515e7f;
			stroke.weight = 1;
			stroke.caps = "none";
						
			vAxisRenderer.axis = vAxis;
			vAxisRenderer.setStyle("verticalAxisTitleAlignment","vertical");
			vAxisRenderer.setStyle("axisStroke", stroke);
			vAxisRenderer.setStyle("tickPlacement ", "inside");
			vAxisRenderer.setStyle("tickStroke", stroke);
			vAxisRenderer.setStyle("tickLength", 3);
		} 
		
		protected function setDateTimeAxis(dtAxis:DateTimeAxis, units:String="seconds", title:String="Time"):void {
			dtAxis.displayLocalTime = true;
			dtAxis.dataUnits = units;
			dtAxis.title = title; 
		}
		
		public function addLegend():void{
			if(_legend != null) return;
			_legend = new Legend();
			_legend.legendItemClass = MetricPaneLegendItem;
			_legend.width = _display.width;
			_legend.height = 20;
			_legend.direction = "horizontal";
			_legend.setStyle("paddingTop", 0);
			_legend.dataProvider = _display;
			super.addChartLegend(_legend);
		}
		
		public function setChartStyle():Boolean {
			var style:ChartStyle = AppConfig.instance.getChartStyle(_type);
			if(style == null){
				Logger.log(this, "Style not configured for chart of type: " + _type);
				return false;
			}
			style.assignStyleToChart(_display);
			return true;
		}
		
		public function setSeriesStyle(series:Series, seriesNum:int):Boolean{
			var style:ChartStyle = AppConfig.instance.getChartStyle(_type);
			if(style == null){
				return false;
			}
			return style.assignStyleToSeries(series, seriesNum);
		}
				
		public static function formatDataTip(hitData:HitData):String{
			var tipText:String = "";
			var date:Date;
			if(hitData.element is LineSeries){
				var line:LineSeries = hitData.element as LineSeries;		
				//if the series name is just a number, it means it is just for pure internal identification purposes,
				//and hence it is not shown to the user
				if(line.displayName != null && line.displayName.length > 0 && !Util.isANumber(line.displayName) ){
					tipText += "<b>" + line.displayName + "</b><br />";
				}
				date = new Date();
				date.time = hitData.item.category;
				tipText += formatTime(date) + "<br />";
				tipText += hitData.item.value;
			}
			else if(hitData.element is ColumnSeries){
				var column:ColumnSeries = hitData.element as ColumnSeries;
				if(column.displayName != null && column.displayName.length > 0 && !Util.isANumber(column.displayName)){
					tipText += "<b>" + column.displayName + "</b><br />";
				}
				tipText += hitData.item.category + "<br />";
				tipText += hitData.item.value;
			}
			else if(hitData.element is AreaSeries){
				var area:AreaSeries = hitData.element as AreaSeries;
				if(area.displayName != null && area.displayName.length > 0 && !Util.isANumber(area.displayName)){
					tipText += "<b>" + area.displayName + "</b><br />";
				}
				date = new Date();
				date.time = hitData.item.category;
				tipText += formatTime(date) + "<br />";
				tipText += hitData.item.value;
			}
			return tipText != "" ? tipText:"Series Type Coming Soon";
		}
		
		private static function formatTime(date:Date):String{
			var time:String = "";
			time += (date.hours/10 >= 1 ? date.hours.toString():"0"+date.hours.toString()) + ":";
			time += (date.minutes/10 >= 1 ? date.minutes.toString():"0"+date.minutes.toString()) + ":";
			time += (date.seconds/10 >= 1 ? date.seconds.toString():"0"+date.seconds.toString()); 
			return time;
		}
		
		protected function validateData(data:XML):Boolean{
			if(data == null){
				Logger.log(this,"ERROR: Can't fill " + _type + " with null data!");
				return false;
			}
			return true; 
		}

	}
}