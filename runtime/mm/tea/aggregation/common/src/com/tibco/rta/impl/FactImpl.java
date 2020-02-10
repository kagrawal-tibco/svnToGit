package com.tibco.rta.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.DataTypeMismatchException;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.UndefinedSchemaElementException;
import com.tibco.rta.model.impl.ModelValidations;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * The Interface Fact.
 */
public class FactImpl implements Fact {

    private static final long serialVersionUID = 7169898234293293411L;

    protected Key key;

    protected String ownerSchemaName;

    protected Map<String, Object> attributes;

    transient protected RtaSchema ownerSchema;
    // Consider default fact as new.
    protected boolean isNew = true;

    protected boolean isDuplicate;

    public FactImpl() {

    }

    public FactImpl(RtaSchema ownerSchema) {
        this(ownerSchema, null);
    }

    public FactImpl(RtaSchema ownerSchema, String uid) {
        this(ownerSchema, uid, new LinkedHashMap<String, Object>());
    }

    public FactImpl(RtaSchema ownerSchema, String uid, Map<String, Object> attributes) {
        this(ownerSchema.getName(), uid);
        this.ownerSchema = ownerSchema;
        this.ownerSchemaName = this.ownerSchema.getName();
        this.attributes = attributes;
    }

    FactImpl(String schemaName, String uid) {
        key = new FactKeyImpl(schemaName, uid);
    }

    @Override
    @JsonIgnore
    public RtaSchema getOwnerSchema() {
        return ownerSchema;
    }

    public void setOwnerSchema(RtaSchema ownerSchema) {
        this.ownerSchema = ownerSchema;
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }


    @Override
    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    @Override
    @JsonIgnore
    public Set<String> getAttributeNames() {
        return attributes.keySet();
    }


    public Map<String, Object> getAttributes() {
        //For now simply return the same map
        return attributes;
//        return Collections.unmodifiableMap(attributes);
    }

    @Override
    public void clear() {
        attributes.clear();
        attributes = null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fact: ");
        stringBuilder.append(key);
        stringBuilder.append(", ");

        for (Map.Entry<String, Object> e : attributes.entrySet()) {
            stringBuilder.append("|k=");
            stringBuilder.append(e.getKey());
            stringBuilder.append(", v=");
            stringBuilder.append(e.getValue());
        }
        return stringBuilder.toString();
    }

    @Override
    public void setAttribute(String attrName, Object value)
            throws UndefinedSchemaElementException, DataTypeMismatchException {
        validateAttr(attrName, value);
        if (attributes == null) {
            attributes = new LinkedHashMap<String, Object>();
        }
        attributes.put(attrName, value);
    }

    public void setAttributes(Map<String, Object> attributes) throws UndefinedSchemaElementException, DataTypeMismatchException {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            validateAttr(entry.getKey(), entry.getValue());
        }
        this.attributes = attributes;
    }

    private void validateAttr(String attrName, Object value) throws UndefinedSchemaElementException, DataTypeMismatchException {
        Attribute attr;
        if ((attr = ownerSchema.getAttribute(attrName)) == null) {
            throw new UndefinedSchemaElementException(String.format("Attribute: [%s] not found in schema [%s]", attrName, ownerSchema.getName()));
        }
        ModelValidations.validateDataType(attr, value);
    }

    public String getOwnerSchemaName() {
        return ownerSchemaName;
    }

    @JsonIgnore
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public void setIsDuplicate(boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }

    @JsonIgnore
    public boolean isDuplicate() {
        return this.isDuplicate;
    }

    public int size() {
        return attributes.size();
    }
}