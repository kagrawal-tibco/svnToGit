package com.tibco.rta.service.cluster;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/4/13
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GMPLeaderElectionStrategy {

    /**
     *
     * @param <G>
     * @return
     */
    public <G extends GroupMember> G electLeader();
}
