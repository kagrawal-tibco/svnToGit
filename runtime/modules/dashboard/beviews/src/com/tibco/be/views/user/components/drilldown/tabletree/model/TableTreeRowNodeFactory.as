package com.tibco.be.views.user.components.drilldown.tabletree.model{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.drilldown.tabletree.view.RowViewTypes;
	import com.tibco.be.views.utils.Logger;
	
	public class TableTreeRowNodeFactory{
		
		public static function createRowNode(datarow:XML, parent:TableTreeRowNode, actionConfig:XML=null):TableTreeRowNode{
			if(datarow.name() == "textmodel"){
				return new TypeHeaderRowNode(datarow, parent, actionConfig);
			}
			else{
				switch(String(datarow.@visualtype)){
					case(RowViewTypes.TYPE):
					case(RowViewTypes.GROUP_BY):
						return new TypeHeaderRowNode(datarow, parent, actionConfig);
					case(RowViewTypes.TABLE_HEADER):
					case(RowViewTypes.TABLE_DATA):
						return new TableTreeRowNode(datarow, parent, actionConfig);
					default:
						Logger.log(
							DefaultLogEvent.WARNING,
							"TableTreeRowNodeFactory.createRowNode - Skipping data creation for row with invalid type: " + String(datarow.@visualtype)
						);
						return null;
				}
			}
			return null;
		}

	}
}