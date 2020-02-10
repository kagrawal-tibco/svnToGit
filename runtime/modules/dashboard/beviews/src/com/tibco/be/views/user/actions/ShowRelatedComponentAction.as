package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.dashboard.BEVComponentFactory;
	import com.tibco.be.views.user.dashboard.AbstractBEVPanel;
	import com.tibco.be.views.user.dashboard.RelatedBEVChartComponentHolder;
	import com.tibco.be.views.user.utils.UserUtils;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.core.Application;
	import mx.events.ListEvent;
	import mx.events.MenuEvent;

	public class ShowRelatedComponentAction extends AbstractAction implements IStoppableAction{
		
		private var _relatedComponentHolder:RelatedBEVChartComponentHolder;
		
		public function ShowRelatedComponentAction(){
			registerAction(CommandTypes.LAUNCH_COMPONENT, null,null);
		}
		
		override public function execute(actionContext:ActionContext):void{
			var triggeringComponent:BEVComponent = actionContext.target as BEVComponent;
			var panel:AbstractBEVPanel = triggeringComponent.componentContainer as AbstractBEVPanel;
			var compid:String = _params["componentid"] as String;
			var compname:String = _params["name"] as String;
			var comptitle:String = _params["title"] as String;
			var comptype:String = _params["componenttype"] as String;
			var rowspan:int = parseInt(_params["rowspan"] as String);
			var colspan:int = parseInt(_params["colspan"] as String);
			var width:int = parseInt(_params["width"] as String);
			var height:int = parseInt(_params["height"] as String);
			//remove all existing Related Component Windows
			var popupChildCnt:int = Application.application.systemManager.popUpChildren.numChildren;
			var relatedComponents:IList = new ArrayCollection();
			for(var i:int = 0 ; i < popupChildCnt ; i++){
				var popupChild:DisplayObject = Application.application.systemManager.popUpChildren.getChildAt(i);
				if(popupChild is RelatedBEVChartComponentHolder){
					relatedComponents.addItem(popupChild);
				}
			}
			var relatedComponentsCnt:int = relatedComponents.length;
			for(var j:int = 0 ; j < relatedComponentsCnt; j++){
				Application.application.systemManager.popUpChildren.removeChild(relatedComponents.getItemAt(j));
			}
			relatedComponents = null;
			var component:BEVComponent = BEVComponentFactory.instance.getComponent(compid, compname, comptitle, comptype, new Rectangle(0,0,width,height));
			_relatedComponentHolder = new RelatedBEVChartComponentHolder(component);
			component.componentContext = _relatedComponentHolder;
			_relatedComponentHolder.width = width;// * colspan;
			_relatedComponentHolder.height = height;// * rowspan;
			Kernel.instance.uimediator.uicontroller.openWindow(_relatedComponentHolder, DisplayObject(UserUtils.getParentDashboard(panel)), false);
			moveWindow(actionContext);
			component.init();
		}
		
		private function moveWindow(actionContext:ActionContext):void{
			var x:Number = actionContext.bounds.x + actionContext.bounds.width - 1;
			if(Application.application.width < (x + _relatedComponentHolder.width)){
				x = actionContext.bounds.x - _relatedComponentHolder.width + 1;
			}
			var y:Number = actionContext.bounds.y;
			if(Application.application.height < (y + _relatedComponentHolder.height)){
				y = Application.application.height - _relatedComponentHolder.height;
			}
			_relatedComponentHolder.move(x, y);
		}
		
		override protected function createNewInstance():AbstractAction{
			return new ShowRelatedComponentAction();
		}
		
		public function stop():void{
			try{
				Kernel.instance.uimediator.uicontroller.closeWindow(_relatedComponentHolder);
			}
			catch(error:Error){
				trace("WARNING: ShowRelatedComponentAction.stop - Attempted removal of invisible component.");
			}
		}
		
		public function shouldStopOnEvent(event:Event):Boolean{
			switch(event.type){
				case(MouseEvent.CLICK):
					return event.target != _relatedComponentHolder;
				case(ListEvent.ITEM_CLICK):
				case(MenuEvent.MENU_HIDE):
					return true;
				default:
					return false;
			}
		}		
	}
}