/*
 * Copyright(c) 2004-2013.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.jdbcstore.impl;

import java.lang.reflect.Constructor;
import java.util.Map;

import com.tibco.cep.runtime.service.cluster.backingstore.EntityDescription;
import com.tibco.cep.runtime.session.RuleServiceProvider;

// Provides mapping between BE generated java class and the corresponding db table
public abstract class DBEntityMap {

    // These keywords are used only for Concept's to index primary & secondary table names (jdbc table mappings)
    public final static String PRIMARY_TABLE_NAME = "pri_tn$"; // This key must NOT clash with user defined names 
    public final static String REVERSE_TABLE_NAME = "rrf_tn$"; // This key must NOT clash with user defined names 
    
    public final static int FTYPE_STRING = 1;
    public final static int FTYPE_INTEGER = 2;
    public final static int FTYPE_LONG = 3;
    public final static int FTYPE_DOUBLE = 4;
    public final static int FTYPE_BOOLEAN = 5;
    public final static int FTYPE_DATETIME = 6;
    public final static int FTYPE_TIMESTAMP = 7;
    public final static int FTYPE_CHAR = 8;
    public final static int FTYPE_BLOB = 9;
    public final static int FTYPE_CLOB = 10;

    Object entityModel;
    //StructDescriptor typeDescriptor;
    Constructor entityConstructor=null;
    Map aliases;
    Class _entityClass;
    String _primarySelectSql;
    String _primaryUpdateSql;
    String _primaryDeleteSql;
    String _primaryInsertSql;
    String _primaryVersionUpdateSql;

    public class DBFieldMap {
        public String classFieldName;
        public String tableFieldName;
        public String tableExtraFieldName1; // FIX THIS - This is a hack for datetime data type - need to better solution
        public int classFieldIndex;
        public int dataObjectFieldIndex; // used during serialization/deserialization for the attribute array - needed mainly for handling datetime type which is an object except when stored in db.
        public int tableFieldIndex;
        public int tableFieldType; // RDFTypes based
        public int tableFieldSqlType; // SqlType based
        public int tableFieldMappingType; // type defined in this class
        public String secondaryTableName;
        public int secondaryTableType; // probably not needed, the type is indicated in tableFieldType
        public boolean isArray = false;
        public boolean hasHistory = false;
        public boolean isReverseRef = false;
        public boolean isContainment = false;
        public DBFieldMap[] secondaryTableField; // This may not be needed
        public int modifiedIndex = -1;
    }

    /**
     *
     * @param entityModel
     * @param typeDescriptor
    public DBEntityMap(Entity entityModel) throws Exception{
        this.entityModel=entityModel;
        //this.typeName= typeDescriptor.getName().toUpperCase();
        this.typeName= "Hello"; //FIX THIS - what should this be?
    }
     */

    /**
     *
     * @param entityModel
     * @param typeDescriptor
     */
    public DBEntityMap(EntityDescription entityDescription, Map aliases) throws Exception{
        this.entityModel = entityDescription;
        //this.typeDescriptor = typeDescriptor;
        this.aliases = aliases;
        //this.typeName = typeDescriptor.getName().toUpperCase(); //FIXME: What should this be?
    }

    /**
     *
     * @return
     */
    public Object getEntityModel() {
        return entityModel;
    }

    /**
     *
     * @return
    public String getTypeName() {
        return typeName; //FIXME: What should this return?
    }
     */

    /**
     *
     * @param entityClass
     * @param id
     * @param extId
     * @return
     * @throws Exception
     */
    public synchronized Object newInstance(Class entityClass, long id, String extId) throws Exception{
        if (entityConstructor == null) {
            entityConstructor = entityClass.getConstructor(new Class[]{long.class, String.class});
        }
        return entityConstructor.newInstance(new Object[] {new Long(id), extId});
    }

    /**
     * 
     * @return
    public StructDescriptor getTypeDescriptor() {
        return typeDescriptor;
    }
     */

    /*
    public StructDescriptor getTypeDescriptor(oracle.jdbc.OracleConnection cnx) throws Exception {
        StructDescriptor dsc= (StructDescriptor) TypeDescriptor.getTypeDescriptor(typeName,cnx);
        if (dsc == null) {
            dsc=StructDescriptor.createDescriptor(typeName, cnx);
            if (dsc != null) {
                cnx.putDescriptor(typeName, dsc);
            }
        }
        return dsc;
    }
    */

    public abstract Class getEntityClass (RuleServiceProvider rsp);

    public abstract String getTableName();

    protected boolean matches(String jdbcAttribute, String beAttribute) {

        String alias=(String) aliases.get("COLUMN." + beAttribute + ".alias");
        if (alias == null) {
            alias = beAttribute;
        }
        if (jdbcAttribute.startsWith("\"")) {
            return jdbcAttribute.equalsIgnoreCase("\"" + alias + "\"");
        } else {
            return jdbcAttribute.equalsIgnoreCase(alias);
        }
    }

    public Class getEntityClass() {
        return _entityClass;
    }

    public void setEntityClass(Class ec) {
        _entityClass = ec;
    }

    public String[] getSecondaryTableNames() {
        return null;
    }

    public String getPrimarySelectSql() {
        return _primarySelectSql;
    }

    public String getPrimaryUpdateSql() {
        return _primaryUpdateSql;
    }

    public String getPrimaryVersionUpdateSql() {
        return _primaryVersionUpdateSql;
    }

    public String getPrimaryInsertSql() {
        return _primaryInsertSql;
    }

    public String getPrimaryDeleteSql() {
        return _primaryDeleteSql;
    }
}
