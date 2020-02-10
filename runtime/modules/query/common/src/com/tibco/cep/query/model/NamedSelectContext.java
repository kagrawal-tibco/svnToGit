package com.tibco.cep.query.model;

/**
 * @author pdhar
 */
public interface NamedSelectContext
        extends NamedContext, SelectContext, QueryModel {

    public static NamedContextId CTX_ID = new NamedContextId() {
        public String toString() { return "SELECT_CONTEXT"; }
    };

}

