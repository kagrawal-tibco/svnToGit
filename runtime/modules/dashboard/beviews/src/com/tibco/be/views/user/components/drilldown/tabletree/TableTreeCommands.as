package com.tibco.be.views.user.components.drilldown.tabletree{
	
	/**
	 * The API for table tree commands is quite simple and does much interpretation of request
	 * context in order to determine what action is to be performed at the server. That contextual
	 * analysis is not available at the client, thus we maintain some state in our requests in order
	 * to simplify logic flow upon server response.
	*/
	public class TableTreeCommands{
		public static const INITIALIZE:int = 0;
		public static const EXPAND_ROW:int = 1;
		public static const COLLAPSE_ROW:int = 2;
		public static const SORT_COLUMN:int = 3;
		public static const APPLY_GROUPBY:int = 4;
		public static const PAGE_CHANGE:int = 5;
		
		public static function stringValue(command:int):String{
			switch(command){
				case(INITIALIZE): return "Initialize";
				case(EXPAND_ROW): return "ExpandRow";
				case(COLLAPSE_ROW): return "CollapseRow";
				case(SORT_COLUMN): return "SortColumn";
				case(APPLY_GROUPBY): return "ApplyGroupBy";
				case(PAGE_CHANGE): return "PageChange";
				default: return "";
			}
		}
	}
}