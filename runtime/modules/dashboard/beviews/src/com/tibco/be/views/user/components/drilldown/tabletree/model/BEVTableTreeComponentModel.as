package com.tibco.be.views.user.components.drilldown.tabletree.model{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.drilldown.tabletree.view.RowViewTypes;
	import com.tibco.be.views.utils.Logger;
	
	import flash.utils.Dictionary;
	
	public class BEVTableTreeComponentModel{
		
		public static function pathEscape(path:String):String{
			return path.replace("/", "//").replace("=", "//");
		}
		
		protected var _rootRow:TableTreeRowNode;
		
		public function BEVTableTreeComponentModel(){
			
		}
		
		public function get rootRow():TableTreeRowNode{ return _rootRow; }
		
		public function setData(data:XML):void{
			//root node is a dummy node to serve as the root of the tree of TableTreeRowNodes
			_rootRow = new RootRowNode();
			
			for each(var txtModelXml:XML in data.textmodel){
				var typeRow:TableTreeRowNode = TableTreeRowNodeFactory.createRowNode(txtModelXml, _rootRow);
				_rootRow.addChild(typeRow);
				addExpansionRowsTo(typeRow, txtModelXml);
			}
		}
		
		public function addExpansionRowsTo(parentRowNode:TableTreeRowNode, data:XML):Array{
			var rows:Array = [];
			var actionConfig:XML;
			if(data.actionconfig != undefined && data.actionconfig.length() > 0){
				actionConfig = new XML(data.actionconfig[0]);
			}
			for each(var row:XML in data.visualizationdata.datarow){
				//var childRow:TableTreeRowNode = new TableTreeRowNode(row, parentRowNode, actionConfig);
				var childRow:TableTreeRowNode = TableTreeRowNodeFactory.createRowNode(row, parentRowNode, actionConfig);
				if(!RowViewTypes.isValidVisualType(childRow.visualType)){
					Logger.log(
						DefaultLogEvent.DEBUG,
						"BEVTableTreeComponentModel.addExpansionRowsTo - Skipping data creation for row with invalid type: " + childRow.visualType
					);
					continue;
				}
				childRow.addChildrenFromData(row.datarow);
				parentRowNode.addChild(childRow);
				rows.push(childRow);
			}
			return rows;
		}
		
		public function removeChildrenOf(parentRowNode:TableTreeRowNode):void{
			parentRowNode.removeAllChildren();
		}

	}
}