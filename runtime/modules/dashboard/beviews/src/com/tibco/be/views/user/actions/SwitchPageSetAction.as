package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.ActionRegistry;
	import com.tibco.be.views.core.ui.actions.IDelegatingAction;
	import com.tibco.be.views.core.ui.controls.MessageBox;
	import com.tibco.be.views.core.ui.controls.ProgressDialog;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.dashboard.BEVContentPane;
	import com.tibco.be.views.user.utils.UserUtils;
	
	import flash.display.DisplayObjectContainer;

	public class SwitchPageSetAction extends AbstractAction implements IDelegatingAction{
		
		private static const SIGN_OUT_ACTION_CONFIG:XML = new XML("<actionconfig command=\""+CommandTypes.SIGNOUT+"\"/>");
		
		private var newPageSetID:String;
		private var newPageSetName:String;
		private var existingPageSetID:String;
		
		private var actionCtx:ActionContext;
		private var taskName:String;
		private var processedItem:Object;
		private var progressDialog:ProgressDialog;
		private var retry:Boolean;
		private var dashboard:BEVContentPane;
		
		private var subTasksRunning:int;
		
		public function SwitchPageSetAction(){
			registerAction(CommandTypes.SWITCH_PAGESET);
			retry = false;
			subTasksRunning = 0;
		}
		
		override public function execute(actionContext:ActionContext):void{
			this.actionCtx = actionContext;
			newPageSetID = actionContext.getDynamicParamValue("new.pagesetid");
			newPageSetName = actionContext.getDynamicParamValue("new.pagesetname");
			existingPageSetID = Kernel.instance.uimediator.uicontroller.currentPageSetId;
			dashboard = BEVContentPane(UserUtils.getParentDashboard(BEVComponent(actionContext.target).componentContainer));
			processedItem ={id:newPageSetID,name:newPageSetName};
			taskName = "Switching to "+newPageSetName;
			progressDialog = ProgressDialog.show(DisplayObjectContainer(dashboard), "Switch Pageset...", executeSwitchPageSet);			
		}
		
		private function executeSwitchPageSet():void{
			progressDialog.startMainTask(taskName);
			Kernel.instance.uimediator.uicontroller.currentPageSetId = processedItem.id;
			Kernel.instance.uimediator.uicontroller.reload(this);
		}
		
		private function rollBackSwitchPageSet():void{
			//we set the retry flag on
			retry = true;
			//we set currently processed pageset to be the current working pageset
			processedItem ={id:existingPageSetID, name:"UNKNOWN"};
			//show dialog 
			taskName = "Resetting pageset to "+processedItem.name+"...";
			progressDialog = ProgressDialog.show(DisplayObjectContainer(dashboard), "Switch Pageset...", executeSwitchPageSet);
		}
		
		private function triggerLogOut():void{
			ActionRegistry.instance.getAction(SIGN_OUT_ACTION_CONFIG).execute(actionCtx);
		}				
		
		public function startMainTask(taskName:String,taskUnits:Number = -1):void{
			subTasksRunning++;
			progressDialog.startSubTask(taskName,taskUnits);
		}
		
		public function startSubTask(taskName:String,taskUnits:Number = -1):void{
			subTasksRunning++;
			progressDialog.startSubTask(taskName,taskUnits);
		}
		
		public function worked(taskUnits:Number):Boolean{
			if(progressDialog.worked(taskUnits) == true){
				subTasksRunning--;
				return true;
			}
			return false;
		}
		
		public function errored(errMsg:String,errorEvent:ServerResponseEvent):void{
			if(retry == false){
				progressDialog.hide();
				MessageBox.show(DisplayObjectContainer(dashboard), "Switch Pageset...", errMsg,rollBackSwitchPageSet,null,MessageBox.WARNING_TYPE);
			}
			else{
				MessageBox.show(DisplayObjectContainer(dashboard), "Switch Pageset...", "Could not reset pageset to "+processedItem.name,triggerLogOut);
			}
		}
		
		public function completedSubTask():void{
			progressDialog.completedSubTask();
			subTasksRunning--;
			if(subTasksRunning == 0){
				progressDialog.hide();
			}
		}
		
		public function completedMainTask():void{
			progressDialog.completedSubTask();
			subTasksRunning--;
			if(subTasksRunning == 0){
				progressDialog.hide();
			}
		}
		
		override protected function createNewInstance():AbstractAction{
			return new SwitchPageSetAction();
		}
	}
}