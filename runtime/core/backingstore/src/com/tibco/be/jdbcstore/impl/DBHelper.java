package com.tibco.be.jdbcstore.impl;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.TimeZone;

import com.tibco.be.jdbcstore.DateTimeValueTuple;
import com.tibco.be.jdbcstore.HistoryTableTuple;
import com.tibco.be.jdbcstore.HistoryTuple;
import com.tibco.be.jdbcstore.RDBMSType;
import com.tibco.be.jdbcstore.ReverseRefTuple;
import com.tibco.be.jdbcstore.serializers.DBConceptDeserializer;
import com.tibco.be.jdbcstore.serializers.DBEventDeserializer;
import com.tibco.be.jdbcstore.serializers.DBStateMachineDeserializer;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class DBHelper {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DBHelper.class);
    static boolean _timestampUseDataTimeZone = false;
    static boolean _skipUnreferencedSecondaryTableEntries = true;
    static boolean _containedConceptArrayAddRemoveOnly = false;
    static boolean _skipUnmodifiedProperties = false;
    static boolean _skipNullValue = false;
    static boolean _skipReverseReferences = false;
    static LinkedList _referencedEntities = new LinkedList();
    
    public static Concept createConcept(DBConceptMap entityMap, Connection conn, Object[] attributes, Map secondaryAttributeMap) throws Exception {
        Concept cept=null;
        if (attributes == null || entityMap == null) {
            return null;
        }
        Class conceptClz = entityMap.getEntityClass();
        //ConceptDeserializer deser=createConceptDeserializer(desc, getSqlConnection(), oracleDatum.getOracleAttributes());
        ConceptDeserializer deser = new DBConceptDeserializer(entityMap, conn, attributes, secondaryAttributeMap);
        //DBConceptDeserializer deser= new DBConceptDeserializer(desc, getSqlConnection(), oracleDatum.getOracleAttributes());
        cept= (Concept) entityMap.newInstance(conceptClz, deser.getId(), deser.getExtId());
        cept.deserialize(deser);
        return cept;
    }

    public static StateMachineConcept createStateMachine(DBConceptMap entityMap, Connection conn, Object[] attributes) throws Exception {
        StateMachineConcept sm =null;
        if (attributes == null || entityMap == null) {
            return null;
        }
        Class smClz = entityMap.getEntityClass();
        DBStateMachineDeserializer deser= new DBStateMachineDeserializer(entityMap, conn, attributes, null/* secondary */);
        sm = (StateMachineConcept) entityMap.newInstance(smClz, deser.getId(), deser.getExtId());
        sm.deserialize(deser);
        return sm;
    }

    public static com.tibco.cep.kernel.model.entity.Event createEvent(RuleServiceProvider rsp, DBEventMap entityMap, Connection conn, Object[] attributes) throws Exception{
        com.tibco.cep.kernel.model.entity.Event event=null;
        if (attributes == null || entityMap == null) {
            // need a log message here
            return null;
        }
        Class eventClz= entityMap.getEntityClass();
        DBEventDeserializer deser = new DBEventDeserializer(rsp, entityMap, conn, attributes);
        event = (com.tibco.cep.kernel.model.entity.Event) entityMap.newInstance(eventClz, deser.getId(), deser.getExtId());
        if (event instanceof SimpleEvent) {
            ((SimpleEvent)event).deserialize(deser);
        } else {
            ((TimeEvent)event).deserialize(deser);
        }
        return event;
    }

    public static com.tibco.cep.kernel.model.entity.Event createEvent(RuleServiceProvider rsp, DBEventMap entityMap, Connection conn, ResultSet rs) throws Exception {
        @SuppressWarnings("unused")
        long version = rs.getLong(1);
        @SuppressWarnings("unused")
        long id = rs.getLong(2);
        Object[] attributes = new Object[entityMap.getFieldCount()];
        int fieldCount = entityMap.getFieldCount();
        for (int i=0; i<fieldCount; i++) {
            DBEntityMap.DBFieldMap fmap = entityMap.getFieldMap(i);
            attributes[fmap.dataObjectFieldIndex] =
                DBHelper.getColumnData(rs, 2 + fmap.tableFieldIndex, fmap.tableFieldMappingType);
        }
        return createEvent(rsp, entityMap, conn, attributes);
    }

    public static Object getColumnDataByRDFType(ResultSet rs, int index, int rdfType) throws Exception {
        Object val = null;
        switch (rdfType) {
            case RDFTypes.STRING_TYPEID:
                val = rs.getString(index);
                break;
            case RDFTypes.INTEGER_TYPEID:
                val = new Integer(rs.getInt(index));
                break;
            case RDFTypes.LONG_TYPEID:
                val = new Long(rs.getLong(index));
                break;
            case RDFTypes.DOUBLE_TYPEID:
                val = new Double(rs.getDouble(index));
                break;
            case RDFTypes.BOOLEAN_TYPEID:
                int i = rs.getInt(index);
                val = new Boolean(i != 0);
                break;
            case RDFTypes.DATETIME_TYPEID:
                String tz = rs.getString(index + 1);
                Timestamp ts = null;
                if ((_timestampUseDataTimeZone == true) && (tz != null)) {
                    ts = rs.getTimestamp(index, new GregorianCalendar(TimeZone.getTimeZone(tz)));
                } else {
                    ts = rs.getTimestamp(index);
                }
                val = new DateTimeValueTuple(ts, tz);
                break;
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                val = new Long(rs.getLong(index));
                break;
            case RDFTypes.CONCEPT_TYPEID:
            case RDFTypes.EVENT_TYPEID:
            case RDFTypes.TIME_EVENT_TYPEID:
            case RDFTypes.ADVISORY_EVENT_TYPEID:
                val = new Long(rs.getLong(index));
                //sqlType = java.sql.Types.NUMERIC;
                logger.log(Level.ERROR, "Property has unexpected entity type: %s", rdfType);
                break;
            default:
                val = rs.getString(index);
                logger.log(Level.ERROR, "Property has unrecognized type: %s", rdfType);
                break;
        }
        if (rs.wasNull()) {
            return null;
        }
        return val;
    }

    public static Object getColumnData(ResultSet rs, int index, int mapType) throws Exception {
        Object val = null;

        switch (mapType)
        {
            case DBEntityMap.FTYPE_STRING:
                val = rs.getString(index);
                break;
            case DBEntityMap.FTYPE_INTEGER:
                val = new Integer(rs.getInt(index));
                break;
            case DBEntityMap.FTYPE_LONG:
                val = new Long(rs.getLong(index));
                break;
            case DBEntityMap.FTYPE_DOUBLE:
                val = new Double(rs.getDouble(index));
                break;
            case DBEntityMap.FTYPE_BOOLEAN:
                int i = rs.getInt(index);
                val = new Boolean(i != 0);
                break;
            case DBEntityMap.FTYPE_DATETIME:
                String tz = rs.getString(index + 1);
                Timestamp ts = null;
                if ((_timestampUseDataTimeZone == true) && (tz != null)) {
                    ts = rs.getTimestamp(index, new GregorianCalendar(TimeZone.getTimeZone(tz)));
                } else {
                    ts = rs.getTimestamp(index);
                }
                val = new DateTimeValueTuple(ts, tz);
                break;
            case DBEntityMap.FTYPE_TIMESTAMP:
                val = rs.getTimestamp(index);
                break;
            case DBEntityMap.FTYPE_CHAR:
                val = rs.getString(index);
                break;
            case DBEntityMap.FTYPE_BLOB:
                //logger.log(Level.ERROR, "Property : " + index + " has blob type : " + mapType);
                val = getBlob(rs, index);
                break;
            case DBEntityMap.FTYPE_CLOB:
                //logger.log(Level.ERROR, "Property : " + index + " has clob type : " + mapType);
                val = null;
                break;
            default:
                val = rs.getString(index);
                logger.log(Level.ERROR, "Property: %s has unrecognized type: %s", index, mapType);
                break;
        }
        if (rs.wasNull()) {
            return null;
        }
        return val;
    }

    public static void setReferencedType(Class referencedType) {
        DBHelper._referencedEntities.add(referencedType.getName());
        Class superClass = referencedType.getSuperclass();
        while (superClass != null) {
            DBHelper._referencedEntities.add(superClass.getName());
            superClass = superClass.getSuperclass();
        }
        System.err.println("ReferencedTypes: " + DBHelper._referencedEntities);
    }
    
    public static boolean skipReverseRefs() {
    	return DBHelper._skipReverseReferences;
    }
    
    public static void setColumnDataBySqlType(PreparedStatement stmt, int index, int sqlType, Object value) throws Exception {
        if ((value == null)) {
            stmt.setNull(index, sqlType);
            return;
        }

        stmt.setObject(index, value, sqlType);
    }

    public static void setColumnData(PreparedStatement stmt, int index, int mapType, Object value) throws Exception {
        if (value == null) {
            switch (mapType) {
                case DBEntityMap.FTYPE_STRING:
                    stmt.setNull(index, java.sql.Types.VARCHAR);
                    break;
                case DBEntityMap.FTYPE_INTEGER:
                    stmt.setNull(index, java.sql.Types.INTEGER);
                    break;
                case DBEntityMap.FTYPE_LONG:
                    stmt.setNull(index, java.sql.Types.NUMERIC);
                    break;
                case DBEntityMap.FTYPE_DOUBLE:
                    stmt.setNull(index, java.sql.Types.DOUBLE);
                    break;
                case DBEntityMap.FTYPE_BOOLEAN:
                    stmt.setNull(index, java.sql.Types.INTEGER);
                    break;
                case DBEntityMap.FTYPE_DATETIME:
                    stmt.setNull(index, java.sql.Types.TIMESTAMP);
                    stmt.setNull(index + 1, java.sql.Types.VARCHAR);
                    break;
                case DBEntityMap.FTYPE_TIMESTAMP:
                    stmt.setNull(index, java.sql.Types.TIMESTAMP);
                    break;
                case DBEntityMap.FTYPE_CHAR:
                    stmt.setNull(index, java.sql.Types.VARCHAR);
                    break;
                default:
                    logger.log(Level.ERROR, "Set null position %s has unknown type: %s", index, mapType);
                    stmt.setNull(index, java.sql.Types.VARCHAR);
                    break;
            }
            return;
        }
        switch (mapType) {
            case DBEntityMap.FTYPE_STRING:
                stmt.setObject(index, value, java.sql.Types.VARCHAR);
                break;
            case DBEntityMap.FTYPE_INTEGER:
                stmt.setObject(index, value, java.sql.Types.INTEGER);
                break;
            case DBEntityMap.FTYPE_LONG:
                //stmt.setObject(index, value, java.sql.Types.NUMERIC);
                stmt.setLong(index, ((Long) value).longValue());
                break;
            case DBEntityMap.FTYPE_DOUBLE:
                stmt.setObject(index, value, java.sql.Types.DOUBLE);
                break;
            case DBEntityMap.FTYPE_BOOLEAN:
                stmt.setObject(index, value, java.sql.Types.INTEGER);
                break;
            case DBEntityMap.FTYPE_DATETIME:
            	String tz = ((DateTimeValueTuple) value).tz;
            	if (_timestampUseDataTimeZone && tz != null) {
            		stmt.setTimestamp(index, ((DateTimeValueTuple) value).ts, new GregorianCalendar(TimeZone.getTimeZone(tz)));
            	} else {
            		stmt.setObject(index, ((DateTimeValueTuple) value).ts, java.sql.Types.TIMESTAMP);
            	}
                stmt.setObject(index + 1, tz, java.sql.Types.VARCHAR);
                break;
            case DBEntityMap.FTYPE_TIMESTAMP:
                stmt.setObject(index, value, java.sql.Types.TIMESTAMP);
                break;
            case DBEntityMap.FTYPE_CHAR:
                stmt.setObject(index, value, java.sql.Types.VARCHAR);
                break;
            default:
                logger.log(Level.ERROR, "Set value position %s has unknown type: %s", index, mapType);
                stmt.setObject(index, value, java.sql.Types.VARCHAR);
                break;
        }
    }

    public static boolean populateSecondaryTable(DBEntityMap.DBFieldMap fMap, PreparedStatement stmt, Object value, Long pid) throws Exception {
        if (fMap.isReverseRef) {
            ReverseRefTuple[] art = (ReverseRefTuple[]) value;
            int length = art.length;
            if (length == 0) {
                return false;
            }
            for (int i=0; i < length; i++) {
                ReverseRefTuple rt = art[i];
                setColumnData(stmt, 1, DBEntityMap.FTYPE_LONG, pid);
                setColumnData(stmt, 2, DBEntityMap.FTYPE_STRING, rt.propertyName);
                setColumnData(stmt, 3, DBEntityMap.FTYPE_LONG, rt.id);
                stmt.addBatch();
            }
        }
        else if (fMap.isArray) {
            if (fMap.hasHistory) {
                Object[] ahtt = (Object[]) value;
                int size = ahtt.length;
                for (int i=0; i<size; i++) {
                    HistoryTableTuple htt = (HistoryTableTuple) ahtt[i];
                    int howMany = htt.howMany;
                    HistoryTuple[] aht = htt.historyTable;
                    int htsize = aht.length;
                    for (int j=0; j<htsize; j++) {
                        HistoryTuple ht = aht[j];
                        setColumnData(stmt, 1, DBEntityMap.FTYPE_LONG, pid);
                        setColumnData(stmt, 2, DBEntityMap.FTYPE_LONG, new Long(i));
                        setColumnData(stmt, 3, DBEntityMap.FTYPE_INTEGER, howMany);
                        setColumnData(stmt, 4, DBEntityMap.FTYPE_TIMESTAMP, ht.ts);
                        setColumnData(stmt, 5, fMap.tableFieldMappingType, ht.value);
                        stmt.addBatch();
                    }
                }
            }
            else {
                Object[] values = (Object[]) value;
                int size = values.length;
                for (int i=0; i<size; i++) {
                    Object val = values[i];
                    setColumnData(stmt, 1, DBEntityMap.FTYPE_LONG, pid);
                    setColumnData(stmt, 2, DBEntityMap.FTYPE_LONG, new Long(i));
                    setColumnData(stmt, 3, fMap.tableFieldMappingType, val);
                    stmt.addBatch();
                }
            }
        }
        else if (fMap.hasHistory) {
            HistoryTableTuple htt = (HistoryTableTuple) value;
            int howMany = htt.howMany;
            HistoryTuple[] aht = htt.historyTable;
            int htsize = aht.length;
            for (int j=0; j<htsize; j++) {
                HistoryTuple ht = aht[j];
                setColumnData(stmt, 1, DBEntityMap.FTYPE_LONG, pid);
                setColumnData(stmt, 2, DBEntityMap.FTYPE_INTEGER, howMany);
                setColumnData(stmt, 3, DBEntityMap.FTYPE_TIMESTAMP, ht.ts);
                setColumnData(stmt, 4, fMap.tableFieldMappingType, ht.value);
                stmt.addBatch();
            }
        }
        else {
            logger.log(Level.ERROR, "Cannot handle statement population of %s", fMap.classFieldName);
        }
        return true;
    }

    public static byte[] getBlob(ResultSet rs, int position) throws Exception {
        byte[] blob = null;
        if (RDBMSType.sRuntimeType == RDBMSType.SQLSERVER) {
            blob = rs.getBytes(position);
        } else if (RDBMSType.sRuntimeType == RDBMSType.ORACLE) {
            blob = rs.getBytes(position);
        } else if (RDBMSType.sRuntimeType == RDBMSType.DB2) {
            blob = rs.getBytes(position);
        } else {
            blob = rs.getBytes(position);
        }
        if (blob != null) {
            logger.log(Level.TRACE, "Read blob with size: %s", blob.length);
        }
        return blob;
    }

    public static void setBlob(PreparedStatement stmt, int position, byte[] buf) throws Exception {
        if (RDBMSType.sRuntimeType == RDBMSType.SQLSERVER) {
            ByteArrayInputStream bis = new ByteArrayInputStream(buf);
            stmt.setBinaryStream(position, bis, buf.length);
        } else if (RDBMSType.sRuntimeType == RDBMSType.ORACLE) {
            ByteArrayInputStream bis = new ByteArrayInputStream(buf);
            stmt.setBinaryStream(position, bis, buf.length);
        } else if (RDBMSType.sRuntimeType == RDBMSType.DB2) {
            ByteArrayInputStream bis = new ByteArrayInputStream(buf);
            stmt.setBinaryStream(position, bis, buf.length);
        } else {
            ByteArrayInputStream bis = new ByteArrayInputStream(buf);
            stmt.setBinaryStream(position, bis, buf.length);
        }
        logger.log(Level.DEBUG, "Write blob with size: %s", buf.length);
    }

    public static String getClob(ResultSet rs, int position) throws Exception {
        return "";
    }

    public static void setClob(PreparedStatement stmt, int position, String buf) throws Exception {
    }
}
