package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.dashboard.IBEVContainer;
	import com.tibco.be.views.user.dialogs.RenameDialog;
	import com.tibco.be.views.user.utils.UserUtils;
	
	import flash.display.DisplayObject;
	
	import mx.managers.PopUpManager;

	public class RenamePanelAction extends AbstractAction{
		
		private var panel:IBEVContainer;
		private var renameDialog:RenameDialog;
		
		public function RenamePanelAction(){
			super();
			registerAction(CommandTypes.SHOW_DIALOG, "dialogname", "renamedialog");
		}
		
		override public function execute(actionContext:ActionContext):void{
			panel = actionContext.target as IBEVContainer;
			var parentObject:DisplayObject = DisplayObject(UserUtils.getParentDashboard(panel));
			renameDialog = RenameDialog(PopUpManager.createPopUp(parentObject, RenameDialog, true));
			PopUpManager.centerPopUp(renameDialog);
			renameDialog.panelNameTxtFld.text = panel.containerTitle;
			renameDialog.controlButtonPanel.setOkButtonHandler(okClicked);
			renameDialog.controlButtonPanel.setCancelButtonHandler(cancelClicked);
		}	
		
		private function cancelClicked():void{
			PopUpManager.removePopUp(renameDialog);
		}	
		
		private function okClicked():void{
			var newPnlTitle:String = renameDialog.panelNameTxtFld.text;	
			PopUpManager.removePopUp(renameDialog);
			//update backend
			panel.containerTitle = newPnlTitle;
		}	
		
		override protected function createNewInstance():AbstractAction{
			return new RenamePanelAction();
		}			
	}
}