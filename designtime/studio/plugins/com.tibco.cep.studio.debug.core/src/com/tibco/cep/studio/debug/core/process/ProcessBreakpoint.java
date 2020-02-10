package com.tibco.cep.studio.debug.core.process;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.core.model.IBreakpoint;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotPreparedException;
import com.sun.jdi.ClassType;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.NativeMethodException;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.RuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleDebugElement;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;

public class ProcessBreakpoint extends RuleBreakpoint implements IProcessBreakpoint {


	public ProcessBreakpoint() {
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
	public ProcessBreakpoint(final IResource resource, final String typeName, final int lineNumber, final int charStart, final int charEnd, final int hitCount,
			final boolean register, final Map<String,Object> attributes, final int suspendPolicy, final String uri,final String nodeId, final TASK_BREAKPOINT_LOCATION nodeLocation,final String nodeTaskType,final int uniqueId)
			throws CoreException {
		IWorkspaceRunnable wr = new IWorkspaceRunnable() {

			public void run(IProgressMonitor monitor) throws CoreException {
				// create the marker
				setMarker(resource.createMarker(PROCESS_BREAKPOINT_MARKER_TYPE));

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


	
	
	/**
	 * Returns a list of locations of the given line number in the given type.
	 * Returns <code>null</code> if locations cannot be determined.
	 * @throws CoreException 
	 */
	protected List<?> determineLocations(int lineNumber, String referenceTypeName, IRuleRunTarget target) throws CoreException {
		List<?> locations= null;
		if(target.getSourceMapper() == null) {
			throw new CoreException(new Status(IStatus.ERROR,StudioDebugCorePlugin.getUniqueIdentifier(),"Source map not found."));
		}
		try {
			
			ReferenceType referenceType = DebuggerSupport.findClass(target.getSession().getVM(),referenceTypeName);
			if(referenceType != null) {
				locations= referenceType.locationsOfLine(lineNumber);
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
				if(bp instanceof IProcessBreakpoint) {
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
	 * Creates, installs, and returns a line breakpoint request at
	 * the given location for this breakpoint.
	 */
	protected BreakpointRequest createLineBreakpointRequest(Location location, IRuleRunTarget target) throws CoreException {
		BreakpointRequest request = null;
		EventRequestManager manager = target.getSession().getEventRequestManager();
		if (manager == null) {
			((RuleDebugElement)target).requestFailed("Unable to create breakpoint request - VM disconnected.", null);  
		}
		try {
			List<BreakpointRequest> requests = manager.breakpointRequests();
			boolean skip = false;
			for(BreakpointRequest br:requests) {
				IRuleBreakpoint bpt = (IRuleBreakpoint) br.getProperty(RULE_BREAKPOINT_PROPERTY);
				if(bpt == this ) { 
					// dont create a duplicate breakpoint request
					// it will end up returning multiple breakpoint
					//events for the same location
					StudioDebugCorePlugin.debug("Skipping breakpoint request at location:"+location);
					//skip = true;
					request = br;
					break;
				}
			}
			if(!skip) {
				request= manager.createBreakpointRequest(location);
				StudioDebugCorePlugin.debug("Creating breakpoint request at location:"+location);
				configureRequest(request, target);
			}
		} catch (VMDisconnectedException e) {
			if (!target.isAvailable()) {			
				return null;
			} 
			StudioDebugCorePlugin.log(e);
		} catch (RuntimeException e) {
			((RuleDebugElement)target).internalError(e);
			return null;
		}
		return request;
	}
	
	IProject getProject() throws DebugException {
		return ensureMarker().getResource().getProject();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.RuleBreakpoint#setEnabled(boolean)
	 * Removes the breakpoint on disabling it .
	 */
	public void setEnabled(boolean enabled) throws CoreException {
		super.setEnabled(enabled);
		if(!enabled && this.getTypeName()!=null &&this.getProcessBreakpointInfo()!=null ){
			IGraphBreakpoint existingBreakpoint = graphBreakpointExists(this.getTypeName(),this.getProcessBreakpointInfo() );
			if (existingBreakpoint != null) {
				DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(existingBreakpoint, true);
			}
		}
	}
	
	/**
	 * Returns a list of locations of the given line number in the given type.
	 * Returns <code>null</code> if locations cannot be determined.
	 * @throws CoreException 
	 */
	protected List<?> determineLocations(IProcessBreakpointInfo pbInfo, String referenceTypeName, IRuleRunTarget target) throws CoreException {
		List<Location> locations=new ArrayList<Location>();
		if(target.getTaskRegistryMapper() == null) {
			throw new CoreException(new Status(IStatus.ERROR,StudioDebugCorePlugin.getUniqueIdentifier(),"Source map not found."));
		}
		try {
			Map<String, String> mapper = target.getTaskRegistryMapper();
			if(getProject().equals(target.getAdapter(IProject.class))) {				
				if(!mapper.isEmpty()) {
					String className = mapper.get(pbInfo.getTaskType());
					if(className != null && !className.isEmpty()) {
						ReferenceType clazz = DebuggerSupport.findClass(target.getSession().getVM(), className);
						if(clazz != null) {
							Method m = DebuggerSupport.findMethodBySignature((ClassType) clazz, "execute", "(Lcom/tibco/cep/bpmn/runtime/agent/Job;Lcom/tibco/cep/bpmn/runtime/utils/Variables;Lcom/tibco/cep/bpmn/runtime/activity/Task;)Lcom/tibco/cep/bpmn/runtime/activity/TaskResult;");
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
							throw new CoreException(new Status(IStatus.ERROR,StudioDebugCorePlugin.getUniqueIdentifier(),String.format("Class:%s not found in debug VM",className)));
						}
					} else {
						throw new CoreException(new Status(IStatus.ERROR,StudioDebugCorePlugin.getUniqueIdentifier(),String.format("Invalid Class:%s specified",className)));
					}
				}
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
	
	/**
	 * @param typeName
	 * @param bpInfo
	 * @return
	 * @throws CoreException
	 */
	public static IProcessBreakpoint graphBreakpointExists(String typeName, IProcessBreakpointInfo bpInfo) throws CoreException {
		String modelId= RuleDebugModel.getModelIdentifier();
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
}