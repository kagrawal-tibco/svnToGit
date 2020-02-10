package com.tibco.be.views.core.ui{
	
	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.io.IOEvent;
	import com.tibco.be.views.core.events.io.StreamDataRequestEvent;
	import com.tibco.be.views.core.events.io.StreamDataResponseEvent;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.actions.ServerCrashAction;
	import com.tibco.be.views.utils.Logger;
	
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;

	public class StreamingUpdatesManager implements IEventBusListener{
		
		private static var _instance:StreamingUpdatesManager;
		
		private var _streamingChannel:String;
		private var _componentMap:Dictionary;
		
		public function StreamingUpdatesManager(streamingChannelName:String=null){
			_streamingChannel = streamingChannelName == null ? Configuration.instance.serverName:streamingChannelName;
			//Calling init in the construction phase to fix BE-10793	
			//Modified to fix BE-10793. The issue is timing related. We initiate the getLayout/getConfig/getData/subscribe 
			//almost at the same time as XML socket is opened. Sometimes some components may get to subscribe stage 
			//before StreamingUpdatesManager.instance.init() gets called. This causes issues with the StreamingUpdatesManager.
			//isComponentRegistered() API 		
			init();	
		}
		
		public static function get instance():StreamingUpdatesManager{
			if(_instance == null){
				_instance = new StreamingUpdatesManager();
			}
			return _instance;
		}
		
		public function init():void{
			_componentMap = new Dictionary();
			unRegisterListeners(); //make sure not to reregister
			registerListeners();
		}
		
		public function shutdown():void{
			_componentMap = null;
		}
		
		public function registerComponent(component:BEVComponent):Boolean{
			if(_componentMap == null){ init(); }
			if(component == null){ return false; }
			try{
				if(_componentMap[component.componentId] == undefined){
					_componentMap[component.componentId] = new ArrayCollection([component]);
				}
				else{
					var subscribedComps:ArrayCollection = _componentMap[component.componentId] as ArrayCollection;
					if(subscribedComps && !subscribedComps.contains(component)){ subscribedComps.addItem(component); }
				}
				return true;
			}
			catch(err:Error){
				Logger.log(DefaultLogEvent.WARNING, "ComponentUpdateManager.registerComponent: Failed registering " + component.componentId);
			}
			return false;
		}
		
		public function unregisterComponent(component:BEVComponent):Boolean{
			if(_componentMap == null){ return true; }
			try{
				if(_componentMap[component.componentId] != undefined){
					var subscribedComps:ArrayCollection = _componentMap[component.componentId] as ArrayCollection;
					if(subscribedComps != null){
						var i:int = subscribedComps.getItemIndex(component);
						if(i != -1){ subscribedComps.removeItemAt(i); }
						if(subscribedComps.length == 0){
							delete _componentMap[component.componentId];
						}
					}
					return true;
				}
				return true;
			}
			catch(err:Error){
				Logger.log(DefaultLogEvent.WARNING, "ComponentUpdateManager.unregisterComponent: Failed unregistering " + component.componentId);
			}
			return false;
		}
		
		public function unregisterAllComponents():void{
			if(_componentMap == null) return;
			for(var key:String in _componentMap){
				delete _componentMap[key];
			}
		}
		
		public function isComponentRegistered(component:BEVComponent):Boolean{
			if(_componentMap == null){ return false; }
			var compSet:ArrayCollection = _componentMap[component.componentId] as ArrayCollection;
			return (compSet && compSet.contains(component));
		}
		
		private function handleStreamingResponse(response:StreamDataResponseEvent):void{
			if(!isRecipient(response)){ return; }
			
			if(response.status == StreamDataResponseEvent.SERVER_CRASH){
				_streamingChannel = "";
				_componentMap = null;
				new ServerCrashAction(false).execute(null); //resets all event listeners no need to do it here
				return;
			}
			else if(response.status == IOEvent.RESPONSE_ERROR || response.failMessage != ""){
				Logger.log(DefaultLogEvent.WARNING, "StreamingUpdatesManager.handleStreamingData - " + response.failMessage);
				return;
			}
			
			var updateXML:XML = XML(response.data);
			//Logger.log(DefaultLogEvent.DEBUG, updateXML.toString());
			//update packets are checked first becasue they are most frequent
			if(updateXML.name() == "updatepackets"){
				for each(var updatepacket:XML in updateXML.updatepacket){
					var compid:String = String(updatepacket.visualizationdata.@componentid);
					var compSet:ArrayCollection = _componentMap[compid] as ArrayCollection;
					for each(var component:BEVComponent in compSet){
						component.updateData(updatepacket.visualizationdata[0]);
						//component.pushUpdate(updatepacket.visualizationdata[0]);
					}
				}
				return;
			}
			else if(updateXML.name() == "psvrhealth"){
				var errorCode:String = new String(updateXML.errorcode);
				var errorMsg:String = new String(updateXML.errormessage);
				if(errorCode == "timeout"){
					//PSVRUtils would call fscommand("connectionLost", nextPSVRServer)
//					PSVRUtils.handlePSVRTimeOut(psvrErrMsg);
				}
				else if(errorCode == "shutdown"){
					//PSVRUtils would call fscommand("timeout", psvrErrMsg)
//					PSVRUtils.handlePSVRShutDown(psvrErrMsg);
				}
				else if(errorCode == "signedout"){
					Logger.log(DefaultLogEvent.INFO, "User " + Session.instance.username + " signed out");
				}
				return;
			}
			else if(updateXML.name() == "error"){
				var errMsg:String = new String(updateXML.message);
				Logger.log(DefaultLogEvent.CRITICAL, "Server Error Message: " + errMsg);
			}
			else{
				//log and leave
				Logger.log(DefaultLogEvent.WARNING, "StreamingUpdatesManager: Receieved unprocessable data [" + response.data + "]");
				return;
			}
		}
		
		public function registerListeners():void{
			//listen for broadcast streaming data response events (i.e. those with null intendedRecipient)
			EventBus.instance.addEventListener(EventTypes.STREAM_DATA_RESPONSE, handleStreamingResponse, this);
		}
		
		private function unRegisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.STREAM_DATA_RESPONSE, handleStreamingResponse);
		}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			//We listen to for all data events, since there are an unknown number of streaming
			//updates arriving asyncronously.
			//
			//Open and Close stream response events are normally handled by a control processor.
			//However, in the event that the server closes the socket, no control processor will be
			//expecting the response event. We thus need to handle such a case here...
			if(event is StreamDataResponseEvent){
				var e:StreamDataResponseEvent = event as StreamDataResponseEvent;
				return(
					e != null &&
					(
						e.command == StreamDataRequestEvent.SEND_DATA ||
						e.status == StreamDataResponseEvent.SERVER_CRASH
					)
				);
			}
			return false;
		}
		
	}
}