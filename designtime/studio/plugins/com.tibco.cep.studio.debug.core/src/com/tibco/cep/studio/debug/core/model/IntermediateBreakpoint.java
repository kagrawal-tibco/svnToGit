package com.tibco.cep.studio.debug.core.model;

import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_INVOKE_USER;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_CALLBACK_BREAKPOINT;

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
import com.sun.jdi.NativeMethodException;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.request.EventRequest;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
@SuppressWarnings({"rawtypes","unchecked"})
public class IntermediateBreakpoint extends RuleBreakpoint {
	
	private static final String INTERMEDIATE_BREAKPOINT_MARKER_TYPE= "com.tibco.cep.studio.debug.core.IntermediateBreakpointMarker";
	private Object taskId  = DebuggerConstants.NULL_TASK;

	/**
	 * 
	 */
	public IntermediateBreakpoint() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @throws CoreException
	 */
	public IntermediateBreakpoint(final IResource resource, final String typeName,
			final int lineNumber, final int charStart, final int charEnd, int hitCount,
			final boolean register, final Map attributes,final int suspendPolicy) throws CoreException {
		IWorkspaceRunnable wr= new IWorkspaceRunnable() {

			public void run(IProgressMonitor monitor) throws CoreException {				
				// create the marker
				setMarker(resource.createMarker(INTERMEDIATE_BREAKPOINT_MARKER_TYPE));
				
				// add attributes
				attributes.put(IBreakpoint.ID, getModelIdentifier());
				attributes.put(IMarker.CHAR_START, new Integer(charStart));
				attributes.put(IMarker.CHAR_END, new Integer(charEnd)); 
				attributes.put(IMarker.LINE_NUMBER, new Integer(lineNumber)); 
				attributes.put(TYPE_NAME, typeName);
				attributes.put(ENABLED, Boolean.TRUE);
				attributes.put(SUSPEND_POLICY, new Integer(suspendPolicy));
				if(attributes.containsKey(DebuggerConstants.VM_TASK_KEY)) {
					setTaskId(attributes.get(DebuggerConstants.VM_TASK_KEY));
				}
				ensureMarker().setAttributes(attributes);
				
				register(register);
			}

		};
		run(getMarkerRule(resource), wr);
	}
	
	
	
	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Object taskId) {
		this.taskId = taskId;
	}


	
	protected void configureRequestProperties(EventRequest request) throws CoreException {
		request.putProperty(RULE_BREAKPOINT_PROPERTY, this);
		request.putProperty(DebuggerConstants.ACTION_FLAGS,DEBUG_CALLBACK_BREAKPOINT
															| DEBUG_ACTION_STATUS_INVOKE_USER);
		request.putProperty(DebuggerConstants.BREAKPOINT_TYPE,getBreakpointType());
		request.putProperty(DebuggerConstants.BREAKPOINT_ID,getId());
		if(!getTaskId().equals(DebuggerConstants.NULL_TASK)) {
			request.putProperty(DebuggerConstants.VM_TASK_KEY,getTaskId());
		}
		
	}
	
	
	
	private Object getTaskId() {
		return taskId;
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
			
			ReferenceType referenceType = DebuggerSupport.findClass(target.getSession().getVM(),referenceTypeName);
			if(referenceType != null) {
				locations= referenceType.locationsOfLine(lineNumber);
			} else {
				throw new CoreException(new Status(IStatus.ERROR,
						StudioDebugCorePlugin.getUniqueIdentifier(),
						String.format("Reference Type:%s not found", referenceTypeName)));
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
	
	

}
