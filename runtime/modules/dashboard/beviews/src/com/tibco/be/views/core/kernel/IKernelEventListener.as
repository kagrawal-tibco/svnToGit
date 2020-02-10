package com.tibco.be.views.core.kernel{
	
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	
	import flash.utils.Dictionary;
	
	/**
	 * The KernelEventListener represents the control mechanism which allows an external entity to decide the post 
	 * kernel loading sequence of operations. 
	 */ 
	public interface IKernelEventListener{
		
		/**
		 * Invoked when the kernel has been completely loaded 
		 * @param kernel The kernel which has been completely loaded 
		 * @param bootParams The boot parameters which were use to boot the kernel
		 */ 
		function loaded(kernel:Kernel, bootParams:Dictionary):void;
		
		/**
		 * Invoked when the kernel failes to load
		 * @param msg The error message
		 * @param errorEvent The error which causes the kernel loading to fail
		 */ 
		function errored(msg:String,errorEvent:ServerResponseEvent):void;
		
	}
}