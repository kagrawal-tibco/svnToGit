package com.tibco.cep.studio.debug.core.model;

import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_COMPLETED;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_INVOKE_USER;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STOP;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_SUSPEND;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IDisconnect;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.ITerminate;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;

import com.sun.jdi.ObjectCollectedException;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ThreadGroupReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.EventRequest;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.java.JavaSource;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
import com.tibco.cep.repo.provider.JavaArchiveResourceProvider;
import com.tibco.cep.repo.provider.impl.BEArchiveResourceProviderImpl;
import com.tibco.cep.repo.provider.impl.JavaArchiveResourceProviderImpl;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.adapters.CoreOntologyAdapter;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.cep.studio.core.repo.emf.StudioEMFProject;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;
import com.tibco.cep.studio.debug.input.VmTask;
import com.tibco.cep.studio.debug.smap.SMapResourceProvider;
import com.tibco.cep.studio.debug.smap.SourceMapper;
import com.tibco.cep.studio.debug.smap.TaskRegistryMapper;
import com.tibco.cep.studio.debug.smap.TaskRegistryMapperImpl;

/**
 * @author pdhar
 *
 */
public abstract class AbstractDebugTarget extends RuleDebugElement implements IDebugTarget {

	/**
	 * the debugee process
	 */
	private IProcess process;
	/**
	 * the launch session
	 */
	private ILaunch launch;
	/**
	 * the BE enterprise archive path
	 */
	protected String fEarPath;
	/**
	 * The BE project
	 */
	protected Project fProject;
	/**
	 * the eclipse workspace project
	 */
	protected IProject fWorkspaceProject;
	/**
	 * The BE source mapper
	 */
	protected SourceMapper sourceMapper;
	
	/**
	 * The process task registry mapper
	 */
	protected TaskRegistryMapper taskRegistryMapper;
	/**
	 * archive resource provider for getting resources inside bar files
	 */
	protected BEArchiveResourceProvider fBeArchiveProvider = new BEArchiveResourceProviderImpl();
	/**
	 * archive resource provider for getting resources inside jar files
	 */
	protected JavaArchiveResourceProvider fJavaArchiveProvider = new JavaArchiveResourceProviderImpl();
	/**
	 * resource provider for the source line mapping
	 */
	protected SMapResourceProvider fSMapResourceProvider = new SMapResourceProvider();
	/**
	 * workspace to load the BE project
	 */
	/**
	 * input queue for the async input tasks
	 */
	protected LinkedBlockingQueue<VmTask> fInputQueue;
	/**
	 * debug target name
	 */
	protected String name;
	/**
	 * debug project name
	 */
	protected String fProjectName;
	/**
	 * debugee port name
	 */
	protected String port;
	/**
	 * debugee host name
	 */
	protected String hostName;
	
	/**
	 * Whether disconnected
	 */
	protected boolean fDisconnected;
	/**
	 * Whether disconnect is supported.
	 */
	protected boolean fSupportsDisconnect;
	/**
	 * Whether terminate is supported. Not all targets
	 * support terminate. For example, a VM that was attached
	 * to remotely may not allow the user to terminate it.
	 */
	protected boolean fSupportsTerminate;
	
	/**
	 * Whether in the process of terminating
	 */
	protected boolean fTerminating;
	
	/**
	 * Whether terminated
	 */
	protected boolean fTerminated;
	
	/**
	 * vm session for the debugee process
	 */
	protected RunSession fSession;
	/**
	 * debugee VM
	 */
	protected VirtualMachine fVM;
	/**
	 * 
	 */
	protected boolean fResumeOnStartup;
	/**
	 * Count of the number of suspend events in this target
	 */
	protected int fSuspendCount = 0;
	/**
	 * Collection of breakpoints added to this target. Values are of type <code>IJavaBreakpoint</code>.
	 */
	protected List<IRuleBreakpoint> fBreakpoints;
	
	/**
	 * Project ontology
	 */
	
	protected Ontology fOntology;
	
	/**
	 * Entity resource path -> Entity map
	 */
	protected Map<String,Entity> fEntityMap;
	/**
	 * Mapped source info providers
	 */
	List<ISourceInfoProvider> mappedSourceInfoProviders = new ArrayList<ISourceInfoProvider>();
	/**
	 * Flag to control one time initialization of persisted breakpoints
	 */
	private boolean fInitializedBreakpoints;
	
	/**
	 * 
	 * @param target
	 */
	public AbstractDebugTarget(IDebugTarget target,VirtualMachine vm) {
		super(target);
		setVM(vm);
		setRequestTimeout(RuleDebugModel.getPreferences().getInt(RuleDebugModel.PREF_REQUEST_TIMEOUT,RuleDebugModel.DEF_REQUEST_TIMEOUT));
		setTerminated(false);
		setTerminating(false);
		setDisconnected(false);
		setBreakpoints(new ArrayList<IRuleBreakpoint>(5));
		setEntityMap(new HashMap<String, Entity>());		
	}
	
	

	/**
	 * @return the fVM
	 */
	public VirtualMachine getVM() {
		return fVM;
	}



	/**
	 * @param fvm the fVM to set
	 */
	public void setVM(VirtualMachine fvm) {
		fVM = fvm;
	}



	public ILaunch getLaunch() {
		return launch;
	}

	/**
	 * @param launch
	 */
	public void setLaunch(ILaunch launch) {
		this.launch = launch;
	}

	/**
	 * @return
	 */
	public IProcess getProcess() {
		return process;
	}

	/**
	 * @param process
	 */
	public void setProcess(IProcess process) {
		this.process = process;
	}
	
	

	/**
	 * @return the session
	 */
	public RunSession getSession() {
		return fSession;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(RunSession session) {
		this.fSession = session;
	}

	/**
	 * @return
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return fProjectName;
	}



	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.fProjectName = projectName;
	}

	/**
	 * @return
	 */
	public SourceMapper getSourceMapper() {
		return sourceMapper;
	}

	/**
	 * @param sourceMapper the sourceMapper to set
	 */
	public void setSourceMapper(SourceMapper sourceMapper) {
		this.sourceMapper = sourceMapper;
	}

	/**
	 * @return
	 */
	public Project getBEProject() {
		return fProject;
	}

	/**
	 * @param project the fProject to set
	 */
	public void setBEProject(Project project) {
		fProject = project;
	}
		
	/**
	 * Sets whether this debug target is disconnected
	 * 
	 * @param disconnected <code>true</code> if this debug
	 *  target is disconnected, otherwise <code>false</code>
	 */
	protected void setDisconnected(boolean disconnected) {
		fDisconnected= disconnected;
	}
	
	/**
	 * @see IDisconnect#isDisconnected()
	 */
	public boolean isDisconnected() {
		return fDisconnected;
	}
	
	/**
	 * Returns whether this debug target supports disconnecting.
	 * 
	 * @return whether this debug target supports disconnecting
	 */
	public boolean supportsDisconnect() {
		return fSupportsDisconnect;
	}
	
	/**
	 * Sets whether this debug target supports disconnection.
	 * Set on creation.
	 * 
	 * @param supported <code>true</code> if this target supports
	 * 	disconnection, otherwise <code>false</code>
	 */
	protected void setSupportsDisconnect(boolean supported) {
		fSupportsDisconnect = supported;
	}
	
	/**
	 * Returns whether this debug target supports termination.
	 * 
	 * @return whether this debug target supports termination
	 */
	public boolean supportsTerminate() {
		return fSupportsTerminate;
	}
	
	/**
	 * Sets whether this debug target supports termination.
	 * Set on creation.
	 * 
	 * @param supported <code>true</code> if this target supports
	 * 	termination, otherwise <code>false</code>
	 */
	protected void setSupportsTerminate(boolean supported) {
		fSupportsTerminate = supported;
	}
	
	protected boolean isTerminating() {
		return fTerminating;
	}

	protected void setTerminating(boolean terminating) {
		fTerminating = terminating;
	}
	
	/**
	 * @see ITerminate#isTerminated()
	 */
	public boolean isTerminated() {
		return fTerminated;
	}

	/**
	 * Sets whether this debug target is terminated
	 * 
	 * @param terminated <code>true</code> if this debug
	 * 	target is terminated, otherwise <code>false</code>
	 */
	protected void setTerminated(boolean terminated) {
		fTerminated = terminated;
	}
	
	

	/**
	 * @return the fOntology
	 */
	public Ontology getOntology() {
		if(getProjectName() == null){
			return null;
		}
		if(fOntology == null) {
			DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex(getProjectName());
			if(index != null) {
				setOntology(new CoreOntologyAdapter(index));
			}
		}
		return fOntology;
	}



	/**
	 * @param ontology the fOntology to set
	 */
	public void setOntology(Ontology ontology) {
		fOntology = ontology;
	}

	

	/**
	 * @return the fEntityMap
	 */
	public Map<String, Entity> getEntityMap() {
		return fEntityMap;
	}



	/**
	 * @param entityMap the fEntityMap to set
	 */
	public void setEntityMap(Map<String, Entity> entityMap) {
		fEntityMap = entityMap;
	}



	/**
	 * @return
	 */
	public  SMapResourceProvider getSMapResourceProvider() {
		return fSMapResourceProvider;
	}
	
	public void init() throws CoreException {
		initSourceInfoProviders();
		setProjectName(getLaunch().getLaunchConfiguration().getAttribute(
				IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME,
				(String) null));
		if (supportsTerminate()) {
			setEarPath(getLaunch().getLaunchConfiguration().getAttribute(
					IStudioDebugLaunchConfigurationConstants.ATTR_EAR_FILE,
					(String) null));
		}
	}
	
	protected boolean initStudioProject() throws CoreException {
		try {
			IProject workspaceProject = ResourcesPlugin.getWorkspace()
					.getRoot().getProject(getProjectName());
			if(workspaceProject != null && workspaceProject.exists()) {
				if(workspaceProject.isOpen()) {
					final StudioEMFProject sproject = new StudioEMFProject(getProjectName());
					sproject.load();
					setBEProject(sproject);
					setWorkspaceProject(workspaceProject);
				} else {
					throw new CoreException(new Status(IStatus.ERROR,
							StudioDebugCorePlugin.getUniqueIdentifier(),
							"Project " + getProjectName()
							+ " is closed."));
				}
				
			} else {
				String prop = System.getProperty("studio.console","false");
				if (prop.equalsIgnoreCase("false")){
					throw new CoreException(new Status(IStatus.ERROR,
							StudioDebugCorePlugin.getUniqueIdentifier(),
							"Project " + getProjectName()
							+ " not found in workspace."));
				}
				
			}
			return true;
		} catch (CoreException e) {
			cleanup();
			if(getProcess() != null) {
				getProcess().terminate();
			}
			if(getLaunch() != null) {
				getLaunch().terminate();
			}

			throw e;
		} catch (Exception e) {
			cleanup();
			if(getProcess() != null) {
				getProcess().terminate();
			}
			if(getLaunch() != null) {
				getLaunch().terminate();
			}
			throw new CoreException(new Status(IStatus.ERROR,
					StudioDebugCorePlugin.getUniqueIdentifier(),
					"Failed to load project:" + getProjectName(), e));
		}
	}

	/**
	 * @return
	 * @throws CoreException
	 */
	protected boolean initLocalProject() throws CoreException {

		try {
			IProject workspaceProject = ResourcesPlugin.getWorkspace()
					.getRoot().getProject(getProjectName());
			if (workspaceProject == null) {
				abort("Project " + getProjectName() + " not found in workspace.", null);
			}
			setWorkspaceProject(workspaceProject);
			ArrayList<ResourceProvider> providers = new ArrayList<ResourceProvider>();
			providers.add(fBeArchiveProvider);
			providers.add(fSMapResourceProvider);
			providers.add(fJavaArchiveProvider);
			
			/**
			 * An arrangement for time-being to specify EAR file path
			 * for remote debugging case.
			 */
			if (fEarPath == null || fEarPath.length() == 0) {
				fEarPath = System.getProperty("tibco.repourl");
			}
			EMFProject project = new DebuggerEMFProject(providers, fEarPath);        	
            project.load();
			setBEProject(project);

			if (fSMapResourceProvider.getAllResourceURI().size() == 0) {
				return false;
			}

			return true;
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR,
					StudioDebugCorePlugin.getUniqueIdentifier(),
					"Failed to load project from ear file:" + fEarPath, e));
		}
	}

	/**
	 * @return
	 * @throws DebugException
	 */
	public String getName() throws DebugException {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 * @throws DebugException
	 */
	public String getEarPath(){
		return fEarPath;
	}
	
	/**
	 * @param path
	 */
	public void setEarPath(String path) {
		fEarPath = path;
	}

	/**
	 * @param resourceName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Entity getEntityFromResourceName(final String resourceName) {
	    Entity entity = getEntityMap().get(resourceName);
	    if (entity == null) {
	    	Ontology ontology = getOntology();
	        entity = ontology.getEntity(resourceName);
	        if (entity == null) {
	            // could be a Rule
	            Collection<Rule> rules = ontology.getRules();
	            for (Iterator<Rule> it = rules.iterator(); it.hasNext();) {
	                Rule r = (Rule) it.next();
	                if (r.getFullPath().equals(resourceName)) {
	                    entity = r;
	                    getEntityMap().put(resourceName, entity);
	                    return entity;
	                }
	            }
	            if(entity == null) {
	            	Collection<RuleFunction> rulefunctions = ontology.getRuleFunctions();
	                for (Iterator<RuleFunction> it = rulefunctions.iterator(); it.hasNext();) {
	                    RuleFunction rf = (RuleFunction) it.next();
	                    if (rf.getFullPath().equals(resourceName)) {
	                        entity = rf;
	                        getEntityMap().put(resourceName, entity);
	                        return entity;
	                    }
	                }
	            }
	            if (entity == null) {
	                // must be a statemachine
	                Collection<Concept> concepts = ontology.getConcepts();
	                for (Concept cept : concepts) {
	                    java.util.List<StateMachine> stateMachines = cept.getStateMachines();
	                    if (stateMachines != null && stateMachines.size() > 0) {
	                        for (StateMachine sm : stateMachines) {
	                            String path = ModelUtils.convertPackageToPath(sm.getFullPath());
	                            if (resourceName.startsWith(path)) {
	                                entity = sm;
	                                getEntityMap().put(resourceName, entity);
	                                return entity;
	                            }
	                        }
	                    }
	                }
	            }
	
	            if (entity == null) {
	                Collection<com.tibco.cep.designtime.model.event.Event> events = ontology.getEvents();
	                for(com.tibco.cep.designtime.model.event.Event event: events) {
	                    if(resourceName.startsWith(event.getFullPath())) {
	                        entity = event.getExpiryAction(false);
	                        getEntityMap().put(resourceName, entity);
	                        return entity;
	                    }
	                }
	                
	            }                
	        }
	    }
	    return entity;
	}

	public String getEntityPathFromResourceName(final String resourceName) {
	    Entity entity = getEntityFromResourceName(resourceName);
	    String path;
	    if (entity instanceof StateMachine) {
	        StateMachine sm = (StateMachine) entity;
	        path = sm.getFullPath();
	    } else {
	        path = entity.getFullPath();
	    }
	    return path;
	}
	
	public IResource getEntityResource (final String resourceName) {
		IPath path = getEntityResourcePath(resourceName);
		if(path == null) {
			return null;
		}
        IResource resource = ResourcesPlugin
        		.getWorkspace()
        		.getRoot()
        		.getProject(getProjectName())
        		.findMember(path);
        return resource;
	}



	public IPath getEntityResourcePath(final String resourceName) {
		Entity entity = getEntityFromResourceName(resourceName);
		String entityURI = getEntityPathFromResourceName(resourceName);
		if(entity == null || entityURI == null) {
			return null;
		}
		IPath path = new Path(entityURI);
        if(entity instanceof Concept) {
        	path = path.addFileExtension(CommonIndexUtils.CONCEPT_EXTENSION);
        } else if(entity instanceof Event) {
        	path = path.addFileExtension(CommonIndexUtils.EVENT_EXTENSION);
        } else if (entity instanceof RuleFunction) {
        	path = path.addFileExtension(CommonIndexUtils.RULEFUNCTION_EXTENSION);
        } else if (entity instanceof Rule) {
        	path = path.addFileExtension(CommonIndexUtils.RULE_EXTENSION);
        } else if(entity instanceof StateMachine){
        	path = path.addFileExtension(CommonIndexUtils.STATEMACHINE_EXTENSION);
        } else if( entity instanceof JavaSource) {
        	path = path.addFileExtension(CommonIndexUtils.JAVA_EXTENSION);
        }
		return path;
	}
	

	/**
	 * @return the workspaceProject
	 */
	public IProject getWorkspaceProject() {
		return fWorkspaceProject;
	}



	/**
	 * @param workspaceProject the workspaceProject to set
	 */
	public void setWorkspaceProject(IProject workspaceProject) {
		this.fWorkspaceProject = workspaceProject;
	}



	/**
	 * @return
	 */
	public String[] getModelIdentifiers() {
		return new String[] { getModelIdentifier()};
	}

	/**
	 * @return
	 */
	public LinkedBlockingQueue<VmTask> getInputQueue() {
		return fInputQueue;
	}

	/**
	 * @param inputQueue the inputQueue to set
	 */
	public void setInputQueue(LinkedBlockingQueue<VmTask> inputQueue) {
		this.fInputQueue = inputQueue;
	}

	/**
	 * @param task
	 * @throws DebugException 
	 */
	public void addInputVmTask(VmTask task) throws DebugException {
		// Add task to VmTask queue
		getInputQueue().add(task);
		// Add IntermediateBreakpoint at last line of method processTask
		// in DebuggerService class
		getSession().addInputVmTask(task.getKey());    	
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String,String> getRuleSessionMap() {
		if(getSession()!= null) {
			return getSession().getRuleSessionMap();
		}
		return Collections.EMPTY_MAP;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,CacheAgent.Type> getAgentTypeMap() {
		if(getSession()!= null) {
			return getSession().getAgentTypeMap();
		}
		return Collections.EMPTY_MAP;
	}
	
	public String getCurrentRuleSessionName(RuleDebugThread rdt) throws DebugException {
		if(getSession()!= null) {
			return getSession().getCurrentRuleSession(rdt);
		}
		return null;
	}

	public boolean isProcessAgent() {
		return getSession().isProcessAgent();
	}

	public void onVmStart() {
	    	if (isResumeOnStartup()) {
				try {
					//setSuspended(true);
					resume();
				} catch (DebugException e) {
					logError(e);
				}
			}
			Iterator<IRuleDebugThread> threads = getSession().getThreadsCache().getThreadIterator();
			while(threads.hasNext()) {
				RuleDebugThread thread = (RuleDebugThread) threads.next();
				synchronized(thread) {
					if (thread.isSuspended()|| thread.getUnderlyingThread().isSuspended()) {
						try {
							boolean suspended = thread.getUnderlyingThread().isSuspended();
							if (suspended) {
								thread.setRunning(true);
								thread.getUnderlyingThread().resume();
								thread.fireResumeEvent(DebugEvent.CLIENT_REQUEST);			            							
							} else {
								thread.setRunning(true);
								thread.fireChangeEvent(DebugEvent.CLIENT_REQUEST);
							}
						} catch (VMDisconnectedException e) {
						} catch (ObjectCollectedException e){
						} catch (RuntimeException e) {
							logError(e);
						} 			
					}
				}
			}
	    	
		}

	public void onVmDisconnect() {
		if (isTerminating()) {
			terminated();
		} else {
			disconnected();
		}
	}
	
	public void onVmDeath() {
		terminated();
	}
	
	public void terminated() {
		setTerminating(false);
		if (!isTerminated()) {
			setTerminated(true);
			setDisconnected(true);
			cleanup();
			fireTerminateEvent();
		}
	}
	

	/**
	 * Sets whether the VM should be resumed on startup.
	 * Has no effect if the VM is already running when
	 * this target is created.
	 * 
	 * @param resume whether the VM should be resumed on startup
	 */
	protected synchronized void setResumeOnStartup(boolean resume) {
		fResumeOnStartup = resume;
	}



	/**
	 * Returns whether this VM should be resumed on startup.
	 * 
	 * @return whether this VM should be resumed on startup
	 */
	protected synchronized boolean isResumeOnStartup() {
		return fResumeOnStartup;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.IRuleRunTarget#findThread(com.sun.jdi.ThreadReference)
	 */
	public RuleDebugThread findThread(ThreadReference ref) {
		if(getSession() != null) {
			return getSession().getThreadsCache().findThread(ref);
		}
		return null;
	}




	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.IRuleRunTarget#findThreadGroup(com.sun.jdi.ThreadGroupReference)
	 */
	public IRuleDebugThreadGroup findThreadGroup(ThreadGroupReference reference) {
		if(getSession() != null) {
			return getSession().getThreadsCache().findThreadGroup(reference);
		}
		return null;
	}
	
	@Override
	public boolean canDisconnect() {
		return supportsDisconnect() && !isDisconnected();
	}
	
	@Override
	public void disconnect() throws DebugException {
		if (!isAvailable()) {
			// already done
			return;
		}
		if (!canDisconnect()) {
			notSupported("VM does not support 'disconnect'."); 
		}
		try {
			if(getSession() != null) {
				synchronized (getSession()) {
					
					RuleDebugThread rdt = getSession().getDebugThreadInfo();
					if(rdt != null) {
					
						rdt.setLastPosition(null);
						if(getSession().getDebugState() != DebugSession.STATE_DISCONNECTED) {
							rdt.setDebugActionStateFlags( DEBUG_ACTION_STOP | DEBUG_ACTION_STATUS_INVOKE_USER | DEBUG_ACTION_STATUS_COMPLETED );
						}
					}
					getSession().resume();
//					getSession().stop();
					if (getSession().getVM() != null) {
						getSession().getVM().dispose();
					}
				}
			}			
		} catch (VMDisconnectedException e) {
			// if the VM disconnects while disconnecting, perform
			// normal disconnect handling
			disconnected();
		} catch (RuntimeException e) {
			targetRequestFailed(MessageFormat.format("{0} occurred disconnecting from VM.",(Object[]) new String[] {e.toString()}), e); 
		}
		
//		terminate();
	}
	
	/** 
	 * Cleans up the internal state of this debug
	 * target as a result of a session ending with a
	 * VM (as a result of a disconnect or termination of
	 * the VM).
	 * <p>
	 * All threads are removed from this target.
	 * This target is removed as a breakpoint listener,
	 * and all breakpoints are removed from this target.
	 * </p>
	 */
	protected void cleanup() {
		DebugPlugin plugin = DebugPlugin.getDefault();
		plugin.getBreakpointManager().removeBreakpointListener(getBreakPointListener());
		plugin.getLaunchManager().removeLaunchListener(getLaunchListener());
		if(getSession() != null) {
			getSession().getThreadsCache().removeAllThreads();
			getSession().getThreadsCache().cleanup();
			if(getSession().getVM()!=null) {
				getSession().setVM(null);
			}
			setSession(null);
			setVM(null);
//			setBEProject(null);
//			setEntityMap(null);
//			setOntology(null);
//			setSourceMapper(null);
//			setWorkspaceProject(null);
//			setInputQueue(null);
		}
	}
	
	abstract public IBreakpointListener getBreakPointListener();
	abstract public ILaunchListener getLaunchListener();
	
    
	/**
	 * @see IAdaptable#getAdapter(Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IDebugTarget.class) {
			return this;
		} else	if(adapter == IRuleDebugTarget.class) {
			if(this instanceof IRuleDebugTarget) {
				return this;
			} 
		} else if(adapter == IRuleRunTarget.class) {
			if(this instanceof IRuleRunTarget) {
				return this;
			}
		} else	if(adapter == RunSession.class) {
			return getSession();
		} else if(adapter == IProject.class) {
			return getWorkspaceProject();
		}
		return super.getAdapter(adapter);
	}
	
	public RuleDebugThread getDebuggerServiceThread() {
		if(getSession() != null) {
			return getSession().getDebuggerServiceThread();
		}
		return null;
	}



	/**
	 * Returns whether this target is available to
	 * handle VM requests
	 */
	public boolean isAvailable() {
		return !(isTerminated() || isTerminating() || isDisconnected());
	}


	@Override
	public boolean isStepFiltersEnabled() {
		return false;
	}



	public void setStepFiltersEnabled(boolean enabled) {
	}



	public boolean supportsStepFilters() {
		return false;
	}



	/**
	 * Returns the number of suspend events that have occurred in this
	 * target.
	 * 
	 * @return the number of suspend events that have occurred in this
	 * target
	 */
	protected int getSuspendCount() {
		return fSuspendCount;
	}



	/**
	 * Increments the suspend counter for this target based on the reason
	 * for the suspend event. The suspend count is not updated for
	 * implicit evaluations.
	 * 
	 * @param eventDetail the reason for the suspend event
	 */
	protected void incrementSuspendCount(int eventDetail) {
	    if (eventDetail != DebugEvent.EVALUATION_IMPLICIT) {
	        fSuspendCount++;
	    }
	}


	@Override
	public IThread[] getThreads() {
		if(getSession() != null) {
			return getSession().getThreadsCache().getThreads();
		} else
			
		return new IThread[0];
	}


	@Override
	public boolean canResume() {
		return !isTerminated() && isSuspended();
	}


	@Override
	public boolean canSuspend() {
			if (!isSuspended() && isAvailable()) {
				// only allow suspend if no threads are currently suspended
				IThread[] threads= getThreads();
				for (int i= 0, numThreads= threads.length; i < numThreads; i++) {
					if (((RuleDebugThread)threads[i]).isSuspended()) {
						return false;
					}
				}
				return true;
			}
			return false;
	//		return !isTerminated() && !isSuspended();
		}



	/**
	 * @return
	 */
	@Override
	public boolean isSuspended() {
		if(getSession() == null) {
			return false;
		}
		return getSession().isSuspended();
	}



	/**
	 * @throws DebugException
	 */
	@Override
	public void resume() throws DebugException {
			try {
				setResumeOnStartup(true);
				getSession().resume();
	        }  catch (VMDisconnectedException e) {
				disconnected();
				return;
			} catch (RuntimeException e) {
	            throw new DebugException(new Status(IStatus.ERROR,StudioDebugCorePlugin.PLUGIN_ID,e.getMessage(),e));
	        }
		}



	/**
	 * @throws DebugException
	 */
	@Override
	public void suspend() throws DebugException {
		RuleDebugThread rdt = getSession().getDebugThreadInfo();
		if(rdt != null) {
			rdt.setLastPosition(null);
			
			if (rdt.getDebugActionType() != DEBUG_ACTION_SUSPEND) {
				rdt.setDebugActionStateFlags(DEBUG_ACTION_SUSPEND | DEBUG_ACTION_STATUS_INVOKE_USER | DEBUG_ACTION_STATUS_COMPLETED);
			}		
		}
		getSession().suspend();
		suspended(DebugEvent.CLIENT_REQUEST);
	}



	public void suspended(int detail) {
		queueSuspendEvent(detail);
	}



	public void resumed(int detail) {
		fireResumeEvent(detail);
	}



	public boolean onEndOfRTC(BreakpointEvent bpe) {
		fireEvent(new DebugEvent(this,DebugEvent.MODEL_SPECIFIC,IRuleRunTarget.END_OF_RTC));
		StudioDebugCorePlugin.debug("End of RTC ....");
		
	    return true;
	}


	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		return breakpoint instanceof IRuleBreakpoint||
				breakpoint instanceof IJavaLineBreakpoint;
	}



	/**
	 * Returns the collection of breakpoints installed in this
	 * debug target.
	 * 
	 * @return list of installed breakpoints - instances of 
	 * 	<code>IRuleBreakpoint</code>
	 */
	public List<IRuleBreakpoint> getBreakpoints() {
		return fBreakpoints;
	}


	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		if (!isAvailable()) {
			return;
		}
		if (supportsBreakpoint(breakpoint)) {
			try {
				if(breakpoint instanceof IRuleBreakpoint){
					IRuleBreakpoint ruleBreakpoint= (IRuleBreakpoint) breakpoint;
					if (!getBreakpoints().contains(breakpoint)) {
						if (!ruleBreakpoint.shouldSkipBreakpoint()) {
							// If the breakpoint should be skipped, don't add the breakpoint
							// request to the VM. Just add the breakpoint to the collection so
							// we have it if the manager is later enabled.
							ruleBreakpoint.addToTarget((IRuleRunTarget)this);
						}					
						getBreakpoints().add(ruleBreakpoint);
					}
				} else if(breakpoint instanceof IJavaLineBreakpoint) {
					IJavaLineBreakpoint jbp = (IJavaLineBreakpoint) breakpoint;
					// check if a IRuleJaavBreakpoint exists for the IJavaBreakpoint
					IRuleJavaBreakpoint rjbp = RuleDebugModel.lineBreakpointExists(getSession(),jbp);
					if(rjbp == null) {
						rjbp = RuleDebugModel.createRuleJavaBreakpoint(jbp,null);
					}
					rjbp.addToTarget((IRuleRunTarget)this);
				}
			} catch (CoreException e) {
				logError(e);
			}
		}
	
	}


	@Override
	public void breakpointChanged(IBreakpoint bp, IMarkerDelta delta) {
		if (!isAvailable()) {
			return;
		}		
		if (supportsBreakpoint(bp) && getSession() != null) {
			try {
				if(bp instanceof IRuleBreakpoint) {
					IRuleBreakpoint rbp = (IRuleBreakpoint) bp;
			
				} else if( bp instanceof IJavaLineBreakpoint) {
					if (IResourceDelta.ADDED == delta.getKind() || IResourceDelta.CHANGED == delta.getKind()) {
						IJavaLineBreakpoint jlbp = (IJavaLineBreakpoint) bp;
						IRuleJavaBreakpoint rjbp = RuleDebugModel.lineBreakpointExists(getSession(), jlbp);
						if (rjbp != null) {
							synchronized (getEventRequestManager()) {
								((RuleJavaBreakpoint) rjbp).setHitCount(jlbp.getHitCount());
								((RuleJavaBreakpoint) rjbp).setSuspendPolicy(jlbp.getSuspendPolicy());
								((RuleJavaBreakpoint) rjbp).setEnabled(jlbp.isEnabled());
								// ((RuleJavaBreakpoint)rjbp).setThreadFilter(thread);
							}
						}

					}

				}
			} catch (CoreException e) {
				logError(e);
			}
		}
	}


	@Override
	public void breakpointRemoved(IBreakpoint bp, IMarkerDelta delta) {
		if (!isAvailable()) {
			return;
		}		
		if (supportsBreakpoint(bp) && getSession() != null) {
			try {
				if(bp instanceof IRuleBreakpoint) {
					IRuleBreakpoint rbp = (IRuleBreakpoint) bp;
					rbp.removeFromTarget((IRuleRunTarget)this);
					getBreakpoints().remove(bp);
					Iterator<IRuleDebugThread> threads = getSession().getThreadsCache().getThreadIterator();
					while (threads.hasNext()) {
						((RuleDebugThread)threads.next()).removeCurrentBreakpoint(rbp);
					}
				} else if( bp instanceof IJavaLineBreakpoint) {
					IJavaLineBreakpoint jlbp = (IJavaLineBreakpoint) bp;
					IRuleJavaBreakpoint rjbp = RuleDebugModel.lineBreakpointExists(getSession(),jlbp);
					if(rjbp != null){
						synchronized (getEventRequestManager()) {
							rjbp.removeFromTarget(((IRuleRunTarget)getDebugTarget()));
						}
					}
				}
			} catch (CoreException e) {
				logError(e);
			}
		}
	}
	
	



	/**
	 * Removes all breakpoints from this target, such
	 * that each breakpoint can update its install
	 * count. This target's collection of breakpoints
	 * is cleared.
	 */
	@SuppressWarnings("unchecked")
	protected void removeAllBreakpoints() {
		Iterator<IRuleBreakpoint> breakpoints= ((ArrayList<IRuleBreakpoint>)((ArrayList<IRuleBreakpoint>)getBreakpoints()).clone()).iterator();
		while (breakpoints.hasNext()) {
			IRuleBreakpoint breakpoint= (IRuleBreakpoint) breakpoints.next();
			try {
				breakpoint.removeFromTarget((IRuleRunTarget)this);
			} catch (CoreException e) {
				logError(e);
			}
		}
		getBreakpoints().clear();
	}



	/**
	 * Adds all the breakpoints in this target's collection
	 * to this debug target.
	 */
	protected void reinstallAllBreakpoints() {
		Iterator<?> breakpoints= ((ArrayList<?>)((ArrayList<?>)getBreakpoints()).clone()).iterator();
		while (breakpoints.hasNext()) {
			IRuleBreakpoint breakpoint= (IRuleBreakpoint) breakpoints.next();
			try {
				breakpoint.addToTarget((IRuleRunTarget)this);
			} catch (CoreException e) {
				logError(e);
			}
		}
	}



	/**
	 * Sets the list of breakpoints installed in this debug
	 * target. Set to an empty list on creation.
	 * 
	 * @param breakpoints empty list
	 */
	protected void setBreakpoints(List<IRuleBreakpoint> breakpoints) {
		fBreakpoints = breakpoints;
	}
	

	
	public void setRequestTimeout(int value) {
		if (supportsRequestTimeout()) {
			VirtualMachine vm = getVM();
			if (vm != null) {
				//((org.eclipse.jdi.VirtualMachine)vm).setRequestTimeout(timeout);
			}
		}
	}
	

	/**
	 * @return
	 * @throws DebugException
	 */
	public ObjectReference getRuleServiceProvider() throws DebugException {
		if(getSession()!= null) {
			return getSession().getRuleServiceProvider();
		}
		return null;
	}

	
	/**
	 * @return
	 * @throws DebugException
	 */
	public boolean isCacheServerMode() throws DebugException {
		if(getSession()!= null) {
			return getSession().isCacheServerMode();
		}
		return false;
	}
	
	
	/**
	 * @return
	 * @throws DebugException
	 */
	public String getClusterName() throws DebugException {
		if(getSession()!= null) {
			return getSession().getClusterName();
		}
		return null;
	}
	
	
	/**
	 * Returns task registry mapper which maps between task type to impl class
	 * @return
	 */
	public TaskRegistryMapper getTaskRegistryMapper() {
		if(taskRegistryMapper == null ) {
			taskRegistryMapper = new TaskRegistryMapperImpl();
		}
		return taskRegistryMapper;
	}
	
	/**
	 * @param taskRegistryMapper
	 */
	public void setTaskRegistryMapper(TaskRegistryMapper taskRegistryMapper) {
		this.taskRegistryMapper = taskRegistryMapper;
	}
	
	public void initSourceInfoProviders() throws CoreException {		
		
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(StudioDebugCorePlugin.PLUGIN_ID, "mappedSourceInfoProvider"); //$NON-NLS-1$
		if(extensionPoint != null) {
			IConfigurationElement[] configs = extensionPoint.getConfigurationElements();
			for (int i= 0; i < configs.length; i++) {
				ISourceInfoProvider provider = (ISourceInfoProvider) configs[i].createExecutableExtension("class"); //$NON-NLS-1$
				if(!mappedSourceInfoProviders.contains(provider)) {
					mappedSourceInfoProviders.add(provider); 
				}
			}
		}
		
		
	}
	
	public List<ISourceInfoProvider> getSourceInfoProviders() {
		return mappedSourceInfoProviders;
	}



	protected void setInitializedBreakpoints() {
		this.fInitializedBreakpoints = true;
		
	}



	protected boolean isInitializedBreakpoints() {
		return this.fInitializedBreakpoints;
	}

	
}
