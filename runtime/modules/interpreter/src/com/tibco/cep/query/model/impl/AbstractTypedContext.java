/**
 *
 */
package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.TypedContext;

/**
 * @author pdhar
 *
 */
public abstract class AbstractTypedContext extends AbstractModelContext
        implements TypedContext {

    protected TypeInfo typeInfo;

    /**
     * @param parentContext
     * @param tree
     */
    public AbstractTypedContext(ModelContext parentContext, CommonTree tree) {
        super(parentContext);
    }


    /**
     * Returns the TypeInfo information for the typed context
     * @return TypeInfo the type information
     */
    public TypeInfo getTypeInfo() throws Exception {
        return typeInfo;
    }


    /**
     * Sets the TypeInfo information for the typed context
     * @param ti the type information
     */
    public void setTypeInfo(TypeInfo ti) {
        this.typeInfo = ti;
    }


    /**
     * Set the argument type based on the class name
     *
     * @param className
     */
    public void setTypeInfo(String className) throws Exception {
        this.typeInfo = new TypeInfoImpl(Class.forName(className));
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractTypedContext)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final AbstractTypedContext that = (AbstractTypedContext) o;

        if (this.typeInfo == null) {
            return that.typeInfo == null;
        }

        return this.typeInfo.equals(that.typeInfo);
    }


    public int hashCode() {
        return this.typeInfo.hashCode();
    }


}
