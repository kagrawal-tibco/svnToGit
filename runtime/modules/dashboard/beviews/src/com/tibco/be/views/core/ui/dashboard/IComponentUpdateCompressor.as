package com.tibco.be.views.core.ui.dashboard{
	
	public interface IComponentUpdateCompressor{
		
		/**
		 * Compresses a series of component updates into a smaller number of updates to prevent
		 * excessive and unnecessary GUI repaints
		 * 
		 * @param updates An array of XML objects which contain component update information
		*/
		function compress(updates:Array):void;
		
	}
}