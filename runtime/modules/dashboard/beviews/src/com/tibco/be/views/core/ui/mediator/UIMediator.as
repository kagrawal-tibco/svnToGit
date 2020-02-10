package com.tibco.be.views.core.ui.mediator{
	
	import com.tibco.be.views.core.ui.DialogProvider;
	import com.tibco.be.views.core.ui.IBEVTooltipProvider;
	import com.tibco.be.views.core.ui.ExceptionHandler;	
	import com.tibco.be.views.core.ui.menu.IBEVMenuProvider;
	
	import flash.utils.Dictionary;
	
	/**
	 * The UIMediator is an abstract class which is the key class of the core UI initialization sequence. 
	 * The UIMediator allows us to have different implementation of the core UI services providers 
	 * without the non-core BEViews UI classes being aware of it.
	 */
	public class UIMediator{
		
		//holds the boot parameters
		protected var _bootParams:Dictionary;
		protected var _uiController:IUIController;
		protected var _menuProvider:IBEVMenuProvider;
		protected var _tooltipProvider:IBEVTooltipProvider;
		protected var _dialogProvider:DialogProvider;
		protected var _exceptionHandler:ExceptionHandler;
		
		/**
		 * Constructor
		 */
		public function UIMediator(){
			_bootParams = null;
		}
		
		/**
		 * Initiates the UIMediator with the boot parameters
		 * Sub-classes should override this function to instantiate and configure 
		 * all the providers. Remember to call super.init();
		 * @public
		 * @param boot parameters
		 */
		 public function init(bootParams:Dictionary):void{
		 	_bootParams = bootParams;
		 }
		 
		 /**
		 * Function returns the menuProvider. i.e the menu provider in 
		 * BEViews world. 
		 * @public
		 * @retuns the instance of menu provider
		 */
		 public function get menuprovider():IBEVMenuProvider{ return _menuProvider; }

		 /**
		 * Function returns the TooltipProvider. i.e the tooltip provider in 
		 * BEViews world. 
		 * @public
		 * @retuns the instance of tooltip provider
		 */
		 public function get tooltipprovider():IBEVTooltipProvider{ return _tooltipProvider; }

		 /**
		 * Function returns the DialogProvider. i.e the dialog provider in 
		 * BEViews world. 
		 * @public
		 * @retuns the instance of dialog provider
		 */		 
		 public function get dialogprovider():DialogProvider{ return _dialogProvider; }
		 
		 /**
		 * returns the UIController
		 * @public
		 * @retuns the instance of UIController
		 */		 
		 public function get uicontroller():IUIController{ return _uiController; }
		 
		 /**
		 * Function returns the ExceptionHandler.
		 * @public
		 * @retuns the instance of exception handler
		 */		 
		 public function get exceptionhandler():ExceptionHandler{ return _exceptionHandler; }
	}
}