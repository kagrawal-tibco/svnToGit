package com.tibco.cep.studio.debug.core.model;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.ILineBreakpoint;

public interface IRuleBreakpoint extends ILineBreakpoint {
	
	public static final String RULEBREAKPOINT_MARKER_TYPE = "com.tibco.cep.studio.debug.core.RuleBreakpointMarker";

	/**
	 * Property identifier for a breakpoint object on an event request
	 */
	public static final String RULE_BREAKPOINT_PROPERTY = "com.tibco.cep.studio.debug.breakpoint";

	@Override
	public String getModelIdentifier();

	/**
	 * Returns the <code>INSTALL_COUNT</code> attribute of this breakpoint
	 * or 0 if the attribute is not set.
	 */
	public int getInstallCount() throws CoreException;

	public String getTypeName() throws CoreException;

	public String getId();

	public String getResourceName();

	/**
	 * Returns the JDI suspend policy that corresponds to this
	 * breakpoint's suspend policy
	 * 
	 * @return the JDI suspend policy that corresponds to this
	 *  breakpoint's suspend policy
	 * @exception CoreException if unable to access this breakpoint's
	 *  suspend policy setting
	 */
	public int getJDISuspendPolicy() throws CoreException;

	public int getSuspendPolicy() throws DebugException;

	public int getHitCount() throws CoreException;

	public IRuleDebugThread[] getThreadFilters();

	public void removeFromTarget(IRuleRunTarget ruleRunTarget) throws CoreException;

	public String getBreakpointType() throws CoreException;

	public IRuleDebugThread getThreadFilter(IRuleRunTarget target)  throws CoreException;

	public void setThreadFilter(IRuleDebugThread thread)  throws CoreException;

	public void removeThreadFilter(IRuleRunTarget target)  throws CoreException;

	public abstract String getMarkerType();

	public abstract void addToTarget(IRuleRunTarget target) throws CoreException;

	public abstract boolean shouldSkipBreakpoint() throws CoreException;

}
