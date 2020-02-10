package com.tibco.cep.query.stream.impl.expression.function;

import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 14, 2008
* Time: 6:32:01 PM
*/
public class RuleFunctionEvaluator
        extends FunctionEvaluator {
    String uri;
    transient RuleFunction rf;
    transient RuleSessionImpl rsi;

    public RuleFunctionEvaluator(
            Class functionClass,
            ExpressionEvaluator[] argEvaluators, String uri) {
        super(functionClass, "invoke", new Class[]{Object[].class}, argEvaluators);
        this.uri=uri;
    }

    public Object evaluate(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap <String, ? extends Tuple> aliasAndTuples) {
        final Object[] args = new Object[this.argEvaluators.length];
        int i = 0;
        for (ExpressionEvaluator argEvaluator : this.argEvaluators) {
            args[i++] = argEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        }
        try {
            if(rf == null){
                rsi = (RuleSessionImpl) RuleServiceProviderManager.getInstance().getDefaultProvider().getRuleRuntime().getRuleSessions()[0];
                rf = rsi.getRuleFunction(uri);
            }

            if (RuleSessionManager.currentRuleSessions.get() == null) {
                RuleSessionManager.currentRuleSessions.set(rsi);
            }

            return rf.invoke(args);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
