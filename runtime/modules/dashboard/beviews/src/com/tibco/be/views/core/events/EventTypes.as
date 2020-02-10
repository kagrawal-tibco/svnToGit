package com.tibco.be.views.core.events{
	
	/**
	 * Provides a central lookup location for all event type strings used by BEViews system events.
	 */
	public class EventTypes{
		
		/** Default BEViews Event */
		public static const BEVIEWS:String = "BEViews";
		
		
		//IO Events
		/** Generic IO event */
		public static const IO:String = "IO";
		/** Pull data request */
		public static const PULL_DATA_REQUEST:String = "PullDataRequest";
		/** Pull data response */
		public static const PULL_DATA_RESPONSE:String = "PullDataResponse";
		/** Stream data request */
		public static const STREAM_DATA_REQUEST:String = "StreamDataRequest";
		/** Stream data response */
		public static const STREAM_DATA_RESPONSE:String = "StreamDataResponse";
		
		
		//Task Events
		/** Generic Simple Task Request Event */
		public static const SIMPLE_TASK:String = "SimpleTask";
		/** Server Command Request Event */
		public static const SERVER_REQUEST:String = "ServerRequest";
		/** Server Command Response Event */
		public static const SERVER_RESPONSE:String = "ServerResponse";
		/** Configuration Command Request Event */
		public static const CONFIG_COMMAND:String = "ConfigurationCommand";
		/** Configuration Command Request Event */
		public static const CONFIG_COMMAND_RESPONSE:String = "ConfigurationCommandResponse";
		/** Control Command Request Event */
		public static const CONTROL_COMMAND:String = "ControlCommand";
		/** Control Command Request Event */
		public static const CONTROL_COMMAND_RESPONSE:String = "ControlCommandResponse";
		/** Local Connection Request Event */
		public static const LC_COMMAND:String = "LocalConnectionCommand";
		/** Local Connection Response Event */
		public static const LC_COMMAND_RESPONSE:String = "LocalConnectionCommandResponse";
		/** MDA Editor Request Event */
		public static const MDA_EDITOR_REQUEST:String = "MDAEditorRequest";
		/** MDA Editor Response Event */
		public static const MDA_EDITOR_RESPONSE:String = "MDAEditorResponse";
		
		
		//Logging Events
		/** Generic logging event */
		public static const LOG:String = "Log";
		/** Log event signaling local connection logging only */
		public static const LOG_TO_LOCAL_CONNECTION:String = "LogLocalConnection";
		
		//Application Events
		/** Query Manager query response */
		public static const SUCCESSFUL_QUERY_RESPONSE:String = "AppQueryManagerQueryRespnose";
		
	}
}