package com.tibco.be.views.ui.gallery.controller{
	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.ConsoleAppender;
	import com.tibco.be.views.core.Logger;
	import com.tibco.be.views.core.io.PSVRClient;
	import com.tibco.be.views.core.utils.ProgressListener;
	import com.tibco.be.views.ui.mdaeditor.controller.MDAEditorController;
	import com.tibco.be.views.ui.mdaeditor.view.MDAEditor;
	import com.tibco.be.views.ui.mdaeditor.view.MDAEditorPanel;
	import com.tibco.be.views.ui.mdaeditor.view.MDANewChartDialog;
	//import com.tibco.be.views.utils.HTTPRequestLoader;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	
	import mx.containers.Panel;
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.managers.CursorManager;
	import mx.managers.PopUpManager;
	import mx.utils.StringUtil;
	
	import com.tibco.be.views.ui.gallery.controller.GalleryController;
	import com.tibco.be.views.ui.gallery.view.ComponentGallery;

	
	public class GalleryAppController implements ProgressListener
	{
	
		private var mysteryObject:Object = null;
		
		private var username:String;
		private var userrole:String;
		
		private var selectedPageConfigID:String = null;
		private var selectedPartitionConfigID:String = null;		
		private var selectedPanelConfigID:String = null;
		
		private var mdaGallery:ComponentGallery = null;
		
		public function GalleryAppController(mysteryObj:Object):void {
			this.mysteryObject = mysteryObj;
		}
		
		public function init():void{
			//TODO we need to extend the kernel concept to have a standalone kernel launching
		 	//here we will take an instance of logger class and append the loggers to it as per the requirement
		 	var loggerInstance:Logger = Logger.instance;
		 	//console Appender instance
		 	var consoleAppender:ConsoleAppender = new ConsoleAppender();
		 	//add the console appender to the logger
		 	loggerInstance.addLogAppender(consoleAppender);			
			Configuration.instance.setProgressListener(this);
			Configuration.instance.loadConfiguration("../properties/configuration.xml");
		}
		
		//ProgressListener interface APIs - START
		public function completed(sizePC:Number):void{
			
		}
		
		public function finished(success:Boolean):void{
			if (success == true){
				this.mysteryObject.btnLogin.addEventListener(MouseEvent.CLICK, loginBtnClicked);
			}
			else{
				Alert.show("Could not load configuration XML","Configuration Error",Alert.OK,Sprite(mysteryObject));
			}
		}
		
		//ProgressListener interface APIs - END				
		
		private function loginBtnClicked(evt:MouseEvent):void {
			username = this.mysteryObject.txtFldUserName.text;
			if (StringUtil.trim(username).length == 0){
				Alert.show("Invalid Username","Login Error",Alert.OK,Sprite(mysteryObject));
				return;
			}
			var password:String = this.mysteryObject.txtFldPassword.text;	
			if (StringUtil.trim(password).length == 0){
				Alert.show("Invalid Password","Login Error",Alert.OK,Sprite(mysteryObject));
				return;
			}					
			CursorManager.setBusyCursor();
			PSVRClient.instance.login(this,userLogged,username,password,userLogFailure);			
		}
		
		public function userLogged(event:Event):void {
			if (PSVRClient.instance.roles.length == 1){
				userrole = PSVRClient.instance.roles[0];
				readGalleryModel();
			}
			else{
				CursorManager.removeBusyCursor();
				userrole = this.mysteryObject.txtFldRole.text;
				if (StringUtil.trim(userrole).length == 0){
					Alert.show("Invalid Role","Login Error",Alert.OK,Sprite(mysteryObject));
					//we need to logout the user
					return;
				}				
				PSVRClient.instance.setRole(this,userRoleSet,userrole,userRoleSetFailure);
			}
		}
		
		public function userLogFailure(event:Event):void {
			CursorManager.removeBusyCursor();
			Alert.show("Could not login "+username,"Login Error",Alert.OK,Sprite(mysteryObject));
		}	
		
		public function userRoleSet(event:Event):void {
			CursorManager.setBusyCursor();
			readGalleryModel();
		}	
		
		public function userRoleSetFailure(event:Event):void{
			CursorManager.removeBusyCursor();
			Alert.show("Could not set "+userrole+" as a role on "+username,"Login Error",Alert.OK,Sprite(mysteryObject));
		}
		
		private function readGalleryModel():void {
			PSVRClient.instance.sendRequest(this,galleryModelLoaded,"getcompgallerymodel",null,galleryModelLoadFailed,null);
		}
		
		private function galleryModelLoaded(success:Boolean, xmlStr:XMLList, stateObj:Object = null):void {
			CursorManager.removeBusyCursor();
			trace(xmlStr);
			//TODO initialize and render gallery
			// Make a new object and pass it the xmlStr
			initializeComponentGallery();
		}
		
		private function initializeComponentGallery():void {
			
		}
		
		private function galleryModelLoadFailed(event:Event):void {
			CursorManager.removeBusyCursor();
			Alert.show("Could not read gallery information for "+username+"/"+userrole,"Gallery Reading Error",Alert.OK,Sprite(mysteryObject));
		}		
		
	}
}