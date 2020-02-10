package com.tibco.be.jdbcstore.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.jdbcstore.JdbcUtil;
import com.tibco.be.jdbcstore.RDBMSType;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class DBEventMap extends DBEntityMap {

    public static final String ID_FIELD_NAME = "id$";
    public static final String EXTID_FIELD_NAME = "extId$";
    public static final String STATE_FIELD_NAME = "state$";
    public static final String TCREATED_FIELD_NAME = "time_created$";
    public static final String TACK_FIELD_NAME = "time_acknowledged$";
    public static final String TSENT_FIELD_NAME = "time_sent$";
    public static final String CURRENT_TIME_FIELD_NAME = "currentTime";
    public static final String NEXT_TIME_FIELD_NAME = "nextTime";
    public static final String CLOSURE_FIELD_NAME = "closure";
    public static final String TTL_FIELD_NAME = "ttl";
    public static final String FIRED_FIELD_NAME = "fired";
    public static final String SMID_FIELD_NAME = "smid";
    public static final String PROP_NAME_FIELD_NAME = "propertyName";

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DBEventMap.class);

    public static final int EVENT_SYSTEM_FIELD_COUNT = 7;
    
    /* TimeEvent and StateTimeoutEvent has hard coded fields
     * but they are not system fields from serialization
     * perspective
     */
    //public static final int TIME_EVENT_SYSTEM_FIELD_COUNT = 9;
    //public static final int STATE_TIMEOUT_EVENT_SYSTEM_FIELD_COUNT = 11;
    public static final int TIME_EVENT_SYSTEM_FIELD_COUNT = 4;
    public static final int STATE_TIMEOUT_EVENT_SYSTEM_FIELD_COUNT = 4;

    //PropertyMap []properties;
    DBEntityMap.DBFieldMap[] _fieldMaps;
    Class eventClass;
    String tableName;
    //HashMap sqlTypes = new HashMap();
    boolean _isStateTimeOut;
    boolean _isTimeEvent;
    Map _fieldMapMap = new HashMap();
    int _lastUpdateParamPos = -1;
    int _systemFieldCount = EVENT_SYSTEM_FIELD_COUNT;

    /**
     *
     * @param entityModel
     * @param typeDescriptor
     * @param oracle
     * @throws Exception
    public DBEventMap(Event entityModel, StructDescriptor typeDescriptor, OracleConnection oracle) throws Exception{
        super(entityModel, typeDescriptor);
        this.oracle=oracle;
        configure();
    }
     */

    public DBEventMap(SimpleEventDescription entityDescription, String tableName, Map aliases) throws Exception{
        super(entityDescription, aliases);
        this.tableName = tableName;
        configure();
    }

    /**
     *
     * @param propertyName
     * @param oracleADT
     * @return
     * @throws java.sql.SQLException
    int getOracleIndex(String propertyName, OracleTypeADT oracleADT) throws SQLException {
        int numAttributes= oracleADT.getNumAttrs();
        for (int i=1; i <= numAttributes; i++) {
            String attributeName= oracleADT.getAttributeName(i);
            if (matches(attributeName, propertyName)) {
                return i-1;
            }
        }
        return -1;
    }
     */

    /**
     *
     * @param pd
     * @param oracleADT
     * @param propertyIndex
     * @param jdbcIndex
    void associate(EventPropertyDefinition pd, OracleTypeADT oracleADT, String columnType, int propertyIndex, int jdbcIndex) throws Exception{
        oracle.jdbc.oracore.OracleType type= oracleADT.getAttrTypeAt(jdbcIndex);
        StructDescriptor sd= null;
        if (type.isObjectType()) {
            sd= StructDescriptor.createDescriptor(columnType, oracle);
        }
        PropertyMap pm= new EventPropertyMap(pd, jdbcIndex, pd.getPropertyName(), sd, type.getTypeCode());
        sqlTypes.put(new Integer(jdbcIndex), type);
        properties[propertyIndex]= pm;
    }
     */

    void associate(SimpleEventDescription.EventProperty pd, int propertyIndex, int dataObjectIndex, int tableIndex) throws Exception{
        DBEntityMap.DBFieldMap fMap = new DBEntityMap.DBFieldMap();
        fMap.classFieldName = pd.propertyName;
        fMap.tableFieldName = JdbcUtil.getInstance().getPDName(pd.propertyName, aliases);
        fMap.classFieldIndex = propertyIndex;
        fMap.dataObjectFieldIndex = dataObjectIndex;
        fMap.tableFieldIndex = tableIndex;
        fMap.tableFieldType = pd.type;
        fMap.isArray = false;
        fMap.hasHistory = false;
        _fieldMaps[propertyIndex + _systemFieldCount] = fMap;
        _fieldMapMap.put(fMap.classFieldName, fMap);
        int mapType = DBEntityMap.FTYPE_STRING;
        int sqlType = java.sql.Types.VARCHAR;
        switch (pd.type) {
            case RDFTypes.STRING_TYPEID:
                mapType = DBEntityMap.FTYPE_STRING;
                //FIXME: Get design-time value for length
                //String maxLength = JdbcUtil.getInstance().getBackingMapEventProperty(pd, Entity.EXTPROP_PROPERTY_BACKINGSTORE_MAXLENGTH);
                String dataType = RDBMSType.sSqlType.getColumnTypeForPrimitiveType("String", 255);
                if (dataType.equalsIgnoreCase("clob")) {
                    sqlType = java.sql.Types.CLOB;
                } else {
                    sqlType = java.sql.Types.VARCHAR;
                }
                break;
            case RDFTypes.INTEGER_TYPEID:
                mapType = DBEntityMap.FTYPE_INTEGER;
                sqlType = java.sql.Types.INTEGER;
                break;
            case RDFTypes.LONG_TYPEID:
                mapType = DBEntityMap.FTYPE_LONG;
                sqlType = java.sql.Types.NUMERIC;
                break;
            case RDFTypes.DOUBLE_TYPEID:
                mapType = DBEntityMap.FTYPE_DOUBLE;
                sqlType = java.sql.Types.DOUBLE;
                break;
            case RDFTypes.BOOLEAN_TYPEID:
                mapType = DBEntityMap.FTYPE_BOOLEAN;
                sqlType = java.sql.Types.INTEGER;
                break;
            case RDFTypes.DATETIME_TYPEID:
                mapType = DBEntityMap.FTYPE_DATETIME;
                sqlType = java.sql.Types.TIMESTAMP;
                break;
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
                mapType = DBEntityMap.FTYPE_LONG;
                sqlType = java.sql.Types.NUMERIC;
                break;
            case RDFTypes.CONCEPT_TYPEID:
            case RDFTypes.EVENT_TYPEID:
            case RDFTypes.TIME_EVENT_TYPEID:
            case RDFTypes.ADVISORY_EVENT_TYPEID:
                mapType = DBEntityMap.FTYPE_LONG;
                sqlType = java.sql.Types.NUMERIC;
                logger.log(Level.ERROR, "Property: %s has entity type: %s, should we expect this?", pd.propertyName, pd.type);
                break;
            default:
                sqlType = java.sql.Types.VARCHAR;
                logger.log(Level.ERROR, "Property: %s has unrecognized type: %s", pd.propertyName, pd.type);
                break;
        }
        fMap.tableFieldSqlType = sqlType;
        fMap.tableFieldMappingType = mapType;
        if (pd.type == RDFTypes.DATETIME_TYPEID) {
            fMap.tableFieldName = JdbcUtil.getInstance().getPDName(pd.propertyName + "_" + "tm", aliases);
            fMap.tableExtraFieldName1 = JdbcUtil.getInstance().getPDName(pd.propertyName + "_" + "tz", aliases);
        }
        else if (pd.type == RDFTypes.CONCEPT_TYPEID || pd.type == RDFTypes.CONCEPT_REFERENCE_TYPEID) {
            fMap.tableFieldName = JdbcUtil.getInstance().getPDName(pd.propertyName + "_" + "id$", aliases);
        }
        //sqlTypes.put(new Integer(tableIndex), type);
    }

    /* FIX THIS - Do we need this function?
     * given an attribute(class level) index, return a oracle type
    public OracleType getOracleType (int attributeIndex) {
        return (OracleType) sqlTypes.get(new Integer(attributeIndex));
    }
    */

    /**
     *
     * @throws Exception
     */
    void configure() throws Exception{
        if (entityModel instanceof Event) {
            //configure ((Event)entityModel);
            logger.log(Level.INFO, "Configure DBEventMap using Event is not supported");
        } else {
            configure ((SimpleEventDescription) entityModel);
        }
    }

    void configure(SimpleEventDescription model) throws Exception{
        List allProperties = (List) model.getProperties();
        _isTimeEvent = (this instanceof DBTimeEventMap);
        _isStateTimeOut = (entityModel instanceof StateMachineTimeoutDescription);
        if (_isStateTimeOut) {
            _systemFieldCount = STATE_TIMEOUT_EVENT_SYSTEM_FIELD_COUNT;
        }
        else if (_isTimeEvent) {
            _systemFieldCount = TIME_EVENT_SYSTEM_FIELD_COUNT;
        }
        //properties = new PropertyMap[allProperties.size() + _systemFieldCount];
        _fieldMaps = new DBEntityMap.DBFieldMap[allProperties.size() + _systemFieldCount];
        setupSystemFieldMaps();
        int tableColumnIndex = 0;
        for (int i=0; i < allProperties.size(); i++) {
            SimpleEventDescription.EventProperty pd = (SimpleEventDescription.EventProperty) allProperties.get(i);
            associate(pd, i, i + _systemFieldCount, tableColumnIndex + _systemFieldCount);
            if (pd.type == RDFTypes.DATETIME_TYPEID) {
                tableColumnIndex += 2;
            }
            else {
                tableColumnIndex++;
            }
            // FIX THIS - since we don't use oracle type anymore, we don't check for table mismatch
        }
        //oracle.jdbc.oracore.OracleType type= oracleADT.getAttrTypeAt(6);
        if (!(model instanceof TimeEventDescription)) {
            // FIX THIS - how to populate the payload???
            //sqlTypes.put(new Integer(6), type); // Hack for payload types
        }
        //VWC FIX THIS -- _isStateTimeOut = StateMachineConceptImpl.StateTimeoutEvent.class.getName().equals(_entityClass.getName());
        setupSelectSql();
        setupUpdateSql();
        setupDeleteSql();
        setupInsertSql();
    }

    /*
    void configure(Event model) throws Exception{
        OracleTypeADT oracleADT= typeDescriptor.getOracleTypeADT();
        List allProperties= model.getAllUserProperties();
        properties = new PropertyMap[allProperties.size()];

        for (int i=0; i < allProperties.size(); i++) {
            EventPropertyDefinition pd= (EventPropertyDefinition) allProperties.get(i);
            int jdbcIndex= getOracleIndex(pd.getPropertyName(), oracleADT);

            if (jdbcIndex >= 0) {
                associate(pd, oracleADT, oracleADT.getAttributeType(jdbcIndex+1), i, jdbcIndex);
            } else {
                throw new Exception("Database schema not in sync with BusinessEvents schema for Event " + model.getFullPath()
                                        + ": Unable to associate property=" + pd.getPropertyName());
            }
        }
        oracle.jdbc.oracore.OracleType type= oracleADT.getAttrTypeAt(6);
        if (!(model instanceof OracleAdapter.TimeEventDescription)) {
            sqlTypes.put(new Integer(6), type); // Hack for payload types
        }
    }
    */

    /**
     *
     * @param propertyIndex
     * @return
    public PropertyMap getPropertyMap(int propertyIndex) {
        return properties[propertyIndex];
    }
     */

    public DBEntityMap.DBFieldMap getFieldMap(int propertyIndex) {
        return _fieldMaps[propertyIndex];
    }

    public DBEntityMap.DBFieldMap getUserFieldMap(int propertyIndex) {
        return _fieldMaps[propertyIndex + _systemFieldCount];
    }

    public int getFieldCount() {
        return _fieldMaps.length;
    }

    public Class getEntityClass(RuleServiceProvider rsp) {
        logger.log(Level.INFO, "Obsolete call - DBEventMap.getEntityClass");
        return null;
        /*
        if (eventClass == null) {
            if (entityModel instanceof Event) {
                eventClass=rsp.getTypeManager().getTypeDescriptor( ((Event)entityModel).getFullPath()).getImplClass();
            } else {
                String implClass=((OracleAdapter.SimpleEventDescription)entityModel).getImplClass();
                try {
                    eventClass=rsp.getClassLoader().loadClass(implClass);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return eventClass;
        */
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName=tableName;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("Event class : " + _entityClass);
        sb.append(", table name : " + tableName);
        //sb.append(", number of properties : " + properties.length);
        sb.append(", number of field maps : " + _fieldMaps.length);
        sb.append("");
        //sb.append(", prim field count : " + _primaryFieldCount);
        //sb.append(", sec field count : " + _secondaryFieldCount);

        for (int i=0; i < _fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            sb.append("Class field : " + fmap.classFieldName);
            sb.append(", table field : " + fmap.tableFieldName);
            sb.append(", table extra field : " + fmap.tableExtraFieldName1);
            sb.append(", Class field index : " + fmap.classFieldIndex);
            sb.append(", data field index : " + fmap.dataObjectFieldIndex);
            sb.append(", table field index : " + fmap.tableFieldIndex);
            sb.append(", table field sql : " + fmap.tableFieldSqlType);
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    private void setupSelectSql() {
        StringBuffer sb = new StringBuffer("select T.cacheId");
        for (int i=0; i<_fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            sb.append(", T.");
            sb.append(fmap.tableFieldName);
            if (fmap.tableFieldMappingType == DBEntityMap.FTYPE_DATETIME) {
                sb.append(", T.");
                sb.append(fmap.tableExtraFieldName1);
            }
        }
        sb.append(" from " + tableName + " T ");
        _primarySelectSql = sb.toString();
        logger.log(Level.DEBUG, this._primarySelectSql);
        if (RDBMSType.sSqlType.optimizable()) {
            _primarySelectSql = RDBMSType.sSqlType.optimizeSelectStatement(_primarySelectSql);
            logger.log(Level.DEBUG, "Optimized:" + this._primarySelectSql);
        }
    }

    // Not used
    private void setupUpdateSql() {
        StringBuffer sb = new StringBuffer("update " + tableName + " T set T.cacheId=?");
        for (int i=0; i<_fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            sb.append(", T.");
            sb.append(fmap.tableFieldName);
            sb.append("=?");
            if (fmap.tableFieldMappingType == DBEntityMap.FTYPE_DATETIME) {
                sb.append(", T.");
                sb.append(fmap.tableExtraFieldName1);
                sb.append("=?");
            }
        }
        sb.append(" where T.");
        sb.append(ID_FIELD_NAME);
        sb.append("=?");
        _primaryUpdateSql = sb.toString();
        logger.log(Level.DEBUG, this._primaryUpdateSql);
        if (RDBMSType.sSqlType.optimizable()) {
            _primaryUpdateSql = RDBMSType.sSqlType.optimizeUpdateStatement(_primaryUpdateSql);
            logger.log(Level.DEBUG, "Optimized:" + this._primaryUpdateSql);
        }
    }

    private void setupDeleteSql() {
        _primaryDeleteSql = "delete from " + tableName + " where id$=?";
        logger.log(Level.DEBUG, this._primaryDeleteSql);
        if (RDBMSType.sSqlType.optimizable()) {
            _primaryDeleteSql = RDBMSType.sSqlType.optimizeDeleteStatement(_primaryDeleteSql);
            logger.log(Level.DEBUG, "Optimized:" + this._primaryDeleteSql);
        }
    }

    private void setupInsertSql() {
        //FIX THIS - Need to work on blob for payload
        StringBuffer sb = new StringBuffer("insert into " + tableName + " (cacheId");
        StringBuffer sbValue = new StringBuffer(" values(?");
        for (int i=0; i<_fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            sb.append(", ");
            sb.append(fmap.tableFieldName);
            sbValue.append(",?");
            if (fmap.tableFieldMappingType == DBEntityMap.FTYPE_DATETIME) {
                sb.append(", ");
                sb.append(fmap.tableExtraFieldName1);
                sbValue.append(",?");
            }
        }
        sb.append(")");
        sb.append(sbValue);
        sb.append(")");
        _primaryInsertSql = sb.toString();
        logger.log(Level.DEBUG, this._primaryInsertSql);
        if (RDBMSType.sSqlType.optimizable()) {
            _primaryInsertSql = RDBMSType.sSqlType.optimizeInsertStatement(_primaryInsertSql);
            logger.log(Level.DEBUG, "Optimized:" + this._primaryInsertSql);
        }
    }

    private void setupSystemFieldMaps() {
        DBEntityMap.DBFieldMap fMap = new DBEntityMap.DBFieldMap();
        fMap.classFieldName = ID_FIELD_NAME;
        fMap.tableFieldName = ID_FIELD_NAME;
        fMap.classFieldIndex = -1;
        fMap.dataObjectFieldIndex = 0;
        fMap.tableFieldIndex = 0;
        fMap.tableFieldType = RDFTypes.LONG_TYPEID;
        fMap.tableFieldSqlType = java.sql.Types.NUMERIC;
        fMap.tableFieldMappingType = DBEntityMap.FTYPE_LONG;
        _fieldMaps[0] = fMap;
        _fieldMapMap.put(ID_FIELD_NAME, fMap);

        fMap = new DBEntityMap.DBFieldMap();
        fMap.classFieldName = EXTID_FIELD_NAME;
        fMap.tableFieldName = EXTID_FIELD_NAME;
        fMap.classFieldIndex = -1;
        fMap.dataObjectFieldIndex = 1;
        fMap.tableFieldIndex = 1;
        fMap.tableFieldType = RDFTypes.STRING_TYPEID;
        fMap.tableFieldSqlType = java.sql.Types.VARCHAR;
        fMap.tableFieldMappingType = DBEntityMap.FTYPE_STRING;
        _fieldMaps[1] = fMap;
        _fieldMapMap.put(EXTID_FIELD_NAME, fMap);

        fMap = new DBEntityMap.DBFieldMap();
        fMap.classFieldName = STATE_FIELD_NAME;
        fMap.tableFieldName = STATE_FIELD_NAME;
        fMap.classFieldIndex = -1;
        fMap.dataObjectFieldIndex = 2;
        fMap.tableFieldIndex = 2;
        fMap.tableFieldType = -1; // no corresponding type available
        fMap.tableFieldSqlType = java.sql.Types.VARCHAR; // using CHAR doesn't seem to work
        fMap.tableFieldMappingType = DBEntityMap.FTYPE_CHAR;
        _fieldMaps[2] = fMap;
        _fieldMapMap.put(STATE_FIELD_NAME, fMap);

        fMap = new DBEntityMap.DBFieldMap();
        fMap.classFieldName = TCREATED_FIELD_NAME;
        fMap.tableFieldName = TCREATED_FIELD_NAME;
        fMap.classFieldIndex = -1;
        fMap.dataObjectFieldIndex = 3;
        fMap.tableFieldIndex = 3;
        fMap.tableFieldType = -1;
        fMap.tableFieldSqlType = java.sql.Types.TIMESTAMP;
        fMap.tableFieldMappingType = DBEntityMap.FTYPE_TIMESTAMP;
        _fieldMaps[3] = fMap;
        _fieldMapMap.put(TCREATED_FIELD_NAME, fMap);

        // Do not create these fields as system fields.
        // Instead, create them are user-defined fields
        // because that's how they are being treated
        // at runtime during serializations
        if (_isTimeEvent) {
            /*
            fMap = new DBEntityMap.DBFieldMap();
            fMap.classFieldName = CURRENT_TIME_FIELD_NAME;
            fMap.tableFieldName = CURRENT_TIME_FIELD_NAME;
            fMap.classFieldIndex = -1;
            fMap.dataObjectFieldIndex = 4;
            fMap.tableFieldIndex = 4;
            fMap.tableFieldType = RDFTypes.LONG_TYPEID;
            fMap.tableFieldSqlType = java.sql.Types.NUMERIC;
            fMap.tableFieldMappingType = DBEntityMap.FTYPE_LONG;
            _fieldMaps[4] = fMap;
            _fieldMapMap.put(CURRENT_TIME_FIELD_NAME, fMap);

            fMap = new DBEntityMap.DBFieldMap();
            fMap.classFieldName = NEXT_TIME_FIELD_NAME;
            fMap.tableFieldName = NEXT_TIME_FIELD_NAME;
            fMap.classFieldIndex = -1;
            fMap.dataObjectFieldIndex = 5;
            fMap.tableFieldIndex = 5;
            fMap.tableFieldType = RDFTypes.LONG_TYPEID;
            fMap.tableFieldSqlType = java.sql.Types.NUMERIC;
            fMap.tableFieldMappingType = DBEntityMap.FTYPE_LONG;
            _fieldMaps[5] = fMap;
            _fieldMapMap.put(NEXT_TIME_FIELD_NAME, fMap);

            fMap = new DBEntityMap.DBFieldMap();
            fMap.classFieldName = CLOSURE_FIELD_NAME;
            fMap.tableFieldName = CLOSURE_FIELD_NAME;
            fMap.classFieldIndex = -1;
            fMap.dataObjectFieldIndex = 6;
            fMap.tableFieldIndex = 6;
            fMap.tableFieldType = RDFTypes.STRING_TYPEID;
            fMap.tableFieldSqlType = java.sql.Types.VARCHAR;
            fMap.tableFieldMappingType = DBEntityMap.FTYPE_STRING;
            _fieldMaps[6] = fMap;
            _fieldMapMap.put(CLOSURE_FIELD_NAME, fMap);

            fMap = new DBEntityMap.DBFieldMap();
            fMap.classFieldName = TTL_FIELD_NAME;
            fMap.tableFieldName = TTL_FIELD_NAME;
            fMap.classFieldIndex = -1;
            fMap.dataObjectFieldIndex = 7;
            fMap.tableFieldIndex = 7;
            fMap.tableFieldType = RDFTypes.LONG_TYPEID;
            fMap.tableFieldSqlType = java.sql.Types.NUMERIC;
            fMap.tableFieldMappingType = DBEntityMap.FTYPE_LONG;
            _fieldMaps[7] = fMap;
            _fieldMapMap.put(TTL_FIELD_NAME, fMap);

            fMap = new DBEntityMap.DBFieldMap();
            fMap.classFieldName = FIRED_FIELD_NAME;
            fMap.tableFieldName = FIRED_FIELD_NAME;
            fMap.classFieldIndex = -1;
            fMap.dataObjectFieldIndex = 8;
            fMap.tableFieldIndex = 8;
            fMap.tableFieldType = RDFTypes.INTEGER_TYPEID;
            fMap.tableFieldSqlType = java.sql.Types.INTEGER;
            fMap.tableFieldMappingType = DBEntityMap.FTYPE_INTEGER;
            _fieldMaps[8] = fMap;
            _fieldMapMap.put(FIRED_FIELD_NAME, fMap);

            if (_isStateTimeOut) {
                fMap = new DBEntityMap.DBFieldMap();
                fMap.classFieldName = SMID_FIELD_NAME;
                fMap.tableFieldName = SMID_FIELD_NAME;
                fMap.classFieldIndex = -1;
                fMap.dataObjectFieldIndex = 9;
                fMap.tableFieldIndex = 9;
                fMap.tableFieldType = RDFTypes.LONG_TYPEID;
                fMap.tableFieldSqlType = java.sql.Types.NUMERIC;
                fMap.tableFieldMappingType = DBEntityMap.FTYPE_LONG;
                _fieldMaps[9] = fMap;
                _fieldMapMap.put(SMID_FIELD_NAME, fMap);

                fMap = new DBEntityMap.DBFieldMap();
                fMap.classFieldName = PROP_NAME_FIELD_NAME;
                fMap.tableFieldName = PROP_NAME_FIELD_NAME;
                fMap.classFieldIndex = -1;
                fMap.dataObjectFieldIndex = 10;
                fMap.tableFieldIndex = 10;
                fMap.tableFieldType = RDFTypes.STRING_TYPEID;
                fMap.tableFieldSqlType = java.sql.Types.VARCHAR;
                fMap.tableFieldMappingType = DBEntityMap.FTYPE_STRING;
                _fieldMaps[10] = fMap;
                _fieldMapMap.put(PROP_NAME_FIELD_NAME, fMap);
            }
            */
        }
        else {
            fMap = new DBEntityMap.DBFieldMap();
            fMap.classFieldName = TACK_FIELD_NAME;
            fMap.tableFieldName = TACK_FIELD_NAME;
            fMap.classFieldIndex = -1;
            fMap.dataObjectFieldIndex = 4;
            fMap.tableFieldIndex = 4;
            fMap.tableFieldType = -1;
            fMap.tableFieldSqlType = java.sql.Types.TIMESTAMP;
            fMap.tableFieldMappingType = DBEntityMap.FTYPE_TIMESTAMP;
            _fieldMaps[4] = fMap;
            _fieldMapMap.put(TACK_FIELD_NAME, fMap);

            fMap = new DBEntityMap.DBFieldMap();
            fMap.classFieldName = TSENT_FIELD_NAME;
            fMap.tableFieldName = TSENT_FIELD_NAME;
            fMap.classFieldIndex = -1;
            fMap.dataObjectFieldIndex = 5;
            fMap.tableFieldIndex = 5;
            fMap.tableFieldType = -1;
            fMap.tableFieldSqlType = java.sql.Types.TIMESTAMP;
            fMap.tableFieldMappingType = DBEntityMap.FTYPE_TIMESTAMP;
            _fieldMaps[5] = fMap;
            _fieldMapMap.put(TSENT_FIELD_NAME, fMap);

            fMap = new DBEntityMap.DBFieldMap();
            //fMap.classFieldName = tableName + "_p";
            fMap.classFieldName = "payload__p";
            fMap.tableFieldName = JdbcUtil.getInstance().getPDName(fMap.classFieldName, aliases);
            fMap.classFieldIndex = -1;
            fMap.dataObjectFieldIndex = 6;
            fMap.tableFieldIndex = 6;
            fMap.tableFieldType = -1;
            fMap.tableFieldSqlType = java.sql.Types.BLOB;
            fMap.tableFieldMappingType = DBEntityMap.FTYPE_BLOB;
            _fieldMaps[6] = fMap;
            _fieldMapMap.put(fMap.classFieldName, fMap);
        }
    }

    public int getSystemFieldCount() {
        return _systemFieldCount;
    }

    public boolean isTimeEvent() {
        return _isTimeEvent;
    }

    public boolean isStateTimeOut() {
        return _isStateTimeOut;
    }
}
