package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.ApplicationContainer;
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.controls.MessageBox;
	import com.tibco.be.views.core.ui.controls.ProgressDialog;
	
	import flash.display.DisplayObjectContainer;
	import flash.events.Event;
	
	import mx.core.Application;

	public class ServerCrashAction extends AbstractAction{
		
		private var _progressDialog:ProgressDialog;
		
		public function ServerCrashAction(shouldRegister:Boolean=true){
			super();
			if(shouldRegister){ registerAction(CommandTypes.SERVER_CRASH, null, null); }
		}
		
		override public function execute(actionContext:ActionContext):void{
			_progressDialog = ProgressDialog.show(
				DisplayObjectContainer(Application.application),
				"Server Crashed!",
				handleServerCrashed
			);
		}
		
		public function handleServerCrashed():void{
			_progressDialog.startMainTask("Reinitializing Dashboard Client...");
			ApplicationContainer.userLoggingOut(Session.instance.username);
			MessageBox.show(
				null,
				"ERROR",
				"Lost Connection to Dashboard Server.",
				handleAlertAck,
				"The server unexpectedly closed the updates socket connection. User interface resetting..."
			);
		}	
		
		public function handleAlertAck(event:Event=null):void{
			Kernel.instance.uimediator.uicontroller.closeAllWindows();
			_progressDialog.hide(errMsgBoxOkHandler);
		}
				
		private function errMsgBoxOkHandler():void{
			Kernel.instance.reset();
		}
		
		override protected function createNewInstance():AbstractAction{
			return new ServerCrashAction();
		}
	}
}