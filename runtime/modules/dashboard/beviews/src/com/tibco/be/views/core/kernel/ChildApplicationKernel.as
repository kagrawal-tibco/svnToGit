package com.tibco.be.views.core.kernel{

	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.events.tasks.ControlResponseEvent;
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.mediator.DrillDownUIMediator;
	import com.tibco.be.views.utils.Logger;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.managers.CursorManager;
	
	/**
	 * A child application kernel works much like the standalone kernel except it uses URL
	 * parameters from a parent BEViews application instance to communicate with the BEViews server,
	 * rather than initializing via Login, setrole, etc.
	*/
	public class ChildApplicationKernel extends Kernel{
		
		override protected function initUIMediator():void{
			//For now, the DrillDownUIMediator works for both DrilldownPage and RelatedPage
			//pagetypes. If other pagetypes come to be that utilize the CHILD_MODE kernel,
			//additional logic may be required here.
			_uiMediator = new DrillDownUIMediator();
			_uiMediator.init(_bootParams);		 	
		}
		
	}
}