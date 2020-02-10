package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	
	import flash.system.fscommand;

	public class PageLauncherLinkAction extends AbstractAction{
		
		public static const SEARCH:String = "SearchPage";
		public static const ASSET:String = "AssetPage";
		
		private var pType:String;
				
		public function PageLauncherLinkAction(pType:String){
			super();
			this.pType = pType;
			registerAction(CommandTypes.LAUNCH_INTERNAL_LINK, "ptype", pType);
		}
		
		override public function execute(actionContext:ActionContext):void{
			//Create the url for the page to launch.
			var encodedToken:String = encodeURIComponent(actionContext.getDynamicParamValue("token"));

			//var url:String = PSVRClient.instance.connectedPSVRPrivateAPIURL + __configParams["baseurl"];
			var url:String = Configuration.instance.serverBaseURL + _configParams["baseurl"];
			url += "?ptype=" + pType; 
			url += "&token=" + encodedToken;

			//pass the url to base html page through fscommand
			fscommand("showlink", url);
		}	
		
		override protected function createNewInstance():AbstractAction{
			return new PageLauncherLinkAction(this.pType);
		}			
	}
}