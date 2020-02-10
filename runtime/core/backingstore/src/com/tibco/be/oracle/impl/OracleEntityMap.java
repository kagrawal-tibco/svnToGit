/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.oracle.impl;

import java.lang.reflect.Constructor;
import java.util.Map;

import oracle.sql.StructDescriptor;
import oracle.sql.TypeDescriptor;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.runtime.service.cluster.backingstore.EntityDescription;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 22, 2006
 * Time: 7:35:38 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class OracleEntityMap {
    Object entityModel;
    StructDescriptor typeDescriptor;
    Constructor entityConstructor=null;
    Map aliases;
    String typeName;

    /**
     *
     * @param entityModel
     * @param typeDescriptor
     */
    public OracleEntityMap(Entity entityModel, StructDescriptor typeDescriptor) throws Exception{
        this.entityModel=entityModel;
        this.typeDescriptor= typeDescriptor;
        this.typeName= typeDescriptor.getName().toUpperCase();
    }

    /**
     *
     * @param entityModel
     * @param typeDescriptor
     */
    public OracleEntityMap(EntityDescription entityDescription, StructDescriptor typeDescriptor, Map aliases) throws Exception{
        this.entityModel=entityDescription;
        this.typeDescriptor= typeDescriptor;
        this.aliases=aliases;
        this.typeName= typeDescriptor.getName().toUpperCase();
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
     */
    public String getOracleTypeName() {
        return typeName;
    }

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
            entityConstructor=entityClass.getConstructor(new Class[]{long.class, String.class});
        }
        return entityConstructor.newInstance(new Object[] {new Long(id), extId});
    }

    /**
     * 
     * @return
     */
    public StructDescriptor getTypeDescriptor() {
        return typeDescriptor;
    }

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

    public abstract Class getEntityClass (RuleServiceProvider rsp);

    public abstract String getOracleTableName();

    protected boolean matches(String oracleAttribute, String beAttribute) {

        String alias=(String) aliases.get("COLUMN." + beAttribute + ".alias");
        if (alias == null) {
            alias = beAttribute;
        }
        if (oracleAttribute.startsWith("\"")) {
            return oracleAttribute.equalsIgnoreCase("\"" + alias + "\"");
        } else {
            return oracleAttribute.equalsIgnoreCase(alias);
        }
    }
}
