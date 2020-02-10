package com.tibco.be.views.user.components.chart{
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.utils.ChartMinMax;
	import com.tibco.be.views.utils.Logger;
	
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	
	/**
	 * Since the xml is interpreted differently for range charts, we need special processing to
	 * build the chart's series object. What's expected is three data rows, one for min, max,
	 * and current.  Within each row is a datacolumn for each point in the series. Points are built
	 * by combining the datacolumn values for each of the three rows into a RangeDataPoint. The
	 * set of RangeDataPoints is then used as the dataprovider for the series.
	*/
	public class RangeChartDataProvider extends ChartDataProvider{
		
		private var _seriesID:String;
		
		public function RangeChartDataProvider(chartConfig:XML){
			super(chartConfig);
			_seriesID = "";
		}
		
		public function get seriesID():String{ return _seriesID; }
		
		override public function setSeriesData(dataXML:XML):ChartMinMax{
			_seriesID = new String(dataXML.@componentid);
			var provider:ArrayCollection = _seriesDataProviders[_seriesID] as ArrayCollection;
			if(provider == null){
				trace("RangeChartDataProvider.setSeriesData - seriesProvider[" + _seriesID + "] is null.");
				return new ChartMinMax();
			}
			provider.removeAll();
			provider.source = getRangeItems(dataXML, _seriesDataPoints).toArray();
			return calcMinMax(provider);
		}
		
		override public function updateSeriesData(updateXML:XML):ChartMinMax{
			var provider:ArrayCollection = _seriesDataProviders[_seriesID] as ArrayCollection;
			if(provider == null){
				trace("RangeChartDataProvider.updateSeriesData - seriesProvider[" + _seriesID + "] is null.");
				return new ChartMinMax();
			}
			if(updateXML.datarow == undefined){//server rollover -> purge
				purge();
				provider.refresh();
				return new ChartMinMax();
			}
			var updateValues:ArrayCollection = getRangeItems(updateXML);
			//go through new range items and update or add values in the series data
			var i:int = 0;
			for each(var newVal:RangeDataPoint in updateValues){
				if(newVal.isFlaggedForDeletion){ //newVal isn't new, it's been removed likely due to server roll-over
					if(provider.contains(newVal)){ provider.removeItemAt(provider.getItemIndex(newVal)); }
					delete _seriesDataPoints[newVal.colID];
					newVal.destroy(); //necessary so LabeledHLOCItemRenderer won't render
					continue;
				}
				var currentVal:RangeDataPoint = _seriesDataPoints[newVal.colID];
				if(currentVal == null){ //new point doesn't already exist, add it
					provider.addItemAt(newVal, i);
					_seriesDataPoints[newVal.colID] = newVal;
				}
				else if(currentVal.needsUpdateFrom(newVal)){
					currentVal.updateFrom(newVal);
				}
				i++;
			}
			provider.refresh();
			return calcMinMax(provider);
		}
		
		override public function setCategoryAxisData(vizDataXML:XML):void{
			if(vizDataXML == null){
				Logger.log(DefaultLogEvent.WARNING, "RangeChartDataProvider.setCategoryAxisData - Tried to set null axis data.");
				return;
			}
			if(_categoryAxisProvider == null){
				_categoryAxisProvider = new ArrayCollection();
			}
			try{
				_categoryAxisProvider.removeAll();
				var datarow:XML = vizDataXML.datarow.(@templatetype=="header")[0];
				if(datarow == null){ return; }
				var dc:DataColumn; //The category still uses DataColumns despite the series data being RangeDataPoints
				for each(var colXML:XML in datarow.datacolumn){
					dc = new DataColumn(colXML);
					_categoryAxisProvider.addItem(dc);
					_categoryAxisDataColumns[dc.colID] = dc;
				}
			}
			catch(error:Error){
				Logger.log(DefaultLogEvent.WARNING, "RangeChartDataProvider.setCategoryAxisData - Failed Category Axis Data Provider Creation!");
			}
		}
		
		override public function updateCategoryAxisData(rowXML:XML):void{
			var axisDataColumn:DataColumn;
			var i:int;
			var currentCols:Dictionary = new Dictionary();;
			
			//add new axis items
			var colIndex:int = 0;
			for each(var colXML:XML in rowXML.datacolumn){
				var colID:String = new String(colXML.@id);
				currentCols[colID] = true;
				if(_categoryAxisDataColumns[colID]){
					//no updating, just removal and addition
				}
				else{
					axisDataColumn = new DataColumn(colXML);
					_categoryAxisDataColumns[colID] = axisDataColumn;
					_categoryAxisProvider.addItemAt(axisDataColumn, colIndex);
				}
				colIndex++;
			}
			
			//clear out stale axis items
			for(i = 0; i < _categoryAxisProvider.length; i++){
				axisDataColumn = _categoryAxisProvider.getItemAt(i) as DataColumn;
				if(!currentCols[axisDataColumn.colID]){
					delete _categoryAxisDataColumns[axisDataColumn.colID];
					_categoryAxisProvider.removeItemAt(i);
					i--;
				}
			}
			_categoryAxisProvider.refresh();
			
			//clear out all stale series data
			var provider:ArrayCollection = _seriesDataProviders[_seriesID] as ArrayCollection;
			for(i = 0; i < provider.length; i++){
				var rdp:RangeDataPoint = provider.getItemAt(i) as RangeDataPoint;
				if(!currentCols[rdp.colID]){
					delete _seriesDataPoints[rdp.colID];
					provider.removeItemAt(i);
					i--;
				}
			}
			provider.refresh();

		}
		
		/**
		 * @param dataXML The XML to parse and extract RangeDataPoints from
		 * @param rdpDirectory An optional Dictionary that will be populated with all parsed RangeDataPoints identified by colID 
		 * @return An array containing all range data points defined in dataXML
		*/
		private function getRangeItems(dataXML:XML, rdpDirectory:Dictionary=null):ArrayCollection{
			if(rdpDirectory == null){ rdpDirectory = new Dictionary(); }
			var rdp:RangeDataPoint;
			var parsedRDPs:ArrayCollection = new ArrayCollection();
			//loop the three datarows for current, minimum, and maximum points
			for(var i:int = 0; i < dataXML.datarow.length(); i++){
				var row:XML = dataXML.datarow[i];
				if(row.@templatetype == "header"){ continue; }
				//loop through each category and set the current/minimum/maximum values in the corresponding range datapoint
				for each(var col:XML in row.datacolumn){
					var colID:String = new String(col.@id);
					rdp = rdpDirectory[colID] as RangeDataPoint;
					if(rdp == null){
						if(_seriesDataPoints[colID] != undefined){ rdp = _seriesDataPoints[colID]; }
						else{ rdp = new RangeDataPoint(col); }
						parsedRDPs.addItem(rdp);
						rdpDirectory[colID] = rdp;
					}
					if(rdp.colID == null || rdp.colID == ""){ rdp.colID = colID; }
					if(col.value == "NULL"){
						if(rdp != null){
							rdp.flagForDeletion();
							if(!parsedRDPs.contains(rdp)){ parsedRDPs.addItem(rdp); }
						}
						else{
							Logger.log(DefaultLogEvent.DEBUG, "RangeChartDataProivder.getRangeItems - Failed flagging data point for removal after roll-over.");
						}
						break;
					}
					switch(String(row.@templateid)){
						case(BEVVRangeChart.CURRENT_SERIES_NAME):
							rdp.current = new Number(col.value);
							rdp.currentDisplayValue = new String(col.displayvalue);
							rdp.currentToolTip = new String(col.tooltip);
							//link and type specific attribs are the same for current, min, and max
							rdp.link = new String(col.link);
							for each(var attribXML:XML in col.typespecificattribute){
								var name:String = new String(attribXML.@name);
								var value:String = new String(attribXML);
								rdp.typeSpecificAttribs[name] = value;
							}
							//currently only honoring alert color for the current value
							rdp.alertFont = rdp.alertColors = null;
							if(col.font != undefined){
								rdp.alertFont = {"color":new String(col.font.@color), "style":new String(col.font.@style)};
							}
							if(col.basecolor != undefined){
								try{
									var baseColor:Number = parseInt(col.basecolor, 16);
									var highlightColor:Number = parseInt(col.highlightcolor, 16);
									if(isNaN(baseColor)){ rdp.alertColors = null; }
									else{
										if(isNaN(highlightColor)){ highlightColor = 0xFFFFFF; }
										rdp.alertColors = {"baseColor":baseColor, "highlightColor":highlightColor};
									}
								}
								catch(error:Error){
									rdp.alertColors = null;
								}
							}
							break;
						case(BEVVRangeChart.MIN_SERIES_NAME):
							rdp.min = new Number(col.value);
							rdp.minDisplayValue = new String(col.displayvalue);
							rdp.minToolTip = new String(col.tooltip);
							break;
						case(BEVVRangeChart.MAX_SERIES_NAME):
							rdp.max = new Number(col.value);
							rdp.maxDisplayValue = new String(col.displayvalue);
							rdp.maxToolTip = new String(col.tooltip);
							break;
						default:
							break;
					}
				}
			}
			//if min or max is not specified in the data, default them to avg
			var minSet:Boolean = dataXML.datarow.(@templateid == "minvalue").length() > 0;
			var maxSet:Boolean = dataXML.datarow.(@templateid == "maxvalue").length() > 0;
			if(!minSet || !maxSet){
				for each(rdp in rdpDirectory){
					if(!minSet){
						rdp.min = rdp.current;
						rdp.minDisplayValue = rdp.currentDisplayValue;
						rdp.minToolTip = rdp.currentToolTip;
					}
					if(!maxSet){
						rdp.max = rdp.current;
						rdp.maxDisplayValue = rdp.currentDisplayValue;
						rdp.maxToolTip = rdp.currentToolTip;
					}
				}
			}
			return parsedRDPs;
		}
		
		private function calcMinMax(provider:ArrayCollection):ChartMinMax{
			var minMax:ChartMinMax = new ChartMinMax();
			for each(var val:RangeDataPoint in provider){
				var min:Number = Math.min(val.min, Math.min(val.current, val.max)); //handle user mistakes gracefully
				var max:Number = Math.max(val.max, Math.max(val.current, val.min)); //handle user mistakes gracefully
				if(max > minMax.max){ minMax.max = max; }
				if(min < minMax.min){ minMax.min = min; }
			}
			return minMax;
		}
		
	}
}