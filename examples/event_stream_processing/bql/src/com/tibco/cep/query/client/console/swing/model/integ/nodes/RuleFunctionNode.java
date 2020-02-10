package com.tibco.cep.query.client.console.swing.model.integ.nodes;

import com.tibco.cep.designtime.model.rule.RuleFunction;

/**
 *
 * @author ksubrama
 */
public class RuleFunctionNode extends AbstractNode {
    private static final long serialVersionUID = 1L;
    private final RuleFunction ruleFunction;

    public RuleFunctionNode(RuleFunction ruleFunction) {
        super(ruleFunction);
        this.ruleFunction = ruleFunction;
        initProperties();
    }

    @Override
    protected void initProperties() {
        properties.put(NAME, getNonNullValue(ruleFunction.getName()));
        properties.put(NAMESPACE, getNonNullValue(ruleFunction.getNamespace()));
        properties.put(ALIAS, getNonNullValue(ruleFunction.getAlias()));
        properties.put(PATH, getNonNullValue(ruleFunction.getFullPath()));
        properties.put(SIGNATURE, getSignature(ruleFunction.getName(),
                ruleFunction.getReturnType(), ruleFunction.getArguments().getSymbolsList()));
    }



}
