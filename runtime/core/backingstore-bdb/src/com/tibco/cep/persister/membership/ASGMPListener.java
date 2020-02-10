package com.tibco.cep.persister.membership;

import com.tibco.as.space.*;
import com.tibco.as.space.persistence.Persister;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.persister.ASPersistenceConstants;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMemberServiceListener;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService;
import com.tibco.cep.runtime.service.dao.impl.tibas.*;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 1/10/12
 * Time: 3:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ASGMPListener implements GroupMemberServiceListener,Runnable {

    private Space persisterSpace;
    private KeyValueTupleAdaptor kvTupleAdaptor;
    public Logger logger = null;
    private volatile GroupMember localMember = null;
    private Cluster cluster = null;
    private ASPersistenceProvider persistenceMgr = null;
    private Metaspace metaspace = null;
    private GroupMembershipService gmp = null;

    public ASGMPListener (Cluster cluster,Metaspace metaspace) {

        persistenceMgr = ((ASDaoProvider) cluster.getDaoProvider()).getBDBPersistenceProvider();
        kvTupleAdaptor = persistenceMgr.getControlSpaceKVTupleAdaptor();
        logger = LogManagerFactory.getLogManager().getLogger(ASGMPListener.class);

        gmp = cluster.getGroupMembershipService();
		if (gmp != null) {
			localMember = gmp.getLocalMember();
		}

        persisterSpace = persistenceMgr.getControlSpace();
        this.cluster = cluster;
        this.metaspace = metaspace;

        logger.log(Level.INFO, "Initalized ASGMPListener for space "+ASPersistenceConstants.PersistenceMembershipSpace);
    }

    @Override
    public void memberJoined(GroupMember member) {

    }

    @Override
    public void memberLeft(GroupMember member) {

		if (member.isSeeder()) {
			try {
				if (obtainSpaceOwnership(member)) {
                    logger.log(Level.INFO, "Attempting failover for Berkeley DB persistence");
                    // Get at list of all the spaces that need to be persisted
                    ASDaoProvider daoProvider= (ASDaoProvider)cluster.getDaoProvider();
                    Collection daos = daoProvider.getAllEntityDao();
                    Iterator begin = daos.iterator();
                    int installCount = 0;

					while (begin.hasNext()) {
                        ASEntityDao entityDao = (ASEntityDao)begin.next();
                        SpaceMap map = ((SpaceMap) entityDao.getInternal());

						// Only persist spaces that were meant to be persisted
                        boolean backingStoreOverrideEnabled = true;
                        EntityDaoConfig entityConfig = this.cluster.getMetadataCache().getEntityDaoConfig(entityDao.getEntityClass());
                        if (entityConfig!=null && !entityConfig.hasBackingStore()) {
                            backingStoreOverrideEnabled = false;
                        }

						if (map != null && backingStoreOverrideEnabled) {
                            Space sp = map.getSpace();
                            logger.log(Level.INFO, "Creating data persister for "+sp.getName());
                            Persister persister = persistenceMgr.createDataPersister(map.getTupleAdaptor(),sp.getName());
                            sp.setPersister(persister);
                            ++installCount;
                        }
                    }
                    logger.log(Level.INFO, "Installed persisters for "+installCount+" data spaces");

                    // Also install a persister for some of the control spaces
                    Collection controlDaos = daoProvider.getAllControlDao();
                    begin = controlDaos.iterator();
                    ASControlDao controlDao = null;
                    while (begin.hasNext()) {
                        controlDao = (ASControlDao)begin.next();
                        switch (controlDao.getType()) {
                            case ObjectTableExtIds:
                            case MasterId:
                            case WorkList$SchedulerId:
                            case ObjectTableIds:
                                //If the persistence implementation class is provided in the property
                                //then the persistence needs to be enabled on the AS space
                                SpaceMap map = controlDao.getSpaceMap();
                                if (map != null) {
                                    Space sp = map.getSpace();
                                    logger.log(Level.INFO, "Creating control persister for "+sp.getName());
                                    Persister persister = persistenceMgr.createControlPersister(map.getTupleAdaptor(), controlDao.getType(),sp.getName());
                                    sp.setPersister(persister);
                                }
                                break;

                            default:
                                break;
                        }
                    }
                    logger.log(Level.INFO, "Failover for Berkeley DB persistence completed.");
                }
			} catch (Throwable t) {
                logger.log(Level.ERROR,t,t.getMessage());
                throw new RuntimeException(t);
            }
        }
    }

    @Override
    public void memberStatusChanged(GroupMember member, Status oldStatus, Status newStatus) {
    }

    public boolean obtainSpaceOwnership(GroupMember memberThatLeft) throws ASException {
        //Check if you are a seeder before attempting to take control of the space
        //You also dont want to do anything if you are already the owner
		if (localMember.isSeeder() && (persistenceMgr.isControlSpaceOwner() != ASPersistenceProvider.ControlSpaceOwnershipStatus.OWNER)) {
            persistenceMgr.setControlSpaceOwner(ASPersistenceProvider.ControlSpaceOwnershipStatus.TRYING);

            boolean ownerMatch = false;
            //try to get a lock on the PersistenceMembershipSpace
            Tuple queryTuple = kvTupleAdaptor.makeTuple(ASPersistenceConstants.SpaceKey);
            Tuple lockTuple = null;

            try {
                lockTuple = persisterSpace.lock(queryTuple,LockOptions.create().setLockWait(0));
			} catch (ASException e) {
				if (e.getStatus() == ASStatus.LOCKED) {
                    //do nothing.
                    //the space must be locked by some other seeder which could be the persister
                    logger.log(Level.INFO, ASPersistenceConstants.PersistenceMembershipSpace+ " already locked.");
                    persistenceMgr.setControlSpaceOwner(ASPersistenceProvider.ControlSpaceOwnershipStatus.NOT_OWNER);
                    return false;
                } else {
                    logger.log(Level.ERROR, "AS lock operation failed!!");
                    persistenceMgr.setControlSpaceOwner(ASPersistenceProvider.ControlSpaceOwnershipStatus.NOT_OWNER);
                    throw e;
                }
            }

            //Check now if the entry in the space corresponds to a different member than the one that has left
            SpaceOwnership previousOwner = (SpaceOwnership)kvTupleAdaptor.extractValue(lockTuple);
			if (previousOwner != null && memberThatLeft != null) {
                ownerMatch = previousOwner.getMemberID().equals(memberThatLeft.getMemberId());
            }

			if (((memberThatLeft == null) && (previousOwner == null)) || ownerMatch) {
                //logger.log(Level.INFO, memberThatLeft.getMemberId().toString(), " has left the space");
                //Put an entry into the cache to indicate you are the owner
                logger.log(Level.INFO,"memberThatLeft="+memberThatLeft+", previousOwner="+previousOwner+", ownerMatch="+ownerMatch);
                SpaceOwnership owner = new SpaceOwnership(localMember.getMemberId(),System.currentTimeMillis());
                Tuple insertTuple = kvTupleAdaptor.makeTuple(ASPersistenceConstants.SpaceKey);
                insertTuple = kvTupleAdaptor.setValue(insertTuple,owner);

                try {
                    Tuple updateTuple = persisterSpace.put(insertTuple);
                    persisterSpace.unlock(queryTuple);
                } catch (ASException e) {
                    throw new RuntimeException(e.getMessage());
                }

                persistenceMgr.setControlSpaceOwner(ASPersistenceProvider.ControlSpaceOwnershipStatus.OWNER);
                logger.log(Level.INFO, ASPersistenceConstants.PersistenceMembershipSpace+" space has a new owner ["+localMember.getMemberId().toString()+"]");
                logger.log(Level.INFO, "Node ["+ localMember.getMemberId().toString()+"] is now the persister.");

                try {
                    return(persistenceMgr.initDBProvider());
                } catch (Exception e) {
                    logger.log(Level.ERROR,"Unable to initialize BDB provider");
                    throw new RuntimeException(e.getMessage());
                }
            }
            else {
                // The previous owner still exists
                // Do nothing
                persistenceMgr.setControlSpaceOwner(ASPersistenceProvider.ControlSpaceOwnershipStatus.NOT_OWNER);
            }

            try {
                logger.log(Level.DEBUG, "Unlocking "+ASPersistenceConstants.PersistenceMembershipSpace);
                persisterSpace.unlock(queryTuple);
            } catch (ASException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public void run() {
		while (localMember == null) {
            try {
                localMember = gmp.getLocalMember();
                if( localMember== null ) {
                    Thread.sleep(ASPersistenceConstants.LocalMemberRetry);
                }
            } catch (NullPointerException e) {
                throw new RuntimeException(e.getMessage());

            } catch (InterruptedException ie) {
                throw new RuntimeException(ie.getMessage());
            }
        }

        logger.log(Level.INFO, "Node ["+ localMember.getMemberId().toString()+"] initialized.");
        try {
            obtainSpaceOwnership(null);
        } catch (ASException as) {
            throw new RuntimeException(as.getMessage());
        }
    }
}
