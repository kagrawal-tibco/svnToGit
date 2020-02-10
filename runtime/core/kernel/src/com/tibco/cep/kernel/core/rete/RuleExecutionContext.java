package com.tibco.cep.kernel.core.rete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.base.OperationList;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.rete.conflict.ConflictResolver;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.RuleFunction;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Dec 23, 2008
 * Time: 8:41:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class RuleExecutionContext {
    ConflictResolver resolver;
    ArrayList added = new ArrayList();
    ArrayList retracted = new ArrayList();
    ArrayList reloaded= new ArrayList();
    HashMap modified = new HashMap();
    ArrayList <Long>cleanup_elements = new ArrayList<Long>();
    ArrayList <Long>cleanup_events   = new ArrayList<Long>();
    HashMap invokeFunctions = new HashMap();
    HashMap fireTimeEvents = new HashMap();

    ArrayList expiries = new ArrayList();
    static ConcurrentHashMap s_contexts = new ConcurrentHashMap();
    HashMap handleMaps = new HashMap();
    HashMap objectMaps = new HashMap();
    OperationList m_opList=null;
    RtcOperationList m_rtcOpList=null;

    String description;
    public RuleExecutionContext() {
        resolver=ReteWM.createConflictResolver();
    }

    public void add(Object object) {
        added.add(object);
    }

    public void retract(Object object) {
        retracted.add(object);
    }

    public void modify(Object object, int[] modifiedBits) {
        modified.put(object, modifiedBits);
    }

    public void expire(Object object) {
        expiries.add(object);
    }

    public void reload(Object object) {
        reloaded.add(object);
    }

    public void cleanupElement(long id) {
        cleanup_elements.add(id);
    }

    public void cleanupEvent(long id) {
        cleanup_events.add(id);
    }

    public void fireRepeatTimeEvent(Event event, int times) {
        fireTimeEvents.put(event, times);
    }

    public void invokeFunction(RuleFunction rf, Object [] args) {
        invokeFunctions.put(rf, args);
    }
    public void copy(java.util.List  added, java.util.List deleted, java.util.List reloaded) {
        this.added.addAll(added);
        this.retracted.addAll(deleted);
        this.reloaded.addAll(reloaded);
    }

    public void reset() {
        added.clear();
        retracted.clear();
        reloaded.clear();
        modified.clear();
        cleanup_elements.clear();
        cleanup_events.clear();
        invokeFunctions.clear();
        expiries.clear();
    }

    /**
     *
     * @return
     */
    public ConflictResolver getResolver() {
        return resolver;
    }

    /**
     *
     * @return
     */
    public static RuleExecutionContext getRuleExecutionContext() {
        return (RuleExecutionContext) s_contexts.get(Thread.currentThread());
    }

    /**
     *
     * @return
     */
    public synchronized static RuleExecutionContext setRuleExecutionContext() {
        RuleExecutionContext ctx=getRuleExecutionContext();
        if (getRuleExecutionContext() == null) {
            ctx= new RuleExecutionContext();
            s_contexts.put(Thread.currentThread(), ctx);
        }
        return ctx;
    }


    protected Handle[] getCurrentHandleMap(Object obj, int size) {
        Handle [] ret= (Handle[]) handleMaps.get(obj);
        if (ret == null) {
            ret = new Handle[size];
            handleMaps.put(obj, ret);
        }
        return ret;
    }

    protected Object[] getCurrentObjectMap(Object obj, int size) {
        Object [] ret= (Object[]) objectMaps.get(obj);
        if (ret == null) {
            ret = new Object[size];
            handleMaps.put(obj, ret);
        }
        return ret;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    protected java.util.Iterator getAsserted() {
        if (added.size() > 0)
            return added.iterator();
        else
            return null;
    }

    protected java.util.Iterator getRetracted() {
        if (retracted.size() > 0)
            return retracted.iterator();
        else
            return null;
    }

    protected java.util.Iterator getReloaded() {
        if (reloaded.size() > 0)
            return reloaded.iterator();
        else
            return null;
    }

    protected java.util.Iterator getModified() {
        if (modified.size() > 0)
            return modified.entrySet().iterator();
        else
            return null;
    }

    protected java.util.Iterator getCleanupElements() {
        if (cleanup_elements.size() > 0)
            return cleanup_elements.iterator();
        else
            return null;
    }

    protected java.util.Iterator getCleanupEvents() {
        if (cleanup_events.size() > 0)
            return cleanup_events.iterator();
        else
            return null;
    }

    protected java.util.Iterator getExpiries() {
        if (expiries.size() > 0)
            return expiries.iterator();
        else
            return null;
    }

    protected java.util.Iterator getInvokeFunctions() {
        if (invokeFunctions.size() > 0)
            return invokeFunctions.entrySet().iterator();
        else
            return null;
    }

    protected java.util.Iterator getRepeatTimeEvents() {
        if (fireTimeEvents.size() > 0)
            return fireTimeEvents.entrySet().iterator();
        else
            return null;
    }

    public OperationList getOpList(WorkingMemoryImpl wm, BaseObjectManager objectManager) {
        if (m_opList == null) {
            m_opList=new OperationList(wm, objectManager);
        }
        return m_opList;
    }

    public RtcOperationList getRtcOpList(boolean isMultiEngineMode) {
        if (m_rtcOpList==null) {
            m_rtcOpList=new RtcOperationList(isMultiEngineMode);
        }
        return m_rtcOpList;
    }

}
