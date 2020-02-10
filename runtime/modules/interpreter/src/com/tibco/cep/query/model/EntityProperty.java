package com.tibco.cep.query.model;

public interface EntityProperty extends ModelContext, TypedContext {
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

    /**
     * Return the parent Entity of the property
     * @return Entity
     */
    Entity getEntity();

    /**
     * Returns true iif the property is an array.
     * @return boolean true iif the property is an array
     */
    boolean isArray();
}
