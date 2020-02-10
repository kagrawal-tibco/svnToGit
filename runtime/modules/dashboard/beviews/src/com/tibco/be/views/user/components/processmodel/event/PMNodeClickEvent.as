package com.tibco.be.views.user.components.processmodel.event{
	
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class PMNodeClickEvent extends Event{
		
		public static const PM_NODE_CLICK_EVENT:String = "pmnodeclickevent";
		
		protected var _dataItemId:String;
		
		protected var _localX:int;
		protected var _localY:int;
		protected var _stageX:int;
		protected var _stageY:int;
		
		protected var _altKey:Boolean;
		protected var _buttonDown:Boolean;
		protected var _ctrlKey:Boolean;
		protected var _shiftKey:Boolean;
		
		public function PMNodeClickEvent(itemId:String, baseMouseEvent:MouseEvent=null){
			super(PM_NODE_CLICK_EVENT);
			_dataItemId = itemId;
			if(baseMouseEvent != null){
				_localX = baseMouseEvent.localX;
				_localY = baseMouseEvent.localY;
				_stageX = baseMouseEvent.stageX;
				_stageY = baseMouseEvent.stageY;
				_altKey = baseMouseEvent.altKey;
				_buttonDown = baseMouseEvent.buttonDown;
				_ctrlKey = baseMouseEvent.ctrlKey;
				_shiftKey = baseMouseEvent.shiftKey;
			}
		}
		
		public function get dataItemId():String{ return _dataItemId; }
		public function get localX():int{ return _localX; }
		public function get localY():int{ return _localY; }
		public function get stageX():int{ return _stageX; }
		public function get stageY():int{ return _stageY; }
		public function get altKey():Boolean{ return _altKey; } 
		public function get buttonDown():Boolean{ return _buttonDown; }
		public function get ctrlKey():Boolean{ return _ctrlKey; }
		public function get shiftKey():Boolean{ return _shiftKey; }
		
	}
}