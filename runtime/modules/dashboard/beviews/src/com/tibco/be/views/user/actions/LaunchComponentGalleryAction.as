package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.ui.gallery.view.ComponentGallery;
	import com.tibco.be.views.ui.gallery.view.GalleryWindow;
	import com.tibco.be.views.user.dashboard.BEVContentPane;
	import com.tibco.be.views.user.dashboard.FloatingBEVPanel;
	import com.tibco.be.views.user.dialogs.IPopupWindow;
	
	import flash.events.Event;
	
	import mx.containers.TitleWindow;
	import mx.core.Application;
	import mx.events.CloseEvent;

	public class LaunchComponentGalleryAction extends AbstractAction{
		
		private var _dashboard:BEVContentPane;
		private var _galleryWindow:FloatingBEVPanel;
		private var _componentGallery:ComponentGallery;
		
		public function LaunchComponentGalleryAction(){
			registerAction(CommandTypes.SHOW_DIALOG, "dialogname", "gallery");
		}
		
		override public function execute(actionContext:ActionContext):void{
			//Show Component Gallery
			if(_componentGallery != null){ return; }
			_dashboard = actionContext.target as BEVContentPane;
			_componentGallery = new ComponentGallery();
			_galleryWindow = new GalleryWindow();
			_galleryWindow.addChild(_componentGallery);
			_galleryWindow.addEventListener(CloseEvent.CLOSE, handleGalleryClose);
			centerWindow(_galleryWindow);
			Kernel.instance.uimediator.uicontroller.closeAllWindows();
			Kernel.instance.uimediator.uicontroller.openWindow(_galleryWindow as IPopupWindow, _dashboard, false);
		}
		
		private function handleGalleryClose(event:Event):void{
			Kernel.instance.uimediator.uicontroller.closeWindow(_galleryWindow as IPopupWindow);
			_componentGallery.destroy();
			_componentGallery = null;
		}
		
		private function centerWindow(window:FloatingBEVPanel):void{
	        window.x = Application.application.width/2 - window.width/2;
	        window.y = Application.application.height/2 - window.height/2;
		}
		
		override protected function createNewInstance():AbstractAction{
			return new LaunchComponentGalleryAction();
		}
	}
}