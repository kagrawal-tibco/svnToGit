package com.tibco.cep.query.service.impl;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.BaseTimeManager;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.core.rete.conflict.ConflictResolver;
import com.tibco.cep.kernel.core.rete.conflict.SimpleConflictResolver;
import com.tibco.cep.kernel.helper.TimerTaskOnce;
import com.tibco.cep.kernel.helper.TimerTaskRepeat;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.impl.DefaultExceptionHandler;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.exception.DuplicateAliasException;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.service.QueryServiceProvider;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.cache.SharedPointer;
import com.tibco.cep.query.stream.impl.rete.LiteReteEntity;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandleType;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityChangeListener;
import com.tibco.cep.query.stream.util.BQLHelper;
import com.tibco.cep.query.utils.idgenerators.CompositeIdentifierGenerator;
import com.tibco.cep.query.utils.idgenerators.ConstantIdentifierGenerator;
import com.tibco.cep.query.utils.idgenerators.StringIdentifierGenerator;
import com.tibco.cep.query.utils.idgenerators.session.SessionIdGenerator;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.scheduler.TaskControllerFactory;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.PreprocessContext;
import com.tibco.cep.runtime.session.impl.RuleSessionConfigImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;
import com.tibco.cep.runtime.session.locks.LockManager;


/**
 * Created by IntelliJ IDEA. User: pdhar Date: Mar 6, 2008 Time: 8:08:40 PM To change this template
 * use File | Settings | File Templates.
 */
public class QueryRuleSessionImpl extends RuleSessionImpl implements QuerySession {
    protected static final Map<RuleServiceProvider, QueryServiceProvider> RSP_TO_QSP =
            new HashMap<RuleServiceProvider, QueryServiceProvider>();

    protected ReteEntityChangeListener changeListener;

    protected SharedObjectSource defaultSOS;

    protected Cache primaryCache;

    protected boolean enableStartupShutdownFns = true;

    protected CompositeIdentifierGenerator idgen;

    /**
     * Make sure you set {@link DelegatedQueryOM#setDistributedServiceInfo(String, String, int)} or {@link DelegatedQueryOM#setSingleServiceInfo(String)} after creation.
     * @param barResource
     * @param manager
     * @throws Exception
     */
    public QueryRuleSessionImpl(BEArchiveResource barResource, RuleSessionManagerImpl manager)
            throws Exception {
        super((LockManager)null);

        sessManager = manager;
        sessManager.setCurrentRuleSession(this);

        try {
            config = new RuleSessionConfigImpl(barResource, sessManager.getRuleServiceProvider());
            name = config.getName();
            logger = sessManager.getRuleServiceProvider().getLogger(this.getClass());
            final LogManager logManager = LogManagerFactory.getLogManager();
            controller = TaskControllerFactory.createTaskController(this,
                    sessManager.getRuleServiceProvider().getProperties());

            objectManager = new DelegatedQueryOM(name);
            objectStoreEnabled = false;

            DefaultExceptionHandler handler = new DefaultExceptionHandler(logger);
            ConflictResolver conflictResolver = new SimpleConflictResolver();
            BaseTimeManager timeManager = new DummyTimeManager();
            workingMemory =
                    new ReteWM(name, logManager, handler, objectManager, timeManager, conflictResolver);

            objectManager.init(workingMemory);

            //--------- Check if BQL Console is enabled. If so, disable startup, shutdown Functions and also channel activation.
            if(BQLHelper.isBQLConsoleEnabled(this.getRuleServiceProvider().getProperties(), name)) {
                enableStartupShutdownFns = false;    
            }

            this.initStartupShutdown();

            activeMode = false;
        }
        finally {
            sessManager.setCurrentRuleSession(null);
        }

        // -------------- Id generator
        try {
            this.idgen = new CompositeIdentifierGenerator(
                    new StringIdentifierGenerator[]{new ConstantIdentifierGenerator(
                            QuerySession.QUERY_ID_PREFIX),
                            new SessionIdGenerator()});
        } catch (Exception e) {
            throw new QueryException(e);
        }
    }

    public DelegatedQueryOM getDelegatedQueryOM(){
        return (DelegatedQueryOM) getRuleSession().getObjectManager();
    }

    public QueryServiceProvider getQueryServiceProvider() throws QueryException {
        final RuleServiceProvider rsp = this.getRuleServiceProvider();
        QueryServiceProvider qsp = RSP_TO_QSP.get(rsp);

        if (null == qsp) {
            qsp = new QueryServiceProviderImpl(rsp);
            RSP_TO_QSP.put(rsp, qsp);
        }

        return qsp;
    }

    /**
     * No-op.
     *
     * @param active
     */
    @Override
    public void setActiveMode(boolean active) {
    }


    @Override
    public void init(boolean cacheServerOnly) throws Exception {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
        if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);
            try {
                cacheServer = cacheServerOnly;
                // Remove this for multiple threads recovery Nick needs to review this
                // to see if it is safe
                // synchronized(workingMemory) {
                if (cacheServer)
                    this.workingMemory.init(null, null, new HashSet());  //no rule and no startup function
                else
                    this.workingMemory.init(this.startup, null, new HashSet());
                /**
                registerTypes();
                if (objectStoreEnabled) {
                    ((ObjectStore)objectManager).recover();
                }
                */
            }
            finally {
                sessManager.setCurrentRuleSession(null);
            }
        } else {
            throw new RuntimeException("Session " + threadSession.getName() + " can't init another session " + this.getName());
        }
    }

    @Override
    protected void initStartupShutdown() {
        if(enableStartupShutdownFns == true) {
            super.initStartupShutdown();
        } else {
            logger.log(Level.WARN, "Disabling all startup and shutdown functions for Agent:" +
                getRegionName() + " Id:" + getInstanceId() + " Name:" + getName());
        }
    }

    public ReteEntityChangeListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(ReteEntityChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public Cache getPrimaryCache() {
        return primaryCache;
    }

    public void setPrimaryCache(Cache primaryCache) {
        this.primaryCache = primaryCache;
    }

    public void setSharedObjectSourceRepository(SharedObjectSourceRepository sosRepository) {
        this.defaultSOS = sosRepository.getDefaultSource();
    }

    /**
     * creates a Query object for a given oql
     *
     * @param name String Name given to the query.
     * @param oql  @return Query
     * @throws QueryException
     */
    public Query createQuery(String name, String oql) throws QueryException {
        return new QueryImpl(oql, this);
    }

    /**
     * Gets the name of the region identifying the agent / session.
     *
     * @return String name of the region.
     */
    public String getRegionName() {
        return name;
    }

    /* (non-Javadoc)
    * @see com.tibco.cep.query.service.QueryService#getSession()
    */
    public RuleSession getRuleSession() {
        return this;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.id.IdentifierGenerator#nextIdentifier()
      */
    public Object nextIdentifier() throws DuplicateAliasException {
        return idgen.nextIdentifier();
    }


    /**
     * @param object
     * @param executeRules
     */
    @Override
    public void assertObject(Object object, boolean executeRules) {
        Class entityClass = object.getClass();

        if (object instanceof Entity && changeListener.hasListeners(entityClass)) {
            Entity entity = (Entity) object;

            ReteEntityHandle handle =
                    new ReteEntityHandle(entity.getClass(), entity.getId(), ReteEntityHandleType.NEW);
            SharedPointer sharedPointer = new LiteReteEntity(entity, defaultSOS);
            handle.setSharedPointer(sharedPointer);

            changeListener.onEntity(handle);
        }
    }

    /**
     * No-op.
     *
     * @param object
     * @param executeRules
     */
    @Override
    public void retractObject(Object object, boolean executeRules) {
    }

    /**
     * Overriden to prevent the event to get added to the WM.
     *
     * @param func  RuleFunction
     * @param event SimpleEvent
     */
    @Override
    public PreprocessContext preprocessPassthru(RuleFunction func, SimpleEvent event) {
        RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();

        if (threadSession == null) {
            sessManager.setCurrentRuleSession(this);

            try {
                PreprocessContext.newContext(this);

                try {
                    func.invoke(new Object[]{event});
                    event.acknowledge();
                }
                catch (Exception ex) {
                    String msg = getName() + " Got exception [" + ex +
                            "] while executing preprocessor [" + func.getSignature() +
                            "] for event [" + event + "]";

                    this.logger.log(Level.ERROR, ex, msg);
                }
            }
            finally {
                //releaseLocks();
                sessManager.setCurrentRuleSession(null);
                PreprocessContext.cleanContext();
            }

            return null;
        }

        if (threadSession == this) { //already inside
            throw new RuntimeException("Can't invokeFunction inside the session itself");
        } else {
            throw new RuntimeException("Session " + threadSession.getName() +
                    " can't invokeFunction in session " + this.getName());
        }
    }

    /**
     * No-op.
     *
     * @param preprocessContext
     */
    @Override
    public void preprocessPostSchedule(PreprocessContext preprocessContext) {
    }

    //-----------------

    protected static class DummyTimeManager extends BaseTimeManager {
        public void start() {
        }

        public void stop() {
        }

        public void shutdown() {
        }

        public void reset() {
        }

        public Event scheduleOnceOnlyEvent(Event event, long delay) {
            return null;
        }

        public BaseHandle scheduleOnceOnlyEvent2(Event event, long delay) {
            return null;
        }

        public BaseHandle loadScheduleOnceOnlyEvent(Event event, long delay) {
            return null;
        }

        public void scheduleEventExpiry(Handle handle, long expireTTL) {
        }

        public void registerEvent(Class eventClass) {
        }

        public void unregisterEvent(Class eventClass) {
        }

        public void scheduleOnceOnly(TimerTaskOnce timerTask, long time) {
        }

        public void scheduleRepeating(TimerTaskRepeat timerTask, long time, long period) {
        }
    }
}
