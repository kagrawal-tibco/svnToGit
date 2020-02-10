package com.tibco.cep.studio.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IThread;

import com.sun.jdi.ThreadReference;

public interface IRuleDebugThread extends IThread {
	
	public static final int PROCESS_STEP_INTO = 0x1;
	public static final int PROCESS_STEP_OVER = 0x2;
	public static final int PROCESS_STEP_RETURN = 0x4;
	
	/**
	 * When the underlying rule debug thread was not suspended, this status code
	 * should be used. 
	 */
	public static final int DEBUG_THREAD_NOT_SUSPENDED = 1000;
	
	/**
	 * While the rule debug thread is currently performing a method invocation, 
	 * use this status code to avoid nested evaluation. 
	 */
	public static final int DEBUG_THREAD_INVOCATION_INPROGRESS = 1001;
	
	/**
	 * Use this status code when stack frame has become invalid.
	 * Once the frame's thread is resumed, the stack frame is no longer valid.
	 */
	public static int STACK_FRAME_INVALID_ON_THREAD_RESUME = 1002;
	
	ThreadReference getUnderlyingThread();
	
	boolean hasAgendaItem() throws DebugException;
	
	

}
