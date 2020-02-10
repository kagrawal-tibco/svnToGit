package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.dashboard.AbstractBEVPanel;
	import com.tibco.be.views.user.utils.UserUtils;
	
	import flash.display.Sprite;
	
	import mx.controls.Alert;

	public class CloneComponentAction extends AbstractAction{
		
		public function CloneComponentAction(){
			registerAction(CommandTypes.SHOW_DIALOG, "dialogname", "metriccloner");
		}
		
		override public function execute(actionContext:ActionContext):void{
			var component:BEVComponent = actionContext.target as BEVComponent;
			var panel:AbstractBEVPanel = component.componentContainer as AbstractBEVPanel;
			Alert.show("Show Progress Dialog And Launch Clone API...","Clone Component...", Alert.OK, Sprite(UserUtils.getParentDashboard(panel)));
		}
		
		override protected function createNewInstance():AbstractAction{
			return new CloneComponentAction();
		}			
	}
}