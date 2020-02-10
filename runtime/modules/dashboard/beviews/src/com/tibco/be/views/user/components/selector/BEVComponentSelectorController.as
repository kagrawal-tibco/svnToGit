package com.tibco.be.views.user.components.selector{
	
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ConfigResponseEvent;
	
	import flash.events.MouseEvent;
	
	public class BEVComponentSelectorController{
		
		protected var _view:BEVComponentSelector;
		
		public function BEVComponentSelectorController(view:BEVComponentSelector){
			_view = view;
		}
		
		public function init():void{
			_view.selectButton.addEventListener(MouseEvent.CLICK, handleSelectClick);
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse, this);
			var componentListReq:ConfigRequestEvent = new ConfigRequestEvent(ConfigRequestEvent.SEARCH_AVAILABLE_COMPONENTS, this);
			EventBus.instance.dispatchEvent(componentListReq);
		}
		
		private function handleSelectClick(event:MouseEvent):void{
			var item:Object = _view.componentList.selectedItem;
			_view.dispatchEvent(
				new BEVComponentSelectedEvent(
					new String(item.@id),
					new String(item.@name),
					new String(item.@title),
					new String(item.@type)
				)
			);
		}
		
		public function handleResponse(response:ConfigResponseEvent):void{
			var xml:XML = response.dataAsXML;
			if(xml != null && xml.comp != undefined){
				_view.dataProvider = xml.comp;
			}
		}

	}
}