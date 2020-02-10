package com.tibco.cep.runtime.model.element.impl;

import com.tibco.cep.kernel.core.rete.BeTransaction;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.entity.Mutable;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/*
* Author: Ashwin Jayaprakash / Date: 10/24/11 / Time: 3:28 PM
*/
public class ManagedObjectManager {
    protected static ManagedObjectSpi managedObjectSpi;

    public static void init(ManagedObjectSpi managedObjectSpi) {
        ManagedObjectManager.managedObjectSpi = managedObjectSpi;
    }

    public static ManagedObjectSpi getManagedObjectSpi() {
        return managedObjectSpi;
    }

    /**
     * @return false if {@link #getManagedObjectSpi()} returns null.
     */
    public static boolean isOn() {
        return managedObjectSpi != null;
    }

    public static boolean hasWriteLock(EntityImpl c) {
        return managedObjectSpi.hasWriteLock(c);
    }

    public static void readLockObject(EntityImpl c) {
        managedObjectSpi.readLockObject(c);
    }

    public static void writeLockObject(EntityImpl c) {
        managedObjectSpi.writeLockObject(c);
    }

    /**
     * @param id
     * @param type     If null, then the id must be typeId encoded.
     * @param locktype
     * @return
     */
    public static EntityImpl fetchById(long id, Class type, ManagedObjectLockType locktype) {
        EntityImpl e = (EntityImpl) BeTransaction.getFromTxn(id);
        if (e != null) {
            return e;
        }

        e = managedObjectSpi.fetchById(id, type, locktype);
        if (e != null) {
            addToTxnAndReload(e);
        }

        return e;
    }

    private static void addToTxnAndReload(EntityImpl e) {
        BeTransaction.addToTxn(e.getId(), e);

        e.setLoadedFromCache();

        String extId = e.getExtId();
        if (extId != null) {
            BeTransaction.addToTxn(getIdToExtIdKey(e.getId()), extId);

            BeTransaction.addToTxn(extId, e);
        }
    }

    private static String getIdToExtIdKey(long id) {
        return "0x_id_ext_" + Long.toHexString(id);
    }

    public static EntityImpl fetchByExtId(String extId, Class entityClass, ManagedObjectLockType locktype) {
        EntityImpl e = (EntityImpl) BeTransaction.getFromTxn(extId);
        if (e != null) {
            return e;
        }

        e = managedObjectSpi.fetchByExtId(extId, entityClass, locktype);
        if (e != null) {
            addToTxnAndReload(e);
        }

        return e;
    }

    /**
     * @param e
     * @return null if c is also null
     */
    public static EntityImpl loadIntoRtc(EntityImpl e) {
        if (e == null) {
            return null;
        }

        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (session == null) {
            session = RuleServiceProviderManager.getInstance().getDefaultProvider().getRuleRuntime().getRuleSessions()[0];
            RuleSessionManager.currentRuleSessions.set(session);
        }
        ObjectManager om = session.getObjectManager();
        if (om instanceof DistributedCacheBasedStore) {
            e.setLoadedFromCache();

            try {
                if (e instanceof Mutable) {
                    e = (EntityImpl) ((DistributedCacheBasedStore) om).getAddElementHandle((Element) e, true).getObject();
                }
                else {
                    e = (EntityImpl) ((DistributedCacheBasedStore) om).getAddEventHandle((Event) e, true).getObject();
                }
            }
            catch (DuplicateExtIdException ex) {
                throw new RuntimeException(ex);
            }

            ((RuleSessionImpl) session).reloadFromCache(e);
        }

        return e;
    }

    public static EntityImpl loadRelationIntoRtc(EntityImpl parentOrReferrer, Class relationType, long conceptIdOfRelation) {
        ManagedObjectLockType parentOrReferrerLock =
                hasWriteLock(parentOrReferrer) ? ManagedObjectLockType.WRITELOCK : ManagedObjectLockType.READLOCK;

        EntityImpl c = (EntityImpl) BeTransaction.getFromTxn(conceptIdOfRelation);

        if (c == null) {
            //Use same lock mode as parent/referrer.
            c = fetchById(conceptIdOfRelation, relationType, parentOrReferrerLock);

            c = loadIntoRtc(c);
        }
        else {
            switch (parentOrReferrerLock) {
                case WRITELOCK:
                    writeLockObject(c);
                    break;

                default:
                    readLockObject(c);
                    break;
            }
        }

        return c;
    }

    public static void insert(EntityImpl entity) {
        addToTxnAndReload(entity);

        managedObjectSpi.insert(entity);
    }

    public static void update(EntityImpl entity) {
        managedObjectSpi.update(entity);
    }

    public static void delete(long entityId, Class entityClass) {
        managedObjectSpi.delete(entityId, entityClass);

        BeTransaction.removeFromTxn(entityId);

        String extId = (String) BeTransaction.removeFromTxn(getIdToExtIdKey(entityId));
        if (extId != null) {
            BeTransaction.removeFromTxn(extId);
        }
    }
}
