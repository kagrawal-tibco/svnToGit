package com.tibco.be.views.user.components.chart.series{
	
	import com.tibco.be.views.user.components.chart.BEVChart;
	import com.tibco.be.views.user.components.chart.BEVChartDataLabel;
	import com.tibco.be.views.user.components.chart.DataColumn;
	import com.tibco.be.views.user.components.chart.renderers.item.LabeledItemRendererFactory;
	import com.tibco.be.views.user.components.chart.renderers.item.LabeledItemUtil;
	
	import mx.charts.ChartItem;
	import mx.charts.chartClasses.CartesianChart;
	import mx.charts.series.LineSeries;
	import mx.collections.ArrayCollection;
	import mx.graphics.SolidColor;
	import mx.graphics.Stroke;
	import mx.skins.ProgrammaticSkin;

	public class BEVLineSeries extends LineSeries implements IBEVLabeledSeries{
		
		public static const NO_DATAPOINTS:String = "none";
		public static const EDGE_DATAPOINTS:String = "edges";
		public static const ALL_DATAPOINTS:String = "all";
		
		private var _labelMaxX:Number;
		private var _dataPointsToShow:String;
		private var _seriesConfig:XML;
		private var _actionConfig:XML;
		private var _itemRadius:Number;
		
		public function BEVLineSeries(seriesConfigObj:BEVSeriesConfig, lineThickness:Number, dataPoints:String, plotShape:String, plotShapeDimension:Number){
			super();
			_dataPointsToShow = dataPoints;
			_seriesConfig = seriesConfigObj.configXML;
			_actionConfig = BEVSeriesUtil.getActionConfig(_seriesConfig);
			_labelMaxX = 0;
			_itemRadius = plotShapeDimension;
			name = seriesConfigObj.seriesID;
			displayName = seriesConfigObj.seriesName;
			yField = BEVChart.VALUE_FIELD;
			xField = BEVChart.CATEGORY_FIELD;
			useHandCursor = true;
			buttonMode = true;
			setStyle("lineStroke", new Stroke(seriesConfigObj.baseColor, lineThickness, 1));
			setStyle("stroke", new Stroke(seriesConfigObj.baseColor, 1));
			setStyle("fill", new SolidColor(seriesConfigObj.baseColor));
			setStyle("radius", plotShapeDimension);
			setStyle("itemRenderer", new LabeledItemRendererFactory(seriesConfigObj.labelConfig, plotShape));
		}
		
		public function get seriesConfig():XML{ return _seriesConfig; }
		public function get actionConfig():XML{ return _actionConfig; }
				
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			//recalculate the plot area width
			var cChart:CartesianChart = chart as CartesianChart;
			if(cChart != null){
				//_labelMaxX = cChart.width - cChart.computedGutters.left - cChart.computedGutters.right - 1;
				_labelMaxX = cChart.width - cChart.computedGutters.right - 1;
			}
			super.updateDisplayList(unscaledWidth, unscaledHeight);
		}
		
		//IBEViewsLabeledSeries
		public function createLabel(labelConfig:XML):BEVChartDataLabel{
			return LabeledItemUtil.createLabel(labelConfig); 
		}
		
		public function updateLabel(label:BEVChartDataLabel, skin:ProgrammaticSkin, data:Object):void{
			var cChart:CartesianChart = chart as CartesianChart;
			var leftPad:Number = cChart == null ? 0:cChart.computedGutters.left+1;
			var topPad:Number = cChart == null ? 0:cChart.computedGutters.top;
			LabeledItemUtil.updateLabel(label, data as ChartItem, _itemRadius, skin.x + leftPad, skin.y + topPad, leftPad, topPad, _labelMaxX);
			if(!LabeledItemUtil.detectAndFixLabelOverlap(chart, label)){
				//overlap existis and cannot be resolved via moving the label => remove the label
				if(chart.contains(label)){ chart.removeChild(label); }
			}
			else{
				chart.addChild(label);
			}
		}
		
		public function rendererShouldDrawPoint(data:Object):Boolean{
			if(data.xNumber == undefined) return false;
			//check for chart alert
			if(data is ChartItem && data.item is DataColumn && data.item.fillColors != null){
				return true;
			}
			if(_dataPointsToShow == EDGE_DATAPOINTS){
				return (data.xNumber == 0 || data.xNumber == items.length-1);
			}
			return (_dataPointsToShow == ALL_DATAPOINTS || items.length == 1);
		}
		//END IBEViewsLabeledSeries
		
	}
}