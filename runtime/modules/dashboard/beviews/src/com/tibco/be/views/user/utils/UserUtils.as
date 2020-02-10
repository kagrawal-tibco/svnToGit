package com.tibco.be.views.user.utils{
	
	import com.tibco.be.views.core.ui.dashboard.IBEVContainer;
	import com.tibco.be.views.user.dashboard.BEVContentPane;
	import com.tibco.be.views.user.dialogs.IPopupWindow;
	
	import flash.display.DisplayObject;
	
	import mx.managers.PopUpManager;
	import com.tibco.be.views.utils.Logger;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	
	public class UserUtils{
		
		private static var _openWindows:Array;

		public static function getParentDashboard(panel:IBEVContainer):IBEVContainer{
			var parent:IBEVContainer = panel.containerParent;
			if(parent == null){ return null; }
			if(parent is BEVContentPane){ return parent; }
			return getParentDashboard(parent);
		}
		
	}
}