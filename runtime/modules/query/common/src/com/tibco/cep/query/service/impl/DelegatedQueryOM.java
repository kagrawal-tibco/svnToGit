/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.query.service.impl;


import java.util.Collection;
import java.util.LinkedList;

import com.tibco.cep.kernel.core.base.AbstractElementHandle;
import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.runtime.service.cluster.om.ObjectManagerInfo;
import com.tibco.cep.runtime.service.cluster.om.ServiceInfo;
import com.tibco.cep.runtime.service.cluster.om.ServiceMemberInfo;

/*
* User: nprade
* Date: May 6, 2008
* Time: 6:50:59 PM
*/

public class DelegatedQueryOM extends BaseObjectManager implements ObjectManagerInfo {
    protected QueryRuleSessionImpl session;

    protected ServiceInfo delegateServiceInfo;

    public DelegatedQueryOM(String agentName) throws Exception {
        super(agentName);
    }

    /**
     * Make sure you set {@link #setSingleServiceInfo(String)} or {@link #setDistributedServiceInfo(String, String,
     * int)} before starting.
     *
     * @throws Exception
     */
    public void start() throws Exception {
        if (delegateServiceInfo == null) {
            throw new IllegalStateException(
                    ServiceInfo.class.getName() + " has not been set on " + getClass().getSimpleName());
        }
    }

    public void stop() throws Exception {
    }

    @Override
    public BaseHandle getHandle(Object object) {
        return null;
    }
    //------------

    /**
     * Use this or {@link #setDistributedServiceInfo(String, String, int)}
     *
     * @param agentName
     */
    public void setSingleServiceInfo(String agentName) {
        String name = agentName + "-" + System.nanoTime();

        this.delegateServiceInfo = new StandaloneServiceInfoImpl(name, name, 0);
    }

    /**
     * Use this or {@link #setSingleServiceInfo(String)}
     *
     * @param clusterName
     * @param agentName
     * @param agentId
     */
    public void setDistributedServiceInfo(String clusterName, String agentName, int agentId) {
        this.delegateServiceInfo = new DistributedServiceInfoImpl(clusterName, agentName, agentId);
    }

    public ServiceInfo getServiceInfo() {
        return delegateServiceInfo;
    }

    //------------

    public void createElement(Element entity) throws DuplicateExtIdException {
    }

    public void createEvent(Event event) throws DuplicateExtIdException {
    }

    public void applyChanges(RtcOperationList rtcList) {
    }

    public BaseHandle getAddHandle(Object object) throws DuplicateExtIdException {
        return null;
    }

    protected AbstractElementHandle getNewElementExtHandle(Element obj,
                                                           AbstractElementHandle _next, TypeInfo _typeInfo) {
        return null;
    }

    protected AbstractElementHandle getNewElementHandle(Element obj, AbstractElementHandle _next, TypeInfo _typeInfo) {
        return null;
    }

    protected AbstractEventHandle getNewEventExtHandle(Event obj, AbstractEventHandle _next, TypeInfo _typeInfo) {
        return null;
    }

    protected AbstractEventHandle getNewEventHandle(Event obj, AbstractEventHandle _next, TypeInfo _typeInfo) {
        return null;
    }

    public void init(WorkingMemory workingMemory) throws Exception {
    }

    public boolean isObjectStore() {
        return false;
    }

    public void shutdown() throws Exception {
    }

    //---------------

    public static class StandaloneServiceInfoImpl implements ServiceInfo {
        protected String name;

        protected String solitaryMemberName;

        protected int solitaryMemberId;

        public StandaloneServiceInfoImpl(String name, String solitaryMemberName,
                                         int solitaryMemberId) {
            this.name = name;
            this.solitaryMemberName = solitaryMemberName;
            this.solitaryMemberId = solitaryMemberId;
        }

        public String getName() {
            return name;
        }

        public Collection<ServiceMemberInfo> getAllMemberInfo() {
            LinkedList<ServiceMemberInfo> list =
                    new LinkedList<ServiceMemberInfo>();
            list.add(getLocalMemberInfo());

            return list;
        }

        public ServiceMemberInfo getLocalMemberInfo() {
            return new StandaloneServiceMemberInfoImpl();
        }

        public class StandaloneServiceMemberInfoImpl implements ServiceMemberInfo {
            public String getName() {
                return solitaryMemberName;
            }

            public int getUniqueId() {
                return solitaryMemberId;
            }
        }
    }

    public static class DistributedServiceInfoImpl implements ServiceInfo {
        protected String clusterName;

        protected String agentName;

        protected int agentId;

        public DistributedServiceInfoImpl(String clusterName, String agentName, int agentId) {
            this.clusterName = clusterName;
            this.agentName = agentName;
            this.agentId = agentId;
        }

        public String getName() {
            return clusterName;
        }

        public Collection<ServiceMemberInfo> getAllMemberInfo() {
            LinkedList<ServiceMemberInfo> list =
                    new LinkedList<ServiceMemberInfo>();
            list.add(getLocalMemberInfo());

            return list;
        }

        public ServiceMemberInfo getLocalMemberInfo() {
            return new DistributedServiceMemberInfoImpl();
        }

        public class DistributedServiceMemberInfoImpl implements ServiceMemberInfo {
            public String getName() {
                return agentName;
            }

            public int getUniqueId() {
                return agentId;
            }
        }
    }
}
