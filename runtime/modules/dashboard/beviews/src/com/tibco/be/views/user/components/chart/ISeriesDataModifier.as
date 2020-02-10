package com.tibco.be.views.user.components.chart{
	
	public interface ISeriesDataModifier{
		
		function modifyDataXML(dataXML:XML):void;
		function modifyData(dataColumn:DataColumn):void;
		
	}
}