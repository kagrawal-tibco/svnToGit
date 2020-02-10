package com.tibco.cep.ui.monitor.panes.table{
	
	import mx.controls.advancedDataGridClasses.AdvancedDataGridItemRenderer;
	import mx.events.ToolTipEvent;
	
	public class CachedObjectTableItemRenderer extends AdvancedDataGridItemRenderer{
		
		public function CachedObjectTableItemRenderer(){
			super();
		}
		
		override protected function toolTipShowHandler(event:ToolTipEvent):void{
			if(listData.hasOwnProperty("dataField") && data.hasOwnProperty(listData["dataField"])){
				if(data[listData["dataField"]] == "-"){
					event.toolTip.text = "Not Available"
				}
				else{
					event.toolTip.text = data[listData['dataField']];
				}
			}
			else{
				event.toolTip.text = null;
			}
			super.toolTipShowHandler(event);
		}
		
	}
}