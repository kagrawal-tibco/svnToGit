package com.tibco.cep.runtime.service.cluster.gmp.impl;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService.State;
import com.tibco.cep.runtime.service.cluster.gmp.MemberConfiguration;

/*
* Author: Ashwin Jayaprakash / Date: Nov 29, 2010 / Time: 1:55:04 PM
*/
public abstract class AbstractGroupMember<M> implements GroupMember<M> {
	
    protected boolean local;

    protected MemberConfiguration config;
    private State state;

    protected Cluster cluster;
    protected M nativeMember;

    protected AbstractGroupMember(AbstractGroupMemberMediator mediator, MemberConfiguration config, boolean local, M nativeMember) {
        this.config = config;
        this.local = local;
        this.cluster = mediator.getCluster();
        this.nativeMember = nativeMember;
        this.state = State.INIT;
    }
    
    @Override
    public void setMemberConfig(MemberConfiguration config) {
    	this.config = config;
    }
    
    @Override
    public MemberConfiguration getMemberConfig() {
    	return this.config;
    }

    @Override
    public void setMemberState(State state) {
    	this.state = state;
    }
    
    @Override
    public State getMemberState() {
    	return this.state;
    }

    @Override
    public boolean isSeeder() {
        try {
            if (local && cluster != null) {
                return cluster.getClusterConfig().isStorageEnabled();
            }

            if (config != null) {
            	return config.getClusterConfiguration().isStorageEnabled();
            }
        }
        catch (Exception e) {
            // System is still booting up. NPE sometimes: http://jira.tibco.com/browse/BE-10551
            return false;
        }
        return false;
    }
    
    abstract public M getNativeMember();

}
