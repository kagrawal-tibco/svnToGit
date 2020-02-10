package com.tibco.be.views.user.components{
	
	public interface IComponentDataProvider{
		
		function setData(dataXML:XML):void;
		
		function updateData(updateXML:XML):void;
		
	}
}