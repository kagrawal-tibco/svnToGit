package com.tibco.be.views.core.ui{
	
	import mx.collections.IList;
	
	/**
	 * The SynTooltipProvider encapsulates the tooltip creation 
	 */
	public interface IBEVTooltipProvider{
		
		/**
		 * Initialize the tool tip provider 
		 * @param initParams the initialization parameters 
		 */ 
		function init(initParams:IList):void;
		
		/**
		 * Returns an instance of a tooltip. This should remove any existing tooltip visible 
		 * @param tooltip The actual tooltip 
		 */ 
		function getTooltip(tooltip:String):IBEVTooltip;
	}
}