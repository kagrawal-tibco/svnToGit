package com.tibco.cep.loadbalancer.impl.client.membership.grid;

import static com.tibco.cep.util.Helper.$logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.loadbalancer.client.core.ActualMember;
import com.tibco.cep.loadbalancer.client.core.ActualSink;
import com.tibco.cep.loadbalancer.membership.MembershipPublisher;
import com.tibco.cep.loadbalancer.util.SimpleMemberCodec;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Nov 17, 2010 / Time: 6:07:18 PM
*/
@LogCategory("loadbalancer.be.client.membership.grid")
public class DataGridMembershipPublisher implements MembershipPublisher<ActualSink, ActualMember> {
    protected Id id;

    protected ControlDao<UID, byte[]> actualMembers;

    protected ActualMember container;

    protected Logger logger;

    protected ResourceProvider resourceProvider;

    public DataGridMembershipPublisher() {
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void setId(Id id) {
        this.id = id;
    }

    @Override
    public void setResourceProvider(ResourceProvider resourceProvider) throws LifecycleException {
        this.logger = $logger(resourceProvider, getClass());

        this.resourceProvider = resourceProvider;
    }

    //------------------

    @Override
    public void publish(ActualMember container) throws LifecycleException {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();

        actualMembers = cluster.getDaoProvider().createControlDao(UID.class, byte[].class, ControlDaoType.LoadBalancer);

        //---------------

        this.container = container;

        UID groupMemberId = doPut(container, cluster);

        //---------------

        logger.log(Level.FINE, String.format("[%s] published [%s:%s]", getId(), groupMemberId, container.getId()));
    }

    protected UID doPut(ActualMember container, Cluster cluster) throws LifecycleException {
        GroupMember groupMember = cluster.getGroupMembershipService().getLocalMember();
        UID groupMemberId = groupMember.getMemberId();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            SimpleMemberCodec.write(container, resourceProvider, baos);
            baos.close();

            byte[] bytes = baos.toByteArray();

            actualMembers.put(groupMemberId, bytes);
        }
        catch (IOException e) {
            throw new LifecycleException(e);
        }

        return groupMemberId;
    }

    @Override
    public void refreshPublication() throws LifecycleException {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();

        UID groupMemberId = doPut(container, cluster);

        //---------------

        logger.log(Level.FINE, String.format("[%s] refreshed [%s:%s]", getId(), groupMemberId, container.getId()));
    }

    @Override
    public void unpublish() throws LifecycleException {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();

        GroupMember groupMember = cluster.getGroupMembershipService().getLocalMember();
        UID groupMemberId = groupMember.getMemberId();

        actualMembers.remove(groupMemberId);

        //---------------

        logger.log(Level.FINE, String.format("[%s] unpublished [%s:%s]", getId(), groupMemberId, container.getId()));

        //---------------

        container = null;
        actualMembers = null;
    }
}
