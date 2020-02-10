package com.tibco.be.views.user.dialogs.businessday{
	import flash.events.Event;
	
	import mx.controls.ComboBox;
	import mx.controls.Label;
	import mx.core.EventPriority;
	import mx.core.UIComponent;
	import mx.events.ListEvent;

	public class SeriesEditorRow extends UIComponent {
		
		private var seriesNameLbl:Label;
		private var seriesBDayComboBox:ComboBox;
		private var seriesBDayNotAvailableLbl:Label;

		private var _seriesEditorRowModel:XML;		
		private var _selectedBDayOption:XML;
		
		public function SeriesEditorRow() {
			super();
			setStyle("backgroundColor",0xB8BDC3);
		}
		
		public function seriesEditorModel (seriesEditorModel:XML):void {
			_seriesEditorRowModel = seriesEditorModel;
		}
		
		override protected function createChildren():void {
			if (seriesNameLbl == null) {
				seriesNameLbl = new Label();
				seriesNameLbl.truncateToFit = true;
				seriesNameLbl.text = String(_seriesEditorRowModel.seriestitle);
				addChild(seriesNameLbl);
			}
			if (_seriesEditorRowModel.busdayoption.length() > 0) {
				if (seriesBDayComboBox == null) {
					seriesBDayComboBox = new ComboBox();
					seriesBDayComboBox.dataProvider = _seriesEditorRowModel.busdayoption;
					seriesBDayComboBox.labelField = "@name";
					_selectedBDayOption = _seriesEditorRowModel.busdayoption.(@id == _seriesEditorRowModel.@selectedbusdayid)[0];
					seriesBDayComboBox.selectedItem = _selectedBDayOption;
					seriesBDayComboBox.addEventListener(ListEvent.CHANGE, seriesBDayComboBoxChangeHandler, false, EventPriority.DEFAULT_HANDLER, true);
					addChild(seriesBDayComboBox);
				}
			}
			else{
				if (seriesBDayNotAvailableLbl == null) {
					seriesBDayNotAvailableLbl = new Label();
					seriesBDayNotAvailableLbl.truncateToFit = true;
					seriesBDayNotAvailableLbl.text = "Not enabled";
					seriesBDayNotAvailableLbl.setStyle("textAlign","center");
					seriesBDayNotAvailableLbl.setStyle("fontStyle","italic");
					seriesBDayNotAvailableLbl.setStyle("fontWeight","bold");
					addChild(seriesBDayNotAvailableLbl);					
				}
			}
		}
		
		private function seriesBDayComboBoxChangeHandler(event:ListEvent):void {
			_selectedBDayOption = XML(seriesBDayComboBox.selectedItem);
			dispatchEvent(new Event(Event.CHANGE));
		}
		
		public function get selectedBDayOption():XML {
			return _selectedBDayOption;
		}
		
		override protected function measure():void {
			super.measure();
			this.height = 30;
			this.minHeight = 30;
			this.explicitHeight = 30;
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			//draw the oval rectangle
			graphics.clear();
			graphics.beginFill(getStyle("backgroundColor"));
			graphics.drawRoundRect(0,0,unscaledWidth,unscaledHeight,unscaledHeight,unscaledHeight);
			
			seriesNameLbl.width = 0.50 * unscaledWidth;
			seriesNameLbl.height = seriesNameLbl.textHeight;			
			seriesNameLbl.move(unscaledHeight , (unscaledHeight - seriesNameLbl.height)/2);
			
			if (seriesBDayComboBox != null) {
				seriesBDayComboBox.width = 0.40 * unscaledWidth;
				seriesBDayComboBox.height = 0.75 * unscaledHeight;
				seriesBDayComboBox.move(unscaledWidth - unscaledHeight - seriesBDayComboBox.width, (unscaledHeight - seriesBDayComboBox.height)/2);
			}
			else if (seriesBDayNotAvailableLbl != null) {
				seriesBDayNotAvailableLbl.width = 0.40 * unscaledWidth;
				seriesBDayNotAvailableLbl.height = seriesBDayNotAvailableLbl.textHeight;			
				seriesBDayNotAvailableLbl.move(unscaledWidth - unscaledHeight - seriesBDayNotAvailableLbl.width, (unscaledHeight - seriesBDayNotAvailableLbl.height)/2);	
			}
		}
	}
}