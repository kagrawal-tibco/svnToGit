package com.tibco.be.views.user.components.drilldown.tabletree.view{
	
	public class RowViewTypes{
		
		public static const NONE:String = "none";
		public static const TYPE:String = "TypeRow";
		public static const TABLE_HEADER:String = "TypeTableHeaderRow";
		public static const TABLE_DATA:String = "TypeDataRow";
		public static const GROUP_BY:String = "GroupByRow";
		
		public static function isValidVisualType(candidateType:String):Boolean{
			return (
				candidateType == NONE ||
				candidateType == TYPE ||
				candidateType == TABLE_HEADER ||
				candidateType == TABLE_DATA ||
				candidateType == GROUP_BY
			);
		}
		
	}
}