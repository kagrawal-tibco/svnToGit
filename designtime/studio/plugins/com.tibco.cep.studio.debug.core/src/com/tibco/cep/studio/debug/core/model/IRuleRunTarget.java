package com.tibco.cep.studio.debug.core.model;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStepFilters;
import org.eclipse.debug.core.model.ITerminate;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.ThreadGroupReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VirtualMachine;
import com.tibco.cep.repo.Project;
import com.tibco.cep.runtime.service.cluster.CacheAgent.Type;
import com.tibco.cep.studio.debug.input.VmTask;
import com.tibco.cep.studio.debug.smap.SMapResourceProvider;
import com.tibco.cep.studio.debug.smap.SourceMapper;

public interface IRuleRunTarget extends IRuleDebugElement,ITerminate,IDebugTarget,IStepFilters  {
	
	public static int END_OF_RTC = 0x1000;
	public static int DEBUG_TASK_REPONSE = 0x2000;
	
	SourceMapper getSourceMapper();
	
    RunSession getSession();
    
    String  getProjectName();
    
    Project getBEProject();
    
    Map<String,String> getRuleSessionMap();
    
    IProject getWorkspaceProject();
    
    SMapResourceProvider getSMapResourceProvider();
    
    public List<ISourceInfoProvider> getSourceInfoProviders() throws CoreException;
    
    void init() throws DebugException;
    
    void start() throws DebugException;

	void started();

	void terminated();

	void onVmStart();
	
	void onVmDisconnect();
	
	void onVmDeath();
	
	RuleDebugThread findThread(ThreadReference ref);

	IRuleDebugThreadGroup findThreadGroup(ThreadGroupReference reference);

	RuleDebugThread getDebuggerServiceThread();
	
	boolean supportsDisconnect();
	
	boolean supportsTerminate();

	void addInputVmTask(VmTask task)throws DebugException;

	void shutdown();

	boolean isAvailable();

	List<IRuleBreakpoint> getBreakpoints();

	void setRequestTimeout(int value);

	VirtualMachine getVM();

	ObjectReference getAgendaItem(RuleDebugThread ruleDebugThread) throws DebugException;
	
    ObjectReference getRuleServiceProvider() throws DebugException;
    
    boolean isCacheServerMode() throws DebugException;
    
    String getClusterName()  throws DebugException;

	/**
	 * Returns the TaskRegistryMap 
	 * @return
	 */
	Map<String,String> getTaskRegistryMapper(); 
	
	boolean isProcessAgent();

	Map<String, Type> getAgentTypeMap();

	public String getCurrentRuleSessionName(RuleDebugThread rdt) throws DebugException;
	
}
