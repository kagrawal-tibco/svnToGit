package com.tibco.be.views.user.components.processmodel{
	import com.tibco.be.views.user.components.processmodel.view.PMEdge;
	
	import mx.core.IContainer;
	import mx.events.EffectEvent;
	
	
	public interface IPMEdgeContainer extends IContainer{
		
		function get absoluteX():int;
		function get absoluteY():int;
		function get edges():Array;
		function set edges(value:Array):void;
		function get parentPMContainer():IPMEdgeContainer;
		function addEdge(edge:PMEdge):void;
		function removeEffectComponent(event:EffectEvent):void;
		
	}
}