package com.tibco.be.oracle.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.oracore.OracleType;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.sql.StructDescriptor;

import com.tibco.be.oracle.EventPropertyMap;
import com.tibco.be.oracle.PropertyMap;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 23, 2006
 * Time: 2:24:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class OracleEventMap extends OracleEntityMap{
    protected OracleConnection oracle;
    protected PropertyMap[] properties;
    private Class eventClass;
    private String tableName;
    private HashMap sqlTypes = new HashMap();
    private String INSERT_STATEMENT;
    private String UPDATE_STATEMENT;

    /**
     *
     * @param entityModel
     * @param typeDescriptor
     * @param oracle
     * @throws Exception
     */
    public OracleEventMap(Event entityModel, StructDescriptor typeDescriptor, String tableName, OracleConnection oracle) throws Exception{
        super(entityModel, typeDescriptor);
        this.oracle=oracle;
        this.tableName=tableName;
        configure();
    }

    public OracleEventMap(OracleAdapter.SimpleEventDescription entityModel, StructDescriptor typeDescriptor, String tableName, OracleConnection oracle, Map aliases) throws Exception{
        super(entityModel, typeDescriptor, aliases);
        this.oracle=oracle;
        this.tableName=tableName;
        configure();
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
        StringBuffer query = new StringBuffer("UPDATE " + tableName + " T SET T.CACHEID=?, SET T.ENTITY=" + oracleADT.getSimpleName() + "(");
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

    /**
     *
     * @param propertyName
     * @param oracleADT
     * @return
     * @throws java.sql.SQLException
     */
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

    public String getInsertStatement() {
        return INSERT_STATEMENT;
    }

    public String getUpdateStatement() {
        return UPDATE_STATEMENT;
    }

    /**
     *
     * @param pd
     * @param oracleADT
     * @param propertyIndex
     * @param oracleIndex
     */
    void associate(EventPropertyDefinition pd, OracleTypeADT oracleADT, String columnType, int propertyIndex, int oracleIndex) throws Exception{
        oracle.jdbc.oracore.OracleType type= oracleADT.getAttrTypeAt(oracleIndex);
        StructDescriptor sd= null;
        if (type.isObjectType()) {
            sd= StructDescriptor.createDescriptor(columnType, oracle);
        }
        PropertyMap pm= new EventPropertyMap(oracle, pd, oracleIndex, pd.getPropertyName(), sd, type.getTypeCode());
        sqlTypes.put(new Integer(oracleIndex), type);
        properties[propertyIndex]= pm;
    }

    void associate(OracleAdapter.SimpleEventDescription.EventProperty pd, OracleTypeADT oracleADT, String columnType, int propertyIndex, int oracleIndex) throws Exception{
        oracle.jdbc.oracore.OracleType type= oracleADT.getAttrTypeAt(oracleIndex);
        StructDescriptor sd= null;
        if (type.isObjectType()) {
            sd= StructDescriptor.createDescriptor(columnType, oracle);
        }
        PropertyMap pm= new EventPropertyMap(oracle, pd, oracleIndex, pd.propertyName, sd, type.getTypeCode());
        sqlTypes.put(new Integer(oracleIndex), type);
        properties[propertyIndex]= pm;
    }

    public OracleType getOracleType (int attributeIndex) {
        return (OracleType) sqlTypes.get(new Integer(attributeIndex));
    }

    /**
     *
     * @throws Exception
     */
    void configure() throws Exception{
        if (entityModel instanceof Event) {
            configure ((Event)entityModel);
        } else {
            configure ((OracleAdapter.SimpleEventDescription) entityModel);
        }
    }

    void configure(OracleAdapter.SimpleEventDescription model) throws Exception{
        OracleTypeADT oracleADT= typeDescriptor.getOracleTypeADT();
        List allProperties= (List) model.getProperties();
        properties = new PropertyMap[allProperties.size()];

        for (int i=0; i < allProperties.size(); i++) {
            OracleAdapter.SimpleEventDescription.EventProperty pd = (OracleAdapter.SimpleEventDescription.EventProperty) allProperties.get(i);
            int oracleIndex= getOracleIndex(pd.propertyName, oracleADT);

            if (oracleIndex >= 0) {
                associate(pd, oracleADT, oracleADT.getAttributeType(oracleIndex+1), i, oracleIndex);

            } else {
                System.out.println("Unable to associate property=" + pd.propertyName);
                throw new Exception("Database schema not in sync with BusinessEvents schema for Event " + model.getImplClass()
                                        + ": Unable to associate property=" + pd.propertyName);
            }
        }
        oracle.jdbc.oracore.OracleType type= oracleADT.getAttrTypeAt(6);
        if (!(model instanceof OracleAdapter.TimeEventDescription)) {
            sqlTypes.put(new Integer(6), type); // Hack for payload types
        }
        prepareInsertStatement(oracleADT);
        prepareUpdateStatement(oracleADT);
    }

    void configure(Event model) throws Exception{
        OracleTypeADT oracleADT= typeDescriptor.getOracleTypeADT();
        List allProperties= model.getAllUserProperties();
        properties = new PropertyMap[allProperties.size()];

        for (int i=0; i < allProperties.size(); i++) {
            EventPropertyDefinition pd= (EventPropertyDefinition) allProperties.get(i);
            int oracleIndex= getOracleIndex(pd.getPropertyName(), oracleADT);

            if (oracleIndex >= 0) {
                associate(pd, oracleADT, oracleADT.getAttributeType(oracleIndex+1), i, oracleIndex);
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

    /**
     *
     * @param propertyIndex
     * @return
     */
    public PropertyMap getPropertyMap(int propertyIndex) {
        return properties[propertyIndex];
    }

    public Class getEntityClass(RuleServiceProvider rsp) {
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
    }

    public String getOracleTableName() {
        return tableName;
    }

    public void setOracleTableName(String tableName) {
        this.tableName=tableName;
    }
}
