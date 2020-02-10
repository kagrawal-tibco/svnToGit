package com.tibco.be.jdbcstore.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.jdbcstore.JdbcUtil;
import com.tibco.be.jdbcstore.RDBMSType;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;

// FIX THIS - This class may not be necessary - use ConceptDescription directly??
// how many layers do we need?
// Cannot find the state machine column matching logic
// This class only captures mapping for user defined fields.  It does not know about system fields
public class DBConceptMap extends DBEntityMap {
    
    public static final String ID_FIELD_NAME = "id$";
    public static final String EXTID_FIELD_NAME = "extId$";
    public static final String STATE_FIELD_NAME = "state$";
    public static final String TCREATED_FIELD_NAME = "time_created$";
    public static final String TMODIFIED_FIELD_NAME = "time_last_modified$";
    public static final String PARENT_FIELD_NAME = "parent$_id$";
    public static final String REVERSEREF_FIELD_NAME = "rrf$";

    // Process system fields
    public static final String PROC_STATUS_FIELD_NAME = "processStatus$";
    public static final String LAST_TASK_FIELD_NAME = "lastTaskExecuted$";
    public static final String PARENT_PROC_FIELD_NAME = "parentProcess$_id$";
    public static final String PROC_VERSION_FIELD_NAME = "processTemplateVersion$";
    public static final String PENDING_EVENT_MAP_FIELD_NAME = "pendingEventMap$";

    public static final int CONCEPT_PRIMARY_SYSTEM_FIELD_COUNT = 6; // 6 primary system fields
    public static final int CONCEPT_SYSTEM_FIELD_COUNT = 7; // 6 primary plus 1 secondary (reverse-reference)
    public static final int PROCESS_SYSTEM_FIELD_COUNT = 4; // process has 4 additional system fields

    // Java class level process system field names
    private static final String PROC_STATUS_PROP_NAME = "$1zprocessStatus";
    private static final String LAST_TASK_PROP_NAME = "$1zlastTaskExecuted";
    private static final String PARENT_PROC_PROP_NAME = "$1zparentProcess";
    private static final String PENDING_EVENT_MAP_PROP_NAME = "$1zpendingEventMap";

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DBConceptMap.class);

    //Connection conn;
    //PropertyMap[] properties;
    // Number of field maps matches number of property of the java class
    DBEntityMap.DBFieldMap[] _fieldMaps;
    Class conceptClass;
    int _primaryFieldCount = 0;
    int _secondaryFieldCount = 0;
    String _tableName;
    String _reverseRefTableName;
    String[] _secondaryTables;
    String _reverseRefSelectSql;
    String _reverseRefUpdateSql;
    String _reverseRefDeleteSql;
    String _reverseRefInsertSql;
    Map _tableNamesMap = null;
    Map _secondarySelectSqlMap = new HashMap();
    Map _secondaryUpdateSqlMap = new HashMap();
    Map _secondaryDeleteSqlMap = new HashMap();
    Map _secondaryRemoveSqlMap = new HashMap();
    Map _secondaryInsertSqlMap = new HashMap();
    Map _fieldMapMap = new HashMap();
    int _lastUpdateParamPos = -1;
    boolean _isMetric = false;
    boolean _isProcess = false;
    int _processVersion = 0;

    /**
     *
     * @param entityModel
     * @param conn
     * @throws Exception
    public DBConceptMap(Concept entityModel,Connection conn) throws Exception{
        super(entityModel, typeDescriptor);
        this.conn = conn;
        configure();
    }
     */

    // lookup by using the db table column
    /*
    public PropertyMap getPropertyMapAt(int jdbcIndex) {
        for (int i=0; i < properties.length;i++) {
            PropertyMap pm = properties[i];
            if (pm.getColumnIndex() == jdbcIndex) {
                return pm;
            }
        }
        return null;
    }
    */

    /**
     *
     * @param entityModel
     * @throws Exception
     */
    public DBConceptMap(ConceptDescription entityModel, Map tableNames, Map aliases) throws Exception{
        super(entityModel, aliases);
        //this.conn = conn;
        this._tableNamesMap = tableNames;
        this._tableName = (String)tableNames.get(DBEntityMap.PRIMARY_TABLE_NAME);
        this._reverseRefTableName = (String)tableNames.get(DBEntityMap.REVERSE_TABLE_NAME);
        configure();
    }

    /**
     *
     * @param propertyName
     * @param oracleADT
     * @return
     * @throws SQLException
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
     * @param type
     * @param columnName
     * @param columnType
     * @param propertyIndex
     * @param jdbcIndex
     * @return
     * @throws Exception
    PropertyMap propertyAtomSimpleMap(Object pd,
                                      oracle.jdbc.oracore.OracleType type,
                                      String columnName, String columnType, int propertyIndex, int jdbcIndex) throws Exception {
        // Struct with no history
        boolean isObjectType= type.isObjectType();
        StructDescriptor typeDescriptor=null;
        if (type.isObjectType()) {
            typeDescriptor= StructDescriptor.createDescriptor(columnType, oracle);
        }
        PropertyMap pm= new PropertyAtomSimpleMap(pd, columnName,
                jdbcIndex, typeDescriptor, type.getTypeCode());
        return pm;
    }
     */

    /**
     *
     * @param pd
     * @param type
     * @param columnName
     * @param columnType
     * @param propertyIndex
     * @param jdbcIndex
     * @return
     * @throws Exception
    PropertyMap propertyAtomSimpleMap(Object pd,
                                      int type,
                                      String columnName, String columnType, int propertyIndex, int jdbcIndex) throws Exception {
        // Struct with no history
        StructDescriptor td=null;
        if (type == OracleTypes.STRUCT) {
            td= StructDescriptor.createDescriptor(columnType, oracle);
        }
        PropertyMap pm= new PropertyAtomSimpleMap(pd, columnName, jdbcIndex, td, type);
        return pm;
    }
     */

    /**
     *
     * @param pd
     * @param columnName
     * @param columnType
     * @param propertyIndex
     * @param jdbcIndex
     * @return
     * @throws Exception
    PropertyMap propertyAtomMap(Object pd, String columnName, String columnType, int propertyIndex, int jdbcIndex) throws Exception{
        StructDescriptor typeDescriptor=null;
        typeDescriptor= StructDescriptor.createDescriptor(columnType, oracle);
        // Get History Table Descriptor;
        String historyTableType= typeDescriptor.getOracleTypeADT().getAttributeType(2);
        ArrayDescriptor historyTableDescriptor  = ArrayDescriptor.createDescriptor(historyTableType, oracle);
        StructDescriptor historyTupleDescriptor = StructDescriptor.createDescriptor(historyTableDescriptor.getBaseName(), oracle);
        oracle.jdbc.oracore.OracleType pt= historyTupleDescriptor.getOracleTypeADT().getAttrTypeAt(1);
        StructDescriptor primitiveDescriptor=null;
        if (pt.isObjectType()) {
            primitiveDescriptor= StructDescriptor.createDescriptor(historyTupleDescriptor.getOracleTypeADT().getAttributeType(2), oracle);
        }
        PropertyMap pm= new PropertyAtomMap(oracle,pd,columnName, jdbcIndex,
                typeDescriptor,historyTableDescriptor,historyTupleDescriptor, primitiveDescriptor);
        return pm;
    }
     */

    /**
     *
     * @param pd
     * @param oracleADT
     * @param propertyIndex
     * @param jdbcIndex
    private void associate(PropertyDefinition pd, OracleTypeADT oracleADT, int propertyIndex, int jdbcIndex) throws Exception {
        if (pd.isArray() && (pd.getHistorySize() > 0)) {
            // Collection of histories
            oracle.jdbc.oracore.OracleType type= oracleADT.getAttrTypeAt(jdbcIndex);
            ArrayDescriptor ad = ArrayDescriptor.createDescriptor(oracleADT.getAttributeType(jdbcIndex+1), oracle);
            if (ad.getBaseType() == OracleTypes.STRUCT) {
                StructDescriptor baseDescriptor= StructDescriptor.createDescriptor(ad.getBaseName(), oracle);
                OracleTypeADT t= baseDescriptor.getOracleTypeADT();
                PropertyMap pm= propertyAtomMap(pd, null, ad.getBaseName(), propertyIndex, jdbcIndex);
                PropertyArrayMap pam= new PropertyArrayMap(pd, oracleADT.getAttributeName(jdbcIndex+1), jdbcIndex, ad, pm);
                properties[propertyIndex]= pam;
            } else {
                throw new Exception("Mismatched Type:" + ad.getBaseType());
            }
        } else if (pd.isArray() && (pd.getHistorySize() == 0)) {
            //Collection with no history
            oracle.jdbc.oracore.OracleType type= oracleADT.getAttrTypeAt(jdbcIndex);
            ArrayDescriptor ad = ArrayDescriptor.createDescriptor(oracleADT.getAttributeType(jdbcIndex+1), oracle);
            PropertyMap pm= propertyAtomSimpleMap(pd, ad.getBaseType(), null, ad.getBaseName(), propertyIndex, jdbcIndex);
            PropertyArrayMap pam= new PropertyArrayMap(pd, oracleADT.getAttributeName(jdbcIndex+1), jdbcIndex, ad, pm);
            properties[propertyIndex]= pam;
        } else if (!pd.isArray() && (pd.getHistorySize() == 0)) {
            PropertyMap pm= propertyAtomSimpleMap(pd, oracleADT.getAttrTypeAt(jdbcIndex),
                oracleADT.getAttributeName(jdbcIndex+1),
                oracleADT.getAttributeType(jdbcIndex+1), propertyIndex, jdbcIndex);
            properties[propertyIndex]= pm;
        } else {
            PropertyMap pm= propertyAtomMap(pd, oracleADT.getAttributeName(jdbcIndex+1),
                    oracleADT.getAttributeType(jdbcIndex+1), propertyIndex, jdbcIndex);
            properties[propertyIndex]= pm;
        }
    }
     */

    // FIX THIS - how do I find out state machine type??
    // This function handles properties
    void associate(ConceptDescription.ConceptProperty pd, int propertyIndex, int dataObjectIndex, int primaryTableColumnIndex) throws Exception{
        DBEntityMap.DBFieldMap fMap = new DBEntityMap.DBFieldMap();
        fMap.classFieldName = pd.propertyName;
        fMap.tableFieldName = JdbcUtil.getInstance().getPDName(pd.propertyName, aliases);
        if (_isProcess) {
            if (fMap.classFieldName.equals(PROC_STATUS_PROP_NAME)) {
                fMap.tableFieldName = PROC_STATUS_FIELD_NAME;
            } else if (fMap.classFieldName.equals(LAST_TASK_PROP_NAME)) {
                fMap.tableFieldName = LAST_TASK_FIELD_NAME;
            } else if (fMap.classFieldName.equals(PARENT_PROC_PROP_NAME)) {
                fMap.tableFieldName = PARENT_PROC_FIELD_NAME;
            }
        }
        // FIX THIS HACK - add datetime column and entityref column - hard code the value
        //fMap.tableFieldName = JdbcUtil.getInstance().getPDName(pd.propertyName, aliases);
        fMap.classFieldIndex = propertyIndex;
        fMap.dataObjectFieldIndex = dataObjectIndex;
        //fMap.tableFieldIndex = primaryTableColumnIndex + CONCEPT_PRIMARY_SYSTEM_FIELD_COUNT;
        fMap.tableFieldIndex = primaryTableColumnIndex;
        fMap.tableFieldType = pd.type;
        fMap.isArray = false;
        fMap.hasHistory = false;
        _fieldMaps[propertyIndex + CONCEPT_SYSTEM_FIELD_COUNT] = fMap;
        _fieldMapMap.put(fMap.classFieldName, fMap);
        int mapType = DBEntityMap.FTYPE_STRING;
        int sqlType = java.sql.Types.VARCHAR;
        switch (pd.type) {
            case RDFTypes.STRING_TYPEID:
                mapType = DBEntityMap.FTYPE_STRING;
                //FIXME: Get design-time value for length
                //String maxLength = JdbcUtil.getInstance().getBackingMapConceptProperty(pd, Entity.EXTPROP_PROPERTY_BACKINGSTORE_MAXLENGTH);
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
                mapType = DBEntityMap.FTYPE_LONG;
                sqlType = java.sql.Types.NUMERIC;
                break;
            case RDFTypes.EVENT_TYPEID:
            case RDFTypes.TIME_EVENT_TYPEID:
            case RDFTypes.ADVISORY_EVENT_TYPEID:
                mapType = DBEntityMap.FTYPE_LONG;
                sqlType = java.sql.Types.NUMERIC;
                logger.log(Level.ERROR, "Property: %s has entity type: %s, should we expect this?", pd.propertyName, pd.type); // FIXME: FATIH
                break;
            default:
                sqlType = java.sql.Types.VARCHAR;
                logger.log(Level.ERROR, "Property: %s has unrecognized type: %s", pd.propertyName, pd.type);
                break;
        }
        fMap.tableFieldSqlType = sqlType;
        fMap.tableFieldMappingType = mapType;

        fMap.modifiedIndex = pd.modifiedIndex;
        logger.log(Level.TRACE, "Dirty bit - %s, pos:%d", fMap.classFieldName, pd.modifiedIndex);
        
        if (pd.isArray && (pd.historySize > 0)) {
            // Array with history...
            // Don't care about the actual complex fields?  Handle by the serializer?
            String parentName = ((ConceptDescription)entityModel).getConceptSimpleName();
            fMap.secondaryTableName = (String)_tableNamesMap.get(pd.propertyName);
            logger.log(Level.TRACE, "Concept: %s field: %s secondary TableName: %s", parentName, pd.propertyName, fMap.secondaryTableName);
            fMap.isArray = true;
            fMap.hasHistory = true;
            //fMap.secondaryTableType = // Do we care?? either primitive type of reference.  What about reverse ref?  Handle as system fields?
        } else if (pd.isArray && (pd.historySize == 0)) {
            // Array with no history...
            String parentName = ((ConceptDescription)entityModel).getConceptSimpleName();
            fMap.secondaryTableName = (String)_tableNamesMap.get(pd.propertyName);
            if(fMap.secondaryTableName == null && pd.propertyName.equals(PENDING_EVENT_MAP_PROP_NAME)){
            	fMap.secondaryTableName = (String)_tableNamesMap.get(PENDING_EVENT_MAP_FIELD_NAME);
            }
            if (pd.type == RDFTypes.CONCEPT_TYPEID && pd.referredToConceptPath != null) {
                fMap.isContainment = true;
            }
            logger.log(Level.TRACE, "Concept: %s field: %s secondary TableName: %s", parentName, pd.propertyName, fMap.secondaryTableName);
            fMap.isArray = true;
        } else if (!pd.isArray && (pd.historySize == 0)) {
            // Single value field...
            String parentName = ((ConceptDescription)entityModel).getConceptSimpleName();
            if (pd.type == RDFTypes.DATETIME_TYPEID) {
                fMap.tableFieldName = JdbcUtil.getInstance().getPDName(pd.propertyName + "_" + "tm", aliases);
                fMap.tableExtraFieldName1 = JdbcUtil.getInstance().getPDName(pd.propertyName + "_" + "tz", aliases);
            }
            else if (pd.type == RDFTypes.CONCEPT_TYPEID || pd.type == RDFTypes.CONCEPT_REFERENCE_TYPEID) {
                if (_isProcess && fMap.classFieldName.equals(PARENT_PROC_PROP_NAME)) {
                    fMap.tableFieldName = PARENT_PROC_FIELD_NAME;
                } else {
                    fMap.tableFieldName = JdbcUtil.getInstance().getPDName(pd.propertyName + "_" + "id$", aliases);
                }
            }
            logger.log(Level.TRACE, "Concept: %s field TableName: %s", parentName, fMap.tableFieldName);
        } else { /* (array is false, history is true) */
            // Single item with history...
            String parentName = ((ConceptDescription)entityModel).getConceptSimpleName();
            fMap.secondaryTableName = (String)_tableNamesMap.get(pd.propertyName);
            logger.log(Level.TRACE, "Concept: %s secondary TableName: %s", parentName, fMap.secondaryTableName);
            fMap.hasHistory = true;
        }
    }

    /*
    void configure(Concept conceptModel) throws Exception{
        OracleTypeADT oracleADT= typeDescriptor.getOracleTypeADT();
        List allProperties= conceptModel.getAllPropertyDefinitions();
        properties = new PropertyMap[allProperties.size()];

        for (int i=0; i < allProperties.size(); i++) {
            PropertyDefinition pd= (PropertyDefinition) allProperties.get(i);
            int jdbcIndex= getOracleIndex(pd.getName(), oracleADT);
            if (jdbcIndex >= 0) {
                associate(pd, oracleADT, i, jdbcIndex);
            } else {
                throw new Exception("Oracle Schema Not In Sync With BE Schema");
            }
        }
    }
    */

    // How do we handle statemachine property inside the parent class?
    void configure(ConceptDescription conceptDesc) throws Exception {
        /*
        // FI: Do not re-create _reverseRefTableName here... use name from 'ClassToTable' entry
        String parentName = ((ConceptDescription)entityModel).getConceptSimpleName();
        _reverseRefTableName = JdbcUtil.getInstance().name2JdbcTableWithAlias(parentName, REVERSEREF_FIELD_NAME, aliases);
        */
        _isMetric = conceptDesc.isMetric();
        _isProcess = conceptDesc.isProcess();
        List allProperties = conceptDesc.getProperties();
        //properties = new PropertyMap[allProperties.size() + CONCEPT_SYSTEM_FIELD_COUNT];
        _fieldMaps = new DBEntityMap.DBFieldMap[allProperties.size() + CONCEPT_SYSTEM_FIELD_COUNT];
        setupSystemFieldMaps();

        // the table index is only a relative index, needs to be offset based on the system fields
        int primaryTableColumnIndex = 0;
        int dataFieldIndex = 0;
        int extraTableCount = 0;
        for (int i=0; i < allProperties.size(); i++) {
            ConceptDescription.ConceptProperty pd = (ConceptDescription.ConceptProperty) allProperties.get(i);
            associate(pd, i, dataFieldIndex + CONCEPT_PRIMARY_SYSTEM_FIELD_COUNT, primaryTableColumnIndex + CONCEPT_PRIMARY_SYSTEM_FIELD_COUNT);
            if (pd.isSingleValue == true) {
                if (pd.type == RDFTypes.DATETIME_TYPEID) {
                    // Date fields are represented by using extra column for timezone
                    primaryTableColumnIndex += 2;
                    extraTableCount++;
                }
                else {
                    //(pd.type == RDFTypes.CONCEPT_TYPEID || pd.type == RDFTypes.CONCEPT_REFERENCE_TYPEID)
                    primaryTableColumnIndex++;
                }
                dataFieldIndex++;
            }
            else {
                _secondaryFieldCount++;
            }
        }
        _primaryFieldCount = primaryTableColumnIndex;

        if ((_primaryFieldCount + _secondaryFieldCount) != (allProperties.size() + extraTableCount)) {
            logger.log(Level.WARN, "Field categorization failed: primary: %s, secondary: %s, total: %s",
                this._primaryFieldCount, this._secondaryFieldCount, (allProperties.size() + extraTableCount));
        }

        _secondaryTables = new String[_secondaryFieldCount + 1];
        //_secondaryTables[0] = _reverseRefTableName;
        int secIdx = 0;
        for (int i=0; i<_fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            if (fmap.secondaryTableName != null) {
                _secondaryTables[secIdx++] = fmap.secondaryTableName;
            }
        }
        setupSelectSql();
        setupUpdateSql();
        setupDeleteSql();
        setupInsertSql();

        /*
        DBEntityMap.DBFieldMap fMap = new DBEntityMap.DBFieldMap();
        fMap.classFieldName = REVERSEREF_FIELD_NAME;
        fMap.tableFieldName = JdbcUtil.getInstance().getPDName(REVERSEREF_FIELD_NAME, aliases);
        fMap.secondaryTableName = _reverseRefTableName;
        // FIX THIS HACK - add datetime column and entityref column - hard code the value
        //fMap.tableFieldName = JdbcUtil.getInstance().getPDName(pd.propertyName, aliases);
        fMap.classFieldIndex = -1;
        fMap.tableFieldIndex = -1;
        fMap.tableFieldType = 0;
        fMap.isArray = false;
        fMap.hasHistory = false;
        fMap.isReverseRef = true;
        _fieldMapMap.put(REVERSEREF_FIELD_NAME, fMap);
        */
    }

    /**
     *
     * @throws Exception
     */
    void configure() throws Exception{
        if (entityModel instanceof Concept) {
            //configure((Concept)entityModel);
            logger.log(Level.INFO, "Configure DBConceptMap using Concept is not supported");
        } else {
            configure((ConceptDescription) entityModel);
        }
    }

    /**
     *
     * @param propertyIndex
     * @return
    public PropertyMap getPropertyMap(int propertyIndex) {
        return properties[propertyIndex];
    }
     */

    // **** This is used only for user defined field
    public DBEntityMap.DBFieldMap getFieldMap(int propertyIndex) {
        return _fieldMaps[propertyIndex];
    }

    public DBEntityMap.DBFieldMap getUserFieldMap(int propertyIndex) {
        return _fieldMaps[propertyIndex + CONCEPT_SYSTEM_FIELD_COUNT];
    }

    /**
     *
     * @param typeManager
     * @return
     */
    public Class getEntityClass(RuleServiceProvider rsp) {
        if (conceptClass == null) {
            if (entityModel instanceof Concept) {
                conceptClass=rsp.getTypeManager().getTypeDescriptor( ((Concept)entityModel).getFullPath()).getImplClass();
            } else {
                String implClass=((ConceptDescription)entityModel).getImplClass();
                try {
                    conceptClass=rsp.getClassLoader().loadClass(implClass);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return conceptClass;
    }

    public String getTableName() {
        return _tableName;
    }

    public void setTableName(String tableName) {
        this._tableName = tableName;
    }

    public int getFieldCount() {
        return _fieldMaps.length;
    }

    public int getPrimaryFieldCount() {
        return _primaryFieldCount;
    }

    public int getSecondaryFieldCount() {
        return _secondaryFieldCount;
    }

    public String getReverseRefTableName() {
        return _reverseRefTableName;
    }

    public String[] getSecondaryTableNames() {
        return _secondaryTables;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("Concept class : " + _entityClass);
        sb.append(", table name : " + _tableName);
        //sb.append(", number of properties : " + properties.length);
        sb.append(", number of field maps : " + _fieldMaps.length);
        sb.append(", prim field count : " + _primaryFieldCount);
        sb.append(", sec field count : " + _secondaryFieldCount);
        sb.append(", reverseRefTable : " + _reverseRefTableName);
        for (int i=0; i < _fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            sb.append("Class field : " + fmap.classFieldName);
            sb.append(", table field : " + fmap.tableFieldName);
            sb.append(", table extra field : " + fmap.tableExtraFieldName1);
            sb.append(", Class field index : " + fmap.classFieldIndex);
            sb.append(", data field index : " + fmap.dataObjectFieldIndex);
            sb.append(", table field index : " + fmap.tableFieldIndex);
            sb.append(", table field sql : " + fmap.tableFieldSqlType);
            sb.append(", secondary table : " + fmap.secondaryTableName);
            sb.append(", isArray : " + fmap.isArray);
            sb.append(", hasHistory : " + fmap.hasHistory);
            sb.append("\n");
        }
        return sb.toString();
    }

    private void setupSelectSql() {
        //StringBuffer sb = new StringBuffer("select T.cacheId, T.id$, T.extId$, T.state$, T.time_created$, T.time_last_modified$, T.parent$_id$");
        StringBuffer sb = new StringBuffer("select T.cacheId");
        for (int i=0; i<_fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            if (fmap.secondaryTableName == null) {
                sb.append(", T.");
                sb.append(fmap.tableFieldName);
                if (fmap.tableFieldMappingType == DBEntityMap.FTYPE_DATETIME) {
                    sb.append(", T.");
                    sb.append(fmap.tableExtraFieldName1);
                }
            } else {
                sb.append("");
            }
        }
        sb.append(" from " + _tableName + " T ");
        _primarySelectSql = sb.toString();
        logger.log(Level.DEBUG, this._primarySelectSql);
        if (RDBMSType.sSqlType.optimizable()) {
            _primarySelectSql = RDBMSType.sSqlType.optimizeSelectStatement(_primarySelectSql);
            logger.log(Level.DEBUG, "Optimized:" + this._primarySelectSql);
        }

        _reverseRefSelectSql = "select T.pid$, T.propertyName$, T.id$ from " + _reverseRefTableName + " T ";
        logger.log(Level.DEBUG, this._reverseRefSelectSql);
        if (RDBMSType.sSqlType.optimizable()) {
            _reverseRefSelectSql = RDBMSType.sSqlType.optimizeSelectStatement(_reverseRefSelectSql);
            logger.log(Level.DEBUG, "Optimized:" + this._reverseRefSelectSql);
        }
        _secondarySelectSqlMap.put(REVERSEREF_FIELD_NAME, _reverseRefSelectSql);

        for (int i=0; i<_fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            if (fmap.secondaryTableName != null && !fmap.classFieldName.equals(REVERSEREF_FIELD_NAME)) {
                String selectSql = setupSecondarySelectSql(fmap);
                logger.log(Level.DEBUG, selectSql);
                if (RDBMSType.sSqlType.optimizable()) {
                    selectSql = RDBMSType.sSqlType.optimizeSelectStatement(selectSql);
                    logger.log(Level.DEBUG, "Optimized:" + selectSql);
                }
                _secondarySelectSqlMap.put(fmap.classFieldName, selectSql);
            }
        }
    }

    private String setupSecondarySelectSql(DBEntityMap.DBFieldMap fmap) {
        StringBuffer sb = new StringBuffer("select T.pid$, ");
        if (fmap.isArray && fmap.hasHistory) {
            sb.append("T.valPid$, T.howMany, T.timeidx, ");
        }
        else if (fmap.isArray && !fmap.hasHistory) {
            sb.append("T.valPid$, ");
        }
        else if (!fmap.isArray && fmap.hasHistory) {
            sb.append("T.howMany, T.timeidx, ");
        }
        else {
            logger.log(Level.INFO, "%s should not be a secondary attribute", fmap.classFieldName);
            return null;
        }
        switch (fmap.tableFieldType) {
            case RDFTypes.CONCEPT_TYPEID:
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
            case RDFTypes.EVENT_TYPEID:
            case RDFTypes.TIME_EVENT_TYPEID:
            case RDFTypes.ADVISORY_EVENT_TYPEID:
                sb.append("T.id$");
                break;
            case RDFTypes.DATETIME_TYPEID:
                sb.append("T.tm, T.tz");
                break;
            default:
                sb.append("T.val");
                break;
        }
        sb.append(" from " + fmap.secondaryTableName + " T ");
        return sb.toString();
    }

    private void setupUpdateSql() {
        //StringBuffer sb = new StringBuffer("update " + tableName + " T set T.cacheId=?, T.id$=?, T.extId$=?, T.state$=?, T.time_last_modified$=?, T.parent$_id$=?");
        StringBuffer sb = new StringBuffer("update " + _tableName + " set cacheId=?");
        _lastUpdateParamPos = 2;
        for (int i=0; i<_fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            if (fmap.secondaryTableName == null && !fmap.classFieldName.equals(TCREATED_FIELD_NAME)) {
                _lastUpdateParamPos++;
                sb.append(", ");
                sb.append(fmap.tableFieldName);
                sb.append("=?");
                if (fmap.tableFieldMappingType == DBEntityMap.FTYPE_DATETIME) {
                    sb.append(", ");
                    sb.append(fmap.tableExtraFieldName1);
                    sb.append("=?");
                    _lastUpdateParamPos++;
                }
            }
        }
        sb.append(" where ");
        sb.append(ID_FIELD_NAME);
        sb.append("=?");
        _primaryUpdateSql = sb.toString();
        _primaryVersionUpdateSql = "update " + _tableName + " set cacheId=?, " +
                                    TMODIFIED_FIELD_NAME + "=?, " + PARENT_FIELD_NAME + "=? where " + ID_FIELD_NAME + "=?";
        logger.log(Level.DEBUG, this._primaryUpdateSql);
        if (RDBMSType.sSqlType.optimizable()) {
            _primaryUpdateSql = RDBMSType.sSqlType.optimizeUpdateStatement(_primaryUpdateSql);
            logger.log(Level.DEBUG, "Optimized:" + this._primaryUpdateSql);
        }
        logger.log(Level.DEBUG, this._primaryVersionUpdateSql);
        if (RDBMSType.sSqlType.optimizable()) {
            _primaryVersionUpdateSql = RDBMSType.sSqlType.optimizeUpdateStatement(_primaryVersionUpdateSql);
            logger.log(Level.DEBUG, "Optimized:" + this._primaryVersionUpdateSql);
        }

        // No need to do this because secondary table all needs to do delete and insert
        /*
        _reverseRefUpdateSql = "update " + _reverseRefTableName + " T set T.pid$=?, T.propertyName$=?, T.id$=?";
        logger.log(Level.DEBUG, _reverseRefUpdateSql);
        _reverseRefUpdateSql = optimize...;
        logger.log(Level.DEBUG, "Optimized:" + _reverseRefUpdateSql);
        _secondaryUpdateSqlMap.put(REVERSEREF_FIELD_NAME, _reverseRefUpdateSql);

        for (int i=0; i<_fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            if (fmap.secondaryTableName != null && !fmap.classFieldName.equals(REVERSEREF_FIELD_NAME)) {
                String updateSql = setupSecondaryUpdateSql(fmap);
                logger.log(Level.DEBUG, updateSql);
                updateSql = optimize...;
                logger.log(Level.DEBUG, "Optimized:" + updateSql);
                _secondaryUpdateSqlMap.put(fmap.classFieldName, updateSql);
            }
        }
        */
    }

    @Deprecated
    @SuppressWarnings("unused")
    private String setupSecondaryUpdateSql(DBEntityMap.DBFieldMap fmap) {
        StringBuffer sb = new StringBuffer("update " + fmap.secondaryTableName + " set pid$=?, ");
        if (fmap.isArray && fmap.hasHistory) {
            sb.append("valPid$=?, howMany=?, timeidx=?, ");
        }
        else if (fmap.isArray && !fmap.hasHistory) {
            sb.append("valPid$=?, ");
        }
        else if (!fmap.isArray && fmap.hasHistory) {
            sb.append("howMany=?, timeidx=?, ");
        }
        else {
            logger.log(Level.INFO, "%s should not be a secondary attribute", fmap.classFieldName);
            return null;
        }
        switch (fmap.tableFieldType) {
            case RDFTypes.CONCEPT_TYPEID:
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
            case RDFTypes.EVENT_TYPEID:
            case RDFTypes.TIME_EVENT_TYPEID:
            case RDFTypes.ADVISORY_EVENT_TYPEID:
                sb.append("id$=?");
                break;
            case RDFTypes.DATETIME_TYPEID:
                sb.append("tm=?, tz=?");
                break;
            default:
                sb.append("val=?");
                break;
        }
        return sb.toString();
    }

    private void setupDeleteSql() {
        _primaryDeleteSql = "delete from " + _tableName + "  where id$=?";
        logger.log(Level.DEBUG, this._primaryDeleteSql);
        if (RDBMSType.sSqlType.optimizable()) {
            _primaryDeleteSql = RDBMSType.sSqlType.optimizeDeleteStatement(_primaryDeleteSql);
            logger.log(Level.DEBUG, "Optimized:" + this._primaryDeleteSql);
        }

        _reverseRefDeleteSql = "delete from " + _reverseRefTableName + "  where pid$=?";
        logger.log(Level.DEBUG, this._reverseRefDeleteSql);
        if (RDBMSType.sSqlType.optimizable()) {
            _reverseRefDeleteSql = RDBMSType.sSqlType.optimizeDeleteStatement(_reverseRefDeleteSql);
            logger.log(Level.DEBUG, "Optimized:" + this._reverseRefDeleteSql);
        }
        _secondaryDeleteSqlMap.put(REVERSEREF_FIELD_NAME, _reverseRefDeleteSql);
        _secondaryRemoveSqlMap.put(REVERSEREF_FIELD_NAME, _reverseRefDeleteSql);

        for (int i=0; i<_fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            if (fmap.secondaryTableName != null && !fmap.classFieldName.equals(REVERSEREF_FIELD_NAME)) {
                String deleteSql = "delete from " + fmap.secondaryTableName + "  where pid$=?";
                String removeSql = "delete from " + fmap.secondaryTableName + "  where pid$=? and id$=?";
                logger.log(Level.DEBUG, deleteSql);
                logger.log(Level.DEBUG, removeSql);
                if (RDBMSType.sSqlType.optimizable()) {
                    deleteSql = RDBMSType.sSqlType.optimizeDeleteStatement(deleteSql);
                    removeSql = RDBMSType.sSqlType.optimizeDeleteStatement(removeSql);
                    logger.log(Level.DEBUG, "Optimized:" + deleteSql);
                    logger.log(Level.DEBUG, "Optimized:" + removeSql);
                }
                _secondaryDeleteSqlMap.put(fmap.classFieldName, deleteSql);
                _secondaryRemoveSqlMap.put(fmap.classFieldName, removeSql);
            }
        }
    }

    private void setupInsertSql() {
        //StringBuffer sb = new StringBuffer("insert into " + tableName + " (cacheId, id$, extId$, state$, time_created$, time_last_modified$, parent$_id$");
        StringBuffer sb = new StringBuffer("insert into " + _tableName + " (cacheId");
        StringBuffer sbValue = new StringBuffer(" values(?");
        for (int i=0; i<_fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            if (fmap.secondaryTableName == null) {
                sb.append(", ");
                sb.append(fmap.tableFieldName);
                sbValue.append(",?");
                if (fmap.tableFieldMappingType == DBEntityMap.FTYPE_DATETIME) {
                    sb.append(", ");
                    sb.append(fmap.tableExtraFieldName1);
                    sbValue.append(",?");
                }
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

        _reverseRefInsertSql = "insert into " + _reverseRefTableName + " (pid$, propertyName$, id$) values(?,?,?)";
        logger.log(Level.DEBUG, this._reverseRefInsertSql);
        if (RDBMSType.sSqlType.optimizable()) {
            _reverseRefInsertSql = RDBMSType.sSqlType.optimizeInsertStatement(_reverseRefInsertSql);
            logger.log(Level.DEBUG, "Optimized:" + this._reverseRefInsertSql);
        }
        _secondaryInsertSqlMap.put(REVERSEREF_FIELD_NAME, _reverseRefInsertSql);

        for (int i=0; i<_fieldMaps.length; i++) {
            DBEntityMap.DBFieldMap fmap = _fieldMaps[i];
            if (fmap.secondaryTableName != null && !fmap.classFieldName.equals(REVERSEREF_FIELD_NAME)) {
                String insertSql = setupSecondaryInsertSql(fmap);
                logger.log(Level.DEBUG, insertSql);
                if (RDBMSType.sSqlType.optimizable()) {
                    insertSql = RDBMSType.sSqlType.optimizeInsertStatement(insertSql);
                    logger.log(Level.DEBUG, "Optimized:" + insertSql);
                }
                _secondaryInsertSqlMap.put(fmap.classFieldName, insertSql);
            }
        }
    }

    private String setupSecondaryInsertSql(DBEntityMap.DBFieldMap fmap) {
        StringBuffer sb = new StringBuffer("insert into " + fmap.secondaryTableName + "  (pid$, ");
        StringBuffer sbVal = new StringBuffer("values(?,");
        if (fmap.isArray && fmap.hasHistory) {
            sb.append("valPid$, howMany, timeidx, ");
            sbVal.append("?,?,?,");
        }
        else if (fmap.isArray && !fmap.hasHistory) {
            sb.append("valPid$, ");
            sbVal.append("?,");
        }
        else if (!fmap.isArray && fmap.hasHistory) {
            sb.append("howMany, timeidx, ");
            sbVal.append("?,?,");
        }
        else {
            logger.log(Level.INFO, "%s should not be a secondary attribute", fmap.classFieldName);
            return null;
        }
        switch (fmap.tableFieldType) {
            case RDFTypes.CONCEPT_TYPEID:
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
            case RDFTypes.EVENT_TYPEID:
            case RDFTypes.TIME_EVENT_TYPEID:
            case RDFTypes.ADVISORY_EVENT_TYPEID:
                sb.append("id$");
                sbVal.append("?");
                break;
            case RDFTypes.DATETIME_TYPEID:
                sb.append("tm, tz");
                sbVal.append("?, ?");
                break;
            default:
                sb.append("val");
                sbVal.append("?");
                break;
        }
        sb.append(") ");
        sb.append(sbVal);
        sb.append(")");
        return sb.toString();
    }

    public Map getSecondarySelectSqlMap() {
        return _secondarySelectSqlMap;
    }

    public Map getSecondaryUpdateSqlMap() {
        return _secondaryUpdateSqlMap;
    }

    public Map getSecondaryDeleteSqlMap() {
        return _secondaryDeleteSqlMap;
    }

    public Map getSecondaryRemoveSqlMap() {
        return _secondaryRemoveSqlMap;
    }

    public Map getSecondaryInsertSqlMap() {
        return _secondaryInsertSqlMap;
    }

    public String getReverseRefSelectSql() {
        return _reverseRefSelectSql;
    }

    public String getReverseRefUpdateSql() {
        return _reverseRefUpdateSql;
    }

    public String getReverseRefDeleteSql() {
        return _reverseRefDeleteSql;
    }

    public String getReverseRefInsertSql() {
        return _reverseRefInsertSql;
    }

    public DBEntityMap.DBFieldMap getFieldMap(String fieldName) {
        return (DBEntityMap.DBFieldMap) _fieldMapMap.get(fieldName);
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

        fMap = new DBEntityMap.DBFieldMap();
        fMap.classFieldName = TMODIFIED_FIELD_NAME;
        fMap.tableFieldName = TMODIFIED_FIELD_NAME;
        fMap.classFieldIndex = -1;
        fMap.dataObjectFieldIndex = 4;
        fMap.tableFieldIndex = 4;
        fMap.tableFieldType = -1;
        fMap.tableFieldSqlType = java.sql.Types.TIMESTAMP;
        fMap.tableFieldMappingType = DBEntityMap.FTYPE_TIMESTAMP;
        _fieldMaps[4] = fMap;
        _fieldMapMap.put(TMODIFIED_FIELD_NAME, fMap);

        fMap = new DBEntityMap.DBFieldMap();
        fMap.classFieldName = PARENT_FIELD_NAME;
        fMap.tableFieldName = PARENT_FIELD_NAME;
        fMap.classFieldIndex = -1;
        fMap.dataObjectFieldIndex = 5;
        fMap.tableFieldIndex = 5;
        fMap.tableFieldType = RDFTypes.LONG_TYPEID;
        fMap.tableFieldSqlType = java.sql.Types.NUMERIC;
        fMap.tableFieldMappingType = DBEntityMap.FTYPE_LONG;
        _fieldMaps[5] = fMap;
        _fieldMapMap.put(PARENT_FIELD_NAME, fMap);

        fMap = new DBEntityMap.DBFieldMap();
        fMap.classFieldName = REVERSEREF_FIELD_NAME;
        fMap.tableFieldName = JdbcUtil.getInstance().getPDName(REVERSEREF_FIELD_NAME, aliases);
        fMap.secondaryTableName = _reverseRefTableName;
        fMap.classFieldIndex = -1;
        fMap.dataObjectFieldIndex = -1;
        fMap.tableFieldIndex = -1;
        fMap.tableFieldType = 0;
        fMap.isArray = false;
        fMap.hasHistory = false;
        fMap.isReverseRef = true;
        _fieldMaps[6] = fMap;
        _fieldMapMap.put(REVERSEREF_FIELD_NAME, fMap);
    }

    public int getLastUpdateParamPos() {
        return _lastUpdateParamPos;
    }

    public boolean isMetric() {
        return _isMetric;
    }

    public boolean isProcess() {
        return _isProcess;
    }
    
    public void setProcessVersion(int version) {
        this._processVersion = version;
    }

    public int getProcessVersion() {
        return this._processVersion;
    }

    /*
     * build all insert/update/delete statements
     * loop fieldMaps to create insert/update statement and rely on
     * the same ordering of the fieldmaps to do the positioning
     * take system fields into consideration
     * then look for secondary table
     * create the sql based on the array and/or history
     * the position of the fields are hardcoded in this case
     * Objective, compile first to test out new serialization and cache aside
     * trace through code to see the read call and write call
     * then try jdbc mappings
     */
}
