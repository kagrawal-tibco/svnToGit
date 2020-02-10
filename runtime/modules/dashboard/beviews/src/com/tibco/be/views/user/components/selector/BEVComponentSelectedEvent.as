package com.tibco.be.views.user.components.selector{
	
	import flash.events.Event;

	public class BEVComponentSelectedEvent extends Event{
		
		public static const COMPONENT_SELECTED:String = "componentSelectedEvent";
		
		private var _componentId:String;
		private var _componentName:String;
		private var _componentTitle:String;
		private var _componentType:String;
		
		public function BEVComponentSelectedEvent(componentId:String, componentName:String, componentTitle:String, componentType:String){
			super(COMPONENT_SELECTED);
			_componentId = componentId;
			_componentName = componentName;
			_componentTitle = componentTitle
			_componentType = componentType;
		}
		
		public function get componentId():String{ return _componentId; }
		public function get componentName():String{ return _componentName; }
		public function get componentTitle():String{ return _componentTitle; }
		public function get componentType():String{ return _componentType; }
		
	}
}