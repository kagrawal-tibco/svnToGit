package com.tibco.be.views.user.components.drilldown.events{
	
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;

	public class BEVDrillDownRequest extends ConfigRequestEvent{
		
		public static const GET_LAYOUT:String = "getlayout";
		public static const GET_TABLETREE_DATA:String = "getdrilldowndata";
		public static const REMOVE_TABLETREE_DATA:String = "removedrilldowndata";
		public static const DRILLDOWN_GET_EXPORT_OPTIONS:String = "getdrilldownexportoptionsdefaults";
		public static const DRILLDOWN_EXPORT_QUERY_DATA:String = "exportdrilldowndata";
				
		public static const GET_QUERY_MANAGER_MODEL:String = "getquerymgrmodel";
		public static const GET_QUERY_MANAGER_FIELDS:String = "getentityfields";
		public static const QUERY_MANAGER_EXECUTE_QUERY:String = "createquery";
		public static const QUERY_MANAGER_GET_EXPORT_OPTIONS:String = "getquerymgrexportoptionsdefaults";
		public static const QUERY_MANAGER_EXPORT_QUERY_DATA:String = "exportquerydata";
		
		public function BEVDrillDownRequest(requestCommand:String, intendedRecipient:Object=null){
			super(requestCommand, intendedRecipient);
			if(Session.instance.sessionId != ""){
				addXMLParameter("sessionid", Session.instance.sessionId);
			}
		}
		
	}
}