package com.tibco.be.views.user.components.drilldown.tabletree.model{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.chart.DataColumn;
	import com.tibco.be.views.utils.BEVUtils;
	import com.tibco.be.views.utils.Logger;
	
	import flash.utils.Dictionary;
	
	public class TableTreeRowNode{
		
		protected var _id:String;
		protected var _title:String;
		protected var _templateId:String;
		protected var _templateType:String;
		protected var _visualType:String;
		protected var _link:String;
		protected var _actionConfig:XML;
		protected var _dataColumns:Dictionary;
		protected var _path:String;
		protected var _parent:TableTreeRowNode;
		protected var _children:Array;
		
		public function TableTreeRowNode(datarow:XML, parent:TableTreeRowNode, actionConfig:XML=null){
			if(datarow.name() == "textmodel"){
				_id = new String(datarow.textconfig.@componentid);
				_title = new String(datarow.@componentname);
				_templateId = "1";
				_templateType = "header";
				_visualType = "TypeRow";
				_dataColumns = null;
			}
			else{
				_id = new String(datarow.@id);
				_title = "";
				_templateId = new String(datarow.@templateid);
				_templateType = new String(datarow.@templatetype);
				_visualType = new String(datarow.@visualtype);
				if(datarow.link != undefined){
					_link = new String(datarow.link[0]);
				}
				_dataColumns = parseDataColumns(datarow.datacolumn);
			}
			if(actionConfig != null){
				_actionConfig = actionConfig;
			}
			else if(datarow.actionconfig != undefined){
				_actionConfig = new XML(datarow.actionconfig[0]);
			}
			else{
				_actionConfig = <empty />;
			}
			_path = parent == null ? _id:parent.path + "/" + _id;
			while(_path.charAt(0) == "/"){ _path = _path.substr(1); }
			_parent = parent;
			_children = [];
		}
		
		public function get id():String{ return _id; }
		public function get title():String{ return _title; }
		public function get templateId():String{ return _templateId; }
		public function get templateType():String{ return _templateType; }
		public function get visualType():String{ return _visualType; }
		public function get link():String{ return _link; }
		public function get actionConfig():XML{ return _actionConfig; }
		public function get dataColumns():Dictionary{ return _dataColumns; }
		public function get path():String{ return _path; }
		public function get parent():TableTreeRowNode{ return _parent; }
		public function get children():Array{ return _children; }
		public function get hasSiblings():Boolean{ return _parent != null && _parent.children.length > 1; }
		public function get hasYoungerSiblings():Boolean{ return hasSiblings && _parent.children.indexOf(this) < _parent.children.length -1; }
		
		public function set parent(value:TableTreeRowNode):void{ _parent = value; }
		public function set children(value:Array):void{ _children = value; }
		
		public function getChildAt(i:int):TableTreeRowNode{
			if(i < 0 || i >= _children.length){
				return null;
			}
			return _children[i] as TableTreeRowNode;
		}
		
		public function addChild(child:TableTreeRowNode, position:int=-1):void{
			if(child != null){
				if(position > -1 && position < _children.length){
					_children.splice(position, 0, child);
				}
				else{
					_children.push(child);
				}
			}
		}
		
		public function addChildrenFromData(dataRows:XMLList):void{
			if(dataRows == null){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".addChildrenFromData - null data xmllist");
				return;
			}
			for each(var childRowXML:XML in dataRows){
				var actionConfig:XML;
				if(childRowXML.actionconfig != undefined && childRowXML.actionconfig.length() > 0){
					actionConfig = new XML(childRowXML.actionconfig[0]);
				}
				var childRow:TableTreeRowNode = new TableTreeRowNode(childRowXML, this, actionConfig);
				childRow.addChildrenFromData(childRowXML.datarow);
				addChild(childRow);
			}
		}
		
		public function removeChildNode(node:TableTreeRowNode):void{
			if(_children == null || _children.length == 0){ return; }
			var iDelete:int = _children.indexOf(node);
			if(iDelete >=0){ _children.splice(iDelete, 1); }
		}
		
		public function removeAllChildren():void{
			if(_children != null){
				_children.splice(0, _children.length)
			}
		}
		
		private function parseDataColumns(columns:XMLList):Dictionary{
			var parsedCols:Dictionary = new Dictionary();
			if(columns == null){ return parsedCols; }
			for each(var col:XML in columns){
				var dc:DataColumn = new DataColumn(col);
				parsedCols[dc.colID] = dc;
			}
			return parsedCols;
		}

	}
}