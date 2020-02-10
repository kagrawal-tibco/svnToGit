package com.tibco.cep.kernel.service.impl;

import com.tibco.cep.kernel.core.base.AbstractElementHandle;
import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.base.ElementHandleMap;
import com.tibco.cep.kernel.core.base.EntityHandleMap;
import com.tibco.cep.kernel.core.base.EventHandleMap;
import com.tibco.cep.kernel.core.base.ExtIdMap;
import com.tibco.cep.kernel.core.base.ObjectHandleMap;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Oct 26, 2006
 * Time: 4:26:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultObjectManager extends BaseObjectManager {
    
    public DefaultObjectManager(String name) {
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
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    public void shutdown() throws Exception {
    }

    public void applyChanges(RtcOperationList rtcList) {
        rtcList.reset();
    }

    protected AbstractElementHandle getNewElementHandle(Element obj, AbstractElementHandle _next, TypeInfo _typeInfo) {
        return new ElementHandleMap.ElementHandle(obj, _next, _typeInfo);
    }

    protected AbstractElementHandle getNewElementExtHandle(Element obj, AbstractElementHandle _next, TypeInfo _typeInfo) {
        return new ElementHandleMap.ElementExtHandle(obj, _next, _typeInfo);
    }

    protected AbstractEventHandle getNewEventHandle(Event obj, AbstractEventHandle _next, TypeInfo _typeInfo) {
        return new EventHandleMap.EventHandle(obj, _next, _typeInfo);
    }

    protected AbstractEventHandle getNewEventExtHandle(Event obj, AbstractEventHandle _next, TypeInfo _typeInfo) {
        return new EventHandleMap.EventExtHandle(obj, _next, _typeInfo);
    }

    public boolean isObjectStore() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}