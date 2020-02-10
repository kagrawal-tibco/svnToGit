package com.tibco.cep.studio.debug.core.model;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ISuspendResume;
import org.eclipse.debug.core.model.ITerminate;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.request.EventRequestManager;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;

/*
 @author ssailapp
 @date Jul 23, 2009 3:49:38 PM
 */

public abstract class RuleDebugElement extends PlatformObject implements IRuleDebugElement {

	protected IDebugTarget debugTarget;

	public RuleDebugElement(IDebugTarget target) {
		debugTarget = target;
	}

	public String getModelIdentifier() {
		return DebuggerConstants.ID_RULE_DEBUG_MODEL;
	}

	public IDebugTarget getDebugTarget() {
		return debugTarget;
	}

	public void setDebugTarget(IDebugTarget target) {
		debugTarget = target;
	}

	public ILaunch getLaunch() {
		return getDebugTarget().getLaunch();
	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (adapter == ISuspendResume.class) {
			return getDebugTarget();
		}
		if (adapter == IDebugElement.class) {
			return this;
		}
		if (adapter == IRuleDebugTarget.class) {
			return getDebugTarget();
		}
		if (adapter == IRuleRunTarget.class) {
			return getDebugTarget();
		}
		if (adapter == IDebugTarget.class) {
			return getDebugTarget();
		}
		if (adapter == ITerminate.class) {
			return getDebugTarget();
		}
		if (adapter == ILaunch.class) {
			return getLaunch();
		}
		return super.getAdapter(adapter);
	}

	/**
	 * @see org.eclipse.debug.core.model.IStepFilters#isStepFiltersEnabled()
	 */
	@Override
	public boolean isStepFiltersEnabled() {
		return ((AbstractDebugTarget) getDebugTarget()).isStepFiltersEnabled();
	}

	public void abort(String message, Throwable e) throws DebugException {
		throw new DebugException(new Status(IStatus.ERROR, StudioDebugCorePlugin.PLUGIN_ID,
				DebugPlugin.INTERNAL_ERROR, message, e));
	}

	/**
	 * Throws a new debug exception with a status code of
	 * <code>NOT_SUPPORTED</code>.
	 * 
	 * @param message
	 *            Failure message
	 * @throws DebugException
	 *             The exception with a status code of
	 *             <code>NOT_SUPPORTED</code>.
	 */
	public void notSupported(String message) throws DebugException {
		throwDebugException(message, DebugException.NOT_SUPPORTED, null);
	}

	/**
	 * Logs the given exception if it is a JDI exception, otherwise throws the
	 * runtime exception.
	 * 
	 * @param e
	 *            The internal runtime exception
	 */
	public void internalError(RuntimeException e) {
		if (e.getClass().getName().startsWith("com.sun.jdi")) {
			logError(e);
		} else {
			throw e;
		}
	}

	/**
	 * Logs a debug exception with the given message, with a status code of
	 * <code>INTERNAL_ERROR</code>.
	 * 
	 * @param message
	 *            The internal error message
	 */
	protected void internalError(String message) {
		logError(new DebugException(new Status(IStatus.ERROR, RuleDebugModel.getPluginIdentifier(), DebugException.INTERNAL_ERROR, message, null)));
	}

	/**
	 * Convenience method to log errors
	 */
	@Override
	public void logError(Exception e) {
		if (!((ISuspendResume) getDebugTarget()).isSuspended()) {
			// Don't log VMDisconnectedExceptions that occur
			// when the VM is unavailable.
			if (e instanceof VMDisconnectedException
					|| (e instanceof CoreException && ((CoreException) e).getStatus().getException() instanceof VMDisconnectedException)) {
				return;
			}
		}
		StudioDebugCorePlugin.log(e);
	}

	/**
	 * Queues a debug event marking the SUSPEND of this element with the
	 * associated detail.
	 * 
	 * @param detail
	 *            The int detail of the event
	 * @see org.eclipse.debug.core.DebugEvent
	 */
	@Override
	public void queueSuspendEvent(int detail) {
		((AbstractDebugTarget) getDebugTarget()).incrementSuspendCount(detail);
		queueEvent(new DebugEvent(this, DebugEvent.SUSPEND, detail));
	}

	/**
	 * Queues a debug event with the event dispatcher to be fired as an event
	 * set when all event processing is complete.
	 * 
	 * @param event
	 *            the event to queue
	 */
	@Override
	public void queueEvent(DebugEvent event) {
		RunSession session = ((AbstractDebugTarget) getDebugTarget()).getSession();
		if (session != null) {
			EventDispatcher dispatcher = session.getEventDispatcher();
			if (dispatcher != null) {
				dispatcher.queue(event);
			}
		}
	}

	/**
	 * Throws a new debug exception with a status code of
	 * <code>REQUEST_FAILED</code>.
	 * 
	 * @param message
	 *            Failure message
	 * @param e
	 *            Exception that has occurred (<code>can be null</code>)
	 * @throws DebugException
	 *             The exception with a status code of
	 *             <code>REQUEST_FAILED</code>
	 */
	public void requestFailed(String message, Exception e) throws DebugException {
		requestFailed(message, e, DebugException.REQUEST_FAILED);
	}

	/**
	 * Throws a new debug exception with the given status code.
	 * 
	 * @param message
	 *            Failure message
	 * @param e
	 *            Exception that has occurred (<code>can be null</code>)
	 * @param code
	 *            status code
	 * @throws DebugException
	 *             a new exception with given status code
	 */
	public void requestFailed(String message, Throwable e, int code) throws DebugException {
		throwDebugException(message, code, e);
	}

	/**
	 * Throws a new debug exception with a status code of
	 * <code>TARGET_REQUEST_FAILED</code> with the given underlying exception.
	 * If the underlying exception is not a JDI exception, the original
	 * exception is thrown.
	 * 
	 * @param message
	 *            Failure message
	 * @param e
	 *            underlying exception that has occurred
	 * @throws DebugException
	 *             The exception with a status code of
	 *             <code>TARGET_REQUEST_FAILED</code>
	 */
	public void targetRequestFailed(String message, RuntimeException e) throws DebugException {
		if (e == null || e.getClass().getName().startsWith("com.sun.jdi")) {
			requestFailed(message, e, DebugException.TARGET_REQUEST_FAILED);
		} else {
			throw e;
		}
	}

	/**
	 * Throws a new debug exception with a status code of
	 * <code>TARGET_REQUEST_FAILED</code>.
	 * 
	 * @param message
	 *            Failure message
	 * @param e
	 *            Throwable that has occurred
	 * @throws DebugException
	 *             The exception with a status code of
	 *             <code>TARGET_REQUEST_FAILED</code>
	 */
	public void targetRequestFailed(String message, Throwable e) throws DebugException {
		throwDebugException(message, DebugException.TARGET_REQUEST_FAILED, e);
	}

	/**
	 * Throws a debug exception with the given message, error code, and
	 * underlying exception.
	 */
	protected void throwDebugException(String message, int code, Throwable exception) throws DebugException {
		if (exception instanceof VMDisconnectedException) {
			disconnected();
		}
		throw new DebugException(new Status(IStatus.ERROR, StudioDebugCorePlugin.getUniqueIdentifier(), code, message, exception));
	}

	/**
	 * The VM has disconnected. Notify the target.
	 */
	protected void disconnected() {
		if (getDebugTarget() != null) {
			if (getDebugTarget() instanceof IRuleDebugTarget) {
				((IRuleDebugTarget) getDebugTarget()).disconnected();
			}
		}
		// if (getDebugTarget() != null) {
		// if(!getDebugTarget().isDisconnected()) {
		// try {
		// getDebugTarget().terminate();
		// } catch (DebugException e) {
		// StudioDebugCorePlugin.log(e);
		// } finally {
		// fireEvent(new DebugEvent(getDebugTarget(), DebugEvent.TERMINATE));
		// }
		// }
		// }

	}

	// Fires a debug event
	@Override
	public void fireEvent(DebugEvent event) {
		StudioDebugCorePlugin.debug(event.toString());
		DebugPlugin.getDefault().fireDebugEventSet(new DebugEvent[] { event });
	}

	// Fires a <code>CHANGE</code> event for this element.
	@Override
	public void fireChangeEvent(int detail) {
		fireEvent(new DebugEvent(this, DebugEvent.CHANGE, detail));
	}

	// Fires a <code>CREATE</code> event for this element.
	@Override
	public void fireCreationEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.CREATE));
	}

	// Fires a <code>RESUME</code> event for this element
	@Override
	public void fireResumeEvent(int detail) {
		fireEvent(new DebugEvent(this, DebugEvent.RESUME, detail));
	}

	// Fires a <code>SUSPEND</code> event for this element
	@Override
	public void fireSuspendEvent(int detail) {
		fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, detail));
	}

	// Fires a <code>TERMINATE</code> event for this element.
	@Override
	public void fireTerminateEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.TERMINATE));
	}

	/**
	 * @param breakPointId
	 * @return
	 */
	public static IRuleBreakpoint getBreakPointById(String breakPointId) {
		IBreakpointManager bpm = DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint[] bps = bpm.getBreakpoints(RuleDebugModel.getModelIdentifier());
		for (IBreakpoint bp : bps) {
			if (bp instanceof IRuleBreakpoint) {
				IRuleBreakpoint lbp = (IRuleBreakpoint) bp;
				if (lbp.getId().equals(breakPointId)) {
					return lbp;
				}
			}

		}
		return null;
	}

	/**
	 * @param bpe
	 * @return
	 */
	public static String getBreakPointId(BreakpointEvent bpe) {
		return (String) bpe.request().getProperty(DebuggerConstants.BREAKPOINT_ID);
	}

	public static IRuleBreakpoint getBreakPointFromEvent(BreakpointEvent bpe) {
		return (IRuleBreakpoint) bpe.request().getProperty(IRuleBreakpoint.RULE_BREAKPOINT_PROPERTY);
	}

	/**
	 * Returns the target VM associated with this element, or <code>null</code>
	 * if none.
	 * 
	 * @return target VM or <code>null</code> if none
	 */
	public VirtualMachine getVM() {
		return ((IRuleRunTarget) getDebugTarget()).getVM();
	}

	/**
	 * @return
	 */
	@Override
	public int getRequestTimeout() {
		if (supportsRequestTimeout()) {
			VirtualMachine vm = getVM();
			if (vm != null) {
				return -1;
			}
		}
		return -1;
	}

	/**
	 * @see IJavaDebugTarget#supportsRequestTimeout()
	 */
	@Override
	public boolean supportsRequestTimeout() {
		// return getJavaDebugTarget().isAvailable() && getVM() instanceof
		// org.eclipse.jdi.VirtualMachine;
		return false;
	}

	/**
	 * @throws CoreException
	 */
	@Override
	public void ensureActiveVM() throws DebugException {
		if (getVM() == null)
			abort("No Virtual machine found", null);
	}

	/**
	 * Returns the underlying VM's event request manager, or <code>null</code>
	 * if none (disconnected/terminated)
	 * 
	 * @return event request manager or <code>null</code>
	 */
	@Override
	public EventRequestManager getEventRequestManager() {
		VirtualMachine vm = getVM();
		if (vm == null) {
			return null;
		}
		return vm.eventRequestManager();
	}

}
