package com.tibco.cep.studio.core.functions.model;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.functions.Predicate;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFStatePredicate implements Predicate {

    protected ExpandedName m_name;
    protected State m_state;
    protected FunctionDomain[] fndomains = new FunctionDomain[0];

    public EMFStatePredicate(State state, ExpandedName stateName) {
        m_state = state;
        m_name = stateName;
    }

	@Override
	public String code() {
		return getReturnClass().getName();
	}

	@Override
	public boolean doesModify() {
		return false;
	}

	@Override
	public Class[] getArguments() {
		return new Class[0];
	}

	@Override
	public String getDocumentation() {
		return code();
	}

	@Override
	public ExpandedName getName() {
		return m_name;
	}

	@Override
	public Class getReturnClass() {
		return java.lang.String.class;
	}

	@Override
	public Class[] getThrownExceptions() {
		return new Class[0];
	}

	@Override
	public String inline() {
		return code();
	}

	@Override
	public boolean isTimeSensitive() {
		return false;
	}

	@Override
	public boolean isValidInAction() {
		return true;
	}

	@Override
	public boolean isValidInBUI() {
		return true;
	}

	@Override
	public boolean isValidInCondition() {
		return true;
	}

	@Override
	public boolean isValidInQuery() {
		return false;
	}

	@Override
	public boolean reevaluate() {
		return false;
	}

	@Override
	public boolean requiresAssert() {
		return false;
	}

	@Override
	public boolean requiresAsync() {
		return false;
	}

	@Override
	public String signature() {
		return code();
	}
	
	@Override
	public String signatureFormat() {
		return code();
	}

	@Override
	public String template() {
		return code();
	}

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
