package com.tibco.cep.ui.monitor.panels
{
	import com.tibco.cep.ui.monitor.BESystemMonitorController;
	import com.tibco.cep.ui.monitor.IUpdateable;
	import com.tibco.cep.ui.monitor.containers.FormItemWithIcon;
	import com.tibco.cep.ui.monitor.io.PSVRClient;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.controls.TextInput;
	import mx.events.FlexEvent;
	
	public class MethodInvocationPanelController extends PopupPanelController implements IUpdateable
	{
		private var _view:methodInvocationPanel;
		private var _panelDefinition:XML;			//contains the XML info necessary to populate the Method details pop-up
//		private var _monitoredNode:XML;
		private var _currentPanel:GeneralPanel;
		private var _numArgs:int = 0;
		private var _beSystemMonitorController:BESystemMonitorController;
		private var _resultsPanel:GeneralPanel;
		private static var _methodIdToMethodDesc:Dictionary = new Dictionary(true);	//TODO:
		private var _hasArgs:Boolean;
		
		private const NO_ARGUMENTS_STATE:String="noArguments";
		private const ARGUMENTS_STATE:String="withArguments";
		
		
		public function MethodInvocationPanelController(monitoredNode:XML, currentPanel:GeneralPanel, 
														beSystemMonitorController:BESystemMonitorController) {
			
			_monitoredNode = monitoredNode;
			_currentPanel = currentPanel;
			_beSystemMonitorController = beSystemMonitorController;
		} //MethodInvocationPanelController
		
		public override function createView():void {
			//we need to receive the method description information from the server, so the panel 
			//is created in the "update" method of this class 
			PSVRClient.instance.getMethodPanel(_monitoredNode.@monitorableID, _monitoredNode.@id, this);
		}
			
		protected override function setData():void{
			setName();
			setDescription();
			setArgs();
			dispatchChangeEvents(_view);
		} //setData
			
		private function setName():void{
			_view.methodName.text = _panelDefinition.page.methodpanelconfig.name.toString();
			_view.methodDesc.toolTip = _panelDefinition.page.methodpanelconfig.description.toString();
		}//setName
			
		private function setDescription():void{
			_view.methodDesc.text = _panelDefinition.page.methodpanelconfig.description.toString();
			_view.methodDesc.toolTip = _panelDefinition.page.methodpanelconfig.description.toString();
		}
		
		//TODO: Highlight the text when textbox selected.
		/** Sets the arguments form, including the default values of the arguments of the method. 
		 *  Validates user input */
		private function setArgs():void {   //TODO: Handle the no arguments case
			validatorsList = new Array();	
				
			for each(var arg:XML in _panelDefinition.page.methodpanelconfig.arguments.children()) {
				var argItem:FormItemWithIcon = new FormItemWithIcon();
				var argTextInput:TextInput = new TextInput();
				var argDataType:String = String(arg.@type).toLowerCase();			//argument type
				
				_hasArgs = true;		//The code is inside the for each loop => method has args, so se set the flat to true
				_view.argsLabel.text = "Arguments:";
				_view.currentState = ARGUMENTS_STATE;
				
				argItem.id = String(arg.@name);
				argItem.label = String(arg.@name);
				//add the icon corresponding to this argument's datatype
				argItem.imageSource = getIconImageClass(argDataType);
				argItem.toolTip = String(arg.@desc); 
				//apply skin
				argItem.styleName="formLblStyle";
				argItem.percentWidth=100;
				
				argTextInput.text = String(arg.@defaultvalue);
				argTextInput.selectionBeginIndex;				//TODO: Check this
				//apply skin
				argTextInput.styleName="textInputStyle";
				argTextInput.percentWidth=100;
				
				//set validator for this argument and adds it to the validators list
				validatorsList.push( setValidator(argTextInput, arg.@required, argDataType) );
						
				argItem.addChild(argTextInput);
				_view.argsForm.addChild(argItem);
			} //for
			
			//if method receives no arguments
			if (!_hasArgs) {
				_view.currentState = NO_ARGUMENTS_STATE;
//				_view.argsLabel.setStyle("fontSize",14);
//				_view.argsLabel.text = "  This method receives no arguments."; 
			}
		} //setArgs
		
		/** Deactivate the OK button */
		protected override function handleInvalidInput(event:Event):void {
			_view.btn_OK.enabled = false;
			_view.btn_OK.toolTip = BTN_OK_TOOLTIP;
		}
		
		/** Activate the OK button */
		protected override function handleValidInput(event:Event):void {
			if (isViewValid()) {//enable button OK
				_view.btn_OK.enabled = true;
				_view.btn_OK.toolTip = "";
			} //else do nothing. Previous state remains as it was
		}
		
		private function OKBtnClickHandler(event:Event):void {
			var parameters:String = getTokenizedParamsStr();
			//returned value is handled by the BESystemMonitorController update(...) method
			PSVRClient.instance.invokeMethod(parameters, _monitoredNode.@monitorableID, _monitoredNode.@id, 
											 _beSystemMonitorController );
			closePopUp();
//			//HUGO: TODO: Remove listeners for memory optimization
		}
		
		/** Creates a TOKENized string with the validated arguments submitted by the user 
		 *  These arguments are the parameters used during method invocation */
		private function getTokenizedParamsStr():String {
			//Handle the no arguments case
			if (!_hasArgs)
				return null;
			
			const TOKEN:String = "#";
			var paramsTokenStr:String="";
			var args:ArrayCollection = new ArrayCollection( _view.argsForm.getChildren() );
			
			try {
				for each (var arg:Object in args){
					var argItem:Array = arg.getChildren();
					var argValue:String = argItem[0].text;
					paramsTokenStr = paramsTokenStr + argValue + TOKEN;
				} //for
			} //try
			catch (e:Error){
				trace("[MethodInvocationController] - Error ocurred during parameters tokenization. NULL parameter returned.");
				trace(e.message);
				trace(e.getStackTrace());
				return null;
			}
			return paramsTokenStr = paramsTokenStr.substr(0, paramsTokenStr.length - 1); //remove the last TOKEN
		} //getTokenizedParamsStr
	
		private function CancelBtnClickHandler(event:Event):void {
			closePopUp();
		}
		
		/** receives and handles the method description information received from the server */
		public function update(operation:String,data:XML):void {
			_panelDefinition = data;
			_view = new methodInvocationPanel();
			_view.addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
			createPopUp(_view, _currentPanel);
		}
		
		public function updateFailure(operation:String,message:String,code:uint):void {
			trace("MethodInvocationPanelController - " + operation + ":\n\n" + message);
		}
		
//		public function get view():methodInvocationPanel {return _view;} 
		
		private function handleCreationComplete(event:FlexEvent):void {
			setData();
			_view.btn_OK.addEventListener(MouseEvent.CLICK, OKBtnClickHandler);
			_view.btn_Cancel.addEventListener(MouseEvent.CLICK, CancelBtnClickHandler);
		}

	} //class
} //package