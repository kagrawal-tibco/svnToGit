package com.tibco.be.views.core.ui.actions{
	
	import flash.utils.Dictionary;
	import flash.xml.XMLNode;
	import flash.xml.XMLNodeType;
	
	import mx.controls.Image;
	
	/**
	 * The AbstractAction forms the basis of all the action that can be done in BEViews world. 
	 */
	public class AbstractAction{
		
		protected var _actionConfig:XML ;
		protected var _command:String ;
		protected var _text:String ;
		protected var _icon:Image;
		protected var _enabled:Boolean;
		protected var _configParams:Dictionary;
		protected var _dynamicParams:Dictionary;		
		protected var _params:Dictionary; 
		
		private var _keyConfigParamName:String;
		private var _keyConfigParamValue:String;
		private var _registered:Boolean;
		private var _inuse:Boolean;
		private var _initialized:Boolean;
		
		/**
		 * Default Constructor 
		 */ 
		public function AbstractAction():void{
			_registered = false;
			_inuse = false;
			_initialized = false;
		}
		
		/** Returns the text of the action. This is used as text in the menus */ 
		public function get text():String{ return _text; }
		/** Returns the icon of the action. This is used as icon in the menus */ 
		public function get icon():Image{ return _icon; }
		/** Returns whether the action is enabled or not */ 
		public function get enabled():Boolean{ return _enabled; }
		/** Returns the action definition which defines this action */ 
		public function get actionConfig():XML{ return _actionConfig; }
		/** Returns the unique string defining the command to execute */
		public function get command():String{ return _command; }
		
		/**
		 * Sets whether the action is enabled or not 
		 * @param value true if the action is to be enabled else false
		 */ 
		public function set enabled(value:Boolean):void{ _enabled = value; }
		
		internal function get inUse():Boolean{ return _inuse; }
		internal function get initialized():Boolean{ return _initialized; }
		
		internal function set inUse(value:Boolean):void{ _inuse = value;}
		internal function set initialized(value:Boolean):void{ _initialized = value; }
		
		/**
		 * Register an action with the ActionRegistry
		 * @param command the command of the action 
		 * @param configParamName name of a key config param which differentiates the action from other actions
		 * @param configParamValue value of a key config param which differentiates the action from other actions
		 */ 
		protected function registerAction(command:String, configParamName:String = null, configParamValue:String = null):void{
			if(_registered == true){ return; }
			ActionRegistry.instance.registerAction(command, configParamName, configParamValue, this);
			_keyConfigParamName = configParamName;
			_keyConfigParamValue = configParamValue;
			_registered = true;
		}
		
		/**
		 * Unregisters an action from the ActionRegistry
		 */ 
		protected function unregisterAction():void{
			if(_registered == false){
				return;
			}
			ActionRegistry.instance.unregisterAction(_command, _keyConfigParamName, _keyConfigParamValue, this);
			_registered = false;
		}		
		
		/**
		 * Initializes an action using the action definition XML 
		 * @param actionConfig The action definition XML 
		 */ 
		public function initByXML(actionConfig:XML):void{
			_actionConfig = actionConfig;
			_command = _actionConfig.@command;
			_enabled = !(_actionConfig.@disabled == "true");
			_text = _actionConfig.text();
			_configParams = new Dictionary();
			for each(var configParam:XML in _actionConfig.configparam){
				var configParamName:String = configParam.@name;
				var configParamValue:String = configParam.text()[0];
				_configParams[configParamName] = configParamValue;
			}
			_dynamicParams = new Dictionary();
			for each(var dynparam:XML in _actionConfig.dynamicparam){
				var dynParamName:String = dynparam.@name;
				var dynParamValue:String = dynparam.text()[0];
				_dynamicParams[dynParamName] = dynParamValue;
			}			
			_params = new Dictionary();
			for each(var param:XML in _actionConfig.param){
				var paramName:String = param.@name;
				var paramValue:String = param.text()[0];
				_params[paramName] = paramValue;
			}			
		}
		
		/**
		 * Initializes the action using the arguments supplied 
		 * @param command The command of the action
		 * @param enabled Is the action enabled or not 
		 * @param text The text of the action
		 * @param configParams key-value pairs which provide configuration information
		 * @param dynamicParams key-value pairs which provide dynamic binding information neccessary for proper execution of the command 
		 * @param parameters key-value pairs which provide static information neccessary for proper execution of the command 
		 */ 
		public function initByArgs(command:String, enabled:Boolean, text:String, configParams:Dictionary, dynamicParams:Dictionary, parameters:Dictionary):void{
			_command = command;
			_enabled = enabled;
			_text = text;
			_configParams = configParams;
			_dynamicParams = dynamicParams;
			_params = parameters;
			
			//<actionconfig command="" disabled=""></actionconfig>
			_actionConfig = new XML("<actionconfig/>");
			_actionConfig.@command = _command;
			_actionConfig.@disabled = !(_enabled);
			
			//<text>...</text>
			var textNode:XML = new XML("<text/>");
			textNode.appendChild(new XMLNode(XMLNodeType.TEXT_NODE, text));			
			_actionConfig.appendChild(textNode);
			
			//<configparam name="">...</configparam>
			appendToAsXML(_actionConfig, "configparam", _configParams);
			
			//<param name="">...</param>
			appendToAsXML(_actionConfig, "param", _params);
			
			//<dynamicparam name="">...</dynamicparam>
			appendToAsXML(_actionConfig, "dynamicparam", _dynamicParams);
			
		}	
		
		private function appendToAsXML(parentNode:XML, nodeName:String, map:Dictionary):void{
			for (var key:Object in map){
				var paramName:String = key as String;
				var paramValue:String = map[key] as String;

				var childNode:XML = new XML("<"+nodeName+"/>");
				childNode.@name = paramName;
				childNode.appendChild(new XMLNode(XMLNodeType.TEXT_NODE, paramValue));
				
				parentNode.appendChild(childNode);
			}			
		}
		
		/**
		 * Executes the action. The sub-classes should override this method to implement their
		 * execution 
		 */ 
		public function execute(actionContext:ActionContext):void{}
		
		/**
		 * Closes/shutdowns the action 
		 */ 
		public function close():void{
			_inuse = false;
			ActionRegistry.instance.removeInstance(_command, _keyConfigParamName, _keyConfigParamValue, this);
		}
		
		internal function clone():AbstractAction{
			return createNewInstance();
		}	
		
		protected function createNewInstance():AbstractAction{
			throw new Error(" Unsupported Operation ");
		}
	}
}