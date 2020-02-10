package com.tibco.cep.query.service.impl;

import java.util.Collection;
import java.util.LinkedList;

import com.tibco.cep.runtime.service.cluster.om.ServiceInfo;
import com.tibco.cep.runtime.service.cluster.om.ServiceMemberInfo;

/*
* Author: Ashwin Jayaprakash Date: May 13, 2009 Time: 5:20:58 PM
*/
public class ServiceInfoImpl implements ServiceInfo {
    protected String name;

    protected String solitaryMemberName;

    protected int solitaryMemberId;

    public ServiceInfoImpl(String name, String solitaryMemberName, int solitaryMemberId) {
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
        return new ServiceMemberInfoImpl();
    }

    public class ServiceMemberInfoImpl implements ServiceMemberInfo {
        public String getName() {
            return solitaryMemberName;
        }

        public int getUniqueId() {
            return solitaryMemberId;
        }
    }
}