package com.tibco.be.views.deprecated{
	
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	
	import mx.events.FlexEvent;

	public class SynChart extends BEVComponent {
		
		private var __padding:Number;
		private var __showLegend:Boolean;
		private var __legendAnchor:String;
				
		private var primaryChartType:String;
		
		private var primaryChart:SynColumnChart;
		
		private var chartLegend:BEViewsLegend;
		
		/**
		 * Constructor
		 */
		public function SynChart(){
			super();
		}//end of constructor
		
		override protected function postInit():void {
			//initiate the properties
			initProperties();			
		}
		
		/**
		 * Initiates the properties of the Component
		 * @private
		 */
		 private function initProperties():void{
		 	__padding = 3;
		 	__showLegend = true;
		 	__legendAnchor = "north";
		 }//end of initProperties
		 
		override protected function handleConfigChange(oldConfig:XML):void {
			initUI();
		}		
		
		override protected function handleDataChange(oldData:XML):void {
		 	//initiate the chart UI
		 	showChartData();
		}				 
		
		 //DUMMY - used only in offline mode
		 /**
		 * fetches config from the local file, used for now
		 */
		 public function getOfflineConfig():void{
		 	var cReq:URLRequest = new URLRequest("data/samplechartconfig.xml");
		 	var cLoader:URLLoader = new URLLoader();
		 	cLoader.load(cReq);
		 	cLoader.addEventListener(Event.COMPLETE, handleOfflineConfig);
		 }//end of getConfig
		 
		 private function handleOfflineConfig(eventObj:Event):void{
		 	this.componentConfig = XMLList(eventObj.target.data)[0];
		 }//end of handleOfflineConfig
		 
		 /**
		 * fetches data from the local file, used for now
		 */
		 public function getOfflineData():void{
		 	var cReq:URLRequest = new URLRequest("data/samplechartdata.xml");
		 	var cLoader:URLLoader = new URLLoader();
		 	cLoader.load(cReq);
		 	cLoader.addEventListener(Event.COMPLETE, handleOfflineData);
		 }//end of getConfig
		 
		 private function handleOfflineData(eventObj:Event):void{
		 	this.componentData = XMLList(eventObj.target.data)[0];
		 }//end of handleOfflineConfig		 
		 
		 //END OF offline mode functions
		 
		 /**
		 * Gets called each time there is a change in the dimensions
		 * @protected
		 */
		 override protected function updateDisplayList(w:Number, h:Number):void{
		 	var legendElMinHeight:Number = BEViewsLegend.DEFAULT_HEIGHT;
		 	var legendElMaxWidth:Number = BEViewsLegend.DEFAULT_WIDTH;
		 	var legendResWidth:Number = (chartLegend != null)?chartLegend.legendWidth:0;
		 	var legendResHeight:Number = (chartLegend != null)?chartLegend.legendHeight:0;
		 	
		 	
		 	if(primaryChart != null){
		 		if(__showLegend){
		 			if(__legendAnchor == "north"){
		 				chartLegend.x = (legendResWidth == 0)?__padding: this.width/2 - legendResWidth/2;
		 				chartLegend.y = 0;//__padding;
		 				chartLegend.width = this.width - __padding * 2;
		 				chartLegend.height = legendElMinHeight;
		 				chartLegend.autoLayout = true;
		 				
					 	primaryChart.x = __padding;
					 	primaryChart.y = legendElMinHeight + __padding;		 		
				 		primaryChart.width = this.width - __padding * 2;
				 		primaryChart.height = this.height - __padding * 2 - legendElMinHeight;		 				
		 			}else if(__legendAnchor == "south"){
					 	primaryChart.x = __padding;
					 	primaryChart.y = __padding;		 		
				 		primaryChart.width = this.width - __padding * 2;
				 		primaryChart.height = this.height - __padding * 2 - legendElMinHeight;		 						 				
				 				 				
		 				chartLegend.x = (legendResWidth == 0)?__padding: this.width/2 - legendResWidth/2;
		 				chartLegend.y = primaryChart.height + __padding;
		 				chartLegend.width = this.width - __padding * 2;
		 				chartLegend.height = legendElMinHeight;
		 				chartLegend.autoLayout = true;
		 			}else if(__legendAnchor == "east"){
		 				//Anand 2/11/2008 - Modified the east anchoring code to allow the legend to be visible completely and to adjust the height as per changes to the legend
					 	primaryChart.x = __padding;
					 	primaryChart.y = __padding;		 		
				 		primaryChart.width = this.width - __padding * 2 - legendResWidth;
				 		primaryChart.height = this.height - __padding * 2;		 						 				
				 				 				
		 				chartLegend.x = primaryChart.width + __padding;
		 				chartLegend.y = 0;
		 				if (legendResHeight <= height) {
		 					chartLegend.y = this.height/2 - legendResHeight/2
		 				}
		 				chartLegend.width = legendResWidth;//legendElMaxWidth;
		 				chartLegend.height = this.height;
		 				chartLegend.autoLayout = true;
		 			}else if(__legendAnchor == "west"){
		 				chartLegend.x = 0;//__padding;
		 				chartLegend.y = (legendResHeight == 0)? 0: this.height/2 - legendResHeight/2;
		 				chartLegend.width = legendElMaxWidth;
		 				chartLegend.height = this.height;
		 				chartLegend.autoLayout = true;
		 						 				
					 	primaryChart.x = __padding + legendElMaxWidth;
					 	primaryChart.y = __padding;		 		
				 		primaryChart.width = this.width - __padding * 2 - legendElMaxWidth;
				 		primaryChart.height = this.height - __padding * 2;		 						 				
		 			}
		 			
		 		}else{
				 	primaryChart.x = __padding;
				 	primaryChart.y = __padding;		 		
			 		primaryChart.width = this.width - __padding * 2;
			 		primaryChart.height = this.height - __padding * 2;
		 		}
		 	}
		 }//end of updateDisplaylist
		 
		 /**
		 * Initiates painting the chart UI
		 */
		 private function initUI():void{
		 	//get the primary chart type
		 	var chartTypeConfig:XML = _compCfg.chartconfig.charttypeconfig[0];
		 	if (chartTypeConfig != null) {
			 	primaryChartType = _compCfg.chartconfig.charttypeconfig[0].@type;
			 	
			 	//for now initiate the bar chart
			 	if(primaryChart == null){
				 	primaryChart = new SynColumnChart();
				 	primaryChart.name = "primaryChart";
				 	this.addChild(primaryChart);
			 	}else{
				 	primaryChart = SynColumnChart(this.getChildByName("primaryChart"));
			 	}
			 	primaryChart.x = __padding;
			 	primaryChart.y = __padding;
			 	primaryChart.width = this.width - __padding * 2;
			 	primaryChart.height = this.height - __padding * 2;
			 	primaryChart.parentRef =  this;
			 	primaryChart.horizontalAxisRenderer = ChartUtils.getFormattedCatAxisRenderer();
			 	primaryChart.verticalAxisRenderer = ChartUtils.getFormattedLinearAxisRenderer();
			 	
			 	//legend display routine
				showLegend();
				
				//show the background of the chart
				showBackground();
			 }//end of chatTypeConfig check
		 	
		 }//end of initUI
		 
		 /**
		 * Function checks for legend settings and based on it triggers the display of the legend
		 * @private
		 */
		 private function showLegend():void{
		 	//add the legend component if it is not already created
		 	if(chartLegend == null){
		 		chartLegend = new BEViewsLegend();
		 		chartLegend.name = "chartLegend";
		 		this.addChild(chartLegend);
		 	}else{
		 		chartLegend = BEViewsLegend(this.getChildByName("chartLegend"));
		 	}
		 	
		 	//set the parent reference in the legend component
		 	chartLegend.parentRef =  this;
		 	
		 	//once the legend's creation is done, we need to call the paintign of chart just make sure its placement is proper or not
		 	chartLegend.addEventListener(FlexEvent.CREATION_COMPLETE, handleLegendUIComplete);
		 	
		 	//now check if the legend is set to be shown in the chart
		 	if(_compCfg.chartconfig.legendconfig.length() != 0){
		 		//set the legend anchor details
		 		__legendAnchor = _compCfg.chartconfig.legendconfig.@anchor;
		 		chartLegend.legendAnchor = __legendAnchor;
		 		chartLegend.showLegend();
		 	}else{
		 		
		 	}		 	
		 }//end of showLegend
		 
		 /**
		 * Function shows the background of the hart based on the settings in the XML
		 * @private
		 */
		 private function showBackground():void{
		 	
		 }//end of showBackground
		 
		 /**
		 * Shows the chart data
		 * @private 
		 */
		 private function showChartData():void{
		 	primaryChart.showData(_compData);	 	
		 	invalidateSize();
		 	invalidateDisplayList();
		 }//end of showChartData
		 
		 //EVENT HANDLERS
		 /**
		 * Gets called once the legend finishes its painting
		 * @private
		 * @param event object
		 */
		 private function handleLegendUIComplete(eventObj:FlexEvent):void{
		 	invalidateSize();
		 	invalidateDisplayList();		 	
		 }//end of handleLegendUIComplete
		 
		/**
		 * Handles component data updates. Sub classes should over ride this 
		 * method to parse and handle the component data update 
		 * @param componentData Represents the update data of the component 
		 */ 
		override public function updateData(componentData:XML):void {
			//do nothing
						
		}		 
		 
	}
}