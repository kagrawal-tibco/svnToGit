package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.Entity;
import com.tibco.cep.query.model.EntityAttribute;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;

public class EntityAttributeImpl
        extends AbstractRegistryEntryContext
        implements EntityAttribute {

    private String name;
    private Object ontologyObject;

    protected EntityAttributeImpl(ModelContext parentContext, CommonTree tree, String name, TypeInfo ti, Object o) {
        super(parentContext);
        this.name = name;
        this.typeInfo = ti;
        this.ontologyObject = o;
    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.impl.IdentifierImpl#getContextType()
      */
    public int getContextType() {
        return ModelContext.CTX_TYPE_ENTITY_ATTRIBUTE;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.TypedContext#getTypeInfo()
      */
    public TypeInfo getTypeInfo() {
        return this.typeInfo;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.EntityAttribute#getName()
      */
    public String getName() {
        return this.name;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.EntityAttribute#setName(java.lang.String)
      */
    public void setName(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.EntityAttribute#getOntologyObject()
      */
    public Object getOntologyObject() {
        return this.ontologyObject;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.EntityAttribute#setOntologyObject(java.lang.Object)
      */
    public void setOntologyObject(Object o) {
        this.ontologyObject = o;
    }


    public Entity getEntity() {
        return (Entity) getParentContext();
    }


    public String toString() {
        return this.getEntity().getEntityPath() + "@" +this.getName();

    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof EntityAttributeImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }
        final EntityAttributeImpl that = (EntityAttributeImpl) o;

        return ((null == this.ontologyObject) && (null == that.ontologyObject))
                || ((null != this.ontologyObject) && (this.ontologyObject.equals(that.ontologyObject)));
    }

    public int hashCode() {
        return (this.ontologyObject != null) ? this.ontologyObject.hashCode() : 0;
    }

}
