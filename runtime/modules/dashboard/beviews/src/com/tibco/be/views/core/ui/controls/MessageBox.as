package com.tibco.be.views.core.ui.controls{
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.events.MouseEvent;
	
	import mx.containers.HBox;
	import mx.containers.TitleWindow;
	import mx.controls.Button;
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.controls.TextArea;
	import mx.core.Application;
	import mx.effects.Fade;
	import mx.effects.Resize;
	import mx.events.CloseEvent;
	import mx.events.EffectEvent;
	import mx.managers.CursorManager;
	import mx.managers.PopUpManager;

	public final class MessageBox{
		
		public static const ERROR_TYPE:String = "error";
		public static const INFO_TYPE:String = "info";
		public static const WARNING_TYPE:String = "warning";
		public static const COLLAPSED_DETAILS_STATE:String = "collapseddetails";
		public static const EXPANDED_DETAILS_STATE:String = "expandeddetails";
		
		[Embed(source="assets/images/messagebox/stop.png")] 
		private static const ERROR_ICON_CLASS:Class;  
		
		[Embed(source="assets/images/messagebox/info.png")] 
		private static const INFO_ICON_CLASS:Class;
		
		[Embed(source="assets/images/messagebox/warn.png")] 
		private static const WARNING_ICON_CLASS:Class;	
		
		private static const BUTTON_WIDTH:int = 100;
		private static const MESSAGE_BOX_COLLAPSED_HEIGHT:int = 120;
		private static const MESSAGE_BOX_EXPANDED_HEIGHT:int = 250;			

		public static function show(parent:DisplayObject,title:String,message:String,closeHandler:Function = null, detailedMessage:String = null,type:String=ERROR_TYPE,state:String=COLLAPSED_DETAILS_STATE):void{
			if(parent == null){ parent = DisplayObjectContainer(Application.application); }
			var msgBox:MessageBox = new MessageBox(parent, title, message, closeHandler, detailedMessage, type, state);
			msgBox.show();
		}
		
		private var _parent:DisplayObject;
		private var _title:String;
		private var _message:String;
		private var _detailedMessage:String;
		private var _type:String;
		private var _state:String;
		private var _closeHandler:Function;
		private var _titledWindow:TitleWindow;
		private var _msgFld:Label;
		private var _showOrHideDetailsBtn:Button;
		private var _resizeEffect:Resize;
		private var _fadeEffect:Fade;
		
		function MessageBox(parent:DisplayObject, title:String,message:String, closeHandler:Function, detailedMessage:String, type:String, state:String){
			_parent = parent;
			_title = title;
			_message = message;
			_detailedMessage = detailedMessage;
			_type = type;
			_state = state;
			_closeHandler = closeHandler;
			_titledWindow = createTitleWindow();
			_resizeEffect = new Resize(_titledWindow);
			_resizeEffect.duration = 500;
			_fadeEffect = new Fade();
			_fadeEffect.duration = 500;
		}
		
		internal function show():void{
			PopUpManager.addPopUp(_titledWindow,_parent,true);
			PopUpManager.bringToFront(_titledWindow);
			var maxWidth:int = Application.application.width * 0.95;
			if(_titledWindow.width > maxWidth){
				_msgFld.toolTip = _message;
				_titledWindow.width = maxWidth;
			}
			PopUpManager.centerPopUp(_titledWindow);
		}
		
		internal function hide():void{
			PopUpManager.removePopUp(_titledWindow);
			if(_closeHandler != null){
				_closeHandler();
			}
		}
		
		private function createTitleWindow():TitleWindow{
			var tWindow:TitleWindow = new TitleWindow();
			tWindow.title = _title;
			tWindow.layout = "vertical";
			tWindow.styleName = "messageBoxPanel";
			var firstBox:HBox = new HBox();
			firstBox.percentWidth = 100;
			firstBox.height = 40;
			firstBox.setStyle("verticalAlign", "middle");
			firstBox.setStyle("horizontalAlign", "center");
			
			var iconImage:Image = new Image();
			switch(_type){
				case(INFO_TYPE):
					iconImage.source = ERROR_ICON_CLASS;
					break;
				case(WARNING_TYPE):
					iconImage.source = WARNING_ICON_CLASS;
					break;
				case(ERROR_TYPE):
				default:
					iconImage.source = ERROR_ICON_CLASS;
					break;
			}
			firstBox.addChild(iconImage);
			
			_msgFld = new Label();
			_msgFld.text = _message;
			_msgFld.toolTip = _message;
			_msgFld.percentWidth = 100;
			firstBox.addChild(_msgFld);
			
			var secondBox:HBox = new HBox();
			secondBox.percentWidth = 100;
			secondBox.setStyle("verticalAlign","middle");
			secondBox.setStyle("horizontalAlign","center");		
			secondBox.setStyle("horizontalGap","25");
			
			var okBtn:Button = new Button();
			okBtn.label = "Ok";
			okBtn.width = BUTTON_WIDTH;
			okBtn.styleName = "commonButton";
			okBtn.addEventListener(MouseEvent.CLICK, okBtnClickHandler);
			secondBox.addChild(okBtn);

			tWindow.addChild(firstBox);
			tWindow.addChild(secondBox);
			tWindow.height = MESSAGE_BOX_COLLAPSED_HEIGHT;
			
			if(_detailedMessage != null){
				_showOrHideDetailsBtn = new Button();
				_showOrHideDetailsBtn.width = BUTTON_WIDTH;
				_showOrHideDetailsBtn.styleName = "commonButton";
				if(_state == COLLAPSED_DETAILS_STATE){
					_showOrHideDetailsBtn.label = "Details >>";	
				}
				else{
					_showOrHideDetailsBtn.label = "<< Details";
					var thirdBox:HBox = new HBox();
					thirdBox.percentWidth = 100;
					thirdBox.percentHeight = 100;
					thirdBox.setStyle("verticalAlign","middle");
					thirdBox.setStyle("horizontalAlign","center");
					
					var textArea:TextArea = new TextArea();
					textArea.text = _detailedMessage;
					textArea.editable = false;
					textArea.percentWidth = 100;
					textArea.percentHeight = 100;
					thirdBox.addChild(textArea);
					
					tWindow.addChild(thirdBox);
					tWindow.height = MESSAGE_BOX_EXPANDED_HEIGHT;
				}
				_showOrHideDetailsBtn.addEventListener(MouseEvent.CLICK, showOrHideDetailsBtnClickHandler);
				secondBox.addChild(_showOrHideDetailsBtn);				
			}
			tWindow.defaultButton = okBtn;
			tWindow.addEventListener(CloseEvent.CLOSE, windowCloseHandler);
			return tWindow;	
		}
		
		private function okBtnClickHandler(event:MouseEvent):void{
			hide();
		}
		
		private function windowCloseHandler(event:CloseEvent):void{
			hide();
		}
		
		private function showOrHideDetailsBtnClickHandler(event:MouseEvent):void{
			_showOrHideDetailsBtn.enabled = false;
			CursorManager.setBusyCursor();			
			if(_state == COLLAPSED_DETAILS_STATE){
				_resizeEffect.heightTo = MESSAGE_BOX_EXPANDED_HEIGHT;
				_resizeEffect.addEventListener(EffectEvent.EFFECT_END, resizeCompleteHandler);
				_resizeEffect.play();
			}
			else{
				_fadeEffect.target = _titledWindow.getChildAt(2);
				_fadeEffect.alphaFrom = 1.0;
				_fadeEffect.alphaTo = 0.0;
				_fadeEffect.addEventListener(EffectEvent.EFFECT_END, fadeCompleteHandler);
				_fadeEffect.play();
			}
		}
		
		private function resizeCompleteHandler(event:EffectEvent):void{
			if(_state == COLLAPSED_DETAILS_STATE){
				_showOrHideDetailsBtn.label = "<< Details";
				var thirdBox:HBox = new HBox();
				thirdBox.percentWidth = 100;
				thirdBox.percentHeight = 100;
				thirdBox.setStyle("verticalAlign","middle");
				thirdBox.setStyle("horizontalAlign","center");
				
				var textArea:TextArea = new TextArea();
				textArea.text = _detailedMessage;
				textArea.editable = false;
				textArea.percentWidth = 100;
				textArea.percentHeight = 100;
				thirdBox.addChild(textArea);
				
				_titledWindow.addChild(thirdBox);
				
				_fadeEffect.target = _titledWindow.getChildAt(2);
				_fadeEffect.alphaFrom = 0.0;
				_fadeEffect.alphaTo = 1.0;
				_fadeEffect.addEventListener(EffectEvent.EFFECT_END, fadeCompleteHandler);
				_fadeEffect.play();
			}
			else{
				_state = COLLAPSED_DETAILS_STATE;
				_showOrHideDetailsBtn.enabled = true;
				CursorManager.removeBusyCursor();					
			}
			_resizeEffect.removeEventListener(EffectEvent.EFFECT_END, resizeCompleteHandler);				
		}
		
		private function fadeCompleteHandler(event:EffectEvent):void{
			if(_state == EXPANDED_DETAILS_STATE){
				_showOrHideDetailsBtn.label = "Details >>";
				_titledWindow.removeChildAt(2);
				_resizeEffect.heightTo = MESSAGE_BOX_COLLAPSED_HEIGHT;
				_resizeEffect.addEventListener(EffectEvent.EFFECT_END, resizeCompleteHandler);
				_resizeEffect.play();
			}
			else{
				_state = EXPANDED_DETAILS_STATE;
				_showOrHideDetailsBtn.enabled = true;
				CursorManager.removeBusyCursor();					
			}
			_fadeEffect.removeEventListener(EffectEvent.EFFECT_END, fadeCompleteHandler);				
		}		
						
	}
}