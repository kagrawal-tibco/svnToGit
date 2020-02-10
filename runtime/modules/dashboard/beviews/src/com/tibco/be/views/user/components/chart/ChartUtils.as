package com.tibco.be.views.user.components.chart{
	import com.tibco.be.views.user.components.chart.series.BEVBarSeries;
	import com.tibco.be.views.user.components.chart.series.BEVColumnSeries;
	import com.tibco.be.views.user.components.chart.series.BEVSeriesConfig;
	
	import mx.charts.series.BarSet;
	import mx.charts.series.ColumnSet;
	import mx.collections.ArrayCollection;
	import mx.graphics.GradientEntry;
	import mx.graphics.IFill;
	import mx.graphics.LinearGradient;
	import mx.graphics.SolidColor;
	
	
	public class ChartUtils{
		
		/**
		 * Function takes the configuration and the data XML and generates a series-segregated array collection
		 * which is further used by the chart classes to paint the data
		 * @public
		 * @param config XML
		 * @param visualization data XML
		 * @returns Array collection
		 */
		public static function getProcessedChartData(cXML:XML, vDataXML:XML):ArrayCollection{
			var cData:ArrayCollection = new ArrayCollection();
			var i:Number;
			var j:Number;
			
			//run through each charttype configuration
			for(i=0;i<cXML.chartconfig.charttypeconfig.length();i++){
				var cTypeXML:XML = cXML.chartconfig.charttypeconfig[i];
				var cType:String = new String(cTypeXML.@type);
				//run through each series inside that
				for(j=0;j<cTypeXML.seriesconfig.length();j++){
					//now for each series, get the respective data from the visualization data
					var seriesCfg:XML = new XML(cTypeXML.seriesconfig[j]);
					var seriesID:String = new String(seriesCfg.@name);
					var seriesName:String = new String(seriesCfg.@displayName);
//					var seriesData:ArrayCollection = ChartUtils.getSeriesObject(vDataXML, seriesID);
					var baseColor:Number = parseInt(seriesCfg.@basecolor, 16);
					var highlightColor:Number = parseInt(seriesCfg.@highlightcolor, 16);
					
					//post all these series variables into an array
					var tObj:Object = new Object();
					tObj.chartType = cType;
					tObj.seriesID = seriesID;
					tObj.seriesName = seriesName;
//					tObj.seriesData = seriesData;
					tObj.baseColor = baseColor;
					tObj.highlightColor = highlightColor;					
					
					//add this item to the chartData array collection
					cData.addItem(tObj);					
				}
			}			
			
			return cData;
		}
		
		public static function getProcessedSeriesConfigs(compConfigXML:XML):ArrayCollection{
			var chartSeriesConfigs:ArrayCollection = new ArrayCollection();
			
			//run through each charttype configuration
			for(var i:int = 0; i < compConfigXML.chartconfig.charttypeconfig.length(); i++){
				var chartTypeConfig:XML = compConfigXML.chartconfig.charttypeconfig[i];
				var chartType:String = chartTypeConfig.@type;
				//run through each series config inside the chart type config
				for(var j:int = 0; j < chartTypeConfig.seriesconfig.length(); j++){
					//now for each series, get the respective config values from the series configuration node
					chartSeriesConfigs.addItem(new BEVSeriesConfig(chartType, chartTypeConfig.seriesconfig[j]));					
				}
			}			
			
			return chartSeriesConfigs;
		}
		
		public static function getDataColumnColors(vDataXML:XML):Array{
			if(vDataXML.datarow == undefined || vDataXML.datarow.length() == 0){ return []; }
			var colors:Array = [];
			var dataCols:XMLList = vDataXML.datarow[0].datacolumn;
			for(var i:Number = 0; i < dataCols.length(); i++){
				colors.push(parseInt(dataCols[i].basecolor, 16));
			}
			return colors;
		}
		
		/**
		 * this function takes a visualization data XML, series ID and generates an array collection 
		 * object with datacolumn objects
		 */
		 public static function getSeriesObject(vDataXML:XML, sID:String):ArrayCollection{
		 	var i:Number;
		 	var seriesXMLList:XMLList = vDataXML.datarow.(@templateid == sID);
		 	var aColl:ArrayCollection = new ArrayCollection();
		 	//run through each datacolumn
		 	for(i=0;i<seriesXMLList.datacolumn.length();i++){
		 		//var dCol:DataColumn = new DataColumn(XML(seriesXMLList.datacolumn[i].toString()));
		 		aColl.addItem(new DataColumn(XML(seriesXMLList.datacolumn[i].toString())));
		 	}
		 	return aColl;
		 } 
		 
		 /**
		 * Function creates a fill as per the settings of the series sent as param
		 * @public
		 * @param series Object
		 * @returns Fill
		 */
		 public static function getHorizontalGradientFill(seriesObj:Object, alpha:Number=1):IFill{
		 	var fillFormat:LinearGradient = new LinearGradient();
		 	var e1:GradientEntry =  new GradientEntry(seriesObj.baseColor, 0, alpha);
		 	var e2:GradientEntry =  new GradientEntry(seriesObj.highlightColor, 0.33, alpha);
		 	var e3:GradientEntry =  new GradientEntry(seriesObj.baseColor, 1, alpha);
		 	fillFormat.entries = [e1, e2, e3];
			return fillFormat;
		 }
		 
		 public static function getVerticalGradientFill(seriesObj:Object, alpha:Number=1):IFill{
		 	var fillFormat:IFill = getHorizontalGradientFill(seriesObj, alpha);
		 	(fillFormat as LinearGradient).angle = 90;
			return fillFormat;
		 }
		 
		 public static function getSolidFill(seriesObj:Object, alpha:Number=1):IFill{
		 	return new SolidColor(seriesObj.baseColor, alpha);
		 }
		 
		 public static function configureBarOverlap(seriesSet:Array, nClusters:Number, plotAreaWidth:Number, clusterWidthRatio:Number, overlap:Number, maxBarSize:Number, isVerticalBar:Boolean=true):void{
		 	/** The cluster width ratio to use when calculating columnWidthRatio and offset */
			var currentClusterWidthRatio:Number = clusterWidthRatio;
		 	/** the calculated width in pixels of all series columns with the specified overlap for one category */
			var clusterWidth:Number;
			/** the width ratio for each column shown in a column cluster (i.e. _clusterWidthRatio * 1/fullColumnEquivilent) */
			var columnWidthRatio:Number; 
			/** the column width in pixels after calculating cluster width */
			var columnPixWidth:Number;
			/** the percentage of a column that is not hidden due to overlap */
			var unOverlapped:Number;
			/** number of series */
			var nSeries:Number = seriesSet.length;
			/**
			 * scalar value indicating how many fullsize (unoverlapped) columns correspond to the
			 * amount of space taken by nSeries overlapped columns. For example, 5 series with 60
			 * percent overlap fit into the space of 2.6 series with no overlap. In this case, 2.6
			 * is the fullColumnEquivilent.
			*/
			var fullColumnEquiv:Number;
			
			if(nSeries == 0 || nClusters == 0){ return; }
			
			unOverlapped = 1-overlap;
			fullColumnEquiv = nSeries*unOverlapped + overlap;
			clusterWidth = maxBarSize*fullColumnEquiv;
			
			if(clusterWidth < plotAreaWidth/nClusters){
				//We have to adjust things so that the bars are not shown wider than max width.
				//Rather than trying to adjust the width of all the bars, we can get the same
				//effect by altering the cluster width ratio so that the n columns at max width
				//will fit %100 of clusterWidthRatio
				currentClusterWidthRatio = Math.min(clusterWidthRatio, clusterWidth/(plotAreaWidth/nClusters));
			}
			else{
				//column widths will be smaller than max (scaled by flex) so we set column width
				//ratios so that the columns fill the cluster space
				currentClusterWidthRatio = clusterWidthRatio;
			}
			columnWidthRatio = currentClusterWidthRatio/fullColumnEquiv;
			columnPixWidth = columnWidthRatio * (plotAreaWidth/nClusters);
			
			for(var i:int = 0; i < nSeries; i++){
				if(isVerticalBar){
					if(seriesSet[i] is ColumnSet){
						var colSet:ColumnSet = seriesSet[i] as ColumnSet;
						colSet.maxColumnWidth = clusterWidth;
						colSet.columnWidthRatio = currentClusterWidthRatio;
					}
					else{
						var columnSeries:BEVColumnSeries = seriesSet[i] as BEVColumnSeries;
						columnSeries.columnWidthRatio = columnWidthRatio;
						columnSeries.offset = -currentClusterWidthRatio/2 + i*unOverlapped*columnWidthRatio + columnWidthRatio/2;
						columnSeries.maxColumnWidth = maxBarSize;
						columnSeries.calculatedColWidth = columnPixWidth;
					}
				}
				else{
					if(seriesSet[i] is BarSet){
						var barSet:BarSet = seriesSet[i] as BarSet;
						barSet.maxBarWidth = clusterWidth;
						barSet.barWidthRatio = currentClusterWidthRatio;
					}
					else{
						var barSeries:BEVBarSeries = seriesSet[(nSeries-1-i)] as BEVBarSeries; //go backwards so series order matches vertical legend
						barSeries.barWidthRatio = columnWidthRatio;
						barSeries.offset = -(currentClusterWidthRatio/2) + i*unOverlapped*columnWidthRatio + columnWidthRatio/2;
						barSeries.maxBarWidth = maxBarSize;
						barSeries.calculatedBarWidth = columnPixWidth;
					}
				}
			}
			
		 }
		 	
	}
}
