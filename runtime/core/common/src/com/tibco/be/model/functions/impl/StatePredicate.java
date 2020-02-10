package com.tibco.be.model.functions.impl;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author ishaan
 * @version Oct 30, 2004, 6:25:55 PM
 */
public class StatePredicate implements Predicate {
    protected ExpandedName m_name;
    protected State m_state;
    protected FunctionDomain[] fndomains = new FunctionDomain[0];

    public StatePredicate(State state, ExpandedName stateName) {
        m_state = state;
        m_name = stateName;
    }

    public String signature() {
        return code();
    }
    
    @Override
    public String signatureFormat() {
    	return code();
    }

    public ExpandedName getName() {
        return m_name;
    }

    public Class getReturnClass() {
        return java.lang.String.class;
    }

    public Class[] getThrownExceptions() {
        return new Class[0];
    }

    public Class[] getArguments() {
        return new Class[0];
    }

    public String code() {
        return getReturnClass().getName();
    }

    public String inline() {
        return code();
    }

    public boolean isValidInCondition() {
        return true;
    }

    public boolean isValidInAction() {
        return true;
    }


    public boolean isValidInBUI() {
        return true;
    }


    public boolean isValidInQuery() {
        return false;
    }

    public boolean isTimeSensitive() {
        return false;
    }

    public boolean requiresAsync() {
        return false;
    }

    public String getDocumentation() {
        return code();
    }

    public boolean reevaluate() {
        return false;
    }

    public String template() {
        return code();
    }

    public boolean doesModify() {
        return false;
    }

    public String toString() {
        String toString = '"' + m_name.getNamespaceURI() + ModelNameUtil.RULE_SEPARATOR_CHAR + m_name.getLocalName() + '"';
        return toString;
    }
    
    public boolean requiresAssert() { return false; }

    public boolean isVarargs() { return false; }
    public boolean isVarargsCodegen() { return isVarargs(); }
    public Class getVarargsCodegenArgType() {
    	Class[] args = getArguments();
    	if(args != null && args.length > 0) return args[args.length - 1];
    	else return null;
    }

	@Override
	public FunctionDomain[] getFunctionDomains() {
		return fndomains;
	}
    
    
}
