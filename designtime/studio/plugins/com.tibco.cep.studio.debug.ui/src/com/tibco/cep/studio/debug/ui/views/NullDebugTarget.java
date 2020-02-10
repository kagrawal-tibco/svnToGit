/**
 * 
 */
package com.tibco.cep.studio.debug.ui.views;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.ThreadGroupReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.EventRequestManager;
import com.tibco.cep.repo.Project;
import com.tibco.cep.runtime.service.cluster.CacheAgent.Type;
import com.tibco.cep.studio.debug.core.model.ISourceInfoProvider;
import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.IRuleDebugThreadGroup;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.core.model.RunSession;
import com.tibco.cep.studio.debug.input.VmTask;
import com.tibco.cep.studio.debug.smap.SMapResourceProvider;
import com.tibco.cep.studio.debug.smap.SourceMapper;

@SuppressWarnings({"rawtypes", "restriction"})
public class NullDebugTarget  implements IDebugTarget,IRuleRunTarget {
	private static String name = "NullDebugTarget";
	private static NullDebugTarget instance = null;
	
	private  NullDebugTarget() {
		// TODO Auto-generated constructor stub
	}
	
	public static NullDebugTarget getInstance() {
		if(instance == null) {
			instance = new NullDebugTarget();
		}
		return instance;
	}
	
	@Override
	public String getName() throws DebugException {
		return name;
	}

	@Override
	public IProcess getProcess() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public IThread[] getThreads() throws DebugException {
		throw  new UnsupportedOperationException();
	}

	@Override
	public boolean hasThreads() throws DebugException {
		throw  new UnsupportedOperationException();
	}

	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		throw  new UnsupportedOperationException();
	}

	@Override
	public IDebugTarget getDebugTarget() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public ILaunch getLaunch() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public String getModelIdentifier() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public Object getAdapter(Class adapter) {
		throw  new UnsupportedOperationException();
	}

	@Override
	public boolean canTerminate() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public boolean isTerminated() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void terminate() throws DebugException {
		throw  new UnsupportedOperationException();
	}

	@Override
	public boolean canResume() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public boolean canSuspend() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public boolean isSuspended() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void resume() throws DebugException {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void suspend() throws DebugException {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		throw  new UnsupportedOperationException();
	}

	@Override
	public boolean canDisconnect() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void disconnect() throws DebugException {
		throw  new UnsupportedOperationException();
	}

	@Override
	public boolean isDisconnected() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {
		throw  new UnsupportedOperationException();
	}

	@Override
	public boolean supportsStorageRetrieval() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public RuleDebugThread findThread(ThreadReference ref) {
		throw  new UnsupportedOperationException();
	}

	@Override
	public IRuleDebugThreadGroup findThreadGroup(ThreadGroupReference reference) {
		throw  new UnsupportedOperationException();
	}

	@Override
	public Project getBEProject() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public RuleDebugThread getDebuggerServiceThread() {
		throw  null;
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.IRuleRunTarget#inputAction(com.tibco.cep.studio.debug.input.VmTask)
	 */
	@Override
	public void addInputVmTask(VmTask task) throws DebugException {
		throw  new UnsupportedOperationException();
	}

	@Override
	public String getProjectName() {
		return  "";
	}

	@Override
	public SMapResourceProvider getSMapResourceProvider() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public RunSession getSession() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public SourceMapper getSourceMapper() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public IProject getWorkspaceProject() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void init() throws DebugException {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void onVmDeath() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void onVmDisconnect() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void onVmStart() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void start() throws DebugException {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void started() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public boolean supportsDisconnect() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public boolean supportsTerminate() {
		throw  new UnsupportedOperationException();
	}

	@Override
	public void terminated() {
		throw  new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStepFilters#isStepFiltersEnabled()
	 */
	@Override
	public boolean isStepFiltersEnabled() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStepFilters#setStepFiltersEnabled(boolean)
	 */
	@Override
	public void setStepFiltersEnabled(boolean enabled) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStepFilters#supportsStepFilters()
	 */
	@Override
	public boolean supportsStepFilters() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.IRuleRunTarget#shutdown()
	 */
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<IRuleBreakpoint> getBreakpoints() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setRequestTimeout(int value) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public VirtualMachine getVM() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ObjectReference getAgendaItem(RuleDebugThread ruleDebugThread)
			throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectReference getRuleServiceProvider() throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCacheServerMode() throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getClusterName() throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, String> getTaskRegistryMapper() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, String> getRuleSessionMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isProcessAgent() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<ISourceInfoProvider> getSourceInfoProviders() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Type> getAgentTypeMap() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getCurrentRuleSessionName(RuleDebugThread rdt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventRequestManager getEventRequestManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ensureActiveVM() throws DebugException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean supportsRequestTimeout() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRequestTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fireTerminateEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireSuspendEvent(int detail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireResumeEvent(int detail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireCreationEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireChangeEvent(int detail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireEvent(DebugEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void queueEvent(DebugEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void queueSuspendEvent(int detail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logError(Exception e) {
		// TODO Auto-generated method stub
		
	}
	
	

}