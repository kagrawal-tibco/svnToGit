package com.tibco.cep.studio.debug.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.osgi.service.prefs.BackingStoreException;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.EventRequest;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;

public class RuleDebugModel {
	
	/**
	 * Preference key for default JDI request timeout value.
	 */
	public static final String PREF_REQUEST_TIMEOUT = getPluginIdentifier() + ".PREF_REQUEST_TIMEOUT"; //$NON-NLS-1$
	
	/**
	 * Preference key for specifying if hot code replace should be performed
	 * when a replacement class file contains compilation errors.
	 */
	public static final String PREF_HCR_WITH_COMPILATION_ERRORS= getPluginIdentifier() + ".PREF_HCR_WITH_COMPILATION_ERRORS"; //$NON-NLS-1$
	
	/**
	 * The default JDI request timeout when no preference is set.
	 */
	public static final int DEF_REQUEST_TIMEOUT = 3000;
	
	/**
	 * Boolean preference controlling whether breakpoints are
	 * hit during an evaluation operation.
	 * If true, breakpoints will be hit as usual during evaluations.
	 * If false, the breakpoint manager will be automatically disabled
	 * during evaluations.
	 * 
	 */
	public static final String PREF_SUSPEND_FOR_BREAKPOINTS_DURING_EVALUATION = getPluginIdentifier() + ".suspend_for_breakpoints_during_evaluation"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	protected RuleDebugModel() {
		super();
	}

	/**
	 * @return
	 */
	public static String getPluginIdentifier() {
		return StudioDebugCorePlugin.PLUGIN_ID;
	}
	
	/**
	 * @return
	 */
	public static String getModelIdentifier() {
		return DebuggerConstants.ID_RULE_DEBUG_MODEL;
	}
	

	
	/**
	 * @param launch
	 * @param hostName
	 * @param port
	 * @param supportsDisconnect TODO
	 * @param supportsTerminate TODO
	 * @param resumeOnStartup TODO
	 * @param process
	 * @return
	 */
	public static IDebugTarget newDebugTarget(final ILaunch launch,
			final String name, final IProcess lProcess, final String hostName,
			final String port, final VirtualMachine vm,
			final boolean supportsDisconnect, final boolean supportsTerminate,
			final boolean resumeOnStartup) {
		final IDebugTarget[] target = new IDebugTarget[1];
		try {
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				public void run(IProgressMonitor m) {
					if (launch.getLaunchMode()
							.equals(ILaunchManager.DEBUG_MODE)) {
						target[0] = new RuleDebugTarget(launch, vm, name,
								lProcess, hostName, port, supportsDisconnect,
								supportsTerminate, resumeOnStartup);
						launch.addDebugTarget(target[0]);
					} else if (launch.getLaunchMode().equals(
							ILaunchManager.RUN_MODE)) {
						target[0] = new RuleRunTarget(launch, vm, name,
								lProcess, hostName, port, supportsDisconnect,
								supportsTerminate, resumeOnStartup);
						launch.addDebugTarget(target[0]);
					}
				}
			};
			ResourcesPlugin.getWorkspace().run(r, null, 0, null);
		} catch (CoreException e) {
			StudioDebugCorePlugin.log(e);
		}
		return target[0];
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
	 * @return
	 * @throws CoreException
	 */
	@SuppressWarnings("rawtypes")
	public static IRuleBreakpoint createLineBreakpoint(IResource resource, String typeName, int lineNumber, int charStart, int charEnd, int hitCount, boolean register, Map attributes) throws CoreException {
		if (attributes == null) {
			attributes = new HashMap(10);
		}		
		return new RuleBreakpoint(resource, typeName, lineNumber, charStart, charEnd, hitCount, register, attributes);
	}
	
	@SuppressWarnings("rawtypes")
	public static IntermediateBreakpoint createIntermediateBreakpoint(IResource resource, String typeName, int lineNumber, int charStart, int charEnd, int hitCount, boolean register, Map attributes) throws CoreException {
		if (attributes == null) {
			attributes = new HashMap(10);
		}		
		return new IntermediateBreakpoint(resource, typeName, lineNumber, charStart, charEnd, hitCount, register, attributes,RuleBreakpoint.SUSPEND_THREAD);
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
	 * @return
	 * @throws CoreException
	 */
	@SuppressWarnings("rawtypes")
	public static IntermediateBreakpoint createIntermediateBreakpoint(IResource resource, String typeName, int lineNumber, int charStart, int charEnd, int hitCount, boolean register, Map attributes,int suspendPolicy) throws CoreException {
		if (attributes == null) {
			attributes = new HashMap(10);
		}		
		return new IntermediateBreakpoint(resource, typeName, lineNumber, charStart, charEnd, hitCount, register, attributes,suspendPolicy);
	}
	
	
	/**
	 * Returns a Rule line breakpoint that is already registered with the breakpoint
	 * manager for a type with the given name at the given line number.
	 * 
	 * @param typeName fully qualified type name
	 * @param lineNumber line number
	 * @return a Rule line breakpoint that is already registered with the breakpoint
	 *  manager for a type with the given name at the given line number or <code>null</code>
	 * if no such breakpoint is registered
	 * @exception CoreException if unable to retrieve the associated marker
	 * 	attributes (line number).
	 */
	public static IRuleBreakpoint lineBreakpointExists(String typeName, int lineNumber) throws CoreException {
		String modelId= RuleDebugModel.getModelIdentifier();
		String markerType= IRuleBreakpoint.RULEBREAKPOINT_MARKER_TYPE;
		IBreakpointManager manager= DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint[] breakpoints= manager.getBreakpoints(modelId);
		for (int i = 0; i < breakpoints.length; i++) {
			if (!(breakpoints[i] instanceof IRuleBreakpoint)) {
				continue;
			}
			IRuleBreakpoint breakpoint = (IRuleBreakpoint) breakpoints[i];
			IMarker marker = breakpoint.getMarker();
			if (marker != null && marker.exists() && marker.getType().equals(markerType)) {
				String breakpointTypeName = breakpoint.getTypeName();
				if (breakpointTypeName.equals(typeName) || breakpointTypeName.startsWith(typeName + '$')) {
					if (breakpoint.getLineNumber() == lineNumber) {
						return breakpoint;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns a Java line breakpoint that is already registered with the breakpoint
	 * manager for a type with the given name at the given line number in the given resource.
	 * 
	 * @param resource the resource
	 * @param typeName fully qualified type name
	 * @param lineNumber line number
	 * @return a Java line breakpoint that is already registered with the breakpoint
	 *  manager for a type with the given name at the given line number or <code>null</code>
	 * if no such breakpoint is registered
	 * @exception CoreException if unable to retrieve the associated marker
	 * 	attributes (line number).
	 */
	public static IRuleBreakpoint lineBreakpointExists(IResource resource, String typeName, int lineNumber) throws CoreException {
		String modelId= getModelIdentifier();
		String markerType= IRuleBreakpoint.RULEBREAKPOINT_MARKER_TYPE;
		IBreakpointManager manager= DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint[] breakpoints= manager.getBreakpoints(modelId);
		for (int i = 0; i < breakpoints.length; i++) {
			if (!(breakpoints[i] instanceof IRuleBreakpoint)) {
				continue;
			}
			IRuleBreakpoint breakpoint = (IRuleBreakpoint) breakpoints[i];
			IMarker marker = breakpoint.getMarker();
			if (marker != null && marker.exists() && marker.getType().equals(markerType)) {
				String breakpointTypeName = breakpoint.getTypeName();
				if ((breakpointTypeName.equals(typeName)) &&
					breakpoint.getLineNumber() == lineNumber &&
					resource.equals(marker.getResource())) {
						return breakpoint;
				}
			}
		}
		return null;
	}

	public static IEclipsePreferences getPreferences() {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(StudioDebugCorePlugin.PLUGIN_ID);
		return prefs;
//		StudioDebugCorePlugin deflt= StudioDebugCorePlugin.getDefault();
//		if (deflt != null) {
//			return deflt.getPluginPreferences();
//		}
//		return null;
	}
	
	public static void savePreferences() throws BackingStoreException {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(StudioDebugCorePlugin.PLUGIN_ID);
		prefs.flush();
//		StudioDebugCorePlugin.getDefault().savePluginPreferences();
	}
	
	/**
	 * Checks if a {@link IRuleJavaBreakpoint} exists for a {@link IJavaLineBreakpoint} from an active session
	 * because the IRuleJavaBreakpoint is never persisted
	 * @param session
	 * @param jlbp
	 * @return
	 * @throws CoreException
	 */
	public static IRuleJavaBreakpoint lineBreakpointExists(RunSession session,IJavaLineBreakpoint jlbp) throws CoreException{
		ArrayList<EventRequest> allbps = session.getBreakpoints();
		for (EventRequest r:allbps) {
			if(r instanceof BreakpointRequest){
				IRuleBreakpoint rbp = (IRuleBreakpoint) r.getProperty(IRuleBreakpoint.RULE_BREAKPOINT_PROPERTY);
				String type = null;
				type = rbp.getBreakpointType();
				if (type != null && type.equals(DebuggerConstants.BREAKPOINT_RULE_JAVA)&&
						rbp.getMarker().getResource().equals(jlbp.getMarker().getResource()) &&
						rbp.getLineNumber() == jlbp.getLineNumber()) {
					return (IRuleJavaBreakpoint) rbp;
				}
			}
		}
		return null;
	}

	/**
	 * Checks if a {@link IRuleJavaBreakpoint} exists for a {@link IJavaLineBreakpoint}
	 * @param jlbp
	 * @return
	 * @throws CoreException 
	 */
	public static IRuleJavaBreakpoint lineBreakpointExists(IJavaLineBreakpoint jlbp) throws CoreException {
		String modelId= getModelIdentifier();
		String markerType= IRuleJavaBreakpoint.JAVA_INTERMEDIATE_BREAKPOINT_MARKER_TYPE;
		IBreakpointManager manager= DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint[] breakpoints= manager.getBreakpoints(modelId);
		for (int i = 0; i < breakpoints.length; i++) {
			if (!(breakpoints[i] instanceof IRuleJavaBreakpoint)) {
				continue;
			}
			IRuleJavaBreakpoint rbp = (IRuleJavaBreakpoint) breakpoints[i];
			IMarker marker = rbp.getMarker();
			IMarker jbpMarker = jlbp.getMarker();
			IResource jbpResource = jbpMarker.getResource();
			
			if (marker != null && marker.exists()) {
				
				String breakpointTypeName = rbp.getTypeName();
				
				if ((breakpointTypeName.equals(jlbp.getTypeName())) &&
					rbp.getLineNumber() == jlbp.getLineNumber() &&
					jbpResource.equals(marker.getResource())) {
						return rbp;
				}
			}
		}
		return null;
	}

	/**
	 * Creates a new {@link IRuleJavaBreakpoint} intermediate break point
	 * @param jlbp
	 * @param attributes
	 * @return
	 * @throws CoreException
	 */
	public static IRuleJavaBreakpoint createRuleJavaBreakpoint(IJavaLineBreakpoint jlbp,Map attributes) throws CoreException {
		if (attributes == null) {
			attributes = new HashMap(10);
		}		
		attributes.put(DebuggerConstants.BREAKPOINT_TYPE,DebuggerConstants.BREAKPOINT_RULE_JAVA);
		IMarker jlbpMarker = jlbp.getMarker();
		return new RuleJavaBreakpoint(jlbpMarker.getResource(), 
				jlbp.getTypeName(), 
				jlbp.getLineNumber(), 
				jlbp.getCharStart(), 
				jlbp.getCharEnd(), 
				jlbp.getHitCount(), 
				false, 
				attributes,
				jlbp.getSuspendPolicy());
	}

}
