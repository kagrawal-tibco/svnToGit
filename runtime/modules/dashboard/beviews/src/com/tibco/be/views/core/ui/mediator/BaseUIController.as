package com.tibco.be.views.core.ui.mediator{
	
	/**
	 * The ‘DefaultUIController’ represents the default full feature controller. 
	 * This controller will be incharge of initializing the dashboard and its 
	 * children and maintaining the state of the dashboard within a login session
	 */
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.kernel.KernelLoader;
	import com.tibco.be.views.core.ui.controls.MessageBox;
	import com.tibco.be.views.core.ui.controls.ProgressDialog;
	import com.tibco.be.views.core.ui.dialogs.LoginDialogController;
	import com.tibco.be.views.core.utils.IProgressListener;
	import com.tibco.be.views.user.actions.ActionRegistrar;
	import com.tibco.be.views.user.dashboard.BEVContentPane;
	import com.tibco.be.views.user.dialogs.IPopupWindow;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.events.Event;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.core.Application;
	import mx.core.Container;
	import mx.managers.PopUpManager;

	public class BaseUIController implements IUIController, IEventBusListener{
		
		public static const DASHBOARD_PAGETYPE:String = "DashboardPage";

		private static var _openWindows:Array;		

		protected var _bootParams:Dictionary;
		protected var _contentPane:BEVContentPane;
		protected var _previousPageSetID:String;
		protected var _currentPageSetID:String;
		protected var _variableDict:Dictionary;
		protected var _loginDialogController:LoginDialogController;
		protected var _progressListener:IProgressListener;
		protected var _serverResponse:ServerResponseEvent;
		
		/** Constructor */
		public function BaseUIController(){
			_variableDict = new Dictionary();
			_currentPageSetID = null;
			ActionRegistrar.registerAllActions();
		}
		
		/**
		 * initiates the component
		 * @public
		 * @param array collection
		 */
		public function init(bootParams:Dictionary):void{
			_bootParams = bootParams;
			if(_bootParams["boottype"] == KernelLoader.TEST_MODE){
				return;
			}
			registerListeners();
			if(Session.instance.token != null && Session.instance.preferredRole != null){
				_progressListener = ProgressDialog.show(DisplayObjectContainer(Application.application), "Loading "+progressTitle+"...", fetchLayout);
			}
			else{
				showLoginDialog();
			}
		}
		
		protected function get progressTitle():String {
			return "Dashboard";
		} 
		
		protected function showLoginDialog():void {
			_loginDialogController = new LoginDialogController(Container(Application.application));
			_loginDialogController.addEventListener(Event.COMPLETE, loginDialogCompleteHandler);
			_loginDialogController.init();
		}
		
		public function getRootContainers():IList{
			return new ArrayCollection([_contentPane]);
		}
		
		protected function loginDialogCompleteHandler(event:Event):void{
			_progressListener = ProgressDialog.show(
				DisplayObjectContainer(Application.application),
				"Loading "+progressTitle+"...",
				fetchLayout
			);
		}
		
		protected function fetchLayout():void{
			_progressListener.startMainTask("Loading "+progressTitle+"...",-1);
			var getLayoutReq:ConfigRequestEvent = new ConfigRequestEvent(
				ConfigRequestEvent.GET_LAYOUT_COMMAND,
				this
			);
			if(currentPageSetId != null){
				getLayoutReq.addXMLParameter("pageid",currentPageSetId);
			}
			EventBus.instance.dispatchEvent(getLayoutReq);
		}		
		
		public function handleResponse(response:ServerResponseEvent):void{
			if(!isRecipient(response)){ return; }
			_serverResponse = response;
			if(response.command == ControlRequestEvent.UNSUBSCRIBE_ALL_COMMAND){
				if(response.failMessage != ""){
					Logger.log(
						DefaultLogEvent.WARNING, 
						"UIControllerImpl.handleResponse - Problem issuing unsubscribe all: " + response.failMessage
					);
				}
				fetchLayout();
			}
			else if(response.command == ConfigRequestEvent.GET_LAYOUT_COMMAND){
				if(response.failMessage != ""){
					if(_progressListener is ProgressDialog){
						ProgressDialog(_progressListener).hide();
						MessageBox.show(
							DisplayObjectContainer(Application.application),
							progressTitle+" Loading Error",
							_serverResponse.dataAsString,reset
						);
					}
					else{
						_progressListener.errored(response.dataAsString, response);
					}					
				}
				else{
					var xml:XML = response.dataAsXML;
					//we set the default pageset id					
					currentPageSetId = new String(response.dataAsXML.@id);
					resetUI();
					//recreate the dashboard 
					_contentPane = new BEVContentPane();
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
				}
			}
		}
		
		public function reload(progressListener:IProgressListener):void{
			if(progressListener != null){
				_progressListener = progressListener;
				issueUnsubscribeAll();
			}
			else{
				progressListener = ProgressDialog.show(
					DisplayObjectContainer(Application.application), 
					"Loading "+progressTitle+"...", 
					issueUnsubscribeAll
				);
			}		
		}
		
		protected function issueUnsubscribeAll():void{
			var unsubscribeReq:ControlRequestEvent = new ControlRequestEvent(
				ControlRequestEvent.UNSUBSCRIBE_ALL_COMMAND,
				this
			);
			EventBus.instance.dispatchEvent(unsubscribeReq);
		}
		
		public function reset(): void{
			unRegisterListeners();
			resetUI();
			init(new Dictionary());
		}
		
		protected function resetUI():void{
			closeAllWindows();
			if(_contentPane != null){
				//remove the dashboard 
				Application.application.removeChild(_contentPane);				
			}
			_contentPane = null;
			//reset the variable dictionary 
			_variableDict = new Dictionary();
			//reset the dashboard list 
		}
		
		public function get currentPageSetId():String{
			return _currentPageSetID;
		}
		
		public function set currentPageSetId(pagesetID:String):void{
			_previousPageSetID = _currentPageSetID;
			_currentPageSetID = pagesetID;
		}
		
		public function addVariable(variableName:String, variableValue:Object):Object{
			var oldValue:Object = _variableDict[variableName];
			_variableDict[variableName] = variableValue;
			return oldValue;
		}
		

		public function getVariable(variableName:String):Object{
			return _variableDict[variableName];
		}
		
		public function openWindow(window:IPopupWindow, parent:DisplayObject, modal:Boolean):void{
			if(_openWindows == null){ _openWindows = new Array(); }
			_openWindows.push(window);
			PopUpManager.addPopUp(window, parent, modal);
		}
		
		public function getWindow(stageX:Number, stageY:Number):IPopupWindow{
			for each(var openWindow:IPopupWindow in _openWindows){
				if(openWindow.hitTestPoint(stageX, stageY)){ return openWindow; }
			} 
			return null;
		}
		
		public function centerWindow(window:IPopupWindow):void{
			if(_openWindows.indexOf(window) >= 0){
				PopUpManager.centerPopUp(window);
			}
		}
		
		public function closeWindow(window:IPopupWindow):void{
			if(_openWindows == null){ return; }
			try{
				_openWindows.splice(_openWindows.indexOf(window), 1);
				PopUpManager.removePopUp(window);
			}
			catch(error:Error){
				Logger.log(DefaultLogEvent.WARNING, "UserUtils.closeWindow - " + error.message);
			}
		}
		
		public function closeAllWindows():void{
			if(_openWindows == null){ return; }
			for(var i:int = 0; i < _openWindows.length; i++){
				var popup:IPopupWindow = _openWindows[i] as IPopupWindow;
				if(popup != null){
					popup.closingWindow();
					PopUpManager.removePopUp(popup);
				}
			}
			_openWindows = null;
		}
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleResponse, this);
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse, this);
		}
		protected function unRegisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleResponse);
			EventBus.instance.removeEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse);
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			return(event.intendedRecipient == this);
		}
		
	}
}