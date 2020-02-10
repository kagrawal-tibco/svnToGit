package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.controls.MessageBox;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObjectContainer;
	import flash.system.fscommand;
	
	import mx.core.Application;

	public class LaunchInternalLinkAction extends AbstractAction {
		
		public function LaunchInternalLinkAction(){
			registerAction(CommandTypes.LAUNCH_INTERNAL_LINK);
		}
		
		override public function execute(actionContext:ActionContext):void{
			if(actionContext == null){
				Logger.log(DefaultLogEvent.WARNING, "LaunchInternalLinkAction.execute - Null action context provided, action cannot be executed.");
				return;
			}
			//do we have a 'link' param ?
			if (_params["url"] != undefined) {
				//we have a link param, so we just launch that 
				launch(_params["url"]);
			}
			//do we have a command param ? 
			else if(_params["command"] != undefined){
				//we have a command, so we are in true internal mode
				var request:ControlRequestEvent = new ControlRequestEvent(_params["command"], this);
				for(var internalDynParamName:String in _dynamicParams){
					var internalDynParamRef:String = _dynamicParams[internalDynParamName];
					if(internalDynParamRef != null){
						var internalValue:String = actionContext.getDynamicParamValue(internalDynParamRef);
						if(internalValue != null){
							request.addXMLParameter(internalDynParamName,internalValue);
						}
					}
				}				
				EventBus.instance.dispatchEvent(request);						
			}
			//do we have any dynamic params
			else{
				//we should have a single dynamic param
				var link:String = ""; 
				for(var dynParamName:String in _dynamicParams){
					var dynParamRef:String = _dynamicParams[dynParamName];
					if(dynParamRef != null){
						var value:String = actionContext.getDynamicParamValue(dynParamRef);
						if(value != null){
							link = link + value;
						}
					}
				}
				if(link != ""){
					launch(link);
				}
				else{
					//we got nothing , notify the user 
					MessageBox.show(DisplayObjectContainer(Application.application),"Launch Internal Link","Could not find information to launch a link"); 
				}				
			}
//			try{
//				var link:String = actionContext.getDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_LINK_PARAM);
//				LaunchInternalLinkAction.launchHACK(link);
//			}
//			catch(error:Error){
//				trace("LaunchInternalLinkAction Error\n" + error.getStackTrace());
//				throw error;
//			}
		}	
		
		override protected function createNewInstance():AbstractAction{
			return new LaunchInternalLinkAction();
		}
		
		private function launch(link:String):void{
			if(link.charAt(0) == "/"){
				if(Configuration.instance.localConnectionName != ""){
					link = link + (link.indexOf("?") > 0 ? "&":"?");					
					link = link + "lcname=" + Configuration.instance.localConnectionName;
				}
				link = Configuration.instance.serverBaseURL + link; 
			}
			fscommand("showlink", link);
		}
	}
}