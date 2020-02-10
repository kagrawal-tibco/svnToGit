package com.tibco.cep.kernel.core.base;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.rete.CompositeAction;
import com.tibco.cep.kernel.core.rete.CompositeActionWrapper;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.*;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.ExceptionHandler;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.TimeManager;
import com.tibco.cep.kernel.service.impl.DefaultExceptionHandler;
import com.tibco.cep.kernel.service.impl.DefaultObjectManager;
import com.tibco.cep.kernel.service.impl.DefaultTimeManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.Logger;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 17, 2006
 * Time: 5:40:52 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class WorkingMemoryImpl implements WorkingMemoryEx {

    // statistic info
    static private int currentId = 0;

     private int getNextId() {
        int retId = currentId++;
        return retId;
    }

    final protected int m_id;
    final protected String m_name;
    final protected LogManager m_logManager;
    private Logger  m_logger;
    final protected ExceptionHandler m_expHandler;
    final protected RuleLoader m_ruleLoader;
    final protected BaseTimeManager m_timeManager;
    private Object m_opList = null;
    private Object m_rtcOpList = null;
    final protected BaseObjectManager m_objectManager;
    final protected HashSet m_ruleTobeApplied;

    final protected List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();
    protected boolean m_shutdown = false;
    protected boolean isMultiEngineMode=false;
    protected boolean isConcurrent=false;

    protected final ConcurrentHashMap<String, AtomicLong> numberOfRulesFired;

    static protected ThreadLocal<WorkingMemory> current = new ThreadLocal<WorkingMemory>();
    static protected ThreadLocal currentContext = new ThreadLocal();

    //methods for RuleLoader
//    abstract protected Rule addRule(Rule rule) throws SetupException;

//    abstract protected Rule removeRule(Rule rule) throws SetupException;

    abstract public void applyObjectToAddedRules();

    abstract public void loadObjectToAddedRule();

    //methods for OperationList
    abstract protected Handle assertHandleInternal(BaseHandle handle);

    abstract protected Handle assertHandleInternal(BaseHandle handle, boolean forwardChain);

    protected boolean modifyHandleInternal(BaseHandle handle, int[] overrideDirtyBitMap, boolean recordThisModification) {
        return modifyHandleInternal(handle, overrideDirtyBitMap, recordThisModification, true);
    }

    abstract protected boolean modifyHandleInternal(BaseHandle handle, int[] overrideDirtyBitMap, boolean recordThisModification,boolean forwardChain );

    abstract protected boolean retractHandleInternal(BaseHandle handle);

    abstract protected boolean retractHandleInternal(BaseHandle handle, boolean forwardChain);

    abstract protected boolean cleanupHandleInternal(BaseHandle handle);

    abstract protected void eventExpiryActions(Event event);

    //methods for TimeManager
    abstract public void expireEvent(AbstractEventHandle handle);

    abstract public void fireRepeatEvent(Event repeatEvent, int times) throws DuplicateExtIdException;

    //method for ObjectManager
    abstract public TypeInfo getTypeInfo(Class objectClass);

    abstract public void registerType(Class objectType);

    abstract public Handle loadObject(Object obj) throws DuplicateExtIdException, DuplicateException;

    abstract public Handle loadScheduleEvent(Event evt, long delay);

    abstract public Handle reloadObject(Object obj) throws DuplicateExtIdException, DuplicateException;

//    abstract public boolean unloadObject(Object obj);
//    abstract public boolean unloadElement(long id);
//    abstract public boolean unloadEvent(long id);

     abstract public void modifyElement(Element element, int[] dirtyBitArray, boolean recordThis) throws DuplicateExtIdException;

//    abstract public void executeRules(String description, List modifyElements, List dirtyBitMap,
//                                      List deletedObjects, List reloaded,
//                                      List reevaluateObjects) throws DuplicateExtIdException;

//    abstract public Handle loadEvictedHandle(long id, String extId, Class type) throws DuplicateExtIdException, DuplicateException;

    abstract public void evictHandle(BaseHandle handle);

    abstract public void reevaluateElements(long[] ids);

    abstract public void reevaluateElements(Collection IDs);

    abstract public void reevaluateEvents(Collection IDs);

    abstract public Object[][] findMatches(Rule rule, Object[] argument, boolean executeRuleAction, boolean dirtyRead);

    protected WorkingMemoryImpl(String name, LogManager logManager, ExceptionHandler expHandler,
                                BaseObjectManager objectManager, BaseTimeManager timeManager, boolean isMultiEngineMode, boolean isConcurrent) {
        m_id = getNextId();

        if (name != null && name.length() > 0) {
            m_name = name;
        } else {
            m_name = "WorkingMemory_" + m_id;
        }

        this.m_logManager = logManager;
        this.m_logger = this.m_logManager.getLogger(WorkingMemoryImpl.class);
        this.numberOfRulesFired = new ConcurrentHashMap<String, AtomicLong>();

        if (expHandler == null) {
            m_expHandler = new DefaultExceptionHandler(this.m_logger);
        } else {
            m_expHandler = expHandler;
        }

        m_ruleLoader = new RuleLoaderImpl(this);

        if (objectManager == null) {
            m_objectManager = new DefaultObjectManager(name);
        } else {
            m_objectManager = objectManager;
        }

        if (timeManager == null) {
            m_timeManager = new DefaultTimeManager(null);
        } else {
            m_timeManager = timeManager;
        }

        m_timeManager.init(this);

        m_ruleTobeApplied = new HashSet();

        this.isConcurrent = isConcurrent;
        if (this.isConcurrent) {
            m_opList = new ThreadLocal();
            m_rtcOpList = new ThreadLocal();
        } else {
            m_opList = new OperationList(this, m_objectManager);
            m_rtcOpList = new RtcOperationList(isMultiEngineMode);
        }
    }

    /**
     *
     * @return
     */
    public Collection<ChangeListener> getChangeListeners() {
        return Collections.unmodifiableList(changeListeners);
    }

    /**
     * Add a new change listener to get rete change notifications.
     * @param changeListener_
     * @return
     */
    public boolean addChangeListener(ChangeListener changeListener_) {
        return changeListeners.add(changeListener_);
    }

    public void removeAllChangeListeners() {
        changeListeners.clear();
    }

    public boolean removeChangeListener(ChangeListener changeListener_) {
        return changeListeners.remove(changeListener_);
    }

    public ExecutionContext getCurrentContext() {
        return (ExecutionContext) currentContext.get();
    }

    public void setCurrentContext(ExecutionContext obj) {
        currentContext.set(obj);
    }

    protected void resetCurrentContext() {
        currentContext.set(null);
    }

    public int getId() {
        return m_id;
    }

    public String getName() {
        return m_name;
    }

    public LogManager getLogManager() {
        return m_logManager;
    }

    public RuleLoader getRuleLoader() {
        return m_ruleLoader;
    }

    public TimeManager getTimeManager() {
        return m_timeManager;
    }

    public ObjectManager getObjectManager() {
        return m_objectManager;
    }

    public long getTotalNumberRulesFired() {
    	long count = 0;
    	for (Entry<String, AtomicLong> entry : numberOfRulesFired.entrySet()) {
    		count += entry.getValue().get();
    	}
        return count;
    }
    
    @Override
    public long getNumberOfRulesFired(String ruleUri) {
    	if (ruleUri == null) {
    		return 0;
    	}
    	AtomicLong count = numberOfRulesFired.get(ruleUri);
        return count == null ? 0 : count.get();
    }

    public void resetTotalNumberRulesFired() {
    	numberOfRulesFired.clear();
    }
    
    protected void incrementRuleFiredCount(String ruleUri) {
    	if (ruleUri == null) {
    		return;
    	}
    	AtomicLong currentCount = numberOfRulesFired.get(ruleUri);
    	if (currentCount == null) {
    		numberOfRulesFired.putIfAbsent(ruleUri, new AtomicLong());
    		currentCount = numberOfRulesFired.get(ruleUri);
    	}
    	currentCount.incrementAndGet();
    }

    public String toString() {
        return m_name;
    }

    protected void preActivate() {
        List eventHandles = m_objectManager.getTimerFiredEventHandles();
        Iterator ite = eventHandles.iterator();
        while(ite.hasNext()) {
            AbstractEventHandle h = (AbstractEventHandle) ite.next();
            this.expireEvent(h);
        }
    }

    //delegate directly to BaseTimeManager - used in ReteWM, subclass of WorkingMemoryImpl
    protected void registerEvent(Class eventClass) {
        m_timeManager.registerEvent(eventClass);
    }

    protected void unregisterEvent(Class eventClass) {
        m_timeManager.unregisterEvent(eventClass);
    }

    //delegate directly to BaseObjectManager - used in ReteWM, subclass of WorkingMemoryImpl
    protected BaseHandle addHandle(Object object) throws DuplicateExtIdException, DuplicateException {
        return m_objectManager.addHandle(object);
    }

    protected BaseHandle getHandle(Object object) {
        return m_objectManager.getHandle(object);
    }

    protected AbstractElementHandle getElementHandle(Element element) {
        return m_objectManager.getElementHandle(element);
    }

    protected AbstractElementHandle getElementHandle(long id) {
        return (AbstractElementHandle) m_objectManager.getElementHandle(id);
    }

    protected AbstractEventHandle getEventHandle(Event event) {
        return m_objectManager.getEventHandle(event);
    }

    protected AbstractEventHandle getEventHandle(long id) {
        return (AbstractEventHandle) m_objectManager.getEventHandle(id);
    }

    protected EntityHandleMap.EntityHandle getEntityHandle(Entity entity) {
        return m_objectManager.getEntityHandle(entity);
    }

    protected EntityHandleMap.EntityHandle getEntityHandle(long id) {
        return (EntityHandleMap.EntityHandle) m_objectManager.getEntityHandle(id);
    }

    protected BaseHandle getObjectHandle(Object object) {
        return m_objectManager.getObjectHandle(object);
    }

    protected BaseHandle getAddHandle(Object object) throws DuplicateExtIdException {
        return m_objectManager.getAddHandle(object);
    }

    protected BaseHandle getAddElementHandle(Element element) throws DuplicateExtIdException {
        return m_objectManager.getAddElementHandle(element);
    }

    protected BaseHandle getAddEventHandle(Event event) throws DuplicateExtIdException {
        return m_objectManager.getAddEventHandle(event);
    }

    protected BaseHandle getAddEntityHandle(Entity entity) throws DuplicateExtIdException {
        return m_objectManager.getAddEntityHandle(entity);
    }

    protected BaseHandle getAddObjectHandle(Object object) {
        return m_objectManager.getAddObjectHandle(object);
    }

    protected void removeElementHandle(AbstractElementHandle handle) {
        m_objectManager.removeElementHandle(handle);
    }

    protected void removeEventHandle(AbstractEventHandle handle) {
        m_objectManager.removeEventHandle(handle);
    }

    protected EntityHandleMap.EntityHandle removeEntityHandle(long id) {
        return m_objectManager.removeEntityHandle(id);
    }

    protected ObjectHandleMap.ObjectHandle removeObjectHandle(Object object) {
        return m_objectManager.removeObjectHandle(object);
    }

    protected Iterator getEventHandleIterator() {
        return m_objectManager.getEventHandleIterator();
    }

    protected Iterator getElementHandleIterator() {
        return m_objectManager.getElementHandleIterator();
    }

    protected Iterator getEntityHandleIterator() {
        return m_objectManager.getEntityHandleIterator();
    }

    protected Iterator getObjectHandleIterator() {
        return m_objectManager.getObjectHandleIterator();
    }

    protected Iterator getNamedInstanceIterator() {
        return m_objectManager.getNamedInstanceIterator();
    }

    static public boolean executingInside(WorkingMemory wm) {
        return current.get() == wm;
    }

    protected void processRecorded() {
        if (m_shutdown) return;

        if (m_logger.isEnabledFor(Level.DEBUG)) {
            if (getRtcOpList().isEmpty() == false) {
                m_logger.log(Level.DEBUG,"RTC Operations: " + getRtcOpList().toString());
            }
        }
        m_objectManager.applyChanges(getRtcOpList());
        getRtcOpList().reset();

       //memoryDump();
    }

    public void memoryDump() {
        if (m_logger.isEnabledFor(Level.DEBUG)) {
            m_logger.log(Level.DEBUG,
                    new StringBuffer(Format.BRK)
                            .append("************************** MEMORY DUMP **************************")
                            .append(Format.BRK)
                            .append(getMemoryDump())
                            .append("*****************************************************************")
                            .toString());
        }
    }

    public StringBuffer getMemoryDump() {
        final StringBuffer sb = new StringBuffer();
        if (m_objectManager.m_elementHandleMap.size() > 0) {
            sb.append(m_objectManager.m_elementHandleMap.contentToString());
        }
        if (m_objectManager.m_eventHandleMap.size() > 0) {
            sb.append(m_objectManager.m_eventHandleMap.contentToString());
        }
        if (m_objectManager.m_entityHandleMap.size() > 0) {
            sb.append(m_objectManager.m_entityHandleMap.contentToString());
        }
        if (m_objectManager.m_objectHandleMap.size() > 0) {
            sb.append(m_objectManager.m_objectHandleMap.contentToString());
        }
        sb.append(JoinTable.memoryDump(true));
        return sb;
    }

    /*
    static public void recordAsserted(BaseHandle handle) {
        if(handle == null) return;
        WorkingMemoryImpl wm = (WorkingMemoryImpl)current.get();
        if(wm != null) {
            wm.m_rtcOpList.setRtcAsserted(handle);
        }
    }

    static public void recordModified(BaseHandle handle) {
        if(handle == null) return;
        WorkingMemoryImpl wm = (WorkingMemoryImpl)current.get();
        if(wm != null) {
            wm.m_rtcOpList.setRtcModified(handle);
        }
    }

    static public void recordRetracted(BaseHandle handle) {
        if(handle == null) return;
        WorkingMemoryImpl wm = (WorkingMemoryImpl)current.get();
        if(wm != null) {
            wm.m_rtcOpList.setRtcDeleted(handle);
        }
    }*/

    public void recordAsserted(BaseHandle handle) {
        getRtcOpList().setRtcAsserted(handle);
        for (ChangeListener changeListener : changeListeners) {
            changeListener.asserted(handle.getObject(), getCurrentContext());
        }
    }

    protected void recordModified(BaseHandle handle) {
        getRtcOpList().setRtcModified(handle);
        for (ChangeListener changeListener : changeListeners) {
            changeListener.modified(handle.getObject(), getCurrentContext());
        }
    }

    protected void recordRetracted(BaseHandle handle) {
        getRtcOpList().setRtcDeleted(handle);
        ExecutionContext context = getCurrentContext();
//        if (changeListener != null) {
//
//            if (context == null)
//                changeListener.eventExpired((Event)handle.getObject());
//            else
//                changeListener.retracted(handle.getObject(), context);
//        }
        if (context == null) {
            for (ChangeListener changeListener : changeListeners) {
            	if(handle.getObject() instanceof Event) {
            		changeListener.eventExpired((Event)handle.getObject());
            	} else if(handle.getObject() instanceof Element) {
            		changeListener.retracted(handle.getObject(), context);
            	}
            }
        } else {
            for (ChangeListener changeListener : changeListeners) {
                changeListener.retracted(handle.getObject(), context);
            }
        }
    }

    public void recordTouchHandle(BaseHandle handle) {
        getRtcOpList().setRtcTouchHandle(handle);
    }

    public void recordScheduled(BaseHandle handle) {
        getGuard().lock();
        try {
            getRtcOpList().setRtcScheduled(handle);
            for (ChangeListener changeListener : changeListeners) {
                changeListener.scheduledTimeEvent((Event) handle.getObject(), getCurrentContext());
            }
        } finally {
            getGuard().unlock();
        }
    }

    static public void recordReverseRef(BaseHandle handle) {
    	if(handle != null) {
		    WorkingMemoryImpl wm = (WorkingMemoryImpl) current.get();
		    if (wm != null) {
		        wm.getRtcOpList().setRtcReverseRef(handle);
		    }
    	}
    }

    static public void recordContainerRef(BaseHandle handle) {
    	if(handle != null) {
	        WorkingMemoryImpl wm = (WorkingMemoryImpl) current.get();
	        if (wm != null) {
	            wm.getRtcOpList().setRtcContainerRef(handle);
	        }
    	}
    }

    static public void recordStateChanged(BaseHandle handle) {
        if (handle == null) {
            throw new RuntimeException("PROGRAM ERROR: handle can't be null");
        }
        WorkingMemoryImpl wm = (WorkingMemoryImpl) current.get();
        if (wm != null) {
            wm.getRtcOpList().setRtcStateChanged(handle);
        } else {
            throw new RuntimeException("PROGRAM ERROR: can't record state changed outside the working memory");
        }
    }

    public OperationList getOpList() {
        if (m_opList instanceof ThreadLocal) {
            OperationList op= (OperationList) ((ThreadLocal) m_opList).get();
            if (op == null) {
                op = new OperationList(this, m_objectManager);
                ((ThreadLocal)m_opList).set(op);
            }
            return op;
        } else {
            return (OperationList) m_opList;
        }
    }

    public RtcOperationList getRtcOpList() {
        if (m_rtcOpList instanceof ThreadLocal) {
            RtcOperationList op= (RtcOperationList) ((ThreadLocal)m_rtcOpList).get();
            if (op == null) {
                op = new RtcOperationList(isMultiEngineMode);
                ((ThreadLocal)m_rtcOpList).set(op);
            }
            return op;
        } else {
            return (RtcOperationList) m_rtcOpList;
        }
    }
    protected abstract boolean stateChangeInternal(BaseHandle handle, int index);

    public abstract void initEntitySharingLevels() throws SetupException;
    
    public void handleException(Exception exp, String message) {
        m_expHandler.handleException(exp, message);
    }
    
    public boolean isConcurrent() {
    	return isConcurrent;
    }

    public static void setCurrentWorkingMemory(WorkingMemory wm) {
        current.set(wm);
    }
    
    public void init(CompositeAction startup, Action activation, Set rules) throws Exception {
    	init(new CompositeActionWrapper(startup), activation, rules);
    }
    
    public void stopAndShutdown(CompositeAction shutdown) throws Exception {
    	stopAndShutdown(new CompositeActionWrapper(shutdown));
    }
}
