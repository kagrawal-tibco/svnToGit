package com.tibco.be.oracle.serializers;

import oracle.jdbc.OracleConnection;
import oracle.sql.ArrayDescriptor;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import com.tibco.be.oracle.PropertyMap;
import com.tibco.be.oracle.impl.OracleConceptMap;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.StateMachineSerializer;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 14, 2009
 * Time: 12:15:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleStateMachineSerializer implements StateMachineSerializer {
    public boolean error=false;
    public String msg;
    String errorPropertyName;
    OracleConceptMap entityDescription;
    Object[] attributes;
    int cur=0;
    PropertyMap curDesc;
    OracleConnection oracle;
    String qualifier=null;

    /**
     *
     *
     */
    public OracleStateMachineSerializer(OracleConceptMap entityDescription, OracleConnection oracle, String qual) {
        this.entityDescription=entityDescription;
        this.oracle=oracle;
        this.qualifier=qual;
    }

    /**
     *
     * @return
     */
    public Datum[] getOracleAttributes() throws Exception {
        //STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(), oracle, attributes);
        //STRUCT s= new STRUCT((StructDescriptor) StructDescriptor.getTypeDescriptor(entityDescription.getOracleTypeName(), oracle),oracle, attributes);
        STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(oracle),oracle, attributes);
        return s.getOracleAttributes();
    }

    private StructDescriptor getStructDescriptor (String name, OracleConnection oracle) throws Exception {
        StructDescriptor sd= (StructDescriptor) StructDescriptor.getTypeDescriptor(name, oracle);
        if (sd == null) {
            sd=StructDescriptor.createDescriptor(name, oracle);
            oracle.putDescriptor(name, sd);
        }
        return sd;
    }

    public void startParent(ConceptOrReference parent) {
        try {
            if (!error) {
                // Save The Parent
                Datum [] atts = new Datum[1];
                atts[0] = new oracle.sql.NUMBER(parent.getId());
                attributes[5] = new oracle.sql.STRUCT(getStructDescriptor(qualifier + "T_ENTITY_REF", oracle), oracle, atts);
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
                attributes = new Object[entityDescription.getTypeDescriptor().getLength()];
                attributes[0] = new oracle.sql.NUMBER(key);
                if (extKey != null) {
                    attributes[1] = new oracle.sql.CHAR(extKey,null);
                }
                attributes[2]= new oracle.sql.CHAR("C", null); // Place Holder for state information
                attributes[3]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(System.currentTimeMillis())); // Place Holder for state information
                attributes[4]= new oracle.sql.TIMESTAMP(new java.sql.Timestamp(System.currentTimeMillis())); // Place Holder for state information
                attributes[6]= new oracle.sql.ARRAY(ArrayDescriptor.createDescriptor(qualifier + "T_REVERSE_REF_TABLE", oracle), oracle, null);
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
                curDesc=(PropertyMap) entityDescription.getPropertyMap(stateIndex);
                cur= curDesc.getColumnIndex();
                Datum oracleAtom=null;
                oracleAtom= new oracle.sql.NUMBER(stateValue);
                attributes[cur]=oracleAtom;
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
