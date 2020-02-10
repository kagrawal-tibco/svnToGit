package com.tibco.be.orcltojdbc;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;
import oracle.sql.ArrayDescriptor;
import oracle.sql.BLOB;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TypeDescriptor;

import com.tibco.be.oracle.OracleLOBManager;
import com.tibco.be.oracle.PropertyArrayMap;
import com.tibco.be.oracle.PropertyAtomMap;
import com.tibco.be.oracle.PropertyAtomSimpleMap;
import com.tibco.be.oracle.PropertyMap;
import com.tibco.be.oracle.impl.OracleActiveConnectionPool;
import com.tibco.be.oracle.impl.OracleAdapter.ConceptDescription;
import com.tibco.be.oracle.impl.OracleAdapter.SimpleEventDescription;
import com.tibco.be.oracle.impl.OracleAdapter.StateMachineTimeoutDescription;
import com.tibco.be.oracle.impl.OracleAdapter.TimeEventDescription;
import com.tibco.be.oracle.impl.OracleConceptMap;
import com.tibco.be.oracle.impl.OracleEntityMap;
import com.tibco.be.oracle.impl.OracleEventMap;
import com.tibco.be.oracle.impl.OracleTimeEventMap;
import com.tibco.be.oracle.serializers.OracleConceptDeserializer;
import com.tibco.be.oracle.serializers.OracleConceptDeserializer_NoNullProps;
import com.tibco.be.oracle.serializers.OracleConceptSerializerImpl;
import com.tibco.be.oracle.serializers.OracleConceptSerializer_NoNullProps;
import com.tibco.be.oracle.serializers.OracleEventDeserializer;
import com.tibco.be.oracle.serializers.OracleEventSerializer_V2;
import com.tibco.be.oracle.serializers.OracleSerializer;
import com.tibco.be.oracle.serializers.OracleStateMachineDeserializer;
import com.tibco.be.oracle.serializers.OracleStateMachineSerializer;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
import com.tibco.cep.runtime.service.cluster.system.EntityIdMask;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTupleImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

public class BSMigrationOrclAdapter {

    private RuleServiceProvider rsp;
    private com.tibco.cep.kernel.service.logging.Logger logger;
    private ClassRegistry classRegistry = null;

    private ThreadLocal m_currentConnection = new ThreadLocal();

    private OracleActiveConnectionPool m_pool;
    private boolean insertObjectTable;
    private boolean isNewSerEnabled = false;
    private boolean useExplicitTemporaryBlobs = false;

    public BSMigrationOrclAdapter(OracleActiveConnectionPool pool,
            boolean insertObjectTable, boolean useExplicitTemporaryBlobs) throws Exception {
        m_pool = pool;
        rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
        this.logger = this.rsp.getLogger(BSMigrationOrclAdapter.class);
        this.insertObjectTable = insertObjectTable;
        this.useExplicitTemporaryBlobs = useExplicitTemporaryBlobs;
        
        //Activate the pool
        m_pool.activate();
        //Initialize maps
        initializeOracleMaps(null);
    }

    /**
     * Initializes Oracle maps for all classes defined in the database.
     * Note: This initialization needs to be done 'per-connection', since 
     * Oracle-11g libraries have internal synchronization on connection
     * while dealing with types.
     * @param conn Connection to initialize types with
     * @throws Exception
     */
    private void initializeOracleMaps(OracleConnection conn) throws Exception {
        if (m_pool.areMapsInitialized(conn)) {
            return;
        }
        boolean localConn = false;
        String className = null;
        if (conn == null) {
            localConn = true;
            conn = getSqlConnection();
        }
        try {
            Map aliases = m_pool.getAliases();
            Map entityPropertiesMap = new HashMap();
            Map oracleTypes2Description = new HashMap();
            
            Map typeMappings = getTypeMappings();
            for (Iterator iter = typeMappings.keySet().iterator(); iter.hasNext();) {
                className = (String) iter.next();
                Class entityClass = ((ClassLoader) rsp.getTypeManager()).loadClass(className);
                if (com.tibco.cep.runtime.model.element.Concept.class.isAssignableFrom(entityClass)) {
                    // Handle Concepts
                    List mapping = (List) typeMappings.get(className);
                    StructDescriptor sd = StructDescriptor.createDescriptor(getQualifier()+(String) mapping.get(0), conn);
                    // Here we connect to DB to inquire Concept data type description...
                    OracleConceptMap conceptMap = new OracleConceptMap((ConceptDescription) new ConceptDescription(className), sd, (String) mapping.get(1), conn, aliases);
                    //conceptMap.setOracleTableName((String) mapping.get(1));
                    entityPropertiesMap.put(entityClass.getName(), conceptMap);
                    oracleTypes2Description.put(sd.getName(), conceptMap);
                } else if (StateMachineConceptImpl.StateTimeoutEvent.class.getName().equals(className)) {
                    List mapping = (List) typeMappings.get(className);
                    StructDescriptor sd = StructDescriptor.createDescriptor(getQualifier() +(String) mapping.get(0), conn);
                    OracleTimeEventMap eventMap = new OracleTimeEventMap(new StateMachineTimeoutDescription(), sd, (String) mapping.get(1), conn, aliases);
                    //eventMap.setOracleTableName((String) mapping.get(1));
                    entityPropertiesMap.put(className, eventMap);
                    oracleTypes2Description.put(sd.getName(), eventMap);
                } else if (SimpleEventImpl.class.isAssignableFrom(entityClass)) {
                    List mapping = (List) typeMappings.get(className);
                    //StructDescriptor sd=StructDescriptor.createDescriptor(name2OracleType(cache.getConceptModel()), conn);
                    StructDescriptor sd = StructDescriptor.createDescriptor(getQualifier()+(String) mapping.get(0), conn);
                    // Here we connect to DB to inquire Event data type description...
                    OracleEventMap eventMap = new OracleEventMap(new SimpleEventDescription(className), sd, (String) mapping.get(1), conn, aliases);
                    //eventMap.setOracleTableName(OracleDeployment.name2OracleTable(oracleType));
                    //eventMap.setOracleTableName((String) mapping.get(1));
                    entityPropertiesMap.put(className, eventMap);
                    oracleTypes2Description.put(sd.getName(), eventMap);
                } else {
                    List mapping = (List) typeMappings.get(className);
                    StructDescriptor sd = StructDescriptor.createDescriptor(getQualifier() +(String) mapping.get(0), conn);
                    OracleTimeEventMap eventMap = new OracleTimeEventMap(new TimeEventDescription(className), sd, (String) mapping.get(1), conn, aliases);
                    //eventMap.setOracleTableName((String) mapping.get(1));
                    entityPropertiesMap.put(entityClass.getName(), eventMap);
                    oracleTypes2Description.put(sd.getName(), eventMap);
                }
            }
            // Cache the results by connection
            m_pool.setMapsInitialized(entityPropertiesMap, oracleTypes2Description, conn);
        } catch (Exception ex) {
            this.logger.log(Level.WARN, ex, "Unable to register class " + className + " with Oracle ");
            throw ex;
        }
        finally {
            if (localConn) {
                releaseConnection();
            }
        }
    }

    public Map getClassRegistry() throws SQLException {
        Map classRegistry = new HashMap();
        // Delete the existing registry
        OraclePreparedStatement getStmt= (OraclePreparedStatement) this.getSqlConnection().prepareStatement("SELECT className, typeId FROM ClassRegistry");
        ResultSet rs= getStmt.executeQuery();
        while (rs.next()) {
            String entityClz= (String) rs.getString(1);
            Integer typeId= (Integer) rs.getInt(2);
            classRegistry.put(entityClz, typeId);
        }
        rs.close();
        getStmt.close();
        return classRegistry;
    }

//    public ConceptsCursorIterator loadStateMachines(int typeId) throws Exception {
//      Class entityClz=classRegistry.getClass(typeId);
//        String tableName=generatedOracleTableName(entityClz.getName());
//        if (tableName != null) {
//            if (logger.isInfo()) {
//                logger.logInfo("Recovering entries from " + tableName);
//            }
//            StringBuffer queryBuf = new StringBuffer(100);
//
//            OracleCallableStatement stmt=this.getLoadConceptsStatement(entityClz.getName(), tableName, queryBuf);
//            stmt.registerOutParameter(1, OracleTypes.CURSOR);
////            stmt.setString(2, entityClz.getName());
////            if(multisite){
////                stmt.setLong(3, _SITEID);
////            }
//            if (logger.isOrclLv1()) {
//                queryBuf.append(" (" +  "<cursor>, " + entityClz.getName() + ", ");
//                if(multisite){
//                  queryBuf.append(_SITEID);
//                }
//                queryBuf.append(")");
//              logger.logOrclLv1(queryBuf.toString());
//            }
//
//            stmt.execute();
//
//            if (_ISNEWSERENABLED) {
//                return new ConceptsCursorIterator((OracleResultSet) stmt.getCursor(1), true);
//            } else {
//                return new ConceptsCursorIterator((OracleResultSet) stmt.getCursor(1), false);
//            }
//
//        }
//        return null;
//    }

    public int countEntities(int typeId, long maxEntityId) throws Exception {
        int count = 0;
        Class entityClz=classRegistry.getClass(typeId);
        String tableName=generatedOracleTableName(entityClz.getName());
        if (tableName != null) {
            StringBuffer queryBuf = new StringBuffer(100);
            try {
                OracleCallableStatement stmt=this.getCountEntitiesStatement(entityClz.getName(), tableName, queryBuf);
                stmt.registerOutParameter(1, OracleTypes.INTEGER);
                stmt.setLong(2, maxEntityId);
                stmt.execute();
            
                count = stmt.getInt(1);
            } catch (Exception e) {
                this.logger.log(Level.WARN, "Counting entity %s failed with: %s", entityClz.getName(), e.getMessage());
                this.logger.log(Level.WARN, "Failed query: %s", queryBuf);
            }
       }
       return count;
    }

    /**
     *
     * @throws Exception
     */
     public ConceptsCursorIterator loadConcepts(int typeId, long maxEntityId) throws Exception {
        Class entityClz=classRegistry.getClass(typeId);
        String tableName=generatedOracleTableName(entityClz.getName());
        if (tableName != null) {
            StringBuffer queryBuf = new StringBuffer(100);
            try {
                OracleCallableStatement stmt;
                // If max entityId is < 0; means there is no tracking for this 
                // Entity, and therefore no reason to query filtered by entityId.
                if (maxEntityId < 0) {
                    stmt=this.getLoadConceptsStatement(entityClz.getName(), tableName, queryBuf);
                    stmt.registerOutParameter(1, OracleTypes.CURSOR);
                } else {
                    stmt=this.getLoadConceptsStatementWithFilter(entityClz.getName(), tableName, queryBuf);
                    stmt.registerOutParameter(1, OracleTypes.CURSOR);
                    stmt.setLong(2, maxEntityId);
                }
                this.logger.log(Level.DEBUG, "%s (<cursor>, %s)", queryBuf, entityClz.getName());                
                stmt.execute();
                return new ConceptsCursorIterator((OracleResultSet) stmt.getCursor(1));
            } catch (Exception e) {
                this.logger.log(Level.WARN, "Loading concepts %s failed with: %s", entityClz.getName(), e.getMessage());
                this.logger.log(Level.WARN, "Failed query: %s", queryBuf);
            }
        }
        return null;
    }

    public EventsCursorIterator loadEvents(int typeId, long maxEntityId) throws Exception {
        Class entityClz=classRegistry.getClass(typeId);
        String tableName=generatedOracleTableName(entityClz.getName());
        if (tableName != null) {
            StringBuffer queryBuf = new StringBuffer(100);
            try {
                OracleCallableStatement stmt;
                // If max entityId is < 0; means there is no tracking for this 
                // Entity, and therefore no reason to query filtered by entityId.
                if (maxEntityId < 0) {
                    stmt=this.getLoadEventsStatement(entityClz.getName(), tableName, queryBuf);
                    stmt.registerOutParameter(1, OracleTypes.CURSOR);
                } else {
                    stmt=this.getLoadEventsStatementWithFilter(entityClz.getName(), tableName, queryBuf);
                    stmt.registerOutParameter(1, OracleTypes.CURSOR);
                    stmt.setLong(2, maxEntityId);
                }
                this.logger.log(Level.DEBUG, "%s (<cursor>, %s)", queryBuf, entityClz.getName());
                stmt.execute();
                return new EventsCursorIterator((OracleResultSet) stmt.getCursor(1));
            } catch (Exception e) {
                this.logger.log(Level.WARN, "Loading events %s failed with: %s", entityClz.getName(), e.getMessage());
                this.logger.log(Level.WARN, "Failed query: %s", queryBuf);
            }
        }
        return null;
    }

    //OracleAdaptor is always active, unless a reconnection is being tried
    public boolean isActive() {
        //return getConnectionPool().isAvailable();
        return true;
    }

    public OracleActiveConnectionPool getConnectionPool() {
        return m_pool;
    }

    /**
     *
     * @throws SQLException
     */
    public void rollback(boolean releaseConnection){
        OracleConnection cnx=(OracleConnection) m_currentConnection.get();
        try {
            if (cnx != null) {
                cnx.rollback();
            }
        } catch (SQLException sqlex) {
        } finally {
            if (releaseConnection)
                releaseConnection();
        }
    }

    /**
     *
     * @throws SQLException
     */
    public void commit() throws SQLException{
        commit(this.getSqlConnection(),true);
    }

    /**
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        releaseConnection();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public OracleConnection getSqlConnection() throws SQLException{
        if (m_currentConnection.get() == null) {
            m_currentConnection.set(m_pool.getConnection());
        }
        return (OracleConnection) m_currentConnection.get();
    }

    public OracleConnection getCurrentConnection() {
        return (OracleConnection) m_currentConnection.get();
    }

    /**
     *
     * @throws SQLException
     */
    public void releaseConnection() {
        if (m_currentConnection.get() != null) {
            try {
                OracleLOBManager.free();
                ((Connection)m_currentConnection.get()).rollback();
                ((Connection)m_currentConnection.get()).close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            m_currentConnection.set(null);
        }
    }

    public HashMap getWorkItems() throws Exception {
        HashMap ret = new HashMap();
        StringBuffer query = new StringBuffer("SELECT workKey, workQueue, scheduledTime, workStatus, work from WorkItems");
        Connection connection = this.getSqlConnection();
        OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement(query.toString());
        ResultSet rs=stmt.executeQuery();
        while (rs.next()) {
            WorkTuple item= createWorkTuple(rs);
            ret.put(item.getWorkId(), item);
        }
        rs.close();
        stmt.close();
        return ret;
    }

    private WorkTuple createWorkTuple(ResultSet rs) throws Exception {
        WorkTuple item = new WorkTuple();
        String workId= rs.getString(1);
        item.setWorkId(workId);
        item.setWorkQueue(rs.getString(2));
        item.setScheduledTime(rs.getLong(3));
        item.setWorkStatus((rs.getInt(4)));
        BLOB blob = (BLOB) rs.getBlob(5);

        blob.open(BLOB.MODE_READONLY);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = blob.getBinaryStream();
        int bufferSize = blob.getBufferSize();
        byte[] buffer = new byte[bufferSize];
        int bytesRead = 0;
        while ((bytesRead = is.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        byte[] buf = baos.toByteArray();
        item.setBuf(buf);
        is.close();
        blob.close();
        if (blob.isTemporary())
            BLOB.freeTemporary(blob);
        return item;
    }
    
    class ConceptsIterator implements Iterator {
        OracleResultSet resultSet;

        ConceptsIterator (OracleResultSet resultSet) {
            this.resultSet=resultSet;
        }

        public boolean hasNext() {
            try {
                boolean hasNext=resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    STRUCT ret=resultSet.getSTRUCT(1);
                    com.tibco.cep.runtime.model.element.Concept cept= createConcept(ret);
                    return cept;
                } else {
                    throw new Exception("ConceptsIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        public void close() {
            try {
                Statement stmt=resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet=null;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    class ConceptsWithVersionIterator implements Iterator {
        OracleResultSet resultSet;
        boolean isStateMachine=false;

        ConceptsWithVersionIterator (OracleResultSet resultSet) {
            this.resultSet=resultSet;
        }

        ConceptsWithVersionIterator (OracleResultSet resultSet, boolean isStateMachine) {
            this.resultSet=resultSet;
            this.isStateMachine=isStateMachine;
        }

        public boolean hasNext() {
            try {
                boolean hasNext=resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    int version=resultSet.getInt(1);
                    STRUCT ret=resultSet.getSTRUCT(2);
                    if (isStateMachine) {
                        StateMachineConcept sm= createStateMachine(ret);
                        ((ConceptImpl) sm).setVersion(version);
                        return sm;
                    } else {
                        com.tibco.cep.runtime.model.element.Concept cept= createConcept(ret);
                        ((ConceptImpl) cept).setVersion(version);
                        return cept;
                    }
                } else {
                    throw new Exception("ConceptsWithVersionIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        public void close() {
            try {
                Statement stmt=resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet=null;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    class ConceptsCursorIterator implements Iterator {
        OracleResultSet resultSet;
        boolean isStateMachine=false;

        ConceptsCursorIterator (OracleResultSet resultSet) {
            this.resultSet=resultSet;
        }
        ConceptsCursorIterator (OracleResultSet resultSet, boolean isStateMachine) {
            this.resultSet=resultSet;
            this.isStateMachine=isStateMachine;
        }
        public boolean hasNext() {
            try {
                boolean hasNext=resultSet.next(); // Concurrent
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    int version=resultSet.getInt(1);
                    STRUCT ret=resultSet.getSTRUCT(2);
                    if (isStateMachine) {
                        StateMachineConcept sm= createStateMachine(ret);
                        ((ConceptImpl) sm).setVersion(version);
                        return sm;
                    } else {
                        com.tibco.cep.runtime.model.element.Concept cept= createConcept(ret);
                        ((ConceptImpl) cept).setVersion(version);
                        return cept;
                    }
                } else {
                    throw new Exception("ConceptsCursorIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        public void close() {
            try {
                Statement stmt=resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet=null;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    class EventsIterator implements Iterator {
        OracleResultSet resultSet;

        EventsIterator (OracleResultSet resultSet) {
            this.resultSet=resultSet;
        }

        public boolean hasNext() {
            try {
                boolean hasNext=resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    STRUCT ret=resultSet.getSTRUCT(1);
                    com.tibco.cep.kernel.model.entity.Event evt= createEvent(ret);
                    return evt;
                } else {
                    throw new Exception("EventsIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        public void close() {
            try {
                Statement stmt=resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet=null;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    class EventsCursorIterator implements Iterator {
        OracleResultSet resultSet;

        EventsCursorIterator (OracleResultSet resultSet) {
            this.resultSet=resultSet;
        }

        public boolean hasNext() {
            try {
                boolean hasNext=resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    STRUCT ret=resultSet.getSTRUCT(1);
                    com.tibco.cep.kernel.model.entity.Event evt= createEvent(ret);
                    return evt;
                } else {
                    throw new Exception("EventsIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        public void close() {
            try {
                Statement stmt=resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet=null;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    class KeyPairIterator implements Iterator {
        OracleResultSet resultSet;
        KeyPairIterator (OracleResultSet resultSet) {
            this.resultSet=resultSet;
        }
        public boolean hasNext() {
            try {
                boolean hasNext=resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    long id= resultSet.getLong(1);
                    String extId= resultSet.getString(2);
                    return new ObjectTupleImpl(id, extId);
                } else {
                    throw new Exception("KeyPairIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        public void close() {
            try {
                Statement stmt=resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet=null;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    class ObjectTableIterator implements Iterator {
        OracleResultSet resultSet;
        ObjectTableIterator (OracleResultSet resultSet) {
            this.resultSet=resultSet;
        }
        public boolean hasNext() {
            try {
                boolean hasNext=resultSet.next();
                if (!hasNext) {
                    close();
                    releaseConnection();
                }
                return hasNext;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public Object next() {
            try {
                if (resultSet != null) {
                    long id= resultSet.getLong(1);
                    String extId= resultSet.getString(2);
                    String className=resultSet.getString(3);
                    return new ObjectTupleImpl(id, extId, classRegistry.getTypeId(className));
                } else {
                    throw new Exception("ObjectTableIterator: Result Set Already Closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            return;
        }

        public void close() {
            try {
                Statement stmt=resultSet.getStatement();
                resultSet.close();
                stmt.close();
                resultSet=null;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public void setClassRegistry (ClassRegistry classRegistry) {
        this.classRegistry = classRegistry;
    }

    private ConceptDeserializer createConceptDeserializer(OracleConceptMap desc, OracleConnection oracle, oracle.sql.Datum[] attributes) {
        if (isNewSerEnabled) {
            return new OracleConceptDeserializer_NoNullProps(desc, oracle, attributes);
        } else {
            return new OracleConceptDeserializer(desc, oracle, attributes);
        }
    }

    /**
     *
     * @param oracleDatum
     * @return
     * @throws Exception
     */
    private com.tibco.cep.runtime.model.element.Concept createConcept(STRUCT oracleDatum) throws Exception {
        com.tibco.cep.runtime.model.element.Concept cept=null;
        if (oracleDatum != null) {
            String oracleType = oracleDatum.getDescriptor().getName();
            OracleConceptMap desc= (OracleConceptMap) getOracleTypesMap(getSqlConnection()).get(oracleType);
            if (desc != null) {
                Class conceptClz= desc.getEntityClass(rsp);
                ConceptDeserializer deser=createConceptDeserializer(desc, getSqlConnection(), oracleDatum.getOracleAttributes());
                cept= (com.tibco.cep.runtime.model.element.Concept) desc.newInstance(conceptClz, deser.getId(), deser.getExtId());
                cept.deserialize(deser);
            }
        }
        return cept;
    }

    private StateMachineConcept createStateMachine(STRUCT oracleDatum) throws Exception {
        StateMachineConcept sm=null;
        if (oracleDatum != null) {
            String oracleType = oracleDatum.getDescriptor().getName();
            OracleConceptMap desc= (OracleConceptMap) getOracleTypesMap(getSqlConnection()).get(oracleType);
            if (desc != null) {
                Class conceptClz= desc.getEntityClass(rsp);
                OracleStateMachineDeserializer deser= new OracleStateMachineDeserializer(desc, getSqlConnection(), oracleDatum.getOracleAttributes());
                sm= (StateMachineConcept) desc.newInstance(conceptClz, deser.getId(), deser.getExtId());
                sm.deserialize(deser);
            }
        }
        return sm;
    }

    private void setNull(OraclePreparedStatement stmt, OracleConceptMap desc, int oracleIndex, int stmtIndex) throws SQLException{
        PropertyMap pm = desc.getPropertyMapAt(oracleIndex);
        if (pm instanceof PropertyAtomSimpleMap) {
            int sqlTypeCode=((PropertyAtomSimpleMap) pm).getSQLTypeCode();
            TypeDescriptor td=((PropertyAtomSimpleMap) pm).getTypeDescriptor();
            if (td != null) {
                stmt.setNull(stmtIndex, sqlTypeCode, td.getTypeName());
            } else {
                stmt.setNull(stmtIndex, sqlTypeCode);
            }
        } else if (pm instanceof PropertyAtomMap) {
            int sqlTypeCode=((PropertyAtomMap) pm).getSQLTypeCode();
            TypeDescriptor td=((PropertyAtomMap) pm).getTypeDescriptor();
            if (td != null) {
                stmt.setNull(stmtIndex, Types.STRUCT, td.getTypeName());
            } else {
                throw new RuntimeException("Fatal Exception " + pm.getColumnName());
            }
        } else if (pm instanceof PropertyArrayMap){
            int sqlTypeCode=((PropertyArrayMap) pm).getSQLTypeCode();
            ArrayDescriptor td=(ArrayDescriptor) ((PropertyArrayMap) pm).getTypeDescriptor();
            //stmt.setArray(stmtIndex,null);
            //stmt.setNull(stmtIndex, sqlTypeCode, td.getTypeName());
            stmt.setArray(stmtIndex,new oracle.sql.ARRAY(td,stmt.getConnection(), null));
        } else {
            if (oracleIndex == 1) { //extId
                stmt.setNull(stmtIndex, Types.VARCHAR);
            } else {
                this.logger.log(Level.ERROR, "Unknown property type");
                throw new RuntimeException("Fatal Exception " + pm.getColumnName());
            }
        }
    }

    private OracleSerializer createConceptSerializer(OracleConceptMap desc, OracleConnection oracle, String qualifier) throws Exception{
        if (isNewSerEnabled) {
            return new OracleConceptSerializer_NoNullProps(desc, oracle, qualifier);
        } else {
            return new OracleConceptSerializerImpl(desc, oracle, qualifier);
        }
    }

    private com.tibco.cep.kernel.model.entity.Event createEvent(STRUCT oracleDatum) throws Exception{
        com.tibco.cep.kernel.model.entity.Event event=null;
        if (oracleDatum != null) {
            String oracleType = oracleDatum.getDescriptor().getName();
            OracleEventMap desc= (OracleEventMap) getOracleTypesMap(getSqlConnection()).get(oracleType);
            if (desc != null) {
                Class eventClz= desc.getEntityClass(rsp);
                OracleEventDeserializer deser= new OracleEventDeserializer(rsp,desc, getSqlConnection(), oracleDatum.getOracleAttributes());
                event = (com.tibco.cep.kernel.model.entity.Event) desc.newInstance(eventClz, deser.getId(), deser.getExtId());
                if (event instanceof SimpleEvent) {
                    ((SimpleEvent)event).deserialize(deser);
                } else {
                    ((TimeEvent)event).deserialize(deser);
                }
            }
        }
        return event;
    }

    private Map getEntityPropsMap(OracleConnection conn) throws Exception {
        if (!m_pool.areMapsInitialized(conn)) {
            initializeOracleMaps(conn);
        }
        return m_pool.getEntityPropsMap(conn);
    }

    private Map getOracleTypesMap(OracleConnection conn) throws Exception {
        if (!m_pool.areMapsInitialized(conn)) {
            initializeOracleMaps(conn);
        }
        return m_pool.getOracleTypesMap(conn);
    }
    
    private OracleCallableStatement getCountEntitiesStatement(String className, String tableName, StringBuffer queryBuf) throws SQLException{
        queryBuf.append("begin SELECT COUNT(*) INTO ?  FROM " + tableName + " T WHERE T.ENTITY.ID$ > ? ; end; ");
        return (OracleCallableStatement) getSqlConnection().prepareCall(queryBuf.toString());
    }

    private OracleCallableStatement getLoadConceptsStatement(String className, String tableName, StringBuffer queryBuf) throws SQLException {
        queryBuf.append("begin open ? for SELECT T.CACHEID, T.ENTITY  FROM " + tableName + " T ; end; ");
        this.logger.log(Level.DEBUG, "Load concepts query: %s", queryBuf);
        return (OracleCallableStatement) getSqlConnection().prepareCall(queryBuf.toString());
    }

    private OracleCallableStatement getLoadConceptsStatementWithFilter(String className, String tableName, StringBuffer queryBuf) throws SQLException{
        queryBuf.append("begin open ? for SELECT T.CACHEID, T.ENTITY  FROM " + tableName + " T WHERE T.ENTITY.ID$ > ? ORDER BY T.ENTITY.ID$ ; end; ");
        this.logger.log(Level.DEBUG, "Load concepts ordered: %s", queryBuf);
        return (OracleCallableStatement) getSqlConnection().prepareCall(queryBuf.toString());
    }

    private OracleCallableStatement getLoadEventsStatement(String className, String tableName, StringBuffer queryBuf) throws SQLException{
        queryBuf.append("begin open ? for SELECT T.ENTITY  FROM " + tableName + " T ; end; ");
        this.logger.log(Level.DEBUG, "Load events query: %s", queryBuf);
        return (OracleCallableStatement) getSqlConnection().prepareCall(queryBuf.toString());
    }

    private OracleCallableStatement getLoadEventsStatementWithFilter(String className, String tableName, StringBuffer queryBuf) throws SQLException{
        queryBuf.append("begin open ? for SELECT T.ENTITY  FROM " + tableName + " T WHERE T.ENTITY.ID$ > ? ORDER BY T.ENTITY.ID$ ; end; ");
        this.logger.log(Level.DEBUG, "Load events ordered: %s", queryBuf);
        return (OracleCallableStatement) getSqlConnection().prepareCall(queryBuf.toString());
    }

    private Map getTypeMappings() throws Exception {
        Map map = new HashMap();
        OraclePreparedStatement stmt= (OraclePreparedStatement) getSqlConnection().prepareStatement("SELECT CLASSNAME, ORACLETYPE, oracleTable FROM ClassToOracleType");
        OracleResultSet rs=(OracleResultSet) stmt.executeQuery();
        while (rs.next())  {
            List info = new ArrayList(2);
            info.add(rs.getString(2).trim().toUpperCase());
            info.add(rs.getString(3).trim().toUpperCase());
            map.put(rs.getString(1).trim(), info);
        }
        rs.close();
        stmt.close();

        // Hack for classes not defined in table ClassToOracleType
        // StateMachineConceptImpl.StateTimeoutEvent.class
        String className = StateMachineConceptImpl.StateTimeoutEvent.class.getName();
        List info = new ArrayList(2);
        info.add("T_STATEMACHINE_TIMEOUT");
        info.add("StateMachineTimeout$$");
        map.put(className, info);

        return map;
    }

    private void commit(OracleConnection cnx, boolean releaseConnection) throws SQLException{
        cnx.commit();
        if (releaseConnection) {
            releaseConnection();
        }
    }

    private String getQualifier() throws SQLException{
        return m_pool.getQualifier();
    }

    public String generatedOracleTableName(String className) {
        OracleEntityMap map = null;
        try {
            map = (OracleEntityMap) getEntityPropsMap(null).get(className);
        } catch (Exception ignore) {
        }

        if (map != null) {
            return map.getOracleTableName();
        }
        return null;
    }

    public void getWorkItems(Object object) {
        // TODO Auto-generated method stub  
    }

    public long insertEvents(String className, Map<Long, Event> entries) throws Exception{
        List serializers = new ArrayList();
        OracleEventMap entityMap= (OracleEventMap) getEntityPropsMap(getSqlConnection()).get(className);
        if (entityMap == null) {
            throw new Exception("Cannot find map for " + className);
        }
        long maxEntityId = 0;
        OraclePreparedStatement stmt = null;
        try {
            String priSql = entityMap.getInsertStatement().replaceFirst("insert", "insert /*+ append */");
            this.logger.log(Level.DEBUG, priSql);
            stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(priSql);
            //stmt.setExecuteBatch(entries.size());
            
            Iterator all_entries= entries.entrySet().iterator();
            Event event = null;
            while (all_entries.hasNext()) {
                try {
                    Map.Entry<Long, Event> entry = (Map.Entry<Long, Event>) all_entries.next();
                    Long id = entry.getKey();
                    maxEntityId = id;
                    event = entry.getValue();
                    OracleEventSerializer_V2 serializer = new OracleEventSerializer_V2(entityMap, stmt, 2, getSqlConnection(),getQualifier(),useExplicitTemporaryBlobs);
                    serializers.add(serializer);
                    //Datum[] attrs = serializer.getOracleAttributes();
                    stmt.setOracleObject(1, new oracle.sql.NUMBER(1));
                    if (event instanceof SimpleEvent) {
                        ((SimpleEvent)event).serialize(serializer);
                    } else {
                        ((TimeEvent)event).serialize(serializer);
                    }
                    stmt.addBatch();
    
                    // insert into ObjectTable also
                    if (insertObjectTable) {
                        insertObjectTable(event);
                    }
                } catch (Exception e) {
                    this.logger.log(Level.ERROR, "Failed to insert event of type %s, with id=%s, extid=%s",
                            className, event.getId(), event.getExtId());
                    throw e;
                }
            }
            stmt.executeBatch();
            return maxEntityId;
            // stmt.setExecuteBatch(entries.size());
        } catch (Exception ex) {
            throw ex;
        } finally {
            for (int i=0; i < serializers.size(); i++) {
                ((OracleEventSerializer_V2) serializers.get(i)).releaseLobTempSpace();
            }
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
        }
    }

    public long insertConcepts(String className, Map<Long, Concept> entries) throws Exception {
        OracleConceptMap entityMap = (OracleConceptMap) getEntityPropsMap(getSqlConnection()).get(className);
        if (entityMap == null) {
            throw new Exception("Cannot find map for " + className);
        }
        long maxEntityId = 0;
        OraclePreparedStatement stmt = null;
        try {
            String priSql = entityMap.getInsertStatement().replaceFirst("insert", "insert /*+ append */");
            this.logger.log(Level.DEBUG, priSql);
            stmt = (OraclePreparedStatement) getSqlConnection().prepareStatement(priSql);
            //stmt.setExecuteBatch(entries.size());

            Iterator all_entries = entries.entrySet().iterator();
            Concept cept = null;
            while (all_entries.hasNext()) {
                try {
                    Map.Entry<Long, Concept> entry= (Map.Entry<Long, Concept>) all_entries.next();
                    Long id = entry.getKey();
                    maxEntityId = id;
                    cept = entry.getValue();
                    OracleSerializer serializer = createConceptSerializer(entityMap, getSqlConnection(), getQualifier());
                    cept.serialize((ConceptSerializer) serializer);
                    if (serializer.hasErrors()) {
                        throw new Exception("Error in Serialization of " + cept + " with message " + serializer.getErrorMessage());
                    }
                    Datum[] attrs = serializer.getOracleAttributes();
                    stmt.setOracleObject(1, new oracle.sql.NUMBER(((VersionedObject) cept).getVersion()));
                    for (int i = 0; i < attrs.length; i++) {
                        if (attrs[i] != null) {
                            stmt.setOracleObject(i + 2, attrs[i]);
                        } else {
                            setNull(stmt, entityMap, i, i + 2);
                        }
                    }
                    stmt.addBatch();

                    // insert into ObjectTable also
                    if (insertObjectTable) {
                        insertObjectTable(cept);
                    }
                } catch (Exception e) {
                    this.logger.log(Level.ERROR, "Failed to insert event of type %s, with id=%s, extid=%s",
                            className, cept.getId(), cept.getExtId());
                    throw e;
                }
            }
            stmt.executeBatch();
            return maxEntityId;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (stmt != null) {
                stmt.clearBatch();
                stmt.close();
            }
        }
    }

    @Deprecated // Mark as @deprecated so it should not used until completely implemented
    public long insertStateMachines(String className, Map entries) throws Exception {
        OracleConceptMap entityMap = (OracleConceptMap) getEntityPropsMap(getSqlConnection()).get(className);
        if (entityMap == null) {
            throw new Exception("Cannot find map for " + className);
        }
        long maxEntityId = 0;
        OraclePreparedStatement stmt= (OraclePreparedStatement) getSqlConnection().prepareStatement(entityMap.getInsertStatement());
        stmt.setExecuteBatch(entries.size());

        Iterator all_entries= entries.entrySet().iterator();
        while (all_entries.hasNext()) {
            Map.Entry<Long, StateMachineConcept> entry= (Map.Entry<Long, StateMachineConcept>) all_entries.next();
            Long id = entry.getKey();
            maxEntityId = id;
            StateMachineConcept sm= entry.getValue();
            OracleStateMachineSerializer serializer = new OracleStateMachineSerializer(entityMap, getSqlConnection(), getQualifier());
            sm.serialize(serializer);
            Datum[] attrs = serializer.getOracleAttributes();
            stmt.setOracleObject(1, new oracle.sql.NUMBER(((VersionedObject) sm).getVersion()));
            for (int i = 0; i < attrs.length; i++) {
                if (attrs[i] != null) {
                    stmt.setOracleObject(i + 2, attrs[i]);
                } else {
                    setNull(stmt, entityMap, i, i + 2);
                }
            }
            stmt.executeUpdate();
        }
        stmt.sendBatch();
        stmt.close();
        return maxEntityId;
    }

    private void insertObjectTable(Entity entity) throws Exception {
        PreparedStatement stmt = this.insertObjectTableStatement();
        stmt.setLong(1, entity.getId());
        stmt.setLong(2, EntityIdMask.getMaskedId(entity.getId()));
        stmt.setLong(3, EntityIdMask.getEntityId(entity.getId()));
        if (entity.getExtId() != null) {
            stmt.setString(4, entity.getExtId());
        } else {
            stmt.setNull(4, java.sql.Types.VARCHAR);
        }
        stmt.setString(5, entity.getClass().getName());
        stmt.setInt(6, 0); // Always set to 0
        stmt.setNull(7, java.sql.Types.NUMERIC);
        stmt.executeUpdate();
        stmt.close();
    }

    private PreparedStatement insertObjectTableStatement() throws SQLException {
        String objSql = new String("insert into OBJECTTABLE(GLOBALID, SITEID, ID, EXTID, CLASSNAME, ISDELETED, TIMEDELETED) values (?,?,?,?,?,?,?)");
        objSql = objSql.replaceFirst("insert", "insert /*+ append */");
        this.logger.log(Level.DEBUG, objSql);
        return getSqlConnection().prepareStatement(objSql);
    }

    public void saveWorkItems(HashMap workItems) throws Exception {
        Iterator allWorkItems = workItems.entrySet().iterator();
        while (allWorkItems.hasNext()) {
            Map.Entry entry = (Map.Entry) allWorkItems.next();
            WorkTuple tuple = (WorkTuple) entry.getValue();
            saveWorkItem(tuple);
        }
    }

    public void saveWorkItem(WorkTuple tuple) throws Exception {
        BLOB blob = null;
        OracleConnection connection = this.getSqlConnection();
        OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement("INSERT INTO WorkItems(workKey, workQueue, scheduledTime, workStatus, work) VALUES (?,?,?,?,?)");
        stmt.setString(1, tuple.getWorkId());
        stmt.setString(2, tuple.getWorkQueue());
        stmt.setLong(3, tuple.getScheduledTime());
        stmt.setInt(4, tuple.getWorkStatus());
        if (this.useExplicitTemporaryBlobs) {
            byte[] b = tuple.getBuf();
            if (b.length > 1024) {
                blob = (BLOB)OracleLOBManager.createBLOB(connection, tuple.getBuf());
                stmt.setBLOB(5, blob);
            } else {
                stmt.setBytesForBlob(5, tuple.getBuf());
            }
        } else {
            stmt.setBytesForBlob(5, tuple.getBuf());
        }
        this.logger.log(Level.DEBUG,
                "INSERT INTO WorkItems (workKey, workQueue, scheduledTime, workStatus, work) VALUES (%s, %s, %s, %s, <blob>)",
                tuple.getWorkId(), tuple.getWorkQueue(), tuple.getScheduledTime(), tuple.getWorkStatus());
        stmt.executeUpdate();
        stmt.close();
    }
}
