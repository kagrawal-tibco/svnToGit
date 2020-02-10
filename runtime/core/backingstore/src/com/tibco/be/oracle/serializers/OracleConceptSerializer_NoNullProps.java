package com.tibco.be.oracle.serializers;

import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.CLOB;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import com.tibco.be.oracle.PropertyArrayMap;
import com.tibco.be.oracle.PropertyAtomMap;
import com.tibco.be.oracle.PropertyMap;
import com.tibco.be.oracle.impl.OracleConceptMap;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.DateTimeTuple;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;

public class OracleConceptSerializer_NoNullProps implements ConceptSerializer, OracleSerializer  {
    public boolean error=false;
    public String msg;
    String errorPropertyName;
    OracleConceptMap entityDescription;
    Object[] attributes;
    int cur=0;
    PropertyMap curDesc;
    OracleConnection oracle;
    String qualifier=null;
    boolean inArray=false;
    Datum[] arrayAttributes;
    int arrayCursor=0;
    int currentHistorySize=0;
    int currentHistoryIndex=0;
    private ArrayList<CLOB> clobs = null;

    /**
     *
     *
     */
    public OracleConceptSerializer_NoNullProps(OracleConceptMap entityDescription, OracleConnection oracle, String qual) {
        this.entityDescription=entityDescription;
        this.oracle=oracle;
        this.qualifier=qual;
        this.clobs = new ArrayList<CLOB>();
    }

    public String getErrorMessage() {
        return msg;
    }

    public boolean hasErrors() {
        return error;
    }

    public void release() {
        this.releaseLobTempSpace();
    }

    public final static StructDescriptor getStructDescriptor(String typeName, OracleConnection oracle) throws SQLException{
        StructDescriptor desc= (StructDescriptor) StructDescriptor.getTypeDescriptor(typeName, oracle);
        if (desc == null) {
            desc=StructDescriptor.createDescriptor(typeName, oracle);
            oracle.putDescriptor(typeName, desc);
        }
        return desc;
    }

    public final static ArrayDescriptor getArrayDescriptor(String typeName, OracleConnection oracle) throws SQLException{
        ArrayDescriptor desc= (ArrayDescriptor) ArrayDescriptor.getTypeDescriptor(typeName, oracle);
        if (desc == null) {
            desc=ArrayDescriptor.createDescriptor(typeName, oracle);
            oracle.putDescriptor(typeName, desc);
        }
        return desc;
    }

    /**
     *
     * @return
     */
    public Datum[] getOracleAttributes() throws Exception{
        //STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(), oracle, attributes);
        //STRUCT s= new STRUCT((StructDescriptor) StructDescriptor.getTypeDescriptor(entityDescription.getOracleTypeName(), oracle),oracle, attributes);
        STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(oracle),oracle, attributes);
        return s.getOracleAttributes();
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
                attributes = new Object[entityDescription.getTypeDescriptor().getLength()];
                attributes[0] = new oracle.sql.NUMBER(key);
                if (extKey != null) {
                    attributes[1] = new oracle.sql.CHAR(extKey,null);
                }
                attributes[2]= new oracle.sql.CHAR("C", null); // Place Holder for state information
                attributes[3]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(System.currentTimeMillis())); // Place Holder for state information
                attributes[4]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(System.currentTimeMillis())); // Place Holder for state information
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
                Datum [] atts = new Datum[1];
                atts[0] = new oracle.sql.NUMBER(parent.getId());
                attributes[5] = new oracle.sql.STRUCT(getStructDescriptor(qualifier + "T_ENTITY_REF", oracle), oracle, atts);
            } else {
                Datum [] atts = new Datum[1];
                atts[0] = null;
                attributes[5] = new oracle.sql.STRUCT(getStructDescriptor(qualifier + "T_ENTITY_REF", oracle), oracle, atts);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void endParentConcept() {

    }

    public void startReverseReferences(ConceptImpl.RevRefItr itr, int size) {
        try {
            if (size > 0) {
                Datum[] arrayAttributes = new Datum[size];
                for (int i=0; i < size; i++) {
                    itr.next();
                    Datum [] atts = new Datum[2];
                    atts[0] = new oracle.sql.CHAR(itr.propName(), null);
                    atts[1] = new oracle.sql.NUMBER(itr.reverseRef().getId());
                    arrayAttributes[i] = new oracle.sql.STRUCT(getStructDescriptor(qualifier + "T_REVERSE_REF", oracle), oracle, atts);
                }
                attributes[6] = new oracle.sql.ARRAY(getArrayDescriptor(qualifier + "T_REVERSE_REF_TABLE", oracle), oracle, arrayAttributes);
            } else {
                attributes[6] = new oracle.sql.ARRAY(getArrayDescriptor(qualifier + "T_REVERSE_REF_TABLE", oracle), oracle, null);
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
     */
    public void startProperty(String propertyName, int propertyIndex, boolean isSet) {
        try {
            if (!error) {
                if (!inArray) {
                    curDesc=(PropertyMap) entityDescription.getPropertyMap(propertyIndex);
                    cur= curDesc.getColumnIndex();
                }
                //cur= curDesc.
                //cur = propertyIndex+5;
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
                curDesc=(PropertyMap) entityDescription.getPropertyMap(index);
                cur= curDesc.getColumnIndex();
                inArray=true;
                if (length > 0) {
                    // Find the property Description
                    arrayAttributes = new Datum[length];
                    attributes[cur]= arrayAttributes;
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
    public void startPropertyArrayElement(int index, boolean isSet) {
        this.arrayCursor=index;
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
     */
    public void startPropertyAtom(String propertyName, int index, boolean isSet, int historyIndex, int historySize) {
        try {
            if (!error) {
                if (isSet) {
                    if (!inArray) {
                        curDesc=(PropertyMap) entityDescription.getPropertyMap(index);
                        cur= curDesc.getColumnIndex();
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
        curDesc=null;
        inArray=false;
        arrayAttributes=null;
        arrayCursor=0;
        currentHistorySize=0;
        currentHistoryIndex=0;
    }

    void setCurrentDatum(Datum[] attrs) throws SQLException {
        if (!inArray) {
            StructDescriptor ad= (StructDescriptor) curDesc.getTypeDescriptor();
            if (ad != null) {
                attributes[cur]= new oracle.sql.STRUCT(ad, oracle, attrs);
            }
        } else {
            ArrayDescriptor ad= (ArrayDescriptor) curDesc.getTypeDescriptor();
            if (ad.getBaseType() == OracleTypes.STRUCT) {
                arrayAttributes[arrayCursor]= new oracle.sql.STRUCT((StructDescriptor) getStructDescriptor(ad.getBaseName(), oracle), oracle, attrs);
            }
        }
    }
    /**
     *
     * @param datum
     */
    void setCurrentDatum(Datum datum) throws Exception{
        if (!inArray) {
            if (datum != null) {
                attributes[cur]=datum;
            }
        } else {
            if (datum != null) {
                arrayAttributes[arrayCursor]= datum;
            }
        }
    }
    /**
     * Writes a simple string atom
     * @param s
     */
    public void writeStringProperty(String s) {
        try {
            if (!error) {
                Datum oracleAtom=null;
                if (s != null) {
                    if (curDesc.getSQLTypeCode() == java.sql.Types.CLOB) {
                        CLOB clob= CLOB.createTemporary(oracle,false,CLOB.DURATION_SESSION);
                        clob.open(CLOB.MODE_READWRITE);
                        Writer writer= clob.setCharacterStream(0);
                        writer.write(s);
                        //os.write(bytes);
                        writer.flush();
                        writer.close();
                        clob.close();
                        oracleAtom= clob;
                        clobs.add(clob);
                    } else {
                        oracleAtom= new oracle.sql.CHAR(s,null);
                    }
                }
                setCurrentDatum(oracleAtom);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
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
                Datum oracleAtom=null;
                oracleAtom= new oracle.sql.NUMBER(i);
                setCurrentDatum(oracleAtom);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
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
                Datum oracleAtom=null;
                oracleAtom= new oracle.sql.NUMBER(b?1:0);
                setCurrentDatum(oracleAtom);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
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
                Datum oracleAtom=null;
                oracleAtom= new oracle.sql.NUMBER(l);
                setCurrentDatum(oracleAtom);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
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
                Datum oracleAtom=null;
                if (Double.isNaN(d)) {
                    oracleAtom= new oracle.sql.NUMBER(0.00);
                } else {
                    oracleAtom= new oracle.sql.NUMBER(d);
                }
                setCurrentDatum(oracleAtom);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
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
                if (date != null) {
                    Datum [] attributes=new Datum[2];
                    if (date.getTimeZone() != null) {
                        attributes[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(date.getTime()));
                        attributes[1]= new oracle.sql.CHAR(date.getTimeZone(),null);
                        setCurrentDatum(attributes);
                    } else {
                        attributes[0]= null;
                        attributes[1]= null;
                        setCurrentDatum(attributes);
                    }
                } else if (inArray){
                    Datum [] attributes=new Datum[2];
                    attributes[0]= null;
                    attributes[1]= null;
                    setCurrentDatum(attributes);
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
     * @param ref
     */
    public void writeReferenceConceptProperty(ConceptOrReference ref) {
        try {
            if (!error) {
                if (ref != null) {
                    Datum [] attributes=new Datum[1];
                    attributes[0]= new oracle.sql.NUMBER(ref.getId());
                    setCurrentDatum(attributes);
                } else if (inArray) {
                    Datum [] attributes=new Datum[1];
                    attributes[0]= null;
                    setCurrentDatum(attributes);
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
     * @param ref
     */
    public void writeContainedConceptProperty(ConceptOrReference ref) {
        try {
            if (!error) {
                if (ref != null) {
                    Datum [] attributes=new Datum[1];
                    attributes[0]= new oracle.sql.NUMBER(ref.getId());
                    setCurrentDatum(attributes);
                } else {
                    Datum [] attributes=new Datum[1];
                    attributes[0]= null;
                    setCurrentDatum(attributes);
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
     * @param attributes
     * @return
     * @throws Exception
     */
    STRUCT historyTable(Datum[] attributes) throws Exception{
        Datum[] atts= new Datum[2];
        if (inArray) {
            PropertyArrayMap pam= (PropertyArrayMap) curDesc;
            PropertyAtomMap pm= (PropertyAtomMap) pam.getBaseMap();
            ArrayDescriptor ad= (ArrayDescriptor) pam.getTypeDescriptor();
            StructDescriptor sd= (StructDescriptor) StructDescriptor.getTypeDescriptor(ad.getBaseName(), oracle);
            atts[0]= new oracle.sql.NUMBER(attributes.length);
            atts[1]=new ARRAY(pm.getHistoryTableDescriptor(),oracle,attributes);
            return new oracle.sql.STRUCT(sd, oracle,atts);
        } else {
            PropertyAtomMap pm= (PropertyAtomMap) curDesc;
            atts[0]= new oracle.sql.NUMBER(attributes.length);
            atts[1]=new ARRAY(pm.getHistoryTableDescriptor(),oracle,attributes);
            return new oracle.sql.STRUCT((StructDescriptor) curDesc.getTypeDescriptor(), oracle,atts);
        }
    }

    PropertyMap currentMap() {
        if (inArray) {
            return ((PropertyArrayMap) curDesc).getBaseMap();
        } else {
            return curDesc;
        }
    }
    /**
     *
     * @param s
     * @param time
     */
    public void writeStringProperty(String s[], long time[]) {
        try {
            PropertyAtomMap pm=(PropertyAtomMap) currentMap();
            if (!error) {
                if (s != null) {
                    Datum []attributes = new Datum[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Datum [] atts= new Datum[2];
                        atts[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]));
                        String cur=s[mapIndex(currentHistoryIndex, time,i)];
                        if (cur != null) {
                            atts[1]= new oracle.sql.CHAR(s[mapIndex(currentHistoryIndex, time,i)], null);
                        } else {
                            atts[1]= null;
                        }
                        attributes[i]= new oracle.sql.STRUCT(pm.getHistoryTupleDescriptor(), oracle, atts);
                    }
                    STRUCT ht= historyTable(attributes);
                    setCurrentDatum(ht);
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
            PropertyAtomMap pm=(PropertyAtomMap) currentMap();
            if (!error) {
                if (val != null) {
                    Datum []attributes = new Datum[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Datum [] atts= new Datum[2];
                        atts[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]));
                        atts[1]= new oracle.sql.NUMBER(val[mapIndex(currentHistoryIndex, time,i)]);
                        attributes[i]= new oracle.sql.STRUCT(pm.getHistoryTupleDescriptor(), oracle, atts);
                    }
                    STRUCT ht= historyTable(attributes);
                    setCurrentDatum(ht);
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
     * @param b
     * @param time
     */
    public void writeBooleanProperty(boolean b[], long time[]) {
        try {
            PropertyAtomMap pm=(PropertyAtomMap) currentMap();
            if (!error) {
                if (b != null) {
                    Datum []attributes = new Datum[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Datum [] atts= new Datum[2];
                        atts[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]));
                        atts[1]= new oracle.sql.NUMBER(b[mapIndex(currentHistoryIndex, time,i)] ? 1:0);
                        attributes[i]= new oracle.sql.STRUCT(pm.getHistoryTupleDescriptor(), oracle, atts);
                    }
                    STRUCT ht= historyTable(attributes);
                    setCurrentDatum(ht);
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
     * @param l
     * @param time
     */
    public void writeLongProperty(long l[], long time[]) {
        try {
            PropertyAtomMap pm=(PropertyAtomMap) currentMap();
            if (!error) {
                if (l != null) {
                    Datum []attributes = new Datum[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Datum [] atts= new Datum[2];
                        atts[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]));
                        atts[1]= new oracle.sql.NUMBER(l[mapIndex(currentHistoryIndex, time,i)]);
                        attributes[i]= new oracle.sql.STRUCT(pm.getHistoryTupleDescriptor(), oracle, atts);
                    }
                    STRUCT ht= historyTable(attributes);
                    setCurrentDatum(ht);
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
     * @param d
     * @param time
     */
    public void writeDoubleProperty(double d[], long time[]) {
        try {
            PropertyAtomMap pm=(PropertyAtomMap) currentMap();
            if (!error) {
                if (d != null) {
                    Datum []attributes = new Datum[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Datum [] atts= new Datum[2];
                        atts[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]));
                        double t=d[mapIndex(currentHistoryIndex, time,i)];
                        if (Double.isNaN(t)) {
                            t=0.00;
                        }
                        atts[1]= new oracle.sql.NUMBER(t);
                        attributes[i]= new oracle.sql.STRUCT(pm.getHistoryTupleDescriptor(), oracle, atts);
                    }
                    STRUCT ht= historyTable(attributes);
                    setCurrentDatum(ht);
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
     * @param date
     * @param tz
     * @param time
     */
    public void writeDateTimeProperty(long date[], String tz[], long time[]) {
        try {
            PropertyAtomMap pm=(PropertyAtomMap) currentMap();
            if (!error) {
                if (date != null) {
                    Datum []attributes = new Datum[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Datum [] atts= new Datum[2];
                        atts[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]));
                        Datum [] dt = new Datum[2];
                        String tz1=tz[mapIndex(currentHistoryIndex, time,i)];
                        if (tz1 != null) {
                            dt[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(date[mapIndex(currentHistoryIndex, time,i)]));
                            dt[1]= new oracle.sql.CHAR(tz[mapIndex(currentHistoryIndex, time,i)], null);
                            atts[1]= new oracle.sql.STRUCT(pm.getPrimitiveDescriptor(), oracle,dt);
                            attributes[i]= new oracle.sql.STRUCT(pm.getHistoryTupleDescriptor(), oracle, atts);
                        } else {
                            atts[1]= new oracle.sql.STRUCT(pm.getPrimitiveDescriptor(), oracle,dt);
                            attributes[i]= new oracle.sql.STRUCT(pm.getHistoryTupleDescriptor(), oracle, atts);
                        }
                    }
                    STRUCT ht= historyTable(attributes);
                    setCurrentDatum(ht);
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
     * @param date
     * @param time
     */
    public void writeDateTimeProperty(DateTimeTuple date[], long time[]) {
        try {
            PropertyAtomMap pm=(PropertyAtomMap) currentMap();
            if (!error) {
                if (date != null) {
                    Datum []attributes = new Datum[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Datum [] atts= new Datum[2];
                        atts[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]));
                        Datum [] dt = new Datum[2];
                        if (dt[1] != null) {
                            dt[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(date[mapIndex(currentHistoryIndex, time,i)].getTime()));
                            dt[1]= new oracle.sql.CHAR(date[mapIndex(currentHistoryIndex, time,i)].getTimeZone(), null);
                        }
                        atts[1]= new oracle.sql.STRUCT(pm.getPrimitiveDescriptor(), oracle,dt);
                        attributes[i]= new oracle.sql.STRUCT(pm.getHistoryTupleDescriptor(), oracle, atts);
                    }
                    STRUCT ht= historyTable(attributes);
                    setCurrentDatum(ht);
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
     * @param ref
     * @param time
     */
    public void writeReferenceConceptProperty(ConceptOrReference ref[], long time[]) {
        try {
            PropertyAtomMap pm=(PropertyAtomMap) currentMap();
            if (!error) {
                if (ref != null) {
                    Datum []attributes = new Datum[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Datum [] atts= new Datum[2];
                        atts[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]));
                        Datum [] dt = new Datum[1];
                        ConceptOrReference r= ref[mapIndex(currentHistoryIndex, time,i)];
                        if (r != null) {
                            dt[0]= new oracle.sql.NUMBER(ref[mapIndex(currentHistoryIndex, time,i)].getId());
                        } else {
                            dt[0]= null;
                        }
                        atts[1]= new oracle.sql.STRUCT(pm.getPrimitiveDescriptor(), oracle,dt);
                        attributes[i]= new oracle.sql.STRUCT(pm.getHistoryTupleDescriptor(), oracle, atts);
                    }
                    STRUCT ht= historyTable(attributes);
                    setCurrentDatum(ht);
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
     * @param ref
     * @param time
     */
    public void writeContainedConceptProperty(ConceptOrReference ref[], long time[]) {
        try {
            PropertyAtomMap pm=(PropertyAtomMap) currentMap();
            if (!error) {
                if (ref != null) {
                    Datum []attributes = new Datum[currentHistorySize];
                    for (int i=0; i < currentHistorySize;i++ ) {
                        Datum [] atts= new Datum[2];
                        atts[0]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(time[mapIndex(currentHistoryIndex, time,i)]));
                        Datum [] dt = new Datum[1];
                        ConceptOrReference r= ref[mapIndex(currentHistoryIndex, time,i)];
                        if (r != null) {
                            dt[0]= new oracle.sql.NUMBER(ref[mapIndex(currentHistoryIndex, time,i)].getId());
                        } else {
                            dt[0]= null;
                        }
                        atts[1]= new oracle.sql.STRUCT(pm.getPrimitiveDescriptor(), oracle,dt);
                        attributes[i]= new oracle.sql.STRUCT(pm.getHistoryTupleDescriptor(), oracle, atts);
                    }
                    STRUCT ht= historyTable(attributes);
                    setCurrentDatum(ht);
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
        for (CLOB clob : clobs) {
            try {
                clob.freeTemporary();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean areNullPropsSerialized() {
        return false;
    }

    public void startPropertyArrayElement(int index) {
        throw new UnsupportedOperationException("Unsupported Operation startPropertyArrayElement");
    }
}



