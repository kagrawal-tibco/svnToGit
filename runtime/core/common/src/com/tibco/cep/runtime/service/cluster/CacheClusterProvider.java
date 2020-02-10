/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.GvCommonUtils;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 22, 2007
 * Time: 5:16:31 PM
 * To change this template use File | Settings | File Templates.
 */
/*

 */

public class CacheClusterProvider {
    //protected Map providerMap = Collections.synchronizedMap(new HashMap());
    private static CacheClusterProvider qMgr;
    private Cluster cacheCluster;
    public static final String CLUSTER_NAME_DEFAULT_VALUE = "standalone-cluster";
    public static final String CLUSTER_NAME_KEY           = "com.tibco.cep.runtime.service.cluster.name";

//    /**
//     *
//     * @param clusterConfig
//     * @param
//     * @return
//     */
//    public final Cluster getCacheCluster(ClusterConfiguration clusterConfig, RuleServiceProvider rsp) throws Exception{
//        if (cacheCluster == null) {
//            cacheCluster = rsp.getCluster();
//        }
//        return cacheCluster;
//    }
//
//    public final  Cluster getCacheCluster( String clusterName) {
//        //return (CacheCluster) providerMap.get(clusterName);
//        return cacheCluster;
//    }

    public final Cluster getCacheCluster(){

        return cacheCluster;
    }

    private CacheClusterProvider() {

    }

    public Cluster createCluster(RuleServiceProvider rsp) throws Exception {
        if (cacheCluster != null) {
            throw new Exception("Cluster already created");
        }

        if (rsp.getProject().isCacheEnabled()) {

            String clusterName = System.getProperty(SystemProperty.CLUSTER_NAME.getPropertyName(), CLUSTER_NAME_DEFAULT_VALUE);
            
            if(GvCommonUtils.isGlobalVar(clusterName)){
             	GlobalVariableDescriptor gv =rsp.getGlobalVariables().getVariable(GvCommonUtils.stripGvMarkers(clusterName));
             	clusterName=gv.getValueAsString();
            }
            

            cacheCluster = new MultiAgentCluster(clusterName, rsp);

            return cacheCluster;
        }
        return cacheCluster;
    }

    /**
     *
     * @return
     */
    public final synchronized static CacheClusterProvider getInstance() {
        if (qMgr == null) {
            qMgr= new CacheClusterProvider();
        }
        return qMgr;
    }



}
