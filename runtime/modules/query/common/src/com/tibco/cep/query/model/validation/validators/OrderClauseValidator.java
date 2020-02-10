package com.tibco.cep.query.model.validation.validators;

import java.util.HashSet;
import java.util.Set;

import com.tibco.cep.query.model.BindVariable;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.Identifier;
import com.tibco.cep.query.model.OrderClause;
import com.tibco.cep.query.model.Projection;
import com.tibco.cep.query.model.ProjectionAttributes;
import com.tibco.cep.query.model.QueryContext;
import com.tibco.cep.query.model.SelectContext;
import com.tibco.cep.query.model.SortCriterion;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;

/*
 * Author: Ashwin Jayaprakash Date: Dec 13, 2007 Time: 12:08:10 PM
 */

public class OrderClauseValidator implements Validator<OrderClause> {
    protected static final String message = "Elements in the Order-by clause cannot use any column other than the"
            + " columns referred to in the Projection list.";

    public boolean validate(OrderClause info) throws ValidationException {
        try {
            SelectContext selectContext = (SelectContext)info.getOwnerContext();

            ProjectionAttributes projectionAttributes = selectContext.getProjectionAttributes();
            Projection[] projections = projectionAttributes.getAllProjections();
            HashSet<Expression> selectColumns = new HashSet<Expression>();
            for (Projection projection : projections) {
                Expression expression = projection.getExpression();
                selectColumns.add(expression);
            }

            SortCriterion[] criteria = info.getSortCriteria();
            for (SortCriterion criterion : criteria) {
                Expression expression = criterion.getExpression();
                TypeInfo typeInfo = expression.getTypeInfo();

                lookForIdentifiers(expression, selectColumns);

                if (typeInfo.isAtom() == false || typeInfo.isComparable() == false) {
                    throw new ValidationException(
                            "Elements in the Order-by clause should be atoms and Comparable.",
                            criterion.getLine(), criterion.getCharPosition());
                }

                final Object first = criterion.getLimitFirst();
                if (!(
                        ((first instanceof Integer) && (((Integer) first) > 0))
                        || (first instanceof BindVariable) && (((BindVariable) first).getTypeInfo().isInt())
                )) {
                    throw new ValidationException("Limit-first must be a number greater than 0.",
                            criterion.getLine(), criterion.getCharPosition());
                }

                final Object offset = criterion.getLimitOffset();
                if (!(
                        ((offset instanceof Integer) && (((Integer) offset) >= 0))
                        || (offset instanceof BindVariable) && (((BindVariable) offset).getTypeInfo().isInt())
                )) {
                    throw new ValidationException(
                            "Limit-offset must be a number greater than or equal to 0.", criterion
                                    .getLine(), criterion.getCharPosition());
                }
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

    protected boolean lookForIdentifiers(QueryContext expr, Set<Expression> selectColumns)
            throws ValidationException {
        if (selectColumns.contains(expr)) {
            return true;
        }

        if (expr.getChildCount() > 0) {
            QueryContext[] children = (QueryContext[]) expr.getChildren();

            for (QueryContext child : children) {
                lookForIdentifiers(child, selectColumns);
            }
        }
        else if (expr instanceof Identifier) {
            throw new ValidationException(message, expr.getLine(), expr.getCharPosition());
        }

        return false;
    }
}
