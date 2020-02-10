package com.tibco.be.views.user.components.chart{
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.chart.renderers.DefaultDataTipRenderer;
	import com.tibco.be.views.user.utils.ChartMinMax;
	import com.tibco.be.views.utils.Logger;
	
	import flash.geom.Rectangle;
	import flash.utils.getQualifiedClassName;
	
	import mx.charts.ChartItem;
	import mx.charts.HitData;
	import mx.charts.chartClasses.ChartBase;
	import mx.charts.chartClasses.Series;
	import mx.charts.chartClasses.StackedSeries;
	import mx.collections.ArrayCollection;
	import mx.graphics.SolidColor;
	
	/**
	 * A wrapper for the different types of charts in the system. This class handles all common
	 * data and functions shared by the various charts
	*/
	public class BEVChart{
		
		public static const VALUE_AXIS_CONFIG_NAME:String = "valueaxisconfig";
		public static const CATEGORY_AXIS_CONFIG_NAME:String = "categoryaxisconfig";
		public static const CATEGORY_FIELD:String = "colID";
		public static const VALUE_FIELD:String = "value";
		
		public static const LOG_SCALE:String = "logarithmic";

		protected var _chart:ChartBase;
		protected var _dataProvider:ChartDataProvider;
		
		public function BEVChart(chart:ChartBase, componentConfig:XML){
			_chart = chart;
			_dataProvider = buildChartDataProvider(componentConfig);
			setDataTipFunction();
			initStyles();
			initEventListeners();
		}
		
		//Getters
		public function get view():ChartBase{ return _chart; }
		public function get width():Number{ return _chart!=null ? _chart.width:-1; }
		public function get height():Number{ return _chart!=null ? _chart.height:-1; }
		public function get dataProvider():ChartDataProvider{ return _dataProvider; }
				
		//Setters		
		public function set name(value:String):void{ if(_chart!=null) _chart.name = value;}
		public function set bgColor(value:Number):void{ _chart.setStyle("fill", new SolidColor(value)); }
				
		//Virtual Methods
		public virtual function buildSeriesSet(compConfigXML:XML):void{}
		public virtual function setChartBoundsAndInterval(minMax:ChartMinMax, forceZeroOrigin:Boolean=false):void{}
		protected virtual function setTypeSpecificProperties(properties:XMLList):void{}
		
		//Methods
		/**
		 * Initializes the chart configuration (axes, axisRenderers, colors, etc.).
		*/
		public function initChartConfig(chartConfig:XML):void{}
		
		protected function buildChartDataProvider(componentConfig:XML):ChartDataProvider{
			return new ChartDataProvider(componentConfig.chartconfig[0]);
		}
		
		protected function setDataTipFunction():void{
			//if child classes don't want to specify a datatip function, they can override this
			//function and do nothing.
			_chart.dataTipFunction = dataTipFunciton;
		}
		
		protected function initStyles():void{
			_chart.showDataTips = true;
			_chart.setStyle("dataTipRenderer", DefaultDataTipRenderer);
			_chart.setStyle("paddingTop", 2);
			_chart.setStyle("paddingRight", 5);
		}
		
		protected function initEventListeners():void{
			
		}
		
		public function setCategoryAxisData(vizDataXML:XML):void{
			_dataProvider.setCategoryAxisData(vizDataXML);
			_chart.dataProvider = _dataProvider.categoryAxisData;
		}
		
		protected function initChartSeriesConfigs(compConfigXML:XML):ArrayCollection{
			return ChartUtils.getProcessedSeriesConfigs(compConfigXML);
		}
		
		public function setDimensions(x:Number, y:Number, width:Number, height:Number):void{
			if(_chart == null){
				Logger.log(DefaultLogEvent.WARNING, "BEViewsChart.setDimensions - Null chart!");
				return;
			}
			//don't set values or update display if nothing's changed
			if(_chart.x != x || _chart.y != y || _chart.width != width || _chart.height != height){
				_chart.x = x;
				_chart.y = y;
				_chart.width = width;
				_chart.height = height;
				//_chart.validateNow();
			}
		}
		
		public function handleDataSet(dataXML:XML):void{
			var minMax:ChartMinMax = _dataProvider.setSeriesData(dataXML);
			setChartBoundsAndInterval(minMax);
		}
				
		public function updateChartData(componentData:XML):void{
			if(componentData.datarow != undefined){
				var headerRows:XMLList = componentData.datarow.(@templatetype=="header");
				for each(var headerRow:XML in headerRows){
					_dataProvider.updateCategoryAxisData(headerRow);
					break; //only considering the first header datarow
				}
			}
			//don't put the next 2 lines in the above if block, it will break purge
			var minMax:ChartMinMax = _dataProvider.updateSeriesData(componentData);
			setChartBoundsAndInterval(minMax);
		}
		
		protected function error(severity:uint, message:String, functionName:String):void{
			var chartName:String = flash.utils.getQualifiedClassName(this).split(".").pop() as String;
			Logger.log(severity, chartName + "." + functionName + " - " + message);
		}
		
		protected function dataTipFunciton(hitData:HitData):String{
			return hitData.item.tooltip; //item is DataColumn or RangeDataPoint
		}
		
		protected function getScaleString(scale:String):String{
			switch(scale){
				case("thousands"): return " (Thousands)";
				case("millions"):  return " (Millions)";
				case("billions"):  return " (Billions)";
				case("trillions"): return " (Trillions)";
				case("quadrillions"): return " (Qadrillions)";
			}
			return "";
		}
		
		public function addEventListener(eventType:String, listenerFunction:Function):void{
			_chart.addEventListener(eventType, listenerFunction);
		}
		
		public function removeEventListener(eventType:String, listenerFunction:Function):void{
			_chart.removeEventListener(eventType, listenerFunction);
		}
				
		public function removeSeriesEventListener(eventType:String, listenerFunction:Function):void{
			for each(var s:Series in _chart.series){
				s.removeEventListener(eventType, listenerFunction);
			}
		}
		
		public function getItemsInRegion(value:Rectangle, target:Object=null):Array{
			return _chart.getItemsInRegion(value);
		}
		
		public function getPointsByCategory(categoryValue:String):Array{
			categoryValue = categoryValue.replace(/\s/g, "");
			var points:Array = new Array();
			var seriesSet:Array = _chart.series;
			if(seriesSet.length > 0 && seriesSet[0] is StackedSeries){
				seriesSet = (seriesSet[0] as StackedSeries).series;
			}
			for each(var series:Series in seriesSet){
				var i:int = 0;
				for each(var dataPoint:ChartItem in series.items){
					try{
						if(dataPoint.item.colID == categoryValue){
							points.push(series.items[i]);
						}
					}
					catch(error:Error){
						trace("BEViewsChart.getItemsByCategory - " + error.message);
					}
					i++;
				}
			}
			return points;
		}
	}
}