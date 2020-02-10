package com.tibco.be.views.user.components.chart.series{
	
	import com.tibco.be.views.user.components.chart.BEVChart;
	import com.tibco.be.views.user.components.chart.BEVChartDataLabel;
	import com.tibco.be.views.user.components.chart.ChartUtils;
	import com.tibco.be.views.user.components.chart.DataColumn;
	import com.tibco.be.views.user.components.chart.renderers.item.LabeledItemRendererFactory;
	import com.tibco.be.views.user.components.chart.renderers.item.LabeledItemUtil;
	
	import flash.text.TextLineMetrics;
	
	import mx.charts.ChartItem;
	import mx.charts.chartClasses.CartesianChart;
	import mx.charts.series.BarSeries;
	import mx.graphics.IFill;
	import mx.skins.ProgrammaticSkin;

	public class BEVBarSeries extends BarSeries implements IBEVLabeledSeries{
		
		private static const DEFAULT_BAR_MAX_WIDTH:int = 5;
		private static const LEFT_BAR_LABEL_PAD:int = 5;
		private static const RIGHT_BAR_LABEL_PAD:int = -2;
		private static const BAR_LABEL_VERTICAL_PAD:int = -1;
		
		private var _seriesConfigXML:XML;
		private var _actionConfigXML:XML;
		private var _seriesConfig:BEVSeriesConfig;
		private var _calculatedBarWidth:int = -1;
		private var _barAlpha:Number;
		private var _labelMaxX:int;
		
		public function BEVBarSeries(seriesConfigObj:BEVSeriesConfig, maximumBarWidth:Number, overlapPercentage:Number, barAlpha:Number=1){
			super();
			name = seriesConfigObj.seriesID;
			displayName = seriesConfigObj.seriesName;
			_seriesConfigXML = seriesConfigObj.configXML;
			_actionConfigXML = BEVSeriesUtil.getActionConfig(_seriesConfigXML);
			_seriesConfig = seriesConfigObj;
			_barAlpha = barAlpha;
			_labelMaxX = 0;
			maxBarWidth =  maximumBarWidth > 0 ? maximumBarWidth:DEFAULT_BAR_MAX_WIDTH;
			_calculatedBarWidth = maximumBarWidth;
			xField = BEVChart.VALUE_FIELD;
			yField = BEVChart.CATEGORY_FIELD;
			fillFunction = colorBar;
			useHandCursor = true;
			buttonMode = true;
			setStyle("itemRenderer", new LabeledItemRendererFactory(seriesConfigObj.labelConfig, LabeledItemRendererFactory.BOX));
			//setStyle("fill", ChartUtils.getVerticalGradientFill(seriesData));
		}
		
		public function get seriesConfig():XML{ return _seriesConfigXML; }
		public function get actionConfig():XML{ return _actionConfigXML; }
		public function get calculatedBarWidth():int{ return _calculatedBarWidth; }
		
		public function set calculatedBarWidth(value:int):void{ _calculatedBarWidth = value; }
		override public function set maxBarWidth(value:Number):void{
			super.maxBarWidth = value > 0 ? value:DEFAULT_BAR_MAX_WIDTH;
		}
				
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			//recalculate the maximum x coordinate for labels
			var cChart:CartesianChart = chart as CartesianChart;
			if(cChart != null){
				_labelMaxX = cChart.width - cChart.computedGutters.right - 1;
			}
			super.updateDisplayList(unscaledWidth, unscaledHeight);
		}
		
		private function colorBar(element:ChartItem, index:Number):IFill{
			var dataCol:DataColumn = null;
			try{
				var series:BarSeries = element.element as BarSeries;
				dataCol = series.dataProvider[index] as DataColumn;
			}
			catch(error:Error){
				/* ignore */
			}
			if(dataCol != null && dataCol.fillColors != null){
				return ChartUtils.getSolidFill({
					"baseColor":dataCol.fillColors.baseColor,
					"highlightColor":dataCol.fillColors.highlightColor	
				});
			}
			return ChartUtils.getSolidFill(_seriesConfig);
		}
		
		//IBEViewsLabeledSeries
		public function createLabel(labelConfig:XML):BEVChartDataLabel{
			return LabeledItemUtil.createLabel(labelConfig);
		}
		
		public function updateLabel(label:BEVChartDataLabel, skin:ProgrammaticSkin, data:Object):void{
			var cChart:CartesianChart = chart as CartesianChart;
			var leftPad:Number = 0;
			var topPad:Number = 0;
			if(cChart != null){
				leftPad = cChart.getStyle("paddingLeft") + cChart.computedGutters.left;
				topPad = cChart.getStyle("paddingTop") + cChart.computedGutters.top;
			}
			
			var txtWidth:Number = label.textWidth;
			var txtHeight:Number = label.textHeight;
			if(isNaN(txtWidth)){
				var metrics:TextLineMetrics = measureText(label.text); 
				txtWidth = metrics.width;
				txtHeight = metrics.height;
			}
			txtHeight -= 5;
			
			var labelX:Number = leftPad + skin.x + skin.width;  //edge of bar (right for +direction, left for -direction)
			if(skin.width > 0){ //bar goes in positive direction (right)
				labelX -= (txtWidth/2+RIGHT_BAR_LABEL_PAD);
			}
			else{ //bar goes in negative direction (left)
				labelX += (txtWidth/2+LEFT_BAR_LABEL_PAD);
			}
			
			var labelY:Number = topPad + skin.y + skin.height/2 + txtHeight/2 + BAR_LABEL_VERTICAL_PAD; //(divide by 4 = 2 for middle of bar, 2 for flex bar width being 2x pix)
			
			LabeledItemUtil.updateLabel(label, data as ChartItem, 0, labelX, labelY, leftPad, topPad, _labelMaxX);
			if(!LabeledItemUtil.detectAndFixLabelOverlap(chart, label, false) || Math.abs(skin.width)-txtWidth < 5 || skin.height < txtHeight){
				//overlap existis and cannot be resolved via moving the label => remove the label
				if(chart.contains(label)){ chart.removeChild(label); }
			}
			else{
				chart.addChild(label);
			}		
		}
		
		public function rendererShouldDrawPoint(data:Object):Boolean{
			return true; //point in this case is the bar
		}
		//END IBEViewsLabeledSeries
	}

}