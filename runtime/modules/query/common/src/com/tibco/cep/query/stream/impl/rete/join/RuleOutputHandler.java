package com.tibco.cep.query.stream.impl.rete.join;

/*
 * Author: Ashwin Jayaprakash Date: Mar 28, 2008 Time: 3:39:13 PM
 */

interface RuleOutputHandler {
    public void handleRuleOutput(Object[] joinColumns);
}
