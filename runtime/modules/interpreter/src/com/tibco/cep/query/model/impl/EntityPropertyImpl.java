/**
 * 
 */
package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.Entity;
import com.tibco.cep.query.model.EntityProperty;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;

/**
 * @author pdhar
 *
 */
public class EntityPropertyImpl extends AbstractRegistryEntryContext implements EntityProperty {

    private String name;
    private Object ontologyObject;
    private boolean isArray;


    /**
     * @param parentContext
     * @param tree
     * @param name
     * @param ti
     */
    public EntityPropertyImpl(ModelContext parentContext, CommonTree tree, String name, TypeInfo ti, Object o,
                              boolean isArray) {
        super(parentContext);
        this.name = name;
        this.typeInfo = ti;
        this.ontologyObject = o;
        this.isArray = isArray;
    }


    public int getContextType() {
        return ModelContext.CTX_TYPE_ENTITY_PROPERTY;
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


    /**
     * Returns true iif the property is an array.
     *
     * @return boolean true iif the property is an array
     */
    public boolean isArray() {
        return this.isArray;
    }


    public String toString() {
        return this.getEntity().getEntityPath() + "." +this.getName();
    }


//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (! (o instanceof EntityPropertyImpl)) {
//            return false;
//        }
//
//        final EntityPropertyImpl that = (EntityPropertyImpl) o;
//
//        return ((null == this.ontologyObject) && (null == that.ontologyObject))
//                || ((null != this.ontologyObject) && (this.ontologyObject.equals(that.ontologyObject)));
//    }
//
//
//    public int hashCode() {
//        return (this.ontologyObject != null) ? this.ontologyObject.hashCode() : 0;
//    }


    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }

        if (!(o instanceof EntityPropertyImpl)) {
            return false;
        }

        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final EntityPropertyImpl that = (EntityPropertyImpl) o;
        return  (this.isArray == that.isArray)
                && (this.name.equals(that.name))
                && (this.ontologyObject == that.ontologyObject);
    }


    @Override
    public int hashCode() {
        long longHash = super.hashCode();
        longHash = longHash * 29 + this.name.hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }


}
