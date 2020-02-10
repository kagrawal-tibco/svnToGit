package com.tibco.be.views.core.ui.mediator{
	
	import com.tibco.be.views.core.ui.mediator.BaseUIMediator;
	import com.tibco.be.views.core.ui.mediator.IUIController;
	
	public class PortletUIMediator extends BaseUIMediator{
		
		public function PortletUIMediator(){
			super();
		}
		
		override protected function buildUIController():IUIController{
			return new PortletUIController();
		}

	}
}