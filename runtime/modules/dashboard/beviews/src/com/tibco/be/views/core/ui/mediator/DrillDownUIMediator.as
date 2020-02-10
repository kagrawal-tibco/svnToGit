package com.tibco.be.views.core.ui.mediator{

	import com.tibco.be.views.core.ui.mediator.IUIController;
	
	public class DrillDownUIMediator extends BaseUIMediator{
		
		public function DrillDownUIMediator(){
			super();
		}
		
		override protected function buildUIController():IUIController{
			return new DrillDownUIController();
		}

	}
}