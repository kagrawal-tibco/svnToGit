package com.tibco.cep.query.stream.impl.rete.join;

import java.util.LinkedList;

import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.impl.EquivalentCondition;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.core.ContextRepository;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.expression.EqualsExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 26, 2007 Time: 5:37:34 PM
 */

public class CustomEqualsCondition extends EquivalentCondition {
    protected EqualsExpressionEvaluator evaluator;

    protected FixedKeyHashMap<String, Tuple> aliasAndTuples;

    protected String leftKey;

    protected String rightKey;

    protected GlobalContext globalContext;

    protected DefaultQueryContext queryContext;

    protected String regionName;

    protected String queryName;

    protected Identifier[] leftIdents;

    protected Identifier[] rightIdents;

    /**
     * todo If the expression takes anything more than 1 item per left/right, it gets ignored.
     *
     * @param rule
     * @param regionName
     * @param queryName
     * @param evaluator
     * @param leftIdents
     * @param rightIdents
     */
    public CustomEqualsCondition(Rule rule, String regionName, String queryName,
                                 EqualsExpressionEvaluator evaluator,
                                 Identifier[] leftIdents, Identifier[] rightIdents) {
        super(rule, unifyIdentifiers(leftIdents, rightIdents));

        this.regionName = regionName;
        this.queryName = queryName;
        this.evaluator = evaluator;
        this.leftIdents = leftIdents;
        this.rightIdents = rightIdents;

        String leftIdentifierName = (leftIdents.length > 0) ? leftIdents[0].getName() : null;
        String rightIdentifierName = (rightIdents.length > 0) ? rightIdents[0].getName() : null;

        if (leftIdentifierName != null && rightIdentifierName != null) {
            this.aliasAndTuples =
                    new FixedKeyHashMap<String, Tuple>(leftIdentifierName, rightIdentifierName);

            this.leftKey = leftIdentifierName;
            this.rightKey = rightIdentifierName;
        }
        else if (leftIdentifierName == null) {
            this.aliasAndTuples =
                    new FixedKeyHashMap<String, Tuple>(rightIdentifierName);

            this.rightKey = rightIdentifierName;
        }
        else {
            this.aliasAndTuples =
                    new FixedKeyHashMap<String, Tuple>(leftIdentifierName);

            this.leftKey = leftIdentifierName;
        }
    }

    /**
     * @param leftIdents
     * @param rightIdents
     * @return
     */
    static Identifier[] unifyIdentifiers(Identifier[] leftIdents, Identifier[] rightIdents) {
        LinkedList<Identifier> idents = new LinkedList<Identifier>();

        for (Identifier leftIdent : leftIdents) {
            idents.add(leftIdent);

            //todo Uses only the first item.
            break;
        }

        for (Identifier rightIdent : rightIdents) {
            idents.add(rightIdent);

            //todo Uses only the first item.
            break;
        }

        return idents.toArray(new Identifier[idents.size()]);
    }

    public Identifier[] getLeftIdentifiers() {
        return leftIdents;
    }

    public Identifier[] getRightIdentifiers() {
        return rightIdents;
    }

    public Identifier[] _getLeftIdentifiers() {
        return leftIdents;
    }

    public Identifier[] _getRightIdentifiers() {
        return rightIdents;
    }

    private int extractHashCode(Object[] objects, boolean left) {
        if (globalContext != null) {
            return doExtraction(objects, left);
        }

        //---------

        ContextRepository repository = Registry.getInstance().getComponent(
                ContextRepository.class);
        globalContext = repository.getGlobalContext();
        queryContext = repository.getQueryContext(regionName, queryName);

        if (globalContext == null || queryContext == null) {
            throw new RuntimeException(
                    "Query: " + queryName + " Condition initiliazation did not work.");
        }

        return doExtraction(objects, left);
    }

    private int doExtraction(Object[] objects, boolean left) {
        int h = 0;

        if (left) {
            aliasAndTuples.put(leftKey, (Tuple) objects[0]);

            h = evaluator
                    .extractLeftHashCode(globalContext, queryContext, aliasAndTuples);
        }
        else {
            aliasAndTuples.put(rightKey, (Tuple) objects[1]);

            h = evaluator.extractRightHashCode(globalContext, queryContext, aliasAndTuples);
        }

        aliasAndTuples.clear();

        return h;
    }

    public int leftExpHashcode(Object[] objs) {
        return extractHashCode(objs, true);
    }

    public int rightExpHashcode(Object[] objs) {
        return extractHashCode(objs, false);
    }

    public boolean eval(Object[] objects) {
        if (leftKey != null) {
            aliasAndTuples.put(leftKey, (Tuple) objects[0]);
        }

        if (rightKey != null) {
            aliasAndTuples.put(rightKey, (Tuple) objects[1]);
        }

        boolean b = evaluator.evaluateBoolean(globalContext, queryContext, aliasAndTuples);

        aliasAndTuples.clear();

        return b;
    }
}
