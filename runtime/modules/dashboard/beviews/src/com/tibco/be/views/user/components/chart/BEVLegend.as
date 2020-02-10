package com.tibco.be.views.user.components.chart{
	
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Box;
	import mx.containers.TileDirection;
	import mx.events.FlexEvent;
	import mx.graphics.SolidColor;
	
	public class BEVLegend extends Box{
		
		public static const DEFUALT_MAX_WIDTH_PERCENT:Number = 0.2;
		
		private var _legendItemCount:int;
		private var _isDataBased:Boolean;
		private var _lengthSortedLegendItems:ArrayCollection;
		private var _legendItemDirectory:Dictionary;
		private var _maxWidthPercentage:Number;
				
		public function BEVLegend(){
			super();
			_lengthSortedLegendItems = new ArrayCollection();
			_legendItemCount = -1;
			_isDataBased = false;
			_maxWidthPercentage = DEFUALT_MAX_WIDTH_PERCENT;
			horizontalScrollPolicy = "off";
			verticalScrollPolicy = "off";
			//setStyle("borderStyle", "solid");
			setStyle("horizontalGap", 5);
		}
		
		public function get isDataBased():Boolean{ return _isDataBased; }
		public function get numLegendItems():Number{ return _legendItemCount; }
		public function get maxWidthPercentage():Number{ return _maxWidthPercentage; }
		
		public function set maxWidthPercentage(value:Number):void{
			_maxWidthPercentage = value > 1 ? 1:(value < DEFUALT_MAX_WIDTH_PERCENT ? DEFUALT_MAX_WIDTH_PERCENT:value);
		}
		override public function set direction(value:String):void{
			if(direction == value){ return; }
			if(value == TileDirection.HORIZONTAL){
				setStyle("horizontalAlign", "center");
				setStyle("verticalAlign", "middle");
			}
			else if(value == TileDirection.VERTICAL){
				setStyle("horizontalAlign", "left");
				setStyle("verticalAlign", "middle");
			}
			super.direction = value;
		}
		
		override protected function measure():void{
			super.measure();
			if(direction == TileDirection.HORIZONTAL){
				measuredHeight *= 0.80; //trimming to fit content
				return;
			}
			//VERTICAL only at this point
			if(_lengthSortedLegendItems.length == 0){ return; }
			var minW:Number = parent == null ? 0:parent.width * _maxWidthPercentage;
			var biggestLegendItem:BEVLegendItem = _lengthSortedLegendItems.getItemAt(_lengthSortedLegendItems.length-1) as BEVLegendItem;
			if(biggestLegendItem != null && biggestLegendItem.measuredWidth < minW){
				minW = biggestLegendItem.measuredWidth + 5;
			}
			measuredWidth = minW;
		}
		
		/**
		 * This API takes care of triggering the display of the legend and the orientation of it based on the 
		 * settings provided in the config XML
		 * @public
		 */
		public function showLegend(configXML:XML):void{
			if(configXML.chartconfig.charttypeconfig.length() > 1){
				buildOverlayChartLegend(configXML);
				sortLegendItems();
				return;
			}
			var cTypeXML:XML = configXML.chartconfig.charttypeconfig[0];
			var cType:String = cTypeXML.@type;
			switch(cType){
				case(BEVChartFactory.PIE_CHART):
					_isDataBased = true;
					buildDataBasedLegend(configXML);
					break;
				default:
					buildSeriesBasedLegend(configXML);
			}
			sortLegendItems();
		}
		
		public function update(data:XML):void{
			if(!_isDataBased) return;  //config-based legends never update
			var dataCol:XML;
			var id:String;
			var i:int = 0;
			var lItem:BEVLegendItem;
			for each(var datarow:XML in data.datarow){
				if(datarow.@templatetype == "header"){
					var validLegendItems:Dictionary = new Dictionary();
					for(i = 0; i < datarow.datacolumn.length(); i++){
						dataCol = datarow.datacolumn[i] as XML;
						id = new String(dataCol.@id);
						validLegendItems[id] = true;
					}
					for each(lItem in _lengthSortedLegendItems){
						if(validLegendItems[lItem.label] != undefined){ continue; }
						_lengthSortedLegendItems.removeItemAt(_lengthSortedLegendItems.getItemIndex(lItem));
						delete _legendItemDirectory[lItem.label];
						if(this.contains(lItem)){ this.removeChild(lItem); }
					}
					continue;
				}
				for(i = 0; i < datarow.datacolumn.length(); i++){
					dataCol = datarow.datacolumn[i] as XML;
					id = new String(dataCol.@id);
					var newColor:uint = parseInt(dataCol.basecolor, 16);
					var legendItemEntry:Object = _legendItemDirectory[id] == undefined ? null:_legendItemDirectory[id];
					if(legendItemEntry == null){
						lItem = new BEVLegendItem();
						lItem.label = id;
						lItem.styleName = "legendLblStyle";
						lItem.setStyle("fill", new SolidColor(newColor));
						_lengthSortedLegendItems.addItem(lItem); //we'll sort later
						_legendItemDirectory[lItem.label] = {legendItem:lItem, color:newColor};
						this.addChildAt(lItem, i);
						lItem.addEventListener(FlexEvent.CREATION_COMPLETE, handleNewLegendItem);
					}
					else if(legendItemEntry.color != newColor){
						(legendItemEntry.legendItem as BEVLegendItem).setStyle("fill", new SolidColor(newColor));
						legendItemEntry.color = newColor;
					}
				}
			}
			sortLegendItems();
			invalidateSize();
		}
		
		private function buildDataBasedLegend(configXML:XML):void{
			_legendItemDirectory = new Dictionary();
			try{
				_legendItemCount = configXML.visualizationdata.datarow[0].datacolumn.length();
			}
			catch(error:Error){
				_legendItemCount = 0;
			}

			this.removeAllChildren();
			
			for(var i:int = 0 ; i < _legendItemCount ; i++){
				var lItem:BEVLegendItem = new BEVLegendItem();
				lItem.label = configXML.visualizationdata.datarow.datacolumn[i].@id;
				lItem.styleName = "legendLblStyle";
				_lengthSortedLegendItems.addItem(lItem);
				_legendItemDirectory[lItem.label] = {legendItem:lItem, color:null};
				this.addChild(lItem);
			}
		}
		
		private function buildSeriesBasedLegend(configXML:XML):void{
			var cTypeXML:XML = configXML.chartconfig.charttypeconfig[0];
			_legendItemCount = cTypeXML.seriesconfig.length();

			this.removeAllChildren();
			_lengthSortedLegendItems.removeAll();
			
			for(var i:int = 0 ; i < _legendItemCount ; i++){
				var lItem:BEVLegendItem = new BEVLegendItem();
				lItem.label = new String(cTypeXML.seriesconfig[i].@displayname);
				lItem.width = width * lItem.textWidth;
				lItem.styleName = "legendLblStyle";
				var sColor:SolidColor = new SolidColor();
				sColor.color = parseInt(cTypeXML.seriesconfig[i].@basecolor, 16);
				lItem.setStyle("fill", sColor);
				_lengthSortedLegendItems.addItem(lItem);
				this.addChild(lItem);
			}
		}
		
		private function buildOverlayChartLegend(configXML:XML):void{
			var overlays:XMLList = configXML.chartconfig.charttypeconfig;
			_legendItemCount = overlays.length();

			this.removeAllChildren();
			
			for each(var chartXML:XML in overlays){
				var seriesSet:XMLList = chartXML.seriesconfig;
				var numSeries:int = seriesSet.length();
				for(var i:int = 0; i < numSeries; i++){
					var lItem:BEVLegendItem = new BEVLegendItem();
					lItem.label = new String(seriesSet[i].@displayname);
					lItem.styleName = "legendLblStyle";
					var sColor:SolidColor = new SolidColor();
					sColor.color = parseInt(seriesSet[i].@basecolor, 16);
					lItem.setStyle("fill", sColor);
					this.addChild(lItem);
				}
			}
		}
		
		private function sortLegendItems():void{
			for(var i:int = 0; i < _lengthSortedLegendItems.length; i++){
				var iItem:BEVLegendItem = _lengthSortedLegendItems[i] as BEVLegendItem;
				for(var j:int = i; j < _lengthSortedLegendItems.length; j++){
					var jItem:BEVLegendItem = _lengthSortedLegendItems[j] as BEVLegendItem;
					if(jItem.label.length < iItem.label.length){
						_lengthSortedLegendItems[i] = jItem;
						_lengthSortedLegendItems[j] = iItem;
						iItem = jItem;
					}
				}  
			}
		}
				
		private function allocateLegendItemWidths():void{
			if(direction == TileDirection.VERTICAL){
				setFixedItemWidths();
				return;
			}
			var legendSidePadding:Number = 20;
			var legendItemSidePadding:Number = getStyle("horizontalGap");
			var markerPadding:Number = BEVLegendItem.MARKER_SIZE;
			var unclippedLegendWidth:Number = 0;
			for(var i:int = 0; i < _lengthSortedLegendItems.length; i++){
				var iItem:BEVLegendItem = _lengthSortedLegendItems[i] as BEVLegendItem;
				unclippedLegendWidth += iItem.textWidth;
			}
			var paddings:Number = legendSidePadding + _lengthSortedLegendItems.length*(markerPadding + legendItemSidePadding);
			var normalization:Number = (width-paddings)/unclippedLegendWidth;
			var avgItemWidth:Number = normalization * (unclippedLegendWidth/_lengthSortedLegendItems.length) + markerPadding + legendItemSidePadding;
						
			//only works if items are sorted
			for(i = 0; i < _lengthSortedLegendItems.length; i++){
				var lItem:BEVLegendItem = _lengthSortedLegendItems[i];
				var surplus:Number = avgItemWidth - (lItem.textWidth + markerPadding);
				if(surplus > 0){
					avgItemWidth += surplus/(_lengthSortedLegendItems.length-(i+1));
					lItem.width = lItem.textWidth + markerPadding;
				}
				else{
					lItem.width = avgItemWidth;
				}
				lItem.validateNow();
			}
		}
		
		private function setFixedItemWidths():void{
			for each(var lItem:BEVLegendItem in _lengthSortedLegendItems){
				lItem.width = width - 10;
				lItem.validateNow();
			}
		}
		
		private function handleNewLegendItem(event:FlexEvent):void{
			measure();
			invalidateDisplayList();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			allocateLegendItemWidths();
		}
	}
}