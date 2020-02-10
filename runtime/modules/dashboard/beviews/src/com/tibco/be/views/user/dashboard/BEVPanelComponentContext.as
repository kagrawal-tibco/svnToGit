package com.tibco.be.views.user.dashboard {
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.IBEVTooltip;
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.dashboard.IBEVComponentContext;
	import com.tibco.be.views.core.ui.dashboard.IBEVContainer;
	import com.tibco.be.views.core.ui.menu.DynamicMenuDefinition;
	import com.tibco.be.views.core.ui.menu.IBEVMenu;
	import com.tibco.be.views.user.utils.UserUtils;
	import com.tibco.be.views.utils.Logger;

	internal class BEVPanelComponentContext implements IBEVComponentContext, IActionContextProvider {
		
		private var panel:EmbeddedBEVPanel;
		
		private var component:BEVComponent;
		
		function BEVPanelComponentContext(panel:EmbeddedBEVPanel, component: BEVComponent) {
			this.panel = panel;
			this.component = component;
		}

		public function showMenu(x:int, y:int, menuDefinition:XML, actionCtxProvider:IActionContextProvider):void {
			var menu:IBEVMenu = Kernel.instance.uimediator.menuprovider.getMenuUsingXML(menuDefinition, actionCtxProvider);
			menu.show(x,y);
		}
		
		public function showDynamicMenu(x:int, y:int, menuDefinition:DynamicMenuDefinition, actionCtxProvider:IActionContextProvider):void {
			var menu:IBEVMenu = Kernel.instance.uimediator.menuprovider.getMenuUsingDynamicDefintion(menuDefinition, actionCtxProvider);
			menu.show(x,y);				
		}
		
		public function showTooltip(x:int, y:int, tooltip:String):void {
			var tooltipHolder:IBEVTooltip = Kernel.instance.uimediator.tooltipprovider.getTooltip(tooltip);
			tooltipHolder.show(x,y);			
		}
		
		public function log(logLevel:Number, logMessage:String):void {
			Logger.log(DefaultLogEvent.INFO, "BEVPanelComponentContext - (" + logLevel + ") " + logMessage);
		}
		
		public function handleException(logLevel:Number, exceptionMessage:String, exception:Error):void {
			Logger.log(DefaultLogEvent.WARNING, "BEVPanelComponentContext - " + exceptionMessage);
			Logger.log(DefaultLogEvent.DEBUG, exception.getStackTrace());			
		}
		
		public function getComponentByName(name:String, searchPeers:Boolean):BEVComponent {
			return searchComponentByName(name, panel, searchPeers);					
		}
		
		private function searchComponentByName(name:String, container:IBEVContainer, searchPeers:Boolean):BEVComponent{
			var comp:BEVComponent = container.getComponentByName(name);
			if(searchPeers || comp != null){ return comp; }
			for each(var childContainer: IBEVContainer in container.childContainers){
				comp = childContainer.getComponentByName(name);
				if(comp != null){ return comp; }
			}
			var parentContainer:IBEVContainer = container.containerParent;
			if(parentContainer == null){ return null; }
			return searchComponentByName(name, parentContainer, false);				
		}		
		
		public function setComponentInteractions(actionConfig:XML):void {
			panel.setInteractions(actionConfig, this);
		}
		
		public function getActionContext(parentActionConfig:XML, actionConfig:XML):ActionContext {
		 	var resolvingMap:DynamicParamsResolver = new DynamicParamsResolver();
		 	resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTCOMPONENT_ID_PARAM, component.componentId);
		 	resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTPANEL_ID_PARAM, panel.containerId);
		 	resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTPAGE_ID_PARAM, UserUtils.getParentDashboard(panel).containerId);
		 	resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTCOMPONENT_TITLE_PARAM, component.componentTitle);
		 	resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTCOMPONENT_HELP_PARAM, component.componentHelp);
		 	return new ActionContext(component, resolvingMap);			
		}
		
	}
}