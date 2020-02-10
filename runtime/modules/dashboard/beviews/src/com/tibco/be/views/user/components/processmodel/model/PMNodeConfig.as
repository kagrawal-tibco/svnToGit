package com.tibco.be.views.user.components.processmodel.model{
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	public class PMNodeConfig extends EventDispatcher{
		
		public static const CONFIG_UPDATE:String = "configupdate";
		public static const CONTENT_DATA_ACTION_MODE:int = 0;
		public static const INDICATOR_DATA_ACTION_MODE:int = 1;
		
		private var _id:String;
		private var _title:String;
		private var _x:Number;
		private var _y:Number;
		private var _width:Number;
		private var _height:Number;
		private var _expandable:Boolean;
		private var _actionConfig:XML;
		private var _columnConfig:XMLList;
		private var _dataActionConfig:XML;
		
//		private var _actionConfig:XML = 
//			<actionconfig disabled="false">
//				<text>ROOT</text>
//				<actionconfig command="showdialog" disabled="false" defaultitem="false">
//					<text>Edit...</text>
//					<configparam name="dialogname">metriceditor</configparam>
//					<dynamicparam name="pageid">currentpage.id</dynamicparam>
//					<dynamicparam name="panelid">currentpanel.id</dynamicparam>
//					<dynamicparam name="componentid">currentcomponent.id</dynamicparam>
//				</actionconfig>
//				<actionconfig command="showdialog" disabled="false" defaultitem="false">
//					<text>Duplicate...</text>
//					<configparam name="dialogname">metriccloner</configparam>
//					<dynamicparam name="pageid">currentpage.id</dynamicparam>
//					<dynamicparam name="panelid">currentpanel.id</dynamicparam>
//					<dynamicparam name="componentid">currentcomponent.id</dynamicparam>
//				</actionconfig>
//				<actionconfig command="showdialog" disabled="false" defaultitem="false">
//					<text>Enlarge</text>
//					<configparam name="dialogname">overlay</configparam>
//				</actionconfig>
//				<actionconfig disabled="true">
//					<text>Related Metrics</text>
//				</actionconfig>
//				<actionconfig disabled="true">
//					<text>Queries</text>
//				</actionconfig>
//				<actionconfig command="delete" disabled="false" defaultitem="false">
//					<text>Remove</text>
//					<configparam name="command">removecomponent</configparam>
//					<dynamicparam name="pageid">currentpage.id</dynamicparam>
//					<dynamicparam name="panelid">currentpanel.id</dynamicparam>
//					<dynamicparam name="componentid">currentcomponent.id</dynamicparam>
//				</actionconfig>
//				<actionconfig command="showdialog" disabled="true" defaultitem="false">
//					<text>Change Business Day...</text>
//					<configparam name="dialogname">changebusdlg</configparam>
//					<dynamicparam name="componentid">currentcomponent.id</dynamicparam>
//				</actionconfig>
//				<actionconfig command="showdialog" disabled="true" defaultitem="false">
//					<text>About this Metric...</text>
//					<configparam name="dialogname">helpdialog</configparam>
//					<dynamicparam name="title">currentcomponent.title</dynamicparam>
//					<dynamicparam name="help">currentcomponentmodel.help</dynamicparam>
//				</actionconfig>
//			</actionconfig>;
		
		//action config
		//column config - 
		//processconfig - handled by model
		//path - handled by model
		
		//souce, root, loopback, and skipto are not used with the Flex/Tom Sawyer UI
		
		public function PMNodeConfig(configXML:XML){
			super();
			setValuesByXML(configXML);
		}
		
		public function get id():String{ return _id; }
		public function get title():String{ return _title; }
		public function get x():Number{ return _x; }
		public function get y():Number{ return _y; }
		public function get width():Number{ return _width; }
		public function get height():Number{ return _height; }
		public function get expandable():Boolean{ return _expandable; }
		public function get actionConfig():XML{ return _actionConfig; }
		public function get columnConfig():XMLList{ return _columnConfig; }
		public function get dataActionConfig():XML{ return _dataActionConfig; }
		
		public function set id(value:String):void{ _id = value; }
		public function set title(value:String):void{ _title = value; }
		public function set x(value:Number):void{ _x = value; }
		public function set y(value:Number):void{ _y = value; }
		public function set width(value:Number):void{ _width = value; }
		public function set height(value:Number):void{ _height = value; }
		public function set actionConfig(value:XML):void{ _actionConfig = value; }
		public function set columnConfig(value:XMLList):void{ _columnConfig = value; }
		
		public function setDataActionMode(mode:int):void{
			var colConfig:XML = null;
			if(mode == CONTENT_DATA_ACTION_MODE && _columnConfig != null){
				colConfig = _columnConfig.(@type == "text")[0] as XML;
				if(colConfig == null){
					colConfig = _columnConfig.(@type == "progress")[0] as XML;
				}
			}
			else if(mode == INDICATOR_DATA_ACTION_MODE && _columnConfig != null){
				colConfig = _columnConfig.(@type == "indicator")[0] as XML;
			}
						
			if(colConfig != null && colConfig.actionconfig != undefined){
				_dataActionConfig = colConfig.actionconfig[0] as XML;
			}
			else{
				_dataActionConfig = null;
			}
			
		}
		
		public function update(configXML:XML):void{
			setValuesByXML(configXML);
			dispatchEvent(new Event(CONFIG_UPDATE));
		}
		
		private function setValuesByXML(configXML:XML):void{
			_id = new String(configXML.@activityid);
			_title = new String(configXML.@title);
			_x = configXML.@x;
			_y = configXML.@y;
			_width = configXML.@width;
			_height = configXML.@height;
			_expandable = configXML.processconfig != undefined;
			if(configXML.actionconfig != undefined){
				if(configXML.actionconfig.actionconfig.length() > 0){
					_actionConfig = configXML.actionconfig[0] as XML;
				}
			}
			if(configXML.columnconfig != undefined){
				_columnConfig = configXML.columnconfig;
			}
		}
	}
}