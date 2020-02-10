package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.events.tasks.ControlResponseEvent;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	
	import flash.system.fscommand;

	public class LaunchExternalLinkAction extends AbstractAction implements IEventBusListener{
		
		public function LaunchExternalLinkAction(){
			registerAction(CommandTypes.LAUNCH_EXTERNAL_LINK);
		}
		
		override public function execute(actionContext:ActionContext):void{
			registerListeners();
            var url:String = new String(_actionConfig.param.(@name == "url")[0]);
            if(url != "undefined"){
                fscommand("showexternallink", url);
                return;
            }
			var target:Object = actionContext.target;
			var linkReq:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.LAUNCH_EXTERNAL_LINK, this);
			var seriesid:String = new String(_actionConfig.param.(@name == "seriesid")[0]);
			var componentid:String = new String(_actionConfig.param.(@name == "componentid")[0]);
			var fieldname:String = new String(_actionConfig.param.(@name == "fieldname")[0]);
			linkReq.addXMLParameter("seriesid", seriesid);
			linkReq.addXMLParameter("componentid", componentid);
			linkReq.addXMLParameter("fieldname", fieldname);
			var hrefprms:String = actionContext.getDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_TYPE_SPEC_HREF_PARAMS_PARAM);
			if(hrefprms != null && hrefprms.length > 0){
				var params:Array = hrefprms.split("&");
				for each(var param:String in params){
					var parts:Array = param.split("=",2);
					if(parts.length == 2){  //might have values like   key=&key2=&key3=
						linkReq.addXMLParameter(parts[0], parts[1]); //parts[0] is key, parts[1] is value
					}
				}
			}
			EventBus.instance.dispatchEvent(linkReq);
		}
		
		private function handleResponse(response:ControlResponseEvent):void{
			if(!isRecipient(response)){ return; }
			if(response.failMessage != ""){
				EventBus.instance.dispatchEvent(
					new DefaultLogEvent(
						DefaultLogEvent.CRITICAL,
						"LaunchExternalLink.handleResponse - Error fetching link.\n" + response.failMessage
						)
					);
			}
			else{
				var link:String = response.dataAsString;
				//see if the link starts with a "http://". If it doesn't, the link is assumed
				//to be to the same host:port as the Views server
				if(link.substr(0,7).toLowerCase() != "http://"){
					if(Configuration.instance.localConnectionName != ""){
						link = link + (link.indexOf("?") > 0 ? "&":"?");					
						link = link + "lcname=" + Configuration.instance.localConnectionName;
					}
					fscommand("showlink", Configuration.instance.serverBaseURL + link);
				}
				else{
					fscommand("showexternallink", response.dataAsString);			 
				}
			}
			unRegisterListeners();
		}
		
		override protected function createNewInstance():AbstractAction{
			return new LaunchExternalLinkAction();
		}
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleResponse, this);
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			return event.intendedRecipient == this;
		}
		public function unRegisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleResponse);
		}
	}
}