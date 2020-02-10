/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.gmp;

import java.io.Serializable;
import java.util.Properties;

import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.ClusterConfiguration;

/*
* Author: Suresh Subramani / Date: Oct 12, 2010 / Time: 3:46:34 PM
*/
public class MemberConfiguration implements Serializable {
	
    private ClusterConfiguration clusterConfiguration;
    private Properties systemProperties;

    public MemberConfiguration(ClusterConfiguration clusterConfiguration, Properties systemProperties) {
        this.clusterConfiguration = clusterConfiguration;
        this.systemProperties = systemProperties;
    }

    public ClusterConfiguration getClusterConfiguration() {
        return clusterConfiguration;
    }

    public Properties getSystemProperties() {
		// Lazy populate as this function is never called
    	if (systemProperties == null) {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            systemProperties = cluster.getRuleServiceProvider().getProperties();
    	}
        return systemProperties;
    }
    
    public String toString() {
    	return "MemberConfiguration [ Cluster:" + this.clusterConfiguration.getClusterName() + 
    			" Properties:" + this.systemProperties + " ]"; 
    }
}
