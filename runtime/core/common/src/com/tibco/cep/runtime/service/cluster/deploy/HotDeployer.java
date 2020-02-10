package com.tibco.cep.runtime.service.cluster.deploy;

import com.tibco.cep.runtime.service.cluster.Cluster;

public interface HotDeployer {

	void init(Cluster cluster) throws Exception;
    void start() throws Exception;
	
    void deploy(String uriPath) throws Exception;
  
    public boolean insert() throws Exception;
    
    public void unloadClass(String vrfURI, String implName) throws Exception;
    
    public void undeployExternalClass() throws Exception;

}
