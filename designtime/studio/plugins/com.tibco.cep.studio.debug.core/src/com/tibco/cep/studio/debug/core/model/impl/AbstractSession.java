package com.tibco.cep.studio.debug.core.model.impl;

import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_INIT;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_START;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_COMPLETED;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.ClassType;
import com.sun.jdi.InterfaceType;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectCollectedException;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadGroupReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.ClassUnloadEvent;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.event.ThreadStartEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.event.VMStartEvent;
import com.sun.jdi.event.WatchpointEvent;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.runtime.service.cluster.CacheAgent.Type;
import com.tibco.cep.runtime.service.debug.DebugTask;
import com.tibco.cep.runtime.service.debug.DebugTaskFactory;
import com.tibco.cep.runtime.service.debug.DebuggerService;
import com.tibco.cep.runtime.service.debug.SmartStepInto;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.AbstractDebugTarget;
import com.tibco.cep.studio.debug.core.model.DebugThreadsCache;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.EventDispatcher;
import com.tibco.cep.studio.debug.core.model.IBreakpointHandler;
import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.IRuleDebugTarget;
import com.tibco.cep.studio.debug.core.model.IRuleDebugThread;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.IStepHandler;
import com.tibco.cep.studio.debug.core.model.IntermediateBreakpoint;
import com.tibco.cep.studio.debug.core.model.JdiEventListener;
import com.tibco.cep.studio.debug.core.model.RuleDebugElement;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.core.model.RunSession;
import com.tibco.cep.studio.debug.core.model.var.RuleDebugLocalVariable;
import com.tibco.cep.studio.debug.core.model.var.ScorecardVariable;
import com.tibco.cep.studio.debug.input.ClearWorkingMemoryContentsInputTask;
import com.tibco.cep.studio.debug.input.DebugInputTask;
import com.tibco.cep.studio.debug.input.DebugTestDataInputTask;
import com.tibco.cep.studio.debug.input.RuleDeployerTask;
import com.tibco.cep.studio.debug.input.TesterInputTask;
import com.tibco.cep.studio.debug.input.VmResponseTask;
import com.tibco.cep.studio.debug.input.VmTask;
import com.tibco.cep.studio.debug.input.WMContentsInputTask;
import com.tibco.cep.studio.debug.input.WorkingMemoryManipulatorInputTask;
import com.tibco.cep.studio.debug.input.serializer.IResponseDeserializer;
import com.tibco.cep.studio.debug.smap.SourceMapper;
import com.tibco.cep.studio.debug.smap.TaskRegistryMapper;
import com.tibco.cep.studio.debug.util.LocationComparator;

/*
 @author ssailapp
 @date Jul 21, 2009
 */

/**
 * @author pdhar
 * 
 */
public abstract class AbstractSession extends RuleDebugElement implements RunSession {

	public static class EventState implements ResultState {
		boolean isHandled = false;
		boolean canResume = false;

		/**
		 * @param isHandled
		 * @param canResume
		 */
		public EventState(boolean isHandled, boolean canResume) {
			super();
			this.isHandled = isHandled;
			this.canResume = canResume;
		}

		@Override
		public boolean canResume() {
			return canResume;
		}

		@Override
		public boolean isHandled() {
			return isHandled;
		}

		public void setHandled(boolean isHandled) {
			this.isHandled = isHandled;
		}

		public void setResume(boolean canResume) {
			this.canResume = canResume;
		}
	}
	public static class InitSessionTask implements VmTask {
		public static String INIT_SESSION_TASK = "InitSession";
		String key;
		public InitSessionTask() {
			key = INIT_SESSION_TASK;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			InitSessionTask other = (InitSessionTask) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}
		
		@Override
		public IDebugTarget getDebugTarget() {
			return getDebugTarget();
		}

		/**
		 * @return the key
		 */
		public Object getKey() {
			return key;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}
		
		
	}
	protected EventDispatcher eventDispatcher;
	protected VirtualMachine vm;
	protected AttachingConnector attachingConnector;
	protected ILaunch fLaunch;
	protected DebugThreadsCache fThreadsCache;
	protected ReferenceType debuggerServiceType = null;
	protected ReferenceType debugTaskFactoryType = null;
	protected long debuggerServiceThreadUniqueId = -1;
	protected RuleDebugThread threadInfo = null;
	protected EventSet resumeEventSet = null;

	protected int sessionCounter;
	protected boolean fProcessAgent = false;
	/**
	 * be rule session names
	 */
	private Map<String,String> fRuleSessionMap;
	protected HashSet<ReferenceType> classFilterSet = new HashSet<ReferenceType>();
	protected HashSet<ReferenceType> classExclusionFilterSet = new HashSet<ReferenceType>();
	
	protected ArrayList<EventRequest> breakpoints ;
	
	private Map<Object,VmTask> responseTaskMap = new ConcurrentHashMap<Object,VmTask>();
	
	private ObjectReference rspInstance;
	private boolean isCacheServerMode = false;
	private String clusterName = null;
	private ReferenceType taskRegistryType = null;
	private List<IBreakpointHandler> fbreakPointHandlers;
	private List<IStepHandler> fStepHandlers;
	protected PropertyChangeSupport debugStateListeners = new PropertyChangeSupport(this);

	private int debugState;
	
	private Map<String, Type> agentTypeMap;

	/**
	 * @param target
	 * @param launch
	 */
	protected AbstractSession(IDebugTarget target, ILaunch launch,
			VirtualMachine vm) {
		super(target);
		setBreakpoints(new ArrayList<EventRequest>(5));
		setVM(vm);
		setLaunch(launch);
		setEventDispatcher(new EventDispatcher(this));
		setThreadsCache(new DebugThreadsCache(target, this));
		setRuleSessions(new HashMap<String,String>());
		this.sessionCounter = 0;
	}

	/**
	 * @throws CoreException
	 */
	public void addInputVmTask(Object key) throws DebugException {
		if (getDebuggerServiceType() != null) {
			createDebuggerServiceBreakPoint(getDebuggerServiceType());
		}		
	}

	/**
	 * @param eventThread
	 * @param entityURI
	 * @param xmlData
	 * @param ruleSessionURI
	 * @param destinationURI
	 */
	public void assertData(RuleDebugThread eventThread, String entityURI,
			String xmlData, String ruleSessionURI, String destinationURI) {
		try {
			DebuggerSupport.assertData(eventThread, getDebugTaskFactoryType(),
					entityURI, xmlData, ruleSessionURI, destinationURI,
					++sessionCounter);
		} catch (Exception e) {
			StudioDebugCorePlugin.log(e);
		}
	}
	public void assertData(RuleDebugThread eventThread, 
			               String[] entityURI,
			               String[] xmlData, 
			               String[] destinationURI, 
			               String ruleSessionName, 
			               String testerSessionName) {
		try {
			DebuggerSupport.debugTestData(eventThread, getDebugTaskFactoryType(), entityURI, xmlData, destinationURI, ruleSessionName, testerSessionName, ++sessionCounter);
		} catch (Exception e) {
			StudioDebugCorePlugin.log(e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private VirtualMachine attach(String hostname, String port)
			throws IllegalConnectorArgumentsException, IOException {
		VirtualMachine vm = null;
		VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
		List<AttachingConnector> attachingConnectors = vmm.attachingConnectors();
		Iterator<AttachingConnector> it = attachingConnectors.iterator();
		while (it.hasNext()) {
			this.attachingConnector = (AttachingConnector) it.next();
			if (!attachingConnector.transport().name().equals("dt_socket")) {
				continue;
			}

			Map args = attachingConnector.defaultArguments();
			Connector.StringArgument hostnameArg = (Connector.StringArgument) args
					.get("hostname");
			hostnameArg.setValue(hostname);

			Connector.IntegerArgument portArg = (Connector.IntegerArgument) args
					.get("port");
			portArg.setValue(port);

			vm = attachingConnector.attach(args);
			if (vm != null) {

				StudioDebugCorePlugin
						.log("Connected to the target VM, address : "
								+ hostname + ":" + port + "\n");
			}
			break;
		}
		return vm;
	}
	
	protected boolean attachVM() throws DebugException {
		try {
			final String hostName = ((AbstractDebugTarget) getDebugTarget())
					.getHostName();
			final String portNum = ((AbstractDebugTarget) getDebugTarget())
					.getPort();
			if (getVM() == null) {
				VirtualMachine lvm = attach(hostName, portNum);
				if (lvm != null) {
					setVM(lvm);
				}
			}
			if (getVM() != null) {
				startDispatcher();
				fThreadsCache.setVM(getVM());
			} else {
				abort("Failed to get virtual machine", null);
			}
		} catch (IOException e) {
			abort("Failed to attach to the virtualmachine.", e);
		} catch (IllegalConnectorArgumentsException e) {
			abort("Failed to attach to the virtualmachine.", e);
		}
		return getVM() != null;
	}

	/**
	 * @param btype
	 * @throws CoreException
	 */
	public void cleanBreakPointRequestsByType(String btype)
			throws DebugException {
		ensureActiveSession();
		Object[] allbps = getVM().eventRequestManager().breakpointRequests()
		.toArray(new BreakpointRequest[0]);
		try {
			for (int x = 0; x < allbps.length; x++) {
				IRuleBreakpoint bp = (IRuleBreakpoint) ((BreakpointRequest) allbps[x]).getProperty(IRuleBreakpoint.RULE_BREAKPOINT_PROPERTY);
				String type = null;
				type = bp.getBreakpointType();
				if (type != null && type.equals(btype)) {
					synchronized (getEventRequestManager()) {
						StudioDebugCorePlugin.debug(
								"Removing breakpoint request [type:"
								+ type
								+ ":"
								+ ((BreakpointRequest) allbps[x])
								.location()
								+"]");
						bp.removeFromTarget(((IRuleRunTarget)getDebugTarget()));
					}
				}
			}
		} catch (CoreException e) {
			abort("Failed to remove breakpoint type:"+btype,e);
		}
	}

	/**
	 * @param referenceType
	 * @throws CoreException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void createDebuggerServiceBreakPoint(ReferenceType referenceType)
			throws DebugException {
		String methodName = "processTask";
		try {
			cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_DEBUGGER_SERVICE);

			ReferenceType debuggerServiceClassType = referenceType;

			Class clazz = DebuggerService.class;
			if (debuggerServiceClassType == null) {
				debuggerServiceClassType = DebuggerSupport.findClass(getVM(),
						clazz.getName());
			}
			String signature = DebuggerSupport.getMethodSignature(clazz,
					methodName, DebugTask.class);
			Method processTaskMethod = DebuggerSupport
					.findMethodBySignature(
							(ClassType) debuggerServiceClassType, methodName,
							signature);
			LinkedList<? extends Comparable> allLines = new LinkedList<Location>(
					processTaskMethod.allLineLocations());
			Collections.sort((List)allLines,new LocationComparator());
			Location lastLine = (Location) allLines.getFirst();
			Map attributes = new HashMap();
			attributes.put(DebuggerConstants.BREAKPOINT_TYPE,DebuggerConstants.BREAKPOINT_DEBUGGER_SERVICE);
			IntermediateBreakpoint bp = RuleDebugModel.createIntermediateBreakpoint(ResourcesPlugin.getWorkspace().getRoot(), 
					debuggerServiceClassType.name(), 
					lastLine.lineNumber(),
					-1,
					-1, 
					0, 
					false, 
					attributes);
			getDebugTarget().breakpointAdded(bp);

		} catch (NoSuchMethodException e) {
			abort("Failed to find method :" + methodName, e);
		} catch (AbsentInformationException e) {
			abort(e.getMessage(), e);
		} catch (CoreException e) {
			abort("Failed to create debugger service breakpoint",e);
		}
	}

	/**
	 * @param em
	 */
	protected void createDebuggerServiceClassPrepareRequest(
			EventRequestManager em) {
		ClassPrepareRequest classPrepareRequest;
		classPrepareRequest = em.createClassPrepareRequest();
		classPrepareRequest.addClassFilter(DebuggerService.class.getName());
		classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		classPrepareRequest.enable();
	}

	/**
	 * @param em
	 */
	protected void createDebugTaskFactoryClassPrepareRequest(
			EventRequestManager em) {
		ClassPrepareRequest classPrepareRequest;
		classPrepareRequest = em.createClassPrepareRequest();
		classPrepareRequest.addClassFilter(DebugTaskFactory.class.getName());
		classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		classPrepareRequest.enable();
	}

	/**
	 * @param referenceType
	 * @param key 
	 * @throws CoreException
	 */
	protected void createDebugTaskResponseBreakPoint(ReferenceType referenceType, Object key)
			throws DebugException {
		String methodName = "notifyResponse";
		try {
			cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_DEBUG_TASK_RESPONSE);

			ReferenceType debuggerServiceClassType = referenceType;

			Class<DebuggerService> clazz = DebuggerService.class;
			if (debuggerServiceClassType == null) {
				debuggerServiceClassType = DebuggerSupport.findClass(getVM(),
						clazz.getName());
			}
			String signature = DebuggerSupport.getMethodSignature(clazz,
					methodName, Object.class);
			Method processTaskMethod = DebuggerSupport
					.findMethodBySignature(
							(ClassType) debuggerServiceClassType, methodName,
							signature);
			LinkedList<? extends Comparable> allLines = new LinkedList<Location>(
					processTaskMethod.allLineLocations());
			Collections.sort((List)allLines,new LocationComparator());
			Location lastLine = (Location) allLines.getFirst();

			Map<Object, Object> attributes = new HashMap<Object, Object>();
			attributes.put(DebuggerConstants.VM_TASK_KEY, key);
			attributes.put(DebuggerConstants.BREAKPOINT_TYPE,DebuggerConstants.BREAKPOINT_DEBUG_TASK_RESPONSE);
			IntermediateBreakpoint bp = RuleDebugModel.createIntermediateBreakpoint(ResourcesPlugin.getWorkspace().getRoot(), 
					debuggerServiceClassType.name(), 
					lastLine.lineNumber(),
					-1,
					-1, 
					0, 
					false, 
					attributes);
			getDebugTarget().breakpointAdded(bp);

		} catch (NoSuchMethodException e) {
			abort("Failed to find method :" + methodName, e);
		} catch (AbsentInformationException e) {
			abort(e.getMessage(), e);
		} catch (CoreException e) {
			abort("Failed to create debugger service breakpoint",e);
		}
	}

	private void createProcessAgentPrepareRequest(EventRequestManager em) {
		ClassPrepareRequest classPrepareRequest;
		classPrepareRequest = em.createClassPrepareRequest();
		classPrepareRequest.addClassFilter("com.tibco.cep.bpmn.runtime.agent.ProcessAgent");
		classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		classPrepareRequest.enable();
	}

	/**
	 * @param em
	 */
	protected void createTaskRegistryClassPrepareRequest(
			EventRequestManager em) {
		ClassPrepareRequest classPrepareRequest;
		classPrepareRequest = em.createClassPrepareRequest();
		classPrepareRequest.addClassFilter("com.tibco.cep.bpmn.runtime.activity.TaskRegistry");
		classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		classPrepareRequest.enable();
	}

	/**
	 * @param referenceType
	 * @return
	 * @throws CoreException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected boolean createTaskRegistryInitBreakPoint(ReferenceType referenceType) throws CoreException {
		try {
			cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_TASK_REGISTRY_INIT);
			Method initProjectMethod = DebuggerSupport.findMethodBySignature((ClassType) getTaskRegistryType(), "<init>", "()V");
			LinkedList<? extends Comparable> allLines = new LinkedList<Location>(initProjectMethod.allLineLocations());
			Collections.sort((List)allLines,new LocationComparator());
			Location lastLine = (Location) allLines.getLast();
			Map attributes = new HashMap();
			attributes.put(DebuggerConstants.BREAKPOINT_TYPE, DebuggerConstants.BREAKPOINT_TASK_REGISTRY_INIT);
			IntermediateBreakpoint bp = RuleDebugModel.createIntermediateBreakpoint(ResourcesPlugin.getWorkspace().getRoot(), getTaskRegistryType().name(),
					lastLine.lineNumber(), -1, -1, 0, false, attributes);
			getDebugTarget().breakpointAdded(bp);
			return true;

		} catch (AbsentInformationException e) {
			throw new CoreException(new Status(IStatus.ERROR, StudioDebugCorePlugin.getUniqueIdentifier(), "Failed to initTaskTypeRegistry breakpoint", e));
		}
	}

	/**
	 * @param eventThread
	 * @param rules
	 * @param deploy
	 * @throws CoreException
	 */
	public void deployRules(RuleDebugThread eventThread, String[] rules,
			boolean deploy) throws CoreException {
		try {
			DebuggerSupport.deployRules(eventThread, getDebugTaskFactoryType(),
					rules, deploy);
		} catch (InvalidTypeException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					StudioDebugCorePlugin.getUniqueIdentifier(),
					"Failed to deploy rules", e));
		} catch (ClassNotLoadedException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					StudioDebugCorePlugin.getUniqueIdentifier(),
					"Failed to deploy rules", e));
		}
	}

	/**
	 * @throws CoreException
	 */
	void ensureActiveSession() throws DebugException {
		if (getVM() == null)
			abort("No Virtual machine found",null);
	}

	public Map<String, Type> getAgentTypeMap() {
		return agentTypeMap;
	}

	/**
	 * @return
	 */
	public AttachingConnector getAttachingConnector() {
		return attachingConnector;
	}

	public List<IBreakpointHandler> getBreakPointHandlers() {
		return fbreakPointHandlers;
	}

	/**
	 * @return the breakpoints
	 */
	public synchronized ArrayList<EventRequest> getBreakpoints() {
		return breakpoints;
	}
	
	
	
	/**
	 * @return the classExclusionFilterSet
	 */
	public HashSet<ReferenceType> getClassExclusionFilterSet() {
		return classExclusionFilterSet;
	}

	/**
	 * @return the classFilterSet
	 */
	public HashSet<ReferenceType> getClassFilterSet() {
		return classFilterSet;
	}

	public String getClusterName() {
		return clusterName;
	}

	/**
	 * @return
	 */
	public RuleDebugThread getDebuggerServiceThread() {
		ThreadReference tr = DebuggerSupport.getThreadReferenceForUniqueID(getVM(),
				debuggerServiceThreadUniqueId);
		if(tr != null) {
			return getThreadsCache().findThread(tr);
		}
		return null;
	}

	/**
	 * @return
	 */
	public ClassType getDebuggerServiceType() {
		return (ClassType) debuggerServiceType;
	}

	public int getDebugState() {
	    return debugState;
	}

	/**
	 * @return the debugTaskFactoryType
	 */
	public ReferenceType getDebugTaskFactoryType() {
		return debugTaskFactoryType;
	}

	/**
	 * @return
	 */
	public synchronized RuleDebugThread getDebugThreadInfo() {
		return threadInfo;
	}

	/**
	 * @param tr
	 * @return
	 */
	public synchronized RuleDebugThread getDebugThreadInfo(ThreadReference tr) {
		RuleDebugThread info = fThreadsCache.findThread(tr);
		return info;
	}

	// ////////////////////// JdiEventListener ////////////////////////////
	// public abstract void setBreakPointsForReferenceType(ReferenceType
	// refType) throws DebuggerException;

	/**
	 * @return
	 */
	public EventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}

	/**
	 * Returns the underlying VM's event request manager, or <code>null</code>
	 * if none (disconnected/terminated)
	 * 
	 * @return event request manager or <code>null</code>
	 */
	public EventRequestManager getEventRequestManager() {
		VirtualMachine vm = getVM();
		if (vm == null) {
			return null;
		}
		return vm.eventRequestManager();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.JdiSession#getJdiEventListener()
	 */
	public JdiEventListener getJdiEventListener() {
		return this;
	}
	
	/**
	 * @return the launch
	 */
	public ILaunch getLaunch() {
		return fLaunch;
	}
	
	ILaunch getProfile() {
		return fLaunch;
	}

	private IResponseDeserializer getResponseDeserializer(VmResponseTask task) throws CoreException {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(DebuggerConstants.DEBUGGER_EXTENSION_POINT_RESPONSE);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(DebuggerConstants.DEBUGGER_EXTENSION_POINT_RESPONSE_DESERIALIZER);
			if (o instanceof IResponseDeserializer) {
				boolean handlesTask = ((IResponseDeserializer) o).handlesTask(task);
				if (handlesTask) {
					return (IResponseDeserializer) o;
				}
			}
		}	
		return null;
	}

	public EventSet getResumeEventSet() {
		return resumeEventSet;
	}

	public <T extends IRuleDebugTarget> T getRuleDebugTarget() {
	    return (T) super.getDebugTarget();
	}

	public ObjectReference getRuleServiceProvider() {
		return rspInstance;
	}
	
	/**
	 * @return the fRuleSessionMap
	 */
	public synchronized Map<String, String> getRuleSessionMap() {
		return fRuleSessionMap;
	}

	public int getSessionCounter() {
		return sessionCounter;
	}

	public List<IStepHandler> getStepHandlers() {
		return fStepHandlers;
	}

//	private void initBreakpoints() {
//		if(getDebugTarget() instanceof IRuleDebugTarget) {
//			List<ReferenceType> clazzez = getVM().allClasses();
//			for (ReferenceType clazz : clazzez) {
//				setBreakPointsForReferenceType(clazz);
//			}			
//		}
//	}

	public ReferenceType getTaskRegistryType() {
		return taskRegistryType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.studio.debug.core.model.JdiSession#getThreadsCache()
	 */
	public DebugThreadsCache getThreadsCache() {
		return fThreadsCache;
	}
	
	/**
	 * Returns the target VM associated with this element, or <code>null</code>
	 * if none.
	 * 
	 * @return target VM or <code>null</code> if none
	 */
	public VirtualMachine getVM() {
		// assert vm != null;
		return vm;
	}

	public void init() throws DebugException {
		initializeSessionHandlers();
		eventDispatcher.init();
		getThreadsCache().init();
	}
	
	private void initializeSessionHandlers() throws DebugException {
		try {
			fbreakPointHandlers = loadBreakpointHandlers();
			fStepHandlers = loadStepHandlers();
		} catch (CoreException e) {
			throw new DebugException(new Status(IStatus.ERROR,StudioDebugCorePlugin.PLUGIN_ID,"Failed to load breakpoint handlers",e));
		}		
	}
	
	public void initSession(RuleDebugThread rdt) throws DebugException{
		synchronized(rdt){
//			setRuleSessions(Arrays.asList(DebuggerSupport.getRuleSessionNames(rdt)));
			Map<String, String> ruleSessionMap = DebuggerSupport.getRuleSessionsClassMap(rdt);
			List<String> ruleSessions = new ArrayList<String>();
			ruleSessions.addAll(ruleSessionMap.keySet());
			setRuleSessions(ruleSessionMap);
			setRuleServiceProvider(DebuggerSupport.getRuleServiceProvider(rdt));//setting Rule ServiceProvider Object Reference
			setCacheServerMode(DebuggerSupport.isCacheServerMode(rdt));//setting cache server mode
			setClusterName(DebuggerSupport.getClusterName(rdt));//setting cluster name
			setAgentMap(DebuggerSupport.getAgentTypeMap(rdt));
			((IRuleRunTarget) getDebugTarget()).started();
		}
	}
	
	public void initSourceMap(RuleDebugThread rdt, ObjectReference rspInstance ) throws DebugException {
		try {
			SourceMapper mapper = ((AbstractDebugTarget)getDebugTarget()).getSourceMapper();
			if(mapper.isEmpty()) {
				List<byte[]> maps = Collections.emptyList();
				if(rspInstance != null) {
					maps = DebuggerSupport.getSMapData(rdt,rspInstance);
				} else {
					maps = DebuggerSupport.getSMapData(rdt);
				}
				if(maps == null || maps.size() == 0) {
					abort("No debug information found.",null);
				} else {
					mapper.init(maps);
				}
			}
		} catch (Exception e) {
			abort("Failed to load debug information.",e);;
		}
	}
	
	public void initTaskRegistry(RuleDebugThread rdt, ObjectReference trInstance ) throws DebugException {
		try {
			TaskRegistryMapper mapper = ((AbstractDebugTarget)getDebugTarget()).getTaskRegistryMapper();
			if(mapper.isEmpty()) {
				Map<String,String> maps = null;
				if(trInstance != null) {
					maps = DebuggerSupport.getTaskRegistryData(rdt,trInstance);
				} else {
					maps = DebuggerSupport.getTaskRegistryData(rdt);
				}
				if(maps == null || maps.size() == 0) {
					abort("No debug information found.",null);
				} else {
					mapper.init(maps);
				}
			}
		} catch (Exception e) {
			abort("Failed to load debug information.",e);;
		}
	}
	
	@Override
	public boolean isCacheServerMode()
			throws DebugException {
		return isCacheServerMode;
	}
	

	public boolean isConnected() {
		return ((getVM() != null) && (eventDispatcher.isRunning()));
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.RunSession#isProcessAgent()
	 */
	public boolean isProcessAgent() {
		return fProcessAgent;
	}

	/**
	 * @return
	 * @throws CoreException
	 */
	private List<IBreakpointHandler> loadBreakpointHandlers() throws CoreException {
		List<IBreakpointHandler> bpHandlers = new ArrayList<IBreakpointHandler>();
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(StudioDebugCorePlugin.PLUGIN_ID, "debugSessionHandler"); //$NON-NLS-1$
		if(extensionPoint != null) {
			IConfigurationElement[] configs = extensionPoint.getConfigurationElements();
			for (int i= 0; i < configs.length; i++) {
				String name = configs[i].getName();
				if(name.equals("breakpointHandler")) {
					bpHandlers.add((IBreakpointHandler) configs[i].createExecutableExtension("class")); //$NON-NLS-1$
				} 
			}
		}
		return bpHandlers;
	}

	/**
	 * @return
	 * @throws CoreException
	 */
	private List<IStepHandler> loadStepHandlers() throws CoreException {
		List<IStepHandler> stepHandlers = new ArrayList<IStepHandler>();
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(StudioDebugCorePlugin.PLUGIN_ID, "debugSessionHandler"); //$NON-NLS-1$
		if(extensionPoint != null) {
			IConfigurationElement[] configs = extensionPoint.getConfigurationElements();
			for (int i= 0; i < configs.length; i++) {
				String name = configs[i].getName();
				if(name.equals("stepHandler")) {
					stepHandlers.add((IStepHandler) configs[i].createExecutableExtension("class")); //$NON-NLS-1$
				}
			}
		}
		return stepHandlers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.JdiEventListener#onBreakpoint(com
	 * .sun.jdi.event.BreakpointEvent)
	 */
	public EventState onBreakpoint(EventSet eventSet,final BreakpointEvent bpe) {

		final ThreadReference thread = bpe.thread();
		setDebugThreadInfo(thread);
		final RuleDebugThread rdt = getDebugThreadInfo(thread);
		final IRuleBreakpoint bp = getBreakPointFromEvent(bpe);
		EventState eventState = new EventState(false, false);//not handled here, not resumed here
		
		String bpType = null;
		try {
			bpType = bp.getBreakpointType();
		} catch (CoreException e1) {
			StudioDebugCorePlugin.log(e1);
		}
		
		for(IBreakpointHandler h: fbreakPointHandlers) {
			if(h.handlesMarkerType(bp.getMarkerType())) {
				return h.handleBreakpoint(this,eventSet,bpe,bpType,rdt,bp,eventState);
			}
		}

//		if (bpType != null) {
//			if (bpType.equals(DebuggerConstants.BREAKPOINT_DEBUGGER_SERVICE)) {
//				try {
//					// Always clear the debugger service breakpoint because we
//					// do not want to resume while the session is suspended 
//					// by a break point or step request
//					// let the user create the break point on demand
//					cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_DEBUGGER_SERVICE);
//					boolean resume = onInputVmTask(bpe);
//					eventState.setResume(resume);
//				} catch (VMDisconnectedException vme) {
//					StudioDebugCorePlugin.log(vme);
//				} finally {
//					eventState.setHandled(true); // debugger service handle here
//					return eventState;
//				}
//			} else if(bpType.equals(DebuggerConstants.BREAKPOINT_DEBUG_TASK_RESPONSE)) { 
//				try {
//					// Always clear the debug task response breakpoint because we
//					// do not want to resume while the session is suspended
//					// by a break point or step request
//					// let the user create the break point on demand
//					cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_DEBUG_TASK_RESPONSE);
//					boolean resume = onResponseAction(bpe);
//					eventState.setResume(resume);
//				} catch (VMDisconnectedException vme) {
//					StudioDebugCorePlugin.log(vme);
//				} finally {
//					eventState.setHandled(true); // debugger service handle here
//					return eventState;
//				}
//			} else if(bpType.equals(DebuggerConstants.BREAKPOINT_TASK_REGISTRY_INIT)){
//				try {
//					synchronized (rdt) {
//						try {
//							rdt.setRunning(false);
//							rdt.addCurrentBreakpoint(bp);
//							RuleDebugStackFrame topStackFrame = (RuleDebugStackFrame) rdt.getTopStackFrame();
//							ObjectReference trInstance = topStackFrame.getUnderlyingThisObject();
//							initTaskRegistry(rdt, trInstance);
//						} finally {
//							rdt.removeCurrentBreakpoint(bp);
//							cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_TASK_REGISTRY_INIT);
//							rdt.resumedByVM();
//						}
//					}
//				}  catch (VMDisconnectedException vme) {
//					StudioDebugCorePlugin.log(vme);
//				}  catch (DebugException e) {
//					StudioDebugCorePlugin.log("Failed to load Task Registry",e);
//				} finally {
//					eventState.setResume(true);
//					eventState.setHandled(true); // debugger service handle here
//					return eventState;
//				}
//			}else if(bpType.equals(DebuggerConstants.BREAKPOINT_INIT_PROJECT)) { 
//				try {
//					synchronized (rdt) {
//						IRuleBreakpoint tbp = getBreakPointFromEvent(bpe);
//						try {
//						rdt.setRunning(false);
//						rdt.addCurrentBreakpoint(tbp);
//						RuleDebugStackFrame topStackFrame = (RuleDebugStackFrame) rdt.getTopStackFrame();
//						ObjectReference rspInstance = topStackFrame.getUnderlyingThisObject();
//						initSourceMap(rdt, rspInstance);
//						} finally {
//							rdt.removeCurrentBreakpoint(tbp);
//							cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_INIT_PROJECT);
//							rdt.resumedByVM();
//						}
//					}
//				}  catch (VMDisconnectedException vme) {
//					StudioDebugCorePlugin.log(vme);
//				}  catch (DebugException e) {
//					StudioDebugCorePlugin.log("Failed to load source maps",e);
//				} finally {
//					eventState.setResume(true);
//					eventState.setHandled(true); // debugger service handle here
//					return eventState;
//				}
//			} else if(bpType.equals(DebuggerConstants.BREAKPOINT_WM_START)){
//				try {
//					synchronized (rdt) {
//						rdt.setRunning(false);
//						rdt.addCurrentBreakpoint(getBreakPointFromEvent(bpe));
//						//getDebugThreadInfo().fireSuspendEvent(DebugEvent.BREAKPOINT);
//						cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_WM_START);
//						if (getDebugTarget() instanceof IRuleDebugTarget) {
//							((IRuleDebugTarget) getDebugTarget())
//							.initializeBreakpoints();
//						}
//						//getDebugThreadInfo().fireResumeEvent(DebugEvent.CLIENT_REQUEST);
//						rdt.resumedByVM();
//					}
//					
//				}  catch (VMDisconnectedException vme) {
//					StudioDebugCorePlugin.log(vme);
//				}  catch (DebugException e) {
//					StudioDebugCorePlugin.log("Failed to initialize breakpoints",e);
//				} finally {
//					eventState.setResume(true);
//					eventState.setHandled(true); // debugger service handle here
//					return eventState;
//				}
//			}else {				
//				eventState.setHandled(false); // not handled here let the
//				// subclass handle it
//			}
//		} else {
//			StudioDebugCorePlugin.debug("Unknown breakpoint type:"
//					+ bpe.location());
//		}
//		
//		eventState.setHandled(false); // not handled here let the subclass
//										// handle it
		return eventState;
	}
	
	public EventState onClassPrepare(EventSet eventSet,final ClassPrepareEvent cpe) {
		final ReferenceType refType = cpe.referenceType();
		final String refTypeName = refType.name();
		final ThreadReference thread = cpe.thread();
		
		EventState eventState = new EventState(false, true);
		
		setDebugThreadInfo(thread);
		
		StudioDebugCorePlugin.debug("ClassPrepare event :" + cpe.referenceType().name());
		if (refTypeName.equals(DebuggerService.class.getName())) {
			setDebuggerServiceType(refType);
			// do not create debugger service break point at startup and keep it
			// running
			// since breakpoints can be inserted or removed at any time after
			// the referencetype
			// has been loaded, so it should be created on user input in the
			// following manner
			// 1. let the user set the input and issue the command
			// 2. set the break point
			// 3. on breakpoint hit execute the task
			// 4. remove the break point
			// 5. resume the vm

			// try {
			// createDebuggerServiceBreakPoint(debuggerServiceType);
			// } catch (CoreException e) {
			// logError(e);
			// }
			// get the rule sessions
			eventState.setHandled(true);
			// return true;
		}
		if (refTypeName.equals("com.tibco.cep.bpmn.runtime.agent.ProcessAgent")) {
			setProcessAgent(true);
			eventState.setHandled(true);
		}

		if (AddonUtil.isProcessAddonInstalled()) {
			if (refTypeName.equals("com.tibco.cep.bpmn.runtime.activity.TaskRegistry")) {
				setTaskRegistryType(refType);
				try {
					eventState.setResume(createTaskRegistryInitBreakPoint(refType));
				} catch (CoreException e) {
					logError(e);
				}
				eventState.setHandled(true);
			}
		}
		if (refTypeName.equals(DebugTaskFactory.class.getName())) {
			setDebugTaskFactoryType(refType);
			eventState.setHandled(true);
		}
		// return false;
		eventState.setHandled(false);
		return eventState;
	}

	public EventState onClassUnload(EventSet eventSet,ClassUnloadEvent cpe) {
		EventState eventState = new EventState(false, true); // not handled here
		StudioDebugCorePlugin.debug("Unloading class:"
				+ cpe.className());
		// return false;
		return eventState;
	}

	public void onEventSet(EventSet eset) {
		this.resumeEventSet = eset;
	}

	public EventState onException(EventSet eventSet,ExceptionEvent ee) {
		EventState eventState = new EventState(false, true); // not handled
																// here,resumed
																// here
		setDebugThreadInfo(ee.thread());
		StudioDebugCorePlugin.debug("Exception event :"
				+ ee.exception().referenceType().name());
		// return false;
		return eventState;
	}

	/**
	 * @param bpe
	 * @return
	 * @throws DebugException
	 */
	public boolean onInputVmTask(BreakpointEvent bpe) throws DebugException {
		try {
			VmTask task = ((AbstractDebugTarget) getDebugTarget())
					.getInputQueue().poll();
			if (task != null) {
				if(task instanceof VmResponseTask) {
					createDebugTaskResponseBreakPoint(getDebuggerServiceType(), task.getKey());
					responseTaskMap.put(task.getKey(),task);
				}
				if (task instanceof TesterInputTask) {
					TesterInputTask tiTask = (TesterInputTask) task;
					RuleDebugThread thread = getDebugThreadInfo(bpe.thread());
					synchronized (thread) {
						thread.setRunning(false);
						thread.addCurrentBreakpoint(getBreakPointFromEvent(bpe));
						tiTask.execute(thread);
						thread.resumedByVM();
					}
					return true;
				} else if (task instanceof WMContentsInputTask) {
					WMContentsInputTask wmContentsInputTask = (WMContentsInputTask) task;
					RuleDebugThread thread = getDebugThreadInfo(bpe.thread());
					synchronized (thread) {
						thread.setRunning(false);
						thread.addCurrentBreakpoint(getBreakPointFromEvent(bpe));
						wmContentsInputTask.execute(thread);
						thread.resumedByVM();
					}
					return true;
				} else if (task instanceof DebugInputTask) {
					DebugInputTask diTask = (DebugInputTask) task;
					RuleDebugThread thread = getDebugThreadInfo(bpe.thread());
					synchronized (thread) {
						thread.setRunning(false);
						thread.addCurrentBreakpoint(getBreakPointFromEvent(bpe));
						assertData(thread, diTask.getEntityURI(),
								diTask.getXmlData(), diTask.getRuleSessionURI(),
								diTask.getDestinationURI());
						thread.resumedByVM();
					}
					return true;
				}else if (task instanceof DebugTestDataInputTask) {
					DebugTestDataInputTask diTask = (DebugTestDataInputTask) task;
					RuleDebugThread thread = getDebugThreadInfo(bpe.thread());
					synchronized (thread) {
						thread.setRunning(false);
						thread.addCurrentBreakpoint(getBreakPointFromEvent(bpe));
						assertData(thread, diTask.getEntityURI(), 
								diTask.getXmlData(), diTask.getDestinationURI(), diTask.getRuleSessionName(), diTask.getTesterSessionName()
								);
						thread.resumedByVM();
					}
					return true;
				}else if (task instanceof RuleDeployerTask) {
					RuleDebugThread thread = getDebugThreadInfo(bpe.thread());
					synchronized (thread) {
						thread.setRunning(false);
						thread.addCurrentBreakpoint(getBreakPointFromEvent(bpe));
						RuleDeployerTask rdTask = (RuleDeployerTask) task;
						deployRules(thread, rdTask.getRules(), rdTask
								.isDeploy());
						thread.resumedByVM();
					}
					return true;
				} else if (task instanceof InitSessionTask) {
					RuleDebugThread thread = getDebugThreadInfo(bpe.thread());
					synchronized (thread) {
						thread.setRunning(false);
						thread.addCurrentBreakpoint(getBreakPointFromEvent(bpe));
						initSourceMap(thread,null);
						if(getDebugTarget() instanceof IRuleDebugTarget){
							((IRuleDebugTarget)getDebugTarget()).initializeBreakpoints();
						}
						initSession(thread);
						thread.resumedByVM();
					}
					return true;
				} else if (task instanceof WorkingMemoryManipulatorInputTask) {
					WorkingMemoryManipulatorInputTask tmmInputTask = (WorkingMemoryManipulatorInputTask) task;
					RuleDebugThread thread = getDebugThreadInfo(bpe.thread());
					synchronized (thread) {
						thread.setRunning(false);
						thread.addCurrentBreakpoint(getBreakPointFromEvent(bpe));
						tmmInputTask.execute(thread);
						thread.resumedByVM();
					}
					return true;
				} else if (task instanceof ClearWorkingMemoryContentsInputTask) {
					ClearWorkingMemoryContentsInputTask cWM = (ClearWorkingMemoryContentsInputTask) task;
					RuleDebugThread thread = getDebugThreadInfo(bpe.thread());
					synchronized (thread) {
						thread.setRunning(false);
						thread.addCurrentBreakpoint(getBreakPointFromEvent(bpe));
						cWM.execute(thread);
						thread.resumedByVM();
					}
					return true;
				}
			}
		} catch (CoreException e) {
			DebugPlugin.log(e);
			((AbstractDebugTarget) getDebugTarget()).abort(
					"Failed to perform input action", e);
		} catch (Exception e) {
			DebugPlugin.log(e);
			((AbstractDebugTarget) getDebugTarget()).abort(
					"Failed to perform input action", e);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.JdiEventListener#onMethodEntry(
	 * com.sun.jdi.event.MethodEntryEvent)
	 */
	public EventState onMethodEntry(EventSet eventSet,MethodEntryEvent event) {
		EventState eventState = new EventState(false, true);// subclass handling
															// not allowed,
															// resume allowed
		StudioDebugCorePlugin.debug("Entering method:"
				+ event.method().name());
		setDebugThreadInfo(event.thread());
		return eventState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.JdiEventListener#onMethodExit(com
	 * .sun.jdi.event.MethodExitEvent)
	 */
	public EventState onMethodExit(EventSet eventSet,MethodExitEvent event) {
		EventState eventState = new EventState(false, true);// subclass handling
															// not allowed,
															// resume allowed
		setDebugThreadInfo(event.thread());
		StudioDebugCorePlugin.debug("Exiting method :"
				+ event.method().name());
		return eventState;
	}

	public boolean onResponseAction(BreakpointEvent bpe) throws DebugException {
		try {
			final Object key = bpe.request().getProperty(DebuggerConstants.VM_TASK_KEY);
				if(key != null) {
					final VmTask task = responseTaskMap.get(key);
					if(task != null) {
						try {
							if (task instanceof VmResponseTask) {
								final RuleDebugThread thread = getDebugThreadInfo(bpe.thread());
								final VmResponseTask responseTask = (VmResponseTask) task;
								IVariable responseVar = null;
								synchronized(thread) {
									thread.setRunning(false);
									thread.addCurrentBreakpoint(getBreakPointFromEvent(bpe));
									
									AbstractDebugTarget debugTarget = ((AbstractDebugTarget)getDebugTarget());
									
									// finally we need to send a event to the ui and attach a new object/or value as data
									DebugEvent event = new DebugEvent(debugTarget, DebugEvent.MODEL_SPECIFIC, IRuleRunTarget.DEBUG_TASK_REPONSE);
									
									try { // Shiv:: adding a hack to consume exception while handling rule input response
										// get the top most stack frame because we have hit a breakpoint
										IStackFrame frame = thread.getTopStackFrame();
										// get all the frame variables
										IVariable[] vars = frame.getVariables();
										for (IVariable var : vars) {
											// there is only one object being returned from DebugTask.getResponse()
											// eliminate ThisVariable and ScoreCardVariable
											if (var instanceof RuleDebugLocalVariable && !(var instanceof ScorecardVariable)) {
												RuleDebugLocalVariable resultObjectVariable = (RuleDebugLocalVariable)var;
												responseVar = resultObjectVariable;
												break;
											}
										}

										// get all the deserializers from the extension point
										IResponseDeserializer deserializer = getResponseDeserializer(responseTask);
										if (deserializer != null) {
											// there could be many VmResponseTasks therefore the event handler needs to know
											// which task caused the event, so send the task as the event data
											// and the task contains the response value inside
											// The deserialization needs to happen before sending the event out because
											// the event handling is asynchronous and the thread will have resumed by then
											// and nothing can be extracted out from the variable and lots of exceptions.
											Object responsedata = deserializer.deserialize(responseVar);
											responseTask.setResponse(responsedata);
										}
									} catch( Exception e) {
										StudioDebugCorePlugin.debug(e.getMessage());
									} finally {
										event.setData(responseTask);
										debugTarget.fireEvent(event);
										// you may need to create or use a DebugEvent handler to capture this event
										// see StudioDebugUIPlugin.init() where I set a DebugEventSetListener()
										// you can add your own listener in any UI component and filter out the 
										// required event
										//thread.fireResumeEvent(DebugEvent.CLIENT_REQUEST);
										thread.resumedByVM();
									}
										
								}
							}
						} finally {
							responseTaskMap.remove(key);
						}						
					}
				}
		}catch (Exception e) {
			((AbstractDebugTarget) getDebugTarget()).abort(
					"Failed to get response action", e);
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.JdiEventListener#onStep(com.sun
	 * .jdi.event.StepEvent)
	 */
	public EventState onStep(EventSet eventSet,StepEvent se) {
		EventState eventState = new EventState(false, false);
		setDebugThreadInfo(se.thread());
		StudioDebugCorePlugin.debug("Recieved Step event :" + se);
		eventState.setHandled(false);
		return eventState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.JdiEventListener#onThreadDeath(
	 * com.sun.jdi.event.ThreadDeathEvent)
	 */
	public EventState onThreadDeath(EventSet eventSet,ThreadDeathEvent tde) {
		EventState eventState = new EventState(false, true);// subclass handling
															// not allowed,
															// resume allowed
		boolean oce = false;
		ThreadGroupReference group = null;
		ThreadReference thread = tde.thread();
		try {
			thread.disableCollection();
		} catch (ObjectCollectedException oc) {
			oce = true;
		} catch (VMDisconnectedException e) {
			logError(e);
			oce = true;
		}
		if (!oce) {
			group = thread.threadGroup();
			if (group != null) {
				group.disableCollection();
			}
		}
		try {
			try {
				StudioDebugCorePlugin.debug("ThreadDeath event :"
						+ tde.thread().name());
			} catch (ObjectCollectedException oc) {
				StudioDebugCorePlugin.debug("ThreadDeath event :"
						+ tde.thread());
			} catch (VMDisconnectedException e) {
				StudioDebugCorePlugin.debug("VM disconnected");
			}
			fThreadsCache.onThreadDeath(tde);
		} finally {
			if (!oce) {
				try {
					thread.enableCollection();
					if (group != null) {
						group.enableCollection();
					}
				} catch (ObjectCollectedException oc) {
				} catch (VMDisconnectedException e) {
				}
			}
		}
		return eventState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.JdiEventListener#onThreadStart(
	 * com.sun.jdi.event.ThreadStartEvent)
	 */
	public EventState onThreadStart(EventSet eventSet,ThreadStartEvent tse) {
		EventState eventState = new EventState(false, true);//subclass handling not allowed, resume allowed
		boolean oce = false;

		ThreadReference thread = tse.thread();
		ThreadGroupReference group = null;
		try {
			try {
				StudioDebugCorePlugin.debug("ThreadStart event :"
						+ tse.thread().name());
			} catch (ObjectCollectedException oe) {
				StudioDebugCorePlugin.debug("ThreadStart event :"
						+ tse.thread());
			}

			fThreadsCache.onThreadStart(tse);
			try {
				thread.disableCollection();
			} catch (ObjectCollectedException oe) {
				oce = true;
			}
			if (!oce) {
				group = thread.threadGroup();
				if (group != null) {
					group.disableCollection();
				}
			}
			
			setDebugThreadInfo(tse.thread());
			RuleDebugThread rdt = getDebugThreadInfo(tse.thread());
			if (tse.thread().name().equals(DebuggerService.class.getName())) {
				this.debuggerServiceThreadUniqueId = ((ObjectReference) tse
						.thread()).uniqueID();
				synchronized (rdt) {
					rdt.setLastPosition(null);
					if (rdt.getDebugActionState() < DEBUG_ACTION_START) {
						rdt.setDebugActionStateFlags(DEBUG_ACTION_START
								| DEBUG_ACTION_STATUS_COMPLETED);
							VmTask task = new InitSessionTask();
							((AbstractDebugTarget) getDebugTarget()).addInputVmTask(task);
							/* 20-01-2011 Calling addInputVmTask instead(to avoid code repetition.
							LinkedBlockingQueue<VmTask> inputQueue = ((AbstractDebugTarget) getDebugTarget()).getInputQueue();
							inputQueue.add(task);
							try {
								addInputVmTask(task.getKey());
							} catch (CoreException e) {
								abort("Failed to launch init session task",e);
							}*/
						eventState.setHandled(true);
					}
				}
				return eventState;
			}
			
			if(rdt != null) {
				synchronized (rdt) {
					if (rdt.getUnderlyingThread().isSuspended() && 
							(!this.isSuspended())) {
						rdt.setRunning(true);
						rdt.resumedByVM();
						VirtualMachine vm = getVM();
						if (vm != null) {
							vm.resume();
						}
					}
				}
			}
		} catch (DebugException e) {
			StudioDebugCorePlugin.log("Failed to initialize threads",e);
		} finally {
			if (!oce) {
				thread.enableCollection();
				if (group != null) {
					group.enableCollection();
				}
			}
		}
		eventState.setHandled(false);
		return eventState;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.JdiEventListener#onVMDeathEvent
	 * (com.sun.jdi.event.VMDeathEvent)
	 */
	@Override
	public EventState onVMDeathEvent(EventSet eventSet,VMDeathEvent event) {
		EventState eventState = new EventState(false, true);// subclass handling
															// not allowed,
															// resume allowed
		((IRuleRunTarget) getDebugTarget()).onVmDeath();
		StudioDebugCorePlugin.debug("VM death method :"
				+ event.virtualMachine().name());
		return eventState;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.JdiEventListener#onVmDisconnect
	 * (com.sun.jdi.event.VMDisconnectEvent)
	 */
	@Override
	public EventState onVmDisconnect(EventSet eventSet,VMDisconnectEvent event) {
		EventState eventState = new EventState(false, true);// subclass handling
															// not allowed,
															// resume allowed
		StudioDebugCorePlugin.debug("VM disconnected method :"
				+ event.virtualMachine().name());
		// ((IRuleRunTarget)getDebugTarget()).terminated();
		((IRuleRunTarget) getDebugTarget()).onVmDisconnect();
		return eventState;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.JdiEventListener#onVmStartEvent
	 * (com.sun.jdi.event.VMStartEvent)
	 */
	@Override
	public EventState onVmStartEvent(EventSet eventSet,VMStartEvent event) {
		EventState eventState = new EventState(false, true);// subclass handling
															// not allowed,
															// resume allowed
		RuleDebugThread tinfo = getDebugThreadInfo(event.thread());
		if (tinfo != null) {
			threadInfo = tinfo;
		} else {
			tinfo = getThreadsCache().findThread(event.thread());
			if (tinfo == null) {
				getThreadsCache().createThread(event.thread());
			}
		}
		if(tinfo != null){
			tinfo.setLastPosition(null);
			tinfo.setDebugActionStateFlags(DEBUG_ACTION_INIT
					| DEBUG_ACTION_STATUS_COMPLETED);
		}

		((IRuleRunTarget) getDebugTarget()).onVmStart();
		return eventState;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.JdiEventListener#onWatchpoint(com
	 * .sun.jdi.event.WatchpointEvent)
	 */
	public EventState onWatchpoint(EventSet eventSet,WatchpointEvent evt) {
		EventState eventState = new EventState(false, true);// subclass handling
															// not allowed,
															// resume allowed
		StudioDebugCorePlugin.debug("WatchPoint event :"
				+ evt.location());
		setDebugThreadInfo(evt.thread());
		return eventState;
	}

	/**
	 * Notifies threads that they have been resumed
	 */
	protected void resumeThreads() throws DebugException {
		Iterator<IRuleDebugThread> threads = getThreadsCache()
				.getThreadIterator();
		while (threads.hasNext()) {
			RuleDebugThread rdThread = (RuleDebugThread) threads.next();
			rdThread.resumedByVM();
		}
	}
	
	private void setAgentMap(Map<String, Type> agentTypeMap) {
		this.agentTypeMap = agentTypeMap;
		
	}
	
	/**
	 * @param breakpoints the breakpoints to set
	 */
	public synchronized void setBreakpoints(ArrayList<EventRequest> breakpoints) {
		this.breakpoints = breakpoints;
	}

	/**
	 * @param refType
	 */
	private void setBreakPointsForClass(ReferenceType refType,String markerType) {
	    final SourceMapper srcMapper = ((AbstractDebugTarget)getDebugTarget()).getSourceMapper();
	    final String beEntityName = srcMapper.getEntityName(refType.name());
//	    final String markerType= RuleBreakpoint.getMarkerType();
	    if (beEntityName == null) return;
	    final IBreakpointManager bpm = DebugPlugin.getDefault().getBreakpointManager();
	    final IBreakpoint[] bps = bpm.getBreakpoints(getModelIdentifier());
	    for (IBreakpoint bp : bps) {
	    	if (!(bp instanceof IRuleBreakpoint)) {
				continue;
			}
	    	final IRuleBreakpoint lbp = (IRuleBreakpoint) bp;
	    	final IMarker marker = lbp.getMarker();
	    	final IResource resource = marker.getResource();
	    	if(!((IRuleRunTarget)getDebugTarget()).getProjectName().equals(resource.getProject().getName())) {
	    		continue;
	    	}
	    	try {
	    		if (marker != null && marker.exists() && marker.getType().equals(markerType)) {
	    			final int lineNo = lbp.getLineNumber();
	    			final String resourcePath = DebuggerSupport.getResourcePath(resource);
	    			final IMappedResourcePosition target = srcMapper.getJavaPosition(resourcePath,lineNo);
	    			if ( target != null && beEntityName.equals(resourcePath)) {
	    				if(getDebugTarget() instanceof AbstractDebugTarget) {
	    					((AbstractDebugTarget) getDebugTarget()).breakpointAdded(lbp);
	    				}
	    			}
	    		}
	    	} catch (Exception e) {
	    		StudioDebugCorePlugin.log(e);
	    	}
	    	
	    }
	}

	/**
	 * @param refType
	 * @return
	 */
	@SuppressWarnings("unused")
	public boolean setBreakPointsForReferenceType(ReferenceType refType,String markerType) {
	        if(DebuggerSupport.isCodeGenType(refType)) {
	//        if (refType.name().indexOf("be.gen")!= -1) { //TODO - we need to provide a generic include list.
	            ClassType clzType = (ClassType) refType;
	            List<InterfaceType> ifTypes = clzType.interfaces();
	            if(DebuggerSupport.implementsInterface(clzType, SmartStepInto.class.getName())) {
	                setBreakPointsForClass(refType,markerType);
	                getClassFilterSet().add(refType);
	                StudioDebugCorePlugin.debug("ClassPrepareEvent - " + refType.name());
	            } else {
	                getClassExclusionFilterSet().add(refType);
	            }
	//            if(DebuggerSupport.extendsClass(clzType,PropertyStateMachineState.class)) {
	//                createPropertyStateMachineBreakPoints(refType);
	//            } else if(DebuggerSupport.extendsClass(clzType,StateTransitionRule.class)) {
	//                createStateTransitionBreakPoints(refType);
	//            }
	        }
	        return true;
	    }
	
	

	public void setCacheServerMode(boolean isCacheServerMode) {
		this.isCacheServerMode = isCacheServerMode;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	/**
	 * @param debuggerServiceType
	 */
	public void setDebuggerServiceType(ReferenceType debuggerServiceType) {
		this.debuggerServiceType = debuggerServiceType;
	}
	
	/**
	 * @param debugState
	 */
	public void setDebugState(int debugState) {
	    int oldValue = this.debugState;
	    this.debugState = debugState;
	    debugStateListeners.firePropertyChange(PROP_STATE, oldValue, debugState);
	}
	
//	
//	public void testData(RuleDebugThread eventThread, String data) {
//		try {
//			DebuggerSupport.testData(eventThread,getDebugTaskFactoryType(),data);
//		}catch(Exception e) {
//			StudioDebugCorePlugin.log(e);
//		}
//	}

	/**
	 * @param debugTaskFactoryType the debugTaskFactoryType to set
	 */
	public void setDebugTaskFactoryType(ReferenceType debugTaskFactoryType) {
		this.debugTaskFactoryType = debugTaskFactoryType;
	}

	/**
	 * @param debugThreadReference
	 * @return
	 */
	public synchronized RuleDebugThread setDebugThreadInfo(
			ThreadReference debugThreadReference) {
		threadInfo = fThreadsCache.findThread(debugThreadReference);
		return threadInfo;
	}

	/**
	 * @param eventDispatcher
	 *            the eventDispatcher to set
	 */
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
	}
	
	/**
	 * @param launch
	 *            the launch to set
	 */
	public void setLaunch(ILaunch launch) {
		this.fLaunch = launch;
	}


	public void setProcessAgent(boolean p) {
		this.fProcessAgent = p;
	}

	public void setRuleServiceProvider(ObjectReference rspInstance) {
		this.rspInstance = rspInstance;
	}
	
	/**
	 * @param ruleSessions
	 *            the fRuleSessionMap to set
	 */
	public synchronized void setRuleSessions(Map<String,String> ruleSessions) {
		fRuleSessionMap = ruleSessions;
	}

	public void setTaskRegistryType(ReferenceType type) {
		this.taskRegistryType = type;
	}

	/**
	 * @param threadsCache
	 *            the threadsCache to set
	 */
	public void setThreadsCache(DebugThreadsCache threadsCache) {
		this.fThreadsCache = threadsCache;
	}

	/**
	 * @param vm
	 *            the vm to set
	 */
	@Override
	public void setVM(VirtualMachine vm) {
		this.vm = vm;
	}
	

	@SuppressWarnings("rawtypes")
	public void start(Map props) throws DebugException {
		try {
			attachVM();
			getThreadsCache().start(props);
			if (getDebugTarget() instanceof IRuleRunTarget) {
				IRuleRunTarget target = (IRuleRunTarget) getDebugTarget();
				if(target.supportsDisconnect()) {
					ReferenceType classRef = DebuggerSupport.findClass(getVM(),
							DebuggerService.class.getName());
					if (classRef != null) {
						setDebuggerServiceType(classRef);
					}
					ReferenceType debugTaskFactoryRef = DebuggerSupport.findClass(getVM(),
							DebugTaskFactory.class.getName());
					if (debugTaskFactoryRef != null) {
						setDebugTaskFactoryType(debugTaskFactoryRef);
					}

				}
			}
			Iterator<IRuleDebugThread> iter = getThreadsCache()
					.getThreadIterator();
			while (iter.hasNext()) {
				final RuleDebugThread thread = (RuleDebugThread) iter.next();
				if (thread.getUnderlyingThread().name().equals(
						DebuggerService.class.getName())) {
					this.debuggerServiceThreadUniqueId = thread
							.getUnderlyingThread().uniqueID();
					LinkedBlockingQueue<VmTask> inputQueue = ((AbstractDebugTarget) getDebugTarget())
							.getInputQueue();
					VmTask task = new InitSessionTask();
					inputQueue.add(task);
					addInputVmTask(task.getKey());
				}
			}
			
			EventRequestManager em = getEventRequestManager();
			createProcessAgentPrepareRequest(em);
			if(AddonUtil.isProcessAddonInstalled()) {
				createTaskRegistryClassPrepareRequest(em);
			}
		} catch (CoreException e) {
			abort("Failed to execute init session task", e);
		}
	}

	public void startDispatcher() {
		if (eventDispatcher != null && !eventDispatcher.isRunning()) {
			// ExecutorService es = Executors.newFixedThreadPool(1);
			// Executors.newFixedThreadPool(1);
			// es.execute(eventDispatcher);

			// eventDispatcher.schedule();
			DebugPlugin plugin = DebugPlugin.getDefault();
			plugin.asyncExec(new Runnable() {
				public void run() {
					EventDispatcher dispatcher = getEventDispatcher();
					if (dispatcher != null) {
						Thread t = new Thread(dispatcher, StudioDebugCorePlugin
								.getUniqueIdentifier()
								+ "BusinessEvents Debugger Event Dispatcher");
						t.setDaemon(true);
						t.start();
					}
				}
			});
		}
	}

	public void stopDispatcher() {
		if (eventDispatcher != null && eventDispatcher.isRunning()) {
			eventDispatcher.stop();
		}
	}

	/**
	 * Notifies threads that they have been suspended
	 */
	protected void suspendThreads() {
		Iterator<IRuleDebugThread> threads = getThreadsCache()
				.getThreadIterator();
		while (threads.hasNext()) {
			((RuleDebugThread) threads.next()).suspendedByVM();
		}
	}
	
	@Override
	public String getCurrentRuleSession(RuleDebugThread rdt) throws DebugException {
		return DebuggerSupport.getCurrentRuleSessionName(rdt);
	}

}
