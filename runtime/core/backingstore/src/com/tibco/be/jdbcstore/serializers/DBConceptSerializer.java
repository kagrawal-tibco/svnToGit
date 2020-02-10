package com.tibco.be.jdbcstore.serializers;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tibco.be.jdbcstore.DateTimeValueTuple;
import com.tibco.be.jdbcstore.HistoryTableTuple;
import com.tibco.be.jdbcstore.HistoryTuple;
import com.tibco.be.jdbcstore.ReverseRefTuple;
import com.tibco.be.jdbcstore.impl.DBConceptMap;
import com.tibco.be.jdbcstore.impl.DBEntityMap;
import com.tibco.be.oracle.OracleLOBManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.DateTimeTuple;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;

public class DBConceptSerializer implements ConceptSerializer {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DBConceptSerializer.class);
    //static final int CONCEPT_FIELD_OFFSET = 6; // 6 primary system fields

    public boolean error=false;
    public String msg;
    String errorPropertyName;
    DBConceptMap entityMap;
    Object [] attributes;
    int cur=0;
    int curObjIndex=0;
    DBEntityMap.DBFieldMap curFieldMap;
    Connection conn;
    String qualifier=null;
    boolean inArray=false;
    Object[] arrayAttributes; // array for storing current array attribute value
    int arrayCursor=0; //index position of the current array attribute
    int currentHistorySize=0;
    int currentHistoryIndex=0;
    Map secondaryAttributeMap = null; //stores data for secondary tables
    boolean isCurrentFieldPrimary = false;
    boolean useExplicitTemporaryBlobs;
    int[] _systemFieldSqlTypes = {java.sql.Types.NUMERIC, java.sql.Types.VARCHAR, java.sql.Types.CHAR,
        java.sql.Types.TIMESTAMP, java.sql.Types.TIMESTAMP, java.sql.Types.NUMERIC};
    private ArrayList<Clob> clobs = null;

    /**
     *
     * 
     */
    public DBConceptSerializer(DBConceptMap entityMap, Connection conn, String qual, boolean useExplicitTemporaryBlobs) {
        this.entityMap = entityMap;
        this.conn = conn;
        this.qualifier = qual;
        this.useExplicitTemporaryBlobs = useExplicitTemporaryBlobs;
        this.clobs = new ArrayList<Clob>();
    }

    /**
     *
     * @return
     * FIX THIS - Need to be replaced by normal objects or something else
    public Datum[] getOracleAttributes() throws Exception {
        //STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(), oracle, attributes);
        //STRUCT s= new STRUCT((StructDescriptor) StructDescriptor.getTypeDescriptor(entityDescription.getOracleTypeName(), oracle),oracle, attributes);
        STRUCT s= new STRUCT(entityMap.getTypeDescriptor(conn), conn, attributes);
        return s.getOracleAttributes();
    }
     */

    public Object[] getAttributes() throws Exception {
        return attributes;
    }

    public Map getSecondaryAttributeMap() throws Exception {
        return secondaryAttributeMap;
    }

    public int[] getSystemFieldSqlTypes() throws Exception {
        return _systemFieldSqlTypes;
    }

    /**
     *
     * @param clz
     * @param key
     * @param extKey
     * @param state
     */
    public void startConcept(Class clz, long key, String extKey, int state,int version) {
        try {
            if (!error) {
                secondaryAttributeMap = new HashMap();
                //attributes = new Object[entityMap.getTypeDescriptor().getLength()];
                // 6 is the additional system fields not there in the concept model java class
                // reverseRef system field occupies a separate table and therefore not part of 'attributes'
                // and this I believe is the only field that is not part of 'attributes'.
                // FIX THIS - Need to extract this info from entityMap class
                attributes = new Object[entityMap.getPrimaryFieldCount() + DBConceptMap.CONCEPT_PRIMARY_SYSTEM_FIELD_COUNT];
                attributes[0] = new Long(key);
                if (extKey != null) {
                    attributes[1] = extKey;
                }
                attributes[2]= "C"; // Place Holder for state information
                long currenttime= System.currentTimeMillis();
                attributes[3]= new Timestamp(currenttime);
                attributes[4]= new Timestamp(currenttime);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void startParentConcept(ConceptOrReference parent) {
        try {
            if (parent != null) {
                attributes[5] = new Long(parent.getId());
            } else {
                attributes[5] = null;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void endParentConcept() {

    }

    // Need to go to a different table & get pid from attribute[0].
    public void startReverseReferences(ConceptImpl.RevRefItr itr, int size) {
        try {
            if (size > 0) {
                ReverseRefTuple[] tuples = new ReverseRefTuple[size];
                for (int i=0; i < size; i++) {
                    itr.next();
                    ReverseRefTuple tuple = new ReverseRefTuple(itr.propName(), itr.reverseRef().getId());
                    tuples[i] = tuple;
                }
                secondaryAttributeMap.put(DBConceptMap.REVERSEREF_FIELD_NAME, tuples);
            } else {
                // Put an empty array or have it remove like this?
                //secondaryAttributeMap.remove("reverseref");
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void endReverseReferences() {

    }

    public void endConcept() {

    }

    /**
     *
     * @return
     */
    public int getType() {
        return ConceptSerializer.TYPE_STREAM;
    }

    /**
     *
     * @param propertyName
     * @param propertyIndex
     * @param isSet
     * FIX THIS - using the curFieldMap to determine the usage of attributes field or not.
     * the list of values in attributes should match the sql statement
     */
    public void startProperty(String propertyName, int propertyIndex, boolean isSet) {
        try {
            if (!error) {
                if (!inArray) {
                    curFieldMap = entityMap.getUserFieldMap(propertyIndex);
                    //cur = curFieldMap.tableFieldIndex + entityMap.CONCEPT_PRIMARY_SYSTEM_FIELD_COUNT; // The index has taken the system field positions into consideration
                    cur = curFieldMap.tableFieldIndex;
                    curObjIndex = curFieldMap.dataObjectFieldIndex;
                    isCurrentFieldPrimary = (curFieldMap.secondaryTableName == null);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param propertyName
     * @param index
     * @param length
     */
    public void startPropertyArray(String propertyName, int index, int length) {
        try {
            if (!error) {
                curFieldMap = entityMap.getUserFieldMap(index);
                //cur = curFieldMap.tableFieldIndex + entityMap.CONCEPT_PRIMARY_SYSTEM_FIELD_COUNT; // for array, the index should be zero because it uses a separate table
                cur = curFieldMap.tableFieldIndex;
                curObjIndex = curFieldMap.dataObjectFieldIndex;
                isCurrentFieldPrimary = (curFieldMap.secondaryTableName == null);
                inArray=true;
                // if length is 0, secondaryAttributeMap will not have this entry
                if (length > 0) {
                    arrayAttributes = new Object[length];
                    secondaryAttributeMap.put(propertyName, arrayAttributes);
                    // Find the property Description
                    //arrayAttributes = new Datum[length];
                    //attributes[cur]= arrayAttributes;
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param index
     */
    public void startPropertyArrayElement(int index) {
        this.arrayCursor = index;
    }

    /**
     *
     */
    public void endPropertyArrayElement() {

    }

    /**
     *
     * @param propertyName
     * @param index
     * @param isSet
     * @param historySize
     * FIX THIS - When is this going to be called?
     */
    public void startPropertyAtom(String propertyName, int index, boolean isSet, int historyIndex, int historySize) {
        try {
            if (!error) {
                if (isSet) {
                    if (!inArray) {
                        curFieldMap = entityMap.getUserFieldMap(index);
                        //cur = curFieldMap.tableFieldIndex + entityMap.CONCEPT_PRIMARY_SYSTEM_FIELD_COUNT;
                        cur = curFieldMap.tableFieldIndex;
                        curObjIndex = curFieldMap.dataObjectFieldIndex;
                        isCurrentFieldPrimary = (curFieldMap.secondaryTableName == null);
                    }
                    currentHistorySize=historySize;
                    currentHistoryIndex=historyIndex;
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     */
    public void endProperty() {
        curFieldMap=null;
        inArray=false;
        arrayAttributes=null;
        arrayCursor=0;
        currentHistorySize=0;
        currentHistoryIndex=0;
        isCurrentFieldPrimary = false;
    }

    /**
     *
     * @param datum
     */
    void setCurrentDatum(Object datum) throws Exception{
        if (!inArray) {
            if (datum != null) {
                if (isCurrentFieldPrimary) {
                    attributes[curObjIndex]=datum;
                }
                else {
                    secondaryAttributeMap.put(curFieldMap.classFieldName, datum);
                }
            }
        } else {
            if (datum != null) {
                arrayAttributes[arrayCursor] = datum;
            }
        }
    }

    /**
     * Writes a simple string atom
     * @param s
     * FIX THIS - Not supporting clob at this moment
     */
    public void writeStringProperty(String s) {
        try {
            if (!error) {
                if (s != null) {
                    //FIXME: This call will not work since runtime model can't have 'SqlType==CLOB'
                    if (curFieldMap.tableFieldSqlType == java.sql.Types.CLOB) {
                        if (useExplicitTemporaryBlobs) {
                            Clob clob= OracleLOBManager.createCLOB(conn, s);
                            setCurrentDatum(clob);
                            clobs.add(clob);
                        } else {
                            //TODO: Should this be set to something different?
                            byte[] bytes=s.getBytes();
                            setCurrentDatum(bytes);
                        }
                    } else {
                        setCurrentDatum(s);
                    }
                } else {
                    setCurrentDatum(s);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param i
     */
    public void writeIntProperty(int i) {
        try {
            if (!error) {
                setCurrentDatum(new Integer(i));
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param b
     */
    public void writeBooleanProperty(boolean b) {
        try {
            if (!error) {
                setCurrentDatum(new Integer(b ? 1 : 0));
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param l
     */
    public void writeLongProperty(long l) {
        try {
            if (!error) {
                setCurrentDatum(new Long(l));
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param d
     */
    public void writeDoubleProperty(double d) {
        try {
            if (!error) {
                Double atom = null;
                if (Double.isNaN(d)) {
                    atom = new Double(0.0d);
                } else {
                    atom = new Double(d);
                }
                setCurrentDatum(atom);
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param date
     */
    public void writeDateTimeProperty(DateTimeTuple date) {
        try {
            if (!error) {
                DateTimeValueTuple dtTuple = null;
                if (date != null) {
                    if (date.getTimeZone() != null) {
                        dtTuple = new DateTimeValueTuple(new Timestamp(date.getTime()), date.getTimeZone());
                        setCurrentDatum(dtTuple);
                    } else {
                        // FIX THIS - use a null directly dtTuple = new DateTimeValueTuple(null, null);
                        setCurrentDatum(null);
                    }
                } else if (inArray){
                    logger.log(Level.INFO, "Not sure how to handle null datetime for array");
                    // FIX THIS - use a null directly dtTuple = new DateTimeValueTuple(null, null);
                    setCurrentDatum(null);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param ref
     */
    public void writeReferenceConceptProperty(ConceptOrReference ref) {
        try {
            if (!error) {
                if (ref != null) {
                    setCurrentDatum(new Long(ref.getId()));
                } else if (inArray) {
                    /* What is this?
                    Datum [] attributes=new Datum[1];
                    attributes[0]= null;
                    setCurrentDatum(attributes);
                    */
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param ref
     */
    public void writeContainedConceptProperty(ConceptOrReference ref) {
        try {
            if (!error) {
                if (ref != null) {
                    setCurrentDatum(new Long(ref.getId()));
                } else {
                    /* What is this?
                    Datum [] attributes=new Datum[1];
                    attributes[0]= null;
                    setCurrentDatum(attributes);
                    */
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param attributes
     * @return
     * @throws Exception
    HistoryTableTuple historyTable(HistoryTuple[] aht) throws Exception{
        HistoryTuple ht;
        if (inArray) {
            PropertyArrayMap pam= (PropertyArrayMap) curFieldMap;
            PropertyAtomMap pm= (PropertyAtomMap) pam.getBaseMap();
            ArrayDescriptor ad= (ArrayDescriptor) pam.getTypeDescriptor();
            StructDescriptor sd= (StructDescriptor) StructDescriptor.getTypeDescriptor(ad.getBaseName(), conn);
            atts[0]= new oracle.sql.NUMBER(attributes.length);
            atts[1]=new ARRAY(pm.getHistoryTableDescriptor(),conn,attributes);
            return new oracle.sql.STRUCT(sd, conn,atts);
        } else {
            DBEntityMap.DBFieldMap fm = curFieldMap;
            atts[0]= new oracle.sql.NUMBER(attributes.length);
            atts[1]=new ARRAY(pm.getHistoryTableDescriptor(),conn,attributes);
            return new oracle.sql.STRUCT((StructDescriptor) curFieldMap.getTypeDescriptor(), conn,atts);
        }
    }
    */

    DBEntityMap.DBFieldMap currentMap() {
        return curFieldMap;
        /*
        if (inArray) {
            return ((PropertyArrayMap) curFieldMap).getBaseMap();
        } else {
            return curFieldMap;
        }
        */
    }

    /**
     *
     * @param s
     * @param time
     */
    public void writeStringProperty(String s[], long time[]) {
        try {
            if (!error) {
                if (s != null) {
                    HistoryTuple []aht = new HistoryTuple[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Timestamp ts = new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]);
                        String cur = s[mapIndex(currentHistoryIndex, time,i)];
                        HistoryTuple ht = new HistoryTuple(ts, cur);
                        aht[i] = ht;
                    }
                    HistoryTableTuple htt = new HistoryTableTuple(currentHistorySize, aht);
                    setCurrentDatum(htt);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param val
     * @param time
     */
    public void writeIntProperty(int []val, long time[]) {
        try {
            if (!error) {
                if (val != null) {
                    HistoryTuple []aht = new HistoryTuple[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Timestamp ts = new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]);
                        Integer cur = new Integer(val[mapIndex(currentHistoryIndex, time,i)]);
                        HistoryTuple ht = new HistoryTuple(ts, cur);
                        aht[i] = ht;
                    }
                    HistoryTableTuple htt = new HistoryTableTuple(currentHistorySize, aht);
                    setCurrentDatum(htt);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param b
     * @param time
     */
    public void writeBooleanProperty(boolean b[], long time[]) {
        try {
            if (!error) {
                if (b != null) {
                    HistoryTuple []aht = new HistoryTuple[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Timestamp ts = new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]);
                        Integer cur = new Integer(b[mapIndex(currentHistoryIndex, time,i)] ? 1:0);
                        HistoryTuple ht = new HistoryTuple(ts, cur);
                        aht[i] = ht;
                    }
                    HistoryTableTuple htt = new HistoryTableTuple(currentHistorySize, aht);
                    setCurrentDatum(htt);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param l
     * @param time
     */
    public void writeLongProperty(long l[], long time[]) {
        try {
            if (!error) {
                if (l != null) {
                    HistoryTuple []aht = new HistoryTuple[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Timestamp ts = new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]);
                        Long cur = new Long(l[mapIndex(currentHistoryIndex, time,i)]);
                        HistoryTuple ht = new HistoryTuple(ts, cur);
                        aht[i] = ht;
                    }
                    HistoryTableTuple htt = new HistoryTableTuple(currentHistorySize, aht);
                    setCurrentDatum(htt);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param d
     * @param time
     */
    public void writeDoubleProperty(double d[], long time[]) {
        try {
            if (!error) {
                if (d != null) {
                    HistoryTuple []aht = new HistoryTuple[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        double t=d[mapIndex(currentHistoryIndex, time,i)];
                        if (Double.isNaN(t)) {
                            t=0.00;
                        }
                        Timestamp ts = new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]);
                        Double cur = new Double(t);
                        HistoryTuple ht = new HistoryTuple(ts, cur);
                        aht[i] = ht;
                    }
                    HistoryTableTuple htt = new HistoryTableTuple(currentHistorySize, aht);
                    setCurrentDatum(htt);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param date
     * @param tz
     * @param time
     */
    public void writeDateTimeProperty(long date[], String tz[], long time[]) {
        try {
            if (!error) {
                if (date != null) {
                    HistoryTuple []aht = new HistoryTuple[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Timestamp ts = new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]);
                        String tz1=tz[mapIndex(currentHistoryIndex, time,i)];
                        HistoryTuple ht = null;
                        if (tz1 != null) {
                            Timestamp tval = new java.sql.Timestamp(date[mapIndex(currentHistoryIndex, time,i)]);
                            String tzval = tz[mapIndex(currentHistoryIndex, time,i)];
                            DateTimeValueTuple dtt = new DateTimeValueTuple(tval, tzval);
                            ht = new HistoryTuple(ts, dtt);
                        } else {
                            ht = new HistoryTuple(ts, null);
                        }
                        aht[i] = ht;
                    }
                    HistoryTableTuple htt = new HistoryTableTuple(currentHistorySize, aht);
                    setCurrentDatum(htt);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param date
     * @param time
     */
    public void writeDateTimeProperty(DateTimeTuple date[], long time[]) {
        try {
            logger.log(Level.ERROR, "Unimplemented writeDateTimeProperty(DateTimeTuple, long) is being called");
            /*
            if (!error) {
                if (date != null) {
                    HistoryTuple []aht = new HistoryTuple[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Timestamp ts = new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]);
                        Datum [] dt = new Datum[2];
                        if (dt[1] != null) {
                            dt[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(date[mapIndex(currentHistoryIndex, time,i)].getTime()));
                            dt[1]= new oracle.sql.CHAR(date[mapIndex(currentHistoryIndex, time,i)].getTimeZone(), null);
                        }
                        atts[1]= new oracle.sql.STRUCT(pm.getPrimitiveDescriptor(), conn, dt);
                        attributes[i]= new oracle.sql.STRUCT(pm.getHistoryTupleDescriptor(), conn, atts);
                    }
                    STRUCT ht= historyTable(attributes);
                    setCurrentDatum(ht);
                }
            }
            */
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param ref
     * @param time
     */
    public void writeReferenceConceptProperty(ConceptOrReference ref[], long time[]) {
        try {
            if (!error) {
                if (ref != null) {
                    HistoryTuple []aht = new HistoryTuple[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Timestamp ts = new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]);
                        ConceptOrReference r= ref[mapIndex(currentHistoryIndex, time,i)];
                        Long cur = null;
                        if (r != null) {
                            cur = new Long(ref[mapIndex(currentHistoryIndex, time,i)].getId());
                        }
                        HistoryTuple ht = new HistoryTuple(ts, cur);
                        aht[i] = ht;
                    }
                    HistoryTableTuple htt = new HistoryTableTuple(currentHistorySize, aht);
                    setCurrentDatum(htt);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param ref
     * @param time
     */
    public void writeContainedConceptProperty(ConceptOrReference ref[], long time[]) {
        try {
            if (!error) {
                if (ref != null) {
                    HistoryTuple []aht = new HistoryTuple[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Timestamp ts = new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]);
                        ConceptOrReference r= ref[mapIndex(currentHistoryIndex, time,i)];
                        Long cur = null;
                        if (r != null) {
                            cur = new Long(ref[mapIndex(currentHistoryIndex, time,i)].getId());
                        }
                        HistoryTuple ht = new HistoryTuple(ts, cur);
                        aht[i] = ht;
                    }
                    HistoryTableTuple htt = new HistoryTableTuple(currentHistorySize, aht);
                    setCurrentDatum(htt);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param m_index
     * @param m_time
     * @param idx
     * @return
     */
    protected int mapIndex(int m_index, long m_time[], int idx) {
        if (m_time[m_time.length-1]==0L) return idx;
        return (m_index+idx+1)%m_time.length; // buffer has rotated; also upgrading to int
    }

    public static class PropertyNULL {
        private int sqlTypeCode;
        private String sqlTypeName;
        public PropertyNULL (int sqlTypeCode, String sqlTypeName) {
            this.setSqlTypeCode(sqlTypeCode);
            this.setSqlTypeName(sqlTypeName);
        }

        public int getSqlTypeCode() {
            return sqlTypeCode;
        }

        public void setSqlTypeCode(int sqlTypeCode) {
            this.sqlTypeCode = sqlTypeCode;
        }

        public String getSqlTypeName() {
            return sqlTypeName;
        }

        public void setSqlTypeName(String sqlTypeName) {
            this.sqlTypeName = sqlTypeName;
        }
    }

    public void releaseLobTempSpace() {
        try {
            if (useExplicitTemporaryBlobs) {
                OracleLOBManager.freeList(clobs);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean areNullPropsSerialized() {
        return true;
    }

    public void startPropertyArrayElement(int index, boolean isSet) {
        throw new UnsupportedOperationException("Unsupported Operation startPropertyArrayElement");
    }

    public boolean hasErrors() {
        return error;
    }

    public String getErrorMessage() {
        return msg;
    }
}
