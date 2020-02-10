package com.tibco.be.views.core.ui.actions{
	
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	
	import flash.geom.Rectangle;
	
	/**
	 * The ActionContext provides contextual information neccessary for the 
	 * correct execution of a action 
	 */ 
	public class ActionContext{
		
		private var _target:Object;
		private var _resolvedDynamicParams:DynamicParamsResolver;		
		private var _bounds:Rectangle;
		
		/**
		 * Default Constructor 
		 * @param target The target element 
		 * @param resolvedDynParams a key-value pairs which represents the resolved values for the dynamic parameters 
		 */  
		public function ActionContext(target:Object, resolvedDynParams:DynamicParamsResolver){
			_target = target;
			_resolvedDynamicParams = resolvedDynParams;
		}
		
		/** Returns the target */ 
		public function get target():Object{ return _target; }
		
		public function get bounds():Rectangle{ return _bounds; }
		
		public function set bounds(value:Rectangle):void{ _bounds = value; }
		
		/**
		 * Returns a resolved dynamic value 
		 * @param dynamicParamName the name of the dynamic parameter 
		 */ 
		public function getDynamicParamValue(dynamicParamName:String):String{
			return _resolvedDynamicParams.getDynamicParamValue(dynamicParamName) as String;
		}
		
		public function getDynamicParamObject(dynamicParamName:String):Object{
			return _resolvedDynamicParams.getDynamicParamValue(dynamicParamName);
		}
		
	}
}