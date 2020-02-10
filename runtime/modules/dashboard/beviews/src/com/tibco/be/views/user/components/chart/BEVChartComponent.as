package com.tibco.be.views.user.components.chart{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.components.chart.renderers.ChartCategoryTickLabel;
	import com.tibco.be.views.user.components.chart.series.BEVLineSeries;
	import com.tibco.be.views.user.components.chart.series.IBEVSeries;
	import com.tibco.be.views.utils.BEVUtils;
	import com.tibco.be.views.utils.Logger;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	
	import mx.charts.ChartItem;
	import mx.containers.TileDirection;
	import mx.core.UITextField;
	import mx.events.FlexEvent;
	
	/** Spacing between a "north" anchored legend and the chart */
	[Style(name="chartLegendSpacingTop", type="Number", format="Number", inherit="no")]
	/** Spacing between a "south" anchored legend and the chart */
	[Style(name="chartLegendSpacingBottom", type="Number", format="Number", inherit="no")]
	/** Spacing between a "east" anchored legend and the chart */
	[Style(name="chartLegendSpacingLeft", type="Number", format="Number", inherit="no")]
	/** Spacing between a "west" anchored legend and the chart */
	[Style(name="chartLegendSpacingRight", type="Number", format="Number", inherit="no")]

	public class BEVChartComponent extends BEVComponent{
		
		private var _showLegend:Boolean;
		private var _legendAnchor:String;
		private var _primaryChart:BEVChart;		
		private var _chartLegend:BEVLegend;
		
		public function BEVChartComponent(){
			super();
		}
				
		override protected function preInit():void{
			initProperties();			
		}
		
		public function initProperties():void{
			_showLegend = true;
			_legendAnchor = "north";
		}
		 
 		//Called when property componentConfig is set in the parent class 
		override protected function handleConfigSet(compConfigXML:XML):void{
			buildComponent(compConfigXML);
			addEventListener(Event.REMOVED_FROM_STAGE, handleRemoved);
			_primaryChart.setCategoryAxisData(compConfigXML.visualizationdata[0]);
			_primaryChart.buildSeriesSet(compConfigXML);
			_primaryChart.addEventListener(MouseEvent.CLICK, handleChartClicked);
		}
		
		//Called when property componentData is set in the parent class
		override protected function handleDataSet(dataXML:XML):void{
			_primaryChart.handleDataSet(dataXML);
		 	if(_chartLegend != null) _chartLegend.update(dataXML);
		}
				
		/**
		 * Handles component data updates.
		 * @param componentData Represents the update data of the component 
		*/ 
		override public function updateData(componentData:XML):void{
			_primaryChart.updateChartData(componentData);
			if(_chartLegend != null && _chartLegend.isDataBased){
				_chartLegend.update(componentData);
			}
		}
		
		/** Constructs this component using the provided component configuration XML */
		private function buildComponent(configXML:XML):void{
			//get the primary chart type
			var chartConfig:XML = configXML.chartconfig[0];
			var chartTypeConfig:XML = chartConfig.charttypeconfig[0];
			if(chartTypeConfig != null){ //initiate the chart
				_chartLegend = null;
				removeAllChildren();
				_primaryChart = BEVChartFactory.instance.buildChart(configXML);
				_primaryChart.initChartConfig(chartConfig);
				addChild(_primaryChart.view);//x, y, width, and height set in updateDisplayList
				showLegend(configXML);
				showBackground(configXML);
			 }
		 }
		 
		/**
		 * Function checks for legend settings and based on it triggers the display of the legend
		 * @private
		*/
		private function showLegend(configXML:XML):void{
			if(configXML.chartconfig.legendconfig == undefined) return;
			//add the legend component if it is not already created
			if(_chartLegend == null){
				_chartLegend = new BEVLegend();
				_chartLegend.name = "chartLegend";
				if(_primaryChart is BEVPieChart && _layoutConstraints != null){
 					_chartLegend.maxWidthPercentage = BEVLegend.DEFUALT_MAX_WIDTH_PERCENT * (_layoutConstraints.width/_layoutConstraints.height);
 				}
 				//once the legend's creation is done, we need to call the painting of the chart to make sure its placement is correct
				_chartLegend.addEventListener(FlexEvent.CREATION_COMPLETE, handleLegendUIComplete);
				addChild(_chartLegend);
			}
			if(configXML.chartconfig.legendconfig.length() != 0){
				_legendAnchor = new String(configXML.chartconfig.legendconfig.@anchor);
				_chartLegend.showLegend(configXML);
			}
		}
		
		/**
		 * Function shows the background of the chart based on the settings in the XML
		 * @private
		*/
		private function showBackground(configXML:XML):void{
			try{
				var xml:XML = configXML.chartconfig[0] as XML;
				var bgColor:Number = parseInt(xml.@backgroundcolor, 16);
				this.setStyle("backgroundColor", bgColor);
			}
			catch(error:Error){
				handleError(DefaultLogEvent.WARNING, error.message, "showBackground"); 
			}
		}
		
		private function handleChartClicked(event:MouseEvent):void{
			var series:IBEVSeries;
			var dataPoints:Array;
			
			//If a category value is clicked, show a list menu of data points for that category
			if(event.target != null && event.target.parent is ChartCategoryTickLabel){
				var renderer:ChartCategoryTickLabel = event.target.parent as ChartCategoryTickLabel;
				dataPoints = _primaryChart.getPointsByCategory(renderer.text);
				if(dataPoints.length == 0){
					return;
				}
				//If there are any datapoints for the clicked category, put an extra fake point
				//(doesn't contain series) in the array so that the menu shown is a always a list
				//menu. The fake item will be skipped when creating the menu.
				if(!_primaryChart is BEVVRangeChart){ dataPoints.push(new ChartItem()); }
			}
			else if(event.target is UITextField && _primaryChart is BEVPieChart){
				//ignore click
			}
			//If the click is inside the chart, generate the menu based off of chart data points
			else{
				var hitRange:Rectangle = new Rectangle(0,0,10,10);
		        hitRange.x = Math.max(0, event.stageX-5);
		        hitRange.y = Math.max(0, event.stageY-5);
		    	dataPoints = _primaryChart.getItemsInRegion(hitRange, event.target);
		    }
		    
	       	if(dataPoints == null || dataPoints.length == 0 || dataPoints[0] == null){ return; }
	       	
	       	//Area charts are a special case. They have a line and an area series for every
	       	//seriesconfig node in the data. We thus remove line series points to prevent confusion
	       	//when creating the menus
	       	if(_primaryChart is BEVAreaChart){
	       		for(var i:int = 0; i < dataPoints.length; i++){
	       			if(dataPoints[i].element is BEVLineSeries){
	       				dataPoints.splice(i,1);
	       				i--;
	       			}
	       		}
	       	}
	       	
	       	//Create the action context provider for the generated menu
	       	var actionCtxProvider:IActionContextProvider = new DataPointActionContextProvider(dataPoints);
	       	
	       	//Build the XML that will serve as the menu data provider.
	       	var rootActionConfig:XML = null;
	       	if(dataPoints.length == 1){
	       		series = dataPoints[0].element as IBEVSeries;
	       		if(series == null){
	       			return;
	       		}
	       		rootActionConfig = series.actionConfig;
	       	}
	       	else{
	       		rootActionConfig = new XML("<actionconfig disabled=\"false\"><text>ROOT</text></actionconfig>");
		       	for each(var dataPoint:ChartItem in dataPoints){
		       		
		       		//get series config from the datapoint's element which should be series
		       		series = dataPoint.element as IBEVSeries;
		       		if(series == null || series.actionConfig == null){
		       			Logger.log(DefaultLogEvent.DEBUG, "BEViewsChartComponent.handleChartClicked - When building menu, datapoint has invalid IBEViewsSeries (" + series + ").");
		       			continue;
		       		}
		       		else if(dataPoint.item == null){
		       			Logger.log(DefaultLogEvent.DEBUG, "BEViewsChartComponent.handleChartClicked - When building menu, dataPoint item is null.");
		       			continue;
		       		}
		       		
		       		//create the aggregated menu's menu item that will display the dataPoint's menu as a submenu
		       		var columnId:String = "";
		       		var menuLabel:String = "";
		       		var seriesActionConfig:XML = null;
		       		if(dataPoint.item is DataColumn){
		       			columnId = new String(dataPoint.item.colID);
		       			menuLabel = new String(dataPoint.item.displayValue);
		       			if(menuLabel == ""){ menuLabel = new String(dataPoint.item.value); }
		       			if(menuLabel == ""){ menuLabel = new String(dataPoint.item.colID); }
		       			seriesActionConfig = new XML(
							"<actionconfig id=\"" + series.seriesConfig.@name + ((columnId == null || columnId == "")?"":"."+columnId) + "\" " + 
							"disabled=\"false\" " + 
							"iconColor=\"" + series.seriesConfig.@basecolor + "\">" +
								"<text>"+menuLabel+"</text>" + 
							"</actionconfig>"
						);
		       		}
		       		else if(dataPoint.item is RangeDataPoint){
		       			columnId = new String(dataPoint.item.colID);
		       			menuLabel = new String(dataPoint.item.colID);
		       			if(menuLabel == ""){ menuLabel = new String(dataPoint.item.currentDisplayValue); }
		       			seriesActionConfig = new XML(
							"<actionconfig id=\"" + series.seriesConfig.@name + ((columnId == null || columnId == "")?"":"."+columnId) + "\" " + 
							"disabled=\"false\">" +
								"<text>"+menuLabel+"</text>" + 
							"</actionconfig>"
						);
		       		}
					else{
						Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".handleChartClicked - Unrecognized data point item.");
						return;
					}
					
					//add all of the dataPoint's menu items (actionconfig nodes) to the aggregated menu item
					for each(var childActionCfg:XML in series.actionConfig.actionconfig){
						seriesActionConfig.appendChild(new XML(childActionCfg));
					}
					
					//add the aggreagated menu item to the aggregated menu
					rootActionConfig.appendChild(seriesActionConfig);	
		       	}	
	       		
	       	}
       		componentContext.showMenu(
       			event.stageX,
       			event.stageY,
       			rootActionConfig,
       			actionCtxProvider
   			);
		}
		
		/**
		 * Gets called once the legend finishes its painting
		 * @private
		 * @param event object
		*/
		private function handleLegendUIComplete(eventObj:FlexEvent):void{
			invalidateSize();
			invalidateDisplayList();		 	
		}
		
		private function handleError(severity:uint, message:String, functionName:String):void{
			Logger.log(severity, "BEViewsChartComponent." + functionName + " - " + message);
		}
		
		private function handleRemoved(event:Event):void{
			removeEventListener(Event.REMOVED_FROM_STAGE, handleRemoved);
			_primaryChart.removeSeriesEventListener(MouseEvent.CLICK, handleChartClicked);
		}
		
		/**
		 * Gets called each time there is a change in the dimensions
		 * @protected
		*/
		override protected function updateDisplayList(w:Number, h:Number):void{
			super.updateDisplayList(w, h);
			var legendWidth:Number = (_chartLegend != null) ? _chartLegend.measuredWidth:0;
			var legendHeight:Number = (_chartLegend != null) ? _chartLegend.measuredHeight:0;
			
			var leftPad:int = getStyle("paddingLeft");
			var rightPad:int = getStyle("paddingRight");
			var topPad:int = getStyle("paddingTop");
			var botPad:int = getStyle("paddingBottom");
			var chartPad:int = 0;
			
			if(_primaryChart != null){
				if(_showLegend && _chartLegend != null){
					var legendX:Number = 0;
			 		var legendY:Number = 0;
					_chartLegend.autoLayout = true;
					if(_legendAnchor == "north"){
						chartPad = getStyle("chartLegendPaddingTop");
						
						_chartLegend.direction = TileDirection.HORIZONTAL;
						_chartLegend.width = width;
						_chartLegend.move(0, topPad);
						
						_primaryChart.setDimensions(
							leftPad,
							legendHeight + topPad + chartPad,
							this.width - leftPad - rightPad,
							this.height - topPad - botPad - chartPad - legendHeight
						);
		 			}
		 			else if(_legendAnchor == "south"){
		 				chartPad = getStyle("chartLegendPaddingBottom");
		 				
					 	_primaryChart.setDimensions(
					 		leftPad,
					 		topPad,		 		
				 			this.width - leftPad - rightPad,
				 			this.height - topPad - botPad - chartPad - legendHeight
				 		);
				 		
				 		_chartLegend.direction = TileDirection.HORIZONTAL;
						_chartLegend.width = width;
		 				_chartLegend.move(0, topPad + _primaryChart.height + chartPad);
		 			}
		 			else if(_legendAnchor == "east"){
		 				chartPad = getStyle("chartLegendPaddingRight");
		 				
					 	_primaryChart.setDimensions(
					 		leftPad,
					 		topPad,		 		
				 			this.width - leftPad - chartPad - legendWidth - rightPad,
				 			this.height - topPad - botPad
				 		);		 						 				
				 		
				 		legendX = (leftPad + _primaryChart.width + chartPad)
				 		legendY = legendHeight <= height ? height/2 - legendHeight/2:0
				 		_chartLegend.direction = TileDirection.VERTICAL;
						_chartLegend.width = legendWidth;
		 				_chartLegend.move(legendX, legendY);
		 				
		 			}
		 			else if(_legendAnchor == "west"){
		 				chartPad = getStyle("chartLegendPaddingLeft");
		 				
		 				legendX = leftPad;
		 				legendY = (legendHeight == 0)? 0: this.height/2 - legendHeight/2;
		 				_chartLegend.direction = TileDirection.VERTICAL;
						_chartLegend.width = legendWidth;
		 				_chartLegend.move(legendX, legendY);
		 				
		 				//_chartLegend.maxWidth = _primaryChart.width*0.2; //causes problems when legend direction is horizontal
					 	_primaryChart.setDimensions(
					 		leftPad + legendWidth + chartPad,
					 		topPad,		 		
				 			this.width - leftPad - chartPad - legendWidth - rightPad,
				 			this.height - topPad - botPad
				 		);					 				
		 			}
		 			_chartLegend.invalidateDisplayList();
		 		}
				else{
				 	_primaryChart.setDimensions(
				 		leftPad,
				 		topPad,
			 			this.width - leftPad - rightPad,
			 			this.height - topPad - botPad
			 		);
		 		}
		 	}
		 }	 
		 
		 override public function destroy():void{
		 	super.destroy();
		 	_primaryChart.removeEventListener(MouseEvent.CLICK, handleChartClicked);
		 }
	}
}