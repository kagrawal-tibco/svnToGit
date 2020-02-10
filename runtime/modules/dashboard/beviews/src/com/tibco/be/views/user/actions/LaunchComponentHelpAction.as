package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.dashboard.AbstractBEVPanel;
	import com.tibco.be.views.user.dashboard.BEVContentPane;
	import com.tibco.be.views.user.dashboard.FloatingBEVPanel;
	import com.tibco.be.views.user.utils.UserUtils;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObject;
	
	import mx.controls.TextArea;

	public class LaunchComponentHelpAction extends AbstractAction{
		
		public function LaunchComponentHelpAction(){
			registerAction(CommandTypes.SHOW_DIALOG, "dialogname", "helpdialog");
		}
		
		override public function execute(actionContext:ActionContext):void{
			var component:BEVComponent = actionContext.target as BEVComponent;
			var panel:AbstractBEVPanel = component.componentContainer as AbstractBEVPanel;
			var dashboard:BEVContentPane = UserUtils.getParentDashboard(panel) as BEVContentPane;
			
			var helpText:String = actionContext.getDynamicParamValue(DynamicParamsResolver.CURRENTCOMPONENT_HELP_PARAM);
			helpText = helpText.replace(new RegExp("\r\n", "g"), "<br />");
			helpText = helpText.replace(new RegExp("\n", "g"), "<br />");
			helpText = helpText.replace(new RegExp("\t", "g"), "&nbsp;&nbsp;&nbsp;&nbsp;");
			Logger.log(DefaultLogEvent.DEBUG, "LaunchComponentHelpAction.execute - Showing Help Message: " + helpText);
			
			var textArea:TextArea = new TextArea();
			textArea.setStyle("focusSkin", null);
			textArea.setStyle("borderStyle", "none");
			textArea.htmlText = helpText;
			textArea.wordWrap = true;
			textArea.editable = false;
			textArea.percentHeight = 100;
			textArea.percentWidth = 100;
			
			var floatingPanel:FloatingBEVPanel = new FloatingBEVPanel();
			floatingPanel.containerParent = dashboard;
			floatingPanel.containerTitle = component.componentTitle;
			floatingPanel.addChild(textArea);
			floatingPanel.width = 200;
			floatingPanel.height = 200;
			
			Kernel.instance.uimediator.uicontroller.openWindow(floatingPanel, DisplayObject(dashboard), false);
			floatingPanel.move(20,20);
		}
		
		override protected function createNewInstance():AbstractAction{
			return new LaunchComponentHelpAction();
		}			
	}
}