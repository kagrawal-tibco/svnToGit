/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.kernel.core.base;

import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 6, 2008
 * Time: 1:58:29 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class RemoteObjectManager extends BaseObjectManager {
    public RemoteObjectManager(String name) {
        super(name);
    }

    public void init(WorkingMemory workingMemory) throws Exception {
        m_workingMemory = (WorkingMemoryImpl) workingMemory;
        ExtIdMap extIdMap = null;
        if(Boolean.parseBoolean(System.getProperty("be.engine.kernel.unifiedExtIdMap", "false"))) {
            extIdMap = new ExtIdMap();
        }
        m_eventHandleMap = new EventHandleMap(m_workingMemory, extIdMap);
        m_elementHandleMap = new ElementHandleMap(m_workingMemory, extIdMap);
        m_entityHandleMap = new EntityHandleMap(m_workingMemory, extIdMap);
        m_objectHandleMap = new ObjectHandleMap(m_workingMemory);
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    public void shutdown() throws Exception {
    }


    public AbstractElementHandle getRemoteAddElementHandle(String clusterName, Element element) throws DuplicateExtIdException {
        AbstractElementHandle h = m_elementHandleMap.getRemoteAdd(clusterName, element);
        return h;
    }

//    public BaseHandle getRemoteElementHandle(String clusterName, String extId) {
//        return getElementHandle(clusterName + "$" + extId);
//    }

    abstract protected AbstractElementHandle getRemoteNewElementExtHandle(String clusterName, Element obj, AbstractElementHandle _next);


    abstract protected AbstractElementHandle getRemoteNewElementHandle(String clusterName, Element obj, AbstractElementHandle _next);
}
