package com.tibco.cep.query.model.impl;

import java.lang.reflect.Array;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.Identifier;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeExpression;
import com.tibco.cep.query.model.TypeInfo;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 8, 2008
* Time: 5:05:58 PM
*/
public class TypeExpressionImpl
        extends AbstractExpression
        implements TypeExpression {

    private byte arrayDepth;
    private TypeInfo instancesTypeInfo;


    public TypeExpressionImpl(ModelContext parentContext, CommonTree tree, byte arrayDepth) {
        super(parentContext, tree);
        this.arrayDepth = arrayDepth;
        this.instancesTypeInfo = null;
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return CTX_TYPE_TYPE;
    }


    /**
     * Gets the TypeInfo of the instances of the type represented by this expression.
     *
     * @return TypeInfo of the instances of the type represented by this expression.
     */
    public TypeInfo getInstancesTypeInfo() {
        return this.instancesTypeInfo;
    }


    /**
     * Returns true if the context has been resolved or false
     *
     * @return boolean
     */
    public boolean isResolved() {
        return null != this.instancesTypeInfo;
    }


    public boolean resolveContext() throws Exception {
        this.setTypeInfo(TypeInfoImpl.CLASS);
        final Identifier typeId = (Identifier) this.childContext.get(0);
        if (this.arrayDepth > 0) {
            final Class arrayClass = Array.newInstance(typeId.getTypeInfo().getDesigntimeClass(), new int[this.arrayDepth]).getClass();
            this.instancesTypeInfo = new TypeInfoImpl(arrayClass);
        } else {
            this.instancesTypeInfo = typeId.getTypeInfo();
        }
        return this.isResolved();
    }


}
