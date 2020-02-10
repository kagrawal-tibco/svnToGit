package com.tibco.be.views.user.components.drilldown.tabletree.actions{
	
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.user.components.drilldown.tabletree.dialogs.DrillDownExportDialogController;
	
	import flash.display.DisplayObjectContainer;
	
	import mx.core.Application;

	public class ShowDrillDownExportDialogAction extends AbstractAction{
		
		public function ShowDrillDownExportDialogAction(){
			registerAction(CommandTypes.SHOW_DIALOG, "dialogname", "drilldownexport");
		}
		
		override protected function createNewInstance():AbstractAction{
			return new ShowDrillDownExportDialogAction();
		}
		
		override public function execute(actionContext:ActionContext):void{
			var controller:DrillDownExportDialogController = new DrillDownExportDialogController(DisplayObjectContainer(Application.application));
			controller.init(); 
		}
		
	}
}