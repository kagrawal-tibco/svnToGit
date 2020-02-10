package com.tibco.cep.query.model.validation.validators;

import java.util.HashSet;
import java.util.Set;

import com.tibco.cep.query.model.AggregateFunctionIdentifier;
import com.tibco.cep.query.model.GroupClause;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.Projection;
import com.tibco.cep.query.model.ProjectionAttributes;
import com.tibco.cep.query.model.TypeNames;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;

/*
 * Author: Ashwin Jayaprakash Date: Dec 12, 2007 Time: 11:31:53 AM
 */

public class ProjectionAttributesValidator implements Validator<ProjectionAttributes> {
    public boolean validate(ProjectionAttributes info) throws ValidationException {
        ModelContext parent = info.getParentContext();

        Projection[] projections = info.getAllProjections();

        Set<AggregateFunctionIdentifier> set = new HashSet<AggregateFunctionIdentifier>();
        for (Projection projection : projections) {
            checkAndCollectAggregates(projection, set);
        }

        GroupClause groupClause = (GroupClause) parent.getContextMap().get(GroupClause.CTX_ID);
        if (groupClause == null && set.isEmpty() == false) {
            AggregateFunctionIdentifier ident = set.iterator().next();

            throw new ValidationException("Aggregates can be used in the Projection"
                    + " list only if an appropriate Group-by clause is specified.",
                    ident.getLine(), ident.getCharPosition());
        }

        return true;
    }

    /**
     * Also validates and disallows {@link TypeNames#PENDING_COUNT}.
     * 
     * @param context
     * @param aggregateFunctions
     *            Adds Aggregates to this set if there are any.
     * @throws ValidationException
     */
    protected void checkAndCollectAggregates(ModelContext context,
            Set<AggregateFunctionIdentifier> aggregateFunctions) throws ValidationException {
        if (context.getChildCount() > 0) {
            ModelContext[] contexts = context.getChildren();

            for (ModelContext childCtx : contexts) {
                if (childCtx instanceof AggregateFunctionIdentifier) {
                    AggregateFunctionIdentifier function = (AggregateFunctionIdentifier) childCtx;
                    String fName = function.getName();

                    if (TypeNames.PENDING_COUNT.equalsIgnoreCase(fName)) {
                        throw new ValidationException("The " + TypeNames.PENDING_COUNT
                                + " aggregate function cannot be used in this context.", function
                                .getLine(), function.getCharPosition());
                    }

                    aggregateFunctions.add(function);
                }
                else {
                    checkAndCollectAggregates(childCtx, aggregateFunctions);
                }
            }
        }
    }
}
