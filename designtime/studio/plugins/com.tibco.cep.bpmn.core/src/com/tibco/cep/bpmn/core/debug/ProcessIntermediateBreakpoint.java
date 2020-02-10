package com.tibco.cep.bpmn.core.debug;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.core.model.IBreakpoint;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotPreparedException;
import com.sun.jdi.ClassType;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.NativeMethodException;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.request.EventRequest;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.IRuleDebugThread;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.RuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleDebugElement;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpointInfo;
import com.tibco.cep.studio.debug.core.process.ProcessBreakpoint;
import com.tibco.cep.studio.debug.core.process.ProcessBreakpointInfo;

public class ProcessIntermediateBreakpoint extends ProcessBreakpoint implements IProcessBreakpoint {


	public ProcessIntermediateBreakpoint() {
		super();
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
	 * @param uniqueId TODO
	 * @param suspendThread
	 */
	public ProcessIntermediateBreakpoint(final IResource resource, final String typeName, final int lineNumber, final int charStart, final int charEnd, final int hitCount,
			final boolean register, final Map<String,Object> attributes, final int suspendPolicy, final String uri,final String nodeId, final TASK_BREAKPOINT_LOCATION nodeLocation,final String nodeTaskType,final int uniqueId)
			throws CoreException {
		IWorkspaceRunnable wr = new IWorkspaceRunnable() {

			public void run(IProgressMonitor monitor) throws CoreException {
				// create the marker
				setMarker(resource.createMarker(PROCESS_INTERMEDIATE_BREAKPOINT_MARKER_TYPE));

				// add attributes
				attributes.put(IBreakpoint.ID, getModelIdentifier());
				attributes.put(IMarker.CHAR_START, new Integer(charStart));
				attributes.put(IMarker.CHAR_END, new Integer(charEnd));
				attributes.put(IMarker.LINE_NUMBER, new Integer(lineNumber));
				attributes.put(TYPE_NAME, typeName);
				attributes.put(ENABLED, Boolean.TRUE);
				attributes.put(SUSPEND_POLICY, new Integer(suspendPolicy));
				attributes.put(PROCESS_BREAKPOINT_PROCESS_URI, uri);
				attributes.put(PROCESS_BREAKPOINT_NODE_ID, nodeId);
				attributes.put(PROCESS_BREAKPOINT_NODE_LOCATION, nodeLocation.name());
				attributes.put(PROCESS_BREAKPOINT_NODE_TASK_TYPE, nodeTaskType);
				attributes.put(PROCESS_BREAKPOINT_NODE_UNIQUE_ID, uniqueId);

				
				// add attributes
				//addLineBreakpointAttributes(attributes, getModelIdentifier(), true, lineNumber, charStart, charEnd);
				addTypeNameAndHitCount(attributes, typeName, hitCount);
				setEnabled(true);
				setAttribute(IMarker.MESSAGE,getMarkerMessage(resource,nodeId,nodeLocation));
				
				
				// set attributes
				//attributes.put(SUSPEND_POLICY, new Integer(getDefaultSuspendPolicy()));
				ensureMarker().setAttributes(attributes);

				register(register);
			}

		};
		run(getMarkerRule(resource), wr);
	}


	
	/* (non-Javadoc)
	 * 
	 * Not supported for class prepare breakpoints.
	 * 
	 * @see org.eclipse.jdt.internal.debug.core.breakpoints.JavaBreakpoint#setRequestThreadFilter(com.sun.jdi.request.EventRequest, com.sun.jdi.ThreadReference)
	 */
	protected void setRequestThreadFilter(EventRequest request, ThreadReference thread) {
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaBreakpoint#supportsInstanceFilters()
	 */
	public boolean supportsInstanceFilters() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaBreakpoint#setThreadFilter(org.eclipse.jdt.debug.core.IRuleDebugThread)
	 */
	public void setThreadFilter(IRuleDebugThread thread) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, RuleDebugModel.getPluginIdentifier(), DebugException.REQUEST_FAILED, "Intermediate breakpoints does not support thread filters", null)); 
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaBreakpoint#supportsThreadFilters()
	 */
	public boolean supportsThreadFilters() {
		return false;
	}
	
	
	
	@Override
	public IProcessBreakpointInfo getProcessBreakpointInfo() throws CoreException {
		return new ProcessBreakpointInfo(getProcessUri(),getNodeId(),getNodeTaskType(), getNodeLocation(),getUniqueId());
	}
	
	
	
	private int getUniqueId() throws DebugException {
		return ensureMarker().getAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_UNIQUE_ID,-1);
	}

	@Override
	public String getProcessUri() throws CoreException {
		return ensureMarker().getAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_PROCESS_URI,null);
	}
	@Override
	public String getNodeId() throws CoreException {
		return ensureMarker().getAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_ID,null);
	}
	
	@Override
	public TASK_BREAKPOINT_LOCATION getNodeLocation() throws CoreException {
		String loc = ensureMarker().getAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_LOCATION,"");
		return TASK_BREAKPOINT_LOCATION.valueOf(loc);
	}
	
	@Override
	public String getNodeTaskType() throws CoreException {
		return ensureMarker().getAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_TASK_TYPE,null);
	}
	
	/**
	 * Add this breakpoint to the breakpoint manager,
	 * or sets it as unregistered.
	 */
	protected void register(boolean register) throws CoreException {
        DebugPlugin plugin = DebugPlugin.getDefault();
		if (plugin != null && register) {
			IBreakpoint[] bps = plugin.getBreakpointManager().getBreakpoints(getModelIdentifier());
			for(IBreakpoint bp: bps) {
				if(bp instanceof ProcessBreakpoint) {
					IProcessBreakpoint pbp = (IProcessBreakpoint) bp;
					if(pbp.getTypeName().equals(getTypeName()) &&
					 pbp.getNodeId() == getNodeId() &&
					 pbp.getNodeLocation() == getNodeLocation()) {
						return;
					}
				}
			}
            plugin.getBreakpointManager().addBreakpoint(this);
		} else {
			setRegistered(false);
		}
	}
	
	public String getId() {
		try {
			if(getMarker() != null && getMarker().getResource() != null) {
				String nodeID = getNodeId();
				TASK_BREAKPOINT_LOCATION nodeLocation = getNodeLocation();
				String typeName = getTypeName();
				return String.format("%s:%s:%s", nodeID,nodeLocation,typeName);
			}
			return null;
		} catch (CoreException e) {
			StudioDebugCorePlugin.log(e);
			return null;
		}
	}
	
	/**
	 * Returns the type of marker associated with Java line breakpoints
	 */
	public String getMarkerType() {
		return PROCESS_BREAKPOINT_MARKER_TYPE;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((fInstalledTargets == null) ? 0 : fInstalledTargets
						.hashCode());
		try {
			int nodeHashCode = (getNodeId() != null) ? getNodeId().hashCode():0;
			int nodeLocationHashCode = (getNodeLocation() != null) ? getNodeLocation().hashCode():0;			
			int nodeTaskTypeHashCode = (getNodeTaskType() != null) ? getNodeTaskType().hashCode():0;			
			result = prime * result + nodeHashCode + nodeLocationHashCode+nodeTaskTypeHashCode ;
		} catch (CoreException e) {
			result = prime * result;
		}
		return result;
	}
	
	protected String getMarkerMessage(IResource resource, String nodeId, TASK_BREAKPOINT_LOCATION nodeLocation) throws DebugException, CoreException {
		StringBuffer message = new StringBuffer(MessageFormat.format("[Resource: {0}",new Object[]{resource.getName()}));
		message.append(getMarkerMessage(false, null, getHitCount(), getSuspendPolicy(), nodeId,nodeLocation.name()));
		return message.toString();
	}
	
	
	protected String getMarkerMessage(boolean conditionEnabled, String condition, int hitCount, int suspendPolicy, String nodeId,String nodeLoc) {
		StringBuffer message= new StringBuffer(getMarkerMessage(hitCount, suspendPolicy));
		if (nodeId != null && nodeLoc != null) {
			message.append(MessageFormat.format("[node: {0}:{1}]", new Object[]{nodeId,nodeLoc})); 
		}
		if (conditionEnabled && condition != null) {
			message.append(MessageFormat.format("[condition: {0}]", new Object[]{condition})); 
		}
			
		return message.toString();
	}
	
	
	protected String getMarkerMessage(int hitCount, int suspendPolicy) {
		StringBuffer buff= new StringBuffer();
		if (hitCount > 0){
			buff.append(MessageFormat.format("[hit count: {0}]", new Object[]{Integer.toString(hitCount)})); 
			buff.append(' ');
		}
		String suspendPolicyString;
		if (suspendPolicy == RuleBreakpoint.SUSPEND_THREAD) {
			suspendPolicyString= "[suspend policy: thread]"; 
		} else {
			suspendPolicyString= "[suspend policy: VM]"; 
		}
		
		buff.append(suspendPolicyString);
		return buff.toString();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.core.breakpoints.JavaBreakpoint#newRequest(org.eclipse.jdt.internal.debug.core.model.RuleDebugTarget, com.sun.jdi.ReferenceType)
	 */
	protected EventRequest[] newRequests(IRuleRunTarget target, String referenceTypeName) throws CoreException {
		IProcessBreakpointInfo pbInfo = getProcessBreakpointInfo();			
		List<?> locations = determineLocations(pbInfo, referenceTypeName, target);
		if (locations == null || locations.isEmpty()) {
			// could be an inner type not yet loaded, or line information not available
			return null;
		}
		EventRequest[] requests = new EventRequest[locations.size()];
		int i = 0;
	    Iterator<?> iterator = locations.iterator();
	    while (iterator.hasNext()) {
	        Location location = (Location) iterator.next();
	        requests[i] = createLineBreakpointRequest(location, target);
	        i++;
	    }
	    return requests;
	}	
	
	protected void configureRequestProperties(EventRequest request) throws CoreException {
		super.configureRequestProperties(request);
		request.putProperty(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_ID,getNodeId());
		request.putProperty(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_LOCATION,getNodeLocation());
		request.putProperty(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_TASK_TYPE,getNodeTaskType());
		request.putProperty(IProcessBreakpoint.PROCESS_BREAKPOINT_PROCESS_URI,getProcessUri());
		request.putProperty(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_UNIQUE_ID,getUniqueId());
	}
	
	
	
	/**
	 * Returns a list of locations of the given line number in the given type.
	 * Returns <code>null</code> if locations cannot be determined.
	 * @throws CoreException 
	 */
	protected List<?> determineLocations(IProcessBreakpointInfo pbInfo, String referenceTypeName, IRuleRunTarget target) throws CoreException {
		List<Location> locations=new ArrayList<Location>();
		if(target.getTaskRegistryMapper() == null) {
			throw new CoreException(new Status(IStatus.ERROR,BpmnCorePlugin.getUniqueIdentifier(),"Source map not found."));
		}
		try {
//			ReferenceType taskRegistryreferenceType = DebuggerSupport.findClass(target.getSession().getVM(),"com.tibco.cep.bpmn.runtime.activity.TaskRegistry");
//			Method m = DebuggerSupport.findMethodBySignature((ClassType) taskRegistryreferenceType, "getInstance", "()V");
			String className = target.getTaskRegistryMapper().get(pbInfo.getTaskType());
			if(className != null && !className.isEmpty()) {
				ReferenceType clazz = DebuggerSupport.findClass(target.getSession().getVM(), className);
				if(clazz != null) {
					Method m = DebuggerSupport
							.findMethodBySignature(
									(ClassType) clazz,
									"execute",
									"(Lcom/tibco/cep/bpmn/runtime/agent/Job;Lcom/tibco/cep/bpmn/runtime/utils/Variables;Lcom/tibco/cep/bpmn/runtime/activity/Task;)Lcom/tibco/cep/bpmn/runtime/activity/TaskResult;");
					Location bpLocation = null;
					if(clazz != null) {
						List<Location> locs = m.allLineLocations();
						if(pbInfo.getLocation() == TASK_BREAKPOINT_LOCATION.START) {
							bpLocation = locs.get(0); // first line
						} else {
							bpLocation = locs.get(locs.size()-1); // last line
						}
						locations.add(bpLocation);
						setAttribute(IMarker.LINE_NUMBER, new Integer(bpLocation.lineNumber()));
						fireChanged();
					}
				} else {
					throw new CoreException(new Status(IStatus.ERROR,BpmnCorePlugin.getUniqueIdentifier(),String.format("Class:%s not found in debug VM",className)));
				}
			} else {
				throw new CoreException(new Status(IStatus.ERROR,BpmnCorePlugin.getUniqueIdentifier(),String.format("Invalid Class:%s specified",className)));
			}
			
		} catch (AbsentInformationException aie) {
			IStatus status = new Status(IStatus.ERROR, StudioDebugCorePlugin
					.getUniqueIdentifier(), NO_LINE_NUMBERS,
					"Absent Line Number Information", null);  
			IStatusHandler handler= DebugPlugin.getDefault().getStatusHandler(status);
			if (handler != null) {
				try {
					handler.handleStatus(status, referenceTypeName);
				} catch (CoreException e) {
				}
			}
			return null;
		} catch (NativeMethodException e) {
			return null;
		} catch (VMDisconnectedException e) {
			return null;
		} catch (ClassNotPreparedException e) {
			// could be a nested type that is not yet loaded
			return null;
		} catch (RuntimeException e) {
			// not able to retrieve line information
			((RuleDebugElement)target).internalError(e);
			return null;
		}
		return locations;
	}
}