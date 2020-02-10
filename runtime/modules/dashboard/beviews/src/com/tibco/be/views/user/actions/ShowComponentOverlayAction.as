package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.dashboard.BEVComponentFactory;
	import com.tibco.be.views.user.dashboard.AbstractBEVPanel;
	import com.tibco.be.views.user.dashboard.OverlayBEVChartComponentHolder;
	import com.tibco.be.views.user.utils.UserUtils;
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.geom.Point;

	public class ShowComponentOverlayAction extends AbstractAction{
		
		public function ShowComponentOverlayAction(){
			registerAction(CommandTypes.SHOW_DIALOG, "dialogname","overlay");
		}
		
		override public function execute(actionContext:ActionContext):void{
			var triggeringComponent:BEVComponent = actionContext.target as BEVComponent;
			var panel:AbstractBEVPanel = triggeringComponent.componentContainer as AbstractBEVPanel;
			var component:BEVComponent = BEVComponentFactory.instance.getComponentByXML(triggeringComponent.componentDefinition);
			var overlayComponentHolder:OverlayBEVChartComponentHolder = new OverlayBEVChartComponentHolder(component);
			var containerToOpenIn:DisplayObjectContainer = triggeringComponent.parent.parent;
			
			component.componentContext = overlayComponentHolder;
			overlayComponentHolder.width = containerToOpenIn.width*1.5;
			overlayComponentHolder.height = containerToOpenIn.height*1.5;
			Kernel.instance.uimediator.uicontroller.openWindow(overlayComponentHolder, DisplayObject(UserUtils.getParentDashboard(panel)), false)
			
			var pnt:Point = containerToOpenIn.localToGlobal(new Point(0, 0));
			overlayComponentHolder.move(pnt.x, pnt.y);
			component.init();
		}
		
		override protected function createNewInstance():AbstractAction{
			return new ShowComponentOverlayAction();
		}			
	}
}