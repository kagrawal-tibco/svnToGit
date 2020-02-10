package com.tibco.be.views.core.ui.menu{
	
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	
	import mx.collections.IList;

	/**
	 * DynamicSynMenuImpl is a concrete implementation of SynMenu for stand alone mode. 
	 * DynamicSynMenuImpl understand the dynamic menu definition object. DynamicSynMenuImpl 
	 * is meant for use for dynamic menus with dynamic/predefined actions.  
	 */ 
	public class DynamicBEVMenu extends AbstractBEVMenu{
		
		private var _menuDefinition:DynamicMenuDefinition;

		function DynamicBEVMenu(synMenuProviderImpl:BEVMenuProvider, menuDefinition:DynamicMenuDefinition, actionContextProvider:IActionContextProvider){
			super(synMenuProviderImpl, menuDefinition, actionContextProvider);
		}
		
		override protected function parseMenuDefinitionObj(menuDefinitionObj:*):XML{
			_menuDefinition = menuDefinitionObj as DynamicMenuDefinition;
			var xmlStr:String = "<ROOT>";
			parseAndPrepMenuConfigXML(_menuDefinition.childMenus,xmlStr, "1");
			xmlStr += "</ROOT>";
		 	return new XML(xmlStr);
		}		
		
		private function parseAndPrepMenuConfigXML(childMenus:IList,xmlStr:String,id:String):void{
			var cnt:int = childMenus.length;
			
			for(var i:int = 0 ; i < cnt ; i++ ){
				var childMenu:DynamicMenuDefinition = childMenus.getItemAt(i) as DynamicMenuDefinition;
				var childID:String = id + "." + i ;
				if(childMenu.childMenus.length > 0){
					xmlStr += "<menuitem label='" + childMenu.text + "' id='" + id + "' enabled='" + childMenu.enabled + "'>";
				}
				else{
					xmlStr += "<menuitem label='" + childMenu.text + "' id='" + id + "' enabled='" + childMenu.enabled + "'/>";
				}
				registerAction(childID,childMenu);
				if(childMenu.childMenus.length > 0){
					parseAndPrepMenuConfigXML(childMenu.childMenus, xmlStr, childID);
					xmlStr += "</menuitem>";
				}
				
			}
		}
		 
	}
}