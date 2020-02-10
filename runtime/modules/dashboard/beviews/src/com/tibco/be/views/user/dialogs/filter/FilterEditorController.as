package com.tibco.be.views.user.dialogs.filter{
	import com.hillelcoren.components.*;
	import com.hillelcoren.components.autoComplete.classes.*;
	import com.hillelcoren.components.autoComplete.interfaces.*;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ConfigResponseEvent;
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.controls.MessageBox;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObjectContainer;
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.controls.ComboBox;
	import mx.controls.TextInput;
	import mx.core.Application;
	import mx.events.CloseEvent;
	import mx.events.FlexEvent;
	import mx.events.ListEvent;
	
	public class FilterEditorController implements IEventBusListener{
		
		private var _view:FilterEditor;
		private var _valueSelectors:Array;
		private var _component:BEVComponent;
		private var _componentId:String;
		private var _seriesConfigId:String;
				
		public function FilterEditorController(view:FilterEditor){
			_view = view;
			_componentId = "";
			_seriesConfigId = "";
			_valueSelectors = [];
		}
		
		public function init(actionContext:ActionContext):void{
			registerListeners();
			_component = actionContext.target as BEVComponent;
			_componentId = actionContext.getDynamicParamValue(DynamicParamsResolver.CURRENTCOMPONENT_ID_PARAM);
			_seriesConfigId = actionContext.getDynamicParamValue(DynamicParamsResolver.CURRENTSERIES_ID_PARAM);
			
			_view.controlButtonPanel.enableApplyButton(false);
			_view.controlButtonPanel.enableApplyToAllButton(false);
			_view.addEventListener(CloseEvent.CLOSE, handleViewClose);
			
			var filterModelRequest:ConfigRequestEvent = new ConfigRequestEvent(ConfigRequestEvent.GET_FILTER_MODEL_COMMAND, this);
			filterModelRequest.addXMLParameter("compid", _componentId);
			filterModelRequest.addXMLParameter("seriescfgid", _seriesConfigId);
			EventBus.instance.dispatchEvent(filterModelRequest);
		}
		
		/**
		 * @param value The filtermdl XML node to use as the filter model.
		*/
		public function set filterData(value:XML):void{
			for each(var dim:XML in value.dim){
				var valSelector:FilterValueSelector = new FilterValueSelector();
				valSelector.itemId = new String(dim.@id);
				valSelector.itemName = new String(dim.@name);
				valSelector.itemDataType = new String(dim.@datatype);
				valSelector.itemInitialValue = new String(dim.@value);	
				
				// Added by Nikhil: put only unique elements in ArrayCollection:
				valSelector.itemValues = new ArrayCollection();
				if(valSelector.itemInitialValue != null && valSelector.itemInitialValue.length > 0) {
					valSelector.itemValues.addItem(valSelector.itemInitialValue);
				}
				if(dim.dataset.@maxsize > 0){
					for each(var val:String in dim.dataset.value){
						val = val.substring(1, val.length-1); //remove quotes from value
						if(val != "" && !valSelector.itemValues.contains(val)) {
							valSelector.itemValues.addItem(val);
						}
					}
				}

				valSelector.addEventListener(FlexEvent.CREATION_COMPLETE, handleValueSelectorCreated);
				_valueSelectors.push(valSelector);
				_view.addChild(valSelector);
				setUIComponent(valSelector,valSelector.itemDataType);				
			}
		}
		private function setUIComponent(fvs:FilterValueSelector, filterDataType:String):void{
			if(filterDataType == "date") {
				var cmbBox:ComboBox = new ComboBox();
				//var cmbBox:ComboBox = new FilterEditorDateDisplay();
				cmbBox.labelField = fvs.itemName;
				cmbBox.dataProvider = fvs.itemValues;
				cmbBox.editable = true;
				cmbBox.text = fvs.itemInitialValue;
				//cmbBox.dataProvider = new ArrayCollection(["2012-02-06T13:36:08.485+0530"]);
				//cmbBox.maxWidth = fvs.width - 6; //reduced from 12
				cmbBox.maxWidth = fvs.width - 12; 
				fvs.addChild(cmbBox);
				fvs.dataComp = cmbBox;
			}
			else if(filterDataType == "numeric") {
				var txtInput:TextInput = new TextInput();
				txtInput.enabled = true;
				txtInput.text = String(fvs.itemInitialValue);
				txtInput.htmlText = String(fvs.itemInitialValue);
				fvs.addChild(txtInput);
				fvs.dataComp = txtInput;
			}
			else if(filterDataType == "string") {
				var autoComplete:AutoCompleteComboBox = new AutoCompleteComboBox();
				autoComplete.dataProvider = fvs.itemValues;
				autoComplete.backspaceAction = "remove";
				autoComplete.matchType = "anyPart";
				autoComplete.allowDuplicates = "false";
				autoComplete.selectedItem = fvs.itemInitialValue;
				autoComplete.allowNewValues = "true";
				autoComplete.allowEditingNewValues = "true";
				autoComplete.actionsMenuDataProvider = fvs.menuData;
				fvs.addChild(autoComplete);
				fvs.dataComp = autoComplete;
			}
			else {
				// unsupported data type warning
				Logger.log(DefaultLogEvent.WARNING, "FilterEditorController.setUIComponent - Unsupported Data Type");
			}
		}
		
		private function handleValueSelectorCreated(event:FlexEvent):void{
			var valSelector:FilterValueSelector = event.target as FilterValueSelector;
			// added by Nikhil on Feb 02 2011: Fix for BE-11030 and BE-10887
			//valSelector.autoComplete.addEventListener(mx.events.ListEvent.CHANGE,enableApplyButtons);	
			valSelector.removeEventListener(FlexEvent.CREATION_COMPLETE, handleValueSelectorCreated);		
			valSelector.dataComp.addEventListener(mx.events.ListEvent.CHANGE, enableApplyButtons);
		}
		
		private function enableApplyButtons(event:Event):void{
			_view.controlButtonPanel.enableApplyButton(true);
			_view.controlButtonPanel.enableApplyToAllButton(true);
			
			//once they're enabled, no point calling enable everytime something changes...
			for each(var fvs:FilterValueSelector in _valueSelectors){
				//fvs.cb_ItemValues.removeEventListener(mx.events.ListEvent.CHANGE, enableApplyButtons);
				// added by Nikhil on Feb 02 2011: Fix for BE-11030 and BE-10887
				//fvs.autoComplete.removeEventListener(mx.events.ListEvent.CHANGE,enableApplyButtons);
				fvs.dataComp.removeEventListener(mx.events.ListEvent.CHANGE, enableApplyButtons);
			}
		}
		
		private function applyToAllClicked():void{
			applyUpdate(true);
		}
		
		private function applyClicked():void{
			applyUpdate();
		}
		
		private function applyUpdate(applyToAll:Boolean=false):void{
			var updateRequest:ConfigRequestEvent = new ConfigRequestEvent(ConfigRequestEvent.UPDATE_FILTER_MODEL_COMMAND, this);
			updateRequest.addXMLParameter("compid", _componentId);
			updateRequest.addXMLParameter("seriescfgid", _seriesConfigId);
			updateRequest.addXMLParameter("applytoall", applyToAll.toString());
			updateRequest.addXMLParameter("filtermodel", buildFilterModelXML());
			EventBus.instance.dispatchEvent(updateRequest);
		}
		
		private function buildFilterModelXML():XML{
			var filterModel:XML = <filtermdl />;
			for each(var valueSelector:FilterValueSelector in _valueSelectors){
				try{
					filterModel.appendChild(
						new XML(
							"<dim id=\""   + valueSelector.itemId + "\"" + 
								"name=\""  + valueSelector.itemName + "\"" +														
								"value=\"" + encodeURIComponent(String(valueSelector.selectedValue)) + "\">" +							
							"</dim>"
						)
					);
				}
				catch(e:Error){
					Logger.log(DefaultLogEvent.WARNING, "Error caught in buildFilterModelXML: " + e.message);
				}
			}
			return filterModel;
		}
		
		private function cancelClicked():void{
			_view.close();	
		}
		
		private function handleViewClose(event:CloseEvent):void{
			_view.removeEventListener(CloseEvent.CLOSE, handleViewClose);
		}
		
		private function handleConfigResponse(event:ConfigResponseEvent):void{
			if(!isRecipient(event)){ return; }
			
			if(event.command == ConfigRequestEvent.GET_FILTER_MODEL_COMMAND){
				if(event.failMessage != ""){
					Logger.log(DefaultLogEvent.WARNING, "ShowFilterEditorAction.handleConfigResponse - " + event.failMessage);
					MessageBox.show(DisplayObjectContainer(Application.application), "Filter Update Failure", "Could not read filter information...");
					return;
				}
				_view.controlButtonPanel.setApplyToAllButtonHandler(applyToAllClicked);
				_view.controlButtonPanel.setApplyButtonHandler(applyClicked);
				_view.controlButtonPanel.setCancelButtonHandler(cancelClicked);
				filterData = event.dataAsXML;
			}
			else if(event.command == ConfigRequestEvent.UPDATE_FILTER_MODEL_COMMAND){
				if(event.failMessage != ""){
					MessageBox.show(_view, "Filter Update Failure", event.failMessage);
					return;
				}
				if(_component != null){
					_component.refreshData();
				}	
				_view.close();
			}
		}
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleConfigResponse, this);
		}
		
		private function unregisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleConfigResponse);
		}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			return true;
		}

	}
}