package com.tibco.be.views.user.dashboard{
	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.ActionRegistry;
	import com.tibco.be.views.core.ui.dashboard.IBEVContainer;
	import com.tibco.be.views.core.ui.mediator.IUIController;
	import com.tibco.be.views.utils.BEVUtils;
	import com.tibco.be.views.utils.Logger;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.utils.Dictionary;
	
	import mx.controls.Alert;
	import mx.controls.LinkButton;
	import mx.events.ItemClickEvent;
	import mx.events.ListEvent;
	import mx.utils.StringUtil;
	
	
	public class BEVHeaderController implements IEventBusListener{
		
		private static const MAX_LOGO_WIDTH:int = 95; 
		
		private static const SWITCH_ROLE_ACTION_CONFIG:XML = <actionconfig command="switchrole"/>;
		private static const SIGN_OUT_ACTION_CONFIG:XML = <actionconfig command="signout"/>;		
		
		private var _uicontroller:IUIController;
		private var _view:BEVHeader;
		
		private var _configXML:XML;
		private var _currentHeaderConfig:XML;
		
		public function BEVHeaderController(bevHeader:BEVHeader){
			_uicontroller = Kernel.instance.uimediator.uicontroller;
			_view = bevHeader;			
		}
		
		public function init():void{
			registerListeners();
			//Modified to fix BE-22726 & BE-22044, the newer flash players make buttons completely invisible if alpha is 0
			//Instead we add a null skin in the css. See CoreStyles.css#BEVHeaderLinkBtn
			//view.logOutButton.alpha = 0;
			var request:ConfigRequestEvent = new ConfigRequestEvent(ConfigRequestEvent.GET_HEADER_CONFIG_COMMAND, this);
			EventBus.instance.dispatchEvent(request);
		}
		
		public function handleResponse(response:ServerResponseEvent):void{
			if(!isRecipient(response)){ return; }
			if(response.failMessage != ""){
				Logger.log(DefaultLogEvent.CRITICAL, "BEVHeaderController.handleResponse - " + response.failMessage);
				Alert.show("BEViews Header Configuration Load Error.", "CRITICAL ERROR");
			}
			else{
				_configXML = response.dataAsXML;
				update();
				initBaseEventListeners();
			}
		}
		
		private function initBaseEventListeners():void{
			_view.actionsBar.addEventListener(ItemClickEvent.ITEM_CLICK, actionItemClicked);
			_view.logOutButton.addEventListener(MouseEvent.CLICK,signOutBtnClickHandler);
			if(_view.userRolesCombo != null){
				_view.userRolesCombo.addEventListener(ListEvent.CHANGE,userRoleSelectionChangeHandler);
			}
		}
		
		public function update():void{
			if(!_view.rightContentVisible){
				Logger.log(DefaultLogEvent.INFO, BEVUtils.getClassName(this) + ".update - No right content, skipping update.");
				return;
			}
			var currentPageSetID:String = _uicontroller.currentPageSetId;
			_currentHeaderConfig = _configXML.pagehdrconfig.(@pageid == currentPageSetID)[0];
			
			//image
			var hasHeaderLogo:Boolean = false;
			if(_currentHeaderConfig == null){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".update - Null header config XML.");
				return;
			}
			else if(_currentHeaderConfig.image == undefined){
				Logger.log(DefaultLogEvent.DEBUG, BEVUtils.getClassName(this) + ".update - No Image node in config XML.");
				return;
			}
			else if(_currentHeaderConfig.image[0] != null && _currentHeaderConfig.image[0].@url[0] && _currentHeaderConfig.image[0].@url[0] != ""){
				_view.logoURL = Configuration.instance.serverBaseURL + _currentHeaderConfig.image[0].@url[0];
				hasHeaderLogo = true;
			}
			
			//title
			if(_currentHeaderConfig.title != null && StringUtil.trim(_currentHeaderConfig.title).length != 0){
				_view.title = _currentHeaderConfig.title;
			}
			
			//username
			_view.username = Session.instance.username;
			
			//preferred role
			_view.userrole = Session.instance.preferredRole;
			
			//user roles
			_view.userroles = Session.instance.roles;
			
			
			if(hasHeaderLogo == true){
				_view.currentState =(_view.userroles.length > 1 ) ? BEVHeader.MULTI_ROW_WITH_IMAGE_STATE:BEVHeader.SINGLE_ROW_WITH_IMAGE_STATE;
				_view.logoImage.addEventListener(Event.COMPLETE,logoLoadingCompleteHandler);
			}
			else{
				_view.currentState =(_view.userroles.length > 1 ) ? BEVHeader.MULTI_ROW_NO_IMAGE_STATE:"";
			}
			
			if(_view.userRolesCombo != null){
				_view.userRolesCombo.selectedItem = Session.instance.preferredRole;
			}
						
			//link bar
			// changes by Nikhil on Feb 16 2011: trim the link names if they are longer than 15 chars
			var actions:Array = new Array(); 
			for each(var actionConfig:XML in _currentHeaderConfig.hdractioncfg.actionconfig){
				var urlName:String = String(actionConfig.text.text());
				var origName:String = urlName;
				if(urlName.length > 15) {
					urlName = urlName.substr(0,15) + "...";
				}
				var linkBarItem:Object = {label:urlName, tooltip:origName};
				//actions.push(urlName);
				actions.push(linkBarItem);				
   				//actions.push(String(actionConfig.text.text()));	
			}
			
			_view.actionsBar.dataProvider = actions;
			_view.actionsBar.callLater(enableDisableActions);
			_view.validateNow();
		}
		
		private function logoLoadingCompleteHandler(event:Event):void{
			if(_view.logoImage.contentWidth > MAX_LOGO_WIDTH){
				_view.logoImage.width = MAX_LOGO_WIDTH;
			}
			else{
				_view.logoImage.width = _view.logoImage.contentWidth;
			}
		}
		
		private function enableDisableActions():void{
			for(var i:Number = 0; i < _view.actionsBar.getChildren().length; i++){
				var actionBtn:LinkButton = _view.actionsBar.getChildAt(i) as LinkButton;
				actionBtn.enabled = _currentHeaderConfig.hdractioncfg.actionconfig[i].@disabled == "false";
				//Modified to fix BE-22726 & BE-22044, the newer flash players make buttons completely invisible if alpha is 0
				//Instead we add a null skin in the css. See CoreStyles.css#BEVHeaderLinkBar#BEVHeaderLinkBtn				
				//actionBtn.alpha = 0;
			}
		}
		
		private function actionItemClicked(event:ItemClickEvent):void{
			var actionConfigXML:XML = _currentHeaderConfig.hdractioncfg.actionconfig[event.index];
			var dynamicParamValues:Dictionary = new Dictionary();
			if (actionConfigXML.@command == CommandTypes.LAUNCH_INTERNAL_LINK) {
				//in header, we get the url as a config param, but LaunchInternalLinkAction understand links as dynamic resolution 
				dynamicParamValues[DynamicParamsResolver.CURRENTDATACOLUMN_LINK_PARAM] = new String(actionConfigXML.param.(@name == "url")[0]);
			}
			fireAction(actionConfigXML,dynamicParamValues);
		}
		
		private function signOutBtnClickHandler(event:MouseEvent):void{
			var dynparams:Dictionary = new Dictionary();
			dynparams["token"] = Session.instance.token;
			fireAction(SIGN_OUT_ACTION_CONFIG, dynparams);
		}
		
		private function userRoleSelectionChangeHandler(event:ListEvent):void{
			var dynparams:Dictionary = new Dictionary();
			dynparams["token"] = Session.instance.token;
			dynparams[DynamicParamsResolver.CURRENT_ROLE] = Session.instance.preferredRole;
			dynparams[DynamicParamsResolver.NEW_ROLE] = _view.userRolesCombo.selectedItem;
			dynparams[DynamicParamsResolver.INVOKING_UI_COMPONENT] = _view.userRolesCombo;
			fireAction(SWITCH_ROLE_ACTION_CONFIG, dynparams);
		}
		
		
		private function fireAction(actionConfig:XML, dynparms:Dictionary):void{
			var action:AbstractAction = ActionRegistry.instance.getAction(actionConfig);
			
			/**Action context needs the following:
			 * 1. Save Dashboard - Current dashboard as target of action context
			 * 2. PageLauncherAction - token as param
			 * 3. SwitchRoleAction - "role.new" and "role.current"
			 * 4. SignoutAction - NONE
			 * 5. Gallery - Current dashboard as target of action context
			 */
			
			var target:Object = null;
			var uiController:IUIController = Kernel.instance.uimediator.uicontroller;
			for each (var container:IBEVContainer in uiController.getRootContainers()){
				if(container.containerId == uiController.currentPageSetId){
					//This is the container on which user is working.
					target = container;
					break;
				}
			}
			
			if(target == null){
				target = uiController.getRootContainers()[0];
			}
			
			var params:DynamicParamsResolver = new DynamicParamsResolver();
			for(var prop:String in dynparms){
				params.setDynamicParamValue(prop,dynparms[prop]);	
			}
			var actionContext:ActionContext = new ActionContext(target, params);
			action.execute(actionContext);			
		}
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse, this);
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			return event.intendedRecipient == this;
		}				

	}
}