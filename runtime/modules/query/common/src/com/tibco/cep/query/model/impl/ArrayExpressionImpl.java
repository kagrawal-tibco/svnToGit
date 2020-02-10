package com.tibco.cep.query.model.impl;

import java.util.Iterator;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.model.ArrayAccessProxy;
import com.tibco.cep.query.model.ArrayExpression;
import com.tibco.cep.query.model.EntityAttributeProxy;
import com.tibco.cep.query.model.EntityPropertyProxy;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.Function;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.Operator;
import com.tibco.cep.query.model.ProxyContext;
import com.tibco.cep.query.model.TypeInfo;

/*
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 28, 2007
 * Time: 6:47:21 PM
 */

public class ArrayExpressionImpl extends BinaryExpressionImpl implements ArrayExpression {

    protected ModelContext identifiedCtx;

    public ArrayExpressionImpl(ModelContext parentContext, CommonTree tree) throws Exception {
        super(parentContext, tree, new BinaryOperatorImpl(AbstractOperator.getValidMask(Operator.OP_ARRAY) , Operator.OP_ARRAY));
        this.identifiedCtx = null;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArrayExpression)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final ArrayExpression that = (ArrayExpression) o;
        return this.getArrayIdentifierExpression().equals(that.getArrayIdentifierExpression())
                && this.getArrayIndexExpression().equals(that.getArrayIndexExpression());
    }


    public Expression getArrayIdentifierExpression() {
        return this.getLeftExpression();
    }


    /**
     * Returns the array index
     *
     * @return int
     */
    public Expression getArrayIndexExpression() {
        return this.getRightExpression();
    }


    public int getContextType() {
        return ModelContext.CTX_TYPE_ARRAY_EXPRESSION;
    }


    public ModelContext getIdentifiedContext() throws ResolveException {
        return this.identifiedCtx;
    }


    public int hashCode() {
        long longHash = this.getArrayIdentifierExpression().hashCode();
        longHash = longHash * 29 + this.getArrayIndexExpression().hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }


    public boolean isResolved() {
        return null != this.identifiedCtx;
    }


    protected ArrayAccessProxy makeArrayAccessProxy(ProxyContext owner, Expression index) {
        for (Iterator it = owner.getChildrenIterator(); it.hasNext(); ) {
            final Object child = it.next();
            if ((child instanceof ArrayAccessProxy) && ((ArrayAccessProxy)child).getArrayIndex().equals(index)) {
                return (ArrayAccessProxy) child;
            }
        }
        return new ArrayAccessProxyImpl(owner, index);
    }


    /**
     * Resolves the context
     * @return boolean
     * @throws Exception
     */
    public boolean resolveContext() throws Exception {
        TypeInfo typeInfo;

        final Expression index = this.getArrayIndexExpression();
        typeInfo = index.getTypeInfo();
        if (!(typeInfo.isInt() || typeInfo.isLong())) {
            throw new ResolveException("Array index is neither int nor long.");
        }

        final ModelContext array = this.getArrayIdentifierExpression().getIdentifiedContext();

        if (array instanceof ProxyContext) {
            typeInfo = ((ProxyContext) array).getTypeInfo();
        } else if (array instanceof Expression) {
            typeInfo = ((Expression) array).getTypeInfo();
        } else if (array instanceof Function) {
            typeInfo = ((Function) array).getTypeInfo();
        } else {
            throw new ResolveException("Array was not resolved.");
        }

        if (!typeInfo.isArray()) {
            throw new ResolveException("Array operator applied to non-array.");
        }

        final ArrayAccessProxy proxy;
        try {
            if (array instanceof EntityAttributeProxy) {
                proxy = this.makeArrayAccessProxy((EntityAttributeProxy) array, index);
                this.identifiedCtx = proxy;
                this.setTypeInfo(proxy.getTypeInfo());
            } else if (array instanceof EntityPropertyProxy) {
                proxy = this.makeArrayAccessProxy((EntityPropertyProxy) array, index);
                this.identifiedCtx = proxy;
                this.setTypeInfo(proxy.getTypeInfo());
            } else {
                this.identifiedCtx = this;
                this.setTypeInfo(typeInfo.getArrayItemType());
            }
        } catch (Exception e) {
            throw new ResolveException("Cannot resolve array property.", e);
        }

        return this.isResolved();
    }


}
