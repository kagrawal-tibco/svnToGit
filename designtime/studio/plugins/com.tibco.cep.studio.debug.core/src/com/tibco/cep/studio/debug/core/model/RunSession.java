package com.tibco.cep.studio.debug.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.tibco.cep.runtime.service.cluster.CacheAgent;

/*
 * Created using Eclipse.
 * User: ssailapp
 * Date: Mar 17, 2009
 * Time: 4:51:41 PM
 */

public interface RunSession extends JdiSession {
	
	public static final String  PROP_STATE = "DebugState";

    public static final int STATE_DISCONNECTED = -1;
    public static final int STATE_STARTING = 1;
    public static final int STATE_RUNNING = 2;
    public static final int STATE_STEPPING = 3;
    public static final int STATE_BREAKPOINT = 4;
    public static final int STATE_SUSPENDED = 5;
    
	
    void addInputVmTask(Object key) throws DebugException;

    void assertData(RuleDebugThread thread, String entityURI, String xmlData, String ruleSessionURI, String destinationURI) throws CoreException;

    public Map<String,CacheAgent.Type> getAgentTypeMap();

    public List<IBreakpointHandler> getBreakPointHandlers();

    ArrayList<EventRequest> getBreakpoints();
    
    String getClusterName()  throws DebugException;

    public String getCurrentRuleSession(RuleDebugThread rdt) throws DebugException;

    RuleDebugThread getDebuggerServiceThread();

    RuleDebugThread getDebugThreadInfo();    

    RuleDebugThread getDebugThreadInfo(ThreadReference tr);
    
	EventDispatcher getEventDispatcher();

	EventRequestManager getEventRequestManager();
	
	ObjectReference getRuleServiceProvider();

	Map<String, String> getRuleSessionMap();

	public List<IStepHandler> getStepHandlers();

	/** Life Cycle Management Interface methods
     * * @throws Exception
     */

    void init() throws DebugException;

	boolean isCacheServerMode() throws DebugException;

	boolean isProcessAgent();
	
	public boolean isStepping();
	
	public boolean isSuspended();
	
	void resume() throws DebugException;	
	
	void setRuleServiceProvider(ObjectReference rspInstance);
	
	void setVM(VirtualMachine vm);
	
	@SuppressWarnings("rawtypes")
	void start(Map props) throws DebugException;
	
	void stop() throws DebugException;
	
	void suspend() throws DebugException;
	
	int getDebugState();
	
}
