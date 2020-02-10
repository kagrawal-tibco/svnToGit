package com.tibco.be.views.user.components.chart{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.utils.SortedNumericList;
	import com.tibco.be.views.user.utils.ChartMinMax;
	import com.tibco.be.views.utils.Logger;
	
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	
	/**
	 * ChartDataProvider is responsible for creating and maintaining datastructures that enable,
	 * point-specific updates to chart data. Tracking and performing data updates only on those
	 * points whose values have changed, saves CPU cycles and helps reduce UI lag (or at least
	 * that's the theory).
	*/
	public class ChartDataProvider{
		
//		protected var _legendProvider:ArrayCollection;
//		protected var _legendDataColumns:Dictionary;
		
		/** The array collection used as a data provider for the category axis. */
		protected var _categoryAxisProvider:ArrayCollection;
		
		/** A dictionary of data points to enable quick referencing while updating a category axis. */
		protected var _categoryAxisDataColumns:Dictionary;
		
		/** A dictionary that maps seriesId to the series data provider ArrayCollection. */
		protected var _seriesDataProviders:Dictionary;
		
		/** A dictionary of all data points to enable quick referencing while updating series data. */
		protected var _seriesDataPoints:Dictionary;
		
		/** A sorted list of values contained in the chart used to maintain chart bounds */
		protected var _chartBoundsQueue:SortedNumericList;
		
		/** A dictionary of the positive and negative sums at each category. This is used for chart bounds calculation. */
		protected var _categorySumsDirectory:Dictionary = new Dictionary();
		
		/** A dictionary mapping series ID to a data value mutator function */
		protected var _seriesValueModifiers:Dictionary;
		
		
		public function ChartDataProvider(chartConfig:XML){
			_seriesDataProviders = new Dictionary();
			_seriesDataPoints = new Dictionary();
			_chartBoundsQueue = new SortedNumericList();
			_categoryAxisDataColumns = new Dictionary();
			_seriesValueModifiers = new Dictionary();
			_categorySumsDirectory = new Dictionary();
			for each(var chart:XML in chartConfig.charttypeconfig){
				var seriesID:String = "";
				if(chart.@type == "verticalrange" || chart.@type == "horizontalrange"){
					seriesID = new String(chartConfig.@componentid);
					_seriesDataProviders[seriesID] = new ArrayCollection();
					break;
				}
				for each(var series:XML in chart.seriesconfig){
					seriesID = new String(series.@name);
					_seriesDataProviders[seriesID] = new ArrayCollection();
				}
			}
		}
		
		public function get seriesModifiers():Dictionary{ return _seriesValueModifiers; }
		
		public function getSeriesData(seriesID:String):ArrayCollection{
			if(!_seriesDataProviders[seriesID]){
				Logger.log(DefaultLogEvent.WARNING, "ChartDataProvider.getSeriesData - No series data provider entry for: " + seriesID);
				return null;
			}
			return _seriesDataProviders[seriesID] as ArrayCollection;
		}
		
		/**
		 * Parses the data in the visualizationdata node into ArrayCollections of DataColumn
		 * objects. The array collections then serve as the data providers for the different series
		 * being displayed in the chart.
		 * 
		 * @param dataXML The visualizationdata node to parse.
		 * @return An object containing a min and max value indicating the min and max values that
		 * 			will be shown on the chart. (Used for setting chart bounds and interval)
		*/
		public function setSeriesData(dataXML:XML):ChartMinMax{
			var minMax:ChartMinMax = new ChartMinMax();
			_chartBoundsQueue.clear();
			for each(var rowXML:XML in dataXML.datarow.(@templatetype=="data")){
				var seriesID:String = new String(rowXML.@templateid);
				var provider:ArrayCollection = _seriesDataProviders[seriesID] as ArrayCollection;
				if(provider == null){
					Logger.log(DefaultLogEvent.WARNING, "ChartDataProvider.setSeriesData - seriesProvider[" + seriesID + "] is null.");
					continue;
				}
				provider.removeAll();
				for each(var colXML:XML in rowXML.datacolumn){
					var colID:String = new String(colXML.@id);
					var dataID:String = seriesID + "##" + colID;
					var dc:DataColumn = new DataColumn(colXML);
					if(_seriesValueModifiers[seriesID] != undefined){
						(_seriesValueModifiers[seriesID] as ISeriesDataModifier).modifyData(dc);
					}
					_chartBoundsQueue.add(dc.value);
					_seriesDataPoints[dataID] = dc;
					provider.addItem(dc);
					if(_categorySumsDirectory[colID] == undefined){
						_categorySumsDirectory[colID] = new CategorySums(dc.value, dc.value);
					}
					else{
						(_categorySumsDirectory[colID] as CategorySums).addValue(dc.value);
					}
				}				
			}
			for each(var sumSet:CategorySums in _categorySumsDirectory){
				if(sumSet.negativeSum < minMax.minCategorySum){ minMax.minCategorySum = sumSet.negativeSum; }
				if(sumSet.positiveSum > minMax.maxCategorySum){ minMax.maxCategorySum = sumSet.positiveSum; }
			}
			if(isNaN(_chartBoundsQueue.maxValue)){  //no data, use default values
				minMax.min = 0;
				minMax.max = 100/1.1; //set bounds will multiply max by 1.1 for upper bound
			}
			else{
				minMax.min = _chartBoundsQueue.minValue;
				minMax.max = _chartBoundsQueue.maxValue;
			}
			return minMax;
		}
		
		/**
		 * Updates all changed datapoints in all series data provider array collections. If a
		 * datapoint's values have not changed, no change will be made to the object. This improves
		 * performance by only redrawing those points that have changed.
		 * 
		 * Note: This function updates and/or adds data. Removal of datapoints is handled when a
		 * 		datarow node with @templatetype == "header" is detected in updateCategoryAxisData.
		 * 
		 * @param updateXML The visualizationdata node containing the update data.
		*/
		public function updateSeriesData(updateXML:XML):ChartMinMax{
			var dc:DataColumn;
			var dataID:String;
			var seriesID:String;
			var provider:ArrayCollection;
			var currentCols:Dictionary;
			var minMax:ChartMinMax = new ChartMinMax();
			
			if(updateXML.datarow == undefined){
				//handle server purge rollover
				purge();
			}
			else{
				for each(var rowXML:XML in updateXML.datarow.(@templatetype=="data")){
					seriesID = new String(rowXML.@templateid);
					provider = _seriesDataProviders[seriesID] as ArrayCollection;
					if(provider == null){
						Logger.log(DefaultLogEvent.WARNING, "ChartDataProvider.updateSeriesData - seriesProvider[" + seriesID + "] is null.");
						continue;
					}
					var colIndex:int = 0;
					for each(var colXML:XML in rowXML.datacolumn){
						dataID = seriesID + "##" + new String(colXML.@id);
						//Update the data column if it exists
						if(_seriesDataPoints[dataID]){
							dc = _seriesDataPoints[dataID] as DataColumn;
							if(colXML.value == "NULL" && provider.getItemIndex(dc) != -1){
								//server reset rollover will remove data points specifically
								(_categorySumsDirectory[dc.colID] as CategorySums).subtractValue(dc.value);
								provider.removeItemAt(provider.getItemIndex(dc));
								delete _seriesDataPoints[dataID];
								dc.destroy();
								continue;
							}
							var previousValue:Number = dc.value;
							//we need to prevent modifying the xml source, else risk causing adverse
							//effects on the data used by other components (related and expanded
							//charts for example) that use the same xml for their data
							var updateXML:XML = new XML(colXML);
							if(_seriesValueModifiers[seriesID] != undefined){
								(_seriesValueModifiers[seriesID] as ISeriesDataModifier).modifyDataXML(updateXML);
							}
							if(dc.needsUpdateFrom(updateXML)){
								dc.updateFrom(updateXML);
							}
							(_categorySumsDirectory[dc.colID] as CategorySums).update(previousValue, dc.value);
							_chartBoundsQueue.update(previousValue, dc.value);
						}
						//add a new data column if the data isn't already in the data provider
						else{
							dc = new DataColumn(colXML);
							if(_seriesValueModifiers[seriesID] != undefined){
								(_seriesValueModifiers[seriesID] as ISeriesDataModifier).modifyData(dc);
							}
							if(_categorySumsDirectory[dc.colID] != undefined){
								(_categorySumsDirectory[dc.colID] as CategorySums).addValue(dc.value);
							}
							else{
								_categorySumsDirectory[dc.colID] = new CategorySums(dc.value, dc.value);
							}
							_chartBoundsQueue.add(dc.value);
							_seriesDataPoints[dataID] = dc;
							provider.addItemAt(dc, colIndex);
						}
						colIndex++;
					}
					provider.refresh();
				}
				for each(var sumSet:CategorySums in _categorySumsDirectory){
					if(sumSet.negativeSum < minMax.minCategorySum){ minMax.minCategorySum = sumSet.negativeSum; }
					if(sumSet.positiveSum > minMax.maxCategorySum){ minMax.maxCategorySum = sumSet.positiveSum; }
				}
			}
			if(isNaN(_chartBoundsQueue.maxValue)){  //no data, use default values
				minMax.min = 0;
				minMax.max = 100/1.1; //set bounds will multiply max by 1.1 for upper bound
			}
			else{
				minMax.min = _chartBoundsQueue.minValue;
				minMax.max = _chartBoundsQueue.maxValue;
			}
			return minMax;
		}
		
		/**
		 * Returns the data provider for the category axis.  If the data provider has not yet
		 * been built, this getter will initialize it.
		*/
		public function get categoryAxisData():ArrayCollection{
			if(_categoryAxisProvider == null){
				_categoryAxisProvider = new ArrayCollection();
			}
			return _categoryAxisProvider;
		}
		
		/**
		 * Creates the category axis data provider array collection based off of the provided
		 * visualizationdata xml object.
		 * @param vizDataXML The visualizationdata XML node (XML object) that will be used to
		 * create the data provider
		*/
		public function setCategoryAxisData(vizDataXML:XML):void{
			if(_categoryAxisProvider == null){
				_categoryAxisProvider = new ArrayCollection();
			}
			try{
				_categoryAxisProvider.removeAll();
				if(vizDataXML == null){ return; }
				var datarow:XML = vizDataXML.datarow.(@templatetype=="header")[0];
				if(datarow == null){ return; }
				var dc:DataColumn;
				for each(var colXML:XML in datarow.datacolumn){
					dc = new DataColumn(colXML);
					_categoryAxisProvider.addItem(dc);
					_categoryAxisDataColumns[new String(colXML.@id)] = dc;
				}
			}
			catch(error:Error){
				Logger.log(DefaultLogEvent.WARNING, "ChartDataProvider.setCategoryAxisData - Failed Category Axis Data Provider Creation!");
			}
			
		}
		
		/**
		 * Upon receipt of a datarow with @templatetype == "header", this function examines the new
		 * data category specifications. Data columns are added, removed, and updated accordingly.
		 * Also, all stale data (i.e. data in the _seriesDataPoints index that no longer has a
		 * corresponding data category) is cleared.
		 * 
		 * @param
		 * 		rowXML The datarow XML node whose template type is set to header and will be used
		 * 		to update the current set of category axis data
		*/
		public function updateCategoryAxisData(rowXML:XML):void{
			if(rowXML == null){
				Logger.log(DefaultLogEvent.WARNING, "ChartDataProvider.updateCategoryAxisData - Attempted to update category axis with null data.");
				return;
			}
			var validColumnIDs:Dictionary = updateAxisItems(rowXML);
			clearStaleCategoryAxisItems(validColumnIDs);
			clearStaleSerisData(validColumnIDs);
		}
		
		/**
		 * @return Dictionary of valid column IDs
		*/ 
		private function updateAxisItems(rowXML:XML):Dictionary{
			var colIndex:int = 0;
			var dataColumn:DataColumn;
			var validColumnIDs:Dictionary = new Dictionary();
			for each(var colXML:XML in rowXML.datacolumn){
				var colID:String = new String(colXML.@id);
				validColumnIDs[colID] = true;
				if(_categoryAxisDataColumns[colID]){
					//update the axis value if need be
					dataColumn = _categoryAxisDataColumns[colID] as DataColumn;
					if(dataColumn.needsUpdateFrom(colXML)){
						dataColumn.updateFrom(colXML);
					}
					//if position of the axis value changed, reflect that change in the dataprovider
					var currentDCPosition:int = _categoryAxisProvider.getItemIndex(dataColumn); 
					if(colIndex != currentDCPosition){
						_categoryAxisProvider.removeItemAt(currentDCPosition);
						_categoryAxisProvider.addItemAt(dataColumn, colIndex);
					}
				}
				else{
					dataColumn = new DataColumn(colXML);
					_categoryAxisDataColumns[colID] = dataColumn;
					_categoryAxisProvider.addItemAt(dataColumn, colIndex);
				}
				colIndex++;
			}
			_categoryAxisProvider.refresh();
			return validColumnIDs;
		}
		
		private function clearStaleCategoryAxisItems(validColumnIDs:Dictionary):void{
			for(var i:int = 0; i < _categoryAxisProvider.length; i++){
				var dataColumn:DataColumn = _categoryAxisProvider.getItemAt(i) as DataColumn;
				if(!validColumnIDs[dataColumn.colID]){
					delete _categoryAxisDataColumns[dataColumn.colID];
					_categoryAxisProvider.removeItemAt(i);
					i--;
				}
			}
			_categoryAxisProvider.refresh();
		}
		
		private function clearStaleSerisData(validColumnIDs:Dictionary):void{
			for(var seriesID:String in _seriesDataProviders){
				var provider:ArrayCollection = _seriesDataProviders[seriesID] as ArrayCollection;
				for(var i:int = 0; i < provider.length; i++){
					var dataColumn:DataColumn = provider.getItemAt(i) as DataColumn;
					var dataID:String = seriesID + "##" + dataColumn.colID;
					if(!validColumnIDs[dataColumn.colID]){
						delete _seriesDataPoints[dataID];
						delete _categorySumsDirectory[dataColumn.colID];
						_chartBoundsQueue.remove(dataColumn.value);
						provider.removeItemAt(i);
						i--;
					}
				}
			}
		}
		
		public function addSeriesValueModifier(seriesID:String, seriesDataModifier:ISeriesDataModifier):void{
			if(seriesID == null || seriesID == "" || seriesDataModifier == null){ return; }
			_seriesValueModifiers[seriesID] = seriesDataModifier;
		}
		
//		public function get legendData():ArrayCollection{
//			if(_legendProvider == null){
//				_legendProvider= new ArrayCollection();
//			}
//			return _legendProvider;
//		}
//		
//		public function setLegendData():void{
//			
//		}
//		
//		public function updateLegendData():void{
//			
//		}
		
		public function purge():void{
			_categoryAxisProvider.removeAll();
			_categoryAxisDataColumns = new Dictionary();
			_chartBoundsQueue.clear();
			for(var key:String in _seriesDataProviders){
				if(_seriesDataProviders[key] is ArrayCollection){
					(_seriesDataProviders[key] as ArrayCollection).removeAll();
				}
			}
			_seriesDataPoints = new Dictionary();
			_categorySumsDirectory = new Dictionary();
		}
		
	}
	
}

internal class CategorySums{

	public var positiveSum:Number;
	public var negativeSum:Number;
	
	public function CategorySums(positiveSum:Number=0, negativeSum:Number=0){
		this.positiveSum = positiveSum > 0 ? positiveSum:0;
		this.negativeSum = negativeSum < 0 ? negativeSum:0;
	}
	
	public function addValue(value:Number):void{
		if(value < 0){ negativeSum += value; }
		else{ positiveSum += value; }
	}
	
	public function subtractValue(value:Number):void{
		if(value < 0){ negativeSum -= value; }
		else{ positiveSum -= value; }
	}
	
	public function update(prevValue:Number, value:Number):void{
		//update needs to add the current and remove the previous value to/from the appropriate sum.
		if(prevValue == value){ return; }
		if(value > 0){
			if(prevValue < 0){ //prevValue contributed to - sum, value contributes to + sum
				negativeSum -= prevValue;
				positiveSum += value;
			}
			else{ //prevValue and value both contribute to + sum
				positiveSum += (value - prevValue);
			}
		}
		else{
			if(prevValue > 0){ //prevValue contributed to + sum, value contributes to - sum
				negativeSum += value;
				positiveSum -= prevValue;
			}
			else{ //prevValue and value both contribute to - sum
				negativeSum += (value - prevValue);
			}
		}
	}
	
}