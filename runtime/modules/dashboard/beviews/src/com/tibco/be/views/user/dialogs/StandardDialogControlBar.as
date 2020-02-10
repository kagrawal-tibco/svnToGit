package com.tibco.be.views.user.dialogs{
	
	import flash.events.MouseEvent;
	
	import mx.containers.ControlBar;
	import mx.controls.Button;
	import mx.core.EventPriority;

	public class StandardDialogControlBar extends ControlBar{
		
		protected var _okBtn:Button;
		protected var _okBtnHandler:Function;
		protected var _showOkBtn:Boolean;
		protected var _cancelBtn:Button;
		protected var _cancelBtnHandler:Function;
		protected var _showCancelBtn:Boolean;
		protected var _applyBtn:Button;
		protected var _applyBtnHandler:Function;
		protected var _showApplyBtn:Boolean;
		
		public function StandardDialogControlBar(){
			super();
			_showOkBtn = true;
			_showCancelBtn = true;
			_showApplyBtn = false;
		}
		
		override protected function createChildren():void{
			super.createChildren();
			if(_okBtn == null && _showOkBtn){
				_okBtn = new Button();
				_okBtn.label = "Ok";
				_okBtn.styleName = "commonButton";
				_okBtn.setStyle("fillAlphas",[1.0,1.0]);
				_okBtn.addEventListener(MouseEvent.CLICK, handleButtonClick, false, EventPriority.DEFAULT_HANDLER, true);
			}
			if(_cancelBtn == null && _showCancelBtn){
				_cancelBtn = new Button();
				_cancelBtn.label = "Cancel";
				_cancelBtn.styleName = "commonButton";
				_cancelBtn.setStyle("fillAlphas",[1.0,1.0]);
				_cancelBtn.addEventListener(MouseEvent.CLICK, handleButtonClick, false, EventPriority.DEFAULT_HANDLER, true);
			}
			if(_applyBtn == null && _showApplyBtn){
				_applyBtn = new Button();
				_applyBtn.label = "Apply";
				_applyBtn.styleName = "commonButton";
				_applyBtn.setStyle("fillAlphas",[1.0,1.0]);
				_applyBtn.addEventListener(MouseEvent.CLICK, handleButtonClick, false, EventPriority.DEFAULT_HANDLER, true);
			}
			
			if(_showOkBtn){ addChild(_okBtn); }		
			if(_showCancelBtn){ addChild(_cancelBtn); }	
			if(_showApplyBtn){ addChild(_applyBtn); }
		}
		
		protected function handleButtonClick(event:MouseEvent):void{
			if(event.target == _okBtn && _okBtnHandler != null){ _okBtnHandler.apply(); }
			else if(event.target == _cancelBtn && _cancelBtnHandler != null){ _cancelBtnHandler.apply(); }
			else if(event.target == _applyBtn && _applyBtnHandler != null){ _applyBtnHandler.apply(); }
		}
		
		public function set showOKButton(value:Boolean):void{
			if(value != _showOkBtn){
				_showOkBtn = value;
				if(_showOkBtn){ addChild(_okBtn); }				
				else{ removeChild(_okBtn); }
				invalidateDisplayList();
			}
		}
		
		public function set showCancelButton(value:Boolean):void{
			if(value != _showCancelBtn){
				_showCancelBtn = value;
				if(_showCancelBtn){ addChild(_cancelBtn); }				
				else{ removeChild(_cancelBtn); }
				invalidateDisplayList();
			}
		}
		
		public function set showApplyButton(value:Boolean):void{
			if(value != _showApplyBtn){
				_showApplyBtn = value;
				if(_showApplyBtn){ addChild(_applyBtn); }				
				else{ removeChild(_applyBtn); }
				invalidateDisplayList();
			}
		}
		
		public function enableOkButton(enable:Boolean):void{ _okBtn.enabled = enable; }
		public function enableCancelButton(enable:Boolean):void{ _cancelBtn.enabled = enable; }
		public function enableApplyButton(enable:Boolean):void{ _applyBtn.enabled = enable; }
		
		public function setOkButtonHandler(handler:Function):void{ _okBtnHandler = handler; }
		public function setCancelButtonHandler(handler:Function):void{ _cancelBtnHandler = handler; }
		public function setApplyButtonHandler(handler:Function):void{ _applyBtnHandler = handler; }
					
	}
}