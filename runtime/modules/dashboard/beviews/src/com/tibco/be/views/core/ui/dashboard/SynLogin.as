package com.tibco.be.views.core.ui.dashboard{
	
	/* SO FAR THIS CLASS IS UNSUSED FOR BEVIEWS */
	
	/**
	 * This component does the user login. It completes the login procedure in two scenarios
	 * Scenario A - When user credentials are supplied by default
	 * Scenario B - When NO user credentials are supplied
	 * In Scenario A, the component simply makes a call to the presentation server
	 * (via PSVRClient) to fetch the login status.
	 * While in Scenario B it shows up a login screen where the user is prompted to 
	 * provide details and choose roles
	 */
	import com.tibco.be.views.core.Logger;
	import com.tibco.be.views.core.io.PSVRClient;
	
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.core.UIComponent;
	import mx.utils.StringUtil;
	import mx.validators.StringValidator;
	import mx.utils.ArrayUtil;
	import mx.collections.IList;

	public class SynLogin extends UIComponent{
		
		public static var LOGIN_COMPLETE_EVENT_TYPE:String = "loginComplete";
		
		private static var CLASS_NAME:String = "SynLogin";
		
		private var _userName:String = null;
		private var _password:String = null;
		private var _role:String = null;
		private var _sToken:String = null;
		
		/**
		 * Constructor
		 */
		 public function SynLogin(){
		 	super();
		 }
		 
		 /**
		 * Initiates the component based on the bootParams supplied
		 * @public
		 * @param boot parameters as an ArrayCollection
		 */
		 public function initComp(bootParams:IList = null):void{
			for each (var bootParam:Object in bootParams){
				if(bootParam.hasOwnProperty("username") == true){
					_userName = bootParam.username;
				}
				else if(bootParam.hasOwnProperty("password") == true){
					_password = bootParam.password;
				}
				else if(bootParam.hasOwnProperty("role") == true){
					_role = bootParam.role;
				}
				else if(bootParam.hasOwnProperty("stoken") == true){
					_sToken = bootParam.stoken;
				}
			}
			if(_sToken == null || StringUtil.trim(_sToken).length == 0){
				if(_userName != null && StringUtil.trim(_userName).length != 0){
				 	PSVRClient.instance.login(this, handleUserLogin, _userName, _password, handleLoginError);
				}
				else{
					//show the UI login dialog
				}
			} 
			else{
				PSVRClient.instance.setTokenAndVerify(this,handleTokenVerification,_sToken,false,handleTokenVerificationError);
			}
		 }
		 
		 /**
		 * this is a call back function sent to the PSVRClient while attempting to login a user
		 * Once the PSVRClient completes the login action, it posts the status onto this function
		 * @public
		 * @param event object
		 */
		 public function handleUserLogin(eventObj:Event):void{
		 	var respXML:XML = XML(eventObj.target.data);
		 	Logger.instance.log(Logger.INFO, CLASS_NAME, "Response received from server - \n" + respXML.toString(), null);
		 	if(_role != null && StringUtil.trim(_role).length != 0){
		 		var roleIdx:int = -1;
		 		for each (var role:String in PSVRClient.instance.roles){
		 			roleIdx++;
		 			if(role == _role){
		 				break;
		 			}
		 		}
		 		if(roleIdx == -1){
		 			//we need to log out the logged in user
		 			throw new Error("No role matching "+_role+" found for "+_userName);
		 		}
		 		else{
				 	PSVRClient.instance.setRole(this, handleRoleSet, _role, handleLoginError);		 			
		 		}
		 	}
		 	else{
		 		if(PSVRClient.instance.roles.length == 1){
				 	//we set the first role as preferred role
				 	PSVRClient.instance.setRole(this, handleRoleSet, PSVRClient.instance.roles[0], handleLoginError);
		 		}
		 		else{
		 			//we need to show the role selection dialog		 			
		 		}
		 	}
		 }
		 
		 /**
		 * function gets triggered once the preferred role is set in the server
		 * @public
		 * @param event Object
		 */
		 public function handleRoleSet(eventObj:Object):void{
		 	//for now we have set the first role as default role, so at this place we have to inform kernel that we
		 	//are done with login. the next step would be launching the dashboard
		 	dispatchEvent(new Event(LOGIN_COMPLETE_EVENT_TYPE));		 	
		 }
		 
		 /**
		 * call back function supplied to the PSVRClient. This is called when an error occurs while 
		 * attempting to login
		 * @public
		 * @param event Object
		 */
		 public function handleLoginError(eventObj:IOErrorEvent):void{
		 	Logger.instance.log(Logger.INFO, CLASS_NAME, "Error while trying to get connected to server - " + eventObj.target.data, null);		 	
		 }
		
		 /**
		 * function gets triggered once the token has been verified by the server
		 * @public
		 * @param event Object
		 */		
		 public function handleTokenVerification(eventObj:Event):void{
		 	if(PSVRClient.instance.preferredRole != null){
		 		dispatchEvent(new Event(LOGIN_COMPLETE_EVENT_TYPE));		 		
		 	}
		 	else if(PSVRClient.instance.roles.length == 1){
			 	//we set the first role as preferred role
			 	PSVRClient.instance.setRole(this, handleRoleSet, PSVRClient.instance.roles[0], handleLoginError);
	 		}
	 		else{
	 			//we need to show the role selection dialog		 			
	 		}
		 }
		 
		 /**
		 * call back function supplied to the PSVRClient. This is called when an error occurs while 
		 * attempting to verify a token
		 * @public
		 * @param event Object
		 */		 
		 public function handleTokenVerificationError(eventObj:Event):void{
		 	
		 }		 

	}
}