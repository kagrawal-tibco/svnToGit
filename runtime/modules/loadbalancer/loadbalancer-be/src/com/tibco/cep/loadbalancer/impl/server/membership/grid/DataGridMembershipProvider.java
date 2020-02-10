package com.tibco.cep.loadbalancer.impl.server.membership.grid;

import static com.tibco.cep.util.Helper.$logger;

import java.io.ByteArrayInputStream;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.impl.service.DefaultThreadFactory;
import com.tibco.cep.loadbalancer.impl.membership.MembershipInfo;
import com.tibco.cep.loadbalancer.membership.MembershipChangeListener;
import com.tibco.cep.loadbalancer.membership.MembershipChangeProvider;
import com.tibco.cep.loadbalancer.server.core.Member;
import com.tibco.cep.loadbalancer.server.core.sink.Sink;
import com.tibco.cep.loadbalancer.util.SimpleMemberCodec;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMemberServiceListener;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDao.ChangeListener;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Nov 17, 2010 / Time: 5:54:01 PM
*/

@LogCategory("loadbalancer.be.server.membership.grid")
public class DataGridMembershipProvider implements ChangeListener<UID, byte[]>, GroupMemberServiceListener,
        MembershipChangeProvider<Sink, Member> {
    /**
     * {@value} a unique Id that has an extremely low chance of the user picking the same Id.
     */
    public static final UID LOADBALANCER_COORDINATION_LOCK_KEY =
            new UID("LOADBALANCER.COORDINATION.LOCK.KEY.0xCAFED0D0.0x13019201011");

    protected Id id;

    protected MembershipChangeListener<Sink, Member> changeListener;

    protected ControlDao<UID, byte[]> actualMembers;

    protected ReentrantLock reentrantLock;

    protected ExecutorService executorService;

    protected Logger logger;

    protected ResourceProvider resourceProvider;

    public DataGridMembershipProvider() {
        this.reentrantLock = new ReentrantLock();
    }

    //---------------

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void setId(Id id) {
        this.id = id;
    }

    @Override
    public void start(ResourceProvider resourceProvider,
                      MembershipChangeListener<Sink, Member> changeListener)
            throws LifecycleException {
        this.logger = $logger(resourceProvider, getClass());

        this.resourceProvider = resourceProvider;

        this.changeListener = changeListener;

        //---------------

        String poolName = getClass().getSimpleName() + ".AsyncProcessor." + id;
        this.executorService = Executors.newSingleThreadExecutor(new DefaultThreadFactory(poolName));

        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();

        this.actualMembers =
                cluster.getDaoProvider().createControlDao(UID.class, byte[].class, ControlDaoType.LoadBalancer);

        this.actualMembers.registerListener(this);

        syncView();

        cluster.getGroupMembershipService().addGroupMemberServiceListener(this);

        //---------------

        this.logger.log(Level.FINE, String.format("Started [%s]", getId()));
    }

    public void stop() {
        actualMembers.unregisterListener(this);

        executorService.shutdown();

        //---------------

        this.logger.log(Level.FINE, String.format("Stopped [%s]", getId()));
    }

    protected Member extractMember(byte[] bytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

            MembershipInfo<Sink, Member> membershipInfo = SimpleMemberCodec.read(bais, resourceProvider);

            return membershipInfo.createContainer(resourceProvider);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Id extractMemberId(byte[] bytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

            MembershipInfo<Sink, Member> membershipInfo = SimpleMemberCodec.read(bais, resourceProvider);

            return membershipInfo.getContainerId();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void syncView() {
        reentrantLock.lock();
        try {
            logger.log(Level.FINE, String.format("[%s] retrieving cluster membership state", getId()));

            for (byte[] bytes : actualMembers.values()) {
                Member member = extractMember(bytes);

                changeListener.hasJoined(member);
            }

            logger.log(Level.FINE, String.format("[%s] retrieved cluster membership state", getId()));
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public void refresh() {
        reentrantLock.lock();
        try {
            logger.log(Level.FINE, String.format("[%s] refreshing cluster membership state", getId()));

            for (Entry<UID, byte[]> entry : actualMembers.entrySet()) {
                doSyncUpdate(entry.getValue(), entry.getValue());
            }

            logger.log(Level.FINE, String.format("[%s] refreshed cluster membership state", getId()));
        }
        finally {
            reentrantLock.unlock();
        }
    }

    private void doSyncUpdate(byte[] oldValue, byte[] newValue) {
        Id oldMemberId = extractMemberId(oldValue);

        changeListener.hasLeft(oldMemberId);

        Member newMember = extractMember(newValue);

        changeListener.hasJoined(newMember);
    }

    //---------------

    @Override
    public void onPut(UID key, final byte[] value) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                try {
                    Member member = extractMember(value);

                    changeListener.hasJoined(member);
                }
                finally {
                    reentrantLock.unlock();
                }
            }
        });
    }

    @Override
    public void onUpdate(UID key, final byte[] oldValue, final byte[] newValue) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                try {
                    doSyncUpdate(oldValue, newValue);
                }
                finally {
                    reentrantLock.unlock();
                }
            }
        });
    }

    @Override
    public void onRemove(UID key, final byte[] value) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                try {
                    Id oldMemberId = extractMemberId(value);

                    changeListener.hasLeft(oldMemberId);
                }
                finally {
                    reentrantLock.unlock();
                }
            }
        });
    }

    //---------------

    @Override
    public void memberJoined(GroupMember member) {
        //Ignore.
    }

    @Override
    public void memberLeft(final GroupMember groupMember) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                try {
                    UID deadMemberId = groupMember.getMemberId();

                    while (!actualMembers.lock(LOADBALANCER_COORDINATION_LOCK_KEY, 500)) {
                        logger.log(Level.FINE, String.format("[%s] trying to acquire coordination lock", getId()));
                    }
                    try {
                        byte[] deadMember = actualMembers.get(deadMemberId);

                        if (deadMember != null) {
                            logger.log(Level.INFO,
                                    String.format("[%s] cleaning up dead member [%s]", getId(), deadMemberId));

                            //Cleanup after the dead server. This will automatically generate an onRemove(xx) on all remaining servers.
                            actualMembers.remove(deadMemberId);
                        }
                    }
                    finally {
                        actualMembers.unlock(LOADBALANCER_COORDINATION_LOCK_KEY);
                    }
                }
                finally {
                    reentrantLock.unlock();
                }
            }
        });
    }

    @Override
    public void memberStatusChanged(GroupMember member, Status oldStatus, Status newStatus) {
        //Ignore.
    }
}
