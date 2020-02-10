package com.tibco.be.views.core.ui.mediator{
	
	import com.tibco.be.views.core.ui.mediator.IUIController;
	import com.tibco.be.views.core.ui.mediator.UIMediator;
	import com.tibco.be.views.core.ui.menu.BEVMenuProvider;
	
	import flash.utils.Dictionary;
	
	/**
	 * This class triggers the initialization of the BEViews as a web application
	 * In this case we tend to use all the default features provided in flex
	 */

	public class BaseUIMediator extends UIMediator{
		
		public function BaseUIMediator(){
			super();
		 	_menuProvider = new BEVMenuProvider();	
		 	_uiController = buildUIController();		
		}
		
		/**
		 * Allow extending classes to instantiate their own UIController
		*/
		protected function buildUIController():IUIController{
			return new BaseUIController();
		}
		
		/**
		 * function initiates the UIMediator
		 * @public
		 * @param boot params as array collection
		 */
		 override public function init(bootParams:Dictionary):void{
		 	super.init(bootParams);
//		 	__tooltipProvider = new ToolTipProviderImpl();
		 	_menuProvider.init(bootParams);
//		 	__dialogProvider = new DialogProviderImpl();
//		 	__exceptionHandler = new ExceptionHandlerImpl();
		 	//initiate the UI controller
		 	_uiController.init(bootParams);
		 }
		
	}
}