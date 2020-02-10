package com.tibco.be.views.user.components.chart{
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	
	import flash.events.Event;
	
	import mx.controls.TextArea;
	import mx.events.FlexEvent;
	
	
	public class DummyComponent extends BEVComponent{
		
		private var _config:TextArea;
		private var _data:TextArea;
		private var _updateCount:int;
		
		public function DummyComponent(){
			super();
			_config = new TextArea();
			_data = new TextArea();
			_updateCount = 0;
			addEventListener(FlexEvent.CREATION_COMPLETE, onCreationComplete);
		}
		
		override protected function handleConfigSet(configXML:XML):void{
			_data.y = this.height/2;
			_config.height = this.height/2;
			_data.height = this.height/2;
			_config.percentWidth = 100;
			_data.percentWidth = 100;
			addChild(_config);
			addChild(_data);
			_config.text = new String(configXML.toXMLString());
//			trace(configXML);
		}
		
		override protected function handleDataSet(dataXML:XML):void{
			_data.text = new String(dataXML.toXMLString());
//			delete dataXML.datarow;
//			trace(dataXML);
			dispatchEvent( new Event(FlexEvent.UPDATE_COMPLETE) );
		}
		
		override public function updateData(componentData:XML):void{
			_data.text = 
				"Update #: " + (++_updateCount) + "\n" +
				"Queue Size: " + _updateQueue.length;
//			trace(componentData);
//			delete componentData.datarow;
		}
		
		private function onCreationComplete(event:Event):void{
			_config.addEventListener(FlexEvent.UPDATE_COMPLETE, myOnUpdateComplete);
			_data.addEventListener(FlexEvent.UPDATE_COMPLETE, myOnUpdateComplete);
		}
		
		private function myOnUpdateComplete(event:Event):void{
			this.dispatchEvent(new FlexEvent(FlexEvent.UPDATE_COMPLETE));
		}
		
	}
}