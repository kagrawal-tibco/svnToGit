package com.tibco.be.views.user.dialogs.businessday{
	
	import flash.events.Event;
	
	import mx.containers.VBox;
	import mx.core.EventPriority;

	public class BusinessDayEditorGrid extends VBox {
		
		private var editorModel:XML;
		
		public function BusinessDayEditorGrid()	{
			super();
			setStyle("horizontalAlign","center");
		}
		
		public function init(editorModel:XML):void {
			this.editorModel = editorModel;
			for each (var seriesbusdaysetting:XML in editorModel.seriesbusdaysetting) {
				var seriesEditorRow:SeriesEditorRow = new SeriesEditorRow();
				seriesEditorRow.seriesEditorModel(seriesbusdaysetting);
				seriesEditorRow.percentWidth = 90;
				seriesEditorRow.addEventListener(Event.CHANGE, seriesEditorRowChangeHandler, false, EventPriority.DEFAULT_HANDLER, true);
				addChild(seriesEditorRow);
			}
		}
		
		override protected function measure():void {
			super.measure();
			height = measuredHeight;
		}
		
		private function seriesEditorRowChangeHandler(event:Event):void {
			dispatchEvent(new Event(Event.CHANGE));
		}
		
		public function get updatedEditorModel():XML {
			var updatedEditorModel:XML = this.editorModel.copy();
			delete updatedEditorModel.comptitle;
			var seriesbusdaysettinglist:XMLList = updatedEditorModel.seriesbusdaysetting;
			var i:int = 0;
			while(i < seriesbusdaysettinglist.length()) {
				var seriesbusdaysetting:XML = seriesbusdaysettinglist[i];
				var seriesEditorRow:SeriesEditorRow = SeriesEditorRow(getChildAt(i));
				var selectedBDayOption:XML = seriesEditorRow.selectedBDayOption;
				if (selectedBDayOption != null) {
					seriesbusdaysetting.@selectedbusdayid = selectedBDayOption.@id;
				}
				delete seriesbusdaysetting.seriestitle;
				for each (var busdayoption:XML in seriesbusdaysetting.busdayoption) {
					delete seriesbusdaysetting.busdayoption;
				}
				i++;
			}
			return updatedEditorModel;
		}
		
	}
}