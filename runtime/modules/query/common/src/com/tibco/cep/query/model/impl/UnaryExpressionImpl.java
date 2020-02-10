package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypedContext;
import com.tibco.cep.query.model.UnaryExpression;
import com.tibco.cep.query.model.UnaryOperator;
import com.tibco.cep.query.model.validation.validators.UnaryExpressionValidator;

/**
 * Created by IntelliJ IDEA. User: pdhar Date: Oct 17, 2007 Time: 7:24:19 PM To
 * change this template use File | Settings | File Templates.
 */
public class UnaryExpressionImpl extends AbstractExpression implements UnaryExpression {
    private UnaryOperator operator;

    public UnaryExpressionImpl(ModelContext parentContext, CommonTree tree, UnaryOperatorImpl op) {
        super(parentContext, tree);
        this.operator = op;
    }

    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_UNARY_EXPRESSION;
    }

    /**
     * @return UnaryOperator the Operator for the unary expression
     */
    public UnaryOperator getOperator() {
        return this.operator;
    }


    public boolean resolveContext() throws Exception {
        final ModelContext child = (ModelContext) this.getChildrenIterator().next();
        if (child instanceof TypedContext) {
            this.setTypeInfo(((TypedContext) child).getTypeInfo());
        }
        return this.isResolved();
    }


    public boolean isResolved() {
        return null != this.typeInfo;
    }


    /**
     * @return Expression argument that the UnaryOperator is applied to.
     */
    public Expression getArgument() {
        return (Expression) this.getChildrenIterator().next();
    }


    public void validate() throws Exception {
        UnaryExpressionValidator unary = new UnaryExpressionValidator();
        unary.validate(this);
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof UnaryExpressionImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final UnaryExpressionImpl that = (UnaryExpressionImpl) o;
        return super.equals(that)
            && this.childContext.equals(that.childContext);
    }


    public int hashCode() {
        long longHash = this.childContext.hashCode();
        longHash = 29 * longHash + this.operator.hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }

    public ModelContext getIdentifiedContext() throws ResolveException {
        if (this.operator.getOpType() == UnaryOperator.OP_GROUP) {
            return this.getArgument().getIdentifiedContext();
        }
        return this.getArgument();
    }
}
