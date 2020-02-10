package com.tibco.be.oracle;

import java.util.List;

import oracle.sql.StructDescriptor;

import com.tibco.cep.designtime.model.Entity;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 11, 2006
 * Time: 9:51:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class OracleEntityDescription {
    private Entity entity;
    private StructDescriptor oracleType;
    private List propertyDescriptions;

    public OracleEntityDescription(Entity entity, StructDescriptor oracleType, List propertyDescriptions) {
        this.setEntity(entity);
        this.setOracleType(oracleType);
        this.setPropertyDescriptions(propertyDescriptions);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public StructDescriptor getOracleType() {
        return oracleType;
    }

    public void setOracleType(StructDescriptor oracleType) {
        this.oracleType = oracleType;
    }

    public List getPropertyDescriptions() {
        return propertyDescriptions;
    }

    public void setPropertyDescriptions(List propertyDescriptions) {
        this.propertyDescriptions = propertyDescriptions;
    }

    
}
