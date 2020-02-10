package com.tibco.be.views.user.components.chart.series{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.chart.BEVChartDataLabel;
	import com.tibco.be.views.user.components.chart.RangeDataPoint;
	import com.tibco.be.views.user.components.chart.renderers.item.LabeledHLOCItemRenderer;
	import com.tibco.be.views.user.components.chart.renderers.item.LabeledItemRendererFactory;
	import com.tibco.be.views.user.components.chart.renderers.item.LabeledItemUtil;
	import com.tibco.be.views.utils.Logger;
	
	import flash.text.TextLineMetrics;
	
	import mx.charts.chartClasses.CartesianChart;
	import mx.charts.series.HLOCSeries;
	import mx.graphics.Stroke;
	import mx.skins.ProgrammaticSkin;

	public class BEVHLOCSeries extends HLOCSeries implements IBEVLabeledSeries{
		
		private var _seriesConfig:XML;
		private var _actionConfig:XML; //store a copy so it doesn't have to be parsed at each get
		private var _whiskerWidth:int;
		private var _whiskerThickness:int;
		private var _barWidth:int;
		
		public function BEVHLOCSeries(chartTypeXML:XML, seriesName:String, barWidth:int, barColor:uint, whiskerThickness:int, whiskerWidth:int, whiskerColor:uint){
			super();
			useHandCursor = true;
			buttonMode = true;
			highField = RangeDataPoint.MAX_FIELD_NAME;
			lowField = RangeDataPoint.MIN_FIELD_NAME;
			openField = closeField = RangeDataPoint.CURRENT_FIELD_NAME;
			
			_seriesConfig = new XML("<seriesconfig name=\"" + seriesName + "\"></seriesconfig>");
			_actionConfig = new XML("<actionconfig disabled=\"false\" id=\"" + seriesName + "\"><text>ROOT</text></actionconfig>");
			for each(var seriesCfg:XML in chartTypeXML.seriesconfig){
				for each(var actionCfg:XML in seriesCfg.actionconfig){
					var newActionCfg:XML = new XML(actionCfg);
					newActionCfg.@iconColor = seriesCfg.@basecolor;
					if(newActionCfg.text == "ROOT"){
						newActionCfg.text = new String(seriesCfg.@displayname);
					}
					_seriesConfig.appendChild(new XML(newActionCfg));
					_actionConfig.appendChild(new XML(newActionCfg));
				}
			}			
			
			_barWidth = barWidth;
			_whiskerWidth = whiskerWidth;
			_whiskerThickness = whiskerThickness;
			var barStroke:Stroke = new Stroke(barColor, barWidth);
			var whiskerStroke:Stroke = new Stroke(whiskerColor, whiskerThickness);
			
			setStyle("closeTickStroke", whiskerStroke);
			setStyle("closeTickLength", whiskerWidth);
			setStyle("openTickStroke", whiskerStroke);
			setStyle("openTickLength", whiskerWidth);
			setStyle("stroke", barStroke);
			//special handling for range chart config requires passing charttypeconfig for valuelabelconfig
			setStyle("itemRenderer", new LabeledItemRendererFactory(chartTypeXML, LabeledItemRendererFactory.RANGE));
		}
		
		public function get seriesConfig():XML{ return _seriesConfig; }
		
		public function get actionConfig():XML{ return _actionConfig; }
		
		//IBEViewsLabeledSeries
		public function createLabel(labelConfig:XML):BEVChartDataLabel{
			return LabeledItemUtil.createLabel(labelConfig); 
		}
		
		/**
		 * Unlike other BEV series, we're assuming the label text is already set
		*/
		public function updateLabel(label:BEVChartDataLabel, skin:ProgrammaticSkin, data:Object):void{
			var cChart:CartesianChart = chart as CartesianChart;
			var hlocRenderer:LabeledHLOCItemRenderer = skin as LabeledHLOCItemRenderer;
			if(hlocRenderer == null){
				Logger.log(DefaultLogEvent.DEBUG, "BEViewsHLOCSeries.updateLabel - Renderer is null.");
				return;
			}
			var rdp:RangeDataPoint = data.item as RangeDataPoint;
			var leftPad:Number = cChart == null ? 0:cChart.computedGutters.left+1;
			var topPad:Number = cChart == null ? 0:cChart.computedGutters.top;
			
			var txtWidth:Number = label.textWidth;
			var txtHeight:Number = label.textHeight;
			if(isNaN(txtWidth)){
				var metrics:TextLineMetrics = measureText(label.text);
				txtWidth = metrics.width;
				txtHeight = metrics.height;
			}
						
			if(hlocRenderer.isMinLabel(label)){
				topPad += skin.height;
			}
			else if(hlocRenderer.isCurrentLabel(label)){
				topPad += (hlocRenderer.whiskerY - txtHeight/2);
				leftPad += _whiskerWidth + txtWidth/2;
				if(rdp.alertFont != null && rdp.alertFont["color"] != undefined){
					label.setStyle("color", parseInt(rdp.alertFont.color, 16));
				}
				else{
					label.setDefaultFontColor();
				}
				
				if(rdp.alertFont != null && rdp.alertFont["style"] != undefined && rdp.alertFont["style"] != "normal"){
					var style:String = String(rdp.alertFont.style);
					if(style.indexOf("bold") >= 0){ label.setStyle("fontWeight", "bold"); }
					if(style.indexOf("italic") >= 0){ label.setStyle("fontStyle", "italic"); }
				}
				else{
					label.setDefaultFontWeight();
					label.setDefaultFontStyle();
				}
			}
			else if(hlocRenderer.isMaxLabel(label)){
				//show on top of vRange data item
				topPad -= txtHeight;
			}
			else{
				Logger.log(DefaultLogEvent.DEBUG, "BEViewsHLOCSeries.updateLabel - Provided lablel doesn't correspond to min, current, or max.");
			}
			
			styleAndPositionLabel(
				label,
				rdp,
				leftPad + skin.x + skin.width/2,
				skin.y + topPad,
				leftPad,
				topPad,
				chart.width-cChart.computedGutters.right,
				chart.height-cChart.computedGutters.bottom
			);
			label.validateNow();
			
			if(!LabeledItemUtil.detectAndFixLabelOverlap(chart, label, false)){
				//overlap existis and cannot be resolved via moving the label => remove the label
				if(chart.contains(label)){ chart.removeChild(label); }
			}
			else{
				chart.addChild(label);
			}
		}
		
		public function rendererShouldDrawPoint(data:Object):Boolean{
			return true;
		}
		//END IBEViewsLabeledSeries
		
		
		private function styleAndPositionLabel(textLabel:BEVChartDataLabel, rdp:RangeDataPoint, x:int, y:int, minX:int=0, minY:int=0, maxX:int=9999, maxY:int=9999):void{
			var txtWidth:Number = textLabel.textWidth;
			var txtHeight:Number = textLabel.textHeight;
			if(isNaN(txtWidth)){
				var metrics:TextLineMetrics = measureText(textLabel.text);
				txtWidth = metrics.width;
				txtHeight = metrics.height;
			}
			
			var avgCharWidth:Number = txtWidth/textLabel.text.length;
			var labelWidth:int =  txtWidth + avgCharWidth;
			textLabel.height = txtHeight + 2;
			textLabel.width = labelWidth;
			maxX -= labelWidth;
						
			//move label to be centered above the datapoint but not off chart
			var plannedX:int = x - labelWidth/2;
			textLabel.x = Math.min(Math.max(minX, plannedX), maxX);
			textLabel.y = Math.max(minY, Math.min(y, maxY-txtHeight));
			
		}
	}
}