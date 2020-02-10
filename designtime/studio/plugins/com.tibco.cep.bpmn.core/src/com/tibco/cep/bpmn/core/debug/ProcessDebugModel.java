package com.tibco.cep.bpmn.core.debug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.VMDisconnectedException;
import com.tibco.cep.studio.debug.core.model.AbstractDebugTarget;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpointInfo;
import com.tibco.cep.studio.debug.core.process.ProcessBreakpoint;
import com.tibco.cep.studio.debug.core.process.ProcessBreakpointInfo;
import com.tibco.cep.studio.debug.smap.TaskRegistryMapper;

public class ProcessDebugModel extends RuleDebugModel {

	private ProcessDebugModel() {
		super();
	}
	
	
	public static IProcessBreakpoint createProcessBreakpoint(IResource resource, String typeName, IProcessBreakpointInfo graphInfo, Map<String,Object> attributes) throws CoreException {
		return createProcessBreakpoint(resource, typeName, -1, -1, -1, 0, true, attributes, graphInfo.getProcessUri(),graphInfo.getGraphObjectId(), graphInfo.getLocation(),graphInfo.getTaskType(),graphInfo.getUniqueId());
	}
	
	public static IProcessBreakpoint createProcessIntermediateBreakpoint(IResource resource, String typeName, IProcessBreakpointInfo graphInfo, Map<String,Object> attributes) throws CoreException {
		return createProcessIntermediateBreakpoint(resource, typeName, -1, -1, -1, 0, false, attributes, graphInfo.getProcessUri(),graphInfo.getGraphObjectId(), graphInfo.getLocation(),graphInfo.getTaskType(),graphInfo.getUniqueId());
	}

	/**
	 * @param resource
	 * @param typeName
	 * @param lineNumber
	 * @param charStart
	 * @param charEnd
	 * @param hitCount
	 * @param register
	 * @param attributes
	 * @param nodeId
	 * @param nodeLocation
	 * @return
	 * @throws CoreException
	 */
	public static ProcessBreakpoint createProcessBreakpoint(IResource resource, String typeName, int lineNumber, int charStart, int charEnd, int hitCount,
			boolean register, Map<String,Object> attributes, String processURI,String nodeId, IProcessBreakpoint.TASK_BREAKPOINT_LOCATION nodeLocation,String nodeTaskType,int uniqueId) throws CoreException {
		if (attributes == null) {
			attributes = new HashMap<String,Object>(10);
		}
		return new ProcessBreakpoint(resource, typeName, lineNumber, charStart, charEnd, hitCount, register, attributes, RuleBreakpoint.SUSPEND_THREAD,
				processURI,nodeId, nodeLocation,nodeTaskType, uniqueId);
	}
	/**
	 * @param resource
	 * @param typeName
	 * @param lineNumber
	 * @param charStart
	 * @param charEnd
	 * @param hitCount
	 * @param register
	 * @param attributes
	 * @param nodeId
	 * @param nodeLocation
	 * @return
	 * @throws CoreException
	 */
	public static ProcessIntermediateBreakpoint createProcessIntermediateBreakpoint(IResource resource, String typeName, int lineNumber, int charStart, int charEnd, int hitCount,
			boolean register, Map<String,Object> attributes, String processURI,String nodeId, IProcessBreakpoint.TASK_BREAKPOINT_LOCATION nodeLocation,String nodeTaskType,int numTasks) throws CoreException {
		if (attributes == null) {
			attributes = new HashMap<String,Object>(10);
		}
		return new ProcessIntermediateBreakpoint(resource, typeName, lineNumber, charStart, charEnd, hitCount, register, attributes, RuleBreakpoint.SUSPEND_THREAD,
				processURI,nodeId, nodeLocation,nodeTaskType, numTasks);
	}

	/**
	 * @param typeName
	 * @param bpInfo
	 * @return
	 * @throws CoreException
	 */
	public static IProcessBreakpoint graphBreakpointExists(String typeName, IProcessBreakpointInfo bpInfo) throws CoreException {
		String modelId= getModelIdentifier();
		String markerType= IProcessBreakpoint.PROCESS_BREAKPOINT_MARKER_TYPE;
		IBreakpointManager manager= DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint[] breakpoints= manager.getBreakpoints(modelId);
		
		for (int i = 0; i < breakpoints.length; i++) {
			if (!(breakpoints[i] instanceof IProcessBreakpoint)) {
				continue;
			}
			IProcessBreakpoint breakpoint = (IProcessBreakpoint) breakpoints[i];
			IMarker marker = breakpoint.getMarker();
			if (marker != null && marker.exists() && marker.getType().equals(markerType)) {
				String breakpointTypeName = breakpoint.getTypeName();
				if ((breakpointTypeName.equals(typeName)) &&
					breakpoint.getNodeId().equals(bpInfo.getGraphObjectId())&&
					breakpoint.getNodeLocation().equals(bpInfo.getLocation())) {
						return breakpoint;
				}
			}
		}
		return null;
	}
	
	public static IProcessBreakpoint getNextGraphBreakpoint(String typeName, IProcessBreakpointInfo bpInfo) throws CoreException {
		String modelId= getModelIdentifier();
		String markerType= IProcessBreakpoint.PROCESS_BREAKPOINT_MARKER_TYPE;
		IBreakpointManager manager= DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint[] breakpoints= manager.getBreakpoints(modelId);
		if(bpInfo.getLocation() == IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.START) {
			for (int i = 0; i < breakpoints.length; i++) {
				if (!(breakpoints[i] instanceof IProcessBreakpoint)) {
					continue;
				}
				IProcessBreakpoint breakpoint = (IProcessBreakpoint) breakpoints[i];
				IMarker marker = breakpoint.getMarker();
				if (marker != null && marker.exists() && marker.getType().equals(markerType)) {
					String breakpointTypeName = breakpoint.getTypeName();
					if ((breakpointTypeName.equals(typeName)) &&
							breakpoint.getNodeId().equals(bpInfo.getGraphObjectId())&&
							breakpoint.getNodeLocation().equals(IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.END)) {
						return breakpoint;
					}
				}
			}

		}
		return null;
	}
	
	/**
	 * @param rdt
	 * @return
	 * @throws DebugException
	 */
	public static IProcessBreakpointInfo getProcessBreakPointInfo(RuleDebugThread rdt) throws DebugException {
		synchronized (rdt) {
			rdt.computeNewStackFrames();
			IStackFrame frame =  rdt.getTopStackFrame();
			RuleDebugStackFrame rdFrame = (RuleDebugStackFrame) frame.getAdapter(RuleDebugStackFrame.class);
			if(rdFrame != null) {
				return getProcessBreakPointInfo(rdFrame);
			}
			
		}
		return null;
	}
	
	/**
	 * @param rdt
	 * @param frame
	 * @return
	 * @throws DebugException
	 */
	public static IProcessBreakpointInfo getProcessBreakPointInfo(RuleDebugStackFrame frame) throws DebugException {
		IProcessBreakpointInfo bpInfo = null;
		final IThread thread = frame.getThread();
		final RuleDebugThread rdt = (RuleDebugThread) thread.getAdapter(RuleDebugThread.class);
		if(rdt != null) {
			synchronized (rdt) {
				
				IDebugTarget target = frame.getDebugTarget();
				String javaSourceName = frame.getDeclaringTypeName();
				// find the task type
				TaskRegistryMapper mapper = ((AbstractDebugTarget) target).getTaskRegistryMapper();
				String taskType = mapper.inverseMap().get(javaSourceName);
				Location location = (Location) frame.getAdapter(Location.class);
				try {
					if (taskType != null) {
						Method m = frame.getUnderlyingMethod();
						List<Location> allLocs = m.allLineLocations();
						IProcessBreakpoint.TASK_BREAKPOINT_LOCATION bploc = null;
						for (int i = 0; i < allLocs.size(); i++) {
							Location l = allLocs.get(i);
							if (location.equals(l) && (i == 0)) {
								bploc = IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.START;
								break;
							}
							if (location.equals(l) && (i == (allLocs.size() - 1))) {
								bploc = IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.END;
								break;
							}
						}
						if(bploc == null) {
							return null;
						}
						ObjectReference thisObject = frame.getUnderlyingThisObject();
						
						String taskId = DebuggerSupport.getProcessTaskIdFromBreakpoint(rdt, thisObject);
						int uniqueId = DebuggerSupport.getProcessTaskUniqueIdFromBreakpoint(rdt, thisObject);
						IVariable[] vars = frame.getVariables();
						if(vars.length > 2) {
							String processUri = DebuggerSupport.getProcessUriFromBreakpoint(rdt, (ObjectReference) vars[1].getAdapter(ObjectReference.class));
							if ( processUri.contains("$") ) {
								processUri = processUri.substring(0,processUri.lastIndexOf( "$" )) ;
							}
//							int numTasks = DebuggerSupport.getNumTasksFromBreakpoint(rdt, (ObjectReference) vars[1].getAdapter(ObjectReference.class));
							bpInfo = new ProcessBreakpointInfo(processUri, taskId, taskType, bploc, uniqueId);
						} else {
							frame.targetRequestFailed("ProcessContext not available", new Exception());
						}
					}
				} catch(AbsentInformationException e) {
					frame.targetRequestFailed("Failed to get node Location", e);
				} catch(VMDisconnectedException e) {
					//TODO
				} catch(DebugException e) {
					//TODO
				}
			}
			
		}
		return bpInfo;
	}
	
	

	

}
