package com.tibco.be.views.user.components.drilldown.tabletree.view{
	
	import com.tibco.be.views.user.components.drilldown.tabletree.model.TableTreeRowConfig;
	import com.tibco.be.views.user.components.drilldown.tabletree.model.TableTreeRowNode;
	
	/**
	 * The ‘group by row’ represents a single group by value. Each ‘group by row’ provides
	 * information like the ‘group by value’, count. It can contain one type table.
	*/
	public class GroupByRow extends TypeHeaderRow{
		
		public function GroupByRow(rowConfig:TableTreeRowConfig, data:TableTreeRowNode, parentRow:TableTreeRow, position:int, indentation:int=0){
			super(rowConfig, data, parentRow, position, indentation, true);
		}
		
		override protected function setContentContainerStyle():void{
			_content.styleName = "tableTreeGroupByContent";
		}
		
		override protected function setChildStyleNames():void{
			if(_titleLabel != null){ _titleLabel.styleName = "tableTreeGroupByRowTitle"; }
			if(_groupByLabel != null){ _groupByLabel.styleName = "tableTreeGroupByRowGroupByPrompt"; }
			if(_groupBySelector != null){ _groupBySelector.styleName = "tableTreeTypeHeaderGroupBySelector"; }
		}
		
	}
}