package com.tibco.cep.runtime.service.om.impl.mem;


import java.lang.reflect.Constructor;
import java.util.Iterator;

import com.tibco.cep.kernel.core.base.AbstractElementHandle;
import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.base.ElementHandleMap;
import com.tibco.cep.kernel.core.base.EntityHandleMap;
import com.tibco.cep.kernel.core.base.EventHandleMap;
import com.tibco.cep.kernel.core.base.ExtIdMap;
import com.tibco.cep.kernel.core.base.ObjectHandleMap;
import com.tibco.cep.kernel.core.base.PersistenceStatusHolder;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.om.impl.ElementExtHandle;
import com.tibco.cep.runtime.service.om.impl.ElementHandle;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.PreprocessContext;
import com.tibco.cep.runtime.session.impl.RuleSessionConfigImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Oct 13, 2006
 * Time: 6:31:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class InMemoryObjectManager extends BaseObjectManager {

    public InMemoryObjectManager(String name) {
        super(name);
    }

    public void init(WorkingMemory workingMemory) throws Exception {
        m_workingMemory = (WorkingMemoryImpl) workingMemory;
        ExtIdMap extIdMap = null;
        if(Boolean.parseBoolean(System.getProperty("be.engine.kernel.unifiedExtIdMap", "false"))) {
            extIdMap = new ExtIdMap();
        }
        m_eventHandleMap   = new EventHandleMap(m_workingMemory, extIdMap);
        m_elementHandleMap = new ElementHandleMap(m_workingMemory, extIdMap);
        m_entityHandleMap  = new EntityHandleMap(m_workingMemory, extIdMap);
        m_objectHandleMap  = new ObjectHandleMap(m_workingMemory);
        
        if(isConcurrent()) {
        	m_workingMemory.getLogManager().getLogger(getClass()).log(Level.INFO, "--ConcurrentRETE Enabled=true");
        }
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    public void shutdown() throws Exception {
    }

    public void applyChanges(RtcOperationList rtcList) {
    	try {
	        Iterator ite = rtcList.iterator();
	        while(ite.hasNext()) {
	            BaseHandle handle = (BaseHandle) ite.next();
	            Object obj = handle.getObject();
	            if(handle.isRtcDeleted()) {
	                if(obj instanceof SimpleEvent) {
	                    ((SimpleEvent) handle.getObject()).acknowledge();
	                } else if(obj instanceof TimeEvent && !((TimeEvent)obj).isRepeating()) {
	                    if(((RuleSessionConfigImpl)RuleSessionManager.getCurrentRuleSession().getConfig()).removeRefOnDeleteNonRepeatingTimeEvent()) {
	                        //null out the reference in the handle to avoid holding on to this object if the handle is still in a timer queue
	                        ((AbstractEventHandle)handle).removeRef();
	                    }
	                }
	            }
	            if(obj instanceof PersistenceStatusHolder) {
	            	((PersistenceStatusHolder)obj).clearPersistenceModified();
	            }
	            handle.rtcClearAll();
	        }
    	} finally {
    		((RuleSessionImpl)RuleSessionManager.getCurrentRuleSession()).releaseLocks();
    	}
    }
    public Element getNamedInstance(String uri, Class entityClz) {
        RuleSessionImpl ruleSession= (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        Element ni=getElement(uri);
        if (ni == null) {
            long id = ruleSession.getRuleServiceProvider().getIdGenerator().nextEntityId(entityClz);
            try {
                Constructor<Element> cons = entityClz.getConstructor(new Class[] {long.class, String.class});
                ni = cons.newInstance(new Object[] {new Long(id), uri});
                ruleSession.assertObject(ni, false);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
        return ni;
    }

    protected AbstractElementHandle getNewElementHandle(Element obj, AbstractElementHandle _next, TypeInfo _typeInfo) {
        return new ElementHandle(obj, _next, _typeInfo);
    }

    protected AbstractElementHandle getNewElementExtHandle(Element obj, AbstractElementHandle _next, TypeInfo _typeInfo) {
        return new ElementExtHandle(obj, _next, _typeInfo);
    }

    protected AbstractEventHandle getNewEventHandle(Event obj, AbstractEventHandle _next, TypeInfo _typeInfo) {
        return new EventHandleMap.EventHandle(obj, _next, _typeInfo);
    }

    protected AbstractEventHandle getNewEventExtHandle(Event obj, AbstractEventHandle _next, TypeInfo _typeInfo) {
        return new EventHandleMap.EventExtHandle(obj, _next, _typeInfo);
    }

    public boolean isObjectStore() {
        return false;
    }

    @Override
    protected Element getElementFromPreprocess(long id) {
        return PreprocessContext.getElementFromPreprocess(id);
    }
    @Override
    protected Event getEventFromPreprocess(long id) {
        return PreprocessContext.getEventFromPreprocess(id);
    }
    @Override
    protected Element getElementFromPreprocess(String extId) {
    	return PreprocessContext.getElementFromPreprocess(extId);
    }
    @Override
    protected Event getEventFromPreprocess(String extId) {
    	return PreprocessContext.getEventFromPreprocess(extId);
    }
}