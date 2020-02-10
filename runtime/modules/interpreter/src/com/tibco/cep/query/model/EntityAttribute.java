package com.tibco.cep.query.model;

public interface EntityAttribute extends ModelContext, TypedContext {

    /**
     * @return Entity that contains this EntityAttribute.
     */
    Entity getEntity();

    /**
     * @return String the name of the Attribute
     */
    public String getName();

    /**
     * @param name the ontology object name
     */
    public void setName(String name);

    /**
     * @return Object the ontology object reference
     */
    public Object getOntologyObject();

    /**
     * @param o the ontology object reference
     */
    public void  setOntologyObject(Object o);
}
