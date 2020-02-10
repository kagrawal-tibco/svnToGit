package com.tibco.be.views.user.components.pagesetselector{
	
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.ActionRegistry;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.ui.containers.BEVDividedPartition;
	import com.tibco.be.views.ui.containers.ButtonScrollList;
	import com.tibco.be.views.user.dashboard.AbstractBEVPanel;
	
	import mx.core.ClassFactory;
	import mx.core.Container;
	import mx.core.ScrollPolicy;
	import mx.events.FlexEvent;
	import mx.events.ListEvent;

	public class BEVPageSetSelectorComponent extends BEVComponent{
		
		private static const SWITCH_PAGESET_CONFIG:XML = new XML("<actionconfig command=\""+CommandTypes.SWITCH_PAGESET+"\"/>");
		
		protected var _internalList:ButtonScrollList;
		protected var _columnconfigs:Array;
		protected var _psDataProvider:BEVPageSetSelectorDataProvider;
		protected var _lastSelectedPagesetId:String;
		
		public function BEVPageSetSelectorComponent():void{
			super();
			horizontalScrollPolicy = ScrollPolicy.OFF;
			if(componentContainer as AbstractBEVPanel){
				(componentContainer as AbstractBEVPanel).styleName = AbstractBEVPanel.HEADER_DISABLED_PANEL_STYLE;
			}
			addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
		}
		
		override public function set percentWidth(value:Number):void{
			super.percentWidth = value;
		}
		
		override public function set width(value:Number):void{
			super.width = value;
		}
		
		/**
		 * Handles component config changes. Sub classes should over ride this 
		 * method to parse and handle the component configuration. 
		 * @param oldConfig Represents the configuration of the component before the change, can be null
		 */ 
		override protected function handleConfigSet(configXML:XML):void{
			if(_internalList != null){
				removeChild(_internalList);
				_internalList = null;
			}
			_internalList = new ButtonScrollList();
			_internalList.percentWidth = 100;
			_internalList.percentHeight = 100;
			
			var pagesetrowconfigs:XMLList = configXML..pagesetrowconfig;
			_columnconfigs = extractColumnConfigs(pagesetrowconfigs);
			//configure the list
			configureList(configXML.pagesetselectorconfig[0]);
			//configure data provider
			configureDataProvider(pagesetrowconfigs);
			_internalList.dataProvider = _psDataProvider;
			configureEventListeners();
			updateSelection(Kernel.instance.uimediator.uicontroller.currentPageSetId, configXML);
			addChild(_internalList);
		}
		
		protected function extractColumnConfigs(pagesetrowconfigs:XMLList):Array {
			var columnConfigs:Array = new Array(null,null);
			for each (var pagesetrowconfig:XML in pagesetrowconfigs){
				for each (var columnconfig:XML in pagesetrowconfig.columnconfig){
					var idx:Number = new Number(columnconfig.@id) - 1;
					if(columnConfigs[idx] == null){
						columnConfigs[idx] = columnconfig;
					}
					else{
						if(parseFloat(columnconfig.@width) > parseFloat(columnConfigs[idx].@width)){
							columnConfigs[idx] = columnconfig;
						}
					}
				}
			}
			return columnConfigs;
		}
		
		protected function configureList(listConfig:XML):void{
			//set the item renderer
//			var itemRendererFactory:ClassFactory = new ClassFactory(PageSetRowRenderer);
//			itemRendererFactory.properties = {columnConfigs:_columnconfigs};
//			_internalList.setListItemRenderer(itemRendererFactory);
			_internalList.setListItemRenderer(new ClassFactory(BasicPageSetRowRenderer));
		}	
		
		protected function configureDataProvider(pagesetrowconfigs:XMLList):void{
			_psDataProvider = new BEVPageSetSelectorDataProvider(pagesetrowconfigs);
		}			
		
		/**
		 * Handles component data changes. Sub classes should over ride this 
		 * method to parse and handle the component data. 
		 * @param oldData Represents the data of the component before the change, can be null
		 */ 
		override protected function handleDataSet(dataXML:XML):void{
			_psDataProvider.setData(dataXML);
		}
		
		/**
		 * Handles component data updates. Sub classes should over ride this 
		 * method to parse and handle the component data update 
		 * @param componentData Represents the update data of the component 
		 */ 
		override public function updateData(componentData:XML):void{
			_psDataProvider.updateData(componentData);
		}
		
		protected function configureEventListeners():void{
			_internalList.addListEventListener(ListEvent.ITEM_CLICK, itemClickHandler);
		}
		
		protected function updateSelection(currentPageSetId:String, configXML:XML):void{
			var pagesetrowconfigs:XMLList = configXML..pagesetrowconfig;
			var idx:int = 0; 
			for each(var pagesetrowconfig:XML in pagesetrowconfigs){
				if(pagesetrowconfig.@pagesetid == currentPageSetId){
					_internalList.selectedIndex = idx;
					break;
				}
				idx++;
			}
		}	
		
		protected function itemClickHandler(event:ListEvent):void{
			var pageSetId:String = _internalList.selectedItem.id;
			var pageSetName:String = _internalList.selectedItem.title;
			
			if(pageSetId == _lastSelectedPagesetId){ return; }
			
			var dynamicResolver:DynamicParamsResolver = new DynamicParamsResolver();
			dynamicResolver.setDynamicParamValue("new.pagesetid",pageSetId); 
			dynamicResolver.setDynamicParamValue("new.pagesetname",pageSetName);
			ActionRegistry.instance.getAction(SWITCH_PAGESET_CONFIG).execute(new ActionContext(this, dynamicResolver));
			_lastSelectedPagesetId = pageSetId;
		}
		
		private function handleCreationComplete(event:FlexEvent):void{
			var parentComp:Container = this;
			for( ; !(parentComp is BEVDividedPartition); parentComp = parentComp.parent as Container){
				parentComp.horizontalScrollPolicy = ScrollPolicy.OFF;
			}
		}								
	}
}