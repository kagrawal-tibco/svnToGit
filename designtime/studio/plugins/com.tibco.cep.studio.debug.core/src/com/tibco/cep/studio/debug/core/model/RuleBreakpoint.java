package com.tibco.cep.studio.debug.core.model;

import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_INVOKE_USER;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_CALLBACK_BREAKPOINT;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.LineBreakpoint;
import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotPreparedException;
import com.sun.jdi.Location;
import com.sun.jdi.NativeMethodException;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.event.Event;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.impl.IMappedResourcePosition;

/*
@author ssailapp
@date Jul 2, 2009 12:09:40 PM
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class RuleBreakpoint extends LineBreakpoint implements IRuleBreakpoint {	
	/**
	 * Suspend policy constant indicating a breakpoint will
	 * suspend the target VM when hit.
	 */
	public static final int SUSPEND_VM = EventRequest.SUSPEND_ALL;
	
	/**
	 * Default suspend policy constant indicating a breakpoint will
	 * suspend only the thread in which it occurred.
	 */
	public static final int SUSPEND_THREAD = EventRequest.SUSPEND_EVENT_THREAD;
	
	/**
	 * Default suspend policy constant indicating a breakpoint will not
	 * suspend the thread in which it occurred.
	 */
	public static final int SUSPEND_NONE = EventRequest.SUSPEND_NONE;
	/**
	 * Breakpoint attribute storing the expired value (value <code>"org.eclipse.jdt.debug.core.expired"</code>).
	 * This attribute is stored as a <code>boolean</code>. Once a hit count has
	 * been reached, a breakpoint is considered to be "expired".
	 */
	public static final String EXPIRED = "com.tibco.cep.studio.debug.core.expired"; 
	/**
	 * Breakpoint attribute storing a breakpoint's hit count value
	 * (value <code>"org.eclipse.jdt.debug.core.hitCount"</code>). This attribute is stored as an
	 * <code>int</code>.
	 */
	public static final String HIT_COUNT = "com.tibco.cep.studio.debug.core.hitCount"; 
	/**
	 * Breakpoint attribute storing the number of debug targets a
	 * breakpoint is installed in (value <code>"org.eclipse.jdt.debug.core.installCount"</code>).
	 * This attribute is a <code>int</code>.
	 */
	public static final String INSTALL_COUNT = "com.tibco.cep.studio.debug.core.installCount"; 	
	
	/**
	 * Breakpoint attribute storing the fully qualified name of the type
	 * this breakpoint is located in.
	 * (value <code>"org.eclipse.jdt.debug.core.typeName"</code>). This attribute is a <code>String</code>.
	 */
	public static final String TYPE_NAME = "com.tibco.cep.studio.debug.core.typeName"; 		
	
	/**
	 * Breakpoint attribute storing suspend policy code for 
	 * this breakpoint. 
	 * This attribute is an <code>int</code> corresponding
	 * to <code>SUSPEND_VM</code> or
	 * <code>SUSPEND_THREAD</code>.
	 */
	public static final String SUSPEND_POLICY = "com.tibco.cep.studio.debug.core.suspendPolicy";
	/**
	 * Marker attribute used to denote the start of the region within a Java
	 * member that the breakpoint is located within.
	 */
	public static final String MEMBER_START =  "com.tibco.cep.studio.debug.core.member_start"; 
	
	/**
	 * Marker attribute used to denote the end of the region within a Java
	 * member that the breakpoint is located within.
	 */
	public static final String MEMBER_END =  "com.tibco.cep.studio.debug.core.member_end"; 
	
	/**
	 * Stores the collection of requests that this breakpoint has installed in
	 * debug targets.
	 * key: a debug target
	 * value: the requests this breakpoint has installed in that target
	 */
	protected HashMap fRequestsByTarget;
	
	/**
	 * The list of threads (ThreadReference objects) in which this breakpoint will suspend,
	 * associated with the target in which each thread exists (RuleDebugTarget).
	 * key: targets the debug targets (IRuleDebugTarget)
	 * value: thread the filtered thread (IRuleDebugThread) in the given target
	 */
	protected Map fFilteredThreadsByTarget;
	
	/**
	 * Stores the type name that this breakpoint was last installed
	 * in. When a breakpoint is created, the TYPE_NAME attribute assigned to it
	 * is that of its top level enclosing type. 
	 */
	protected String fInstalledTypeName = null;
	
	/**
	 * List of targets in which this breakpoint is installed.
	 * Used to prevent firing of more than one install notification
	 * when a breakpoint's requests are re-created.
	 */
	protected Set fInstalledTargets = null;
	
	/**
	 * List of active instance filters for this breakpoint
	 * (list of <code>IJavaObject</code>).
	 */
	protected List fInstanceFilters = null;
	/**
	 * RuleBreakpoint attributes
	 */	
	protected static final String[] fgExpiredEnabledAttributes= new String[]{EXPIRED, ENABLED};
	
	/**
	 * Status code indicating that a request to create a breakpoint in a type
	 * with no line number attributes has occurred.
	 */
	public static final int NO_LINE_NUMBERS= 162;
	
	public RuleBreakpoint() {
		fRequestsByTarget = new HashMap(1);
		fFilteredThreadsByTarget= new HashMap(1);
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
	 */
	public RuleBreakpoint(final IResource resource, final String typeName,
			final int lineNumber, final int charStart,final int charEnd,
			final int hitCount,final boolean register,final Map attributes) throws CoreException {
		this();
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				// create the marker
				IMarker marker = resource.createMarker(RULEBREAKPOINT_MARKER_TYPE);
				setMarker(marker);
				
				// add attributes
				addLineBreakpointAttributes(attributes, getModelIdentifier(), true, lineNumber, charStart, charEnd);
				addTypeNameAndHitCount(attributes, typeName, hitCount);
				setEnabled(true);
				setAttribute(IMarker.MESSAGE,getMarkerMessage(resource));
				
				
				// set attributes
				attributes.put(SUSPEND_POLICY, new Integer(getDefaultSuspendPolicy()));
				ensureMarker().setAttributes(attributes);
				// add to breakpoint manager if requested
				register(register);
			}
		};
		run(getMarkerRule(resource), runnable);
	}
	
	/**
	 * Adds the standard attributes of a line breakpoint to
	 * the given attribute map.
	 * The standard attributes are:
	 * <ol>
	 * <li>IBreakpoint.ID</li>
	 * <li>IBreakpoint.ENABLED</li>
	 * <li>IMarker.LINE_NUMBER</li>
	 * <li>IMarker.CHAR_START</li>
	 * <li>IMarker.CHAR_END</li>
	 * </ol>	
	 * 
	 */	
	public void addLineBreakpointAttributes(Map attributes, String modelIdentifier, boolean enabled, int lineNumber, int charStart, int charEnd) {
		attributes.put(IBreakpoint.ID, modelIdentifier);
		attributes.put(IBreakpoint.ENABLED, Boolean.valueOf(enabled));
		attributes.put(IMarker.LINE_NUMBER, new Integer(lineNumber));
		attributes.put(IMarker.CHAR_START, new Integer(charStart));
		attributes.put(IMarker.CHAR_END, new Integer(charEnd)); 
	}		
	
	/**
	 * Adds type name and hit count attributes to the given
	 * map.
	 *
	 * If <code>hitCount > 0</code>, adds the <code>HIT_COUNT</code> attribute
	 * to the given breakpoint, and resets the <code>EXPIRED</code> attribute
	 * to false (since, if the hit count is changed, the breakpoint should no
	 * longer be expired).
	 */
	public void addTypeNameAndHitCount(Map attributes, String typeName, int hitCount) {
		attributes.put(TYPE_NAME, typeName);
		if (hitCount > 0) {
			attributes.put(HIT_COUNT, new Integer(hitCount));
			attributes.put(EXPIRED, Boolean.FALSE);
		}
	}

	@Override
	public String getModelIdentifier() {
		return RuleDebugModel.getModelIdentifier();
	}
	
	public void setMarker(IMarker marker) throws CoreException {
		super.setMarker(marker);
		configureAtStartup();
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
				if(bp instanceof RuleBreakpoint) {
					IRuleBreakpoint rbp = (IRuleBreakpoint) bp;
					if(rbp.getTypeName().equals(getTypeName()) &&
					 rbp.getLineNumber() == getLineNumber()) {
						return;
					}
				}
			}
            plugin.getBreakpointManager().addBreakpoint(this);
		} else {
			setRegistered(false);
		}
	}

	/**
	 * Returns whether the hitCount of this breakpoint is equal to the hitCount of
	 * the associated request.
	 */
	protected boolean hasHitCountChanged(EventRequest request) throws CoreException {
		int hitCount= getHitCount();
		Integer requestCount= (Integer) request.getProperty(HIT_COUNT);
		int oldCount = -1;
		if (requestCount != null)  {
			oldCount = requestCount.intValue();
		} 
		return hitCount != oldCount;
	}
	
	/**
	 * Called when a breakpoint event is encountered. Expires the
	 * hit count in the event's request and updates the marker.
	 * @param event the event whose request should have its hit count
	 * expired or <code>null</code> to only update the breakpoint marker.
	 */
	protected void expireHitCount(Event event) {
		Integer requestCount= null;
		EventRequest request= null;
		if (event != null) {
			request= event.request();
			requestCount= (Integer) request.getProperty(HIT_COUNT);
		}
		if (requestCount != null) {
			if (request != null) {
				request.putProperty(EXPIRED, Boolean.TRUE);
			}
			try {
				setAttributes(fgExpiredEnabledAttributes, new Object[]{Boolean.TRUE, Boolean.FALSE});
				// make a note that we auto-disabled this breakpoint.
			} catch (CoreException ce) {
				StudioDebugCorePlugin.log(ce);
			}
		}
	}
	
	/**
	 * Returns whether this breakpoint should be "skipped". Breakpoints
	 * are skipped if the breakpoint manager is disabled and the breakpoint
	 * is registered with the manager
	 * 
	 * @return whether this breakpoint should be skipped
	 */
	@Override
	public boolean shouldSkipBreakpoint() throws CoreException {
		DebugPlugin plugin = DebugPlugin.getDefault();
        return plugin != null && isRegistered() && !plugin.getBreakpointManager().isEnabled();
	}

	/**
	 * Attempts to create a breakpoint request for this breakpoint in the given
	 * reference type in the given target.
	 * 
	 * @return Whether a request was created
	 */
	protected boolean createRequest(IRuleRunTarget target, String referenceTypeName) throws CoreException {
		if (shouldSkipBreakpoint()) {
			return false;
		}
		EventRequest[] requests= newRequests(target, referenceTypeName);
		if (requests == null) {
			return false;
		}
		fInstalledTypeName = referenceTypeName;
		for (int i = 0; i < requests.length; i++) {
            EventRequest request = requests[i];
            registerRequest(request, target);    
        }
		return true;
	}
	
	/**
	 * Add the given event request to the given debug target. If 
	 * the request is the breakpoint request associated with this 
	 * breakpoint, increment the install count.
	 */
	protected void registerRequest(EventRequest request, IRuleRunTarget target) throws CoreException {
		if (request == null) {
			return;
		}
		List reqs = getRequests(target);
		if (reqs.isEmpty()) {
			fRequestsByTarget.put(target, reqs);
		}
		reqs.add(request);
//		target.addJDIEventListener(this, request);
		// update the install attribute on the breakpoint
		if (!(request instanceof ClassPrepareRequest)) {
			incrementInstallCount();
			// notification 
			fireInstalled(target);
		}
	}
	
	/**
	 * Remove the given request from the given target. If the request
	 * is the breakpoint request associated with this breakpoint,
	 * decrement the install count.
	 */
	protected void deregisterRequest(EventRequest request, IRuleRunTarget target) throws CoreException {
//		target.removeJDIEventListener(this, request);
		// A request may be getting de-registered because the breakpoint has
		// been deleted. It may be that this occurred because of a marker deletion.
		// Don't try updating the marker (decrementing the install count) if
		// it no longer exists.
		if (!(request instanceof ClassPrepareRequest) && getMarker().exists()) {
			decrementInstallCount();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.debug.core.breakpoints.JavaBreakpoint#newRequest(org.eclipse.jdt.internal.debug.core.model.RuleDebugTarget, com.sun.jdi.ReferenceType)
	 */
	protected EventRequest[] newRequests(IRuleRunTarget target, String referenceTypeName) throws CoreException {
		int lineNumber = getLineNumber();			
		List locations = determineLocations(lineNumber, referenceTypeName, target);
		if (locations == null || locations.isEmpty()) {
			// could be an inner type not yet loaded, or line information not available
			return null;
		}
		EventRequest[] requests = new EventRequest[locations.size()];
		int i = 0;
	    Iterator iterator = locations.iterator();
	    while (iterator.hasNext()) {
	        Location location = (Location) iterator.next();
	        requests[i] = createLineBreakpointRequest(location, target);
	        i++;
	    }
	    return requests;
	}	
	
	/**
	 * Add this breakpoint to the given target. After it has been
	 * added to the given target, this breakpoint will suspend
	 * execution of that target as appropriate.
	 */
	@Override
	public void addToTarget(IRuleRunTarget target) throws CoreException {
		fireAdding(target);
		createRequests(target);
	}
	
	/**
	 * Creates event requests for the given target
	 */
	@SuppressWarnings("unused")
	protected void createRequests(IRuleRunTarget target) throws CoreException {
		if (target.isTerminated() || shouldSkipBreakpoint() || target.getSession() == null) {
			return;
		}
		final String referenceTypeName= getTypeName();
		if(referenceTypeName != null) {
			boolean success = createRequest(target, referenceTypeName);
		}
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
				if(br.location().equals(location)) { 
					// dont create a duplicate breakpoint request
					// it will end up returning multiple breakpoint
					//events for the same location
					StudioDebugCorePlugin.debug("Skipping breakpoint request at location:"+location);
					skip = true;
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
	
	/**
	 * Returns a list of locations of the given line number in the given type.
	 * Returns <code>null</code> if locations cannot be determined.
	 * @throws CoreException 
	 */
	protected List determineLocations(int lineNumber, String referenceTypeName, IRuleRunTarget target) throws CoreException {
		List locations= null;
		if(target.getSourceMapper() == null) {
			throw new CoreException(new Status(IStatus.ERROR,StudioDebugCorePlugin.getUniqueIdentifier(),"Source map not found."));
		}
		try {
			IMappedResourcePosition javaLine = target.getSourceMapper().getJavaPosition(referenceTypeName, lineNumber);
			if(javaLine != null) {
				
				if(javaLine.getCrossMappedPosition().getLineNumber() != lineNumber) {
					setAttribute(IMarker.LINE_NUMBER, new Integer(javaLine.getCrossMappedPosition().getLineNumber()));
					fireChanged();
				}
				ReferenceType referenceType = DebuggerSupport.findClass(target.getSession().getVM(),javaLine.getResourceName());
				if(referenceType != null) {
					locations= referenceType.locationsOfLine(javaLine.getLineNumber());
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
	 * Returns the requests that this breakpoint has installed
	 * in the given target.
	 */
	protected ArrayList getRequests(IRuleRunTarget target) {
		ArrayList list= (ArrayList)fRequestsByTarget.get(target);
		if (list == null) {
			list= new ArrayList(2);
		}
		return list;
	}
	
	/**
	 * Removes this breakpoint from the given target.
	 */
	public void removeFromTarget(final IRuleRunTarget target) throws CoreException {
		removeRequests(target);
		Object removed = fFilteredThreadsByTarget.remove(target);
		boolean changed = removed != null;
		boolean markerExists = markerExists();
		if (!markerExists || (markerExists && getInstallCount() == 0)) {
			fInstalledTypeName = null;
		}
		
		// remove instance filters 
		if (fInstanceFilters != null && !fInstanceFilters.isEmpty()) {
			for (int i = 0; i < fInstanceFilters.size(); i++) {
				IValue object = (IValue)fInstanceFilters.get(i);
				if (object.getDebugTarget().equals(target)) {
					fInstanceFilters.remove(i);
					changed = true;
				}
			}
		}
		
		// fire change notification if required
		if (changed) {
			fireChanged();
		}
		
		// notification
		fireRemoved(target);
	}		
	
	/**
	 * Remove all requests that this breakpoint has installed in the given
	 * debug target.
	 */
	protected void removeRequests(final IRuleRunTarget target) throws CoreException {
		// removing was previously done is a workspace runnable, but that is
		// not possible since it can be a resource callback (marker deletion) that
		// causes a breakpoint to be removed
		ArrayList requests= (ArrayList)getRequests(target).clone();
		// Iterate over a copy of the requests since this list of requests
		// can be changed in other threads which would cause an ConcurrentModificationException
		Iterator iter = requests.iterator();
		EventRequest req;
		while (iter.hasNext()) {
			req = (EventRequest)iter.next();
			try {
				if (target.isAvailable() && !isExpired(req)) { // cannot delete an expired request
					EventRequestManager manager = target.getSession().getEventRequestManager();
					if (manager != null) {
						StudioDebugCorePlugin.debug("Removing breakpoint request [type:"
								+ getBreakpointType()
								+ ":"
								+ ((BreakpointRequest)req).location()
								+"]");
								//"Removing breakpoint request at location:"+((BreakpointRequest)req).location());
						manager.deleteEventRequest(req); // disable & remove 
					}					
				}
			} catch (VMDisconnectedException e) {
				if (target.isAvailable()) {
					StudioDebugCorePlugin.log(e);
				}
			} catch (RuntimeException e) {
                ((RuleDebugElement)target).internalError(e);
			} finally {
				deregisterRequest(req, target);
			}
		}
		fRequestsByTarget.remove(target);
	}
	
	/**
	 * Returns whether this breakpoint has expired.
	 */
	public boolean isExpired() throws CoreException {
		return ensureMarker().getAttribute(EXPIRED, false);
	}	
	
	/**
	 * Returns whether the given request is expired
	 */
	public boolean isExpired(EventRequest request) {
		Boolean requestExpired= (Boolean) request.getProperty(EXPIRED);
		if (requestExpired == null) {
				return false;
		}
		return requestExpired.booleanValue();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaBreakpoint#isInstalled()
	 */
	public boolean isInstalled() throws CoreException {
		return ensureMarker().getAttribute(INSTALL_COUNT, 0) > 0;
	}	
	
	/**
	 * Increments the install count of this breakpoint
	 */
	protected void incrementInstallCount() throws CoreException {	
		int count = getInstallCount();
		setAttribute(INSTALL_COUNT, count + 1);
	}	
	
	/**
	 * Returns the <code>INSTALL_COUNT</code> attribute of this breakpoint
	 * or 0 if the attribute is not set.
	 */
	public int getInstallCount() throws CoreException {
		return ensureMarker().getAttribute(INSTALL_COUNT, 0);
	}	

	/**
	 * Decrements the install count of this breakpoint.
	 */
	protected void decrementInstallCount() throws CoreException {
		int count= getInstallCount();
		if (count > 0) {
			setAttribute(INSTALL_COUNT, count - 1);	
		}
		if (count == 1) {
			if (isExpired()) {
				// if breakpoint was auto-disabled, re-enable it
				setAttributes(fgExpiredEnabledAttributes,
						new Object[]{Boolean.FALSE, Boolean.TRUE});
			}
		}
	}
	
	/**
	 * Sets the type name in which to install this breakpoint.
	 */
	protected void setTypeName(String typeName) throws CoreException {
		setAttribute(TYPE_NAME, typeName);
	}	

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaBreakpoint#getTypeName()
	 */
	public String getTypeName() throws CoreException {
		if (fInstalledTypeName == null) {
			return ensureMarker().getAttribute(TYPE_NAME, null);
		}
		return fInstalledTypeName;
	}

	
	/**
	 * Resets the install count attribute on this breakpoint's marker
	 * to "0".  Resets the expired attribute on all breakpoint markers to <code>false</code>.
	 * Resets the enabled attribute on the breakpoint marker to <code>true</code>.
	 * If a workbench crashes, the attributes could have been persisted
	 * in an incorrect state.
	 */
	private void configureAtStartup() throws CoreException {
		List<String> attributes= null;
		List values = new ArrayList(3);
		if (isInstalled()) {
			attributes= new ArrayList(3);
			attributes.add(INSTALL_COUNT);
			values.add(new Integer(0));
		}
		if (isExpired()) {
			if (attributes == null) {
				attributes= new ArrayList<String>(3);
			}
			// if breakpoint was auto-disabled, re-enable it
			attributes.add(EXPIRED);
			values.add(Boolean.FALSE);
			attributes.add(ENABLED);
			values.add(Boolean.TRUE);
		}
		if (attributes != null) {
			String[] strAttributes= new String[attributes.size()];
			setAttributes((String[])attributes.toArray(strAttributes), values.toArray());
		}
	}
	
	/**
	 * Configure a breakpoint request with common properties:
	 * <ul>
	 * <li><code>JAVA_BREAKPOINT_PROPERTY</code></li>
	 * <li><code>HIT_COUNT</code></li>
	 * <li><code>EXPIRED</code></li>
	 * </ul>
	 * and sets the suspend policy of the request to suspend 
	 * the event thread.
	 */
	protected void configureRequest(EventRequest request, IRuleRunTarget target) throws CoreException {
		request.setSuspendPolicy(getJDISuspendPolicy());
		configureRequestProperties(request);		
		target.getSession().getBreakpoints().add(request);
		configureRequestThreadFilter(request, target);
		configureRequestHitCount(request);
		configureInstanceFilters(request, target);
		// Important: only enable a request after it has been configured
		updateEnabledState(request, target);
	}

	protected void configureRequestProperties(EventRequest request) throws CoreException {
		request.putProperty(RULE_BREAKPOINT_PROPERTY, this);
		request.putProperty(DebuggerConstants.ACTION_FLAGS,	DEBUG_CALLBACK_BREAKPOINT
															| DEBUG_ACTION_STATUS_INVOKE_USER);
		request.putProperty(DebuggerConstants.BREAKPOINT_TYPE,getBreakpointType());		
		request.putProperty(DebuggerConstants.BREAKPOINT_ID,getId());
	}
	/**
	 * Update the enabled state of the given request in the given target, which is associated
	 * with this breakpoint. Set the enabled state of the request
	 * to the enabled state of this breakpoint.
	 */
	protected void updateEnabledState(EventRequest request, IRuleRunTarget target) throws CoreException  {
		internalUpdateEnabledState(request, isEnabled(), target);
	}
	
	/**
	 * Set the enabled state of the given request to the given
	 * value, also taking into account instance filters.
	 */
	protected void internalUpdateEnabledState(EventRequest request, boolean enabled, IRuleRunTarget target) {
		if (request.isEnabled() != enabled) {
			// change the enabled state
			try {
				// if the request has expired, do not disable.
				// BreakpointRequests that have expired cannot be deleted.
				if (!isExpired(request)) {
					request.setEnabled(enabled);
				}
			} catch (VMDisconnectedException e) {
			} catch (RuntimeException e) {
				((RuleDebugElement)target).internalError(e);
			}
		}
	}
	/**
	 * Configure the thread filter property of the given request.
	 */
	protected void configureRequestThreadFilter(EventRequest request, IRuleRunTarget target) {
		IRuleDebugThread thread= (IRuleDebugThread)fFilteredThreadsByTarget.get(target);
		if (thread == null || (!(thread instanceof RuleDebugThread))) {
			return;
		}
		setRequestThreadFilter(request, ((RuleDebugThread)thread).getUnderlyingThread());
	}
	
	/**
	 * @see JavaBreakpoint#setRequestThreadFilter(EventRequest)
	 */
	protected void setRequestThreadFilter(EventRequest request, ThreadReference thread) {
		((BreakpointRequest)request).addThreadFilter(thread);
	}
	
	/**
	 * Configure the given request's hit count
	 */
	protected void configureRequestHitCount(EventRequest request) throws CoreException {
		int hitCount= getHitCount();
		if (hitCount > 0) {
			request.addCountFilter(hitCount);
			request.putProperty(HIT_COUNT, new Integer(hitCount));
		}
	}
	
	protected void configureInstanceFilters(EventRequest request, IRuleRunTarget target) {
		if (fInstanceFilters != null && !fInstanceFilters.isEmpty()) {
			Iterator iter = fInstanceFilters.iterator();
			while (iter.hasNext()) {
				IValue object = (IValue)iter.next();
				if (object.getDebugTarget().equals(target)) {
//					addInstanceFilter(request, ((JDIObjectValue)object).getUnderlyingObject());
				}
			}
		}
	}

	public String getId() {
		try {
			if(getMarker() != null && getMarker().getResource() != null) {
//				IPath resourcePath = getMarker().getResource().getFullPath().removeFileExtension().removeFirstSegments(1).makeAbsolute();
				int line = getLineNumber();
				String typeName = getTypeName();
				return ""+line+":"+typeName;
			}
			return null;
		} catch (CoreException e) {
			StudioDebugCorePlugin.log(e);
			return null;
		}
	}
	
	public String getResourceName() {
		if(getMarker() != null && getMarker().getResource() == getMarker().getResource().getProject()) {
			try {
				return getTypeName();
			} catch (CoreException e) {
				IPath resourcePath = getMarker().getResource().getFullPath().removeFileExtension().removeFirstSegments(1).makeAbsolute();
				return resourcePath.toString();
			}
		}
		if(getMarker() != null && getMarker().getResource() != null) {
			IPath resourcePath = getMarker().getResource().getFullPath().removeFileExtension().removeFirstSegments(1).makeAbsolute();
			return resourcePath.toString();
		}
		return "";		
	}
	
	public String getBreakpointType() throws CoreException {
		if(getMarker() != null && getMarker().getResource() != null) {
			return (String) getMarker().getAttribute(DebuggerConstants.BREAKPOINT_TYPE);
		}
		return "";	
	}
	
	protected String getMarkerMessage(IResource resource) throws DebugException, CoreException {
		StringBuffer message = new StringBuffer(MessageFormat.format("[Resource: {0}",new Object[]{resource.getName()}));
		message.append(getMarkerMessage(false, null, getHitCount(), getSuspendPolicy(), getLineNumber()));
		return message.toString();
	}
	
	
	protected String getMarkerMessage(boolean conditionEnabled, String condition, int hitCount, int suspendPolicy, int lineNumber) {
		StringBuffer message= new StringBuffer(getMarkerMessage(hitCount, suspendPolicy));
		if (lineNumber != -1) {
			message.append(MessageFormat.format("[line: {0}]", new Object[]{Integer.toString(lineNumber)})); 
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
	
	/**
	 * Sets whether this breakpoint's hit count has expired.
	 */
	public void setExpired(boolean expired) throws CoreException {
		setAttribute(EXPIRED, expired);	
	}
	
	/**
	 * Returns the JDI suspend policy that corresponds to this
	 * breakpoint's suspend policy
	 * 
	 * @return the JDI suspend policy that corresponds to this
	 *  breakpoint's suspend policy
	 * @exception CoreException if unable to access this breakpoint's
	 *  suspend policy setting
	 */
	public int getJDISuspendPolicy() throws CoreException {
		int breakpointPolicy = getSuspendPolicy();
		if (breakpointPolicy == SUSPEND_THREAD) {
			return EventRequest.SUSPEND_EVENT_THREAD;
		}
		return EventRequest.SUSPEND_ALL;
	}

	/**
	 * returns the default suspend policy based on the pref setting on the 
	 * Java-Debug pref page
	 * @return the default suspend policy
	 * @since 3.2
	 */
	protected int getDefaultSuspendPolicy() {
		IEclipsePreferences store = RuleDebugModel.getPreferences();
		return store.getInt(StudioDebugCorePlugin.PREF_DEFAULT_BREAKPOINT_SUSPEND_POLICY,RuleBreakpoint.SUSPEND_THREAD);
	}

	public int getSuspendPolicy() throws DebugException {
		return ensureMarker().getAttribute(SUSPEND_POLICY, RuleBreakpoint.SUSPEND_THREAD);
	}
	
	
	public String toString() {
		return getId();
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
			result = prime * result + getLineNumber();
		} catch (CoreException e) {
			result = prime * result;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleBreakpoint other = (RuleBreakpoint) obj;
		if (fInstalledTargets == null) {
			if (other.fInstalledTargets != null)
				return false;
		} else if (!fInstalledTargets.equals(other.fInstalledTargets))
			return false;
		try {
			if (getLineNumber() != other.getLineNumber())
				return false;
		} catch (CoreException e) {
			return false;
		}
		return true;
	}

	public int getHitCount() throws CoreException {
		return ensureMarker().getAttribute(HIT_COUNT, -1);
	}
	
	
	public void setHitCount(int count) throws CoreException {	
		if (getHitCount() != count) {
			if (!isEnabled() && count > -1) {
				setAttributes(new String []{ENABLED, HIT_COUNT, EXPIRED},
					new Object[]{Boolean.TRUE, new Integer(count), Boolean.FALSE});
			} else {
				setAttributes(new String[]{HIT_COUNT, EXPIRED},
					new Object[]{new Integer(count), Boolean.FALSE});
			}
			recreate();
		}
	}
	
	/**
	 * Returns the type of marker associated with Java line breakpoints
	 */
	@Override
	public  String getMarkerType() {
		return RULEBREAKPOINT_MARKER_TYPE;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ILineBreakpoint#getLineNumber()
	 */
	public int getLineNumber() throws CoreException {
		return ensureMarker().getAttribute(IMarker.LINE_NUMBER, -1);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ILineBreakpoint#getCharStart()
	 */
	public int getCharStart() throws CoreException {
		return ensureMarker().getAttribute(IMarker.CHAR_START, -1);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ILineBreakpoint#getCharEnd()
	 */
	public int getCharEnd() throws CoreException {
		return ensureMarker().getAttribute(IMarker.CHAR_END, -1);
	}	
	

	public void setSuspendPolicy(int suspendPolicy) throws CoreException {
		if(getSuspendPolicy() != suspendPolicy) {
			setAttribute(SUSPEND_POLICY, suspendPolicy);
			recreate();
		}		
	}
	
	/**
	 * Notifies listeners this breakpoint is to be added to the
	 * given target.
	 * 
	 * @param target debug target
	 */
	protected void fireAdding(IRuleRunTarget target) {
		StudioDebugCorePlugin plugin = StudioDebugCorePlugin.getDefault();
        if (plugin != null)
            plugin.fireBreakpointAdding(target, this);
	}
	
	/**
	 * Notifies listeners this breakpoint has been removed from the
	 * given target.
	 * 
	 * @param target debug target
	 */
	protected void fireRemoved(IRuleRunTarget target) {
		StudioDebugCorePlugin plugin = StudioDebugCorePlugin.getDefault();
        if (plugin != null) {
            plugin.fireBreakpointRemoved(target, this);
            setInstalledIn(target, false);
        }
	}	
	
	/**
	 * Notifies listeners this breakpoint has been installed in the
	 * given target.
	 * 
	 * @param target debug target
	 */
	protected void fireInstalled(IRuleRunTarget target) {
        StudioDebugCorePlugin plugin = StudioDebugCorePlugin.getDefault();
		if (plugin!= null && !isInstalledIn(target)) {
            plugin.fireBreakpointInstalled(target, this);
			setInstalledIn(target, true);
		}
	}	
	
	
	/**
	 * Returns whether this breakpoint is installed in the given target.
	 * 
	 * @param target
	 * @return whether this breakpoint is installed in the given target
	 */
	protected boolean isInstalledIn(IRuleRunTarget target) {
		return fInstalledTargets != null && fInstalledTargets.contains(target);
	}
	
	/**
	 * Sets this breakpoint as installed in the given target
	 * 
	 * @param target
	 * @param installed whether installed
	 */
	protected void setInstalledIn(IRuleRunTarget target, boolean installed) {
		if (installed) {
			if (fInstalledTargets == null) {
				fInstalledTargets = new HashSet();
			}
			fInstalledTargets.add(target);
		} else {
			if (fInstalledTargets != null) {
				fInstalledTargets.remove(target);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaBreakpoint#setThreadFilter(org.eclipse.jdt.debug.core.IRuleDebugThread)
	 */
	public void setThreadFilter(IRuleDebugThread thread)  throws CoreException {
		if (!(thread.getDebugTarget() instanceof AbstractDebugTarget) || !(thread instanceof RuleDebugThread)) {
			return;
		}
		AbstractDebugTarget target= (AbstractDebugTarget)thread.getDebugTarget();
		if (thread != fFilteredThreadsByTarget.put(target, thread) ) {
			// recreate the breakpoint only if it is not the same thread
			
			// Other breakpoints set attributes on the underlying
			// marker and the marker changes are eventually
			// propagated to the target. The target then asks the
			// breakpoint to update its request. Since thread filters
			// are transient properties, they are not set on
			// the marker. Thus we must update the request
			// here.
//			recreate(target);
			fireChanged();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.IDebugEventSetListener#handleDebugEvents(org.eclipse.debug.core.DebugEvent[])
	 */
	public void handleDebugEvents(DebugEvent[] events) {
		for (int i = 0; i < events.length; i++) {
			DebugEvent event = events[i];
			if (event.getKind() == DebugEvent.TERMINATE) {
				Object source= event.getSource();
				if (!(source instanceof RuleDebugThread)) {
					return;
				}
				try {
					cleanupForThreadTermination((RuleDebugThread)source);
				} catch (VMDisconnectedException exception) {
					// Thread death often occurs at shutdown.
					// A VMDisconnectedException trying to 
					// update the breakpoint request is
					// acceptable.
				}
			}
		}
	}
	
	
	/**
	 * Removes cached information relevant to this thread which has
	 * terminated.
	 * 
	 * Remove thread filters for terminated threads
	 * 
	 * Subclasses may override but need to call super.
	 */
	protected void cleanupForThreadTermination(RuleDebugThread thread) {
		RuleDebugTarget target= (RuleDebugTarget)thread.getDebugTarget();
		if (thread == getThreadFilter(target)) {
			try {
				removeThreadFilter(target);
			} catch (CoreException e) {
				StudioDebugCorePlugin.log(e);
			}
		}
	}
	
	
	public IRuleDebugThread getThreadFilter(IRuleRunTarget target) {
		return (IRuleDebugThread)fFilteredThreadsByTarget.get(target);
	}
	

	public IRuleDebugThread[] getThreadFilters() {
		IRuleDebugThread[] threads= null;
		Collection values= fFilteredThreadsByTarget.values();
		threads= new IRuleDebugThread[values.size()];
		values.toArray(threads);
		return threads;
	}


	public void removeThreadFilter(IRuleRunTarget javaTarget)  throws CoreException {
		if (!(javaTarget instanceof AbstractDebugTarget)) {
			return;
		}
		AbstractDebugTarget target= (AbstractDebugTarget)javaTarget;
		if (fFilteredThreadsByTarget.remove(target) != null) {
//			recreate(target);
			fireChanged();
		}
	}
	
	/**
	 * Change notification when there are no marker changes. If the marker
	 * does not exist, do not fire a change notification (the marker may not
	 * exist if the associated project was closed).
	 */
	protected void fireChanged() {
        DebugPlugin plugin = DebugPlugin.getDefault();
		if (plugin != null && markerExists()) {	
            plugin.getBreakpointManager().fireBreakpointChanged(this);
		}					
	}
	
	
	/**
	 * An attribute of this breakpoint has changed - recreate event requests in
	 * all targets.
	 */
	protected void recreate() throws CoreException {
		DebugPlugin plugin = DebugPlugin.getDefault();
		if (plugin != null) {
			IDebugTarget[] targets = plugin.getLaunchManager()
					.getDebugTargets();
			for (int i = 0; i < targets.length; i++) {
				IDebugTarget target = targets[i];
				MultiStatus multiStatus = new MultiStatus(StudioDebugCorePlugin
						.getUniqueIdentifier(), -1,
						"Exception occurred while updating breakpoint.",
						null);
				IRuleRunTarget jdiTarget = (IRuleRunTarget) target
						.getAdapter(IRuleRunTarget.class);
				if (jdiTarget instanceof IRuleDebugTarget) {
					try {
						recreate(jdiTarget);
					} catch (CoreException e) {
						multiStatus.add(e.getStatus());
					}
				} else if(jdiTarget instanceof RuleRunTarget && !jdiTarget.isAvailable()){
					/*If no ruleRunTargets are active then allow drawing breakpoints. JIRA BE-25628*/
					try {
						recreate(jdiTarget);
					} catch (CoreException e) {
						multiStatus.add(e.getStatus());
					}
				} else {
					multiStatus.add(Status.CANCEL_STATUS);
				}
				if (!multiStatus.isOK()) {
					throw new CoreException(multiStatus);
				}
			}
		}
	}
	
	/**
	 * Recreate this breakpoint in the given target, as long as the
	 * target already contains this breakpoint.
	 * 
	 * @param target the target in which to re-create the breakpoint 
	 */
	protected void recreate(IRuleRunTarget target) throws CoreException {
		if (target.isAvailable() && target.getBreakpoints().contains(this)) {
			removeRequests(target);
			createRequests(target);
		}
	}
	
	public void setEnabled(boolean enabled) throws CoreException {
		super.setEnabled(enabled);
		recreate();
	}
	

	public boolean supportsInstanceFilters() {
		return false;
	}

	
	public boolean supportsThreadFilters() {
		return true;
	}

	
	
	
	
}
