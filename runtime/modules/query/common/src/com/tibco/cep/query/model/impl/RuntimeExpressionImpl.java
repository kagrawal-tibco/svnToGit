package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.RuntimeExpression;
import com.tibco.cep.query.model.TypeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 21, 2007
 * Time: 1:12:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuntimeExpressionImpl extends AbstractExpression implements RuntimeExpression {

    public RuntimeExpressionImpl(ModelContext parentContext, CommonTree tree) throws Exception {
        super(parentContext, tree);
        this.typeInfo = new TypeInfoImpl(Object.class);
    }

    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_RUNTIME_EXPRESSION;
    }

    /**
     * Returns the TypeInfo information for the typed context
     *
     * @return TypeInfo the type information
     */
    public TypeInfo getTypeInfo() throws Exception {
        return super.getTypeInfo();    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Set the argument type based on the class name
     *
     * @param className
     */
    public void setTypeInfo(String className) throws Exception {
    }

    /**
     * Sets the TypeInfo information for the typed context
     *
     * @param ti the type information
     */
    public void setTypeInfo(TypeInfo ti) {

    }

    public boolean isResolved() {
        return true;
    }

}
