package com.tibco.cep.ui.monitor.events{
	
	import flash.events.Event;

	public class TopologyUpdateEvent extends Event{
		private var _topology:XML;
		public function TopologyUpdateEvent(newTopology:XML){
			super(EventTypes.TOPOLOGY_UPDATE, true, false);
			_topology = newTopology;
		}
		public function get topology():XML{ return _topology; }

	}
}