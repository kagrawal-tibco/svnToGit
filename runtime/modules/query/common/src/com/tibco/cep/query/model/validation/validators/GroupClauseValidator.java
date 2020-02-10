package com.tibco.cep.query.model.validation.validators;

import java.util.HashSet;
import java.util.Set;

import com.tibco.cep.query.model.AggregateFunctionIdentifier;
import com.tibco.cep.query.model.BinaryExpression;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.FieldList;
import com.tibco.cep.query.model.GroupClause;
import com.tibco.cep.query.model.HavingClause;
import com.tibco.cep.query.model.Identifier;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.Operator;
import com.tibco.cep.query.model.Projection;
import com.tibco.cep.query.model.ProjectionAttributes;
import com.tibco.cep.query.model.QueryContext;
import com.tibco.cep.query.model.SelectContext;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;

/*
 * Author: Ashwin Jayaprakash Date: Dec 10, 2007 Time: 6:21:36 PM
 */

public class GroupClauseValidator implements Validator<GroupClause> {
    protected static final String message = "Columns that are not part of the Group-By"
            + " clause have to be used within an Aggregate function.";

    public boolean validate(GroupClause info) throws ValidationException {
        SelectContext selectContext = (SelectContext)info.getOwnerContext();

        try {
            HashSet<Expression> groupFields = new HashSet<Expression>();
            FieldList fieldList = info.getFieldList();
            int children = fieldList.getChildCount();
            for (int i = 0; i < children; i++) {
                Expression expression = fieldList.getExpression(i);

                if (groupFields.add(expression) == false) {
                    throw new ValidationException("Elements in the Group clause must be unique.",
                            expression.getLine(), expression.getCharPosition());
                }
            }

            HavingClause havingClause = info.getHavingClause();
            if (havingClause != null) {
                Expression havingExpr = havingClause.getExpression();
                TypeInfo typeInfo = havingExpr.getTypeInfo();
                if (typeInfo.isAtom() == false || typeInfo.isBoolean() == false) {
                    throw new ValidationException(
                            "Having clause must be an expression that evaluates to a boolean value",
                            havingClause.getLine(), havingClause.getCharPosition());
                }

                verifyAgainstGroupColumns(havingExpr, groupFields, havingExpr);
            }

            ProjectionAttributes projectionAttributes = selectContext.getProjectionAttributes();
            Projection[] projections = projectionAttributes.getAllProjections();
            for (Projection projection : projections) {
                Expression expression = projection.getExpression();
                verifyAgainstGroupColumns(expression, groupFields, expression);
            }
        }
        catch (ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ValidationException(e, info.getLine(), info.getCharPosition());
        }

        return true;
    }

    /**
     * @param exprChild
     * @param groupFields
     * @param expr
     *            Always the root Select expression.
     * @return
     * @throws ValidationException
     */
    protected boolean verifyAgainstGroupColumns(ModelContext exprChild,
            Set<Expression> groupFields, Expression expr) throws ValidationException {
        // Simple and direct, first level check.
        if (groupFields.contains(exprChild)) {
            return true;
        }

        if (exprChild.getChildCount() > 0) {
            QueryContext[] contexts = (QueryContext[]) exprChild.getChildren();

            boolean parentIsBinaryPropOrAttr = false;
            if (exprChild instanceof BinaryExpression) {
                BinaryExpression binary = (BinaryExpression) exprChild;

                int opType = binary.getOperator().getOpType();
                parentIsBinaryPropOrAttr = opType == Operator.OP_DOT || opType == Operator.OP_AT;
            }

            boolean[] contextVerifyResults = new boolean[contexts.length];
            for (int i = 0; i < contexts.length; i++) {
                contextVerifyResults[i] = verifyAgainstGroupColumns(contexts[i], groupFields, expr);

                if (contexts[i] instanceof Identifier && contextVerifyResults[i] == false) {
                    if (i == 1 && parentIsBinaryPropOrAttr && contextVerifyResults[0] == true) {
                        /*
                         * For c.a.b, the tree looks like c.a to the left and b
                         * to the right. If c.a is present in the group-by
                         * clause, then c.a.b should be allowed. Even if just
                         * "c" is present in the group-by, then c.a.b should be
                         * allowed.
                         */

                        /*
                         * Ok to return and break the loop. Because this is a
                         * binary-expression and there can only be 2 elements.
                         */
                        return true;
                    }

                    /*
                     * The fact that we've reached here is because this Ident is
                     * possibly a non group-by column. So, check progressively
                     * upwards to see if this is inside the scope of an
                     * Aggregate function.
                     */
                    boolean wrappedInAggregate = false;
                    ModelContext parent = contexts[i].getParentContext();
                    while (true) {
                        if (parent instanceof AggregateFunctionIdentifier) {
                            /*
                             * Finally. The Ident in question is wrapped in an
                             * Aggregate.
                             */
                            wrappedInAggregate = true;

                            break;
                        }
                        else if (parent == expr) {
                            // Reached the root.
                            break;
                        }

                        parent = parent.getParentContext();
                    }

                    if (wrappedInAggregate == false) {
                        throw new ValidationException(message, contexts[i].getLine(), contexts[i]
                                .getCharPosition());
                    }
                }
            }
        }
        else if (exprChild == expr && exprChild instanceof Identifier) {
            throw new ValidationException(message, expr.getLine(), expr.getCharPosition());
        }

        return false;
    }
}
