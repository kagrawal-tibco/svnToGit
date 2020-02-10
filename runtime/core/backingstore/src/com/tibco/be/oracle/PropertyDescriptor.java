package com.tibco.be.oracle;

import oracle.sql.ArrayDescriptor;
import oracle.sql.StructDescriptor;
import oracle.sql.TypeDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 4, 2006
 * Time: 2:53:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyDescriptor {
    private Object pd;

    private TypeDescriptor oracleType;
    private ArrayDescriptor arrayDescriptor;
    private StructDescriptor historyDescriptor;
    private OracleBEMetadata.PropertyConverter propertyConverter;
    private StructDescriptor primitiveDescriptor;

    public PropertyDescriptor (Object pd, OracleBEMetadata.PropertyConverter converter) {
        this.setPd(pd);
        this.setPropertyConverter(converter);
    }

    public PropertyDescriptor (Object  pd, TypeDescriptor oracleType, OracleBEMetadata.PropertyConverter converter) {
        this.setPd(pd);
        this.setOracleType(oracleType);
        this.setPropertyConverter(converter);
    }

    public PropertyDescriptor (Object  pd, TypeDescriptor baseDescriptor, ArrayDescriptor arrayDescriptor, StructDescriptor historyDescriptor, OracleBEMetadata.PropertyConverter converter) {
        this.setPd(pd);
        this.setOracleType(baseDescriptor);
        this.setPropertyConverter(converter);
        this.arrayDescriptor= arrayDescriptor;
        this.historyDescriptor=historyDescriptor;
    }

    public PropertyDescriptor (Object  pd, StructDescriptor primitiveDescriptor, TypeDescriptor baseDescriptor, ArrayDescriptor arrayDescriptor, StructDescriptor historyDescriptor, OracleBEMetadata.PropertyConverter converter) {
        this.setPd(pd);
        this.setOracleType(baseDescriptor);
        this.setPropertyConverter(converter);
        this.arrayDescriptor= arrayDescriptor;
        this.historyDescriptor=historyDescriptor;
        this.setPrimitiveDescriptor(primitiveDescriptor);
    }

    public PropertyDescriptor (Object  pd, TypeDescriptor baseDescriptor, StructDescriptor historyDescriptor, OracleBEMetadata.PropertyConverter converter) {
        this.setPd(pd);
        this.setOracleType(baseDescriptor);
        this.setPropertyConverter(converter);
        this.historyDescriptor=historyDescriptor;
    }
    /**
    public PropertyDescriptor (Object  pd, StructDescriptor primitiveDescriptor, TypeDescriptor baseDescriptor, StructDescriptor historyDescriptor, OracleBEMetadata.PropertyConverter converter) {
        this.setPd(pd);
        this.setPrimitiveDescriptor(primitiveDescriptor);
        this.setOracleType(baseDescriptor);
        this.setPropertyConverter(converter);
        this.historyDescriptor=historyDescriptor;
    }**/

    public Object getPd() {
        return pd;
    }

    public void setPd(Object pd) {
        this.pd = pd;
    }

    public TypeDescriptor getOracleType() {
        return oracleType;
    }

    public void setOracleType(TypeDescriptor oracleType) {
        this.oracleType = oracleType;
    }

    public ArrayDescriptor getArrayDescriptor() {
        return arrayDescriptor;
    }

    public void setArrayDescriptor(ArrayDescriptor arrayDescriptor) {
        this.arrayDescriptor = arrayDescriptor;
    }

    public StructDescriptor getHistoryDescriptor() {
        return historyDescriptor;
    }

    public void setHistoryDescriptor(StructDescriptor historyDescriptor) {
        this.historyDescriptor = historyDescriptor;
    }

    public OracleBEMetadata.PropertyConverter getPropertyConverter() {
        return propertyConverter;
    }

    public void setPropertyConverter(OracleBEMetadata.PropertyConverter propertyConverter) {
        this.propertyConverter = propertyConverter;
    }

    public StructDescriptor getPrimitiveDescriptor() {
        return primitiveDescriptor;
    }

    public void setPrimitiveDescriptor(StructDescriptor primitiveDescriptor) {
        this.primitiveDescriptor = primitiveDescriptor;
    }
}
