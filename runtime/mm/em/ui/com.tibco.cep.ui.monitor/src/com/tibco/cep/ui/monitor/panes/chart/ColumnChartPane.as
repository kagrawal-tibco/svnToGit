package com.tibco.cep.ui.monitor.panes.chart{
	
	import com.tibco.cep.ui.monitor.util.RandomXMLGenerator;
	
	import mx.charts.AxisRenderer;
	import mx.charts.CategoryAxis;
	import mx.charts.ColumnChart;
	import mx.charts.LinearAxis;
	import mx.charts.series.ColumnSeries;
	import mx.collections.XMLListCollection;
	
	
	public class ColumnChartPane extends ChartPane{
		
		private var _series:ColumnSeries;	//needed in setSeries(), to set the horizontal axis
		[Bindable]
		private var _seriesDataProvider:XMLListCollection;
		private var _haxis:CategoryAxis;
		
		public function ColumnChartPane(type:String, title:String, vAxisTitle:String=SERIES_VALUE_NODE, haxisTitle:String=SERIES_CATEGORY_NODE){
			//creates chart and binds the cartesian chart series with the array containing the series
			super(new ColumnChart(), title);
			_type = type;
			
			//create and set the horizontal axis
			_haxis = new CategoryAxis();
			_haxis.title = haxisTitle;
			_haxis.categoryField = SERIES_CATEGORY_NODE;
			_display.horizontalAxis=_haxis;
			
			//create and set the horizontal renderer
			var hAxisRenderer:CategoryAxisRenderer = new CategoryAxisRenderer();
			setHAxisRenderer(_haxis, hAxisRenderer);
			
			//add horizontal renderer to display
			_display.horizontalAxisRenderers  = [hAxisRenderer];
			
			//create and set the vertical axis
			var vAxis:LinearAxis= new LinearAxis();
			vAxis.title = vAxisTitle;
			_display.verticalAxis = vAxis;
			
			//create and set the vertical renderer
			var vAxisRenderer:AxisRenderer = new AxisRenderer(); 
			setVAxisRenderer(vAxis, vAxisRenderer); 

			//add vertical renderer to display
			_display.verticalAxisRenderers = [vAxisRenderer];
			
//			setSeries();

			addDisplay(_display);
			
//			setChartStyle();
		}
		
		private function setSeries(name:String=""):void {
			_series = new ColumnSeries();
				
			_seriesDataProvider = new XMLListCollection();		
			
			_series.dataProvider = _seriesDataProvider;
			_series.horizontalAxis = _haxis;
			_series.displayName = name;
			_series.xField = SERIES_CATEGORY_NODE;
			_series.yField = SERIES_VALUE_NODE;	
			
			//resets and adds series to series set array
			if (_chartSeriesSet.length >0)
				_chartSeriesSet.splice(0,_chartSeriesSet.length);	
			_chartSeriesSet.push(_series);
		}
		
		/* An attempt to help memory leakage, but seems to be causing other problems...
		public override function destory():void{
			_seriesSet = null;
			_haxis = null
			_display.horizontalAxisRenderers = [];
			_haxisRenderer = null;
			super.destory();
		}
		//*/
		
		protected override function fillDisplay(data:XML):void{
			if(!validateData(data)) return;
			_data = new XML(data);
			showDisplay();
		}
		
		protected override function updateDisplay(data:XML):void{
			if(!validateData(data)) return;
			if(RANDOMIZE_UPDATES){
				RandomXMLGenerator.updateRandom(_data);
			}
			else{
				_data = new XML(data);
			}
			showDisplay();
		}
		
		private function showDisplay():void{
			var seriesList:XMLList = _data.series;
			var seriesName:String="";
			var seriesNum:int=0;
			
			for each(var seriesXml:XML in seriesList){
				seriesName = seriesXml.@name == undefined ? ""+seriesNum:(seriesXml.@name).toString();
				if (_series == null) { 
					setSeries(seriesName);
					setChartStyle(); 
				}
					
				_seriesDataProvider.removeAll();
				
				for each (var dataPoint:XML in seriesXml.datapoint){
					_seriesDataProvider.addItem(dataPoint);
				}	
				
			}
			seriesNum++;
			_seriesDataProvider.refresh();
			
			if(_chartSeriesSet.length > 1) addLegend();  //TODO: Check what's this
		}
		
		private function hAxisLabeler():void{
			
		}
		
	}
}