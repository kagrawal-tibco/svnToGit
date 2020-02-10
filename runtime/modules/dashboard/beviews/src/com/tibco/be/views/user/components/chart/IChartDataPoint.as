package com.tibco.be.views.user.components.chart{
	
	import flash.utils.Dictionary;
	
	public interface IChartDataPoint{
		function get colID():String;
		function get value():Number;
		function get valueObj():Object;
		function get link():String;
		function get typeSpecificAttribs():Dictionary;
	}
	
}