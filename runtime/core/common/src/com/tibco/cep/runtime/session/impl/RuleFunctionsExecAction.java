package com.tibco.cep.runtime.session.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.cep.kernel.core.rete.CompositeAction;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;

/*
* User: Nicolas Prade
* Date: Sep 17, 2009
* Time: 6:39:14 PM
*/


public class RuleFunctionsExecAction implements CompositeAction 
{
    private List<RuleFunction> ruleFunctions;


    public RuleFunctionsExecAction(
            List<RuleFunction> ruleFunctions) {
        if (null == ruleFunctions) {
            ruleFunctions = new ArrayList<RuleFunction>(0);
        }
        this.ruleFunctions = Collections.unmodifiableList(ruleFunctions);
    }


    public void execute(Object[] objects) {
    	for(int ii = 0, count = getComponentCount(); ii < count; ii++) {
    		executeComponent(ii, objects);
    	}
    }


    public Identifier[] getIdentifiers() {
        return null;
    }


    public Rule getRule() {
        return null;
    }


    public List<RuleFunction> getRuleFunctions() {
        return this.ruleFunctions;
    }


	@Override
	public int getComponentCount() {
		return ruleFunctions.size();
	}


	@Override
	public void executeComponent(int index, Object[] objects) {
		ruleFunctions.get(index).invoke((Object[]) null);
	}
}
