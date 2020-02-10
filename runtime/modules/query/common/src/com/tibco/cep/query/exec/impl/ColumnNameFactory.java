package com.tibco.cep.query.exec.impl;

import java.util.Calendar;

import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.TupleInfoDescriptorImpl;
import com.tibco.cep.query.model.Aliased;
import com.tibco.cep.query.model.EntityAttribute;
import com.tibco.cep.query.model.EntityAttributeProxy;
import com.tibco.cep.query.model.EntityProperty;
import com.tibco.cep.query.model.EntityPropertyProxy;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.Projection;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.TypeNames;
import com.tibco.cep.runtime.model.TypeManager;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jan 12, 2009
* Time: 4:04:56 PM
*/
public class ColumnNameFactory {


    private TypeManager typeManager;


    public ColumnNameFactory(
            TypeManager typeManager) {

        this.typeManager = typeManager;
    }


    public String makeColumnClassName(
            TypeInfo typeInfo)
            throws Exception {

        final Class columnClass = typeInfo.getRuntimeClass(this.typeManager);

        if (com.tibco.cep.kernel.model.entity.Entity[].class.isAssignableFrom(columnClass)) {
            final Class componentClass = columnClass.getComponentType();
            final TypeManager.TypeDescriptor descriptor = this.typeManager.getTypeDescriptor(componentClass);
            if (null != descriptor) {
                return descriptor.getURI() + "[]";
            } else {
                return columnClass.getCanonicalName();
            }

        } else if (com.tibco.cep.kernel.model.entity.Entity.class.isAssignableFrom(columnClass)) {
            final TypeManager.TypeDescriptor descriptor = this.typeManager.getTypeDescriptor(columnClass);
            if (null != descriptor) {
                return descriptor.getURI();
            } else {
                return columnClass.getCanonicalName();
            }

        } else if (Boolean[].class.equals(columnClass)
                || boolean[].class.equals(columnClass)) {
            return TypeNames.BOOLEAN + "[]";

        } else if (Boolean.class.equals(columnClass)
                || boolean.class.equals(columnClass)) {
            return TypeNames.BOOLEAN;

        } else if (Calendar[].class.isAssignableFrom(columnClass)) {
            return TypeNames.DATETIME + "[]";

        } else if (Calendar.class.isAssignableFrom(columnClass)) {
            return TypeNames.DATETIME;

        } else if (Double[].class.equals(columnClass)
                || Float[].class.equals(columnClass)
                || double[].class.equals(columnClass)
                || float[].class.equals(columnClass)) {
            return TypeNames.DOUBLE + "[]";

        } else if (Double.class.equals(columnClass)
                || Float.class.equals(columnClass)
                || double.class.equals(columnClass)
                || float.class.equals(columnClass)) {
            return TypeNames.DOUBLE;

        } else if (Byte[].class.equals(columnClass)
                || Character[].class.equals(columnClass)
                || Integer[].class.equals(columnClass)
                || Short[].class.equals(columnClass)
                || byte[].class.equals(columnClass)
                || char[].class.equals(columnClass)
                || int[].class.equals(columnClass)
                || short[].class.equals(columnClass)) {
            return TypeNames.INT + "[]";

        } else if (Byte.class.equals(columnClass)
                || Character.class.equals(columnClass)
                || Integer.class.equals(columnClass)
                || Short.class.equals(columnClass)
                || byte.class.equals(columnClass)
                || char.class.equals(columnClass)
                || int.class.equals(columnClass)
                || short.class.equals(columnClass)) {
            return TypeNames.INT;

        } else if (Long[].class.equals(columnClass)
                || long[].class.equals(columnClass)) {
            return TypeNames.LONG + "[]";

        } else if (Long.class.equals(columnClass)
                || long.class.equals(columnClass)) {
            return TypeNames.LONG;

        } else if (String[].class.isAssignableFrom(columnClass)) {
            return TypeNames.STRING + "[]";

        } else if (String.class.isAssignableFrom(columnClass)) {
            return TypeNames.STRING;

        } else {
            return columnClass.getCanonicalName();
        }
    }


    public String makeColumnName(
            EntityAttributeProxy proxy,
            TupleInfoDescriptor descriptor) {

        final ModelContext owner = proxy.getAttributeOwner();
        final EntityAttribute attr = proxy.getAttribute();

        String ownerName;
        if (owner instanceof Aliased) {
            final Aliased aliased = (Aliased) owner;
            if (aliased.isPseudoAliased()) {
                ownerName = attr.getEntity().getEntityName();
            } else {
                ownerName = aliased.getAlias();
            }
        } else {
            ownerName = proxy.getAttribute().getEntity().getEntityName();
        }

        return makeColumnName(ownerName + "@" + attr.getName(), descriptor);
    }


    public String makeColumnName(
            EntityPropertyProxy proxy,
            TupleInfoDescriptor descriptor) {

        final ModelContext owner = proxy.getPropertyOwner();
        final EntityProperty prop = proxy.getProperty();

        String ownerName;
        if (owner instanceof Aliased) {
            final Aliased aliased = (Aliased) owner;
            if (aliased.isPseudoAliased()) {
                ownerName = prop.getEntity().getEntityName();
            } else {
                ownerName = aliased.getAlias();
            }
        } else {
            ownerName = prop.getEntity().getEntityName();
        }

        return makeColumnName(ownerName + "." + prop.getName(), descriptor);
    }


    public String makeColumnName(
            ModelContext projectionItem,
            Projection projection,
            TupleInfoDescriptorImpl descriptor) {

        if (projection.isPseudoAliased()) {
            if (projectionItem instanceof EntityPropertyProxy) {
                return makeColumnName(((EntityPropertyProxy) projectionItem), descriptor);

            } else if (projectionItem instanceof EntityAttributeProxy) {
                return makeColumnName(((EntityAttributeProxy) projectionItem), descriptor);
            }
            return makeColumnName(projection.getProjectionText(), descriptor);

        } else {
            return makeColumnName(projection.getAlias(), descriptor);
        }
    }


    public String makeColumnName(
            String baseName,
            TupleInfoDescriptor descriptor) {

        String newName = baseName;
        for (int i = 1; null != descriptor.getColumnByName(newName); i++) {
            newName = baseName + " (" + i + ")";
        }
        return newName;
    }

}
