package com.tibco.be.views.core.events.tasks{
	import com.tibco.be.views.core.events.EventTypes;	
	
	public class ControlRequestEvent extends ServerRequestEvent{
		
		/** Command to fetch server configuration parameters */
		public static const GET_SERVER_CONFIG_COMMAND:String = "getconfig";
		
		/** Command to perform login */
		public static const LOGIN_COMMAND:String = "signin";

		/** Command to fetch the user roles */
		public static const GET_ROLES_COMMAND:String = "getroles";
		
		/** Command to validate the user-selected role */
		public static const VALIDATE_ROLE:String = "validaterole";

		/** Command to set a role as the current one */
		public static const SET_ROLE_COMMAND:String = "setrole";
		
		/** Command to switch the current role. */
		public static const SWITCH_ROLE_COMMAND:String = "switchrole";

		/** Command to set the login token and verify it against the PSVR */
		public static const VERIFY_TOKEN_COMMAND:String = "verify";
		
		/** Command to set server timeout value */
		public static const SET_TIMEOUT:String = "option maxsessiontime ";

		/** Command to configure the options of the real time streaming interface */ 
		public static const OPTION_COMMAND:String = "option";
		
		/**
		 * Application command that will perform all tasks associated with starting a streaming
		 * updates session with the server. This command is not sent to the server.
		*/
		public static const PREPARE_STREAMING_CONNECTION:String = "preparestreamingconnection";

		/** Command to create a streaming channel */ 
		public static const CREATE_CHANNEL_COMMAND:String = "createchannel";

		/** Command to start the streaming channel */ 
		public static const START_CHANNEL_COMMAND:String = "startchannel";
		
		/** Command to open a data stream */  
		public static const OPEN_DATA_STREAM_COMMAND:String = "opendatastream";
		
		/** Command to issue to the server to start client streaming */
		public static const START_STREAMING_COMMAND:String = "startstreaming";
		
		/** Command to tell the server which channel this client is listening on */
		public static const SEND_STREAMING_DATA_COMMAND:String = "sendData";
		
		/** Command to issue to the server to stop client streaming */
		public static const STOP_STREAMING_COMMAND:String = "stopstreaming";

		/** Command to close the data stream */
		public static const CLOSE_DATA_STREAM_COMMAND:String = "closedatastream";
		
		/** Command to subscribe for specific component updates on a streaming channel */
		public static const SUBSCRIBE_COMMAND:String = "subscribe";

		/** Command to unsubscribe specific component updates on a streaming channel */  
		public static const UNSUBSCRIBE_COMMAND:String = "unsubscribe";

		/** Command to subscribe all component updates on a streaming channel */  
		public static const UNSUBSCRIBE_ALL_COMMAND:String = "unsubscribeall";
		
		/** Command to initialize the signout process. The process consists of closing the streaming
		 * socket, then issuing the signout control request (SIGN_OUT_COMMAND).
		 */
		public static const INIT_SIGN_OUT_COMMAND:String = "initSignOut";
		
		public static const LAUNCH_EXTERNAL_LINK:String = "launchexternallink";
		
		/** Command to sign out from the server. */
		public static const SIGN_OUT_COMMAND:String = "signout";
		
		public function ControlRequestEvent(requestCommand:String, intendedRecipient:Object=null){
			super(requestCommand, EventTypes.CONTROL_COMMAND, intendedRecipient);
		}
		
		override public function get logMessage():String{
			return "ControlRequestEvent: " + command; 
		}
		
		override protected function get details():String{
			return "command="+command;
		}
		
	}
}