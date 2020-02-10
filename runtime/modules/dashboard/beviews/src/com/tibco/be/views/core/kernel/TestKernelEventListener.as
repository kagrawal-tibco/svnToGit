package com.tibco.be.views.core.kernel{
	
	import com.tibco.be.views.core.DefaultKernelEventListener;
	import com.tibco.be.views.core.Kernel;
	
	import flash.utils.Dictionary;

	public class TestKernelEventListener extends DefaultKernelEventListener{
		
		private var handler:Function;
		
		public function TestKernelEventListener(handler:Function){
			super();
			this.handler = handler;
		}
		
		override public function loaded(kernel:Kernel, bootParams:Dictionary):void{
			super.loaded(kernel,bootParams);
			handler();
		}
		
	}
}