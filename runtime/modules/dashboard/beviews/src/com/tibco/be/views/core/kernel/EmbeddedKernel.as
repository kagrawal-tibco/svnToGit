package com.tibco.be.views.core.kernel{
	
	import com.tibco.be.views.core.ui.mediator.PortletUIMediator;
	
	/**
	 * The Embedded Kernel allows launching the BEViews platform in embedded mode. Currently the 
	 * embedded platform is not implemented. 
	 */
	public class EmbeddedKernel extends Kernel{

		 override protected function initUIMediator():void{
		 	_uiMediator = new PortletUIMediator();
		 	_uiMediator.init(_bootParams);
		 }
		 
	}
}