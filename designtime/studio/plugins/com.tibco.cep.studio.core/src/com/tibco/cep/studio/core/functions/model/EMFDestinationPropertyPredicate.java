package com.tibco.cep.studio.core.functions.model;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import java.util.Arrays;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.functions.Predicate;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFDestinationPropertyPredicate implements Predicate {

	protected ExpandedName m_name;
	// protected PropertyInstance m_property;
	protected Destination m_destination;
	protected FunctionDomain[] fndomains = new FunctionDomain[] { ACTION, CONDITION };

	public EMFDestinationPropertyPredicate(Destination destination, ExpandedName propName) {
		m_destination = destination;
		// m_property = destinationProp;
		m_name = propName;
	}

	@Override
	public String code() {
		return toString();
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

	public boolean isValidInCondition() {
		return Arrays.asList(fndomains).contains(CONDITION);
		// return true;
	}

	public boolean isValidInAction() {
		return Arrays.asList(fndomains).contains(ACTION);
		// return true;
	}

	public boolean isValidInBUI() {
		return Arrays.asList(fndomains).contains(ACTION);
		// return true;
	}

	public boolean isValidInQuery() {
		return Arrays.asList(fndomains).contains(QUERY);
		// return false;
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

	public Destination getDestination() {
		return m_destination;
	}

	public String toString() {
		return '"' + this.m_name.localName + '"';
	}

	public boolean isVarargs() {
		return false;
	}
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
