package com.tibco.be.views.user.components.chart{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.chart.axis.BEVCategoryAxis;
	import com.tibco.be.views.user.components.chart.renderers.BEVAxisRenderer;
	import com.tibco.be.views.user.components.chart.renderers.ChartLabelRenderer;
	import com.tibco.be.views.user.utils.ChartMinMax;
	import com.tibco.be.views.utils.BEVUtils;
	import com.tibco.be.views.utils.Logger;
	
	import flash.geom.Rectangle;
	
	import mx.charts.AxisRenderer;
	import mx.charts.CategoryAxis;
	import mx.charts.GridLines;
	import mx.charts.LinearAxis;
	import mx.charts.LogAxis;
	import mx.charts.chartClasses.AxisBase;
	import mx.charts.chartClasses.CartesianChart;
	import mx.charts.chartClasses.IAxis;
	import mx.events.FlexEvent;
	import mx.graphics.GradientEntry;
	import mx.graphics.IFill;
	import mx.graphics.LinearGradient;
	import mx.graphics.SolidColor;
	import mx.graphics.Stroke;
	
	public class BEVCartesianChart extends BEVChart{
		
		protected var _hAxis:IAxis;
		protected var _vAxis:IAxis;
		protected var _hAxisRenderer:AxisRenderer;
		protected var _vAxisRenderer:AxisRenderer;
		protected var _gridLines:GridLines;
		protected var _gridLineColor:uint;
		
		/** Used to calculate the gridline interval */
		protected var _numDivisions:Number;
		
		public function BEVCartesianChart(chart:CartesianChart, componentConfig:XML){
			super(chart, componentConfig);
			_numDivisions = -1;
			addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
		}
		
		public function get plotArea():Rectangle{
			var left:Number = myChart.computedGutters.left == 0 ? myChart.getStyle("gutterLeft"):myChart.computedGutters.left;
			return new Rectangle(
				left,
				myChart.computedGutters.top,
				myChart.width - myChart.computedGutters.left - myChart.computedGutters.right,
				myChart.height - myChart.computedGutters.top - myChart.computedGutters.bottom
			);
		}
		
		private function get myChart():CartesianChart{ return _chart as CartesianChart; }
		
		public function get verticalLabelSpace():Number{
			return myChart.computedGutters.left;
		}
		
		override public function initChartConfig(chartConfig:XML):void{
			configurePlotArea(chartConfig);
			prepAndConfigureAxes(chartConfig);
			setTypeSpecificProperties(chartConfig.charttypeconfig.typespecificattribute);
		}
		
		protected function handleCreationComplete(event:FlexEvent):void{ /*virtual*/ }
		
		override public function setChartBoundsAndInterval(minMax:ChartMinMax, forceZeroOrigin:Boolean=false):void{
			var axis:LinearAxis;
			var minValue:Number;
			var maxValue:Number;
			
			if(_vAxis is LinearAxis){
				axis = _vAxis as LinearAxis;
			}
			else if(_hAxis is LinearAxis){
				axis = _hAxis as LinearAxis;
			}
			else{
				return; //don't adjust logarithmic or category axes here
			}
			if(_numDivisions < 1){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".setChartBoundsAndInterval - Invalid _numDivisions, defaulting to 1.");
				_numDivisions = 1;
			}
			if(minMax == null || minMax.min > minMax.max){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".setChartBoundsAndInterval - Invalid minMax, defaulting to [0, 1].");
				minMax = new ChartMinMax();
				minMax.min = 0;
				minMax.max = 1;
			}
			
			maxValue = (forceZeroOrigin && minMax.max < 0) ? 0:minMax.max;
			minValue = (forceZeroOrigin && minMax.min > 0) ? 0:minMax.min;
			
			//could probably condense this, but it helps clarity/tweaking to keep track of each component in the calculation
			//see testing/ChartBoundsAndTickTest.mxml for easier testing of calculations
			var range:Number = maxValue - minValue;
			var roughTick:Number = range/_numDivisions;
			var exponent:Number = Math.log(roughTick)*Math.LOG10E;
			var magnitude:Number = Math.pow(10, Math.floor(exponent));
			var residual:Number = roughTick/magnitude;
			var scale:Number = BEVUtils.roundToFactor(residual, 0.5);
			var tick:Number = scale * magnitude;
			
			var chartMin:Number = BEVUtils.floorToFactor(minValue, tick);
			var chartMax:Number = BEVUtils.ceilToFactor(maxValue, tick);
			
			axis.minimum = chartMin;
			axis.maximum = chartMax;
			axis.interval = tick;
		}
		
		/**
		 * Sets up which axis (horizontal or vertical) corresponds to the value or category.
		 * The default (non-overridden) version treats the value axis as vertical and the
		 * horizontal axis as category.
		*/
		protected function prepAndConfigureAxes(chartConfig:XML):void{
			var hAxisCfg:XML = new XML(chartConfig.categoryaxisconfig);
			var vAxisCfg:XML = new XML(chartConfig.valueaxisconfig);
			configureAxes(hAxisCfg, vAxisCfg);
		}
		
		/**
		 * Configures the horizontal and vertical axes as specified in the provided configuration
		 * XMLs. Axis Renderers are created and appropriate styles and properties are set. Note this
		 * function enables category and value axes to be used horizontally or vertically.
		 * 
		 * @param hAxisCfg The XML configuration node to use to configure the horizontal axis.
		 * @param vAxisCfg The XML configuration node to use to configure the vertical axis.
		*/ 
		protected function configureAxes(hAxisCfg:XML, vAxisCfg:XML):void{
			//Error check
			if(myChart == null){
				error(DefaultLogEvent.CRITICAL, "Null chart object", "configureAxes");
			}
			if(hAxisCfg == null){
				error(DefaultLogEvent.WARNING, "Invalid horizontal axis configuration XML!", "configureAxes");
				return;
			}
			if(vAxisCfg == null){
				error(DefaultLogEvent.WARNING, "Invalid vertical axis configuration XML!", "configureAxes");
				return;
			}
			
			myChart.horizontalAxisRenderers = [];
			myChart.verticalAxisRenderers = [];
			
			//Setup the chart's horizontal and vertical axis objects
			_hAxis = buildAxis(hAxisCfg);
			_vAxis = buildAxis(vAxisCfg);
			myChart.horizontalAxis = _hAxis;
			myChart.verticalAxis = _vAxis;
			
			_hAxisRenderer = buildAxisRenderer(_hAxis, hAxisCfg);
			_vAxisRenderer = buildAxisRenderer(_vAxis, vAxisCfg);
			_vAxisRenderer.setStyle("verticalAxisTitleAlignment", "vertical");
			
			myChart.horizontalAxisRenderers = [_hAxisRenderer];
			myChart.verticalAxisRenderers = [_vAxisRenderer];
		}
		
		protected function configurePlotArea(chartConfig:XML):void{
			var plotAreaConfig:XML;
			var plotPatternConfig:XML;
			var lines:String = "none";
			
			_gridLines = new GridLines();
			try{
				plotAreaConfig = XML(chartConfig.plotareaconfig);
				if(plotAreaConfig == null){ return; }
				
				//Plot pattern defines grid line directions and grid line colors
				plotPatternConfig = XML(plotAreaConfig.plotpatternconfig);
				if(plotPatternConfig != null){
					_gridLineColor = parseInt(plotPatternConfig.@color, 16);
					lines = new String(plotPatternConfig.@lines);
					if(lines == "none"){
						//still want bg color even if no gridlines will be shown
						_gridLines.setStyle("direction", "both"); 
						var invisibleStroke:Stroke = new Stroke(0xFFFFFF, 1, 0.0);
						_gridLines.setStyle("horizontalStroke", invisibleStroke);
						_gridLines.setStyle("verticalStroke", invisibleStroke);
					}
					else{
						_gridLines.setStyle("direction", lines); 
						_gridLines.setStyle("horizontalStroke", new Stroke(_gridLineColor, 1, 0.8));
						_gridLines.setStyle("verticalStroke", new Stroke(_gridLineColor, 1, 0.8));
					}
				}
				
				//Fill properties
				var bgColor:Number = parseInt(plotAreaConfig.@backgroundcolor, 16);
				var gEndColor:Number = parseInt(plotAreaConfig.@gradientendcolor, 16);
//				var gDirection:String = new String(plotAreaConfig.@gradientdirection);
				var fill:IFill;
				
				//Choose and configure the Fill
				if(isNaN(gEndColor)){ //no gradient, use solid color
					fill = new SolidColor(bgColor, .5);
				}
				else{
					fill = new LinearGradient();
					var gFill:LinearGradient = fill as LinearGradient;
					var e1:GradientEntry = new GradientEntry(bgColor, 0, 0.5);
					var e2:GradientEntry = new GradientEntry(gEndColor, 1, 0.5);
					gFill.entries = [e1, e2];
//					switch(gDirection){
//						case("toptobottom"): gFill.angle = 90; break;
//						case("righttoleft"): gFill.angle = 180; break;
//						case("bottomtotop"): gFill.angle = 270; break;
//						default: //lefttoright - default 0 val
//					}
				}
				
				//Use the Fill according to what grid lines are being displayed
				if(lines == "vertical"){
					if(gFill != null){
						gFill.angle = 90;
					}
					_gridLines.setStyle("verticalShowOrigin", false);
					_gridLines.setStyle("verticalFill", fill);
					_gridLines.setStyle("verticalAlternateFill", fill);
				}
				else{
					_gridLines.setStyle("horizontalFill", fill);
			 		_gridLines.setStyle("horizontalAlternateFill", fill);
				}
				
				_chart.backgroundElements = [_gridLines];
			}
			catch(error:Error){
				Logger.log(DefaultLogEvent.CRITICAL, "BEViewsCartesianChart.configurePlotArea - " + error.message);
				Logger.log(DefaultLogEvent.DEBUG, error.getStackTrace());
			}
			
		}
		
		private function buildAxis(config:XML):IAxis{
			if(config.name() == VALUE_AXIS_CONFIG_NAME){
				return (config.@scale == LOG_SCALE ? new LogAxis() : new LinearAxis());
			}
			var cAxis:BEVCategoryAxis = new BEVCategoryAxis();
			cAxis.dataProvider = _dataProvider.categoryAxisData;
			cAxis.categoryField = CATEGORY_FIELD;
			cAxis.labelFunction = axisLabelFunction;
			cAxis.ticksBetweenLabels = this is BEVVerticalBarChart || this is BEVHorizontalBarChart;
			return cAxis;
		}
		
		private function buildAxisRenderer(axis:IAxis, config:XML):AxisRenderer{
			var renderer:AxisRenderer;
			if(config != null){
				(axis as AxisBase).title = config.@name + getScaleString(config.@scale);
				switch(String(config.name())){
					case(VALUE_AXIS_CONFIG_NAME):
						renderer = new BEVAxisRenderer();
						renderer.axis = axis;
						_numDivisions = configValueAxisRenderer(renderer, config);
						break;
					case(CATEGORY_AXIS_CONFIG_NAME):
						renderer = new BEVAxisRenderer();
						renderer.axis = axis;
						configCategoryAxisRenderer(renderer, config);
						break;
				}
			}
			return renderer;
		}
		
		/**
		 * Sets properties for the Value AxisRenderer.
		 * @param chart A reference to the parent BEViewsCartesianChart
		 * @param axisRenderer The AxisRenderer that needs to be configured
		 * @param config the valueaxisconfig xml from the chartconfig
		*/
		protected function configValueAxisRenderer(axisRenderer:AxisRenderer, config:XML):Number{
			setCommonAxisRendererProperties(axisRenderer, config);
			axisRenderer.titleRenderer = new ChartLabelRenderer(this, ChartLabelRenderer.VALUE_TYPE, config);
			var scale:String = new String(config.@scale);
			//Insert scale into the ticklabel config
			if(config.ticklabelconfig && scale){ config.ticklabelconfig.@scale = scale; }
			if( XML(config.ticklabelconfig) != null ){
				axisRenderer.labelRenderer =
					new ChartLabelRenderer(this, ChartLabelRenderer.VALUE_TYPE, config.ticklabelconfig[0]);
			}
			var numDivisions:Number = config.@noofvisualdivisions;
			if(!isNaN(numDivisions)){
				axisRenderer.setStyle("canDropLabels", false);
				return numDivisions;
			}
			return -1;
		}
		
		/**
		 * Sets properties for the Category AxisRenderer.
		 * @param chart A reference to the parent BEViewsCartesianChart
		 * @param axisRenderer The AxisRenderer that needs to be configured
		 * @param config the categoryaxisconfig xml from the chartconfig
		*/
		protected function configCategoryAxisRenderer(axisRenderer:AxisRenderer, config:XML):void{
			setCommonAxisRendererProperties(axisRenderer, config);
			axisRenderer.setStyle("canDropLabels", true);
			axisRenderer.titleRenderer = new ChartLabelRenderer(this, ChartLabelRenderer.CATEGORY_TYPE, config);
			if( XML(config.ticklabelconfig) != null ){
				axisRenderer.labelRenderer =
					new ChartLabelRenderer(this, ChartLabelRenderer.CATEGORY_TYPE, config.ticklabelconfig[0]);
			}
		}
		
		override public function updateChartData(componentData:XML):void{
			//not ideal, but remove all labels here to simplify layout and in case of reset/purge
			for(var i:int = 0; i < _chart.numChildren; i++){
				if(_chart.getChildAt(i) is BEVChartDataLabel){
					_chart.removeChildAt(i--);
				}
			}
			super.updateChartData(componentData);
		}
		
		protected static function setCommonAxisRendererProperties(axisRenderer:AxisRenderer, config:XML):void{
			var stroke:Stroke = new Stroke();
		 	stroke.weight = 1;
			stroke.color = 0x909090;
			
			var fontSize:Number = new Number(config.@fontsize);
			var fontColor:Number = config.@fontcolor == undefined ? 0x414141:parseInt(config.@fontcolor, 16);
			var fontStyles:String = new String(config.@fontstyle);
			//var showLabels:Boolean = config.@showticklabels == true || true;
			var relativePos:String = new String(config.@relativeposition);
			
			//Process values
			var fontStyle:String = fontStyles.indexOf("italic") >= 0 ? "italic":"normal";
			var fontWeight:String = fontStyles.indexOf("bold") >= 0 ? "bold":"normal";
			relativePos = relativePos == "above" ? "top":(relativePos == "below" ? "bottom":relativePos);
			
			axisRenderer.setStyle("labelGap", 0);//remove the gap between labels and axis
			axisRenderer.setStyle("axisStroke", stroke);
			axisRenderer.setStyle("tickStroke", stroke);
		 	axisRenderer.setStyle("tickLength", 3);
		 	axisRenderer.setStyle("fontSize", fontSize);
			axisRenderer.setStyle("color", fontColor);
			axisRenderer.setStyle("fontStyle", fontStyle);
			axisRenderer.setStyle("fontWeight", fontWeight);
			axisRenderer.setStyle("showLabels", true); //sever doesn't specify
			axisRenderer.placement = relativePos;
		}
		
		private static function axisLabelFunction(categoryValue:Object, previousCategoryValue:Object, axis:CategoryAxis, categoryItem:Object):String{
			if(categoryItem is DataColumn){
				return categoryItem.displayValue;
			}
			return categoryValue.toString();
		}

	}
}