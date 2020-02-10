package com.tibco.be.views.user.dialogs.filter{
	import com.tibco.be.views.user.dialogs.StandardDialogControlBar;
	
	import flash.events.MouseEvent;
	
	import mx.controls.Button;
	import mx.core.EventPriority;
	
	
	public class FilterEditorDialogControlBar extends StandardDialogControlBar{
		
		private var _applyToAllBtn:Button;
		private var _applyToAllBtnHandler:Function;
		private var _showApplyToAllBtn:Boolean;
		
		public function FilterEditorDialogControlBar(){
			super();
			_showOkBtn = false;
			_showApplyBtn = true;
			_showApplyToAllBtn = true;
		}
		
		override protected function createChildren():void{
			super.createChildren();
			if(_applyToAllBtn == null && _showApplyToAllBtn){
				_applyToAllBtn = new Button();
				_applyToAllBtn.label = "Apply To All";
				_applyToAllBtn.width = 100;
				_applyToAllBtn.styleName = "commonButton";
				_applyToAllBtn.setStyle("fillAlphas",[1.0,1.0]);
				_applyToAllBtn.addEventListener(MouseEvent.CLICK, handleButtonClick, false, EventPriority.DEFAULT_HANDLER, true);
			}
			
			removeAllChildren(); //standard has cancel and apply first
			if(_showApplyToAllBtn){ addChild(_applyToAllBtn); }
			if(_showApplyBtn){ addChild(_applyBtn); }
			if(_showCancelBtn){ addChild(_cancelBtn); }
		}
		
		override protected function handleButtonClick(event:MouseEvent):void{
			super.handleButtonClick(event);
			if(event.target == _applyToAllBtn && _applyToAllBtnHandler != null){ _applyToAllBtnHandler.apply(); }
		}
		
		public function set showApplyToAllButton(value:Boolean):void{
			if(value != _showApplyToAllBtn){
				_showApplyToAllBtn = value;
				if(_showApplyToAllBtn){ addChild(_applyToAllBtn); }				
				else{ removeChild(_applyToAllBtn); }
				invalidateDisplayList();
			}
		}
		
		public function enableApplyToAllButton(enable:Boolean):void{ _applyToAllBtn.enabled = enable; }
		
		public function setApplyToAllButtonHandler(handler:Function):void{ _applyToAllBtnHandler = handler; }
		
	}
}