package com.tibco.be.views.user.components.drilldown.tabletree.model{

	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.utils.BEVUtils;
	import com.tibco.be.views.utils.Logger;
	
	import mx.collections.ArrayCollection;
	
	public class TypeHeaderRowNode extends TableTreeRowNode{
		
		public static const NO_GROUPBY_STRING:String = "";
		
		[Bindable]
		protected var _groupByList:ArrayCollection;
		
		protected var _totalRowsAvailable:int;
		
		public function TypeHeaderRowNode(datarow:XML, parent:TableTreeRowNode, actionConfig:XML=null){
			super(datarow, parent, actionConfig);
			_groupByList = new ArrayCollection();
			
			var textModel:XML = getRootTextModel(datarow);
			if(textModel == null || textModel.actionconfig == undefined || textModel.actionconfig.actionconfig == undefined){
				//this is ok and will occur regularly once a user has used up all group-by values
				Logger.log(DefaultLogEvent.DEBUG, BEVUtils.getClassName(this) + ".CONSTRUCTOR - Undefined actionconfig for row " + String(datarow.name()));
				return;
			}
			_totalRowsAvailable = -1;
			parseGroupByList(textModel);
			parsePaginationData(textModel);
		}
		
		public function get groupByItems():ArrayCollection{ return _groupByList; }
		public function get totalRowsAvailable():int{ return _totalRowsAvailable; } 
		
		private function getRootTextModel(startingXMLNode:XML):XML{
			var rootXML:XML = startingXMLNode;
			if(String(startingXMLNode.name()) == "drilldownmodel"){
				if(startingXMLNode.textmodel == undefined){
					return <empty />;
				}
				return startingXMLNode.textmodel[0] as XML;
			}
			while(rootXML as XML){
				if(String(rootXML.name()) == "textmodel"){ break; }
				rootXML = rootXML.parent() as XML;
			}
			return rootXML == null ? <empty />:rootXML;
		}
		
		private function parseGroupByList(textModel:XML):void{
			_groupByList.addItem(NO_GROUPBY_STRING);
			var groupByFieldXml:XML;
			var groupByActionConfigs:XMLList = textModel.actionconfig.actionconfig.(text == "GROUPBY");
			if(groupByActionConfigs.length() == 0){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".parseGroupByList - Empty Group-By action config.");
			}
			else if(groupByActionConfigs.length() == 1){
				for each(groupByFieldXml in groupByActionConfigs[0].actionconfig){
					_groupByList.addItem(new String(groupByFieldXml.text));
				}
			}
			else{
				for each(var groupByXml:XML in groupByActionConfigs){
					if(String(groupByXml.parent().@qualifier) != _id){ continue; }
					for each(groupByFieldXml in groupByXml.actionconfig){
						_groupByList.addItem(new String(groupByFieldXml.text));
					}
					break;
				}
			}
		}
		
		private function parsePaginationData(textModel:XML):void{
			//leave _totalRowsAvailable as is if no textconfig node is defined
			if(textModel.textconfig != undefined){
				_totalRowsAvailable = -1;
				var txtCfg:XML = textModel.textconfig[0] as XML;
				if(txtCfg != null){
					_totalRowsAvailable = parseInt(txtCfg.@maxrows);
					if(isNaN(_totalRowsAvailable)){ _totalRowsAvailable = -1; }
				}
			}
		}
		
		public function updatePaginationData(textModel:XML):void{
			parsePaginationData(textModel);
		}
		
	}
}