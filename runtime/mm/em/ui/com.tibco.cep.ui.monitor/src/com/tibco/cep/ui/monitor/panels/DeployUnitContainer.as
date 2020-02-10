package com.tibco.cep.ui.monitor.panels
{
	import mx.containers.FormItem;
	import mx.containers.VBox;
	import mx.controls.CheckBox;
	import mx.controls.Label;
	import mx.events.FlexEvent;
	
	public class DeployUnitContainer
	{
		private var _duFqn:String; 
		private var _duStId:String;		//DU Site Topology ID 
		private var _duName:String; 
		private var _duCDDDeployLoc:String; 
		private var _duEarDeployLoc:String;
		private var _duContainer:VBox;
		private var _ducb:CheckBox;
		private var _checkBoxEventHandler:Function;
		
		public function DeployUnitContainer(duFqn:String, duStId:String, duCDDDeployLoc:String, 
		                                    duEarDeployLoc:String, checkBoxEventHandler : Function) { 
			_duFqn = duFqn;
			_duStId = duStId;
			var duSplit:Array = duFqn.split("/"); 
			_duName = duSplit[duSplit.length-1];
			_duCDDDeployLoc = duCDDDeployLoc;
			_duEarDeployLoc = duEarDeployLoc;
			_checkBoxEventHandler = checkBoxEventHandler;
			createView();
		}
		
		public function get duFqn():String {return _duFqn;}
		
		public function get container():VBox {return _duContainer;}
		
		public function get checkBox():CheckBox { return _ducb; }
		
		private function createView():void {
			_duContainer = new VBox;
			_duContainer.setStyle("verticalGap", 0);
			_duContainer.setStyle("paddingBottom", 0);
			_duContainer.setStyle("paddingLeft", 5);
			_duContainer.id = _duFqn;

			_ducb = new CheckBox();
			_ducb.label = _duName;
			_ducb.id = _duContainer.id + "cb";
			_ducb.addEventListener(FlexEvent.VALUE_COMMIT,_checkBoxEventHandler)

			_duContainer.addChild(_ducb);
			_duContainer.addChild(createFormItemAndLabel("CDD Path: ", _duCDDDeployLoc));
			_duContainer.addChild(createFormItemAndLabel("EAR Path: ", _duEarDeployLoc));
		}
		
		private function createFormItemAndLabel(formItemLabel:String, fiLabelText:String):FormItem {
			var formItem:FormItem = new FormItem();
			formItem.label = formItemLabel;
			formItem.styleName = "formLblStyle";
			formItem.percentWidth=100;
			formItem.id = _duContainer.id + "fil"; 

			var label:Label = new Label();
			label.text = fiLabelText;
			label.toolTip = fiLabelText;
			label.styleName="labelStyle" 
			label.percentWidth=100;
			label.selectable = true;
			label.id = _duContainer.id + "filt"; 
			
			formItem.addChild(label);
			return formItem;
		}

	}
}