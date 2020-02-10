package com.tibco.cep.studio.debug.core.model;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugElement;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.EventRequestManager;

public interface IRuleDebugElement extends IDebugElement{

	public abstract EventRequestManager getEventRequestManager();

	public abstract void ensureActiveVM() throws DebugException;

	public abstract boolean supportsRequestTimeout();

	public abstract int getRequestTimeout();

	public abstract VirtualMachine getVM();

	public abstract void fireTerminateEvent();

	public abstract void fireSuspendEvent(int detail);

	public abstract void fireResumeEvent(int detail);

	public abstract void fireCreationEvent();

	public abstract void fireChangeEvent(int detail);

	public abstract void fireEvent(DebugEvent event);

	public abstract void queueEvent(DebugEvent event);

	public abstract void queueSuspendEvent(int detail);

	public abstract void logError(Exception e);

	public abstract boolean isStepFiltersEnabled();

}
