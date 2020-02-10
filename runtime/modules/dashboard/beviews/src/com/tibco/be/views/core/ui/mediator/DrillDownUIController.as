package com.tibco.be.views.core.ui.mediator{
	
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.ActionRegistry;
	import com.tibco.be.views.core.ui.controls.MessageBox;
	import com.tibco.be.views.core.ui.controls.ProgressDialog;
	import com.tibco.be.views.user.actions.IStoppableAction;
	import com.tibco.be.views.user.components.drilldown.events.BEVDrillDownRequest;
	import com.tibco.be.views.user.dashboard.BEVContentPane;
	
	import flash.display.DisplayObjectContainer;
	import flash.system.fscommand;
	
	import mx.core.Application;
	
	public class DrillDownUIController extends BaseUIController{
		
		public static const DRILLDOWN_PAGETYPE:String = "SearchPage";
		public static const RELATEDCONCEPT_PAGETYPE:String = "RelatedPage";
		
		private var _stoppableActions:Array;
		
		public function DrillDownUIController(){
			super();
		}
		
		override protected function get progressTitle():String{
			if(_bootParams["pagetype"] == DRILLDOWN_PAGETYPE){
				return "Search Page";
			}
			if(_bootParams["pagetype"] == RELATEDCONCEPT_PAGETYPE){
				return "Related Assets Page";
			}
			return super.progressTitle;
		}
		
		override protected function showLoginDialog():void{
			MessageBox.show(DisplayObjectContainer(Application.application),progressTitle+" Loading Error","No token specified");
		}
		
		override protected function fetchLayout():void{
			_progressListener.startMainTask("Loading "+progressTitle+"...",-1);
			var getLayoutReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.GET_LAYOUT, this);
			//add all boot params to the layout request 
			for(var paramname:String in _bootParams){
				getLayoutReq.addXMLParameter(paramname, _bootParams[paramname]);
		 	} 
			EventBus.instance.dispatchEvent(getLayoutReq);
		}
		
		override public function handleResponse(response:ServerResponseEvent):void{
			//right now the only response will be for BEVDrillDownRequest.GET_LAYOUT
			if(response.failMessage != ""){
				if(_progressListener is ProgressDialog){
					ProgressDialog(_progressListener).hide();
					MessageBox.show(
						DisplayObjectContainer(Application.application),
						progressTitle+" Loading Error",
						response.failMessage
					);
				}
				else{
					_progressListener.errored(response.dataAsString, response);
				}					
			}
			else{
				var xml:XML = response.dataAsXML;
				//we set the default pageset id					
				currentPageSetId = new String(xml.@id);
				if(xml.pageconfig != undefined && xml.pageconfig.variable != undefined){
					//extract session id 
					var sessionId:String = new String(xml.pageconfig.variable.(@name == "sessionid")[0]);
					//set it @ the session level
					Session.instance.sessionId = sessionId;
				}
				resetUI();
				//recreate the dashboard 
				_contentPane = new BEVContentPane(false);
				//add to the main container
				Application.application.addChild(_contentPane);
				//set dimensions
				_contentPane.percentHeight = 100;
				_contentPane.percentWidth = 100;			
				//trigger dashboard layout loading				
		 		_contentPane.init(response.dataAsXML);
				if(_progressListener is ProgressDialog){
					_progressListener.completedMainTask();
					ProgressDialog(_progressListener).hide();
				}
				else{
					_progressListener.completedSubTask();
				}
				if(xml.pageconfig != undefined && xml.pageconfig.action != undefined){
					performActions(xml.pageconfig.action);
				}
				//let's update the page title
				if(xml.pageconfig != undefined){
					fscommand("annotatetitle",new String(xml.pageconfig.@name));
				}
			}
		}
		
		override public function reset(): void{
			unRegisterListeners();
			resetUI();
			init(_bootParams);
		}		
		
		override protected function resetUI():void{
			for each(var stoppableAction:IStoppableAction in _stoppableActions){
				stoppableAction.stop();
			}
			super.resetUI();
		}
		
		
		private function performActions(actionConfigs:XMLList):void{
			_stoppableActions = new Array();
			for each(var actionConfig:XML in actionConfigs){
				var action:AbstractAction = ActionRegistry.instance.getAction(actionConfig);
				if(action != null){
					if(action is IStoppableAction){
						_stoppableActions.push(action);
					}
					var dynamicParamsResolver: DynamicParamsResolver = new DynamicParamsResolver();
					dynamicParamsResolver.setDynamicParamValue(DynamicParamsResolver.SESSION_ID,Session.instance.sessionId);
					action.execute(new ActionContext(_contentPane, dynamicParamsResolver));
				}
			}
		}
	}
}