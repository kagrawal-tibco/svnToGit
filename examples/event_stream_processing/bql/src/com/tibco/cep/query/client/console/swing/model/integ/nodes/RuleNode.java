package com.tibco.cep.query.client.console.swing.model.integ.nodes;

import com.tibco.cep.designtime.model.rule.Rule;

/**
 *
 * @author ksubrama
 */
public class RuleNode extends AbstractNode {
    private static final long serialVersionUID = 1L;
    private final Rule rule;

    public RuleNode(Rule rule) {
        super(rule);
        this.rule = rule;
        initProperties();
    }

    @Override
    protected void initProperties() {
        properties.put(NAME, getNonNullValue(rule.getName()));
        properties.put(NAMESPACE, getNonNullValue(rule.getNamespace()));
        properties.put(ALIAS, getNonNullValue(rule.getAlias()));
        properties.put(PRIORITY, String.valueOf(rule.getPriority()));
        properties.put(PATH, getNonNullValue(rule.getFullPath()));
        properties.put(SIGNATURE, getSignature(rule.getName(),
                rule.getReturnType(), rule.getDeclarations().getSymbolsList()));        
    }
}
