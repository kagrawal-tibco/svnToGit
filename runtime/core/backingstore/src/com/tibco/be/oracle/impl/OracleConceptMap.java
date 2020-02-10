package com.tibco.be.oracle.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.sql.ArrayDescriptor;
import oracle.sql.StructDescriptor;

import com.tibco.be.oracle.PropertyArrayMap;
import com.tibco.be.oracle.PropertyAtomMap;
import com.tibco.be.oracle.PropertyAtomSimpleMap;
import com.tibco.be.oracle.PropertyMap;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 22, 2006
 * Time: 7:38:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleConceptMap extends OracleEntityMap {
    
    OracleConnection oracle;
    PropertyMap[] properties;
    Class conceptClass;
    String tableName;
    private String INSERT_STATEMENT;
    private String UPDATE_STATEMENT;

    /**
     *
     * @param entityModel
     * @param typeDescriptor
     * @param oracle
     * @throws Exception
     */
    public OracleConceptMap(Concept entityModel,
                            StructDescriptor typeDescriptor, String tableName, OracleConnection oracle) throws Exception{
        super(entityModel, typeDescriptor);
        this.oracle=oracle;
        this.tableName=tableName;
        configure();
    }

    /**
     *
     * @param entityModel
     * @param typeDescriptor
     * @param oracle
     * @throws Exception
     */
    public OracleConceptMap(OracleAdapter.ConceptDescription entityModel, 
                            StructDescriptor typeDescriptor, String tableName, OracleConnection oracle, Map aliases) throws Exception{
        super(entityModel, typeDescriptor, aliases);
        this.oracle=oracle;
        this.tableName=tableName;
        configure();
    }
    
    public PropertyMap getPropertyMapAt(int oracleIndex) {
        for (int i=0; i < properties.length;i++) {
            PropertyMap pm = properties[i];
            if (pm.getColumnIndex() == oracleIndex) {
                return pm;
            }
        }
        return null;
    }

    public String getInsertStatement() {
        return INSERT_STATEMENT;
    }

    private void prepareInsertStatement(OracleTypeADT oracleADT) throws Exception{
        StringBuffer query = new StringBuffer("INSERT INTO " + tableName + " VALUES (?," + oracleADT.getSimpleName() + "(");
        int numAttributes= oracleADT.getNumAttrs();
        for (int i=1; i <= numAttributes; i++) {
            if (i > 1) {
                query.append(",");
            }
            query.append("?");
        }
        query.append("))");
        INSERT_STATEMENT=query.toString();
    }

    private void prepareUpdateStatement(OracleTypeADT oracleADT) throws Exception{
        StringBuffer query = new StringBuffer("UPDATE " + tableName + " T SET T.CACHEID=?, T.ENTITY=" + oracleADT.getSimpleName() + "(");
        int numAttributes= oracleADT.getNumAttrs();
        for (int i=1; i <= numAttributes; i++) {
            if (i > 1) {
                query.append(",");
            }
            query.append("?");
        }
        query.append(") WHERE T.ENTITY.ID$ = ?");
        UPDATE_STATEMENT=query.toString();
    }

    public String getUpdateStatement() {
        return UPDATE_STATEMENT;
    }

    /**
     *
     * @param propertyName
     * @param oracleADT
     * @return
     * @throws SQLException
     */
    private int getOracleIndex(String propertyName, OracleTypeADT oracleADT) throws SQLException {
        int numAttributes= oracleADT.getNumAttrs();
        for (int i=1; i <= numAttributes; i++) {
            String attributeName= oracleADT.getAttributeName(i);
            if (matches(attributeName, propertyName)) {
                return i-1;
            }
        }
        return -1;
    }

    /*
    private int prepareMergeStatement(OracleTypeADT oracleADT) throws SQLException {
        StringBuffer query= new StringBuffer();
        query.append("MERGE INTO BLAH T USING (SELECT ? VERSION , " + oracleADT.getSimpleName().toUpperCase() + " OBJ (");
        int numAttributes= oracleADT.getNumAttrs();
        for (int i=1; i <= numAttributes; i++) {
            //String attributeName= oracleADT.getAttributeName(i);
            if (i == 1) {
                query.append("? " );
            } else {
                query.append(",? " );
            }
        }
        query.append( ") FROM DUAL) I  ON (T.ENTITY.ID$ = I.OBJ.ID$) WHEN MATCHED  ");
        query.append(" UPDATE T.ENTITY=I.OBJ WHEN NOT MATCHED THEN INSERT (CACHEID, ENTITY) VALUES(I.VERSION, I.OBJ)");
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
     * @param oracleIndex
     * @return
     * @throws Exception
     */
    private PropertyMap propertyAtomSimpleMap(Object pd,
                                      oracle.jdbc.oracore.OracleType type,
                                      String columnName, String columnType, int propertyIndex, int oracleIndex) throws Exception {
        // STRUCT with no history
        StructDescriptor typeDescriptor=null;
        if (type.isObjectType()) {
            typeDescriptor= StructDescriptor.createDescriptor(columnType, oracle);
        }
        PropertyMap pm= new PropertyAtomSimpleMap(oracle, pd, columnName,
                oracleIndex, typeDescriptor, type.getTypeCode());
        return pm;
    }

    /**
     *
     * @param pd
     * @param type
     * @param columnName
     * @param columnType
     * @param propertyIndex
     * @param oracleIndex
     * @return
     * @throws Exception
     */
    private PropertyMap propertyAtomSimpleMap(Object pd,
                                      int type,
                                      String columnName, String columnType, int propertyIndex, int oracleIndex) throws Exception {
        // STRUCT with no history
        StructDescriptor td=null;
        if (type == OracleTypes.STRUCT) {
            td= StructDescriptor.createDescriptor(columnType, oracle);
        }
        PropertyMap pm= new PropertyAtomSimpleMap(oracle, pd, columnName, oracleIndex, td, type);
        return pm;
    }

    /**
     *
     * @param pd
     * @param columnName
     * @param columnType
     * @param propertyIndex
     * @param oracleIndex
     * @return
     * @throws Exception
     */
    private PropertyMap propertyAtomMap(Object pd, String columnName, String columnType, int propertyIndex, int oracleIndex) throws Exception{
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
        PropertyMap pm= new PropertyAtomMap(oracle,pd,columnName, oracleIndex,
                typeDescriptor,historyTableDescriptor,historyTupleDescriptor, primitiveDescriptor);
        return pm;
    }

    /**
     *
     * @param pd
     * @param oracleADT
     * @param propertyIndex
     * @param oracleIndex
     */
    private void associate(PropertyDefinition pd, OracleTypeADT oracleADT, int propertyIndex, int oracleIndex) throws Exception {
        if (pd.isArray() && (pd.getHistorySize() > 0)) {
            // Collection of histories
            //oracle.jdbc.oracore.OracleType type= oracleADT.getAttrTypeAt(oracleIndex);
            ArrayDescriptor ad = ArrayDescriptor.createDescriptor(oracleADT.getAttributeType(oracleIndex+1), oracle);
            if (ad.getBaseType() == OracleTypes.STRUCT) {
                //StructDescriptor baseDescriptor= StructDescriptor.createDescriptor(ad.getBaseName(), oracle);
                //OracleTypeADT adtType= baseDescriptor.getOracleTypeADT();
                PropertyMap pm= propertyAtomMap(pd, null, ad.getBaseName(), propertyIndex, oracleIndex);
                PropertyArrayMap pam= new PropertyArrayMap(oracle, pd, oracleADT.getAttributeName(oracleIndex+1), oracleIndex, ad, pm);
                properties[propertyIndex]= pam;
            } else {
                throw new Exception("Mismatched Type:" + ad.getBaseType());
            }
        } else if (pd.isArray() && (pd.getHistorySize() == 0)) {
            //Collection with no history
            //oracle.jdbc.oracore.OracleType type= oracleADT.getAttrTypeAt(oracleIndex);
            ArrayDescriptor ad = ArrayDescriptor.createDescriptor(oracleADT.getAttributeType(oracleIndex+1), oracle);
            PropertyMap pm= propertyAtomSimpleMap(pd, ad.getBaseType(), null, ad.getBaseName(), propertyIndex, oracleIndex);
            PropertyArrayMap pam= new PropertyArrayMap(oracle, pd, oracleADT.getAttributeName(oracleIndex+1), oracleIndex, ad, pm);
            properties[propertyIndex]= pam;
        } else if (!pd.isArray() && (pd.getHistorySize() == 0)) {
            PropertyMap pm= propertyAtomSimpleMap(pd, oracleADT.getAttrTypeAt(oracleIndex),
                oracleADT.getAttributeName(oracleIndex+1),
                oracleADT.getAttributeType(oracleIndex+1), propertyIndex, oracleIndex);
            properties[propertyIndex]= pm;
        } else {
            PropertyMap pm= propertyAtomMap(pd, oracleADT.getAttributeName(oracleIndex+1),
                    oracleADT.getAttributeType(oracleIndex+1), propertyIndex, oracleIndex);
            properties[propertyIndex]= pm;
        }
    }

    void associate(OracleAdapter.ConceptDescription.ConceptProperty pd, OracleTypeADT oracleADT, int propertyIndex, int oracleIndex) throws Exception{
        if (pd.isArray && (pd.historySize > 0)) {
            //oracle.jdbc.oracore.OracleType type= oracleADT.getAttrTypeAt(oracleIndex);
            ArrayDescriptor ad = ArrayDescriptor.createDescriptor(oracleADT.getAttributeType(oracleIndex+1), oracle);
            if (ad.getBaseType() == OracleTypes.STRUCT) {
                //StructDescriptor baseDescriptor= StructDescriptor.createDescriptor(ad.getBaseName(), oracle);
                //OracleTypeADT adtType= baseDescriptor.getOracleTypeADT();
                PropertyMap pm= propertyAtomMap(pd, null, ad.getBaseName(), propertyIndex, oracleIndex);
                PropertyArrayMap pam= new PropertyArrayMap(oracle, pd, oracleADT.getAttributeName(oracleIndex+1), oracleIndex, ad, pm);
                properties[propertyIndex]= pam;
            } else {
                throw new Exception("Mismatched Type:" + ad.getBaseType());
            }
            // Collection of histories
        } else if (pd.isArray && (pd.historySize == 0)) {
            //Collection with no history
            //oracle.jdbc.oracore.OracleType type= oracleADT.getAttrTypeAt(oracleIndex);
            ArrayDescriptor ad = ArrayDescriptor.createDescriptor(oracleADT.getAttributeType(oracleIndex+1), oracle);
            PropertyMap pm= propertyAtomSimpleMap(pd, ad.getBaseType(), null, ad.getBaseName(), propertyIndex, oracleIndex);
            PropertyArrayMap pam= new PropertyArrayMap(oracle, pd, oracleADT.getAttributeName(oracleIndex+1), oracleIndex, ad, pm);
            properties[propertyIndex]= pam;
        } else if (!pd.isArray && (pd.historySize == 0)) {
            PropertyMap pm= propertyAtomSimpleMap(pd, oracleADT.getAttrTypeAt(oracleIndex),
                oracleADT.getAttributeName(oracleIndex+1),
                oracleADT.getAttributeType(oracleIndex+1), propertyIndex, oracleIndex);
            properties[propertyIndex]= pm;
        } else {
            PropertyMap pm= propertyAtomMap(pd, oracleADT.getAttributeName(oracleIndex+1),
                    oracleADT.getAttributeType(oracleIndex+1), propertyIndex, oracleIndex);
            properties[propertyIndex]= pm;
        }
    }

    private void configure(Concept conceptModel) throws Exception{
        OracleTypeADT oracleADT= typeDescriptor.getOracleTypeADT();
        List allProperties= conceptModel.getAllPropertyDefinitions();
        properties = new PropertyMap[allProperties.size()];

        for (int i=0; i < allProperties.size(); i++) {
            PropertyDefinition pd= (PropertyDefinition) allProperties.get(i);
            int oracleIndex= getOracleIndex(pd.getName(), oracleADT);
            if (oracleIndex >= 0) {
                associate(pd, oracleADT, i, oracleIndex);
            } else {
                throw new Exception("Database schema not in sync with BusinessEvents schema for Concept " + conceptModel.getFullPath()
                                        + ": Unable to associate property=" + pd.getName());
            }
        }
    }

    private void configure(OracleAdapter.ConceptDescription conceptModel) throws Exception{
        OracleTypeADT oracleADT= typeDescriptor.getOracleTypeADT();
        ArrayList allProperties= conceptModel.getProperties();
        properties = new PropertyMap[allProperties.size()];

        for (int i=0; i < allProperties.size(); i++) {
            OracleAdapter.ConceptDescription.ConceptProperty pd = (OracleAdapter.ConceptDescription.ConceptProperty) allProperties.get(i);
            int oracleIndex= getOracleIndex(pd.propertyName, oracleADT);
            if (oracleIndex >= 0) {
                associate(pd, oracleADT, i, oracleIndex);
            } else {
                throw new Exception("Database schema not in sync with BusinessEvents schema for Concept " + conceptModel.getImplClass()
                                        + ": Unable to associate property=" + pd.propertyName);
            }
        }
        prepareInsertStatement(oracleADT);
        prepareUpdateStatement(oracleADT);
    }

    /**
     *
     * @throws Exception
     */
    private void configure() throws Exception{
        if (entityModel instanceof Concept) {
            configure ((Concept)entityModel);
        } else {
            configure ((OracleAdapter.ConceptDescription) entityModel);
        }
    }

    /**
     *
     * @param propertyIndex
     * @return
     */
    public PropertyMap getPropertyMap(int propertyIndex) {
        return properties[propertyIndex];
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
                String implClass=((OracleAdapter.ConceptDescription)entityModel).getImplClass();
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

    public String getOracleTableName() {
        return tableName;
    }

    public void setOracleTableName(String tableName) {
        this.tableName=tableName;
    }
}
