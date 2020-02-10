package com.tibco.cep.query.model;

/**
 * @author pdhar
 */
public interface NamedDeleteContext
        extends NamedContext, DeleteContext, QueryModel {

    public static NamedContextId CTX_ID = new NamedContextId() {
        public String toString() { return "DELETE_CONTEXT"; }
    };

}