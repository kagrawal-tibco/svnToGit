package com.tibco.cep.bpmn.runtime.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.base.BaseTimeManager;
import com.tibco.cep.kernel.core.rete.ReteListener;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.core.rete.conflict.ConflictResolver;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.service.ExceptionHandler;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.session.RuleSessionManager;


public class ProcessWM extends ReteWM
{

    static final Logger logger =  LogManagerFactory.getLogManager().getLogger(ProcessWM.class);

    public ProcessWM(String name, LogManager logManager, ExceptionHandler expHandler,
                     BaseObjectManager objectManager, BaseTimeManager timeManager,
                     ConflictResolver resolver, boolean isMultiEngineMode, boolean isConcurrent) {
        super(name, logManager, expHandler, objectManager, timeManager, resolver, isMultiEngineMode, isConcurrent);
        this.activeModeOn = true;
    }

    private static void clearInRete(Collection list) {
        if(list == null) return;
        for(Object o : list) {
            clearInRete(o);
        }
    }

    private static void clearInRete(Object o) {
        if(o instanceof ConceptImpl) {
            ConceptImpl c = (ConceptImpl)o;
            clearInRete(c);
        } else if (o instanceof BaseHandle) {
            BaseHandle b = (BaseHandle)o;
            clearInRete(b);
        }
    }
    private static void clearInRete(ConceptImpl c) {
        c.clearInRete();
    }
    private static void clearInRete(BaseHandle b) {
        b.clearInRete();
    }

    private void setTypeInfo(BaseHandle handle) {
        //if (handle.getTypeInfo() != null) return;
        Object o = handle.getObject();
        assert o != null : "ProcessWM.setTypeInfo : this shouldn't happen";
        if (o != null) {
            TypeInfo typeInfo = getTypeInfo(o.getClass());
            if (typeInfo == null) {
                System.out.println("TypeInfo null : " + o.getClass());
            }
         handle.setTypeInfo(getTypeInfo(o.getClass()));
        }
    }

    @Override
    protected void putInRete(BaseHandle handle) {
        if(handle.isInRete()) {
            //throw new RuntimeException("Handle " + handle + " already put in Rete");
        }
        //if no typeinfo that means no class nodes, so no rules will fire
        //skip it to avoid a null ponter exception
        else if (handle.getTypeInfo() != null) {
            m_rete.assertObject(handle);
            handle.setInRete();
        }
    }

    public void executeRules(String description, List reloadFromCacheObjects, List assertHandles,
                             Map<String, Set<Job.PendingEvent>> pendingEvents, List reevaluateObjects) throws Exception
    {
        WorkingMemory wm = current.get();
        if(wm == this) {
            throw new Exception("Can't executeRules inside the WorkingMemory itself");
        }
        else if (wm == null) {
            current.set(this);
            getGuard().lock();
            try {
                //setActiveMode(true);
                ((ProcessRuleSession)RuleSessionManager.getCurrentRuleSession()).setThreadLocalWorkingMemory(this);
                //synchronized(this) {

                if(m_shutdown) return;
                if(m_reteListener != null)
                    m_reteListener.rtcStart(ReteListener.RTC_POST_PROCESS, description);

                clearInRete(reloadFromCacheObjects);
                clearInRete(assertHandles);

                HashSet<Event> events = new HashSet<Event>();
                for(Set<Job.PendingEvent> pendingEventSet : pendingEvents.values()) {

                    for (Job.PendingEvent pe : pendingEventSet) {

                        if (pe.getState() != Job.PendingEventState.CONSUMED) events.add(pe.getEvent());
                    }
                }
                clearInRete(events);
                clearInRete(reevaluateObjects);

                ConflictResolver resolver=getResolver();
                try {
                    ArrayList handles2Assert= new ArrayList();
                    ArrayList handles2Load = new ArrayList();
                    ArrayList handles2reevaluate = new ArrayList();

                    if (reloadFromCacheObjects != null) {
                        Iterator ite = reloadFromCacheObjects.iterator();
                        while(ite.hasNext()) {
                            Object obj= ite.next();
                            BaseHandle handle =null;
                            if (obj instanceof BaseHandle) {
                                handle = (BaseHandle) obj;
                                clearInRete(handle);
                            } else {
                                clearInRete(obj);
                                handle =m_objectManager.getHandle(obj);
                            }
                            if (handle != null) {
                                if(handle.isInRete()|| handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
                                    continue;
                                }
                                setTypeInfo(handle);
//    							moveRTCBitsToHandle(obj, handle);
                                handles2Load.add(handle);
                            }
                        }
                    }

                    if (handles2Load.size() > 0) {
                        reloadHandleInternal(handles2Load);
                    }

                    if (assertHandles != null) {
                        Iterator ite = assertHandles.iterator();
                        while(ite.hasNext()) {
                            doAssertHandle(ite.next(), handles2reevaluate, handles2Assert);
                        }
                    }

                    if(events != null) {

                        for(Event e: events) {
                            doAssertHandle(e, handles2reevaluate, handles2Assert);
                        }

                    }

                    for (Object handle : handles2Assert) {
                        assertHandleInternal((BaseHandle) handle);
                    }

                    if (reevaluateObjects != null) {
                        Iterator ite = reevaluateObjects.iterator();
                        while(ite.hasNext()) {
                            Object obj= ite.next();
                            BaseHandle handle =null;
                            if (obj instanceof BaseHandle) {
                                handle = (BaseHandle) obj;
                            } else {
                                handle =m_objectManager.getAddHandle(obj);
                            }
                            if(handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
                                continue;
                            }
                            setTypeInfo(handle);
                            handles2reevaluate.add(handle);
                        }
                    }

                    for (Object handle : handles2reevaluate) {
                        reevaluateHandleInternal((BaseHandle) handle);
                    }

                    resolveConflict(resolver);
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    current.set(null);
                    if(m_reteListener != null) {
                        m_reteListener.rtcResolved();
                        processRecorded();  //apply
                        m_reteListener.rtcEnd();
                    }
                    else {
                        processRecorded();  //apply
                    }
                }
            } finally {
                current.set(null);
                getGuard().unlock();
                setActiveMode(false);
                ((ProcessRuleSession)RuleSessionManager.getCurrentRuleSession()).setThreadLocalWorkingMemory(null);
            }
        }
        else {
            throw new Exception("WorkingMemory " + getName() + " can't executeRules in WorkingMemory " + this.getName());
        }
    }

    protected void doAssertHandle(Object obj, List handles2reevaluate, List handles2Assert) throws DuplicateExtIdException {
        BaseHandle handle =null;
        if (obj instanceof BaseHandle) {
            handle = (BaseHandle) obj;
        } else {
            handle =m_objectManager.getAddHandle(obj);
        }
        if(handle.isInRete()|| handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
            return;
        }
        setTypeInfo(handle);
        /* for inference task, want to always fire rules on the objects so put all as asserted */
//		if(handle.isAsserted()) {
//			handles2reevaluate.add(handle);
//		} else {
        handles2Assert.add(handle);
//		}
    }

    public void executeRules(String description, Job job) throws Exception  {

        WorkingMemory wm = current.get();
        if(wm == this) {
            throw new Exception("Can't executeRules inside the WorkingMemory itself");
        }
        if (wm != null) {
            throw new Exception(String.format("WorkingMemory %s can't executeRules in WorkingMemory " , wm.getName(),  this.getName()));
        }

        current.set(this);
        getGuard().lock();

        JobImpl jobImpl = (JobImpl) job;
        Collection reloadFromCacheObjects = new ArrayList<Concept> ();
        Collection assertHandles = new ArrayList<Concept> ();
        Collection<Event> events = jobImpl.getAllPendingEvents();
        Collection reevaluateObjects = new ArrayList();
        jobImpl.entitiesForRuleEvaluation(assertHandles, reloadFromCacheObjects, reevaluateObjects);

        logger.log(Level.INFO, String.format("[WM:%s %s]", getName(), stringify(assertHandles)));
        try {

            //setActiveMode(true);
            ((ProcessRuleSession)RuleSessionManager.getCurrentRuleSession()).setThreadLocalWorkingMemory(this);
            //synchronized(this) {

            if(m_shutdown) return;
            if(m_reteListener != null)
                m_reteListener.rtcStart(ReteListener.RTC_POST_PROCESS, description);

            clearInRete(reloadFromCacheObjects);
            clearInRete(assertHandles);


            clearInRete(events);
            clearInRete(reevaluateObjects);

            ConflictResolver resolver=getResolver();
            try {
                ArrayList handles2Assert= new ArrayList();
                ArrayList handles2Load = new ArrayList();
                ArrayList handles2reevaluate = new ArrayList();

                if (reloadFromCacheObjects != null) {
                    Iterator ite = reloadFromCacheObjects.iterator();
                    while(ite.hasNext()) {
                        Object obj= ite.next();
                        BaseHandle handle =null;
                        if (obj instanceof BaseHandle) {
                            handle = (BaseHandle) obj;
                            clearInRete(handle);
                        } else {
                            clearInRete(obj);
                            handle =m_objectManager.getHandle(obj);
                        }
                        if (handle != null) {
                            if(handle.isInRete()|| handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
                                continue;
                            }
                            setTypeInfo(handle);
//    							moveRTCBitsToHandle(obj, handle);
                            handles2Load.add(handle);
                        }
                    }
                }

                if (handles2Load.size() > 0) {
                    reloadHandleInternal(handles2Load);
                }

                if (assertHandles != null) {
                    Iterator ite = assertHandles.iterator();
                    while(ite.hasNext()) {
                        doAssertHandle(ite.next(), handles2reevaluate, handles2Assert);
                    }
                }

                if(events != null) {

                    for(Event e: events) {
                        doAssertHandle(e, handles2reevaluate, handles2Assert);
                    }

                }

                for (Object handle : handles2Assert) {
                    assertHandleInternal((BaseHandle) handle);
                }

                if (reevaluateObjects != null) {
                    Iterator ite = reevaluateObjects.iterator();
                    while(ite.hasNext()) {
                        Object obj= ite.next();
                        BaseHandle handle =null;
                        if (obj instanceof BaseHandle) {
                            handle = (BaseHandle) obj;
                        } else {
                            handle =m_objectManager.getAddHandle(obj);
                        }
                        if(handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
                            continue;
                        }
                        setTypeInfo(handle);
                        handles2reevaluate.add(handle);
                    }
                }

                for (Object handle : handles2reevaluate) {
                    reevaluateHandleInternal((BaseHandle) handle);
                }

                resolveConflict(resolver);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            finally {
                current.set(null);
                if(m_reteListener != null) {
                    m_reteListener.rtcResolved();
                    processRecorded();  //apply
                    m_reteListener.rtcEnd();
                }
                else {
                    processRecorded();  //apply
                }
            }

        }
        finally {
            current.set(null);
            getGuard().unlock();
            ((ProcessRuleSession)RuleSessionManager.getCurrentRuleSession()).setThreadLocalWorkingMemory(null);
        }



    }

    private String stringify(Collection<Entity> assertHandles) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Assert size:%d", assertHandles.size()));
        builder.append("{");
        for (Entity en : assertHandles) {
            builder.append(String.format("(%s,%d,%s)", en.getClass().getSimpleName(), en.getId(), en.getExtId()));
        }
        builder.append("}");
        return builder.toString();
    }
}
