package com.tibco.be.views.user.actions{
	
	public class ActionRegistrar{
		
		public static function registerAllActions():void{
			
			//NOTE: The constructors for these actions call registerAction which adds them to the
			//		ActionRegistry
			
			//dashboard actions
			var launchComponentGalleryAction:LaunchComponentGalleryAction = new LaunchComponentGalleryAction();
			var saveDashboardAction:SaveDashboardAction = new SaveDashboardAction();
			var assetPageLauncherLinkAction:PageLauncherLinkAction = new PageLauncherLinkAction(PageLauncherLinkAction.SEARCH);
			var searchPageLauncherLinkAction:PageLauncherLinkAction = new PageLauncherLinkAction(PageLauncherLinkAction.ASSET);
			var signoutAction:SignoutAction = new SignoutAction();
			var switchRoleAction:SwitchRoleAction = new SwitchRoleAction();
			var switchPageSetAction:SwitchPageSetAction = new SwitchPageSetAction();
			var recurringAction:RecurringAction = new RecurringAction();
			
			//panel actions
			var renamePanelAction:RenamePanelAction = new RenamePanelAction();
			
			//component actions 
			var launchBusinessDayEditorAction:LaunchBusinessDayEditorAction = new LaunchBusinessDayEditorAction();
			
			//chart actions 
			var cloneComponentAction:CloneComponentAction = new CloneComponentAction();
			var removeComponentAction:RemoveComponentAction = new RemoveComponentAction();
			var showRelatedComponentAction:ShowRelatedComponentAction = new ShowRelatedComponentAction();
			var showComponentOverlayAction:ShowComponentOverlayAction = new ShowComponentOverlayAction();
			var launchComponentHelpAction:LaunchComponentHelpAction = new LaunchComponentHelpAction();
			var launchInternalLinkAction:LaunchInternalLinkAction = new LaunchInternalLinkAction();
			var launchExternalLinkAction:LaunchExternalLinkAction = new LaunchExternalLinkAction();
			var showFilterEditorAction:ShowFilterEditorAction = new ShowFilterEditorAction();

		}
	}
}