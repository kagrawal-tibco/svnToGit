package com.tibco.cep.ui.monitor.events{
	
	public class EventTypes{
		
		public static const SERVER_CRASH:String = "serverCrash";
		
		//Panel Events
		public static const PANEL_READY:String = "panelReady";
		
		//Topology Events
		public static const TOPOLOGY_UPDATE:String = "topologyUpdate";
		public static const TOPOLOGY_UPDATE_FAIL:String = "topologyUpdateFail";
		public static const TOPOLOGY_PURGED:String = "topologyPurged";
		
		//Metric Pane Events
		public static const PANE_UPDATE_FAILURE:String = "metricPaneUpdateFailure";
		public static const PANE_EXPANDED:String = "paneExpanded";
		public static const PANE_PROMOTED:String = "panePromoted";
		public static const PANE_DEMOTED:String = "paneDemoted";
		public static const PANE_CLOSED:String = "paneClosed";
		
		//Global Variables
		public static const GLOBAL_VARIABLE_SAVE_BTTN_CLICK:String = "gvSaveBttnClick";
		public static const GLOBAL_VARIABLE_TABLE_ENTRY_CHANGE:String = "gvTableEntryChange";
	}
}