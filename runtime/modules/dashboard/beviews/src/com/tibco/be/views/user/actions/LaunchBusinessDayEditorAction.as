package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.dialogs.businessday.BusinessDayEditorController;

	public class LaunchBusinessDayEditorAction extends AbstractAction{
		
		
		public function LaunchBusinessDayEditorAction(){
			registerAction(CommandTypes.SHOW_DIALOG, "dialogname", "changebusdlg");
		}
		
		override public function execute(actionContext:ActionContext):void{
			var component:BEVComponent = actionContext.target as BEVComponent;
			var businessDayEditorController:BusinessDayEditorController = new BusinessDayEditorController(component);
			businessDayEditorController.init();
		}	
		
		override protected function createNewInstance():AbstractAction{
			return new LaunchBusinessDayEditorAction();
		}
		
	}
}