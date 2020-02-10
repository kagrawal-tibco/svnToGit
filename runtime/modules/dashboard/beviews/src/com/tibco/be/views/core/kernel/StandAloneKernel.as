package com.tibco.be.views.core.kernel{
	
	import com.tibco.be.views.core.ui.mediator.BaseUIMediator;
	
	/**
	 * The StandAloneKernel Kernel allows launching the BEViews platform in standalone independent
	 * mode. Standalone mode represents the mode in which BEViews is launched as itself and not
	 * embedded inside another application
	 */
	public class StandAloneKernel extends Kernel{

		 override protected function initUIMediator():void{
		 	_uiMediator = new BaseUIMediator();
		 	_uiMediator.init(_bootParams);		 	
		 }
	}
}