package com.tibco.be.jdbcstore.serializers;

import java.sql.Connection;
import java.sql.Timestamp;

import com.tibco.be.jdbcstore.impl.DBConceptMap;
import com.tibco.be.jdbcstore.impl.DBEntityMap;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.StateMachineSerializer;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;

public class DBStateMachineSerializer implements StateMachineSerializer {

    static final int SM_FIELD_OFFSET = 6; // 6 primary system fields

    public boolean error=false;
    public String msg;
    String errorPropertyName;
    DBConceptMap entityMap;
    Object [] attributes;
    int cur=0;
    //PropertyMap curDesc;
    DBEntityMap.DBFieldMap curFieldMap;
    Connection conn;
    String qualifier=null;
    int[] _systemFieldSqlTypes = {java.sql.Types.NUMERIC, java.sql.Types.VARCHAR, java.sql.Types.CHAR,
        java.sql.Types.TIMESTAMP, java.sql.Types.TIMESTAMP, java.sql.Types.NUMERIC};

    /**
     *
     *
     */
    public DBStateMachineSerializer(DBConceptMap entityMap, Connection conn, String qual) {
        this.entityMap=entityMap;
        this.conn=conn;
        this.qualifier=qual;
    }

    /**
     *
     * @return
    public Datum[] getOracleAttributes() throws Exception{
        // STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(), oracle, attributes);
        //STRUCT s= new STRUCT((StructDescriptor) StructDescriptor.getTypeDescriptor(entityDescription.getOracleTypeName(), oracle),oracle, attributes);
        STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(oracle),oracle, attributes);
        return s.getOracleAttributes();
    }
     */

    public Object[] getAttributes() throws Exception {
        return attributes;
    }

    public int[] getSystemFieldSqlTypes() throws Exception {
        return _systemFieldSqlTypes;
    }

    /*
    private StructDescriptor getStructDescriptor (String name, Connection oracle) throws Exception {
        StructDescriptor sd= (StructDescriptor) StructDescriptor.getTypeDescriptor(name, oracle);
        if (sd == null) {
            sd=StructDescriptor.createDescriptor(name, oracle);
            oracle.putDescriptor(name, sd);
        }
        return sd;
    }
    */

    public void startParent(ConceptOrReference parent) {
        try {
            if (!error) {
                // Save The Parent
                // FIX THIS - Need to check for null parent??
                attributes[5] = new Long(parent.getId());
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void startStateMachine(Class clz, long key, String extKey, int state, int version) {
        try {
            if (!error) {
                // There should not be user-defined complex field 
                attributes = new Object[entityMap.getPrimaryFieldCount() + SM_FIELD_OFFSET];
                attributes[0] = new Long(key);
                if (extKey != null) {
                    attributes[1] = extKey;
                }
                attributes[2]= new Character('C'); // Place Holder for state information
                attributes[3]= new Timestamp(System.currentTimeMillis()); // Place Holder for state information
                attributes[4]= new Timestamp(System.currentTimeMillis()); // Place Holder for state information
                //attributes[6]= new oracle.sql.ARRAY(ArrayDescriptor.createDescriptor(qualifier + "T_REVERSE_REF_TABLE", oracle), oracle, null);
                attributes[6]= null;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void endParentConcept() {

    }

    public void endReverseReferences() {

    }

    public void endStateMachine() {

    }

    /**
     *
     * @return
     */
    public int getType() {
        return ConceptSerializer.TYPE_STREAM;
    }

    public void addState(int stateIndex, int stateValue) {
        try {
            if (!error) {
                curFieldMap = entityMap.getFieldMap(stateIndex);
                cur = curFieldMap.dataObjectFieldIndex;
                attributes[cur]=new Integer(stateValue);
            } else {
                throw new RuntimeException("Serializer in Invalid State " + msg);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }
}
