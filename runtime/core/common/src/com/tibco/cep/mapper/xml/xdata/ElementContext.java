// copyright 2001 TIBCO Software Inc

package com.tibco.cep.mapper.xml.xdata;

/**
 * Maintains the current namespace context for an element
 */

import java.util.HashMap;

import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.SmType;

public final class ElementContext {
    private ElementContext          previous;
    private HashMap                 prefixToSmSchema;
    private SmSchema                effectiveSchema;
    private SmType                  type;

    public ElementContext(ElementContext previous) {
        this.previous = previous;
        prefixToSmSchema = new HashMap();
    }

    /**
     * Returns the element context for the parent
     * element
     */
    public ElementContext getPrevious() {
        return previous;
    }

    /**
     * Gets the currently effective schema.  This may be "inherited"
     * from an ancestor element
     */
    public SmSchema getEffectiveSchema() {
        if (effectiveSchema == null) {
            if (previous == null) {
                return null;
            } else {
                return previous.getEffectiveSchema();
            }
        } else {
            return effectiveSchema;
        }
    }

    /**
     * Sets the effective schema for this and any child elements
     * that don't change it.
     */
    public void setEffectiveSchema(SmSchema schema) {
        effectiveSchema = schema;
    }

    /**
     * Returns the type of the current element
     */
    public SmType getType() {
        return type;
    }

    /**
     * Sets the type of the current element
     */
    public void setType(SmType type) {
        this.type = type;
    }

    /**
     * Adds a prefix to schema mapping
     */
    protected void addSchema(String prefix, SmSchema schema) {
        prefixToSmSchema.put(prefix, schema);
    }

    /**
     * Returns the schema that is currently referred to by the
     * specified prefix, or null if the prefix does not map
     * to a schema.
     */
    protected SmSchema getSchema(String prefix) {
        SmSchema ret = (SmSchema) prefixToSmSchema.get(prefix);
        if (ret == null) {
            if (previous == null) {
                return null;
            } else {
                return previous.getSchema(prefix);
            }
        } else {
            return ret;
        }
    }
}