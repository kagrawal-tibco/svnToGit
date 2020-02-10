/**
 * 
 */
package com.tibco.cep.query.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.model.BinaryExpression;
import com.tibco.cep.query.model.BinaryOperator;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.Operator;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.validation.validators.BinaryExpressionValidator;
import com.tibco.cep.query.model.visitor.HierarchicalContextVisitor;
import com.tibco.cep.query.model.visitor.impl.ContextResolutionVisitor;

/**
 * @author pdhar
 */
public class BinaryExpressionImpl extends AbstractExpression implements BinaryExpression {

    protected BinaryOperator operator;


    /**
     * @param parentContext
     * @param tree
     * @param operator
     */
    public BinaryExpressionImpl(ModelContext parentContext, CommonTree tree, BinaryOperator operator) {
        super(parentContext, tree);
        this.operator = operator;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.Expression#getTypeInfo()
     */
    public TypeInfo getTypeInfo() throws Exception {
        final List<Expression> operands = new ArrayList<Expression>();
        operands.add(this.getLeftExpression());
        operands.add(this.getRightExpression());
        return this.getOperator().getResultType(operands);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.Expression#hasGroupFunction()
     */
    public boolean hasAggregateFunctionReference() {
        // TODO Auto-generated method stub
        return false;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.Expression#hasPropertyReference()
     */
    public boolean hasPropertyReference() {
        // TODO Auto-generated method stub
        return false;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.ModelContext#getContextType()
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_BINARY_EXPRESSION;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.BinaryExpression#getRightExpression()
     */
    public Expression getRightExpression() {
        return (Expression) this.getChild(1);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.BinaryExpression#getLeftExpression()
     */
    public Expression getLeftExpression() {
        return (Expression) this.getChild(0);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.BinaryExpression#getOperator()
     */
    public BinaryOperator getOperator() {
        return operator;
    }


    public boolean resolveContext() throws Exception {
        final List<Expression> operands = new ArrayList<Expression>();
        operands.add(this.getLeftExpression());
        operands.add(this.getRightExpression());
        this.typeInfo = this.getOperator().getResultType(operands);
        return this.isResolved();
    }


    /**
     * Returns true if the context has been resolved or false
     * 
     * @return boolean
     */
    public boolean isResolved() {
        return null != this.typeInfo;
    }


    /**
     * visitor pattern accept Resolve the left expression
     * 
     * @param v
     * @return boolean
     */
    public boolean accept(HierarchicalContextVisitor v) throws Exception {
        if (v instanceof ContextResolutionVisitor) {
            ModelContext leftExpr = childContext.get(0);
            ModelContext rightExpr = childContext.get(1);
            return leftExpr.accept(v)
                    && rightExpr.accept(v)
                    && v.visit(this);
        }
        else {
            return super.accept(v);
        }
    }


    public ModelContext getIdentifiedContext() throws ResolveException {
        switch (this.getOperator().getOpType()) {
            case Operator.OP_AT:
            case Operator.OP_DOT: {
                return this.getRightExpression().getIdentifiedContext();
            }
            case Operator.OP_CAST: {
                return this.getLeftExpression().getIdentifiedContext();                
            }

        }
        return super.getIdentifiedContext();
    }


    public void validate() throws Exception {
        BinaryExpressionValidator validator = new BinaryExpressionValidator();

        validator.validate(this);
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof BinaryExpressionImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }
        final BinaryExpressionImpl that = (BinaryExpressionImpl) o;

        return this.getOperator().equals(that.getOperator())
                && this.getLeftExpression().equals(that.getLeftExpression())
                && this.getRightExpression().equals(that.getRightExpression());
    }


    public int hashCode() {
        long longHash = this.operator.hashCode();
        longHash = longHash * 29 + this.getLeftExpression().hashCode();
        longHash ^= (longHash >>> 32);
        longHash = longHash * 29 + this.getRightExpression().hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }
}
