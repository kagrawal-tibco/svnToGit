package com.tibco.be.views.core.ui.actions{
	
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.logging.LoggingService;
	
	import flash.utils.Dictionary;
	import flash.utils.getQualifiedClassName;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	
	/**
	 * The ActionRegistry represents the pool of actions registered with the system. 
	 * One can have a action not registered with the registry. 
	 */ 
	public class ActionRegistry{
		
		private static var _instance:ActionRegistry;
		
		private var _cmdToKeyCfgParamNamesDict:Dictionary;
		private var _keyToActionsDict:Dictionary;
		
		public static function get instance():ActionRegistry{
			if(_instance == null){
				_instance = new ActionRegistry();
			}
			return _instance;
		}
		
		function ActionRegistry():void{
			_cmdToKeyCfgParamNamesDict = new Dictionary();
			_keyToActionsDict = new Dictionary();
		}
		
		/**
		 * Returns a instance of an action given a action definition XML. The instance is returned iff 
		 * there is a registered action against the given action definition XML. Also if all the instances 
		 * are in use, a new instance is created
		 * @param actionCfg The action definition XML 
		 */ 
		public function getAction(actionCfg:XML):AbstractAction{
			var command:String = actionCfg.@command;
			var keyConfigParamName:String = null;
			var keyConfigParamValue:String = null;
			var keyParamNames:IList = _cmdToKeyCfgParamNamesDict[command];
			if(keyParamNames == null){
//				Logger.instance.logA(this, Logger.WARNING, "could not find an action regsitered for ["+command+"]");
				EventBus.instance.dispatchEvent(
					new DefaultLogEvent(DefaultLogEvent.WARNING,"could not find an action registered for ["+command+"]")
				);
				return null;
			}
			var keyParamNamesCnt:int = keyParamNames.length;
			for(var i:int = 0 ; i < keyParamNamesCnt ; i++){
				var keyParamName:String = keyParamNames.getItemAt(i) as String;
				for each(var configParam:XML in actionCfg.configparam){
					if(configParam.@name == keyParamName){
						keyConfigParamName = keyParamName;
						keyConfigParamValue = configParam.text();
						break;
					}
				}
				if(keyConfigParamName != null){
					break;
				}
				for each(var param:XML in actionCfg.param){
					if(param.@name == keyParamName){
						keyConfigParamName = keyParamName;
						keyConfigParamValue = param.text();
						break;
					}
				}
				if(keyConfigParamName != null){
					break;
				}
			}
			
			var actions:IList = _keyToActionsDict[createKey(command, keyConfigParamName, keyConfigParamValue)] as IList;
			if(actions != null){
				for each(var action:AbstractAction in actions){
					if(!action.inUse){
						if(!action.initialized){
							action.initByXML(actionCfg);
							action.initialized = true;
						}
						action.inUse = true;
						return action;
					}
				}
				if(actions.length > 0){
					var clonedAction:AbstractAction = (actions.getItemAt(0) as AbstractAction).clone();
					clonedAction.initByXML(actionCfg);
					clonedAction.initialized = true;
					clonedAction.inUse = true;
					if(!contains(actions, clonedAction)){
						actions.addItem(clonedAction);
					}
					return clonedAction;
				}
			}
			trace(
				"ERROR: ActionRegistry.getAction - Unsupported key [" +
				"command: '" + command + "', " +
				"param name: '" + keyConfigParamName + "', " + 
				"param value: '" + keyConfigParamValue + "']"
			);
			return null;
		}
		
		/**
		 * Registers a action 
		 * @param command the command of the action 
		 * @param keyConfigParamName name of a key config param which differentiates the action from other actions
		 * @param keyConfigParamValue value of a key config param which differentiates the action from other actions
		 * @param action the actual action
		 */
		internal function registerAction(command:String, keyConfigParamName:String, keyConfigParamValue:String, action:AbstractAction):void{
			var keyParamNames:IList = _cmdToKeyCfgParamNamesDict[command];
			if(keyParamNames == null){
				keyParamNames = new ArrayCollection();
				_cmdToKeyCfgParamNamesDict[command] = keyParamNames;
			}
			if( (keyConfigParamName != null) && !contains(keyParamNames, keyConfigParamName) ){
				keyParamNames.addItem(keyConfigParamName);
			}
			var key:String = createKey(command, keyConfigParamName, keyConfigParamValue);

			var actions:IList = _keyToActionsDict[key] as IList;
			if(actions == null){
				actions = new ArrayCollection();
				_keyToActionsDict[key] = actions;
				actions.addItem(action);
			}
			else{
				if(actions.length == 0){ 
					//this should generally not happen,but we will handle it as a gesture of good will 
					actions.addItem(action);
				}
				else{
					//we need to check what's going on here 
					var add:Boolean = false;
					var qualifiedNameOfAction:String = getQualifiedClassName(action);
					for each(var listElement:AbstractAction in actions){
						var qualifiedNameOfListElement:String = getQualifiedClassName(listElement);
						if(qualifiedNameOfListElement != qualifiedNameOfAction){
							throw new Error(qualifiedNameOfAction + " cannot be registered,since "+qualifiedNameOfListElement+" has already been registered"); 
						}
						if(listElement.inUse == true){
							add = true;
						}
						else{
							add = false;
						}
					}
					if(add == true){
						actions.addItem(action);
					}
				}
			}
			_keyToActionsDict[key] = actions;
		}
		
		/**
		 * Unregisters an action. Not that all the action instance will be removed 
		 * @param command the command of the action 
		 * @param keyConfigParamName name of a key config param which differentiates the action from other actions
		 * @param keyConfigParamValue value of a key config param which differentiates the action from other actions
		 * @param action the actual action
		 */ 
		internal function unregisterAction(command:String, keyConfigParamName:String, keyConfigParamValue:String, action:AbstractAction):void{
			_keyToActionsDict[createKey(command, keyConfigParamName, keyConfigParamValue)] = null;
		}
		
		private function contains(list:IList,element:Object):Boolean{
			for each(var item:Object in list){
				if(item == element){
					return true;
				}
			}
			return false;
		}
		
		/**
		 * Removes an instance of a action 
		 * @param command the command of the action 
		 * @param keyConfigParamName name of a key config param which differentiates the action from other actions
		 * @param keyConfigParamValue value of a key config param which differentiates the action from other actions
		 * @param action the actual action
		 */  
		internal function removeInstance(command:String, keyConfigParamName:String, keyConfigParamValue:String, action:AbstractAction):void{
			var actions:IList = _keyToActionsDict[createKey(command, keyConfigParamName, keyConfigParamValue)] as IList;
			if(actions != null){
				var idx:int = actions.getItemIndex(action);
				if(idx != -1){
					actions.removeItemAt(idx);
				}
			}
		}
		/**
		 * Creates the composite key based on the command, configparamname and configparamvalue. 
		 * It does it in two forms when configparamname is not null (return command + configparamname + configparamvalue) and in other case when it is null (just returns command).
		 */ 
		private function createKey(command:String, keyConfigParamName:String, keyConfigParamValue:String):String{
			var key:String = command;
			
			if(keyConfigParamName != null){
				key += "::"+keyConfigParamName+"::"+keyConfigParamValue;
			}
			return key;
		}
	
	}
}