package com.tibco.be.functions.cluster;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.tibco.be.model.functions.Enabled;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.model.serializers.DataInputFlexibleEventDeserializer;
import com.tibco.cep.runtime.model.serializers.DataOutputFlexibleEventSerializer;
import com.tibco.cep.runtime.model.serializers.SerializableHelper;
import com.tibco.cep.runtime.model.serializers.SerializableLite;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.tasks.AgentAction;
import com.tibco.cep.runtime.service.cluster.agent.tasks.AgentActionManager;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.cluster.om.RemoteObjectStore;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkEntry;
import com.tibco.cep.runtime.service.cluster.scheduler.impl.DefaultSchedulerCache;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.service.cluster.util.WorkManagerFactory;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 7, 2008
 * Time: 11:03:41 AM
 * To change this template use File | Settings | File Templates.
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Cluster",
        synopsis = "Functions to operate on the cluster")
public class ClusterFunctions {

	static final Logger LOGGER;
    static ConcurrentHashMap<String, WorkManager> m_workManagers = new ConcurrentHashMap<String, WorkManager>();
    static long nextId=0L;
    static ConcurrentHashMap<String, LinkedBlockingQueue<Object>> localNamedPipes = new ConcurrentHashMap<String, LinkedBlockingQueue<Object>>();
    
    static {
    	Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
    	if (cluster != null) {
    		LOGGER = cluster.getRuleServiceProvider().getLogger(ClusterFunctions.class);
    	} else {
    		LOGGER = LogManagerFactory.getLogManager().getLogger(ClusterFunctions.class);
    	}
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getClusterName",
        synopsis = "Returns the name of the BE cluster that this agent is connected",
        signature = "String getClusterName()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The name of the BE cluster"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the name of the BE cluster that this agent is connected",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = "<br/> String cName = ClusterFunctions.getClusterName();<br/><br/>"
    )

    public final static String getClusterName() {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                return cluster.getClusterName();
            } else {
                return null;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSiteId",
        synopsis = "Returns the site-id of the cluster",
        signature = "long getSiteId()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The site-id of the cluster"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the site-id of the cluster",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )

    public final static long getSiteId() {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                return cluster.getClusterConfig().getSiteId();
            } else {
                return 0L;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAgentName",
        synopsis = "Returns the agent name",
        signature = "String getAgentName()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The name of the agent"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the name of the BE Agent",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = "<br/> String cName = ClusterFunctions.getAgentName();<br/><br/>"
    )

    public final static String getAgentName() {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                if (session.getObjectManager() instanceof DistributedCacheBasedStore) {
                    return ((DistributedCacheBasedStore) session.getObjectManager()).getCacheAgent().getAgentName();
                }
            }
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "refreshEntity",
        synopsis = "Refresh the local copy from the cluster",
        signature = "void refreshEntity(long id, int typeId, int version)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "id of the local copy of entity"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "typeId", type = "int", desc = "Type id of the local copy of entity.  This comes from the typeId argument of a subscription preprocessor."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "version", type = "int", desc = "Version of the entity.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Refresh the local copy from the cluster",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = "<br/> String cName = ClusterFunctions.refreshEntity(id,typeId,version);<br/><br/>"
    )

    public final static void refreshEntity(long id, int typeId, int version) {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                RuleSession session = RuleSessionManager.getCurrentRuleSession();
                if (session != null) {
                    if (session.getObjectManager() instanceof DistributedCacheBasedStore) {
                        ((DistributedCacheBasedStore) session.getObjectManager()).refreshEntity(id, typeId, version);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "typeIdToURI",
        synopsis = "Returns the design time URI for the typeId",
        signature = "String typeIdToURI(int typeId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "typeId", type = "int", desc = "The type id of the entity.  This comes from the typeId argument of a subscription preprocessor.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The design time URI of the typeid if found else <code>null</code>"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the design time URI for the typeId",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = "<br/> String eURI = ClusterFunctions.typeIdToURI(int typeId);<br/><br/>"
    )
    public static String typeIdToURI(int typeId) {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                RuleSession session = RuleSessionManager.getCurrentRuleSession();
                if (session != null) {
                    Class entityClz=cluster.getMetadataCache().getClass(typeId);
                    if (entityClz != null) {
                        if (StateMachineConcept.class.isAssignableFrom(entityClz)) {
                            return ModelNameUtil.generatedClassNameToStateMachineModelPath(entityClz.getName());
                        } else {
                            return ModelNameUtil.generatedClassNameToModelPath(entityClz.getName());
                        }
                    }
                }
            }
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAgentId",
        synopsis = "Returns the unique id of the agent",
        signature = "int getAgentId()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The id of the agent"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the unique id of the agent",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = "<br/> int cAgentId = ClusterFunctions.getAgentId();<br/><br/>"
    )
    public final static int getAgentId() {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                if (session.getObjectManager() instanceof DistributedCacheBasedStore) {
                    return ((DistributedCacheBasedStore) session.getObjectManager()).getCacheAgent().getAgentId();
                }
            }
            return -1;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createWorkManager",
        synopsis = "Creates a work manager to schedule background jobs",
        signature = "void createWorkManager(String name, int threadPool)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "A unique name to identify the thread pool"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "threadPool", type = "int", desc = "Specify the number of threads in the pool")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a work manager to schedule background jobs",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public final static synchronized void createWorkManager(String name, int threadPool) {
        createWorkManagerWithQueueSize(name, threadPool, 2 * threadPool /*default size is 2xthreadPool */);
    }
    
    public final static synchronized void createWorkManagerWithQueueSize(String name, int threadPool, int queueSize) {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                RuleSession session = RuleSessionManager.getCurrentRuleSession();
                if (session != null) {
                    if (threadPool <= 0) {
                        throw new RuntimeException("createWorkManager(" + name + "," + threadPool + "): Thread pool should be a positive number");
                    }
                    name = session.getName() + "." + name;
                    WorkManager workManager = (WorkManager) m_workManagers.get(name);
                    if (workManager == null) {
                        workManager =
                                WorkManagerFactory.create(cluster.getClusterName(), null, null,
                                        "UserWorkManager:" + name, threadPool, queueSize,
                                        session.getRuleServiceProvider());

                        m_workManagers.put(name, workManager);
                        workManager.start();
                    }
                } else {
                    throw new RuntimeException("createWorkManager(" + name + "," + threadPool + "): Rule Session is NULL");
                }
            } else {
                throw new RuntimeException("createWorkManager(" + name + "," + threadPool + "): Cluster is NULL");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addAgentListener(String agentGroupName, Object filter, String ruleFunctionURI) {
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "stopWorkManager",
        synopsis = "Shuts down the work manager. This call will wait for all the current jobs to finish",
        signature = "void stopWorkManager(String name)",
        params = {
        		 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "A unique name to identify the thread pool.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Shuts down the work manager. This call will wait for all the current jobs to finish",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public final static synchronized void stopWorkManager(String name) {
        try {
            WorkManager workManager=getWorkManager(name);
            if (workManager != null) {
                workManager.shutdown();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public final static WorkManager getWorkManager(String name) {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                RuleSession session = RuleSessionManager.getCurrentRuleSession();
                if (session != null) {
                    name = session.getName() + "." + name;
                    return (WorkManager) m_workManagers.get(name);
                } else {
                    throw new RuntimeException("getWorkManager(" + name + "): Rule Session is NULL");
                }
            } else {
                throw new RuntimeException("getWorkManager(" + name + "): Cluster is NULL");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "scheduleWork",
        synopsis = "Schedules a job with the specified work manager",
        enabled = @Enabled(value=false),
        signature = "void scheduleWork(String name, Object work)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "A unique name to identify the thread pool")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Schedules a job with the specified work manager",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public final static synchronized void scheduleWork(String name, Object work) {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                RuleSession session = RuleSessionManager.getCurrentRuleSession();
                if (session != null) {
                    name = session.getName() + "." + name;
                    WorkManager workManager= (WorkManager) m_workManagers.get(name);
                    if (workManager != null) {
                        workManager.submitJob((Runnable) work);
                    } else {
                        throw new RuntimeException("scheduleWork(" + name + "," + work + "): Work Manager Not Found");
                    }
                } else {
                    throw new RuntimeException("scheduleWork(" + name + "," + work + ") Rule Session is NULL");
                }
            } else {
                throw new RuntimeException("scheduleWork(" + name + "," + work + "): Cluster is NULL");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isEventRecovered",
        synopsis = "This function returns true if the passed event is received from another agent in the cluster.\nThe function will return false if the event was created in this agent.",
        signature = "boolean isEventRecovered(SimpleEvent evt)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "evt", type = "SimpleEvent", desc = "The event to be tested")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "<code>true</code> if the event is recevied from another agent in the cluster else <code>false</code>"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function returns true if the passed event is received from another agent in the cluster.\nThe function will return false if the event was created in this agent.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = "<br/> String cName = ClusterFunctions.isEventRecovered(SimpleEvent evt);<br/><br/>"
    )
    public final static boolean isEventRecovered(SimpleEvent evt) {
        return ((SimpleEventImpl) evt).isRecovered();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createScheduler",
        synopsis = "Creates a cluster wide scheduler for time dependent jobs",
        signature = "void createScheduler(String schedulerName, long pollInterval, long refreshAhead)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "schedulerName", type = "String", desc = "A unique id"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pollInterval", type = "long", desc = "The time in milliseconds of polling the cache for due entries"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "refreshAhead", type = "long", desc = "The time in milliseconds that is used to pre-load the entries from the underlying store to the scheduler cache")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a cluster wide scheduler for time dependent jobs. If the scheduler is created by another node, then the method can be used to overwrite\nthe pollInterval and refreshAhead",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public final static void createScheduler(String schedulerName, long pollInterval, long refreshAhead) {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                if ((schedulerName == null) || (schedulerName.trim().length() == 0)) {
                    throw new RuntimeException("Scheduler Name cannot be NULL");
                } else if (pollInterval <= 0) {
                    throw new RuntimeException("Scheduler " + schedulerName + " pollInterval should be a positive number");
                } else if (refreshAhead <= 0) {
                    throw new RuntimeException("Scheduler " + schedulerName + " refreshAhead should be a positive number");
                } else {
                    long tmp = (pollInterval > refreshAhead) ? pollInterval : refreshAhead;
                    cluster.getSchedulerCache().createScheduler(schedulerName, tmp, tmp, true, DefaultSchedulerCache.MODE_ALL);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "scheduleEvent",
        synopsis = "Schedules a simple event to be sent to the default destination after <code>scheduledTime</code>.\nThe scheduled time is represented in milliseconds from January 1, 1970 (the UNIX epoch).",
        signature = "void scheduleEvent(String schedulerName, String workKey, SimpleEvent event, long scheduledTime)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "schedulerName", type = "String", desc = "A unique id."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "workKey", type = "String", desc = "A unique key that identifies the work item."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The simple event to be scheduled."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scheduledTime", type = "long", desc = "The time in milliseconds when the event should be scheduled.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Schedules a simple event to be sent to the default destination after <code>scheduledTime</code>.\nThe scheduled time is represented in milliseconds from January 1, 1970 (the UNIX epoch).\nHint: To schedule a time <code>n</code> milliseconds from the present, you can use\n<code>DateTime.getTimeInMillis() + n</code>.",
        cautions = "Events with default destinations on local channels will be received on the agent where the scheduler is running.  If your configuration allows schedulers to run on cache agents (this is the default), do not schedule events with default destinations on local channels.",
        fndomain = {ACTION},
        example = ""
    )
    public final static void scheduleEvent(String schedulerName, String workKey, SimpleEvent event, long scheduledTime) {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                if ((schedulerName == null) || (schedulerName.trim().length() == 0)) {
                    throw new RuntimeException("scheduleEvent(): Scheduler Name cannot be NULL");
                } else if (event == null) {
                    throw new RuntimeException("scheduleEvent(): Event cannot be NULL");
                } else if (workKey == null) {
                    throw new RuntimeException("scheduleEvent(): Work Key cannot be NULL");
                } else {
                    if (!cluster.getSchedulerCache().schedulerExists(schedulerName)) {
                        throw new RuntimeException("Scheduler " + schedulerName + " not registered with the cluster");
                    }
                    RuleSession session = RuleSessionManager.getCurrentRuleSession();
                    if (AgentActionManager.hasActionManager(session)) {
                        // NON-PreProcessor context (startup or rules)
                        AgentActionManager.addAction(session, new ScheduleSendEventAction(schedulerName, workKey, event, scheduledTime, 0));
                    } else {
                        // IN-PreProcessor context (preprocessor function)
                        cluster.getSchedulerCache().schedule(schedulerName, workKey, new SendEventTask2(event, scheduledTime));
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "scheduleRepeatingEvent",
        synopsis = "Schedules a simple event to be sent to the default destination beginning on <code>startingDate</code>, and repeating every <code>interval</code> milliseconds.\nIt is recommended to specify starting date/time using <code>DateTime.createTime()</code> e.g. DateTime.createTime(2014, 0, 1, 0, 0, 0, null).",
        signature = "void scheduleRepeatingEvent(String schedulerName, String workKey, SimpleEvent event, DateTime startingDate, long interval)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "schedulerName", type = "String", desc = "A unique id."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "workKey", type = "String", desc = "A unique key that identifies the work item.\nHint: If persistence is used, removing previously scheduled repeating event should be considered. This will prevent creating too many repeating events, or violating unique keys. See <code>Cluster.removeSchedule()</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "The simple event to be scheduled."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "startingDate", type = "DateTime", desc = "The starting date/time of repeating scheduled event (date/time will be rolled into future, if defined in the past)."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "interval", type = "long", desc = "The repeat interval of the scheduled event (if 0, event will be scheduled once for the exact date/time specified).")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Schedules a simple event to be sent to the default destination beginning on <code>startingDate</code>, and repeating every <code>interval</code> milliseconds.\nIt is recommended to specify starting date/time using <code>DateTime.createTime()</code> e.g. DateTime.createTime(2014, 0, 1, 0, 0, 0, null).",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public final static void scheduleRepeatingEvent(String schedulerName, String workKey, SimpleEvent event, Calendar startingDate, long interval) {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                if ((schedulerName == null) || (schedulerName.trim().length() == 0)) {
                    throw new RuntimeException("scheduleRepeatingEvent(): Scheduler Name cannot be NULL");
                } else if (event == null) {
                    throw new RuntimeException("scheduleRepeatingEvent(): Event cannot be NULL");
                } else if (workKey == null) {
                    throw new RuntimeException("scheduleRepeatingEvent(): Work Key cannot be NULL");
                } else {
                    if (!cluster.getSchedulerCache().schedulerExists(schedulerName)) {
                        throw new RuntimeException("Scheduler " + schedulerName + " not registered with the cluster");
                    }

                    long now = System.currentTimeMillis();
                    long scheduledTime = now; // Default to current-time
                    if (startingDate != null) {
                        scheduledTime = startingDate.getTimeInMillis();
                    }
                    long repeatInterval = 0; // Default to non-repeating
                    if (interval > 0) {
                        repeatInterval = interval;
                        LOGGER.log(Level.WARN,"ScheduleRepeatingEvent:  repeatInterval:= %s", repeatInterval);
                    }
                    while (scheduledTime < now) {
                        if (repeatInterval == 0) {
                            // Non-repeating schedule, and scheduled in the past.
                            // Will schedule and execute once.
                            break;
                        }
                        scheduledTime += repeatInterval;
                    }
                    RuleSession session = RuleSessionManager.getCurrentRuleSession();
                    if (AgentActionManager.hasActionManager(session)) {
                        // NON-PreProcessor context (startup or rules)
                        AgentActionManager.addAction(session, new ScheduleSendEventAction(schedulerName, workKey, event, scheduledTime, repeatInterval));
                    } else {
                        // IN-PreProcessor context (preprocessor function)
                        cluster.getSchedulerCache().schedule(schedulerName, workKey, new SendRepeatingEventTask2(event, scheduledTime, repeatInterval));
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

	/** BE-22006
    @com.tibco.be.model.functions.BEFunction (
        name = "listScheduledItems",
        synopsis = "Returns previously scheduled entries for a given period in the future.",
        signature = "void listScheduledItems(String schedulerName, long period, int limit)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "schedulerName", type = "String", desc = "A unique id"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "period", type = "long", desc = "Period to query for entries (in milliseconds, from currenttime)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "limit", type = "int", desc = "Maximum number of entries to return")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Each array element is an Object[] itself, consisting of [Schedule-time(long), Work-key(string)]"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns previously scheduled entries for given period in the future",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public final static Object[] listScheduledItems(String schedulerName, long period, int limit) {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                return cluster.getSchedulerCache().listEntries(schedulerName, period, (limit > 0) ? limit:Integer.MAX_VALUE);
            }
            return new Object[]{};
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getScheduledItem",
        synopsis = "Returns the scheduled entry identified with the key.",
        signature = "void getScheduledItem(String schedulerName, String workKey)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "schedulerName", type = "String", desc = "A unique id"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "workKey", type = "String", desc = "A unique key")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Scheduled item (most cases it will be an Event)"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the scheduled entry identified with the key.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public final static Object getScheduledItem(String schedulerName, String workKey) {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                WorkEntry result = cluster.getSchedulerCache().getEntry(schedulerName, workKey);
                if (result instanceof SendEventTask) {
                    SendEventTask task = (SendEventTask)result;
                    return task.evt;
                } else if (result instanceof SendTimeEventTask) {
                    //TODO: Anything special we can return?
                    //SendTimeEventTask task = (SendTimeEventTask)result;
                } else if (result instanceof SMTimeoutTask) {
                    //TODO: Anything special we can return?
                    //SMTimeoutTask task = (SMTimeoutTask)result;
                }
                return result;
            }
            return new Object[]{};
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
	*/
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getScheduleTime",
        signature = "long getScheduleTime(String schedulerName, String workKey)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "schedulerName", type = "String", desc = "Scheduler Name to check"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "workKey", type = "String", desc = "Key associated to the entry")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "Scheduled time of the entry matching the key"),
        version = "5.6",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the scheduled time of the entry identified with the key.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public final static long getScheduleTime(String schedulerName, String workKey) {
    	long scheduledTime = 0;
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
            	if (schedulerName == null || schedulerName.isEmpty()) {
            		throw new RuntimeException("getScheduleTime(): Scheduler Name cannot be NULL");
            	} else if (workKey == null || workKey.isEmpty()) {
            		throw new RuntimeException("getScheduleTime(): Work Key cannot be NULL");
            	} else {
            		WorkEntry result = cluster.getSchedulerCache().getEntry(schedulerName, workKey);
            		if (result != null) {
            			scheduledTime = result.getScheduleTime();
            		} else {
            			throw new RuntimeException(String.format("No Item scheduled under Scheduler Name[%s] with Work Key[%s].", schedulerName, workKey));
            		}
            	}
            }
            return scheduledTime;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeSchedule",
        synopsis = "Removes a previously scheduled task",
        signature = "void removeSchedule(String schedulerName, String workKey)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "schedulerName", type = "String", desc = "A unique id"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "workKey", type = "String", desc = "A unique key")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes a previously scheduled task",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public final static void removeSchedule(String schedulerName, String workKey) {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                if ((schedulerName == null) || (schedulerName.trim().length() == 0)) {
                    throw new RuntimeException("removeSchedule(): Scheduler Name cannot be NULL");
                } else if (workKey == null) {
                    throw new RuntimeException("removeSchedule():  Work Key cannot be NULL");
                } else {
                    RuleSession session = RuleSessionManager.getCurrentRuleSession();
                    if (AgentActionManager.hasActionManager(session)) {
                        AgentActionManager.addAction(session, new RemoveScheduleAction(schedulerName, workKey));
                        return;
                    } else {
                        cluster.getSchedulerCache().remove(schedulerName, workKey);
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "registerStateMachineTimeoutCallback",
        synopsis = "Register a rule function to pre-process state machine timeouts. The pre-processor should be used to load and/or\nlock associated objects.",
        signature = "void registerStateMachineTimeoutCallback(String entityURI, String ruleFunctionURI)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityURI", type = "String", desc = "URI for the concept. The preprocessor will be called when any states of state machines for these concepts timeout."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFunctionURI", type = "String", desc = "URI for the pre-processor rulefunction.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Register a rule function to pre-process state machine timeouts. The pre-processor should be used to load and/or\nlock associated objects.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public final static void registerStateMachineTimeoutCallback(String entityURI, String ruleFunctionURI) {
    	try {
    		RuleSession session = RuleSessionManager.getCurrentRuleSession();
    		if (session != null) {
    			((RuleSessionImpl) session).registerSMTimeoutCallback(entityURI, ruleFunctionURI);
    		}
    	} catch (Exception ex) {
    		throw new RuntimeException(ex);
    	}
    }

//    /**
//     * @.name isEventRecovered
//     * @.synopsis This function returns true if the passed event is received from another agent in the cluster.
//     *  The function will return false if the event was created in this agent.
//     * @.signature boolean isEventRecovered(SimpleEvent evt)
//     * @.version 3.0
//     * @.see
//     * @.mapper false
//     * @.description This function returns true if the passed event is received from another agent in the cluster.
//     *  The function will return false if the event was created in this agent.
//     * @.cautions none
//     * @.domain action, condition, query
//     * @.example
//     * <br/> String cName = ClusterFunctions.isEventRecovered(SimpleEvent evt);<br/><br/>
//     * @return boolean true if the event was received from another agent
//     */
//    public final static boolean notifyAgent(String agentName, SimpleEvent evt) {
//        return ((SimpleEventImpl) evt).isRecovered();
//    }
//
//    static class NotifyAgentAction implements AgentAction {
//        SimpleEvent evt;
//
//        NotifyAgentAction(SimpleEvent evt, String agentName) {
//            this.evt=evt;
//        }
//
//        public void run(CacheAgent cacheAgent) throws Exception{
//            if (cacheAgent instanceof InferenceAgent) {
//                InferenceAgent ia= (InferenceAgent) cacheAgent;
//                ia.notifyAgent(evt);
//            }
//        }
//    }

    public static class SendEventTask implements WorkEntry {
        SimpleEvent evt;
        long scheduledTime;

        public SendEventTask() {
        }

        public void execute(String key, Object agent) throws Exception {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                ChannelManager manager = cluster.getRuleServiceProvider().getChannelManager();
                manager.sendEvent(evt, evt.getDestinationURI(), null);
            } else {
                throw new Exception("Unable to send event, cluster is NULL");
            }
        }

        public SimpleEvent getEvent() {
			return evt;
		}
        
        public long getScheduleTime() {
            return scheduledTime;
        }

        public long setScheduleTime(long newScheduledTime) {
            long oldScheduledTime = scheduledTime;
            scheduledTime = newScheduledTime;
            return oldScheduledTime;
        }

        public long getRepeatInterval() {
            return 0;
        }

        public void readExternal(DataInput dataInput) throws IOException {
            scheduledTime = dataInput.readLong();
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            evt = (SimpleEvent) SerializableHelper.readSerializableLite(dataInput, (ClassLoader) cluster.getRuleServiceProvider().getTypeManager());
        }

        public void writeExternal(DataOutput dataOutput) throws IOException {
            dataOutput.writeLong(scheduledTime);
            SerializableHelper.writeSerializableLite(dataOutput, (SerializableLite) evt);
        }
    }
    
    public static class SendEventTask2 extends SendEventTask {
        
    	public SendEventTask2(SimpleEvent evt, long scheduledTime) {
            this.scheduledTime = scheduledTime;
            this.evt = evt;
        }
        
    	public SendEventTask2() {
    		super();
    	}
    	
    	public void readExternal(DataInput dataInput) throws IOException {
            scheduledTime = dataInput.readLong();
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            RuleServiceProvider rsp = cluster.getRuleServiceProvider();
            try {
            	if(!dataInput.readBoolean()) {
		            evt = (SimpleEvent) Class.forName(dataInput.readUTF(), true, rsp.getClassLoader()).newInstance();
		            evt.deserialize(new DataInputFlexibleEventDeserializer(dataInput, rsp.getTypeManager()));
            	} else {
            		evt = null;
            	}
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            	throw new RuntimeException(ex);
            }
        }

        public void writeExternal(DataOutput dataOutput) throws IOException {
            dataOutput.writeLong(scheduledTime);
            dataOutput.writeBoolean(evt == null);
            if(evt != null) {
	            dataOutput.writeUTF(evt.getClass().getName());
	            String[] propNames = evt.getPropertyNames();
	            evt.serialize(new DataOutputFlexibleEventSerializer(dataOutput, evt.getClass(), propNames == null ? 0 : propNames.length));
            }
        }
    }

    public static class SendRepeatingEventTask extends SendEventTask implements WorkEntry {

        long repeatInterval;
        String repeatDetails = ""; // Future use!

        public SendRepeatingEventTask() {
        }
       
        public long getRepeatInterval() {
            return repeatInterval;
        }

        public void readExternal(DataInput dataInput) throws IOException {
            repeatInterval = dataInput.readLong();
            repeatDetails = dataInput.readUTF();
            super.readExternal(dataInput);
        }

        public void writeExternal(DataOutput dataOutput) throws IOException {
            dataOutput.writeLong(repeatInterval);
            dataOutput.writeUTF(repeatDetails);
            super.writeExternal(dataOutput);
        }
    }
    
    public static class SendRepeatingEventTask2 extends SendEventTask2 implements WorkEntry {

        long repeatInterval;
        String repeatDetails = ""; // Future use!

        public SendRepeatingEventTask2() {
        }

        public SendRepeatingEventTask2(SimpleEvent evt, long scheduledTime, long repeatInterval) {
            super(evt, scheduledTime);
            this.repeatInterval = repeatInterval;
        }
        
        public long getRepeatInterval() {
            return repeatInterval;
        }

        public void readExternal(DataInput dataInput) throws IOException {
            repeatInterval = dataInput.readLong();
            repeatDetails = dataInput.readUTF();
            super.readExternal(dataInput);
        }

        public void writeExternal(DataOutput dataOutput) throws IOException {
            dataOutput.writeLong(repeatInterval);
            dataOutput.writeUTF(repeatDetails);
            super.writeExternal(dataOutput);
        }
    }

    static class ScheduleSendEventAction implements AgentAction {
        String schedulerName;
        String key;
        SimpleEvent evt;
        long scheduleTime;
        long repeatInterval;

        ScheduleSendEventAction(String schedulerName, String key, SimpleEvent evt, long scheduleTime, long repeatInterval) {
            this.evt = evt;
            this.schedulerName = schedulerName;
            this.key = key;
            this.scheduleTime = scheduleTime;
            this.repeatInterval = repeatInterval;
        }
        // NON-PreProcessor context
        public void run(CacheAgent cacheAgent) throws Exception {
            // Run method is called in Post-RTC (see: applyTransactionToActions) and adds to the scheduler cache.
            if (this.repeatInterval > 0) {
                cacheAgent.getCluster().getSchedulerCache().schedule(schedulerName, key, new SendRepeatingEventTask2(evt, scheduleTime, repeatInterval));
            } else {
	            cacheAgent.getCluster().getSchedulerCache().schedule(schedulerName, key, new SendEventTask2(evt, scheduleTime));
	        }
        }
    }

    static class RemoveScheduleAction implements AgentAction {
        String schedulerName;
        String key;

        RemoveScheduleAction(String schedulerName, String key) {
            this.schedulerName = schedulerName;
            this.key = key;
        }

        public void run(CacheAgent cacheAgent) throws Exception {
            cacheAgent.getCluster().getSchedulerCache().remove(schedulerName, key);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getRemoteByExtId",
        synopsis = "This function returns a Concept from a remote cluster",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.cluster.remote", value=false),
        signature = "Concept getRemoteByExtId(String clusterName,String entityURI, String extId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "clusterName", type = "String", desc = "the cluster name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityURI", type = "String", desc = "entity URI"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "extId")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "A concept from the remote cluster if found else <code>null</code>"),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function returns a concept from a remote cluster.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = "<br/> Concept cept = ClusterFunctions.getRemoteByExtId(String clusterName, String entityURI, String extId );<br/><br/>"
    )
    public static Concept getRemoteByExtId(String clusterName,String entityURI, String extId)  {
        try {
            //Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (! (session.getObjectManager() instanceof RemoteObjectStore)) {
                throw new RuntimeException("Incorrect Object Manager type selected for using with remote cache cluster access.");
            }
            return (Concept) ((RemoteObjectStore)session.getObjectManager()).getRemoteElement(clusterName,extId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getRemoteById",
        synopsis = "This function returns a Concept from a remote cluster",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.cluster.remote", value=false),
        signature = "Concept getRemoteByExtId(String clusterName,String entityURI, long id)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "clusterName", type = "String", desc = "the cluster name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityURI", type = "String", desc = "entity URI"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "id")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "A concept from the remote cluster if found else <code>null</code>"),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function returns a concept from a remote cluster.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = "<br/> Concept cept = ClusterFunctions.getRemoteByExtId(String clusterName, String entityURI, long id );<br/><br/>"
    )
    public static Concept getRemoteById(String clusterName, String entityURI, long id) {
        try {
            //Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (!(session.getObjectManager() instanceof RemoteObjectStore)) {
                throw new RuntimeException("Incorrect Object Manager type selected for using with remote cache cluster access.");
            }
            return (Concept) ((RemoteObjectStore) session.getObjectManager()).getRemoteElement(clusterName, id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "runRuleFunction",
        synopsis = "This function runs a ruleFunction continously as per the return status of the ruleFunction",
        signature = "void runRuleFunction(String workMgrName, String ruleFnURI, Object[] args,  boolean bInPreprocessContext)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "workMgrName", type = "String", desc = "The workMgrName - The thread that will execute the rule Function"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFnURI", type = "String", desc = "The ruleFn to execute. This ruleFunction should return boolean true to continue, false to stop executing"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "The args for ruleFunction")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function runs a ruleFunction continously as per the return status of the ruleFunction",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void runRuleFunction(String workMgrName, String ruleFnURI, Object[] args,  boolean bInPreprocessContext)  {

        RuleSessionImpl ruleSession = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        WorkManager mgr = (WorkManager) m_workManagers.get(ruleSession.getName() + "." + workMgrName);

        if (mgr == null) throw new RuntimeException("Invalid work manager name provided - " + workMgrName);

        RuleFunction rf = ruleSession.getRuleFunction(ruleFnURI);
        if (rf == null)  throw new RuntimeException("Invalid rule function uri provided - " + ruleFnURI);

        RuleFunction.ParameterDescriptor[] pds = rf.getParameterDescriptors();
        if (!(pds[pds.length-1].getType() == boolean.class)) {
            throw new RuntimeException("Rule function signature has to return a boolean value - " + ruleFnURI);
        }

        RuleFunctionTask rft = new RuleFunctionTask(ruleSession, rf, args, bInPreprocessContext);

        try {
            mgr.submitJob(rft);
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "executeRuleFunction",
        synopsis = "This function runs a rule function inside the context of a work manager",
        signature = "void runRuleFunction(String workMgrName, String ruleFnURI, boolean bInPreprocessContext, Object... args)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "workMgrName", type = "String", desc = "The workMgrName - The thread that will execute the rule Function"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFnURI", type = "String", desc = "The ruleFn to execute. This ruleFunction should return boolean true to continue, false to stop executing"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bInPreprocessContext", type = "boolean", desc = "Whether to execute in Pre-processor context"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "The args for ruleFunction")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function runs a rule function inside the context of a work manager",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void executeRuleFunction(String workMgrName, String ruleFnURI, boolean bInPreprocessContext, Object... args)  {

        RuleSessionImpl ruleSession = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        WorkManager mgr = (WorkManager) m_workManagers.get(ruleSession.getName() + "." + workMgrName);

        if (mgr == null) throw new RuntimeException(String.format("Invalid work manager name provided - %s", workMgrName));


        RuleFunction rf = ruleSession.getRuleFunction(ruleFnURI);
        if (rf == null)  throw new RuntimeException(String.format("Invalid rule function uri provided - %s", ruleFnURI));

        RuleFunction.ParameterDescriptor[] pds = rf.getParameterDescriptors();
        if (!(pds[pds.length - 1].getType() == boolean.class)) {
            throw new RuntimeException(String.format("Rule function signature has to return a boolean value - %s ", ruleFnURI));
        }

        RuleFunctionTask rft = new RuleFunctionTask(ruleSession, rf, args, bInPreprocessContext);

        try {
            mgr.submitJob(rft);
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createLocalNamedPipe",
        synopsis = "Creates a per-JVM in-memory blocking queue/pipe for inter-thread communication.",
        signature = "boolean createLocalNamedPipe(String pipeName, int capacity)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pipeName", type = "String", desc = "Name of the pipe"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "capacity", type = "int", desc = "Maximum capacity of the pipe")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "<code>true</code> if no pipe was previously registered with the specified name, <code>false</code> if a pipe was already registered with the specified name"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a per-JVM in-memory blocking queue/pipe for inter-thread communication.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static boolean createLocalNamedPipe(String pipeName, int capacity) {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<Object>(capacity);

        LinkedBlockingQueue<Object> existingQueue = localNamedPipes.putIfAbsent(pipeName, queue);

        return (existingQueue == null);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getLocalNamedPipeCurrentSize",
        synopsis = "Retrieves the current size of the pipe.",
        signature = "int getLocalNamedPipeCurrentSize(String pipeName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pipeName", type = "String", desc = "Name of the pipe")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "the current size of the pipe"),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves the current size of the pipe.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static int getLocalNamedPipeCurrentSize(String pipeName){
        LinkedBlockingQueue<Object> queue = localNamedPipes.get(pipeName);

        return queue.size();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "sendMessageOverLocalNamedPipe",
        synopsis = "Send a message over the pipe.",
        signature = "void sendMessageOverLocalNamedPipe(String pipeName, Object message)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pipeName", type = "String", desc = "Name of the pipe"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "The message to be sent over the pipe.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Send a message over the pipe. If the pipe is full, then this call blocks until\nspace is available to place this message in the queue.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static void sendMessageOverLocalNamedPipe(String pipeName, Object message) {
        LinkedBlockingQueue<Object> queue = localNamedPipes.get(pipeName);

        for (int i = 0; ; i++) {
            try {
                queue.put(message);

                break;
            }
            catch (InterruptedException e) {
                if (i == 3) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "sendMessagesOverLocalNamedPipe",
        synopsis = "Send a <code>java.util.Collection</code> full of messages over the pipe.",
        signature = "void sendMessagesOverLocalNamedPipe(String pipeName, Object collection)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pipeName", type = "String", desc = "Name of the pipe"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "collection", type = "Object", desc = "Collection of messages to be sent over the pipe.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Send a <code>java.util.Collection</code> full of messages over the pipe. Each\nitem in the collection is sent separately as a message. If the pipe is full, then this call\nblocks until space is available to place this message in the queue.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static void sendMessagesOverLocalNamedPipe(String pipeName, Object collection) {
        Collection<Object> messages = (Collection<Object>) collection;

        LinkedBlockingQueue<Object> queue = localNamedPipes.get(pipeName);

        for (Object message : messages) {
            for (int i = 0; ; i++) {
                try {
                    queue.put(message);

                    break;
                }
                catch (InterruptedException e) {
                    if (i == 3) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "pollMessageFromLocalNamedPipe",
        synopsis = "Receive a message if one is available on the pipe.",
        signature = "Object pollMessageFromLocalNamedPipe(String pipeName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pipeName", type = "String", desc = "Name of the pipe")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve a message if one is available on the pipe. If not, then the call\nreturns immediately with a <code>null</code> value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object pollMessageFromLocalNamedPipe(String pipeName) {
        LinkedBlockingQueue<Object> queue = localNamedPipes.get(pipeName);

        return queue.poll();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "waitForMessageFromLocalNamedPipe",
        synopsis = "",
        signature = "Object waitForMessageFromLocalNamedPipe(String pipeName, long timeoutMillis)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pipeName", type = "String", desc = "Name of the pipe"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeoutMillis", type = "long", desc = "Specified timeout in milliseconds")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve a message if one is available on the pipe. If not, then the call\nblocks until a message is available for the duration specified. If there is no message\neven after the specified timeout, then the method returns <code>null</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object waitForMessageFromLocalNamedPipe(String pipeName, long timeoutMillis) {
        LinkedBlockingQueue<Object> queue = localNamedPipes.get(pipeName);

        for (int i = 0; ; i++) {
            try {
                return queue.poll(timeoutMillis, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e) {
                if (i == 3) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class RuleFunctionTask implements Runnable {

        RuleSessionImpl session;
        RuleFunction rf;
        Object[] args;
        boolean bInPreprocessContext;

        RuleFunctionTask(RuleSessionImpl session, RuleFunction rf, Object[] args, boolean bInPreprocessContext) {
            this.session = session;
            this.rf = rf;
            this.args = args;
            this.bInPreprocessContext = bInPreprocessContext;
        }

        public void run() {
            boolean bStatus = true;
            boolean bSynchronize = !bInPreprocessContext;
            while (bStatus) {
                Boolean ret = (Boolean) session.invokeFunction(rf, args, bSynchronize);
                bStatus = ret;
            }
        }
    }
}
