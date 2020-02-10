package com.tibco.be.views.core.ui.actions{
	
	import com.tibco.be.views.core.utils.IProgressListener;
	
	public interface IChainableAction{
		function execute(actionContext:ActionContext, progressListener:IProgressListener):void;
	}
	
}