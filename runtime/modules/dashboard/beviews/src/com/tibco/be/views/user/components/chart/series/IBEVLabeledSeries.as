package com.tibco.be.views.user.components.chart.series{
	
	import com.tibco.be.views.user.components.chart.BEVChartDataLabel;
	
	import mx.skins.ProgrammaticSkin;
	
	public interface IBEVLabeledSeries extends IBEVSeries{
		
		/**
		 * Provides an easy way to get new, uniform instances of BEViewsChartDataLabel.
		 * 
		 * @return new BEViewsChartDataLable
		*/
		function createLabel(labelConfig:XML):BEVChartDataLabel;
		
		/**
		 * Updates the properties (position and text) of the provided BEViewsChartDataLabel. If,
		 * while updating position properties, overlap with other labels is detected, rearranging is
		 * attempted. If overlap cannot be resolved, the label is removed.
		 * 
		 * @param label
		 * 		BEViewsChartDataLabel to update
		 * @param skin
		 * 		ProgramaticSkin (typically an instance of ItemRenderer) to use as the basis for
		 * 		where the label is place.
		 * @param data
		 * 		Object (typically ChartItem from the calling ItemRenderer) containing label text
		 * 		info.
		 * 
		 * @return Boolean value which signals whether or not the corresponding data point
		 * 	should be drawn.
		*/
		function updateLabel(label:BEVChartDataLabel, skin:ProgrammaticSkin, data:Object):void;
		
		/**
		 * Method inteded for use by a Labeled Item Renderer. It determines whether or not the
		 * renderer should draw the data point (circle, box, etc.) on the chart.
		 * 
		 * @param data
		 * 	Object (typically ChartItem) associated with the calling item renderer.
		*/
		function rendererShouldDrawPoint(data:Object):Boolean;
		
	}
}

/*
		public function createLabel(labelConfig:XML):BEViewsChartDataLabel{
			return null;
		}
		
		public function updateLabel(label:BEViewsChartDataLabel, skin:ProgrammaticSkin, data:Object):void{
		
		}
		
		public function rendererShouldDrawPoint(data:Object):Boolean{
			return false;
		}
//*/