package com.tibco.be.views.user.components.alert{
	
	import com.tibco.be.views.user.components.table.BEVTableComponent;

	public class AlertComponent extends BEVTableComponent{
		
		protected override function configureDataProvider(dataRowConfigXML:XML, componentConfig:XML):void {
			_dataProvider = new LimitingTableDataProvider(dataRowConfigXML,Number(componentConfig.textconfig.@maxrows));
		}			
	}
}