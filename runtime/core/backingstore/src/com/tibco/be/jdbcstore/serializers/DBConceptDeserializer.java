package com.tibco.be.jdbcstore.serializers;

import java.sql.Connection;
import java.util.Map;

import com.tibco.be.jdbcstore.DateTimeValueTuple;
import com.tibco.be.jdbcstore.HistoryTableTuple;
import com.tibco.be.jdbcstore.HistoryTuple;
import com.tibco.be.jdbcstore.ReverseRefTuple;
import com.tibco.be.jdbcstore.impl.DBConceptMap;
import com.tibco.be.jdbcstore.impl.DBEntityMap;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.DateTimeTuple;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.DateTimeTupleImpl;

public class DBConceptDeserializer implements ConceptDeserializer {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DBConceptDeserializer.class);
    static final int CONCEPT_FIELD_OFFSET = 6; // 6 primary system fields

    boolean error=false;
    String msg;
    DBConceptMap entityMap;
    Object [] attributes;
    int cur=0;
    int curObjIndex=0;
    //PropertyMap curDesc;
    DBEntityMap.DBFieldMap curFieldMap;
    //OracleConnection oracle;
    //Datum[] datum;
    boolean inHistory=false;
    //Datum[] historyAttributes;
    int historyTimeCursor=0, historyValueCursor=0;
    //Datum[] timeSeries;
    //Datum[] valSeries;
    boolean inArray=false;
    Object[] arrayAttributes;
    int arrayCursor=0;
    long lastTime=0;
    //STRUCT currHistStruct;
    String nextTz;
    //Datum currentDatum;
    Map secondaryAttributeMap;
    Connection conn;
    boolean isCurrentFieldPrimary = false;

    public DBConceptDeserializer(DBConceptMap entityMap, Connection conn, Object[] attributes, Map secondaryAttributeMap) {
        this.entityMap = entityMap;
        this.conn = conn;
        this.attributes = attributes;
        this.secondaryAttributeMap = secondaryAttributeMap;
    }

    public boolean hasSchemaChanged() {
        return false;
    }

    public ConceptSerializer.ConceptMigrator getConceptMigrator() {
        return null;
    }

    public int getVersion() {
        return 0;
    }

    public boolean isDeleted() {
        return false;
    }

    /**
     *
     * @return
    public Datum[] getOracleAttributes() throws Exception{
        //STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(), oracle, attributes);
        STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(oracle), oracle, attributes);
        return s.getOracleAttributes();
    }
    */

    public Object[] getAttributes() throws Exception {
        return attributes;
    }

    public Map getSecondaryAttributeMap() throws Exception {
        return secondaryAttributeMap;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    Object currentDatum() throws Exception{
        if (inArray) {
            return arrayAttributes[this.arrayCursor];
        } else {
            if (isCurrentFieldPrimary) {
                return attributes[curObjIndex];
            }
            else {
                return secondaryAttributeMap.get(curFieldMap.classFieldName);
            }
        }
    }
    
    /**
     *
     */
    public long startConcept() {
        try {
            cur=CONCEPT_FIELD_OFFSET;
            curObjIndex=CONCEPT_FIELD_OFFSET;
            return ((Long) attributes[0]).longValue();
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void endConcept() {

    }

    /**
     *
     * @return
     */
    public String getExtId() {
        return (String) attributes[1];
    }

    /**
     *
     * @return
     */
    public long getId() {
        try {
            Long id = (Long) attributes[0];
            if (id != null) {
                return id.longValue();
            }
            return 0L;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @return
     */
    public ConceptOrReference startParentConcept() {
        try {
            Long pid = (Long) attributes[5];
            if (pid != null) {
                return new com.tibco.cep.runtime.model.element.impl.Reference(pid.longValue());
            }
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     */
    public void endParentConcept() {

    }

    /**
     *
     * @return
     */
    public int startReverseReferences() {
        try {
            ReverseRefTuple[] tuples = (ReverseRefTuple[]) secondaryAttributeMap.get(DBConceptMap.REVERSEREF_FIELD_NAME);
            if (tuples != null) {
                return tuples.length;
            }
            return 0;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void getReverseReferences(ConceptImpl.RevRefItr itr) {
        try {
            ReverseRefTuple[] tuples = (ReverseRefTuple[]) secondaryAttributeMap.get(DBConceptMap.REVERSEREF_FIELD_NAME);
            if (tuples != null) {
                for (int i=0; i < tuples.length; i++) {
                    itr.next();
                    ReverseRefTuple rt = tuples[i];
                    itr.setReverseRef(new com.tibco.cep.runtime.model.element.impl.Reference(rt.id));
                    itr.setPropName(rt.propertyName);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void endReverseReferences() {

    }

    /**
     *
     * @return
     */
    public int getType() {
        return ConceptSerializer.TYPE_STREAM;
    }

    public boolean startProperty(String propertyName, int propertyIndex) {
        try {
            if (inArray) {
                return arrayAttributes[arrayCursor] != null;
            } else {
                curFieldMap = entityMap.getUserFieldMap(propertyIndex);
                //cur = curFieldMap.tableFieldIndex + CONCEPT_FIELD_OFFSET;
                cur = curFieldMap.tableFieldIndex;
                curObjIndex = curFieldMap.dataObjectFieldIndex;
                isCurrentFieldPrimary = (curFieldMap.secondaryTableName == null);
                //curDesc=(PropertyDescriptor) entityDescription.getPropertyDescriptions().get(propertyIndex);
                if (isCurrentFieldPrimary) {
                    return attributes[curObjIndex] != null;
                }
                else {
                    return secondaryAttributeMap.get(curFieldMap.classFieldName) != null;
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    public int startPropertyArray(String propertyName, int propertyIndex) {
        try {
            inArray = true;
            curFieldMap = entityMap.getUserFieldMap(propertyIndex);
            cur = curFieldMap.tableFieldIndex; // + CONCEPT_FIELD_OFFSET;
            curObjIndex = curFieldMap.dataObjectFieldIndex;
            isCurrentFieldPrimary = (curFieldMap.secondaryTableName == null);

            arrayAttributes = (Object[]) secondaryAttributeMap.get(propertyName);
            if (arrayAttributes != null) {
                return arrayAttributes.length;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param index
     */
    public boolean startPropertyArrayElement(int index) {
        inArray=true;
        arrayCursor=index;
        return true;
    }

    /**
     *
     */
    public void endPropertyArrayElement() {
        inArray=false;
    }

    /**
     *
     * @param propertyName
     * @param index
     * @return
     */
    public int startPropertyAtom(String propertyName, int index) {
        try {
            if (!inArray) {
                curFieldMap= entityMap.getUserFieldMap(index);
                cur=curFieldMap.tableFieldIndex; // + CONCEPT_FIELD_OFFSET;
                curObjIndex=curFieldMap.dataObjectFieldIndex;
                isCurrentFieldPrimary = (curFieldMap.secondaryTableName == null);
            }
            Object cur = currentDatum();
            if (cur != null) {
                return ((HistoryTableTuple)cur).howMany;
                //Datum[] attributes= cur.getOracleAttributes();
                //return ((oracle.sql.NUMBER) attributes[0]).intValue();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     */
    public void endProperty() {
        curFieldMap=null;
        inHistory=false;
        //historyAttributes=null;
        historyTimeCursor=0;
        historyValueCursor=0;
        inArray=false;
        arrayAttributes=null;
        arrayCursor=0;
        lastTime=0;
        nextTz=null;
    }

    public String getStringProperty() {
        try {
            String ret = (String) currentDatum();
            /*
            if (ret != null) {
                if (ret instanceof oracle.sql.CHAR) {
                    oracle.sql.CHAR tmp=(oracle.sql.CHAR) currentDatum();
                    return tmp.stringValue();
                } else if (ret instanceof oracle.sql.CLOB) {
                    CLOB clob= (CLOB) ret;
                    String str ="";
                    StringBuilder clobcontent = new StringBuilder();
                    BufferedReader re = new BufferedReader(clob.getCharacterStream());
                    while ((str = re.readLine()) != null) {
                        clobcontent.append(str);
                    }
                    clob.getCharacterStream().close();
                    return  clobcontent.toString();
                }
            } else {
                return null;
            }
            */
            /* For debugging only
                if (curFieldMap != null) {
                    logger().log(Level.INFO, "  Field name: " + curFieldMap.classFieldName + 
                        ", table field name : " + curFieldMap.tableFieldName +
                        ", field type : " + curFieldMap.tableFieldType + 
                        ", field sql type : " + curFieldMap.tableFieldSqlType +
                        ", inArray - " + inArray +
                        ", isPrimaryField - " + isCurrentFieldPrimary +
                        ", current object index - " + curObjIndex);
                }
            */
            return ret;
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            /* debugging start */
            if (entityMap != null) {
                logger.log(Level.INFO, "Table name: %s encountered exception: %s", 
                        this.entityMap.getTableName(), this.msg);
                if (curFieldMap != null) {
                    logger.log(Level.INFO, "  Field name: %s"
                            + ", table field name: %s"
                            + ", field type: %s"
                            + ", field sql type: %s"
                            + ", inArray - %s"
                            + ", isPrimaryField - %s"
                            + ", current object index - %s",
                            this.curFieldMap.classFieldName,
                            this.curFieldMap.tableFieldName,
                            this.curFieldMap.tableFieldType,
                            this.curFieldMap.tableFieldSqlType,
                            this.inArray,
                            this.isCurrentFieldPrimary,
                            this.curObjIndex);
                }
                try {
                    logger.log(Level.INFO, "  field value : " + currentDatum());
                }
                catch (Exception e) {
                }
            }
            /* debugging end */
            throw new RuntimeException(ex);
        }
    }

    public int getIntProperty() {
        try {
            Integer ret = (Integer) currentDatum();
            if (ret != null) {
                return ret.intValue();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public boolean getBooleanProperty() {
        try {
            Boolean ret = (Boolean) currentDatum();
            if (ret != null) {
                return ret.booleanValue();
            } else {
                return false;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public long getLongProperty() {
        try {
            Long ret = (Long) currentDatum();
            if (ret != null) {
                return ret.longValue();
            } else {
                return 0;
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
    public double getDoubleProperty() {
        try {
            Double ret = (Double) currentDatum();
            if (ret != null) {
                return ret.doubleValue();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @return
     */
    public DateTimeTuple getDateTimeProperty() {
        try {
            DateTimeValueTuple ret = (DateTimeValueTuple) currentDatum();
            if (ret != null) {
                return new DateTimeTupleImpl(ret.ts.getTime(), ret.tz);
            } else {
                return new DateTimeTupleImpl(0L, null);
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
    public ConceptOrReference getReferenceConceptProperty() {
        try {
            Long ret=(Long) currentDatum();
            if (ret != null) {
                return new com.tibco.cep.runtime.model.element.impl.Reference(ret.longValue());
            } else {
                return null;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public ConceptOrReference getContainedConceptProperty() {
        try {
            Long ret=(Long) currentDatum();
            if (ret != null) {
                return new com.tibco.cep.runtime.model.element.impl.Reference(ret.longValue());
            } else {
                return null;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param s
     * @param time
     */
    public void getStringProperty(String[] s, long[]time) {
        try {
            HistoryTableTuple ret=(HistoryTableTuple) currentDatum();
            if (ret != null) {
                int historySize= ret.howMany;
                HistoryTuple[] historyTuples= ret.historyTable;
                if (historyTuples != null) {
                    int till= s.length > historySize? historySize: s.length;

                    for (int i=0; i < till; i++) {
                        HistoryTuple tuple = historyTuples[i];
                        time[i] = tuple.ts.getTime();
                        s[i] = (String) tuple.value;
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getIntProperty(int[]val, long[]time) {
        try {
            HistoryTableTuple ret=(HistoryTableTuple) currentDatum();
            if (ret != null) {
                int historySize= ret.howMany;
                HistoryTuple[] historyTuples= ret.historyTable;
                if (historyTuples != null) {
                    int till= val.length > historySize? historySize: val.length;

                    for (int i=0; i < till; i++) {
                        HistoryTuple tuple = historyTuples[i];
                        time[i] = tuple.ts.getTime();
                        val[i] = ((Integer) tuple.value).intValue();
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getBooleanProperty(boolean[]b, long[] time) {
        try {
            HistoryTableTuple ret=(HistoryTableTuple) currentDatum();
            if (ret != null) {
                int historySize= ret.howMany;
                HistoryTuple[] historyTuples= ret.historyTable;
                if (historyTuples != null) {
                    int till= b.length > historySize? historySize: b.length;

                    for (int i=0; i < till; i++) {
                        HistoryTuple tuple = historyTuples[i];
                        time[i] = tuple.ts.getTime();
                        b[i] = ((Boolean) tuple.value).booleanValue();
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getLongProperty(long[] l, long[] time) {
        try {
            HistoryTableTuple ret=(HistoryTableTuple) currentDatum();
            if (ret != null) {
                int historySize= ret.howMany;
                HistoryTuple[] historyTuples= ret.historyTable;
                if (historyTuples != null) {
                    int till= l.length > historySize? historySize: l.length;

                    for (int i=0; i < till; i++) {
                        HistoryTuple tuple = historyTuples[i];
                        time[i] = tuple.ts.getTime();
                        l[i] = ((Long) tuple.value).longValue();
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getDoubleProperty(double[] d, long[] time) {
        try {
            HistoryTableTuple ret=(HistoryTableTuple) currentDatum();
            if (ret != null) {
                int historySize= ret.howMany;
                HistoryTuple[] historyTuples= ret.historyTable;
                if (historyTuples != null) {
                    int till= d.length > historySize? historySize: d.length;

                    for (int i=0; i < till; i++) {
                        HistoryTuple tuple = historyTuples[i];
                        time[i] = tuple.ts.getTime();
                        d[i] = ((Double) tuple.value).doubleValue();
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getDateTimeProperty(DateTimeTuple[] date, long[] time) {
        try {
            HistoryTableTuple ret=(HistoryTableTuple) currentDatum();
            if (ret != null) {
                int historySize= ret.howMany;
                HistoryTuple[] historyTuples= ret.historyTable;
                if (historyTuples != null) {
                    int till= date.length > historySize? historySize: date.length;

                    for (int i=0; i < till; i++) {
                        HistoryTuple tuple = historyTuples[i];
                        time[i] = tuple.ts.getTime();
                        DateTimeValueTuple dt = (DateTimeValueTuple) tuple.value;
                        DateTimeTupleImpl ti = new DateTimeTupleImpl(dt.ts.getTime(), dt.tz);
                        date[i] = ti;
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getDateTimeProperty(long[] date, String[] tz, long[] time) {
        try {
            HistoryTableTuple ret=(HistoryTableTuple) currentDatum();
            if (ret != null) {
                int historySize= ret.howMany;
                HistoryTuple[] historyTuples= ret.historyTable;
                if (historyTuples != null) {
                    int till= date.length > historySize? historySize: date.length;

                    for (int i=0; i < till; i++) {
                        HistoryTuple tuple = historyTuples[i];
                        time[i] = tuple.ts.getTime();
                        DateTimeValueTuple dt = (DateTimeValueTuple) tuple.value;
                        date[i] = dt.ts.getTime();
                        tz[i] = dt.tz;
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getReferenceConceptProperty(ConceptOrReference[] ref, long[] time) {
        try {
            HistoryTableTuple ret=(HistoryTableTuple) currentDatum();
            if (ret != null) {
                int historySize= ret.howMany;
                HistoryTuple[] historyTuples= ret.historyTable;
                if (historyTuples != null) {
                    int till= ref.length > historySize? historySize: ref.length;

                    for (int i=0; i < till; i++) {
                        HistoryTuple tuple = historyTuples[i];
                        time[i] = tuple.ts.getTime();
                        ref[i] = new com.tibco.cep.runtime.model.element.impl.Reference(((Long) tuple.value).longValue());
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getContainedConceptProperty(ConceptOrReference[] ref, long[] time) {
        try {
            HistoryTableTuple ret=(HistoryTableTuple) currentDatum();
            if (ret != null) {
                int historySize= ret.howMany;
                HistoryTuple[] historyTuples= ret.historyTable;
                if (historyTuples != null) {
                    int till= ref.length > historySize? historySize: ref.length;

                    for (int i=0; i < till; i++) {
                        HistoryTuple tuple = historyTuples[i];
                        time[i] = tuple.ts.getTime();
                        ref[i] = new com.tibco.cep.runtime.model.element.impl.Reference(((Long) tuple.value).longValue());
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public boolean areNullPropsSerialized() {
        return true;
    }
}
