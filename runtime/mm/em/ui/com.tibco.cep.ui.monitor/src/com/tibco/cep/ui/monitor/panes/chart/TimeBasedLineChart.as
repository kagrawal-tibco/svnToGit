package com.tibco.cep.ui.monitor.panes.chart{
	import com.tibco.cep.ui.monitor.util.RandomXMLGenerator;
	
	import flash.utils.Dictionary;
	
	import mx.charts.AxisRenderer;
	import mx.charts.DateTimeAxis;
	import mx.charts.LineChart;
	import mx.charts.LinearAxis;
	import mx.charts.chartClasses.Series;
	import mx.charts.renderers.CircleItemRenderer;
	import mx.charts.series.LineSeries;
	import mx.collections.XMLListCollection;
	import mx.core.ClassFactory;
	
	public class TimeBasedLineChart extends ChartPane{
		
		//Dictionary containing the dataproviders (XMLListCollection) for each series.
		//The key is the series name.
		[Bindable]
		private var _seriesNameToDataProvider:Dictionary;
		private var _seriesNameToSeriesObj:Dictionary;
		private var _seriesNameToPrevNumPts:Dictionary;
		
		public function TimeBasedLineChart(type:String, title:String, vaxisTitle:String=SERIES_VALUE_NODE){
			//creates chart and binds the cartesian chart series with the array containing the series
			super(new LineChart(), title);
			_type = type;
			
			//create data provider source
			_seriesNameToDataProvider = new Dictionary(true);
			_seriesNameToSeriesObj = new Dictionary(true);
			_seriesNameToPrevNumPts = new Dictionary(true);
			
			//create and set the horizontal axis
			var hAxis:DateTimeAxis = new DateTimeAxis();
			//*Updated to fix BE-3359 (origin shows 16:00 when chart is empty). mwiley 07/02/12
			setDateTimeAxis(hAxis, "days");
			//setDateTimeAxis(hAxis);
			//*END fix BE-3359/
			_display.horizontalAxis = hAxis;
			
			//create and set the horizontal renderer
			var hAxisRenderer:AxisRenderer = new AxisRenderer();
			hAxisRenderer.axis = hAxis;
			hAxisRenderer.setStyle("showLabels", "true");
			//add horizontal renderer to display
			_display.horizontalAxisRenderers = [hAxisRenderer];
			
			//create and set the vertical axis
			var vAxis:LinearAxis= new LinearAxis();
			vAxis.title = vaxisTitle;
			
			_display.verticalAxis = vAxis;
			
			//create and set the vertical renderer
			var vAxisRenderer:AxisRenderer = new AxisRenderer(); 
			setVAxisRenderer(vAxis, vAxisRenderer); 
			//add vertical renderer to display
			_display.verticalAxisRenderers = [vAxisRenderer];

			addDisplay(_display);
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
		
		private function setSeries(name:String, seriesNum:int):void {
			//creates and sets chart series
			var series:LineSeries;
			series = new LineSeries();

			_seriesNameToDataProvider[name] = new XMLListCollection();
			
			series.dataProvider = _seriesNameToDataProvider[name] as XMLListCollection;
			series.displayName = name; 
			series.xField = SERIES_CATEGORY_NODE;
			series.yField = SERIES_VALUE_NODE;	
			
			setSeriesStyle(series,seriesNum);
			
			_seriesNameToPrevNumPts[name] = 0;
			_seriesNameToSeriesObj[name]=series;	//todo: remove entries from dictionary
			_chartSeriesSet.push(series);
		}
		
		private function showDisplay():void{
			var series:Series;
			var seriesDataProvider:XMLListCollection;
			var seriesList:XMLList = _data.series;
			var seriesNum:int = 0;
			var seriesName:String="";
			
			//*Updated to fix BE-3359 (origin shows 16:00 when chart is empty). mwiley 07/02/12
			//These are a lot of conditions, but checking them in this order should minimize the
			//more-expensive e4x functions as well as unnecessary sets of dataUnits
			var hDateTimeAxis:DateTimeAxis = _display.horizontalAxis as DateTimeAxis;
			if(hDateTimeAxis && hDateTimeAxis.dataUnits != "seconds" && seriesList.length() > 0 && seriesList.datapoint != undefined && seriesList.datapoint.length() > 0){
				hDateTimeAxis.dataUnits = "seconds";
			}
			//*END fix BE-3359/
			
			for each(var seriesXml:XML in seriesList){
				seriesName = seriesXml.@name == undefined ? ""+seriesNum:(seriesXml.@name).toString();
				if( _seriesNameToDataProvider[seriesName] == undefined ) {	//this series wasn't created yet
					setSeries(seriesName, seriesNum);
					setChartStyle();
				}
				
				//if series has just one point and prev series had more than one, add dot to style to represent point.
				if (seriesXml.datapoint.length()==1 && _seriesNameToPrevNumPts[seriesName] !=1) {
					series = _seriesNameToSeriesObj[seriesName] as Series;
					series.setStyle("radius", 4);
					series.setStyle("itemRenderer", new ClassFactory(CircleItemRenderer));
				} //series has more than one point, and previously had just one point, remove dot from style
				else if (seriesXml.datapoint.length()!=1 && _seriesNameToPrevNumPts[seriesName]==1) {
					series = _seriesNameToSeriesObj[seriesName] as Series;
					series.setStyle("radius", 0);	//remove dots
					series.setStyle("itemRenderer", new ClassFactory(CircleItemRenderer));
				}
				
				seriesDataProvider = (_seriesNameToDataProvider[seriesName] as XMLListCollection);
				seriesDataProvider.removeAll();
				
				for each (var dataPoint:XML in seriesXml.datapoint){
					seriesDataProvider.addItem(dataPoint);
				}	
				_seriesNameToPrevNumPts[seriesName] = seriesXml.datapoint.length();
				seriesNum++;
				seriesDataProvider.refresh();
			}
			
			if(_chartSeriesSet.length > 1) addLegend();	
		}
		
	}
}