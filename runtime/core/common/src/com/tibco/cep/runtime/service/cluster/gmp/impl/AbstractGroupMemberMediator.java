package com.tibco.cep.runtime.service.cluster.gmp.impl;

import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMemberServiceListener;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.GroupMemberMediator;
import com.tibco.cep.util.Helper;
import com.tibco.cep.util.annotation.Copy;
import com.tibco.cep.util.annotation.Optional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
* Author: Ashwin Jayaprakash / Date: Nov 29, 2010 / Time: 11:40:07 AM
*/
public abstract class AbstractGroupMemberMediator<M, G extends GroupMember, D extends DaoProvider>
        implements GroupMemberMediator {
	
    protected final ConcurrentHashMap<M, G> nativeToBEMemberMap;

    /**
     * Map used as a Set.
     */
    protected ConcurrentHashMap<GroupMemberServiceListener, GroupMemberServiceListener> listeners;

    protected G localMember;

    protected D daoProvider;

    protected Cluster cluster;

    protected Logger logger;

    protected AbstractGroupMemberMediator(D daoProvider) {
        this.daoProvider = daoProvider;

        this.nativeToBEMemberMap = new ConcurrentHashMap<M, G>();
        this.listeners = new ConcurrentHashMap<GroupMemberServiceListener, GroupMemberServiceListener>();
    }

    public final void init(Cluster cluster) {
        this.cluster = cluster;

        ResourceProvider resourceProvider = cluster.getResourceProvider();
        this.logger = Helper.$logger(resourceProvider, getClass());

        //--------------

        logger.log(Level.INFO, "Initializing [" + getClass().getSimpleName() + "]");

        initHook();

        updateAllMembers();

        logger.log(Level.INFO, "Initialized [" + getClass().getSimpleName() + "]");
    }

    /**
     * {@link #cluster} and {@link #logger} are set before this method is called.
     */
    protected abstract void initHook();

    public Cluster getCluster() {
        return cluster;
    }

    //------------------

    protected abstract M fetchLocalNativeMember();

    /**
     * @param m The local member.
     * @return
     */
    protected abstract G transformLocalMember(M m);

    protected abstract Collection<M> fetchNativeMembers();

    /**
     * @param m May even be the local member. Can also be remote members.
     * @return Can return null if the member disappears before this call can be made.
     */
    @Optional
    protected abstract G transformLiveNativeMember(M m);

    protected synchronized void updateLocalMember() {
        M m = fetchLocalNativeMember();

        localMember = transformLocalMember(m);

        nativeToBEMemberMap.putIfAbsent(m, localMember);
    }

    /**
     * Cleans up everything and fetches everything afresh - even {@link #updateLocalMember()}.
     */
    @Copy
    @Override
    public synchronized Collection<G> updateAllMembers() {
        Collection<M> members = fetchNativeMembers();

        nativeToBEMemberMap.clear();
        updateLocalMember();

        for (M m : members) {
            if (nativeToBEMemberMap.get(m) == null) {
                G g = transformLiveNativeMember(m);
                if (g != null) {
                    nativeToBEMemberMap.putIfAbsent(m, g);
                }
            }
        }
        return new ArrayList<G>(nativeToBEMemberMap.values());
    }

    protected synchronized void handleMemberJoined(M m) {
        try {
            int retries = 0;

            G g = null;
            while ((g = transformLiveNativeMember(m)) == null) {
                retries++;

                if (retries == 10) {
                    logger.log(Level.SEVERE, "Member does not seem to be ready even after [" + retries +
                            "] retry attempts. Dropping member join notification [" + m + "]");

                    return;
                }
                else {
                    logger.log(Level.FINE, "Waiting for member to initialize..");
                }

                try {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e) {
                }
            }

            nativeToBEMemberMap.putIfAbsent(m, g);

            for (GroupMemberServiceListener lsnr : listeners.values()) {
                lsnr.memberJoined(g);
            }
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    protected synchronized void handleMemberLeft(M m) {
        try {
            G g = nativeToBEMemberMap.remove(m);

            if (g != null) {
                for (GroupMemberServiceListener lsnr : listeners.values()) {
                    lsnr.memberLeft(g);
                }
            }
        }
        catch (Exception e) {
            //Log level is set to FINE. In ERROR/SEVERE levels, a coherence shutdown exception would be seen on every shutdown which is not desired.
            logger.log(Level.FINE, e.getMessage(), e);
        }
    }

    //------------------

    @Override
    public void addGroupMemberServiceListener(GroupMemberServiceListener listener) {
        GroupMemberServiceListener lsnr = listeners.putIfAbsent(listener, listener);
        if (lsnr != null) {
            //log it
        }
    }

    @Override
    public void removeGroupMemberServiceListener(GroupMemberServiceListener listener) {
        if (!(listeners.remove(listener, listener))) {
            //log the exception:
        }
    }

    //------------------

    @Override
    public G getLocalMember() {
    	if (localMember == null) {
    		updateLocalMember();
    	}
        return localMember;
    }

    @Copy
    @Override
    public Collection<G> getMembers() {
        return new ArrayList<G>(nativeToBEMemberMap.values());
    }
}
