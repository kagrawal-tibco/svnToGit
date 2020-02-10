package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.model.BinaryExpression;
import com.tibco.cep.query.model.BindVariable;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.FieldList;
import com.tibco.cep.query.model.Function;
import com.tibco.cep.query.model.FunctionIdentifier;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.Operator;
import com.tibco.cep.query.model.OrderClause;
import com.tibco.cep.query.model.QueryContext;
import com.tibco.cep.query.model.SelectContext;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.TypedContext;
import com.tibco.cep.query.model.UnaryExpression;
import com.tibco.cep.query.model.validation.Resolvable;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 19, 2007
 * Time: 2:56:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class BindVariableImpl extends AbstractExpression implements BindVariable {

    String label;


    /**
     * Constructor
     * @param parentContext
     * @param tree
     * @param label
     */
    public BindVariableImpl(ModelContext parentContext, CommonTree tree, String label) throws Exception {
        super(parentContext, tree);
        this.label = label;
    }


    /**
     * Returns the bind variable label
     *
     * @return int
     */
    public String getLabel() {
        return this.label;
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_BIND_VARIABLE;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof BindVariable)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final BindVariableImpl that = (BindVariableImpl) o;
        if (null == this.label) {
            return (null == that.label);
        } else {
            return this.label.equals(that.label);
        }
    }


    public int hashCode() {
        return this.label.hashCode();
    }


    public boolean isResolved() {
        return true;
    }


    public boolean resolveContext() throws Exception {
        this.setTypeInfo((TypeInfo) null);
        return true;
    }


    public boolean isBindingResolved() {
        return null != this.typeInfo;
    }


    public boolean resolveBinding() throws Exception {
        return this.resolveBinding(this.parentContext, this);
    }


    protected boolean resolveBinding(ModelContext ancestor, ModelContext ancestorChild) throws Exception {

        if (ancestor instanceof BinaryExpression) {
            switch (((BinaryExpression) ancestor).getOperator().getOpType()) {
                case Operator.OP_AT:
                case Operator.OP_DOT:
                    // Always unsupported.
                    break;

                case Operator.OP_IN:{
                    TypeInfo otherTypeInfoInComparison;
                    if (this == ((BinaryExpression) ancestor).getRightExpression()) {
                        otherTypeInfoInComparison = find(((BinaryExpression) ancestor).getLeftExpression());
                    } else {
                        otherTypeInfoInComparison = ((BinaryExpression) ancestor).getRightExpression().getTypeInfo();
                        if(otherTypeInfoInComparison == null){
                            otherTypeInfoInComparison = find(((BinaryExpression) ancestor).getLeftExpression());
                        }
                    }
                    if (null != otherTypeInfoInComparison) {
                        this.setTypeInfo(otherTypeInfoInComparison);
                        return this.isBindingResolved();
                    }

                    break; // No side is resolved => Unsupported.
                }

                case Operator.OP_EQ:
                case Operator.OP_GE:
                case Operator.OP_GT:
                case Operator.OP_LE:
                case Operator.OP_LT:
                case Operator.OP_NE: {
                    TypeInfo otherTypeInfoInComparison;
                    if (this == ((BinaryExpression) ancestor).getRightExpression()) {
                        otherTypeInfoInComparison = ((BinaryExpression) ancestor).getLeftExpression().getTypeInfo();
                    } else {
                        otherTypeInfoInComparison = ((BinaryExpression) ancestor).getRightExpression().getTypeInfo();
                    }
                    if (null != otherTypeInfoInComparison) {
                        this.setTypeInfo(otherTypeInfoInComparison);
                        return this.isBindingResolved();
                    } else {
                        break; // No side is resolved => Unsupported.
                    }
                }
                case Operator.OP_AND:
                case Operator.OP_NOT:
                case Operator.OP_OR: {
                    this.setTypeInfo(new TypeInfoImpl(Boolean.class));
                    return this.isBindingResolved();
                }
                case Operator.OP_ARRAY:
                    if (this == ((BinaryExpression) ancestor).getRightExpression()) {
                        this.setTypeInfo(new TypeInfoImpl(Integer.class));
                        return this.isBindingResolved();
                    } else break; // Unsupported.
                case Operator.OP_BETWEEN: {
                    if (this == ((BinaryExpression) ancestor).getLeftExpression()) {
                        final Expression range = ((BinaryExpression) ancestor).getRightExpression();
                        final TypeInfo rangeTypeInfo = range.getTypeInfo();
                        if (null != rangeTypeInfo) {
                           this.setTypeInfo(rangeTypeInfo);
                            return this.isBindingResolved();
                        }
                    }
                    break; // If not returned yet => unsupported situation.
                }
                case Operator.OP_LIKE:
                    if (this == ((BinaryExpression) ancestor).getRightExpression()) {
                        this.setTypeInfo(new TypeInfoImpl(String.class));
                        return this.isBindingResolved();
                    } else break; // Unsupported.
                case Operator.OP_RANGE: {
                    TypeInfo otherTypeInfoInRange;
                    if (this == ((BinaryExpression) ancestor).getRightExpression()) {
                        otherTypeInfoInRange = ((BinaryExpression) ancestor).getLeftExpression().getTypeInfo();
                    } else {
                        otherTypeInfoInRange = ((BinaryExpression) ancestor).getRightExpression().getTypeInfo();
                    }

                    if (null != otherTypeInfoInRange) {
                        this.setTypeInfo(otherTypeInfoInRange); // Just match the other.
                        return this.isBindingResolved();
                    } else {
                        // Match the type info of the left arg of the BETWEEN clause..
                        final BinaryExpression between = (BinaryExpression) ancestor.getParentContext();
                        final TypeInfo betweenLeftArgTypeInfo = between.getLeftExpression().getTypeInfo();
                        if (null != betweenLeftArgTypeInfo) {
                            this.setTypeInfo(betweenLeftArgTypeInfo);
                            return this.isBindingResolved();
                        } else {
                            break; // Nothing resolved in the BETWEEN => Unsupported.
                        }
                    }
                }
                default:
                    this.setTypeInfo(((BinaryExpression) ancestor).getTypeInfo());
                    return this.isBindingResolved();
            }
        } else if (ancestor instanceof UnaryExpression) {
            return this.resolveBinding(ancestor.getParentContext(), ancestor);

        } else if (ancestor instanceof FunctionIdentifier) {
            int argIndex = 0;
            for (ModelContext arg : this.childContext) {
                if (arg == ancestorChild) {
                    break;
                }
                argIndex++;
            }
            final Function f = (Function) ((FunctionIdentifier) ancestor).getIdentifiedContext();
            this.setTypeInfo(f.getArguments()[argIndex].getTypeInfo());
            return this.isBindingResolved();

        } else if (ancestor instanceof FieldList) {
            final ModelContext otherSide = ancestor.getParentContext().getChildren()[0];
            if ((otherSide instanceof TypedContext)
                    && (otherSide instanceof Resolvable)
                    && ((Resolvable) otherSide).isResolved()) {
                this.setTypeInfo(((TypedContext) otherSide).getTypeInfo());
                return this.isBindingResolved();
            }

        } else if ((ancestor instanceof SelectContext) || (ancestor instanceof OrderClause)) {
            this.setTypeInfo(TypeInfoImpl.INTEGER);
            return this.isBindingResolved();
        }

        throw new ResolveException("Bind variable '" + this.getSourceString()
                + "' cannot be resolved in: " + ((QueryContext) ancestor).getSourceString());
    }

    TypeInfo find(ModelContext node) throws Exception {
        if(node instanceof BinaryExpression){
            return find(((BinaryExpression) node).getRightExpression());
        }
        else if(node instanceof TypedContext){
            return ((TypedContext) node).getTypeInfo();
        }

        return null;
    }

    public String toString() {
        return "$" + this.label;
    }


}
