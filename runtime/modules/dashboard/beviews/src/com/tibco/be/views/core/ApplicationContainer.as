package com.tibco.be.views.core{
	
	import flash.external.ExternalInterface;
	import flash.system.fscommand;
	
	public class ApplicationContainer{
		
		/**
		 *
		 * @param  command:String
		 * @param  args:String
		 * @return
		 * @purpose Make an fscommand call
		*/
		public static function doFSCommand(command:String,args:String):void{
			fscommand(command,args);
		}
	
		/**
		 *
		 * @param  linkURL:String
		 * @return
		 * @purpose Launch an URL
		*/
		public static function launchLink(linkURL:String):void{
//			var privateURI:String = PSVRClient.instance.connectedPSVRPrivateAPIURL;
//			doFSCommand("showlink", privateURI + linkURL);
		}
	
		/**
		 *
		 * @param  linkURL:String
		 * @return
		 * @purpose Launch an URL
		*/
		public static function launchExternalLink(linkURL:String):void{
			doFSCommand("showexternallink", linkURL);
		}
	
		/**
		 * @param
		 * @return
		 * @purpose Handles a connection lost event
		*/
		public static function handleDisconnect():void{
			//doFSCommand("connectionlost", PSVRClient.getInstance().getNextServerURL());
		}
		
		/**
		 * @param
		 * @return
		 * @purpose Handles session timeout.  The parent jsp page would pop up a dialog box and when user
		 * hits OK, it will reload the page, showing the login screen.  
		*/
		public static function handleTimeOut(errorMessage:String):void{
			trace("DONT remove this trace message.");
			trace("Error Message: " + errorMessage);
			doFSCommand("timeout", errorMessage);
		}
	
		public static function userLoggingOut(userid:String):void{
			doFSCommand("userloggingout", userid);
		}
	
		public static function userRoleChanging(role:String):void{
			doFSCommand("userolechanging", role);
		}
	
		public static function debug(trace:String):void{
			doFSCommand("debug", trace);
		}
	}
}