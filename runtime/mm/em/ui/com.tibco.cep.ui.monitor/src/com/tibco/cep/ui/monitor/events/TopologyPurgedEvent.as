package com.tibco.cep.ui.monitor.events{
	
	import flash.events.Event;

	public class TopologyPurgedEvent extends Event{
		
		public function TopologyPurgedEvent(){
			super(EventTypes.TOPOLOGY_PURGED, true, false);
		}

	}
}