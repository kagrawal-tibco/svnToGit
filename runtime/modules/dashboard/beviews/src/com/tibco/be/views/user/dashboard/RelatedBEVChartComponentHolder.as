package com.tibco.be.views.user.dashboard{
	
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.menu.IBEVMenu;
	import com.tibco.be.views.user.dialogs.IPopupWindow;
	
	import flash.events.MouseEvent;
	
	public class RelatedBEVChartComponentHolder extends BEVChartComponentHolder implements IPopupWindow{
		
		private var _openChartMenu:IBEVMenu;
		
		public function RelatedBEVChartComponentHolder(component:BEVComponent){
			super();
			_component = component;
		}
		
		override protected function init():void{
			super.init();
			vb_ComponentSpace.styleName = "BEVChartComponentHolderContentHolderRelated";
		}
		
		override protected function addHeaderButtons():void{
			//no buttons
		}
		
		override public function setComponentInteractions(actionConfig:XML):void{
			//do nothing
		}
		
		override public function getActionContext(parentActionConfig:XML, actionConfig:XML):ActionContext{
			return null;
		}
		
		override protected function handleDragStart(event:MouseEvent):void{
			//dragging not allowed for related chart display
		}
		
		override public function showMenu(x:int, y:int, menuDefinition:XML, actionCtxProvider:IActionContextProvider):void{
			//since we're avoiding closing the component menu in our show call, we need to close any menus we may have already opened
			if(_openChartMenu != null){
				_openChartMenu.close();
			}
			_openChartMenu = Kernel.instance.uimediator.menuprovider.getMenuUsingXML(menuDefinition, actionCtxProvider);
			_openChartMenu.show(x, y, false);
		}
		
		public function closingWindow():void{
			
		}
		
	}
}