package com.tibco.be.views.user.components.drilldown.tabletree.view{
	
	public interface ITableTreeTableRow{
		function getRowCells():Array;
		function get contentWidth():Number;
		function get tableRowHScrollPosition():int;
		function set tableRowHScrollPosition(value:int):void;
	}
}