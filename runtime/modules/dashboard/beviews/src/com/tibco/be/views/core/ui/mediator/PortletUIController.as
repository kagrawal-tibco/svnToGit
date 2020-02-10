package com.tibco.be.views.core.ui.mediator{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.dashboard.BEVComponentFactory;
	import com.tibco.be.views.portlet.*;
	import com.tibco.be.views.portlet.login.*;
	import com.tibco.be.views.user.components.selector.BEVComponentSelectedEvent;
	import com.tibco.be.views.user.components.selector.BEVComponentSelector;
	import com.tibco.be.views.utils.Logger;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.utils.Dictionary;
	
	import mx.core.Application;
	import mx.events.FlexEvent;

	/**
	 * Controls what's shown in the portlet. Initially the login is shown. Upon completion of login,
	 * the component chooser is displayed.  Once a component is selected, it is displayed.
	*/
	public class PortletUIController extends BaseUIController{
		
		public static const PORTLET_PAGETYPE:String = "PortletPage";
		
		private var _loginDialog:PortletLoginDialog;
		private var _componentSelector:BEVComponentSelector;
		private var _component:BEVComponent;
		private var _componentContainer:BEVPortletComponentHolder
		
		public function PortletUIController(){
			super();
			
			_loginDialog = new PortletLoginDialog();
			_loginDialog.percentHeight = 100;
			_loginDialog.percentWidth = 100;
			_loginDialog.addEventListener(FlexEvent.CREATION_COMPLETE, handleLoginCreationComplete);
			
			_componentSelector = new BEVComponentSelector();
			_componentSelector.percentWidth = 100;
			_componentSelector.percentHeight = 100;
			_componentSelector.addEventListener(BEVComponentSelectedEvent.COMPONENT_SELECTED, handleComponentSelected);
			
		}
		
		override public function init(bootParams:Dictionary):void{
			Application.application.addChild(_loginDialog);			
		}
		
		protected function handleLoginCreationComplete(event:FlexEvent):void{
			_loginDialog.removeEventListener(FlexEvent.CREATION_COMPLETE, handleLoginCreationComplete);
			_loginDialog.addCompletionListener(handleLoginComplete);
		}
		
		protected function handleLoginComplete(event:Event):void{
			_loginDialog.removeCompletionListener(handleLoginComplete);
			Application.application.removeChild(_loginDialog);
			Application.application.addChild(_componentSelector);
		}
		
		protected function handleComponentSelected(event:BEVComponentSelectedEvent):void{
			_component = BEVComponentFactory.instance.getComponent(
				event.componentId,
				event.componentName,
				event.componentTitle,
				event.componentType,
				null
			);
			_component.percentWidth = 100;
			_component.percentHeight = 100;
			_component.displayMode = BEVComponent.PORTLET_DISPLAY_MODE;
			
			_componentContainer = new BEVPortletComponentHolder();
			_componentContainer.addEventListener(FlexEvent.CREATION_COMPLETE, handleComponentHolderCreation);
			Application.application.removeChild(_componentSelector);
			Application.application.addChild(_componentContainer);
		}
		
		private function handleComponentHolderCreation(event:FlexEvent):void{
			if(_component == null){
				Logger.log(DefaultLogEvent.WARNING, "PortletUIController.handleComponentHolderCreation - null component.");
			}
			_componentContainer.component = _component;
			_componentContainer.addChangeComponentListener(handleComponentChange);
			_component.init();
		}
		
		private function handleComponentChange(event:MouseEvent):void{
			_componentContainer.removeChangeComponentListener(handleComponentChange);
			Application.application.removeChild(_componentContainer);
			Application.application.addChild(_componentSelector);
		}
		
	}
}