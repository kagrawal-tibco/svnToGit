package com.tibco.be.views.core.ui{
	
	/**
	 * Represents the actual tooltip being shown
	 */ 
	public interface IBEVTooltip{
		
		/**
		 * Shows the tooltip at the specified location 
		 * @param x the x co-ordinate 
		 * @param y the y co-ordinate 
		 */ 
		function show(x:int,y:int):void;
		
		/**
		 * Closes the tooltip 
		 */ 
		function close():void;
	}
}