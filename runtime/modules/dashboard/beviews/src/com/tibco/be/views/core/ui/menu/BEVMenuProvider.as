package com.tibco.be.views.core.ui.menu{
	
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	
	/**
	 * SynMenuProviderImpl is the stand alone mode implementation of the SynMenuProvider. 
	 * It uses the native menu as the menu system 
	 */ 
	public class BEVMenuProvider implements IBEVMenuProvider{
		
		private var _visibleMenus:IList;

		public function init(initParams:Dictionary):void{
			_visibleMenus = new ArrayCollection();
		}
		
		public function getMenuUsingXML(menuConfig:XML, actionCtxProvider:IActionContextProvider, usePersistentMenu:Boolean=false):IBEVMenu{
			var bevMenu:IBEVMenu = new StaticBEVMenu(this, menuConfig, actionCtxProvider, usePersistentMenu);
			_visibleMenus.addItem(bevMenu);
			return bevMenu;
		}
		
		public function getMenuUsingDynamicDefintion(menu:DynamicMenuDefinition, actionCtxProvider:IActionContextProvider):IBEVMenu{
			var bevMenu:IBEVMenu = new DynamicBEVMenu(this, menu, actionCtxProvider);
			_visibleMenus.addItem(bevMenu);
			return bevMenu;
		}
		
		internal function hideAll(menuToSkip:IBEVMenu=null):void{
			var cnt:int = _visibleMenus.length;
			var localList:IList = new ArrayCollection(_visibleMenus.toArray());
			for(var i:int = 0 ; i < cnt ; i ++){
				var menu:IBEVMenu = localList.getItemAt(i) as IBEVMenu;
				if(menu != menuToSkip){
					menu.close();
				}
			}
		}
		
		internal function remove(menu:IBEVMenu):void{
			var idx:int = _visibleMenus.getItemIndex(menu);
			if(idx != -1){
				_visibleMenus.removeItemAt(idx);
			}
		}
	}
}