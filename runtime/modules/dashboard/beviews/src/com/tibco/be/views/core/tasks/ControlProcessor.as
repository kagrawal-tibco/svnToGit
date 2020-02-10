package com.tibco.be.views.core.tasks{
	
	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.io.PullDataRequestEvent;
	import com.tibco.be.views.core.events.io.PullDataResponseEvent;
	import com.tibco.be.views.core.events.io.StreamDataRequestEvent;
	import com.tibco.be.views.core.events.io.StreamDataResponseEvent;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.events.tasks.ControlResponseEvent;
	import com.tibco.be.views.core.ui.StreamingUpdatesManager;
	import com.tibco.be.views.utils.Logger;
	
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	
	/**
	 * A ControlProcessor is used to manage the sequence of events and server request/responses
	 * that are required to perform a specific control action. One instance of ControlProcessor is
	 * created per control action. Subsequent ControlRequestEvents may be dispatched in order to
	 * perform other control actions that are necessary to complete the current control action. As a
	 * general rule, however, one control processor instance should handle exactly one control
	 * action. Once the control action has completed, the ControlProcessor instance should be
	 * removed from the EventBus liteners.
	*/	
	public class ControlProcessor extends ServerCommandProcessor implements IEventBusListener{
		
		public function ControlProcessor(requestEvent:ControlRequestEvent){
			if(!(requestEvent is ControlRequestEvent)){
				EventBus.instance.dispatchEvent(
					new DefaultLogEvent(
						DefaultLogEvent.WARNING, 
						"ControlProcessor - Constructed with non-ControlRequestEvent"
					)
				);
			}
			super(requestEvent);
		}
		
		/**
		 * Starts the ControlProcessor on the provided (constructor) ControlRequestEvent
		*/
		override public function process():void{
			registerListeners();
			if(_request == null){
				Logger.log(DefaultLogEvent.DEBUG, "ControlProcessor.process - NULL request object.");
				return;
			}
			else if(_request.command == ControlRequestEvent.OPEN_DATA_STREAM_COMMAND ||
					_request.command == ControlRequestEvent.START_STREAMING_COMMAND ||
					_request.command == ControlRequestEvent.SEND_STREAMING_DATA_COMMAND ||
					_request.command == ControlRequestEvent.STOP_STREAMING_COMMAND ||
					_request.command == ControlRequestEvent.INIT_SIGN_OUT_COMMAND ||
					_request.command == ControlRequestEvent.CLOSE_DATA_STREAM_COMMAND){
				processStreamRequest();
			}
			else{
				processPullRequest();
			}
		}
		
		private function processPullRequest():void{
			try{
				var dataReq:PullDataRequestEvent = buildDefaultHTTPPullReq();
				var cmd:String = _request.command;
				var params:Dictionary = new Dictionary();
								
				//Add token, command, and any user-specified xml parameters to the params dictionary
				params["stoken"] = Session.instance.token;
				params["command"] = cmd;
				for(var key:String in _request.xmlParams){
					params[key] = _request.xmlParams[key];
				}
				
				//Special handling per command (Note not all control methods need special handling).
				switch(cmd){
					case(ControlRequestEvent.GET_SERVER_CONFIG_COMMAND):
						dataReq.hostName = "";
						dataReq.port = "";
						dataReq.contextPath = Configuration.REMOTE_PROPERTIES_URL;
						params = null;
						break;
						
					case(ControlRequestEvent.PREPARE_STREAMING_CONNECTION):
						params["command"] = ControlRequestEvent.CREATE_CHANNEL_COMMAND;
						params["name"] = Configuration.DEFAULT_DB_CHANNEL_NAME;
						break;
					
					case(ControlRequestEvent.SWITCH_ROLE_COMMAND):
						//swtich role issues unsubscribe-all then setrole
						params["command"] = ControlRequestEvent.UNSUBSCRIBE_ALL_COMMAND;
						//params["name"] = Session.instance.preferredRole;
						break;
						
					case(ControlRequestEvent.SUBSCRIBE_COMMAND):
					case(ControlRequestEvent.UNSUBSCRIBE_COMMAND):
						var componentName:String = _request.getStateVariable("componentName") as String;
						if(componentName == null || componentName == ""){
							processorError("process", "Can't " + cmd + " component with null or blank name");
							return;
						}
						params["name"] = Configuration.DEFAULT_DB_CHANNEL_NAME;
						params["componentname"] = componentName;
						break;

					case(ControlRequestEvent.UNSUBSCRIBE_ALL_COMMAND):
						params["name"] = Configuration.DEFAULT_DB_CHANNEL_NAME;
						break;
				}
				
				//Build the request's XML message and send the request
				dataReq.payload = getRequestXMLString(params);
				EventBus.instance.dispatchEvent(dataReq);
			}
			catch(error:Error){
				processorError("process", error.message);
				return;
			}
		}
		
		
		private function processStreamRequest():void{
			try{
				//init the request
				var streamRequest:StreamDataRequestEvent = new StreamDataRequestEvent(
					Configuration.DEFAULT_DB_CHANNEL_NAME,
					"command set below",
					Configuration.instance.serverName,
					Configuration.instance.updatePort,
					this
				);
				
				//One should note that open connection and close connection are socket control
				//actions that won't actually send anything to the server, and thus do not define
				//any data in the request. As such, when opening and closing the socket, we don't
				//want to unregister listeners yet as StreamDataResponseEvents we want to handle
				//will be generated by the socket.connect or socket.close actions in the
				//StreamingClient.
				switch(_request.command){
					//Open connection
					case(ControlRequestEvent.OPEN_DATA_STREAM_COMMAND):
						streamRequest.command = StreamDataRequestEvent.OPEN_SOCKET;
						break;
					
					//Send Data
					case(ControlRequestEvent.START_STREAMING_COMMAND):
					case(ControlRequestEvent.STOP_STREAMING_COMMAND):
						var params:Dictionary = new Dictionary();
						params["command"] = _request.command;
						params["stoken"] = Session.instance.token;
						params["name"] = Configuration.DEFAULT_DB_CHANNEL_NAME;
						streamRequest.data = getRequestXML(params);
					case(ControlRequestEvent.SEND_STREAMING_DATA_COMMAND):
						streamRequest.command = StreamDataRequestEvent.SEND_DATA;
						break;
					
					//Close Connection
					case(ControlRequestEvent.INIT_SIGN_OUT_COMMAND):
					case(ControlRequestEvent.CLOSE_DATA_STREAM_COMMAND):
						streamRequest.command = StreamDataRequestEvent.CLOSE_SOCKET;
						break;
					default:
						streamRequest.command = "";
						
				}
				
				//send the request
				EventBus.instance.dispatchEvent(streamRequest);
			}
			catch(error:Error){
				processorError("processStreamRequest", error.message);
			}
		}
		
		/**
		 * Handles pull data responses and takes appropriate action depending on the type of
		 * command that was sent in the original pull data request.
		*/
		private function handlePullDataResponse(pullResponse:PullDataResponseEvent):void{
			try{
				var contentStr:String = "";
				var contentXML:XML = null;
				var xmlResponse:XML = XML(pullResponse.data);
				if(super.errorCheck(pullResponse, xmlResponse)){
					unRegisterListeners();
					return;
				}
				//Modified to support backend fix for BE-8262
				if(xmlResponse.content != undefined && xmlResponse.content.children().length() > 0){
					contentStr = new String(xmlResponse.content.children()[0].toString());
					contentXML = new XML(contentStr);
				}
				_response = new ControlResponseEvent(_request as ControlRequestEvent, _request.intendedRecipient)
				_response.data = contentStr;
				switch(_request.command){
					case(ControlRequestEvent.GET_SERVER_CONFIG_COMMAND):
						_response.data = pullResponse.data;
						break;
					case(ControlRequestEvent.LOGIN_COMMAND):
						handleLoginResponse(contentStr);
						break;
					case(ControlRequestEvent.GET_ROLES_COMMAND):
						handleGetRolesResponse(contentXML);
						break;
					case(ControlRequestEvent.VALIDATE_ROLE):
						//invalid will be caught by the above errorCheck
						break;
					case(ControlRequestEvent.SET_ROLE_COMMAND):
						handleSetRoleResponse(contentStr);
						break;
					case(ControlRequestEvent.SWITCH_ROLE_COMMAND):
						//The switch role command starts with telling the server to unsubscribe all.
						//Once that finishes (ie control gets here), we finish the switch role
						//action by calling set role.
						var setRoleReq:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.SET_ROLE_COMMAND, _request.intendedRecipient);
						setRoleReq.addXMLParameter("role", _request.getXMLParameter("role")); 
						EventBus.instance.dispatchEvent(setRoleReq);
						break;
					case(ControlRequestEvent.SET_TIMEOUT + String(Configuration.instance.timeOut)):
//						trace("Set timeout to " + Configuration.instance.timeOut);
//						createStreamingChannel();
						throw new Error("Unsupported Control Process: " + _request.command);
						break;
					case(ControlRequestEvent.PREPARE_STREAMING_CONNECTION):
					case(ControlRequestEvent.CREATE_CHANNEL_COMMAND):
						Session.instance.streamingChannelCreated = true;
						startStreamingChannel();
						break;
					case(ControlRequestEvent.START_CHANNEL_COMMAND):
						openStreamingSocket();
						break;
					case(ControlRequestEvent.UNSUBSCRIBE_ALL_COMMAND):
						StreamingUpdatesManager.instance.unregisterAllComponents();
						break;
					case(ControlRequestEvent.SIGN_OUT_COMMAND):
						//handled by SignoutAction
						break;
				}
				EventBus.instance.dispatchEvent(_response);
				unRegisterListeners();
			}
			catch(error:Error){
				processorError("handlePullDataResponse", error.message + "\n" + error.getStackTrace());
				unRegisterListeners();
				return;
			}
		}
		
		/** Handles stream data responses sent from the server. */
		private function handleStreamDataResponse(streamResponse:StreamDataResponseEvent):void{
			try{
				var contentStr:String = "";
				var contentXML:XML = null;
				var xmlResponse:XML = XML(streamResponse.data);
				if(super.errorCheck(streamResponse, xmlResponse)){
					unRegisterListeners();
					return;
				}
				if(xmlResponse.content != undefined){
					contentStr = new String(xmlResponse.content.children()[0].toString());
					contentXML = new XML(contentStr);
				}
				_response = new ControlResponseEvent(_request as ControlRequestEvent, _request.intendedRecipient);
				_response.data = streamResponse.data;
				if(_response.failMessage != ""){
					processorError(
						"handleStreamDataResponse",
						"'" + _response.command + "' Failed:\n\t" + _response.failMessage
					);
					return;
				}
				switch(streamResponse.requestCommand){
					case(StreamDataRequestEvent.OPEN_SOCKET):
						Logger.log(DefaultLogEvent.DEBUG, "ControlProcessor.handleStreamDataResponse - Streaming Channel Opened.")
						//Modified to fix BE-10793. The issue is timing related. We initiate the getLayout/getConfig/getData/subscribe 
						//almost at the same time as XML socket is opened. Sometimes some components may get to subscribe stage 
						//before StreamingUpdatesManager.instance.init() gets called. This causes issues with the StreamingUpdatesManager.
						//isComponentRegistered() API 
						//StreamingUpdatesManager.instance.init();
						Session.instance.streamingSocketOpen = true;
						startStreaming();
						break;
					case(StreamDataRequestEvent.CLOSE_SOCKET):
						Logger.log(DefaultLogEvent.DEBUG, "ControlProcessor.handleStreamDataResponse - Streaming Channel Closed");
						if(_request.command == ControlRequestEvent.INIT_SIGN_OUT_COMMAND){
							signout();
						}
						StreamingUpdatesManager.instance.shutdown();
						Session.instance.streamingSocketOpen = false;
						break;
					case(StreamDataRequestEvent.SEND_DATA):
						if(contentXML != null && contentXML.name() == "command"){
							if(String(contentXML.@name) == ControlRequestEvent.START_STREAMING_COMMAND){
								Logger.log(DefaultLogEvent.DEBUG, "ControlProcessor.handleStreamResponse - StartStreaming acknowledged.");
								_response.broadcast = true; //login dialog completes on this event
							}
							else if(String(streamResponse.dataAsXML.@name) == ControlRequestEvent.STOP_STREAMING_COMMAND){
								Logger.log(DefaultLogEvent.DEBUG, "ControlProcessor.handleStreamResponse - StopStreaming acknowledged.");
							}
						}
						else{
							//might happen, no big deal. just be sure not to send a response until the command ack is received
							Logger.log(DefaultLogEvent.DEBUG, "ControlProcessor.handleStreamDataResponse - Misc. data received when expecting start/stop response.");
							return;
						}
						break;
					default:
						//responses handled by this processor can only include start and close as
						//those are the only two commands with well-defined, single occurrence
						//StreamDataResponseEvents
						processorError("handleStreamDataResponse", "Unhandled streaming command: " + streamResponse.requestCommand);
						return;
				}
				EventBus.instance.dispatchEvent(_response);
				unRegisterListeners();
			}
			catch(error:Error){
				processorError("handleStreamDataResponse", error.message);
				Logger.log(DefaultLogEvent.DEBUG, error.getStackTrace());
				unRegisterListeners();
				return;
			}
		}
		
		/**
		 * Handles the pull data response sent from the server. Sets the session token and username
		 * if login was successful.
		*/
		private function handleLoginResponse(responseStr:String):void{
			if(responseStr == null || responseStr == ""){
				_response.failMessage = "Could not connect to server, Please try again...";
				return;
			}
			Session.instance.token = responseStr;
			Session.instance.username = _request.getXMLParameter("username"); 
		}
		
		/**
		 * Parses the data received in a PullDataResponseEvent resulting from a PullDataRequestEvent
		 * whose command was GET_ROLES or SET_ROLE.  In the case of SET_ROLE, no session values
		 * will be set.
		 * @param xmlResponse The XML data from the PullDataResponseEvent
		 * @param setSessionRoles A flag indicating whether or not to set session values
		*/
		private function handleGetRolesResponse(xmlResponse:XML):void{
			var roleNameList:XMLList = xmlResponse..@name;
			var roles:ArrayCollection = new ArrayCollection();
			for each(var roleName:XML in roleNameList ){
				roles.addItem(new String(roleName));
			}
			Session.instance.roles = roles;
			if(roles.length == 1){
				Session.instance.preferredRole = roles.getItemAt(0) as String;
				//caller of GET_ROLES needs to handle how to proceed
			}
		}
				
		private function handleSetRoleResponse(response:String):void{
			var preferredRoleSetStr:String = "Preferred role set to ";
			if(response.indexOf(preferredRoleSetStr) >= 0){
				var preferredRole:String = response.replace(preferredRoleSetStr, "");
				var roleID:int = Session.instance.roles.getItemIndex(preferredRole);
				if(roleID >= 0){
					Session.instance.preferredRole = Session.instance.roles.getItemAt(roleID) as String;
				}
				else{
					processorError("handleSetRoleResponse", "Specified preferred role (" + preferredRole + ") is unknown.");
				}
			}
			else{
				processorError("handleSetRoleResponse", "Unhandled Response (" + response + ").");
			}
		}
		
		/** Sends the set timeout command to the server */
		private function setTimeOut():void{
			var setTimeoutReq:ControlRequestEvent =
				new ControlRequestEvent(ControlRequestEvent.SET_TIMEOUT + Configuration.instance.timeOut, this);
			EventBus.instance.dispatchEvent(setTimeoutReq);
		}
		
		/** Sends a request to validate the selected user role */
		private function validateRole(roleName:String):void{
			var validateRoleReq:ControlRequestEvent =
				new ControlRequestEvent(ControlRequestEvent.VALIDATE_ROLE, this);
			validateRoleReq.addXMLParameter("role", roleName);
			EventBus.instance.dispatchEvent(validateRoleReq);
		}
		
		/** Issues an HTTP request to the server to create the streaming socket */
		private function createStreamingChannel():void{
			var createRequest:ControlRequestEvent = new ControlRequestEvent(
				ControlRequestEvent.CREATE_CHANNEL_COMMAND,
				this
			);
			createRequest.addXMLParameter("name", Configuration.DEFAULT_DB_CHANNEL_NAME);
			EventBus.instance.dispatchEvent(createRequest);
		}
		
		/** Issues an HTTP request to the server to start the streaming socket */
		private function startStreamingChannel():void{
			var createRequest:ControlRequestEvent = new ControlRequestEvent(
				ControlRequestEvent.START_CHANNEL_COMMAND,
				this
			);
			createRequest.addXMLParameter("name", Configuration.DEFAULT_DB_CHANNEL_NAME);
			EventBus.instance.dispatchEvent(createRequest);
		}
		
		/**
		 * Creates a new Control Request to handle the opening of the streaming socket.
		*/
		private function openStreamingSocket():void{
			var openSockeReq:ControlRequestEvent = new ControlRequestEvent(
				ControlRequestEvent.OPEN_DATA_STREAM_COMMAND,
				this
			);
			EventBus.instance.dispatchEvent(openSockeReq);
		}
		
		private function startStreaming():void{
			var startStreamingReq:ControlRequestEvent = new ControlRequestEvent(
				ControlRequestEvent.START_STREAMING_COMMAND,
				this
			);
			EventBus.instance.dispatchEvent(startStreamingReq);
		}
		
		private function stopStreaming():void{
			var stopStreamingReq:ControlRequestEvent = new ControlRequestEvent(
				ControlRequestEvent.STOP_STREAMING_COMMAND,
				this
			);
			EventBus.instance.dispatchEvent(stopStreamingReq);
		}
		
		private function closeStreamingSocket(signingOut:Boolean=false):void{
			var closeSocketReq:ControlRequestEvent = new ControlRequestEvent(
				ControlRequestEvent.CLOSE_DATA_STREAM_COMMAND,
				this
			);
			EventBus.instance.dispatchEvent(closeSocketReq);
		}
				
		private function signout():void{
			try{
				//Broadcast so the SignoutAction can perform follow-up operations
				var signoutCommand:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.SIGN_OUT_COMMAND);
				EventBus.instance.dispatchEvent(signoutCommand);
			}
			catch(error:Error){
				processorError("signout", error.message);
				return;
			}
		}
		
		/**
		 * Error function dispatches a log event and a control response event with error detail
		 * @param functionName The ControlProcessor function where the error originated
		 * @param message The error message
		*/
		override public function processorError(functionName:String, message:String):void{
			functionName = functionName == null ? "":functionName;
			message = message == null ? "":message;
			
			//Broadcast the error response
			var response:ControlResponseEvent = new ControlResponseEvent(_request as ControlRequestEvent, null);
			response.data = null;
			response.failMessage = message;
			if(message.indexOf("Failed to authenticate") != -1 || message.indexOf("not a valid role") != -1 || message.indexOf("is not usable") != -1){
				//handled login failures...
			}
			else{
				var logEntry:DefaultLogEvent = new DefaultLogEvent(
					DefaultLogEvent.WARNING, 
					"ControlProcessor." + functionName + " - " + message
				);
				EventBus.instance.dispatchEvent(logEntry);
			}
			EventBus.instance.dispatchEvent(response);
			return;
		}
		
		//IEventBusListener
		override public function registerListeners():void{
			try{
				EventBus.instance.addEventListener(EventTypes.PULL_DATA_RESPONSE, handlePullDataResponse, this);
				EventBus.instance.addEventListener(EventTypes.STREAM_DATA_RESPONSE, handleStreamDataResponse,this);
				//EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleConfigResponse,this);
			}
			catch(error:Error){
				processorError("registerListeners", error.message);
				return;
			}
		}
		
		public function unRegisterListeners():void{
			try{
				EventBus.instance.removeEventListener(EventTypes.PULL_DATA_RESPONSE, handlePullDataResponse);
				EventBus.instance.removeEventListener(EventTypes.STREAM_DATA_RESPONSE, handleStreamDataResponse);
				//EventBus.instance.removeEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleConfigResponse);
			}
			catch(error:Error){
				processorError("unRegisterListeners", error.message);
				return;
			}
		}
				
	}
}