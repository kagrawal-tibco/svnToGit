package com.tibco.be.views.user.components.drilldown.tabletree.model{
	
	public class RootRowNode extends TableTreeRowNode{
		
		private static const XML_NODE_DEFINITION:XML = <rootNode id="" templateid="" visualtype="none" />;
		
		public function RootRowNode():void{
			super(XML_NODE_DEFINITION, null);
			_title = "_ROOT_";
		}

	}
}