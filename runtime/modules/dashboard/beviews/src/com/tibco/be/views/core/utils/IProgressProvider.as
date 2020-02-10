package com.tibco.be.views.core.utils {
	
	/**
	 * This is a utility interface which provides APIs to allow monitoring the progress of a certain task. 
	 * Any class which does long term operations and wishes to be monitorable should implement this 
	 * interface.
	 */
	public interface IProgressProvider {
		
		function set progressListener(pListener:IProgressListener):void;
		
		function get progressListener():IProgressListener;
		
	}
}