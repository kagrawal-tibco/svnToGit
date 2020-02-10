package com.tibco.cep.ui.monitor.panes.chart{
	
	import com.tibco.cep.ui.monitor.util.Logger;
	
	import mx.charts.*;
	import mx.charts.chartClasses.*;
	import mx.graphics.SolidColor;
	import mx.graphics.Stroke;
	
	public class ChartStyle{
		
		private var dataColors:Array;
		private var fontColor:uint;
		private var bgColor:uint;
		
		public function ChartStyle(colors:Array, fontColor:uint, backgroundColor:uint){
			try{
				for(var i:int = 0; i < colors.length; i++){
					var tmp:uint = colors[i] as uint;		//test if every color is in of uint data type
				}
				this.dataColors = colors;
			}
			catch(err:Error){
				throw new Error("ERROR: Couldn't create ChartStyle due to invalid color item.\n\n" + err.message);
			}
			this.fontColor = fontColor;
			this.bgColor = backgroundColor;
		}
		
		public function getDataColor(index:int):uint{ return dataColors[index] as uint; }
		
		public function assignStyleToChart(chart:ChartBase):void{
			//chart.setStyle("fill", this.bgColor);
			chart.setStyle("fill", 0xedf1f3);
			chart.setStyle("color", this.fontColor);
			var i:int;
			if(chart is AreaChart){
				for(i = 0; i < chart.series.length && i < dataColors.length; i++){
					(chart.series[i] as Series).setStyle("areaFill", new SolidColor(this.dataColors[i] as uint, .40));
					(chart.series[i] as Series).setStyle("areaStroke", new Stroke(this.dataColors[i] as uint, 3, .65));
				}
			}
			else{
				for(i = 0; i < chart.series.length && i < dataColors.length; i++){
					(chart.series[i] as Series).setStyle("fill", new SolidColor(this.dataColors[i] as uint, .65));
					(chart.series[i] as Series).setStyle("lineStroke", new Stroke(this.dataColors[i] as uint, 3, .65));
				}
			}
			
			//set grid line styles
			var gridLines:GridLines = new GridLines();
			gridLines.setStyle("direction", "horizontal");
			gridLines.setStyle("horizontalFill", 0xced8e4);
			chart.backgroundElements = [gridLines];
			
		}
		
		public function assignStyleToSeries(series:Series, seriesNum:int=0):Boolean{
			if(seriesNum > dataColors.length){
				Logger.log(this,"ERROR: Failed setting series color! Series with number " + seriesNum + 
							" exceeds the total number of colors: " + dataColors.length);
				return false;
			}
			series.setStyle("fill", new SolidColor(this.dataColors[seriesNum] as uint, .65));
			series.setStyle("stroke", new Stroke(this.dataColors[seriesNum] as uint, 3, .65));
			series.setStyle("lineStroke", new Stroke(this.dataColors[seriesNum] as uint, 3, .65));
			return true;
		}		

	}
}
     