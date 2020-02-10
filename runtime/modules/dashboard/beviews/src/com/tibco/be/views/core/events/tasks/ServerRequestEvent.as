package com.tibco.be.views.core.events.tasks{
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	
	import flash.events.ErrorEvent;
	import flash.events.Event;
	import flash.events.SecurityErrorEvent;
	import flash.utils.Dictionary;
	
	public class ServerRequestEvent extends EventBusEvent{

		/** The command to issue to the server. */
		protected var _command:String;
		
		/** Dictionary of key/value pairs that will be added as additional variables in POST data. */
		protected var _requestParams:Dictionary;
		
		/** Parameters that will be added to the requestXML POST parameter */
		protected var _xmlParams:Dictionary;
		
		/** 
		 * Dictionary of objects needed to represent state at the time of the request.  These
		 * objects are not sent to the server, they are intended for User-end use only.
		*/
		protected var _stateVariables:Dictionary;
		protected var _respStatus:int;
		protected var _responseObject:*;
		protected var _rawResponse:SecurityErrorEvent;
		
		public function ServerRequestEvent(requestCommand:String, type:String=null, intendedRecipient:Object=null){
			if(type == null || type == "") type = EventTypes.SERVER_REQUEST;
			super(type, intendedRecipient);
			_command = requestCommand;
			_requestParams = new Dictionary();
			_stateVariables = new Dictionary();
			
		}
		
		public function get command():String{ return _command; }
		public function get requestParams():Dictionary{ return _requestParams; }
		public function get xmlParams():Dictionary{ return _xmlParams; }
		public function get stateVariables():Dictionary{ return _stateVariables; }
		public function get respStatus():int{ return _respStatus; }
		public function get rawResponseEvent():SecurityErrorEvent{ return _rawResponse; }
		
		public function set command(value:String):void{ _command = value; }
		public function set requestParams(value:Dictionary):void{ _requestParams = value; }
		public function set xmlParams(value:Dictionary):void{ _xmlParams = value; }
		public function set stateVariables(value:Dictionary):void{ _stateVariables = value; }
		public function set respStatus(value:int):void{ _respStatus = value; }
		public function set rawResponse(value:SecurityErrorEvent):void{ _rawResponse = value; }
		
		override public function clone():Event{
			var cloned:ServerRequestEvent = new ServerRequestEvent(_command, type, _intendedRecipient);
//			cloned.requestParams = _requestParams;
//			cloned.xmlParams = _xmlParams;
//			cloned.stateVariables = _stateVariables;
//			cloned.respStatus = _respStatus;
//			cloned.rawResponse = _rawResponse;
			return cloned;
		}
				
		/**
		 * Adds a request parameter  
		 * @param paramName - The name of the parameter
		 * @param paramValue - The value of the parameter
		*/ 
		public function addRequestParameter(paramName:String, paramValue:String):void{
			if(_requestParams == null){
				_requestParams = new Dictionary();
			}
			_requestParams[paramName] = paramValue;
		}
		
		/**
		 * Removes a request parameters if present
		 * @param paramName - The name of the parameter
		*/ 
		public function removeRequestParameter(paramName:String):void{
			if(_requestParams == null){
		 		return;
		 	}
		 	delete _requestParams[paramName];
		 }
		 
		/**
		 * Rerturns whether a 'parameter' exist 
		 * @param paramName - The name of the parameter
		*/ 
		public function containsRequestParmeter(paramName:String):Boolean{
			if(_requestParams == null){
				return false;
			}
			return (_requestParams[paramName] != undefined);
		}
		
		/**
		 * Returns the value of a parameter, if present else returns null
		 * param paramName - The name of the parameter
		*/
		public function getRequestParameter(paramName:String):String{
			if(_requestParams == null){
				return null;
			}
			return _requestParams[paramName] == undefined ? null:_requestParams[paramName];
		}
		
		/**
		 * Adds a parameter to be included in the XML request message
		 * @param paramName - The name of the parameter
		 * @param paramValue - The value of the parameter
		*/
		public function addXMLParameter(paramName:String, paramValue:String):void{
			if(_xmlParams == null){
				_xmlParams = new Dictionary();
			}
			_xmlParams[paramName] = paramValue;
		}
		
		/**
		 * Removes a request parameters if present
		 * @param paramName - The name of the parameter
		*/ 
		public function removeXMLParameter(paramName:String):void{
			if(_xmlParams == null){
		 		return;
		 	}
		 	delete _xmlParams[paramName];
		 }
		 
		/**
		 * Rerturns whether a 'parameter' exist 
		 * @param paramName - The name of the parameter
		*/ 
		public function containsXMLParmeter(paramName:String):Boolean{
			if(_xmlParams == null){
				return false;
			}
			return (_xmlParams[paramName] != undefined);
		}
		
		/**
		 * Returns the value of a parameter, if present else returns null
		 * param paramName - The name of the parameter
		*/
		public function getXMLParameter(paramName:String):String{
			if(_xmlParams == null){
				return null;
			}
			return _xmlParams[paramName] == undefined ? null:_xmlParams[paramName];
		}
		
		
		/**
		 * Adds a state variable 
		 * @param variableName - The name of the variable
		 * @param variableValue - The value of the variable
		*/ 
		public function addStateVariable(variableName:String, variableValue:*):void{
			if(_stateVariables == null){
				_stateVariables = new Dictionary();
			}
			_stateVariables[variableName] = variableValue;
		}
		
		/** Returns the value of a state variable if present,else returns null */ 
		public function getStateVariable(variableName:String):*{
			if(_stateVariables == null){
				return null;
			}
			return _stateVariables[variableName] == undefined ? null:_stateVariables[variableName];
		}
		
		/**
		 * Removes a state variable
		 * @param variableName - The name of the variable
		*/ 	
		public function removeStateVariable(variableName:String):void{
			if(_stateVariables == null){
				return;
			}
			delete _stateVariables[variableName];
		}
		 
		/**
		 * Returns whether a variable exists 
		 * @param variableName - The name of the variable
		*/ 
		public function containsStateVariable(variableName:String):Boolean{
			if(_stateVariables == null){
				return false;
			}
			return (_stateVariables[variableName] != undefined);
		}		 
		
		/** Returns the response status */ 
		public function get responseStatus():int{
			return _respStatus;
		}
		
		/** Returns the response as XML */ 
		public function get responseAsXML():XML{
			return _responseObject as XML;
		}
		
		/** Returns the response as String */ 
		 public function get responseAsString():String{
			if(_responseObject is ErrorEvent){
				return ErrorEvent(_responseObject).text;	
			}
			else if(_responseObject is Event){
				return Event(_responseObject).toString();	
			}
			return _responseObject as String;
		}
		
		/** Returns the response as Event */ 
		public function get responseAsEvent():Event{
			return _responseObject as Event;
		}
		
		//ILogable
		override public function get logMessage():String{
			//no string builder... lame
			var msg:String = "";
			msg += ("Server Request For Command '"+_command+"'\n");
			for each (var paramName:String in _requestParams){
				msg += ("\tRequest Parameter '"+paramName+"' - "+_requestParams[paramName]+"'\n");
			}
			for each (var variableName:String in _stateVariables){
				msg += ("\tState Variable '"+variableName+"' - "+_stateVariables[variableName]+"'\n");
			}
			msg += ("\t\tRaw Response - "+_rawResponse+"\n");
			msg += ("\t\tResponse Status - "+_respStatus+"\n");
			msg += ("\t\tResponse Content - "+_responseObject+"\n");
			return msg;
		}

	}
}