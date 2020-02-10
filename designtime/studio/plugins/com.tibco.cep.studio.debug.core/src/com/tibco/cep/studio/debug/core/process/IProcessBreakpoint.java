package com.tibco.cep.studio.debug.core.process;

import org.eclipse.core.runtime.CoreException;

public interface IProcessBreakpoint extends IGraphBreakpoint {
	
	static final String PROCESS_PLUGIN_ID = "com.tibco.cep.bpmn.core";
	
	static final String PROCESS_BREAKPOINT_MARKER_TYPE= PROCESS_PLUGIN_ID + ".ProcessBreakpointMarker"; //$NON-NLS-1$
	
	static final String PROCESS_INTERMEDIATE_BREAKPOINT_MARKER_TYPE = PROCESS_PLUGIN_ID +".ProcessIntermediateBreakpointMarker"; //$NON-NLS-1$
	
	static final String PROCESS_BREAKPOINT_PROCESS_URI= PROCESS_PLUGIN_ID +".processUri"; //$NON-NLS-1$
	
	static final String PROCESS_BREAKPOINT_NODE_ID= PROCESS_PLUGIN_ID +".nodeId"; //$NON-NLS-1$
	
	static final String PROCESS_BREAKPOINT_NODE_LOCATION= PROCESS_PLUGIN_ID +".nodeLocation"; //$NON-NLS-1$
	
	static final String PROCESS_BREAKPOINT_NODE_TASK_TYPE= PROCESS_PLUGIN_ID +".nodeTaskType"; //$NON-NLS-1$
	
	static final String PROCESS_BREAKPOINT_NODE_UNIQUE_ID= PROCESS_PLUGIN_ID +".nodeUniqueId"; //$NON-NLS-1$

	static final String PROCESS_INTERMEDIATE_BREAKPOINT_STEP_OVER = PROCESS_PLUGIN_ID +".IntermediateStepOver"; //$NON-NLS-1$;

	/**
	 * @deprecated Use {@link #PROCESS_INTERMEDIATE_BREAKPOINT_STEP_OVER} instead
	 */
	static final String PROCESS_INTERMEDIATE_BREAKPOINT_STEP_OUT = PROCESS_INTERMEDIATE_BREAKPOINT_STEP_OVER; //$NON-NLS-1$;
	
	
	
	
	public enum TASK_BREAKPOINT_LOCATION {
		START,
		END;			
	}
	
	IProcessBreakpointInfo  getProcessBreakpointInfo() throws CoreException;

	String getNodeId() throws CoreException;

	TASK_BREAKPOINT_LOCATION getNodeLocation() throws CoreException;
	
	String getNodeTaskType() throws CoreException;
	
	String getProcessUri() throws CoreException;

	



	

}
