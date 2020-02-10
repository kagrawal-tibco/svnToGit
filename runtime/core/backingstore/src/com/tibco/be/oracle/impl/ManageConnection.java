/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.oracle.impl;

import java.util.Map;
import java.util.Set;

import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.InvocationService;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 12, 2007
 * Time: 6:43:22 PM
 * To change this template use File | Settings | File Templates.
 */

//bala: cannot find any reference of this in 4.x as well, so deprecated
@Deprecated
public class ManageConnection implements Invocable {

    //public class CacheFlush extends CacheServiceAgent {
    private String uri = null;
    private boolean switchToSecondary;

    public ManageConnection(String uri, boolean switchToSecondary) {
        this.uri = uri;
        this.switchToSecondary = switchToSecondary;
    }

    public ManageConnection() {
    }


//    public Result execute(Object entry, InvocableDef.InvocableContext c) {
//        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
//        GroupMember gm = cluster.getGroupMembershipService().getLocalMember();
//        DefaultMemberResult<Boolean> boolResult = new DefaultMemberResult<Boolean>(gm);
//
//        try {
//            OracleActiveConnectionPool activePool = OracleConnectionManager.getActiveConnectionPool(uri);
//            if (activePool != null) {
//                if (switchToSecondary)
//                    activePool.switchLocalToSecondary();
//                else
//                    activePool.switchLocalToPrimary();
//
//                boolResult.setReturnValue(Boolean.TRUE);
//                boolResult.setReturnStatus(Status.SUCCESS);
//            }
//        } catch (Exception e) {
//            boolResult.setReturnStatus(Status.ERROR);
//            boolResult.setException(e);
//        }
//        return boolResult;
//    }
//TODO:BALA

    public final static void switchDBAcrossCluster(String uri, boolean switchToSecondary) throws Exception {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                InvocationService invoker = cluster.getDaoProvider().getInvocationService();
                Set set = null;
                if (!cluster.getClusterConfig().isCacheAside()) {
                    set = cluster.getGroupMembershipService().getStorageEnabledMembers();
                }

                Map map = (Map) invoker.invoke(new ManageConnection(uri, switchToSecondary), set);

                //Check for errors
                StringBuilder sb = null;

                if (!cluster.getClusterConfig().isCacheAside()) {
                    //Check if all cache-servers could be invoked
                    if (map.size() != set.size()) {
                        sb = new StringBuilder().append("\n");
                        set.removeAll(map.keySet());
                        for (Object obj : set) {
                            sb.append("Could not invoke " + obj + "\n");
                        }
                    }
                }

                // Check if any node returned exception
                for (Object obj : map.entrySet()) {
                    Map.Entry entry = (Map.Entry) obj;
                    if (entry.getValue() instanceof Exception) {
                        Exception e = (Exception) entry.getValue();
                        if (sb == null)
                            sb = new StringBuilder().append("\n");
                        sb.append("Invocation error on  " + entry.getKey() + ": " + e.getMessage() + "\n");
                    }
                }
                if (sb != null)
                    throw new Exception(sb.toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Object invoke(Map.Entry entry) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}
