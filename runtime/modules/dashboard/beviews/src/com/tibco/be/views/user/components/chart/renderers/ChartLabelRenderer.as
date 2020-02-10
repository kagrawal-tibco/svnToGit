package com.tibco.be.views.user.components.chart.renderers{
	
	import com.tibco.be.views.user.components.chart.BEVCartesianChart;
	
	import mx.core.ClassFactory;

	public class ChartLabelRenderer extends ClassFactory{
		
		public static const CATEGORY_TYPE:int = 0;
		public static const VALUE_TYPE:int = 1;
		private static const TITLE_LABEL:int = 0;
		private static const TICK_LABEL:int = 1;
		
		private var _parentChart:BEVCartesianChart;
		private var _axisType:int;
		private var _labelType:int;
		private var _fontSize:int;
		private var _fontColor:uint;
		private var _fontStyles:String;
		private var _placement:String;
		private var _rotation:Number;
		private var _skipFactor:Number;
		private var _scale:String;
		
		public function ChartLabelRenderer(parentChart:BEVCartesianChart, axisType:int, rendererConfig:XML){
			var rendererClass:Class;
			_parentChart = parentChart;
			_axisType = axisType;
			
			//renderer config parsed here so it's not done at every newInstance call
			_fontSize = rendererConfig.@fontsize;
			_fontColor = parseInt(rendererConfig.@fontcolor, 16);
			_fontStyles = new String(rendererConfig.@fontstyle);
			_placement = new String(rendererConfig.@placement);
			_rotation = new Number(rendererConfig.@rotation);
			_skipFactor = new Number(rendererConfig.@skipfactor);
			_scale = new String(rendererConfig.@scale);
			
			if(rendererConfig.name() == "ticklabelconfig"){
				_labelType = TICK_LABEL;	
			}
			else{
				_labelType = TITLE_LABEL;
			}
			
		}
		
		override public function newInstance():*{
			var numChartLabels:Number = 1;
			try{
				numChartLabels = _parentChart.dataProvider.categoryAxisData.length;
			}
			catch(error:Error){
				//ignore, just use 1
			}
			switch(_axisType){
				case(CATEGORY_TYPE):
					if(_labelType == TITLE_LABEL){
						var catTitleLabel:ChartCategoryTitleLabel = new ChartCategoryTitleLabel();
						catTitleLabel.configure(_parentChart, numChartLabels, _fontSize, _fontColor, _fontStyles, _placement, _rotation, _skipFactor);
						return catTitleLabel;
					}
					var catTickLabel:ChartCategoryTickLabel = new ChartCategoryTickLabel();
					catTickLabel.configure(_parentChart, numChartLabels, _fontSize, _fontColor, _fontStyles, _placement, _rotation, _skipFactor);
					return catTickLabel;
				case(VALUE_TYPE):
					if(_labelType == TITLE_LABEL){
						var valTitleLabel:ChartValueTitleLabel = new ChartValueTitleLabel();
						valTitleLabel.configure(_parentChart, numChartLabels, _fontSize, _fontColor, _fontStyles, _placement, _rotation, _skipFactor, _scale);
						return valTitleLabel;
					}
					var valRenderer:ChartValueTickLabel = new ChartValueTickLabel();
					valRenderer.configure(_parentChart, numChartLabels, _fontSize, _fontColor, _fontStyles, _placement, _rotation, _skipFactor, _scale);
					return valRenderer;
			}
			return null;
		}
		
	}
}