package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.dialogs.filter.FilterEditor;
	
	import flash.display.DisplayObjectContainer;
	
	import mx.core.Application;
	import mx.events.CloseEvent;

	public class ShowFilterEditorAction extends AbstractAction{
		
		private var _filterEditor:FilterEditor;
		
		public function ShowFilterEditorAction(){
			registerAction(CommandTypes.SHOW_DIALOG, "dialogname", "filtereditor");
		}
		
		override protected function createNewInstance():AbstractAction{
			return new ShowFilterEditorAction();
		}
		
		override public function execute(actionContext:ActionContext):void{
			var component:BEVComponent = actionContext.target as BEVComponent;
			_filterEditor = new FilterEditor();
			Kernel.instance.uimediator.uicontroller.openWindow(_filterEditor, DisplayObjectContainer(Application.application), true);
			Kernel.instance.uimediator.uicontroller.centerWindow(_filterEditor);
			_filterEditor.initControler(actionContext);
			_filterEditor.addEventListener(CloseEvent.CLOSE, handleEditorClose);
		}
		
		private function handleEditorClose(event:CloseEvent):void{
			//trace("Closed Filter Editor");
			completionCleanUp();
		}
		
		private function completionCleanUp():void{
			_filterEditor.removeEventListener(CloseEvent.CLOSE, handleEditorClose);
			_filterEditor = null;
		}
		
	}
}