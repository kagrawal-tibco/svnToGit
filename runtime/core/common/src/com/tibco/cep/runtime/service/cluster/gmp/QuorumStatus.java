package com.tibco.cep.runtime.service.cluster.gmp;

/*
* Author: Ashwin Jayaprakash / Date: Dec 22, 2010 / Time: 12:02:34 PM
*/
public class QuorumStatus {
	
    protected int minCacheServers;

    protected int minSeeders;
    
    protected int actualSeeders;

    protected int quorumReachedSeeders;

    protected boolean quorum;

    public QuorumStatus(int minCacheServers, int minSeeders, int actualSeeders, int quorumReachedSeeders, boolean quorum) {
        this.minCacheServers = minCacheServers;
        this.minSeeders = minSeeders;
        this.actualSeeders = actualSeeders;
        this.quorumReachedSeeders = quorumReachedSeeders;
        
        this.quorum = quorum;
    }

    public int getMinCacheServers() {
        return minCacheServers;
    }
    
    public int getMinSeeders() {
        return minSeeders;
    }

    public int getActualSeeders() {
        return actualSeeders;
    }

    public int getQuorumReachedSeeders() {
        return quorumReachedSeeders;
    }

    public boolean isQuorum() {
        return quorum;
    }
    
    @Override
    public boolean equals(Object quorumStatObject) {
    	if (quorumStatObject == null) {
    		return false;
    	}
    	if ((quorumStatObject instanceof QuorumStatus) == false) {
    		return false;
    	}
    	QuorumStatus quorumStat = (QuorumStatus)quorumStatObject;
        if (this.minCacheServers == quorumStat.minCacheServers &&
	        this.minSeeders == quorumStat.minSeeders &&
	        this.actualSeeders == quorumStat.actualSeeders &&
	        this.quorumReachedSeeders == quorumStat.quorumReachedSeeders &&
	        this.quorum == quorumStat.quorum) {
        	return true;
        }
    	return false;
    }
}
