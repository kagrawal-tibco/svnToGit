package com.tibco.cep.query.stream.impl.rete.join;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.impl.IdentifierImpl;
import com.tibco.cep.query.stream.expression.ComplexAndExpression;
import com.tibco.cep.query.stream.expression.ComplexAndExpressionEvaluator;
import com.tibco.cep.query.stream.expression.ComplexOrExpression;
import com.tibco.cep.query.stream.expression.ComplexOrExpressionEvaluator;
import com.tibco.cep.query.stream.expression.EqualsExpressionEvaluator;
import com.tibco.cep.query.stream.expression.EvaluatableExpression;
import com.tibco.cep.query.stream.expression.Expression;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.expression.NotExpression;
import com.tibco.cep.query.stream.expression.NotExpressionEvaluator;
import com.tibco.cep.query.stream.join.AbstractPetey;
import com.tibco.cep.query.stream.join.ExistsSimpleExpression;
import com.tibco.cep.query.stream.join.InSimpleExpression;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Oct 26, 2007 Time: 5:37:34 PM
 */

public class ExpressionConvertor {
    protected final String reqionName;

    protected final String queryName;

    public ExpressionConvertor(String reqionName, String queryName) {
        this.reqionName = reqionName;
        this.queryName = queryName;
    }

    public String getReqionName() {
        return reqionName;
    }

    public String getQueryName() {
        return queryName;
    }

    /**
     * @param expression
     * @param rule
     * @return
     * @throws RuntimeException
     */
    public Condition[] convert(Expression expression, Rule rule) {
        if (expression instanceof ComplexAndExpression) {
            Expression[] expressions = ((ComplexAndExpression) expression).getComponents();

            return handleTopAnd(expressions, rule);
        }
        else if (expression instanceof ComplexOrExpression) {
            Expression[] expressions = ((ComplexOrExpression) expression).getComponents();

            return handleTopOr(expressions, rule);
        }

        return handleTopAnd(new Expression[]{expression}, rule);
    }

    protected Condition[] handleTopAnd(Expression[] expressions, Rule rule) {
        Condition[] conditions = new Condition[expressions.length];

        for (int i = 0; i < expressions.length; i++) {
            ExpressionEvaluator evaluator = constructEvaluators(expressions[i]);

            if (evaluator instanceof EqualsExpressionEvaluator) {
                EqualsExpression equalsExpression = (EqualsExpression) expressions[i];

                Identifier[] leftIdents =
                        Helper.extractIdentifiers(equalsExpression.getLeftExpression());

                Identifier[] rightIdents =
                        Helper.extractIdentifiers(equalsExpression.getRightExpression());

                conditions[i] = new CustomEqualsCondition(rule, reqionName, queryName,
                        (EqualsExpressionEvaluator) evaluator, leftIdents, rightIdents);
            }
            else {
                Identifier[] identifiers = Helper.extractIdentifiers(expressions[i]);

                conditions[i] =
                        new CustomCondition(rule, identifiers, reqionName, queryName, evaluator);
            }
        }

        return conditions;
    }

    protected Condition[] handleTopOr(Expression[] expressions, Rule rule) {
        Map<String, Identifier> allIdentifiers = new HashMap<String, Identifier>();

        final ExpressionEvaluator[] evaluators =
                new ExpressionEvaluator[expressions.length];

        for (int i = 0; i < expressions.length; i++) {
            evaluators[i] = constructEvaluators(expressions[i]);

            Map<String, Class<? extends Tuple>> aliasAndTypes = expressions[i].getAliasAndTypes();
            for (String alias : aliasAndTypes.keySet()) {
                if (allIdentifiers.containsKey(alias) == false) {
                    Class type = aliasAndTypes.get(alias);
                    allIdentifiers.put(alias, new IdentifierImpl(type, alias));
                }
            }
        }

        final Identifier[] identifiers = allIdentifiers.values().toArray(
                new Identifier[allIdentifiers.size()]);
        Condition condition =
                new CustomOrCondition(rule, identifiers, reqionName, queryName, evaluators);

        return new Condition[]{condition};
    }

    /**
     * @param expression Should at least be an {@link EvaluatableExpression}.
     * @return
     * @throws RuntimeException
     */
    protected ExpressionEvaluator constructEvaluators(Expression expression) {
        if (expression instanceof ComplexAndExpression) {
            Expression[] andComponents = ((ComplexAndExpression) expression)
                    .getComponents();
            ExpressionEvaluator[] andEvals =
                    new ExpressionEvaluator[andComponents.length];

            for (int i = 0; i < andComponents.length; i++) {
                andEvals[i] = constructEvaluators(andComponents[i]);
            }

            // todo Optimize - do not wrap if the parent is also 'And'.
            return new ComplexAndExpressionEvaluator(andEvals);
        }
        else if (expression instanceof ComplexOrExpression) {
            Expression[] orComponents = ((ComplexOrExpression) expression).getComponents();
            ExpressionEvaluator[] orEvals =
                    new ExpressionEvaluator[orComponents.length];

            for (int i = 0; i < orEvals.length; i++) {
                orEvals[i] = constructEvaluators(orComponents[i]);
            }

            return new ComplexOrExpressionEvaluator(orEvals);
        }
        else if (expression instanceof NotExpression) {
            NotExpression notExpression = (NotExpression) expression;

            Expression innerExpr = notExpression.getInnerExpression();
            ExpressionEvaluator innerExprEval = constructEvaluators(innerExpr);

            return new NotExpressionEvaluator(innerExprEval);
        }
        else if (expression instanceof InSimpleExpression) {
            AbstractPetey petey = ((InSimpleExpression) expression).getInPetey();
            return petey.generateExpressionEvaluator();
        }
        else if (expression instanceof ExistsSimpleExpression) {
            AbstractPetey petey = ((ExistsSimpleExpression) expression).getExistsPetey();
            return petey.generateExpressionEvaluator();
        }
        else if (expression instanceof EqualsExpression) {
            EqualsExpression equalsExpression = (EqualsExpression) expression;

            Expression e = equalsExpression.getLeftExpression();
            ExpressionEvaluator leftEval = constructEvaluators(e);

            e = equalsExpression.getRightExpression();
            ExpressionEvaluator rightEval = constructEvaluators(e);

            return new EqualsExpressionEvaluator(leftEval, rightEval);
        }
        else if (expression instanceof EvaluatableExpression) {
            return (EvaluatableExpression) expression;
        }

        throw new RuntimeException("Unrecognized type: " + expression.getClass().getName());
    }
}
