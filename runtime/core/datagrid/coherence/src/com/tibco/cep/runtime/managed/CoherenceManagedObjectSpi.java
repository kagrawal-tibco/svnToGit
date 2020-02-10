package com.tibco.cep.runtime.managed;

import com.tangosol.coherence.transaction.Connection;
import com.tangosol.coherence.transaction.DefaultConnectionFactory;
import com.tangosol.coherence.transaction.Isolation;
import com.tangosol.coherence.transaction.OptimisticNamedCache;
import com.tangosol.coherence.transaction.exception.PredicateFailedException;
import com.tangosol.io.ExternalizableLite;
import com.tangosol.io.Serializer;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.Service;
import com.tangosol.util.ExternalizableHelper;
import com.tangosol.util.Filter;
import com.tangosol.util.ValueExtractor;
import com.tangosol.util.extractor.ReflectionExtractor;
import com.tangosol.util.filter.EqualsFilter;
import com.tibco.be.functions.coherence.extractor.CoherenceExtractorFunctions;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectLockType;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/*
* Author: Ashwin Jayaprakash / Date: 11/22/11 / Time: 3:07 PM
*/
public class CoherenceManagedObjectSpi extends AbstractGridManagedObjectSpi {
    public static final boolean USE_SHELL = true;

    protected static final ThreadLocal<TxnData> allTxnData = new ThreadLocal<TxnData>();

    protected DefaultConnectionFactory connectionFactory;

    protected ClassLoader classLoader;

    protected Serializer serializer;

    protected ValueExtractor extIdExtractor;

    public CoherenceManagedObjectSpi() {
        Service txnService = CacheFactory.getService("TransactionalCache");
        Cluster cluster = RuleServiceProviderManager.getInstance().getDefaultProvider().getCluster();
        this.classLoader = (ClassLoader) cluster.getRuleServiceProvider().getTypeManager();
        txnService.setContextClassLoader(this.classLoader);
        this.serializer = txnService.getSerializer();

        this.connectionFactory = new DefaultConnectionFactory();

        this.extIdExtractor = (ValueExtractor) CoherenceExtractorFunctions.C_EntityExtIdGetter();
    }

    //----------------

    private static OptimisticNamedCache getOnc(EntityDao dao, TxnData txnData) {
        HashMap<EntityDao, OptimisticNamedCache> map = txnData.daoMap;

        OptimisticNamedCache onc = map.get(dao);
        if (onc == null) {
            onc = txnData.connection.getNamedCache(dao.getName());
            map.put(dao, onc);
        }

        return onc;
    }

    private EntityImpl getFromOnc(long id, OptimisticNamedCache onc) {
        if (USE_SHELL) {
            ComparableShell cs = (ComparableShell) onc.get(id);

            return (cs == null) ? null : cs.getConcept(classLoader);
        }

        return (EntityImpl) onc.get(id);
    }

    @Override
    public EntityImpl fetchById(long id, Class entityClass, ManagedObjectLockType locktype) {
        EntityDao dao = (entityClass == null) ? getDao(id) : getDao(entityClass);

        try {
            TxnData txnData = allTxnData.get();
            OptimisticNamedCache onc = getOnc(dao, txnData);
            EntityImpl e = getFromOnc(id, onc);
            txnData.idsAndVersions.put(id, SpiHelper.extractVersion(e));

            return e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public EntityImpl fetchByExtId(String extId, Class entityClass, ManagedObjectLockType locktype) {
        if (entityClass == null) {
            return null;
        }

        EntityDao dao = getDao(entityClass);

        try {
            NamedCache nc = (NamedCache) dao.getInternal();
            Filter filter = new EqualsFilter(extIdExtractor, extId);

            Thread.currentThread().setContextClassLoader(classLoader);
            Set set = nc.entrySet(filter);

            if (set.isEmpty()) {
                return null;
            }

            Entry entry = (Entry) set.iterator().next();
            EntityImpl e = USE_SHELL
                    ? ((ComparableShell) entry.getValue()).getConcept(classLoader)
                    : (EntityImpl) entry.getValue();

            TxnData txnData = allTxnData.get();
            txnData.idsAndVersions.put(e.getId(), SpiHelper.extractVersion(e));

            return e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EntityImpl> fetchByExtIds(Collection<String> extIds, Class entityClass, ManagedObjectLockType locktype) {
        throw new UnsupportedOperationException("fetchByExtIds not implemented for CoherenceManagedObjectSpi");
    }
    
    @Override
    public void insert(EntityImpl entity) {
        EntityDao dao = getDao(entity);

        try {
            OptimisticNamedCache onc = getOnc(dao, allTxnData.get());

            Object o = USE_SHELL ? new ComparableShell(entity, serializer) : entity;
            onc.insert(entity.getId(), o);

            TxnData txnData = allTxnData.get();
            txnData.idsAndVersions.put(entity.getId(), SpiHelper.extractVersion(entity));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(EntityImpl entity) {
        EntityDao dao = getDao(entity);

        TxnData txnData = allTxnData.get();
        Long id = entity.getId();
        Integer previousVersion = txnData.idsAndVersions.get(id);
        OptimisticNamedCache onc = getOnc(dao, txnData);

        try {
            Object o = USE_SHELL ? new ComparableShell(entity, serializer) : entity;
            onc.update(id, o, new EqualsFilter(new ReflectionExtractor("getVersion"), previousVersion));

            txnData.idsAndVersions.put(id, SpiHelper.extractVersion(entity));
        }
        catch (PredicateFailedException f) {
            EntityImpl a = (EntityImpl) onc.get(id);
            int v = (a != null) ? SpiHelper.extractVersion(a) : -1;

            throw new RuntimeException(
                    "Optimistic concurrency error occurred while updating entity " + entity +
                            ". Expected version in the cache: " +
                            previousVersion + " but actual version was: " + v);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long entityId, Class entityClass) {
        EntityDao dao = getDao(entityClass);

        TxnData txnData = allTxnData.get();
        Long id = entityId;
        Integer previousVersion = txnData.idsAndVersions.get(id);
        OptimisticNamedCache onc = getOnc(dao, allTxnData.get());

        try {
            onc.delete(id, new EqualsFilter(new ReflectionExtractor("getVersion"), previousVersion));

            txnData.idsAndVersions.remove(id);
        }
        catch (PredicateFailedException f) {
            EntityImpl a = (EntityImpl) onc.get(id);
            int v = (a != null) ? SpiHelper.extractVersion(a) : -1;

            throw new RuntimeException(
                    "Optimistic concurrency error occurred while deleting entity with id " + id +
                            ". Expected version in the cache: " + previousVersion + " but actual version was: " + v);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //----------------

    @Override
    public void begin() {
        Connection connection = connectionFactory.createConnection(classLoader);
        connection.setAutoCommit(false);
        connection.setIsolationLevel(Isolation.READ_COMMITTED);
        connection.setEager(false);
        connection.setTransactionTimeout(300);

        allTxnData.set(new TxnData(connection));
    }

    @Override
    public void commit() {
        allTxnData.get().commit();
        allTxnData.remove();
    }

    @Override
    public void rollback() {
        allTxnData.get().rollback();
        allTxnData.remove();
    }

    //----------------

    protected static class TxnData {
        protected final Connection connection;

        protected final HashMap<EntityDao, OptimisticNamedCache> daoMap;

        protected final HashMap<Long, Integer> idsAndVersions;

        public TxnData(Connection connection) {
            this.connection = connection;
            this.daoMap = new HashMap<EntityDao, OptimisticNamedCache>();
            this.idsAndVersions = new HashMap<Long, Integer>();
        }

        protected void commit() {
            discard(true);
        }

        protected void rollback() {
            discard(false);
        }

        private void discard(boolean commit) {
            try {
                if (commit) {
                    connection.commit();
                }
                else {
                    /*
                    This check is required for some inexplicable reason. Rollback on predicatefailure/lockfailure
                    seems to be doing an internal auto-close even before the call reaches this point.

                    This happens only if connection.eager = false.

                    So, without this check rollback() throws a ConnectionClosedException.
                    */
                    if (!connection.isClosed()) {
                        connection.rollback();
                    }
                }
            }
            finally {
                try {
                    if (!connection.isClosed()) {
                        connection.close();
                    }
                }
                catch (Exception e) {
                }
            }

            daoMap.clear();
            idsAndVersions.clear();
        }
    }

    public static class ComparableShell implements ExternalizableLite {
        protected int objectHash;

        protected byte[] object;

        String extId;

        int version;

        public ComparableShell() {
        }

        public ComparableShell(EntityImpl entity, Serializer serializer) {
            this.object = ExternalizableHelper.toByteArray(entity, serializer);
            this.objectHash = Arrays.hashCode(this.object);
            this.extId = entity.getExtId();
            this.version = SpiHelper.extractVersion(entity);
        }

        public String getExtId() {
            return extId;
        }

        public int getVersion() {
            return version;
        }

        public EntityImpl getConcept(ClassLoader cl) {
            return (EntityImpl) ExternalizableHelper.fromByteArray(object, cl);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ComparableShell)) {
                return false;
            }

            ComparableShell shell = (ComparableShell) o;

            if (objectHash != shell.objectHash) {
                return false;
            }
            if (version != shell.version) {
                return false;
            }
            if (extId != null ? !extId.equals(shell.extId) : shell.extId != null) {
                return false;
            }
            if (!Arrays.equals(object, shell.object)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = objectHash;
            result = 31 * result + (extId != null ? extId.hashCode() : 0);
            result = 31 * result + version;
            return result;
        }

        @Override
        public void readExternal(DataInput dataInput) throws IOException {
            int b = dataInput.readInt();
            object = new byte[b];
            dataInput.readFully(object);
            objectHash = Arrays.hashCode(object);

            String s = dataInput.readUTF();
            extId = (s.length() == 0) ? null : s;
            version = dataInput.readInt();
        }

        @Override
        public void writeExternal(DataOutput dataOutput) throws IOException {
            dataOutput.writeInt(object.length);
            dataOutput.write(object);

            dataOutput.writeUTF(extId == null ? "" : extId);
            dataOutput.writeInt(version);
        }
    }
}
