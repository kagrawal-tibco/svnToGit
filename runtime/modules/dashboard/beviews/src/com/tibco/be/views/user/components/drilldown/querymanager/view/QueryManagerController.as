package com.tibco.be.views.user.components.drilldown.querymanager.view{
	
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.events.ValidationResultEvent;

	public class QueryManagerController{
		
		protected var _fieldList:ArrayCollection;
		
		private var _view:QueryManagerMainPanel;
		private var _entityNames:ArrayCollection;
		private var _entNamesIDMap:Dictionary;
		private var _selectedEntityID:String;
		private var _selectedEntityName:String;
		
		public function QueryManagerController(view:QueryManagerMainPanel, queryMgrXML:XML){
			_view = view;
			_fieldList = new ArrayCollection();
			_entityNames = new ArrayCollection();
			_entNamesIDMap = new Dictionary();
			var selectedCmbName:String;
			
			// traverse through the XML and create appropriate editor panels based on data type
			var selectedID:String = queryMgrXML.@selectedentityid;
			_view.conditionEditorList.dataProvider = new ArrayCollection();
			for each(var entity:XML in queryMgrXML.entity){
				//trace("entity name :"+entity.@name);
				// maintain entity name id mapping
				_entNamesIDMap[String(entity.@name)] = String(entity.@id);
				_entityNames.addItem(String(entity.@name));
				if(entity.@id == selectedID){
					_selectedEntityID = new String(entity.@id);
					_selectedEntityName = new String(entity.@name);
					selectedCmbName = String(entity.@name);
					renderFields(entity.field);
				}
			}
			_view.cmbFieldNames.dataProvider = _entityNames;
			if(selectedCmbName != null && selectedCmbName != ""){
				_view.cmbFieldNames.selectedItem = selectedCmbName;
				_view.execute.enabled = true;
				_view.export.enabled = true;	
			}
		}
		
		public function getEntityId(entityName:String):String{
			return _entNamesIDMap[entityName];
		}
		
		public function getValidationResult():ValidationResultEvent {
			var result:Boolean = true;
			for each(var field:ConditionEditor in _fieldList){
				var valResult:ValidationResultEvent = field.doValidation();
				 
				if(valResult != null && valResult.results != null && valResult.results.length != 0) {
					return valResult;
				}
			}
			return null;
		}
		
		public function getQueryMgrModel():XML{
			var qmXML:XML = new XML(<qmgrmdl/>);
			var entityXML:XML = new XML(<entity/>);
			entityXML.@id = _selectedEntityID;
			entityXML.@name = _selectedEntityName;
			qmXML.@selectedentityid = _selectedEntityID;
			for each(var field:ConditionEditor in _fieldList){
				entityXML.appendChild(field.getFieldXML());
			}
			qmXML.appendChild(entityXML);
			//trace("Query mgr model XML: "+qmXML);
			return qmXML;
		}
		
		public function updateFieldsPanel(updatedXML:XML):void{
			// traverse through the XML and create appropriate editor panels based on data type
			var selectedID:String = updatedXML.@selectedentityid;			
			//qmUI.accordion.removeAllChildren();
			_view.conditionEditorList.dataProvider = new ArrayCollection();
			for each(var entity:XML in updatedXML.entity){
				//trace("entity name :"+entity.@name);
				//fieldNames.addItem(String(entity.@name));
				if(entity.@id == selectedID){
					_selectedEntityID = new String(entity.@id);
					_selectedEntityName = new String(entity.@name);					
					renderFields(entity.field);
				}
			}
			if(selectedID!= null && selectedID != ""){
				_view.execute.enabled = true;
				_view.export.enabled = true;
			}
		}
		
		private function renderFields(entityFieldsXML:XMLList):void{
			for each(var item:ConditionEditor in _view.listData){
				var renderer:ConditionEditorListItem = _view.conditionEditorList.itemToItemRenderer(item) as ConditionEditorListItem;
				if(renderer != null){
					renderer.stopEdit();
				}
			}
			_fieldList.removeAll();
			_view.listData.removeAll();
			var count:int = 0;
			for each(var field:XML in entityFieldsXML){
				//trace("field name: "+field.@name);
				var fieldID:String = field.@id;
				var fieldName:String = field.@name;
				var fieldDataType:String = field.@datatype;
				var defaultOperator:String = "Is Any";
				var defaultConditions:ArrayCollection = new ArrayCollection();
				
				// look for condition, not default condition...
				//if(field.condition.length > 0) {
				if(field.hasOwnProperty('condition')){
					var defOp:String = String(field.condition.@operator);
					if(defOp != null && defOp != ""){
						defaultOperator = new String(field.condition.@operator);	
					}
					
					for each(var value:XML in field.condition.value){
						//if(String(value) == "Is Any"){
						if(defOp == "Is Any" || defOp == "Is Null" || defOp =="Is Not Null"){
							defaultConditions.addItem("");
						}
						else{
							defaultConditions.addItem(new String(value));
						}
					}
				}
				else {
					defaultConditions.addItem("");
				}
				// create editor panels based on data type
				//var header:HeaderRenderer = null;
				if(fieldDataType == "boolean"){
					var booleanConditionEditor:ConditionEditor = new NewBooleanEditor();
					//qmUI.accordion.addChild(booleanConditionEditor);
					_view.listData.addItem(booleanConditionEditor);
					booleanConditionEditor.init(fieldID, fieldName, fieldDataType, defaultOperator, defaultConditions);
					//header = HeaderRenderer(qmUI.accordion.getHeaderAt(count++));
					//booleanConditionEditor.headerRenderer = header;
					//header.lblField.text = fieldName;
					//header.lblCondition.text = "  "+defaultOperator+" "+defaultConditions[0];
					booleanConditionEditor.conditionAsString = defaultOperator+" "+defaultConditions[0];
					_fieldList.addItem(booleanConditionEditor);
				}
				if(fieldDataType == "string"){
					var strConditionEditor:ConditionEditor = new NewStringConditionEditor();
					//qmUI.accordion.addChild(strConditionEditor);
					//qmUI.list.addChild(strConditionEditor);
					_view.listData.addItem(strConditionEditor);
					strConditionEditor.init(fieldID, fieldName, fieldDataType, defaultOperator, defaultConditions);				
					//header = HeaderRenderer(qmUI.accordion.getHeaderAt(count++));
					//strConditionEditor.headerRenderer = header;
					//header.lblField.text = fieldName;
					//header.lblCondition.text = "  "+defaultOperator+" "+defaultConditions[0];
					if (defaultConditions.length > 0) {
					 	strConditionEditor.conditionAsString = defaultOperator+" "+defaultConditions[0];
					}
					else {
						strConditionEditor.conditionAsString = defaultOperator;
					}
					_fieldList.addItem(strConditionEditor);
				}
				if(fieldDataType == "int" || fieldDataType == "double" || fieldDataType == "long" || fieldDataType == "float" || fieldDataType == "short"){
					var intConditionEditor:ConditionEditor = new NumericConditionEditor();
					//qmUI.accordion.addChild(intConditionEditor);
					//qmUI.list.addChild(intConditionEditor);
					_view.listData.addItem(intConditionEditor);
					intConditionEditor.init(fieldID, fieldName, fieldDataType, defaultOperator, defaultConditions);							
					//header = HeaderRenderer(qmUI.accordion.getHeaderAt(count++));
					//intConditionEditor.headerRenderer = header;
					//header.lblField.text = fieldName;
					//header.lblCondition.text = "  "+defaultOperator+" "+defaultConditions[0];
					intConditionEditor.conditionAsString = defaultOperator+" "+defaultConditions[0];
					_fieldList.addItem(intConditionEditor);
				}
				if(fieldDataType == "datetime"){
					var dateConditionEditor:ConditionEditor = new DateConditionEditor();
					//qmUI.accordion.addChild(dateConditionEditor);
					//qmUI.list.addChild(dateConditionEditor);
					_view.listData.addItem(dateConditionEditor);
					//header = HeaderRenderer(qmUI.accordion.getHeaderAt(count++));
					//dateConditionEditor.headerRenderer = header;
					dateConditionEditor.init(fieldID, fieldName, fieldDataType, defaultOperator, defaultConditions);
					//header.lblField.text = fieldName;
					//dateConditionEditor.conditionAsString = dateConditionEditor.conditionAsString; 
					_fieldList.addItem(dateConditionEditor);
				}
			}
			_view.conditionEditorList.dataProvider = _view.listData;
		}
	}
}


/*
			var conditionEditor:ConditionEditor = ConditionEditorFactory.create(fieldDataType);
				qmUI.addConditionEditor(conditionEditor);
				conditionEditor.init(fieldID, fieldName, fieldDataType, defaultOperator, defaultConditions);
				if(fieldDataType != "datetime"){
					conditionEditor.conditionAsString = defaultOperator+" "+defaultConditions[0];
				}
				fieldList.addItem(conditionEditor);
				
			}
			qmUI.refresh();
//*/